package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourcesCompat
{
  public static final int ID_NULL = 0;
  private static final String TAG = "ResourcesCompat";
  private static final Object sColorStateCacheLock = new Object();
  private static final WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> sColorStateCaches;
  private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal();
  
  static
  {
    sColorStateCaches = new WeakHashMap(0);
  }
  
  private static void addColorStateListToCache(ColorStateListCacheKey paramColorStateListCacheKey, int paramInt, ColorStateList paramColorStateList, Resources.Theme paramTheme)
  {
    synchronized (sColorStateCacheLock)
    {
      WeakHashMap localWeakHashMap = sColorStateCaches;
      Object localObject2 = (SparseArray)localWeakHashMap.get(paramColorStateListCacheKey);
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new android/util/SparseArray;
        ((SparseArray)localObject1).<init>();
        localWeakHashMap.put(paramColorStateListCacheKey, localObject1);
      }
      localObject2 = new androidx/core/content/res/ResourcesCompat$ColorStateListCacheEntry;
      ((ColorStateListCacheEntry)localObject2).<init>(paramColorStateList, paramColorStateListCacheKey.mResources.getConfiguration(), paramTheme);
      ((SparseArray)localObject1).append(paramInt, localObject2);
      return;
    }
  }
  
  public static void clearCachesForTheme(Resources.Theme paramTheme)
  {
    synchronized (sColorStateCacheLock)
    {
      Iterator localIterator = sColorStateCaches.keySet().iterator();
      while (localIterator.hasNext())
      {
        ColorStateListCacheKey localColorStateListCacheKey = (ColorStateListCacheKey)localIterator.next();
        if ((localColorStateListCacheKey != null) && (paramTheme.equals(localColorStateListCacheKey.mTheme))) {
          localIterator.remove();
        }
      }
      return;
    }
  }
  
  private static ColorStateList getCachedColorStateList(ColorStateListCacheKey paramColorStateListCacheKey, int paramInt)
  {
    synchronized (sColorStateCacheLock)
    {
      SparseArray localSparseArray = (SparseArray)sColorStateCaches.get(paramColorStateListCacheKey);
      if ((localSparseArray != null) && (localSparseArray.size() > 0))
      {
        ColorStateListCacheEntry localColorStateListCacheEntry = (ColorStateListCacheEntry)localSparseArray.get(paramInt);
        if (localColorStateListCacheEntry != null)
        {
          if ((localColorStateListCacheEntry.mConfiguration.equals(paramColorStateListCacheKey.mResources.getConfiguration())) && (((paramColorStateListCacheKey.mTheme == null) && (localColorStateListCacheEntry.mThemeHash == 0)) || ((paramColorStateListCacheKey.mTheme != null) && (localColorStateListCacheEntry.mThemeHash == paramColorStateListCacheKey.mTheme.hashCode()))))
          {
            paramColorStateListCacheKey = localColorStateListCacheEntry.mValue;
            return paramColorStateListCacheKey;
          }
          localSparseArray.remove(paramInt);
        }
      }
      return null;
    }
  }
  
  public static Typeface getCachedFont(Context paramContext, int paramInt)
    throws Resources.NotFoundException
  {
    if (paramContext.isRestricted()) {
      return null;
    }
    return loadFont(paramContext, paramInt, new TypedValue(), 0, null, null, false, true);
  }
  
  public static int getColor(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getColor(paramResources, paramInt, paramTheme);
    }
    return paramResources.getColor(paramInt);
  }
  
  public static ColorStateList getColorStateList(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    ColorStateListCacheKey localColorStateListCacheKey = new ColorStateListCacheKey(paramResources, paramTheme);
    ColorStateList localColorStateList = getCachedColorStateList(localColorStateListCacheKey, paramInt);
    if (localColorStateList != null) {
      return localColorStateList;
    }
    localColorStateList = inflateColorStateList(paramResources, paramInt, paramTheme);
    if (localColorStateList != null)
    {
      addColorStateListToCache(localColorStateListCacheKey, paramInt, localColorStateList, paramTheme);
      return localColorStateList;
    }
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getColorStateList(paramResources, paramInt, paramTheme);
    }
    return paramResources.getColorStateList(paramInt);
  }
  
  public static Drawable getDrawable(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getDrawable(paramResources, paramInt, paramTheme);
    }
    return paramResources.getDrawable(paramInt);
  }
  
  public static Drawable getDrawableForDensity(Resources paramResources, int paramInt1, int paramInt2, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getDrawableForDensity(paramResources, paramInt1, paramInt2, paramTheme);
    }
    if (Build.VERSION.SDK_INT >= 15) {
      return Api15Impl.getDrawableForDensity(paramResources, paramInt1, paramInt2);
    }
    return paramResources.getDrawable(paramInt1);
  }
  
  public static float getFloat(Resources paramResources, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 29) {
      return Api29Impl.getFloat(paramResources, paramInt);
    }
    Object localObject = getTypedValue();
    paramResources.getValue(paramInt, (TypedValue)localObject, true);
    if (((TypedValue)localObject).type == 4) {
      return ((TypedValue)localObject).getFloat();
    }
    StringBuilder localStringBuilder = new StringBuilder().append("Resource ID #0x");
    paramResources = Integer.toHexString(paramInt);
    Log5ECF72.a(paramResources);
    LogE84000.a(paramResources);
    Log229316.a(paramResources);
    paramResources = localStringBuilder.append(paramResources).append(" type #0x");
    localObject = Integer.toHexString(((TypedValue)localObject).type);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw new Resources.NotFoundException((String)localObject + " is not valid");
  }
  
  public static Typeface getFont(Context paramContext, int paramInt)
    throws Resources.NotFoundException
  {
    if (paramContext.isRestricted()) {
      return null;
    }
    return loadFont(paramContext, paramInt, new TypedValue(), 0, null, null, false, false);
  }
  
  public static Typeface getFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, FontCallback paramFontCallback)
    throws Resources.NotFoundException
  {
    if (paramContext.isRestricted()) {
      return null;
    }
    return loadFont(paramContext, paramInt1, paramTypedValue, paramInt2, paramFontCallback, null, true, false);
  }
  
  public static void getFont(Context paramContext, int paramInt, FontCallback paramFontCallback, Handler paramHandler)
    throws Resources.NotFoundException
  {
    Preconditions.checkNotNull(paramFontCallback);
    if (paramContext.isRestricted())
    {
      paramFontCallback.callbackFailAsync(-4, paramHandler);
      return;
    }
    loadFont(paramContext, paramInt, new TypedValue(), 0, paramFontCallback, paramHandler, false, false);
  }
  
  private static TypedValue getTypedValue()
  {
    ThreadLocal localThreadLocal = sTempTypedValue;
    TypedValue localTypedValue2 = (TypedValue)localThreadLocal.get();
    TypedValue localTypedValue1 = localTypedValue2;
    if (localTypedValue2 == null)
    {
      localTypedValue1 = new TypedValue();
      localThreadLocal.set(localTypedValue1);
    }
    return localTypedValue1;
  }
  
  private static ColorStateList inflateColorStateList(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    if (isColorInt(paramResources, paramInt)) {
      return null;
    }
    XmlResourceParser localXmlResourceParser = paramResources.getXml(paramInt);
    try
    {
      paramResources = ColorStateListInflaterCompat.createFromXml(paramResources, localXmlResourceParser, paramTheme);
      return paramResources;
    }
    catch (Exception paramResources)
    {
      Log.w("ResourcesCompat", "Failed to inflate ColorStateList, leaving it to the framework", paramResources);
    }
    return null;
  }
  
  private static boolean isColorInt(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = getTypedValue();
    boolean bool = true;
    paramResources.getValue(paramInt, localTypedValue, true);
    if ((localTypedValue.type < 28) || (localTypedValue.type > 31)) {
      bool = false;
    }
    return bool;
  }
  
  private static Typeface loadFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean1, boolean paramBoolean2)
  {
    Resources localResources = paramContext.getResources();
    localResources.getValue(paramInt1, paramTypedValue, true);
    paramContext = loadFont(paramContext, localResources, paramTypedValue, paramInt1, paramInt2, paramFontCallback, paramHandler, paramBoolean1, paramBoolean2);
    if ((paramContext == null) && (paramFontCallback == null) && (!paramBoolean2))
    {
      paramTypedValue = new StringBuilder().append("Font resource ID #0x");
      paramContext = Integer.toHexString(paramInt1);
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      throw new Resources.NotFoundException(paramContext + " could not be retrieved.");
    }
    return paramContext;
  }
  
  private static Typeface loadFont(Context paramContext, Resources paramResources, TypedValue paramTypedValue, int paramInt1, int paramInt2, FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramTypedValue.string != null)
    {
      String str = paramTypedValue.string.toString();
      if (!str.startsWith("res/"))
      {
        if (paramFontCallback != null) {
          paramFontCallback.callbackFailAsync(-3, paramHandler);
        }
        return null;
      }
      Object localObject = TypefaceCompat.findFromCache(paramResources, paramInt1, str, paramTypedValue.assetCookie, paramInt2);
      if (localObject != null)
      {
        if (paramFontCallback != null) {
          paramFontCallback.callbackSuccessAsync((Typeface)localObject, paramHandler);
        }
        return (Typeface)localObject;
      }
      if (paramBoolean2) {
        return null;
      }
      try
      {
        localObject = str.toLowerCase();
        try
        {
          int i;
          if (((String)localObject).endsWith(".xml"))
          {
            localObject = paramResources.getXml(paramInt1);
            localObject = FontResourcesParserCompat.parse((XmlPullParser)localObject, paramResources);
            if (localObject == null) {
              try
              {
                Log.e("ResourcesCompat", "Failed to find font-family tag");
                if (paramFontCallback != null) {
                  paramFontCallback.callbackFailAsync(-3, paramHandler);
                }
                return null;
              }
              catch (IOException paramContext)
              {
                break label267;
              }
              catch (XmlPullParserException paramContext)
              {
                paramResources = str;
              }
            }
            i = paramTypedValue.assetCookie;
          }
          try
          {
            return TypefaceCompat.createFromResourcesFamilyXml(paramContext, (FontResourcesParserCompat.FamilyResourceEntry)localObject, paramResources, paramInt1, str, i, paramInt2, paramFontCallback, paramHandler, paramBoolean1);
          }
          catch (IOException paramContext)
          {
            break label267;
          }
          catch (XmlPullParserException paramContext)
          {
            paramResources = str;
          }
          paramContext = TypefaceCompat.createFromResourcesFontFile(paramContext, paramResources, paramInt1, str, paramTypedValue.assetCookie, paramInt2);
          if (paramFontCallback != null)
          {
            if (paramContext != null) {}
            try
            {
              paramFontCallback.callbackSuccessAsync(paramContext, paramHandler);
            }
            catch (IOException paramContext)
            {
              break label267;
            }
            catch (XmlPullParserException paramContext)
            {
              paramResources = str;
              break label302;
            }
            paramFontCallback.callbackFailAsync(-3, paramHandler);
          }
          return paramContext;
        }
        catch (XmlPullParserException paramContext)
        {
          paramResources = str;
        }
        label267:
        Log.e("ResourcesCompat", "Failed to parse xml resource " + str, paramContext);
      }
      catch (IOException paramContext)
      {
        Log.e("ResourcesCompat", "Failed to read xml resource " + str, paramContext);
      }
      catch (XmlPullParserException paramContext)
      {
        paramResources = str;
      }
      label302:
      if (paramFontCallback != null) {
        paramFontCallback.callbackFailAsync(-3, paramHandler);
      }
      return null;
    }
    paramResources = new StringBuilder().append("Resource \"").append(paramResources.getResourceName(paramInt1)).append("\" (");
    paramContext = Integer.toHexString(paramInt1);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    throw new Resources.NotFoundException(paramContext + ") is not a Font: " + paramTypedValue);
  }
  
  static class Api15Impl
  {
    static Drawable getDrawableForDensity(Resources paramResources, int paramInt1, int paramInt2)
    {
      return paramResources.getDrawableForDensity(paramInt1, paramInt2);
    }
  }
  
  static class Api21Impl
  {
    static Drawable getDrawable(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    {
      return paramResources.getDrawable(paramInt, paramTheme);
    }
    
    static Drawable getDrawableForDensity(Resources paramResources, int paramInt1, int paramInt2, Resources.Theme paramTheme)
    {
      return paramResources.getDrawableForDensity(paramInt1, paramInt2, paramTheme);
    }
  }
  
  static class Api23Impl
  {
    static int getColor(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    {
      return paramResources.getColor(paramInt, paramTheme);
    }
    
    static ColorStateList getColorStateList(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    {
      return paramResources.getColorStateList(paramInt, paramTheme);
    }
  }
  
  static class Api29Impl
  {
    static float getFloat(Resources paramResources, int paramInt)
    {
      return paramResources.getFloat(paramInt);
    }
  }
  
  private static class ColorStateListCacheEntry
  {
    final Configuration mConfiguration;
    final int mThemeHash;
    final ColorStateList mValue;
    
    ColorStateListCacheEntry(ColorStateList paramColorStateList, Configuration paramConfiguration, Resources.Theme paramTheme)
    {
      this.mValue = paramColorStateList;
      this.mConfiguration = paramConfiguration;
      int i;
      if (paramTheme == null) {
        i = 0;
      } else {
        i = paramTheme.hashCode();
      }
      this.mThemeHash = i;
    }
  }
  
  private static final class ColorStateListCacheKey
  {
    final Resources mResources;
    final Resources.Theme mTheme;
    
    ColorStateListCacheKey(Resources paramResources, Resources.Theme paramTheme)
    {
      this.mResources = paramResources;
      this.mTheme = paramTheme;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (ColorStateListCacheKey)paramObject;
        if ((!this.mResources.equals(((ColorStateListCacheKey)paramObject).mResources)) || (!ObjectsCompat.equals(this.mTheme, ((ColorStateListCacheKey)paramObject).mTheme))) {
          bool = false;
        }
        return bool;
      }
      return false;
    }
    
    public int hashCode()
    {
      return ObjectsCompat.hash(new Object[] { this.mResources, this.mTheme });
    }
  }
  
  public static abstract class FontCallback
  {
    public static Handler getHandler(Handler paramHandler)
    {
      if (paramHandler == null) {
        paramHandler = new Handler(Looper.getMainLooper());
      }
      return paramHandler;
    }
    
    public final void callbackFailAsync(int paramInt, Handler paramHandler)
    {
      getHandler(paramHandler).post(new ResourcesCompat.FontCallback..ExternalSyntheticLambda1(this, paramInt));
    }
    
    public final void callbackSuccessAsync(Typeface paramTypeface, Handler paramHandler)
    {
      getHandler(paramHandler).post(new ResourcesCompat.FontCallback..ExternalSyntheticLambda0(this, paramTypeface));
    }
    
    public abstract void onFontRetrievalFailed(int paramInt);
    
    public abstract void onFontRetrieved(Typeface paramTypeface);
  }
  
  public static final class ThemeCompat
  {
    public static void rebase(Resources.Theme paramTheme)
    {
      if (Build.VERSION.SDK_INT >= 29) {
        Api29Impl.rebase(paramTheme);
      } else if (Build.VERSION.SDK_INT >= 23) {
        Api23Impl.rebase(paramTheme);
      }
    }
    
    static class Api23Impl
    {
      private static Method sRebaseMethod;
      private static boolean sRebaseMethodFetched;
      private static final Object sRebaseMethodLock = new Object();
      
      static void rebase(Resources.Theme paramTheme)
      {
        synchronized (sRebaseMethodLock)
        {
          boolean bool = sRebaseMethodFetched;
          if (!bool)
          {
            try
            {
              Method localMethod1 = Resources.Theme.class.getDeclaredMethod("rebase", new Class[0]);
              sRebaseMethod = localMethod1;
              localMethod1.setAccessible(true);
            }
            catch (NoSuchMethodException localNoSuchMethodException)
            {
              Log.i("ResourcesCompat", "Failed to retrieve rebase() method", localNoSuchMethodException);
            }
            sRebaseMethodFetched = true;
          }
          Method localMethod2 = sRebaseMethod;
          if (localMethod2 != null)
          {
            try
            {
              localMethod2.invoke(paramTheme, new Object[0]);
            }
            catch (InvocationTargetException paramTheme) {}catch (IllegalAccessException paramTheme) {}
            Log.i("ResourcesCompat", "Failed to invoke rebase() method via reflection", paramTheme);
            sRebaseMethod = null;
          }
          return;
        }
      }
    }
    
    static class Api29Impl
    {
      static void rebase(Resources.Theme paramTheme)
      {
        paramTheme.rebase();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/ResourcesCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */