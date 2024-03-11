package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(d1={"\000>\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\b\n\000\n\002\020\016\n\000\bÀ\002\030\0002\0020\0012\0020\002B\007\b\002¢\006\002\020\003J\b\020\t\032\0020\nH\026J\034\020\013\032\0020\n2\006\020\f\032\0020\r2\n\020\016\032\0060\017j\002`\020H\026J\034\020\021\032\0020\n2\006\020\f\032\0020\r2\n\020\016\032\0060\017j\002`\020H\027J\020\020\022\032\0020\n2\006\020\023\032\0020\017H\026J\020\020\024\032\0020\0052\006\020\025\032\0020\026H\027J\b\020\027\032\0020\030H\026R\016\020\004\032\0020\005X\004¢\006\002\n\000R\024\020\006\032\0020\0028VX\004¢\006\006\032\004\b\007\020\b¨\006\031"}, d2={"Lkotlinx/coroutines/scheduling/DefaultIoScheduler;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "()V", "default", "Lkotlinx/coroutines/CoroutineDispatcher;", "executor", "getExecutor", "()Ljava/util/concurrent/Executor;", "close", "", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "execute", "command", "limitedParallelism", "parallelism", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DefaultIoScheduler
  extends ExecutorCoroutineDispatcher
  implements Executor
{
  public static final DefaultIoScheduler INSTANCE = new DefaultIoScheduler();
  private static final CoroutineDispatcher jdField_default;
  
  static
  {
    UnlimitedIoScheduler localUnlimitedIoScheduler = UnlimitedIoScheduler.INSTANCE;
    default = localUnlimitedIoScheduler.limitedParallelism(SystemPropsKt.systemProp$default("kotlinx.coroutines.io.parallelism", RangesKt.coerceAtLeast(64, SystemPropsKt.getAVAILABLE_PROCESSORS()), 0, 0, 12, null));
  }
  
  public void close()
  {
    throw new IllegalStateException("Cannot be invoked on Dispatchers.IO".toString());
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    default.dispatch(paramCoroutineContext, paramRunnable);
  }
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    default.dispatchYield(paramCoroutineContext, paramRunnable);
  }
  
  public void execute(Runnable paramRunnable)
  {
    dispatch((CoroutineContext)EmptyCoroutineContext.INSTANCE, paramRunnable);
  }
  
  public Executor getExecutor()
  {
    return (Executor)this;
  }
  
  public CoroutineDispatcher limitedParallelism(int paramInt)
  {
    return UnlimitedIoScheduler.INSTANCE.limitedParallelism(paramInt);
  }
  
  public String toString()
  {
    return "Dispatchers.IO";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/DefaultIoScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */