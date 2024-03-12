package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Oscillator
{
  public static final int BOUNCE = 6;
  public static final int COS_WAVE = 5;
  public static final int CUSTOM = 7;
  public static final int REVERSE_SAW_WAVE = 4;
  public static final int SAW_WAVE = 3;
  public static final int SIN_WAVE = 0;
  public static final int SQUARE_WAVE = 1;
  public static String TAG = "Oscillator";
  public static final int TRIANGLE_WAVE = 2;
  double PI2 = 6.283185307179586D;
  double[] mArea;
  MonotonicCurveFit mCustomCurve;
  String mCustomType;
  private boolean mNormalized = false;
  float[] mPeriod = new float[0];
  double[] mPosition = new double[0];
  int mType;
  
  public void addPoint(double paramDouble, float paramFloat)
  {
    int k = this.mPeriod.length + 1;
    int j = Arrays.binarySearch(this.mPosition, paramDouble);
    int i = j;
    if (j < 0) {
      i = -j - 1;
    }
    this.mPosition = Arrays.copyOf(this.mPosition, k);
    this.mPeriod = Arrays.copyOf(this.mPeriod, k);
    this.mArea = new double[k];
    double[] arrayOfDouble = this.mPosition;
    System.arraycopy(arrayOfDouble, i, arrayOfDouble, i + 1, k - i - 1);
    this.mPosition[i] = paramDouble;
    this.mPeriod[i] = paramFloat;
    this.mNormalized = false;
  }
  
  double getDP(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      paramDouble = 1.0E-5D;
    } else if (paramDouble >= 1.0D) {
      paramDouble = 0.999999D;
    }
    int i = Arrays.binarySearch(this.mPosition, paramDouble);
    double d = 0.0D;
    if (i > 0) {
      return 0.0D;
    }
    if (i != 0)
    {
      i = -i - 1;
      float[] arrayOfFloat = this.mPeriod;
      d = arrayOfFloat[i] - arrayOfFloat[(i - 1)];
      double[] arrayOfDouble = this.mPosition;
      d /= (arrayOfDouble[i] - arrayOfDouble[(i - 1)]);
      d = d * paramDouble + (arrayOfFloat[(i - 1)] - arrayOfDouble[(i - 1)] * d);
    }
    return d;
  }
  
  double getP(double paramDouble)
  {
    if (paramDouble < 0.0D) {
      paramDouble = 0.0D;
    } else if (paramDouble > 1.0D) {
      paramDouble = 1.0D;
    }
    int i = Arrays.binarySearch(this.mPosition, paramDouble);
    double d = 0.0D;
    if (i > 0)
    {
      paramDouble = 1.0D;
    }
    else if (i != 0)
    {
      i = -i - 1;
      float[] arrayOfFloat = this.mPeriod;
      d = arrayOfFloat[i] - arrayOfFloat[(i - 1)];
      double[] arrayOfDouble = this.mPosition;
      d /= (arrayOfDouble[i] - arrayOfDouble[(i - 1)]);
      paramDouble = this.mArea[(i - 1)] + (arrayOfFloat[(i - 1)] - arrayOfDouble[(i - 1)] * d) * (paramDouble - arrayOfDouble[(i - 1)]) + (paramDouble * paramDouble - arrayOfDouble[(i - 1)] * arrayOfDouble[(i - 1)]) * d / 2.0D;
    }
    else
    {
      paramDouble = d;
    }
    return paramDouble;
  }
  
  public double getSlope(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    paramDouble2 += getP(paramDouble1);
    paramDouble1 = getDP(paramDouble1) + paramDouble3;
    switch (this.mType)
    {
    default: 
      paramDouble3 = this.PI2;
      return paramDouble3 * paramDouble1 * Math.cos(paramDouble3 * paramDouble2);
    case 7: 
      return this.mCustomCurve.getSlope(paramDouble2 % 1.0D, 0);
    case 6: 
      return paramDouble1 * 4.0D * ((paramDouble2 * 4.0D + 2.0D) % 4.0D - 2.0D);
    case 5: 
      paramDouble3 = this.PI2;
      return -paramDouble3 * paramDouble1 * Math.sin(paramDouble3 * paramDouble2);
    case 4: 
      return -paramDouble1 * 2.0D;
    case 3: 
      return 2.0D * paramDouble1;
    case 2: 
      return paramDouble1 * 4.0D * Math.signum((paramDouble2 * 4.0D + 3.0D) % 4.0D - 2.0D);
    }
    return 0.0D;
  }
  
  public double getValue(double paramDouble1, double paramDouble2)
  {
    paramDouble1 = getP(paramDouble1) + paramDouble2;
    switch (this.mType)
    {
    default: 
      return Math.sin(this.PI2 * paramDouble1);
    case 7: 
      return this.mCustomCurve.getPos(paramDouble1 % 1.0D, 0);
    case 6: 
      paramDouble1 = 1.0D - Math.abs(paramDouble1 * 4.0D % 4.0D - 2.0D);
      return 1.0D - paramDouble1 * paramDouble1;
    case 5: 
      return Math.cos(this.PI2 * (paramDouble2 + paramDouble1));
    case 4: 
      return 1.0D - (paramDouble1 * 2.0D + 1.0D) % 2.0D;
    case 3: 
      return (paramDouble1 * 2.0D + 1.0D) % 2.0D - 1.0D;
    case 2: 
      return 1.0D - Math.abs((paramDouble1 * 4.0D + 1.0D) % 4.0D - 2.0D);
    }
    return Math.signum(0.5D - paramDouble1 % 1.0D);
  }
  
  public void normalize()
  {
    double d2 = 0.0D;
    double d1 = 0.0D;
    Object localObject;
    for (int i = 0;; i++)
    {
      localObject = this.mPeriod;
      if (i >= localObject.length) {
        break;
      }
      d1 += localObject[i];
    }
    float f;
    for (i = 1;; i++)
    {
      localObject = this.mPeriod;
      if (i >= localObject.length) {
        break;
      }
      f = (localObject[(i - 1)] + localObject[i]) / 2.0F;
      localObject = this.mPosition;
      double d4 = localObject[i];
      double d3 = localObject[(i - 1)];
      d2 += f * (d4 - d3);
    }
    for (i = 0;; i++)
    {
      localObject = this.mPeriod;
      if (i >= localObject.length) {
        break;
      }
      localObject[i] = ((float)(localObject[i] * (d1 / d2)));
    }
    this.mArea[0] = 0.0D;
    for (i = 1;; i++)
    {
      localObject = this.mPeriod;
      if (i >= localObject.length) {
        break;
      }
      f = (localObject[(i - 1)] + localObject[i]) / 2.0F;
      localObject = this.mPosition;
      d1 = localObject[i];
      d2 = localObject[(i - 1)];
      localObject = this.mArea;
      localObject[i] = (localObject[(i - 1)] + f * (d1 - d2));
    }
    this.mNormalized = true;
  }
  
  public void setType(int paramInt, String paramString)
  {
    this.mType = paramInt;
    this.mCustomType = paramString;
    if (paramString != null) {
      this.mCustomCurve = MonotonicCurveFit.buildWave(paramString);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("pos =");
    String str = Arrays.toString(this.mPosition);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append(" period=");
    str = Arrays.toString(this.mPeriod);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/Oscillator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */