package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\n\002\020\016\n\000\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\003\032\0020\004H\026J\r\020\005\032\0020\004H\000¢\006\002\b\006J\b\020\007\032\0020\bH\026¨\006\t"}, d2={"Lkotlinx/coroutines/scheduling/DefaultScheduler;", "Lkotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher;", "()V", "close", "", "shutdown", "shutdown$kotlinx_coroutines_core", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DefaultScheduler
  extends SchedulerCoroutineDispatcher
{
  public static final DefaultScheduler INSTANCE = new DefaultScheduler();
  
  private DefaultScheduler()
  {
    super(TasksKt.CORE_POOL_SIZE, TasksKt.MAX_POOL_SIZE, TasksKt.IDLE_WORKER_KEEP_ALIVE_NS, "DefaultDispatcher");
  }
  
  public void close()
  {
    throw new UnsupportedOperationException("Dispatchers.Default cannot be closed");
  }
  
  public final void shutdown$kotlinx_coroutines_core()
  {
    super.close();
  }
  
  public String toString()
  {
    return "Dispatchers.Default";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/DefaultScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */