package kotlinx.coroutines.android;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.Delay.DefaultImpls;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\b6\030\0002\0020\0012\0020\002B\007\b\004¢\006\002\020\003R\022\020\004\032\0020\000X¦\004¢\006\006\032\004\b\005\020\006\001\001\007¨\006\b"}, d2={"Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "()V", "immediate", "getImmediate", "()Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/android/HandlerContext;", "kotlinx-coroutines-android"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class HandlerDispatcher
  extends MainCoroutineDispatcher
  implements Delay
{
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated without replacement as an internal method never intended for public use")
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    return Delay.DefaultImpls.delay((Delay)this, paramLong, paramContinuation);
  }
  
  public abstract HandlerDispatcher getImmediate();
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
  {
    return Delay.DefaultImpls.invokeOnTimeout((Delay)this, paramLong, paramRunnable, paramCoroutineContext);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/android/HandlerDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */