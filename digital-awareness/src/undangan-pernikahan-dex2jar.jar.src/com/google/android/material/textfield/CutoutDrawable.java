package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.os.Build.VERSION;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

class CutoutDrawable
  extends MaterialShapeDrawable
{
  private final RectF cutoutBounds;
  private final Paint cutoutPaint = new Paint(1);
  
  CutoutDrawable()
  {
    this(null);
  }
  
  CutoutDrawable(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    super(paramShapeAppearanceModel);
    setPaintStyles();
    this.cutoutBounds = new RectF();
  }
  
  private void setPaintStyles()
  {
    this.cutoutPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.cutoutPaint.setColor(-1);
    this.cutoutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
  }
  
  protected void drawStrokeShape(Canvas paramCanvas)
  {
    if (this.cutoutBounds.isEmpty())
    {
      super.drawStrokeShape(paramCanvas);
    }
    else
    {
      paramCanvas.save();
      if (Build.VERSION.SDK_INT >= 26) {
        paramCanvas.clipOutRect(this.cutoutBounds);
      } else {
        paramCanvas.clipRect(this.cutoutBounds, Region.Op.DIFFERENCE);
      }
      super.drawStrokeShape(paramCanvas);
      paramCanvas.restore();
    }
  }
  
  boolean hasCutout()
  {
    return this.cutoutBounds.isEmpty() ^ true;
  }
  
  void removeCutout()
  {
    setCutout(0.0F, 0.0F, 0.0F, 0.0F);
  }
  
  void setCutout(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if ((paramFloat1 != this.cutoutBounds.left) || (paramFloat2 != this.cutoutBounds.top) || (paramFloat3 != this.cutoutBounds.right) || (paramFloat4 != this.cutoutBounds.bottom))
    {
      this.cutoutBounds.set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      invalidateSelf();
    }
  }
  
  void setCutout(RectF paramRectF)
  {
    setCutout(paramRectF.left, paramRectF.top, paramRectF.right, paramRectF.bottom);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/CutoutDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */