package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(d1={"\0008\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\0324\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\b\020\003\032\004\030\0010\0042\f\020\005\032\b\022\004\022\002H\0020\006H\000ø\001\000¢\006\002\020\007\032I\020\b\032\004\030\0010\004\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012%\b\002\020\t\032\037\022\023\022\0210\013¢\006\f\b\f\022\b\b\r\022\004\b\b(\016\022\004\022\0020\017\030\0010\nH\000ø\001\000¢\006\002\020\020\032.\020\b\032\004\030\0010\004\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\n\020\021\032\006\022\002\b\0030\022H\000ø\001\000¢\006\002\020\023\002\004\n\002\b\031¨\006\024"}, d2={"recoverResult", "Lkotlin/Result;", "T", "state", "", "uCont", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toState", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "caller", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CompletionStateKt
{
  public static final <T> Object recoverResult(Object paramObject, Continuation<? super T> paramContinuation)
  {
    if ((paramObject instanceof CompletedExceptionally))
    {
      Object localObject = Result.Companion;
      localObject = ((CompletedExceptionally)paramObject).cause;
      paramObject = localObject;
      if (DebugKt.getRECOVER_STACK_TRACES()) {
        if (!(paramContinuation instanceof CoroutineStackFrame)) {
          paramObject = localObject;
        } else {
          paramObject = StackTraceRecoveryKt.access$recoverFromStackFrame((Throwable)localObject, (CoroutineStackFrame)paramContinuation);
        }
      }
      paramObject = Result.constructor-impl(ResultKt.createFailure((Throwable)paramObject));
    }
    else
    {
      paramContinuation = Result.Companion;
      paramObject = Result.constructor-impl(paramObject);
    }
    return paramObject;
  }
  
  public static final <T> Object toState(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    Object localObject = Result.exceptionOrNull-impl(paramObject);
    if (localObject == null)
    {
      localObject = paramObject;
      paramObject = localObject;
      if (paramFunction1 != null) {
        paramObject = new CompletedWithCancellation(localObject, paramFunction1);
      }
    }
    else
    {
      paramObject = new CompletedExceptionally((Throwable)localObject, false, 2, null);
    }
    return paramObject;
  }
  
  public static final <T> Object toState(Object paramObject, CancellableContinuation<?> paramCancellableContinuation)
  {
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null)
    {
      if ((DebugKt.getRECOVER_STACK_TRACES()) && (((Continuation)paramCancellableContinuation instanceof CoroutineStackFrame))) {
        paramObject = StackTraceRecoveryKt.access$recoverFromStackFrame(localThrowable, (CoroutineStackFrame)paramCancellableContinuation);
      } else {
        paramObject = localThrowable;
      }
      paramObject = new CompletedExceptionally((Throwable)paramObject, false, 2, null);
    }
    return paramObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CompletionStateKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */