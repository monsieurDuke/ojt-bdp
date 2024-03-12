package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import androidx.core.provider.FontsContractCompat.FontInfo;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

class TypefaceCompatApi24Impl
  extends TypefaceCompatBaseImpl
{
  private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  private static final String TAG = "TypefaceCompatApi24Impl";
  private static final Method sAddFontWeightStyle;
  private static final Method sCreateFromFamiliesWithDefault;
  private static final Class<?> sFontFamily;
  private static final Constructor<?> sFontFamilyCtor;
  
  static
  {
    try
    {
      Class localClass1 = Class.forName("android.graphics.FontFamily");
      localConstructor = localClass1.getConstructor(new Class[0]);
      localMethod2 = localClass1.getMethod("addFontWeightStyle", new Class[] { ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE });
      localMethod1 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(localClass1, 1).getClass() });
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    Log.e("TypefaceCompatApi24Impl", localClassNotFoundException.getClass().getName(), localClassNotFoundException);
    Constructor localConstructor = null;
    Method localMethod2 = null;
    Class localClass2 = null;
    Method localMethod1 = null;
    sFontFamilyCtor = localConstructor;
    sFontFamily = localClass2;
    sAddFontWeightStyle = localMethod2;
    sCreateFromFamiliesWithDefault = localMethod1;
  }
  
  private static boolean addFontWeightStyle(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    try
    {
      paramBoolean = ((Boolean)sAddFontWeightStyle.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Boolean.valueOf(paramBoolean) })).booleanValue();
      return paramBoolean;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    return false;
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject)
  {
    try
    {
      Object localObject = Array.newInstance(sFontFamily, 1);
      Array.set(localObject, 0, paramObject);
      paramObject = (Typeface)sCreateFromFamiliesWithDefault.invoke(null, new Object[] { localObject });
      return (Typeface)paramObject;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    return null;
  }
  
  public static boolean isUsable()
  {
    Method localMethod = sAddFontWeightStyle;
    if (localMethod == null) {
      Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
    }
    boolean bool;
    if (localMethod != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static Object newFamily()
  {
    try
    {
      Object localObject = sFontFamilyCtor.newInstance(new Object[0]);
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException) {}catch (InstantiationException localInstantiationException) {}catch (IllegalAccessException localIllegalAccessException) {}
    return null;
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    Object localObject1 = newFamily();
    if (localObject1 == null) {
      return null;
    }
    for (Object localObject2 : paramFontFamilyFilesResourceEntry.getEntries())
    {
      ByteBuffer localByteBuffer = TypefaceCompatUtil.copyToDirectBuffer(paramContext, paramResources, ((FontResourcesParserCompat.FontFileResourceEntry)localObject2).getResourceId());
      if (localByteBuffer == null) {
        return null;
      }
      if (!addFontWeightStyle(localObject1, localByteBuffer, ((FontResourcesParserCompat.FontFileResourceEntry)localObject2).getTtcIndex(), ((FontResourcesParserCompat.FontFileResourceEntry)localObject2).getWeight(), ((FontResourcesParserCompat.FontFileResourceEntry)localObject2).isItalic())) {
        return null;
      }
    }
    return createFromFamiliesWithDefault(localObject1);
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    Object localObject = newFamily();
    if (localObject == null) {
      return null;
    }
    SimpleArrayMap localSimpleArrayMap = new SimpleArrayMap();
    int j = paramArrayOfFontInfo.length;
    for (int i = 0; i < j; i++)
    {
      FontsContractCompat.FontInfo localFontInfo = paramArrayOfFontInfo[i];
      Uri localUri = localFontInfo.getUri();
      ByteBuffer localByteBuffer2 = (ByteBuffer)localSimpleArrayMap.get(localUri);
      ByteBuffer localByteBuffer1 = localByteBuffer2;
      if (localByteBuffer2 == null)
      {
        localByteBuffer1 = TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, localUri);
        localSimpleArrayMap.put(localUri, localByteBuffer1);
      }
      if (localByteBuffer1 == null) {
        return null;
      }
      if (!addFontWeightStyle(localObject, localByteBuffer1, localFontInfo.getTtcIndex(), localFontInfo.getWeight(), localFontInfo.isItalic())) {
        return null;
      }
    }
    paramContext = createFromFamiliesWithDefault(localObject);
    if (paramContext == null) {
      return null;
    }
    return Typeface.create(paramContext, paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/TypefaceCompatApi24Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */