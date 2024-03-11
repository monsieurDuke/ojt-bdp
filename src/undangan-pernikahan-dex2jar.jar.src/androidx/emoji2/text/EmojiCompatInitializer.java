package androidx.emoji2.text;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import androidx.core.os.TraceCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleInitializer;
import androidx.startup.AppInitializer;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class EmojiCompatInitializer
  implements Initializer<Boolean>
{
  private static final long STARTUP_THREAD_CREATION_DELAY_MS = 500L;
  private static final String S_INITIALIZER_THREAD_NAME = "EmojiCompatInitializer";
  
  public Boolean create(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      EmojiCompat.init(new BackgroundDefaultConfig(paramContext));
      delayUntilFirstResume(paramContext);
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  void delayUntilFirstResume(final Context paramContext)
  {
    paramContext = ((LifecycleOwner)AppInitializer.getInstance(paramContext).initializeComponent(ProcessLifecycleInitializer.class)).getLifecycle();
    paramContext.addObserver(new DefaultLifecycleObserver()
    {
      public void onResume(LifecycleOwner paramAnonymousLifecycleOwner)
      {
        EmojiCompatInitializer.this.loadEmojiCompatAfterDelay();
        paramContext.removeObserver(this);
      }
    });
  }
  
  public List<Class<? extends Initializer<?>>> dependencies()
  {
    return Collections.singletonList(ProcessLifecycleInitializer.class);
  }
  
  void loadEmojiCompatAfterDelay()
  {
    ConcurrencyHelpers.mainHandlerAsync().postDelayed(new LoadEmojiCompatRunnable(), 500L);
  }
  
  static class BackgroundDefaultConfig
    extends EmojiCompat.Config
  {
    protected BackgroundDefaultConfig(Context paramContext)
    {
      super();
      setMetadataLoadStrategy(1);
    }
  }
  
  static class BackgroundDefaultLoader
    implements EmojiCompat.MetadataRepoLoader
  {
    private final Context mContext;
    
    BackgroundDefaultLoader(Context paramContext)
    {
      this.mContext = paramContext.getApplicationContext();
    }
    
    void doLoad(EmojiCompat.MetadataRepoLoaderCallback paramMetadataRepoLoaderCallback, ThreadPoolExecutor paramThreadPoolExecutor)
    {
      try
      {
        Object localObject = DefaultEmojiCompatConfig.create(this.mContext);
        if (localObject != null)
        {
          ((FontRequestEmojiCompatConfig)localObject).setLoadingExecutor(paramThreadPoolExecutor);
          EmojiCompat.MetadataRepoLoader localMetadataRepoLoader = ((FontRequestEmojiCompatConfig)localObject).getMetadataRepoLoader();
          localObject = new androidx/emoji2/text/EmojiCompatInitializer$BackgroundDefaultLoader$1;
          ((1)localObject).<init>(this, paramMetadataRepoLoaderCallback, paramThreadPoolExecutor);
          localMetadataRepoLoader.load((EmojiCompat.MetadataRepoLoaderCallback)localObject);
        }
        else
        {
          localObject = new java/lang/RuntimeException;
          ((RuntimeException)localObject).<init>("EmojiCompat font provider not available on this device.");
          throw ((Throwable)localObject);
        }
      }
      finally
      {
        paramMetadataRepoLoaderCallback.onFailed(localThrowable);
        paramThreadPoolExecutor.shutdown();
      }
    }
    
    public void load(EmojiCompat.MetadataRepoLoaderCallback paramMetadataRepoLoaderCallback)
    {
      ThreadPoolExecutor localThreadPoolExecutor = ConcurrencyHelpers.createBackgroundPriorityExecutor("EmojiCompatInitializer");
      localThreadPoolExecutor.execute(new EmojiCompatInitializer.BackgroundDefaultLoader..ExternalSyntheticLambda0(this, paramMetadataRepoLoaderCallback, localThreadPoolExecutor));
    }
  }
  
  static class LoadEmojiCompatRunnable
    implements Runnable
  {
    public void run()
    {
      try
      {
        TraceCompat.beginSection("EmojiCompat.EmojiCompatInitializer.run");
        if (EmojiCompat.isConfigured()) {
          EmojiCompat.get().load();
        }
        return;
      }
      finally
      {
        TraceCompat.endSection();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/EmojiCompatInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */