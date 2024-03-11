package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import com.google.android.material.color.MaterialColors;

final class LinearDrawingDelegate
  extends DrawingDelegate<LinearProgressIndicatorSpec>
{
  private float displayedCornerRadius;
  private float displayedTrackThickness;
  private float trackLength = 300.0F;
  
  public LinearDrawingDelegate(LinearProgressIndicatorSpec paramLinearProgressIndicatorSpec)
  {
    super(paramLinearProgressIndicatorSpec);
  }
  
  public void adjustCanvas(Canvas paramCanvas, float paramFloat)
  {
    Rect localRect = paramCanvas.getClipBounds();
    this.trackLength = localRect.width();
    float f2 = ((LinearProgressIndicatorSpec)this.spec).trackThickness;
    paramCanvas.translate(localRect.left + localRect.width() / 2.0F, localRect.top + localRect.height() / 2.0F + Math.max(0.0F, (localRect.height() - ((LinearProgressIndicatorSpec)this.spec).trackThickness) / 2.0F));
    if (((LinearProgressIndicatorSpec)this.spec).drawHorizontallyInverse) {
      paramCanvas.scale(-1.0F, 1.0F);
    }
    if (((this.drawable.isShowing()) && (((LinearProgressIndicatorSpec)this.spec).showAnimationBehavior == 1)) || ((this.drawable.isHiding()) && (((LinearProgressIndicatorSpec)this.spec).hideAnimationBehavior == 2))) {
      paramCanvas.scale(1.0F, -1.0F);
    }
    if ((this.drawable.isShowing()) || (this.drawable.isHiding())) {
      paramCanvas.translate(0.0F, ((LinearProgressIndicatorSpec)this.spec).trackThickness * (paramFloat - 1.0F) / 2.0F);
    }
    float f1 = this.trackLength;
    paramCanvas.clipRect(-f1 / 2.0F, -f2 / 2.0F, f1 / 2.0F, f2 / 2.0F);
    this.displayedTrackThickness = (((LinearProgressIndicatorSpec)this.spec).trackThickness * paramFloat);
    this.displayedCornerRadius = (((LinearProgressIndicatorSpec)this.spec).trackCornerRadius * paramFloat);
  }
  
  public void fillIndicator(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, int paramInt)
  {
    if (paramFloat1 == paramFloat2) {
      return;
    }
    float f4 = this.trackLength;
    float f1 = -f4 / 2.0F;
    float f5 = this.displayedCornerRadius;
    float f2 = -f4 / 2.0F;
    paramPaint.setStyle(Paint.Style.FILL);
    paramPaint.setAntiAlias(true);
    paramPaint.setColor(paramInt);
    float f3 = this.displayedTrackThickness;
    RectF localRectF = new RectF(f1 + (f4 - f5 * 2.0F) * paramFloat1, -f3 / 2.0F, f2 + (f4 - f5 * 2.0F) * paramFloat2 + f5 * 2.0F, f3 / 2.0F);
    paramFloat1 = this.displayedCornerRadius;
    paramCanvas.drawRoundRect(localRectF, paramFloat1, paramFloat1, paramPaint);
  }
  
  void fillTrack(Canvas paramCanvas, Paint paramPaint)
  {
    int i = MaterialColors.compositeARGBWithAlpha(((LinearProgressIndicatorSpec)this.spec).trackColor, this.drawable.getAlpha());
    paramPaint.setStyle(Paint.Style.FILL);
    paramPaint.setAntiAlias(true);
    paramPaint.setColor(i);
    float f1 = this.trackLength;
    float f3 = -f1 / 2.0F;
    float f2 = this.displayedTrackThickness;
    RectF localRectF = new RectF(f3, -f2 / 2.0F, f1 / 2.0F, f2 / 2.0F);
    f1 = this.displayedCornerRadius;
    paramCanvas.drawRoundRect(localRectF, f1, f1, paramPaint);
  }
  
  public int getPreferredHeight()
  {
    return ((LinearProgressIndicatorSpec)this.spec).trackThickness;
  }
  
  public int getPreferredWidth()
  {
    return -1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/LinearDrawingDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */