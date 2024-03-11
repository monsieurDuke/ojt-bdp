package androidx.constraintlayout.core.motion.utils;

import java.io.PrintStream;

public class SpringStopEngine
  implements StopEngine
{
  private static final double UNSET = Double.MAX_VALUE;
  private int mBoundaryMode = 0;
  double mDamping = 0.5D;
  private boolean mInitialized = false;
  private float mLastTime;
  private double mLastVelocity;
  private float mMass;
  private float mPos;
  private double mStiffness;
  private float mStopThreshold;
  private double mTargetPos;
  private float mV;
  
  private void compute(double paramDouble)
  {
    double d2 = this.mStiffness;
    double d1 = this.mDamping;
    int i = (int)(9.0D / (Math.sqrt(this.mStiffness / this.mMass) * paramDouble * 4.0D) + 1.0D);
    double d3 = paramDouble / i;
    int j = 0;
    paramDouble = d2;
    while (j < i)
    {
      float f1 = this.mPos;
      double d5 = f1;
      d2 = this.mTargetPos;
      double d4 = -paramDouble;
      float f2 = this.mV;
      double d6 = f2;
      float f3 = this.mMass;
      d4 = (d4 * (d5 - d2) - d6 * d1) / f3;
      d4 = f2 + d4 * d3 / 2.0D;
      d2 = (-(f1 + d3 * d4 / 2.0D - d2) * paramDouble - d4 * d1) / f3 * d3;
      d5 = f2;
      d4 = d2 / 2.0D;
      f2 = (float)(f2 + d2);
      this.mV = f2;
      f1 = (float)(f1 + (d5 + d4) * d3);
      this.mPos = f1;
      int k = this.mBoundaryMode;
      if (k > 0)
      {
        if ((f1 < 0.0F) && ((k & 0x1) == 1))
        {
          this.mPos = (-f1);
          this.mV = (-f2);
        }
        f1 = this.mPos;
        if ((f1 > 1.0F) && ((k & 0x2) == 2))
        {
          this.mPos = (2.0F - f1);
          this.mV = (-this.mV);
        }
      }
      j++;
    }
  }
  
  public String debug(String paramString, float paramFloat)
  {
    return null;
  }
  
  public float getAcceleration()
  {
    double d3 = this.mStiffness;
    double d1 = this.mDamping;
    double d2 = this.mPos;
    double d4 = this.mTargetPos;
    return (float)(-d3 * (d2 - d4) - this.mV * d1) / this.mMass;
  }
  
  public float getInterpolation(float paramFloat)
  {
    compute(paramFloat - this.mLastTime);
    this.mLastTime = paramFloat;
    return this.mPos;
  }
  
  public float getVelocity()
  {
    return 0.0F;
  }
  
  public float getVelocity(float paramFloat)
  {
    return this.mV;
  }
  
  public boolean isStopped()
  {
    double d1 = this.mPos - this.mTargetPos;
    double d2 = this.mStiffness;
    double d3 = this.mV;
    boolean bool;
    if (Math.sqrt((d3 * d3 * this.mMass + d2 * d1 * d1) / d2) <= this.mStopThreshold) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void log(String paramString)
  {
    Object localObject = new Throwable().getStackTrace()[1];
    localObject = ".(" + ((StackTraceElement)localObject).getFileName() + ":" + ((StackTraceElement)localObject).getLineNumber() + ") " + ((StackTraceElement)localObject).getMethodName() + "() ";
    System.out.println((String)localObject + paramString);
  }
  
  public void springConfig(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, int paramInt)
  {
    this.mTargetPos = paramFloat2;
    this.mDamping = paramFloat6;
    this.mInitialized = false;
    this.mPos = paramFloat1;
    this.mLastVelocity = paramFloat3;
    this.mStiffness = paramFloat5;
    this.mMass = paramFloat4;
    this.mStopThreshold = paramFloat7;
    this.mBoundaryMode = paramInt;
    this.mLastTime = 0.0F;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/SpringStopEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */