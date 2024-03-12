package kotlinx.coroutines.android;

import android.os.Build.VERSION;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlinx.coroutines.CoroutineExceptionHandler;

@Metadata(d1={"\000.\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\003\n\000\n\002\030\002\n\000\b\001\030\0002\0020\0012\0020\002B\005¢\006\002\020\003J\030\020\006\032\0020\0072\006\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\n\020\f\032\004\030\0010\rH\002R\020\020\004\032\004\030\0010\005X\016¢\006\002\n\000¨\006\016"}, d2={"Lkotlinx/coroutines/android/AndroidExceptionPreHandler;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "()V", "_preHandler", "", "handleException", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "preHandler", "Ljava/lang/reflect/Method;", "kotlinx-coroutines-android"}, k=1, mv={1, 6, 0}, xi=48)
public final class AndroidExceptionPreHandler
  extends AbstractCoroutineContextElement
  implements CoroutineExceptionHandler
{
  private volatile Object _preHandler = this;
  
  public AndroidExceptionPreHandler()
  {
    super((CoroutineContext.Key)CoroutineExceptionHandler.Key);
  }
  
  private final Method preHandler()
  {
    Object localObject1 = this._preHandler;
    if (localObject1 != this) {
      return (Method)localObject1;
    }
    localObject1 = null;
    int j = 0;
    try
    {
      Method localMethod1 = Thread.class.getDeclaredMethod("getUncaughtExceptionPreHandler", new Class[0]);
      int i = j;
      if (Modifier.isPublic(localMethod1.getModifiers()))
      {
        boolean bool = Modifier.isStatic(localMethod1.getModifiers());
        i = j;
        if (bool) {
          i = 1;
        }
      }
      if (i != 0) {
        localObject1 = localMethod1;
      }
    }
    finally
    {
      Method localMethod2 = (Method)null;
    }
    this._preHandler = localObject1;
    return (Method)localObject1;
  }
  
  public void handleException(CoroutineContext paramCoroutineContext, Throwable paramThrowable)
  {
    int i = Build.VERSION.SDK_INT;
    if ((26 <= i) && (i < 28)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramCoroutineContext = preHandler();
      Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = null;
      if (paramCoroutineContext == null) {
        paramCoroutineContext = null;
      } else {
        paramCoroutineContext = paramCoroutineContext.invoke(null, new Object[0]);
      }
      if ((paramCoroutineContext instanceof Thread.UncaughtExceptionHandler)) {
        localUncaughtExceptionHandler = (Thread.UncaughtExceptionHandler)paramCoroutineContext;
      }
      if (localUncaughtExceptionHandler != null) {
        localUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), paramThrowable);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/android/AndroidExceptionPreHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */