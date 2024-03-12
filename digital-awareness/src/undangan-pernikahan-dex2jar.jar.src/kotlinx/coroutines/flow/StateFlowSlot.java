package kotlinx.coroutines.flow;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

@Metadata(d1={"\000.\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\006\n\002\030\002\b\002\030\0002\f\022\b\022\006\022\002\b\0030\0030\023B\007¢\006\004\b\001\020\002J\033\020\006\032\0020\0052\n\020\004\032\006\022\002\b\0030\003H\026¢\006\004\b\006\020\007J\023\020\t\032\0020\bH@ø\001\000¢\006\004\b\t\020\nJ)\020\r\032\020\022\f\022\n\022\004\022\0020\b\030\0010\f0\0132\n\020\004\032\006\022\002\b\0030\003H\026¢\006\004\b\r\020\016J\r\020\017\032\0020\b¢\006\004\b\017\020\002J\r\020\020\032\0020\005¢\006\004\b\020\020\021\002\004\n\002\b\031¨\006\022"}, d2={"Lkotlinx/coroutines/flow/StateFlowSlot;", "<init>", "()V", "Lkotlinx/coroutines/flow/StateFlowImpl;", "flow", "", "allocateLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)Z", "", "awaitPending", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "Lkotlin/coroutines/Continuation;", "freeLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)[Lkotlin/coroutines/Continuation;", "makePending", "takePending", "()Z", "kotlinx-coroutines-core", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;"}, k=1, mv={1, 6, 0}, xi=48)
final class StateFlowSlot
  extends AbstractSharedFlowSlot<StateFlowImpl<?>>
{
  static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(StateFlowSlot.class, Object.class, "_state");
  volatile Object _state = null;
  
  public boolean allocateLocked(StateFlowImpl<?> paramStateFlowImpl)
  {
    if (this._state != null) {
      return false;
    }
    this._state = StateFlowKt.access$getNONE$p();
    return true;
  }
  
  public final Object awaitPending(Continuation<? super Unit> paramContinuation)
  {
    Object localObject1 = IntrinsicsKt.intercepted(paramContinuation);
    int i = 1;
    localObject1 = new CancellableContinuationImpl((Continuation)localObject1, 1);
    ((CancellableContinuationImpl)localObject1).initCancellability();
    Object localObject2 = (CancellableContinuation)localObject1;
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(this._state instanceof CancellableContinuationImpl ^ true))) {
      throw new AssertionError();
    }
    if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, StateFlowKt.access$getNONE$p(), localObject2))
    {
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (this._state != StateFlowKt.access$getPENDING$p()) {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      localObject2 = (Continuation)localObject2;
      Result.Companion localCompanion = Result.Companion;
      ((Continuation)localObject2).resumeWith(Result.constructor-impl(Unit.INSTANCE));
    }
    localObject1 = ((CancellableContinuationImpl)localObject1).getResult();
    if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return localObject1;
    }
    return Unit.INSTANCE;
  }
  
  public Continuation<Unit>[] freeLocked(StateFlowImpl<?> paramStateFlowImpl)
  {
    this._state = null;
    return AbstractSharedFlowKt.EMPTY_RESUMES;
  }
  
  public final void makePending()
  {
    for (;;)
    {
      Object localObject = this._state;
      if (localObject == null) {
        return;
      }
      if (localObject == StateFlowKt.access$getPENDING$p()) {
        return;
      }
      if (localObject == StateFlowKt.access$getNONE$p())
      {
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, StateFlowKt.access$getPENDING$p())) {}
      }
      else if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, StateFlowKt.access$getNONE$p()))
      {
        localObject = (Continuation)localObject;
        Result.Companion localCompanion = Result.Companion;
        ((Continuation)localObject).resumeWith(Result.constructor-impl(Unit.INSTANCE));
        return;
      }
    }
  }
  
  public final boolean takePending()
  {
    Object localObject = _state$FU.getAndSet(this, StateFlowKt.access$getNONE$p());
    Intrinsics.checkNotNull(localObject);
    boolean bool2 = DebugKt.getASSERTIONS_ENABLED();
    boolean bool1 = true;
    if ((bool2) && (!(localObject instanceof CancellableContinuationImpl ^ true))) {
      throw new AssertionError();
    }
    if (localObject != StateFlowKt.access$getPENDING$p()) {
      bool1 = false;
    }
    return bool1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/StateFlowSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */