package androidx.constraintlayout.core.motion.utils;

public class HyperSpline
{
  double[][] mCtl;
  Cubic[][] mCurve;
  double[] mCurveLength;
  int mDimensionality;
  int mPoints;
  double mTotalLength;
  
  public HyperSpline() {}
  
  public HyperSpline(double[][] paramArrayOfDouble)
  {
    setup(paramArrayOfDouble);
  }
  
  static Cubic[] calcNaturalCubic(int paramInt, double[] paramArrayOfDouble)
  {
    double[] arrayOfDouble2 = new double[paramInt];
    Object localObject = new double[paramInt];
    double[] arrayOfDouble1 = new double[paramInt];
    int i = paramInt - 1;
    arrayOfDouble2[0] = 0.5D;
    for (paramInt = 1; paramInt < i; paramInt++) {
      arrayOfDouble2[paramInt] = (1.0D / (4.0D - arrayOfDouble2[(paramInt - 1)]));
    }
    arrayOfDouble2[i] = (1.0D / (2.0D - arrayOfDouble2[(i - 1)]));
    localObject[0] = ((paramArrayOfDouble[1] - paramArrayOfDouble[0]) * 3.0D * arrayOfDouble2[0]);
    for (paramInt = 1; paramInt < i; paramInt++) {
      localObject[paramInt] = (((paramArrayOfDouble[(paramInt + 1)] - paramArrayOfDouble[(paramInt - 1)]) * 3.0D - localObject[(paramInt - 1)]) * arrayOfDouble2[paramInt]);
    }
    localObject[i] = (((paramArrayOfDouble[i] - paramArrayOfDouble[(i - 1)]) * 3.0D - localObject[(i - 1)]) * arrayOfDouble2[i]);
    arrayOfDouble1[i] = localObject[i];
    for (paramInt = i - 1; paramInt >= 0; paramInt--) {
      localObject[paramInt] -= arrayOfDouble2[paramInt] * arrayOfDouble1[(paramInt + 1)];
    }
    localObject = new Cubic[i];
    for (paramInt = 0; paramInt < i; paramInt++) {
      localObject[paramInt] = new Cubic((float)paramArrayOfDouble[paramInt], arrayOfDouble1[paramInt], (paramArrayOfDouble[(paramInt + 1)] - paramArrayOfDouble[paramInt]) * 3.0D - arrayOfDouble1[paramInt] * 2.0D - arrayOfDouble1[(paramInt + 1)], (paramArrayOfDouble[paramInt] - paramArrayOfDouble[(paramInt + 1)]) * 2.0D + arrayOfDouble1[paramInt] + arrayOfDouble1[(paramInt + 1)]);
    }
    return (Cubic[])localObject;
  }
  
  public double approxLength(Cubic[] paramArrayOfCubic)
  {
    double d1 = 0.0D;
    int i = paramArrayOfCubic.length;
    double[] arrayOfDouble = new double[paramArrayOfCubic.length];
    double d2 = 0.0D;
    double d3;
    double d4;
    while (d2 < 1.0D)
    {
      d3 = 0.0D;
      for (i = 0; i < paramArrayOfCubic.length; i++)
      {
        d4 = arrayOfDouble[i];
        double d5 = paramArrayOfCubic[i].eval(d2);
        arrayOfDouble[i] = d5;
        d4 -= d5;
        d3 += d4 * d4;
      }
      d4 = d1;
      if (d2 > 0.0D) {
        d4 = d1 + Math.sqrt(d3);
      }
      d2 += 0.1D;
      d1 = d4;
    }
    d2 = 0.0D;
    for (i = 0; i < paramArrayOfCubic.length; i++)
    {
      d4 = arrayOfDouble[i];
      d3 = paramArrayOfCubic[i].eval(1.0D);
      arrayOfDouble[i] = d3;
      d3 = d4 - d3;
      d2 += d3 * d3;
    }
    return d1 + Math.sqrt(d2);
  }
  
  public double getPos(double paramDouble, int paramInt)
  {
    paramDouble = this.mTotalLength * paramDouble;
    double[] arrayOfDouble;
    for (int i = 0;; i++)
    {
      arrayOfDouble = this.mCurveLength;
      if (i >= arrayOfDouble.length - 1) {
        break;
      }
      double d = arrayOfDouble[i];
      if (d >= paramDouble) {
        break;
      }
      paramDouble -= d;
    }
    return this.mCurve[paramInt][i].eval(paramDouble / arrayOfDouble[i]);
  }
  
  public void getPos(double paramDouble, double[] paramArrayOfDouble)
  {
    paramDouble = this.mTotalLength * paramDouble;
    for (int i = 0;; i++)
    {
      double[] arrayOfDouble = this.mCurveLength;
      if (i >= arrayOfDouble.length - 1) {
        break;
      }
      double d = arrayOfDouble[i];
      if (d >= paramDouble) {
        break;
      }
      paramDouble -= d;
    }
    for (int j = 0; j < paramArrayOfDouble.length; j++) {
      paramArrayOfDouble[j] = this.mCurve[j][i].eval(paramDouble / this.mCurveLength[i]);
    }
  }
  
  public void getPos(double paramDouble, float[] paramArrayOfFloat)
  {
    paramDouble = this.mTotalLength * paramDouble;
    for (int i = 0;; i++)
    {
      double[] arrayOfDouble = this.mCurveLength;
      if (i >= arrayOfDouble.length - 1) {
        break;
      }
      double d = arrayOfDouble[i];
      if (d >= paramDouble) {
        break;
      }
      paramDouble -= d;
    }
    for (int j = 0; j < paramArrayOfFloat.length; j++) {
      paramArrayOfFloat[j] = ((float)this.mCurve[j][i].eval(paramDouble / this.mCurveLength[i]));
    }
  }
  
  public void getVelocity(double paramDouble, double[] paramArrayOfDouble)
  {
    paramDouble = this.mTotalLength * paramDouble;
    for (int i = 0;; i++)
    {
      double[] arrayOfDouble = this.mCurveLength;
      if (i >= arrayOfDouble.length - 1) {
        break;
      }
      double d = arrayOfDouble[i];
      if (d >= paramDouble) {
        break;
      }
      paramDouble -= d;
    }
    for (int j = 0; j < paramArrayOfDouble.length; j++) {
      paramArrayOfDouble[j] = this.mCurve[j][i].vel(paramDouble / this.mCurveLength[i]);
    }
  }
  
  public void setup(double[][] paramArrayOfDouble)
  {
    int j = paramArrayOfDouble[0].length;
    this.mDimensionality = j;
    int i = paramArrayOfDouble.length;
    this.mPoints = i;
    this.mCtl = new double[j][i];
    this.mCurve = new Cubic[this.mDimensionality][];
    for (i = 0; i < this.mDimensionality; i++) {
      for (j = 0; j < this.mPoints; j++) {
        this.mCtl[i][j] = paramArrayOfDouble[j][i];
      }
    }
    double[] arrayOfDouble;
    for (i = 0;; i++)
    {
      j = this.mDimensionality;
      if (i >= j) {
        break;
      }
      paramArrayOfDouble = this.mCurve;
      arrayOfDouble = this.mCtl[i];
      paramArrayOfDouble[i] = calcNaturalCubic(arrayOfDouble.length, arrayOfDouble);
    }
    this.mCurveLength = new double[this.mPoints - 1];
    this.mTotalLength = 0.0D;
    paramArrayOfDouble = new Cubic[j];
    for (i = 0; i < this.mCurveLength.length; i++)
    {
      for (j = 0; j < this.mDimensionality; j++) {
        paramArrayOfDouble[j] = this.mCurve[j][i];
      }
      double d1 = this.mTotalLength;
      arrayOfDouble = this.mCurveLength;
      double d2 = approxLength(paramArrayOfDouble);
      arrayOfDouble[i] = d2;
      this.mTotalLength = (d1 + d2);
    }
  }
  
  public static class Cubic
  {
    double mA;
    double mB;
    double mC;
    double mD;
    
    public Cubic(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
      this.mA = paramDouble1;
      this.mB = paramDouble2;
      this.mC = paramDouble3;
      this.mD = paramDouble4;
    }
    
    public double eval(double paramDouble)
    {
      return ((this.mD * paramDouble + this.mC) * paramDouble + this.mB) * paramDouble + this.mA;
    }
    
    public double vel(double paramDouble)
    {
      return (this.mD * 3.0D * paramDouble + this.mC * 2.0D) * paramDouble + this.mB;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/HyperSpline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */