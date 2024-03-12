package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.internal.ChannelFlow;

@Metadata(d1={"\000@\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\007\n\002\020\016\n\000\b\022\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002BU\022-\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004¢\006\002\b\t\022\b\b\002\020\n\032\0020\013\022\b\b\002\020\f\032\0020\r\022\b\b\002\020\016\032\0020\017ø\001\000¢\006\002\020\020J\037\020\022\032\0020\0072\f\020\023\032\b\022\004\022\0028\0000\005H@ø\001\000¢\006\002\020\024J&\020\025\032\b\022\004\022\0028\0000\0022\006\020\n\032\0020\0132\006\020\f\032\0020\r2\006\020\016\032\0020\017H\024J\b\020\026\032\0020\027H\026R:\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004¢\006\002\b\tX\004ø\001\000¢\006\004\n\002\020\021\002\004\n\002\b\031¨\006\030"}, d2={"Lkotlinx/coroutines/flow/ChannelFlowBuilder;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "Lkotlin/jvm/functions/Function2;", "collectTo", "scope", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
class ChannelFlowBuilder<T>
  extends ChannelFlow<T>
{
  private final Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> block;
  
  public ChannelFlowBuilder(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    super(paramCoroutineContext, paramInt, paramBufferOverflow);
    this.block = paramFunction2;
  }
  
  protected Object collectTo(ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    return collectTo$suspendImpl(this, paramProducerScope, paramContinuation);
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return (ChannelFlow)new ChannelFlowBuilder(this.block, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public String toString()
  {
    return "block[" + this.block + "] -> " + super.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/ChannelFlowBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */