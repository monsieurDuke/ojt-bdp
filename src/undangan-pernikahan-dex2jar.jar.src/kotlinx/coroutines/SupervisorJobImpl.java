package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\003\n\000\b\002\030\0002\0020\001B\017\022\b\020\002\032\004\030\0010\003¢\006\002\020\004J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026¨\006\t"}, d2={"Lkotlinx/coroutines/SupervisorJobImpl;", "Lkotlinx/coroutines/JobImpl;", "parent", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/Job;)V", "childCancelled", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class SupervisorJobImpl
  extends JobImpl
{
  public SupervisorJobImpl(Job paramJob)
  {
    super(paramJob);
  }
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    return false;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/SupervisorJobImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */