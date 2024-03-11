package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.dimen;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SlideDistanceProvider
  implements VisibilityAnimatorProvider
{
  private static final int DEFAULT_DISTANCE = -1;
  private int slideDistance = -1;
  private int slideEdge;
  
  public SlideDistanceProvider(int paramInt)
  {
    this.slideEdge = paramInt;
  }
  
  private static Animator createTranslationAppearAnimator(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    float f2 = paramView2.getTranslationX();
    float f1 = paramView2.getTranslationY();
    switch (paramInt1)
    {
    default: 
      throw new IllegalArgumentException("Invalid slide direction: " + paramInt1);
    case 8388613: 
      if (isRtl(paramView1)) {
        f1 = f2 - paramInt2;
      } else {
        f1 = paramInt2 + f2;
      }
      return createTranslationXAnimator(paramView2, f1, f2, f2);
    case 8388611: 
      if (isRtl(paramView1)) {
        f1 = paramInt2 + f2;
      } else {
        f1 = f2 - paramInt2;
      }
      return createTranslationXAnimator(paramView2, f1, f2, f2);
    case 80: 
      return createTranslationYAnimator(paramView2, paramInt2 + f1, f1, f1);
    case 48: 
      return createTranslationYAnimator(paramView2, f1 - paramInt2, f1, f1);
    case 5: 
      return createTranslationXAnimator(paramView2, f2 - paramInt2, f2, f2);
    }
    return createTranslationXAnimator(paramView2, paramInt2 + f2, f2, f2);
  }
  
  private static Animator createTranslationDisappearAnimator(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    float f2 = paramView2.getTranslationX();
    float f1 = paramView2.getTranslationY();
    switch (paramInt1)
    {
    default: 
      throw new IllegalArgumentException("Invalid slide direction: " + paramInt1);
    case 8388613: 
      if (isRtl(paramView1)) {
        f1 = paramInt2 + f2;
      } else {
        f1 = f2 - paramInt2;
      }
      return createTranslationXAnimator(paramView2, f2, f1, f2);
    case 8388611: 
      if (isRtl(paramView1)) {
        f1 = f2 - paramInt2;
      } else {
        f1 = paramInt2 + f2;
      }
      return createTranslationXAnimator(paramView2, f2, f1, f2);
    case 80: 
      return createTranslationYAnimator(paramView2, f1, f1 - paramInt2, f1);
    case 48: 
      return createTranslationYAnimator(paramView2, f1, paramInt2 + f1, f1);
    case 5: 
      return createTranslationXAnimator(paramView2, f2, paramInt2 + f2, f2);
    }
    return createTranslationXAnimator(paramView2, f2, f2 - paramInt2, f2);
  }
  
  private static Animator createTranslationXAnimator(View paramView, float paramFloat1, float paramFloat2, final float paramFloat3)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(paramView, new PropertyValuesHolder[] { PropertyValuesHolder.ofFloat(View.TRANSLATION_X, new float[] { paramFloat1, paramFloat2 }) });
    localObjectAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        SlideDistanceProvider.this.setTranslationX(paramFloat3);
      }
    });
    return localObjectAnimator;
  }
  
  private static Animator createTranslationYAnimator(View paramView, float paramFloat1, float paramFloat2, final float paramFloat3)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(paramView, new PropertyValuesHolder[] { PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[] { paramFloat1, paramFloat2 }) });
    localObjectAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        SlideDistanceProvider.this.setTranslationY(paramFloat3);
      }
    });
    return localObjectAnimator;
  }
  
  private int getSlideDistanceOrDefault(Context paramContext)
  {
    int i = this.slideDistance;
    if (i != -1) {
      return i;
    }
    return paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_transition_shared_axis_slide_distance);
  }
  
  private static boolean isRtl(View paramView)
  {
    int i = ViewCompat.getLayoutDirection(paramView);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public Animator createAppear(ViewGroup paramViewGroup, View paramView)
  {
    return createTranslationAppearAnimator(paramViewGroup, paramView, this.slideEdge, getSlideDistanceOrDefault(paramView.getContext()));
  }
  
  public Animator createDisappear(ViewGroup paramViewGroup, View paramView)
  {
    return createTranslationDisappearAnimator(paramViewGroup, paramView, this.slideEdge, getSlideDistanceOrDefault(paramView.getContext()));
  }
  
  public int getSlideDistance()
  {
    return this.slideDistance;
  }
  
  public int getSlideEdge()
  {
    return this.slideEdge;
  }
  
  public void setSlideDistance(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.slideDistance = paramInt;
      return;
    }
    throw new IllegalArgumentException("Slide distance must be positive. If attempting to reverse the direction of the slide, use setSlideEdge(int) instead.");
  }
  
  public void setSlideEdge(int paramInt)
  {
    this.slideEdge = paramInt;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface GravityFlag {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/SlideDistanceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */