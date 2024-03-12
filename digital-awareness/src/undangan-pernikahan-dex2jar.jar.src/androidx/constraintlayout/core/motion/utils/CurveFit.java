package androidx.constraintlayout.core.motion.utils;

public abstract class CurveFit
{
  public static final int CONSTANT = 2;
  public static final int LINEAR = 1;
  public static final int SPLINE = 0;
  
  public static CurveFit get(int paramInt, double[] paramArrayOfDouble, double[][] paramArrayOfDouble1)
  {
    if (paramArrayOfDouble.length == 1) {
      paramInt = 2;
    }
    switch (paramInt)
    {
    case 1: 
    default: 
      return new LinearCurveFit(paramArrayOfDouble, paramArrayOfDouble1);
    case 2: 
      return new Constant(paramArrayOfDouble[0], paramArrayOfDouble1[0]);
    }
    return new MonotonicCurveFit(paramArrayOfDouble, paramArrayOfDouble1);
  }
  
  public static CurveFit getArc(int[] paramArrayOfInt, double[] paramArrayOfDouble, double[][] paramArrayOfDouble1)
  {
    return new ArcCurveFit(paramArrayOfInt, paramArrayOfDouble, paramArrayOfDouble1);
  }
  
  public abstract double getPos(double paramDouble, int paramInt);
  
  public abstract void getPos(double paramDouble, double[] paramArrayOfDouble);
  
  public abstract void getPos(double paramDouble, float[] paramArrayOfFloat);
  
  public abstract double getSlope(double paramDouble, int paramInt);
  
  public abstract void getSlope(double paramDouble, double[] paramArrayOfDouble);
  
  public abstract double[] getTimePoints();
  
  static class Constant
    extends CurveFit
  {
    double mTime;
    double[] mValue;
    
    Constant(double paramDouble, double[] paramArrayOfDouble)
    {
      this.mTime = paramDouble;
      this.mValue = paramArrayOfDouble;
    }
    
    public double getPos(double paramDouble, int paramInt)
    {
      return this.mValue[paramInt];
    }
    
    public void getPos(double paramDouble, double[] paramArrayOfDouble)
    {
      double[] arrayOfDouble = this.mValue;
      System.arraycopy(arrayOfDouble, 0, paramArrayOfDouble, 0, arrayOfDouble.length);
    }
    
    public void getPos(double paramDouble, float[] paramArrayOfFloat)
    {
      for (int i = 0;; i++)
      {
        double[] arrayOfDouble = this.mValue;
        if (i >= arrayOfDouble.length) {
          break;
        }
        paramArrayOfFloat[i] = ((float)arrayOfDouble[i]);
      }
    }
    
    public double getSlope(double paramDouble, int paramInt)
    {
      return 0.0D;
    }
    
    public void getSlope(double paramDouble, double[] paramArrayOfDouble)
    {
      for (int i = 0; i < this.mValue.length; i++) {
        paramArrayOfDouble[i] = 0.0D;
      }
    }
    
    public double[] getTimePoints()
    {
      return new double[] { this.mTime };
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/CurveFit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */