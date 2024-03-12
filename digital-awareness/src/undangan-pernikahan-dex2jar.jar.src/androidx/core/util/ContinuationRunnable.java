package androidx.core.util;

import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\b\003\n\002\020\016\n\000\b\002\030\0002\0020\0012\0020\002B\023\022\f\020\003\032\b\022\004\022\0020\0050\004¢\006\002\020\006J\b\020\007\032\0020\005H\026J\b\020\b\032\0020\tH\026R\024\020\003\032\b\022\004\022\0020\0050\004X\004¢\006\002\n\000¨\006\n"}, d2={"Landroidx/core/util/ContinuationRunnable;", "Ljava/lang/Runnable;", "Ljava/util/concurrent/atomic/AtomicBoolean;", "continuation", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "run", "toString", "", "core-ktx_release"}, k=1, mv={1, 6, 0}, xi=48)
final class ContinuationRunnable
  extends AtomicBoolean
  implements Runnable
{
  private final Continuation<Unit> continuation;
  
  public ContinuationRunnable(Continuation<? super Unit> paramContinuation)
  {
    super(false);
    this.continuation = paramContinuation;
  }
  
  public void run()
  {
    if (compareAndSet(false, true))
    {
      Continuation localContinuation = this.continuation;
      Result.Companion localCompanion = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(Unit.INSTANCE));
    }
  }
  
  public String toString()
  {
    return "ContinuationRunnable(ran = " + get() + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/ContinuationRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */