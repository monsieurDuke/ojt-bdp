package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.state.WidgetFrame;
import java.text.DecimalFormat;
import java.util.Arrays;

public abstract class SplineSet
{
  private static final String TAG = "SplineSet";
  private int count;
  protected CurveFit mCurveFit;
  protected int[] mTimePoints = new int[10];
  private String mType;
  protected float[] mValues = new float[10];
  
  public static SplineSet makeCustomSpline(String paramString, KeyFrameArray.CustomArray paramCustomArray)
  {
    return new CustomSet(paramString, paramCustomArray);
  }
  
  public static SplineSet makeCustomSplineSet(String paramString, KeyFrameArray.CustomVar paramCustomVar)
  {
    return new CustomSpline(paramString, paramCustomVar);
  }
  
  public static SplineSet makeSpline(String paramString, long paramLong)
  {
    return new CoreSpline(paramString, paramLong);
  }
  
  public float get(float paramFloat)
  {
    return (float)this.mCurveFit.getPos(paramFloat, 0);
  }
  
  public CurveFit getCurveFit()
  {
    return this.mCurveFit;
  }
  
  public float getSlope(float paramFloat)
  {
    return (float)this.mCurveFit.getSlope(paramFloat, 0);
  }
  
  public void setPoint(int paramInt, float paramFloat)
  {
    Object localObject = this.mTimePoints;
    if (localObject.length < this.count + 1)
    {
      this.mTimePoints = Arrays.copyOf((int[])localObject, localObject.length * 2);
      localObject = this.mValues;
      this.mValues = Arrays.copyOf((float[])localObject, localObject.length * 2);
    }
    localObject = this.mTimePoints;
    int i = this.count;
    localObject[i] = paramInt;
    this.mValues[i] = paramFloat;
    this.count = (i + 1);
  }
  
  public void setProperty(TypedValues paramTypedValues, float paramFloat)
  {
    paramTypedValues.setValue(TypedValues.AttributesType.getId(this.mType), get(paramFloat));
  }
  
  public void setType(String paramString)
  {
    this.mType = paramString;
  }
  
  public void setup(int paramInt)
  {
    int i = this.count;
    if (i == 0) {
      return;
    }
    Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, i - 1);
    int j = 1;
    i = 1;
    while (i < this.count)
    {
      localObject = this.mTimePoints;
      int k = j;
      if (localObject[(i - 1)] != localObject[i]) {
        k = j + 1;
      }
      i++;
      j = k;
    }
    double[] arrayOfDouble = new double[j];
    Object localObject = new double[j][1];
    j = 0;
    for (i = 0; i < this.count; i++) {
      if (i > 0)
      {
        int[] arrayOfInt = this.mTimePoints;
        if (arrayOfInt[i] == arrayOfInt[(i - 1)]) {}
      }
      else
      {
        arrayOfDouble[j] = (this.mTimePoints[i] * 0.01D);
        localObject[j][0] = this.mValues[i];
        j++;
      }
    }
    this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, (double[][])localObject);
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
  
  private static class CoreSpline
    extends SplineSet
  {
    long start;
    String type;
    
    public CoreSpline(String paramString, long paramLong)
    {
      this.type = paramString;
      this.start = paramLong;
    }
    
    public void setProperty(TypedValues paramTypedValues, float paramFloat)
    {
      paramTypedValues.setValue(paramTypedValues.getId(this.type), get(paramFloat));
    }
  }
  
  public static class CustomSet
    extends SplineSet
  {
    String mAttributeName = paramString.split(",")[1];
    KeyFrameArray.CustomArray mConstraintAttributeList;
    float[] mTempValues;
    
    public CustomSet(String paramString, KeyFrameArray.CustomArray paramCustomArray)
    {
      this.mConstraintAttributeList = paramCustomArray;
    }
    
    public void setPoint(int paramInt, float paramFloat)
    {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
    }
    
    public void setPoint(int paramInt, CustomAttribute paramCustomAttribute)
    {
      this.mConstraintAttributeList.append(paramInt, paramCustomAttribute);
    }
    
    public void setProperty(WidgetFrame paramWidgetFrame, float paramFloat)
    {
      this.mCurveFit.getPos(paramFloat, this.mTempValues);
      paramWidgetFrame.setCustomValue(this.mConstraintAttributeList.valueAt(0), this.mTempValues);
    }
    
    public void setup(int paramInt)
    {
      int k = this.mConstraintAttributeList.size();
      int i = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
      double[] arrayOfDouble1 = new double[k];
      this.mTempValues = new float[i];
      double[][] arrayOfDouble = new double[k][i];
      for (i = 0; i < k; i++)
      {
        int j = this.mConstraintAttributeList.keyAt(i);
        Object localObject = this.mConstraintAttributeList.valueAt(i);
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
      }
      this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble1, arrayOfDouble);
    }
  }
  
  public static class CustomSpline
    extends SplineSet
  {
    String mAttributeName = paramString.split(",")[1];
    KeyFrameArray.CustomVar mConstraintAttributeList;
    float[] mTempValues;
    
    public CustomSpline(String paramString, KeyFrameArray.CustomVar paramCustomVar)
    {
      this.mConstraintAttributeList = paramCustomVar;
    }
    
    public void setPoint(int paramInt, float paramFloat)
    {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
    }
    
    public void setPoint(int paramInt, CustomVariable paramCustomVariable)
    {
      this.mConstraintAttributeList.append(paramInt, paramCustomVariable);
    }
    
    public void setProperty(MotionWidget paramMotionWidget, float paramFloat)
    {
      this.mCurveFit.getPos(paramFloat, this.mTempValues);
      this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(paramMotionWidget, this.mTempValues);
    }
    
    public void setProperty(TypedValues paramTypedValues, float paramFloat)
    {
      setProperty((MotionWidget)paramTypedValues, paramFloat);
    }
    
    public void setup(int paramInt)
    {
      int k = this.mConstraintAttributeList.size();
      int i = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
      double[] arrayOfDouble = new double[k];
      this.mTempValues = new float[i];
      double[][] arrayOfDouble1 = new double[k][i];
      for (i = 0; i < k; i++)
      {
        int j = this.mConstraintAttributeList.keyAt(i);
        Object localObject = this.mConstraintAttributeList.valueAt(i);
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
      }
      this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, arrayOfDouble1);
    }
  }
  
  private static class Sort
  {
    static void doubleQuickSort(int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt1, int paramInt2)
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
        int k = arrayOfInt[paramInt2];
        paramInt1 = paramInt2;
        if (j < k)
        {
          i = partition(paramArrayOfInt, paramArrayOfFloat, j, k);
          paramInt1 = paramInt2 + 1;
          arrayOfInt[paramInt2] = (i - 1);
          paramInt2 = paramInt1 + 1;
          arrayOfInt[paramInt1] = j;
          j = paramInt2 + 1;
          arrayOfInt[paramInt2] = k;
          paramInt1 = j + 1;
          arrayOfInt[j] = (i + 1);
        }
      }
    }
    
    private static int partition(int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt1, int paramInt2)
    {
      int k = paramArrayOfInt[paramInt2];
      int i = paramInt1;
      for (int j = i; paramInt1 < paramInt2; j = i)
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
    
    private static void swap(int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt1, int paramInt2)
    {
      int i = paramArrayOfInt[paramInt1];
      paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
      paramArrayOfInt[paramInt2] = i;
      float f = paramArrayOfFloat[paramInt1];
      paramArrayOfFloat[paramInt1] = paramArrayOfFloat[paramInt2];
      paramArrayOfFloat[paramInt2] = f;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/SplineSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */