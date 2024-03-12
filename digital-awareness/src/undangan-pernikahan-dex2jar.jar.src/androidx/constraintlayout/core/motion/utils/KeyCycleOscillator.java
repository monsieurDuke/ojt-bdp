package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.MotionWidget;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public abstract class KeyCycleOscillator
{
  private static final String TAG = "KeyCycleOscillator";
  private CurveFit mCurveFit;
  private CycleOscillator mCycleOscillator;
  private String mType;
  public int mVariesBy = 0;
  ArrayList<WavePoint> mWavePoints = new ArrayList();
  private int mWaveShape = 0;
  private String mWaveString = null;
  
  public static KeyCycleOscillator makeWidgetCycle(String paramString)
  {
    if (paramString.equals("pathRotate")) {
      return new PathRotateSet(paramString);
    }
    return new CoreSpline(paramString);
  }
  
  public float get(float paramFloat)
  {
    return (float)this.mCycleOscillator.getValues(paramFloat);
  }
  
  public CurveFit getCurveFit()
  {
    return this.mCurveFit;
  }
  
  public float getSlope(float paramFloat)
  {
    return (float)this.mCycleOscillator.getSlope(paramFloat);
  }
  
  protected void setCustom(Object paramObject) {}
  
  public void setPoint(int paramInt1, int paramInt2, String paramString, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.mWavePoints.add(new WavePoint(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4));
    if (paramInt3 != -1) {
      this.mVariesBy = paramInt3;
    }
    this.mWaveShape = paramInt2;
    this.mWaveString = paramString;
  }
  
  public void setPoint(int paramInt1, int paramInt2, String paramString, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Object paramObject)
  {
    this.mWavePoints.add(new WavePoint(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4));
    if (paramInt3 != -1) {
      this.mVariesBy = paramInt3;
    }
    this.mWaveShape = paramInt2;
    setCustom(paramObject);
    this.mWaveString = paramString;
  }
  
  public void setProperty(MotionWidget paramMotionWidget, float paramFloat) {}
  
  public void setType(String paramString)
  {
    this.mType = paramString;
  }
  
  public void setup(float paramFloat)
  {
    int i = this.mWavePoints.size();
    if (i == 0) {
      return;
    }
    Collections.sort(this.mWavePoints, new Comparator()
    {
      public int compare(KeyCycleOscillator.WavePoint paramAnonymousWavePoint1, KeyCycleOscillator.WavePoint paramAnonymousWavePoint2)
      {
        return Integer.compare(paramAnonymousWavePoint1.mPosition, paramAnonymousWavePoint2.mPosition);
      }
    });
    double[] arrayOfDouble = new double[i];
    double[][] arrayOfDouble1 = new double[i][3];
    this.mCycleOscillator = new CycleOscillator(this.mWaveShape, this.mWaveString, this.mVariesBy, i);
    i = 0;
    Iterator localIterator = this.mWavePoints.iterator();
    while (localIterator.hasNext())
    {
      WavePoint localWavePoint = (WavePoint)localIterator.next();
      arrayOfDouble[i] = (localWavePoint.mPeriod * 0.01D);
      arrayOfDouble1[i][0] = localWavePoint.mValue;
      arrayOfDouble1[i][1] = localWavePoint.mOffset;
      arrayOfDouble1[i][2] = localWavePoint.mPhase;
      this.mCycleOscillator.setPoint(i, localWavePoint.mPosition, localWavePoint.mPeriod, localWavePoint.mOffset, localWavePoint.mPhase, localWavePoint.mValue);
      i++;
    }
    this.mCycleOscillator.setup(paramFloat);
    this.mCurveFit = CurveFit.get(0, arrayOfDouble, arrayOfDouble1);
  }
  
  public String toString()
  {
    String str = this.mType;
    DecimalFormat localDecimalFormat = new DecimalFormat("##.##");
    Iterator localIterator = this.mWavePoints.iterator();
    while (localIterator.hasNext())
    {
      WavePoint localWavePoint = (WavePoint)localIterator.next();
      str = str + "[" + localWavePoint.mPosition + " , " + localDecimalFormat.format(localWavePoint.mValue) + "] ";
    }
    return str;
  }
  
  public boolean variesByPath()
  {
    int i = this.mVariesBy;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private static class CoreSpline
    extends KeyCycleOscillator
  {
    String type;
    int typeId;
    
    public CoreSpline(String paramString)
    {
      this.type = paramString;
      this.typeId = TypedValues.CycleType.getId(paramString);
    }
    
    public void setProperty(MotionWidget paramMotionWidget, float paramFloat)
    {
      paramMotionWidget.setValue(this.typeId, get(paramFloat));
    }
  }
  
  static class CycleOscillator
  {
    private static final String TAG = "CycleOscillator";
    static final int UNSET = -1;
    private final int OFFST;
    private final int PHASE;
    private final int VALUE;
    CurveFit mCurveFit;
    float[] mOffset;
    Oscillator mOscillator;
    float mPathLength;
    float[] mPeriod;
    float[] mPhase;
    double[] mPosition;
    float[] mScale;
    double[] mSplineSlopeCache;
    double[] mSplineValueCache;
    float[] mValues;
    private final int mVariesBy;
    int mWaveShape;
    
    CycleOscillator(int paramInt1, String paramString, int paramInt2, int paramInt3)
    {
      Oscillator localOscillator = new Oscillator();
      this.mOscillator = localOscillator;
      this.OFFST = 0;
      this.PHASE = 1;
      this.VALUE = 2;
      this.mWaveShape = paramInt1;
      this.mVariesBy = paramInt2;
      localOscillator.setType(paramInt1, paramString);
      this.mValues = new float[paramInt3];
      this.mPosition = new double[paramInt3];
      this.mPeriod = new float[paramInt3];
      this.mOffset = new float[paramInt3];
      this.mPhase = new float[paramInt3];
      this.mScale = new float[paramInt3];
    }
    
    public double getLastPhase()
    {
      return this.mSplineValueCache[1];
    }
    
    public double getSlope(float paramFloat)
    {
      Object localObject = this.mCurveFit;
      if (localObject != null)
      {
        ((CurveFit)localObject).getSlope(paramFloat, this.mSplineSlopeCache);
        this.mCurveFit.getPos(paramFloat, this.mSplineValueCache);
      }
      else
      {
        localObject = this.mSplineSlopeCache;
        localObject[0] = 0.0D;
        localObject[1] = 0.0D;
        localObject[2] = 0.0D;
      }
      double d1 = this.mOscillator.getValue(paramFloat, this.mSplineValueCache[1]);
      double d2 = this.mOscillator.getSlope(paramFloat, this.mSplineValueCache[1], this.mSplineSlopeCache[1]);
      localObject = this.mSplineSlopeCache;
      return localObject[0] + localObject[2] * d1 + this.mSplineValueCache[2] * d2;
    }
    
    public double getValues(float paramFloat)
    {
      Object localObject = this.mCurveFit;
      if (localObject != null)
      {
        ((CurveFit)localObject).getPos(paramFloat, this.mSplineValueCache);
      }
      else
      {
        localObject = this.mSplineValueCache;
        localObject[0] = this.mOffset[0];
        localObject[1] = this.mPhase[0];
        localObject[2] = this.mValues[0];
      }
      localObject = this.mSplineValueCache;
      double d1 = localObject[0];
      double d2 = localObject[1];
      d2 = this.mOscillator.getValue(paramFloat, d2);
      return this.mSplineValueCache[2] * d2 + d1;
    }
    
    public void setPoint(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.mPosition[paramInt1] = (paramInt2 / 100.0D);
      this.mPeriod[paramInt1] = paramFloat1;
      this.mOffset[paramInt1] = paramFloat2;
      this.mPhase[paramInt1] = paramFloat3;
      this.mValues[paramInt1] = paramFloat4;
    }
    
    public void setup(float paramFloat)
    {
      this.mPathLength = paramFloat;
      int i = this.mPosition.length;
      double[][] arrayOfDouble = new double[i][3];
      Object localObject = this.mValues;
      this.mSplineValueCache = new double[localObject.length + 2];
      this.mSplineSlopeCache = new double[localObject.length + 2];
      if (this.mPosition[0] > 0.0D) {
        this.mOscillator.addPoint(0.0D, this.mPeriod[0]);
      }
      localObject = this.mPosition;
      i = localObject.length - 1;
      if (localObject[i] < 1.0D) {
        this.mOscillator.addPoint(1.0D, this.mPeriod[i]);
      }
      for (i = 0; i < arrayOfDouble.length; i++)
      {
        arrayOfDouble[i][0] = this.mOffset[i];
        arrayOfDouble[i][1] = this.mPhase[i];
        arrayOfDouble[i][2] = this.mValues[i];
        this.mOscillator.addPoint(this.mPosition[i], this.mPeriod[i]);
      }
      this.mOscillator.normalize();
      localObject = this.mPosition;
      if (localObject.length > 1) {
        this.mCurveFit = CurveFit.get(0, (double[])localObject, arrayOfDouble);
      } else {
        this.mCurveFit = null;
      }
    }
  }
  
  private static class IntDoubleSort
  {
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
    
    static void sort(int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt1, int paramInt2)
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
        int k = arrayOfInt[paramInt1];
        paramInt2 = paramInt1 - 1;
        i = arrayOfInt[paramInt2];
        paramInt1 = paramInt2;
        if (k < i)
        {
          int j = partition(paramArrayOfInt, paramArrayOfFloat, k, i);
          paramInt1 = paramInt2 + 1;
          arrayOfInt[paramInt2] = (j - 1);
          paramInt2 = paramInt1 + 1;
          arrayOfInt[paramInt1] = k;
          k = paramInt2 + 1;
          arrayOfInt[paramInt2] = i;
          paramInt1 = k + 1;
          arrayOfInt[k] = (j + 1);
        }
      }
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
  
  private static class IntFloatFloatSort
  {
    private static int partition(int[] paramArrayOfInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2)
    {
      int k = paramArrayOfInt[paramInt2];
      int i = paramInt1;
      for (int j = i; paramInt1 < paramInt2; j = i)
      {
        i = j;
        if (paramArrayOfInt[paramInt1] <= k)
        {
          swap(paramArrayOfInt, paramArrayOfFloat1, paramArrayOfFloat2, j, paramInt1);
          i = j + 1;
        }
        paramInt1++;
      }
      swap(paramArrayOfInt, paramArrayOfFloat1, paramArrayOfFloat2, j, paramInt2);
      return j;
    }
    
    static void sort(int[] paramArrayOfInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2)
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
        i = arrayOfInt[paramInt1];
        paramInt2 = paramInt1 - 1;
        int k = arrayOfInt[paramInt2];
        paramInt1 = paramInt2;
        if (i < k)
        {
          int j = partition(paramArrayOfInt, paramArrayOfFloat1, paramArrayOfFloat2, i, k);
          paramInt1 = paramInt2 + 1;
          arrayOfInt[paramInt2] = (j - 1);
          paramInt2 = paramInt1 + 1;
          arrayOfInt[paramInt1] = i;
          i = paramInt2 + 1;
          arrayOfInt[paramInt2] = k;
          paramInt1 = i + 1;
          arrayOfInt[i] = (j + 1);
        }
      }
    }
    
    private static void swap(int[] paramArrayOfInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2)
    {
      int i = paramArrayOfInt[paramInt1];
      paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
      paramArrayOfInt[paramInt2] = i;
      float f = paramArrayOfFloat1[paramInt1];
      paramArrayOfFloat1[paramInt1] = paramArrayOfFloat1[paramInt2];
      paramArrayOfFloat1[paramInt2] = f;
      f = paramArrayOfFloat2[paramInt1];
      paramArrayOfFloat2[paramInt1] = paramArrayOfFloat2[paramInt2];
      paramArrayOfFloat2[paramInt2] = f;
    }
  }
  
  public static class PathRotateSet
    extends KeyCycleOscillator
  {
    String type;
    int typeId;
    
    public PathRotateSet(String paramString)
    {
      this.type = paramString;
      this.typeId = TypedValues.CycleType.getId(paramString);
    }
    
    public void setPathRotate(MotionWidget paramMotionWidget, float paramFloat, double paramDouble1, double paramDouble2)
    {
      paramMotionWidget.setRotationZ(get(paramFloat) + (float)Math.toDegrees(Math.atan2(paramDouble2, paramDouble1)));
    }
    
    public void setProperty(MotionWidget paramMotionWidget, float paramFloat)
    {
      paramMotionWidget.setValue(this.typeId, get(paramFloat));
    }
  }
  
  static class WavePoint
  {
    float mOffset;
    float mPeriod;
    float mPhase;
    int mPosition;
    float mValue;
    
    public WavePoint(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.mPosition = paramInt;
      this.mValue = paramFloat4;
      this.mOffset = paramFloat2;
      this.mPeriod = paramFloat1;
      this.mPhase = paramFloat3;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/KeyCycleOscillator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */