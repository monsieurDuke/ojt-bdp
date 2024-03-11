package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.material.R.dimen;
import com.google.android.material.R.layout;
import com.google.android.material.navigation.NavigationBarItemView;

final class NavigationRailItemView
  extends NavigationBarItemView
{
  public NavigationRailItemView(Context paramContext)
  {
    super(paramContext);
  }
  
  protected int getItemDefaultMarginResId()
  {
    return R.dimen.mtrl_navigation_rail_icon_margin;
  }
  
  protected int getItemLayoutResId()
  {
    return R.layout.mtrl_navigation_rail_item;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (View.MeasureSpec.getMode(paramInt2) == 0)
    {
      paramInt1 = View.MeasureSpec.getSize(paramInt2);
      paramInt1 = Math.max(getMeasuredHeight(), paramInt1);
      setMeasuredDimension(getMeasuredWidthAndState(), View.resolveSizeAndState(paramInt1, paramInt2, 0));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigationrail/NavigationRailItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */