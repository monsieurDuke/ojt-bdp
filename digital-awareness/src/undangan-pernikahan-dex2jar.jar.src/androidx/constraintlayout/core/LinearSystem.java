package androidx.constraintlayout.core;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem
{
  public static long ARRAY_ROW_CREATION = 0L;
  public static final boolean DEBUG = false;
  private static final boolean DEBUG_CONSTRAINTS = false;
  public static final boolean FULL_DEBUG = false;
  public static final boolean MEASURE = false;
  public static long OPTIMIZED_ARRAY_ROW_CREATION = 0L;
  public static boolean OPTIMIZED_ENGINE;
  private static int POOL_SIZE;
  public static boolean SIMPLIFY_SYNONYMS;
  public static boolean SKIP_COLUMNS;
  public static boolean USE_BASIC_SYNONYMS;
  public static boolean USE_DEPENDENCY_ORDERING = false;
  public static boolean USE_SYNONYMS;
  public static Metrics sMetrics;
  private int TABLE_SIZE = 32;
  public boolean graphOptimizer = false;
  public boolean hasSimpleDefinition = false;
  private boolean[] mAlreadyTestedCandidates = new boolean[32];
  final Cache mCache;
  private Row mGoal;
  private int mMaxColumns = 32;
  private int mMaxRows = 32;
  int mNumColumns = 1;
  int mNumRows = 0;
  private SolverVariable[] mPoolVariables = new SolverVariable[POOL_SIZE];
  private int mPoolVariablesCount = 0;
  ArrayRow[] mRows = null;
  private Row mTempGoal;
  private HashMap<String, SolverVariable> mVariables = null;
  int mVariablesID = 0;
  public boolean newgraphOptimizer = false;
  
  static
  {
    USE_BASIC_SYNONYMS = true;
    SIMPLIFY_SYNONYMS = true;
    USE_SYNONYMS = true;
    SKIP_COLUMNS = true;
    OPTIMIZED_ENGINE = false;
    POOL_SIZE = 1000;
  }
  
  public LinearSystem()
  {
    releaseRows();
    Cache localCache = new Cache();
    this.mCache = localCache;
    this.mGoal = new PriorityGoalRow(localCache);
    if (OPTIMIZED_ENGINE) {
      this.mTempGoal = new ValuesRow(localCache);
    } else {
      this.mTempGoal = new ArrayRow(localCache);
    }
  }
  
  private SolverVariable acquireSolverVariable(SolverVariable.Type paramType, String paramString)
  {
    SolverVariable localSolverVariable = (SolverVariable)this.mCache.solverVariablePool.acquire();
    if (localSolverVariable == null)
    {
      localSolverVariable = new SolverVariable(paramType, paramString);
      localSolverVariable.setType(paramType, paramString);
      paramType = localSolverVariable;
    }
    else
    {
      localSolverVariable.reset();
      localSolverVariable.setType(paramType, paramString);
      paramType = localSolverVariable;
    }
    int i = this.mPoolVariablesCount;
    int j = POOL_SIZE;
    if (i >= j)
    {
      i = j * 2;
      POOL_SIZE = i;
      this.mPoolVariables = ((SolverVariable[])Arrays.copyOf(this.mPoolVariables, i));
    }
    paramString = this.mPoolVariables;
    i = this.mPoolVariablesCount;
    this.mPoolVariablesCount = (i + 1);
    paramString[i] = paramType;
    return paramType;
  }
  
  private void addError(ArrayRow paramArrayRow)
  {
    paramArrayRow.addError(this, 0);
  }
  
  private final void addRow(ArrayRow paramArrayRow)
  {
    if ((SIMPLIFY_SYNONYMS) && (paramArrayRow.isSimpleDefinition))
    {
      paramArrayRow.variable.setFinalValue(this, paramArrayRow.constantValue);
    }
    else
    {
      this.mRows[this.mNumRows] = paramArrayRow;
      paramArrayRow.variable.definitionId = this.mNumRows;
      this.mNumRows += 1;
      paramArrayRow.variable.updateReferencesWithNewDefinition(this, paramArrayRow);
    }
    if ((SIMPLIFY_SYNONYMS) && (this.hasSimpleDefinition))
    {
      int j;
      for (int i = 0; i < this.mNumRows; i = j + 1)
      {
        if (this.mRows[i] == null) {
          System.out.println("WTF");
        }
        paramArrayRow = this.mRows[i];
        j = i;
        if (paramArrayRow != null)
        {
          j = i;
          if (paramArrayRow.isSimpleDefinition)
          {
            paramArrayRow = this.mRows[i];
            paramArrayRow.variable.setFinalValue(this, paramArrayRow.constantValue);
            if (OPTIMIZED_ENGINE) {
              this.mCache.optimizedArrayRowPool.release(paramArrayRow);
            } else {
              this.mCache.arrayRowPool.release(paramArrayRow);
            }
            this.mRows[i] = null;
            int k = i + 1;
            int m;
            for (j = i + 1;; j++)
            {
              m = this.mNumRows;
              if (j >= m) {
                break;
              }
              paramArrayRow = this.mRows;
              paramArrayRow[(j - 1)] = paramArrayRow[j];
              if (paramArrayRow[(j - 1)].variable.definitionId == j) {
                this.mRows[(j - 1)].variable.definitionId = (j - 1);
              }
              k = j;
            }
            if (k < m) {
              this.mRows[k] = null;
            }
            this.mNumRows = (m - 1);
            j = i - 1;
          }
        }
      }
      this.hasSimpleDefinition = false;
    }
  }
  
  private void addSingleError(ArrayRow paramArrayRow, int paramInt)
  {
    addSingleError(paramArrayRow, paramInt, 0);
  }
  
  private void computeValues()
  {
    for (int i = 0; i < this.mNumRows; i++)
    {
      ArrayRow localArrayRow = this.mRows[i];
      localArrayRow.variable.computedValue = localArrayRow.constantValue;
    }
  }
  
  public static ArrayRow createRowDimensionPercent(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, float paramFloat)
  {
    return paramLinearSystem.createRow().createRowDimensionPercent(paramSolverVariable1, paramSolverVariable2, paramFloat);
  }
  
  private SolverVariable createVariable(String paramString, SolverVariable.Type paramType)
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null) {
      localMetrics.variables += 1L;
    }
    if (this.mNumColumns + 1 >= this.mMaxColumns) {
      increaseTableSize();
    }
    paramType = acquireSolverVariable(paramType, null);
    paramType.setName(paramString);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns += 1;
    paramType.id = i;
    if (this.mVariables == null) {
      this.mVariables = new HashMap();
    }
    this.mVariables.put(paramString, paramType);
    this.mCache.mIndexedVariables[this.mVariablesID] = paramType;
    return paramType;
  }
  
  private void displayRows()
  {
    displaySolverVariables();
    String str = "";
    for (int i = 0; i < this.mNumRows; i++)
    {
      str = str + this.mRows[i];
      str = str + "\n";
    }
    str = str + this.mGoal + "\n";
    System.out.println(str);
  }
  
  private void displaySolverVariables()
  {
    String str = "Display Rows (" + this.mNumRows + "x" + this.mNumColumns + ")\n";
    System.out.println(str);
  }
  
  private int enforceBFS(Row paramRow)
    throws Exception
  {
    int k = 0;
    int m = 0;
    int i;
    for (int j = 0;; j++)
    {
      i = m;
      if (j >= this.mNumRows) {
        break;
      }
      if ((this.mRows[j].variable.mType != SolverVariable.Type.UNRESTRICTED) && (this.mRows[j].constantValue < 0.0F))
      {
        i = 1;
        break;
      }
    }
    if (i != 0)
    {
      int i1 = 0;
      int i8;
      for (j = 0; i1 == 0; j = i8)
      {
        paramRow = sMetrics;
        if (paramRow != null) {
          paramRow.bfs += 1L;
        }
        i8 = j + 1;
        float f1 = Float.MAX_VALUE;
        k = 0;
        m = -1;
        int n = -1;
        j = 0;
        Object localObject;
        while (j < this.mNumRows)
        {
          paramRow = this.mRows[j];
          float f2;
          int i3;
          int i4;
          int i5;
          if (paramRow.variable.mType == SolverVariable.Type.UNRESTRICTED)
          {
            f2 = f1;
            i3 = k;
            i4 = m;
            i5 = n;
          }
          else if (paramRow.isSimpleDefinition)
          {
            f2 = f1;
            i3 = k;
            i4 = m;
            i5 = n;
          }
          else if (paramRow.constantValue < 0.0F)
          {
            float f3;
            int i6;
            int i2;
            if (SKIP_COLUMNS)
            {
              int i9 = paramRow.variables.getCurrentSize();
              i4 = 0;
              while (i4 < i9)
              {
                localObject = paramRow.variables.getVariable(i4);
                f3 = paramRow.variables.get((SolverVariable)localObject);
                int i7;
                if (f3 <= 0.0F)
                {
                  f2 = f1;
                  i6 = k;
                  i5 = m;
                  i7 = n;
                  i3 = i;
                }
                else
                {
                  i3 = 0;
                  i2 = k;
                  k = i3;
                  for (;;)
                  {
                    f2 = f1;
                    i6 = i2;
                    i5 = m;
                    i7 = n;
                    i3 = i;
                    if (k >= 9) {
                      break;
                    }
                    f2 = localObject.strengthVector[k] / f3;
                    if ((f2 >= f1) || (k != i2))
                    {
                      i3 = i2;
                      if (k <= i2) {}
                    }
                    else
                    {
                      f1 = f2;
                      m = j;
                      n = ((SolverVariable)localObject).id;
                      i3 = k;
                    }
                    k++;
                    i2 = i3;
                  }
                }
                i4++;
                i = i3;
                f1 = f2;
                k = i6;
                m = i5;
                n = i7;
              }
              f2 = f1;
              i3 = k;
              i4 = m;
              i5 = n;
            }
            else
            {
              i6 = i;
              i2 = 1;
              for (;;)
              {
                f2 = f1;
                i3 = k;
                i4 = m;
                i5 = n;
                i = i6;
                if (i2 >= this.mNumColumns) {
                  break;
                }
                localObject = this.mCache.mIndexedVariables[i2];
                f3 = paramRow.variables.get((SolverVariable)localObject);
                if (f3 <= 0.0F)
                {
                  f2 = f1;
                  i4 = k;
                  i3 = m;
                  i5 = n;
                }
                else
                {
                  i = 0;
                  for (;;)
                  {
                    f2 = f1;
                    i4 = k;
                    i3 = m;
                    i5 = n;
                    if (i >= 9) {
                      break;
                    }
                    f2 = localObject.strengthVector[i] / f3;
                    if ((f2 >= f1) || (i != k))
                    {
                      i3 = k;
                      if (i <= k) {}
                    }
                    else
                    {
                      f1 = f2;
                      m = j;
                      n = i2;
                      i3 = i;
                    }
                    i++;
                    k = i3;
                  }
                }
                i2++;
                f1 = f2;
                k = i4;
                m = i3;
                n = i5;
              }
            }
          }
          else
          {
            i5 = n;
            i4 = m;
            i3 = k;
            f2 = f1;
          }
          j++;
          f1 = f2;
          k = i3;
          m = i4;
          n = i5;
        }
        if (m != -1)
        {
          paramRow = this.mRows[m];
          paramRow.variable.definitionId = -1;
          localObject = sMetrics;
          if (localObject != null) {
            ((Metrics)localObject).pivots += 1L;
          }
          paramRow.pivot(this.mCache.mIndexedVariables[n]);
          paramRow.variable.definitionId = m;
          paramRow.variable.updateReferencesWithNewDefinition(this, paramRow);
        }
        else
        {
          i1 = 1;
        }
        if (i8 > this.mNumColumns / 2) {
          i1 = 1;
        }
      }
    }
    else
    {
      j = k;
    }
    return j;
  }
  
  private String getDisplaySize(int paramInt)
  {
    int i = paramInt * 4 / 1024 / 1024;
    if (i > 0) {
      return "" + i + " Mb";
    }
    i = paramInt * 4 / 1024;
    if (i > 0) {
      return "" + i + " Kb";
    }
    return "" + paramInt * 4 + " bytes";
  }
  
  private String getDisplayStrength(int paramInt)
  {
    if (paramInt == 1) {
      return "LOW";
    }
    if (paramInt == 2) {
      return "MEDIUM";
    }
    if (paramInt == 3) {
      return "HIGH";
    }
    if (paramInt == 4) {
      return "HIGHEST";
    }
    if (paramInt == 5) {
      return "EQUALITY";
    }
    if (paramInt == 8) {
      return "FIXED";
    }
    if (paramInt == 6) {
      return "BARRIER";
    }
    return "NONE";
  }
  
  public static Metrics getMetrics()
  {
    return sMetrics;
  }
  
  private void increaseTableSize()
  {
    int i = this.TABLE_SIZE * 2;
    this.TABLE_SIZE = i;
    this.mRows = ((ArrayRow[])Arrays.copyOf(this.mRows, i));
    Object localObject = this.mCache;
    ((Cache)localObject).mIndexedVariables = ((SolverVariable[])Arrays.copyOf(((Cache)localObject).mIndexedVariables, this.TABLE_SIZE));
    i = this.TABLE_SIZE;
    this.mAlreadyTestedCandidates = new boolean[i];
    this.mMaxColumns = i;
    this.mMaxRows = i;
    localObject = sMetrics;
    if (localObject != null)
    {
      ((Metrics)localObject).tableSizeIncrease += 1L;
      localObject = sMetrics;
      ((Metrics)localObject).maxTableSize = Math.max(((Metrics)localObject).maxTableSize, this.TABLE_SIZE);
      localObject = sMetrics;
      ((Metrics)localObject).lastTableSize = ((Metrics)localObject).maxTableSize;
    }
  }
  
  private final int optimize(Row paramRow, boolean paramBoolean)
  {
    Object localObject1 = sMetrics;
    if (localObject1 != null) {
      ((Metrics)localObject1).optimize += 1L;
    }
    int n = 0;
    int m = 0;
    int j;
    int k;
    for (int i = 0;; i++)
    {
      j = n;
      k = m;
      if (i >= this.mNumColumns) {
        break;
      }
      this.mAlreadyTestedCandidates[i] = false;
    }
    while (j == 0)
    {
      localObject1 = sMetrics;
      if (localObject1 != null) {
        ((Metrics)localObject1).iterations += 1L;
      }
      n = k + 1;
      if (n >= this.mNumColumns * 2) {
        return n;
      }
      if (paramRow.getKey() != null) {
        this.mAlreadyTestedCandidates[paramRow.getKey().id] = true;
      }
      localObject1 = paramRow.getPivotCandidate(this, this.mAlreadyTestedCandidates);
      if (localObject1 != null)
      {
        if (this.mAlreadyTestedCandidates[localObject1.id] != 0) {
          return n;
        }
        this.mAlreadyTestedCandidates[localObject1.id] = true;
      }
      if (localObject1 != null)
      {
        float f1 = Float.MAX_VALUE;
        m = -1;
        i = 0;
        Object localObject2;
        while (i < this.mNumRows)
        {
          localObject2 = this.mRows[i];
          float f2;
          if (((ArrayRow)localObject2).variable.mType == SolverVariable.Type.UNRESTRICTED)
          {
            f2 = f1;
            k = m;
          }
          else if (((ArrayRow)localObject2).isSimpleDefinition)
          {
            f2 = f1;
            k = m;
          }
          else
          {
            f2 = f1;
            k = m;
            if (((ArrayRow)localObject2).hasVariable((SolverVariable)localObject1))
            {
              float f3 = ((ArrayRow)localObject2).variables.get((SolverVariable)localObject1);
              f2 = f1;
              k = m;
              if (f3 < 0.0F)
              {
                f3 = -((ArrayRow)localObject2).constantValue / f3;
                f2 = f1;
                k = m;
                if (f3 < f1)
                {
                  f2 = f3;
                  k = i;
                }
              }
            }
          }
          i++;
          f1 = f2;
          m = k;
        }
        if (m > -1)
        {
          ArrayRow localArrayRow = this.mRows[m];
          localArrayRow.variable.definitionId = -1;
          localObject2 = sMetrics;
          if (localObject2 != null) {
            ((Metrics)localObject2).pivots += 1L;
          }
          localArrayRow.pivot((SolverVariable)localObject1);
          localArrayRow.variable.definitionId = m;
          localArrayRow.variable.updateReferencesWithNewDefinition(this, localArrayRow);
        }
      }
      else
      {
        j = 1;
      }
      k = n;
    }
    return k;
  }
  
  private void releaseRows()
  {
    int i;
    ArrayRow localArrayRow;
    if (OPTIMIZED_ENGINE) {
      for (i = 0; i < this.mNumRows; i++)
      {
        localArrayRow = this.mRows[i];
        if (localArrayRow != null) {
          this.mCache.optimizedArrayRowPool.release(localArrayRow);
        }
        this.mRows[i] = null;
      }
    } else {
      for (i = 0; i < this.mNumRows; i++)
      {
        localArrayRow = this.mRows[i];
        if (localArrayRow != null) {
          this.mCache.arrayRowPool.release(localArrayRow);
        }
        this.mRows[i] = null;
      }
    }
  }
  
  public void addCenterPoint(ConstraintWidget paramConstraintWidget1, ConstraintWidget paramConstraintWidget2, float paramFloat, int paramInt)
  {
    SolverVariable localSolverVariable2 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.LEFT));
    SolverVariable localSolverVariable4 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.TOP));
    SolverVariable localSolverVariable1 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT));
    SolverVariable localSolverVariable6 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.BOTTOM));
    SolverVariable localSolverVariable3 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT));
    SolverVariable localSolverVariable5 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.TOP));
    paramConstraintWidget1 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT));
    SolverVariable localSolverVariable7 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM));
    paramConstraintWidget2 = createRow();
    paramConstraintWidget2.createRowWithAngle(localSolverVariable4, localSolverVariable6, localSolverVariable5, localSolverVariable7, (float)(Math.sin(paramFloat) * paramInt));
    addConstraint(paramConstraintWidget2);
    paramConstraintWidget2 = createRow();
    paramConstraintWidget2.createRowWithAngle(localSolverVariable2, localSolverVariable1, localSolverVariable3, paramConstraintWidget1, (float)(Math.cos(paramFloat) * paramInt));
    addConstraint(paramConstraintWidget2);
  }
  
  public void addCentering(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2, int paramInt3)
  {
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowCentering(paramSolverVariable1, paramSolverVariable2, paramInt1, paramFloat, paramSolverVariable3, paramSolverVariable4, paramInt2);
    if (paramInt3 != 8) {
      localArrayRow.addError(this, paramInt3);
    }
    addConstraint(localArrayRow);
  }
  
  public void addConstraint(ArrayRow paramArrayRow)
  {
    if (paramArrayRow == null) {
      return;
    }
    Object localObject = sMetrics;
    if (localObject != null)
    {
      ((Metrics)localObject).constraints += 1L;
      if (paramArrayRow.isSimpleDefinition)
      {
        localObject = sMetrics;
        ((Metrics)localObject).simpleconstraints += 1L;
      }
    }
    if ((this.mNumRows + 1 >= this.mMaxRows) || (this.mNumColumns + 1 >= this.mMaxColumns)) {
      increaseTableSize();
    }
    int i = 0;
    int j = 0;
    if (!paramArrayRow.isSimpleDefinition)
    {
      paramArrayRow.updateFromSystem(this);
      if (paramArrayRow.isEmpty()) {
        return;
      }
      paramArrayRow.ensurePositiveConstant();
      i = j;
      if (paramArrayRow.chooseSubject(this))
      {
        localObject = createExtraVariable();
        paramArrayRow.variable = ((SolverVariable)localObject);
        int k = this.mNumRows;
        addRow(paramArrayRow);
        i = j;
        if (this.mNumRows == k + 1)
        {
          j = 1;
          this.mTempGoal.initFromRow(paramArrayRow);
          optimize(this.mTempGoal, true);
          i = j;
          if (((SolverVariable)localObject).definitionId == -1)
          {
            if (paramArrayRow.variable == localObject)
            {
              SolverVariable localSolverVariable = paramArrayRow.pickPivot((SolverVariable)localObject);
              if (localSolverVariable != null)
              {
                localObject = sMetrics;
                if (localObject != null) {
                  ((Metrics)localObject).pivots += 1L;
                }
                paramArrayRow.pivot(localSolverVariable);
              }
            }
            if (!paramArrayRow.isSimpleDefinition) {
              paramArrayRow.variable.updateReferencesWithNewDefinition(this, paramArrayRow);
            }
            if (OPTIMIZED_ENGINE) {
              this.mCache.optimizedArrayRowPool.release(paramArrayRow);
            } else {
              this.mCache.arrayRowPool.release(paramArrayRow);
            }
            this.mNumRows -= 1;
            i = j;
          }
        }
      }
      if (!paramArrayRow.hasKeyVariable()) {
        return;
      }
    }
    if (i == 0) {
      addRow(paramArrayRow);
    }
  }
  
  public ArrayRow addEquality(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    if ((USE_BASIC_SYNONYMS) && (paramInt2 == 8) && (paramSolverVariable2.isFinalValue) && (paramSolverVariable1.definitionId == -1))
    {
      paramSolverVariable1.setFinalValue(this, paramSolverVariable2.computedValue + paramInt1);
      return null;
    }
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowEquals(paramSolverVariable1, paramSolverVariable2, paramInt1);
    if (paramInt2 != 8) {
      localArrayRow.addError(this, paramInt2);
    }
    addConstraint(localArrayRow);
    return localArrayRow;
  }
  
  public void addEquality(SolverVariable paramSolverVariable, int paramInt)
  {
    Object localObject;
    if ((USE_BASIC_SYNONYMS) && (paramSolverVariable.definitionId == -1))
    {
      paramSolverVariable.setFinalValue(this, paramInt);
      for (i = 0; i < this.mVariablesID + 1; i++)
      {
        localObject = this.mCache.mIndexedVariables[i];
        if ((localObject != null) && (((SolverVariable)localObject).isSynonym) && (((SolverVariable)localObject).synonym == paramSolverVariable.id)) {
          ((SolverVariable)localObject).setFinalValue(this, paramInt + ((SolverVariable)localObject).synonymDelta);
        }
      }
      return;
    }
    int i = paramSolverVariable.definitionId;
    if (paramSolverVariable.definitionId != -1)
    {
      localObject = this.mRows[i];
      if (((ArrayRow)localObject).isSimpleDefinition)
      {
        ((ArrayRow)localObject).constantValue = paramInt;
      }
      else if (((ArrayRow)localObject).variables.getCurrentSize() == 0)
      {
        ((ArrayRow)localObject).isSimpleDefinition = true;
        ((ArrayRow)localObject).constantValue = paramInt;
      }
      else
      {
        localObject = createRow();
        ((ArrayRow)localObject).createRowEquals(paramSolverVariable, paramInt);
        addConstraint((ArrayRow)localObject);
      }
    }
    else
    {
      localObject = createRow();
      ((ArrayRow)localObject).createRowDefinition(paramSolverVariable, paramInt);
      addConstraint((ArrayRow)localObject);
    }
  }
  
  public void addGreaterBarrier(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    localSolverVariable.strength = 0;
    localArrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt);
    addConstraint(localArrayRow);
  }
  
  public void addGreaterThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    localSolverVariable.strength = 0;
    localArrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt1);
    if (paramInt2 != 8) {
      addSingleError(localArrayRow, (int)(-1.0F * localArrayRow.variables.get(localSolverVariable)), paramInt2);
    }
    addConstraint(localArrayRow);
  }
  
  public void addLowerBarrier(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    localSolverVariable.strength = 0;
    localArrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt);
    addConstraint(localArrayRow);
  }
  
  public void addLowerThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    localSolverVariable.strength = 0;
    localArrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt1);
    if (paramInt2 != 8) {
      addSingleError(localArrayRow, (int)(-1.0F * localArrayRow.variables.get(localSolverVariable)), paramInt2);
    }
    addConstraint(localArrayRow);
  }
  
  public void addRatio(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat, int paramInt)
  {
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowDimensionRatio(paramSolverVariable1, paramSolverVariable2, paramSolverVariable3, paramSolverVariable4, paramFloat);
    if (paramInt != 8) {
      localArrayRow.addError(this, paramInt);
    }
    addConstraint(localArrayRow);
  }
  
  void addSingleError(ArrayRow paramArrayRow, int paramInt1, int paramInt2)
  {
    paramArrayRow.addSingleError(createErrorVariable(paramInt2, null), paramInt1);
  }
  
  public void addSynonym(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt)
  {
    if ((paramSolverVariable1.definitionId == -1) && (paramInt == 0))
    {
      SolverVariable localSolverVariable = paramSolverVariable2;
      int i = paramInt;
      if (paramSolverVariable2.isSynonym)
      {
        i = (int)(paramInt + paramSolverVariable2.synonymDelta);
        localSolverVariable = this.mCache.mIndexedVariables[paramSolverVariable2.synonym];
      }
      if (paramSolverVariable1.isSynonym)
      {
        paramInt = (int)(i - paramSolverVariable1.synonymDelta);
        paramSolverVariable1 = this.mCache.mIndexedVariables[paramSolverVariable1.synonym];
      }
      else
      {
        paramSolverVariable1.setSynonym(this, localSolverVariable, 0.0F);
      }
    }
    else
    {
      addEquality(paramSolverVariable1, paramSolverVariable2, paramInt, 8);
    }
  }
  
  final void cleanupRows()
  {
    int j;
    for (int i = 0; i < this.mNumRows; i = j + 1)
    {
      ArrayRow localArrayRow = this.mRows[i];
      if (localArrayRow.variables.getCurrentSize() == 0) {
        localArrayRow.isSimpleDefinition = true;
      }
      j = i;
      if (localArrayRow.isSimpleDefinition)
      {
        localArrayRow.variable.computedValue = localArrayRow.constantValue;
        localArrayRow.variable.removeFromRow(localArrayRow);
        int k;
        for (j = i;; j++)
        {
          k = this.mNumRows;
          if (j >= k - 1) {
            break;
          }
          ArrayRow[] arrayOfArrayRow = this.mRows;
          arrayOfArrayRow[j] = arrayOfArrayRow[(j + 1)];
        }
        this.mRows[(k - 1)] = null;
        this.mNumRows = (k - 1);
        j = i - 1;
        if (OPTIMIZED_ENGINE) {
          this.mCache.optimizedArrayRowPool.release(localArrayRow);
        } else {
          this.mCache.arrayRowPool.release(localArrayRow);
        }
      }
    }
  }
  
  public SolverVariable createErrorVariable(int paramInt, String paramString)
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null) {
      localMetrics.errors += 1L;
    }
    if (this.mNumColumns + 1 >= this.mMaxColumns) {
      increaseTableSize();
    }
    paramString = acquireSolverVariable(SolverVariable.Type.ERROR, paramString);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns += 1;
    paramString.id = i;
    paramString.strength = paramInt;
    this.mCache.mIndexedVariables[this.mVariablesID] = paramString;
    this.mGoal.addError(paramString);
    return paramString;
  }
  
  public SolverVariable createExtraVariable()
  {
    Object localObject = sMetrics;
    if (localObject != null) {
      ((Metrics)localObject).extravariables += 1L;
    }
    if (this.mNumColumns + 1 >= this.mMaxColumns) {
      increaseTableSize();
    }
    localObject = acquireSolverVariable(SolverVariable.Type.SLACK, null);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns += 1;
    ((SolverVariable)localObject).id = i;
    this.mCache.mIndexedVariables[this.mVariablesID] = localObject;
    return (SolverVariable)localObject;
  }
  
  public SolverVariable createObjectVariable(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if (this.mNumColumns + 1 >= this.mMaxColumns) {
      increaseTableSize();
    }
    Object localObject2 = null;
    if ((paramObject instanceof ConstraintAnchor))
    {
      localObject2 = ((ConstraintAnchor)paramObject).getSolverVariable();
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        ((ConstraintAnchor)paramObject).resetSolverVariable(this.mCache);
        localObject1 = ((ConstraintAnchor)paramObject).getSolverVariable();
      }
      if ((((SolverVariable)localObject1).id != -1) && (((SolverVariable)localObject1).id <= this.mVariablesID))
      {
        localObject2 = localObject1;
        if (this.mCache.mIndexedVariables[localObject1.id] != null) {}
      }
      else
      {
        if (((SolverVariable)localObject1).id != -1) {
          ((SolverVariable)localObject1).reset();
        }
        int i = this.mVariablesID + 1;
        this.mVariablesID = i;
        this.mNumColumns += 1;
        ((SolverVariable)localObject1).id = i;
        ((SolverVariable)localObject1).mType = SolverVariable.Type.UNRESTRICTED;
        this.mCache.mIndexedVariables[this.mVariablesID] = localObject1;
        localObject2 = localObject1;
      }
    }
    return (SolverVariable)localObject2;
  }
  
  public ArrayRow createRow()
  {
    Object localObject;
    if (OPTIMIZED_ENGINE)
    {
      localObject = (ArrayRow)this.mCache.optimizedArrayRowPool.acquire();
      if (localObject == null)
      {
        localObject = new ValuesRow(this.mCache);
        OPTIMIZED_ARRAY_ROW_CREATION += 1L;
      }
      else
      {
        ((ArrayRow)localObject).reset();
      }
    }
    else
    {
      localObject = (ArrayRow)this.mCache.arrayRowPool.acquire();
      if (localObject == null)
      {
        localObject = new ArrayRow(this.mCache);
        ARRAY_ROW_CREATION += 1L;
      }
      else
      {
        ((ArrayRow)localObject).reset();
      }
    }
    SolverVariable.increaseErrorId();
    return (ArrayRow)localObject;
  }
  
  public SolverVariable createSlackVariable()
  {
    Object localObject = sMetrics;
    if (localObject != null) {
      ((Metrics)localObject).slackvariables += 1L;
    }
    if (this.mNumColumns + 1 >= this.mMaxColumns) {
      increaseTableSize();
    }
    localObject = acquireSolverVariable(SolverVariable.Type.SLACK, null);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns += 1;
    ((SolverVariable)localObject).id = i;
    this.mCache.mIndexedVariables[this.mVariablesID] = localObject;
    return (SolverVariable)localObject;
  }
  
  public void displayReadableRows()
  {
    displaySolverVariables();
    Object localObject1 = " num vars " + this.mVariablesID + "\n";
    int i = 0;
    SolverVariable localSolverVariable;
    while (i < this.mVariablesID + 1)
    {
      localSolverVariable = this.mCache.mIndexedVariables[i];
      localObject2 = localObject1;
      if (localSolverVariable != null)
      {
        localObject2 = localObject1;
        if (localSolverVariable.isFinalValue) {
          localObject2 = (String)localObject1 + " $[" + i + "] => " + localSolverVariable + " = " + localSolverVariable.computedValue + "\n";
        }
      }
      i++;
      localObject1 = localObject2;
    }
    localObject1 = (String)localObject1 + "\n";
    i = 0;
    while (i < this.mVariablesID + 1)
    {
      localSolverVariable = this.mCache.mIndexedVariables[i];
      localObject2 = localObject1;
      if (localSolverVariable != null)
      {
        localObject2 = localObject1;
        if (localSolverVariable.isSynonym)
        {
          localObject2 = this.mCache.mIndexedVariables[localSolverVariable.synonym];
          localObject2 = (String)localObject1 + " ~[" + i + "] => " + localSolverVariable + " = " + localObject2 + " + " + localSolverVariable.synonymDelta + "\n";
        }
      }
      i++;
      localObject1 = localObject2;
    }
    localObject1 = (String)localObject1 + "\n\n #  ";
    for (i = 0; i < this.mNumRows; i++)
    {
      localObject1 = (String)localObject1 + this.mRows[i].toReadableString();
      localObject1 = (String)localObject1 + "\n #  ";
    }
    Object localObject2 = localObject1;
    if (this.mGoal != null) {
      localObject2 = (String)localObject1 + "Goal: " + this.mGoal + "\n";
    }
    System.out.println((String)localObject2);
  }
  
  void displaySystemInformation()
  {
    int i = 0;
    int j = 0;
    while (j < this.TABLE_SIZE)
    {
      localObject = this.mRows[j];
      k = i;
      if (localObject != null) {
        k = i + ((ArrayRow)localObject).sizeInBytes();
      }
      j++;
      i = k;
    }
    j = 0;
    int k = 0;
    while (k < this.mNumRows)
    {
      localObject = this.mRows[k];
      int m = j;
      if (localObject != null) {
        m = j + ((ArrayRow)localObject).sizeInBytes();
      }
      k++;
      j = m;
    }
    PrintStream localPrintStream = System.out;
    Object localObject = new StringBuilder().append("Linear System -> Table size: ").append(this.TABLE_SIZE).append(" (");
    k = this.TABLE_SIZE;
    localPrintStream.println(getDisplaySize(k * k) + ") -- row sizes: " + getDisplaySize(i) + ", actual size: " + getDisplaySize(j) + " rows: " + this.mNumRows + "/" + this.mMaxRows + " cols: " + this.mNumColumns + "/" + this.mMaxColumns + " " + 0 + " occupied cells, " + getDisplaySize(0));
  }
  
  public void displayVariablesReadableRows()
  {
    displaySolverVariables();
    Object localObject2 = "";
    int i = 0;
    while (i < this.mNumRows)
    {
      localObject1 = localObject2;
      if (this.mRows[i].variable.mType == SolverVariable.Type.UNRESTRICTED)
      {
        localObject1 = (String)localObject2 + this.mRows[i].toReadableString();
        localObject1 = (String)localObject1 + "\n";
      }
      i++;
      localObject2 = localObject1;
    }
    Object localObject1 = (String)localObject2 + this.mGoal + "\n";
    System.out.println((String)localObject1);
  }
  
  public void fillMetrics(Metrics paramMetrics)
  {
    sMetrics = paramMetrics;
  }
  
  public Cache getCache()
  {
    return this.mCache;
  }
  
  Row getGoal()
  {
    return this.mGoal;
  }
  
  public int getMemoryUsed()
  {
    int k = 0;
    int i = 0;
    while (i < this.mNumRows)
    {
      ArrayRow localArrayRow = this.mRows[i];
      int j = k;
      if (localArrayRow != null) {
        j = k + localArrayRow.sizeInBytes();
      }
      i++;
      k = j;
    }
    return k;
  }
  
  public int getNumEquations()
  {
    return this.mNumRows;
  }
  
  public int getNumVariables()
  {
    return this.mVariablesID;
  }
  
  public int getObjectVariableValue(Object paramObject)
  {
    paramObject = ((ConstraintAnchor)paramObject).getSolverVariable();
    if (paramObject != null) {
      return (int)(((SolverVariable)paramObject).computedValue + 0.5F);
    }
    return 0;
  }
  
  ArrayRow getRow(int paramInt)
  {
    return this.mRows[paramInt];
  }
  
  float getValueFor(String paramString)
  {
    paramString = getVariable(paramString, SolverVariable.Type.UNRESTRICTED);
    if (paramString == null) {
      return 0.0F;
    }
    return paramString.computedValue;
  }
  
  SolverVariable getVariable(String paramString, SolverVariable.Type paramType)
  {
    if (this.mVariables == null) {
      this.mVariables = new HashMap();
    }
    SolverVariable localSolverVariable2 = (SolverVariable)this.mVariables.get(paramString);
    SolverVariable localSolverVariable1 = localSolverVariable2;
    if (localSolverVariable2 == null) {
      localSolverVariable1 = createVariable(paramString, paramType);
    }
    return localSolverVariable1;
  }
  
  public void minimize()
    throws Exception
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null) {
      localMetrics.minimize += 1L;
    }
    if (this.mGoal.isEmpty())
    {
      computeValues();
      return;
    }
    if ((!this.graphOptimizer) && (!this.newgraphOptimizer))
    {
      minimizeGoal(this.mGoal);
    }
    else
    {
      localMetrics = sMetrics;
      if (localMetrics != null) {
        localMetrics.graphOptimizer += 1L;
      }
      int k = 1;
      int i;
      for (int j = 0;; j++)
      {
        i = k;
        if (j >= this.mNumRows) {
          break;
        }
        if (!this.mRows[j].isSimpleDefinition)
        {
          i = 0;
          break;
        }
      }
      if (i == 0)
      {
        minimizeGoal(this.mGoal);
      }
      else
      {
        localMetrics = sMetrics;
        if (localMetrics != null) {
          localMetrics.fullySolved += 1L;
        }
        computeValues();
      }
    }
  }
  
  void minimizeGoal(Row paramRow)
    throws Exception
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null)
    {
      localMetrics.minimizeGoal += 1L;
      localMetrics = sMetrics;
      localMetrics.maxVariables = Math.max(localMetrics.maxVariables, this.mNumColumns);
      localMetrics = sMetrics;
      localMetrics.maxRows = Math.max(localMetrics.maxRows, this.mNumRows);
    }
    enforceBFS(paramRow);
    optimize(paramRow, false);
    computeValues();
  }
  
  public void removeRow(ArrayRow paramArrayRow)
  {
    if ((paramArrayRow.isSimpleDefinition) && (paramArrayRow.variable != null))
    {
      if (paramArrayRow.variable.definitionId != -1)
      {
        int j;
        for (int i = paramArrayRow.variable.definitionId;; i++)
        {
          j = this.mNumRows;
          if (i >= j - 1) {
            break;
          }
          Object localObject = this.mRows[(i + 1)].variable;
          if (((SolverVariable)localObject).definitionId == i + 1) {
            ((SolverVariable)localObject).definitionId = i;
          }
          localObject = this.mRows;
          localObject[i] = localObject[(i + 1)];
        }
        this.mNumRows = (j - 1);
      }
      if (!paramArrayRow.variable.isFinalValue) {
        paramArrayRow.variable.setFinalValue(this, paramArrayRow.constantValue);
      }
      if (OPTIMIZED_ENGINE) {
        this.mCache.optimizedArrayRowPool.release(paramArrayRow);
      } else {
        this.mCache.arrayRowPool.release(paramArrayRow);
      }
    }
  }
  
  public void reset()
  {
    for (int i = 0; i < this.mCache.mIndexedVariables.length; i++)
    {
      localObject = this.mCache.mIndexedVariables[i];
      if (localObject != null) {
        ((SolverVariable)localObject).reset();
      }
    }
    this.mCache.solverVariablePool.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
    this.mPoolVariablesCount = 0;
    Arrays.fill(this.mCache.mIndexedVariables, null);
    Object localObject = this.mVariables;
    if (localObject != null) {
      ((HashMap)localObject).clear();
    }
    this.mVariablesID = 0;
    this.mGoal.clear();
    this.mNumColumns = 1;
    for (i = 0; i < this.mNumRows; i++)
    {
      localObject = this.mRows[i];
      if (localObject != null) {
        ((ArrayRow)localObject).used = false;
      }
    }
    releaseRows();
    this.mNumRows = 0;
    if (OPTIMIZED_ENGINE) {
      this.mTempGoal = new ValuesRow(this.mCache);
    } else {
      this.mTempGoal = new ArrayRow(this.mCache);
    }
  }
  
  static abstract interface Row
  {
    public abstract void addError(SolverVariable paramSolverVariable);
    
    public abstract void clear();
    
    public abstract SolverVariable getKey();
    
    public abstract SolverVariable getPivotCandidate(LinearSystem paramLinearSystem, boolean[] paramArrayOfBoolean);
    
    public abstract void initFromRow(Row paramRow);
    
    public abstract boolean isEmpty();
    
    public abstract void updateFromFinalVariable(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable, boolean paramBoolean);
    
    public abstract void updateFromRow(LinearSystem paramLinearSystem, ArrayRow paramArrayRow, boolean paramBoolean);
    
    public abstract void updateFromSystem(LinearSystem paramLinearSystem);
  }
  
  class ValuesRow
    extends ArrayRow
  {
    public ValuesRow(Cache paramCache)
    {
      this.variables = new SolverVariableValues(this, paramCache);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/LinearSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */