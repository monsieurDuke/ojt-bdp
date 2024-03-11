package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000<\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\000\030\000*\004\b\000\020\0012\016\022\004\022\002H\001\022\004\022\002H\0010\002B1\022\f\020\003\032\b\022\004\022\0028\0000\004\022\b\b\002\020\005\032\0020\006\022\b\b\002\020\007\032\0020\b\022\b\b\002\020\t\032\0020\n¢\006\002\020\013J&\020\f\032\b\022\004\022\0028\0000\r2\006\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nH\024J\016\020\016\032\b\022\004\022\0028\0000\004H\026J\037\020\017\032\0020\0202\f\020\021\032\b\022\004\022\0028\0000\022H@ø\001\000¢\006\002\020\023\002\004\n\002\b\031¨\006\024"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelFlowOperatorImpl;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "dropChannelOperators", "flowCollect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ChannelFlowOperatorImpl<T>
  extends ChannelFlowOperator<T, T>
{
  public ChannelFlowOperatorImpl(Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    super(paramFlow, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return (ChannelFlow)new ChannelFlowOperatorImpl(this.flow, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public Flow<T> dropChannelOperators()
  {
    return this.flow;
  }
  
  protected Object flowCollect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = this.flow.collect(paramFlowCollector, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/ChannelFlowOperatorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */