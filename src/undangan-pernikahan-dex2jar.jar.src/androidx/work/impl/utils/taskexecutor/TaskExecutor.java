package androidx.work.impl.utils.taskexecutor;

import androidx.work.impl.utils.SerialExecutor;
import java.util.concurrent.Executor;

public abstract interface TaskExecutor
{
  public abstract void executeOnBackgroundThread(Runnable paramRunnable);
  
  public abstract SerialExecutor getBackgroundExecutor();
  
  public abstract Executor getMainThreadExecutor();
  
  public abstract void postToMainThread(Runnable paramRunnable);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/taskexecutor/TaskExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */