package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

@Metadata(d1={"\000\n\n\000\n\002\020\002\n\002\b\002\032\021\020\000\032\0020\001H@ø\001\000¢\006\002\020\002\002\004\n\002\b\031¨\006\003"}, d2={"yield", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class YieldKt
{
  public static final Object yield(Continuation<? super Unit> paramContinuation)
  {
    CoroutineContext localCoroutineContext = paramContinuation.getContext();
    JobKt.ensureActive(localCoroutineContext);
    Object localObject = IntrinsicsKt.intercepted(paramContinuation);
    if ((localObject instanceof DispatchedContinuation)) {
      localObject = (DispatchedContinuation)localObject;
    } else {
      localObject = null;
    }
    if (localObject == null)
    {
      localObject = Unit.INSTANCE;
    }
    else
    {
      if (((DispatchedContinuation)localObject).dispatcher.isDispatchNeeded(localCoroutineContext))
      {
        ((DispatchedContinuation)localObject).dispatchYield$kotlinx_coroutines_core(localCoroutineContext, Unit.INSTANCE);
      }
      else
      {
        YieldContext localYieldContext = new YieldContext();
        ((DispatchedContinuation)localObject).dispatchYield$kotlinx_coroutines_core(localCoroutineContext.plus((CoroutineContext)localYieldContext), Unit.INSTANCE);
        if (localYieldContext.dispatcherWasUnconfined)
        {
          if (DispatchedContinuationKt.yieldUndispatched((DispatchedContinuation)localObject)) {
            localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
          } else {
            localObject = Unit.INSTANCE;
          }
          break label123;
        }
      }
      localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
    label123:
    if (localObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    if (localObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return localObject;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/YieldKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */