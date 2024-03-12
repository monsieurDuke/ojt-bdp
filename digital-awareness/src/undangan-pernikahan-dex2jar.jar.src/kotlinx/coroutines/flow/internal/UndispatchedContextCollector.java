package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1={"\000,\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\005\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\033\022\f\020\003\032\b\022\004\022\0028\0000\002\022\006\020\004\032\0020\005¢\006\002\020\006J\031\020\016\032\0020\f2\006\020\017\032\0028\000H@ø\001\000¢\006\002\020\020R\016\020\007\032\0020\bX\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000R/\020\t\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\0020\f0\013\022\006\022\004\030\0010\b0\nX\004ø\001\000¢\006\004\n\002\020\r\002\004\n\002\b\031¨\006\021"}, d2={"Lkotlinx/coroutines/flow/internal/UndispatchedContextCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "downstream", "emitContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V", "countOrElement", "", "emitRef", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/jvm/functions/Function2;", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class UndispatchedContextCollector<T>
  implements FlowCollector<T>
{
  private final Object countOrElement;
  private final CoroutineContext emitContext;
  private final Function2<T, Continuation<? super Unit>, Object> emitRef;
  
  public UndispatchedContextCollector(FlowCollector<? super T> paramFlowCollector, CoroutineContext paramCoroutineContext)
  {
    this.emitContext = paramCoroutineContext;
    this.countOrElement = ThreadContextKt.threadContextElements(paramCoroutineContext);
    this.emitRef = ((Function2)new SuspendLambda(paramFlowCollector, null)
    {
      final FlowCollector<T> $downstream;
      Object L$0;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$downstream, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(T paramAnonymousT, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousT, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
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
          Object localObject2 = this.L$0;
          paramAnonymousObject = this.$downstream;
          Continuation localContinuation = (Continuation)this;
          this.label = 1;
          if (((FlowCollector)paramAnonymousObject).emit(localObject2, localContinuation) == localObject1) {
            return localObject1;
          }
          break;
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public Object emit(T paramT, Continuation<? super Unit> paramContinuation)
  {
    paramT = ChannelFlowKt.withContextUndispatched(this.emitContext, paramT, this.countOrElement, this.emitRef, paramContinuation);
    if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramT;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/UndispatchedContextCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */