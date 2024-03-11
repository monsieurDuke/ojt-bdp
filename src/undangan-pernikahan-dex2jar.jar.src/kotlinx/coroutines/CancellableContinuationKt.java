package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(d1={"\0000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032\"\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\004H\000\0323\020\005\032\002H\002\"\004\b\000\020\0022\032\b\004\020\006\032\024\022\n\022\b\022\004\022\002H\0020\b\022\004\022\0020\t0\007HHø\001\000¢\006\002\020\n\0323\020\013\032\002H\002\"\004\b\000\020\0022\032\b\004\020\006\032\024\022\n\022\b\022\004\022\002H\0020\b\022\004\022\0020\t0\007HHø\001\000¢\006\002\020\n\032\030\020\f\032\0020\t*\006\022\002\b\0030\b2\006\020\r\032\0020\016H\007\032\030\020\017\032\0020\t*\006\022\002\b\0030\b2\006\020\020\032\0020\021H\000\002\004\n\002\b\031¨\006\022"}, d2={"getOrCreateCancellableContinuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "T", "delegate", "Lkotlin/coroutines/Continuation;", "suspendCancellableCoroutine", "block", "Lkotlin/Function1;", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "suspendCancellableCoroutineReusable", "disposeOnCancellation", "handle", "Lkotlinx/coroutines/DisposableHandle;", "removeOnCancellation", "node", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CancellableContinuationKt
{
  public static final void disposeOnCancellation(CancellableContinuation<?> paramCancellableContinuation, DisposableHandle paramDisposableHandle)
  {
    paramCancellableContinuation.invokeOnCancellation((Function1)new DisposeOnCancel(paramDisposableHandle));
  }
  
  public static final <T> CancellableContinuationImpl<T> getOrCreateCancellableContinuation(Continuation<? super T> paramContinuation)
  {
    if (!(paramContinuation instanceof DispatchedContinuation)) {
      return new CancellableContinuationImpl(paramContinuation, 1);
    }
    CancellableContinuationImpl localCancellableContinuationImpl = ((DispatchedContinuation)paramContinuation).claimReusableCancellableContinuation();
    if (localCancellableContinuationImpl == null) {}
    while (!localCancellableContinuationImpl.resetStateReusable())
    {
      localCancellableContinuationImpl = null;
      break;
    }
    if (localCancellableContinuationImpl == null) {
      return new CancellableContinuationImpl(paramContinuation, 2);
    }
    return localCancellableContinuationImpl;
  }
  
  public static final void removeOnCancellation(CancellableContinuation<?> paramCancellableContinuation, LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    paramCancellableContinuation.invokeOnCancellation((Function1)new RemoveOnCancel(paramLockFreeLinkedListNode));
  }
  
  public static final <T> Object suspendCancellableCoroutine(Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    localCancellableContinuationImpl.initCancellability();
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction1;
  }
  
  private static final <T> Object suspendCancellableCoroutine$$forInline(Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    InlineMarker.mark(0);
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    localCancellableContinuationImpl.initCancellability();
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    InlineMarker.mark(1);
    return paramFunction1;
  }
  
  public static final <T> Object suspendCancellableCoroutineReusable(Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction1;
  }
  
  private static final <T> Object suspendCancellableCoroutineReusable$$forInline(Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    InlineMarker.mark(0);
    CancellableContinuationImpl localCancellableContinuationImpl = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    InlineMarker.mark(1);
    return paramFunction1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CancellableContinuationKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */