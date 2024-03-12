package androidx.work.impl;

import androidx.work.impl.model.WorkSpec;

public abstract interface Scheduler
{
  public static final int MAX_GREEDY_SCHEDULER_LIMIT = 200;
  public static final int MAX_SCHEDULER_LIMIT = 50;
  
  public abstract void cancel(String paramString);
  
  public abstract boolean hasLimitedSchedulingSlots();
  
  public abstract void schedule(WorkSpec... paramVarArgs);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/Scheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */