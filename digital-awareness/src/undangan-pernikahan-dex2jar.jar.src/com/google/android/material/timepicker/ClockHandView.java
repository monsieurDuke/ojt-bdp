package com.google.android.material.timepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ClockHandView
  extends View
{
  private static final int ANIMATION_DURATION = 200;
  private boolean animatingOnTouchUp;
  private final float centerDotRadius;
  private boolean changedDuringTouch;
  private int circleRadius;
  private double degRad;
  private float downX;
  private float downY;
  private boolean isInTapRegion;
  private final List<OnRotateListener> listeners = new ArrayList();
  private OnActionUpListener onActionUpListener;
  private float originalDeg;
  private final Paint paint;
  private ValueAnimator rotationAnimator;
  private int scaledTouchSlop;
  private final RectF selectorBox;
  private final int selectorRadius;
  private final int selectorStrokeWidth;
  
  public ClockHandView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ClockHandView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.materialClockStyle);
  }
  
  public ClockHandView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Paint localPaint = new Paint();
    this.paint = localPaint;
    this.selectorBox = new RectF();
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ClockHandView, paramInt, R.style.Widget_MaterialComponents_TimePicker_Clock);
    this.circleRadius = paramAttributeSet.getDimensionPixelSize(R.styleable.ClockHandView_materialCircleRadius, 0);
    this.selectorRadius = paramAttributeSet.getDimensionPixelSize(R.styleable.ClockHandView_selectorSize, 0);
    Resources localResources = getResources();
    this.selectorStrokeWidth = localResources.getDimensionPixelSize(R.dimen.material_clock_hand_stroke_width);
    this.centerDotRadius = localResources.getDimensionPixelSize(R.dimen.material_clock_hand_center_dot_radius);
    paramInt = paramAttributeSet.getColor(R.styleable.ClockHandView_clockHandColor, 0);
    localPaint.setAntiAlias(true);
    localPaint.setColor(paramInt);
    setHandRotation(0.0F);
    this.scaledTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    ViewCompat.setImportantForAccessibility(this, 2);
    paramAttributeSet.recycle();
  }
  
  private void drawSelector(Canvas paramCanvas)
  {
    int j = getHeight() / 2;
    int i = getWidth() / 2;
    float f1 = i;
    float f3 = this.circleRadius;
    float f4 = (float)Math.cos(this.degRad);
    float f5 = j;
    float f6 = this.circleRadius;
    float f2 = (float)Math.sin(this.degRad);
    this.paint.setStrokeWidth(0.0F);
    paramCanvas.drawCircle(f1 + f3 * f4, f5 + f6 * f2, this.selectorRadius, this.paint);
    double d2 = Math.sin(this.degRad);
    double d1 = Math.cos(this.degRad);
    f2 = this.circleRadius - this.selectorRadius;
    f1 = (int)(f2 * d1) + i;
    f2 = (int)(f2 * d2) + j;
    this.paint.setStrokeWidth(this.selectorStrokeWidth);
    paramCanvas.drawLine(i, j, f1, f2, this.paint);
    paramCanvas.drawCircle(i, j, this.centerDotRadius, this.paint);
  }
  
  private int getDegreesFromXY(float paramFloat1, float paramFloat2)
  {
    int j = getWidth() / 2;
    int i = getHeight() / 2;
    double d = paramFloat1 - j;
    j = (int)Math.toDegrees(Math.atan2(paramFloat2 - i, d)) + 90;
    i = j;
    if (j < 0) {
      i = j + 360;
    }
    return i;
  }
  
  private Pair<Float, Float> getValuesForAnimation(float paramFloat)
  {
    float f4 = getHandRotation();
    float f3 = f4;
    float f2 = paramFloat;
    if (Math.abs(f4 - paramFloat) > 180.0F)
    {
      float f1 = paramFloat;
      if (f4 > 180.0F)
      {
        f1 = paramFloat;
        if (paramFloat < 180.0F) {
          f1 = paramFloat + 360.0F;
        }
      }
      f3 = f4;
      f2 = f1;
      if (f4 < 180.0F)
      {
        f3 = f4;
        f2 = f1;
        if (f1 > 180.0F)
        {
          f3 = f4 + 360.0F;
          f2 = f1;
        }
      }
    }
    return new Pair(Float.valueOf(f3), Float.valueOf(f2));
  }
  
  private boolean handleTouchInput(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    int j = getDegreesFromXY(paramFloat1, paramFloat2);
    paramFloat2 = getHandRotation();
    paramFloat1 = j;
    boolean bool = false;
    int i;
    if (paramFloat2 != paramFloat1) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramBoolean2) && (i != 0)) {
      return true;
    }
    if ((i == 0) && (!paramBoolean1)) {
      return false;
    }
    paramFloat1 = j;
    paramBoolean1 = bool;
    if (paramBoolean3)
    {
      paramBoolean1 = bool;
      if (this.animatingOnTouchUp) {
        paramBoolean1 = true;
      }
    }
    setHandRotation(paramFloat1, paramBoolean1);
    return true;
  }
  
  private void setHandRotationInternal(float paramFloat, boolean paramBoolean)
  {
    paramFloat %= 360.0F;
    this.originalDeg = paramFloat;
    this.degRad = Math.toRadians(paramFloat - 90.0F);
    int i = getHeight() / 2;
    float f2 = getWidth() / 2 + this.circleRadius * (float)Math.cos(this.degRad);
    float f1 = i + this.circleRadius * (float)Math.sin(this.degRad);
    Object localObject = this.selectorBox;
    i = this.selectorRadius;
    ((RectF)localObject).set(f2 - i, f1 - i, i + f2, i + f1);
    localObject = this.listeners.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((OnRotateListener)((Iterator)localObject).next()).onRotate(paramFloat, paramBoolean);
    }
    invalidate();
  }
  
  public void addOnRotateListener(OnRotateListener paramOnRotateListener)
  {
    this.listeners.add(paramOnRotateListener);
  }
  
  public RectF getCurrentSelectorBox()
  {
    return this.selectorBox;
  }
  
  public float getHandRotation()
  {
    return this.originalDeg;
  }
  
  public int getSelectorRadius()
  {
    return this.selectorRadius;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawSelector(paramCanvas);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    setHandRotation(getHandRotation());
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int j = paramMotionEvent.getActionMasked();
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool5 = false;
    boolean bool1 = false;
    float f2 = paramMotionEvent.getX();
    float f1 = paramMotionEvent.getY();
    boolean bool2 = false;
    switch (j)
    {
    default: 
      bool3 = bool4;
      bool2 = bool5;
      break;
    case 1: 
    case 2: 
      int k = (int)(f2 - this.downX);
      int i = (int)(f1 - this.downY);
      if (k * k + i * i > this.scaledTouchSlop) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.isInTapRegion = bool1;
      bool1 = bool3;
      if (this.changedDuringTouch) {
        bool1 = true;
      }
      if (j == 1) {
        bool2 = true;
      }
      bool4 = bool2;
      bool3 = bool1;
      bool2 = bool5;
      bool1 = bool4;
      break;
    case 0: 
      this.downX = f2;
      this.downY = f1;
      this.isInTapRegion = true;
      this.changedDuringTouch = false;
      bool2 = true;
      bool3 = bool4;
    }
    bool4 = this.changedDuringTouch;
    bool2 = handleTouchInput(f2, f1, bool3, bool2, bool1) | bool4;
    this.changedDuringTouch = bool2;
    if ((bool2) && (bool1))
    {
      paramMotionEvent = this.onActionUpListener;
      if (paramMotionEvent != null) {
        paramMotionEvent.onActionUp(getDegreesFromXY(f2, f1), this.isInTapRegion);
      }
    }
    return true;
  }
  
  public void setAnimateOnTouchUp(boolean paramBoolean)
  {
    this.animatingOnTouchUp = paramBoolean;
  }
  
  public void setCircleRadius(int paramInt)
  {
    this.circleRadius = paramInt;
    invalidate();
  }
  
  public void setHandRotation(float paramFloat)
  {
    setHandRotation(paramFloat, false);
  }
  
  public void setHandRotation(float paramFloat, boolean paramBoolean)
  {
    Object localObject = this.rotationAnimator;
    if (localObject != null) {
      ((ValueAnimator)localObject).cancel();
    }
    if (!paramBoolean)
    {
      setHandRotationInternal(paramFloat, false);
      return;
    }
    localObject = getValuesForAnimation(paramFloat);
    localObject = ValueAnimator.ofFloat(new float[] { ((Float)((Pair)localObject).first).floatValue(), ((Float)((Pair)localObject).second).floatValue() });
    this.rotationAnimator = ((ValueAnimator)localObject);
    ((ValueAnimator)localObject).setDuration(200L);
    this.rotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        ClockHandView.this.setHandRotationInternal(f, true);
      }
    });
    this.rotationAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        paramAnonymousAnimator.end();
      }
    });
    this.rotationAnimator.start();
  }
  
  public void setOnActionUpListener(OnActionUpListener paramOnActionUpListener)
  {
    this.onActionUpListener = paramOnActionUpListener;
  }
  
  public static abstract interface OnActionUpListener
  {
    public abstract void onActionUp(float paramFloat, boolean paramBoolean);
  }
  
  public static abstract interface OnRotateListener
  {
    public abstract void onRotate(float paramFloat, boolean paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/ClockHandView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */