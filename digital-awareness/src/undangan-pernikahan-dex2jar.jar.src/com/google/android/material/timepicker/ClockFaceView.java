package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.color;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.resources.MaterialResources;
import java.util.Arrays;

class ClockFaceView
  extends RadialViewGroup
  implements ClockHandView.OnRotateListener
{
  private static final float EPSILON = 0.001F;
  private static final int INITIAL_CAPACITY = 12;
  private static final String VALUE_PLACEHOLDER = "";
  private final int clockHandPadding;
  private final ClockHandView clockHandView;
  private final int clockSize;
  private float currentHandRotation;
  private final int[] gradientColors;
  private final float[] gradientPositions = { 0.0F, 0.9F, 1.0F };
  private final int minimumHeight;
  private final int minimumWidth;
  private final RectF scratch = new RectF();
  private final ColorStateList textColor;
  private final SparseArray<TextView> textViewPool = new SparseArray();
  private final Rect textViewRect = new Rect();
  private final AccessibilityDelegateCompat valueAccessibilityDelegate;
  private String[] values;
  
  public ClockFaceView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ClockFaceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.materialClockStyle);
  }
  
  public ClockFaceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ClockFaceView, paramInt, R.style.Widget_MaterialComponents_TimePicker_Clock);
    paramAttributeSet = getResources();
    ColorStateList localColorStateList = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.ClockFaceView_clockNumberTextColor);
    this.textColor = localColorStateList;
    LayoutInflater.from(paramContext).inflate(R.layout.material_clockface_view, this, true);
    ClockHandView localClockHandView = (ClockHandView)findViewById(R.id.material_clock_hand);
    this.clockHandView = localClockHandView;
    this.clockHandPadding = paramAttributeSet.getDimensionPixelSize(R.dimen.material_clock_hand_padding);
    paramInt = localColorStateList.getDefaultColor();
    paramInt = localColorStateList.getColorForState(new int[] { 16842913 }, paramInt);
    this.gradientColors = new int[] { paramInt, paramInt, localColorStateList.getDefaultColor() };
    localClockHandView.addOnRotateListener(this);
    paramInt = AppCompatResources.getColorStateList(paramContext, R.color.material_timepicker_clockface).getDefaultColor();
    paramContext = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.ClockFaceView_clockFaceBackgroundColor);
    if (paramContext != null) {
      paramInt = paramContext.getDefaultColor();
    }
    setBackgroundColor(paramInt);
    getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
    {
      public boolean onPreDraw()
      {
        if (!ClockFaceView.this.isShown()) {
          return true;
        }
        ClockFaceView.this.getViewTreeObserver().removeOnPreDrawListener(this);
        int i = ClockFaceView.this.getHeight() / 2;
        int k = ClockFaceView.this.clockHandView.getSelectorRadius();
        int j = ClockFaceView.this.clockHandPadding;
        ClockFaceView.this.setRadius(i - k - j);
        return true;
      }
    });
    setFocusable(true);
    localTypedArray.recycle();
    this.valueAccessibilityDelegate = new AccessibilityDelegateCompat()
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        int i = ((Integer)paramAnonymousView.getTag(R.id.material_value_index)).intValue();
        if (i > 0) {
          paramAnonymousAccessibilityNodeInfoCompat.setTraversalAfter((View)ClockFaceView.this.textViewPool.get(i - 1));
        }
        paramAnonymousAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, i, 1, false, paramAnonymousView.isSelected()));
        paramAnonymousAccessibilityNodeInfoCompat.setClickable(true);
        paramAnonymousAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
      }
      
      public boolean performAccessibilityAction(View paramAnonymousView, int paramAnonymousInt, Bundle paramAnonymousBundle)
      {
        if (paramAnonymousInt == 16)
        {
          long l = SystemClock.uptimeMillis();
          float f2 = paramAnonymousView.getX() + paramAnonymousView.getWidth() / 2.0F;
          float f1 = paramAnonymousView.getY() + paramAnonymousView.getHeight() / 2.0F;
          ClockFaceView.this.clockHandView.onTouchEvent(MotionEvent.obtain(l, l, 0, f2, f1, 0));
          ClockFaceView.this.clockHandView.onTouchEvent(MotionEvent.obtain(l, l, 1, f2, f1, 0));
          return true;
        }
        return super.performAccessibilityAction(paramAnonymousView, paramAnonymousInt, paramAnonymousBundle);
      }
    };
    paramContext = new String[12];
    Arrays.fill(paramContext, "");
    setValues(paramContext, 0);
    this.minimumHeight = paramAttributeSet.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_height);
    this.minimumWidth = paramAttributeSet.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_width);
    this.clockSize = paramAttributeSet.getDimensionPixelSize(R.dimen.material_clock_size);
  }
  
  private void findIntersectingTextView()
  {
    RectF localRectF = this.clockHandView.getCurrentSelectorBox();
    for (int i = 0; i < this.textViewPool.size(); i++)
    {
      TextView localTextView = (TextView)this.textViewPool.get(i);
      if (localTextView != null)
      {
        localTextView.getDrawingRect(this.textViewRect);
        offsetDescendantRectToMyCoords(localTextView, this.textViewRect);
        localTextView.setSelected(localRectF.contains(this.textViewRect.centerX(), this.textViewRect.centerY()));
        RadialGradient localRadialGradient = getGradientForTextView(localRectF, this.textViewRect, localTextView);
        localTextView.getPaint().setShader(localRadialGradient);
        localTextView.invalidate();
      }
    }
  }
  
  private RadialGradient getGradientForTextView(RectF paramRectF, Rect paramRect, TextView paramTextView)
  {
    this.scratch.set(paramRect);
    this.scratch.offset(paramTextView.getPaddingLeft(), paramTextView.getPaddingTop());
    if (!RectF.intersects(paramRectF, this.scratch)) {
      return null;
    }
    return new RadialGradient(paramRectF.centerX() - this.scratch.left, paramRectF.centerY() - this.scratch.top, 0.5F * paramRectF.width(), this.gradientColors, this.gradientPositions, Shader.TileMode.CLAMP);
  }
  
  private static float max3(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.max(Math.max(paramFloat1, paramFloat2), paramFloat3);
  }
  
  private void updateTextViews(int paramInt)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    int j = this.textViewPool.size();
    for (int i = 0; i < Math.max(this.values.length, j); i++)
    {
      TextView localTextView2 = (TextView)this.textViewPool.get(i);
      if (i >= this.values.length)
      {
        removeView(localTextView2);
        this.textViewPool.remove(i);
      }
      else
      {
        TextView localTextView1 = localTextView2;
        if (localTextView2 == null)
        {
          localTextView1 = (TextView)localLayoutInflater.inflate(R.layout.material_clockface_textview, this, false);
          this.textViewPool.put(i, localTextView1);
          addView(localTextView1);
        }
        localTextView1.setVisibility(0);
        localTextView1.setText(this.values[i]);
        localTextView1.setTag(R.id.material_value_index, Integer.valueOf(i));
        ViewCompat.setAccessibilityDelegate(localTextView1, this.valueAccessibilityDelegate);
        localTextView1.setTextColor(this.textColor);
        if (paramInt != 0) {
          localTextView1.setContentDescription(getResources().getString(paramInt, new Object[] { this.values[i] }));
        }
      }
    }
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    AccessibilityNodeInfoCompat.wrap(paramAccessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.values.length, false, 1));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    findIntersectingTextView();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
    float f1 = localDisplayMetrics.heightPixels;
    float f2 = localDisplayMetrics.widthPixels;
    paramInt2 = (int)(this.clockSize / max3(this.minimumHeight / f1, this.minimumWidth / f2, 1.0F));
    paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
    setMeasuredDimension(paramInt2, paramInt2);
    super.onMeasure(paramInt1, paramInt1);
  }
  
  public void onRotate(float paramFloat, boolean paramBoolean)
  {
    if (Math.abs(this.currentHandRotation - paramFloat) > 0.001F)
    {
      this.currentHandRotation = paramFloat;
      findIntersectingTextView();
    }
  }
  
  public void setHandRotation(float paramFloat)
  {
    this.clockHandView.setHandRotation(paramFloat);
    findIntersectingTextView();
  }
  
  public void setRadius(int paramInt)
  {
    if (paramInt != getRadius())
    {
      super.setRadius(paramInt);
      this.clockHandView.setCircleRadius(getRadius());
    }
  }
  
  public void setValues(String[] paramArrayOfString, int paramInt)
  {
    this.values = paramArrayOfString;
    updateTextViews(paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/ClockFaceView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */