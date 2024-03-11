package androidx.constraintlayout.core;

import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables
  implements ArrayRow.ArrayRowVariables
{
  private static final boolean DEBUG = false;
  private static final boolean FULL_NEW_CHECK = false;
  static final int NONE = -1;
  private static float epsilon = 0.001F;
  private int ROW_SIZE = 8;
  private SolverVariable candidate = null;
  int currentSize = 0;
  private int[] mArrayIndices = new int[8];
  private int[] mArrayNextIndices = new int[8];
  private float[] mArrayValues = new float[8];
  protected final Cache mCache;
  private boolean mDidFillOnce = false;
  private int mHead = -1;
  private int mLast = -1;
  private final ArrayRow mRow;
  
  ArrayLinkedVariables(ArrayRow paramArrayRow, Cache paramCache)
  {
    this.mRow = paramArrayRow;
    this.mCache = paramCache;
  }
  
  public void add(SolverVariable paramSolverVariable, float paramFloat, boolean paramBoolean)
  {
    float f1 = epsilon;
    if ((paramFloat > -f1) && (paramFloat < f1)) {
      return;
    }
    if (this.mHead == -1)
    {
      this.mHead = 0;
      this.mArrayValues[0] = paramFloat;
      this.mArrayIndices[0] = paramSolverVariable.id;
      this.mArrayNextIndices[this.mHead] = -1;
      paramSolverVariable.usageInRowCount += 1;
      paramSolverVariable.addToRow(this.mRow);
      this.currentSize += 1;
      if (!this.mDidFillOnce)
      {
        i = this.mLast + 1;
        this.mLast = i;
        paramSolverVariable = this.mArrayIndices;
        if (i >= paramSolverVariable.length)
        {
          this.mDidFillOnce = true;
          this.mLast = (paramSolverVariable.length - 1);
        }
      }
      return;
    }
    int i = this.mHead;
    int m = -1;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      if (this.mArrayIndices[i] == paramSolverVariable.id)
      {
        localObject = this.mArrayValues;
        f1 = localObject[i] + paramFloat;
        float f2 = epsilon;
        paramFloat = f1;
        if (f1 > -f2)
        {
          paramFloat = f1;
          if (f1 < f2) {
            paramFloat = 0.0F;
          }
        }
        localObject[i] = paramFloat;
        if (paramFloat == 0.0F)
        {
          if (i == this.mHead)
          {
            this.mHead = this.mArrayNextIndices[i];
          }
          else
          {
            localObject = this.mArrayNextIndices;
            localObject[m] = localObject[i];
          }
          if (paramBoolean) {
            paramSolverVariable.removeFromRow(this.mRow);
          }
          if (this.mDidFillOnce) {
            this.mLast = i;
          }
          paramSolverVariable.usageInRowCount -= 1;
          this.currentSize -= 1;
        }
        return;
      }
      if (this.mArrayIndices[i] < paramSolverVariable.id) {
        m = i;
      }
      i = this.mArrayNextIndices[i];
    }
    j = this.mLast;
    i = j + 1;
    if (this.mDidFillOnce)
    {
      localObject = this.mArrayIndices;
      if (localObject[j] == -1) {
        i = this.mLast;
      } else {
        i = localObject.length;
      }
    }
    Object localObject = this.mArrayIndices;
    j = i;
    if (i >= localObject.length)
    {
      j = i;
      if (this.currentSize < localObject.length) {
        for (int k = 0;; k++)
        {
          localObject = this.mArrayIndices;
          j = i;
          if (k >= localObject.length) {
            break;
          }
          if (localObject[k] == -1)
          {
            j = k;
            break;
          }
        }
      }
    }
    localObject = this.mArrayIndices;
    i = j;
    if (j >= localObject.length)
    {
      i = localObject.length;
      j = this.ROW_SIZE * 2;
      this.ROW_SIZE = j;
      this.mDidFillOnce = false;
      this.mLast = (i - 1);
      this.mArrayValues = Arrays.copyOf(this.mArrayValues, j);
      this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
      this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
    }
    this.mArrayIndices[i] = paramSolverVariable.id;
    this.mArrayValues[i] = paramFloat;
    if (m != -1)
    {
      localObject = this.mArrayNextIndices;
      localObject[i] = localObject[m];
      localObject[m] = i;
    }
    else
    {
      this.mArrayNextIndices[i] = this.mHead;
      this.mHead = i;
    }
    paramSolverVariable.usageInRowCount += 1;
    paramSolverVariable.addToRow(this.mRow);
    this.currentSize += 1;
    if (!this.mDidFillOnce) {
      this.mLast += 1;
    }
    i = this.mLast;
    paramSolverVariable = this.mArrayIndices;
    if (i >= paramSolverVariable.length)
    {
      this.mDidFillOnce = true;
      this.mLast = (paramSolverVariable.length - 1);
    }
  }
  
  public final void clear()
  {
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      SolverVariable localSolverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[j]];
      if (localSolverVariable != null) {
        localSolverVariable.removeFromRow(this.mRow);
      }
      j = this.mArrayNextIndices[j];
    }
    this.mHead = -1;
    this.mLast = -1;
    this.mDidFillOnce = false;
    this.currentSize = 0;
  }
  
  public boolean contains(SolverVariable paramSolverVariable)
  {
    if (this.mHead == -1) {
      return false;
    }
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      if (this.mArrayIndices[j] == paramSolverVariable.id) {
        return true;
      }
      j = this.mArrayNextIndices[j];
    }
    return false;
  }
  
  public void display()
  {
    int j = this.currentSize;
    System.out.print("{ ");
    for (int i = 0; i < j; i++)
    {
      SolverVariable localSolverVariable = getVariable(i);
      if (localSolverVariable != null) {
        System.out.print(localSolverVariable + " = " + getVariableValue(i) + " ");
      }
    }
    System.out.println(" }");
  }
  
  public void divideByAmount(float paramFloat)
  {
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      float[] arrayOfFloat = this.mArrayValues;
      arrayOfFloat[j] /= paramFloat;
      j = this.mArrayNextIndices[j];
    }
  }
  
  public final float get(SolverVariable paramSolverVariable)
  {
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      if (this.mArrayIndices[j] == paramSolverVariable.id) {
        return this.mArrayValues[j];
      }
      j = this.mArrayNextIndices[j];
    }
    return 0.0F;
  }
  
  public int getCurrentSize()
  {
    return this.currentSize;
  }
  
  public int getHead()
  {
    return this.mHead;
  }
  
  public final int getId(int paramInt)
  {
    return this.mArrayIndices[paramInt];
  }
  
  public final int getNextIndice(int paramInt)
  {
    return this.mArrayNextIndices[paramInt];
  }
  
  SolverVariable getPivotCandidate()
  {
    Object localObject1 = this.candidate;
    if (localObject1 == null)
    {
      int j = this.mHead;
      int i = 0;
      Object localObject2;
      for (localObject1 = null; (j != -1) && (i < this.currentSize); localObject1 = localObject2)
      {
        localObject2 = localObject1;
        if (this.mArrayValues[j] < 0.0F)
        {
          SolverVariable localSolverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[j]];
          if (localObject1 != null)
          {
            localObject2 = localObject1;
            if (((SolverVariable)localObject1).strength >= localSolverVariable.strength) {}
          }
          else
          {
            localObject2 = localSolverVariable;
          }
        }
        j = this.mArrayNextIndices[j];
        i++;
      }
      return (SolverVariable)localObject1;
    }
    return (SolverVariable)localObject1;
  }
  
  public final float getValue(int paramInt)
  {
    return this.mArrayValues[paramInt];
  }
  
  public SolverVariable getVariable(int paramInt)
  {
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      if (i == paramInt) {
        return this.mCache.mIndexedVariables[this.mArrayIndices[j]];
      }
      j = this.mArrayNextIndices[j];
    }
    return null;
  }
  
  public float getVariableValue(int paramInt)
  {
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      if (i == paramInt) {
        return this.mArrayValues[j];
      }
      j = this.mArrayNextIndices[j];
    }
    return 0.0F;
  }
  
  boolean hasAtLeastOnePositiveVariable()
  {
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      if (this.mArrayValues[j] > 0.0F) {
        return true;
      }
      j = this.mArrayNextIndices[j];
    }
    return false;
  }
  
  public int indexOf(SolverVariable paramSolverVariable)
  {
    if (this.mHead == -1) {
      return -1;
    }
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      if (this.mArrayIndices[j] == paramSolverVariable.id) {
        return j;
      }
      j = this.mArrayNextIndices[j];
    }
    return -1;
  }
  
  public void invert()
  {
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      float[] arrayOfFloat = this.mArrayValues;
      arrayOfFloat[j] *= -1.0F;
      j = this.mArrayNextIndices[j];
    }
  }
  
  public final void put(SolverVariable paramSolverVariable, float paramFloat)
  {
    if (paramFloat == 0.0F)
    {
      remove(paramSolverVariable, true);
      return;
    }
    if (this.mHead == -1)
    {
      this.mHead = 0;
      this.mArrayValues[0] = paramFloat;
      this.mArrayIndices[0] = paramSolverVariable.id;
      this.mArrayNextIndices[this.mHead] = -1;
      paramSolverVariable.usageInRowCount += 1;
      paramSolverVariable.addToRow(this.mRow);
      this.currentSize += 1;
      if (!this.mDidFillOnce)
      {
        i = this.mLast + 1;
        this.mLast = i;
        paramSolverVariable = this.mArrayIndices;
        if (i >= paramSolverVariable.length)
        {
          this.mDidFillOnce = true;
          this.mLast = (paramSolverVariable.length - 1);
        }
      }
      return;
    }
    int i = this.mHead;
    int m = -1;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      if (this.mArrayIndices[i] == paramSolverVariable.id)
      {
        this.mArrayValues[i] = paramFloat;
        return;
      }
      if (this.mArrayIndices[i] < paramSolverVariable.id) {
        m = i;
      }
      i = this.mArrayNextIndices[i];
    }
    j = this.mLast;
    i = j + 1;
    if (this.mDidFillOnce)
    {
      arrayOfInt = this.mArrayIndices;
      if (arrayOfInt[j] == -1) {
        i = this.mLast;
      } else {
        i = arrayOfInt.length;
      }
    }
    int[] arrayOfInt = this.mArrayIndices;
    j = i;
    if (i >= arrayOfInt.length)
    {
      j = i;
      if (this.currentSize < arrayOfInt.length) {
        for (int k = 0;; k++)
        {
          arrayOfInt = this.mArrayIndices;
          j = i;
          if (k >= arrayOfInt.length) {
            break;
          }
          if (arrayOfInt[k] == -1)
          {
            j = k;
            break;
          }
        }
      }
    }
    arrayOfInt = this.mArrayIndices;
    i = j;
    if (j >= arrayOfInt.length)
    {
      i = arrayOfInt.length;
      j = this.ROW_SIZE * 2;
      this.ROW_SIZE = j;
      this.mDidFillOnce = false;
      this.mLast = (i - 1);
      this.mArrayValues = Arrays.copyOf(this.mArrayValues, j);
      this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
      this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
    }
    this.mArrayIndices[i] = paramSolverVariable.id;
    this.mArrayValues[i] = paramFloat;
    if (m != -1)
    {
      arrayOfInt = this.mArrayNextIndices;
      arrayOfInt[i] = arrayOfInt[m];
      arrayOfInt[m] = i;
    }
    else
    {
      this.mArrayNextIndices[i] = this.mHead;
      this.mHead = i;
    }
    paramSolverVariable.usageInRowCount += 1;
    paramSolverVariable.addToRow(this.mRow);
    i = this.currentSize + 1;
    this.currentSize = i;
    if (!this.mDidFillOnce) {
      this.mLast += 1;
    }
    paramSolverVariable = this.mArrayIndices;
    if (i >= paramSolverVariable.length) {
      this.mDidFillOnce = true;
    }
    if (this.mLast >= paramSolverVariable.length)
    {
      this.mDidFillOnce = true;
      this.mLast = (paramSolverVariable.length - 1);
    }
  }
  
  public final float remove(SolverVariable paramSolverVariable, boolean paramBoolean)
  {
    if (this.candidate == paramSolverVariable) {
      this.candidate = null;
    }
    if (this.mHead == -1) {
      return 0.0F;
    }
    int i = this.mHead;
    int k = -1;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      if (this.mArrayIndices[i] == paramSolverVariable.id)
      {
        if (i == this.mHead)
        {
          this.mHead = this.mArrayNextIndices[i];
        }
        else
        {
          int[] arrayOfInt = this.mArrayNextIndices;
          arrayOfInt[k] = arrayOfInt[i];
        }
        if (paramBoolean) {
          paramSolverVariable.removeFromRow(this.mRow);
        }
        paramSolverVariable.usageInRowCount -= 1;
        this.currentSize -= 1;
        this.mArrayIndices[i] = -1;
        if (this.mDidFillOnce) {
          this.mLast = i;
        }
        return this.mArrayValues[i];
      }
      k = i;
      i = this.mArrayNextIndices[i];
    }
    return 0.0F;
  }
  
  public int sizeInBytes()
  {
    return 0 + this.mArrayIndices.length * 4 * 3 + 36;
  }
  
  public String toString()
  {
    String str = "";
    int j = this.mHead;
    for (int i = 0; (j != -1) && (i < this.currentSize); i++)
    {
      str = str + " -> ";
      str = str + this.mArrayValues[j] + " : ";
      str = str + this.mCache.mIndexedVariables[this.mArrayIndices[j]];
      j = this.mArrayNextIndices[j];
    }
    return str;
  }
  
  public float use(ArrayRow paramArrayRow, boolean paramBoolean)
  {
    float f = get(paramArrayRow.variable);
    remove(paramArrayRow.variable, paramBoolean);
    paramArrayRow = paramArrayRow.variables;
    int j = paramArrayRow.getCurrentSize();
    for (int i = 0; i < j; i++)
    {
      SolverVariable localSolverVariable = paramArrayRow.getVariable(i);
      add(localSolverVariable, paramArrayRow.get(localSolverVariable) * f, paramBoolean);
    }
    return f;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/ArrayLinkedVariables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */