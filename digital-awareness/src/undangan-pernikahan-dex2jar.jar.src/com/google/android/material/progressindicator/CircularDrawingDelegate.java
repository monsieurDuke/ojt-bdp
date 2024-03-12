package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import com.google.android.material.color.MaterialColors;

final class CircularDrawingDelegate
  extends DrawingDelegate<CircularProgressIndicatorSpec>
{
  private float adjustedRadius;
  private int arcDirectionFactor = 1;
  private float displayedCornerRadius;
  private float displayedTrackThickness;
  
  public CircularDrawingDelegate(CircularProgressIndicatorSpec paramCircularProgressIndicatorSpec)
  {
    super(paramCircularProgressIndicatorSpec);
  }
  
  private void drawRoundedEnd(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramCanvas.save();
    paramCanvas.rotate(paramFloat3);
    paramFloat3 = this.adjustedRadius;
    paramCanvas.drawRoundRect(new RectF(paramFloat3 - paramFloat1 / 2.0F, paramFloat2, paramFloat3 + paramFloat1 / 2.0F, -paramFloat2), paramFloat2, paramFloat2, paramPaint);
    paramCanvas.restore();
  }
  
  private int getSize()
  {
    return ((CircularProgressIndicatorSpec)this.spec).indicatorSize + ((CircularProgressIndicatorSpec)this.spec).indicatorInset * 2;
  }
  
  public void adjustCanvas(Canvas paramCanvas, float paramFloat)
  {
    float f = ((CircularProgressIndicatorSpec)this.spec).indicatorSize / 2.0F + ((CircularProgressIndicatorSpec)this.spec).indicatorInset;
    paramCanvas.translate(f, f);
    paramCanvas.rotate(-90.0F);
    paramCanvas.clipRect(-f, -f, f, f);
    int i;
    if (((CircularProgressIndicatorSpec)this.spec).indicatorDirection == 0) {
      i = 1;
    } else {
      i = -1;
    }
    this.arcDirectionFactor = i;
    this.displayedTrackThickness = (((CircularProgressIndicatorSpec)this.spec).trackThickness * paramFloat);
    this.displayedCornerRadius = (((CircularProgressIndicatorSpec)this.spec).trackCornerRadius * paramFloat);
    this.adjustedRadius = ((((CircularProgressIndicatorSpec)this.spec).indicatorSize - ((CircularProgressIndicatorSpec)this.spec).trackThickness) / 2.0F);
    if (((this.drawable.isShowing()) && (((CircularProgressIndicatorSpec)this.spec).showAnimationBehavior == 2)) || ((this.drawable.isHiding()) && (((CircularProgressIndicatorSpec)this.spec).hideAnimationBehavior == 1))) {
      this.adjustedRadius += (1.0F - paramFloat) * ((CircularProgressIndicatorSpec)this.spec).trackThickness / 2.0F;
    } else if (((this.drawable.isShowing()) && (((CircularProgressIndicatorSpec)this.spec).showAnimationBehavior == 1)) || ((this.drawable.isHiding()) && (((CircularProgressIndicatorSpec)this.spec).hideAnimationBehavior == 2))) {
      this.adjustedRadius -= (1.0F - paramFloat) * ((CircularProgressIndicatorSpec)this.spec).trackThickness / 2.0F;
    }
  }
  
  void fillIndicator(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, int paramInt)
  {
    if (paramFloat1 == paramFloat2) {
      return;
    }
    paramPaint.setStyle(Paint.Style.STROKE);
    paramPaint.setStrokeCap(Paint.Cap.BUTT);
    paramPaint.setAntiAlias(true);
    paramPaint.setColor(paramInt);
    paramPaint.setStrokeWidth(this.displayedTrackThickness);
    paramInt = this.arcDirectionFactor;
    float f = paramFloat1 * 360.0F * paramInt;
    if (paramFloat2 >= paramFloat1) {
      paramFloat1 = (paramFloat2 - paramFloat1) * 360.0F * paramInt;
    } else {
      paramFloat1 = (paramFloat2 + 1.0F - paramFloat1) * 360.0F * paramInt;
    }
    paramFloat2 = this.adjustedRadius;
    paramCanvas.drawArc(new RectF(-paramFloat2, -paramFloat2, paramFloat2, paramFloat2), f, paramFloat1, false, paramPaint);
    if ((this.displayedCornerRadius > 0.0F) && (Math.abs(paramFloat1) < 360.0F))
    {
      paramPaint.setStyle(Paint.Style.FILL);
      drawRoundedEnd(paramCanvas, paramPaint, this.displayedTrackThickness, this.displayedCornerRadius, f);
      drawRoundedEnd(paramCanvas, paramPaint, this.displayedTrackThickness, this.displayedCornerRadius, f + paramFloat1);
    }
  }
  
  void fillTrack(Canvas paramCanvas, Paint paramPaint)
  {
    int i = MaterialColors.compositeARGBWithAlpha(((CircularProgressIndicatorSpec)this.spec).trackColor, this.drawable.getAlpha());
    paramPaint.setStyle(Paint.Style.STROKE);
    paramPaint.setStrokeCap(Paint.Cap.BUTT);
    paramPaint.setAntiAlias(true);
    paramPaint.setColor(i);
    paramPaint.setStrokeWidth(this.displayedTrackThickness);
    float f = this.adjustedRadius;
    paramCanvas.drawArc(new RectF(-f, -f, f, f), 0.0F, 360.0F, false, paramPaint);
  }
  
  public int getPreferredHeight()
  {
    return getSize();
  }
  
  public int getPreferredWidth()
  {
    return getSize();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/CircularDrawingDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */