package kotlinx.coroutines.internal;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DefaultExecutorKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

@Metadata(d1={"\000f\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\b\000\030\0002\0020\0012\0060\002j\002`\0032\0020\004B\025\022\006\020\005\032\0020\001\022\006\020\006\032\0020\007¢\006\002\020\bJ\024\020\017\032\0020\0202\n\020\021\032\0060\002j\002`\003H\002J\031\020\022\032\0020\0232\006\020\024\032\0020\025HAø\001\000¢\006\002\020\026J\034\020\027\032\0020\0232\006\020\030\032\0020\0312\n\020\021\032\0060\002j\002`\003H\026J#\020\032\032\0020\0232\n\020\021\032\0060\002j\002`\0032\f\020\027\032\b\022\004\022\0020\0230\033H\bJ\034\020\034\032\0020\0232\006\020\030\032\0020\0312\n\020\021\032\0060\002j\002`\003H\027J%\020\035\032\0020\0362\006\020\037\032\0020\0252\n\020\021\032\0060\002j\002`\0032\006\020\030\032\0020\031H\001J\020\020 \032\0020\0012\006\020\006\032\0020\007H\027J\b\020!\032\0020\023H\026J\037\020\"\032\0020\0232\006\020\037\032\0020\0252\f\020#\032\b\022\004\022\0020\0230$H\001J\b\020%\032\0020\020H\002R\016\020\005\032\0020\001X\004¢\006\002\n\000R\016\020\006\032\0020\007X\004¢\006\002\n\000R\030\020\t\032\f\022\b\022\0060\002j\002`\0030\nX\004¢\006\002\n\000R\016\020\013\032\0020\007X\016¢\006\002\n\000R\022\020\f\032\0060\rj\002`\016X\004¢\006\002\n\000\002\004\n\002\b\031¨\006&"}, d2={"Lkotlinx/coroutines/internal/LimitedDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "Lkotlinx/coroutines/Delay;", "dispatcher", "parallelism", "", "(Lkotlinx/coroutines/CoroutineDispatcher;I)V", "queue", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "runningWorkers", "workerAllocationLock", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "addAndTryDispatching", "", "block", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "dispatchInternal", "Lkotlin/Function0;", "dispatchYield", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "limitedParallelism", "run", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "tryAllocateWorker", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class LimitedDispatcher
  extends CoroutineDispatcher
  implements Runnable, Delay
{
  private final Delay $$delegate_0;
  private final CoroutineDispatcher dispatcher;
  private final int parallelism;
  private final LockFreeTaskQueue<Runnable> queue;
  private volatile int runningWorkers;
  private final Object workerAllocationLock;
  
  public LimitedDispatcher(CoroutineDispatcher paramCoroutineDispatcher, int paramInt)
  {
    this.dispatcher = paramCoroutineDispatcher;
    this.parallelism = paramInt;
    if ((paramCoroutineDispatcher instanceof Delay)) {
      paramCoroutineDispatcher = (Delay)paramCoroutineDispatcher;
    } else {
      paramCoroutineDispatcher = null;
    }
    Object localObject = paramCoroutineDispatcher;
    if (paramCoroutineDispatcher == null) {
      localObject = DefaultExecutorKt.getDefaultDelay();
    }
    this.$$delegate_0 = ((Delay)localObject);
    this.queue = new LockFreeTaskQueue(false);
    this.workerAllocationLock = new Object();
  }
  
  private final boolean addAndTryDispatching(Runnable paramRunnable)
  {
    this.queue.addLast(paramRunnable);
    boolean bool;
    if (this.runningWorkers >= this.parallelism) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final void dispatchInternal(Runnable paramRunnable, Function0<Unit> paramFunction0)
  {
    if (addAndTryDispatching(paramRunnable)) {
      return;
    }
    if (!tryAllocateWorker()) {
      return;
    }
    paramFunction0.invoke();
  }
  
  private final boolean tryAllocateWorker()
  {
    synchronized (this.workerAllocationLock)
    {
      int i = this.runningWorkers;
      int j = this.parallelism;
      if (i >= j) {
        return false;
      }
      this.runningWorkers += 1;
      return true;
    }
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated without replacement as an internal method never intended for public use")
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    return this.$$delegate_0.delay(paramLong, paramContinuation);
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    if ((!addAndTryDispatching(paramRunnable)) && (tryAllocateWorker())) {
      this.dispatcher.dispatch((CoroutineContext)this, (Runnable)this);
    }
  }
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    if ((!addAndTryDispatching(paramRunnable)) && (tryAllocateWorker())) {
      this.dispatcher.dispatchYield((CoroutineContext)this, (Runnable)this);
    }
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
  {
    return this.$$delegate_0.invokeOnTimeout(paramLong, paramRunnable, paramCoroutineContext);
  }
  
  public CoroutineDispatcher limitedParallelism(int paramInt)
  {
    LimitedDispatcherKt.checkParallelism(paramInt);
    if (paramInt >= this.parallelism) {
      return (CoroutineDispatcher)this;
    }
    return super.limitedParallelism(paramInt);
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: getfield 81	kotlinx/coroutines/internal/LimitedDispatcher:queue	Lkotlinx/coroutines/internal/LockFreeTaskQueue;
    //   6: invokevirtual 140	kotlinx/coroutines/internal/LockFreeTaskQueue:removeFirstOrNull	()Ljava/lang/Object;
    //   9: checkcast 6	java/lang/Runnable
    //   12: astore_2
    //   13: aload_2
    //   14: ifnull +65 -> 79
    //   17: aload_2
    //   18: invokeinterface 142 1 0
    //   23: goto +14 -> 37
    //   26: astore_2
    //   27: getstatic 148	kotlin/coroutines/EmptyCoroutineContext:INSTANCE	Lkotlin/coroutines/EmptyCoroutineContext;
    //   30: checkcast 121	kotlin/coroutines/CoroutineContext
    //   33: aload_2
    //   34: invokestatic 154	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   37: iinc 1 1
    //   40: iload_1
    //   41: bipush 16
    //   43: if_icmplt +33 -> 76
    //   46: aload_0
    //   47: getfield 64	kotlinx/coroutines/internal/LimitedDispatcher:dispatcher	Lkotlinx/coroutines/CoroutineDispatcher;
    //   50: aload_0
    //   51: checkcast 121	kotlin/coroutines/CoroutineContext
    //   54: invokevirtual 158	kotlinx/coroutines/CoroutineDispatcher:isDispatchNeeded	(Lkotlin/coroutines/CoroutineContext;)Z
    //   57: ifeq +19 -> 76
    //   60: aload_0
    //   61: getfield 64	kotlinx/coroutines/internal/LimitedDispatcher:dispatcher	Lkotlinx/coroutines/CoroutineDispatcher;
    //   64: aload_0
    //   65: checkcast 121	kotlin/coroutines/CoroutineContext
    //   68: aload_0
    //   69: checkcast 6	java/lang/Runnable
    //   72: invokevirtual 123	kotlinx/coroutines/CoroutineDispatcher:dispatch	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V
    //   75: return
    //   76: goto -74 -> 2
    //   79: aload_0
    //   80: getfield 86	kotlinx/coroutines/internal/LimitedDispatcher:workerAllocationLock	Ljava/lang/Object;
    //   83: astore_2
    //   84: aload_2
    //   85: monitorenter
    //   86: aload_0
    //   87: aload_0
    //   88: getfield 94	kotlinx/coroutines/internal/LimitedDispatcher:runningWorkers	I
    //   91: iconst_1
    //   92: isub
    //   93: putfield 94	kotlinx/coroutines/internal/LimitedDispatcher:runningWorkers	I
    //   96: aload_0
    //   97: getfield 81	kotlinx/coroutines/internal/LimitedDispatcher:queue	Lkotlinx/coroutines/internal/LockFreeTaskQueue;
    //   100: invokevirtual 162	kotlinx/coroutines/internal/LockFreeTaskQueue:getSize	()I
    //   103: istore_1
    //   104: iload_1
    //   105: ifne +6 -> 111
    //   108: aload_2
    //   109: monitorexit
    //   110: return
    //   111: aload_0
    //   112: aload_0
    //   113: getfield 94	kotlinx/coroutines/internal/LimitedDispatcher:runningWorkers	I
    //   116: iconst_1
    //   117: iadd
    //   118: putfield 94	kotlinx/coroutines/internal/LimitedDispatcher:runningWorkers	I
    //   121: iconst_0
    //   122: istore_1
    //   123: getstatic 167	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   126: astore_3
    //   127: aload_2
    //   128: monitorexit
    //   129: goto -127 -> 2
    //   132: astore_3
    //   133: aload_2
    //   134: monitorexit
    //   135: aload_3
    //   136: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	137	0	this	LimitedDispatcher
    //   1	122	1	i	int
    //   12	6	2	localRunnable	Runnable
    //   26	8	2	localThrowable	Throwable
    //   126	1	3	localUnit	Unit
    //   132	4	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   17	23	26	finally
    //   86	104	132	finally
    //   111	121	132	finally
    //   123	127	132	finally
  }
  
  public void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    this.$$delegate_0.scheduleResumeAfterDelay(paramLong, paramCancellableContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LimitedDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */