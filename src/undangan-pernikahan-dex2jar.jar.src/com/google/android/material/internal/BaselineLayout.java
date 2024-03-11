package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout
  extends ViewGroup
{
  private int baseline = -1;
  
  public BaselineLayout(Context paramContext)
  {
    super(paramContext, null, 0);
  }
  
  public BaselineLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }
  
  public BaselineLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public int getBaseline()
  {
    return this.baseline;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = getChildCount();
    int m = getPaddingLeft();
    int k = getPaddingRight();
    int i = getPaddingTop();
    for (paramInt2 = 0; paramInt2 < j; paramInt2++)
    {
      View localView = getChildAt(paramInt2);
      if (localView.getVisibility() != 8)
      {
        int i2 = localView.getMeasuredWidth();
        int n = localView.getMeasuredHeight();
        int i1 = (paramInt3 - paramInt1 - k - m - i2) / 2 + m;
        if ((this.baseline != -1) && (localView.getBaseline() != -1)) {
          paramInt4 = this.baseline + i - localView.getBaseline();
        } else {
          paramInt4 = i;
        }
        localView.layout(i1, paramInt4, i1 + i2, paramInt4 + n);
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i4 = getChildCount();
    int i3 = 0;
    int j = 0;
    int k = -1;
    int i = -1;
    int i1 = 0;
    for (int i2 = 0; i2 < i4; i2++)
    {
      View localView = getChildAt(i2);
      if (localView.getVisibility() != 8)
      {
        measureChild(localView, paramInt1, paramInt2);
        int i5 = localView.getBaseline();
        int n = k;
        m = i;
        if (i5 != -1)
        {
          n = Math.max(k, i5);
          m = Math.max(i, localView.getMeasuredHeight() - i5);
        }
        i3 = Math.max(i3, localView.getMeasuredWidth());
        j = Math.max(j, localView.getMeasuredHeight());
        i1 = View.combineMeasuredStates(i1, localView.getMeasuredState());
        i = m;
        k = n;
      }
    }
    int m = j;
    if (k != -1)
    {
      m = Math.max(j, k + Math.max(i, getPaddingBottom()));
      this.baseline = k;
    }
    i = Math.max(m, getSuggestedMinimumHeight());
    j = Math.max(i3, getSuggestedMinimumWidth());
    setMeasuredDimension(View.resolveSizeAndState(j, paramInt1, i1), View.resolveSizeAndState(i, paramInt2, i1 << 16));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/BaselineLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */