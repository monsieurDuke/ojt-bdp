package kotlinx.coroutines.internal;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\002\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\007\032\0020\006H\002R\016\020\003\032\0020\004X\004¢\006\002\n\000R\020\020\005\032\0020\0068\006X\004¢\006\002\n\000¨\006\b"}, d2={"Lkotlinx/coroutines/internal/MainDispatcherLoader;", "", "()V", "FAST_SERVICE_LOADER_ENABLED", "", "dispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "loadMainDispatcher", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class MainDispatcherLoader
{
  private static final boolean FAST_SERVICE_LOADER_ENABLED;
  public static final MainDispatcherLoader INSTANCE;
  public static final MainCoroutineDispatcher dispatcher;
  
  static
  {
    MainDispatcherLoader localMainDispatcherLoader = new MainDispatcherLoader();
    INSTANCE = localMainDispatcherLoader;
    FAST_SERVICE_LOADER_ENABLED = SystemPropsKt.systemProp("kotlinx.coroutines.fast.service.loader", true);
    dispatcher = localMainDispatcherLoader.loadMainDispatcher();
  }
  
  private final MainCoroutineDispatcher loadMainDispatcher()
  {
    Object localObject2;
    try
    {
      if (FAST_SERVICE_LOADER_ENABLED) {
        localObject2 = FastServiceLoader.INSTANCE.loadMainDispatcherFactory$kotlinx_coroutines_core();
      } else {
        localObject2 = SequencesKt.toList(SequencesKt.asSequence(ServiceLoader.load(MainDispatcherFactory.class, MainDispatcherFactory.class.getClassLoader()).iterator()));
      }
      Iterator localIterator = ((Iterable)localObject2).iterator();
      if (!localIterator.hasNext())
      {
        localObject1 = null;
      }
      else
      {
        localObject1 = localIterator.next();
        if (localIterator.hasNext())
        {
          int j = ((MainDispatcherFactory)localObject1).getLoadPriority();
          Object localObject3 = localObject1;
          do
          {
            Object localObject4 = localIterator.next();
            int k = ((MainDispatcherFactory)localObject4).getLoadPriority();
            localObject1 = localObject3;
            int i = j;
            if (j < k)
            {
              localObject1 = localObject4;
              i = k;
            }
            localObject3 = localObject1;
            j = i;
          } while (localIterator.hasNext());
        }
      }
      Object localObject1 = (MainDispatcherFactory)localObject1;
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = MainDispatchersKt.tryCreateDispatcher((MainDispatcherFactory)localObject1, (List)localObject2);
      }
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = (MainCoroutineDispatcher)MainDispatchersKt.createMissingDispatcher$default(null, null, 3, null);
      }
    }
    finally
    {
      localObject2 = (MainCoroutineDispatcher)MainDispatchersKt.createMissingDispatcher$default(localThrowable, null, 2, null);
    }
    return (MainCoroutineDispatcher)localObject2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/MainDispatcherLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */