package androidx.work;

public abstract interface RunnableScheduler
{
  public abstract void cancel(Runnable paramRunnable);
  
  public abstract void scheduleWithDelay(long paramLong, Runnable paramRunnable);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/RunnableScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */