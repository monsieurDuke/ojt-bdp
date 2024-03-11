package androidx.emoji2.text;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.os.TraceCompat;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat;
import androidx.core.provider.FontsContractCompat.FontFamilyResult;
import androidx.core.provider.FontsContractCompat.FontInfo;
import androidx.core.util.Preconditions;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class FontRequestEmojiCompatConfig
  extends EmojiCompat.Config
{
  private static final FontProviderHelper DEFAULT_FONTS_CONTRACT = new FontProviderHelper();
  
  public FontRequestEmojiCompatConfig(Context paramContext, FontRequest paramFontRequest)
  {
    super(new FontRequestMetadataLoader(paramContext, paramFontRequest, DEFAULT_FONTS_CONTRACT));
  }
  
  public FontRequestEmojiCompatConfig(Context paramContext, FontRequest paramFontRequest, FontProviderHelper paramFontProviderHelper)
  {
    super(new FontRequestMetadataLoader(paramContext, paramFontRequest, paramFontProviderHelper));
  }
  
  @Deprecated
  public FontRequestEmojiCompatConfig setHandler(Handler paramHandler)
  {
    if (paramHandler == null) {
      return this;
    }
    setLoadingExecutor(ConcurrencyHelpers.convertHandlerToExecutor(paramHandler));
    return this;
  }
  
  public FontRequestEmojiCompatConfig setLoadingExecutor(Executor paramExecutor)
  {
    ((FontRequestMetadataLoader)getMetadataRepoLoader()).setExecutor(paramExecutor);
    return this;
  }
  
  public FontRequestEmojiCompatConfig setRetryPolicy(RetryPolicy paramRetryPolicy)
  {
    ((FontRequestMetadataLoader)getMetadataRepoLoader()).setRetryPolicy(paramRetryPolicy);
    return this;
  }
  
  public static class ExponentialBackoffRetryPolicy
    extends FontRequestEmojiCompatConfig.RetryPolicy
  {
    private long mRetryOrigin;
    private final long mTotalMs;
    
    public ExponentialBackoffRetryPolicy(long paramLong)
    {
      this.mTotalMs = paramLong;
    }
    
    public long getRetryDelay()
    {
      if (this.mRetryOrigin == 0L)
      {
        this.mRetryOrigin = SystemClock.uptimeMillis();
        return 0L;
      }
      long l = SystemClock.uptimeMillis() - this.mRetryOrigin;
      if (l > this.mTotalMs) {
        return -1L;
      }
      return Math.min(Math.max(l, 1000L), this.mTotalMs - l);
    }
  }
  
  public static class FontProviderHelper
  {
    public Typeface buildTypeface(Context paramContext, FontsContractCompat.FontInfo paramFontInfo)
      throws PackageManager.NameNotFoundException
    {
      return FontsContractCompat.buildTypeface(paramContext, null, new FontsContractCompat.FontInfo[] { paramFontInfo });
    }
    
    public FontsContractCompat.FontFamilyResult fetchFonts(Context paramContext, FontRequest paramFontRequest)
      throws PackageManager.NameNotFoundException
    {
      return FontsContractCompat.fetchFonts(paramContext, null, paramFontRequest);
    }
    
    public void registerObserver(Context paramContext, Uri paramUri, ContentObserver paramContentObserver)
    {
      paramContext.getContentResolver().registerContentObserver(paramUri, false, paramContentObserver);
    }
    
    public void unregisterObserver(Context paramContext, ContentObserver paramContentObserver)
    {
      paramContext.getContentResolver().unregisterContentObserver(paramContentObserver);
    }
  }
  
  private static class FontRequestMetadataLoader
    implements EmojiCompat.MetadataRepoLoader
  {
    private static final String S_TRACE_BUILD_TYPEFACE = "EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface";
    EmojiCompat.MetadataRepoLoaderCallback mCallback;
    private final Context mContext;
    private Executor mExecutor;
    private final FontRequestEmojiCompatConfig.FontProviderHelper mFontProviderHelper;
    private final Object mLock = new Object();
    private Handler mMainHandler;
    private Runnable mMainHandlerLoadCallback;
    private ThreadPoolExecutor mMyThreadPoolExecutor;
    private ContentObserver mObserver;
    private final FontRequest mRequest;
    private FontRequestEmojiCompatConfig.RetryPolicy mRetryPolicy;
    
    FontRequestMetadataLoader(Context paramContext, FontRequest paramFontRequest, FontRequestEmojiCompatConfig.FontProviderHelper paramFontProviderHelper)
    {
      Preconditions.checkNotNull(paramContext, "Context cannot be null");
      Preconditions.checkNotNull(paramFontRequest, "FontRequest cannot be null");
      this.mContext = paramContext.getApplicationContext();
      this.mRequest = paramFontRequest;
      this.mFontProviderHelper = paramFontProviderHelper;
    }
    
    private void cleanUp()
    {
      synchronized (this.mLock)
      {
        this.mCallback = null;
        Object localObject2 = this.mObserver;
        if (localObject2 != null)
        {
          this.mFontProviderHelper.unregisterObserver(this.mContext, (ContentObserver)localObject2);
          this.mObserver = null;
        }
        localObject2 = this.mMainHandler;
        if (localObject2 != null) {
          ((Handler)localObject2).removeCallbacks(this.mMainHandlerLoadCallback);
        }
        this.mMainHandler = null;
        localObject2 = this.mMyThreadPoolExecutor;
        if (localObject2 != null) {
          ((ThreadPoolExecutor)localObject2).shutdown();
        }
        this.mExecutor = null;
        this.mMyThreadPoolExecutor = null;
        return;
      }
    }
    
    private FontsContractCompat.FontInfo retrieveFontInfo()
    {
      try
      {
        Object localObject = this.mFontProviderHelper.fetchFonts(this.mContext, this.mRequest);
        if (((FontsContractCompat.FontFamilyResult)localObject).getStatusCode() == 0)
        {
          localObject = ((FontsContractCompat.FontFamilyResult)localObject).getFonts();
          if ((localObject != null) && (localObject.length != 0)) {
            return localObject[0];
          }
          throw new RuntimeException("fetchFonts failed (empty result)");
        }
        throw new RuntimeException("fetchFonts failed (" + ((FontsContractCompat.FontFamilyResult)localObject).getStatusCode() + ")");
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        throw new RuntimeException("provider not found", localNameNotFoundException);
      }
    }
    
    private void scheduleRetry(Uri paramUri, long paramLong)
    {
      synchronized (this.mLock)
      {
        Object localObject2 = this.mMainHandler;
        Object localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject1 = ConcurrencyHelpers.mainHandlerAsync();
          this.mMainHandler = ((Handler)localObject1);
        }
        if (this.mObserver == null)
        {
          localObject2 = new androidx/emoji2/text/FontRequestEmojiCompatConfig$FontRequestMetadataLoader$1;
          ((1)localObject2).<init>(this, (Handler)localObject1);
          this.mObserver = ((ContentObserver)localObject2);
          this.mFontProviderHelper.registerObserver(this.mContext, paramUri, (ContentObserver)localObject2);
        }
        if (this.mMainHandlerLoadCallback == null)
        {
          paramUri = new androidx/emoji2/text/FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1;
          paramUri.<init>(this);
          this.mMainHandlerLoadCallback = paramUri;
        }
        ((Handler)localObject1).postDelayed(this.mMainHandlerLoadCallback, paramLong);
        return;
      }
    }
    
    void createMetadata()
    {
      synchronized (this.mLock)
      {
        if (this.mCallback == null) {
          return;
        }
        try
        {
          FontsContractCompat.FontInfo localFontInfo1 = retrieveFontInfo();
          int i = localFontInfo1.getResultCode();
          if (i == 2) {
            synchronized (this.mLock)
            {
              localObject8 = this.mRetryPolicy;
              if (localObject8 != null)
              {
                long l = ((FontRequestEmojiCompatConfig.RetryPolicy)localObject8).getRetryDelay();
                if (l >= 0L)
                {
                  scheduleRetry(localFontInfo1.getUri(), l);
                  return;
                }
              }
            }
          }
          if (i == 0) {
            try
            {
              TraceCompat.beginSection("EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface");
              ??? = this.mFontProviderHelper.buildTypeface(this.mContext, localFontInfo2);
              Object localObject4 = TypefaceCompatUtil.mmap(this.mContext, null, localFontInfo2.getUri());
              if ((localObject4 != null) && (??? != null))
              {
                localObject8 = MetadataRepo.create((Typeface)???, (ByteBuffer)localObject4);
                TraceCompat.endSection();
                synchronized (this.mLock)
                {
                  localObject4 = this.mCallback;
                  if (localObject4 != null) {
                    ((EmojiCompat.MetadataRepoLoaderCallback)localObject4).onLoaded((MetadataRepo)localObject8);
                  }
                  cleanUp();
                }
              }
              ??? = new java/lang/RuntimeException;
              ((RuntimeException)???).<init>("Unable to open file.");
              throw ((Throwable)???);
            }
            finally
            {
              TraceCompat.endSection();
            }
          }
          ??? = new java/lang/RuntimeException;
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          ((RuntimeException)???).<init>("fetchFonts result is not OK. (" + i + ")");
          throw ((Throwable)???);
        }
        finally
        {
          synchronized (this.mLock)
          {
            Object localObject8 = this.mCallback;
            if (localObject8 != null) {
              ((EmojiCompat.MetadataRepoLoaderCallback)localObject8).onFailed(localThrowable);
            }
            cleanUp();
            return;
          }
        }
      }
    }
    
    public void load(EmojiCompat.MetadataRepoLoaderCallback paramMetadataRepoLoaderCallback)
    {
      Preconditions.checkNotNull(paramMetadataRepoLoaderCallback, "LoaderCallback cannot be null");
      synchronized (this.mLock)
      {
        this.mCallback = paramMetadataRepoLoaderCallback;
        loadInternal();
        return;
      }
    }
    
    void loadInternal()
    {
      synchronized (this.mLock)
      {
        if (this.mCallback == null) {
          return;
        }
        if (this.mExecutor == null)
        {
          localObject2 = ConcurrencyHelpers.createBackgroundPriorityExecutor("emojiCompat");
          this.mMyThreadPoolExecutor = ((ThreadPoolExecutor)localObject2);
          this.mExecutor = ((Executor)localObject2);
        }
        Executor localExecutor = this.mExecutor;
        Object localObject2 = new androidx/emoji2/text/FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda0;
        ((FontRequestEmojiCompatConfig.FontRequestMetadataLoader..ExternalSyntheticLambda0)localObject2).<init>(this);
        localExecutor.execute((Runnable)localObject2);
        return;
      }
    }
    
    public void setExecutor(Executor paramExecutor)
    {
      synchronized (this.mLock)
      {
        this.mExecutor = paramExecutor;
        return;
      }
    }
    
    public void setRetryPolicy(FontRequestEmojiCompatConfig.RetryPolicy paramRetryPolicy)
    {
      synchronized (this.mLock)
      {
        this.mRetryPolicy = paramRetryPolicy;
        return;
      }
    }
  }
  
  public static abstract class RetryPolicy
  {
    public abstract long getRetryDelay();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/FontRequestEmojiCompatConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */