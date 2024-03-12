package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;

public abstract interface VisibilityAnimatorProvider
{
  public abstract Animator createAppear(ViewGroup paramViewGroup, View paramView);
  
  public abstract Animator createDisappear(ViewGroup paramViewGroup, View paramView);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/VisibilityAnimatorProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */