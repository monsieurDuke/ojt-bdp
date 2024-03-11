package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import java.io.PrintStream;
import java.text.DecimalFormat;

public abstract class TimeCycleSplineSet
{
  protected static final int CURVE_OFFSET = 2;
  protected static final int CURVE_PERIOD = 1;
  protected static final int CURVE_VALUE = 0;
  private static final String TAG = "SplineSet";
  protected static float VAL_2PI = 6.2831855F;
  protected int count;
  protected float last_cycle = NaN.0F;
  protected long last_time;
  protected float[] mCache = new float[3];
  protected boolean mContinue = false;
  protected CurveFit mCurveFit;
  protected int[] mTimePoints = new int[10];
  protected String mType;
  protected float[][] mValues = new float[10][3];
  protected int mWaveShape = 0;
  
  protected float calcWave(float paramFloat)
  {
    switch (this.mWaveShape)
    {
    default: 
      return (float)Math.sin(VAL_2PI * paramFloat);
    case 6: 
      paramFloat = 1.0F - Math.abs(paramFloat * 4.0F % 4.0F - 2.0F);
      return 1.0F - paramFloat * paramFloat;
    case 5: 
      return (float)Math.cos(VAL_2PI * paramFloat);
    case 4: 
      return 1.0F - (paramFloat * 2.0F + 1.0F) % 2.0F;
    case 3: 
      return (paramFloat * 2.0F + 1.0F) % 2.0F - 1.0F;
    case 2: 
      return 1.0F - Math.abs(paramFloat);
    }
    return Math.signum(VAL_2PI * paramFloat);
  }
  
  public CurveFit getCurveFit()
  {
    return this.mCurveFit;
  }
  
  public void setPoint(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3)
  {
    Object localObject = this.mTimePoints;
    int i = this.count;
    localObject[i] = paramInt1;
    localObject = this.mValues[i];
    localObject[0] = paramFloat1;
    localObject[1] = paramFloat2;
    localObject[2] = paramFloat3;
    this.mWaveShape = Math.max(this.mWaveShape, paramInt2);
    this.count += 1;
  }
  
  protected void setStartTime(long paramLong)
  {
    this.last_time = paramLong;
  }
  
  public void setType(String paramString)
  {
    this.mType = paramString;
  }
  
  public void setup(int paramInt)
  {
    int i = this.count;
    if (i == 0)
    {
      System.err.println("Error no points added to " + this.mType);
      return;
    }
    Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, i - 1);
    i = 0;
    int j = 1;
    for (;;)
    {
      localObject1 = this.mTimePoints;
      if (j >= localObject1.length) {
        break;
      }
      int k = i;
      if (localObject1[j] != localObject1[(j - 1)]) {
        k = i + 1;
      }
      j++;
      i = k;
    }
    j = i;
    if (i == 0) {
      j = 1;
    }
    double[] arrayOfDouble = new double[j];
    Object localObject1 = new double[j][3];
    j = 0;
    for (i = 0; i < this.count; i++)
    {
      Object localObject2;
      if (i > 0)
      {
        localObject2 = this.mTimePoints;
        if (localObject2[i] == localObject2[(i - 1)]) {}
      }
      else
      {
        arrayOfDouble[j] = (this.mTimePoints[i] * 0.01D);
        Object localObject3 = localObject1[j];
        localObject2 = this.mValues[i];
        localObject3[0] = localObject2[0];
        localObject1[j][1] = localObject2[1];
        localObject1[j][2] = localObject2[2];
        j++;
      }
    }
    this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, (double[][])localObject1);
  }
  
  public String toString()
  {
    String str = this.mType;
    DecimalFormat localDecimalFormat = new DecimalFormat("##.##");
    for (int i = 0; i < this.count; i++) {
      str = str + "[" + this.mTimePoints[i] + " , " + localDecimalFormat.format(this.mValues[i]) + "] ";
    }
    return str;
  }
  
  public static class CustomSet
    extends TimeCycleSplineSet
  {
    String mAttributeName = paramString.split(",")[1];
    float[] mCache;
    KeyFrameArray.CustomArray mConstraintAttributeList;
    float[] mTempValues;
    KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();
    
    public CustomSet(String paramString, KeyFrameArray.CustomArray paramCustomArray)
    {
      this.mConstraintAttributeList = paramCustomArray;
    }
    
    public void setPoint(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3)
    {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
    }
    
    public void setPoint(int paramInt1, CustomAttribute paramCustomAttribute, float paramFloat1, int paramInt2, float paramFloat2)
    {
      this.mConstraintAttributeList.append(paramInt1, paramCustomAttribute);
      this.mWaveProperties.append(paramInt1, new float[] { paramFloat1, paramFloat2 });
      this.mWaveShape = Math.max(this.mWaveShape, paramInt2);
    }
    
    public boolean setProperty(MotionWidget paramMotionWidget, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      this.mCurveFit.getPos(paramFloat, this.mTempValues);
      float[] arrayOfFloat = this.mTempValues;
      float f2 = arrayOfFloat[(arrayOfFloat.length - 2)];
      paramFloat = arrayOfFloat[(arrayOfFloat.length - 1)];
      long l = this.last_time;
      if (Float.isNaN(this.last_cycle))
      {
        this.last_cycle = paramKeyCache.getFloatValue(paramMotionWidget, this.mAttributeName, 0);
        if (Float.isNaN(this.last_cycle)) {
          this.last_cycle = 0.0F;
        }
      }
      this.last_cycle = ((float)((this.last_cycle + (paramLong - l) * 1.0E-9D * f2) % 1.0D));
      this.last_time = paramLong;
      float f1 = calcWave(this.last_cycle);
      this.mContinue = false;
      for (int i = 0; i < this.mCache.length; i++)
      {
        boolean bool2 = this.mContinue;
        boolean bool1;
        if (this.mTempValues[i] != 0.0D) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.mContinue = (bool2 | bool1);
        this.mCache[i] = (this.mTempValues[i] * f1 + paramFloat);
      }
      paramMotionWidget.setInterpolatedValue(this.mConstraintAttributeList.valueAt(0), this.mCache);
      if (f2 != 0.0F) {
        this.mContinue = true;
      }
      return this.mContinue;
    }
    
    public void setup(int paramInt)
    {
      int m = this.mConstraintAttributeList.size();
      int k = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
      double[] arrayOfDouble1 = new double[m];
      this.mTempValues = new float[k + 2];
      this.mCache = new float[k];
      double[][] arrayOfDouble = new double[m][k + 2];
      for (int i = 0; i < m; i++)
      {
        int j = this.mConstraintAttributeList.keyAt(i);
        Object localObject = this.mConstraintAttributeList.valueAt(i);
        float[] arrayOfFloat = this.mWaveProperties.valueAt(i);
        arrayOfDouble1[i] = (j * 0.01D);
        ((CustomAttribute)localObject).getValuesToInterpolate(this.mTempValues);
        for (j = 0;; j++)
        {
          localObject = this.mTempValues;
          if (j >= localObject.length) {
            break;
          }
          arrayOfDouble[i][j] = localObject[j];
        }
        arrayOfDouble[i][k] = arrayOfFloat[0];
        arrayOfDouble[i][(k + 1)] = arrayOfFloat[1];
      }
      this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble1, arrayOfDouble);
    }
  }
  
  public static class CustomVarSet
    extends TimeCycleSplineSet
  {
    String mAttributeName = paramString.split(",")[1];
    float[] mCache;
    KeyFrameArray.CustomVar mConstraintAttributeList;
    float[] mTempValues;
    KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();
    
    public CustomVarSet(String paramString, KeyFrameArray.CustomVar paramCustomVar)
    {
      this.mConstraintAttributeList = paramCustomVar;
    }
    
    public void setPoint(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3)
    {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
    }
    
    public void setPoint(int paramInt1, CustomVariable paramCustomVariable, float paramFloat1, int paramInt2, float paramFloat2)
    {
      this.mConstraintAttributeList.append(paramInt1, paramCustomVariable);
      this.mWaveProperties.append(paramInt1, new float[] { paramFloat1, paramFloat2 });
      this.mWaveShape = Math.max(this.mWaveShape, paramInt2);
    }
    
    public boolean setProperty(MotionWidget paramMotionWidget, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      this.mCurveFit.getPos(paramFloat, this.mTempValues);
      float[] arrayOfFloat = this.mTempValues;
      paramFloat = arrayOfFloat[(arrayOfFloat.length - 2)];
      float f1 = arrayOfFloat[(arrayOfFloat.length - 1)];
      long l = this.last_time;
      if (Float.isNaN(this.last_cycle))
      {
        this.last_cycle = paramKeyCache.getFloatValue(paramMotionWidget, this.mAttributeName, 0);
        if (Float.isNaN(this.last_cycle)) {
          this.last_cycle = 0.0F;
        }
      }
      this.last_cycle = ((float)((this.last_cycle + (paramLong - l) * 1.0E-9D * paramFloat) % 1.0D));
      this.last_time = paramLong;
      float f2 = calcWave(this.last_cycle);
      this.mContinue = false;
      for (int i = 0; i < this.mCache.length; i++)
      {
        boolean bool2 = this.mContinue;
        boolean bool1;
        if (this.mTempValues[i] != 0.0D) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.mContinue = (bool2 | bool1);
        this.mCache[i] = (this.mTempValues[i] * f2 + f1);
      }
      this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(paramMotionWidget, this.mCache);
      if (paramFloat != 0.0F) {
        this.mContinue = true;
      }
      return this.mContinue;
    }
    
    public void setup(int paramInt)
    {
      int k = this.mConstraintAttributeList.size();
      int m = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
      double[] arrayOfDouble = new double[k];
      this.mTempValues = new float[m + 2];
      this.mCache = new float[m];
      double[][] arrayOfDouble1 = new double[k][m + 2];
      for (int i = 0; i < k; i++)
      {
        int j = this.mConstraintAttributeList.keyAt(i);
        Object localObject = this.mConstraintAttributeList.valueAt(i);
        float[] arrayOfFloat = this.mWaveProperties.valueAt(i);
        arrayOfDouble[i] = (j * 0.01D);
        ((CustomVariable)localObject).getValuesToInterpolate(this.mTempValues);
        for (j = 0;; j++)
        {
          localObject = this.mTempValues;
          if (j >= localObject.length) {
            break;
          }
          arrayOfDouble1[i][j] = localObject[j];
        }
        arrayOfDouble1[i][m] = arrayOfFloat[0];
        arrayOfDouble1[i][(m + 1)] = arrayOfFloat[1];
      }
      this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, arrayOfDouble1);
    }
  }
  
  protected static class Sort
  {
    static void doubleQuickSort(int[] paramArrayOfInt, float[][] paramArrayOfFloat, int paramInt1, int paramInt2)
    {
      int[] arrayOfInt = new int[paramArrayOfInt.length + 10];
      int i = 0 + 1;
      arrayOfInt[0] = paramInt2;
      paramInt2 = i + 1;
      arrayOfInt[i] = paramInt1;
      paramInt1 = paramInt2;
      while (paramInt1 > 0)
      {
        paramInt1--;
        int j = arrayOfInt[paramInt1];
        paramInt2 = paramInt1 - 1;
        i = arrayOfInt[paramInt2];
        paramInt1 = paramInt2;
        if (j < i)
        {
          int k = partition(paramArrayOfInt, paramArrayOfFloat, j, i);
          int m = paramInt2 + 1;
          arrayOfInt[paramInt2] = (k - 1);
          paramInt1 = m + 1;
          arrayOfInt[m] = j;
          paramInt2 = paramInt1 + 1;
          arrayOfInt[paramInt1] = i;
          paramInt1 = paramInt2 + 1;
          arrayOfInt[paramInt2] = (k + 1);
        }
      }
    }
    
    private static int partition(int[] paramArrayOfInt, float[][] paramArrayOfFloat, int paramInt1, int paramInt2)
    {
      int k = paramArrayOfInt[paramInt2];
      int i;
      for (int j = paramInt1; paramInt1 < paramInt2; j = i)
      {
        i = j;
        if (paramArrayOfInt[paramInt1] <= k)
        {
          swap(paramArrayOfInt, paramArrayOfFloat, j, paramInt1);
          i = j + 1;
        }
        paramInt1++;
      }
      swap(paramArrayOfInt, paramArrayOfFloat, j, paramInt2);
      return j;
    }
    
    private static void swap(int[] paramArrayOfInt, float[][] paramArrayOfFloat, int paramInt1, int paramInt2)
    {
      int i = paramArrayOfInt[paramInt1];
      paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
      paramArrayOfInt[paramInt2] = i;
      paramArrayOfInt = paramArrayOfFloat[paramInt1];
      paramArrayOfFloat[paramInt1] = paramArrayOfFloat[paramInt2];
      paramArrayOfFloat[paramInt2] = paramArrayOfInt;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/TimeCycleSplineSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */