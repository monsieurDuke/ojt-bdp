package kotlinx.coroutines.internal;

import java.util.List;
import kotlin.Metadata;
import kotlinx.coroutines.MainCoroutineDispatcher;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\000\bÇ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\007\032\0020\b2\f\020\t\032\b\022\004\022\0020\0010\nH\026R\024\020\003\032\0020\0048VX\004¢\006\006\032\004\b\005\020\006¨\006\013"}, d2={"Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcherFactory;", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "()V", "loadPriority", "", "getLoadPriority", "()I", "createDispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "allFactories", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class MissingMainCoroutineDispatcherFactory
  implements MainDispatcherFactory
{
  public static final MissingMainCoroutineDispatcherFactory INSTANCE = new MissingMainCoroutineDispatcherFactory();
  
  public MainCoroutineDispatcher createDispatcher(List<? extends MainDispatcherFactory> paramList)
  {
    return (MainCoroutineDispatcher)new MissingMainCoroutineDispatcher(null, null, 2, null);
  }
  
  public int getLoadPriority()
  {
    return -1;
  }
  
  public String hintOnError()
  {
    String str = MainDispatcherFactory.DefaultImpls.hintOnError((MainDispatcherFactory)this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/MissingMainCoroutineDispatcherFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */