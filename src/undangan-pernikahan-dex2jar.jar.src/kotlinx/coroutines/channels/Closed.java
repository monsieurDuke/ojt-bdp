package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.Symbol;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0004\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\003\n\002\b\f\n\002\020\002\n\002\b\006\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\b\000\030\000*\006\b\000\020\001 \0002\0020\0022\b\022\004\022\002H\0010\003B\017\022\b\020\004\032\004\030\0010\005¢\006\002\020\006J\025\020\021\032\0020\0222\006\020\023\032\0028\000H\026¢\006\002\020\024J\b\020\025\032\0020\022H\026J\024\020\026\032\0020\0222\n\020\027\032\006\022\002\b\0030\000H\026J\b\020\030\032\0020\031H\026J\037\020\032\032\0020\0332\006\020\023\032\0028\0002\b\020\034\032\004\030\0010\035H\026¢\006\002\020\036J\022\020\037\032\0020\0332\b\020\034\032\004\030\0010\035H\026R\022\020\004\032\004\030\0010\0058\006X\004¢\006\002\n\000R\032\020\007\032\b\022\004\022\0028\0000\0008VX\004¢\006\006\032\004\b\b\020\tR\032\020\n\032\b\022\004\022\0028\0000\0008VX\004¢\006\006\032\004\b\013\020\tR\021\020\f\032\0020\0058F¢\006\006\032\004\b\r\020\016R\021\020\017\032\0020\0058F¢\006\006\032\004\b\020\020\016¨\006 "}, d2={"Lkotlinx/coroutines/channels/Closed;", "E", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "closeCause", "", "(Ljava/lang/Throwable;)V", "offerResult", "getOfferResult", "()Lkotlinx/coroutines/channels/Closed;", "pollResult", "getPollResult", "receiveException", "getReceiveException", "()Ljava/lang/Throwable;", "sendException", "getSendException", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "completeResumeSend", "resumeSendClosed", "closed", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "tryResumeSend", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class Closed<E>
  extends Send
  implements ReceiveOrClosed<E>
{
  public final Throwable closeCause;
  
  public Closed(Throwable paramThrowable)
  {
    this.closeCause = paramThrowable;
  }
  
  public void completeResumeReceive(E paramE) {}
  
  public void completeResumeSend() {}
  
  public Closed<E> getOfferResult()
  {
    return this;
  }
  
  public Closed<E> getPollResult()
  {
    return this;
  }
  
  public final Throwable getReceiveException()
  {
    Throwable localThrowable2 = this.closeCause;
    Throwable localThrowable1 = localThrowable2;
    if (localThrowable2 == null) {
      localThrowable1 = (Throwable)new ClosedReceiveChannelException("Channel was closed");
    }
    return localThrowable1;
  }
  
  public final Throwable getSendException()
  {
    Throwable localThrowable2 = this.closeCause;
    Throwable localThrowable1 = localThrowable2;
    if (localThrowable2 == null) {
      localThrowable1 = (Throwable)new ClosedSendChannelException("Channel was closed");
    }
    return localThrowable1;
  }
  
  public void resumeSendClosed(Closed<?> paramClosed)
  {
    if (!DebugKt.getASSERTIONS_ENABLED()) {
      return;
    }
    throw new AssertionError();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Closed@");
    String str = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + '[' + this.closeCause + ']';
  }
  
  public Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp)
  {
    paramE = CancellableContinuationImplKt.RESUME_TOKEN;
    if (paramPrepareOp != null) {
      paramPrepareOp.finishPrepare();
    }
    return paramE;
  }
  
  public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
  {
    Symbol localSymbol = CancellableContinuationImplKt.RESUME_TOKEN;
    if (paramPrepareOp != null) {
      paramPrepareOp.finishPrepare();
    }
    return localSymbol;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/Closed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */