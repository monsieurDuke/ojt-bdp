package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Metadata(d1={"\000:\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\032N\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022/\b\005\020\003\032)\b\001\022\n\022\b\022\004\022\002H\0020\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004¢\006\002\b\tH\bø\001\000¢\006\002\020\n\032\030\020\013\032\0020\007*\006\022\002\b\0030\f2\006\020\r\032\0020\016H\001\032\033\020\017\032\004\030\0010\020*\004\030\0010\0202\b\020\021\032\004\030\0010\020H\020\002\004\n\002\b\031¨\006\022"}, d2={"unsafeFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "checkContext", "Lkotlinx/coroutines/flow/internal/SafeCollector;", "currentContext", "Lkotlin/coroutines/CoroutineContext;", "transitiveCoroutineParent", "Lkotlinx/coroutines/Job;", "collectJob", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SafeCollector_commonKt
{
  public static final void checkContext(SafeCollector<?> paramSafeCollector, CoroutineContext paramCoroutineContext)
  {
    if (((Number)paramCoroutineContext.fold(Integer.valueOf(0), (Function2)new Lambda(paramSafeCollector)
    {
      final SafeCollector<?> $this_checkContext;
      
      public final Integer invoke(int paramAnonymousInt, CoroutineContext.Element paramAnonymousElement)
      {
        Object localObject2 = paramAnonymousElement.getKey();
        Object localObject1 = this.$this_checkContext.collectContext.get((CoroutineContext.Key)localObject2);
        if (localObject2 != Job.Key)
        {
          if (paramAnonymousElement != localObject1) {
            paramAnonymousInt = Integer.MIN_VALUE;
          } else {
            paramAnonymousInt++;
          }
          return Integer.valueOf(paramAnonymousInt);
        }
        localObject1 = (Job)localObject1;
        localObject2 = SafeCollector_commonKt.transitiveCoroutineParent((Job)paramAnonymousElement, (Job)localObject1);
        if (localObject2 == localObject1)
        {
          if (localObject1 != null) {
            paramAnonymousInt++;
          }
          return Integer.valueOf(paramAnonymousInt);
        }
        paramAnonymousElement = new StringBuilder().append("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of ");
        paramAnonymousElement = paramAnonymousElement.append(localObject2);
        paramAnonymousElement = paramAnonymousElement.append(", expected child of ");
        paramAnonymousElement = paramAnonymousElement.append(localObject1);
        throw new IllegalStateException(".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'".toString());
      }
    })).intValue() == paramSafeCollector.collectContextSize) {
      return;
    }
    paramSafeCollector = new StringBuilder().append("Flow invariant is violated:\n\t\tFlow was collected in ").append(paramSafeCollector.collectContext);
    paramSafeCollector = paramSafeCollector.append(",\n\t\tbut emission happened in ");
    paramSafeCollector = paramSafeCollector.append(paramCoroutineContext);
    throw new IllegalStateException(".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead".toString());
  }
  
  public static final Job transitiveCoroutineParent(Job paramJob1, Job paramJob2)
  {
    for (;;)
    {
      if (paramJob1 == null) {
        return null;
      }
      if (paramJob1 == paramJob2) {
        return paramJob1;
      }
      if (!(paramJob1 instanceof ScopeCoroutine)) {
        return paramJob1;
      }
      paramJob1 = ((ScopeCoroutine)paramJob1).getParent$kotlinx_coroutines_core();
    }
  }
  
  public static final <T> Flow<T> unsafeFlow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    (Flow)new Flow()
    {
      final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> $block;
      
      public Object collect(FlowCollector<? super T> paramAnonymousFlowCollector, Continuation<? super Unit> paramAnonymousContinuation)
      {
        paramAnonymousFlowCollector = this.$block.invoke(paramAnonymousFlowCollector, paramAnonymousContinuation);
        if (paramAnonymousFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return paramAnonymousFlowCollector;
        }
        return Unit.INSTANCE;
      }
      
      public Object collect$$forInline(FlowCollector<? super T> paramAnonymousFlowCollector, Continuation<? super Unit> paramAnonymousContinuation)
      {
        InlineMarker.mark(4);
        new ContinuationImpl(paramAnonymousContinuation)
        {
          int label;
          Object result;
          final SafeCollector_commonKt.unsafeFlow.1 this$0;
          
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            this.result = paramAnonymous2Object;
            this.label |= 0x80000000;
            return this.this$0.collect(null, (Continuation)this);
          }
        };
        InlineMarker.mark(5);
        this.$block.invoke(paramAnonymousFlowCollector, paramAnonymousContinuation);
        return Unit.INSTANCE;
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/SafeCollector_commonKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */