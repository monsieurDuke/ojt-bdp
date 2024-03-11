package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.content.ContextCompat;
import com.google.android.material.R.color;

@Deprecated
public class ShadowDrawableWrapper
  extends DrawableWrapper
{
  static final double COS_45 = Math.cos(Math.toRadians(45.0D));
  static final float SHADOW_BOTTOM_SCALE = 1.0F;
  static final float SHADOW_HORIZ_SCALE = 0.5F;
  static final float SHADOW_MULTIPLIER = 1.5F;
  static final float SHADOW_TOP_SCALE = 0.25F;
  private boolean addPaddingForCorners = true;
  final RectF contentBounds;
  float cornerRadius;
  final Paint cornerShadowPaint;
  Path cornerShadowPath;
  private boolean dirty = true;
  final Paint edgeShadowPaint;
  float maxShadowSize;
  private boolean printedShadowClipWarning = false;
  float rawMaxShadowSize;
  float rawShadowSize;
  private float rotation;
  private final int shadowEndColor;
  private final int shadowMiddleColor;
  float shadowSize;
  private final int shadowStartColor;
  
  public ShadowDrawableWrapper(Context paramContext, Drawable paramDrawable, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramDrawable);
    this.shadowStartColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_start_color);
    this.shadowMiddleColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_mid_color);
    this.shadowEndColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_end_color);
    paramContext = new Paint(5);
    this.cornerShadowPaint = paramContext;
    paramContext.setStyle(Paint.Style.FILL);
    this.cornerRadius = Math.round(paramFloat1);
    this.contentBounds = new RectF();
    paramContext = new Paint(paramContext);
    this.edgeShadowPaint = paramContext;
    paramContext.setAntiAlias(false);
    setShadowSize(paramFloat2, paramFloat3);
  }
  
  private void buildComponents(Rect paramRect)
  {
    float f = this.rawMaxShadowSize * 1.5F;
    this.contentBounds.set(paramRect.left + this.rawMaxShadowSize, paramRect.top + f, paramRect.right - this.rawMaxShadowSize, paramRect.bottom - f);
    getWrappedDrawable().setBounds((int)this.contentBounds.left, (int)this.contentBounds.top, (int)this.contentBounds.right, (int)this.contentBounds.bottom);
    buildShadowCorners();
  }
  
  private void buildShadowCorners()
  {
    float f1 = this.cornerRadius;
    RectF localRectF = new RectF(-f1, -f1, f1, f1);
    Object localObject1 = new RectF(localRectF);
    f1 = this.shadowSize;
    ((RectF)localObject1).inset(-f1, -f1);
    Object localObject2 = this.cornerShadowPath;
    if (localObject2 == null) {
      this.cornerShadowPath = new Path();
    } else {
      ((Path)localObject2).reset();
    }
    this.cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
    this.cornerShadowPath.moveTo(-this.cornerRadius, 0.0F);
    this.cornerShadowPath.rLineTo(-this.shadowSize, 0.0F);
    this.cornerShadowPath.arcTo((RectF)localObject1, 180.0F, 90.0F, false);
    this.cornerShadowPath.arcTo(localRectF, 270.0F, -90.0F, false);
    this.cornerShadowPath.close();
    f1 = -((RectF)localObject1).top;
    if (f1 > 0.0F)
    {
      f2 = this.cornerRadius / f1;
      float f3 = (1.0F - f2) / 2.0F;
      localObject2 = this.cornerShadowPaint;
      k = this.shadowStartColor;
      j = this.shadowMiddleColor;
      i = this.shadowEndColor;
      Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
      ((Paint)localObject2).setShader(new RadialGradient(0.0F, 0.0F, f1, new int[] { 0, k, j, i }, new float[] { 0.0F, f2, f2 + f3, 1.0F }, localTileMode));
    }
    localObject2 = this.edgeShadowPaint;
    f1 = localRectF.top;
    float f2 = ((RectF)localObject1).top;
    int j = this.shadowStartColor;
    int k = this.shadowMiddleColor;
    int i = this.shadowEndColor;
    localObject1 = Shader.TileMode.CLAMP;
    ((Paint)localObject2).setShader(new LinearGradient(0.0F, f1, 0.0F, f2, new int[] { j, k, i }, new float[] { 0.0F, 0.5F, 1.0F }, (Shader.TileMode)localObject1));
    this.edgeShadowPaint.setAntiAlias(false);
  }
  
  public static float calculateHorizontalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean) {
      return (float)(paramFloat1 + (1.0D - COS_45) * paramFloat2);
    }
    return paramFloat1;
  }
  
  public static float calculateVerticalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean) {
      return (float)(1.5F * paramFloat1 + (1.0D - COS_45) * paramFloat2);
    }
    return 1.5F * paramFloat1;
  }
  
  private void drawShadow(Canvas paramCanvas)
  {
    int k = paramCanvas.save();
    paramCanvas.rotate(this.rotation, this.contentBounds.centerX(), this.contentBounds.centerY());
    float f2 = -this.cornerRadius - this.shadowSize;
    float f1 = this.cornerRadius;
    float f3 = this.contentBounds.width();
    int i = 1;
    if (f3 - f1 * 2.0F > 0.0F) {
      j = 1;
    } else {
      j = 0;
    }
    if (this.contentBounds.height() - f1 * 2.0F <= 0.0F) {
      i = 0;
    }
    float f5 = this.rawShadowSize;
    f3 = f1 / (f1 + (f5 - 0.5F * f5));
    float f4 = f1 / (f1 + (f5 - 0.25F * f5));
    f5 = f1 / (f1 + (f5 - f5 * 1.0F));
    int m = paramCanvas.save();
    paramCanvas.translate(this.contentBounds.left + f1, this.contentBounds.top + f1);
    paramCanvas.scale(f3, f4);
    paramCanvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
    if (j != 0)
    {
      paramCanvas.scale(1.0F / f3, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.contentBounds.width() - f1 * 2.0F, -this.cornerRadius, this.edgeShadowPaint);
    }
    paramCanvas.restoreToCount(m);
    m = paramCanvas.save();
    paramCanvas.translate(this.contentBounds.right - f1, this.contentBounds.bottom - f1);
    paramCanvas.scale(f3, f5);
    paramCanvas.rotate(180.0F);
    paramCanvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
    if (j != 0)
    {
      paramCanvas.scale(1.0F / f3, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.contentBounds.width() - f1 * 2.0F, -this.cornerRadius + this.shadowSize, this.edgeShadowPaint);
    }
    paramCanvas.restoreToCount(m);
    int j = paramCanvas.save();
    paramCanvas.translate(this.contentBounds.left + f1, this.contentBounds.bottom - f1);
    paramCanvas.scale(f3, f5);
    paramCanvas.rotate(270.0F);
    paramCanvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
    if (i != 0)
    {
      paramCanvas.scale(1.0F / f5, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.contentBounds.height() - f1 * 2.0F, -this.cornerRadius, this.edgeShadowPaint);
    }
    paramCanvas.restoreToCount(j);
    j = paramCanvas.save();
    paramCanvas.translate(this.contentBounds.right - f1, this.contentBounds.top + f1);
    paramCanvas.scale(f3, f4);
    paramCanvas.rotate(90.0F);
    paramCanvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
    if (i != 0)
    {
      paramCanvas.scale(1.0F / f4, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.contentBounds.height() - 2.0F * f1, -this.cornerRadius, this.edgeShadowPaint);
    }
    paramCanvas.restoreToCount(j);
    paramCanvas.restoreToCount(k);
  }
  
  private static int toEven(float paramFloat)
  {
    int i = Math.round(paramFloat);
    if (i % 2 == 1) {
      i--;
    }
    return i;
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.dirty)
    {
      buildComponents(getBounds());
      this.dirty = false;
    }
    drawShadow(paramCanvas);
    super.draw(paramCanvas);
  }
  
  public float getCornerRadius()
  {
    return this.cornerRadius;
  }
  
  public float getMaxShadowSize()
  {
    return this.rawMaxShadowSize;
  }
  
  public float getMinHeight()
  {
    float f = this.rawMaxShadowSize;
    f = Math.max(f, this.cornerRadius + f * 1.5F / 2.0F);
    return this.rawMaxShadowSize * 1.5F * 2.0F + f * 2.0F;
  }
  
  public float getMinWidth()
  {
    float f = this.rawMaxShadowSize;
    f = Math.max(f, this.cornerRadius + f / 2.0F);
    return this.rawMaxShadowSize * 2.0F + f * 2.0F;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public boolean getPadding(Rect paramRect)
  {
    int i = (int)Math.ceil(calculateVerticalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
    int j = (int)Math.ceil(calculateHorizontalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
    paramRect.set(j, i, j, i);
    return true;
  }
  
  public float getShadowSize()
  {
    return this.rawShadowSize;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    this.dirty = true;
  }
  
  public void setAddPaddingForCorners(boolean paramBoolean)
  {
    this.addPaddingForCorners = paramBoolean;
    invalidateSelf();
  }
  
  public void setAlpha(int paramInt)
  {
    super.setAlpha(paramInt);
    this.cornerShadowPaint.setAlpha(paramInt);
    this.edgeShadowPaint.setAlpha(paramInt);
  }
  
  public void setCornerRadius(float paramFloat)
  {
    paramFloat = Math.round(paramFloat);
    if (this.cornerRadius == paramFloat) {
      return;
    }
    this.cornerRadius = paramFloat;
    this.dirty = true;
    invalidateSelf();
  }
  
  public void setMaxShadowSize(float paramFloat)
  {
    setShadowSize(this.rawShadowSize, paramFloat);
  }
  
  public final void setRotation(float paramFloat)
  {
    if (this.rotation != paramFloat)
    {
      this.rotation = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setShadowSize(float paramFloat)
  {
    setShadowSize(paramFloat, this.rawMaxShadowSize);
  }
  
  public void setShadowSize(float paramFloat1, float paramFloat2)
  {
    if ((paramFloat1 >= 0.0F) && (paramFloat2 >= 0.0F))
    {
      float f1 = toEven(paramFloat1);
      float f2 = toEven(paramFloat2);
      paramFloat1 = f1;
      if (f1 > f2)
      {
        paramFloat2 = f2;
        paramFloat1 = paramFloat2;
        if (!this.printedShadowClipWarning)
        {
          this.printedShadowClipWarning = true;
          paramFloat1 = paramFloat2;
        }
      }
      if ((this.rawShadowSize == paramFloat1) && (this.rawMaxShadowSize == f2)) {
        return;
      }
      this.rawShadowSize = paramFloat1;
      this.rawMaxShadowSize = f2;
      this.shadowSize = Math.round(1.5F * paramFloat1);
      this.maxShadowSize = f2;
      this.dirty = true;
      invalidateSelf();
      return;
    }
    throw new IllegalArgumentException("invalid shadow size");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shadow/ShadowDrawableWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */