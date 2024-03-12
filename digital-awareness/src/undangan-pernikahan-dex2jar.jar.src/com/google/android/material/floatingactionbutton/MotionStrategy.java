package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import com.google.android.material.animation.MotionSpec;
import java.util.List;

abstract interface MotionStrategy
{
  public abstract void addAnimationListener(Animator.AnimatorListener paramAnimatorListener);
  
  public abstract AnimatorSet createAnimator();
  
  public abstract MotionSpec getCurrentMotionSpec();
  
  public abstract int getDefaultMotionSpecResource();
  
  public abstract List<Animator.AnimatorListener> getListeners();
  
  public abstract MotionSpec getMotionSpec();
  
  public abstract void onAnimationCancel();
  
  public abstract void onAnimationEnd();
  
  public abstract void onAnimationStart(Animator paramAnimator);
  
  public abstract void onChange(ExtendedFloatingActionButton.OnChangedCallback paramOnChangedCallback);
  
  public abstract void performNow();
  
  public abstract void removeAnimationListener(Animator.AnimatorListener paramAnimatorListener);
  
  public abstract void setMotionSpec(MotionSpec paramMotionSpec);
  
  public abstract boolean shouldCancel();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/floatingactionbutton/MotionStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */