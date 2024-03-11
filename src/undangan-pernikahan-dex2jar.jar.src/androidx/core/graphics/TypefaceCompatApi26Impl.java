package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TypefaceCompatApi26Impl
  extends TypefaceCompatApi21Impl
{
  private static final String ABORT_CREATION_METHOD = "abortCreation";
  private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
  private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  private static final String FREEZE_METHOD = "freeze";
  private static final int RESOLVE_BY_FONT_TABLE = -1;
  private static final String TAG = "TypefaceCompatApi26Impl";
  protected final Method mAbortCreation;
  protected final Method mAddFontFromAssetManager;
  protected final Method mAddFontFromBuffer;
  protected final Method mCreateFromFamiliesWithDefault;
  protected final Class<?> mFontFamily;
  protected final Constructor<?> mFontFamilyCtor;
  protected final Method mFreeze;
  
  public TypefaceCompatApi26Impl()
  {
    try
    {
      localClass = obtainFontFamily();
      localConstructor = obtainFontFamilyCtor(localClass);
      localMethod5 = obtainAddFontFromAssetManagerMethod(localClass);
      localMethod4 = obtainAddFontFromBufferMethod(localClass);
      localMethod6 = obtainFreezeMethod(localClass);
      localMethod3 = obtainAbortCreationMethod(localClass);
      Method localMethod1 = obtainCreateFromFamiliesWithDefaultMethod(localClass);
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    Log.e("TypefaceCompatApi26Impl", "Unable to collect necessary methods for class " + localClassNotFoundException.getClass().getName(), localClassNotFoundException);
    Class localClass = null;
    Constructor localConstructor = null;
    Method localMethod5 = null;
    Method localMethod4 = null;
    Method localMethod6 = null;
    Method localMethod3 = null;
    Method localMethod2 = null;
    this.mFontFamily = localClass;
    this.mFontFamilyCtor = localConstructor;
    this.mAddFontFromAssetManager = localMethod5;
    this.mAddFontFromBuffer = localMethod4;
    this.mFreeze = localMethod6;
    this.mAbortCreation = localMethod3;
    this.mCreateFromFamiliesWithDefault = localMethod2;
  }
  
  private void abortCreation(Object paramObject)
  {
    try
    {
      this.mAbortCreation.invoke(paramObject, new Object[0]);
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
  }
  
  private boolean addFontFromAssetManager(Context paramContext, Object paramObject, String paramString, int paramInt1, int paramInt2, int paramInt3, FontVariationAxis[] paramArrayOfFontVariationAxis)
  {
    try
    {
      boolean bool = ((Boolean)this.mAddFontFromAssetManager.invoke(paramObject, new Object[] { paramContext.getAssets(), paramString, Integer.valueOf(0), Boolean.valueOf(false), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), paramArrayOfFontVariationAxis })).booleanValue();
      return bool;
    }
    catch (InvocationTargetException paramContext) {}catch (IllegalAccessException paramContext) {}
    return false;
  }
  
  private boolean addFontFromBuffer(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      boolean bool = ((Boolean)this.mAddFontFromBuffer.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) })).booleanValue();
      return bool;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    return false;
  }
  
  private boolean freeze(Object paramObject)
  {
    try
    {
      boolean bool = ((Boolean)this.mFreeze.invoke(paramObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    return false;
  }
  
  private boolean isFontFamilyPrivateAPIAvailable()
  {
    if (this.mAddFontFromAssetManager == null) {
      Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
    }
    boolean bool;
    if (this.mAddFontFromAssetManager != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private Object newFamily()
  {
    try
    {
      Object localObject = this.mFontFamilyCtor.newInstance(new Object[0]);
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException) {}catch (InstantiationException localInstantiationException) {}catch (IllegalAccessException localIllegalAccessException) {}
    return null;
  }
  
  protected Typeface createFromFamiliesWithDefault(Object paramObject)
  {
    try
    {
      Object localObject = Array.newInstance(this.mFontFamily, 1);
      Array.set(localObject, 0, paramObject);
      paramObject = (Typeface)this.mCreateFromFamiliesWithDefault.invoke(null, new Object[] { localObject, Integer.valueOf(-1), Integer.valueOf(-1) });
      return (Typeface)paramObject;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    return null;
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    if (!isFontFamilyPrivateAPIAvailable()) {
      return super.createFromFontFamilyFilesResourceEntry(paramContext, paramFontFamilyFilesResourceEntry, paramResources, paramInt);
    }
    paramResources = newFamily();
    if (paramResources == null) {
      return null;
    }
    for (Object localObject : paramFontFamilyFilesResourceEntry.getEntries()) {
      if (!addFontFromAssetManager(paramContext, paramResources, ((FontResourcesParserCompat.FontFileResourceEntry)localObject).getFileName(), ((FontResourcesParserCompat.FontFileResourceEntry)localObject).getTtcIndex(), ((FontResourcesParserCompat.FontFileResourceEntry)localObject).getWeight(), ((FontResourcesParserCompat.FontFileResourceEntry)localObject).isItalic(), FontVariationAxis.fromFontVariationSettings(((FontResourcesParserCompat.FontFileResourceEntry)localObject).getVariationSettings())))
      {
        abortCreation(paramResources);
        return null;
      }
    }
    if (!freeze(paramResources)) {
      return null;
    }
    return createFromFamiliesWithDefault(paramResources);
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, androidx.core.provider.FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_3
    //   1: arraylength
    //   2: iconst_1
    //   3: if_icmpge +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: aload_0
    //   9: invokespecial 185	androidx/core/graphics/TypefaceCompatApi26Impl:isFontFamilyPrivateAPIAvailable	()Z
    //   12: ifne +106 -> 118
    //   15: aload_0
    //   16: aload_3
    //   17: iload 4
    //   19: invokevirtual 235	androidx/core/graphics/TypefaceCompatApi26Impl:findBestInfo	([Landroidx/core/provider/FontsContractCompat$FontInfo;I)Landroidx/core/provider/FontsContractCompat$FontInfo;
    //   22: astore_3
    //   23: aload_1
    //   24: invokevirtual 239	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   27: astore_1
    //   28: aload_1
    //   29: aload_3
    //   30: invokevirtual 245	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   33: ldc -9
    //   35: aload_2
    //   36: invokevirtual 253	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   39: astore_1
    //   40: aload_1
    //   41: ifnonnull +13 -> 54
    //   44: aload_1
    //   45: ifnull +7 -> 52
    //   48: aload_1
    //   49: invokevirtual 258	android/os/ParcelFileDescriptor:close	()V
    //   52: aconst_null
    //   53: areturn
    //   54: new 260	android/graphics/Typeface$Builder
    //   57: astore_2
    //   58: aload_2
    //   59: aload_1
    //   60: invokevirtual 264	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   63: invokespecial 267	android/graphics/Typeface$Builder:<init>	(Ljava/io/FileDescriptor;)V
    //   66: aload_2
    //   67: aload_3
    //   68: invokevirtual 268	androidx/core/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   71: invokevirtual 272	android/graphics/Typeface$Builder:setWeight	(I)Landroid/graphics/Typeface$Builder;
    //   74: aload_3
    //   75: invokevirtual 273	androidx/core/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   78: invokevirtual 277	android/graphics/Typeface$Builder:setItalic	(Z)Landroid/graphics/Typeface$Builder;
    //   81: invokevirtual 281	android/graphics/Typeface$Builder:build	()Landroid/graphics/Typeface;
    //   84: astore_2
    //   85: aload_1
    //   86: ifnull +7 -> 93
    //   89: aload_1
    //   90: invokevirtual 258	android/os/ParcelFileDescriptor:close	()V
    //   93: aload_2
    //   94: areturn
    //   95: astore_2
    //   96: aload_1
    //   97: ifnull +16 -> 113
    //   100: aload_1
    //   101: invokevirtual 258	android/os/ParcelFileDescriptor:close	()V
    //   104: goto +9 -> 113
    //   107: astore_1
    //   108: aload_2
    //   109: aload_1
    //   110: invokevirtual 287	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   113: aload_2
    //   114: athrow
    //   115: astore_1
    //   116: aconst_null
    //   117: areturn
    //   118: aload_1
    //   119: aload_3
    //   120: aload_2
    //   121: invokestatic 293	androidx/core/graphics/TypefaceCompatUtil:readFontInfoIntoByteBuffer	(Landroid/content/Context;[Landroidx/core/provider/FontsContractCompat$FontInfo;Landroid/os/CancellationSignal;)Ljava/util/Map;
    //   124: astore_2
    //   125: aload_0
    //   126: invokespecial 189	androidx/core/graphics/TypefaceCompatApi26Impl:newFamily	()Ljava/lang/Object;
    //   129: astore 8
    //   131: aload 8
    //   133: ifnonnull +5 -> 138
    //   136: aconst_null
    //   137: areturn
    //   138: aload_3
    //   139: arraylength
    //   140: istore 7
    //   142: iconst_0
    //   143: istore 6
    //   145: iconst_0
    //   146: istore 5
    //   148: iload 5
    //   150: iload 7
    //   152: if_icmpge +71 -> 223
    //   155: aload_3
    //   156: iload 5
    //   158: aaload
    //   159: astore_1
    //   160: aload_2
    //   161: aload_1
    //   162: invokevirtual 245	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   165: invokeinterface 299 2 0
    //   170: checkcast 301	java/nio/ByteBuffer
    //   173: astore 9
    //   175: aload 9
    //   177: ifnonnull +6 -> 183
    //   180: goto +37 -> 217
    //   183: aload_0
    //   184: aload 8
    //   186: aload 9
    //   188: aload_1
    //   189: invokevirtual 302	androidx/core/provider/FontsContractCompat$FontInfo:getTtcIndex	()I
    //   192: aload_1
    //   193: invokevirtual 268	androidx/core/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   196: aload_1
    //   197: invokevirtual 273	androidx/core/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   200: invokespecial 304	androidx/core/graphics/TypefaceCompatApi26Impl:addFontFromBuffer	(Ljava/lang/Object;Ljava/nio/ByteBuffer;III)Z
    //   203: ifne +11 -> 214
    //   206: aload_0
    //   207: aload 8
    //   209: invokespecial 223	androidx/core/graphics/TypefaceCompatApi26Impl:abortCreation	(Ljava/lang/Object;)V
    //   212: aconst_null
    //   213: areturn
    //   214: iconst_1
    //   215: istore 6
    //   217: iinc 5 1
    //   220: goto -72 -> 148
    //   223: iload 6
    //   225: ifne +11 -> 236
    //   228: aload_0
    //   229: aload 8
    //   231: invokespecial 223	androidx/core/graphics/TypefaceCompatApi26Impl:abortCreation	(Ljava/lang/Object;)V
    //   234: aconst_null
    //   235: areturn
    //   236: aload_0
    //   237: aload 8
    //   239: invokespecial 225	androidx/core/graphics/TypefaceCompatApi26Impl:freeze	(Ljava/lang/Object;)Z
    //   242: ifne +5 -> 247
    //   245: aconst_null
    //   246: areturn
    //   247: aload_0
    //   248: aload 8
    //   250: invokevirtual 227	androidx/core/graphics/TypefaceCompatApi26Impl:createFromFamiliesWithDefault	(Ljava/lang/Object;)Landroid/graphics/Typeface;
    //   253: astore_1
    //   254: aload_1
    //   255: ifnonnull +5 -> 260
    //   258: aconst_null
    //   259: areturn
    //   260: aload_1
    //   261: iload 4
    //   263: invokestatic 308	android/graphics/Typeface:create	(Landroid/graphics/Typeface;I)Landroid/graphics/Typeface;
    //   266: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	267	0	this	TypefaceCompatApi26Impl
    //   0	267	1	paramContext	Context
    //   0	267	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	267	3	paramArrayOfFontInfo	androidx.core.provider.FontsContractCompat.FontInfo[]
    //   0	267	4	paramInt	int
    //   146	72	5	i	int
    //   143	81	6	j	int
    //   140	13	7	k	int
    //   129	120	8	localObject	Object
    //   173	14	9	localByteBuffer	ByteBuffer
    // Exception table:
    //   from	to	target	type
    //   54	85	95	finally
    //   100	104	107	finally
    //   28	40	115	java/io/IOException
    //   48	52	115	java/io/IOException
    //   89	93	115	java/io/IOException
    //   108	113	115	java/io/IOException
    //   113	115	115	java/io/IOException
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    if (!isFontFamilyPrivateAPIAvailable()) {
      return super.createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramString, paramInt2);
    }
    paramResources = newFamily();
    if (paramResources == null) {
      return null;
    }
    if (!addFontFromAssetManager(paramContext, paramResources, paramString, 0, -1, -1, null))
    {
      abortCreation(paramResources);
      return null;
    }
    if (!freeze(paramResources)) {
      return null;
    }
    return createFromFamiliesWithDefault(paramResources);
  }
  
  protected Method obtainAbortCreationMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("abortCreation", new Class[0]);
  }
  
  protected Method obtainAddFontFromAssetManagerMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("addFontFromAssetManager", new Class[] { AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class });
  }
  
  protected Method obtainAddFontFromBufferMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("addFontFromBuffer", new Class[] { ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE });
  }
  
  protected Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    paramClass = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(paramClass, 1).getClass(), Integer.TYPE, Integer.TYPE });
    paramClass.setAccessible(true);
    return paramClass;
  }
  
  protected Class<?> obtainFontFamily()
    throws ClassNotFoundException
  {
    return Class.forName("android.graphics.FontFamily");
  }
  
  protected Constructor<?> obtainFontFamilyCtor(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getConstructor(new Class[0]);
  }
  
  protected Method obtainFreezeMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("freeze", new Class[0]);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/TypefaceCompatApi26Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */