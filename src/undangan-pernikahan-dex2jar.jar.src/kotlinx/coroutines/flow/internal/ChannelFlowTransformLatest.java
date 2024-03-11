package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000R\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\b\000\030\000*\004\b\000\020\001*\004\b\001\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\003Bx\022B\020\004\032>\b\001\022\n\022\b\022\004\022\0028\0010\006\022\023\022\0218\000¢\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\n\022\b\022\004\022\0020\0130\n\022\006\022\004\030\0010\f0\005¢\006\002\b\r\022\f\020\016\032\b\022\004\022\0028\0000\017\022\b\b\002\020\020\032\0020\021\022\b\b\002\020\022\032\0020\023\022\b\b\002\020\024\032\0020\025ø\001\000¢\006\002\020\026J&\020\030\032\b\022\004\022\0028\0010\0312\006\020\020\032\0020\0212\006\020\022\032\0020\0232\006\020\024\032\0020\025H\024J\037\020\032\032\0020\0132\f\020\033\032\b\022\004\022\0028\0010\006H@ø\001\000¢\006\002\020\034RO\020\004\032>\b\001\022\n\022\b\022\004\022\0028\0010\006\022\023\022\0218\000¢\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\n\022\b\022\004\022\0020\0130\n\022\006\022\004\030\0010\f0\005¢\006\002\b\rX\004ø\001\000¢\006\004\n\002\020\027\002\004\n\002\b\031¨\006\035"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelFlowTransformLatest;", "T", "R", "Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "transform", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/ParameterName;", "name", "value", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/jvm/functions/Function3;Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "Lkotlin/jvm/functions/Function3;", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flowCollect", "collector", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ChannelFlowTransformLatest<T, R>
  extends ChannelFlowOperator<T, R>
{
  private final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> transform;
  
  public ChannelFlowTransformLatest(Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction3, Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    super(paramFlow, paramCoroutineContext, paramInt, paramBufferOverflow);
    this.transform = paramFunction3;
  }
  
  protected ChannelFlow<R> create(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return (ChannelFlow)new ChannelFlowTransformLatest(this.transform, this.flow, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  protected Object flowCollect(final FlowCollector<? super R> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(paramFlowCollector instanceof SendingCollector))) {
      throw new AssertionError();
    }
    paramFlowCollector = CoroutineScopeKt.coroutineScope((Function2)new SuspendLambda(paramFlowCollector, null)
    {
      private Object L$0;
      int label;
      final ChannelFlowTransformLatest<T, R> this$0;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 3(this.this$0, paramFlowCollector, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(CoroutineScope paramAnonymousCoroutineScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((3)create(paramAnonymousCoroutineScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 1: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          final Object localObject2 = (CoroutineScope)this.L$0;
          Object localObject3 = new Ref.ObjectRef();
          paramAnonymousObject = this.this$0.flow;
          localObject3 = (FlowCollector)new FlowCollector()
          {
            final Ref.ObjectRef<Job> $previousFlow;
            
            public final Object emit(final T paramAnonymous2T, Continuation<? super Unit> paramAnonymous2Continuation)
            {
              if ((paramAnonymous2Continuation instanceof emit.1))
              {
                local1 = (emit.1)paramAnonymous2Continuation;
                if ((local1.label & 0x80000000) != 0)
                {
                  local1.label += Integer.MIN_VALUE;
                  break label46;
                }
              }
              ContinuationImpl local1 = new ContinuationImpl(paramAnonymous2Continuation)
              {
                Object L$0;
                Object L$1;
                Object L$2;
                int label;
                Object result;
                final ChannelFlowTransformLatest.flowCollect.3.1<T> this$0;
                
                public final Object invokeSuspend(Object paramAnonymous3Object)
                {
                  this.result = paramAnonymous3Object;
                  this.label |= 0x80000000;
                  return this.this$0.emit(null, (Continuation)this);
                }
              };
              label46:
              Object localObject1 = local1.result;
              Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
              switch (local1.label)
              {
              default: 
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
              case 1: 
                paramAnonymous2T = (Job)local1.L$2;
                paramAnonymous2T = local1.L$1;
                paramAnonymous2Continuation = (1)local1.L$0;
                ResultKt.throwOnFailure(localObject1);
                break;
              case 0: 
                ResultKt.throwOnFailure(localObject1);
                paramAnonymous2Continuation = this;
                localObject1 = (Job)paramAnonymous2Continuation.$previousFlow.element;
                if (localObject1 != null)
                {
                  ((Job)localObject1).cancel((CancellationException)new ChildCancelledException());
                  local1.L$0 = paramAnonymous2Continuation;
                  local1.L$1 = paramAnonymous2T;
                  local1.L$2 = localObject1;
                  local1.label = 1;
                  if (((Job)localObject1).join(local1) == localObject2) {
                    return localObject2;
                  }
                }
                break;
              }
              paramAnonymous2Continuation.$previousFlow.element = BuildersKt.launch$default(localObject2, null, CoroutineStart.UNDISPATCHED, (Function2)new SuspendLambda(paramAnonymous2Continuation.this$0, paramAnonymous2Continuation.$collector)
              {
                int label;
                final ChannelFlowTransformLatest<T, R> this$0;
                
                public final Continuation<Unit> create(Object paramAnonymous3Object, Continuation<?> paramAnonymous3Continuation)
                {
                  return (Continuation)new 2(this.this$0, this.$collector, paramAnonymous2T, paramAnonymous3Continuation);
                }
                
                public final Object invoke(CoroutineScope paramAnonymous3CoroutineScope, Continuation<? super Unit> paramAnonymous3Continuation)
                {
                  return ((2)create(paramAnonymous3CoroutineScope, paramAnonymous3Continuation)).invokeSuspend(Unit.INSTANCE);
                }
                
                public final Object invokeSuspend(Object paramAnonymous3Object)
                {
                  Object localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  switch (this.label)
                  {
                  default: 
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  case 1: 
                    ResultKt.throwOnFailure(paramAnonymous3Object);
                    break;
                  case 0: 
                    ResultKt.throwOnFailure(paramAnonymous3Object);
                    Function3 localFunction3 = ChannelFlowTransformLatest.access$getTransform$p(this.this$0);
                    FlowCollector localFlowCollector = this.$collector;
                    paramAnonymous3Object = paramAnonymous2T;
                    this.label = 1;
                    if (localFunction3.invoke(localFlowCollector, paramAnonymous3Object, this) == localObject) {
                      return localObject;
                    }
                    break;
                  }
                  return Unit.INSTANCE;
                }
              }, 1, null);
              return Unit.INSTANCE;
            }
          };
          localObject2 = (Continuation)this;
          this.label = 1;
          if (((Flow)paramAnonymousObject).collect((FlowCollector)localObject3, (Continuation)localObject2) == localObject1) {
            return localObject1;
          }
          break;
        }
        return Unit.INSTANCE;
      }
    }, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/ChannelFlowTransformLatest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */