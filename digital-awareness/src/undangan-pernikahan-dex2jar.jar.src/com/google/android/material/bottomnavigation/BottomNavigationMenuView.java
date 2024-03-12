package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.dimen;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import java.util.ArrayList;

public class BottomNavigationMenuView
  extends NavigationBarMenuView
{
  private final int activeItemMaxWidth;
  private final int activeItemMinWidth;
  private final int inactiveItemMaxWidth;
  private final int inactiveItemMinWidth;
  private boolean itemHorizontalTranslationEnabled;
  private int[] tempChildWidths;
  
  public BottomNavigationMenuView(Context paramContext)
  {
    super(paramContext);
    paramContext = new FrameLayout.LayoutParams(-2, -2);
    paramContext.gravity = 17;
    setLayoutParams(paramContext);
    paramContext = getResources();
    this.inactiveItemMaxWidth = paramContext.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
    this.inactiveItemMinWidth = paramContext.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
    this.activeItemMaxWidth = paramContext.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
    this.activeItemMinWidth = paramContext.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_min_width);
    this.tempChildWidths = new int[5];
  }
  
  protected NavigationBarItemView createNavigationBarItemView(Context paramContext)
  {
    return new BottomNavigationItemView(paramContext);
  }
  
  public boolean isItemHorizontalTranslationEnabled()
  {
    return this.itemHorizontalTranslationEnabled;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    paramInt3 -= paramInt1;
    paramInt4 -= paramInt2;
    paramInt2 = 0;
    for (paramInt1 = 0; paramInt1 < i; paramInt1++)
    {
      View localView = getChildAt(paramInt1);
      if (localView.getVisibility() != 8)
      {
        if (ViewCompat.getLayoutDirection(this) == 1) {
          localView.layout(paramInt3 - paramInt2 - localView.getMeasuredWidth(), 0, paramInt3 - paramInt2, paramInt4);
        } else {
          localView.layout(paramInt2, 0, localView.getMeasuredWidth() + paramInt2, paramInt4);
        }
        paramInt2 += localView.getMeasuredWidth();
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Object localObject1 = getMenu();
    int i3 = View.MeasureSpec.getSize(paramInt1);
    int j = ((MenuBuilder)localObject1).getVisibleItems().size();
    int n = getChildCount();
    int i2 = View.MeasureSpec.getSize(paramInt2);
    int i1 = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
    if (isShifting(getLabelVisibilityMode(), j)) {
      if (isItemHorizontalTranslationEnabled())
      {
        Object localObject2 = getChildAt(getSelectedItemPosition());
        i = this.activeItemMinWidth;
        paramInt1 = i;
        if (((View)localObject2).getVisibility() != 8)
        {
          ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(this.activeItemMaxWidth, Integer.MIN_VALUE), i1);
          paramInt1 = Math.max(i, ((View)localObject2).getMeasuredWidth());
        }
        if (((View)localObject2).getVisibility() != 8) {
          i = 1;
        } else {
          i = 0;
        }
        i = j - i;
        k = Math.min(i3 - this.inactiveItemMinWidth * i, Math.min(paramInt1, this.activeItemMaxWidth));
        if (i == 0) {
          paramInt1 = 1;
        } else {
          paramInt1 = i;
        }
        int m = Math.min((i3 - k) / paramInt1, this.inactiveItemMaxWidth);
        i = i3 - k - m * i;
        for (paramInt1 = 0; paramInt1 < n; paramInt1++) {
          if (getChildAt(paramInt1).getVisibility() != 8)
          {
            localObject2 = this.tempChildWidths;
            if (paramInt1 == getSelectedItemPosition()) {
              j = k;
            } else {
              j = m;
            }
            localObject2[paramInt1] = j;
            if (i > 0)
            {
              localObject2 = this.tempChildWidths;
              localObject2[paramInt1] += 1;
              i--;
            }
          }
          else
          {
            this.tempChildWidths[paramInt1] = 0;
          }
        }
        break label408;
      }
    }
    if (j == 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = j;
    }
    int k = Math.min(i3 / paramInt1, this.activeItemMaxWidth);
    int i = i3 - k * j;
    paramInt1 = 0;
    while (paramInt1 < n)
    {
      if (getChildAt(paramInt1).getVisibility() != 8)
      {
        localObject1 = this.tempChildWidths;
        localObject1[paramInt1] = k;
        j = i;
        if (i > 0)
        {
          localObject1[paramInt1] = (k + 1);
          j = i - 1;
        }
      }
      else
      {
        this.tempChildWidths[paramInt1] = 0;
        j = i;
      }
      paramInt1++;
      i = j;
    }
    label408:
    i = 0;
    for (paramInt1 = 0; paramInt1 < n; paramInt1++)
    {
      localObject1 = getChildAt(paramInt1);
      if (((View)localObject1).getVisibility() != 8)
      {
        ((View)localObject1).measure(View.MeasureSpec.makeMeasureSpec(this.tempChildWidths[paramInt1], 1073741824), i1);
        ((View)localObject1).getLayoutParams().width = ((View)localObject1).getMeasuredWidth();
        i += ((View)localObject1).getMeasuredWidth();
      }
    }
    setMeasuredDimension(View.resolveSizeAndState(i, View.MeasureSpec.makeMeasureSpec(i, 1073741824), 0), View.resolveSizeAndState(i2, paramInt2, 0));
  }
  
  public void setItemHorizontalTranslationEnabled(boolean paramBoolean)
  {
    this.itemHorizontalTranslationEnabled = paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/bottomnavigation/BottomNavigationMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */