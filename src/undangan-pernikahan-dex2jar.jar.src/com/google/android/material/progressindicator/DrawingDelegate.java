package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;

abstract class DrawingDelegate<S extends BaseProgressIndicatorSpec>
{
  protected DrawableWithAnimatedVisibilityChange drawable;
  S spec;
  
  public DrawingDelegate(S paramS)
  {
    this.spec = paramS;
  }
  
  abstract void adjustCanvas(Canvas paramCanvas, float paramFloat);
  
  abstract void fillIndicator(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, int paramInt);
  
  abstract void fillTrack(Canvas paramCanvas, Paint paramPaint);
  
  abstract int getPreferredHeight();
  
  abstract int getPreferredWidth();
  
  protected void registerDrawable(DrawableWithAnimatedVisibilityChange paramDrawableWithAnimatedVisibilityChange)
  {
    this.drawable = paramDrawableWithAnimatedVisibilityChange;
  }
  
  void validateSpecAndAdjustCanvas(Canvas paramCanvas, float paramFloat)
  {
    this.spec.validateSpec();
    adjustCanvas(paramCanvas, paramFloat);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/DrawingDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */