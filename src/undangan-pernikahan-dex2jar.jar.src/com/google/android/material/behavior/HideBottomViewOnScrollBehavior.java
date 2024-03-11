package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewPropertyAnimator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import com.google.android.material.animation.AnimationUtils;

public class HideBottomViewOnScrollBehavior<V extends View>
  extends CoordinatorLayout.Behavior<V>
{
  protected static final int ENTER_ANIMATION_DURATION = 225;
  protected static final int EXIT_ANIMATION_DURATION = 175;
  private static final int STATE_SCROLLED_DOWN = 1;
  private static final int STATE_SCROLLED_UP = 2;
  private int additionalHiddenOffsetY = 0;
  private ViewPropertyAnimator currentAnimator;
  private int currentState = 2;
  private int height = 0;
  
  public HideBottomViewOnScrollBehavior() {}
  
  public HideBottomViewOnScrollBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void animateChildTo(V paramV, int paramInt, long paramLong, TimeInterpolator paramTimeInterpolator)
  {
    this.currentAnimator = paramV.animate().translationY(paramInt).setInterpolator(paramTimeInterpolator).setDuration(paramLong).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        HideBottomViewOnScrollBehavior.access$002(HideBottomViewOnScrollBehavior.this, null);
      }
    });
  }
  
  public boolean isScrolledDown()
  {
    int i = this.currentState;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isScrolledUp()
  {
    boolean bool;
    if (this.currentState == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramV.getLayoutParams();
    this.height = (paramV.getMeasuredHeight() + localMarginLayoutParams.bottomMargin);
    return super.onLayoutChild(paramCoordinatorLayout, paramV, paramInt);
  }
  
  public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    if (paramInt2 > 0) {
      slideDown(paramV);
    } else if (paramInt2 < 0) {
      slideUp(paramV);
    }
  }
  
  public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    boolean bool;
    if (paramInt1 == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void setAdditionalHiddenOffsetY(V paramV, int paramInt)
  {
    this.additionalHiddenOffsetY = paramInt;
    if (this.currentState == 1) {
      paramV.setTranslationY(this.height + paramInt);
    }
  }
  
  public void slideDown(V paramV)
  {
    slideDown(paramV, true);
  }
  
  public void slideDown(V paramV, boolean paramBoolean)
  {
    if (isScrolledDown()) {
      return;
    }
    ViewPropertyAnimator localViewPropertyAnimator = this.currentAnimator;
    if (localViewPropertyAnimator != null)
    {
      localViewPropertyAnimator.cancel();
      paramV.clearAnimation();
    }
    this.currentState = 1;
    int i = this.height + this.additionalHiddenOffsetY;
    if (paramBoolean) {
      animateChildTo(paramV, i, 175L, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
    } else {
      paramV.setTranslationY(i);
    }
  }
  
  public void slideUp(V paramV)
  {
    slideUp(paramV, true);
  }
  
  public void slideUp(V paramV, boolean paramBoolean)
  {
    if (isScrolledUp()) {
      return;
    }
    ViewPropertyAnimator localViewPropertyAnimator = this.currentAnimator;
    if (localViewPropertyAnimator != null)
    {
      localViewPropertyAnimator.cancel();
      paramV.clearAnimation();
    }
    this.currentState = 2;
    if (paramBoolean) {
      animateChildTo(paramV, 0, 225L, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
    } else {
      paramV.setTranslationY(0);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/behavior/HideBottomViewOnScrollBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */