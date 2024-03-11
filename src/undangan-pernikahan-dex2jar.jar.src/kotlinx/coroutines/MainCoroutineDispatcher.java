package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.LimitedDispatcherKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\002\b\006\n\002\020\b\n\000\n\002\020\016\n\002\b\002\b&\030\0002\0020\001B\005¢\006\002\020\002J\020\020\006\032\0020\0012\006\020\007\032\0020\bH\026J\b\020\t\032\0020\nH\026J\n\020\013\032\004\030\0010\nH\005R\022\020\003\032\0020\000X¦\004¢\006\006\032\004\b\004\020\005¨\006\f"}, d2={"Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "immediate", "getImmediate", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "limitedParallelism", "parallelism", "", "toString", "", "toStringInternalImpl", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class MainCoroutineDispatcher
  extends CoroutineDispatcher
{
  public abstract MainCoroutineDispatcher getImmediate();
  
  public CoroutineDispatcher limitedParallelism(int paramInt)
  {
    LimitedDispatcherKt.checkParallelism(paramInt);
    return (CoroutineDispatcher)this;
  }
  
  public String toString()
  {
    Object localObject2 = toStringInternalImpl();
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject2 = new StringBuilder();
      localObject1 = DebugStringsKt.getClassSimpleName(this);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localObject2 = ((StringBuilder)localObject2).append((String)localObject1).append('@');
      localObject1 = DebugStringsKt.getHexAddress(this);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localObject1 = (String)localObject1;
    }
    return (String)localObject1;
  }
  
  protected final String toStringInternalImpl()
  {
    MainCoroutineDispatcher localMainCoroutineDispatcher1 = Dispatchers.getMain();
    if (this == localMainCoroutineDispatcher1) {
      return "Dispatchers.Main";
    }
    MainCoroutineDispatcher localMainCoroutineDispatcher2;
    try
    {
      localMainCoroutineDispatcher1 = localMainCoroutineDispatcher1.getImmediate();
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      localMainCoroutineDispatcher2 = (MainCoroutineDispatcher)null;
      localMainCoroutineDispatcher2 = null;
    }
    if (this == localMainCoroutineDispatcher2) {
      return "Dispatchers.Main.immediate";
    }
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/MainCoroutineDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */