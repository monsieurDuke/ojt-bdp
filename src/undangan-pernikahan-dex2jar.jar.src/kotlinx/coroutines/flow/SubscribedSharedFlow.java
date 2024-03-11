package kotlinx.coroutines.flow;

import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.Continuation<*>;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000:\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\003\n\002\020\001\n\002\b\003\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002BE\022\f\020\003\032\b\022\004\022\0028\0000\002\022-\020\004\032)\b\001\022\n\022\b\022\004\022\0028\0000\006\022\n\022\b\022\004\022\0020\b0\007\022\006\022\004\030\0010\t0\005¢\006\002\b\nø\001\000¢\006\002\020\013J\037\020\021\032\0020\0222\f\020\023\032\b\022\004\022\0028\0000\006H@ø\001\000¢\006\002\020\024R:\020\004\032)\b\001\022\n\022\b\022\004\022\0028\0000\006\022\n\022\b\022\004\022\0020\b0\007\022\006\022\004\030\0010\t0\005¢\006\002\b\nX\004ø\001\000¢\006\004\n\002\020\fR\030\020\r\032\b\022\004\022\0028\0000\016X\005¢\006\006\032\004\b\017\020\020R\024\020\003\032\b\022\004\022\0028\0000\002X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\025"}, d2={"Lkotlinx/coroutines/flow/SubscribedSharedFlow;", "T", "Lkotlinx/coroutines/flow/SharedFlow;", "sharedFlow", "action", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/SharedFlow;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "collect", "", "collector", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class SubscribedSharedFlow<T>
  implements SharedFlow<T>
{
  private final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> action;
  private final SharedFlow<T> sharedFlow;
  
  public SubscribedSharedFlow(SharedFlow<? extends T> paramSharedFlow, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    this.sharedFlow = paramSharedFlow;
    this.action = paramFunction2;
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<?> paramContinuation)
  {
    if ((paramContinuation instanceof collect.1))
    {
      localObject1 = (collect.1)paramContinuation;
      if ((((collect.1)localObject1).label & 0x80000000) != 0)
      {
        ((collect.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<?>)localObject1;
        break label48;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      int label;
      Object result;
      final SubscribedSharedFlow<T> this$0;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return this.this$0.collect(null, (Continuation)this);
      }
    };
    label48:
    Object localObject2 = paramContinuation.result;
    Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      ResultKt.throwOnFailure(localObject2);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject2);
      localObject2 = this.sharedFlow;
      paramFlowCollector = (FlowCollector)new SubscribedFlowCollector(paramFlowCollector, this.action);
      paramContinuation.label = 1;
      if (((SharedFlow)localObject2).collect(paramFlowCollector, paramContinuation) == localObject1) {
        return localObject1;
      }
      break;
    }
    throw new KotlinNothingValueException();
  }
  
  public List<T> getReplayCache()
  {
    return this.sharedFlow.getReplayCache();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SubscribedSharedFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */