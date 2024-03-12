package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;

@Metadata(d1={"\0008\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\bg\030\0002\0020\001J\031\020\002\032\0020\0032\006\020\004\032\0020\005H@ø\001\000¢\006\002\020\006J$\020\007\032\0020\b2\006\020\t\032\0020\0052\n\020\n\032\0060\013j\002`\f2\006\020\r\032\0020\016H\026J\036\020\017\032\0020\0032\006\020\t\032\0020\0052\f\020\020\032\b\022\004\022\0020\0030\021H&\002\004\n\002\b\031¨\006\022"}, d2={"Lkotlinx/coroutines/Delay;", "", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "context", "Lkotlin/coroutines/CoroutineContext;", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface Delay
{
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated without replacement as an internal method never intended for public use")
  public abstract Object delay(long paramLong, Continuation<? super Unit> paramContinuation);
  
  public abstract DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext);
  
  public abstract void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated without replacement as an internal method never intended for public use")
    public static Object delay(Delay paramDelay, long paramLong, Continuation<? super Unit> paramContinuation)
    {
      if (paramLong <= 0L) {
        return Unit.INSTANCE;
      }
      CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
      localCancellableContinuationImpl.initCancellability();
      paramDelay.scheduleResumeAfterDelay(paramLong, (CancellableContinuation)localCancellableContinuationImpl);
      paramDelay = localCancellableContinuationImpl.getResult();
      if (paramDelay == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      if (paramDelay == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramDelay;
      }
      return Unit.INSTANCE;
    }
    
    public static DisposableHandle invokeOnTimeout(Delay paramDelay, long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
    {
      return DefaultExecutorKt.getDefaultDelay().invokeOnTimeout(paramLong, paramRunnable, paramCoroutineContext);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/Delay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */