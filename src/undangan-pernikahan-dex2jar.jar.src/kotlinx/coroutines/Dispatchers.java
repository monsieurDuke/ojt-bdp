package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

@Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\007\n\002\020\002\n\000\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\023\032\0020\024H\007R\034\020\003\032\0020\0048\006X\004¢\006\016\n\000\022\004\b\005\020\002\032\004\b\006\020\007R\034\020\b\032\0020\0048\006X\004¢\006\016\n\000\022\004\b\t\020\002\032\004\b\n\020\007R\032\020\013\032\0020\f8FX\004¢\006\f\022\004\b\r\020\002\032\004\b\016\020\017R\034\020\020\032\0020\0048\006X\004¢\006\016\n\000\022\004\b\021\020\002\032\004\b\022\020\007¨\006\025"}, d2={"Lkotlinx/coroutines/Dispatchers;", "", "()V", "Default", "Lkotlinx/coroutines/CoroutineDispatcher;", "getDefault$annotations", "getDefault", "()Lkotlinx/coroutines/CoroutineDispatcher;", "IO", "getIO$annotations", "getIO", "Main", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "getMain$annotations", "getMain", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "Unconfined", "getUnconfined$annotations", "getUnconfined", "shutdown", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class Dispatchers
{
  private static final CoroutineDispatcher Default;
  public static final Dispatchers INSTANCE = new Dispatchers();
  private static final CoroutineDispatcher IO = (CoroutineDispatcher)DefaultIoScheduler.INSTANCE;
  private static final CoroutineDispatcher Unconfined;
  
  static
  {
    Default = (CoroutineDispatcher)DefaultScheduler.INSTANCE;
    Unconfined = (CoroutineDispatcher)Unconfined.INSTANCE;
  }
  
  public static final CoroutineDispatcher getDefault()
  {
    return Default;
  }
  
  public static final CoroutineDispatcher getIO()
  {
    return IO;
  }
  
  public static final MainCoroutineDispatcher getMain()
  {
    return MainDispatcherLoader.dispatcher;
  }
  
  public static final CoroutineDispatcher getUnconfined()
  {
    return Unconfined;
  }
  
  public final void shutdown()
  {
    DefaultExecutor.INSTANCE.shutdown();
    DefaultScheduler.INSTANCE.shutdown$kotlinx_coroutines_core();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/Dispatchers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */