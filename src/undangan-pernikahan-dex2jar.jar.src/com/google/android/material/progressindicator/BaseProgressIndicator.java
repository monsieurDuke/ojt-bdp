package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public abstract class BaseProgressIndicator<S extends BaseProgressIndicatorSpec>
  extends ProgressBar
{
  static final float DEFAULT_OPACITY = 0.2F;
  static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ProgressIndicator;
  public static final int HIDE_INWARD = 2;
  public static final int HIDE_NONE = 0;
  public static final int HIDE_OUTWARD = 1;
  static final int MAX_ALPHA = 255;
  static final int MAX_HIDE_DELAY = 1000;
  public static final int SHOW_INWARD = 2;
  public static final int SHOW_NONE = 0;
  public static final int SHOW_OUTWARD = 1;
  AnimatorDurationScaleProvider animatorDurationScaleProvider;
  private final Runnable delayedHide = new Runnable()
  {
    public void run()
    {
      BaseProgressIndicator.this.internalHide();
      BaseProgressIndicator.access$202(BaseProgressIndicator.this, -1L);
    }
  };
  private final Runnable delayedShow = new Runnable()
  {
    public void run()
    {
      BaseProgressIndicator.this.internalShow();
    }
  };
  private final Animatable2Compat.AnimationCallback hideAnimationCallback = new Animatable2Compat.AnimationCallback()
  {
    public void onAnimationEnd(Drawable paramAnonymousDrawable)
    {
      super.onAnimationEnd(paramAnonymousDrawable);
      if (!BaseProgressIndicator.this.isIndeterminateModeChangeRequested)
      {
        paramAnonymousDrawable = BaseProgressIndicator.this;
        paramAnonymousDrawable.setVisibility(paramAnonymousDrawable.visibilityAfterHide);
      }
    }
  };
  private boolean isIndeterminateModeChangeRequested = false;
  private boolean isParentDoneInitializing;
  private long lastShowStartTime = -1L;
  private final int minHideDelay;
  private final int showDelay;
  S spec;
  private int storedProgress;
  private boolean storedProgressAnimated;
  private final Animatable2Compat.AnimationCallback switchIndeterminateModeCallback = new Animatable2Compat.AnimationCallback()
  {
    public void onAnimationEnd(Drawable paramAnonymousDrawable)
    {
      BaseProgressIndicator.this.setIndeterminate(false);
      paramAnonymousDrawable = BaseProgressIndicator.this;
      paramAnonymousDrawable.setProgressCompat(paramAnonymousDrawable.storedProgress, BaseProgressIndicator.this.storedProgressAnimated);
    }
  };
  private int visibilityAfterHide = 4;
  
  protected BaseProgressIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt1, DEF_STYLE_RES), paramAttributeSet, paramInt1);
    paramContext = getContext();
    this.spec = createSpec(paramContext, paramAttributeSet);
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.BaseProgressIndicator, paramInt1, paramInt2, new int[0]);
    this.showDelay = paramContext.getInt(R.styleable.BaseProgressIndicator_showDelay, -1);
    this.minHideDelay = Math.min(paramContext.getInt(R.styleable.BaseProgressIndicator_minHideDelay, -1), 1000);
    paramContext.recycle();
    this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
    this.isParentDoneInitializing = true;
  }
  
  private DrawingDelegate<S> getCurrentDrawingDelegate()
  {
    boolean bool = isIndeterminate();
    Object localObject2 = null;
    Object localObject1 = null;
    if (bool)
    {
      if (getIndeterminateDrawable() != null) {
        localObject1 = getIndeterminateDrawable().getDrawingDelegate();
      }
      return (DrawingDelegate<S>)localObject1;
    }
    if (getProgressDrawable() == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = getProgressDrawable().getDrawingDelegate();
    }
    return (DrawingDelegate<S>)localObject1;
  }
  
  private void internalHide()
  {
    ((DrawableWithAnimatedVisibilityChange)getCurrentDrawable()).setVisible(false, false, true);
    if (isNoLongerNeedToBeVisible()) {
      setVisibility(4);
    }
  }
  
  private void internalShow()
  {
    if (this.minHideDelay > 0) {
      this.lastShowStartTime = SystemClock.uptimeMillis();
    }
    setVisibility(0);
  }
  
  private boolean isNoLongerNeedToBeVisible()
  {
    boolean bool;
    if (((getProgressDrawable() != null) && (getProgressDrawable().isVisible())) || ((getIndeterminateDrawable() != null) && (getIndeterminateDrawable().isVisible()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void registerAnimationCallbacks()
  {
    if ((getProgressDrawable() != null) && (getIndeterminateDrawable() != null)) {
      getIndeterminateDrawable().getAnimatorDelegate().registerAnimatorsCompleteCallback(this.switchIndeterminateModeCallback);
    }
    if (getProgressDrawable() != null) {
      getProgressDrawable().registerAnimationCallback(this.hideAnimationCallback);
    }
    if (getIndeterminateDrawable() != null) {
      getIndeterminateDrawable().registerAnimationCallback(this.hideAnimationCallback);
    }
  }
  
  private void unregisterAnimationCallbacks()
  {
    if (getIndeterminateDrawable() != null)
    {
      getIndeterminateDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
      getIndeterminateDrawable().getAnimatorDelegate().unregisterAnimatorsCompleteCallback();
    }
    if (getProgressDrawable() != null) {
      getProgressDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
    }
  }
  
  protected void applyNewVisibility(boolean paramBoolean)
  {
    if (!this.isParentDoneInitializing) {
      return;
    }
    ((DrawableWithAnimatedVisibilityChange)getCurrentDrawable()).setVisible(visibleToUser(), false, paramBoolean);
  }
  
  abstract S createSpec(Context paramContext, AttributeSet paramAttributeSet);
  
  public Drawable getCurrentDrawable()
  {
    Object localObject;
    if (isIndeterminate()) {
      localObject = getIndeterminateDrawable();
    } else {
      localObject = getProgressDrawable();
    }
    return (Drawable)localObject;
  }
  
  public int getHideAnimationBehavior()
  {
    return this.spec.hideAnimationBehavior;
  }
  
  public IndeterminateDrawable<S> getIndeterminateDrawable()
  {
    return (IndeterminateDrawable)super.getIndeterminateDrawable();
  }
  
  public int[] getIndicatorColor()
  {
    return this.spec.indicatorColors;
  }
  
  public DeterminateDrawable<S> getProgressDrawable()
  {
    return (DeterminateDrawable)super.getProgressDrawable();
  }
  
  public int getShowAnimationBehavior()
  {
    return this.spec.showAnimationBehavior;
  }
  
  public int getTrackColor()
  {
    return this.spec.trackColor;
  }
  
  public int getTrackCornerRadius()
  {
    return this.spec.trackCornerRadius;
  }
  
  public int getTrackThickness()
  {
    return this.spec.trackThickness;
  }
  
  public void hide()
  {
    if (getVisibility() != 0)
    {
      removeCallbacks(this.delayedShow);
      return;
    }
    removeCallbacks(this.delayedHide);
    long l = SystemClock.uptimeMillis() - this.lastShowStartTime;
    int j = this.minHideDelay;
    int i;
    if (l >= j) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.delayedHide.run();
      return;
    }
    postDelayed(this.delayedHide, j - l);
  }
  
  public void invalidate()
  {
    super.invalidate();
    if (getCurrentDrawable() != null) {
      getCurrentDrawable().invalidateSelf();
    }
  }
  
  boolean isEffectivelyVisible()
  {
    for (Object localObject = this;; localObject = (View)localObject)
    {
      int i = ((View)localObject).getVisibility();
      boolean bool = false;
      if (i != 0) {
        return false;
      }
      localObject = ((View)localObject).getParent();
      if (localObject == null)
      {
        if (getWindowVisibility() == 0) {
          bool = true;
        }
        return bool;
      }
      if (!(localObject instanceof View)) {
        return true;
      }
    }
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    registerAnimationCallbacks();
    if (visibleToUser()) {
      internalShow();
    }
  }
  
  protected void onDetachedFromWindow()
  {
    removeCallbacks(this.delayedHide);
    removeCallbacks(this.delayedShow);
    ((DrawableWithAnimatedVisibilityChange)getCurrentDrawable()).hideNow();
    unregisterAnimationCallbacks();
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    try
    {
      int i = paramCanvas.save();
      if ((getPaddingLeft() != 0) || (getPaddingTop() != 0)) {
        paramCanvas.translate(getPaddingLeft(), getPaddingTop());
      }
      if ((getPaddingRight() != 0) || (getPaddingBottom() != 0)) {
        paramCanvas.clipRect(0, 0, getWidth() - (getPaddingLeft() + getPaddingRight()), getHeight() - (getPaddingTop() + getPaddingBottom()));
      }
      getCurrentDrawable().draw(paramCanvas);
      paramCanvas.restoreToCount(i);
      return;
    }
    finally {}
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    try
    {
      super.onMeasure(paramInt1, paramInt2);
      DrawingDelegate localDrawingDelegate = getCurrentDrawingDelegate();
      if (localDrawingDelegate == null) {
        return;
      }
      paramInt1 = localDrawingDelegate.getPreferredWidth();
      paramInt2 = localDrawingDelegate.getPreferredHeight();
      if (paramInt1 < 0) {
        paramInt1 = getMeasuredWidth();
      } else {
        paramInt1 = getPaddingLeft() + paramInt1 + getPaddingRight();
      }
      if (paramInt2 < 0) {
        paramInt2 = getMeasuredHeight();
      } else {
        paramInt2 = getPaddingTop() + paramInt2 + getPaddingBottom();
      }
      setMeasuredDimension(paramInt1, paramInt2);
      return;
    }
    finally {}
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
    boolean bool;
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    }
    applyNewVisibility(bool);
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    applyNewVisibility(false);
  }
  
  public void setAnimatorDurationScaleProvider(AnimatorDurationScaleProvider paramAnimatorDurationScaleProvider)
  {
    this.animatorDurationScaleProvider = paramAnimatorDurationScaleProvider;
    if (getProgressDrawable() != null) {
      getProgressDrawable().animatorDurationScaleProvider = paramAnimatorDurationScaleProvider;
    }
    if (getIndeterminateDrawable() != null) {
      getIndeterminateDrawable().animatorDurationScaleProvider = paramAnimatorDurationScaleProvider;
    }
  }
  
  public void setHideAnimationBehavior(int paramInt)
  {
    this.spec.hideAnimationBehavior = paramInt;
    invalidate();
  }
  
  public void setIndeterminate(boolean paramBoolean)
  {
    try
    {
      boolean bool = isIndeterminate();
      if (paramBoolean == bool) {
        return;
      }
      DrawableWithAnimatedVisibilityChange localDrawableWithAnimatedVisibilityChange = (DrawableWithAnimatedVisibilityChange)getCurrentDrawable();
      if (localDrawableWithAnimatedVisibilityChange != null) {
        localDrawableWithAnimatedVisibilityChange.hideNow();
      }
      super.setIndeterminate(paramBoolean);
      localDrawableWithAnimatedVisibilityChange = (DrawableWithAnimatedVisibilityChange)getCurrentDrawable();
      if (localDrawableWithAnimatedVisibilityChange != null) {
        localDrawableWithAnimatedVisibilityChange.setVisible(visibleToUser(), false, false);
      }
      if (((localDrawableWithAnimatedVisibilityChange instanceof IndeterminateDrawable)) && (visibleToUser())) {
        ((IndeterminateDrawable)localDrawableWithAnimatedVisibilityChange).getAnimatorDelegate().startAnimator();
      }
      this.isIndeterminateModeChangeRequested = false;
      return;
    }
    finally {}
  }
  
  public void setIndeterminateDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == null)
    {
      super.setIndeterminateDrawable(null);
      return;
    }
    if ((paramDrawable instanceof IndeterminateDrawable))
    {
      ((DrawableWithAnimatedVisibilityChange)paramDrawable).hideNow();
      super.setIndeterminateDrawable(paramDrawable);
      return;
    }
    throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
  }
  
  public void setIndicatorColor(int... paramVarArgs)
  {
    int[] arrayOfInt = paramVarArgs;
    if (paramVarArgs.length == 0) {
      arrayOfInt = new int[] { MaterialColors.getColor(getContext(), R.attr.colorPrimary, -1) };
    }
    if (!Arrays.equals(getIndicatorColor(), arrayOfInt))
    {
      this.spec.indicatorColors = arrayOfInt;
      getIndeterminateDrawable().getAnimatorDelegate().invalidateSpecValues();
      invalidate();
    }
  }
  
  public void setProgress(int paramInt)
  {
    try
    {
      boolean bool = isIndeterminate();
      if (bool) {
        return;
      }
      setProgressCompat(paramInt, false);
      return;
    }
    finally {}
  }
  
  public void setProgressCompat(int paramInt, boolean paramBoolean)
  {
    if (isIndeterminate())
    {
      if (getProgressDrawable() != null)
      {
        this.storedProgress = paramInt;
        this.storedProgressAnimated = paramBoolean;
        this.isIndeterminateModeChangeRequested = true;
        if ((getIndeterminateDrawable().isVisible()) && (this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(getContext().getContentResolver()) != 0.0F)) {
          getIndeterminateDrawable().getAnimatorDelegate().requestCancelAnimatorAfterCurrentCycle();
        } else {
          this.switchIndeterminateModeCallback.onAnimationEnd(getIndeterminateDrawable());
        }
      }
    }
    else
    {
      super.setProgress(paramInt);
      if ((getProgressDrawable() != null) && (!paramBoolean)) {
        getProgressDrawable().jumpToCurrentState();
      }
    }
  }
  
  public void setProgressDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == null)
    {
      super.setProgressDrawable(null);
      return;
    }
    if ((paramDrawable instanceof DeterminateDrawable))
    {
      paramDrawable = (DeterminateDrawable)paramDrawable;
      paramDrawable.hideNow();
      super.setProgressDrawable(paramDrawable);
      paramDrawable.setLevelByFraction(getProgress() / getMax());
      return;
    }
    throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
  }
  
  public void setShowAnimationBehavior(int paramInt)
  {
    this.spec.showAnimationBehavior = paramInt;
    invalidate();
  }
  
  public void setTrackColor(int paramInt)
  {
    if (this.spec.trackColor != paramInt)
    {
      this.spec.trackColor = paramInt;
      invalidate();
    }
  }
  
  public void setTrackCornerRadius(int paramInt)
  {
    if (this.spec.trackCornerRadius != paramInt)
    {
      BaseProgressIndicatorSpec localBaseProgressIndicatorSpec = this.spec;
      localBaseProgressIndicatorSpec.trackCornerRadius = Math.min(paramInt, localBaseProgressIndicatorSpec.trackThickness / 2);
    }
  }
  
  public void setTrackThickness(int paramInt)
  {
    if (this.spec.trackThickness != paramInt)
    {
      this.spec.trackThickness = paramInt;
      requestLayout();
    }
  }
  
  public void setVisibilityAfterHide(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 4) && (paramInt != 8)) {
      throw new IllegalArgumentException("The component's visibility must be one of VISIBLE, INVISIBLE, and GONE defined in View.");
    }
    this.visibilityAfterHide = paramInt;
  }
  
  public void show()
  {
    if (this.showDelay > 0)
    {
      removeCallbacks(this.delayedShow);
      postDelayed(this.delayedShow, this.showDelay);
    }
    else
    {
      this.delayedShow.run();
    }
  }
  
  boolean visibleToUser()
  {
    boolean bool;
    if ((ViewCompat.isAttachedToWindow(this)) && (getWindowVisibility() == 0) && (isEffectivelyVisible())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface HideAnimationBehavior {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ShowAnimationBehavior {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/BaseProgressIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */