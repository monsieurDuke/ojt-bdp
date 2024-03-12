package kotlinx.coroutines;

import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

@Metadata(d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\024\020\005\032\0020\0062\n\020\007\032\0060\bj\002`\tH\026J\b\020\n\032\0020\013H\026R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006\f"}, d2={"Lkotlinx/coroutines/DispatcherExecutor;", "Ljava/util/concurrent/Executor;", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lkotlinx/coroutines/CoroutineDispatcher;)V", "execute", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class DispatcherExecutor
  implements Executor
{
  public final CoroutineDispatcher dispatcher;
  
  public DispatcherExecutor(CoroutineDispatcher paramCoroutineDispatcher)
  {
    this.dispatcher = paramCoroutineDispatcher;
  }
  
  public void execute(Runnable paramRunnable)
  {
    this.dispatcher.dispatch((CoroutineContext)EmptyCoroutineContext.INSTANCE, paramRunnable);
  }
  
  public String toString()
  {
    return this.dispatcher.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DispatcherExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */