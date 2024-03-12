package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;
import androidx.core.util.Preconditions;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionSpec;
import java.util.ArrayList;
import java.util.List;

abstract class BaseMotionStrategy
  implements MotionStrategy
{
  private final Context context;
  private MotionSpec defaultMotionSpec;
  private final ExtendedFloatingActionButton fab;
  private final ArrayList<Animator.AnimatorListener> listeners = new ArrayList();
  private MotionSpec motionSpec;
  private final AnimatorTracker tracker;
  
  BaseMotionStrategy(ExtendedFloatingActionButton paramExtendedFloatingActionButton, AnimatorTracker paramAnimatorTracker)
  {
    this.fab = paramExtendedFloatingActionButton;
    this.context = paramExtendedFloatingActionButton.getContext();
    this.tracker = paramAnimatorTracker;
  }
  
  public final void addAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.listeners.add(paramAnimatorListener);
  }
  
  public AnimatorSet createAnimator()
  {
    return createAnimator(getCurrentMotionSpec());
  }
  
  AnimatorSet createAnimator(MotionSpec paramMotionSpec)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramMotionSpec.hasPropertyValues("opacity")) {
      localArrayList.add(paramMotionSpec.getAnimator("opacity", this.fab, View.ALPHA));
    }
    if (paramMotionSpec.hasPropertyValues("scale"))
    {
      localArrayList.add(paramMotionSpec.getAnimator("scale", this.fab, View.SCALE_Y));
      localArrayList.add(paramMotionSpec.getAnimator("scale", this.fab, View.SCALE_X));
    }
    if (paramMotionSpec.hasPropertyValues("width")) {
      localArrayList.add(paramMotionSpec.getAnimator("width", this.fab, ExtendedFloatingActionButton.WIDTH));
    }
    if (paramMotionSpec.hasPropertyValues("height")) {
      localArrayList.add(paramMotionSpec.getAnimator("height", this.fab, ExtendedFloatingActionButton.HEIGHT));
    }
    if (paramMotionSpec.hasPropertyValues("paddingStart")) {
      localArrayList.add(paramMotionSpec.getAnimator("paddingStart", this.fab, ExtendedFloatingActionButton.PADDING_START));
    }
    if (paramMotionSpec.hasPropertyValues("paddingEnd")) {
      localArrayList.add(paramMotionSpec.getAnimator("paddingEnd", this.fab, ExtendedFloatingActionButton.PADDING_END));
    }
    if (paramMotionSpec.hasPropertyValues("labelOpacity")) {
      localArrayList.add(paramMotionSpec.getAnimator("labelOpacity", this.fab, new Property(Float.class, "LABEL_OPACITY_PROPERTY")
      {
        public Float get(ExtendedFloatingActionButton paramAnonymousExtendedFloatingActionButton)
        {
          int i = Color.alpha(paramAnonymousExtendedFloatingActionButton.originalTextCsl.getColorForState(paramAnonymousExtendedFloatingActionButton.getDrawableState(), BaseMotionStrategy.this.fab.originalTextCsl.getDefaultColor()));
          return Float.valueOf(AnimationUtils.lerp(0.0F, 1.0F, Color.alpha(paramAnonymousExtendedFloatingActionButton.getCurrentTextColor()) / 255.0F / i));
        }
        
        public void set(ExtendedFloatingActionButton paramAnonymousExtendedFloatingActionButton, Float paramAnonymousFloat)
        {
          int i = paramAnonymousExtendedFloatingActionButton.originalTextCsl.getColorForState(paramAnonymousExtendedFloatingActionButton.getDrawableState(), BaseMotionStrategy.this.fab.originalTextCsl.getDefaultColor());
          ColorStateList localColorStateList = ColorStateList.valueOf(Color.argb((int)(255.0F * AnimationUtils.lerp(0.0F, Color.alpha(i) / 255.0F, paramAnonymousFloat.floatValue())), Color.red(i), Color.green(i), Color.blue(i)));
          if (paramAnonymousFloat.floatValue() == 1.0F) {
            paramAnonymousExtendedFloatingActionButton.silentlyUpdateTextColor(paramAnonymousExtendedFloatingActionButton.originalTextCsl);
          } else {
            paramAnonymousExtendedFloatingActionButton.silentlyUpdateTextColor(localColorStateList);
          }
        }
      }));
    }
    paramMotionSpec = new AnimatorSet();
    AnimatorSetCompat.playTogether(paramMotionSpec, localArrayList);
    return paramMotionSpec;
  }
  
  public final MotionSpec getCurrentMotionSpec()
  {
    MotionSpec localMotionSpec = this.motionSpec;
    if (localMotionSpec != null) {
      return localMotionSpec;
    }
    if (this.defaultMotionSpec == null) {
      this.defaultMotionSpec = MotionSpec.createFromResource(this.context, getDefaultMotionSpecResource());
    }
    return (MotionSpec)Preconditions.checkNotNull(this.defaultMotionSpec);
  }
  
  public final List<Animator.AnimatorListener> getListeners()
  {
    return this.listeners;
  }
  
  public MotionSpec getMotionSpec()
  {
    return this.motionSpec;
  }
  
  public void onAnimationCancel()
  {
    this.tracker.clear();
  }
  
  public void onAnimationEnd()
  {
    this.tracker.clear();
  }
  
  public void onAnimationStart(Animator paramAnimator)
  {
    this.tracker.onNextAnimationStart(paramAnimator);
  }
  
  public final void removeAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.listeners.remove(paramAnimatorListener);
  }
  
  public final void setMotionSpec(MotionSpec paramMotionSpec)
  {
    this.motionSpec = paramMotionSpec;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/floatingactionbutton/BaseMotionStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */