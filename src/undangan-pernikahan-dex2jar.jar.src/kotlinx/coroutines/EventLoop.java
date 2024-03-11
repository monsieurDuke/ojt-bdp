package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.ArrayQueue;
import kotlinx.coroutines.internal.LimitedDispatcherKt;

@Metadata(d1={"\0008\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\t\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\007\n\002\020\b\n\002\b\005\b \030\0002\0020\001B\005¢\006\002\020\002J\020\020\022\032\0020\0232\b\b\002\020\024\032\0020\004J\020\020\025\032\0020\n2\006\020\024\032\0020\004H\002J\022\020\026\032\0020\0232\n\020\027\032\006\022\002\b\0030\020J\020\020\030\032\0020\0232\b\b\002\020\024\032\0020\004J\016\020\031\032\0020\0012\006\020\032\032\0020\033J\b\020\034\032\0020\nH\026J\006\020\035\032\0020\004J\b\020\036\032\0020\004H\026J\b\020\037\032\0020\023H\026R\021\020\003\032\0020\0048F¢\006\006\032\004\b\003\020\005R\024\020\006\032\0020\0048TX\004¢\006\006\032\004\b\006\020\005R\021\020\007\032\0020\0048F¢\006\006\032\004\b\007\020\005R\021\020\b\032\0020\0048F¢\006\006\032\004\b\b\020\005R\024\020\t\032\0020\n8TX\004¢\006\006\032\004\b\013\020\fR\016\020\r\032\0020\004X\016¢\006\002\n\000R\032\020\016\032\016\022\b\022\006\022\002\b\0030\020\030\0010\017X\016¢\006\002\n\000R\016\020\021\032\0020\nX\016¢\006\002\n\000¨\006 "}, d2={"Lkotlinx/coroutines/EventLoop;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "isActive", "", "()Z", "isEmpty", "isUnconfinedLoopActive", "isUnconfinedQueueEmpty", "nextTime", "", "getNextTime", "()J", "shared", "unconfinedQueue", "Lkotlinx/coroutines/internal/ArrayQueue;", "Lkotlinx/coroutines/DispatchedTask;", "useCount", "decrementUseCount", "", "unconfined", "delta", "dispatchUnconfined", "task", "incrementUseCount", "limitedParallelism", "parallelism", "", "processNextEvent", "processUnconfinedEvent", "shouldBeProcessedFromContext", "shutdown", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class EventLoop
  extends CoroutineDispatcher
{
  private boolean shared;
  private ArrayQueue<DispatchedTask<?>> unconfinedQueue;
  private long useCount;
  
  private final long delta(boolean paramBoolean)
  {
    long l;
    if (paramBoolean) {
      l = 4294967296L;
    } else {
      l = 1L;
    }
    return l;
  }
  
  public final void decrementUseCount(boolean paramBoolean)
  {
    long l = this.useCount - delta(paramBoolean);
    this.useCount = l;
    if (l > 0L) {
      return;
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (this.useCount == 0L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (this.shared) {
      shutdown();
    }
  }
  
  public final void dispatchUnconfined(DispatchedTask<?> paramDispatchedTask)
  {
    ArrayQueue localArrayQueue2 = this.unconfinedQueue;
    ArrayQueue localArrayQueue1 = localArrayQueue2;
    if (localArrayQueue2 == null)
    {
      localArrayQueue1 = new ArrayQueue();
      this.unconfinedQueue = localArrayQueue1;
    }
    localArrayQueue1.addLast(paramDispatchedTask);
  }
  
  protected long getNextTime()
  {
    ArrayQueue localArrayQueue = this.unconfinedQueue;
    long l = Long.MAX_VALUE;
    if (localArrayQueue == null) {
      return Long.MAX_VALUE;
    }
    if (!localArrayQueue.isEmpty()) {
      l = 0L;
    }
    return l;
  }
  
  public final void incrementUseCount(boolean paramBoolean)
  {
    this.useCount += delta(paramBoolean);
    if (!paramBoolean) {
      this.shared = true;
    }
  }
  
  public final boolean isActive()
  {
    boolean bool;
    if (this.useCount > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected boolean isEmpty()
  {
    return isUnconfinedQueueEmpty();
  }
  
  public final boolean isUnconfinedLoopActive()
  {
    long l = this.useCount;
    boolean bool = true;
    if (l < delta(true)) {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isUnconfinedQueueEmpty()
  {
    ArrayQueue localArrayQueue = this.unconfinedQueue;
    boolean bool;
    if (localArrayQueue == null) {
      bool = true;
    } else {
      bool = localArrayQueue.isEmpty();
    }
    return bool;
  }
  
  public final CoroutineDispatcher limitedParallelism(int paramInt)
  {
    LimitedDispatcherKt.checkParallelism(paramInt);
    return (CoroutineDispatcher)this;
  }
  
  public long processNextEvent()
  {
    if (!processUnconfinedEvent()) {
      return Long.MAX_VALUE;
    }
    return 0L;
  }
  
  public final boolean processUnconfinedEvent()
  {
    Object localObject = this.unconfinedQueue;
    if (localObject == null) {
      return false;
    }
    localObject = (DispatchedTask)((ArrayQueue)localObject).removeFirstOrNull();
    if (localObject == null) {
      return false;
    }
    ((DispatchedTask)localObject).run();
    return true;
  }
  
  public boolean shouldBeProcessedFromContext()
  {
    return false;
  }
  
  public void shutdown() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/EventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */