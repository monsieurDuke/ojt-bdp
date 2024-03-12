package androidx.work.impl;

import android.content.Context;
import android.os.PowerManager.WakeLock;
import androidx.core.content.ContextCompat;
import androidx.work.Configuration;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.WorkerParameters.RuntimeExtras;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.utils.SerialExecutor;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Processor
  implements ExecutionListener, ForegroundProcessor
{
  private static final String FOREGROUND_WAKELOCK_TAG = "ProcessorForegroundLck";
  private static final String TAG;
  private Context mAppContext;
  private Set<String> mCancelledIds;
  private Configuration mConfiguration;
  private Map<String, WorkerWrapper> mEnqueuedWorkMap;
  private PowerManager.WakeLock mForegroundLock;
  private Map<String, WorkerWrapper> mForegroundWorkMap;
  private final Object mLock;
  private final List<ExecutionListener> mOuterListeners;
  private List<Scheduler> mSchedulers;
  private WorkDatabase mWorkDatabase;
  private TaskExecutor mWorkTaskExecutor;
  
  static
  {
    String str = Logger.tagWithPrefix("Processor");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public Processor(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, WorkDatabase paramWorkDatabase, List<Scheduler> paramList)
  {
    this.mAppContext = paramContext;
    this.mConfiguration = paramConfiguration;
    this.mWorkTaskExecutor = paramTaskExecutor;
    this.mWorkDatabase = paramWorkDatabase;
    this.mEnqueuedWorkMap = new HashMap();
    this.mForegroundWorkMap = new HashMap();
    this.mSchedulers = paramList;
    this.mCancelledIds = new HashSet();
    this.mOuterListeners = new ArrayList();
    this.mForegroundLock = null;
    this.mLock = new Object();
  }
  
  private static boolean interrupt(String paramString, WorkerWrapper paramWorkerWrapper)
  {
    if (paramWorkerWrapper != null)
    {
      paramWorkerWrapper.interrupt();
      paramWorkerWrapper = Logger.get();
      str = TAG;
      paramString = String.format("WorkerWrapper interrupted for %s", new Object[] { paramString });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      paramWorkerWrapper.debug(str, paramString, new Throwable[0]);
      return true;
    }
    paramWorkerWrapper = Logger.get();
    String str = TAG;
    paramString = String.format("WorkerWrapper could not be found for %s", new Object[] { paramString });
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    paramWorkerWrapper.debug(str, paramString, new Throwable[0]);
    return false;
  }
  
  /* Error */
  private void stopForegroundService()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 101	androidx/work/impl/Processor:mLock	Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 85	androidx/work/impl/Processor:mForegroundWorkMap	Ljava/util/Map;
    //   11: invokeinterface 135 1 0
    //   16: iconst_1
    //   17: ixor
    //   18: ifne +61 -> 79
    //   21: aload_0
    //   22: getfield 72	androidx/work/impl/Processor:mAppContext	Landroid/content/Context;
    //   25: invokestatic 141	androidx/work/impl/foreground/SystemForegroundDispatcher:createStopForegroundIntent	(Landroid/content/Context;)Landroid/content/Intent;
    //   28: astore_2
    //   29: aload_0
    //   30: getfield 72	androidx/work/impl/Processor:mAppContext	Landroid/content/Context;
    //   33: aload_2
    //   34: invokevirtual 147	android/content/Context:startService	(Landroid/content/Intent;)Landroid/content/ComponentName;
    //   37: pop
    //   38: goto +23 -> 61
    //   41: astore_2
    //   42: invokestatic 112	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   45: getstatic 64	androidx/work/impl/Processor:TAG	Ljava/lang/String;
    //   48: ldc -107
    //   50: iconst_1
    //   51: anewarray 122	java/lang/Throwable
    //   54: dup
    //   55: iconst_0
    //   56: aload_2
    //   57: aastore
    //   58: invokevirtual 152	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   61: aload_0
    //   62: getfield 99	androidx/work/impl/Processor:mForegroundLock	Landroid/os/PowerManager$WakeLock;
    //   65: astore_2
    //   66: aload_2
    //   67: ifnull +12 -> 79
    //   70: aload_2
    //   71: invokevirtual 157	android/os/PowerManager$WakeLock:release	()V
    //   74: aload_0
    //   75: aconst_null
    //   76: putfield 99	androidx/work/impl/Processor:mForegroundLock	Landroid/os/PowerManager$WakeLock;
    //   79: aload_1
    //   80: monitorexit
    //   81: return
    //   82: astore_2
    //   83: aload_1
    //   84: monitorexit
    //   85: aload_2
    //   86: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	87	0	this	Processor
    //   4	80	1	localObject1	Object
    //   28	6	2	localIntent	android.content.Intent
    //   41	16	2	localObject2	Object
    //   65	6	2	localWakeLock	PowerManager.WakeLock
    //   82	4	2	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   29	38	41	finally
    //   7	29	82	finally
    //   42	61	82	finally
    //   61	66	82	finally
    //   70	79	82	finally
    //   79	81	82	finally
    //   83	85	82	finally
  }
  
  public void addExecutionListener(ExecutionListener paramExecutionListener)
  {
    synchronized (this.mLock)
    {
      this.mOuterListeners.add(paramExecutionListener);
      return;
    }
  }
  
  public boolean hasWork()
  {
    synchronized (this.mLock)
    {
      boolean bool;
      if ((this.mEnqueuedWorkMap.isEmpty()) && (this.mForegroundWorkMap.isEmpty())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  public boolean isCancelled(String paramString)
  {
    synchronized (this.mLock)
    {
      boolean bool = this.mCancelledIds.contains(paramString);
      return bool;
    }
  }
  
  public boolean isEnqueued(String paramString)
  {
    synchronized (this.mLock)
    {
      boolean bool;
      if ((!this.mEnqueuedWorkMap.containsKey(paramString)) && (!this.mForegroundWorkMap.containsKey(paramString))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  public boolean isEnqueuedInForeground(String paramString)
  {
    synchronized (this.mLock)
    {
      boolean bool = this.mForegroundWorkMap.containsKey(paramString);
      return bool;
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      this.mEnqueuedWorkMap.remove(paramString);
      Logger localLogger = Logger.get();
      String str = TAG;
      Object localObject2 = String.format("%s %s executed; reschedule = %s", new Object[] { getClass().getSimpleName(), paramString, Boolean.valueOf(paramBoolean) });
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localLogger.debug(str, (String)localObject2, new Throwable[0]);
      localObject2 = this.mOuterListeners.iterator();
      while (((Iterator)localObject2).hasNext()) {
        ((ExecutionListener)((Iterator)localObject2).next()).onExecuted(paramString, paramBoolean);
      }
      return;
    }
  }
  
  public void removeExecutionListener(ExecutionListener paramExecutionListener)
  {
    synchronized (this.mLock)
    {
      this.mOuterListeners.remove(paramExecutionListener);
      return;
    }
  }
  
  public void startForeground(String paramString, ForegroundInfo paramForegroundInfo)
  {
    synchronized (this.mLock)
    {
      Logger localLogger = Logger.get();
      Object localObject3 = TAG;
      Object localObject2 = String.format("Moving WorkSpec (%s) to the foreground", new Object[] { paramString });
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localLogger.info((String)localObject3, (String)localObject2, new Throwable[0]);
      localObject3 = (WorkerWrapper)this.mEnqueuedWorkMap.remove(paramString);
      if (localObject3 != null)
      {
        if (this.mForegroundLock == null)
        {
          localObject2 = WakeLocks.newWakeLock(this.mAppContext, "ProcessorForegroundLck");
          this.mForegroundLock = ((PowerManager.WakeLock)localObject2);
          ((PowerManager.WakeLock)localObject2).acquire();
        }
        this.mForegroundWorkMap.put(paramString, localObject3);
        paramString = SystemForegroundDispatcher.createStartForegroundIntent(this.mAppContext, paramString, paramForegroundInfo);
        ContextCompat.startForegroundService(this.mAppContext, paramString);
      }
      return;
    }
  }
  
  public boolean startWork(String paramString)
  {
    return startWork(paramString, null);
  }
  
  public boolean startWork(String paramString, WorkerParameters.RuntimeExtras paramRuntimeExtras)
  {
    synchronized (this.mLock)
    {
      if (isEnqueued(paramString))
      {
        localObject2 = Logger.get();
        paramRuntimeExtras = TAG;
        paramString = String.format("Work %s is already enqueued for processing", new Object[] { paramString });
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        ((Logger)localObject2).debug(paramRuntimeExtras, paramString, new Throwable[0]);
        return false;
      }
      Object localObject2 = new androidx/work/impl/WorkerWrapper$Builder;
      ((WorkerWrapper.Builder)localObject2).<init>(this.mAppContext, this.mConfiguration, this.mWorkTaskExecutor, this, this.mWorkDatabase, paramString);
      paramRuntimeExtras = ((WorkerWrapper.Builder)localObject2).withSchedulers(this.mSchedulers).withRuntimeExtras(paramRuntimeExtras).build();
      ListenableFuture localListenableFuture = paramRuntimeExtras.getFuture();
      localObject2 = new androidx/work/impl/Processor$FutureListener;
      ((FutureListener)localObject2).<init>(this, paramString, localListenableFuture);
      localListenableFuture.addListener((Runnable)localObject2, this.mWorkTaskExecutor.getMainThreadExecutor());
      this.mEnqueuedWorkMap.put(paramString, paramRuntimeExtras);
      this.mWorkTaskExecutor.getBackgroundExecutor().execute(paramRuntimeExtras);
      paramRuntimeExtras = Logger.get();
      ??? = TAG;
      paramString = String.format("%s: processing %s", new Object[] { getClass().getSimpleName(), paramString });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      paramRuntimeExtras.debug((String)???, paramString, new Throwable[0]);
      return true;
    }
  }
  
  public boolean stopAndCancelWork(String paramString)
  {
    synchronized (this.mLock)
    {
      Object localObject1 = Logger.get();
      String str = TAG;
      int i = 1;
      Object localObject2 = String.format("Processor cancelling %s", new Object[] { paramString });
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      ((Logger)localObject1).debug(str, (String)localObject2, new Throwable[0]);
      this.mCancelledIds.add(paramString);
      localObject2 = (WorkerWrapper)this.mForegroundWorkMap.remove(paramString);
      if (localObject2 == null) {
        i = 0;
      }
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = (WorkerWrapper)this.mEnqueuedWorkMap.remove(paramString);
      }
      boolean bool = interrupt(paramString, (WorkerWrapper)localObject1);
      if (i != 0) {
        stopForegroundService();
      }
      return bool;
    }
  }
  
  public void stopForeground(String paramString)
  {
    synchronized (this.mLock)
    {
      this.mForegroundWorkMap.remove(paramString);
      stopForegroundService();
      return;
    }
  }
  
  public boolean stopForegroundWork(String paramString)
  {
    synchronized (this.mLock)
    {
      Logger localLogger = Logger.get();
      String str2 = TAG;
      String str1 = String.format("Processor stopping foreground work %s", new Object[] { paramString });
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
      localLogger.debug(str2, str1, new Throwable[0]);
      boolean bool = interrupt(paramString, (WorkerWrapper)this.mForegroundWorkMap.remove(paramString));
      return bool;
    }
  }
  
  public boolean stopWork(String paramString)
  {
    synchronized (this.mLock)
    {
      Logger localLogger = Logger.get();
      String str1 = TAG;
      String str2 = String.format("Processor stopping background work %s", new Object[] { paramString });
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      localLogger.debug(str1, str2, new Throwable[0]);
      boolean bool = interrupt(paramString, (WorkerWrapper)this.mEnqueuedWorkMap.remove(paramString));
      return bool;
    }
  }
  
  private static class FutureListener
    implements Runnable
  {
    private ExecutionListener mExecutionListener;
    private ListenableFuture<Boolean> mFuture;
    private String mWorkSpecId;
    
    FutureListener(ExecutionListener paramExecutionListener, String paramString, ListenableFuture<Boolean> paramListenableFuture)
    {
      this.mExecutionListener = paramExecutionListener;
      this.mWorkSpecId = paramString;
      this.mFuture = paramListenableFuture;
    }
    
    public void run()
    {
      try
      {
        bool = ((Boolean)this.mFuture.get()).booleanValue();
      }
      catch (ExecutionException localExecutionException) {}catch (InterruptedException localInterruptedException) {}
      boolean bool = true;
      this.mExecutionListener.onExecuted(this.mWorkSpecId, bool);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/Processor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */