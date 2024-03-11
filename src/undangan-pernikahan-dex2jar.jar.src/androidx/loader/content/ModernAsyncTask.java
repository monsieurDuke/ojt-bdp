package androidx.loader.content;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

abstract class ModernAsyncTask<Params, Progress, Result>
{
  private static final int CORE_POOL_SIZE = 5;
  private static final int KEEP_ALIVE = 1;
  private static final String LOG_TAG = "AsyncTask";
  private static final int MAXIMUM_POOL_SIZE = 128;
  private static final int MESSAGE_POST_PROGRESS = 2;
  private static final int MESSAGE_POST_RESULT = 1;
  public static final Executor THREAD_POOL_EXECUTOR;
  private static volatile Executor sDefaultExecutor;
  private static InternalHandler sHandler;
  private static final BlockingQueue<Runnable> sPoolWorkQueue;
  private static final ThreadFactory sThreadFactory;
  final AtomicBoolean mCancelled = new AtomicBoolean();
  private final FutureTask<Result> mFuture;
  private volatile Status mStatus = Status.PENDING;
  final AtomicBoolean mTaskInvoked = new AtomicBoolean();
  private final WorkerRunnable<Params, Result> mWorker;
  
  static
  {
    Object localObject = new ThreadFactory()
    {
      private final AtomicInteger mCount = new AtomicInteger(1);
      
      public Thread newThread(Runnable paramAnonymousRunnable)
      {
        return new Thread(paramAnonymousRunnable, "ModernAsyncTask #" + this.mCount.getAndIncrement());
      }
    };
    sThreadFactory = (ThreadFactory)localObject;
    LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue(10);
    sPoolWorkQueue = localLinkedBlockingQueue;
    localObject = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, localLinkedBlockingQueue, (ThreadFactory)localObject);
    THREAD_POOL_EXECUTOR = (Executor)localObject;
    sDefaultExecutor = (Executor)localObject;
  }
  
  ModernAsyncTask()
  {
    WorkerRunnable local2 = new WorkerRunnable()
    {
      /* Error */
      public Result call()
        throws java.lang.Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 15	androidx/loader/content/ModernAsyncTask$2:this$0	Landroidx/loader/content/ModernAsyncTask;
        //   4: getfield 26	androidx/loader/content/ModernAsyncTask:mTaskInvoked	Ljava/util/concurrent/atomic/AtomicBoolean;
        //   7: iconst_1
        //   8: invokevirtual 32	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
        //   11: aconst_null
        //   12: astore_2
        //   13: aload_2
        //   14: astore_1
        //   15: bipush 10
        //   17: invokestatic 38	android/os/Process:setThreadPriority	(I)V
        //   20: aload_2
        //   21: astore_1
        //   22: aload_0
        //   23: getfield 15	androidx/loader/content/ModernAsyncTask$2:this$0	Landroidx/loader/content/ModernAsyncTask;
        //   26: aload_0
        //   27: getfield 42	androidx/loader/content/ModernAsyncTask$2:mParams	[Ljava/lang/Object;
        //   30: invokevirtual 46	androidx/loader/content/ModernAsyncTask:doInBackground	([Ljava/lang/Object;)Ljava/lang/Object;
        //   33: astore_2
        //   34: aload_2
        //   35: astore_1
        //   36: invokestatic 51	android/os/Binder:flushPendingCommands	()V
        //   39: aload_0
        //   40: getfield 15	androidx/loader/content/ModernAsyncTask$2:this$0	Landroidx/loader/content/ModernAsyncTask;
        //   43: aload_2
        //   44: invokevirtual 55	androidx/loader/content/ModernAsyncTask:postResult	(Ljava/lang/Object;)Ljava/lang/Object;
        //   47: pop
        //   48: aload_2
        //   49: areturn
        //   50: astore_2
        //   51: aload_0
        //   52: getfield 15	androidx/loader/content/ModernAsyncTask$2:this$0	Landroidx/loader/content/ModernAsyncTask;
        //   55: getfield 58	androidx/loader/content/ModernAsyncTask:mCancelled	Ljava/util/concurrent/atomic/AtomicBoolean;
        //   58: iconst_1
        //   59: invokevirtual 32	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
        //   62: aload_2
        //   63: athrow
        //   64: astore_2
        //   65: aload_0
        //   66: getfield 15	androidx/loader/content/ModernAsyncTask$2:this$0	Landroidx/loader/content/ModernAsyncTask;
        //   69: aload_1
        //   70: invokevirtual 55	androidx/loader/content/ModernAsyncTask:postResult	(Ljava/lang/Object;)Ljava/lang/Object;
        //   73: pop
        //   74: aload_2
        //   75: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	76	0	this	2
        //   14	56	1	localObject1	Object
        //   12	37	2	localObject2	Object
        //   50	13	2	localObject3	Object
        //   64	11	2	localObject4	Object
        // Exception table:
        //   from	to	target	type
        //   15	20	50	finally
        //   22	34	50	finally
        //   36	39	50	finally
        //   51	62	64	finally
        //   62	64	64	finally
      }
    };
    this.mWorker = local2;
    this.mFuture = new FutureTask(local2)
    {
      /* Error */
      protected void done()
      {
        // Byte code:
        //   0: aload_0
        //   1: invokevirtual 30	androidx/loader/content/ModernAsyncTask$3:get	()Ljava/lang/Object;
        //   4: astore_1
        //   5: aload_0
        //   6: getfield 15	androidx/loader/content/ModernAsyncTask$3:this$0	Landroidx/loader/content/ModernAsyncTask;
        //   9: aload_1
        //   10: invokevirtual 34	androidx/loader/content/ModernAsyncTask:postResultIfNotInvoked	(Ljava/lang/Object;)V
        //   13: goto +50 -> 63
        //   16: astore_1
        //   17: new 36	java/lang/RuntimeException
        //   20: dup
        //   21: ldc 38
        //   23: aload_1
        //   24: invokespecial 41	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   27: athrow
        //   28: astore_1
        //   29: aload_0
        //   30: getfield 15	androidx/loader/content/ModernAsyncTask$3:this$0	Landroidx/loader/content/ModernAsyncTask;
        //   33: aconst_null
        //   34: invokevirtual 34	androidx/loader/content/ModernAsyncTask:postResultIfNotInvoked	(Ljava/lang/Object;)V
        //   37: goto +26 -> 63
        //   40: astore_1
        //   41: new 36	java/lang/RuntimeException
        //   44: dup
        //   45: ldc 38
        //   47: aload_1
        //   48: invokevirtual 45	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
        //   51: invokespecial 41	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   54: athrow
        //   55: astore_1
        //   56: ldc 47
        //   58: aload_1
        //   59: invokestatic 53	android/util/Log:w	(Ljava/lang/String;Ljava/lang/Throwable;)I
        //   62: pop
        //   63: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	64	0	this	3
        //   4	6	1	localObject	Object
        //   16	8	1	localThrowable	Throwable
        //   28	1	1	localCancellationException	java.util.concurrent.CancellationException
        //   40	8	1	localExecutionException	ExecutionException
        //   55	4	1	localInterruptedException	InterruptedException
        // Exception table:
        //   from	to	target	type
        //   0	13	16	finally
        //   0	13	28	java/util/concurrent/CancellationException
        //   0	13	40	java/util/concurrent/ExecutionException
        //   0	13	55	java/lang/InterruptedException
      }
    };
  }
  
  public static void execute(Runnable paramRunnable)
  {
    sDefaultExecutor.execute(paramRunnable);
  }
  
  private static Handler getHandler()
  {
    try
    {
      if (sHandler == null)
      {
        localInternalHandler = new androidx/loader/content/ModernAsyncTask$InternalHandler;
        localInternalHandler.<init>();
        sHandler = localInternalHandler;
      }
      InternalHandler localInternalHandler = sHandler;
      return localInternalHandler;
    }
    finally {}
  }
  
  public static void setDefaultExecutor(Executor paramExecutor)
  {
    sDefaultExecutor = paramExecutor;
  }
  
  public final boolean cancel(boolean paramBoolean)
  {
    this.mCancelled.set(true);
    return this.mFuture.cancel(paramBoolean);
  }
  
  protected abstract Result doInBackground(Params... paramVarArgs);
  
  public final ModernAsyncTask<Params, Progress, Result> execute(Params... paramVarArgs)
  {
    return executeOnExecutor(sDefaultExecutor, paramVarArgs);
  }
  
  public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor paramExecutor, Params... paramVarArgs)
  {
    if (this.mStatus != Status.PENDING)
    {
      switch (4.$SwitchMap$androidx$loader$content$ModernAsyncTask$Status[this.mStatus.ordinal()])
      {
      default: 
        throw new IllegalStateException("We should never reach this state");
      case 2: 
        throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
      }
      throw new IllegalStateException("Cannot execute task: the task is already running.");
    }
    this.mStatus = Status.RUNNING;
    onPreExecute();
    this.mWorker.mParams = paramVarArgs;
    paramExecutor.execute(this.mFuture);
    return this;
  }
  
  void finish(Result paramResult)
  {
    if (isCancelled()) {
      onCancelled(paramResult);
    } else {
      onPostExecute(paramResult);
    }
    this.mStatus = Status.FINISHED;
  }
  
  public final Result get()
    throws InterruptedException, ExecutionException
  {
    return (Result)this.mFuture.get();
  }
  
  public final Result get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    return (Result)this.mFuture.get(paramLong, paramTimeUnit);
  }
  
  public final Status getStatus()
  {
    return this.mStatus;
  }
  
  public final boolean isCancelled()
  {
    return this.mCancelled.get();
  }
  
  protected void onCancelled() {}
  
  protected void onCancelled(Result paramResult)
  {
    onCancelled();
  }
  
  protected void onPostExecute(Result paramResult) {}
  
  protected void onPreExecute() {}
  
  protected void onProgressUpdate(Progress... paramVarArgs) {}
  
  Result postResult(Result paramResult)
  {
    getHandler().obtainMessage(1, new AsyncTaskResult(this, new Object[] { paramResult })).sendToTarget();
    return paramResult;
  }
  
  void postResultIfNotInvoked(Result paramResult)
  {
    if (!this.mTaskInvoked.get()) {
      postResult(paramResult);
    }
  }
  
  protected final void publishProgress(Progress... paramVarArgs)
  {
    if (!isCancelled()) {
      getHandler().obtainMessage(2, new AsyncTaskResult(this, paramVarArgs)).sendToTarget();
    }
  }
  
  private static class AsyncTaskResult<Data>
  {
    final Data[] mData;
    final ModernAsyncTask mTask;
    
    AsyncTaskResult(ModernAsyncTask paramModernAsyncTask, Data... paramVarArgs)
    {
      this.mTask = paramModernAsyncTask;
      this.mData = paramVarArgs;
    }
  }
  
  private static class InternalHandler
    extends Handler
  {
    InternalHandler()
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      ModernAsyncTask.AsyncTaskResult localAsyncTaskResult = (ModernAsyncTask.AsyncTaskResult)paramMessage.obj;
      switch (paramMessage.what)
      {
      default: 
        break;
      case 2: 
        localAsyncTaskResult.mTask.onProgressUpdate(localAsyncTaskResult.mData);
        break;
      case 1: 
        localAsyncTaskResult.mTask.finish(localAsyncTaskResult.mData[0]);
      }
    }
  }
  
  public static enum Status
  {
    private static final Status[] $VALUES;
    
    static
    {
      Status localStatus2 = new Status("PENDING", 0);
      PENDING = localStatus2;
      Status localStatus1 = new Status("RUNNING", 1);
      RUNNING = localStatus1;
      Status localStatus3 = new Status("FINISHED", 2);
      FINISHED = localStatus3;
      $VALUES = new Status[] { localStatus2, localStatus1, localStatus3 };
    }
    
    private Status() {}
  }
  
  private static abstract class WorkerRunnable<Params, Result>
    implements Callable<Result>
  {
    Params[] mParams;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/loader/content/ModernAsyncTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */