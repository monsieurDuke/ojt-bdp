package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialDivider
  extends View
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider;
  private int color;
  private final MaterialShapeDrawable dividerDrawable;
  private int insetEnd;
  private int insetStart;
  private int thickness;
  
  public MaterialDivider(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialDivider(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.materialDividerStyle);
  }
  
  public MaterialDivider(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = getContext();
    this.dividerDrawable = new MaterialShapeDrawable();
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.MaterialDivider, paramInt, i, new int[0]);
    this.thickness = paramAttributeSet.getDimensionPixelSize(R.styleable.MaterialDivider_dividerThickness, getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));
    this.insetStart = paramAttributeSet.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0);
    this.insetEnd = paramAttributeSet.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0);
    setDividerColor(MaterialResources.getColorStateList(paramContext, paramAttributeSet, R.styleable.MaterialDivider_dividerColor).getDefaultColor());
    paramAttributeSet.recycle();
  }
  
  public int getDividerColor()
  {
    return this.color;
  }
  
  public int getDividerInsetEnd()
  {
    return this.insetEnd;
  }
  
  public int getDividerInsetStart()
  {
    return this.insetStart;
  }
  
  public int getDividerThickness()
  {
    return this.thickness;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = ViewCompat.getLayoutDirection(this);
    int j = 1;
    if (i != 1) {
      j = 0;
    }
    if (j != 0) {
      i = this.insetEnd;
    } else {
      i = this.insetStart;
    }
    int k = getWidth();
    if (j != 0) {
      j = this.insetStart;
    } else {
      j = this.insetEnd;
    }
    this.dividerDrawable.setBounds(i, 0, k - j, getBottom() - getTop());
    this.dividerDrawable.draw(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    paramInt1 = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = getMeasuredHeight();
    if ((paramInt1 == Integer.MIN_VALUE) || (paramInt1 == 0))
    {
      int i = this.thickness;
      paramInt1 = paramInt2;
      if (i > 0)
      {
        paramInt1 = paramInt2;
        if (paramInt2 != i) {
          paramInt1 = this.thickness;
        }
      }
      setMeasuredDimension(getMeasuredWidth(), paramInt1);
    }
  }
  
  public void setDividerColor(int paramInt)
  {
    if (this.color != paramInt)
    {
      this.color = paramInt;
      this.dividerDrawable.setFillColor(ColorStateList.valueOf(paramInt));
      invalidate();
    }
  }
  
  public void setDividerColorResource(int paramInt)
  {
    setDividerColor(ContextCompat.getColor(getContext(), paramInt));
  }
  
  public void setDividerInsetEnd(int paramInt)
  {
    this.insetEnd = paramInt;
  }
  
  public void setDividerInsetEndResource(int paramInt)
  {
    setDividerInsetEnd(getContext().getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setDividerInsetStart(int paramInt)
  {
    this.insetStart = paramInt;
  }
  
  public void setDividerInsetStartResource(int paramInt)
  {
    setDividerInsetStart(getContext().getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setDividerThickness(int paramInt)
  {
    if (this.thickness != paramInt)
    {
      this.thickness = paramInt;
      requestLayout();
    }
  }
  
  public void setDividerThicknessResource(int paramInt)
  {
    setDividerThickness(getContext().getResources().getDimensionPixelSize(paramInt));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/divider/MaterialDivider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */