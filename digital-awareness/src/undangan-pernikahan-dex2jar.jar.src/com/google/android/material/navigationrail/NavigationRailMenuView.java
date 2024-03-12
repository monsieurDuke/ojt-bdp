package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import androidx.appcompat.view.menu.MenuBuilder;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import java.util.ArrayList;

public class NavigationRailMenuView
  extends NavigationBarMenuView
{
  private int itemMinimumHeight = -1;
  private final FrameLayout.LayoutParams layoutParams;
  
  public NavigationRailMenuView(Context paramContext)
  {
    super(paramContext);
    paramContext = new FrameLayout.LayoutParams(-1, -2);
    this.layoutParams = paramContext;
    paramContext.gravity = 49;
    setLayoutParams(paramContext);
    setItemActiveIndicatorResizeable(true);
  }
  
  private int makeSharedHeightSpec(int paramInt1, int paramInt2, int paramInt3)
  {
    paramInt3 = paramInt2 / Math.max(1, paramInt3);
    paramInt2 = this.itemMinimumHeight;
    if (paramInt2 != -1) {
      paramInt1 = paramInt2;
    } else {
      paramInt1 = View.MeasureSpec.getSize(paramInt1);
    }
    return View.MeasureSpec.makeMeasureSpec(Math.min(paramInt1, paramInt3), 0);
  }
  
  private int measureChildHeight(View paramView, int paramInt1, int paramInt2)
  {
    if (paramView.getVisibility() != 8)
    {
      paramView.measure(paramInt1, paramInt2);
      return paramView.getMeasuredHeight();
    }
    return 0;
  }
  
  private int measureSharedChildHeights(int paramInt1, int paramInt2, int paramInt3, View paramView)
  {
    makeSharedHeightSpec(paramInt1, paramInt2, paramInt3);
    if (paramView == null) {
      paramInt2 = makeSharedHeightSpec(paramInt1, paramInt2, paramInt3);
    } else {
      paramInt2 = View.MeasureSpec.makeMeasureSpec(paramView.getMeasuredHeight(), 0);
    }
    int k = getChildCount();
    int i = 0;
    paramInt3 = 0;
    while (paramInt3 < k)
    {
      View localView = getChildAt(paramInt3);
      int j = i;
      if (localView != paramView) {
        j = i + measureChildHeight(localView, paramInt1, paramInt2);
      }
      paramInt3++;
      i = j;
    }
    return i;
  }
  
  private int measureShiftingChildHeights(int paramInt1, int paramInt2, int paramInt3)
  {
    int k = 0;
    View localView = getChildAt(getSelectedItemPosition());
    int j = paramInt2;
    int i = paramInt3;
    if (localView != null)
    {
      k = measureChildHeight(localView, paramInt1, makeSharedHeightSpec(paramInt1, paramInt2, paramInt3));
      j = paramInt2 - k;
      i = paramInt3 - 1;
    }
    return measureSharedChildHeights(paramInt1, j, i, localView) + k;
  }
  
  protected NavigationBarItemView createNavigationBarItemView(Context paramContext)
  {
    return new NavigationRailItemView(paramContext);
  }
  
  public int getItemMinimumHeight()
  {
    return this.itemMinimumHeight;
  }
  
  int getMenuGravity()
  {
    return this.layoutParams.gravity;
  }
  
  boolean isTopGravity()
  {
    boolean bool;
    if ((this.layoutParams.gravity & 0x70) == 48) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = getChildCount();
    paramInt4 = 0;
    paramInt2 = 0;
    while (paramInt2 < j)
    {
      View localView = getChildAt(paramInt2);
      int i = paramInt4;
      if (localView.getVisibility() != 8)
      {
        i = localView.getMeasuredHeight();
        localView.layout(0, paramInt4, paramInt3 - paramInt1, i + paramInt4);
        i = paramInt4 + i;
      }
      paramInt2++;
      paramInt4 = i;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.getSize(paramInt2);
    int i = getMenu().getVisibleItems().size();
    if ((i > 1) && (isShifting(getLabelVisibilityMode(), i))) {
      i = measureShiftingChildHeights(paramInt1, j, i);
    } else {
      i = measureSharedChildHeights(paramInt1, j, i, null);
    }
    j = View.MeasureSpec.getSize(paramInt1);
    setMeasuredDimension(View.resolveSizeAndState(j, paramInt1, 0), View.resolveSizeAndState(i, paramInt2, 0));
  }
  
  public void setItemMinimumHeight(int paramInt)
  {
    if (this.itemMinimumHeight != paramInt)
    {
      this.itemMinimumHeight = paramInt;
      requestLayout();
    }
  }
  
  void setMenuGravity(int paramInt)
  {
    if (this.layoutParams.gravity != paramInt)
    {
      this.layoutParams.gravity = paramInt;
      setLayoutParams(this.layoutParams);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigationrail/NavigationRailMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */