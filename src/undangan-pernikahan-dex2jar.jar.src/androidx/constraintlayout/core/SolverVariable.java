package androidx.constraintlayout.core;

import java.util.Arrays;
import java.util.HashSet;

public class SolverVariable
  implements Comparable<SolverVariable>
{
  private static final boolean INTERNAL_DEBUG = false;
  static final int MAX_STRENGTH = 9;
  public static final int STRENGTH_BARRIER = 6;
  public static final int STRENGTH_CENTERING = 7;
  public static final int STRENGTH_EQUALITY = 5;
  public static final int STRENGTH_FIXED = 8;
  public static final int STRENGTH_HIGH = 3;
  public static final int STRENGTH_HIGHEST = 4;
  public static final int STRENGTH_LOW = 1;
  public static final int STRENGTH_MEDIUM = 2;
  public static final int STRENGTH_NONE = 0;
  private static final boolean VAR_USE_HASH = false;
  private static int uniqueConstantId = 1;
  private static int uniqueErrorId;
  private static int uniqueId = 1;
  private static int uniqueSlackId = 1;
  private static int uniqueUnrestrictedId;
  public float computedValue;
  int definitionId = -1;
  float[] goalStrengthVector = new float[9];
  public int id = -1;
  public boolean inGoal;
  HashSet<ArrayRow> inRows = null;
  public boolean isFinalValue = false;
  boolean isSynonym = false;
  ArrayRow[] mClientEquations = new ArrayRow[16];
  int mClientEquationsCount = 0;
  private String mName;
  Type mType;
  public int strength = 0;
  float[] strengthVector = new float[9];
  int synonym = -1;
  float synonymDelta = 0.0F;
  public int usageInRowCount = 0;
  
  static
  {
    uniqueErrorId = 1;
    uniqueUnrestrictedId = 1;
  }
  
  public SolverVariable(Type paramType, String paramString)
  {
    this.mType = paramType;
  }
  
  public SolverVariable(String paramString, Type paramType)
  {
    this.mName = paramString;
    this.mType = paramType;
  }
  
  private static String getUniqueName(Type paramType, String paramString)
  {
    if (paramString != null) {
      return paramString + uniqueErrorId;
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$SolverVariable$Type[paramType.ordinal()])
    {
    default: 
      throw new AssertionError(paramType.name());
    case 5: 
      paramType = new StringBuilder().append("V");
      i = uniqueId + 1;
      uniqueId = i;
      return i;
    case 4: 
      paramType = new StringBuilder().append("e");
      i = uniqueErrorId + 1;
      uniqueErrorId = i;
      return i;
    case 3: 
      paramType = new StringBuilder().append("S");
      i = uniqueSlackId + 1;
      uniqueSlackId = i;
      return i;
    case 2: 
      paramType = new StringBuilder().append("C");
      i = uniqueConstantId + 1;
      uniqueConstantId = i;
      return i;
    }
    paramType = new StringBuilder().append("U");
    int i = uniqueUnrestrictedId + 1;
    uniqueUnrestrictedId = i;
    return i;
  }
  
  static void increaseErrorId()
  {
    uniqueErrorId += 1;
  }
  
  public final void addToRow(ArrayRow paramArrayRow)
  {
    int j;
    for (int i = 0;; i++)
    {
      j = this.mClientEquationsCount;
      if (i >= j) {
        break;
      }
      if (this.mClientEquations[i] == paramArrayRow) {
        return;
      }
    }
    ArrayRow[] arrayOfArrayRow = this.mClientEquations;
    if (j >= arrayOfArrayRow.length) {
      this.mClientEquations = ((ArrayRow[])Arrays.copyOf(arrayOfArrayRow, arrayOfArrayRow.length * 2));
    }
    arrayOfArrayRow = this.mClientEquations;
    i = this.mClientEquationsCount;
    arrayOfArrayRow[i] = paramArrayRow;
    this.mClientEquationsCount = (i + 1);
  }
  
  void clearStrengths()
  {
    for (int i = 0; i < 9; i++) {
      this.strengthVector[i] = 0.0F;
    }
  }
  
  public int compareTo(SolverVariable paramSolverVariable)
  {
    return this.id - paramSolverVariable.id;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public final void removeFromRow(ArrayRow paramArrayRow)
  {
    int j = this.mClientEquationsCount;
    for (int i = 0; i < j; i++) {
      if (this.mClientEquations[i] == paramArrayRow)
      {
        while (i < j - 1)
        {
          paramArrayRow = this.mClientEquations;
          paramArrayRow[i] = paramArrayRow[(i + 1)];
          i++;
        }
        this.mClientEquationsCount -= 1;
        return;
      }
    }
  }
  
  public void reset()
  {
    this.mName = null;
    this.mType = Type.UNKNOWN;
    this.strength = 0;
    this.id = -1;
    this.definitionId = -1;
    this.computedValue = 0.0F;
    this.isFinalValue = false;
    this.isSynonym = false;
    this.synonym = -1;
    this.synonymDelta = 0.0F;
    int j = this.mClientEquationsCount;
    for (int i = 0; i < j; i++) {
      this.mClientEquations[i] = null;
    }
    this.mClientEquationsCount = 0;
    this.usageInRowCount = 0;
    this.inGoal = false;
    Arrays.fill(this.goalStrengthVector, 0.0F);
  }
  
  public void setFinalValue(LinearSystem paramLinearSystem, float paramFloat)
  {
    this.computedValue = paramFloat;
    this.isFinalValue = true;
    this.isSynonym = false;
    this.synonym = -1;
    this.synonymDelta = 0.0F;
    int j = this.mClientEquationsCount;
    this.definitionId = -1;
    for (int i = 0; i < j; i++) {
      this.mClientEquations[i].updateFromFinalVariable(paramLinearSystem, this, false);
    }
    this.mClientEquationsCount = 0;
  }
  
  public void setName(String paramString)
  {
    this.mName = paramString;
  }
  
  public void setSynonym(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable, float paramFloat)
  {
    this.isSynonym = true;
    this.synonym = paramSolverVariable.id;
    this.synonymDelta = paramFloat;
    int j = this.mClientEquationsCount;
    this.definitionId = -1;
    for (int i = 0; i < j; i++) {
      this.mClientEquations[i].updateFromSynonymVariable(paramLinearSystem, this, false);
    }
    this.mClientEquationsCount = 0;
    paramLinearSystem.displayReadableRows();
  }
  
  public void setType(Type paramType, String paramString)
  {
    this.mType = paramType;
  }
  
  String strengthsToString()
  {
    Object localObject2 = this + "[";
    int i = 0;
    int k = 1;
    for (int j = 0; j < this.strengthVector.length; j++)
    {
      localObject1 = (String)localObject2 + this.strengthVector[j];
      localObject2 = this.strengthVector;
      float f = localObject2[j];
      if (f > 0.0F) {
        i = 0;
      } else if (f < 0.0F) {
        i = 1;
      }
      if (f != 0.0F) {
        k = 0;
      }
      if (j < localObject2.length - 1) {
        localObject2 = (String)localObject1 + ", ";
      } else {
        localObject2 = (String)localObject1 + "] ";
      }
    }
    Object localObject1 = localObject2;
    if (i != 0) {
      localObject1 = (String)localObject2 + " (-)";
    }
    localObject2 = localObject1;
    if (k != 0) {
      localObject2 = (String)localObject1 + " (*)";
    }
    return (String)localObject2;
  }
  
  public String toString()
  {
    String str;
    if (this.mName != null) {
      str = "" + this.mName;
    } else {
      str = "" + this.id;
    }
    return str;
  }
  
  public final void updateReferencesWithNewDefinition(LinearSystem paramLinearSystem, ArrayRow paramArrayRow)
  {
    int j = this.mClientEquationsCount;
    for (int i = 0; i < j; i++) {
      this.mClientEquations[i].updateFromRow(paramLinearSystem, paramArrayRow, false);
    }
    this.mClientEquationsCount = 0;
  }
  
  public static enum Type
  {
    private static final Type[] $VALUES;
    
    static
    {
      Type localType4 = new Type("UNRESTRICTED", 0);
      UNRESTRICTED = localType4;
      Type localType3 = new Type("CONSTANT", 1);
      CONSTANT = localType3;
      Type localType5 = new Type("SLACK", 2);
      SLACK = localType5;
      Type localType1 = new Type("ERROR", 3);
      ERROR = localType1;
      Type localType2 = new Type("UNKNOWN", 4);
      UNKNOWN = localType2;
      $VALUES = new Type[] { localType4, localType3, localType5, localType1, localType2 };
    }
    
    private Type() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/SolverVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */