package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

@Metadata(d1={"\000:\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002Be\022\f\020\003\032\b\022\004\022\0028\0000\002\022\024\020\004\032\020\022\004\022\0028\000\022\006\022\004\030\0010\0060\005\022:\020\007\0326\022\025\022\023\030\0010\006¢\006\f\b\t\022\b\b\n\022\004\b\b(\013\022\025\022\023\030\0010\006¢\006\f\b\t\022\b\b\n\022\004\b\b(\f\022\004\022\0020\r0\b¢\006\002\020\016J\037\020\017\032\0020\0202\f\020\021\032\b\022\004\022\0028\0000\022H@ø\001\000¢\006\002\020\023RD\020\007\0326\022\025\022\023\030\0010\006¢\006\f\b\t\022\b\b\n\022\004\b\b(\013\022\025\022\023\030\0010\006¢\006\f\b\t\022\b\b\n\022\004\b\b(\f\022\004\022\0020\r0\b8\006X\004¢\006\002\n\000R\036\020\004\032\020\022\004\022\0028\000\022\006\022\004\030\0010\0060\0058\006X\004¢\006\002\n\000R\024\020\003\032\b\022\004\022\0028\0000\002X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\024"}, d2={"Lkotlinx/coroutines/flow/DistinctFlowImpl;", "T", "Lkotlinx/coroutines/flow/Flow;", "upstream", "keySelector", "Lkotlin/Function1;", "", "areEquivalent", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "old", "new", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class DistinctFlowImpl<T>
  implements Flow<T>
{
  public final Function2<Object, Object, Boolean> areEquivalent;
  public final Function1<T, Object> keySelector;
  private final Flow<T> upstream;
  
  public DistinctFlowImpl(Flow<? extends T> paramFlow, Function1<? super T, ? extends Object> paramFunction1, Function2<Object, Object, Boolean> paramFunction2)
  {
    this.upstream = paramFlow;
    this.keySelector = paramFunction1;
    this.areEquivalent = paramFunction2;
  }
  
  public Object collect(final FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    final Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    localObjectRef.element = NullSurrogateKt.NULL;
    paramFlowCollector = this.upstream.collect((FlowCollector)new FlowCollector()
    {
      final DistinctFlowImpl<T> this$0;
      
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
          final DistinctFlowImpl.collect.2<T> this$0;
          
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
          localObject2 = this.this$0.keySelector.invoke(paramAnonymousT);
          if ((localObjectRef.element == NullSurrogateKt.NULL) || (!((Boolean)this.this$0.areEquivalent.invoke(localObjectRef.element, localObject2)).booleanValue())) {}
          break;
        }
        for (;;)
        {
          return Unit.INSTANCE;
          localObjectRef.element = localObject2;
          localObject2 = paramFlowCollector;
          paramAnonymousContinuation.label = 1;
          if (((FlowCollector)localObject2).emit(paramAnonymousT, paramAnonymousContinuation) == localObject1) {
            return localObject1;
          }
        }
      }
    }, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/DistinctFlowImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */