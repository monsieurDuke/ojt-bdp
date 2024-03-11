package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\007\n\002\020\003\n\000\b\020\030\0002\0020\0012\0020\002B\017\022\b\020\003\032\004\030\0010\004¢\006\002\020\005J\b\020\f\032\0020\007H\026J\020\020\r\032\0020\0072\006\020\016\032\0020\017H\026J\b\020\006\032\0020\007H\003R\024\020\006\032\0020\007X\004¢\006\b\n\000\032\004\b\b\020\tR\024\020\n\032\0020\0078PX\004¢\006\006\032\004\b\013\020\t¨\006\020"}, d2={"Lkotlinx/coroutines/JobImpl;", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/CompletableJob;", "parent", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/Job;)V", "handlesException", "", "getHandlesException$kotlinx_coroutines_core", "()Z", "onCancelComplete", "getOnCancelComplete$kotlinx_coroutines_core", "complete", "completeExceptionally", "exception", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class JobImpl
  extends JobSupport
  implements CompletableJob
{
  private final boolean handlesException;
  
  public JobImpl(Job paramJob)
  {
    super(true);
    initParentJob(paramJob);
    this.handlesException = handlesException();
  }
  
  private final boolean handlesException()
  {
    Object localObject1 = getParentHandle$kotlinx_coroutines_core();
    if ((localObject1 instanceof ChildHandleNode)) {
      localObject1 = (ChildHandleNode)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((ChildHandleNode)localObject1).getJob();
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      return false;
    }
    for (;;)
    {
      if (((JobSupport)localObject2).getHandlesException$kotlinx_coroutines_core()) {
        return true;
      }
      localObject1 = ((JobSupport)localObject2).getParentHandle$kotlinx_coroutines_core();
      if ((localObject1 instanceof ChildHandleNode)) {
        localObject1 = (ChildHandleNode)localObject1;
      } else {
        localObject1 = null;
      }
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = ((ChildHandleNode)localObject1).getJob();
      }
      if (localObject1 == null) {
        return false;
      }
      localObject2 = localObject1;
    }
  }
  
  public boolean complete()
  {
    return makeCompleting$kotlinx_coroutines_core(Unit.INSTANCE);
  }
  
  public boolean completeExceptionally(Throwable paramThrowable)
  {
    return makeCompleting$kotlinx_coroutines_core(new CompletedExceptionally(paramThrowable, false, 2, null));
  }
  
  public boolean getHandlesException$kotlinx_coroutines_core()
  {
    return this.handlesException;
  }
  
  public boolean getOnCancelComplete$kotlinx_coroutines_core()
  {
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/JobImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */