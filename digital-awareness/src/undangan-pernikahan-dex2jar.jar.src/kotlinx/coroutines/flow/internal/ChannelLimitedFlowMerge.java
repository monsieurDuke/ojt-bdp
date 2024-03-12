package kotlinx.coroutines.flow.internal;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\034\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B7\022\022\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0050\004\022\b\b\002\020\006\032\0020\007\022\b\b\002\020\b\032\0020\t\022\b\b\002\020\n\032\0020\013¢\006\002\020\fJ\037\020\r\032\0020\0162\f\020\017\032\b\022\004\022\0028\0000\020H@ø\001\000¢\006\002\020\021J&\020\022\032\b\022\004\022\0028\0000\0022\006\020\006\032\0020\0072\006\020\b\032\0020\t2\006\020\n\032\0020\013H\024J\026\020\023\032\b\022\004\022\0028\0000\0242\006\020\017\032\0020\025H\026R\032\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0050\004X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\026"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelLimitedFlowMerge;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flows", "", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Ljava/lang/Iterable;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "collectTo", "", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ChannelLimitedFlowMerge<T>
  extends ChannelFlow<T>
{
  private final Iterable<Flow<T>> flows;
  
  public ChannelLimitedFlowMerge(Iterable<? extends Flow<? extends T>> paramIterable, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    super(paramCoroutineContext, paramInt, paramBufferOverflow);
    this.flows = paramIterable;
  }
  
  protected Object collectTo(ProducerScope<? super T> paramProducerScope, final Continuation<? super Unit> paramContinuation)
  {
    paramContinuation = new SendingCollector((SendChannel)paramProducerScope);
    Iterator localIterator = this.flows.iterator();
    while (localIterator.hasNext())
    {
      Flow localFlow = (Flow)localIterator.next();
      BuildersKt.launch$default((CoroutineScope)paramProducerScope, null, null, (Function2)new SuspendLambda(localFlow, paramContinuation)
      {
        final Flow<T> $flow;
        int label;
        
        public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
        {
          return (Continuation)new 1(this.$flow, paramContinuation, paramAnonymousContinuation);
        }
        
        public final Object invoke(CoroutineScope paramAnonymousCoroutineScope, Continuation<? super Unit> paramAnonymousContinuation)
        {
          return ((1)create(paramAnonymousCoroutineScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
        }
        
        public final Object invokeSuspend(Object paramAnonymousObject)
        {
          Object localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
          switch (this.label)
          {
          default: 
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          case 1: 
            ResultKt.throwOnFailure(paramAnonymousObject);
            break;
          case 0: 
            ResultKt.throwOnFailure(paramAnonymousObject);
            Flow localFlow = this.$flow;
            FlowCollector localFlowCollector = (FlowCollector)paramContinuation;
            paramAnonymousObject = (Continuation)this;
            this.label = 1;
            if (localFlow.collect(localFlowCollector, (Continuation)paramAnonymousObject) == localObject) {
              return localObject;
            }
            break;
          }
          return Unit.INSTANCE;
        }
      }, 3, null);
    }
    return Unit.INSTANCE;
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return (ChannelFlow)new ChannelLimitedFlowMerge(this.flows, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public ReceiveChannel<T> produceImpl(CoroutineScope paramCoroutineScope)
  {
    return ProduceKt.produce(paramCoroutineScope, this.context, this.capacity, getCollectToFun$kotlinx_coroutines_core());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/ChannelLimitedFlowMerge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */