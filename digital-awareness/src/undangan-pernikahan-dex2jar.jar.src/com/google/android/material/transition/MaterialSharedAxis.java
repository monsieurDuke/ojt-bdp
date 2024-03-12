package com.google.android.material.transition;

import com.google.android.material.R.attr;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class MaterialSharedAxis
  extends MaterialVisibility<VisibilityAnimatorProvider>
{
  private static final int DEFAULT_THEMED_DURATION_ATTR = R.attr.motionDurationLong1;
  private static final int DEFAULT_THEMED_EASING_ATTR = R.attr.motionEasingStandard;
  public static final int X = 0;
  public static final int Y = 1;
  public static final int Z = 2;
  private final int axis;
  private final boolean forward;
  
  public MaterialSharedAxis(int paramInt, boolean paramBoolean)
  {
    super(createPrimaryAnimatorProvider(paramInt, paramBoolean), createSecondaryAnimatorProvider());
    this.axis = paramInt;
    this.forward = paramBoolean;
  }
  
  private static VisibilityAnimatorProvider createPrimaryAnimatorProvider(int paramInt, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Invalid axis: " + paramInt);
    case 2: 
      return new ScaleProvider(paramBoolean);
    case 1: 
      if (paramBoolean) {
        paramInt = 80;
      } else {
        paramInt = 48;
      }
      return new SlideDistanceProvider(paramInt);
    }
    if (paramBoolean) {
      paramInt = 8388613;
    } else {
      paramInt = 8388611;
    }
    return new SlideDistanceProvider(paramInt);
  }
  
  private static VisibilityAnimatorProvider createSecondaryAnimatorProvider()
  {
    return new FadeThroughProvider();
  }
  
  public int getAxis()
  {
    return this.axis;
  }
  
  int getDurationThemeAttrResId(boolean paramBoolean)
  {
    return DEFAULT_THEMED_DURATION_ATTR;
  }
  
  int getEasingThemeAttrResId(boolean paramBoolean)
  {
    return DEFAULT_THEMED_EASING_ATTR;
  }
  
  public boolean isForward()
  {
    return this.forward;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Axis {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/MaterialSharedAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */