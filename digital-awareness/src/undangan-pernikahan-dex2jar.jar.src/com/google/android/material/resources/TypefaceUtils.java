package com.google.android.material.resources;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import androidx.core.math.MathUtils;

public class TypefaceUtils
{
  public static Typeface maybeCopyWithFontWeightAdjustment(Context paramContext, Typeface paramTypeface)
  {
    return maybeCopyWithFontWeightAdjustment(paramContext.getResources().getConfiguration(), paramTypeface);
  }
  
  public static Typeface maybeCopyWithFontWeightAdjustment(Configuration paramConfiguration, Typeface paramTypeface)
  {
    if ((Build.VERSION.SDK_INT >= 31) && (paramConfiguration.fontWeightAdjustment != Integer.MAX_VALUE) && (paramConfiguration.fontWeightAdjustment != 0)) {
      return Typeface.create(paramTypeface, MathUtils.clamp(paramTypeface.getWeight() + paramConfiguration.fontWeightAdjustment, 1, 1000), paramTypeface.isItalic());
    }
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/resources/TypefaceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */