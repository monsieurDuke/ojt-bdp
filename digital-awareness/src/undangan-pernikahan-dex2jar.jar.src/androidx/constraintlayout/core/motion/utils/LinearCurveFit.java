package androidx.constraintlayout.core.motion.utils;

public class LinearCurveFit
  extends CurveFit
{
  private static final String TAG = "LinearCurveFit";
  private boolean mExtrapolate = true;
  double[] mSlopeTemp;
  private double[] mT;
  private double mTotalLength = NaN.0D;
  private double[][] mY;
  
  public LinearCurveFit(double[] paramArrayOfDouble, double[][] paramArrayOfDouble1)
  {
    int i = paramArrayOfDouble.length;
    i = paramArrayOfDouble1[0].length;
    this.mSlopeTemp = new double[i];
    this.mT = paramArrayOfDouble;
    this.mY = paramArrayOfDouble1;
    if (i > 2)
    {
      double d3 = 0.0D;
      double d1 = 0.0D;
      double d2 = 0.0D;
      for (int j = 0; j < paramArrayOfDouble.length; j++)
      {
        double d5 = paramArrayOfDouble1[j][0];
        double d4 = paramArrayOfDouble1[j][0];
        if (j > 0) {
          d3 += Math.hypot(d5 - d1, d4 - d2);
        }
        d1 = d5;
        d2 = d4;
      }
      this.mTotalLength = 0.0D;
    }
  }
  
  private double getLength2D(double paramDouble)
  {
    if (Double.isNaN(this.mTotalLength)) {
      return 0.0D;
    }
    Object localObject1 = this.mT;
    int j = localObject1.length;
    if (paramDouble <= localObject1[0]) {
      return 0.0D;
    }
    if (paramDouble >= localObject1[(j - 1)]) {
      return this.mTotalLength;
    }
    double d4 = 0.0D;
    double d1 = 0.0D;
    double d2 = 0.0D;
    int i = 0;
    while (i < j - 1)
    {
      localObject1 = this.mY[i];
      double d6 = localObject1[0];
      double d5 = localObject1[1];
      if (i > 0) {
        d1 = d4 + Math.hypot(d6 - d1, d5 - d2);
      } else {
        d1 = d4;
      }
      double d3 = d6;
      d2 = d5;
      localObject1 = this.mT;
      d4 = localObject1[i];
      if (paramDouble == d4) {
        return d1;
      }
      if (paramDouble < localObject1[(i + 1)])
      {
        paramDouble = (paramDouble - d4) / (localObject1[(i + 1)] - d4);
        localObject1 = this.mY;
        Object localObject2 = localObject1[i];
        d3 = localObject2[0];
        d2 = localObject1[(i + 1)][0];
        return d1 + Math.hypot(d5 - ((1.0D - paramDouble) * localObject2[1] + localObject1[(i + 1)][1] * paramDouble), d6 - ((1.0D - paramDouble) * d3 + d2 * paramDouble));
      }
      i++;
      d4 = d1;
      d1 = d3;
    }
    return 0.0D;
  }
  
  public double getPos(double paramDouble, int paramInt)
  {
    Object localObject = this.mT;
    int j = localObject.length;
    double d;
    if (this.mExtrapolate)
    {
      d = localObject[0];
      if (paramDouble <= d) {
        return this.mY[0][paramInt] + (paramDouble - d) * getSlope(d, paramInt);
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
      d = localObject[i];
      if (paramDouble == d) {
        return this.mY[i][paramInt];
      }
      if (paramDouble < localObject[(i + 1)])
      {
        paramDouble = (paramDouble - d) / (localObject[(i + 1)] - d);
        localObject = this.mY;
        return (1.0D - paramDouble) * localObject[i][paramInt] + localObject[(i + 1)][paramInt] * paramDouble;
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
        paramDouble = (paramDouble - d2) / (d1 - d2);
        for (j = 0; j < k; j++)
        {
          localObject = this.mY;
          paramArrayOfDouble[j] = ((1.0D - paramDouble) * localObject[i][j] + localObject[(i + 1)][j] * paramDouble);
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
        double d2 = localObject[(i + 1)];
        d1 = localObject[i];
        paramDouble = (paramDouble - d1) / (d2 - d1);
        for (j = 0; j < k; j++)
        {
          localObject = this.mY;
          paramArrayOfFloat[j] = ((float)((1.0D - paramDouble) * localObject[i][j] + localObject[(i + 1)][j] * paramDouble));
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
        paramDouble = localObject[i][paramInt];
        return (localObject[(i + 1)][paramInt] - paramDouble) / d1;
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
        paramDouble = (paramDouble - d2) / d1;
        for (j = 0; j < k; j++)
        {
          localObject = this.mY;
          paramDouble = localObject[i][j];
          paramArrayOfDouble[j] = ((localObject[(i + 1)][j] - paramDouble) / d1);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/LinearCurveFit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */