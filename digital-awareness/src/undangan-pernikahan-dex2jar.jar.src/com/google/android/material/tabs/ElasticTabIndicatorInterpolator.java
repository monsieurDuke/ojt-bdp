package com.google.android.material.tabs;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;

class ElasticTabIndicatorInterpolator
  extends TabIndicatorInterpolator
{
  private static float accInterp(float paramFloat)
  {
    return (float)(1.0D - Math.cos(paramFloat * 3.141592653589793D / 2.0D));
  }
  
  private static float decInterp(float paramFloat)
  {
    return (float)Math.sin(paramFloat * 3.141592653589793D / 2.0D);
  }
  
  void updateIndicatorForOffset(TabLayout paramTabLayout, View paramView1, View paramView2, float paramFloat, Drawable paramDrawable)
  {
    paramView1 = calculateIndicatorWidthForTab(paramTabLayout, paramView1);
    paramTabLayout = calculateIndicatorWidthForTab(paramTabLayout, paramView2);
    int i;
    if (paramView1.left < paramTabLayout.left) {
      i = 1;
    } else {
      i = 0;
    }
    float f;
    if (i != 0)
    {
      f = accInterp(paramFloat);
      paramFloat = decInterp(paramFloat);
    }
    else
    {
      f = decInterp(paramFloat);
      paramFloat = accInterp(paramFloat);
    }
    paramDrawable.setBounds(AnimationUtils.lerp((int)paramView1.left, (int)paramTabLayout.left, f), paramDrawable.getBounds().top, AnimationUtils.lerp((int)paramView1.right, (int)paramTabLayout.right, paramFloat), paramDrawable.getBounds().bottom);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/tabs/ElasticTabIndicatorInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */