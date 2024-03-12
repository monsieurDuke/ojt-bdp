package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\0000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\003\n\000\n\002\020\002\n\000\b\000\030\0002\0020\0012\0020\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\020\020\n\032\0020\0132\006\020\f\032\0020\rH\026J\023\020\016\032\0020\0172\b\020\f\032\004\030\0010\rH\002R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000R\024\020\006\032\0020\0078VX\004¢\006\006\032\004\b\b\020\t¨\006\020"}, d2={"Lkotlinx/coroutines/ChildHandleNode;", "Lkotlinx/coroutines/JobCancellingNode;", "Lkotlinx/coroutines/ChildHandle;", "childJob", "Lkotlinx/coroutines/ChildJob;", "(Lkotlinx/coroutines/ChildJob;)V", "parent", "Lkotlinx/coroutines/Job;", "getParent", "()Lkotlinx/coroutines/Job;", "childCancelled", "", "cause", "", "invoke", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ChildHandleNode
  extends JobCancellingNode
  implements ChildHandle
{
  public final ChildJob childJob;
  
  public ChildHandleNode(ChildJob paramChildJob)
  {
    this.childJob = paramChildJob;
  }
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    return getJob().childCancelled(paramThrowable);
  }
  
  public Job getParent()
  {
    return (Job)getJob();
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.childJob.parentCancelled((ParentJob)getJob());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ChildHandleNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */