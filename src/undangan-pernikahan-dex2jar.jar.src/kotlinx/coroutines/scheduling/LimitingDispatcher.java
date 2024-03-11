package kotlinx.coroutines.scheduling;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;

@Metadata(d1={"\000R\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\004\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\t\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\030\002\b\002\030\0002\0020+2\0020,2\0020\037B)\022\006\020\002\032\0020\001\022\006\020\004\032\0020\003\022\b\020\006\032\004\030\0010\005\022\006\020\007\032\0020\003¢\006\004\b\b\020\tJ\017\020\013\032\0020\nH\026¢\006\004\b\013\020\fJ\017\020\r\032\0020\nH\026¢\006\004\b\r\020\fJ#\020\023\032\0020\n2\006\020\017\032\0020\0162\n\020\022\032\0060\020j\002`\021H\026¢\006\004\b\023\020\024J#\020\023\032\0020\n2\n\020\022\032\0060\020j\002`\0212\006\020\026\032\0020\025H\002¢\006\004\b\023\020\027J#\020\030\032\0020\n2\006\020\017\032\0020\0162\n\020\022\032\0060\020j\002`\021H\026¢\006\004\b\030\020\024J\033\020\032\032\0020\n2\n\020\031\032\0060\020j\002`\021H\026¢\006\004\b\032\020\033J\017\020\034\032\0020\005H\026¢\006\004\b\034\020\035R\024\020\002\032\0020\0018\002X\004¢\006\006\n\004\b\002\020\036R\024\020\"\032\0020\0378VX\004¢\006\006\032\004\b \020!R\026\020\006\032\004\030\0010\0058\002X\004¢\006\006\n\004\b\006\020#R\024\020\004\032\0020\0038\002X\004¢\006\006\n\004\b\004\020$R\036\020&\032\f\022\b\022\0060\020j\002`\0210%8\002X\004¢\006\006\n\004\b&\020'R\032\020\007\032\0020\0038\026X\004¢\006\f\n\004\b\007\020$\032\004\b(\020)¨\006*"}, d2={"Lkotlinx/coroutines/scheduling/LimitingDispatcher;", "Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "dispatcher", "", "parallelism", "", "name", "taskMode", "<init>", "(Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;ILjava/lang/String;I)V", "", "afterTask", "()V", "close", "Lkotlin/coroutines/CoroutineContext;", "context", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "", "tailDispatch", "(Ljava/lang/Runnable;Z)V", "dispatchYield", "command", "execute", "(Ljava/lang/Runnable;)V", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "executor", "Ljava/lang/String;", "I", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "queue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "getTaskMode", "()I", "kotlinx-coroutines-core", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/scheduling/TaskContext;"}, k=1, mv={1, 6, 0}, xi=48)
final class LimitingDispatcher
  extends ExecutorCoroutineDispatcher
  implements TaskContext, Executor
{
  private static final AtomicIntegerFieldUpdater inFlightTasks$FU = AtomicIntegerFieldUpdater.newUpdater(LimitingDispatcher.class, "inFlightTasks");
  private final ExperimentalCoroutineDispatcher dispatcher;
  private volatile int inFlightTasks;
  private final String name;
  private final int parallelism;
  private final ConcurrentLinkedQueue<Runnable> queue;
  private final int taskMode;
  
  public LimitingDispatcher(ExperimentalCoroutineDispatcher paramExperimentalCoroutineDispatcher, int paramInt1, String paramString, int paramInt2)
  {
    this.dispatcher = paramExperimentalCoroutineDispatcher;
    this.parallelism = paramInt1;
    this.name = paramString;
    this.taskMode = paramInt2;
    this.queue = new ConcurrentLinkedQueue();
    this.inFlightTasks = 0;
  }
  
  private final void dispatch(Runnable paramRunnable, boolean paramBoolean)
  {
    for (;;)
    {
      AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = inFlightTasks$FU;
      if (localAtomicIntegerFieldUpdater.incrementAndGet(this) <= this.parallelism)
      {
        this.dispatcher.dispatchWithContext$kotlinx_coroutines_core(paramRunnable, (TaskContext)this, paramBoolean);
        return;
      }
      this.queue.add(paramRunnable);
      if (localAtomicIntegerFieldUpdater.decrementAndGet(this) >= this.parallelism) {
        return;
      }
      paramRunnable = (Runnable)this.queue.poll();
      if (paramRunnable == null) {
        return;
      }
    }
  }
  
  public void afterTask()
  {
    Runnable localRunnable = (Runnable)this.queue.poll();
    if (localRunnable != null)
    {
      this.dispatcher.dispatchWithContext$kotlinx_coroutines_core(localRunnable, (TaskContext)this, true);
      return;
    }
    inFlightTasks$FU.decrementAndGet(this);
    localRunnable = (Runnable)this.queue.poll();
    if (localRunnable == null) {
      return;
    }
    dispatch(localRunnable, true);
  }
  
  public void close()
  {
    throw new IllegalStateException("Close cannot be invoked on LimitingBlockingDispatcher".toString());
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    dispatch(paramRunnable, false);
  }
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    dispatch(paramRunnable, true);
  }
  
  public void execute(Runnable paramRunnable)
  {
    dispatch(paramRunnable, false);
  }
  
  public Executor getExecutor()
  {
    return (Executor)this;
  }
  
  public int getTaskMode()
  {
    return this.taskMode;
  }
  
  public String toString()
  {
    String str2 = this.name;
    String str1 = str2;
    if (str2 == null) {
      str1 = super.toString() + "[dispatcher = " + this.dispatcher + ']';
    }
    return str1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/LimitingDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */