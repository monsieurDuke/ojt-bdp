package kotlinx.coroutines;

import java.util.concurrent.Future;
import kotlin.Metadata;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003¢\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\002J\b\020\t\032\0020\nH\026R\022\020\002\032\006\022\002\b\0030\003X\004¢\006\002\n\000¨\006\013"}, d2={"Lkotlinx/coroutines/CancelFutureOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "future", "Ljava/util/concurrent/Future;", "(Ljava/util/concurrent/Future;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class CancelFutureOnCancel
  extends CancelHandler
{
  private final Future<?> future;
  
  public CancelFutureOnCancel(Future<?> paramFuture)
  {
    this.future = paramFuture;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (paramThrowable != null) {
      this.future.cancel(false);
    }
  }
  
  public String toString()
  {
    return "CancelFutureOnCancel[" + this.future + ']';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CancelFutureOnCancel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */