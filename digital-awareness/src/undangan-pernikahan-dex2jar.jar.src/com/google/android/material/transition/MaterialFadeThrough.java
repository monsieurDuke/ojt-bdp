package com.google.android.material.transition;

import com.google.android.material.R.attr;

public final class MaterialFadeThrough
  extends MaterialVisibility<FadeThroughProvider>
{
  private static final float DEFAULT_START_SCALE = 0.92F;
  private static final int DEFAULT_THEMED_DURATION_ATTR = R.attr.motionDurationLong1;
  private static final int DEFAULT_THEMED_EASING_ATTR = R.attr.motionEasingStandard;
  
  public MaterialFadeThrough()
  {
    super(createPrimaryAnimatorProvider(), createSecondaryAnimatorProvider());
  }
  
  private static FadeThroughProvider createPrimaryAnimatorProvider()
  {
    return new FadeThroughProvider();
  }
  
  private static VisibilityAnimatorProvider createSecondaryAnimatorProvider()
  {
    ScaleProvider localScaleProvider = new ScaleProvider();
    localScaleProvider.setScaleOnDisappear(false);
    localScaleProvider.setIncomingStartScale(0.92F);
    return localScaleProvider;
  }
  
  int getDurationThemeAttrResId(boolean paramBoolean)
  {
    return DEFAULT_THEMED_DURATION_ATTR;
  }
  
  int getEasingThemeAttrResId(boolean paramBoolean)
  {
    return DEFAULT_THEMED_EASING_ATTR;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/MaterialFadeThrough.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */