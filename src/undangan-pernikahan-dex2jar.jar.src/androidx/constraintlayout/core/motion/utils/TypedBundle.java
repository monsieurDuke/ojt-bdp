package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

public class TypedBundle
{
  private static final int INITIAL_BOOLEAN = 4;
  private static final int INITIAL_FLOAT = 10;
  private static final int INITIAL_INT = 10;
  private static final int INITIAL_STRING = 5;
  int mCountBoolean = 0;
  int mCountFloat = 0;
  int mCountInt = 0;
  int mCountString = 0;
  int[] mTypeBoolean = new int[4];
  int[] mTypeFloat = new int[10];
  int[] mTypeInt = new int[10];
  int[] mTypeString = new int[5];
  boolean[] mValueBoolean = new boolean[4];
  float[] mValueFloat = new float[10];
  int[] mValueInt = new int[10];
  String[] mValueString = new String[5];
  
  public void add(int paramInt, float paramFloat)
  {
    int i = this.mCountFloat;
    Object localObject = this.mTypeFloat;
    if (i >= localObject.length)
    {
      this.mTypeFloat = Arrays.copyOf((int[])localObject, localObject.length * 2);
      localObject = this.mValueFloat;
      this.mValueFloat = Arrays.copyOf((float[])localObject, localObject.length * 2);
    }
    localObject = this.mTypeFloat;
    i = this.mCountFloat;
    localObject[i] = paramInt;
    localObject = this.mValueFloat;
    this.mCountFloat = (i + 1);
    localObject[i] = paramFloat;
  }
  
  public void add(int paramInt1, int paramInt2)
  {
    int i = this.mCountInt;
    int[] arrayOfInt = this.mTypeInt;
    if (i >= arrayOfInt.length)
    {
      this.mTypeInt = Arrays.copyOf(arrayOfInt, arrayOfInt.length * 2);
      arrayOfInt = this.mValueInt;
      this.mValueInt = Arrays.copyOf(arrayOfInt, arrayOfInt.length * 2);
    }
    arrayOfInt = this.mTypeInt;
    i = this.mCountInt;
    arrayOfInt[i] = paramInt1;
    arrayOfInt = this.mValueInt;
    this.mCountInt = (i + 1);
    arrayOfInt[i] = paramInt2;
  }
  
  public void add(int paramInt, String paramString)
  {
    int i = this.mCountString;
    Object localObject = this.mTypeString;
    if (i >= localObject.length)
    {
      this.mTypeString = Arrays.copyOf((int[])localObject, localObject.length * 2);
      localObject = this.mValueString;
      this.mValueString = ((String[])Arrays.copyOf((Object[])localObject, localObject.length * 2));
    }
    localObject = this.mTypeString;
    i = this.mCountString;
    localObject[i] = paramInt;
    localObject = this.mValueString;
    this.mCountString = (i + 1);
    localObject[i] = paramString;
  }
  
  public void add(int paramInt, boolean paramBoolean)
  {
    int i = this.mCountBoolean;
    Object localObject = this.mTypeBoolean;
    if (i >= localObject.length)
    {
      this.mTypeBoolean = Arrays.copyOf((int[])localObject, localObject.length * 2);
      localObject = this.mValueBoolean;
      this.mValueBoolean = Arrays.copyOf((boolean[])localObject, localObject.length * 2);
    }
    localObject = this.mTypeBoolean;
    i = this.mCountBoolean;
    localObject[i] = paramInt;
    localObject = this.mValueBoolean;
    this.mCountBoolean = (i + 1);
    localObject[i] = paramBoolean;
  }
  
  public void addIfNotNull(int paramInt, String paramString)
  {
    if (paramString != null) {
      add(paramInt, paramString);
    }
  }
  
  public void applyDelta(TypedBundle paramTypedBundle)
  {
    for (int i = 0; i < this.mCountInt; i++) {
      paramTypedBundle.add(this.mTypeInt[i], this.mValueInt[i]);
    }
    for (i = 0; i < this.mCountFloat; i++) {
      paramTypedBundle.add(this.mTypeFloat[i], this.mValueFloat[i]);
    }
    for (i = 0; i < this.mCountString; i++) {
      paramTypedBundle.add(this.mTypeString[i], this.mValueString[i]);
    }
    for (i = 0; i < this.mCountBoolean; i++) {
      paramTypedBundle.add(this.mTypeBoolean[i], this.mValueBoolean[i]);
    }
  }
  
  public void applyDelta(TypedValues paramTypedValues)
  {
    for (int i = 0; i < this.mCountInt; i++) {
      paramTypedValues.setValue(this.mTypeInt[i], this.mValueInt[i]);
    }
    for (i = 0; i < this.mCountFloat; i++) {
      paramTypedValues.setValue(this.mTypeFloat[i], this.mValueFloat[i]);
    }
    for (i = 0; i < this.mCountString; i++) {
      paramTypedValues.setValue(this.mTypeString[i], this.mValueString[i]);
    }
    for (i = 0; i < this.mCountBoolean; i++) {
      paramTypedValues.setValue(this.mTypeBoolean[i], this.mValueBoolean[i]);
    }
  }
  
  public void clear()
  {
    this.mCountBoolean = 0;
    this.mCountString = 0;
    this.mCountFloat = 0;
    this.mCountInt = 0;
  }
  
  public int getInteger(int paramInt)
  {
    for (int i = 0; i < this.mCountInt; i++) {
      if (this.mTypeInt[i] == paramInt) {
        return this.mValueInt[i];
      }
    }
    return -1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/TypedBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */