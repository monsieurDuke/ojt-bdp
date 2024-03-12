package okhttp3.internal.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\004\n\002\020!\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\t\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020 \n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\r\030\000 #2\0020\001:\003\"#$B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\f\020\023\032\b\022\004\022\0020\t0\024J\030\020\025\032\0020\0262\006\020\027\032\0020\0302\006\020\031\032\0020\rH\002J\b\020\032\032\004\030\0010\030J\020\020\033\032\0020\0262\006\020\027\032\0020\030H\002J\006\020\034\032\0020\026J\025\020\035\032\0020\0262\006\020\036\032\0020\tH\000¢\006\002\b\037J\006\020 \032\0020\tJ\020\020!\032\0020\0262\006\020\027\032\0020\030H\002R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\005\020\006R\024\020\007\032\b\022\004\022\0020\t0\bX\004¢\006\002\n\000R\016\020\n\032\0020\013X\016¢\006\002\n\000R\016\020\f\032\0020\rX\016¢\006\002\n\000R\016\020\016\032\0020\017X\016¢\006\002\n\000R\024\020\020\032\b\022\004\022\0020\t0\bX\004¢\006\002\n\000R\016\020\021\032\0020\022X\004¢\006\002\n\000¨\006%"}, d2={"Lokhttp3/internal/concurrent/TaskRunner;", "", "backend", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "(Lokhttp3/internal/concurrent/TaskRunner$Backend;)V", "getBackend", "()Lokhttp3/internal/concurrent/TaskRunner$Backend;", "busyQueues", "", "Lokhttp3/internal/concurrent/TaskQueue;", "coordinatorWaiting", "", "coordinatorWakeUpAt", "", "nextQueueName", "", "readyQueues", "runnable", "Ljava/lang/Runnable;", "activeQueues", "", "afterRun", "", "task", "Lokhttp3/internal/concurrent/Task;", "delayNanos", "awaitTaskToRun", "beforeRun", "cancelAll", "kickCoordinator", "taskQueue", "kickCoordinator$okhttp", "newQueue", "runTask", "Backend", "Companion", "RealBackend", "okhttp"}, k=1, mv={1, 4, 0})
public final class TaskRunner
{
  public static final Companion Companion = new Companion(null);
  public static final TaskRunner INSTANCE = new TaskRunner((Backend)new RealBackend(Util.threadFactory(Util.okHttpName + " TaskRunner", true)));
  private static final Logger logger;
  private final Backend backend;
  private final List<TaskQueue> busyQueues;
  private boolean coordinatorWaiting;
  private long coordinatorWakeUpAt;
  private int nextQueueName;
  private final List<TaskQueue> readyQueues;
  private final Runnable runnable;
  
  static
  {
    Logger localLogger = Logger.getLogger(TaskRunner.class.getName());
    Intrinsics.checkNotNullExpressionValue(localLogger, "Logger.getLogger(TaskRunner::class.java.name)");
    logger = localLogger;
  }
  
  public TaskRunner(Backend paramBackend)
  {
    this.backend = paramBackend;
    this.nextQueueName = 10000;
    this.busyQueues = ((List)new ArrayList());
    this.readyQueues = ((List)new ArrayList());
    this.runnable = ((Runnable)new Runnable()
    {
      final TaskRunner this$0;
      
      public void run()
      {
        Task localTask;
        long l1;
        boolean bool;
        Object localObject3;
        long l2;
        Object localObject6;
        Object localObject7;
        synchronized (this.this$0)
        {
          localTask = this.this$0.awaitTaskToRun();
          if (localTask != null)
          {
            ??? = localTask.getQueue$okhttp();
            Intrinsics.checkNotNull(???);
            l1 = -1L;
            bool = TaskRunner.Companion.getLogger().isLoggable(Level.FINE);
            if (bool)
            {
              l1 = ((TaskQueue)???).getTaskRunner$okhttp().getBackend().nanoTime();
              TaskLoggerKt.access$log(localTask, (TaskQueue)???, "starting");
            }
          }
        }
      }
    });
  }
  
  private final void afterRun(Task paramTask, long paramLong)
  {
    Object localObject;
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      localObject = new StringBuilder().append("Thread ");
      paramTask = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramTask, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramTask.getName() + " MUST hold lock on " + this));
    }
    TaskQueue localTaskQueue = paramTask.getQueue$okhttp();
    Intrinsics.checkNotNull(localTaskQueue);
    int i;
    if (localTaskQueue.getActiveTask$okhttp() == paramTask) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      boolean bool = localTaskQueue.getCancelActiveTask$okhttp();
      localTaskQueue.setCancelActiveTask$okhttp(false);
      localObject = (Task)null;
      localTaskQueue.setActiveTask$okhttp(null);
      this.busyQueues.remove(localTaskQueue);
      if ((paramLong != -1L) && (!bool) && (!localTaskQueue.getShutdown$okhttp())) {
        localTaskQueue.scheduleAndDecide$okhttp(paramTask, paramLong, true);
      }
      if ((((Collection)localTaskQueue.getFutureTasks$okhttp()).isEmpty() ^ true)) {
        this.readyQueues.add(localTaskQueue);
      }
      return;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  private final void beforeRun(Task paramTask)
  {
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      paramTask = new StringBuilder().append("Thread ");
      localObject = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localObject, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(((Thread)localObject).getName() + " MUST hold lock on " + this));
    }
    paramTask.setNextExecuteNanoTime$okhttp(-1L);
    Object localObject = paramTask.getQueue$okhttp();
    Intrinsics.checkNotNull(localObject);
    ((TaskQueue)localObject).getFutureTasks$okhttp().remove(paramTask);
    this.readyQueues.remove(localObject);
    ((TaskQueue)localObject).setActiveTask$okhttp(paramTask);
    this.busyQueues.add(localObject);
  }
  
  /* Error */
  private final void runTask(Task paramTask)
  {
    // Byte code:
    //   0: getstatic 161	okhttp3/internal/Util:assertionsEnabled	Z
    //   3: ifeq +69 -> 72
    //   6: aload_0
    //   7: invokestatic 167	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   10: ifne +6 -> 16
    //   13: goto +59 -> 72
    //   16: new 74	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 76	java/lang/StringBuilder:<init>	()V
    //   23: ldc -87
    //   25: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: astore 4
    //   30: invokestatic 173	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   33: astore_1
    //   34: aload_1
    //   35: ldc -81
    //   37: invokestatic 122	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
    //   40: new 177	java/lang/AssertionError
    //   43: dup
    //   44: aload 4
    //   46: aload_1
    //   47: invokevirtual 178	java/lang/Thread:getName	()Ljava/lang/String;
    //   50: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: ldc -4
    //   55: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: aload_0
    //   59: invokevirtual 183	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   62: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: invokespecial 186	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   68: checkcast 188	java/lang/Throwable
    //   71: athrow
    //   72: invokestatic 173	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   75: astore 4
    //   77: aload 4
    //   79: ldc -3
    //   81: invokestatic 122	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
    //   84: aload 4
    //   86: invokevirtual 178	java/lang/Thread:getName	()Ljava/lang/String;
    //   89: astore 5
    //   91: aload 4
    //   93: aload_1
    //   94: invokevirtual 254	okhttp3/internal/concurrent/Task:getName	()Ljava/lang/String;
    //   97: invokevirtual 257	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   100: aload_1
    //   101: invokevirtual 261	okhttp3/internal/concurrent/Task:runOnce	()J
    //   104: lstore_2
    //   105: aload_0
    //   106: monitorenter
    //   107: aload_0
    //   108: aload_1
    //   109: lload_2
    //   110: invokespecial 263	okhttp3/internal/concurrent/TaskRunner:afterRun	(Lokhttp3/internal/concurrent/Task;J)V
    //   113: getstatic 268	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   116: astore_1
    //   117: aload_0
    //   118: monitorexit
    //   119: aload 4
    //   121: aload 5
    //   123: invokevirtual 257	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   126: return
    //   127: astore_1
    //   128: aload_0
    //   129: monitorexit
    //   130: aload_1
    //   131: athrow
    //   132: astore 6
    //   134: aload_0
    //   135: monitorenter
    //   136: aload_0
    //   137: aload_1
    //   138: ldc2_w 218
    //   141: invokespecial 263	okhttp3/internal/concurrent/TaskRunner:afterRun	(Lokhttp3/internal/concurrent/Task;J)V
    //   144: getstatic 268	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   147: astore_1
    //   148: aload_0
    //   149: monitorexit
    //   150: aload 4
    //   152: aload 5
    //   154: invokevirtual 257	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   157: aload 6
    //   159: athrow
    //   160: astore_1
    //   161: aload_0
    //   162: monitorexit
    //   163: aload_1
    //   164: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	TaskRunner
    //   0	165	1	paramTask	Task
    //   104	6	2	l	long
    //   28	123	4	localObject1	Object
    //   89	64	5	str	String
    //   132	26	6	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   107	117	127	finally
    //   100	105	132	finally
    //   136	148	160	finally
  }
  
  public final List<TaskQueue> activeQueues()
  {
    try
    {
      List localList = CollectionsKt.plus((Collection)this.busyQueues, (Iterable)this.readyQueues);
      return localList;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public final Task awaitTaskToRun()
  {
    // Byte code:
    //   0: getstatic 161	okhttp3/internal/Util:assertionsEnabled	Z
    //   3: ifeq +72 -> 75
    //   6: aload_0
    //   7: invokestatic 167	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   10: ifeq +6 -> 16
    //   13: goto +62 -> 75
    //   16: new 74	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 76	java/lang/StringBuilder:<init>	()V
    //   23: ldc -87
    //   25: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: astore 9
    //   30: invokestatic 173	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   33: astore 10
    //   35: aload 10
    //   37: ldc -81
    //   39: invokestatic 122	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
    //   42: new 177	java/lang/AssertionError
    //   45: dup
    //   46: aload 9
    //   48: aload 10
    //   50: invokevirtual 178	java/lang/Thread:getName	()Ljava/lang/String;
    //   53: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: ldc -76
    //   58: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: aload_0
    //   62: invokevirtual 183	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   65: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   68: invokespecial 186	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   71: checkcast 188	java/lang/Throwable
    //   74: athrow
    //   75: aload_0
    //   76: getfield 143	okhttp3/internal/concurrent/TaskRunner:readyQueues	Ljava/util/List;
    //   79: invokeinterface 281 1 0
    //   84: ifeq +5 -> 89
    //   87: aconst_null
    //   88: areturn
    //   89: aload_0
    //   90: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   93: invokeinterface 284 1 0
    //   98: lstore 5
    //   100: ldc2_w 285
    //   103: lstore_3
    //   104: aconst_null
    //   105: checkcast 190	okhttp3/internal/concurrent/Task
    //   108: astore 9
    //   110: iconst_0
    //   111: istore_2
    //   112: aload_0
    //   113: getfield 143	okhttp3/internal/concurrent/TaskRunner:readyQueues	Ljava/util/List;
    //   116: invokeinterface 290 1 0
    //   121: astore 11
    //   123: iload_2
    //   124: istore_1
    //   125: aload 11
    //   127: invokeinterface 295 1 0
    //   132: ifeq +75 -> 207
    //   135: aload 11
    //   137: invokeinterface 299 1 0
    //   142: checkcast 199	okhttp3/internal/concurrent/TaskQueue
    //   145: invokevirtual 230	okhttp3/internal/concurrent/TaskQueue:getFutureTasks$okhttp	()Ljava/util/List;
    //   148: iconst_0
    //   149: invokeinterface 303 2 0
    //   154: checkcast 190	okhttp3/internal/concurrent/Task
    //   157: astore 10
    //   159: lconst_0
    //   160: aload 10
    //   162: invokevirtual 306	okhttp3/internal/concurrent/Task:getNextExecuteNanoTime$okhttp	()J
    //   165: lload 5
    //   167: lsub
    //   168: invokestatic 312	java/lang/Math:max	(JJ)J
    //   171: lstore 7
    //   173: lload 7
    //   175: lconst_0
    //   176: lcmp
    //   177: ifle +13 -> 190
    //   180: lload 7
    //   182: lload_3
    //   183: invokestatic 315	java/lang/Math:min	(JJ)J
    //   186: lstore_3
    //   187: goto +17 -> 204
    //   190: aload 9
    //   192: ifnull +8 -> 200
    //   195: iconst_1
    //   196: istore_1
    //   197: goto +10 -> 207
    //   200: aload 10
    //   202: astore 9
    //   204: goto -81 -> 123
    //   207: aload 9
    //   209: ifnull +53 -> 262
    //   212: aload_0
    //   213: aload 9
    //   215: invokespecial 317	okhttp3/internal/concurrent/TaskRunner:beforeRun	(Lokhttp3/internal/concurrent/Task;)V
    //   218: iload_1
    //   219: ifne +27 -> 246
    //   222: aload_0
    //   223: getfield 319	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   226: ifne +33 -> 259
    //   229: aload_0
    //   230: getfield 143	okhttp3/internal/concurrent/TaskRunner:readyQueues	Ljava/util/List;
    //   233: checkcast 232	java/util/Collection
    //   236: invokeinterface 235 1 0
    //   241: iconst_1
    //   242: ixor
    //   243: ifeq +16 -> 259
    //   246: aload_0
    //   247: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   250: aload_0
    //   251: getfield 150	okhttp3/internal/concurrent/TaskRunner:runnable	Ljava/lang/Runnable;
    //   254: invokeinterface 323 2 0
    //   259: aload 9
    //   261: areturn
    //   262: aload_0
    //   263: getfield 319	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   266: ifeq +27 -> 293
    //   269: lload_3
    //   270: aload_0
    //   271: getfield 325	okhttp3/internal/concurrent/TaskRunner:coordinatorWakeUpAt	J
    //   274: lload 5
    //   276: lsub
    //   277: lcmp
    //   278: ifge +13 -> 291
    //   281: aload_0
    //   282: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   285: aload_0
    //   286: invokeinterface 328 2 0
    //   291: aconst_null
    //   292: areturn
    //   293: aload_0
    //   294: iconst_1
    //   295: putfield 319	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   298: aload_0
    //   299: lload 5
    //   301: lload_3
    //   302: ladd
    //   303: putfield 325	okhttp3/internal/concurrent/TaskRunner:coordinatorWakeUpAt	J
    //   306: aload_0
    //   307: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   310: aload_0
    //   311: lload_3
    //   312: invokeinterface 332 4 0
    //   317: aload_0
    //   318: iconst_0
    //   319: putfield 319	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   322: goto +17 -> 339
    //   325: astore 9
    //   327: goto +15 -> 342
    //   330: astore 9
    //   332: aload_0
    //   333: invokevirtual 334	okhttp3/internal/concurrent/TaskRunner:cancelAll	()V
    //   336: goto -19 -> 317
    //   339: goto -264 -> 75
    //   342: aload_0
    //   343: iconst_0
    //   344: putfield 319	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   347: aload 9
    //   349: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	350	0	this	TaskRunner
    //   124	95	1	i	int
    //   111	13	2	j	int
    //   103	209	3	l1	long
    //   98	202	5	l2	long
    //   171	10	7	l3	long
    //   28	232	9	localObject1	Object
    //   325	1	9	localObject2	Object
    //   330	18	9	localInterruptedException	InterruptedException
    //   33	168	10	localObject3	Object
    //   121	15	11	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   306	317	325	finally
    //   332	336	325	finally
    //   306	317	330	java/lang/InterruptedException
  }
  
  public final void cancelAll()
  {
    for (int i = this.busyQueues.size() - 1; i >= 0; i--) {
      ((TaskQueue)this.busyQueues.get(i)).cancelAllAndDecide$okhttp();
    }
    for (i = this.readyQueues.size() - 1; i >= 0; i--)
    {
      TaskQueue localTaskQueue = (TaskQueue)this.readyQueues.get(i);
      localTaskQueue.cancelAllAndDecide$okhttp();
      if (localTaskQueue.getFutureTasks$okhttp().isEmpty()) {
        this.readyQueues.remove(i);
      }
    }
  }
  
  public final Backend getBackend()
  {
    return this.backend;
  }
  
  public final void kickCoordinator$okhttp(TaskQueue paramTaskQueue)
  {
    Intrinsics.checkNotNullParameter(paramTaskQueue, "taskQueue");
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      StringBuilder localStringBuilder = new StringBuilder().append("Thread ");
      paramTaskQueue = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramTaskQueue, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramTaskQueue.getName() + " MUST hold lock on " + this));
    }
    if (paramTaskQueue.getActiveTask$okhttp() == null) {
      if ((((Collection)paramTaskQueue.getFutureTasks$okhttp()).isEmpty() ^ true)) {
        Util.addIfAbsent(this.readyQueues, paramTaskQueue);
      } else {
        this.readyQueues.remove(paramTaskQueue);
      }
    }
    if (this.coordinatorWaiting) {
      this.backend.coordinatorNotify(this);
    } else {
      this.backend.execute(this.runnable);
    }
  }
  
  public final TaskQueue newQueue()
  {
    try
    {
      int i = this.nextQueueName;
      this.nextQueueName = (i + 1);
      return new TaskQueue(this, 'Q' + i);
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\002\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&J\020\020\006\032\0020\0032\006\020\004\032\0020\005H&J\030\020\007\032\0020\0032\006\020\004\032\0020\0052\006\020\b\032\0020\tH&J\020\020\n\032\0020\0032\006\020\013\032\0020\fH&J\b\020\r\032\0020\tH&¨\006\016"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$Backend;", "", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "okhttp"}, k=1, mv={1, 4, 0})
  public static abstract interface Backend
  {
    public abstract void beforeTask(TaskRunner paramTaskRunner);
    
    public abstract void coordinatorNotify(TaskRunner paramTaskRunner);
    
    public abstract void coordinatorWait(TaskRunner paramTaskRunner, long paramLong);
    
    public abstract void execute(Runnable paramRunnable);
    
    public abstract long nanoTime();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000R\021\020\005\032\0020\006¢\006\b\n\000\032\004\b\007\020\b¨\006\t"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$Companion;", "", "()V", "INSTANCE", "Lokhttp3/internal/concurrent/TaskRunner;", "logger", "Ljava/util/logging/Logger;", "getLogger", "()Ljava/util/logging/Logger;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final Logger getLogger()
    {
      return TaskRunner.access$getLogger$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\003\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\026J\020\020\013\032\0020\b2\006\020\t\032\0020\nH\026J\030\020\f\032\0020\b2\006\020\t\032\0020\n2\006\020\r\032\0020\016H\026J\020\020\017\032\0020\b2\006\020\020\032\0020\021H\026J\b\020\022\032\0020\016H\026J\006\020\023\032\0020\bR\016\020\005\032\0020\006X\004¢\006\002\n\000¨\006\024"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$RealBackend;", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "(Ljava/util/concurrent/ThreadFactory;)V", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "shutdown", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class RealBackend
    implements TaskRunner.Backend
  {
    private final ThreadPoolExecutor executor;
    
    public RealBackend(ThreadFactory paramThreadFactory)
    {
      TimeUnit localTimeUnit = TimeUnit.SECONDS;
      BlockingQueue localBlockingQueue = (BlockingQueue)new SynchronousQueue();
      this.executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, localTimeUnit, localBlockingQueue, paramThreadFactory);
    }
    
    public void beforeTask(TaskRunner paramTaskRunner)
    {
      Intrinsics.checkNotNullParameter(paramTaskRunner, "taskRunner");
    }
    
    public void coordinatorNotify(TaskRunner paramTaskRunner)
    {
      Intrinsics.checkNotNullParameter(paramTaskRunner, "taskRunner");
      ((Object)paramTaskRunner).notify();
    }
    
    public void coordinatorWait(TaskRunner paramTaskRunner, long paramLong)
      throws InterruptedException
    {
      Intrinsics.checkNotNullParameter(paramTaskRunner, "taskRunner");
      long l = paramLong / 1000000L;
      if ((l > 0L) || (paramLong > 0L)) {
        ((Object)paramTaskRunner).wait(l, (int)(paramLong - 1000000L * l));
      }
    }
    
    public void execute(Runnable paramRunnable)
    {
      Intrinsics.checkNotNullParameter(paramRunnable, "runnable");
      this.executor.execute(paramRunnable);
    }
    
    public long nanoTime()
    {
      return System.nanoTime();
    }
    
    public final void shutdown()
    {
      this.executor.shutdown();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/concurrent/TaskRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */