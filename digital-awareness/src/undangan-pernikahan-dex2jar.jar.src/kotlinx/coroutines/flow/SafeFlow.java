package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000(\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\006\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B7\022-\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004¢\006\002\b\tø\001\000¢\006\002\020\nJ\037\020\f\032\0020\0072\f\020\r\032\b\022\004\022\0028\0000\005H@ø\001\000¢\006\002\020\016R:\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004¢\006\002\b\tX\004ø\001\000¢\006\004\n\002\020\013\002\004\n\002\b\031¨\006\017"}, d2={"Lkotlinx/coroutines/flow/SafeFlow;", "T", "Lkotlinx/coroutines/flow/AbstractFlow;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "collectSafely", "collector", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class SafeFlow<T>
  extends AbstractFlow<T>
{
  private final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> block;
  
  public SafeFlow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    this.block = paramFunction2;
  }
  
  public Object collectSafely(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = this.block.invoke(paramFlowCollector, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SafeFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */