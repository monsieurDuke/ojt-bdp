package androidx.work.impl.utils;

import java.util.concurrent.Executor;

public class SynchronousExecutor
  implements Executor
{
  public void execute(Runnable paramRunnable)
  {
    paramRunnable.run();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/SynchronousExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */