package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\004\bÁ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\017\020\n\032\004\030\0010\004H\000¢\006\002\b\013J\r\020\f\032\0020\rH\000¢\006\002\b\016J\025\020\017\032\0020\r2\006\020\003\032\0020\004H\000¢\006\002\b\020R\024\020\003\032\0020\0048@X\004¢\006\006\032\004\b\005\020\006R\"\020\007\032\026\022\006\022\004\030\0010\0040\bj\n\022\006\022\004\030\0010\004`\tX\004¢\006\002\n\000¨\006\021"}, d2={"Lkotlinx/coroutines/ThreadLocalEventLoop;", "", "()V", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "getEventLoop$kotlinx_coroutines_core", "()Lkotlinx/coroutines/EventLoop;", "ref", "Ljava/lang/ThreadLocal;", "Lkotlinx/coroutines/internal/CommonThreadLocal;", "currentOrNull", "currentOrNull$kotlinx_coroutines_core", "resetEventLoop", "", "resetEventLoop$kotlinx_coroutines_core", "setEventLoop", "setEventLoop$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ThreadLocalEventLoop
{
  public static final ThreadLocalEventLoop INSTANCE = new ThreadLocalEventLoop();
  private static final ThreadLocal<EventLoop> ref = new ThreadLocal();
  
  public final EventLoop currentOrNull$kotlinx_coroutines_core()
  {
    return (EventLoop)ref.get();
  }
  
  public final EventLoop getEventLoop$kotlinx_coroutines_core()
  {
    ThreadLocal localThreadLocal = ref;
    EventLoop localEventLoop2 = (EventLoop)localThreadLocal.get();
    EventLoop localEventLoop1 = localEventLoop2;
    if (localEventLoop2 == null)
    {
      localEventLoop1 = EventLoopKt.createEventLoop();
      localThreadLocal.set(localEventLoop1);
    }
    return localEventLoop1;
  }
  
  public final void resetEventLoop$kotlinx_coroutines_core()
  {
    ref.set(null);
  }
  
  public final void setEventLoop$kotlinx_coroutines_core(EventLoop paramEventLoop)
  {
    ref.set(paramEventLoop);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ThreadLocalEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */