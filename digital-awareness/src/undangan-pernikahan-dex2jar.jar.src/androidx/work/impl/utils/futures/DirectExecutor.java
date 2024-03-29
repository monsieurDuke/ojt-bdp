package androidx.work.impl.utils.futures;

import java.util.concurrent.Executor;

 enum DirectExecutor
  implements Executor
{
  private static final DirectExecutor[] $VALUES;
  
  static
  {
    DirectExecutor localDirectExecutor = new DirectExecutor("INSTANCE", 0);
    INSTANCE = localDirectExecutor;
    $VALUES = new DirectExecutor[] { localDirectExecutor };
  }
  
  private DirectExecutor() {}
  
  public void execute(Runnable paramRunnable)
  {
    paramRunnable.run();
  }
  
  public String toString()
  {
    return "DirectExecutor";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/futures/DirectExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */