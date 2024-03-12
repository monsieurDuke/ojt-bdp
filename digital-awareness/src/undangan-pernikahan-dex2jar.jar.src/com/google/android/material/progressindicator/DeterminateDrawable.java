package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.color.MaterialColors;

public final class DeterminateDrawable<S extends BaseProgressIndicatorSpec>
  extends DrawableWithAnimatedVisibilityChange
{
  private static final FloatPropertyCompat<DeterminateDrawable> INDICATOR_LENGTH_IN_LEVEL = new FloatPropertyCompat("indicatorLevel")
  {
    public float getValue(DeterminateDrawable paramAnonymousDeterminateDrawable)
    {
      return paramAnonymousDeterminateDrawable.getIndicatorFraction() * 10000.0F;
    }
    
    public void setValue(DeterminateDrawable paramAnonymousDeterminateDrawable, float paramAnonymousFloat)
    {
      paramAnonymousDeterminateDrawable.setIndicatorFraction(paramAnonymousFloat / 10000.0F);
    }
  };
  private static final int MAX_DRAWABLE_LEVEL = 10000;
  private static final float SPRING_FORCE_STIFFNESS = 50.0F;
  private DrawingDelegate<S> drawingDelegate;
  private float indicatorFraction;
  private boolean skipAnimationOnLevelChange = false;
  private final SpringAnimation springAnimation;
  private final SpringForce springForce;
  
  DeterminateDrawable(Context paramContext, BaseProgressIndicatorSpec paramBaseProgressIndicatorSpec, DrawingDelegate<S> paramDrawingDelegate)
  {
    super(paramContext, paramBaseProgressIndicatorSpec);
    setDrawingDelegate(paramDrawingDelegate);
    paramContext = new SpringForce();
    this.springForce = paramContext;
    paramContext.setDampingRatio(1.0F);
    paramContext.setStiffness(50.0F);
    paramBaseProgressIndicatorSpec = new SpringAnimation(this, INDICATOR_LENGTH_IN_LEVEL);
    this.springAnimation = paramBaseProgressIndicatorSpec;
    paramBaseProgressIndicatorSpec.setSpring(paramContext);
    setGrowFraction(1.0F);
  }
  
  public static DeterminateDrawable<CircularProgressIndicatorSpec> createCircularDrawable(Context paramContext, CircularProgressIndicatorSpec paramCircularProgressIndicatorSpec)
  {
    return new DeterminateDrawable(paramContext, paramCircularProgressIndicatorSpec, new CircularDrawingDelegate(paramCircularProgressIndicatorSpec));
  }
  
  public static DeterminateDrawable<LinearProgressIndicatorSpec> createLinearDrawable(Context paramContext, LinearProgressIndicatorSpec paramLinearProgressIndicatorSpec)
  {
    return new DeterminateDrawable(paramContext, paramLinearProgressIndicatorSpec, new LinearDrawingDelegate(paramLinearProgressIndicatorSpec));
  }
  
  private float getIndicatorFraction()
  {
    return this.indicatorFraction;
  }
  
  private void setIndicatorFraction(float paramFloat)
  {
    this.indicatorFraction = paramFloat;
    invalidateSelf();
  }
  
  public void addSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener paramOnAnimationEndListener)
  {
    this.springAnimation.addEndListener(paramOnAnimationEndListener);
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = new Rect();
    if ((!getBounds().isEmpty()) && (isVisible()) && (paramCanvas.getClipBounds(localRect)))
    {
      paramCanvas.save();
      this.drawingDelegate.validateSpecAndAdjustCanvas(paramCanvas, getGrowFraction());
      this.drawingDelegate.fillTrack(paramCanvas, this.paint);
      int i = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], getAlpha());
      this.drawingDelegate.fillIndicator(paramCanvas, this.paint, 0.0F, getIndicatorFraction(), i);
      paramCanvas.restore();
      return;
    }
  }
  
  DrawingDelegate<S> getDrawingDelegate()
  {
    return this.drawingDelegate;
  }
  
  public int getIntrinsicHeight()
  {
    return this.drawingDelegate.getPreferredHeight();
  }
  
  public int getIntrinsicWidth()
  {
    return this.drawingDelegate.getPreferredWidth();
  }
  
  public void jumpToCurrentState()
  {
    this.springAnimation.skipToEnd();
    setIndicatorFraction(getLevel() / 10000.0F);
  }
  
  protected boolean onLevelChange(int paramInt)
  {
    if (this.skipAnimationOnLevelChange)
    {
      this.springAnimation.skipToEnd();
      setIndicatorFraction(paramInt / 10000.0F);
    }
    else
    {
      this.springAnimation.setStartValue(getIndicatorFraction() * 10000.0F);
      this.springAnimation.animateToFinalPosition(paramInt);
    }
    return true;
  }
  
  public void removeSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener paramOnAnimationEndListener)
  {
    this.springAnimation.removeEndListener(paramOnAnimationEndListener);
  }
  
  void setDrawingDelegate(DrawingDelegate<S> paramDrawingDelegate)
  {
    this.drawingDelegate = paramDrawingDelegate;
    paramDrawingDelegate.registerDrawable(this);
  }
  
  void setLevelByFraction(float paramFloat)
  {
    setLevel((int)(10000.0F * paramFloat));
  }
  
  boolean setVisibleInternal(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    paramBoolean1 = super.setVisibleInternal(paramBoolean1, paramBoolean2, paramBoolean3);
    float f = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
    if (f == 0.0F)
    {
      this.skipAnimationOnLevelChange = true;
    }
    else
    {
      this.skipAnimationOnLevelChange = false;
      this.springForce.setStiffness(50.0F / f);
    }
    return paramBoolean1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/DeterminateDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */