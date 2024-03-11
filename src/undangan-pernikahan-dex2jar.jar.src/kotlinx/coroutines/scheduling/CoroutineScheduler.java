package kotlinx.coroutines.scheduling;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.Random.Default;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.AbstractTimeSource;
import kotlinx.coroutines.AbstractTimeSourceKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.ResizableAtomicArray;
import kotlinx.coroutines.internal.Symbol;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000b\n\002\030\002\n\002\020\b\n\002\b\002\n\002\020\t\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\002\b\006\n\002\020\002\n\002\b\004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b-\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\b\000\030\000 X2\0020\\2\0020]:\003XYZB+\022\006\020\002\032\0020\001\022\006\020\003\032\0020\001\022\b\b\002\020\005\032\0020\004\022\b\b\002\020\007\032\0020\006¢\006\004\b\b\020\tJ\027\020\r\032\0020\f2\006\020\013\032\0020\nH\002¢\006\004\b\r\020\016J\030\020\020\032\0020\0012\006\020\017\032\0020\004H\b¢\006\004\b\020\020\021J\030\020\022\032\0020\0012\006\020\017\032\0020\004H\b¢\006\004\b\022\020\021J\017\020\024\032\0020\023H\026¢\006\004\b\024\020\025J\017\020\026\032\0020\001H\002¢\006\004\b\026\020\027J!\020\035\032\0020\n2\n\020\032\032\0060\030j\002`\0312\006\020\034\032\0020\033¢\006\004\b\035\020\036J\030\020\037\032\0020\0012\006\020\017\032\0020\004H\b¢\006\004\b\037\020\021J\025\020!\032\b\030\0010 R\0020\000H\002¢\006\004\b!\020\"J\020\020#\032\0020\023H\b¢\006\004\b#\020\025J\020\020$\032\0020\001H\b¢\006\004\b$\020\027J-\020&\032\0020\0232\n\020\032\032\0060\030j\002`\0312\b\b\002\020\034\032\0020\0332\b\b\002\020%\032\0020\f¢\006\004\b&\020'J\033\020)\032\0020\0232\n\020(\032\0060\030j\002`\031H\026¢\006\004\b)\020*J\020\020+\032\0020\004H\b¢\006\004\b+\020,J\020\020-\032\0020\001H\b¢\006\004\b-\020\027J\033\020/\032\0020\0012\n\020.\032\0060 R\0020\000H\002¢\006\004\b/\0200J\025\0201\032\b\030\0010 R\0020\000H\002¢\006\004\b1\020\"J\031\0202\032\0020\f2\n\020.\032\0060 R\0020\000¢\006\004\b2\0203J)\0206\032\0020\0232\n\020.\032\0060 R\0020\0002\006\0204\032\0020\0012\006\0205\032\0020\001¢\006\004\b6\0207J\020\0208\032\0020\004H\b¢\006\004\b8\020,J\025\0209\032\0020\0232\006\020\013\032\0020\n¢\006\004\b9\020:J\025\020<\032\0020\0232\006\020;\032\0020\004¢\006\004\b<\020=J\027\020?\032\0020\0232\006\020>\032\0020\fH\002¢\006\004\b?\020@J\r\020A\032\0020\023¢\006\004\bA\020\025J\017\020B\032\0020\006H\026¢\006\004\bB\020CJ\020\020D\032\0020\fH\b¢\006\004\bD\020EJ\031\020F\032\0020\f2\b\b\002\020\017\032\0020\004H\002¢\006\004\bF\020GJ\017\020H\032\0020\fH\002¢\006\004\bH\020EJ+\020I\032\004\030\0010\n*\b\030\0010 R\0020\0002\006\020\013\032\0020\n2\006\020%\032\0020\fH\002¢\006\004\bI\020JR\025\020\020\032\0020\0018Â\002X\004¢\006\006\032\004\bK\020\027R\024\020\002\032\0020\0018\006X\004¢\006\006\n\004\b\002\020LR\025\020\037\032\0020\0018Â\002X\004¢\006\006\032\004\bM\020\027R\024\020O\032\0020N8\006X\004¢\006\006\n\004\bO\020PR\024\020Q\032\0020N8\006X\004¢\006\006\n\004\bQ\020PR\024\020\005\032\0020\0048\006X\004¢\006\006\n\004\b\005\020RR\021\020S\032\0020\f8F¢\006\006\032\004\bS\020ER\024\020\003\032\0020\0018\006X\004¢\006\006\n\004\b\003\020LR\024\020\007\032\0020\0068\006X\004¢\006\006\n\004\b\007\020TR\036\020V\032\f\022\b\022\0060 R\0020\0000U8\006X\004¢\006\006\n\004\bV\020W¨\006["}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "", "corePoolSize", "maxPoolSize", "", "idleWorkerKeepAliveNs", "", "schedulerName", "<init>", "(IIJLjava/lang/String;)V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "addToGlobalQueue", "(Lkotlinx/coroutines/scheduling/Task;)Z", "state", "availableCpuPermits", "(J)I", "blockingTasks", "", "close", "()V", "createNewWorker", "()I", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "Lkotlinx/coroutines/scheduling/TaskContext;", "taskContext", "createTask", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;)Lkotlinx/coroutines/scheduling/Task;", "createdWorkers", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "currentWorker", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "decrementBlockingTasks", "decrementCreatedWorkers", "tailDispatch", "dispatch", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;Z)V", "command", "execute", "(Ljava/lang/Runnable;)V", "incrementBlockingTasks", "()J", "incrementCreatedWorkers", "worker", "parkedWorkersStackNextIndex", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)I", "parkedWorkersStackPop", "parkedWorkersStackPush", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)Z", "oldIndex", "newIndex", "parkedWorkersStackTopUpdate", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;II)V", "releaseCpuPermit", "runSafely", "(Lkotlinx/coroutines/scheduling/Task;)V", "timeout", "shutdown", "(J)V", "skipUnpark", "signalBlockingWork", "(Z)V", "signalCpuWork", "toString", "()Ljava/lang/String;", "tryAcquireCpuPermit", "()Z", "tryCreateWorker", "(J)Z", "tryUnpark", "submitToLocalQueue", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "getAvailableCpuPermits", "I", "getCreatedWorkers", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalBlockingQueue", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalCpuQueue", "J", "isTerminated", "Ljava/lang/String;", "Lkotlinx/coroutines/internal/ResizableAtomicArray;", "workers", "Lkotlinx/coroutines/internal/ResizableAtomicArray;", "Companion", "Worker", "WorkerState", "kotlinx-coroutines-core", "Ljava/util/concurrent/Executor;", "Ljava/io/Closeable;"}, k=1, mv={1, 6, 0}, xi=48)
public final class CoroutineScheduler
  implements Executor, Closeable
{
  private static final long BLOCKING_MASK = 4398044413952L;
  private static final int BLOCKING_SHIFT = 21;
  private static final int CLAIMED = 0;
  private static final long CPU_PERMITS_MASK = 9223367638808264704L;
  private static final int CPU_PERMITS_SHIFT = 42;
  private static final long CREATED_MASK = 2097151L;
  public static final Companion Companion = new Companion(null);
  public static final int MAX_SUPPORTED_POOL_SIZE = 2097150;
  public static final int MIN_SUPPORTED_POOL_SIZE = 1;
  public static final Symbol NOT_IN_STACK = new Symbol("NOT_IN_STACK");
  private static final int PARKED = -1;
  private static final long PARKED_INDEX_MASK = 2097151L;
  private static final long PARKED_VERSION_INC = 2097152L;
  private static final long PARKED_VERSION_MASK = -2097152L;
  private static final int TERMINATED = 1;
  private static final AtomicIntegerFieldUpdater _isTerminated$FU = AtomicIntegerFieldUpdater.newUpdater(CoroutineScheduler.class, "_isTerminated");
  static final AtomicLongFieldUpdater controlState$FU;
  private static final AtomicLongFieldUpdater parkedWorkersStack$FU = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "parkedWorkersStack");
  private volatile int _isTerminated;
  volatile long controlState;
  public final int corePoolSize;
  public final GlobalQueue globalBlockingQueue;
  public final GlobalQueue globalCpuQueue;
  public final long idleWorkerKeepAliveNs;
  public final int maxPoolSize;
  private volatile long parkedWorkersStack;
  public final String schedulerName;
  public final ResizableAtomicArray<Worker> workers;
  
  static
  {
    controlState$FU = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "controlState");
  }
  
  public CoroutineScheduler(int paramInt1, int paramInt2, long paramLong, String paramString)
  {
    this.corePoolSize = paramInt1;
    this.maxPoolSize = paramInt2;
    this.idleWorkerKeepAliveNs = paramLong;
    this.schedulerName = paramString;
    int j = 1;
    int i;
    if (paramInt1 >= 1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt2 >= paramInt1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt2 <= 2097150) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          if (paramLong > 0L) {
            paramInt2 = j;
          } else {
            paramInt2 = 0;
          }
          if (paramInt2 != 0)
          {
            this.globalCpuQueue = new GlobalQueue();
            this.globalBlockingQueue = new GlobalQueue();
            this.parkedWorkersStack = 0L;
            this.workers = new ResizableAtomicArray(paramInt1 + 1);
            this.controlState = (paramInt1 << 42);
            this._isTerminated = 0;
            return;
          }
          throw new IllegalArgumentException(("Idle worker keep alive time " + paramLong + " must be positive").toString());
        }
        throw new IllegalArgumentException(("Max pool size " + paramInt2 + " should not exceed maximal supported number of threads 2097150").toString());
      }
      throw new IllegalArgumentException(("Max pool size " + paramInt2 + " should be greater than or equals to core pool size " + paramInt1).toString());
    }
    throw new IllegalArgumentException(("Core pool size " + paramInt1 + " should be at least 1").toString());
  }
  
  private final boolean addToGlobalQueue(Task paramTask)
  {
    int j = paramTask.taskContext.getTaskMode();
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    boolean bool;
    if (i != 0) {
      bool = this.globalBlockingQueue.addLast(paramTask);
    } else {
      bool = this.globalCpuQueue.addLast(paramTask);
    }
    return bool;
  }
  
  private final int blockingTasks(long paramLong)
  {
    return (int)((0x3FFFFE00000 & paramLong) >> 21);
  }
  
  private final int createNewWorker()
  {
    synchronized (this.workers)
    {
      boolean bool = isTerminated();
      if (bool) {
        return -1;
      }
      long l = this.controlState;
      int i = (int)(l & 0x1FFFFF);
      int j = (int)((0x3FFFFE00000 & l) >> 21);
      j = RangesKt.coerceAtLeast(i - j, 0);
      int k = this.corePoolSize;
      if (j >= k) {
        return 0;
      }
      k = this.maxPoolSize;
      if (i >= k) {
        return 0;
      }
      k = (int)(this.controlState & 0x1FFFFF) + 1;
      if ((k > 0) && (this.workers.get(k) == null)) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localObject1 = new kotlinx/coroutines/scheduling/CoroutineScheduler$Worker;
        ((Worker)localObject1).<init>(this, k);
        this.workers.setSynchronized(k, localObject1);
        i = (int)(controlState$FU.incrementAndGet(this) & 0x1FFFFF);
        if (k == i) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          ((Worker)localObject1).start();
          return j + 1;
        }
        localObject1 = new java/lang/IllegalArgumentException;
        ((IllegalArgumentException)localObject1).<init>("Failed requirement.".toString());
        throw ((Throwable)localObject1);
      }
      Object localObject1 = new java/lang/IllegalArgumentException;
      ((IllegalArgumentException)localObject1).<init>("Failed requirement.".toString());
      throw ((Throwable)localObject1);
    }
  }
  
  private final int createdWorkers(long paramLong)
  {
    return (int)(0x1FFFFF & paramLong);
  }
  
  private final Worker currentWorker()
  {
    Object localObject1 = Thread.currentThread();
    boolean bool = localObject1 instanceof Worker;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (Worker)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {}
    while (!Intrinsics.areEqual(Worker.access$getThis$0$p((Worker)localObject1), this))
    {
      localObject1 = localObject2;
      break;
    }
    return (Worker)localObject1;
  }
  
  private final void decrementBlockingTasks()
  {
    controlState$FU.addAndGet(this, -2097152L);
  }
  
  private final int decrementCreatedWorkers()
  {
    return (int)(0x1FFFFF & controlState$FU.getAndDecrement(this));
  }
  
  private final int getAvailableCpuPermits()
  {
    return (int)((0x7FFFFC0000000000 & this.controlState) >> 42);
  }
  
  private final int getCreatedWorkers()
  {
    return (int)(this.controlState & 0x1FFFFF);
  }
  
  private final long incrementBlockingTasks()
  {
    return controlState$FU.addAndGet(this, 2097152L);
  }
  
  private final int incrementCreatedWorkers()
  {
    return (int)(0x1FFFFF & controlState$FU.incrementAndGet(this));
  }
  
  private final int parkedWorkersStackNextIndex(Worker paramWorker)
  {
    for (paramWorker = paramWorker.getNextParkedWorker();; paramWorker = paramWorker.getNextParkedWorker())
    {
      if (paramWorker == NOT_IN_STACK) {
        return -1;
      }
      if (paramWorker == null) {
        return 0;
      }
      paramWorker = (Worker)paramWorker;
      int i = paramWorker.getIndexInArray();
      if (i != 0) {
        return i;
      }
    }
  }
  
  private final Worker parkedWorkersStackPop()
  {
    for (;;)
    {
      long l = this.parkedWorkersStack;
      int i = (int)(0x1FFFFF & l);
      Worker localWorker = (Worker)this.workers.get(i);
      if (localWorker == null) {
        return null;
      }
      i = parkedWorkersStackNextIndex(localWorker);
      if ((i >= 0) && (parkedWorkersStack$FU.compareAndSet(this, l, 2097152L + l & 0xFFFFFFFFFFE00000 | i)))
      {
        localWorker.setNextParkedWorker(NOT_IN_STACK);
        return localWorker;
      }
    }
  }
  
  private final long releaseCpuPermit()
  {
    return controlState$FU.addAndGet(this, 4398046511104L);
  }
  
  private final void signalBlockingWork(boolean paramBoolean)
  {
    long l = controlState$FU.addAndGet(this, 2097152L);
    if (paramBoolean) {
      return;
    }
    if (tryUnpark()) {
      return;
    }
    if (tryCreateWorker(l)) {
      return;
    }
    tryUnpark();
  }
  
  private final Task submitToLocalQueue(Worker paramWorker, Task paramTask, boolean paramBoolean)
  {
    if (paramWorker == null) {
      return paramTask;
    }
    if (paramWorker.state == WorkerState.TERMINATED) {
      return paramTask;
    }
    if ((paramTask.taskContext.getTaskMode() == 0) && (paramWorker.state == WorkerState.BLOCKING)) {
      return paramTask;
    }
    paramWorker.mayHaveLocalTasks = true;
    return paramWorker.localQueue.add(paramTask, paramBoolean);
  }
  
  private final boolean tryAcquireCpuPermit()
  {
    for (;;)
    {
      long l = this.controlState;
      if ((int)((0x7FFFFC0000000000 & l) >> 42) == 0) {
        return false;
      }
      if (controlState$FU.compareAndSet(this, l, l - 4398046511104L)) {
        return true;
      }
    }
  }
  
  private final boolean tryCreateWorker(long paramLong)
  {
    int j = (int)(0x1FFFFF & paramLong);
    int i = (int)((0x3FFFFE00000 & paramLong) >> 21);
    if (RangesKt.coerceAtLeast(j - i, 0) < this.corePoolSize)
    {
      i = createNewWorker();
      if ((i == 1) && (this.corePoolSize > 1)) {
        createNewWorker();
      }
      if (i > 0) {
        return true;
      }
    }
    return false;
  }
  
  private final boolean tryUnpark()
  {
    for (;;)
    {
      Worker localWorker = parkedWorkersStackPop();
      if (localWorker == null) {
        return false;
      }
      if (Worker.workerCtl$FU.compareAndSet(localWorker, -1, 0))
      {
        LockSupport.unpark((Thread)localWorker);
        return true;
      }
    }
  }
  
  public final int availableCpuPermits(long paramLong)
  {
    return (int)((0x7FFFFC0000000000 & paramLong) >> 42);
  }
  
  public void close()
  {
    shutdown(10000L);
  }
  
  public final Task createTask(Runnable paramRunnable, TaskContext paramTaskContext)
  {
    long l = TasksKt.schedulerTimeSource.nanoTime();
    if ((paramRunnable instanceof Task))
    {
      ((Task)paramRunnable).submissionTime = l;
      ((Task)paramRunnable).taskContext = paramTaskContext;
      return (Task)paramRunnable;
    }
    return (Task)new TaskImpl(paramRunnable, l, paramTaskContext);
  }
  
  public final void dispatch(Runnable paramRunnable, TaskContext paramTaskContext, boolean paramBoolean)
  {
    Object localObject = AbstractTimeSourceKt.getTimeSource();
    if (localObject != null) {
      ((AbstractTimeSource)localObject).trackTask();
    }
    paramTaskContext = createTask(paramRunnable, paramTaskContext);
    paramRunnable = currentWorker();
    localObject = submitToLocalQueue(paramRunnable, paramTaskContext, paramBoolean);
    if ((localObject != null) && (!addToGlobalQueue((Task)localObject)))
    {
      paramRunnable = Intrinsics.stringPlus(this.schedulerName, " was terminated");
      Log5ECF72.a(paramRunnable);
      LogE84000.a(paramRunnable);
      Log229316.a(paramRunnable);
      throw new RejectedExecutionException(paramRunnable);
    }
    if ((paramBoolean) && (paramRunnable != null)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    if (paramTaskContext.taskContext.getTaskMode() == 0)
    {
      if (paramBoolean) {
        return;
      }
      signalCpuWork();
    }
    else
    {
      signalBlockingWork(paramBoolean);
    }
  }
  
  public void execute(Runnable paramRunnable)
  {
    dispatch$default(this, paramRunnable, null, false, 6, null);
  }
  
  public final boolean isTerminated()
  {
    return this._isTerminated;
  }
  
  public final boolean parkedWorkersStackPush(Worker paramWorker)
  {
    if (paramWorker.getNextParkedWorker() != NOT_IN_STACK) {
      return false;
    }
    for (;;)
    {
      long l = this.parkedWorkersStack;
      int k = (int)(0x1FFFFF & l);
      int j = paramWorker.getIndexInArray();
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (j != 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      paramWorker.setNextParkedWorker(this.workers.get(k));
      if (parkedWorkersStack$FU.compareAndSet(this, l, 2097152L + l & 0xFFFFFFFFFFE00000 | j)) {
        return true;
      }
    }
  }
  
  public final void parkedWorkersStackTopUpdate(Worker paramWorker, int paramInt1, int paramInt2)
  {
    for (;;)
    {
      long l = this.parkedWorkersStack;
      int i = (int)(0x1FFFFF & l);
      if (i == paramInt1) {
        if (paramInt2 == 0) {
          i = parkedWorkersStackNextIndex(paramWorker);
        } else {
          i = paramInt2;
        }
      }
      if ((i >= 0) && (parkedWorkersStack$FU.compareAndSet(this, l, 2097152L + l & 0xFFFFFFFFFFE00000 | i))) {
        return;
      }
    }
  }
  
  /* Error */
  public final void runSafely(Task paramTask)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 474	kotlinx/coroutines/scheduling/Task:run	()V
    //   4: invokestatic 424	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   7: astore_2
    //   8: aload_2
    //   9: astore_1
    //   10: aload_2
    //   11: ifnonnull +6 -> 17
    //   14: goto +7 -> 21
    //   17: aload_1
    //   18: invokevirtual 477	kotlinx/coroutines/AbstractTimeSource:unTrackTask	()V
    //   21: goto +32 -> 53
    //   24: astore_1
    //   25: invokestatic 310	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   28: astore_2
    //   29: aload_2
    //   30: invokevirtual 481	java/lang/Thread:getUncaughtExceptionHandler	()Ljava/lang/Thread$UncaughtExceptionHandler;
    //   33: aload_2
    //   34: aload_1
    //   35: invokeinterface 487 3 0
    //   40: invokestatic 424	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   43: astore_2
    //   44: aload_2
    //   45: astore_1
    //   46: aload_2
    //   47: ifnonnull -30 -> 17
    //   50: goto -29 -> 21
    //   53: return
    //   54: astore_2
    //   55: invokestatic 424	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   58: astore_1
    //   59: aload_1
    //   60: ifnonnull +6 -> 66
    //   63: goto +7 -> 70
    //   66: aload_1
    //   67: invokevirtual 477	kotlinx/coroutines/AbstractTimeSource:unTrackTask	()V
    //   70: aload_2
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	CoroutineScheduler
    //   0	72	1	paramTask	Task
    //   7	40	2	localObject1	Object
    //   54	17	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   0	4	24	finally
    //   25	40	54	finally
  }
  
  public final void shutdown(long paramLong)
  {
    ??? = _isTerminated$FU;
    int j = 0;
    if (!((AtomicIntegerFieldUpdater)???).compareAndSet(this, 0, 1)) {
      return;
    }
    Worker localWorker = currentWorker();
    synchronized (this.workers)
    {
      long l = this.controlState;
      int n = (int)(l & 0x1FFFFF);
      int i;
      int k;
      Object localObject2;
      if (1 <= n)
      {
        i = 1;
        for (;;)
        {
          k = i;
          int m = k + 1;
          ??? = this.workers.get(k);
          Intrinsics.checkNotNull(???);
          ??? = (Worker)???;
          if (??? != localWorker)
          {
            while (((Worker)???).isAlive())
            {
              LockSupport.unpark((Thread)???);
              ((Worker)???).join(paramLong);
            }
            localObject2 = ((Worker)???).state;
            if (DebugKt.getASSERTIONS_ENABLED())
            {
              if (localObject2 == WorkerState.TERMINATED) {
                i = 1;
              } else {
                i = 0;
              }
              if (i == 0) {
                throw new AssertionError();
              }
            }
            ((Worker)???).localQueue.offloadAllWorkTo(this.globalBlockingQueue);
          }
          i = m;
          if (k == n) {
            break;
          }
        }
      }
      this.globalBlockingQueue.close();
      this.globalCpuQueue.close();
      if (localWorker == null) {
        localObject2 = null;
      } else {
        localObject2 = localWorker.findTask(true);
      }
      ??? = localObject2;
      if (localObject2 == null)
      {
        localObject2 = (Task)this.globalCpuQueue.removeFirstOrNull();
        ??? = localObject2;
        if (localObject2 == null)
        {
          localObject2 = (Task)this.globalBlockingQueue.removeFirstOrNull();
          ??? = localObject2;
          if (localObject2 == null)
          {
            if (localWorker != null) {
              localWorker.tryReleaseCpu(WorkerState.TERMINATED);
            }
            if (DebugKt.getASSERTIONS_ENABLED())
            {
              k = (int)((0x7FFFFC0000000000 & this.controlState) >> 42);
              i = j;
              if (k == this.corePoolSize) {
                i = 1;
              }
              if (i == 0) {
                throw new AssertionError();
              }
            }
            this.parkedWorkersStack = 0L;
            this.controlState = 0L;
            return;
          }
        }
      }
      runSafely((Task)???);
    }
  }
  
  public final void signalCpuWork()
  {
    if (tryUnpark()) {
      return;
    }
    if (tryCreateWorker$default(this, 0L, 1, null)) {
      return;
    }
    tryUnpark();
  }
  
  public String toString()
  {
    int i1 = 0;
    int n = 0;
    int m = 0;
    int j = 0;
    int k = 0;
    Object localObject2 = new ArrayList();
    int i4 = this.workers.currentLength();
    int i = 1;
    while (i < i4)
    {
      int i2 = i + 1;
      localObject1 = (Worker)this.workers.get(i);
      if (localObject1 == null)
      {
        i = i2;
      }
      else
      {
        int i5 = ((Worker)localObject1).localQueue.getSize$kotlinx_coroutines_core();
        localObject1 = ((Worker)localObject1).state;
        switch (WhenMappings.$EnumSwitchMapping$0[localObject1.ordinal()])
        {
        default: 
          break;
        case 5: 
          k++;
          break;
        case 4: 
          int i3 = j + 1;
          j = i3;
          i = i2;
          if (i5 > 0)
          {
            ((Collection)localObject2).add(i5 + 'd');
            j = i3;
            i = i2;
          }
          break;
        case 3: 
          m++;
          ((Collection)localObject2).add(i5 + 'c');
          i = i2;
          break;
        case 2: 
          n++;
          ((Collection)localObject2).add(i5 + 'b');
          i = i2;
          break;
        case 1: 
          i1++;
          i = i2;
          continue;
          i = i2;
        }
      }
    }
    long l = this.controlState;
    Object localObject1 = new StringBuilder();
    StringBuilder localStringBuilder = ((StringBuilder)localObject1).append(this.schedulerName).append('@');
    String str = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append("[Pool Size {core = ").append(this.corePoolSize);
    localStringBuilder = localStringBuilder.append(", max = ").append(this.maxPoolSize);
    localStringBuilder = localStringBuilder.append("}, Worker States {CPU = ");
    localStringBuilder = localStringBuilder.append(m);
    localStringBuilder = localStringBuilder.append(", blocking = ");
    localStringBuilder = localStringBuilder.append(n);
    localStringBuilder = localStringBuilder.append(", parked = ");
    localStringBuilder = localStringBuilder.append(i1);
    localStringBuilder = localStringBuilder.append(", dormant = ");
    localStringBuilder = localStringBuilder.append(j);
    localStringBuilder = localStringBuilder.append(", terminated = ");
    localStringBuilder = localStringBuilder.append(k);
    localStringBuilder = localStringBuilder.append("}, running workers queues = ");
    localObject2 = localStringBuilder.append(localObject2);
    localObject2 = ((StringBuilder)localObject2).append(", global CPU queue size = ").append(this.globalCpuQueue.getSize());
    ((StringBuilder)localObject2).append(", global blocking queue size = ").append(this.globalBlockingQueue.getSize());
    localObject2 = ((StringBuilder)localObject1).append(", Control State {created workers= ").append((int)(0x1FFFFF & l));
    localObject2 = ((StringBuilder)localObject2).append(", blocking tasks = ").append((int)((0x3FFFFE00000 & l) >> 21));
    localObject2 = ((StringBuilder)localObject2).append(", CPUs acquired = ").append(this.corePoolSize - (int)((0x7FFFFC0000000000 & l) >> 42));
    ((StringBuilder)localObject2).append("}]");
    return ((StringBuilder)localObject1).toString();
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\b\n\002\b\007\n\002\030\002\n\002\b\006\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\006XT¢\006\002\n\000R\016\020\007\032\0020\006XT¢\006\002\n\000R\016\020\b\032\0020\004XT¢\006\002\n\000R\016\020\t\032\0020\006XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\016\020\013\032\0020\006XT¢\006\002\n\000R\016\020\f\032\0020\006XT¢\006\002\n\000R\020\020\r\032\0020\0168\006X\004¢\006\002\n\000R\016\020\017\032\0020\006XT¢\006\002\n\000R\016\020\020\032\0020\004XT¢\006\002\n\000R\016\020\021\032\0020\004XT¢\006\002\n\000R\016\020\022\032\0020\004XT¢\006\002\n\000R\016\020\023\032\0020\006XT¢\006\002\n\000¨\006\024"}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Companion;", "", "()V", "BLOCKING_MASK", "", "BLOCKING_SHIFT", "", "CLAIMED", "CPU_PERMITS_MASK", "CPU_PERMITS_SHIFT", "CREATED_MASK", "MAX_SUPPORTED_POOL_SIZE", "MIN_SUPPORTED_POOL_SIZE", "NOT_IN_STACK", "Lkotlinx/coroutines/internal/Symbol;", "PARKED", "PARKED_INDEX_MASK", "PARKED_VERSION_INC", "PARKED_VERSION_MASK", "TERMINATED", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Companion {}
  
  @Metadata(d1={"\000P\n\002\030\002\n\002\020\b\n\002\b\005\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\023\n\002\030\002\n\002\b\013\n\002\030\002\n\002\b\004\n\002\020\t\n\002\b\002\n\002\020\000\n\002\b\007\n\002\030\002\n\002\b\007\n\002\030\002\b\004\030\0002\0020GB\021\b\026\022\006\020\002\032\0020\001¢\006\004\b\003\020\004B\t\b\002¢\006\004\b\003\020\005J\027\020\b\032\0020\0072\006\020\006\032\0020\001H\002¢\006\004\b\b\020\tJ\027\020\n\032\0020\0072\006\020\006\032\0020\001H\002¢\006\004\b\n\020\tJ\027\020\r\032\0020\0072\006\020\f\032\0020\013H\002¢\006\004\b\r\020\016J\031\020\021\032\004\030\0010\0132\006\020\020\032\0020\017H\002¢\006\004\b\021\020\022J\027\020\023\032\004\030\0010\0132\006\020\020\032\0020\017¢\006\004\b\023\020\022J\027\020\025\032\0020\0072\006\020\024\032\0020\001H\002¢\006\004\b\025\020\tJ\017\020\026\032\0020\017H\002¢\006\004\b\026\020\027J\025\020\031\032\0020\0012\006\020\030\032\0020\001¢\006\004\b\031\020\032J\017\020\033\032\0020\007H\002¢\006\004\b\033\020\034J\021\020\035\032\004\030\0010\013H\002¢\006\004\b\035\020\036J\017\020\037\032\0020\007H\026¢\006\004\b\037\020\034J\017\020 \032\0020\007H\002¢\006\004\b \020\034J\017\020!\032\0020\017H\002¢\006\004\b!\020\027J\017\020\"\032\0020\007H\002¢\006\004\b\"\020\034J\025\020%\032\0020\0172\006\020$\032\0020#¢\006\004\b%\020&J\031\020(\032\004\030\0010\0132\006\020'\032\0020\017H\002¢\006\004\b(\020\022J\017\020)\032\0020\007H\002¢\006\004\b)\020\034R*\020*\032\0020\0012\006\020\002\032\0020\0018\006@FX\016¢\006\022\n\004\b*\020+\032\004\b,\020-\"\004\b.\020\tR\024\0200\032\0020/8\006X\004¢\006\006\n\004\b0\0201R\026\0202\032\0020\0178\006@\006X\016¢\006\006\n\004\b2\0203R\026\0205\032\002048\002@\002X\016¢\006\006\n\004\b5\0206R$\0208\032\004\030\001078\006@\006X\016¢\006\022\n\004\b8\0209\032\004\b:\020;\"\004\b<\020=R\026\020>\032\0020\0018\002@\002X\016¢\006\006\n\004\b>\020+R\022\020B\032\0020?8Æ\002¢\006\006\032\004\b@\020AR\026\020C\032\0020#8\006@\006X\016¢\006\006\n\004\bC\020DR\026\020E\032\002048\002@\002X\016¢\006\006\n\004\bE\0206¨\006F"}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "", "index", "<init>", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;I)V", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;)V", "taskMode", "", "afterTask", "(I)V", "beforeTask", "Lkotlinx/coroutines/scheduling/Task;", "task", "executeTask", "(Lkotlinx/coroutines/scheduling/Task;)V", "", "scanLocalQueue", "findAnyTask", "(Z)Lkotlinx/coroutines/scheduling/Task;", "findTask", "mode", "idleReset", "inStack", "()Z", "upperBound", "nextInt", "(I)I", "park", "()V", "pollGlobalQueues", "()Lkotlinx/coroutines/scheduling/Task;", "run", "runWorker", "tryAcquireCpuPermit", "tryPark", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "newState", "tryReleaseCpu", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;)Z", "blockingOnly", "trySteal", "tryTerminateWorker", "indexInArray", "I", "getIndexInArray", "()I", "setIndexInArray", "Lkotlinx/coroutines/scheduling/WorkQueue;", "localQueue", "Lkotlinx/coroutines/scheduling/WorkQueue;", "mayHaveLocalTasks", "Z", "", "minDelayUntilStealableTaskNs", "J", "", "nextParkedWorker", "Ljava/lang/Object;", "getNextParkedWorker", "()Ljava/lang/Object;", "setNextParkedWorker", "(Ljava/lang/Object;)V", "rngState", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "getScheduler", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "scheduler", "state", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "terminationDeadline", "kotlinx-coroutines-core", "Ljava/lang/Thread;"}, k=1, mv={1, 6, 0}, xi=48)
  public final class Worker
    extends Thread
  {
    static final AtomicIntegerFieldUpdater workerCtl$FU = AtomicIntegerFieldUpdater.newUpdater(Worker.class, "workerCtl");
    private volatile int indexInArray;
    public final WorkQueue localQueue;
    public boolean mayHaveLocalTasks;
    private long minDelayUntilStealableTaskNs;
    private volatile Object nextParkedWorker;
    private int rngState;
    public CoroutineScheduler.WorkerState state;
    private long terminationDeadline;
    volatile int workerCtl;
    
    private Worker()
    {
      setDaemon(true);
      this.localQueue = new WorkQueue();
      this.state = CoroutineScheduler.WorkerState.DORMANT;
      this.workerCtl = 0;
      this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
      this.rngState = Random.Default.nextInt();
    }
    
    public Worker()
    {
      this();
      int i;
      setIndexInArray(i);
    }
    
    private final void afterTask(int paramInt)
    {
      if (paramInt == 0) {
        return;
      }
      Object localObject = CoroutineScheduler.this;
      CoroutineScheduler.controlState$FU.addAndGet(localObject, -2097152L);
      localObject = this.state;
      if (localObject != CoroutineScheduler.WorkerState.TERMINATED)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          if (localObject == CoroutineScheduler.WorkerState.BLOCKING) {
            paramInt = 1;
          } else {
            paramInt = 0;
          }
          if (paramInt == 0) {
            throw new AssertionError();
          }
        }
        this.state = CoroutineScheduler.WorkerState.DORMANT;
      }
    }
    
    private final void beforeTask(int paramInt)
    {
      if (paramInt == 0) {
        return;
      }
      if (tryReleaseCpu(CoroutineScheduler.WorkerState.BLOCKING)) {
        CoroutineScheduler.this.signalCpuWork();
      }
    }
    
    private final void executeTask(Task paramTask)
    {
      int i = paramTask.taskContext.getTaskMode();
      idleReset(i);
      beforeTask(i);
      CoroutineScheduler.this.runSafely(paramTask);
      afterTask(i);
    }
    
    private final Task findAnyTask(boolean paramBoolean)
    {
      Task localTask;
      if (paramBoolean)
      {
        int i;
        if (nextInt(CoroutineScheduler.this.corePoolSize * 2) == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          localTask = pollGlobalQueues();
          if (localTask != null) {
            return localTask;
          }
        }
        localTask = this.localQueue.poll();
        if (localTask == null)
        {
          if (i == 0)
          {
            localTask = pollGlobalQueues();
            if (localTask != null) {
              return localTask;
            }
          }
        }
        else {
          return localTask;
        }
      }
      else
      {
        localTask = pollGlobalQueues();
        if (localTask != null) {
          return localTask;
        }
      }
      return trySteal(false);
      return localTask;
    }
    
    private final void idleReset(int paramInt)
    {
      this.terminationDeadline = 0L;
      if (this.state == CoroutineScheduler.WorkerState.PARKING)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          int i = 1;
          if (paramInt == 1) {
            paramInt = i;
          } else {
            paramInt = 0;
          }
          if (paramInt == 0) {
            throw new AssertionError();
          }
        }
        this.state = CoroutineScheduler.WorkerState.BLOCKING;
      }
    }
    
    private final boolean inStack()
    {
      boolean bool;
      if (this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private final void park()
    {
      if (this.terminationDeadline == 0L) {
        this.terminationDeadline = (System.nanoTime() + CoroutineScheduler.this.idleWorkerKeepAliveNs);
      }
      LockSupport.parkNanos(CoroutineScheduler.this.idleWorkerKeepAliveNs);
      if (System.nanoTime() - this.terminationDeadline >= 0L)
      {
        this.terminationDeadline = 0L;
        tryTerminateWorker();
      }
    }
    
    private final Task pollGlobalQueues()
    {
      if (nextInt(2) == 0)
      {
        localTask = (Task)CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
        if (localTask == null) {
          return (Task)CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
        }
        return localTask;
      }
      Task localTask = (Task)CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
      if (localTask == null) {
        return (Task)CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
      }
      return localTask;
    }
    
    private final void runWorker()
    {
      int i = 0;
      while ((!CoroutineScheduler.this.isTerminated()) && (this.state != CoroutineScheduler.WorkerState.TERMINATED))
      {
        Task localTask = findTask(this.mayHaveLocalTasks);
        if (localTask != null)
        {
          i = 0;
          this.minDelayUntilStealableTaskNs = 0L;
          executeTask(localTask);
        }
        else
        {
          this.mayHaveLocalTasks = false;
          if (this.minDelayUntilStealableTaskNs != 0L)
          {
            if (i == 0)
            {
              i = 1;
            }
            else
            {
              i = 0;
              tryReleaseCpu(CoroutineScheduler.WorkerState.PARKING);
              Thread.interrupted();
              LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
              this.minDelayUntilStealableTaskNs = 0L;
            }
          }
          else {
            tryPark();
          }
        }
      }
      tryReleaseCpu(CoroutineScheduler.WorkerState.TERMINATED);
    }
    
    private final boolean tryAcquireCpuPermit()
    {
      Object localObject = this.state;
      CoroutineScheduler.WorkerState localWorkerState = CoroutineScheduler.WorkerState.CPU_ACQUIRED;
      boolean bool = false;
      if (localObject == localWorkerState) {
        bool = true;
      } else {
        localObject = CoroutineScheduler.this;
      }
      label91:
      for (;;)
      {
        long l = ((CoroutineScheduler)localObject).controlState;
        int i;
        if ((int)((0x7FFFFC0000000000 & l) >> 42) == 0)
        {
          i = 0;
        }
        else
        {
          if (!CoroutineScheduler.controlState$FU.compareAndSet(localObject, l, l - 4398046511104L)) {
            break label91;
          }
          i = 1;
        }
        if (i != 0)
        {
          this.state = CoroutineScheduler.WorkerState.CPU_ACQUIRED;
          bool = true;
        }
        return bool;
      }
    }
    
    private final void tryPark()
    {
      if (!inStack())
      {
        CoroutineScheduler.this.parkedWorkersStackPush(this);
        return;
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (this.localQueue.getSize$kotlinx_coroutines_core() == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      this.workerCtl = -1;
      while ((inStack()) && (this.workerCtl == -1) && (!CoroutineScheduler.this.isTerminated()) && (this.state != CoroutineScheduler.WorkerState.TERMINATED))
      {
        tryReleaseCpu(CoroutineScheduler.WorkerState.PARKING);
        Thread.interrupted();
        park();
      }
    }
    
    private final Task trySteal(boolean paramBoolean)
    {
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (this.localQueue.getSize$kotlinx_coroutines_core() == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      int m = (int)(CoroutineScheduler.this.controlState & 0x1FFFFF);
      if (m < 2) {
        return null;
      }
      int i = nextInt(m);
      long l1 = Long.MAX_VALUE;
      CoroutineScheduler localCoroutineScheduler = CoroutineScheduler.this;
      int j = 0;
      long l2;
      for (;;)
      {
        l2 = 0L;
        if (j >= m) {
          break;
        }
        int k = i + 1;
        i = k;
        if (k > m) {
          i = 1;
        }
        Worker localWorker = (Worker)localCoroutineScheduler.workers.get(i);
        long l3;
        if ((localWorker != null) && (localWorker != this))
        {
          if (DebugKt.getASSERTIONS_ENABLED())
          {
            if (this.localQueue.getSize$kotlinx_coroutines_core() == 0) {
              k = 1;
            } else {
              k = 0;
            }
            if (k == 0) {
              throw new AssertionError();
            }
          }
          if (paramBoolean) {
            l2 = this.localQueue.tryStealBlockingFrom(localWorker.localQueue);
          } else {
            l2 = this.localQueue.tryStealFrom(localWorker.localQueue);
          }
          if (l2 == -1L) {
            return this.localQueue.poll();
          }
          l3 = l1;
          if (l2 > 0L) {
            l3 = Math.min(l1, l2);
          }
        }
        else
        {
          l3 = l1;
        }
        j++;
        l1 = l3;
      }
      if (l1 != Long.MAX_VALUE) {
        l2 = l1;
      }
      this.minDelayUntilStealableTaskNs = l2;
      return null;
    }
    
    private final void tryTerminateWorker()
    {
      ResizableAtomicArray localResizableAtomicArray = CoroutineScheduler.this.workers;
      Object localObject1 = CoroutineScheduler.this;
      try
      {
        boolean bool = ((CoroutineScheduler)localObject1).isTerminated();
        if (bool) {
          return;
        }
        int j = (int)(((CoroutineScheduler)localObject1).controlState & 0x1FFFFF);
        int i = ((CoroutineScheduler)localObject1).corePoolSize;
        if (j <= i) {
          return;
        }
        bool = workerCtl$FU.compareAndSet(this, -1, 1);
        if (!bool) {
          return;
        }
        j = getIndexInArray();
        setIndexInArray(0);
        ((CoroutineScheduler)localObject1).parkedWorkersStackTopUpdate(this, j, 0);
        i = (int)(0x1FFFFF & CoroutineScheduler.controlState$FU.getAndDecrement(localObject1));
        if (i != j)
        {
          Object localObject3 = ((CoroutineScheduler)localObject1).workers.get(i);
          Intrinsics.checkNotNull(localObject3);
          localObject3 = (Worker)localObject3;
          ((CoroutineScheduler)localObject1).workers.setSynchronized(j, localObject3);
          ((Worker)localObject3).setIndexInArray(j);
          ((CoroutineScheduler)localObject1).parkedWorkersStackTopUpdate((Worker)localObject3, i, j);
        }
        ((CoroutineScheduler)localObject1).workers.setSynchronized(i, null);
        localObject1 = Unit.INSTANCE;
        this.state = CoroutineScheduler.WorkerState.TERMINATED;
        return;
      }
      finally {}
    }
    
    public final Task findTask(boolean paramBoolean)
    {
      if (tryAcquireCpuPermit()) {
        return findAnyTask(paramBoolean);
      }
      Task localTask1;
      if (paramBoolean)
      {
        Task localTask2 = this.localQueue.poll();
        localTask1 = localTask2;
        if (localTask2 == null) {
          localTask1 = (Task)CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
        }
      }
      else
      {
        localTask1 = (Task)CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
      }
      if (localTask1 == null) {
        localTask1 = trySteal(true);
      }
      return localTask1;
    }
    
    public final int getIndexInArray()
    {
      return this.indexInArray;
    }
    
    public final Object getNextParkedWorker()
    {
      return this.nextParkedWorker;
    }
    
    public final CoroutineScheduler getScheduler()
    {
      return access$getThis$0$p(this);
    }
    
    public final int nextInt(int paramInt)
    {
      int i = this.rngState;
      i ^= i << 13;
      i ^= i >> 17;
      i ^= i << 5;
      this.rngState = i;
      int j = paramInt - 1;
      if ((j & paramInt) == 0) {
        return i & j;
      }
      return (0x7FFFFFFF & i) % paramInt;
    }
    
    public void run()
    {
      runWorker();
    }
    
    public final void setIndexInArray(int paramInt)
    {
      StringBuilder localStringBuilder = new StringBuilder().append(CoroutineScheduler.this.schedulerName).append("-worker-");
      String str;
      if (paramInt == 0)
      {
        str = "TERMINATED";
      }
      else
      {
        str = String.valueOf(paramInt);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
      }
      setName(str);
      this.indexInArray = paramInt;
    }
    
    public final void setNextParkedWorker(Object paramObject)
    {
      this.nextParkedWorker = paramObject;
    }
    
    public final boolean tryReleaseCpu(CoroutineScheduler.WorkerState paramWorkerState)
    {
      CoroutineScheduler.WorkerState localWorkerState = this.state;
      boolean bool;
      if (localWorkerState == CoroutineScheduler.WorkerState.CPU_ACQUIRED) {
        bool = true;
      } else {
        bool = false;
      }
      if (bool)
      {
        CoroutineScheduler localCoroutineScheduler = CoroutineScheduler.this;
        CoroutineScheduler.controlState$FU.addAndGet(localCoroutineScheduler, 4398046511104L);
      }
      if (localWorkerState != paramWorkerState) {
        this.state = paramWorkerState;
      }
      return bool;
    }
  }
  
  @Metadata(d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\007\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004j\002\b\005j\002\b\006j\002\b\007¨\006\b"}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "", "(Ljava/lang/String;I)V", "CPU_ACQUIRED", "BLOCKING", "PARKING", "DORMANT", "TERMINATED", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static enum WorkerState
  {
    private static final WorkerState[] $VALUES = $values();
    
    static
    {
      BLOCKING = new WorkerState("BLOCKING", 1);
      PARKING = new WorkerState("PARKING", 2);
      DORMANT = new WorkerState("DORMANT", 3);
      TERMINATED = new WorkerState("TERMINATED", 4);
    }
    
    private WorkerState() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/CoroutineScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */