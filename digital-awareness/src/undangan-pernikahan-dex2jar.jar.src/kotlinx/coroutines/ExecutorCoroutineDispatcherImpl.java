package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ConcurrentKt;

@Metadata(d1={"\000j\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\002\030\002\n\000\b\000\030\0002\0020\0012\0020\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\030\020\b\032\0020\t2\006\020\n\032\0020\0132\006\020\f\032\0020\rH\002J\b\020\016\032\0020\tH\026J\034\020\017\032\0020\t2\006\020\n\032\0020\0132\n\020\020\032\0060\021j\002`\022H\026J\023\020\023\032\0020\0242\b\020\025\032\004\030\0010\026H\002J\b\020\027\032\0020\030H\026J$\020\031\032\0020\0322\006\020\033\032\0020\0342\n\020\020\032\0060\021j\002`\0222\006\020\n\032\0020\013H\026J\036\020\035\032\0020\t2\006\020\033\032\0020\0342\f\020\036\032\b\022\004\022\0020\t0\037H\026J\b\020 \032\0020!H\026J.\020\"\032\b\022\002\b\003\030\0010#*\0020$2\n\020\020\032\0060\021j\002`\0222\006\020\n\032\0020\0132\006\020\033\032\0020\034H\002R\024\020\003\032\0020\004X\004¢\006\b\n\000\032\004\b\006\020\007¨\006%"}, d2={"Lkotlinx/coroutines/ExecutorCoroutineDispatcherImpl;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "executor", "Ljava/util/concurrent/Executor;", "(Ljava/util/concurrent/Executor;)V", "getExecutor", "()Ljava/util/concurrent/Executor;", "cancelJobOnRejection", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "Ljava/util/concurrent/RejectedExecutionException;", "close", "dispatch", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "equals", "", "other", "", "hashCode", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "", "scheduleBlock", "Ljava/util/concurrent/ScheduledFuture;", "Ljava/util/concurrent/ScheduledExecutorService;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ExecutorCoroutineDispatcherImpl
  extends ExecutorCoroutineDispatcher
  implements Delay
{
  private final Executor executor;
  
  public ExecutorCoroutineDispatcherImpl(Executor paramExecutor)
  {
    this.executor = paramExecutor;
    ConcurrentKt.removeFutureOnCancel(getExecutor());
  }
  
  private final void cancelJobOnRejection(CoroutineContext paramCoroutineContext, RejectedExecutionException paramRejectedExecutionException)
  {
    JobKt.cancel(paramCoroutineContext, ExceptionsKt.CancellationException("The task was rejected", (Throwable)paramRejectedExecutionException));
  }
  
  private final ScheduledFuture<?> scheduleBlock(ScheduledExecutorService paramScheduledExecutorService, Runnable paramRunnable, CoroutineContext paramCoroutineContext, long paramLong)
  {
    try
    {
      paramScheduledExecutorService = paramScheduledExecutorService.schedule(paramRunnable, paramLong, TimeUnit.MILLISECONDS);
    }
    catch (RejectedExecutionException paramScheduledExecutorService)
    {
      cancelJobOnRejection(paramCoroutineContext, paramScheduledExecutorService);
      paramScheduledExecutorService = (ScheduledFuture)null;
      paramScheduledExecutorService = null;
    }
    return paramScheduledExecutorService;
  }
  
  public void close()
  {
    Object localObject = getExecutor();
    if ((localObject instanceof ExecutorService)) {
      localObject = (ExecutorService)localObject;
    } else {
      localObject = null;
    }
    if (localObject != null) {
      ((ExecutorService)localObject).shutdown();
    }
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated without replacement as an internal method never intended for public use")
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    return Delay.DefaultImpls.delay((Delay)this, paramLong, paramContinuation);
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    try
    {
      Executor localExecutor = getExecutor();
      localObject = AbstractTimeSourceKt.getTimeSource();
      if (localObject != null) {
        for (;;)
        {
          Runnable localRunnable = ((AbstractTimeSource)localObject).wrapTask(paramRunnable);
          localObject = localRunnable;
          if (localRunnable != null) {
            break;
          }
        }
      }
      localObject = paramRunnable;
      localExecutor.execute((Runnable)localObject);
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      Object localObject = AbstractTimeSourceKt.getTimeSource();
      if (localObject != null) {
        ((AbstractTimeSource)localObject).unTrackTask();
      }
      cancelJobOnRejection(paramCoroutineContext, localRejectedExecutionException);
      Dispatchers.getIO().dispatch(paramCoroutineContext, paramRunnable);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ExecutorCoroutineDispatcherImpl)) && (((ExecutorCoroutineDispatcherImpl)paramObject).getExecutor() == getExecutor())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Executor getExecutor()
  {
    return this.executor;
  }
  
  public int hashCode()
  {
    return System.identityHashCode(getExecutor());
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
  {
    Object localObject1 = getExecutor();
    boolean bool = localObject1 instanceof ScheduledExecutorService;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (ScheduledExecutorService)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = scheduleBlock((ScheduledExecutorService)localObject1, paramRunnable, paramCoroutineContext, paramLong);
    }
    if (localObject1 != null) {
      paramRunnable = (DisposableHandle)new DisposableFutureHandle((Future)localObject1);
    } else {
      paramRunnable = DefaultExecutor.INSTANCE.invokeOnTimeout(paramLong, paramRunnable, paramCoroutineContext);
    }
    return paramRunnable;
  }
  
  public void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    Object localObject = getExecutor();
    boolean bool = localObject instanceof ScheduledExecutorService;
    CoroutineContext localCoroutineContext = null;
    if (bool) {
      localObject = (ScheduledExecutorService)localObject;
    } else {
      localObject = null;
    }
    if (localObject == null)
    {
      localObject = localCoroutineContext;
    }
    else
    {
      Runnable localRunnable = (Runnable)new ResumeUndispatchedRunnable((CoroutineDispatcher)this, paramCancellableContinuation);
      localCoroutineContext = paramCancellableContinuation.getContext();
      localObject = scheduleBlock((ScheduledExecutorService)localObject, localRunnable, localCoroutineContext, paramLong);
    }
    if (localObject != null)
    {
      JobKt.cancelFutureOnCancellation(paramCancellableContinuation, (Future)localObject);
      return;
    }
    DefaultExecutor.INSTANCE.scheduleResumeAfterDelay(paramLong, paramCancellableContinuation);
  }
  
  public String toString()
  {
    return getExecutor().toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ExecutorCoroutineDispatcherImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */