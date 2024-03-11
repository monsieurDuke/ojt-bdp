package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

@Metadata(d1={"\000\032\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\t\n\000\032\b\020\000\032\0020\001H\000\032\031\020\002\032\0020\0032\016\b\004\020\004\032\b\022\004\022\0020\0030\005H\b\032\b\020\006\032\0020\007H\007¨\006\b"}, d2={"createEventLoop", "Lkotlinx/coroutines/EventLoop;", "platformAutoreleasePool", "", "block", "Lkotlin/Function0;", "processNextEventInCurrentThread", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class EventLoopKt
{
  public static final EventLoop createEventLoop()
  {
    return (EventLoop)new BlockingEventLoop(Thread.currentThread());
  }
  
  public static final void platformAutoreleasePool(Function0<Unit> paramFunction0)
  {
    paramFunction0.invoke();
  }
  
  public static final long processNextEventInCurrentThread()
  {
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.currentOrNull$kotlinx_coroutines_core();
    long l;
    if (localEventLoop == null) {
      l = Long.MAX_VALUE;
    } else {
      l = localEventLoop.processNextEvent();
    }
    return l;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/EventLoopKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */