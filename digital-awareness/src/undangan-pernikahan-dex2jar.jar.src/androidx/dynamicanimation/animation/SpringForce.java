package androidx.dynamicanimation.animation;

public final class SpringForce
  implements Force
{
  public static final float DAMPING_RATIO_HIGH_BOUNCY = 0.2F;
  public static final float DAMPING_RATIO_LOW_BOUNCY = 0.75F;
  public static final float DAMPING_RATIO_MEDIUM_BOUNCY = 0.5F;
  public static final float DAMPING_RATIO_NO_BOUNCY = 1.0F;
  public static final float STIFFNESS_HIGH = 10000.0F;
  public static final float STIFFNESS_LOW = 200.0F;
  public static final float STIFFNESS_MEDIUM = 1500.0F;
  public static final float STIFFNESS_VERY_LOW = 50.0F;
  private static final double UNSET = Double.MAX_VALUE;
  private static final double VELOCITY_THRESHOLD_MULTIPLIER = 62.5D;
  private double mDampedFreq;
  double mDampingRatio = 0.5D;
  private double mFinalPosition = Double.MAX_VALUE;
  private double mGammaMinus;
  private double mGammaPlus;
  private boolean mInitialized = false;
  private final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
  double mNaturalFreq = Math.sqrt(1500.0D);
  private double mValueThreshold;
  private double mVelocityThreshold;
  
  public SpringForce() {}
  
  public SpringForce(float paramFloat)
  {
    this.mFinalPosition = paramFloat;
  }
  
  private void init()
  {
    if (this.mInitialized) {
      return;
    }
    if (this.mFinalPosition != Double.MAX_VALUE)
    {
      double d2 = this.mDampingRatio;
      if (d2 > 1.0D)
      {
        double d1 = -d2;
        double d3 = this.mNaturalFreq;
        this.mGammaPlus = (d1 * d3 + d3 * Math.sqrt(d2 * d2 - 1.0D));
        d1 = this.mDampingRatio;
        d2 = -d1;
        d3 = this.mNaturalFreq;
        this.mGammaMinus = (d2 * d3 - d3 * Math.sqrt(d1 * d1 - 1.0D));
      }
      else if ((d2 >= 0.0D) && (d2 < 1.0D))
      {
        this.mDampedFreq = (this.mNaturalFreq * Math.sqrt(1.0D - d2 * d2));
      }
      this.mInitialized = true;
      return;
    }
    throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
  }
  
  public float getAcceleration(float paramFloat1, float paramFloat2)
  {
    float f = getFinalPosition();
    double d2 = this.mNaturalFreq;
    double d1 = this.mDampingRatio;
    return (float)(-(d2 * d2) * (paramFloat1 - f) - paramFloat2 * (d2 * 2.0D * d1));
  }
  
  public float getDampingRatio()
  {
    return (float)this.mDampingRatio;
  }
  
  public float getFinalPosition()
  {
    return (float)this.mFinalPosition;
  }
  
  public float getStiffness()
  {
    double d = this.mNaturalFreq;
    return (float)(d * d);
  }
  
  public boolean isAtEquilibrium(float paramFloat1, float paramFloat2)
  {
    return (Math.abs(paramFloat2) < this.mVelocityThreshold) && (Math.abs(paramFloat1 - getFinalPosition()) < this.mValueThreshold);
  }
  
  public SpringForce setDampingRatio(float paramFloat)
  {
    if (paramFloat >= 0.0F)
    {
      this.mDampingRatio = paramFloat;
      this.mInitialized = false;
      return this;
    }
    throw new IllegalArgumentException("Damping ratio must be non-negative");
  }
  
  public SpringForce setFinalPosition(float paramFloat)
  {
    this.mFinalPosition = paramFloat;
    return this;
  }
  
  public SpringForce setStiffness(float paramFloat)
  {
    if (paramFloat > 0.0F)
    {
      this.mNaturalFreq = Math.sqrt(paramFloat);
      this.mInitialized = false;
      return this;
    }
    throw new IllegalArgumentException("Spring stiffness constant must be positive.");
  }
  
  void setValueThreshold(double paramDouble)
  {
    paramDouble = Math.abs(paramDouble);
    this.mValueThreshold = paramDouble;
    this.mVelocityThreshold = (paramDouble * 62.5D);
  }
  
  DynamicAnimation.MassState updateValues(double paramDouble1, double paramDouble2, long paramLong)
  {
    init();
    double d1 = paramLong / 1000.0D;
    double d2 = paramDouble1 - this.mFinalPosition;
    paramDouble1 = this.mDampingRatio;
    double d3;
    double d4;
    double d5;
    if (paramDouble1 > 1.0D)
    {
      d3 = this.mGammaMinus;
      d4 = this.mGammaPlus;
      paramDouble1 = d2 - (d3 * d2 - paramDouble2) / (d3 - d4);
      d2 = (d3 * d2 - paramDouble2) / (d3 - d4);
      paramDouble2 = Math.pow(2.718281828459045D, d3 * d1) * paramDouble1 + Math.pow(2.718281828459045D, this.mGammaPlus * d1) * d2;
      d5 = this.mGammaMinus;
      d4 = Math.pow(2.718281828459045D, d5 * d1);
      d3 = this.mGammaPlus;
      paramDouble1 = paramDouble1 * d5 * d4 + d2 * d3 * Math.pow(2.718281828459045D, d3 * d1);
    }
    else if (paramDouble1 == 1.0D)
    {
      d3 = this.mNaturalFreq;
      paramDouble1 = paramDouble2 + d3 * d2;
      paramDouble2 = Math.pow(2.718281828459045D, -d3 * d1);
      d3 = Math.pow(2.718281828459045D, -this.mNaturalFreq * d1);
      d5 = this.mNaturalFreq;
      d4 = -d5;
      d5 = Math.pow(2.718281828459045D, -d5 * d1);
      paramDouble2 *= (d2 + paramDouble1 * d1);
      paramDouble1 = (d2 + paramDouble1 * d1) * d3 * d4 + d5 * paramDouble1;
    }
    else
    {
      d3 = 1.0D / this.mDampedFreq;
      d4 = this.mNaturalFreq;
      d3 *= (paramDouble1 * d4 * d2 + paramDouble2);
      paramDouble1 = Math.pow(2.718281828459045D, -paramDouble1 * d4 * d1) * (Math.cos(this.mDampedFreq * d1) * d2 + Math.sin(this.mDampedFreq * d1) * d3);
      paramDouble2 = this.mNaturalFreq;
      d4 = -paramDouble2;
      d5 = this.mDampingRatio;
      double d6 = Math.pow(2.718281828459045D, -d5 * paramDouble2 * d1);
      paramDouble2 = this.mDampedFreq;
      double d7 = -paramDouble2;
      double d8 = Math.sin(paramDouble2 * d1);
      double d9 = this.mDampedFreq;
      d1 = Math.cos(d9 * d1);
      paramDouble2 = paramDouble1;
      paramDouble1 = d4 * paramDouble1 * d5 + d6 * (d7 * d2 * d8 + d9 * d3 * d1);
    }
    this.mMassState.mValue = ((float)(this.mFinalPosition + paramDouble2));
    this.mMassState.mVelocity = ((float)paramDouble1);
    return this.mMassState;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/dynamicanimation/animation/SpringForce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */