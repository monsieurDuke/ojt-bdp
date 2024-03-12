package com.google.android.material.progressindicator;

import android.content.ContentResolver;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;

public class AnimatorDurationScaleProvider
{
  private static float defaultSystemAnimatorDurationScale = 1.0F;
  
  public static void setDefaultSystemAnimatorDurationScale(float paramFloat)
  {
    defaultSystemAnimatorDurationScale = paramFloat;
  }
  
  public float getSystemAnimatorDurationScale(ContentResolver paramContentResolver)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Settings.Global.getFloat(paramContentResolver, "animator_duration_scale", 1.0F);
    }
    if (Build.VERSION.SDK_INT == 16) {
      return Settings.System.getFloat(paramContentResolver, "animator_duration_scale", 1.0F);
    }
    return defaultSystemAnimatorDurationScale;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/AnimatorDurationScaleProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */