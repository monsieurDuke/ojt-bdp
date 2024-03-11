package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.Symbol;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\002\n\002\b\007\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\020\030\000*\004\b\000\020\0012\0020\002B\033\022\006\020\003\032\0028\000\022\f\020\004\032\b\022\004\022\0020\0060\005¢\006\002\020\007J\b\020\013\032\0020\006H\026J\024\020\f\032\0020\0062\n\020\r\032\006\022\002\b\0030\016H\026J\b\020\017\032\0020\020H\026J\024\020\021\032\004\030\0010\0222\b\020\023\032\004\030\0010\024H\026R\026\020\004\032\b\022\004\022\0020\0060\0058\006X\004¢\006\002\n\000R\026\020\003\032\0028\000X\004¢\006\n\n\002\020\n\032\004\b\b\020\t¨\006\025"}, d2={"Lkotlinx/coroutines/channels/SendElement;", "E", "Lkotlinx/coroutines/channels/Send;", "pollResult", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "getPollResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "completeResumeSend", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class SendElement<E>
  extends Send
{
  public final CancellableContinuation<Unit> cont;
  private final E pollResult;
  
  public SendElement(E paramE, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    this.pollResult = paramE;
    this.cont = paramCancellableContinuation;
  }
  
  public void completeResumeSend()
  {
    this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
  }
  
  public E getPollResult()
  {
    return (E)this.pollResult;
  }
  
  public void resumeSendClosed(Closed<?> paramClosed)
  {
    Continuation localContinuation = (Continuation)this.cont;
    Result.Companion localCompanion = Result.Companion;
    localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramClosed.getSendException())));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = DebugStringsKt.getClassSimpleName(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append('@');
    str = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + '(' + getPollResult() + ')';
  }
  
  public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
  {
    CancellableContinuation localCancellableContinuation = this.cont;
    Unit localUnit = Unit.INSTANCE;
    if (paramPrepareOp == null) {
      localObject = null;
    } else {
      localObject = paramPrepareOp.desc;
    }
    Object localObject = localCancellableContinuation.tryResume(localUnit, localObject);
    if (localObject == null) {
      return null;
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (localObject == CancellableContinuationImplKt.RESUME_TOKEN) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (paramPrepareOp != null) {
      paramPrepareOp.finishPrepare();
    }
    return CancellableContinuationImplKt.RESUME_TOKEN;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/SendElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */