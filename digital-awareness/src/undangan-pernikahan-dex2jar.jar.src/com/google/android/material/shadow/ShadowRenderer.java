package com.google.android.material.shadow;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import androidx.core.graphics.ColorUtils;

public class ShadowRenderer
{
  private static final int COLOR_ALPHA_END = 0;
  private static final int COLOR_ALPHA_MIDDLE = 20;
  private static final int COLOR_ALPHA_START = 68;
  private static final int[] cornerColors = new int[4];
  private static final float[] cornerPositions = { 0.0F, 0.0F, 0.5F, 1.0F };
  private static final int[] edgeColors = new int[3];
  private static final float[] edgePositions = { 0.0F, 0.5F, 1.0F };
  private final Paint cornerShadowPaint;
  private final Paint edgeShadowPaint;
  private final Path scratch = new Path();
  private int shadowEndColor;
  private int shadowMiddleColor;
  private final Paint shadowPaint = new Paint();
  private int shadowStartColor;
  private Paint transparentPaint = new Paint();
  
  public ShadowRenderer()
  {
    this(-16777216);
  }
  
  public ShadowRenderer(int paramInt)
  {
    setShadowColor(paramInt);
    this.transparentPaint.setColor(0);
    Paint localPaint = new Paint(4);
    this.cornerShadowPaint = localPaint;
    localPaint.setStyle(Paint.Style.FILL);
    this.edgeShadowPaint = new Paint(localPaint);
  }
  
  public void drawCornerShadow(Canvas paramCanvas, Matrix paramMatrix, RectF paramRectF, int paramInt, float paramFloat1, float paramFloat2)
  {
    int i;
    if (paramFloat2 < 0.0F) {
      i = 1;
    } else {
      i = 0;
    }
    Path localPath = this.scratch;
    if (i != 0)
    {
      localObject = cornerColors;
      localObject[0] = 0;
      localObject[1] = this.shadowEndColor;
      localObject[2] = this.shadowMiddleColor;
      localObject[3] = this.shadowStartColor;
    }
    else
    {
      localPath.rewind();
      localPath.moveTo(paramRectF.centerX(), paramRectF.centerY());
      localPath.arcTo(paramRectF, paramFloat1, paramFloat2);
      localPath.close();
      paramRectF.inset(-paramInt, -paramInt);
      localObject = cornerColors;
      localObject[0] = 0;
      localObject[1] = this.shadowStartColor;
      localObject[2] = this.shadowMiddleColor;
      localObject[3] = this.shadowEndColor;
    }
    float f2 = paramRectF.width() / 2.0F;
    if (f2 <= 0.0F) {
      return;
    }
    float f1 = 1.0F - paramInt / f2;
    float f3 = (1.0F - f1) / 2.0F;
    Object localObject = cornerPositions;
    localObject[1] = f1;
    localObject[2] = (f1 + f3);
    localObject = new RadialGradient(paramRectF.centerX(), paramRectF.centerY(), f2, cornerColors, (float[])localObject, Shader.TileMode.CLAMP);
    this.cornerShadowPaint.setShader((Shader)localObject);
    paramCanvas.save();
    paramCanvas.concat(paramMatrix);
    paramCanvas.scale(1.0F, paramRectF.height() / paramRectF.width());
    if (i == 0)
    {
      paramCanvas.clipPath(localPath, Region.Op.DIFFERENCE);
      paramCanvas.drawPath(localPath, this.transparentPaint);
    }
    paramCanvas.drawArc(paramRectF, paramFloat1, paramFloat2, true, this.cornerShadowPaint);
    paramCanvas.restore();
  }
  
  public void drawEdgeShadow(Canvas paramCanvas, Matrix paramMatrix, RectF paramRectF, int paramInt)
  {
    paramRectF.bottom += paramInt;
    paramRectF.offset(0.0F, -paramInt);
    int[] arrayOfInt = edgeColors;
    arrayOfInt[0] = this.shadowEndColor;
    arrayOfInt[1] = this.shadowMiddleColor;
    arrayOfInt[2] = this.shadowStartColor;
    this.edgeShadowPaint.setShader(new LinearGradient(paramRectF.left, paramRectF.top, paramRectF.left, paramRectF.bottom, arrayOfInt, edgePositions, Shader.TileMode.CLAMP));
    paramCanvas.save();
    paramCanvas.concat(paramMatrix);
    paramCanvas.drawRect(paramRectF, this.edgeShadowPaint);
    paramCanvas.restore();
  }
  
  public Paint getShadowPaint()
  {
    return this.shadowPaint;
  }
  
  public void setShadowColor(int paramInt)
  {
    this.shadowStartColor = ColorUtils.setAlphaComponent(paramInt, 68);
    this.shadowMiddleColor = ColorUtils.setAlphaComponent(paramInt, 20);
    this.shadowEndColor = ColorUtils.setAlphaComponent(paramInt, 0);
    this.shadowPaint.setColor(this.shadowStartColor);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shadow/ShadowRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */