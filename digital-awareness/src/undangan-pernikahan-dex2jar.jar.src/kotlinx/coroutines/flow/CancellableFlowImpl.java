package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlinx.coroutines.JobKt;

@Metadata(d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\023\022\f\020\003\032\b\022\004\022\0028\0000\004¢\006\002\020\005J\037\020\006\032\0020\0072\f\020\b\032\b\022\004\022\0028\0000\tH@ø\001\000¢\006\002\020\nR\024\020\003\032\b\022\004\022\0028\0000\004X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\013"}, d2={"Lkotlinx/coroutines/flow/CancellableFlowImpl;", "T", "Lkotlinx/coroutines/flow/CancellableFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;)V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class CancellableFlowImpl<T>
  implements CancellableFlow<T>
{
  private final Flow<T> flow;
  
  public CancellableFlowImpl(Flow<? extends T> paramFlow)
  {
    this.flow = paramFlow;
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = this.flow.collect((FlowCollector)new FlowCollector()
    {
      final FlowCollector<T> $collector;
      
      public final Object emit(T paramAnonymousT, Continuation<? super Unit> paramAnonymousContinuation)
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
          int label;
          Object result;
          final CancellableFlowImpl.collect.2<T> this$0;
          
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            this.result = paramAnonymous2Object;
            this.label |= 0x80000000;
            return this.this$0.emit(null, (Continuation)this);
          }
        };
        label48:
        Object localObject2 = paramAnonymousContinuation.result;
        Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (paramAnonymousContinuation.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 1: 
          ResultKt.throwOnFailure(localObject2);
          break;
        case 0: 
          ResultKt.throwOnFailure(localObject2);
          JobKt.ensureActive(paramAnonymousContinuation.getContext());
          localObject2 = this.$collector;
          paramAnonymousContinuation.label = 1;
          if (((FlowCollector)localObject2).emit(paramAnonymousT, paramAnonymousContinuation) == localObject1) {
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/CancellableFlowImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */