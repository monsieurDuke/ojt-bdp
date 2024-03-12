package kotlinx.coroutines.android;

import android.os.Looper;
import java.util.List;
import kotlin.Metadata;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherFactory;

@Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\000\n\002\020\016\n\000\b\000\030\0002\0020\001B\005¢\006\002\020\002J\026\020\007\032\0020\b2\f\020\t\032\b\022\004\022\0020\0010\nH\026J\b\020\013\032\0020\fH\026R\024\020\003\032\0020\0048VX\004¢\006\006\032\004\b\005\020\006¨\006\r"}, d2={"Lkotlinx/coroutines/android/AndroidDispatcherFactory;", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "()V", "loadPriority", "", "getLoadPriority", "()I", "createDispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "allFactories", "", "hintOnError", "", "kotlinx-coroutines-android"}, k=1, mv={1, 6, 0}, xi=48)
public final class AndroidDispatcherFactory
  implements MainDispatcherFactory
{
  public MainCoroutineDispatcher createDispatcher(List<? extends MainDispatcherFactory> paramList)
  {
    paramList = Looper.getMainLooper();
    if (paramList != null) {
      return (MainCoroutineDispatcher)new HandlerContext(HandlerDispatcherKt.asHandler(paramList, true), null, 2, null);
    }
    throw new IllegalStateException("The main looper is not available");
  }
  
  public int getLoadPriority()
  {
    return 1073741823;
  }
  
  public String hintOnError()
  {
    return "For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/android/AndroidDispatcherFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */