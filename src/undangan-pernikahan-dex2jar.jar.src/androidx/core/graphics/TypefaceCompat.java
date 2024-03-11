package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.collection.LruCache;
import androidx.core.content.res.FontResourcesParserCompat.FamilyResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.ProviderResourceEntry;
import androidx.core.content.res.ResourcesCompat.FontCallback;
import androidx.core.provider.FontsContractCompat;
import androidx.core.provider.FontsContractCompat.FontInfo;
import androidx.core.provider.FontsContractCompat.FontRequestCallback;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class TypefaceCompat
{
  private static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
  private static final TypefaceCompatBaseImpl sTypefaceCompatImpl;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 29) {
      sTypefaceCompatImpl = new TypefaceCompatApi29Impl();
    } else if (Build.VERSION.SDK_INT >= 28) {
      sTypefaceCompatImpl = new TypefaceCompatApi28Impl();
    } else if (Build.VERSION.SDK_INT >= 26) {
      sTypefaceCompatImpl = new TypefaceCompatApi26Impl();
    } else if ((Build.VERSION.SDK_INT >= 24) && (TypefaceCompatApi24Impl.isUsable())) {
      sTypefaceCompatImpl = new TypefaceCompatApi24Impl();
    } else if (Build.VERSION.SDK_INT >= 21) {
      sTypefaceCompatImpl = new TypefaceCompatApi21Impl();
    } else {
      sTypefaceCompatImpl = new TypefaceCompatBaseImpl();
    }
  }
  
  public static void clearCache()
  {
    sTypefaceCache.evictAll();
  }
  
  public static Typeface create(Context paramContext, Typeface paramTypeface, int paramInt)
  {
    if (paramContext != null)
    {
      if (Build.VERSION.SDK_INT < 21)
      {
        paramContext = getBestFontFromFamily(paramContext, paramTypeface, paramInt);
        if (paramContext != null) {
          return paramContext;
        }
      }
      return Typeface.create(paramTypeface, paramInt);
    }
    throw new IllegalArgumentException("Context cannot be null");
  }
  
  public static Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    return sTypefaceCompatImpl.createFromFontInfo(paramContext, paramCancellationSignal, paramArrayOfFontInfo, paramInt);
  }
  
  @Deprecated
  public static Typeface createFromResourcesFamilyXml(Context paramContext, FontResourcesParserCompat.FamilyResourceEntry paramFamilyResourceEntry, Resources paramResources, int paramInt1, int paramInt2, ResourcesCompat.FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean)
  {
    return createFromResourcesFamilyXml(paramContext, paramFamilyResourceEntry, paramResources, paramInt1, null, 0, paramInt2, paramFontCallback, paramHandler, paramBoolean);
  }
  
  public static Typeface createFromResourcesFamilyXml(Context paramContext, FontResourcesParserCompat.FamilyResourceEntry paramFamilyResourceEntry, Resources paramResources, int paramInt1, String paramString, int paramInt2, int paramInt3, ResourcesCompat.FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean)
  {
    if ((paramFamilyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry))
    {
      paramFamilyResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry)paramFamilyResourceEntry;
      Typeface localTypeface = getSystemFontFamily(paramFamilyResourceEntry.getSystemFontFamilyName());
      if (localTypeface != null)
      {
        if (paramFontCallback != null) {
          paramFontCallback.callbackSuccessAsync(localTypeface, paramHandler);
        }
        return localTypeface;
      }
      boolean bool;
      if (paramBoolean)
      {
        if (paramFamilyResourceEntry.getFetchStrategy() == 0) {
          bool = true;
        } else {
          bool = false;
        }
      }
      else if (paramFontCallback == null) {
        bool = true;
      } else {
        bool = false;
      }
      int i;
      if (paramBoolean) {
        i = paramFamilyResourceEntry.getTimeout();
      } else {
        i = -1;
      }
      paramHandler = ResourcesCompat.FontCallback.getHandler(paramHandler);
      paramFontCallback = new ResourcesCallbackAdapter(paramFontCallback);
      paramContext = FontsContractCompat.requestFont(paramContext, paramFamilyResourceEntry.getRequest(), paramInt3, bool, i, paramHandler, paramFontCallback);
    }
    else
    {
      paramFamilyResourceEntry = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(paramContext, (FontResourcesParserCompat.FontFamilyFilesResourceEntry)paramFamilyResourceEntry, paramResources, paramInt3);
      paramContext = paramFamilyResourceEntry;
      if (paramFontCallback != null) {
        if (paramFamilyResourceEntry != null)
        {
          paramFontCallback.callbackSuccessAsync(paramFamilyResourceEntry, paramHandler);
          paramContext = paramFamilyResourceEntry;
        }
        else
        {
          paramFontCallback.callbackFailAsync(-3, paramHandler);
          paramContext = paramFamilyResourceEntry;
        }
      }
    }
    if (paramContext != null)
    {
      paramFamilyResourceEntry = sTypefaceCache;
      paramResources = createResourceUid(paramResources, paramInt1, paramString, paramInt2, paramInt3);
      Log5ECF72.a(paramResources);
      LogE84000.a(paramResources);
      Log229316.a(paramResources);
      paramFamilyResourceEntry.put(paramResources, paramContext);
    }
    return paramContext;
  }
  
  @Deprecated
  public static Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    return createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramString, 0, paramInt2);
  }
  
  public static Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2, int paramInt3)
  {
    paramContext = sTypefaceCompatImpl.createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramString, paramInt3);
    if (paramContext != null)
    {
      paramResources = createResourceUid(paramResources, paramInt1, paramString, paramInt2, paramInt3);
      Log5ECF72.a(paramResources);
      LogE84000.a(paramResources);
      Log229316.a(paramResources);
      sTypefaceCache.put(paramResources, paramContext);
    }
    return paramContext;
  }
  
  private static String createResourceUid(Resources paramResources, int paramInt1, String paramString, int paramInt2, int paramInt3)
  {
    return paramResources.getResourcePackageName(paramInt1) + '-' + paramString + '-' + paramInt2 + '-' + paramInt1 + '-' + paramInt3;
  }
  
  @Deprecated
  public static Typeface findFromCache(Resources paramResources, int paramInt1, int paramInt2)
  {
    return findFromCache(paramResources, paramInt1, null, 0, paramInt2);
  }
  
  public static Typeface findFromCache(Resources paramResources, int paramInt1, String paramString, int paramInt2, int paramInt3)
  {
    LruCache localLruCache = sTypefaceCache;
    paramResources = createResourceUid(paramResources, paramInt1, paramString, paramInt2, paramInt3);
    Log5ECF72.a(paramResources);
    LogE84000.a(paramResources);
    Log229316.a(paramResources);
    return (Typeface)localLruCache.get(paramResources);
  }
  
  private static Typeface getBestFontFromFamily(Context paramContext, Typeface paramTypeface, int paramInt)
  {
    TypefaceCompatBaseImpl localTypefaceCompatBaseImpl = sTypefaceCompatImpl;
    paramTypeface = localTypefaceCompatBaseImpl.getFontFamily(paramTypeface);
    if (paramTypeface == null) {
      return null;
    }
    return localTypefaceCompatBaseImpl.createFromFontFamilyFilesResourceEntry(paramContext, paramTypeface, paramContext.getResources(), paramInt);
  }
  
  private static Typeface getSystemFontFamily(String paramString)
  {
    Object localObject = null;
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      Typeface localTypeface1 = Typeface.create(paramString, 0);
      Typeface localTypeface2 = Typeface.create(Typeface.DEFAULT, 0);
      paramString = (String)localObject;
      if (localTypeface1 != null)
      {
        paramString = (String)localObject;
        if (!localTypeface1.equals(localTypeface2)) {
          paramString = localTypeface1;
        }
      }
      return paramString;
    }
    return null;
  }
  
  public static class ResourcesCallbackAdapter
    extends FontsContractCompat.FontRequestCallback
  {
    private ResourcesCompat.FontCallback mFontCallback;
    
    public ResourcesCallbackAdapter(ResourcesCompat.FontCallback paramFontCallback)
    {
      this.mFontCallback = paramFontCallback;
    }
    
    public void onTypefaceRequestFailed(int paramInt)
    {
      ResourcesCompat.FontCallback localFontCallback = this.mFontCallback;
      if (localFontCallback != null) {
        localFontCallback.onFontRetrievalFailed(paramInt);
      }
    }
    
    public void onTypefaceRetrieved(Typeface paramTypeface)
    {
      ResourcesCompat.FontCallback localFontCallback = this.mFontCallback;
      if (localFontCallback != null) {
        localFontCallback.onFontRetrieved(paramTypeface);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/TypefaceCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */