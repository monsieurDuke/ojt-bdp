package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.MainDispatchersKt;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(d1={"\000\022\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\032\b\020\006\032\0020\001H\002\"\024\020\000\032\0020\001X\004¢\006\b\n\000\032\004\b\002\020\003\"\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\007"}, d2={"DefaultDelay", "Lkotlinx/coroutines/Delay;", "getDefaultDelay", "()Lkotlinx/coroutines/Delay;", "defaultMainDelayOptIn", "", "initializeDefaultDelay", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class DefaultExecutorKt
{
  private static final Delay DefaultDelay = initializeDefaultDelay();
  private static final boolean defaultMainDelayOptIn = SystemPropsKt.systemProp("kotlinx.coroutines.main.delay", false);
  
  public static final Delay getDefaultDelay()
  {
    return DefaultDelay;
  }
  
  private static final Delay initializeDefaultDelay()
  {
    if (!defaultMainDelayOptIn) {
      return (Delay)DefaultExecutor.INSTANCE;
    }
    Object localObject = Dispatchers.getMain();
    if ((!MainDispatchersKt.isMissing((MainCoroutineDispatcher)localObject)) && ((localObject instanceof Delay))) {
      localObject = (Delay)localObject;
    } else {
      localObject = (Delay)DefaultExecutor.INSTANCE;
    }
    return (Delay)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DefaultExecutorKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */