package com.google.android.material.tabs;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;

class FadeTabIndicatorInterpolator
  extends TabIndicatorInterpolator
{
  private static final float FADE_THRESHOLD = 0.5F;
  
  void updateIndicatorForOffset(TabLayout paramTabLayout, View paramView1, View paramView2, float paramFloat, Drawable paramDrawable)
  {
    if (paramFloat < 0.5F) {
      paramView2 = paramView1;
    }
    paramTabLayout = calculateIndicatorWidthForTab(paramTabLayout, paramView2);
    if (paramFloat < 0.5F) {
      paramFloat = AnimationUtils.lerp(1.0F, 0.0F, 0.0F, 0.5F, paramFloat);
    } else {
      paramFloat = AnimationUtils.lerp(0.0F, 1.0F, 0.5F, 1.0F, paramFloat);
    }
    paramDrawable.setBounds((int)paramTabLayout.left, paramDrawable.getBounds().top, (int)paramTabLayout.right, paramDrawable.getBounds().bottom);
    paramDrawable.setAlpha((int)(255.0F * paramFloat));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/tabs/FadeTabIndicatorInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */