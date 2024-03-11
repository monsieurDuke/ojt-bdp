package com.google.android.material.bottomnavigation;

import android.content.Context;
import com.google.android.material.R.dimen;
import com.google.android.material.R.layout;
import com.google.android.material.navigation.NavigationBarItemView;

public class BottomNavigationItemView
  extends NavigationBarItemView
{
  public BottomNavigationItemView(Context paramContext)
  {
    super(paramContext);
  }
  
  protected int getItemDefaultMarginResId()
  {
    return R.dimen.design_bottom_navigation_margin;
  }
  
  protected int getItemLayoutResId()
  {
    return R.layout.design_bottom_navigation_item;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/bottomnavigation/BottomNavigationItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */