package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup;

public final class FadeProvider
  implements VisibilityAnimatorProvider
{
  private float incomingEndThreshold = 1.0F;
  
  private static Animator createFadeAnimator(View paramView, final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5)
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        FadeProvider.this.setAlpha(TransitionUtils.lerp(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f));
      }
    });
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        FadeProvider.this.setAlpha(paramFloat5);
      }
    });
    return localValueAnimator;
  }
  
  public Animator createAppear(ViewGroup paramViewGroup, View paramView)
  {
    float f;
    if (paramView.getAlpha() == 0.0F) {
      f = 1.0F;
    } else {
      f = paramView.getAlpha();
    }
    return createFadeAnimator(paramView, 0.0F, f, 0.0F, this.incomingEndThreshold, f);
  }
  
  public Animator createDisappear(ViewGroup paramViewGroup, View paramView)
  {
    float f;
    if (paramView.getAlpha() == 0.0F) {
      f = 1.0F;
    } else {
      f = paramView.getAlpha();
    }
    return createFadeAnimator(paramView, f, 0.0F, 0.0F, 1.0F, f);
  }
  
  public float getIncomingEndThreshold()
  {
    return this.incomingEndThreshold;
  }
  
  public void setIncomingEndThreshold(float paramFloat)
  {
    this.incomingEndThreshold = paramFloat;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/FadeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */