package androidx.constraintlayout.core;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityGoalRow
  extends ArrayRow
{
  private static final boolean DEBUG = false;
  static final int NOT_FOUND = -1;
  private static final float epsilon = 1.0E-4F;
  private int TABLE_SIZE = 128;
  GoalVariableAccessor accessor = new GoalVariableAccessor(this);
  private SolverVariable[] arrayGoals = new SolverVariable[''];
  Cache mCache;
  private int numGoals = 0;
  private SolverVariable[] sortArray = new SolverVariable[''];
  
  public PriorityGoalRow(Cache paramCache)
  {
    super(paramCache);
    this.mCache = paramCache;
  }
  
  private final void addToGoal(SolverVariable paramSolverVariable)
  {
    int i = this.numGoals;
    SolverVariable[] arrayOfSolverVariable = this.arrayGoals;
    if (i + 1 > arrayOfSolverVariable.length)
    {
      arrayOfSolverVariable = (SolverVariable[])Arrays.copyOf(arrayOfSolverVariable, arrayOfSolverVariable.length * 2);
      this.arrayGoals = arrayOfSolverVariable;
      this.sortArray = ((SolverVariable[])Arrays.copyOf(arrayOfSolverVariable, arrayOfSolverVariable.length * 2));
    }
    arrayOfSolverVariable = this.arrayGoals;
    i = this.numGoals;
    arrayOfSolverVariable[i] = paramSolverVariable;
    i++;
    this.numGoals = i;
    if ((i > 1) && (arrayOfSolverVariable[(i - 1)].id > paramSolverVariable.id))
    {
      int j;
      for (i = 0;; i++)
      {
        j = this.numGoals;
        if (i >= j) {
          break;
        }
        this.sortArray[i] = this.arrayGoals[i];
      }
      Arrays.sort(this.sortArray, 0, j, new Comparator()
      {
        public int compare(SolverVariable paramAnonymousSolverVariable1, SolverVariable paramAnonymousSolverVariable2)
        {
          return paramAnonymousSolverVariable1.id - paramAnonymousSolverVariable2.id;
        }
      });
      for (i = 0; i < this.numGoals; i++) {
        this.arrayGoals[i] = this.sortArray[i];
      }
    }
    paramSolverVariable.inGoal = true;
    paramSolverVariable.addToRow(this);
  }
  
  private final void removeGoal(SolverVariable paramSolverVariable)
  {
    for (int i = 0; i < this.numGoals; i++) {
      if (this.arrayGoals[i] == paramSolverVariable)
      {
        int j;
        for (;;)
        {
          j = this.numGoals;
          if (i >= j - 1) {
            break;
          }
          SolverVariable[] arrayOfSolverVariable = this.arrayGoals;
          arrayOfSolverVariable[i] = arrayOfSolverVariable[(i + 1)];
          i++;
        }
        this.numGoals = (j - 1);
        paramSolverVariable.inGoal = false;
        return;
      }
    }
  }
  
  public void addError(SolverVariable paramSolverVariable)
  {
    this.accessor.init(paramSolverVariable);
    this.accessor.reset();
    paramSolverVariable.goalStrengthVector[paramSolverVariable.strength] = 1.0F;
    addToGoal(paramSolverVariable);
  }
  
  public void clear()
  {
    this.numGoals = 0;
    this.constantValue = 0.0F;
  }
  
  public SolverVariable getPivotCandidate(LinearSystem paramLinearSystem, boolean[] paramArrayOfBoolean)
  {
    int k = -1;
    int i = 0;
    while (i < this.numGoals)
    {
      paramLinearSystem = this.arrayGoals[i];
      int j;
      if (paramArrayOfBoolean[paramLinearSystem.id] != 0)
      {
        j = k;
      }
      else
      {
        this.accessor.init(paramLinearSystem);
        if (k == -1)
        {
          j = k;
          if (this.accessor.isNegative()) {
            j = i;
          }
        }
        else
        {
          j = k;
          if (this.accessor.isSmallerThan(this.arrayGoals[k])) {
            j = i;
          }
        }
      }
      i++;
      k = j;
    }
    if (k == -1) {
      return null;
    }
    return this.arrayGoals[k];
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.numGoals == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    String str = "" + " goal -> (" + this.constantValue + ") : ";
    for (int i = 0; i < this.numGoals; i++)
    {
      SolverVariable localSolverVariable = this.arrayGoals[i];
      this.accessor.init(localSolverVariable);
      str = str + this.accessor + " ";
    }
    return str;
  }
  
  public void updateFromRow(LinearSystem paramLinearSystem, ArrayRow paramArrayRow, boolean paramBoolean)
  {
    paramLinearSystem = paramArrayRow.variable;
    if (paramLinearSystem == null) {
      return;
    }
    ArrayRow.ArrayRowVariables localArrayRowVariables = paramArrayRow.variables;
    int j = localArrayRowVariables.getCurrentSize();
    for (int i = 0; i < j; i++)
    {
      SolverVariable localSolverVariable = localArrayRowVariables.getVariable(i);
      float f = localArrayRowVariables.getVariableValue(i);
      this.accessor.init(localSolverVariable);
      if (this.accessor.addToGoal(paramLinearSystem, f)) {
        addToGoal(localSolverVariable);
      }
      this.constantValue += paramArrayRow.constantValue * f;
    }
    removeGoal(paramLinearSystem);
  }
  
  class GoalVariableAccessor
  {
    PriorityGoalRow row;
    SolverVariable variable;
    
    public GoalVariableAccessor(PriorityGoalRow paramPriorityGoalRow)
    {
      this.row = paramPriorityGoalRow;
    }
    
    public void add(SolverVariable paramSolverVariable)
    {
      for (int i = 0; i < 9; i++)
      {
        float[] arrayOfFloat = this.variable.goalStrengthVector;
        arrayOfFloat[i] += paramSolverVariable.goalStrengthVector[i];
        if (Math.abs(this.variable.goalStrengthVector[i]) < 1.0E-4F) {
          this.variable.goalStrengthVector[i] = 0.0F;
        }
      }
    }
    
    public boolean addToGoal(SolverVariable paramSolverVariable, float paramFloat)
    {
      if (this.variable.inGoal)
      {
        int j = 1;
        for (i = 0; i < 9; i++)
        {
          float[] arrayOfFloat = this.variable.goalStrengthVector;
          arrayOfFloat[i] += paramSolverVariable.goalStrengthVector[i] * paramFloat;
          if (Math.abs(this.variable.goalStrengthVector[i]) < 1.0E-4F) {
            this.variable.goalStrengthVector[i] = 0.0F;
          } else {
            j = 0;
          }
        }
        if (j != 0) {
          PriorityGoalRow.this.removeGoal(this.variable);
        }
        return false;
      }
      for (int i = 0; i < 9; i++)
      {
        float f1 = paramSolverVariable.goalStrengthVector[i];
        if (f1 != 0.0F)
        {
          float f2 = paramFloat * f1;
          f1 = f2;
          if (Math.abs(f2) < 1.0E-4F) {
            f1 = 0.0F;
          }
          this.variable.goalStrengthVector[i] = f1;
        }
        else
        {
          this.variable.goalStrengthVector[i] = 0.0F;
        }
      }
      return true;
    }
    
    public void init(SolverVariable paramSolverVariable)
    {
      this.variable = paramSolverVariable;
    }
    
    public final boolean isNegative()
    {
      for (int i = 8; i >= 0; i--)
      {
        float f = this.variable.goalStrengthVector[i];
        if (f > 0.0F) {
          return false;
        }
        if (f < 0.0F) {
          return true;
        }
      }
      return false;
    }
    
    public final boolean isNull()
    {
      for (int i = 0; i < 9; i++) {
        if (this.variable.goalStrengthVector[i] != 0.0F) {
          return false;
        }
      }
      return true;
    }
    
    public final boolean isSmallerThan(SolverVariable paramSolverVariable)
    {
      int i = 8;
      while (i >= 0)
      {
        float f1 = paramSolverVariable.goalStrengthVector[i];
        float f2 = this.variable.goalStrengthVector[i];
        if (f2 == f1) {
          i--;
        } else {
          return f2 < f1;
        }
      }
      return false;
    }
    
    public void reset()
    {
      Arrays.fill(this.variable.goalStrengthVector, 0.0F);
    }
    
    public String toString()
    {
      String str1 = "[ ";
      String str2 = str1;
      if (this.variable != null) {
        for (int i = 0;; i++)
        {
          str2 = str1;
          if (i >= 9) {
            break;
          }
          str1 = str1 + this.variable.goalStrengthVector[i] + " ";
        }
      }
      return str2 + "] " + this.variable;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/PriorityGoalRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */