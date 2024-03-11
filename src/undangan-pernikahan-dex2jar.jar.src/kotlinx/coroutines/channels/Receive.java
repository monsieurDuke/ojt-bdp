package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\0000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\020\003\n\002\020\002\n\002\b\004\n\002\030\002\n\000\b \030\000*\006\b\000\020\001 \0002\0020\0022\b\022\004\022\002H\0010\003B\005¢\006\002\020\004J#\020\t\032\020\022\004\022\0020\013\022\004\022\0020\f\030\0010\n2\006\020\r\032\0028\000H\026¢\006\002\020\016J\024\020\017\032\0020\f2\n\020\020\032\006\022\002\b\0030\021H&R\024\020\005\032\0020\0068VX\004¢\006\006\032\004\b\007\020\b¨\006\022"}, d2={"Lkotlinx/coroutines/channels/Receive;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "()V", "offerResult", "Lkotlinx/coroutines/internal/Symbol;", "getOfferResult", "()Lkotlinx/coroutines/internal/Symbol;", "resumeOnCancellationFun", "Lkotlin/Function1;", "", "", "value", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class Receive<E>
  extends LockFreeLinkedListNode
  implements ReceiveOrClosed<E>
{
  public Symbol getOfferResult()
  {
    return AbstractChannelKt.OFFER_SUCCESS;
  }
  
  public Function1<Throwable, Unit> resumeOnCancellationFun(E paramE)
  {
    return null;
  }
  
  public abstract void resumeReceiveClosed(Closed<?> paramClosed);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/Receive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */