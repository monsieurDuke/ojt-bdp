package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Metadata(d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\003\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\033\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0028\0000\006¢\006\002\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026¨\006\f"}, d2={"Lkotlinx/coroutines/flow/internal/FlowCoroutine;", "T", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "context", "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "childCancelled", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class FlowCoroutine<T>
  extends ScopeCoroutine<T>
{
  public FlowCoroutine(CoroutineContext paramCoroutineContext, Continuation<? super T> paramContinuation)
  {
    super(paramCoroutineContext, paramContinuation);
  }
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof ChildCancelledException)) {
      return true;
    }
    return cancelImpl$kotlinx_coroutines_core(paramThrowable);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/FlowCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */