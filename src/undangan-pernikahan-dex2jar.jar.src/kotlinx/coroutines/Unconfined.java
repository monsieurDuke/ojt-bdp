package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;

@Metadata(d1={"\0006\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\034\020\003\032\0020\0042\006\020\005\032\0020\0062\n\020\007\032\0060\bj\002`\tH\026J\020\020\n\032\0020\0132\006\020\005\032\0020\006H\026J\020\020\f\032\0020\0012\006\020\r\032\0020\016H\027J\b\020\017\032\0020\020H\026¨\006\021"}, d2={"Lkotlinx/coroutines/Unconfined;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "isDispatchNeeded", "", "limitedParallelism", "parallelism", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class Unconfined
  extends CoroutineDispatcher
{
  public static final Unconfined INSTANCE = new Unconfined();
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    paramCoroutineContext = (YieldContext)paramCoroutineContext.get((CoroutineContext.Key)YieldContext.Key);
    if (paramCoroutineContext != null)
    {
      paramCoroutineContext.dispatcherWasUnconfined = true;
      return;
    }
    throw new UnsupportedOperationException("Dispatchers.Unconfined.dispatch function can only be used by the yield function. If you wrap Unconfined dispatcher in your code, make sure you properly delegate isDispatchNeeded and dispatch calls.");
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    return false;
  }
  
  public CoroutineDispatcher limitedParallelism(int paramInt)
  {
    throw new UnsupportedOperationException("limitedParallelism is not supported for Dispatchers.Unconfined");
  }
  
  public String toString()
  {
    return "Dispatchers.Unconfined";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/Unconfined.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */