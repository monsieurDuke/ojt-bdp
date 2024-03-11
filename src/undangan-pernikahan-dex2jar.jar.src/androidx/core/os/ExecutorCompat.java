package androidx.core.os;

import android.os.Handler;
import androidx.core.util.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public final class ExecutorCompat
{
  public static Executor create(Handler paramHandler)
  {
    return new HandlerExecutor(paramHandler);
  }
  
  private static class HandlerExecutor
    implements Executor
  {
    private final Handler mHandler;
    
    HandlerExecutor(Handler paramHandler)
    {
      this.mHandler = ((Handler)Preconditions.checkNotNull(paramHandler));
    }
    
    public void execute(Runnable paramRunnable)
    {
      if (this.mHandler.post((Runnable)Preconditions.checkNotNull(paramRunnable))) {
        return;
      }
      throw new RejectedExecutionException(this.mHandler + " is shutting down");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/ExecutorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */