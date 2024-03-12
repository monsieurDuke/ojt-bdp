package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class FontRequestWorker
{
  private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = RequestExecutor.createDefaultExecutor("fonts-androidx", 10, 10000);
  static final Object LOCK = new Object();
  static final SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> PENDING_REPLIES = new SimpleArrayMap();
  static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
  
  private static String createCacheId(FontRequest paramFontRequest, int paramInt)
  {
    return paramFontRequest.getId() + "-" + paramInt;
  }
  
  private static int getFontFamilyResultStatus(FontsContractCompat.FontFamilyResult paramFontFamilyResult)
  {
    int i = paramFontFamilyResult.getStatusCode();
    int j = -3;
    if (i != 0)
    {
      switch (paramFontFamilyResult.getStatusCode())
      {
      default: 
        return -3;
      }
      return -2;
    }
    paramFontFamilyResult = paramFontFamilyResult.getFonts();
    if ((paramFontFamilyResult != null) && (paramFontFamilyResult.length != 0))
    {
      int m = paramFontFamilyResult.length;
      for (i = 0; i < m; i++)
      {
        int k = paramFontFamilyResult[i].getResultCode();
        if (k != 0)
        {
          if (k < 0) {
            i = j;
          } else {
            i = k;
          }
          return i;
        }
      }
      return 0;
    }
    return 1;
  }
  
  static TypefaceResult getFontSync(String paramString, Context paramContext, FontRequest paramFontRequest, int paramInt)
  {
    LruCache localLruCache = sTypefaceCache;
    Typeface localTypeface = (Typeface)localLruCache.get(paramString);
    if (localTypeface != null) {
      return new TypefaceResult(localTypeface);
    }
    try
    {
      paramFontRequest = FontProvider.getFontFamilyResult(paramContext, paramFontRequest, null);
      int i = getFontFamilyResultStatus(paramFontRequest);
      if (i != 0) {
        return new TypefaceResult(i);
      }
      paramContext = TypefaceCompat.createFromFontInfo(paramContext, null, paramFontRequest.getFonts(), paramInt);
      if (paramContext != null)
      {
        localLruCache.put(paramString, paramContext);
        return new TypefaceResult(paramContext);
      }
      return new TypefaceResult(-3);
    }
    catch (PackageManager.NameNotFoundException paramString) {}
    return new TypefaceResult(-1);
  }
  
  static Typeface requestFontAsync(final Context paramContext, final FontRequest paramFontRequest, final int paramInt, Executor paramExecutor, CallbackWithHandler arg4)
  {
    String str = createCacheId(paramFontRequest, paramInt);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Object localObject = (Typeface)sTypefaceCache.get(str);
    if (localObject != null)
    {
      ???.onTypefaceResult(new TypefaceResult((Typeface)localObject));
      return (Typeface)localObject;
    }
    localObject = new Consumer()
    {
      public void accept(FontRequestWorker.TypefaceResult paramAnonymousTypefaceResult)
      {
        FontRequestWorker.TypefaceResult localTypefaceResult = paramAnonymousTypefaceResult;
        if (paramAnonymousTypefaceResult == null) {
          localTypefaceResult = new FontRequestWorker.TypefaceResult(-3);
        }
        FontRequestWorker.this.onTypefaceResult(localTypefaceResult);
      }
    };
    synchronized (LOCK)
    {
      SimpleArrayMap localSimpleArrayMap = PENDING_REPLIES;
      ArrayList localArrayList = (ArrayList)localSimpleArrayMap.get(str);
      if (localArrayList != null)
      {
        localArrayList.add(localObject);
        return null;
      }
      localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      localArrayList.add(localObject);
      localSimpleArrayMap.put(str, localArrayList);
      paramContext = new Callable()
      {
        public FontRequestWorker.TypefaceResult call()
        {
          try
          {
            FontRequestWorker.TypefaceResult localTypefaceResult = FontRequestWorker.getFontSync(FontRequestWorker.this, paramContext, paramFontRequest, paramInt);
            return localTypefaceResult;
          }
          finally {}
          return new FontRequestWorker.TypefaceResult(-3);
        }
      };
      if (paramExecutor == null) {
        paramExecutor = DEFAULT_EXECUTOR_SERVICE;
      }
      RequestExecutor.execute(paramExecutor, paramContext, new Consumer()
      {
        public void accept(FontRequestWorker.TypefaceResult paramAnonymousTypefaceResult)
        {
          synchronized (FontRequestWorker.LOCK)
          {
            ArrayList localArrayList = (ArrayList)FontRequestWorker.PENDING_REPLIES.get(FontRequestWorker.this);
            if (localArrayList == null) {
              return;
            }
            FontRequestWorker.PENDING_REPLIES.remove(FontRequestWorker.this);
            for (int i = 0; i < localArrayList.size(); i++) {
              ((Consumer)localArrayList.get(i)).accept(paramAnonymousTypefaceResult);
            }
            return;
          }
        }
      });
      return null;
    }
  }
  
  static Typeface requestFontSync(final Context paramContext, final FontRequest paramFontRequest, CallbackWithHandler paramCallbackWithHandler, final int paramInt1, int paramInt2)
  {
    String str = createCacheId(paramFontRequest, paramInt1);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Typeface localTypeface = (Typeface)sTypefaceCache.get(str);
    if (localTypeface != null)
    {
      paramCallbackWithHandler.onTypefaceResult(new TypefaceResult(localTypeface));
      return localTypeface;
    }
    if (paramInt2 == -1)
    {
      paramContext = getFontSync(str, paramContext, paramFontRequest, paramInt1);
      paramCallbackWithHandler.onTypefaceResult(paramContext);
      return paramContext.mTypeface;
    }
    paramContext = new Callable()
    {
      public FontRequestWorker.TypefaceResult call()
      {
        return FontRequestWorker.getFontSync(FontRequestWorker.this, paramContext, paramFontRequest, paramInt1);
      }
    };
    try
    {
      paramContext = (TypefaceResult)RequestExecutor.submit(DEFAULT_EXECUTOR_SERVICE, paramContext, paramInt2);
      paramCallbackWithHandler.onTypefaceResult(paramContext);
      paramContext = paramContext.mTypeface;
      return paramContext;
    }
    catch (InterruptedException paramContext)
    {
      paramCallbackWithHandler.onTypefaceResult(new TypefaceResult(-3));
    }
    return null;
  }
  
  static void resetTypefaceCache()
  {
    sTypefaceCache.evictAll();
  }
  
  static final class TypefaceResult
  {
    final int mResult;
    final Typeface mTypeface;
    
    TypefaceResult(int paramInt)
    {
      this.mTypeface = null;
      this.mResult = paramInt;
    }
    
    TypefaceResult(Typeface paramTypeface)
    {
      this.mTypeface = paramTypeface;
      this.mResult = 0;
    }
    
    boolean isSuccess()
    {
      boolean bool;
      if (this.mResult == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/provider/FontRequestWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */