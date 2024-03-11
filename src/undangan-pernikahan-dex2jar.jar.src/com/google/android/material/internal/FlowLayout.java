package com.google.android.material.internal;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.id;
import com.google.android.material.R.styleable;

public class FlowLayout
  extends ViewGroup
{
  private int itemSpacing;
  private int lineSpacing;
  private int rowCount;
  private boolean singleLine = false;
  
  public FlowLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FlowLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public FlowLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    loadFromAttributes(paramContext, paramAttributeSet);
  }
  
  public FlowLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    loadFromAttributes(paramContext, paramAttributeSet);
  }
  
  private static int getMeasuredDimension(int paramInt1, int paramInt2, int paramInt3)
  {
    switch (paramInt2)
    {
    default: 
      return paramInt3;
    case 1073741824: 
      return paramInt1;
    }
    return Math.min(paramInt3, paramInt1);
  }
  
  private void loadFromAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    paramContext = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.FlowLayout, 0, 0);
    this.lineSpacing = paramContext.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
    this.itemSpacing = paramContext.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
    paramContext.recycle();
  }
  
  protected int getItemSpacing()
  {
    return this.itemSpacing;
  }
  
  protected int getLineSpacing()
  {
    return this.lineSpacing;
  }
  
  protected int getRowCount()
  {
    return this.rowCount;
  }
  
  public int getRowIndex(View paramView)
  {
    paramView = paramView.getTag(R.id.row_index_key);
    if (!(paramView instanceof Integer)) {
      return -1;
    }
    return ((Integer)paramView).intValue();
  }
  
  public boolean isSingleLine()
  {
    return this.singleLine;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt2 = getChildCount();
    int i = 0;
    if (paramInt2 == 0)
    {
      this.rowCount = 0;
      return;
    }
    this.rowCount = 1;
    if (ViewCompat.getLayoutDirection(this) == 1) {
      i = 1;
    }
    if (i != 0) {
      paramInt2 = getPaddingRight();
    } else {
      paramInt2 = getPaddingLeft();
    }
    if (i != 0) {
      j = getPaddingLeft();
    } else {
      j = getPaddingRight();
    }
    paramInt4 = paramInt2;
    int m = getPaddingTop();
    int k = m;
    int i2 = paramInt3 - paramInt1 - j;
    int j = 0;
    paramInt1 = m;
    paramInt3 = paramInt4;
    while (j < getChildCount())
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() == 8)
      {
        localView.setTag(R.id.row_index_key, Integer.valueOf(-1));
      }
      else
      {
        Object localObject = localView.getLayoutParams();
        int n = 0;
        m = 0;
        if ((localObject instanceof ViewGroup.MarginLayoutParams))
        {
          localObject = (ViewGroup.MarginLayoutParams)localObject;
          n = MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)localObject);
          m = MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)localObject);
        }
        int i3 = localView.getMeasuredWidth();
        int i1 = paramInt3;
        paramInt4 = paramInt1;
        if (!this.singleLine)
        {
          i1 = paramInt3;
          paramInt4 = paramInt1;
          if (paramInt3 + n + i3 > i2)
          {
            i1 = paramInt2;
            paramInt4 = k + this.lineSpacing;
            this.rowCount += 1;
          }
        }
        localView.setTag(R.id.row_index_key, Integer.valueOf(this.rowCount - 1));
        paramInt1 = i1 + n + localView.getMeasuredWidth();
        k = localView.getMeasuredHeight() + paramInt4;
        if (i != 0) {
          localView.layout(i2 - paramInt1, paramInt4, i2 - i1 - n, k);
        } else {
          localView.layout(i1 + n, paramInt4, paramInt1, k);
        }
        paramInt3 = i1 + (n + m + localView.getMeasuredWidth() + this.itemSpacing);
        paramInt1 = paramInt4;
      }
      j++;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i6 = View.MeasureSpec.getSize(paramInt1);
    int i8 = View.MeasureSpec.getMode(paramInt1);
    int i10 = View.MeasureSpec.getSize(paramInt2);
    int i9 = View.MeasureSpec.getMode(paramInt2);
    int i;
    if ((i8 != Integer.MIN_VALUE) && (i8 != 1073741824)) {
      i = Integer.MAX_VALUE;
    } else {
      i = i6;
    }
    int m = getPaddingLeft();
    int k = getPaddingTop();
    int n = k;
    int j = 0;
    int i11 = getPaddingRight();
    int i2 = 0;
    int i1 = i;
    while (i2 < getChildCount())
    {
      View localView = getChildAt(i2);
      if (localView.getVisibility() != 8)
      {
        measureChild(localView, paramInt1, paramInt2);
        Object localObject = localView.getLayoutParams();
        int i4 = 0;
        int i3 = 0;
        if ((localObject instanceof ViewGroup.MarginLayoutParams))
        {
          localObject = (ViewGroup.MarginLayoutParams)localObject;
          i4 = 0 + ((ViewGroup.MarginLayoutParams)localObject).leftMargin;
          i3 = 0 + ((ViewGroup.MarginLayoutParams)localObject).rightMargin;
        }
        int i5;
        if ((m + i4 + localView.getMeasuredWidth() > i - i11) && (!isSingleLine()))
        {
          i5 = getPaddingLeft();
          k = this.lineSpacing + n;
        }
        else
        {
          i5 = m;
        }
        int i7 = i5 + i4 + localView.getMeasuredWidth();
        n = localView.getMeasuredHeight() + k;
        m = j;
        if (i7 > j) {
          m = i7;
        }
        i4 = i5 + (i4 + i3 + localView.getMeasuredWidth() + this.itemSpacing);
        if (i2 == getChildCount() - 1)
        {
          j = m + i3;
          m = i4;
        }
        else
        {
          j = m;
          m = i4;
        }
      }
      i2++;
    }
    paramInt2 = getPaddingRight();
    paramInt1 = getPaddingBottom();
    setMeasuredDimension(getMeasuredDimension(i6, i8, j + paramInt2), getMeasuredDimension(i10, i9, n + paramInt1));
  }
  
  protected void setItemSpacing(int paramInt)
  {
    this.itemSpacing = paramInt;
  }
  
  protected void setLineSpacing(int paramInt)
  {
    this.lineSpacing = paramInt;
  }
  
  public void setSingleLine(boolean paramBoolean)
  {
    this.singleLine = paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/FlowLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */