package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\007\n\002\030\002\b\002\030\0002\0020\017B0\022'\020\b\032#\022\025\022\023\030\0010\002¢\006\f\b\003\022\b\b\004\022\004\b\b(\005\022\004\022\0020\0060\001j\002`\007¢\006\004\b\t\020\nJ\032\020\013\032\0020\0062\b\020\005\032\004\030\0010\002H\002¢\006\004\b\013\020\fR5\020\b\032#\022\025\022\023\030\0010\002¢\006\f\b\003\022\b\b\004\022\004\b\b(\005\022\004\022\0020\0060\001j\002`\0078\002X\004¢\006\006\n\004\b\b\020\r¨\006\016"}, d2={"Lkotlinx/coroutines/InvokeOnCancelling;", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "handler", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "invoke", "(Ljava/lang/Throwable;)V", "Lkotlin/jvm/functions/Function1;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/JobCancellingNode;"}, k=1, mv={1, 6, 0}, xi=48)
final class InvokeOnCancelling
  extends JobCancellingNode
{
  private static final AtomicIntegerFieldUpdater _invoked$FU = AtomicIntegerFieldUpdater.newUpdater(InvokeOnCancelling.class, "_invoked");
  private volatile int _invoked;
  private final Function1<Throwable, Unit> handler;
  
  public InvokeOnCancelling(Function1<? super Throwable, Unit> paramFunction1)
  {
    this.handler = paramFunction1;
    this._invoked = 0;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (_invoked$FU.compareAndSet(this, 0, 1)) {
      this.handler.invoke(paramThrowable);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/InvokeOnCancelling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */