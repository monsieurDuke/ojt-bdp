package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.TransitionValues;
import androidx.transition.Visibility;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class MaterialVisibility<P extends VisibilityAnimatorProvider>
  extends Visibility
{
  private final List<VisibilityAnimatorProvider> additionalAnimatorProviders = new ArrayList();
  private final P primaryAnimatorProvider;
  private VisibilityAnimatorProvider secondaryAnimatorProvider;
  
  protected MaterialVisibility(P paramP, VisibilityAnimatorProvider paramVisibilityAnimatorProvider)
  {
    this.primaryAnimatorProvider = paramP;
    this.secondaryAnimatorProvider = paramVisibilityAnimatorProvider;
  }
  
  private static void addAnimatorIfNeeded(List<Animator> paramList, VisibilityAnimatorProvider paramVisibilityAnimatorProvider, ViewGroup paramViewGroup, View paramView, boolean paramBoolean)
  {
    if (paramVisibilityAnimatorProvider == null) {
      return;
    }
    if (paramBoolean) {
      paramVisibilityAnimatorProvider = paramVisibilityAnimatorProvider.createAppear(paramViewGroup, paramView);
    } else {
      paramVisibilityAnimatorProvider = paramVisibilityAnimatorProvider.createDisappear(paramViewGroup, paramView);
    }
    if (paramVisibilityAnimatorProvider != null) {
      paramList.add(paramVisibilityAnimatorProvider);
    }
  }
  
  private Animator createAnimator(ViewGroup paramViewGroup, View paramView, boolean paramBoolean)
  {
    AnimatorSet localAnimatorSet = new AnimatorSet();
    ArrayList localArrayList = new ArrayList();
    addAnimatorIfNeeded(localArrayList, this.primaryAnimatorProvider, paramViewGroup, paramView, paramBoolean);
    addAnimatorIfNeeded(localArrayList, this.secondaryAnimatorProvider, paramViewGroup, paramView, paramBoolean);
    Iterator localIterator = this.additionalAnimatorProviders.iterator();
    while (localIterator.hasNext()) {
      addAnimatorIfNeeded(localArrayList, (VisibilityAnimatorProvider)localIterator.next(), paramViewGroup, paramView, paramBoolean);
    }
    maybeApplyThemeValues(paramViewGroup.getContext(), paramBoolean);
    AnimatorSetCompat.playTogether(localAnimatorSet, localArrayList);
    return localAnimatorSet;
  }
  
  private void maybeApplyThemeValues(Context paramContext, boolean paramBoolean)
  {
    TransitionUtils.maybeApplyThemeDuration(this, paramContext, getDurationThemeAttrResId(paramBoolean));
    TransitionUtils.maybeApplyThemeInterpolator(this, paramContext, getEasingThemeAttrResId(paramBoolean), getDefaultEasingInterpolator(paramBoolean));
  }
  
  public void addAdditionalAnimatorProvider(VisibilityAnimatorProvider paramVisibilityAnimatorProvider)
  {
    this.additionalAnimatorProviders.add(paramVisibilityAnimatorProvider);
  }
  
  public void clearAdditionalAnimatorProvider()
  {
    this.additionalAnimatorProviders.clear();
  }
  
  TimeInterpolator getDefaultEasingInterpolator(boolean paramBoolean)
  {
    return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
  }
  
  int getDurationThemeAttrResId(boolean paramBoolean)
  {
    return 0;
  }
  
  int getEasingThemeAttrResId(boolean paramBoolean)
  {
    return 0;
  }
  
  public P getPrimaryAnimatorProvider()
  {
    return this.primaryAnimatorProvider;
  }
  
  public VisibilityAnimatorProvider getSecondaryAnimatorProvider()
  {
    return this.secondaryAnimatorProvider;
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return createAnimator(paramViewGroup, paramView, true);
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return createAnimator(paramViewGroup, paramView, false);
  }
  
  public boolean removeAdditionalAnimatorProvider(VisibilityAnimatorProvider paramVisibilityAnimatorProvider)
  {
    return this.additionalAnimatorProviders.remove(paramVisibilityAnimatorProvider);
  }
  
  public void setSecondaryAnimatorProvider(VisibilityAnimatorProvider paramVisibilityAnimatorProvider)
  {
    this.secondaryAnimatorProvider = paramVisibilityAnimatorProvider;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/MaterialVisibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */