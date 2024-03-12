package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;

public final class ScaleProvider
  implements VisibilityAnimatorProvider
{
  private boolean growing;
  private float incomingEndScale = 1.0F;
  private float incomingStartScale = 0.8F;
  private float outgoingEndScale = 1.1F;
  private float outgoingStartScale = 1.0F;
  private boolean scaleOnDisappear = true;
  
  public ScaleProvider()
  {
    this(true);
  }
  
  public ScaleProvider(boolean paramBoolean)
  {
    this.growing = paramBoolean;
  }
  
  private static Animator createScaleAnimator(View paramView, float paramFloat1, float paramFloat2)
  {
    final float f1 = paramView.getScaleX();
    final float f2 = paramView.getScaleY();
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(paramView, new PropertyValuesHolder[] { PropertyValuesHolder.ofFloat(View.SCALE_X, new float[] { f1 * paramFloat1, f1 * paramFloat2 }), PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[] { f2 * paramFloat1, f2 * paramFloat2 }) });
    localObjectAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        ScaleProvider.this.setScaleX(f1);
        ScaleProvider.this.setScaleY(f2);
      }
    });
    return localObjectAnimator;
  }
  
  public Animator createAppear(ViewGroup paramViewGroup, View paramView)
  {
    if (this.growing) {
      return createScaleAnimator(paramView, this.incomingStartScale, this.incomingEndScale);
    }
    return createScaleAnimator(paramView, this.outgoingEndScale, this.outgoingStartScale);
  }
  
  public Animator createDisappear(ViewGroup paramViewGroup, View paramView)
  {
    if (!this.scaleOnDisappear) {
      return null;
    }
    if (this.growing) {
      return createScaleAnimator(paramView, this.outgoingStartScale, this.outgoingEndScale);
    }
    return createScaleAnimator(paramView, this.incomingEndScale, this.incomingStartScale);
  }
  
  public float getIncomingEndScale()
  {
    return this.incomingEndScale;
  }
  
  public float getIncomingStartScale()
  {
    return this.incomingStartScale;
  }
  
  public float getOutgoingEndScale()
  {
    return this.outgoingEndScale;
  }
  
  public float getOutgoingStartScale()
  {
    return this.outgoingStartScale;
  }
  
  public boolean isGrowing()
  {
    return this.growing;
  }
  
  public boolean isScaleOnDisappear()
  {
    return this.scaleOnDisappear;
  }
  
  public void setGrowing(boolean paramBoolean)
  {
    this.growing = paramBoolean;
  }
  
  public void setIncomingEndScale(float paramFloat)
  {
    this.incomingEndScale = paramFloat;
  }
  
  public void setIncomingStartScale(float paramFloat)
  {
    this.incomingStartScale = paramFloat;
  }
  
  public void setOutgoingEndScale(float paramFloat)
  {
    this.outgoingEndScale = paramFloat;
  }
  
  public void setOutgoingStartScale(float paramFloat)
  {
    this.outgoingStartScale = paramFloat;
  }
  
  public void setScaleOnDisappear(boolean paramBoolean)
  {
    this.scaleOnDisappear = paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/ScaleProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */