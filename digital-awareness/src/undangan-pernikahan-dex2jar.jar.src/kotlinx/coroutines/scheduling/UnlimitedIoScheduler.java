package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\bÂ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\034\020\003\032\0020\0042\006\020\005\032\0020\0062\n\020\007\032\0060\bj\002`\tH\026J\034\020\n\032\0020\0042\006\020\005\032\0020\0062\n\020\007\032\0060\bj\002`\tH\027¨\006\013"}, d2={"Lkotlinx/coroutines/scheduling/UnlimitedIoScheduler;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class UnlimitedIoScheduler
  extends CoroutineDispatcher
{
  public static final UnlimitedIoScheduler INSTANCE = new UnlimitedIoScheduler();
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    DefaultScheduler.INSTANCE.dispatchWithContext$kotlinx_coroutines_core(paramRunnable, TasksKt.BlockingContext, false);
  }
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    DefaultScheduler.INSTANCE.dispatchWithContext$kotlinx_coroutines_core(paramRunnable, TasksKt.BlockingContext, true);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/UnlimitedIoScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */