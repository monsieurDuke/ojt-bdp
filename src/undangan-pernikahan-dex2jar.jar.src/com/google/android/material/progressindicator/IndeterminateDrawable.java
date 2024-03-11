package com.google.android.material.progressindicator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;

public final class IndeterminateDrawable<S extends BaseProgressIndicatorSpec>
  extends DrawableWithAnimatedVisibilityChange
{
  private IndeterminateAnimatorDelegate<ObjectAnimator> animatorDelegate;
  private DrawingDelegate<S> drawingDelegate;
  
  IndeterminateDrawable(Context paramContext, BaseProgressIndicatorSpec paramBaseProgressIndicatorSpec, DrawingDelegate<S> paramDrawingDelegate, IndeterminateAnimatorDelegate<ObjectAnimator> paramIndeterminateAnimatorDelegate)
  {
    super(paramContext, paramBaseProgressIndicatorSpec);
    setDrawingDelegate(paramDrawingDelegate);
    setAnimatorDelegate(paramIndeterminateAnimatorDelegate);
  }
  
  public static IndeterminateDrawable<CircularProgressIndicatorSpec> createCircularDrawable(Context paramContext, CircularProgressIndicatorSpec paramCircularProgressIndicatorSpec)
  {
    return new IndeterminateDrawable(paramContext, paramCircularProgressIndicatorSpec, new CircularDrawingDelegate(paramCircularProgressIndicatorSpec), new CircularIndeterminateAnimatorDelegate(paramCircularProgressIndicatorSpec));
  }
  
  public static IndeterminateDrawable<LinearProgressIndicatorSpec> createLinearDrawable(Context paramContext, LinearProgressIndicatorSpec paramLinearProgressIndicatorSpec)
  {
    LinearDrawingDelegate localLinearDrawingDelegate = new LinearDrawingDelegate(paramLinearProgressIndicatorSpec);
    Object localObject;
    if (paramLinearProgressIndicatorSpec.indeterminateAnimationType == 0) {
      localObject = new LinearIndeterminateContiguousAnimatorDelegate(paramLinearProgressIndicatorSpec);
    } else {
      localObject = new LinearIndeterminateDisjointAnimatorDelegate(paramContext, paramLinearProgressIndicatorSpec);
    }
    return new IndeterminateDrawable(paramContext, paramLinearProgressIndicatorSpec, localLinearDrawingDelegate, (IndeterminateAnimatorDelegate)localObject);
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = new Rect();
    if ((!getBounds().isEmpty()) && (isVisible()) && (paramCanvas.getClipBounds(localRect)))
    {
      paramCanvas.save();
      this.drawingDelegate.validateSpecAndAdjustCanvas(paramCanvas, getGrowFraction());
      this.drawingDelegate.fillTrack(paramCanvas, this.paint);
      for (int i = 0; i < this.animatorDelegate.segmentColors.length; i++) {
        this.drawingDelegate.fillIndicator(paramCanvas, this.paint, this.animatorDelegate.segmentPositions[(i * 2)], this.animatorDelegate.segmentPositions[(i * 2 + 1)], this.animatorDelegate.segmentColors[i]);
      }
      paramCanvas.restore();
      return;
    }
  }
  
  IndeterminateAnimatorDelegate<ObjectAnimator> getAnimatorDelegate()
  {
    return this.animatorDelegate;
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
  
  void setAnimatorDelegate(IndeterminateAnimatorDelegate<ObjectAnimator> paramIndeterminateAnimatorDelegate)
  {
    this.animatorDelegate = paramIndeterminateAnimatorDelegate;
    paramIndeterminateAnimatorDelegate.registerDrawable(this);
  }
  
  void setDrawingDelegate(DrawingDelegate<S> paramDrawingDelegate)
  {
    this.drawingDelegate = paramDrawingDelegate;
    paramDrawingDelegate.registerDrawable(this);
  }
  
  boolean setVisibleInternal(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    paramBoolean2 = super.setVisibleInternal(paramBoolean1, paramBoolean2, paramBoolean3);
    if (!isRunning()) {
      this.animatorDelegate.cancelAnimatorImmediately();
    }
    float f = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
    if ((paramBoolean1) && ((paramBoolean3) || ((Build.VERSION.SDK_INT <= 21) && (f > 0.0F)))) {
      this.animatorDelegate.startAnimator();
    }
    return paramBoolean2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/IndeterminateDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */