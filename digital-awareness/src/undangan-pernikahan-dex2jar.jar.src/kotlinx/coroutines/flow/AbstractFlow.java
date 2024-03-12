package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.internal.SafeCollector;

@Metadata(d1={"\000 \n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\003\b'\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\005¢\006\002\020\004J\037\020\005\032\0020\0062\f\020\007\032\b\022\004\022\0028\0000\bH@ø\001\000¢\006\002\020\tJ\037\020\n\032\0020\0062\f\020\007\032\b\022\004\022\0028\0000\bH¦@ø\001\000¢\006\002\020\t\002\004\n\002\b\031¨\006\013"}, d2={"Lkotlinx/coroutines/flow/AbstractFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "()V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectSafely", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AbstractFlow<T>
  implements Flow<T>, CancellableFlow<T>
{
  public final Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    if ((paramContinuation instanceof collect.1))
    {
      localObject1 = (collect.1)paramContinuation;
      if ((((collect.1)localObject1).label & 0x80000000) != 0)
      {
        ((collect.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super Unit>)localObject1;
        break label48;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      int label;
      Object result;
      final AbstractFlow<T> this$0;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return this.this$0.collect(null, (Continuation)this);
      }
    };
    label48:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      paramContinuation = (SafeCollector)paramContinuation.L$0;
      paramFlowCollector = paramContinuation;
    }
    try
    {
      ResultKt.throwOnFailure(localObject1);
      break label175;
      ResultKt.throwOnFailure(localObject1);
      localObject1 = new SafeCollector(paramFlowCollector, paramContinuation.getContext());
      paramFlowCollector = (FlowCollector<? super T>)localObject1;
      Object localObject3 = (FlowCollector)localObject1;
      paramFlowCollector = (FlowCollector<? super T>)localObject1;
      paramContinuation.L$0 = localObject1;
      paramFlowCollector = (FlowCollector<? super T>)localObject1;
      paramContinuation.label = 1;
      paramFlowCollector = (FlowCollector<? super T>)localObject1;
      localObject3 = collectSafely((FlowCollector)localObject3, paramContinuation);
      paramContinuation = (Continuation<? super Unit>)localObject1;
      if (localObject3 == localObject2) {
        return localObject2;
      }
      label175:
      return Unit.INSTANCE;
    }
    finally
    {
      paramFlowCollector.releaseIntercepted();
    }
  }
  
  public abstract Object collectSafely(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/AbstractFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */