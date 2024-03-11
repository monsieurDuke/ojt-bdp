package com.google.android.material.tabs;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;

class TabIndicatorInterpolator
{
  private static final int MIN_INDICATOR_WIDTH = 24;
  
  static RectF calculateIndicatorWidthForTab(TabLayout paramTabLayout, View paramView)
  {
    if (paramView == null) {
      return new RectF();
    }
    if ((!paramTabLayout.isTabIndicatorFullWidth()) && ((paramView instanceof TabLayout.TabView))) {
      return calculateTabViewContentBounds((TabLayout.TabView)paramView, 24);
    }
    return new RectF(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
  }
  
  static RectF calculateTabViewContentBounds(TabLayout.TabView paramTabView, int paramInt)
  {
    int i = paramTabView.getContentWidth();
    int k = paramTabView.getContentHeight();
    int j = (int)ViewUtils.dpToPx(paramTabView.getContext(), paramInt);
    paramInt = i;
    if (i < j) {
      paramInt = j;
    }
    i = (paramTabView.getLeft() + paramTabView.getRight()) / 2;
    j = (paramTabView.getTop() + paramTabView.getBottom()) / 2;
    int m = paramInt / 2;
    k /= 2;
    paramInt /= 2;
    int n = i / 2;
    return new RectF(i - m, j - k, paramInt + i, n + j);
  }
  
  void setIndicatorBoundsForTab(TabLayout paramTabLayout, View paramView, Drawable paramDrawable)
  {
    paramTabLayout = calculateIndicatorWidthForTab(paramTabLayout, paramView);
    paramDrawable.setBounds((int)paramTabLayout.left, paramDrawable.getBounds().top, (int)paramTabLayout.right, paramDrawable.getBounds().bottom);
  }
  
  void updateIndicatorForOffset(TabLayout paramTabLayout, View paramView1, View paramView2, float paramFloat, Drawable paramDrawable)
  {
    paramView1 = calculateIndicatorWidthForTab(paramTabLayout, paramView1);
    paramTabLayout = calculateIndicatorWidthForTab(paramTabLayout, paramView2);
    paramDrawable.setBounds(AnimationUtils.lerp((int)paramView1.left, (int)paramTabLayout.left, paramFloat), paramDrawable.getBounds().top, AnimationUtils.lerp((int)paramView1.right, (int)paramTabLayout.right, paramFloat), paramDrawable.getBounds().bottom);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/tabs/TabIndicatorInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */