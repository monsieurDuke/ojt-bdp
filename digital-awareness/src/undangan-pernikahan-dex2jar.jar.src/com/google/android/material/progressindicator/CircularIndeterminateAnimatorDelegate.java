package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.color.MaterialColors;

final class CircularIndeterminateAnimatorDelegate
  extends IndeterminateAnimatorDelegate<ObjectAnimator>
{
  private static final Property<CircularIndeterminateAnimatorDelegate, Float> ANIMATION_FRACTION = new Property(Float.class, "animationFraction")
  {
    public Float get(CircularIndeterminateAnimatorDelegate paramAnonymousCircularIndeterminateAnimatorDelegate)
    {
      return Float.valueOf(paramAnonymousCircularIndeterminateAnimatorDelegate.getAnimationFraction());
    }
    
    public void set(CircularIndeterminateAnimatorDelegate paramAnonymousCircularIndeterminateAnimatorDelegate, Float paramAnonymousFloat)
    {
      paramAnonymousCircularIndeterminateAnimatorDelegate.setAnimationFraction(paramAnonymousFloat.floatValue());
    }
  };
  private static final Property<CircularIndeterminateAnimatorDelegate, Float> COMPLETE_END_FRACTION = new Property(Float.class, "completeEndFraction")
  {
    public Float get(CircularIndeterminateAnimatorDelegate paramAnonymousCircularIndeterminateAnimatorDelegate)
    {
      return Float.valueOf(paramAnonymousCircularIndeterminateAnimatorDelegate.getCompleteEndFraction());
    }
    
    public void set(CircularIndeterminateAnimatorDelegate paramAnonymousCircularIndeterminateAnimatorDelegate, Float paramAnonymousFloat)
    {
      paramAnonymousCircularIndeterminateAnimatorDelegate.setCompleteEndFraction(paramAnonymousFloat.floatValue());
    }
  };
  private static final int CONSTANT_ROTATION_DEGREES = 1520;
  private static final int[] DELAY_TO_COLLAPSE_IN_MS;
  private static final int[] DELAY_TO_EXPAND_IN_MS = { 0, 1350, 2700, 4050 };
  private static final int[] DELAY_TO_FADE_IN_MS;
  private static final int DURATION_TO_COLLAPSE_IN_MS = 667;
  private static final int DURATION_TO_COMPLETE_END_IN_MS = 333;
  private static final int DURATION_TO_EXPAND_IN_MS = 667;
  private static final int DURATION_TO_FADE_IN_MS = 333;
  private static final int EXTRA_DEGREES_PER_CYCLE = 250;
  private static final int TAIL_DEGREES_OFFSET = -20;
  private static final int TOTAL_CYCLES = 4;
  private static final int TOTAL_DURATION_IN_MS = 5400;
  private float animationFraction;
  private ObjectAnimator animator;
  Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
  private final BaseProgressIndicatorSpec baseSpec;
  private ObjectAnimator completeEndAnimator;
  private float completeEndFraction;
  private int indicatorColorIndexOffset = 0;
  private final FastOutSlowInInterpolator interpolator;
  
  static
  {
    DELAY_TO_COLLAPSE_IN_MS = new int[] { 667, 2017, 3367, 4717 };
    DELAY_TO_FADE_IN_MS = new int[] { 1000, 2350, 3700, 5050 };
  }
  
  public CircularIndeterminateAnimatorDelegate(CircularProgressIndicatorSpec paramCircularProgressIndicatorSpec)
  {
    super(1);
    this.baseSpec = paramCircularProgressIndicatorSpec;
    this.interpolator = new FastOutSlowInInterpolator();
  }
  
  private float getAnimationFraction()
  {
    return this.animationFraction;
  }
  
  private float getCompleteEndFraction()
  {
    return this.completeEndFraction;
  }
  
  private void maybeInitializeAnimators()
  {
    ObjectAnimator localObjectAnimator;
    if (this.animator == null)
    {
      localObjectAnimator = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[] { 0.0F, 1.0F });
      this.animator = localObjectAnimator;
      localObjectAnimator.setDuration(5400L);
      this.animator.setInterpolator(null);
      this.animator.setRepeatCount(-1);
      this.animator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationRepeat(Animator paramAnonymousAnimator)
        {
          super.onAnimationRepeat(paramAnonymousAnimator);
          paramAnonymousAnimator = CircularIndeterminateAnimatorDelegate.this;
          CircularIndeterminateAnimatorDelegate.access$002(paramAnonymousAnimator, (paramAnonymousAnimator.indicatorColorIndexOffset + 4) % CircularIndeterminateAnimatorDelegate.this.baseSpec.indicatorColors.length);
        }
      });
    }
    if (this.completeEndAnimator == null)
    {
      localObjectAnimator = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, new float[] { 0.0F, 1.0F });
      this.completeEndAnimator = localObjectAnimator;
      localObjectAnimator.setDuration(333L);
      this.completeEndAnimator.setInterpolator(this.interpolator);
      this.completeEndAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          super.onAnimationEnd(paramAnonymousAnimator);
          CircularIndeterminateAnimatorDelegate.this.cancelAnimatorImmediately();
          if (CircularIndeterminateAnimatorDelegate.this.animatorCompleteCallback != null) {
            CircularIndeterminateAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(CircularIndeterminateAnimatorDelegate.this.drawable);
          }
        }
      });
    }
  }
  
  private void maybeUpdateSegmentColors(int paramInt)
  {
    for (int i = 0; i < 4; i++)
    {
      float f = getFractionInRange(paramInt, DELAY_TO_FADE_IN_MS[i], 333);
      if ((f >= 0.0F) && (f <= 1.0F))
      {
        i = (this.indicatorColorIndexOffset + i) % this.baseSpec.indicatorColors.length;
        int j = this.baseSpec.indicatorColors.length;
        paramInt = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[i], this.drawable.getAlpha());
        i = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[((i + 1) % j)], this.drawable.getAlpha());
        f = this.interpolator.getInterpolation(f);
        this.segmentColors[0] = ArgbEvaluatorCompat.getInstance().evaluate(f, Integer.valueOf(paramInt), Integer.valueOf(i)).intValue();
        break;
      }
    }
  }
  
  private void setCompleteEndFraction(float paramFloat)
  {
    this.completeEndFraction = paramFloat;
  }
  
  private void updateSegmentPositions(int paramInt)
  {
    this.segmentPositions[0] = (this.animationFraction * 1520.0F - 20.0F);
    this.segmentPositions[1] = (this.animationFraction * 1520.0F);
    for (int i = 0; i < 4; i++)
    {
      float f = getFractionInRange(paramInt, DELAY_TO_EXPAND_IN_MS[i], 667);
      arrayOfFloat = this.segmentPositions;
      arrayOfFloat[1] += this.interpolator.getInterpolation(f) * 250.0F;
      f = getFractionInRange(paramInt, DELAY_TO_COLLAPSE_IN_MS[i], 667);
      arrayOfFloat = this.segmentPositions;
      arrayOfFloat[0] += this.interpolator.getInterpolation(f) * 250.0F;
    }
    float[] arrayOfFloat = this.segmentPositions;
    arrayOfFloat[0] += (this.segmentPositions[1] - this.segmentPositions[0]) * this.completeEndFraction;
    arrayOfFloat = this.segmentPositions;
    arrayOfFloat[0] /= 360.0F;
    arrayOfFloat = this.segmentPositions;
    arrayOfFloat[1] /= 360.0F;
  }
  
  void cancelAnimatorImmediately()
  {
    ObjectAnimator localObjectAnimator = this.animator;
    if (localObjectAnimator != null) {
      localObjectAnimator.cancel();
    }
  }
  
  public void invalidateSpecValues()
  {
    resetPropertiesForNewStart();
  }
  
  public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback paramAnimationCallback)
  {
    this.animatorCompleteCallback = paramAnimationCallback;
  }
  
  void requestCancelAnimatorAfterCurrentCycle()
  {
    ObjectAnimator localObjectAnimator = this.completeEndAnimator;
    if ((localObjectAnimator != null) && (!localObjectAnimator.isRunning()))
    {
      if (this.drawable.isVisible()) {
        this.completeEndAnimator.start();
      } else {
        cancelAnimatorImmediately();
      }
      return;
    }
  }
  
  void resetPropertiesForNewStart()
  {
    this.indicatorColorIndexOffset = 0;
    this.segmentColors[0] = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.getAlpha());
    this.completeEndFraction = 0.0F;
  }
  
  void setAnimationFraction(float paramFloat)
  {
    this.animationFraction = paramFloat;
    int i = (int)(5400.0F * paramFloat);
    updateSegmentPositions(i);
    maybeUpdateSegmentColors(i);
    this.drawable.invalidateSelf();
  }
  
  void startAnimator()
  {
    maybeInitializeAnimators();
    resetPropertiesForNewStart();
    this.animator.start();
  }
  
  public void unregisterAnimatorsCompleteCallback()
  {
    this.animatorCompleteCallback = null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/CircularIndeterminateAnimatorDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */