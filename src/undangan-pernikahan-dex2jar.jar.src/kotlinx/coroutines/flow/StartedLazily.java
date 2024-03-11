package kotlinx.coroutines.flow;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref.BooleanRef;

@Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\b\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\005¢\006\002\020\002J\034\020\003\032\b\022\004\022\0020\0050\0042\f\020\006\032\b\022\004\022\0020\b0\007H\026J\b\020\t\032\0020\nH\026¨\006\013"}, d2={"Lkotlinx/coroutines/flow/StartedLazily;", "Lkotlinx/coroutines/flow/SharingStarted;", "()V", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class StartedLazily
  implements SharingStarted
{
  public Flow<SharingCommand> command(StateFlow<Integer> paramStateFlow)
  {
    FlowKt.flow((Function2)new SuspendLambda(paramStateFlow, null)
    {
      final StateFlow<Integer> $subscriptionCount;
      private Object L$0;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$subscriptionCount, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(FlowCollector<? super SharingCommand> paramAnonymousFlowCollector, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousFlowCollector, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
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
          final Object localObject3 = (FlowCollector)this.L$0;
          Object localObject2 = new Ref.BooleanRef();
          paramAnonymousObject = this.$subscriptionCount;
          localObject2 = (FlowCollector)new FlowCollector()
          {
            final Ref.BooleanRef $started;
            
            public final Object emit(int paramAnonymous2Int, Continuation<? super Unit> paramAnonymous2Continuation)
            {
              if ((paramAnonymous2Continuation instanceof emit.1))
              {
                localObject1 = (emit.1)paramAnonymous2Continuation;
                if ((((emit.1)localObject1).label & 0x80000000) != 0)
                {
                  ((emit.1)localObject1).label += Integer.MIN_VALUE;
                  paramAnonymous2Continuation = (Continuation<? super Unit>)localObject1;
                  break label48;
                }
              }
              paramAnonymous2Continuation = new ContinuationImpl(paramAnonymous2Continuation)
              {
                int label;
                Object result;
                final StartedLazily.command.1.1<T> this$0;
                
                public final Object invokeSuspend(Object paramAnonymous3Object)
                {
                  this.result = paramAnonymous3Object;
                  this.label |= 0x80000000;
                  return this.this$0.emit(0, (Continuation)this);
                }
              };
              label48:
              Object localObject2 = paramAnonymous2Continuation.result;
              Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
              switch (paramAnonymous2Continuation.label)
              {
              default: 
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
              case 1: 
                ResultKt.throwOnFailure(localObject2);
                break;
              case 0: 
                ResultKt.throwOnFailure(localObject2);
                if ((paramAnonymous2Int > 0) && (!this.$started.element))
                {
                  this.$started.element = true;
                  localObject2 = localObject3;
                  SharingCommand localSharingCommand = SharingCommand.START;
                  paramAnonymous2Continuation.label = 1;
                  if (((FlowCollector)localObject2).emit(localSharingCommand, paramAnonymous2Continuation) == localObject1) {
                    return localObject1;
                  }
                }
                break;
              }
              for (;;)
              {
                return Unit.INSTANCE;
              }
            }
          };
          localObject3 = (Continuation)this;
          this.label = 1;
          if (((StateFlow)paramAnonymousObject).collect((FlowCollector)localObject2, (Continuation)localObject3) == localObject1) {
            return localObject1;
          }
          break;
        }
        throw new KotlinNothingValueException();
      }
    });
  }
  
  public String toString()
  {
    return "SharingStarted.Lazily";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/StartedLazily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */