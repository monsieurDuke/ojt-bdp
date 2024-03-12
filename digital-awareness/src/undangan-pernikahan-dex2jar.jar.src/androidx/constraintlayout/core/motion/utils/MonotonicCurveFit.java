package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

public class MonotonicCurveFit
  extends CurveFit
{
  private static final String TAG = "MonotonicCurveFit";
  private boolean mExtrapolate = true;
  double[] mSlopeTemp;
  private double[] mT;
  private double[][] mTangent;
  private double[][] mY;
  
  public MonotonicCurveFit(double[] paramArrayOfDouble, double[][] paramArrayOfDouble1)
  {
    int m = paramArrayOfDouble.length;
    int k = paramArrayOfDouble1[0].length;
    this.mSlopeTemp = new double[k];
    double[][] arrayOfDouble2 = new double[m - 1][k];
    double[][] arrayOfDouble1 = new double[m][k];
    int j;
    double d2;
    double d1;
    for (int i = 0; i < k; i++)
    {
      for (j = 0; j < m - 1; j++)
      {
        d2 = paramArrayOfDouble[(j + 1)];
        d1 = paramArrayOfDouble[j];
        arrayOfDouble2[j][i] = ((paramArrayOfDouble1[(j + 1)][i] - paramArrayOfDouble1[j][i]) / (d2 - d1));
        if (j == 0) {
          arrayOfDouble1[j][i] = arrayOfDouble2[j][i];
        } else {
          arrayOfDouble1[j][i] = ((arrayOfDouble2[(j - 1)][i] + arrayOfDouble2[j][i]) * 0.5D);
        }
      }
      arrayOfDouble1[(m - 1)][i] = arrayOfDouble2[(m - 2)][i];
    }
    for (i = 0; i < m - 1; i++) {
      for (j = 0; j < k; j++) {
        if (arrayOfDouble2[i][j] == 0.0D)
        {
          arrayOfDouble1[i][j] = 0.0D;
          arrayOfDouble1[(i + 1)][j] = 0.0D;
        }
        else
        {
          d2 = arrayOfDouble1[i][j] / arrayOfDouble2[i][j];
          d1 = arrayOfDouble1[(i + 1)][j] / arrayOfDouble2[i][j];
          double d3 = Math.hypot(d2, d1);
          if (d3 > 9.0D)
          {
            d3 = 3.0D / d3;
            arrayOfDouble1[i][j] = (d3 * d2 * arrayOfDouble2[i][j]);
            arrayOfDouble1[(i + 1)][j] = (d3 * d1 * arrayOfDouble2[i][j]);
          }
        }
      }
    }
    this.mT = paramArrayOfDouble;
    this.mY = paramArrayOfDouble1;
    this.mTangent = arrayOfDouble1;
  }
  
  public static MonotonicCurveFit buildWave(String paramString)
  {
    double[] arrayOfDouble = new double[paramString.length() / 2];
    int j = paramString.indexOf('(') + 1;
    int k = paramString.indexOf(',', j);
    for (int i = 0; k != -1; i++)
    {
      arrayOfDouble[i] = Double.parseDouble(paramString.substring(j, k).trim());
      k++;
      j = k;
      k = paramString.indexOf(',', k);
    }
    arrayOfDouble[i] = Double.parseDouble(paramString.substring(j, paramString.indexOf(41, j)).trim());
    return buildWave(Arrays.copyOf(arrayOfDouble, i + 1));
  }
  
  private static MonotonicCurveFit buildWave(double[] paramArrayOfDouble)
  {
    int i = paramArrayOfDouble.length * 3 - 2;
    int j = paramArrayOfDouble.length - 1;
    double d1 = 1.0D / j;
    double[][] arrayOfDouble1 = new double[i][1];
    double[] arrayOfDouble = new double[i];
    for (i = 0; i < paramArrayOfDouble.length; i++)
    {
      double d2 = paramArrayOfDouble[i];
      arrayOfDouble1[(i + j)][0] = d2;
      arrayOfDouble[(i + j)] = (i * d1);
      if (i > 0)
      {
        arrayOfDouble1[(j * 2 + i)][0] = (d2 + 1.0D);
        arrayOfDouble[(j * 2 + i)] = (i * d1 + 1.0D);
        arrayOfDouble1[(i - 1)][0] = (d2 - 1.0D - d1);
        arrayOfDouble[(i - 1)] = (i * d1 - 1.0D - d1);
      }
    }
    return new MonotonicCurveFit(arrayOfDouble, arrayOfDouble1);
  }
  
  private static double diff(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
  {
    double d = paramDouble2 * paramDouble2;
    return -6.0D * d * paramDouble4 + paramDouble2 * 6.0D * paramDouble4 + d * 6.0D * paramDouble3 - 6.0D * paramDouble2 * paramDouble3 + paramDouble1 * 3.0D * paramDouble6 * d + 3.0D * paramDouble1 * paramDouble5 * d - 2.0D * paramDouble1 * paramDouble6 * paramDouble2 - 4.0D * paramDouble1 * paramDouble5 * paramDouble2 + paramDouble1 * paramDouble5;
  }
  
  private static double interpolate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
  {
    double d1 = paramDouble2 * paramDouble2;
    double d2 = d1 * paramDouble2;
    return -2.0D * d2 * paramDouble4 + d1 * 3.0D * paramDouble4 + d2 * 2.0D * paramDouble3 - 3.0D * d1 * paramDouble3 + paramDouble3 + paramDouble1 * paramDouble6 * d2 + paramDouble1 * paramDouble5 * d2 - paramDouble1 * paramDouble6 * d1 - paramDouble1 * 2.0D * paramDouble5 * d1 + paramDouble1 * paramDouble5 * paramDouble2;
  }
  
  public double getPos(double paramDouble, int paramInt)
  {
    Object localObject = this.mT;
    int j = localObject.length;
    double d1;
    if (this.mExtrapolate)
    {
      d1 = localObject[0];
      if (paramDouble <= d1) {
        return this.mY[0][paramInt] + (paramDouble - d1) * getSlope(d1, paramInt);
      }
      if (paramDouble >= localObject[(j - 1)]) {
        return this.mY[(j - 1)][paramInt] + (paramDouble - localObject[(j - 1)]) * getSlope(localObject[(j - 1)], paramInt);
      }
    }
    else
    {
      if (paramDouble <= localObject[0]) {
        return this.mY[0][paramInt];
      }
      if (paramDouble >= localObject[(j - 1)]) {
        return this.mY[(j - 1)][paramInt];
      }
    }
    for (int i = 0; i < j - 1; i++)
    {
      localObject = this.mT;
      double d2 = localObject[i];
      if (paramDouble == d2) {
        return this.mY[i][paramInt];
      }
      if (paramDouble < localObject[(i + 1)])
      {
        d1 = localObject[(i + 1)] - d2;
        paramDouble = (paramDouble - d2) / d1;
        localObject = this.mY;
        double d3 = localObject[i][paramInt];
        d2 = localObject[(i + 1)][paramInt];
        localObject = this.mTangent;
        return interpolate(d1, paramDouble, d3, d2, localObject[i][paramInt], localObject[(i + 1)][paramInt]);
      }
    }
    return 0.0D;
  }
  
  public void getPos(double paramDouble, double[] paramArrayOfDouble)
  {
    Object localObject = this.mT;
    int m = localObject.length;
    int k = this.mY[0].length;
    double d1;
    if (this.mExtrapolate)
    {
      d1 = localObject[0];
      if (paramDouble <= d1)
      {
        getSlope(d1, this.mSlopeTemp);
        for (i = 0; i < k; i++) {
          paramArrayOfDouble[i] = (this.mY[0][i] + (paramDouble - this.mT[0]) * this.mSlopeTemp[i]);
        }
        return;
      }
      if (paramDouble >= localObject[(m - 1)])
      {
        getSlope(localObject[(m - 1)], this.mSlopeTemp);
        for (i = 0; i < k; i++) {
          paramArrayOfDouble[i] = (this.mY[(m - 1)][i] + (paramDouble - this.mT[(m - 1)]) * this.mSlopeTemp[i]);
        }
      }
    }
    else
    {
      if (paramDouble <= localObject[0])
      {
        for (i = 0; i < k; i++) {
          paramArrayOfDouble[i] = this.mY[0][i];
        }
        return;
      }
      if (paramDouble >= localObject[(m - 1)])
      {
        for (i = 0; i < k; i++) {
          paramArrayOfDouble[i] = this.mY[(m - 1)][i];
        }
        return;
      }
    }
    for (int i = 0; i < m - 1; i++)
    {
      int j;
      if (paramDouble == this.mT[i]) {
        for (j = 0; j < k; j++) {
          paramArrayOfDouble[j] = this.mY[i][j];
        }
      }
      localObject = this.mT;
      if (paramDouble < localObject[(i + 1)])
      {
        d1 = localObject[(i + 1)];
        double d2 = localObject[i];
        d1 -= d2;
        double d3 = (paramDouble - d2) / d1;
        for (j = 0; j < k; j++)
        {
          localObject = this.mY;
          d2 = localObject[i][j];
          paramDouble = localObject[(i + 1)][j];
          localObject = this.mTangent;
          paramArrayOfDouble[j] = interpolate(d1, d3, d2, paramDouble, localObject[i][j], localObject[(i + 1)][j]);
        }
        return;
      }
    }
  }
  
  public void getPos(double paramDouble, float[] paramArrayOfFloat)
  {
    Object localObject = this.mT;
    int m = localObject.length;
    int k = this.mY[0].length;
    double d1;
    if (this.mExtrapolate)
    {
      d1 = localObject[0];
      if (paramDouble <= d1)
      {
        getSlope(d1, this.mSlopeTemp);
        for (i = 0; i < k; i++) {
          paramArrayOfFloat[i] = ((float)(this.mY[0][i] + (paramDouble - this.mT[0]) * this.mSlopeTemp[i]));
        }
        return;
      }
      if (paramDouble >= localObject[(m - 1)])
      {
        getSlope(localObject[(m - 1)], this.mSlopeTemp);
        for (i = 0; i < k; i++) {
          paramArrayOfFloat[i] = ((float)(this.mY[(m - 1)][i] + (paramDouble - this.mT[(m - 1)]) * this.mSlopeTemp[i]));
        }
      }
    }
    else
    {
      if (paramDouble <= localObject[0])
      {
        for (i = 0; i < k; i++) {
          paramArrayOfFloat[i] = ((float)this.mY[0][i]);
        }
        return;
      }
      if (paramDouble >= localObject[(m - 1)])
      {
        for (i = 0; i < k; i++) {
          paramArrayOfFloat[i] = ((float)this.mY[(m - 1)][i]);
        }
        return;
      }
    }
    for (int i = 0; i < m - 1; i++)
    {
      int j;
      if (paramDouble == this.mT[i]) {
        for (j = 0; j < k; j++) {
          paramArrayOfFloat[j] = ((float)this.mY[i][j]);
        }
      }
      localObject = this.mT;
      if (paramDouble < localObject[(i + 1)])
      {
        d1 = localObject[(i + 1)];
        double d2 = localObject[i];
        d1 -= d2;
        paramDouble = (paramDouble - d2) / d1;
        for (j = 0; j < k; j++)
        {
          localObject = this.mY;
          double d3 = localObject[i][j];
          d2 = localObject[(i + 1)][j];
          localObject = this.mTangent;
          paramArrayOfFloat[j] = ((float)interpolate(d1, paramDouble, d3, d2, localObject[i][j], localObject[(i + 1)][j]));
        }
        return;
      }
    }
  }
  
  public double getSlope(double paramDouble, int paramInt)
  {
    Object localObject = this.mT;
    int j = localObject.length;
    if (paramDouble < localObject[0]) {
      paramDouble = localObject[0];
    } else if (paramDouble >= localObject[(j - 1)]) {
      paramDouble = localObject[(j - 1)];
    }
    for (int i = 0; i < j - 1; i++)
    {
      localObject = this.mT;
      if (paramDouble <= localObject[(i + 1)])
      {
        double d1 = localObject[(i + 1)];
        double d2 = localObject[i];
        d1 -= d2;
        paramDouble = (paramDouble - d2) / d1;
        localObject = this.mY;
        double d3 = localObject[i][paramInt];
        d2 = localObject[(i + 1)][paramInt];
        localObject = this.mTangent;
        return diff(d1, paramDouble, d3, d2, localObject[i][paramInt], localObject[(i + 1)][paramInt]) / d1;
      }
    }
    return 0.0D;
  }
  
  public void getSlope(double paramDouble, double[] paramArrayOfDouble)
  {
    Object localObject = this.mT;
    int j = localObject.length;
    int k = this.mY[0].length;
    if (paramDouble <= localObject[0]) {
      paramDouble = localObject[0];
    } else if (paramDouble >= localObject[(j - 1)]) {
      paramDouble = localObject[(j - 1)];
    }
    for (int i = 0; i < j - 1; i++)
    {
      localObject = this.mT;
      if (paramDouble <= localObject[(i + 1)])
      {
        double d1 = localObject[(i + 1)];
        double d2 = localObject[i];
        d1 -= d2;
        double d3 = (paramDouble - d2) / d1;
        for (j = 0; j < k; j++)
        {
          localObject = this.mY;
          d2 = localObject[i][j];
          paramDouble = localObject[(i + 1)][j];
          localObject = this.mTangent;
          paramArrayOfDouble[j] = (diff(d1, d3, d2, paramDouble, localObject[i][j], localObject[(i + 1)][j]) / d1);
        }
        break;
      }
    }
  }
  
  public double[] getTimePoints()
  {
    return this.mT;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/MonotonicCurveFit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */