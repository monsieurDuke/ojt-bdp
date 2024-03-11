package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000F\n\000\n\002\020\013\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\004\n\002\020\002\n\000\n\002\020\016\n\000\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\003\032\016\020\006\032\0020\0022\006\020\007\032\0020\b\032\006\020\t\032\0020\002\032M\020\n\032\002H\013\"\004\b\000\020\0132'\020\f\032#\b\001\022\004\022\0020\002\022\n\022\b\022\004\022\002H\0130\016\022\006\022\004\030\0010\0170\r¢\006\002\b\020H@ø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\021\032\021\020\022\032\0020\bHHø\001\000¢\006\002\020\023\032\036\020\024\032\0020\025*\0020\0022\006\020\026\032\0020\0272\n\b\002\020\030\032\004\030\0010\031\032\034\020\024\032\0020\025*\0020\0022\020\b\002\020\030\032\n\030\0010\032j\004\030\001`\033\032\n\020\034\032\0020\025*\0020\002\032\025\020\035\032\0020\002*\0020\0022\006\020\007\032\0020\bH\002\"\033\020\000\032\0020\001*\0020\0028F¢\006\f\022\004\b\003\020\004\032\004\b\000\020\005\002\004\n\002\b\031¨\006\036"}, d2={"isActive", "", "Lkotlinx/coroutines/CoroutineScope;", "isActive$annotations", "(Lkotlinx/coroutines/CoroutineScope;)V", "(Lkotlinx/coroutines/CoroutineScope;)Z", "CoroutineScope", "context", "Lkotlin/coroutines/CoroutineContext;", "MainScope", "coroutineScope", "R", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "currentCoroutineContext", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancel", "", "message", "", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "ensureActive", "plus", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CoroutineScopeKt
{
  public static final CoroutineScope CoroutineScope(CoroutineContext paramCoroutineContext)
  {
    if (paramCoroutineContext.get((CoroutineContext.Key)Job.Key) == null) {
      paramCoroutineContext = paramCoroutineContext.plus((CoroutineContext)JobKt.Job$default(null, 1, null));
    }
    return (CoroutineScope)new ContextScope(paramCoroutineContext);
  }
  
  public static final CoroutineScope MainScope()
  {
    return (CoroutineScope)new ContextScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus((CoroutineContext)Dispatchers.getMain()));
  }
  
  public static final void cancel(CoroutineScope paramCoroutineScope, String paramString, Throwable paramThrowable)
  {
    cancel(paramCoroutineScope, ExceptionsKt.CancellationException(paramString, paramThrowable));
  }
  
  public static final void cancel(CoroutineScope paramCoroutineScope, CancellationException paramCancellationException)
  {
    Job localJob = (Job)paramCoroutineScope.getCoroutineContext().get((CoroutineContext.Key)Job.Key);
    if (localJob != null)
    {
      localJob.cancel(paramCancellationException);
      return;
    }
    paramCoroutineScope = Intrinsics.stringPlus("Scope cannot be cancelled because it does not have a job: ", paramCoroutineScope);
    Log5ECF72.a(paramCoroutineScope);
    LogE84000.a(paramCoroutineScope);
    Log229316.a(paramCoroutineScope);
    throw new IllegalStateException(paramCoroutineScope.toString());
  }
  
  public static final <R> Object coroutineScope(Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> paramFunction2, Continuation<? super R> paramContinuation)
  {
    ScopeCoroutine localScopeCoroutine = new ScopeCoroutine(paramContinuation.getContext(), paramContinuation);
    paramFunction2 = UndispatchedKt.startUndispatchedOrReturn(localScopeCoroutine, localScopeCoroutine, paramFunction2);
    if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction2;
  }
  
  public static final Object currentCoroutineContext(Continuation<? super CoroutineContext> paramContinuation)
  {
    return paramContinuation.getContext();
  }
  
  private static final Object currentCoroutineContext$$forInline(Continuation<? super CoroutineContext> paramContinuation)
  {
    InlineMarker.mark(3);
    throw new NullPointerException();
  }
  
  public static final void ensureActive(CoroutineScope paramCoroutineScope)
  {
    JobKt.ensureActive(paramCoroutineScope.getCoroutineContext());
  }
  
  public static final boolean isActive(CoroutineScope paramCoroutineScope)
  {
    paramCoroutineScope = (Job)paramCoroutineScope.getCoroutineContext().get((CoroutineContext.Key)Job.Key);
    boolean bool;
    if (paramCoroutineScope == null) {
      bool = true;
    } else {
      bool = paramCoroutineScope.isActive();
    }
    return bool;
  }
  
  public static final CoroutineScope plus(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext)
  {
    return (CoroutineScope)new ContextScope(paramCoroutineScope.getCoroutineContext().plus(paramCoroutineContext));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CoroutineScopeKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */