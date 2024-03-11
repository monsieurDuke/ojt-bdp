package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.sync.Semaphore;
import kotlinx.coroutines.sync.SemaphoreKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000F\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B?\022\022\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0040\004\022\006\020\005\032\0020\006\022\b\b\002\020\007\032\0020\b\022\b\b\002\020\t\032\0020\006\022\b\b\002\020\n\032\0020\013¢\006\002\020\fJ\b\020\r\032\0020\016H\024J\037\020\017\032\0020\0202\f\020\021\032\b\022\004\022\0028\0000\022H@ø\001\000¢\006\002\020\023J&\020\024\032\b\022\004\022\0028\0000\0022\006\020\007\032\0020\b2\006\020\t\032\0020\0062\006\020\n\032\0020\013H\024J\026\020\025\032\b\022\004\022\0028\0000\0262\006\020\021\032\0020\027H\026R\016\020\005\032\0020\006X\004¢\006\002\n\000R\032\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0040\004X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\030"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelFlowMerge;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "concurrency", "", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/flow/Flow;ILkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "additionalToStringProps", "", "collectTo", "", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ChannelFlowMerge<T>
  extends ChannelFlow<T>
{
  private final int concurrency;
  private final Flow<Flow<T>> flow;
  
  public ChannelFlowMerge(Flow<? extends Flow<? extends T>> paramFlow, int paramInt1, CoroutineContext paramCoroutineContext, int paramInt2, BufferOverflow paramBufferOverflow)
  {
    super(paramCoroutineContext, paramInt2, paramBufferOverflow);
    this.flow = paramFlow;
    this.concurrency = paramInt1;
  }
  
  protected String additionalToStringProps()
  {
    String str = Intrinsics.stringPlus("concurrency=", Integer.valueOf(this.concurrency));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  protected Object collectTo(final ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    final Semaphore localSemaphore = SemaphoreKt.Semaphore$default(this.concurrency, 0, 2, null);
    final SendingCollector localSendingCollector = new SendingCollector((SendChannel)paramProducerScope);
    Job localJob = (Job)paramContinuation.getContext().get((CoroutineContext.Key)Job.Key);
    paramProducerScope = this.flow.collect((FlowCollector)new FlowCollector()
    {
      final Job $job;
      
      public final Object emit(Flow<? extends T> paramAnonymousFlow, Continuation<? super Unit> paramAnonymousContinuation)
      {
        if ((paramAnonymousContinuation instanceof emit.1))
        {
          localObject1 = (emit.1)paramAnonymousContinuation;
          if ((((emit.1)localObject1).label & 0x80000000) != 0)
          {
            ((emit.1)localObject1).label += Integer.MIN_VALUE;
            paramAnonymousContinuation = (Continuation<? super Unit>)localObject1;
            break label48;
          }
        }
        paramAnonymousContinuation = new ContinuationImpl(paramAnonymousContinuation)
        {
          Object L$0;
          Object L$1;
          int label;
          Object result;
          final ChannelFlowMerge.collectTo.2<T> this$0;
          
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            this.result = paramAnonymous2Object;
            this.label |= 0x80000000;
            return this.this$0.emit(null, (Continuation)this);
          }
        };
        label48:
        Object localObject1 = paramAnonymousContinuation.result;
        Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (paramAnonymousContinuation.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 1: 
          paramAnonymousFlow = (Flow)paramAnonymousContinuation.L$1;
          paramAnonymousContinuation = (2)paramAnonymousContinuation.L$0;
          ResultKt.throwOnFailure(localObject1);
          break;
        case 0: 
          ResultKt.throwOnFailure(localObject1);
          localObject1 = this.$job;
          if (localObject1 != null) {
            JobKt.ensureActive((Job)localObject1);
          }
          localObject1 = localSemaphore;
          paramAnonymousContinuation.L$0 = this;
          paramAnonymousContinuation.L$1 = paramAnonymousFlow;
          paramAnonymousContinuation.label = 1;
          if (((Semaphore)localObject1).acquire(paramAnonymousContinuation) == localObject2) {
            return localObject2;
          }
          paramAnonymousContinuation = this;
        }
        BuildersKt.launch$default((CoroutineScope)paramProducerScope, null, null, (Function2)new SuspendLambda(paramAnonymousFlow, localSendingCollector)
        {
          final Flow<T> $inner;
          int label;
          
          public final Continuation<Unit> create(Object paramAnonymous2Object, Continuation<?> paramAnonymous2Continuation)
          {
            return (Continuation)new 1(this.$inner, this.$collector, this.$semaphore, paramAnonymous2Continuation);
          }
          
          public final Object invoke(CoroutineScope paramAnonymous2CoroutineScope, Continuation<? super Unit> paramAnonymous2Continuation)
          {
            return ((1)create(paramAnonymous2CoroutineScope, paramAnonymous2Continuation)).invokeSuspend(Unit.INSTANCE);
          }
          
          /* Error */
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            // Byte code:
            //   0: invokestatic 96	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
            //   3: astore_2
            //   4: aload_0
            //   5: getfield 98	kotlinx/coroutines/flow/internal/ChannelFlowMerge$collectTo$2$1:label	I
            //   8: tableswitch	default:+24->32, 0:+45->53, 1:+34->42
            //   32: new 100	java/lang/IllegalStateException
            //   35: dup
            //   36: ldc 102
            //   38: invokespecial 105	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
            //   41: athrow
            //   42: aload_1
            //   43: invokestatic 111	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
            //   46: goto +52 -> 98
            //   49: astore_1
            //   50: goto +62 -> 112
            //   53: aload_1
            //   54: invokestatic 111	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
            //   57: aload_0
            //   58: getfield 55	kotlinx/coroutines/flow/internal/ChannelFlowMerge$collectTo$2$1:$inner	Lkotlinx/coroutines/flow/Flow;
            //   61: astore 4
            //   63: aload_0
            //   64: getfield 57	kotlinx/coroutines/flow/internal/ChannelFlowMerge$collectTo$2$1:$collector	Lkotlinx/coroutines/flow/internal/SendingCollector;
            //   67: checkcast 113	kotlinx/coroutines/flow/FlowCollector
            //   70: astore_3
            //   71: aload_0
            //   72: checkcast 71	kotlin/coroutines/Continuation
            //   75: astore_1
            //   76: aload_0
            //   77: iconst_1
            //   78: putfield 98	kotlinx/coroutines/flow/internal/ChannelFlowMerge$collectTo$2$1:label	I
            //   81: aload 4
            //   83: aload_3
            //   84: aload_1
            //   85: invokeinterface 119 3 0
            //   90: astore_1
            //   91: aload_1
            //   92: aload_2
            //   93: if_acmpne +5 -> 98
            //   96: aload_2
            //   97: areturn
            //   98: aload_0
            //   99: getfield 59	kotlinx/coroutines/flow/internal/ChannelFlowMerge$collectTo$2$1:$semaphore	Lkotlinx/coroutines/sync/Semaphore;
            //   102: invokeinterface 125 1 0
            //   107: getstatic 87	kotlin/Unit:INSTANCE	Lkotlin/Unit;
            //   110: areturn
            //   111: astore_1
            //   112: aload_0
            //   113: getfield 59	kotlinx/coroutines/flow/internal/ChannelFlowMerge$collectTo$2$1:$semaphore	Lkotlinx/coroutines/sync/Semaphore;
            //   116: invokeinterface 125 1 0
            //   121: aload_1
            //   122: athrow
            // Local variable table:
            //   start	length	slot	name	signature
            //   0	123	0	this	1
            //   0	123	1	paramAnonymous2Object	Object
            //   3	94	2	localObject	Object
            //   70	14	3	localFlowCollector	FlowCollector
            //   61	21	4	localFlow	Flow
            // Exception table:
            //   from	to	target	type
            //   42	46	49	finally
            //   57	91	111	finally
          }
        }, 3, null);
        return Unit.INSTANCE;
      }
    }, paramContinuation);
    if (paramProducerScope == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramProducerScope;
    }
    return Unit.INSTANCE;
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return (ChannelFlow)new ChannelFlowMerge(this.flow, this.concurrency, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public ReceiveChannel<T> produceImpl(CoroutineScope paramCoroutineScope)
  {
    return ProduceKt.produce(paramCoroutineScope, this.context, this.capacity, getCollectToFun$kotlinx_coroutines_core());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/ChannelFlowMerge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */