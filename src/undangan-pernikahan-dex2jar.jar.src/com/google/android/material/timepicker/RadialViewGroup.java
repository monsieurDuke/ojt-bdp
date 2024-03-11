package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.styleable;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;

class RadialViewGroup
  extends ConstraintLayout
{
  private static final String SKIP_TAG = "skip";
  private MaterialShapeDrawable background;
  private int radius;
  private final Runnable updateLayoutParametersRunnable;
  
  public RadialViewGroup(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RadialViewGroup(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public RadialViewGroup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    LayoutInflater.from(paramContext).inflate(R.layout.material_radial_view_group, this);
    ViewCompat.setBackground(this, createBackground());
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RadialViewGroup, paramInt, 0);
    this.radius = paramContext.getDimensionPixelSize(R.styleable.RadialViewGroup_materialCircleRadius, 0);
    this.updateLayoutParametersRunnable = new Runnable()
    {
      public void run()
      {
        RadialViewGroup.this.updateLayoutParams();
      }
    };
    paramContext.recycle();
  }
  
  private Drawable createBackground()
  {
    MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
    this.background = localMaterialShapeDrawable;
    localMaterialShapeDrawable.setCornerSize(new RelativeCornerSize(0.5F));
    this.background.setFillColor(ColorStateList.valueOf(-1));
    return this.background;
  }
  
  private static boolean shouldSkipView(View paramView)
  {
    return "skip".equals(paramView.getTag());
  }
  
  private void updateLayoutParamsAsync()
  {
    Handler localHandler = getHandler();
    if (localHandler != null)
    {
      localHandler.removeCallbacks(this.updateLayoutParametersRunnable);
      localHandler.post(this.updateLayoutParametersRunnable);
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (paramView.getId() == -1) {
      paramView.setId(ViewCompat.generateViewId());
    }
    updateLayoutParamsAsync();
  }
  
  public int getRadius()
  {
    return this.radius;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    updateLayoutParams();
  }
  
  public void onViewRemoved(View paramView)
  {
    super.onViewRemoved(paramView);
    updateLayoutParamsAsync();
  }
  
  public void setBackgroundColor(int paramInt)
  {
    this.background.setFillColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setRadius(int paramInt)
  {
    this.radius = paramInt;
    updateLayoutParams();
  }
  
  protected void updateLayoutParams()
  {
    int i = 1;
    int m = getChildCount();
    int j = 0;
    while (j < m)
    {
      int k = i;
      if (shouldSkipView(getChildAt(j))) {
        k = i + 1;
      }
      j++;
      i = k;
    }
    ConstraintSet localConstraintSet = new ConstraintSet();
    localConstraintSet.clone(this);
    float f2 = 0.0F;
    j = 0;
    while (j < m)
    {
      View localView = getChildAt(j);
      float f1 = f2;
      if (localView.getId() != R.id.circle_center) {
        if (shouldSkipView(localView))
        {
          f1 = f2;
        }
        else
        {
          localConstraintSet.constrainCircle(localView.getId(), R.id.circle_center, this.radius, f2);
          f1 = f2 + 360.0F / (m - i);
        }
      }
      j++;
      f2 = f1;
    }
    localConstraintSet.applyTo(this);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/RadialViewGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */