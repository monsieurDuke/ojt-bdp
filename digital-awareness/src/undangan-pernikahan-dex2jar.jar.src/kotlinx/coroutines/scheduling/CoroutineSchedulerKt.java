package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(d1={"\000\020\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\001\032\020\020\004\032\0020\0012\006\020\002\032\0020\003H\001¨\006\005"}, d2={"isSchedulerWorker", "", "thread", "Ljava/lang/Thread;", "mayNotBlock", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CoroutineSchedulerKt
{
  public static final boolean isSchedulerWorker(Thread paramThread)
  {
    return paramThread instanceof CoroutineScheduler.Worker;
  }
  
  public static final boolean mayNotBlock(Thread paramThread)
  {
    boolean bool;
    if (((paramThread instanceof CoroutineScheduler.Worker)) && (((CoroutineScheduler.Worker)paramThread).state == CoroutineScheduler.WorkerState.CPU_ACQUIRED)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/CoroutineSchedulerKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */