package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.FlowCollector;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000N\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\b\n\000\n\002\030\002\n\002\020\002\n\002\b\013\n\002\020\000\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\0020\0032\0020\004B\033\022\f\020\005\032\b\022\004\022\0028\0000\002\022\006\020\006\032\0020\007¢\006\002\020\bJ'\020\025\032\0020\0202\006\020\026\032\0020\0072\b\020\027\032\004\030\0010\0072\006\020\030\032\0028\000H\002¢\006\002\020\031J\031\020\032\032\0020\0202\006\020\030\032\0028\000H@ø\001\000¢\006\002\020\033J%\020\032\032\004\030\0010\0342\f\020\035\032\b\022\004\022\0020\0200\0172\006\020\030\032\0028\000H\002¢\006\002\020\036J\032\020\037\032\0020\0202\006\020 \032\0020!2\b\020\030\032\004\030\0010\034H\002J\n\020\"\032\004\030\0010#H\026J \020$\032\0020\0342\016\020%\032\n\022\006\022\004\030\0010\0340&H\026ø\001\000¢\006\002\020'J\b\020(\032\0020\020H\026R\026\020\t\032\004\030\0010\0048VX\004¢\006\006\032\004\b\n\020\013R\020\020\006\032\0020\0078\000X\004¢\006\002\n\000R\020\020\f\032\0020\r8\000X\004¢\006\002\n\000R\026\020\005\032\b\022\004\022\0028\0000\0028\000X\004¢\006\002\n\000R\026\020\016\032\n\022\004\022\0020\020\030\0010\017X\016¢\006\002\n\000R\024\020\021\032\0020\0078VX\004¢\006\006\032\004\b\022\020\023R\020\020\024\032\004\030\0010\007X\016¢\006\002\n\000\002\004\n\002\b\031¨\006)"}, d2={"Lkotlinx/coroutines/flow/internal/SafeCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "collector", "collectContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "collectContextSize", "", "completion", "Lkotlin/coroutines/Continuation;", "", "context", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "lastEmissionContext", "checkContext", "currentContext", "previousContext", "value", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "uCont", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)Ljava/lang/Object;", "exceptionTransparencyViolated", "exception", "Lkotlinx/coroutines/flow/internal/DownstreamExceptionContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "invokeSuspend", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "releaseIntercepted", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class SafeCollector<T>
  extends ContinuationImpl
  implements FlowCollector<T>, CoroutineStackFrame
{
  public final CoroutineContext collectContext;
  public final int collectContextSize;
  public final FlowCollector<T> collector;
  private Continuation<? super Unit> completion;
  private CoroutineContext lastEmissionContext;
  
  public SafeCollector(FlowCollector<? super T> paramFlowCollector, CoroutineContext paramCoroutineContext)
  {
    super((Continuation)NoOpContinuation.INSTANCE, (CoroutineContext)EmptyCoroutineContext.INSTANCE);
    this.collector = paramFlowCollector;
    this.collectContext = paramCoroutineContext;
    this.collectContextSize = ((Number)paramCoroutineContext.fold(Integer.valueOf(0), (Function2)collectContextSize.1.INSTANCE)).intValue();
  }
  
  private final void checkContext(CoroutineContext paramCoroutineContext1, CoroutineContext paramCoroutineContext2, T paramT)
  {
    if ((paramCoroutineContext2 instanceof DownstreamExceptionContext)) {
      exceptionTransparencyViolated((DownstreamExceptionContext)paramCoroutineContext2, paramT);
    }
    SafeCollector_commonKt.checkContext(this, paramCoroutineContext1);
  }
  
  private final Object emit(Continuation<? super Unit> paramContinuation, T paramT)
  {
    CoroutineContext localCoroutineContext1 = paramContinuation.getContext();
    JobKt.ensureActive(localCoroutineContext1);
    CoroutineContext localCoroutineContext2 = this.lastEmissionContext;
    if (localCoroutineContext2 != localCoroutineContext1)
    {
      checkContext(localCoroutineContext1, localCoroutineContext2, paramT);
      this.lastEmissionContext = localCoroutineContext1;
    }
    this.completion = paramContinuation;
    paramContinuation = SafeCollectorKt.access$getEmitFun$p().invoke(this.collector, paramT, (Continuation)this);
    if (!Intrinsics.areEqual(paramContinuation, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
      this.completion = null;
    }
    return paramContinuation;
  }
  
  private final void exceptionTransparencyViolated(DownstreamExceptionContext paramDownstreamExceptionContext, Object paramObject)
  {
    paramDownstreamExceptionContext = new StringBuilder().append("\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception ").append(paramDownstreamExceptionContext.e);
    paramDownstreamExceptionContext = paramDownstreamExceptionContext.append(", but then emission attempt of value '");
    paramDownstreamExceptionContext = paramDownstreamExceptionContext.append(paramObject);
    paramDownstreamExceptionContext = StringsKt.trimIndent("' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            ");
    Log5ECF72.a(paramDownstreamExceptionContext);
    LogE84000.a(paramDownstreamExceptionContext);
    Log229316.a(paramDownstreamExceptionContext);
    throw new IllegalStateException(paramDownstreamExceptionContext.toString());
  }
  
  public Object emit(T paramT, Continuation<? super Unit> paramContinuation)
  {
    try
    {
      paramT = emit(paramContinuation, paramT);
      if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramT;
      }
      return Unit.INSTANCE;
    }
    finally
    {
      this.lastEmissionContext = ((CoroutineContext)new DownstreamExceptionContext(paramT, paramContinuation.getContext()));
    }
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    Object localObject = this.completion;
    if ((localObject instanceof CoroutineStackFrame)) {
      localObject = (CoroutineStackFrame)localObject;
    } else {
      localObject = null;
    }
    return (CoroutineStackFrame)localObject;
  }
  
  public CoroutineContext getContext()
  {
    CoroutineContext localCoroutineContext2 = this.lastEmissionContext;
    CoroutineContext localCoroutineContext1 = localCoroutineContext2;
    if (localCoroutineContext2 == null) {
      localCoroutineContext1 = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
    }
    return localCoroutineContext1;
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  public Object invokeSuspend(Object paramObject)
  {
    Object localObject = Result.exceptionOrNull-impl(paramObject);
    if (localObject != null) {
      this.lastEmissionContext = ((CoroutineContext)new DownstreamExceptionContext((Throwable)localObject, getContext()));
    }
    localObject = this.completion;
    if (localObject != null) {
      ((Continuation)localObject).resumeWith(paramObject);
    }
    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
  }
  
  public void releaseIntercepted()
  {
    super.releaseIntercepted();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/SafeCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */