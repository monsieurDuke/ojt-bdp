package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\b\000\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003¢\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\002R\024\020\002\032\006\022\002\b\0030\0038\006X\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlinx/coroutines/ChildContinuation;", "Lkotlinx/coroutines/JobCancellingNode;", "child", "Lkotlinx/coroutines/CancellableContinuationImpl;", "(Lkotlinx/coroutines/CancellableContinuationImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ChildContinuation
  extends JobCancellingNode
{
  public final CancellableContinuationImpl<?> child;
  
  public ChildContinuation(CancellableContinuationImpl<?> paramCancellableContinuationImpl)
  {
    this.child = paramCancellableContinuationImpl;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    paramThrowable = this.child;
    paramThrowable.parentCancelled$kotlinx_coroutines_core(paramThrowable.getContinuationCancellationCause((Job)getJob()));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ChildContinuation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */