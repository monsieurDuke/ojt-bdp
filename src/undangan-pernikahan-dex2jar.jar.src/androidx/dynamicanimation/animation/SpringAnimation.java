package androidx.dynamicanimation.animation;

import android.os.Looper;
import android.util.AndroidRuntimeException;

public final class SpringAnimation
  extends DynamicAnimation<SpringAnimation>
{
  private static final float UNSET = Float.MAX_VALUE;
  private boolean mEndRequested = false;
  private float mPendingPosition = Float.MAX_VALUE;
  private SpringForce mSpring = null;
  
  public SpringAnimation(FloatValueHolder paramFloatValueHolder)
  {
    super(paramFloatValueHolder);
  }
  
  public <K> SpringAnimation(K paramK, FloatPropertyCompat<K> paramFloatPropertyCompat)
  {
    super(paramK, paramFloatPropertyCompat);
  }
  
  public <K> SpringAnimation(K paramK, FloatPropertyCompat<K> paramFloatPropertyCompat, float paramFloat)
  {
    super(paramK, paramFloatPropertyCompat);
    this.mSpring = new SpringForce(paramFloat);
  }
  
  private void sanityCheck()
  {
    SpringForce localSpringForce = this.mSpring;
    if (localSpringForce != null)
    {
      double d = localSpringForce.getFinalPosition();
      if (d <= this.mMaxValue)
      {
        if (d >= this.mMinValue) {
          return;
        }
        throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
      }
      throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
    }
    throw new UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
  }
  
  public void animateToFinalPosition(float paramFloat)
  {
    if (isRunning())
    {
      this.mPendingPosition = paramFloat;
    }
    else
    {
      if (this.mSpring == null) {
        this.mSpring = new SpringForce(paramFloat);
      }
      this.mSpring.setFinalPosition(paramFloat);
      start();
    }
  }
  
  public boolean canSkipToEnd()
  {
    boolean bool;
    if (this.mSpring.mDampingRatio > 0.0D) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  float getAcceleration(float paramFloat1, float paramFloat2)
  {
    return this.mSpring.getAcceleration(paramFloat1, paramFloat2);
  }
  
  public SpringForce getSpring()
  {
    return this.mSpring;
  }
  
  boolean isAtEquilibrium(float paramFloat1, float paramFloat2)
  {
    return this.mSpring.isAtEquilibrium(paramFloat1, paramFloat2);
  }
  
  public SpringAnimation setSpring(SpringForce paramSpringForce)
  {
    this.mSpring = paramSpringForce;
    return this;
  }
  
  void setValueThreshold(float paramFloat) {}
  
  public void skipToEnd()
  {
    if (canSkipToEnd())
    {
      if (Looper.myLooper() == Looper.getMainLooper())
      {
        if (this.mRunning) {
          this.mEndRequested = true;
        }
        return;
      }
      throw new AndroidRuntimeException("Animations may only be started on the main thread");
    }
    throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
  }
  
  public void start()
  {
    sanityCheck();
    this.mSpring.setValueThreshold(getValueThreshold());
    super.start();
  }
  
  boolean updateValueAndVelocity(long paramLong)
  {
    if (this.mEndRequested)
    {
      float f = this.mPendingPosition;
      if (f != Float.MAX_VALUE)
      {
        this.mSpring.setFinalPosition(f);
        this.mPendingPosition = Float.MAX_VALUE;
      }
      this.mValue = this.mSpring.getFinalPosition();
      this.mVelocity = 0.0F;
      this.mEndRequested = false;
      return true;
    }
    DynamicAnimation.MassState localMassState;
    if (this.mPendingPosition != Float.MAX_VALUE)
    {
      double d = this.mSpring.getFinalPosition();
      localMassState = this.mSpring.updateValues(this.mValue, this.mVelocity, paramLong / 2L);
      this.mSpring.setFinalPosition(this.mPendingPosition);
      this.mPendingPosition = Float.MAX_VALUE;
      localMassState = this.mSpring.updateValues(localMassState.mValue, localMassState.mVelocity, paramLong / 2L);
      this.mValue = localMassState.mValue;
      this.mVelocity = localMassState.mVelocity;
    }
    else
    {
      localMassState = this.mSpring.updateValues(this.mValue, this.mVelocity, paramLong);
      this.mValue = localMassState.mValue;
      this.mVelocity = localMassState.mVelocity;
    }
    this.mValue = Math.max(this.mValue, this.mMinValue);
    this.mValue = Math.min(this.mValue, this.mMaxValue);
    if (isAtEquilibrium(this.mValue, this.mVelocity))
    {
      this.mValue = this.mSpring.getFinalPosition();
      this.mVelocity = 0.0F;
      return true;
    }
    return false;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/dynamicanimation/animation/SpringAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */