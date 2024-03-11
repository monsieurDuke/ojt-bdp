package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;

@Metadata(d1={"\000T\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\t\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\n\b\020\030\0002\0020\001B-\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\003\022\b\b\002\020\005\032\0020\006\022\b\b\002\020\007\032\0020\b¢\006\002\020\tJ\b\020\020\032\0020\021H\026J\b\020\022\032\0020\013H\002J\034\020\023\032\0020\0212\006\020\024\032\0020\0252\n\020\026\032\0060\027j\002`\030H\026J)\020\031\032\0020\0212\n\020\026\032\0060\027j\002`\0302\006\020\024\032\0020\0322\006\020\033\032\0020\034H\000¢\006\002\b\035J\034\020\036\032\0020\0212\006\020\024\032\0020\0252\n\020\026\032\0060\027j\002`\030H\026J\r\020\037\032\0020\021H\000¢\006\002\b J\025\020!\032\0020\0212\006\020\"\032\0020\006H\000¢\006\002\b#J\r\020$\032\0020\021H\000¢\006\002\b%R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\n\032\0020\013X\016¢\006\002\n\000R\024\020\f\032\0020\r8VX\004¢\006\006\032\004\b\016\020\017R\016\020\005\032\0020\006X\004¢\006\002\n\000R\016\020\004\032\0020\003X\004¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000¨\006&"}, d2={"Lkotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "corePoolSize", "", "maxPoolSize", "idleWorkerKeepAliveNs", "", "schedulerName", "", "(IIJLjava/lang/String;)V", "coroutineScheduler", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "close", "", "createScheduler", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchWithContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "tailDispatch", "", "dispatchWithContext$kotlinx_coroutines_core", "dispatchYield", "restore", "restore$kotlinx_coroutines_core", "shutdown", "timeout", "shutdown$kotlinx_coroutines_core", "usePrivateScheduler", "usePrivateScheduler$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class SchedulerCoroutineDispatcher
  extends ExecutorCoroutineDispatcher
{
  private final int corePoolSize;
  private CoroutineScheduler coroutineScheduler;
  private final long idleWorkerKeepAliveNs;
  private final int maxPoolSize;
  private final String schedulerName;
  
  public SchedulerCoroutineDispatcher()
  {
    this(0, 0, 0L, null, 15, null);
  }
  
  public SchedulerCoroutineDispatcher(int paramInt1, int paramInt2, long paramLong, String paramString)
  {
    this.corePoolSize = paramInt1;
    this.maxPoolSize = paramInt2;
    this.idleWorkerKeepAliveNs = paramLong;
    this.schedulerName = paramString;
    this.coroutineScheduler = createScheduler();
  }
  
  private final CoroutineScheduler createScheduler()
  {
    return new CoroutineScheduler(this.corePoolSize, this.maxPoolSize, this.idleWorkerKeepAliveNs, this.schedulerName);
  }
  
  public void close()
  {
    this.coroutineScheduler.close();
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    CoroutineScheduler.dispatch$default(this.coroutineScheduler, paramRunnable, null, false, 6, null);
  }
  
  public final void dispatchWithContext$kotlinx_coroutines_core(Runnable paramRunnable, TaskContext paramTaskContext, boolean paramBoolean)
  {
    this.coroutineScheduler.dispatch(paramRunnable, paramTaskContext, paramBoolean);
  }
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    CoroutineScheduler.dispatch$default(this.coroutineScheduler, paramRunnable, null, true, 2, null);
  }
  
  public Executor getExecutor()
  {
    return (Executor)this.coroutineScheduler;
  }
  
  public final void restore$kotlinx_coroutines_core()
  {
    usePrivateScheduler$kotlinx_coroutines_core();
  }
  
  public final void shutdown$kotlinx_coroutines_core(long paramLong)
  {
    try
    {
      this.coroutineScheduler.shutdown(paramLong);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void usePrivateScheduler$kotlinx_coroutines_core()
  {
    try
    {
      this.coroutineScheduler.shutdown(1000L);
      this.coroutineScheduler = createScheduler();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */