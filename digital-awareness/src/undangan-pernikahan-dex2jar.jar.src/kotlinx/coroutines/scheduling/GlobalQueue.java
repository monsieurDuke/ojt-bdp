package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlinx.coroutines.internal.LockFreeTaskQueue;

@Metadata(d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b\000\030\0002\b\022\004\022\0020\0020\001B\005¢\006\002\020\003¨\006\004"}, d2={"Lkotlinx/coroutines/scheduling/GlobalQueue;", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "Lkotlinx/coroutines/scheduling/Task;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class GlobalQueue
  extends LockFreeTaskQueue<Task>
{
  public GlobalQueue()
  {
    super(false);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/GlobalQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */