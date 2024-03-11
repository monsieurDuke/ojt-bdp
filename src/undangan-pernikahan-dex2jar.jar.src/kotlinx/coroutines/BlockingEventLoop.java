package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004R\024\020\002\032\0020\003X\004¢\006\b\n\000\032\004\b\005\020\006¨\006\007"}, d2={"Lkotlinx/coroutines/BlockingEventLoop;", "Lkotlinx/coroutines/EventLoopImplBase;", "thread", "Ljava/lang/Thread;", "(Ljava/lang/Thread;)V", "getThread", "()Ljava/lang/Thread;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class BlockingEventLoop
  extends EventLoopImplBase
{
  private final Thread thread;
  
  public BlockingEventLoop(Thread paramThread)
  {
    this.thread = paramThread;
  }
  
  protected Thread getThread()
  {
    return this.thread;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/BlockingEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */