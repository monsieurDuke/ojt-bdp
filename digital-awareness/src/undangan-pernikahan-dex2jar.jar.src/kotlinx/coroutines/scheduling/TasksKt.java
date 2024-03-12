package kotlinx.coroutines.scheduling;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(d1={"\000.\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\016\n\000\n\002\020\t\n\002\b\006\n\002\030\002\n\000\n\002\020\013\n\002\030\002\n\002\b\002\"\020\020\000\032\0020\0018\000X\004¢\006\002\n\000\"\020\020\002\032\0020\0038\000X\004¢\006\002\n\000\"\016\020\004\032\0020\005XT¢\006\002\n\000\"\020\020\006\032\0020\0078\000X\004¢\006\002\n\000\"\020\020\b\032\0020\0038\000X\004¢\006\002\n\000\"\020\020\t\032\0020\0018\000X\004¢\006\002\n\000\"\016\020\n\032\0020\003XT¢\006\002\n\000\"\016\020\013\032\0020\003XT¢\006\002\n\000\"\020\020\f\032\0020\0078\000X\004¢\006\002\n\000\"\022\020\r\032\0020\0168\000@\000X\016¢\006\002\n\000\"\031\020\017\032\0020\020*\0020\0218À\002X\004¢\006\006\032\004\b\017\020\022¨\006\023"}, d2={"BlockingContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "CORE_POOL_SIZE", "", "DEFAULT_SCHEDULER_NAME", "", "IDLE_WORKER_KEEP_ALIVE_NS", "", "MAX_POOL_SIZE", "NonBlockingContext", "TASK_NON_BLOCKING", "TASK_PROBABLY_BLOCKING", "WORK_STEALING_TIME_RESOLUTION_NS", "schedulerTimeSource", "Lkotlinx/coroutines/scheduling/SchedulerTimeSource;", "isBlocking", "", "Lkotlinx/coroutines/scheduling/Task;", "(Lkotlinx/coroutines/scheduling/Task;)Z", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class TasksKt
{
  public static final TaskContext BlockingContext = (TaskContext)new TaskContextImpl(1);
  public static final int CORE_POOL_SIZE;
  public static final String DEFAULT_SCHEDULER_NAME = "DefaultDispatcher";
  public static final long IDLE_WORKER_KEEP_ALIVE_NS;
  public static final int MAX_POOL_SIZE;
  public static final TaskContext NonBlockingContext;
  public static final int TASK_NON_BLOCKING = 0;
  public static final int TASK_PROBABLY_BLOCKING = 1;
  public static final long WORK_STEALING_TIME_RESOLUTION_NS = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.resolution.ns", 100000L, 0L, 0L, 12, null);
  public static SchedulerTimeSource schedulerTimeSource;
  
  static
  {
    int i = RangesKt.coerceAtLeast(SystemPropsKt.getAVAILABLE_PROCESSORS(), 2);
    CORE_POOL_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.core.pool.size", i, 1, 0, 8, null);
    MAX_POOL_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.max.pool.size", 2097150, 0, 2097150, 4, null);
    IDLE_WORKER_KEEP_ALIVE_NS = TimeUnit.SECONDS.toNanos(SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.keep.alive.sec", 60L, 0L, 0L, 12, null));
    schedulerTimeSource = (SchedulerTimeSource)NanoTimeSource.INSTANCE;
    NonBlockingContext = (TaskContext)new TaskContextImpl(0);
  }
  
  public static final boolean isBlocking(Task paramTask)
  {
    int i = paramTask.taskContext.getTaskMode();
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/TasksKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */