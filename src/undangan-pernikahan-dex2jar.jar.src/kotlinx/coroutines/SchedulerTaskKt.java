package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.scheduling.Task;
import kotlinx.coroutines.scheduling.TaskContext;

@Metadata(d1={"\000\036\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\003\032\021\020\t\032\0020\n*\0060\001j\002`\002H\b\"&\020\000\032\0060\001j\002`\002*\0060\003j\002`\0048@X\004¢\006\f\022\004\b\005\020\006\032\004\b\007\020\b*\f\b\000\020\013\"\0020\0032\0020\003*\f\b\000\020\f\"\0020\0012\0020\001¨\006\r"}, d2={"taskContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "Lkotlinx/coroutines/SchedulerTaskContext;", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "getTaskContext$annotations", "(Lkotlinx/coroutines/scheduling/Task;)V", "getTaskContext", "(Lkotlinx/coroutines/scheduling/Task;)Lkotlinx/coroutines/scheduling/TaskContext;", "afterTask", "", "SchedulerTask", "SchedulerTaskContext", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SchedulerTaskKt
{
  public static final void afterTask(TaskContext paramTaskContext)
  {
    paramTaskContext.afterTask();
  }
  
  public static final TaskContext getTaskContext(Task paramTask)
  {
    return paramTask.taskContext;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/SchedulerTaskKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */