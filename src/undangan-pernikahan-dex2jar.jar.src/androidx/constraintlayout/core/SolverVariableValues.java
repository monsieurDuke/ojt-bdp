package androidx.constraintlayout.core;

import java.io.PrintStream;
import java.util.Arrays;

public class SolverVariableValues
  implements ArrayRow.ArrayRowVariables
{
  private static final boolean DEBUG = false;
  private static final boolean HASH = true;
  private static float epsilon = 0.001F;
  private int HASH_SIZE = 16;
  private final int NONE = -1;
  private int SIZE = 16;
  int head = -1;
  int[] keys = new int[16];
  protected final Cache mCache;
  int mCount = 0;
  private final ArrayRow mRow;
  int[] next = new int[16];
  int[] nextKeys = new int[16];
  int[] previous = new int[16];
  float[] values = new float[16];
  int[] variables = new int[16];
  
  SolverVariableValues(ArrayRow paramArrayRow, Cache paramCache)
  {
    this.mRow = paramArrayRow;
    this.mCache = paramCache;
    clear();
  }
  
  private void addToHashMap(SolverVariable paramSolverVariable, int paramInt)
  {
    int k = paramSolverVariable.id % this.HASH_SIZE;
    paramSolverVariable = this.keys;
    int j = paramSolverVariable[k];
    int i = j;
    if (j == -1)
    {
      paramSolverVariable[k] = paramInt;
    }
    else
    {
      for (;;)
      {
        paramSolverVariable = this.nextKeys;
        if (paramSolverVariable[i] == -1) {
          break;
        }
        i = paramSolverVariable[i];
      }
      paramSolverVariable[i] = paramInt;
    }
    this.nextKeys[paramInt] = -1;
  }
  
  private void addVariable(int paramInt, SolverVariable paramSolverVariable, float paramFloat)
  {
    this.variables[paramInt] = paramSolverVariable.id;
    this.values[paramInt] = paramFloat;
    this.previous[paramInt] = -1;
    this.next[paramInt] = -1;
    paramSolverVariable.addToRow(this.mRow);
    paramSolverVariable.usageInRowCount += 1;
    this.mCount += 1;
  }
  
  private void displayHash()
  {
    for (int i = 0; i < this.HASH_SIZE; i++) {
      if (this.keys[i] != -1)
      {
        String str = hashCode() + " hash [" + i + "] => ";
        int k = this.keys[i];
        int j = 0;
        while (j == 0)
        {
          str = str + " " + this.variables[k];
          int[] arrayOfInt = this.nextKeys;
          if (arrayOfInt[k] != -1) {
            k = arrayOfInt[k];
          } else {
            j = 1;
          }
        }
        System.out.println(str);
      }
    }
  }
  
  private int findEmptySlot()
  {
    for (int i = 0; i < this.SIZE; i++) {
      if (this.variables[i] == -1) {
        return i;
      }
    }
    return -1;
  }
  
  private void increaseSize()
  {
    int j = this.SIZE * 2;
    this.variables = Arrays.copyOf(this.variables, j);
    this.values = Arrays.copyOf(this.values, j);
    this.previous = Arrays.copyOf(this.previous, j);
    this.next = Arrays.copyOf(this.next, j);
    this.nextKeys = Arrays.copyOf(this.nextKeys, j);
    for (int i = this.SIZE; i < j; i++)
    {
      this.variables[i] = -1;
      this.nextKeys[i] = -1;
    }
    this.SIZE = j;
  }
  
  private void insertVariable(int paramInt, SolverVariable paramSolverVariable, float paramFloat)
  {
    int i = findEmptySlot();
    addVariable(i, paramSolverVariable, paramFloat);
    if (paramInt != -1)
    {
      this.previous[i] = paramInt;
      int[] arrayOfInt = this.next;
      arrayOfInt[i] = arrayOfInt[paramInt];
      arrayOfInt[paramInt] = i;
    }
    else
    {
      this.previous[i] = -1;
      if (this.mCount > 0)
      {
        this.next[i] = this.head;
        this.head = i;
      }
      else
      {
        this.next[i] = -1;
      }
    }
    paramInt = this.next[i];
    if (paramInt != -1) {
      this.previous[paramInt] = i;
    }
    addToHashMap(paramSolverVariable, i);
  }
  
  private void removeFromHashMap(SolverVariable paramSolverVariable)
  {
    int m = paramSolverVariable.id % this.HASH_SIZE;
    int j = this.keys[m];
    if (j == -1) {
      return;
    }
    int k = paramSolverVariable.id;
    int i = j;
    if (this.variables[j] == k)
    {
      int[] arrayOfInt = this.keys;
      paramSolverVariable = this.nextKeys;
      arrayOfInt[m] = paramSolverVariable[j];
      paramSolverVariable[j] = -1;
    }
    else
    {
      for (;;)
      {
        paramSolverVariable = this.nextKeys;
        j = paramSolverVariable[i];
        if ((j == -1) || (this.variables[j] == k)) {
          break;
        }
        i = paramSolverVariable[i];
      }
      j = paramSolverVariable[i];
      if ((j != -1) && (this.variables[j] == k))
      {
        paramSolverVariable[i] = paramSolverVariable[j];
        paramSolverVariable[j] = -1;
      }
    }
  }
  
  public void add(SolverVariable paramSolverVariable, float paramFloat, boolean paramBoolean)
  {
    float f = epsilon;
    if ((paramFloat > -f) && (paramFloat < f)) {
      return;
    }
    int i = indexOf(paramSolverVariable);
    if (i == -1)
    {
      put(paramSolverVariable, paramFloat);
    }
    else
    {
      float[] arrayOfFloat = this.values;
      paramFloat = arrayOfFloat[i] + paramFloat;
      arrayOfFloat[i] = paramFloat;
      f = epsilon;
      if ((paramFloat > -f) && (paramFloat < f))
      {
        arrayOfFloat[i] = 0.0F;
        remove(paramSolverVariable, paramBoolean);
      }
    }
  }
  
  public void clear()
  {
    int j = this.mCount;
    for (int i = 0; i < j; i++)
    {
      SolverVariable localSolverVariable = getVariable(i);
      if (localSolverVariable != null) {
        localSolverVariable.removeFromRow(this.mRow);
      }
    }
    for (i = 0; i < this.SIZE; i++)
    {
      this.variables[i] = -1;
      this.nextKeys[i] = -1;
    }
    for (i = 0; i < this.HASH_SIZE; i++) {
      this.keys[i] = -1;
    }
    this.mCount = 0;
    this.head = -1;
  }
  
  public boolean contains(SolverVariable paramSolverVariable)
  {
    boolean bool;
    if (indexOf(paramSolverVariable) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void display()
  {
    int j = this.mCount;
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
    int k = this.mCount;
    int j = this.head;
    for (int i = 0; i < k; i++)
    {
      float[] arrayOfFloat = this.values;
      arrayOfFloat[j] /= paramFloat;
      j = this.next[j];
      if (j == -1) {
        break;
      }
    }
  }
  
  public float get(SolverVariable paramSolverVariable)
  {
    int i = indexOf(paramSolverVariable);
    if (i != -1) {
      return this.values[i];
    }
    return 0.0F;
  }
  
  public int getCurrentSize()
  {
    return this.mCount;
  }
  
  public SolverVariable getVariable(int paramInt)
  {
    int k = this.mCount;
    if (k == 0) {
      return null;
    }
    int j = this.head;
    for (int i = 0; i < k; i++)
    {
      if ((i == paramInt) && (j != -1)) {
        return this.mCache.mIndexedVariables[this.variables[j]];
      }
      j = this.next[j];
      if (j == -1) {
        break;
      }
    }
    return null;
  }
  
  public float getVariableValue(int paramInt)
  {
    int k = this.mCount;
    int j = this.head;
    for (int i = 0; i < k; i++)
    {
      if (i == paramInt) {
        return this.values[j];
      }
      j = this.next[j];
      if (j == -1) {
        break;
      }
    }
    return 0.0F;
  }
  
  public int indexOf(SolverVariable paramSolverVariable)
  {
    if ((this.mCount != 0) && (paramSolverVariable != null))
    {
      int k = paramSolverVariable.id;
      int i = this.HASH_SIZE;
      int j = this.keys[(k % i)];
      if (j == -1) {
        return -1;
      }
      i = j;
      if (this.variables[j] == k) {
        return j;
      }
      for (;;)
      {
        paramSolverVariable = this.nextKeys;
        j = paramSolverVariable[i];
        if ((j == -1) || (this.variables[j] == k)) {
          break;
        }
        i = paramSolverVariable[i];
      }
      if (j == -1) {
        return -1;
      }
      if (this.variables[j] == k) {
        return j;
      }
      return -1;
    }
    return -1;
  }
  
  public void invert()
  {
    int k = this.mCount;
    int j = this.head;
    for (int i = 0; i < k; i++)
    {
      float[] arrayOfFloat = this.values;
      arrayOfFloat[j] *= -1.0F;
      j = this.next[j];
      if (j == -1) {
        break;
      }
    }
  }
  
  public void put(SolverVariable paramSolverVariable, float paramFloat)
  {
    float f = epsilon;
    if ((paramFloat > -f) && (paramFloat < f))
    {
      remove(paramSolverVariable, true);
      return;
    }
    if (this.mCount == 0)
    {
      addVariable(0, paramSolverVariable, paramFloat);
      addToHashMap(paramSolverVariable, 0);
      this.head = 0;
    }
    else
    {
      int i = indexOf(paramSolverVariable);
      if (i != -1)
      {
        this.values[i] = paramFloat;
      }
      else
      {
        if (this.mCount + 1 >= this.SIZE) {
          increaseSize();
        }
        int n = this.mCount;
        int j = -1;
        i = this.head;
        int m;
        for (int k = 0;; k++)
        {
          m = j;
          if (k >= n) {
            break;
          }
          if (this.variables[i] == paramSolverVariable.id)
          {
            this.values[i] = paramFloat;
            return;
          }
          if (this.variables[i] < paramSolverVariable.id) {
            j = i;
          }
          i = this.next[i];
          if (i == -1)
          {
            m = j;
            break;
          }
        }
        insertVariable(m, paramSolverVariable, paramFloat);
      }
    }
  }
  
  public float remove(SolverVariable paramSolverVariable, boolean paramBoolean)
  {
    int i = indexOf(paramSolverVariable);
    if (i == -1) {
      return 0.0F;
    }
    removeFromHashMap(paramSolverVariable);
    float f = this.values[i];
    if (this.head == i) {
      this.head = this.next[i];
    }
    this.variables[i] = -1;
    int[] arrayOfInt2 = this.previous;
    int j = arrayOfInt2[i];
    if (j != -1)
    {
      int[] arrayOfInt1 = this.next;
      arrayOfInt1[j] = arrayOfInt1[i];
    }
    j = this.next[i];
    if (j != -1) {
      arrayOfInt2[j] = arrayOfInt2[i];
    }
    this.mCount -= 1;
    paramSolverVariable.usageInRowCount -= 1;
    if (paramBoolean) {
      paramSolverVariable.removeFromRow(this.mRow);
    }
    return f;
  }
  
  public int sizeInBytes()
  {
    return 0;
  }
  
  public String toString()
  {
    String str = hashCode() + " { ";
    int j = this.mCount;
    for (int i = 0; i < j; i++)
    {
      SolverVariable localSolverVariable = getVariable(i);
      if (localSolverVariable != null)
      {
        str = str + localSolverVariable + " = " + getVariableValue(i) + " ";
        int k = indexOf(localSolverVariable);
        str = str + "[p: ";
        if (this.previous[k] != -1) {
          str = str + this.mCache.mIndexedVariables[this.variables[this.previous[k]]];
        } else {
          str = str + "none";
        }
        str = str + ", n: ";
        if (this.next[k] != -1) {
          str = str + this.mCache.mIndexedVariables[this.variables[this.next[k]]];
        } else {
          str = str + "none";
        }
        str = str + "]";
      }
    }
    return str + " }";
  }
  
  public float use(ArrayRow paramArrayRow, boolean paramBoolean)
  {
    float f1 = get(paramArrayRow.variable);
    remove(paramArrayRow.variable, paramBoolean);
    paramArrayRow = (SolverVariableValues)paramArrayRow.variables;
    int m = paramArrayRow.getCurrentSize();
    int i = paramArrayRow.head;
    int j = 0;
    i = 0;
    while (j < m)
    {
      int k = j;
      if (paramArrayRow.variables[i] != -1)
      {
        float f2 = paramArrayRow.values[i];
        add(this.mCache.mIndexedVariables[paramArrayRow.variables[i]], f2 * f1, paramBoolean);
        k = j + 1;
      }
      i++;
      j = k;
    }
    return f1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/SolverVariableValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */