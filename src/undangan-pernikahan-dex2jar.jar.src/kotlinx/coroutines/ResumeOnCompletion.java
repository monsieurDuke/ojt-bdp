package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\b\003\n\002\020\003\n\000\b\002\030\0002\0020\001B\023\022\f\020\002\032\b\022\004\022\0020\0040\003¢\006\002\020\005J\023\020\006\032\0020\0042\b\020\007\032\004\030\0010\bH\002R\024\020\002\032\b\022\004\022\0020\0040\003X\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlinx/coroutines/ResumeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "continuation", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "invoke", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class ResumeOnCompletion
  extends JobNode
{
  private final Continuation<Unit> continuation;
  
  public ResumeOnCompletion(Continuation<? super Unit> paramContinuation)
  {
    this.continuation = paramContinuation;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    paramThrowable = this.continuation;
    Result.Companion localCompanion = Result.Companion;
    paramThrowable.resumeWith(Result.constructor-impl(Unit.INSTANCE));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ResumeOnCompletion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */