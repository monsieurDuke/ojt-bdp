package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1={"\000<\n\000\n\002\020\b\n\002\b\007\n\002\020\013\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\003\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\032 \020\f\032\0020\r\"\004\b\000\020\016*\b\022\004\022\002H\0160\0172\006\020\020\032\0020\001H\000\032.\020\021\032\0020\r\"\004\b\000\020\016*\b\022\004\022\002H\0160\0172\f\020\022\032\b\022\004\022\002H\0160\0232\006\020\024\032\0020\tH\000\032\020\020\025\032\0020\r*\006\022\002\b\0030\017H\002\032\031\020\026\032\0020\r*\006\022\002\b\0030\0232\006\020\027\032\0020\030H\b\032'\020\031\032\0020\r*\006\022\002\b\0030\0172\006\020\032\032\0020\0332\f\020\034\032\b\022\004\022\0020\r0\035H\b\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\026\020\002\032\0020\0018\000XT¢\006\b\n\000\022\004\b\003\020\004\"\016\020\005\032\0020\001XT¢\006\002\n\000\"\016\020\006\032\0020\001XT¢\006\002\n\000\"\016\020\007\032\0020\001XT¢\006\002\n\000\"\030\020\b\032\0020\t*\0020\0018@X\004¢\006\006\032\004\b\b\020\n\"\030\020\013\032\0020\t*\0020\0018@X\004¢\006\006\032\004\b\013\020\n¨\006\036"}, d2={"MODE_ATOMIC", "", "MODE_CANCELLABLE", "getMODE_CANCELLABLE$annotations", "()V", "MODE_CANCELLABLE_REUSABLE", "MODE_UNDISPATCHED", "MODE_UNINITIALIZED", "isCancellableMode", "", "(I)Z", "isReusableMode", "dispatch", "", "T", "Lkotlinx/coroutines/DispatchedTask;", "mode", "resume", "delegate", "Lkotlin/coroutines/Continuation;", "undispatched", "resumeUnconfined", "resumeWithStackTrace", "exception", "", "runUnconfinedEventLoop", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "block", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class DispatchedTaskKt
{
  public static final int MODE_ATOMIC = 0;
  public static final int MODE_CANCELLABLE = 1;
  public static final int MODE_CANCELLABLE_REUSABLE = 2;
  public static final int MODE_UNDISPATCHED = 4;
  public static final int MODE_UNINITIALIZED = -1;
  
  public static final <T> void dispatch(DispatchedTask<? super T> paramDispatchedTask, int paramInt)
  {
    boolean bool2 = DebugKt.getASSERTIONS_ENABLED();
    boolean bool1 = true;
    if (bool2)
    {
      int i;
      if (paramInt != -1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    Object localObject = paramDispatchedTask.getDelegate$kotlinx_coroutines_core();
    if (paramInt != 4) {
      bool1 = false;
    }
    if ((!bool1) && ((localObject instanceof DispatchedContinuation)) && (isCancellableMode(paramInt) == isCancellableMode(paramDispatchedTask.resumeMode)))
    {
      CoroutineDispatcher localCoroutineDispatcher = ((DispatchedContinuation)localObject).dispatcher;
      localObject = ((Continuation)localObject).getContext();
      if (localCoroutineDispatcher.isDispatchNeeded((CoroutineContext)localObject)) {
        localCoroutineDispatcher.dispatch((CoroutineContext)localObject, (Runnable)paramDispatchedTask);
      } else {
        resumeUnconfined(paramDispatchedTask);
      }
    }
    else
    {
      resume(paramDispatchedTask, (Continuation)localObject, bool1);
    }
  }
  
  public static final boolean isCancellableMode(int paramInt)
  {
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (paramInt != 1) {
      if (paramInt == 2) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  public static final boolean isReusableMode(int paramInt)
  {
    boolean bool;
    if (paramInt == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> void resume(DispatchedTask<? super T> paramDispatchedTask, Continuation<? super T> paramContinuation, boolean paramBoolean)
  {
    Object localObject4 = paramDispatchedTask.takeState$kotlinx_coroutines_core();
    Object localObject1 = paramDispatchedTask.getExceptionalResult$kotlinx_coroutines_core(localObject4);
    Object localObject2 = Result.Companion;
    if (localObject1 != null) {
      paramDispatchedTask = ResultKt.createFailure((Throwable)localObject1);
    } else {
      paramDispatchedTask = paramDispatchedTask.getSuccessfulResult$kotlinx_coroutines_core(localObject4);
    }
    localObject2 = Result.constructor-impl(paramDispatchedTask);
    if (paramBoolean)
    {
      localObject4 = (DispatchedContinuation)paramContinuation;
      paramDispatchedTask = ((DispatchedContinuation)localObject4).continuation;
      localObject1 = ((DispatchedContinuation)localObject4).countOrElement;
      paramContinuation = paramDispatchedTask.getContext();
      localObject1 = ThreadContextKt.updateThreadContext(paramContinuation, localObject1);
      if (localObject1 != ThreadContextKt.NO_THREAD_ELEMENTS)
      {
        paramDispatchedTask = CoroutineContextKt.updateUndispatchedCompletion(paramDispatchedTask, paramContinuation, localObject1);
      }
      else
      {
        paramDispatchedTask = null;
        UndispatchedCoroutine localUndispatchedCoroutine = (UndispatchedCoroutine)null;
      }
      try
      {
        ((DispatchedContinuation)localObject4).continuation.resumeWith(localObject2);
        localObject2 = Unit.INSTANCE;
      }
      finally
      {
        if ((paramDispatchedTask == null) || (paramDispatchedTask.clearThreadContext())) {
          ThreadContextKt.restoreThreadContext(paramContinuation, localObject1);
        }
      }
    }
    paramContinuation.resumeWith(localObject3);
  }
  
  private static final void resumeUnconfined(DispatchedTask<?> paramDispatchedTask)
  {
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
    if (localEventLoop.isUnconfinedLoopActive())
    {
      localEventLoop.dispatchUnconfined(paramDispatchedTask);
    }
    else
    {
      localEventLoop.incrementUseCount(true);
      try
      {
        resume(paramDispatchedTask, paramDispatchedTask.getDelegate$kotlinx_coroutines_core(), true);
        for (;;)
        {
          boolean bool = localEventLoop.processUnconfinedEvent();
          if (!bool) {
            break;
          }
        }
      }
      finally {}
    }
    try
    {
      paramDispatchedTask.handleFatalException(localThrowable, null);
      return;
    }
    finally
    {
      localEventLoop.decrementUseCount(true);
    }
  }
  
  public static final void resumeWithStackTrace(Continuation<?> paramContinuation, Throwable paramThrowable)
  {
    Result.Companion localCompanion = Result.Companion;
    if ((DebugKt.getRECOVER_STACK_TRACES()) && ((paramContinuation instanceof CoroutineStackFrame))) {
      paramThrowable = StackTraceRecoveryKt.access$recoverFromStackFrame(paramThrowable, (CoroutineStackFrame)paramContinuation);
    }
    paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramThrowable)));
  }
  
  public static final void runUnconfinedEventLoop(DispatchedTask<?> paramDispatchedTask, EventLoop paramEventLoop, Function0<Unit> paramFunction0)
  {
    paramEventLoop.incrementUseCount(true);
    try
    {
      paramFunction0.invoke();
      boolean bool;
      do
      {
        bool = paramEventLoop.processUnconfinedEvent();
      } while (bool);
      InlineMarker.finallyStart(1);
    }
    finally {}
    try
    {
      paramDispatchedTask.handleFatalException(paramFunction0, null);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramEventLoop.decrementUseCount(true);
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DispatchedTaskKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */