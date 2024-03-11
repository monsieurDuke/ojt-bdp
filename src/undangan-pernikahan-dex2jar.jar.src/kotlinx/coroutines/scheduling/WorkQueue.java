package kotlinx.coroutines.scheduling;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0;

@Metadata(d1={"\000@\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\002\b\005\n\002\030\002\n\000\n\002\020\002\n\002\b\t\n\002\020\t\n\002\b\b\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\006\n\002\020\000\b\000\030\0002\0020*B\007¢\006\004\b\001\020\002J!\020\007\032\004\030\0010\0032\006\020\004\032\0020\0032\b\b\002\020\006\032\0020\005¢\006\004\b\007\020\bJ\031\020\t\032\004\030\0010\0032\006\020\004\032\0020\003H\002¢\006\004\b\t\020\nJ\025\020\016\032\0020\r2\006\020\f\032\0020\013¢\006\004\b\016\020\017J\017\020\020\032\004\030\0010\003¢\006\004\b\020\020\021J\021\020\022\032\004\030\0010\003H\002¢\006\004\b\022\020\021J\027\020\024\032\0020\0052\006\020\023\032\0020\013H\002¢\006\004\b\024\020\025J\025\020\030\032\0020\0272\006\020\026\032\0020\000¢\006\004\b\030\020\031J\025\020\032\032\0020\0272\006\020\026\032\0020\000¢\006\004\b\032\020\031J\037\020\034\032\0020\0272\006\020\026\032\0020\0002\006\020\033\032\0020\005H\002¢\006\004\b\034\020\035J\025\020\036\032\0020\r*\004\030\0010\003H\002¢\006\004\b\036\020\037R\034\020!\032\n\022\006\022\004\030\0010\0030 8\002X\004¢\006\006\n\004\b!\020\"R\024\020&\032\0020#8@X\004¢\006\006\032\004\b$\020%R\024\020(\032\0020#8@X\004¢\006\006\032\004\b'\020%¨\006)"}, d2={"Lkotlinx/coroutines/scheduling/WorkQueue;", "<init>", "()V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "fair", "add", "(Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "addLast", "(Lkotlinx/coroutines/scheduling/Task;)Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalQueue", "", "offloadAllWorkTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)V", "poll", "()Lkotlinx/coroutines/scheduling/Task;", "pollBuffer", "queue", "pollTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)Z", "victim", "", "tryStealBlockingFrom", "(Lkotlinx/coroutines/scheduling/WorkQueue;)J", "tryStealFrom", "blockingOnly", "tryStealLastScheduled", "(Lkotlinx/coroutines/scheduling/WorkQueue;Z)J", "decrementIfBlocking", "(Lkotlinx/coroutines/scheduling/Task;)V", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "buffer", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "", "getBufferSize$kotlinx_coroutines_core", "()I", "bufferSize", "getSize$kotlinx_coroutines_core", "size", "kotlinx-coroutines-core", ""}, k=1, mv={1, 6, 0}, xi=48)
public final class WorkQueue
{
  private static final AtomicIntegerFieldUpdater blockingTasksInBuffer$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "blockingTasksInBuffer");
  private static final AtomicIntegerFieldUpdater consumerIndex$FU;
  private static final AtomicReferenceFieldUpdater lastScheduledTask$FU = AtomicReferenceFieldUpdater.newUpdater(WorkQueue.class, Object.class, "lastScheduledTask");
  private static final AtomicIntegerFieldUpdater producerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "producerIndex");
  private volatile int blockingTasksInBuffer = 0;
  private final AtomicReferenceArray<Task> buffer = new AtomicReferenceArray(128);
  private volatile int consumerIndex = 0;
  private volatile Object lastScheduledTask = null;
  private volatile int producerIndex = 0;
  
  static
  {
    consumerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "consumerIndex");
  }
  
  private final Task addLast(Task paramTask)
  {
    int j = paramTask.taskContext.getTaskMode();
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    if (i != 0) {
      blockingTasksInBuffer$FU.incrementAndGet(this);
    }
    if (getBufferSize$kotlinx_coroutines_core() == 127) {
      return paramTask;
    }
    i = this.producerIndex & 0x7F;
    while (this.buffer.get(i) != null) {
      Thread.yield();
    }
    this.buffer.lazySet(i, paramTask);
    producerIndex$FU.incrementAndGet(this);
    return null;
  }
  
  private final void decrementIfBlocking(Task paramTask)
  {
    if (paramTask != null)
    {
      int i = paramTask.taskContext.getTaskMode();
      int j = 0;
      if (i == 1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        int k = blockingTasksInBuffer$FU.decrementAndGet(this);
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          i = j;
          if (k >= 0) {
            i = 1;
          }
          if (i == 0) {
            throw new AssertionError();
          }
        }
      }
    }
  }
  
  private final Task pollBuffer()
  {
    for (;;)
    {
      int i = this.consumerIndex;
      if (i - this.producerIndex == 0) {
        return null;
      }
      if (consumerIndex$FU.compareAndSet(this, i, i + 1))
      {
        Task localTask = (Task)this.buffer.getAndSet(i & 0x7F, null);
        if (localTask != null)
        {
          decrementIfBlocking(localTask);
          return localTask;
        }
      }
    }
  }
  
  private final boolean pollTo(GlobalQueue paramGlobalQueue)
  {
    Task localTask = pollBuffer();
    if (localTask == null) {
      return false;
    }
    paramGlobalQueue.addLast(localTask);
    return true;
  }
  
  private final long tryStealLastScheduled(WorkQueue paramWorkQueue, boolean paramBoolean)
  {
    for (;;)
    {
      Task localTask = (Task)paramWorkQueue.lastScheduledTask;
      if (localTask == null) {
        return -2L;
      }
      if (paramBoolean)
      {
        int j = localTask.taskContext.getTaskMode();
        int i = 1;
        if (j != 1) {
          i = 0;
        }
        if (i == 0) {
          return -2L;
        }
      }
      long l = TasksKt.schedulerTimeSource.nanoTime() - localTask.submissionTime;
      if (l < TasksKt.WORK_STEALING_TIME_RESOLUTION_NS) {
        return TasksKt.WORK_STEALING_TIME_RESOLUTION_NS - l;
      }
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(lastScheduledTask$FU, paramWorkQueue, localTask, null))
      {
        add$default(this, localTask, false, 2, null);
        return -1L;
      }
    }
  }
  
  public final Task add(Task paramTask, boolean paramBoolean)
  {
    if (paramBoolean) {
      return addLast(paramTask);
    }
    paramTask = (Task)lastScheduledTask$FU.getAndSet(this, paramTask);
    if (paramTask == null) {
      return null;
    }
    return addLast(paramTask);
  }
  
  public final int getBufferSize$kotlinx_coroutines_core()
  {
    return this.producerIndex - this.consumerIndex;
  }
  
  public final int getSize$kotlinx_coroutines_core()
  {
    int i;
    if (this.lastScheduledTask != null) {
      i = getBufferSize$kotlinx_coroutines_core() + 1;
    } else {
      i = getBufferSize$kotlinx_coroutines_core();
    }
    return i;
  }
  
  public final void offloadAllWorkTo(GlobalQueue paramGlobalQueue)
  {
    Task localTask = (Task)lastScheduledTask$FU.getAndSet(this, null);
    if (localTask != null) {
      paramGlobalQueue.addLast(localTask);
    }
    while (pollTo(paramGlobalQueue)) {}
  }
  
  public final Task poll()
  {
    Task localTask2 = (Task)lastScheduledTask$FU.getAndSet(this, null);
    Task localTask1 = localTask2;
    if (localTask2 == null) {
      localTask1 = pollBuffer();
    }
    return localTask1;
  }
  
  public final long tryStealBlockingFrom(WorkQueue paramWorkQueue)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (getBufferSize$kotlinx_coroutines_core() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    int i = paramWorkQueue.consumerIndex;
    int k = paramWorkQueue.producerIndex;
    AtomicReferenceArray localAtomicReferenceArray = paramWorkQueue.buffer;
    while (i != k)
    {
      int m = i & 0x7F;
      if (paramWorkQueue.blockingTasksInBuffer == 0) {
        break;
      }
      Task localTask = (Task)localAtomicReferenceArray.get(m);
      if (localTask != null)
      {
        int j;
        if (localTask.taskContext.getTaskMode() == 1) {
          j = 1;
        } else {
          j = 0;
        }
        if ((j != 0) && (ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0.m(localAtomicReferenceArray, m, localTask, null)))
        {
          blockingTasksInBuffer$FU.decrementAndGet(paramWorkQueue);
          add$default(this, localTask, false, 2, null);
          return -1L;
        }
      }
      i++;
    }
    return tryStealLastScheduled(paramWorkQueue, true);
  }
  
  public final long tryStealFrom(WorkQueue paramWorkQueue)
  {
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int j = 1;
    int i;
    if (bool)
    {
      if (getBufferSize$kotlinx_coroutines_core() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    Task localTask = paramWorkQueue.pollBuffer();
    if (localTask != null)
    {
      paramWorkQueue = add$default(this, localTask, false, 2, null);
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (paramWorkQueue == null) {
          i = j;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      return -1L;
    }
    return tryStealLastScheduled(paramWorkQueue, false);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/WorkQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */