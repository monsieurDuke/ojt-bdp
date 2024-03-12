package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1={"\0000\n\002\b\004\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\032[\020\000\032\002H\001\"\004\b\000\020\001\"\004\b\001\020\0022\006\020\003\032\0020\0042\006\020\005\032\002H\0022\b\b\002\020\006\032\0020\0072\"\020\b\032\036\b\001\022\004\022\002H\002\022\n\022\b\022\004\022\002H\0010\n\022\006\022\004\030\0010\0070\tH@ø\001\000¢\006\002\020\013\032\036\020\f\032\b\022\004\022\002H\0010\r\"\004\b\000\020\001*\b\022\004\022\002H\0010\016H\000\032&\020\017\032\b\022\004\022\002H\0010\020\"\004\b\000\020\001*\b\022\004\022\002H\0010\0202\006\020\021\032\0020\004H\002\002\004\n\002\b\031¨\006\022"}, d2={"withContextUndispatched", "T", "V", "newContext", "Lkotlin/coroutines/CoroutineContext;", "value", "countOrElement", "", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asChannelFlow", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "Lkotlinx/coroutines/flow/Flow;", "withUndispatchedContextCollector", "Lkotlinx/coroutines/flow/FlowCollector;", "emitContext", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ChannelFlowKt
{
  public static final <T> ChannelFlow<T> asChannelFlow(Flow<? extends T> paramFlow)
  {
    ChannelFlow localChannelFlow1;
    if ((paramFlow instanceof ChannelFlow)) {
      localChannelFlow1 = (ChannelFlow)paramFlow;
    } else {
      localChannelFlow1 = null;
    }
    ChannelFlow localChannelFlow2 = localChannelFlow1;
    if (localChannelFlow1 == null) {
      localChannelFlow2 = (ChannelFlow)new ChannelFlowOperatorImpl(paramFlow, null, 0, null, 14, null);
    }
    return localChannelFlow2;
  }
  
  public static final <T, V> Object withContextUndispatched(CoroutineContext paramCoroutineContext, V paramV, Object paramObject, Function2<? super V, ? super Continuation<? super T>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    paramObject = ThreadContextKt.updateThreadContext(paramCoroutineContext, paramObject);
    try
    {
      Object localObject = new kotlinx/coroutines/flow/internal/StackFrameContinuation;
      ((StackFrameContinuation)localObject).<init>(paramContinuation, paramCoroutineContext);
      localObject = (Continuation)localObject;
      paramV = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction2, 2)).invoke(paramV, localObject);
      ThreadContextKt.restoreThreadContext(paramCoroutineContext, paramObject);
      if (paramV == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      return paramV;
    }
    finally
    {
      ThreadContextKt.restoreThreadContext(paramCoroutineContext, paramObject);
    }
  }
  
  private static final <T> FlowCollector<T> withUndispatchedContextCollector(FlowCollector<? super T> paramFlowCollector, CoroutineContext paramCoroutineContext)
  {
    boolean bool;
    if ((paramFlowCollector instanceof SendingCollector)) {
      bool = true;
    } else {
      bool = paramFlowCollector instanceof NopCollector;
    }
    if (!bool) {
      paramFlowCollector = (FlowCollector)new UndispatchedContextCollector(paramFlowCollector, paramCoroutineContext);
    }
    return paramFlowCollector;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/ChannelFlowKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */