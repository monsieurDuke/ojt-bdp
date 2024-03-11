package com.google.android.material.transition;

import android.animation.TimeInterpolator;
import com.google.android.material.R.attr;
import com.google.android.material.animation.AnimationUtils;

public final class MaterialFade
  extends MaterialVisibility<FadeProvider>
{
  private static final float DEFAULT_FADE_END_THRESHOLD_ENTER = 0.3F;
  private static final float DEFAULT_START_SCALE = 0.8F;
  private static final int DEFAULT_THEMED_EASING_ATTR = R.attr.motionEasingLinear;
  private static final int DEFAULT_THEMED_INCOMING_DURATION_ATTR = R.attr.motionDurationShort2;
  private static final int DEFAULT_THEMED_OUTGOING_DURATION_ATTR = R.attr.motionDurationShort1;
  
  public MaterialFade()
  {
    super(createPrimaryAnimatorProvider(), createSecondaryAnimatorProvider());
  }
  
  private static FadeProvider createPrimaryAnimatorProvider()
  {
    FadeProvider localFadeProvider = new FadeProvider();
    localFadeProvider.setIncomingEndThreshold(0.3F);
    return localFadeProvider;
  }
  
  private static VisibilityAnimatorProvider createSecondaryAnimatorProvider()
  {
    ScaleProvider localScaleProvider = new ScaleProvider();
    localScaleProvider.setScaleOnDisappear(false);
    localScaleProvider.setIncomingStartScale(0.8F);
    return localScaleProvider;
  }
  
  TimeInterpolator getDefaultEasingInterpolator(boolean paramBoolean)
  {
    return AnimationUtils.LINEAR_INTERPOLATOR;
  }
  
  int getDurationThemeAttrResId(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = DEFAULT_THEMED_INCOMING_DURATION_ATTR;
    } else {
      i = DEFAULT_THEMED_OUTGOING_DURATION_ATTR;
    }
    return i;
  }
  
  int getEasingThemeAttrResId(boolean paramBoolean)
  {
    return DEFAULT_THEMED_EASING_ATTR;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/MaterialFade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */