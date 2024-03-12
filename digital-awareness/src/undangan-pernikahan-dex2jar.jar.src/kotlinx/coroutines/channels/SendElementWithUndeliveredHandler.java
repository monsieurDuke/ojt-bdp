package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;

@Metadata(d1={"\000,\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B9\022\006\020\003\032\0028\000\022\f\020\004\032\b\022\004\022\0020\0060\005\022\034\020\007\032\030\022\004\022\0028\000\022\004\022\0020\0060\bj\b\022\004\022\0028\000`\t¢\006\002\020\nJ\b\020\013\032\0020\fH\026J\b\020\r\032\0020\006H\026R&\020\007\032\030\022\004\022\0028\000\022\004\022\0020\0060\bj\b\022\004\022\0028\000`\t8\006X\004¢\006\002\n\000¨\006\016"}, d2={"Lkotlinx/coroutines/channels/SendElementWithUndeliveredHandler;", "E", "Lkotlinx/coroutines/channels/SendElement;", "pollResult", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "onUndeliveredElement", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;Lkotlin/jvm/functions/Function1;)V", "remove", "", "undeliveredElement", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class SendElementWithUndeliveredHandler<E>
  extends SendElement<E>
{
  public final Function1<E, Unit> onUndeliveredElement;
  
  public SendElementWithUndeliveredHandler(E paramE, CancellableContinuation<? super Unit> paramCancellableContinuation, Function1<? super E, Unit> paramFunction1)
  {
    super(paramE, paramCancellableContinuation);
    this.onUndeliveredElement = paramFunction1;
  }
  
  public boolean remove()
  {
    if (!super.remove()) {
      return false;
    }
    undeliveredElement();
    return true;
  }
  
  public void undeliveredElement()
  {
    OnUndeliveredElementKt.callUndeliveredElement(this.onUndeliveredElement, getPollResult(), this.cont.getContext());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/SendElementWithUndeliveredHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */