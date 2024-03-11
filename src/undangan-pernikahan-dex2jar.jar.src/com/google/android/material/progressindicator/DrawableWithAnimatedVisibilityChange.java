package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class DrawableWithAnimatedVisibilityChange
  extends Drawable
  implements Animatable2Compat
{
  private static final boolean DEFAULT_DRAWABLE_RESTART = false;
  private static final int GROW_DURATION = 500;
  private static final Property<DrawableWithAnimatedVisibilityChange, Float> GROW_FRACTION = new Property(Float.class, "growFraction")
  {
    public Float get(DrawableWithAnimatedVisibilityChange paramAnonymousDrawableWithAnimatedVisibilityChange)
    {
      return Float.valueOf(paramAnonymousDrawableWithAnimatedVisibilityChange.getGrowFraction());
    }
    
    public void set(DrawableWithAnimatedVisibilityChange paramAnonymousDrawableWithAnimatedVisibilityChange, Float paramAnonymousFloat)
    {
      paramAnonymousDrawableWithAnimatedVisibilityChange.setGrowFraction(paramAnonymousFloat.floatValue());
    }
  };
  private List<Animatable2Compat.AnimationCallback> animationCallbacks;
  AnimatorDurationScaleProvider animatorDurationScaleProvider;
  final BaseProgressIndicatorSpec baseSpec;
  final Context context;
  private float growFraction;
  private ValueAnimator hideAnimator;
  private boolean ignoreCallbacks;
  private Animatable2Compat.AnimationCallback internalAnimationCallback;
  private float mockGrowFraction;
  private boolean mockHideAnimationRunning;
  private boolean mockShowAnimationRunning;
  final Paint paint = new Paint();
  private ValueAnimator showAnimator;
  private int totalAlpha;
  
  DrawableWithAnimatedVisibilityChange(Context paramContext, BaseProgressIndicatorSpec paramBaseProgressIndicatorSpec)
  {
    this.context = paramContext;
    this.baseSpec = paramBaseProgressIndicatorSpec;
    this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
    setAlpha(255);
  }
  
  private void dispatchAnimationEnd()
  {
    Object localObject = this.internalAnimationCallback;
    if (localObject != null) {
      ((Animatable2Compat.AnimationCallback)localObject).onAnimationEnd(this);
    }
    localObject = this.animationCallbacks;
    if ((localObject != null) && (!this.ignoreCallbacks))
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((Animatable2Compat.AnimationCallback)((Iterator)localObject).next()).onAnimationEnd(this);
      }
    }
  }
  
  private void dispatchAnimationStart()
  {
    Object localObject = this.internalAnimationCallback;
    if (localObject != null) {
      ((Animatable2Compat.AnimationCallback)localObject).onAnimationStart(this);
    }
    localObject = this.animationCallbacks;
    if ((localObject != null) && (!this.ignoreCallbacks))
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((Animatable2Compat.AnimationCallback)((Iterator)localObject).next()).onAnimationStart(this);
      }
    }
  }
  
  private void endAnimatorWithoutCallbacks(ValueAnimator... paramVarArgs)
  {
    boolean bool = this.ignoreCallbacks;
    this.ignoreCallbacks = true;
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramVarArgs[i].end();
    }
    this.ignoreCallbacks = bool;
  }
  
  private void maybeInitializeAnimators()
  {
    ObjectAnimator localObjectAnimator;
    if (this.showAnimator == null)
    {
      localObjectAnimator = ObjectAnimator.ofFloat(this, GROW_FRACTION, new float[] { 0.0F, 1.0F });
      this.showAnimator = localObjectAnimator;
      localObjectAnimator.setDuration(500L);
      this.showAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      setShowAnimator(this.showAnimator);
    }
    if (this.hideAnimator == null)
    {
      localObjectAnimator = ObjectAnimator.ofFloat(this, GROW_FRACTION, new float[] { 1.0F, 0.0F });
      this.hideAnimator = localObjectAnimator;
      localObjectAnimator.setDuration(500L);
      this.hideAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      setHideAnimator(this.hideAnimator);
    }
  }
  
  private void setHideAnimator(ValueAnimator paramValueAnimator)
  {
    ValueAnimator localValueAnimator = this.hideAnimator;
    if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
      throw new IllegalArgumentException("Cannot set hideAnimator while the current hideAnimator is running.");
    }
    this.hideAnimator = paramValueAnimator;
    paramValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        super.onAnimationEnd(paramAnonymousAnimator);
        DrawableWithAnimatedVisibilityChange.this.setVisible(false, false);
        DrawableWithAnimatedVisibilityChange.this.dispatchAnimationEnd();
      }
    });
  }
  
  private void setShowAnimator(ValueAnimator paramValueAnimator)
  {
    ValueAnimator localValueAnimator = this.showAnimator;
    if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
      throw new IllegalArgumentException("Cannot set showAnimator while the current showAnimator is running.");
    }
    this.showAnimator = paramValueAnimator;
    paramValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        super.onAnimationStart(paramAnonymousAnimator);
        DrawableWithAnimatedVisibilityChange.this.dispatchAnimationStart();
      }
    });
  }
  
  public void clearAnimationCallbacks()
  {
    this.animationCallbacks.clear();
    this.animationCallbacks = null;
  }
  
  public int getAlpha()
  {
    return this.totalAlpha;
  }
  
  float getGrowFraction()
  {
    if ((!this.baseSpec.isShowAnimationEnabled()) && (!this.baseSpec.isHideAnimationEnabled())) {
      return 1.0F;
    }
    if ((!this.mockHideAnimationRunning) && (!this.mockShowAnimationRunning)) {
      return this.growFraction;
    }
    return this.mockGrowFraction;
  }
  
  ValueAnimator getHideAnimator()
  {
    return this.hideAnimator;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public boolean hideNow()
  {
    return setVisible(false, false, false);
  }
  
  public boolean isHiding()
  {
    ValueAnimator localValueAnimator = this.hideAnimator;
    boolean bool;
    if (((localValueAnimator != null) && (localValueAnimator.isRunning())) || (this.mockHideAnimationRunning)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isRunning()
  {
    boolean bool;
    if ((!isShowing()) && (!isHiding())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isShowing()
  {
    ValueAnimator localValueAnimator = this.showAnimator;
    boolean bool;
    if (((localValueAnimator != null) && (localValueAnimator.isRunning())) || (this.mockShowAnimationRunning)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void registerAnimationCallback(Animatable2Compat.AnimationCallback paramAnimationCallback)
  {
    if (this.animationCallbacks == null) {
      this.animationCallbacks = new ArrayList();
    }
    if (!this.animationCallbacks.contains(paramAnimationCallback)) {
      this.animationCallbacks.add(paramAnimationCallback);
    }
  }
  
  public void setAlpha(int paramInt)
  {
    this.totalAlpha = paramInt;
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.paint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  void setGrowFraction(float paramFloat)
  {
    if (this.growFraction != paramFloat)
    {
      this.growFraction = paramFloat;
      invalidateSelf();
    }
  }
  
  void setInternalAnimationCallback(Animatable2Compat.AnimationCallback paramAnimationCallback)
  {
    this.internalAnimationCallback = paramAnimationCallback;
  }
  
  void setMockHideAnimationRunning(boolean paramBoolean, float paramFloat)
  {
    this.mockHideAnimationRunning = paramBoolean;
    this.mockGrowFraction = paramFloat;
  }
  
  void setMockShowAnimationRunning(boolean paramBoolean, float paramFloat)
  {
    this.mockShowAnimationRunning = paramBoolean;
    this.mockGrowFraction = paramFloat;
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    return setVisible(paramBoolean1, paramBoolean2, true);
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    float f = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
    if ((paramBoolean3) && (f > 0.0F)) {
      paramBoolean3 = true;
    } else {
      paramBoolean3 = false;
    }
    return setVisibleInternal(paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  boolean setVisibleInternal(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    maybeInitializeAnimators();
    if ((!isVisible()) && (!paramBoolean1)) {
      return false;
    }
    ValueAnimator localValueAnimator;
    if (paramBoolean1) {
      localValueAnimator = this.showAnimator;
    } else {
      localValueAnimator = this.hideAnimator;
    }
    if (!paramBoolean3)
    {
      if (localValueAnimator.isRunning()) {
        localValueAnimator.end();
      } else {
        endAnimatorWithoutCallbacks(new ValueAnimator[] { localValueAnimator });
      }
      return super.setVisible(paramBoolean1, false);
    }
    if ((paramBoolean3) && (localValueAnimator.isRunning())) {
      return false;
    }
    if ((paramBoolean1) && (!super.setVisible(paramBoolean1, false))) {
      paramBoolean3 = false;
    } else {
      paramBoolean3 = true;
    }
    BaseProgressIndicatorSpec localBaseProgressIndicatorSpec = this.baseSpec;
    if (paramBoolean1) {
      paramBoolean1 = localBaseProgressIndicatorSpec.isShowAnimationEnabled();
    } else {
      paramBoolean1 = localBaseProgressIndicatorSpec.isHideAnimationEnabled();
    }
    if (!paramBoolean1)
    {
      endAnimatorWithoutCallbacks(new ValueAnimator[] { localValueAnimator });
      return paramBoolean3;
    }
    if ((!paramBoolean2) && (Build.VERSION.SDK_INT >= 19) && (localValueAnimator.isPaused())) {
      localValueAnimator.resume();
    } else {
      localValueAnimator.start();
    }
    return paramBoolean3;
  }
  
  public void start()
  {
    setVisibleInternal(true, true, false);
  }
  
  public void stop()
  {
    setVisibleInternal(false, true, false);
  }
  
  public boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback paramAnimationCallback)
  {
    List localList = this.animationCallbacks;
    if ((localList != null) && (localList.contains(paramAnimationCallback)))
    {
      this.animationCallbacks.remove(paramAnimationCallback);
      if (this.animationCallbacks.isEmpty()) {
        this.animationCallbacks = null;
      }
      return true;
    }
    return false;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/DrawableWithAnimatedVisibilityChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */