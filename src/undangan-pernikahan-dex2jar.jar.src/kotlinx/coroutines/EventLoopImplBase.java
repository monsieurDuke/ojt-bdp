package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000Z\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\002\n\002\020\t\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\023\n\002\030\002\n\002\030\002\b \030\0002\002092\0020::\0044567B\007¢\006\004\b\001\020\002J\017\020\004\032\0020\003H\002¢\006\004\b\004\020\002J\027\020\007\032\n\030\0010\005j\004\030\001`\006H\002¢\006\004\b\007\020\bJ!\020\f\032\0020\0032\006\020\n\032\0020\t2\n\020\013\032\0060\005j\002`\006¢\006\004\b\f\020\rJ\033\020\017\032\0020\0032\n\020\016\032\0060\005j\002`\006H\026¢\006\004\b\017\020\020J\033\020\022\032\0020\0212\n\020\016\032\0060\005j\002`\006H\002¢\006\004\b\022\020\023J\017\020\025\032\0020\024H\026¢\006\004\b\025\020\026J\017\020\027\032\0020\003H\002¢\006\004\b\027\020\002J\017\020\030\032\0020\003H\004¢\006\004\b\030\020\002J\035\020\034\032\0020\0032\006\020\031\032\0020\0242\006\020\033\032\0020\032¢\006\004\b\034\020\035J\037\020\037\032\0020\0362\006\020\031\032\0020\0242\006\020\033\032\0020\032H\002¢\006\004\b\037\020 J#\020#\032\0020\"2\006\020!\032\0020\0242\n\020\013\032\0060\005j\002`\006H\004¢\006\004\b#\020$J%\020'\032\0020\0032\006\020!\032\0020\0242\f\020&\032\b\022\004\022\0020\0030%H\026¢\006\004\b'\020(J\027\020)\032\0020\0212\006\020\016\032\0020\032H\002¢\006\004\b)\020*J\017\020+\032\0020\003H\026¢\006\004\b+\020\002R$\020-\032\0020\0212\006\020,\032\0020\0218B@BX\016¢\006\f\032\004\b-\020.\"\004\b/\0200R\024\0201\032\0020\0218TX\004¢\006\006\032\004\b1\020.R\024\0203\032\0020\0248TX\004¢\006\006\032\004\b2\020\026¨\0068"}, d2={"Lkotlinx/coroutines/EventLoopImplBase;", "<init>", "()V", "", "closeQueue", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dequeue", "()Ljava/lang/Runnable;", "Lkotlin/coroutines/CoroutineContext;", "context", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "task", "enqueue", "(Ljava/lang/Runnable;)V", "", "enqueueImpl", "(Ljava/lang/Runnable;)Z", "", "processNextEvent", "()J", "rescheduleAllDelayed", "resetAll", "now", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "delayedTask", "schedule", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V", "", "scheduleImpl", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)I", "timeMillis", "Lkotlinx/coroutines/DisposableHandle;", "scheduleInvokeOnTimeout", "(JLjava/lang/Runnable;)Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/CancellableContinuation;", "continuation", "scheduleResumeAfterDelay", "(JLkotlinx/coroutines/CancellableContinuation;)V", "shouldUnpark", "(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)Z", "shutdown", "value", "isCompleted", "()Z", "setCompleted", "(Z)V", "isEmpty", "getNextTime", "nextTime", "DelayedResumeTask", "DelayedRunnableTask", "DelayedTask", "DelayedTaskQueue", "kotlinx-coroutines-core", "Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/Delay;"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class EventLoopImplBase
  extends EventLoopImplPlatform
  implements Delay
{
  private static final AtomicReferenceFieldUpdater _delayed$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_delayed");
  private static final AtomicReferenceFieldUpdater _queue$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_queue");
  private volatile Object _delayed = null;
  private volatile int _isCompleted = 0;
  private volatile Object _queue = null;
  
  private final void closeQueue()
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!isCompleted())) {
      throw new AssertionError();
    }
    for (;;)
    {
      Object localObject = this._queue;
      if (localObject == null)
      {
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, null, EventLoop_commonKt.access$getCLOSED_EMPTY$p())) {}
      }
      else
      {
        if ((localObject instanceof LockFreeTaskQueueCore))
        {
          ((LockFreeTaskQueueCore)localObject).close();
          return;
        }
        if (localObject == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
          return;
        }
        LockFreeTaskQueueCore localLockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
        if (localObject == null) {
          break;
        }
        localLockFreeTaskQueueCore.addLast((Runnable)localObject);
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, localObject, localLockFreeTaskQueueCore)) {
          return;
        }
      }
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }");
  }
  
  private final Runnable dequeue()
  {
    for (;;)
    {
      Object localObject1 = this._queue;
      if (localObject1 == null) {
        return null;
      }
      if ((localObject1 instanceof LockFreeTaskQueueCore))
      {
        if (localObject1 != null)
        {
          Object localObject2 = ((LockFreeTaskQueueCore)localObject1).removeFirstOrNull();
          if (localObject2 != LockFreeTaskQueueCore.REMOVE_FROZEN) {
            return (Runnable)localObject2;
          }
          AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, localObject1, ((LockFreeTaskQueueCore)localObject1).next());
        }
        else
        {
          throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeTaskQueueCore<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }>{ kotlinx.coroutines.EventLoop_commonKt.Queue<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }> }");
        }
      }
      else
      {
        if (localObject1 == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
          return null;
        }
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, localObject1, null))
        {
          if (localObject1 != null) {
            return (Runnable)localObject1;
          }
          throw new NullPointerException("null cannot be cast to non-null type java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }");
        }
      }
    }
  }
  
  private final boolean enqueueImpl(Runnable paramRunnable)
  {
    for (;;)
    {
      Object localObject = this._queue;
      if (isCompleted()) {
        return false;
      }
      if (localObject == null)
      {
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, null, paramRunnable)) {
          return true;
        }
      }
      else if ((localObject instanceof LockFreeTaskQueueCore))
      {
        if (localObject != null) {
          switch (((LockFreeTaskQueueCore)localObject).addLast(paramRunnable))
          {
          default: 
            break;
          case 2: 
            return false;
          case 1: 
            AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, localObject, ((LockFreeTaskQueueCore)localObject).next());
            break;
          case 0: 
            return true;
          }
        } else {
          throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeTaskQueueCore<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }>{ kotlinx.coroutines.EventLoop_commonKt.Queue<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }> }");
        }
      }
      else
      {
        if (localObject == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
          return false;
        }
        LockFreeTaskQueueCore localLockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
        if (localObject == null) {
          break;
        }
        localLockFreeTaskQueueCore.addLast((Runnable)localObject);
        localLockFreeTaskQueueCore.addLast(paramRunnable);
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, localObject, localLockFreeTaskQueueCore)) {
          return true;
        }
      }
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }");
  }
  
  private final boolean isCompleted()
  {
    return this._isCompleted;
  }
  
  private final void rescheduleAllDelayed()
  {
    Object localObject = AbstractTimeSourceKt.getTimeSource();
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = Long.valueOf(((AbstractTimeSource)localObject).nanoTime());
    }
    long l;
    if (localObject == null) {
      l = System.nanoTime();
    } else {
      l = ((Long)localObject).longValue();
    }
    for (;;)
    {
      localObject = (DelayedTaskQueue)this._delayed;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = (DelayedTask)((DelayedTaskQueue)localObject).removeFirstOrNull();
      }
      if (localObject == null) {
        return;
      }
      reschedule(l, (DelayedTask)localObject);
    }
  }
  
  private final int scheduleImpl(long paramLong, DelayedTask paramDelayedTask)
  {
    if (isCompleted()) {
      return 1;
    }
    DelayedTaskQueue localDelayedTaskQueue = (DelayedTaskQueue)this._delayed;
    Object localObject = localDelayedTaskQueue;
    if (localDelayedTaskQueue == null)
    {
      localObject = (EventLoopImplBase)this;
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_delayed$FU, localObject, null, new DelayedTaskQueue(paramLong));
      localObject = ((EventLoopImplBase)localObject)._delayed;
      Intrinsics.checkNotNull(localObject);
      localObject = (DelayedTaskQueue)localObject;
    }
    return paramDelayedTask.scheduleTask(paramLong, (DelayedTaskQueue)localObject, this);
  }
  
  private final void setCompleted(boolean paramBoolean)
  {
    this._isCompleted = paramBoolean;
  }
  
  private final boolean shouldUnpark(DelayedTask paramDelayedTask)
  {
    Object localObject = (DelayedTaskQueue)this._delayed;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (DelayedTask)((DelayedTaskQueue)localObject).peek();
    }
    boolean bool;
    if (localObject == paramDelayedTask) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated without replacement as an internal method never intended for public use")
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    return Delay.DefaultImpls.delay((Delay)this, paramLong, paramContinuation);
  }
  
  public final void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    enqueue(paramRunnable);
  }
  
  public void enqueue(Runnable paramRunnable)
  {
    if (enqueueImpl(paramRunnable)) {
      unpark();
    } else {
      DefaultExecutor.INSTANCE.enqueue(paramRunnable);
    }
  }
  
  protected long getNextTime()
  {
    if (super.getNextTime() == 0L) {
      return 0L;
    }
    Object localObject1 = this._queue;
    if (localObject1 != null)
    {
      if (!(localObject1 instanceof LockFreeTaskQueueCore)) {
        break label147;
      }
      if (!((LockFreeTaskQueueCore)localObject1).isEmpty()) {
        return 0L;
      }
    }
    localObject1 = (DelayedTaskQueue)this._delayed;
    Object localObject2 = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = (DelayedTask)((DelayedTaskQueue)localObject1).peek();
    }
    if (localObject1 == null) {
      return Long.MAX_VALUE;
    }
    long l2 = ((DelayedTask)localObject1).nanoTime;
    localObject1 = AbstractTimeSourceKt.getTimeSource();
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = Long.valueOf(((AbstractTimeSource)localObject1).nanoTime());
    }
    long l1;
    if (localObject1 == null) {
      l1 = System.nanoTime();
    } else {
      l1 = ((Long)localObject1).longValue();
    }
    return RangesKt.coerceAtLeast(l2 - l1, 0L);
    label147:
    if (localObject1 == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
      return Long.MAX_VALUE;
    }
    return 0L;
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
  {
    return Delay.DefaultImpls.invokeOnTimeout((Delay)this, paramLong, paramRunnable, paramCoroutineContext);
  }
  
  protected boolean isEmpty()
  {
    boolean bool2 = isUnconfinedQueueEmpty();
    boolean bool1 = false;
    if (!bool2) {
      return false;
    }
    Object localObject = (DelayedTaskQueue)this._delayed;
    if ((localObject != null) && (!((DelayedTaskQueue)localObject).isEmpty())) {
      return false;
    }
    localObject = this._queue;
    if (localObject == null) {
      bool1 = true;
    } else if ((localObject instanceof LockFreeTaskQueueCore)) {
      bool1 = ((LockFreeTaskQueueCore)localObject).isEmpty();
    } else if (localObject == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
      bool1 = true;
    }
    return bool1;
  }
  
  public long processNextEvent()
  {
    if (processUnconfinedEvent()) {
      return 0L;
    }
    DelayedTaskQueue localDelayedTaskQueue = (DelayedTaskQueue)this._delayed;
    if ((localDelayedTaskQueue != null) && (!localDelayedTaskQueue.isEmpty()))
    {
      Object localObject1 = AbstractTimeSourceKt.getTimeSource();
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = Long.valueOf(((AbstractTimeSource)localObject1).nanoTime());
      }
      long l;
      if (localObject1 == null) {
        l = System.nanoTime();
      } else {
        l = ((Long)localObject1).longValue();
      }
      synchronized ((ThreadSafeHeap)localDelayedTaskQueue)
      {
        do
        {
          localObject1 = ???.firstImpl();
          if (localObject1 == null)
          {
            localObject1 = null;
          }
          else
          {
            localObject1 = (DelayedTask)localObject1;
            boolean bool;
            if (((DelayedTask)localObject1).timeToExecute(l)) {
              bool = enqueueImpl((Runnable)localObject1);
            } else {
              bool = false;
            }
            if (bool)
            {
              localObject1 = ???.removeAtImpl(0);
            }
            else
            {
              localObject1 = (ThreadSafeHeapNode)null;
              localObject1 = null;
            }
          }
        } while ((DelayedTask)localObject1 != null);
      }
    }
    Runnable localRunnable = dequeue();
    if (localRunnable != null)
    {
      localRunnable.run();
      return 0L;
    }
    return getNextTime();
  }
  
  protected final void resetAll()
  {
    this._queue = null;
    this._delayed = null;
  }
  
  public final void schedule(long paramLong, DelayedTask paramDelayedTask)
  {
    switch (scheduleImpl(paramLong, paramDelayedTask))
    {
    default: 
      throw new IllegalStateException("unexpected result".toString());
    case 2: 
      break;
    case 1: 
      reschedule(paramLong, paramDelayedTask);
      break;
    case 0: 
      if (shouldUnpark(paramDelayedTask)) {
        unpark();
      }
      break;
    }
  }
  
  protected final DisposableHandle scheduleInvokeOnTimeout(long paramLong, Runnable paramRunnable)
  {
    long l = EventLoop_commonKt.delayToNanos(paramLong);
    if (l < 4611686018427387903L)
    {
      Object localObject = AbstractTimeSourceKt.getTimeSource();
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = Long.valueOf(((AbstractTimeSource)localObject).nanoTime());
      }
      if (localObject == null) {
        paramLong = System.nanoTime();
      } else {
        paramLong = ((Long)localObject).longValue();
      }
      paramRunnable = new DelayedRunnableTask(paramLong + l, paramRunnable);
      schedule(paramLong, (DelayedTask)paramRunnable);
      paramRunnable = (DisposableHandle)paramRunnable;
    }
    else
    {
      paramRunnable = (DisposableHandle)NonDisposableHandle.INSTANCE;
    }
    return paramRunnable;
  }
  
  public void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    long l = EventLoop_commonKt.delayToNanos(paramLong);
    if (l < 4611686018427387903L)
    {
      Object localObject = AbstractTimeSourceKt.getTimeSource();
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = Long.valueOf(((AbstractTimeSource)localObject).nanoTime());
      }
      if (localObject == null) {
        paramLong = System.nanoTime();
      } else {
        paramLong = ((Long)localObject).longValue();
      }
      localObject = new DelayedResumeTask(paramLong + l, paramCancellableContinuation);
      CancellableContinuationKt.disposeOnCancellation(paramCancellableContinuation, (DisposableHandle)localObject);
      schedule(paramLong, (DelayedTask)localObject);
    }
  }
  
  public void shutdown()
  {
    ThreadLocalEventLoop.INSTANCE.resetEventLoop$kotlinx_coroutines_core();
    setCompleted(true);
    closeQueue();
    while (processNextEvent() <= 0L) {}
    rescheduleAllDelayed();
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\020\002\n\002\b\003\n\002\020\016\n\000\b\004\030\0002\0020\001B\033\022\006\020\002\032\0020\003\022\f\020\004\032\b\022\004\022\0020\0060\005¢\006\002\020\007J\b\020\b\032\0020\006H\026J\b\020\t\032\0020\nH\026R\024\020\004\032\b\022\004\022\0020\0060\005X\004¢\006\002\n\000¨\006\013"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedResumeTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/EventLoopImplBase;JLkotlinx/coroutines/CancellableContinuation;)V", "run", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class DelayedResumeTask
    extends EventLoopImplBase.DelayedTask
  {
    private final CancellableContinuation<Unit> cont;
    
    public DelayedResumeTask(CancellableContinuation<? super Unit> paramCancellableContinuation)
    {
      super();
      CancellableContinuation localCancellableContinuation;
      this.cont = localCancellableContinuation;
    }
    
    public void run()
    {
      this.cont.resumeUndispatched((CoroutineDispatcher)EventLoopImplBase.this, Unit.INSTANCE);
    }
    
    public String toString()
    {
      String str = Intrinsics.stringPlus(super.toString(), this.cont);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
  }
  
  @Metadata(d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\031\022\006\020\002\032\0020\003\022\n\020\004\032\0060\005j\002`\006¢\006\002\020\007J\b\020\b\032\0020\tH\026J\b\020\n\032\0020\013H\026R\022\020\004\032\0060\005j\002`\006X\004¢\006\002\n\000¨\006\f"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedRunnableTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "(JLjava/lang/Runnable;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class DelayedRunnableTask
    extends EventLoopImplBase.DelayedTask
  {
    private final Runnable block;
    
    public DelayedRunnableTask(long paramLong, Runnable paramRunnable)
    {
      super();
      this.block = paramRunnable;
    }
    
    public void run()
    {
      this.block.run();
    }
    
    public String toString()
    {
      String str = Intrinsics.stringPlus(super.toString(), this.block);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
  }
  
  @Metadata(d1={"\000X\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\017\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\b\006\n\002\020\b\n\002\b\007\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\016\n\000\b \030\0002\0060\001j\002`\0022\b\022\004\022\0020\0000\0032\0020\0042\0020\005B\r\022\006\020\006\032\0020\007¢\006\002\020\bJ\021\020\030\032\0020\0232\006\020\031\032\0020\000H\002J\006\020\032\032\0020\033J\036\020\034\032\0020\0232\006\020\035\032\0020\0072\006\020\036\032\0020\0372\006\020 \032\0020!J\016\020\"\032\0020#2\006\020\035\032\0020\007J\b\020$\032\0020%H\026R\020\020\t\032\004\030\0010\nX\016¢\006\002\n\000R0\020\r\032\b\022\002\b\003\030\0010\f2\f\020\013\032\b\022\002\b\003\030\0010\f8V@VX\016¢\006\f\032\004\b\016\020\017\"\004\b\020\020\021R\032\020\022\032\0020\023X\016¢\006\016\n\000\032\004\b\024\020\025\"\004\b\026\020\027R\022\020\006\032\0020\0078\006@\006X\016¢\006\002\n\000¨\006&"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "nanoTime", "", "(J)V", "_heap", "", "value", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "heap", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "dispose", "", "scheduleTask", "now", "delayed", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "eventLoop", "Lkotlinx/coroutines/EventLoopImplBase;", "timeToExecute", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static abstract class DelayedTask
    implements Runnable, Comparable<DelayedTask>, DisposableHandle, ThreadSafeHeapNode
  {
    private Object _heap;
    private int index;
    public long nanoTime;
    
    public DelayedTask(long paramLong)
    {
      this.nanoTime = paramLong;
      this.index = -1;
    }
    
    public int compareTo(DelayedTask paramDelayedTask)
    {
      long l = this.nanoTime - paramDelayedTask.nanoTime;
      int i;
      if (l > 0L) {
        i = 1;
      } else if (l < 0L) {
        i = -1;
      } else {
        i = 0;
      }
      return i;
    }
    
    public final void dispose()
    {
      try
      {
        Object localObject1 = this._heap;
        Symbol localSymbol = EventLoop_commonKt.access$getDISPOSED_TASK$p();
        if (localObject1 == localSymbol) {
          return;
        }
        if ((localObject1 instanceof EventLoopImplBase.DelayedTaskQueue)) {
          localObject1 = (EventLoopImplBase.DelayedTaskQueue)localObject1;
        } else {
          localObject1 = null;
        }
        if (localObject1 != null) {
          for (;;)
          {
            ((EventLoopImplBase.DelayedTaskQueue)localObject1).remove((ThreadSafeHeapNode)this);
          }
        }
        this._heap = EventLoop_commonKt.access$getDISPOSED_TASK$p();
        return;
      }
      finally {}
    }
    
    public ThreadSafeHeap<?> getHeap()
    {
      Object localObject = this._heap;
      if ((localObject instanceof ThreadSafeHeap)) {
        localObject = (ThreadSafeHeap)localObject;
      } else {
        localObject = null;
      }
      return (ThreadSafeHeap<?>)localObject;
    }
    
    public int getIndex()
    {
      return this.index;
    }
    
    /* Error */
    public final int scheduleTask(long paramLong, EventLoopImplBase.DelayedTaskQueue paramDelayedTaskQueue, EventLoopImplBase paramEventLoopImplBase)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 78	kotlinx/coroutines/EventLoopImplBase$DelayedTask:_heap	Ljava/lang/Object;
      //   6: astore 8
      //   8: invokestatic 84	kotlinx/coroutines/EventLoop_commonKt:access$getDISPOSED_TASK$p	()Lkotlinx/coroutines/internal/Symbol;
      //   11: astore 9
      //   13: aload 8
      //   15: aload 9
      //   17: if_acmpne +7 -> 24
      //   20: aload_0
      //   21: monitorexit
      //   22: iconst_2
      //   23: ireturn
      //   24: aload_3
      //   25: checkcast 93	kotlinx/coroutines/internal/ThreadSafeHeap
      //   28: astore 8
      //   30: aload 8
      //   32: monitorenter
      //   33: aload 8
      //   35: invokevirtual 99	kotlinx/coroutines/internal/ThreadSafeHeap:firstImpl	()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      //   38: checkcast 2	kotlinx/coroutines/EventLoopImplBase$DelayedTask
      //   41: astore 9
      //   43: aload 4
      //   45: invokestatic 103	kotlinx/coroutines/EventLoopImplBase:access$isCompleted	(Lkotlinx/coroutines/EventLoopImplBase;)Z
      //   48: istore 7
      //   50: iload 7
      //   52: ifeq +10 -> 62
      //   55: aload 8
      //   57: monitorexit
      //   58: aload_0
      //   59: monitorexit
      //   60: iconst_1
      //   61: ireturn
      //   62: aload 9
      //   64: ifnonnull +11 -> 75
      //   67: aload_3
      //   68: lload_1
      //   69: putfield 106	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   72: goto +41 -> 113
      //   75: aload 9
      //   77: getfield 69	kotlinx/coroutines/EventLoopImplBase$DelayedTask:nanoTime	J
      //   80: lstore 5
      //   82: lload 5
      //   84: lload_1
      //   85: lsub
      //   86: lconst_0
      //   87: lcmp
      //   88: iflt +6 -> 94
      //   91: goto +6 -> 97
      //   94: lload 5
      //   96: lstore_1
      //   97: lload_1
      //   98: aload_3
      //   99: getfield 106	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   102: lsub
      //   103: lconst_0
      //   104: lcmp
      //   105: ifle +8 -> 113
      //   108: aload_3
      //   109: lload_1
      //   110: putfield 106	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   113: aload_0
      //   114: getfield 69	kotlinx/coroutines/EventLoopImplBase$DelayedTask:nanoTime	J
      //   117: aload_3
      //   118: getfield 106	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   121: lsub
      //   122: lconst_0
      //   123: lcmp
      //   124: ifge +11 -> 135
      //   127: aload_0
      //   128: aload_3
      //   129: getfield 106	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   132: putfield 69	kotlinx/coroutines/EventLoopImplBase$DelayedTask:nanoTime	J
      //   135: aload 8
      //   137: aload_0
      //   138: checkcast 13	kotlinx/coroutines/internal/ThreadSafeHeapNode
      //   141: invokevirtual 110	kotlinx/coroutines/internal/ThreadSafeHeap:addImpl	(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V
      //   144: aload 8
      //   146: monitorexit
      //   147: aload_0
      //   148: monitorexit
      //   149: iconst_0
      //   150: ireturn
      //   151: astore_3
      //   152: aload 8
      //   154: monitorexit
      //   155: aload_3
      //   156: athrow
      //   157: astore_3
      //   158: aload_0
      //   159: monitorexit
      //   160: aload_3
      //   161: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	162	0	this	DelayedTask
      //   0	162	1	paramLong	long
      //   0	162	3	paramDelayedTaskQueue	EventLoopImplBase.DelayedTaskQueue
      //   0	162	4	paramEventLoopImplBase	EventLoopImplBase
      //   80	15	5	l	long
      //   48	3	7	bool	boolean
      //   11	65	9	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   33	50	151	finally
      //   67	72	151	finally
      //   75	82	151	finally
      //   97	113	151	finally
      //   113	135	151	finally
      //   135	144	151	finally
      //   2	13	157	finally
      //   24	33	157	finally
      //   55	58	157	finally
      //   144	147	157	finally
      //   152	157	157	finally
    }
    
    public void setHeap(ThreadSafeHeap<?> paramThreadSafeHeap)
    {
      int i;
      if (this._heap != EventLoop_commonKt.access$getDISPOSED_TASK$p()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this._heap = paramThreadSafeHeap;
        return;
      }
      throw new IllegalArgumentException("Failed requirement.".toString());
    }
    
    public void setIndex(int paramInt)
    {
      this.index = paramInt;
    }
    
    public final boolean timeToExecute(long paramLong)
    {
      boolean bool;
      if (paramLong - this.nanoTime >= 0L) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public String toString()
    {
      return "Delayed[nanos=" + this.nanoTime + ']';
    }
  }
  
  @Metadata(d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\b\002\b\000\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005R\022\020\003\032\0020\0048\006@\006X\016¢\006\002\n\000¨\006\006"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "timeNow", "", "(J)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class DelayedTaskQueue
    extends ThreadSafeHeap<EventLoopImplBase.DelayedTask>
  {
    public long timeNow;
    
    public DelayedTaskQueue(long paramLong)
    {
      this.timeNow = paramLong;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/EventLoopImplBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */