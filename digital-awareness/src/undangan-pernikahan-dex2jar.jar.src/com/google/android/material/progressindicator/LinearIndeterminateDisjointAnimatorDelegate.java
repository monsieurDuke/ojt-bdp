package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Property;
import android.view.animation.Interpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import com.google.android.material.R.animator;
import com.google.android.material.color.MaterialColors;
import java.util.Arrays;

final class LinearIndeterminateDisjointAnimatorDelegate
  extends IndeterminateAnimatorDelegate<ObjectAnimator>
{
  private static final Property<LinearIndeterminateDisjointAnimatorDelegate, Float> ANIMATION_FRACTION = new Property(Float.class, "animationFraction")
  {
    public Float get(LinearIndeterminateDisjointAnimatorDelegate paramAnonymousLinearIndeterminateDisjointAnimatorDelegate)
    {
      return Float.valueOf(paramAnonymousLinearIndeterminateDisjointAnimatorDelegate.getAnimationFraction());
    }
    
    public void set(LinearIndeterminateDisjointAnimatorDelegate paramAnonymousLinearIndeterminateDisjointAnimatorDelegate, Float paramAnonymousFloat)
    {
      paramAnonymousLinearIndeterminateDisjointAnimatorDelegate.setAnimationFraction(paramAnonymousFloat.floatValue());
    }
  };
  private static final int[] DELAY_TO_MOVE_SEGMENT_ENDS;
  private static final int[] DURATION_TO_MOVE_SEGMENT_ENDS = { 533, 567, 850, 750 };
  private static final int TOTAL_DURATION_IN_MS = 1800;
  private float animationFraction;
  private ObjectAnimator animator;
  Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
  private final BaseProgressIndicatorSpec baseSpec;
  private ObjectAnimator completeEndAnimator;
  private boolean dirtyColors;
  private int indicatorColorIndex = 0;
  private final Interpolator[] interpolatorArray;
  
  static
  {
    DELAY_TO_MOVE_SEGMENT_ENDS = new int[] { 1267, 1000, 333, 0 };
  }
  
  public LinearIndeterminateDisjointAnimatorDelegate(Context paramContext, LinearProgressIndicatorSpec paramLinearProgressIndicatorSpec)
  {
    super(2);
    this.baseSpec = paramLinearProgressIndicatorSpec;
    this.interpolatorArray = new Interpolator[] { AnimationUtilsCompat.loadInterpolator(paramContext, R.animator.linear_indeterminate_line1_head_interpolator), AnimationUtilsCompat.loadInterpolator(paramContext, R.animator.linear_indeterminate_line1_tail_interpolator), AnimationUtilsCompat.loadInterpolator(paramContext, R.animator.linear_indeterminate_line2_head_interpolator), AnimationUtilsCompat.loadInterpolator(paramContext, R.animator.linear_indeterminate_line2_tail_interpolator) };
  }
  
  private float getAnimationFraction()
  {
    return this.animationFraction;
  }
  
  private void maybeInitializeAnimators()
  {
    ObjectAnimator localObjectAnimator;
    if (this.animator == null)
    {
      localObjectAnimator = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[] { 0.0F, 1.0F });
      this.animator = localObjectAnimator;
      localObjectAnimator.setDuration(1800L);
      this.animator.setInterpolator(null);
      this.animator.setRepeatCount(-1);
      this.animator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationRepeat(Animator paramAnonymousAnimator)
        {
          super.onAnimationRepeat(paramAnonymousAnimator);
          paramAnonymousAnimator = LinearIndeterminateDisjointAnimatorDelegate.this;
          LinearIndeterminateDisjointAnimatorDelegate.access$002(paramAnonymousAnimator, (paramAnonymousAnimator.indicatorColorIndex + 1) % LinearIndeterminateDisjointAnimatorDelegate.this.baseSpec.indicatorColors.length);
          LinearIndeterminateDisjointAnimatorDelegate.access$202(LinearIndeterminateDisjointAnimatorDelegate.this, true);
        }
      });
    }
    if (this.completeEndAnimator == null)
    {
      localObjectAnimator = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[] { 1.0F });
      this.completeEndAnimator = localObjectAnimator;
      localObjectAnimator.setDuration(1800L);
      this.completeEndAnimator.setInterpolator(null);
      this.completeEndAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          super.onAnimationEnd(paramAnonymousAnimator);
          LinearIndeterminateDisjointAnimatorDelegate.this.cancelAnimatorImmediately();
          if (LinearIndeterminateDisjointAnimatorDelegate.this.animatorCompleteCallback != null) {
            LinearIndeterminateDisjointAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(LinearIndeterminateDisjointAnimatorDelegate.this.drawable);
          }
        }
      });
    }
  }
  
  private void maybeUpdateSegmentColors()
  {
    if (this.dirtyColors)
    {
      Arrays.fill(this.segmentColors, MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[this.indicatorColorIndex], this.drawable.getAlpha()));
      this.dirtyColors = false;
    }
  }
  
  private void updateSegmentPositions(int paramInt)
  {
    for (int i = 0; i < 4; i++)
    {
      float f = getFractionInRange(paramInt, DELAY_TO_MOVE_SEGMENT_ENDS[i], DURATION_TO_MOVE_SEGMENT_ENDS[i]);
      f = this.interpolatorArray[i].getInterpolation(f);
      this.segmentPositions[i] = Math.max(0.0F, Math.min(1.0F, f));
    }
  }
  
  public void cancelAnimatorImmediately()
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
  
  public void requestCancelAnimatorAfterCurrentCycle()
  {
    ObjectAnimator localObjectAnimator = this.completeEndAnimator;
    if ((localObjectAnimator != null) && (!localObjectAnimator.isRunning()))
    {
      cancelAnimatorImmediately();
      if (this.drawable.isVisible())
      {
        this.completeEndAnimator.setFloatValues(new float[] { this.animationFraction, 1.0F });
        this.completeEndAnimator.setDuration(((1.0F - this.animationFraction) * 1800.0F));
        this.completeEndAnimator.start();
      }
      return;
    }
  }
  
  void resetPropertiesForNewStart()
  {
    this.indicatorColorIndex = 0;
    int i = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.getAlpha());
    this.segmentColors[0] = i;
    this.segmentColors[1] = i;
  }
  
  void setAnimationFraction(float paramFloat)
  {
    this.animationFraction = paramFloat;
    updateSegmentPositions((int)(1800.0F * paramFloat));
    maybeUpdateSegmentColors();
    this.drawable.invalidateSelf();
  }
  
  public void startAnimator()
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/LinearIndeterminateDisjointAnimatorDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */