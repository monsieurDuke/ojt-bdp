package androidx.work.impl.utils;

import androidx.work.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WorkTimer
{
  private static final String TAG;
  private final ThreadFactory mBackgroundThreadFactory;
  private final ScheduledExecutorService mExecutorService;
  final Map<String, TimeLimitExceededListener> mListeners;
  final Object mLock;
  final Map<String, WorkTimerRunnable> mTimerMap;
  
  static
  {
    String str = Logger.tagWithPrefix("WorkTimer");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public WorkTimer()
  {
    ThreadFactory local1 = new ThreadFactory()
    {
      private int mThreadsCreated = 0;
      
      public Thread newThread(Runnable paramAnonymousRunnable)
      {
        paramAnonymousRunnable = Executors.defaultThreadFactory().newThread(paramAnonymousRunnable);
        paramAnonymousRunnable.setName("WorkManager-WorkTimer-thread-" + this.mThreadsCreated);
        this.mThreadsCreated += 1;
        return paramAnonymousRunnable;
      }
    };
    this.mBackgroundThreadFactory = local1;
    this.mTimerMap = new HashMap();
    this.mListeners = new HashMap();
    this.mLock = new Object();
    this.mExecutorService = Executors.newSingleThreadScheduledExecutor(local1);
  }
  
  public ScheduledExecutorService getExecutorService()
  {
    return this.mExecutorService;
  }
  
  public Map<String, TimeLimitExceededListener> getListeners()
  {
    try
    {
      Map localMap = this.mListeners;
      return localMap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Map<String, WorkTimerRunnable> getTimerMap()
  {
    try
    {
      Map localMap = this.mTimerMap;
      return localMap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDestroy()
  {
    if (!this.mExecutorService.isShutdown()) {
      this.mExecutorService.shutdownNow();
    }
  }
  
  public void startTimer(String paramString, long paramLong, TimeLimitExceededListener paramTimeLimitExceededListener)
  {
    synchronized (this.mLock)
    {
      Logger localLogger = Logger.get();
      Object localObject2 = TAG;
      String str = String.format("Starting timer for %s", new Object[] { paramString });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localLogger.debug((String)localObject2, str, new Throwable[0]);
      stopTimer(paramString);
      localObject2 = new androidx/work/impl/utils/WorkTimer$WorkTimerRunnable;
      ((WorkTimerRunnable)localObject2).<init>(this, paramString);
      this.mTimerMap.put(paramString, localObject2);
      this.mListeners.put(paramString, paramTimeLimitExceededListener);
      this.mExecutorService.schedule((Runnable)localObject2, paramLong, TimeUnit.MILLISECONDS);
      return;
    }
  }
  
  public void stopTimer(String paramString)
  {
    synchronized (this.mLock)
    {
      if ((WorkTimerRunnable)this.mTimerMap.remove(paramString) != null)
      {
        Logger localLogger = Logger.get();
        String str1 = TAG;
        String str2 = String.format("Stopping timer for %s", new Object[] { paramString });
        Log5ECF72.a(str2);
        LogE84000.a(str2);
        Log229316.a(str2);
        localLogger.debug(str1, str2, new Throwable[0]);
        this.mListeners.remove(paramString);
      }
      return;
    }
  }
  
  public static abstract interface TimeLimitExceededListener
  {
    public abstract void onTimeLimitExceeded(String paramString);
  }
  
  public static class WorkTimerRunnable
    implements Runnable
  {
    static final String TAG = "WrkTimerRunnable";
    private final String mWorkSpecId;
    private final WorkTimer mWorkTimer;
    
    WorkTimerRunnable(WorkTimer paramWorkTimer, String paramString)
    {
      this.mWorkTimer = paramWorkTimer;
      this.mWorkSpecId = paramString;
    }
    
    public void run()
    {
      synchronized (this.mWorkTimer.mLock)
      {
        Object localObject2;
        if ((WorkTimerRunnable)this.mWorkTimer.mTimerMap.remove(this.mWorkSpecId) != null)
        {
          localObject2 = (WorkTimer.TimeLimitExceededListener)this.mWorkTimer.mListeners.remove(this.mWorkSpecId);
          if (localObject2 != null) {
            ((WorkTimer.TimeLimitExceededListener)localObject2).onTimeLimitExceeded(this.mWorkSpecId);
          }
        }
        else
        {
          Logger localLogger = Logger.get();
          localObject2 = String.format("Timer with %s is already marked as complete.", new Object[] { this.mWorkSpecId });
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          localLogger.debug("WrkTimerRunnable", (String)localObject2, new Throwable[0]);
        }
        return;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/WorkTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */