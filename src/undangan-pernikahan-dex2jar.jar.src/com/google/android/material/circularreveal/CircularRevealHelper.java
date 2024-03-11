package com.google.android.material.circularreveal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import com.google.android.material.math.MathUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CircularRevealHelper
{
  public static final int BITMAP_SHADER = 0;
  public static final int CLIP_PATH = 1;
  private static final boolean DEBUG = false;
  public static final int REVEAL_ANIMATOR = 2;
  public static final int STRATEGY;
  private boolean buildingCircularRevealCache;
  private Paint debugPaint;
  private final Delegate delegate;
  private boolean hasCircularRevealCache;
  private Drawable overlayDrawable;
  private CircularRevealWidget.RevealInfo revealInfo;
  private final Paint revealPaint;
  private final Path revealPath;
  private final Paint scrimPaint;
  private final View view;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {
      STRATEGY = 2;
    } else if (Build.VERSION.SDK_INT >= 18) {
      STRATEGY = 1;
    } else {
      STRATEGY = 0;
    }
  }
  
  public CircularRevealHelper(Delegate paramDelegate)
  {
    this.delegate = paramDelegate;
    paramDelegate = (View)paramDelegate;
    this.view = paramDelegate;
    paramDelegate.setWillNotDraw(false);
    this.revealPath = new Path();
    this.revealPaint = new Paint(7);
    paramDelegate = new Paint(1);
    this.scrimPaint = paramDelegate;
    paramDelegate.setColor(0);
  }
  
  private void drawDebugCircle(Canvas paramCanvas, int paramInt, float paramFloat)
  {
    this.debugPaint.setColor(paramInt);
    this.debugPaint.setStrokeWidth(paramFloat);
    paramCanvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius - paramFloat / 2.0F, this.debugPaint);
  }
  
  private void drawDebugMode(Canvas paramCanvas)
  {
    this.delegate.actualDraw(paramCanvas);
    if (shouldDrawScrim()) {
      paramCanvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.scrimPaint);
    }
    if (shouldDrawCircularReveal())
    {
      drawDebugCircle(paramCanvas, -16777216, 10.0F);
      drawDebugCircle(paramCanvas, -65536, 5.0F);
    }
    drawOverlayDrawable(paramCanvas);
  }
  
  private void drawOverlayDrawable(Canvas paramCanvas)
  {
    if (shouldDrawOverlayDrawable())
    {
      Rect localRect = this.overlayDrawable.getBounds();
      float f1 = this.revealInfo.centerX - localRect.width() / 2.0F;
      float f2 = this.revealInfo.centerY - localRect.height() / 2.0F;
      paramCanvas.translate(f1, f2);
      this.overlayDrawable.draw(paramCanvas);
      paramCanvas.translate(-f1, -f2);
    }
  }
  
  private float getDistanceToFurthestCorner(CircularRevealWidget.RevealInfo paramRevealInfo)
  {
    return MathUtils.distanceToFurthestCorner(paramRevealInfo.centerX, paramRevealInfo.centerY, 0.0F, 0.0F, this.view.getWidth(), this.view.getHeight());
  }
  
  private void invalidateRevealInfo()
  {
    if (STRATEGY == 1)
    {
      this.revealPath.rewind();
      CircularRevealWidget.RevealInfo localRevealInfo = this.revealInfo;
      if (localRevealInfo != null) {
        this.revealPath.addCircle(localRevealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, Path.Direction.CW);
      }
    }
    this.view.invalidate();
  }
  
  private boolean shouldDrawCircularReveal()
  {
    CircularRevealWidget.RevealInfo localRevealInfo = this.revealInfo;
    boolean bool1 = false;
    boolean bool2 = false;
    int i;
    if ((localRevealInfo != null) && (!localRevealInfo.isInvalid())) {
      i = 0;
    } else {
      i = 1;
    }
    if (STRATEGY == 0)
    {
      bool1 = bool2;
      if (i == 0)
      {
        bool1 = bool2;
        if (this.hasCircularRevealCache) {
          bool1 = true;
        }
      }
      return bool1;
    }
    if (i == 0) {
      bool1 = true;
    }
    return bool1;
  }
  
  private boolean shouldDrawOverlayDrawable()
  {
    boolean bool;
    if ((!this.buildingCircularRevealCache) && (this.overlayDrawable != null) && (this.revealInfo != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean shouldDrawScrim()
  {
    boolean bool;
    if ((!this.buildingCircularRevealCache) && (Color.alpha(this.scrimPaint.getColor()) != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void buildCircularRevealCache()
  {
    if (STRATEGY == 0)
    {
      this.buildingCircularRevealCache = true;
      this.hasCircularRevealCache = false;
      this.view.buildDrawingCache();
      Object localObject2 = this.view.getDrawingCache();
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = localObject2;
        if (this.view.getWidth() != 0)
        {
          localObject1 = localObject2;
          if (this.view.getHeight() != 0)
          {
            localObject1 = Bitmap.createBitmap(this.view.getWidth(), this.view.getHeight(), Bitmap.Config.ARGB_8888);
            localObject2 = new Canvas((Bitmap)localObject1);
            this.view.draw((Canvas)localObject2);
          }
        }
      }
      if (localObject1 != null) {
        this.revealPaint.setShader(new BitmapShader((Bitmap)localObject1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
      }
      this.buildingCircularRevealCache = false;
      this.hasCircularRevealCache = true;
    }
  }
  
  public void destroyCircularRevealCache()
  {
    if (STRATEGY == 0)
    {
      this.hasCircularRevealCache = false;
      this.view.destroyDrawingCache();
      this.revealPaint.setShader(null);
      this.view.invalidate();
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (shouldDrawCircularReveal())
    {
      int i = STRATEGY;
      switch (i)
      {
      default: 
        throw new IllegalStateException("Unsupported strategy " + i);
      case 2: 
        this.delegate.actualDraw(paramCanvas);
        if (!shouldDrawScrim()) {
          break;
        }
        paramCanvas.drawRect(0.0F, 0.0F, this.view.getWidth(), this.view.getHeight(), this.scrimPaint);
        break;
      case 1: 
        i = paramCanvas.save();
        paramCanvas.clipPath(this.revealPath);
        this.delegate.actualDraw(paramCanvas);
        if (shouldDrawScrim()) {
          paramCanvas.drawRect(0.0F, 0.0F, this.view.getWidth(), this.view.getHeight(), this.scrimPaint);
        }
        paramCanvas.restoreToCount(i);
        break;
      case 0: 
        paramCanvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.revealPaint);
        if (!shouldDrawScrim()) {
          break;
        }
        paramCanvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.scrimPaint);
        break;
      }
    }
    else
    {
      this.delegate.actualDraw(paramCanvas);
      if (shouldDrawScrim()) {
        paramCanvas.drawRect(0.0F, 0.0F, this.view.getWidth(), this.view.getHeight(), this.scrimPaint);
      }
    }
    drawOverlayDrawable(paramCanvas);
  }
  
  public Drawable getCircularRevealOverlayDrawable()
  {
    return this.overlayDrawable;
  }
  
  public int getCircularRevealScrimColor()
  {
    return this.scrimPaint.getColor();
  }
  
  public CircularRevealWidget.RevealInfo getRevealInfo()
  {
    if (this.revealInfo == null) {
      return null;
    }
    CircularRevealWidget.RevealInfo localRevealInfo = new CircularRevealWidget.RevealInfo(this.revealInfo);
    if (localRevealInfo.isInvalid()) {
      localRevealInfo.radius = getDistanceToFurthestCorner(localRevealInfo);
    }
    return localRevealInfo;
  }
  
  public boolean isOpaque()
  {
    boolean bool;
    if ((this.delegate.actualIsOpaque()) && (!shouldDrawCircularReveal())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void setCircularRevealOverlayDrawable(Drawable paramDrawable)
  {
    this.overlayDrawable = paramDrawable;
    this.view.invalidate();
  }
  
  public void setCircularRevealScrimColor(int paramInt)
  {
    this.scrimPaint.setColor(paramInt);
    this.view.invalidate();
  }
  
  public void setRevealInfo(CircularRevealWidget.RevealInfo paramRevealInfo)
  {
    if (paramRevealInfo == null)
    {
      this.revealInfo = null;
    }
    else
    {
      CircularRevealWidget.RevealInfo localRevealInfo = this.revealInfo;
      if (localRevealInfo == null) {
        this.revealInfo = new CircularRevealWidget.RevealInfo(paramRevealInfo);
      } else {
        localRevealInfo.set(paramRevealInfo);
      }
      if (MathUtils.geq(paramRevealInfo.radius, getDistanceToFurthestCorner(paramRevealInfo), 1.0E-4F)) {
        this.revealInfo.radius = Float.MAX_VALUE;
      }
    }
    invalidateRevealInfo();
  }
  
  public static abstract interface Delegate
  {
    public abstract void actualDraw(Canvas paramCanvas);
    
    public abstract boolean actualIsOpaque();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Strategy {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/circularreveal/CircularRevealHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */