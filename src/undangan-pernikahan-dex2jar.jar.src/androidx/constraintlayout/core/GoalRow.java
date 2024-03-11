package androidx.constraintlayout.core;

public class GoalRow
  extends ArrayRow
{
  public GoalRow(Cache paramCache)
  {
    super(paramCache);
  }
  
  public void addError(SolverVariable paramSolverVariable)
  {
    super.addError(paramSolverVariable);
    paramSolverVariable.usageInRowCount -= 1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/GoalRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */