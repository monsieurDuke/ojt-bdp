package androidx.arch.core.executor;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DefaultTaskExecutor
  extends TaskExecutor
{
  private final ExecutorService mDiskIO = Executors.newFixedThreadPool(4, new ThreadFactory()
  {
    private static final String THREAD_NAME_STEM = "arch_disk_io_%d";
    private final AtomicInteger mThreadId = new AtomicInteger(0);
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      Thread localThread = new Thread(paramAnonymousRunnable);
      paramAnonymousRunnable = String.format("arch_disk_io_%d", new Object[] { Integer.valueOf(this.mThreadId.getAndIncrement()) });
      Log5ECF72.a(paramAnonymousRunnable);
      LogE84000.a(paramAnonymousRunnable);
      Log229316.a(paramAnonymousRunnable);
      localThread.setName(paramAnonymousRunnable);
      return localThread;
    }
  });
  private final Object mLock = new Object();
  private volatile Handler mMainHandler;
  
  private static Handler createAsync(Looper paramLooper)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Handler.createAsync(paramLooper);
    }
    if (Build.VERSION.SDK_INT >= 16) {
      try
      {
        Handler localHandler = (Handler)Handler.class.getDeclaredConstructor(new Class[] { Looper.class, Handler.Callback.class, Boolean.TYPE }).newInstance(new Object[] { paramLooper, null, Boolean.valueOf(true) });
        return localHandler;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        return new Handler(paramLooper);
      }
      catch (NoSuchMethodException localNoSuchMethodException) {}catch (InstantiationException localInstantiationException) {}catch (IllegalAccessException localIllegalAccessException) {}
    }
    return new Handler(paramLooper);
  }
  
  public void executeOnDiskIO(Runnable paramRunnable)
  {
    this.mDiskIO.execute(paramRunnable);
  }
  
  public boolean isMainThread()
  {
    boolean bool;
    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void postToMainThread(Runnable paramRunnable)
  {
    if (this.mMainHandler == null) {
      synchronized (this.mLock)
      {
        if (this.mMainHandler == null) {
          this.mMainHandler = createAsync(Looper.getMainLooper());
        }
      }
    }
    this.mMainHandler.post(paramRunnable);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/arch/core/executor/DefaultTaskExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */