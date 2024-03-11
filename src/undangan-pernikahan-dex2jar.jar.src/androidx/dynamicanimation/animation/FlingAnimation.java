package androidx.dynamicanimation.animation;

public final class FlingAnimation
  extends DynamicAnimation<FlingAnimation>
{
  private final DragForce mFlingForce;
  
  public FlingAnimation(FloatValueHolder paramFloatValueHolder)
  {
    super(paramFloatValueHolder);
    paramFloatValueHolder = new DragForce();
    this.mFlingForce = paramFloatValueHolder;
    paramFloatValueHolder.setValueThreshold(getValueThreshold());
  }
  
  public <K> FlingAnimation(K paramK, FloatPropertyCompat<K> paramFloatPropertyCompat)
  {
    super(paramK, paramFloatPropertyCompat);
    paramK = new DragForce();
    this.mFlingForce = paramK;
    paramK.setValueThreshold(getValueThreshold());
  }
  
  float getAcceleration(float paramFloat1, float paramFloat2)
  {
    return this.mFlingForce.getAcceleration(paramFloat1, paramFloat2);
  }
  
  public float getFriction()
  {
    return this.mFlingForce.getFrictionScalar();
  }
  
  boolean isAtEquilibrium(float paramFloat1, float paramFloat2)
  {
    boolean bool;
    if ((paramFloat1 < this.mMaxValue) && (paramFloat1 > this.mMinValue) && (!this.mFlingForce.isAtEquilibrium(paramFloat1, paramFloat2))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public FlingAnimation setFriction(float paramFloat)
  {
    if (paramFloat > 0.0F)
    {
      this.mFlingForce.setFrictionScalar(paramFloat);
      return this;
    }
    throw new IllegalArgumentException("Friction must be positive");
  }
  
  public FlingAnimation setMaxValue(float paramFloat)
  {
    super.setMaxValue(paramFloat);
    return this;
  }
  
  public FlingAnimation setMinValue(float paramFloat)
  {
    super.setMinValue(paramFloat);
    return this;
  }
  
  public FlingAnimation setStartVelocity(float paramFloat)
  {
    super.setStartVelocity(paramFloat);
    return this;
  }
  
  void setValueThreshold(float paramFloat)
  {
    this.mFlingForce.setValueThreshold(paramFloat);
  }
  
  boolean updateValueAndVelocity(long paramLong)
  {
    DynamicAnimation.MassState localMassState = this.mFlingForce.updateValueAndVelocity(this.mValue, this.mVelocity, paramLong);
    this.mValue = localMassState.mValue;
    this.mVelocity = localMassState.mVelocity;
    if (this.mValue < this.mMinValue)
    {
      this.mValue = this.mMinValue;
      return true;
    }
    if (this.mValue > this.mMaxValue)
    {
      this.mValue = this.mMaxValue;
      return true;
    }
    return isAtEquilibrium(this.mValue, this.mVelocity);
  }
  
  static final class DragForce
    implements Force
  {
    private static final float DEFAULT_FRICTION = -4.2F;
    private static final float VELOCITY_THRESHOLD_MULTIPLIER = 62.5F;
    private float mFriction = -4.2F;
    private final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
    private float mVelocityThreshold;
    
    public float getAcceleration(float paramFloat1, float paramFloat2)
    {
      return this.mFriction * paramFloat2;
    }
    
    float getFrictionScalar()
    {
      return this.mFriction / -4.2F;
    }
    
    public boolean isAtEquilibrium(float paramFloat1, float paramFloat2)
    {
      boolean bool;
      if (Math.abs(paramFloat2) < this.mVelocityThreshold) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void setFrictionScalar(float paramFloat)
    {
      this.mFriction = (-4.2F * paramFloat);
    }
    
    void setValueThreshold(float paramFloat)
    {
      this.mVelocityThreshold = (62.5F * paramFloat);
    }
    
    DynamicAnimation.MassState updateValueAndVelocity(float paramFloat1, float paramFloat2, long paramLong)
    {
      this.mMassState.mVelocity = ((float)(paramFloat2 * Math.exp((float)paramLong / 1000.0F * this.mFriction)));
      DynamicAnimation.MassState localMassState = this.mMassState;
      float f = this.mFriction;
      localMassState.mValue = ((float)(paramFloat1 - paramFloat2 / f + paramFloat2 / f * Math.exp(f * (float)paramLong / 1000.0F)));
      if (isAtEquilibrium(this.mMassState.mValue, this.mMassState.mVelocity)) {
        this.mMassState.mVelocity = 0.0F;
      }
      return this.mMassState;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/dynamicanimation/animation/FlingAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */