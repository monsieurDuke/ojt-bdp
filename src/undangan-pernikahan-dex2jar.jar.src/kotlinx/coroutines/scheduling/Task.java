package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\b \030\0002\0060\001j\002`\002B\007\b\026¢\006\002\020\003B\025\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bR\022\020\t\032\0020\n8Æ\002¢\006\006\032\004\b\013\020\fR\022\020\004\032\0020\0058\006@\006X\016¢\006\002\n\000R\022\020\006\032\0020\0078\006@\006X\016¢\006\002\n\000¨\006\r"}, d2={"Lkotlinx/coroutines/scheduling/Task;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "()V", "submissionTime", "", "taskContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "(JLkotlinx/coroutines/scheduling/TaskContext;)V", "mode", "", "getMode", "()I", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class Task
  implements Runnable
{
  public long submissionTime;
  public TaskContext taskContext;
  
  public Task()
  {
    this(0L, TasksKt.NonBlockingContext);
  }
  
  public Task(long paramLong, TaskContext paramTaskContext)
  {
    this.submissionTime = paramLong;
    this.taskContext = paramTaskContext;
  }
  
  public final int getMode()
  {
    return this.taskContext.getTaskMode();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */