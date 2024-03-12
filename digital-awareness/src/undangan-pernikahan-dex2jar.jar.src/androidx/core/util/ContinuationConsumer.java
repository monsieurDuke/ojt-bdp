package androidx.core.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\n\002\020\016\n\000\b\003\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\0020\003B\023\022\f\020\004\032\b\022\004\022\0028\0000\005¢\006\002\020\006J\025\020\007\032\0020\b2\006\020\t\032\0028\000H\026¢\006\002\020\nJ\b\020\013\032\0020\fH\026R\024\020\004\032\b\022\004\022\0028\0000\005X\004¢\006\002\n\000¨\006\r"}, d2={"Landroidx/core/util/ContinuationConsumer;", "T", "Ljava/util/function/Consumer;", "Ljava/util/concurrent/atomic/AtomicBoolean;", "continuation", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;)V", "accept", "", "value", "(Ljava/lang/Object;)V", "toString", "", "core-ktx_release"}, k=1, mv={1, 6, 0}, xi=48)
final class ContinuationConsumer<T>
  extends AtomicBoolean
  implements Consumer<T>
{
  private final Continuation<T> continuation;
  
  public ContinuationConsumer(Continuation<? super T> paramContinuation)
  {
    super(false);
    this.continuation = paramContinuation;
  }
  
  public void accept(T paramT)
  {
    if (compareAndSet(false, true))
    {
      Continuation localContinuation = this.continuation;
      Result.Companion localCompanion = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(paramT));
    }
  }
  
  public String toString()
  {
    return "ContinuationConsumer(resultAccepted = " + get() + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/ContinuationConsumer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */