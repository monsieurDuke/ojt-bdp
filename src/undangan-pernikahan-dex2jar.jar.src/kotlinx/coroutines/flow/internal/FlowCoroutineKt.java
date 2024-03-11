package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(d1={"\0000\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\002\032B\020\000\032\002H\001\"\004\b\000\020\0012)\b\001\020\002\032#\b\001\022\004\022\0020\004\022\n\022\b\022\004\022\002H\0010\005\022\006\022\004\030\0010\0060\003¢\006\002\b\007H@ø\001\000¢\006\002\020\b\032S\020\t\032\b\022\004\022\002H\0010\n\"\004\b\000\020\00125\b\001\020\002\032/\b\001\022\004\022\0020\004\022\n\022\b\022\004\022\002H\0010\f\022\n\022\b\022\004\022\0020\r0\005\022\006\022\004\030\0010\0060\013¢\006\002\b\007H\000ø\001\000¢\006\002\020\016\002\004\n\002\b\031¨\006\017"}, d2={"flowScope", "R", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "scopedFlow", "Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "(Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class FlowCoroutineKt
{
  public static final <R> Object flowScope(Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> paramFunction2, Continuation<? super R> paramContinuation)
  {
    FlowCoroutine localFlowCoroutine = new FlowCoroutine(paramContinuation.getContext(), paramContinuation);
    paramFunction2 = UndispatchedKt.startUndispatchedOrReturn((ScopeCoroutine)localFlowCoroutine, localFlowCoroutine, paramFunction2);
    if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction2;
  }
  
  public static final <R> Flow<R> scopedFlow(Function3<? super CoroutineScope, ? super FlowCollector<? super R>, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    (Flow)new Flow()
    {
      final Function3 $block$inlined;
      
      public Object collect(FlowCollector<? super R> paramAnonymousFlowCollector, Continuation<? super Unit> paramAnonymousContinuation)
      {
        paramAnonymousFlowCollector = FlowCoroutineKt.flowScope((Function2)new FlowCoroutineKt.scopedFlow.1.1(this.$block$inlined, paramAnonymousFlowCollector, null), paramAnonymousContinuation);
        if (paramAnonymousFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return paramAnonymousFlowCollector;
        }
        return Unit.INSTANCE;
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/FlowCoroutineKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */