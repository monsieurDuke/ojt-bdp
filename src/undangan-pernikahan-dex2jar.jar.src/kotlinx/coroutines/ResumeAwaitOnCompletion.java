package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000 \n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\b\002\030\000*\004\b\000\020\0012\0020\002B\023\022\f\020\003\032\b\022\004\022\0028\0000\004¢\006\002\020\005J\023\020\006\032\0020\0072\b\020\b\032\004\030\0010\tH\002R\024\020\003\032\b\022\004\022\0028\0000\004X\004¢\006\002\n\000¨\006\n"}, d2={"Lkotlinx/coroutines/ResumeAwaitOnCompletion;", "T", "Lkotlinx/coroutines/JobNode;", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "(Lkotlinx/coroutines/CancellableContinuationImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class ResumeAwaitOnCompletion<T>
  extends JobNode
{
  private final CancellableContinuationImpl<T> continuation;
  
  public ResumeAwaitOnCompletion(CancellableContinuationImpl<? super T> paramCancellableContinuationImpl)
  {
    this.continuation = paramCancellableContinuationImpl;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    paramThrowable = getJob().getState$kotlinx_coroutines_core();
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(paramThrowable instanceof Incomplete ^ true))) {
      throw new AssertionError();
    }
    Continuation localContinuation;
    Result.Companion localCompanion;
    if ((paramThrowable instanceof CompletedExceptionally))
    {
      localContinuation = (Continuation)this.continuation;
      localCompanion = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(((CompletedExceptionally)paramThrowable).cause)));
    }
    else
    {
      localContinuation = (Continuation)this.continuation;
      localCompanion = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(JobSupportKt.unboxState(paramThrowable)));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ResumeAwaitOnCompletion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */