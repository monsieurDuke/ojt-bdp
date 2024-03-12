package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\004\n\002\020\002\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\007\032\0020\bH\026R\024\020\002\032\0020\003X\004¢\006\b\n\000\032\004\b\005\020\006¨\006\t"}, d2={"Lkotlinx/coroutines/scheduling/TaskContextImpl;", "Lkotlinx/coroutines/scheduling/TaskContext;", "taskMode", "", "(I)V", "getTaskMode", "()I", "afterTask", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class TaskContextImpl
  implements TaskContext
{
  private final int taskMode;
  
  public TaskContextImpl(int paramInt)
  {
    this.taskMode = paramInt;
  }
  
  public void afterTask() {}
  
  public int getTaskMode()
  {
    return this.taskMode;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/TaskContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */