package kotlinx.coroutines.flow;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.SendingCollector;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\\\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\005\b\002\030\000*\004\b\000\020\0012\b\022\004\022\0028\0000\032B;\022\f\020\003\032\b\022\004\022\0028\0000\002\022\006\020\005\032\0020\004\022\b\b\002\020\007\032\0020\006\022\b\b\002\020\t\032\0020\b\022\b\b\002\020\013\032\0020\n¢\006\004\b\f\020\rJ\017\020\017\032\0020\016H\024¢\006\004\b\017\020\020J!\020\024\032\0020\0232\f\020\022\032\b\022\004\022\0028\0000\021H@ø\001\000¢\006\004\b\024\020\025J!\020\030\032\0020\0232\f\020\027\032\b\022\004\022\0028\0000\026H@ø\001\000¢\006\004\b\030\020\031J-\020\033\032\b\022\004\022\0028\0000\0322\006\020\007\032\0020\0062\006\020\t\032\0020\b2\006\020\013\032\0020\nH\024¢\006\004\b\033\020\034J\025\020\036\032\b\022\004\022\0028\0000\035H\026¢\006\004\b\036\020\037J\017\020 \032\0020\023H\002¢\006\004\b \020!J\035\020#\032\b\022\004\022\0028\0000\0022\006\020\027\032\0020\"H\026¢\006\004\b#\020$R\032\020\003\032\b\022\004\022\0028\0000\0028\002X\004¢\006\006\n\004\b\003\020%R\024\020\005\032\0020\0048\002X\004¢\006\006\n\004\b\005\020&\002\004\n\002\b\031¨\006'"}, d2={"Lkotlinx/coroutines/flow/ChannelAsFlow;", "T", "Lkotlinx/coroutines/channels/ReceiveChannel;", "channel", "", "consume", "Lkotlin/coroutines/CoroutineContext;", "context", "", "capacity", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "<init>", "(Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "", "additionalToStringProps", "()Ljava/lang/String;", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "", "collect", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ProducerScope;", "scope", "collectTo", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "create", "(Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)Lkotlinx/coroutines/flow/internal/ChannelFlow;", "Lkotlinx/coroutines/flow/Flow;", "dropChannelOperators", "()Lkotlinx/coroutines/flow/Flow;", "markConsumed", "()V", "Lkotlinx/coroutines/CoroutineScope;", "produceImpl", "(Lkotlinx/coroutines/CoroutineScope;)Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Z", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class ChannelAsFlow<T>
  extends ChannelFlow<T>
{
  private static final AtomicIntegerFieldUpdater consumed$FU = AtomicIntegerFieldUpdater.newUpdater(ChannelAsFlow.class, "consumed");
  private final ReceiveChannel<T> channel;
  private final boolean consume;
  private volatile int consumed;
  
  public ChannelAsFlow(ReceiveChannel<? extends T> paramReceiveChannel, boolean paramBoolean, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    super(paramCoroutineContext, paramInt, paramBufferOverflow);
    this.channel = paramReceiveChannel;
    this.consume = paramBoolean;
    this.consumed = 0;
  }
  
  private final void markConsumed()
  {
    if (this.consume)
    {
      AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = consumed$FU;
      int i = 1;
      if (localAtomicIntegerFieldUpdater.getAndSet(this, 1) != 0) {
        i = 0;
      }
      if (i == 0) {
        throw new IllegalStateException("ReceiveChannel.consumeAsFlow can be collected just once".toString());
      }
    }
  }
  
  protected String additionalToStringProps()
  {
    String str = Intrinsics.stringPlus("channel=", this.channel);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    if (this.capacity == -3)
    {
      markConsumed();
      paramFlowCollector = FlowKt__ChannelsKt.access$emitAllImpl$FlowKt__ChannelsKt(paramFlowCollector, this.channel, this.consume, paramContinuation);
      if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramFlowCollector;
      }
    }
    for (paramFlowCollector = Unit.INSTANCE;; paramFlowCollector = Unit.INSTANCE)
    {
      return paramFlowCollector;
      paramFlowCollector = super.collect(paramFlowCollector, paramContinuation);
      if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramFlowCollector;
      }
    }
  }
  
  protected Object collectTo(ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    paramProducerScope = FlowKt__ChannelsKt.access$emitAllImpl$FlowKt__ChannelsKt((FlowCollector)new SendingCollector((SendChannel)paramProducerScope), this.channel, this.consume, paramContinuation);
    if (paramProducerScope == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramProducerScope;
    }
    return Unit.INSTANCE;
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return (ChannelFlow)new ChannelAsFlow(this.channel, this.consume, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public Flow<T> dropChannelOperators()
  {
    return (Flow)new ChannelAsFlow(this.channel, this.consume, null, 0, null, 28, null);
  }
  
  public ReceiveChannel<T> produceImpl(CoroutineScope paramCoroutineScope)
  {
    markConsumed();
    if (this.capacity == -3) {
      paramCoroutineScope = this.channel;
    } else {
      paramCoroutineScope = super.produceImpl(paramCoroutineScope);
    }
    return paramCoroutineScope;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/ChannelAsFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */