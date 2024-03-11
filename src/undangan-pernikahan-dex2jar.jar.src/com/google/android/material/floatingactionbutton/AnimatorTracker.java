package com.google.android.material.floatingactionbutton;

import android.animation.Animator;

class AnimatorTracker
{
  private Animator currentAnimator;
  
  public void cancelCurrent()
  {
    Animator localAnimator = this.currentAnimator;
    if (localAnimator != null) {
      localAnimator.cancel();
    }
  }
  
  public void clear()
  {
    this.currentAnimator = null;
  }
  
  public void onNextAnimationStart(Animator paramAnimator)
  {
    cancelCurrent();
    this.currentAnimator = paramAnimator;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/floatingactionbutton/AnimatorTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */