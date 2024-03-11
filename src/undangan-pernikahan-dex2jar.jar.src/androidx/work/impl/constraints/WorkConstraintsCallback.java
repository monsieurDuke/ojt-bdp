package androidx.work.impl.constraints;

import java.util.List;

public abstract interface WorkConstraintsCallback
{
  public abstract void onAllConstraintsMet(List<String> paramList);
  
  public abstract void onAllConstraintsNotMet(List<String> paramList);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/WorkConstraintsCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */