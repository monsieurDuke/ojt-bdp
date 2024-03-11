package androidx.constraintlayout.core;

import java.util.ArrayList;

public class ArrayRow
  implements LinearSystem.Row
{
  private static final boolean DEBUG = false;
  private static final boolean FULL_NEW_CHECK = false;
  float constantValue = 0.0F;
  boolean isSimpleDefinition = false;
  boolean used = false;
  SolverVariable variable = null;
  public ArrayRowVariables variables;
  ArrayList<SolverVariable> variablesToUpdate = new ArrayList();
  
  public ArrayRow() {}
  
  public ArrayRow(Cache paramCache)
  {
    this.variables = new ArrayLinkedVariables(this, paramCache);
  }
  
  private boolean isNew(SolverVariable paramSolverVariable, LinearSystem paramLinearSystem)
  {
    int i = paramSolverVariable.usageInRowCount;
    boolean bool = true;
    if (i > 1) {
      bool = false;
    }
    return bool;
  }
  
  private SolverVariable pickPivotInVariables(boolean[] paramArrayOfBoolean, SolverVariable paramSolverVariable)
  {
    float f5 = 0.0F;
    Object localObject2 = null;
    Object localObject1 = null;
    float f3 = 0.0F;
    int j = this.variables.getCurrentSize();
    int i = 0;
    while (i < j)
    {
      float f2 = this.variables.getVariableValue(i);
      float f1 = f5;
      Object localObject3 = localObject2;
      Object localObject4 = localObject1;
      float f4 = f3;
      if (f2 < 0.0F)
      {
        SolverVariable localSolverVariable = this.variables.getVariable(i);
        if (paramArrayOfBoolean != null)
        {
          f1 = f5;
          localObject3 = localObject2;
          localObject4 = localObject1;
          f4 = f3;
          if (paramArrayOfBoolean[localSolverVariable.id] != 0) {}
        }
        else
        {
          f1 = f5;
          localObject3 = localObject2;
          localObject4 = localObject1;
          f4 = f3;
          if (localSolverVariable != paramSolverVariable) {
            if (1 != 0)
            {
              if (localSolverVariable.mType != SolverVariable.Type.SLACK)
              {
                f1 = f5;
                localObject3 = localObject2;
                localObject4 = localObject1;
                f4 = f3;
                if (localSolverVariable.mType != SolverVariable.Type.ERROR) {}
              }
              else
              {
                f1 = f5;
                localObject3 = localObject2;
                localObject4 = localObject1;
                f4 = f3;
                if (f2 < f5)
                {
                  f1 = f2;
                  localObject3 = localSolverVariable;
                  localObject4 = localObject1;
                  f4 = f3;
                }
              }
            }
            else if (localSolverVariable.mType == SolverVariable.Type.SLACK)
            {
              f1 = f5;
              localObject3 = localObject2;
              localObject4 = localObject1;
              f4 = f3;
              if (f2 < f3)
              {
                f1 = f5;
                localObject3 = localObject2;
                localObject4 = localSolverVariable;
                f4 = f2;
              }
            }
            else
            {
              f1 = f5;
              localObject3 = localObject2;
              localObject4 = localObject1;
              f4 = f3;
              if (localSolverVariable.mType == SolverVariable.Type.ERROR)
              {
                f1 = f5;
                localObject3 = localObject2;
                localObject4 = localObject1;
                f4 = f3;
                if (f2 < f5)
                {
                  f1 = f2;
                  localObject3 = localSolverVariable;
                  f4 = f3;
                  localObject4 = localObject1;
                }
              }
            }
          }
        }
      }
      i++;
      f5 = f1;
      localObject2 = localObject3;
      localObject1 = localObject4;
      f3 = f4;
    }
    if (1 != 0) {
      return (SolverVariable)localObject2;
    }
    if (localObject2 != null) {
      paramArrayOfBoolean = (boolean[])localObject2;
    } else {
      paramArrayOfBoolean = (boolean[])localObject1;
    }
    return paramArrayOfBoolean;
  }
  
  public ArrayRow addError(LinearSystem paramLinearSystem, int paramInt)
  {
    this.variables.put(paramLinearSystem.createErrorVariable(paramInt, "ep"), 1.0F);
    this.variables.put(paramLinearSystem.createErrorVariable(paramInt, "em"), -1.0F);
    return this;
  }
  
  public void addError(SolverVariable paramSolverVariable)
  {
    float f = 1.0F;
    if (paramSolverVariable.strength == 1) {
      f = 1.0F;
    } else if (paramSolverVariable.strength == 2) {
      f = 1000.0F;
    } else if (paramSolverVariable.strength == 3) {
      f = 1000000.0F;
    } else if (paramSolverVariable.strength == 4) {
      f = 1.0E9F;
    } else if (paramSolverVariable.strength == 5) {
      f = 1.0E12F;
    }
    this.variables.put(paramSolverVariable, f);
  }
  
  ArrayRow addSingleError(SolverVariable paramSolverVariable, int paramInt)
  {
    this.variables.put(paramSolverVariable, paramInt);
    return this;
  }
  
  boolean chooseSubject(LinearSystem paramLinearSystem)
  {
    boolean bool = false;
    paramLinearSystem = chooseSubjectInVariables(paramLinearSystem);
    if (paramLinearSystem == null) {
      bool = true;
    } else {
      pivot(paramLinearSystem);
    }
    if (this.variables.getCurrentSize() == 0) {
      this.isSimpleDefinition = true;
    }
    return bool;
  }
  
  SolverVariable chooseSubjectInVariables(LinearSystem paramLinearSystem)
  {
    Object localObject4 = null;
    Object localObject3 = null;
    float f5 = 0.0F;
    float f4 = 0.0F;
    boolean bool4 = false;
    boolean bool3 = false;
    int j = this.variables.getCurrentSize();
    int i = 0;
    while (i < j)
    {
      float f1 = this.variables.getVariableValue(i);
      SolverVariable localSolverVariable = this.variables.getVariable(i);
      Object localObject2;
      boolean bool1;
      Object localObject1;
      float f2;
      float f3;
      boolean bool2;
      if (localSolverVariable.mType == SolverVariable.Type.UNRESTRICTED)
      {
        if (localObject3 == null)
        {
          localObject2 = localSolverVariable;
          bool1 = isNew(localSolverVariable, paramLinearSystem);
          localObject1 = localObject4;
          f2 = f1;
          f3 = f4;
          bool2 = bool3;
        }
        else if (f5 > f1)
        {
          localObject2 = localSolverVariable;
          bool1 = isNew(localSolverVariable, paramLinearSystem);
          localObject1 = localObject4;
          f2 = f1;
          f3 = f4;
          bool2 = bool3;
        }
        else
        {
          localObject1 = localObject4;
          localObject2 = localObject3;
          f2 = f5;
          f3 = f4;
          bool1 = bool4;
          bool2 = bool3;
          if (!bool4)
          {
            localObject1 = localObject4;
            localObject2 = localObject3;
            f2 = f5;
            f3 = f4;
            bool1 = bool4;
            bool2 = bool3;
            if (isNew(localSolverVariable, paramLinearSystem))
            {
              bool1 = true;
              localObject1 = localObject4;
              localObject2 = localSolverVariable;
              f2 = f1;
              f3 = f4;
              bool2 = bool3;
            }
          }
        }
      }
      else
      {
        localObject1 = localObject4;
        localObject2 = localObject3;
        f2 = f5;
        f3 = f4;
        bool1 = bool4;
        bool2 = bool3;
        if (localObject3 == null)
        {
          localObject1 = localObject4;
          localObject2 = localObject3;
          f2 = f5;
          f3 = f4;
          bool1 = bool4;
          bool2 = bool3;
          if (f1 < 0.0F) {
            if (localObject4 == null)
            {
              localObject1 = localSolverVariable;
              bool2 = isNew(localSolverVariable, paramLinearSystem);
              localObject2 = localObject3;
              f2 = f5;
              f3 = f1;
              bool1 = bool4;
            }
            else if (f4 > f1)
            {
              localObject1 = localSolverVariable;
              bool2 = isNew(localSolverVariable, paramLinearSystem);
              localObject2 = localObject3;
              f2 = f5;
              f3 = f1;
              bool1 = bool4;
            }
            else
            {
              localObject1 = localObject4;
              localObject2 = localObject3;
              f2 = f5;
              f3 = f4;
              bool1 = bool4;
              bool2 = bool3;
              if (!bool3)
              {
                localObject1 = localObject4;
                localObject2 = localObject3;
                f2 = f5;
                f3 = f4;
                bool1 = bool4;
                bool2 = bool3;
                if (isNew(localSolverVariable, paramLinearSystem))
                {
                  bool2 = true;
                  bool1 = bool4;
                  f3 = f1;
                  f2 = f5;
                  localObject2 = localObject3;
                  localObject1 = localSolverVariable;
                }
              }
            }
          }
        }
      }
      i++;
      localObject4 = localObject1;
      localObject3 = localObject2;
      f5 = f2;
      f4 = f3;
      bool4 = bool1;
      bool3 = bool2;
    }
    if (localObject3 != null) {
      return (SolverVariable)localObject3;
    }
    return (SolverVariable)localObject4;
  }
  
  public void clear()
  {
    this.variables.clear();
    this.variable = null;
    this.constantValue = 0.0F;
  }
  
  ArrayRow createRowCentering(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2)
  {
    if (paramSolverVariable2 == paramSolverVariable3)
    {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable2, -2.0F);
      return this;
    }
    if (paramFloat == 0.5F)
    {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      if ((paramInt1 > 0) || (paramInt2 > 0)) {
        this.constantValue = (-paramInt1 + paramInt2);
      }
    }
    else if (paramFloat <= 0.0F)
    {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.constantValue = paramInt1;
    }
    else if (paramFloat >= 1.0F)
    {
      this.variables.put(paramSolverVariable4, -1.0F);
      this.variables.put(paramSolverVariable3, 1.0F);
      this.constantValue = (-paramInt2);
    }
    else
    {
      this.variables.put(paramSolverVariable1, (1.0F - paramFloat) * 1.0F);
      this.variables.put(paramSolverVariable2, (1.0F - paramFloat) * -1.0F);
      this.variables.put(paramSolverVariable3, -1.0F * paramFloat);
      this.variables.put(paramSolverVariable4, paramFloat * 1.0F);
      if ((paramInt1 > 0) || (paramInt2 > 0)) {
        this.constantValue = (-paramInt1 * (1.0F - paramFloat) + paramInt2 * paramFloat);
      }
    }
    return this;
  }
  
  ArrayRow createRowDefinition(SolverVariable paramSolverVariable, int paramInt)
  {
    this.variable = paramSolverVariable;
    paramSolverVariable.computedValue = paramInt;
    this.constantValue = paramInt;
    this.isSimpleDefinition = true;
    return this;
  }
  
  ArrayRow createRowDimensionPercent(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, float paramFloat)
  {
    this.variables.put(paramSolverVariable1, -1.0F);
    this.variables.put(paramSolverVariable2, paramFloat);
    return this;
  }
  
  public ArrayRow createRowDimensionRatio(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat)
  {
    this.variables.put(paramSolverVariable1, -1.0F);
    this.variables.put(paramSolverVariable2, 1.0F);
    this.variables.put(paramSolverVariable3, paramFloat);
    this.variables.put(paramSolverVariable4, -paramFloat);
    return this;
  }
  
  public ArrayRow createRowEqualDimension(float paramFloat1, float paramFloat2, float paramFloat3, SolverVariable paramSolverVariable1, int paramInt1, SolverVariable paramSolverVariable2, int paramInt2, SolverVariable paramSolverVariable3, int paramInt3, SolverVariable paramSolverVariable4, int paramInt4)
  {
    if ((paramFloat2 != 0.0F) && (paramFloat1 != paramFloat3))
    {
      paramFloat1 = paramFloat1 / paramFloat2 / (paramFloat3 / paramFloat2);
      this.constantValue = (-paramInt1 - paramInt2 + paramInt3 * paramFloat1 + paramInt4 * paramFloat1);
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable4, paramFloat1);
      this.variables.put(paramSolverVariable3, -paramFloat1);
    }
    else
    {
      this.constantValue = (-paramInt1 - paramInt2 + paramInt3 + paramInt4);
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
    }
    return this;
  }
  
  public ArrayRow createRowEqualMatchDimensions(float paramFloat1, float paramFloat2, float paramFloat3, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4)
  {
    this.constantValue = 0.0F;
    if ((paramFloat2 != 0.0F) && (paramFloat1 != paramFloat3))
    {
      if (paramFloat1 == 0.0F)
      {
        this.variables.put(paramSolverVariable1, 1.0F);
        this.variables.put(paramSolverVariable2, -1.0F);
      }
      else if (paramFloat3 == 0.0F)
      {
        this.variables.put(paramSolverVariable3, 1.0F);
        this.variables.put(paramSolverVariable4, -1.0F);
      }
      else
      {
        paramFloat1 = paramFloat1 / paramFloat2 / (paramFloat3 / paramFloat2);
        this.variables.put(paramSolverVariable1, 1.0F);
        this.variables.put(paramSolverVariable2, -1.0F);
        this.variables.put(paramSolverVariable4, paramFloat1);
        this.variables.put(paramSolverVariable3, -paramFloat1);
      }
    }
    else
    {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
    }
    return this;
  }
  
  public ArrayRow createRowEquals(SolverVariable paramSolverVariable, int paramInt)
  {
    if (paramInt < 0)
    {
      this.constantValue = (paramInt * -1);
      this.variables.put(paramSolverVariable, 1.0F);
    }
    else
    {
      this.constantValue = paramInt;
      this.variables.put(paramSolverVariable, -1.0F);
    }
    return this;
  }
  
  public ArrayRow createRowEquals(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt)
  {
    int i = 0;
    int k = 0;
    if (paramInt != 0)
    {
      int j = paramInt;
      paramInt = k;
      i = j;
      if (j < 0)
      {
        i = j * -1;
        paramInt = 1;
      }
      this.constantValue = i;
      i = paramInt;
    }
    if (i == 0)
    {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
    }
    else
    {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
    }
    return this;
  }
  
  public ArrayRow createRowGreaterThan(SolverVariable paramSolverVariable1, int paramInt, SolverVariable paramSolverVariable2)
  {
    this.constantValue = paramInt;
    this.variables.put(paramSolverVariable1, -1.0F);
    return this;
  }
  
  public ArrayRow createRowGreaterThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, int paramInt)
  {
    int i = 0;
    int j = 0;
    if (paramInt != 0)
    {
      i = paramInt;
      paramInt = j;
      j = i;
      if (i < 0)
      {
        j = i * -1;
        paramInt = 1;
      }
      this.constantValue = j;
      i = paramInt;
    }
    if (i == 0)
    {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.variables.put(paramSolverVariable3, 1.0F);
    }
    else
    {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
    }
    return this;
  }
  
  public ArrayRow createRowLowerThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, int paramInt)
  {
    int i = 0;
    int k = 0;
    if (paramInt != 0)
    {
      int j = paramInt;
      paramInt = k;
      i = j;
      if (j < 0)
      {
        i = j * -1;
        paramInt = 1;
      }
      this.constantValue = i;
      i = paramInt;
    }
    if (i == 0)
    {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
    }
    else
    {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable3, 1.0F);
    }
    return this;
  }
  
  public ArrayRow createRowWithAngle(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat)
  {
    this.variables.put(paramSolverVariable3, 0.5F);
    this.variables.put(paramSolverVariable4, 0.5F);
    this.variables.put(paramSolverVariable1, -0.5F);
    this.variables.put(paramSolverVariable2, -0.5F);
    this.constantValue = (-paramFloat);
    return this;
  }
  
  void ensurePositiveConstant()
  {
    float f = this.constantValue;
    if (f < 0.0F)
    {
      this.constantValue = (f * -1.0F);
      this.variables.invert();
    }
  }
  
  public SolverVariable getKey()
  {
    return this.variable;
  }
  
  public SolverVariable getPivotCandidate(LinearSystem paramLinearSystem, boolean[] paramArrayOfBoolean)
  {
    return pickPivotInVariables(paramArrayOfBoolean, null);
  }
  
  boolean hasKeyVariable()
  {
    SolverVariable localSolverVariable = this.variable;
    boolean bool;
    if ((localSolverVariable != null) && ((localSolverVariable.mType == SolverVariable.Type.UNRESTRICTED) || (this.constantValue >= 0.0F))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean hasVariable(SolverVariable paramSolverVariable)
  {
    return this.variables.contains(paramSolverVariable);
  }
  
  public void initFromRow(LinearSystem.Row paramRow)
  {
    if ((paramRow instanceof ArrayRow))
    {
      ArrayRow localArrayRow = (ArrayRow)paramRow;
      this.variable = null;
      this.variables.clear();
      for (int i = 0; i < localArrayRow.variables.getCurrentSize(); i++)
      {
        paramRow = localArrayRow.variables.getVariable(i);
        float f = localArrayRow.variables.getVariableValue(i);
        this.variables.add(paramRow, f, true);
      }
    }
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if ((this.variable == null) && (this.constantValue == 0.0F) && (this.variables.getCurrentSize() == 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public SolverVariable pickPivot(SolverVariable paramSolverVariable)
  {
    return pickPivotInVariables(null, paramSolverVariable);
  }
  
  void pivot(SolverVariable paramSolverVariable)
  {
    SolverVariable localSolverVariable = this.variable;
    if (localSolverVariable != null)
    {
      this.variables.put(localSolverVariable, -1.0F);
      this.variable.definitionId = -1;
      this.variable = null;
    }
    float f = this.variables.remove(paramSolverVariable, true) * -1.0F;
    this.variable = paramSolverVariable;
    if (f == 1.0F) {
      return;
    }
    this.constantValue /= f;
    this.variables.divideByAmount(f);
  }
  
  public void reset()
  {
    this.variable = null;
    this.variables.clear();
    this.constantValue = 0.0F;
    this.isSimpleDefinition = false;
  }
  
  int sizeInBytes()
  {
    int i = 0;
    if (this.variable != null) {
      i = 0 + 4;
    }
    return i + 4 + 4 + this.variables.sizeInBytes();
  }
  
  String toReadableString()
  {
    if (this.variable == null) {
      localObject1 = "" + "0";
    } else {
      localObject1 = "" + this.variable;
    }
    Object localObject2 = (String)localObject1 + " = ";
    int i = 0;
    Object localObject1 = localObject2;
    if (this.constantValue != 0.0F)
    {
      localObject1 = (String)localObject2 + this.constantValue;
      i = 1;
    }
    int k = this.variables.getCurrentSize();
    for (int j = 0; j < k; j++)
    {
      localObject2 = this.variables.getVariable(j);
      if (localObject2 != null)
      {
        float f2 = this.variables.getVariableValue(j);
        if (f2 != 0.0F)
        {
          String str = ((SolverVariable)localObject2).toString();
          float f1;
          if (i == 0)
          {
            localObject2 = localObject1;
            f1 = f2;
            if (f2 < 0.0F)
            {
              localObject2 = (String)localObject1 + "- ";
              f1 = f2 * -1.0F;
            }
          }
          else if (f2 > 0.0F)
          {
            localObject2 = (String)localObject1 + " + ";
            f1 = f2;
          }
          else
          {
            localObject2 = (String)localObject1 + " - ";
            f1 = f2 * -1.0F;
          }
          if (f1 == 1.0F) {
            localObject1 = (String)localObject2 + str;
          } else {
            localObject1 = (String)localObject2 + f1 + " " + str;
          }
          i = 1;
        }
      }
    }
    localObject2 = localObject1;
    if (i == 0) {
      localObject2 = (String)localObject1 + "0.0";
    }
    return (String)localObject2;
  }
  
  public String toString()
  {
    return toReadableString();
  }
  
  public void updateFromFinalVariable(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable, boolean paramBoolean)
  {
    if ((paramSolverVariable != null) && (paramSolverVariable.isFinalValue))
    {
      float f = this.variables.get(paramSolverVariable);
      this.constantValue += paramSolverVariable.computedValue * f;
      this.variables.remove(paramSolverVariable, paramBoolean);
      if (paramBoolean) {
        paramSolverVariable.removeFromRow(this);
      }
      if ((LinearSystem.SIMPLIFY_SYNONYMS) && (this.variables.getCurrentSize() == 0))
      {
        this.isSimpleDefinition = true;
        paramLinearSystem.hasSimpleDefinition = true;
      }
      return;
    }
  }
  
  public void updateFromRow(LinearSystem paramLinearSystem, ArrayRow paramArrayRow, boolean paramBoolean)
  {
    float f = this.variables.use(paramArrayRow, paramBoolean);
    this.constantValue += paramArrayRow.constantValue * f;
    if (paramBoolean) {
      paramArrayRow.variable.removeFromRow(this);
    }
    if ((LinearSystem.SIMPLIFY_SYNONYMS) && (this.variable != null) && (this.variables.getCurrentSize() == 0))
    {
      this.isSimpleDefinition = true;
      paramLinearSystem.hasSimpleDefinition = true;
    }
  }
  
  public void updateFromSynonymVariable(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable, boolean paramBoolean)
  {
    if ((paramSolverVariable != null) && (paramSolverVariable.isSynonym))
    {
      float f = this.variables.get(paramSolverVariable);
      this.constantValue += paramSolverVariable.synonymDelta * f;
      this.variables.remove(paramSolverVariable, paramBoolean);
      if (paramBoolean) {
        paramSolverVariable.removeFromRow(this);
      }
      this.variables.add(paramLinearSystem.mCache.mIndexedVariables[paramSolverVariable.synonym], f, paramBoolean);
      if ((LinearSystem.SIMPLIFY_SYNONYMS) && (this.variables.getCurrentSize() == 0))
      {
        this.isSimpleDefinition = true;
        paramLinearSystem.hasSimpleDefinition = true;
      }
      return;
    }
  }
  
  public void updateFromSystem(LinearSystem paramLinearSystem)
  {
    if (paramLinearSystem.mRows.length == 0) {
      return;
    }
    int i = 0;
    while (i == 0)
    {
      int k = this.variables.getCurrentSize();
      SolverVariable localSolverVariable;
      for (int j = 0; j < k; j++)
      {
        localSolverVariable = this.variables.getVariable(j);
        if ((localSolverVariable.definitionId != -1) || (localSolverVariable.isFinalValue) || (localSolverVariable.isSynonym)) {
          this.variablesToUpdate.add(localSolverVariable);
        }
      }
      k = this.variablesToUpdate.size();
      if (k > 0)
      {
        for (j = 0; j < k; j++)
        {
          localSolverVariable = (SolverVariable)this.variablesToUpdate.get(j);
          if (localSolverVariable.isFinalValue) {
            updateFromFinalVariable(paramLinearSystem, localSolverVariable, true);
          } else if (localSolverVariable.isSynonym) {
            updateFromSynonymVariable(paramLinearSystem, localSolverVariable, true);
          } else {
            updateFromRow(paramLinearSystem, paramLinearSystem.mRows[localSolverVariable.definitionId], true);
          }
        }
        this.variablesToUpdate.clear();
      }
      else
      {
        i = 1;
      }
    }
    if ((LinearSystem.SIMPLIFY_SYNONYMS) && (this.variable != null) && (this.variables.getCurrentSize() == 0))
    {
      this.isSimpleDefinition = true;
      paramLinearSystem.hasSimpleDefinition = true;
    }
  }
  
  public static abstract interface ArrayRowVariables
  {
    public abstract void add(SolverVariable paramSolverVariable, float paramFloat, boolean paramBoolean);
    
    public abstract void clear();
    
    public abstract boolean contains(SolverVariable paramSolverVariable);
    
    public abstract void display();
    
    public abstract void divideByAmount(float paramFloat);
    
    public abstract float get(SolverVariable paramSolverVariable);
    
    public abstract int getCurrentSize();
    
    public abstract SolverVariable getVariable(int paramInt);
    
    public abstract float getVariableValue(int paramInt);
    
    public abstract int indexOf(SolverVariable paramSolverVariable);
    
    public abstract void invert();
    
    public abstract void put(SolverVariable paramSolverVariable, float paramFloat);
    
    public abstract float remove(SolverVariable paramSolverVariable, boolean paramBoolean);
    
    public abstract int sizeInBytes();
    
    public abstract float use(ArrayRow paramArrayRow, boolean paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/ArrayRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */