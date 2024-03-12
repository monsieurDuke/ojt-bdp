package com.google.android.material.ripple;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.StateSet;
import androidx.core.graphics.ColorUtils;

public class RippleUtils
{
  private static final int[] ENABLED_PRESSED_STATE_SET = { 16842910, 16842919 };
  private static final int[] FOCUSED_STATE_SET;
  private static final int[] HOVERED_FOCUSED_STATE_SET;
  private static final int[] HOVERED_STATE_SET;
  static final String LOG_TAG = RippleUtils.class.getSimpleName();
  private static final int[] PRESSED_STATE_SET;
  private static final int[] SELECTED_FOCUSED_STATE_SET;
  private static final int[] SELECTED_HOVERED_FOCUSED_STATE_SET;
  private static final int[] SELECTED_HOVERED_STATE_SET;
  private static final int[] SELECTED_PRESSED_STATE_SET;
  private static final int[] SELECTED_STATE_SET;
  static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";
  public static final boolean USE_FRAMEWORK_RIPPLE;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    }
    USE_FRAMEWORK_RIPPLE = bool;
    PRESSED_STATE_SET = new int[] { 16842919 };
    HOVERED_FOCUSED_STATE_SET = new int[] { 16843623, 16842908 };
    FOCUSED_STATE_SET = new int[] { 16842908 };
    HOVERED_STATE_SET = new int[] { 16843623 };
    SELECTED_PRESSED_STATE_SET = new int[] { 16842913, 16842919 };
    SELECTED_HOVERED_FOCUSED_STATE_SET = new int[] { 16842913, 16843623, 16842908 };
    SELECTED_FOCUSED_STATE_SET = new int[] { 16842913, 16842908 };
    SELECTED_HOVERED_STATE_SET = new int[] { 16842913, 16843623 };
    SELECTED_STATE_SET = new int[] { 16842913 };
  }
  
  public static ColorStateList convertToRippleDrawableColor(ColorStateList paramColorStateList)
  {
    if (USE_FRAMEWORK_RIPPLE)
    {
      arrayOfInt = new int[2][];
      arrayOfInt1 = new int[2];
      arrayOfInt[0] = SELECTED_STATE_SET;
      arrayOfInt1[0] = getColorForState(paramColorStateList, SELECTED_PRESSED_STATE_SET);
      i = 0 + 1;
      arrayOfInt[i] = StateSet.NOTHING;
      arrayOfInt1[i] = getColorForState(paramColorStateList, PRESSED_STATE_SET);
      return new ColorStateList(arrayOfInt, arrayOfInt1);
    }
    int[][] arrayOfInt = new int[10][];
    int[] arrayOfInt1 = new int[10];
    int[] arrayOfInt2 = SELECTED_PRESSED_STATE_SET;
    arrayOfInt[0] = arrayOfInt2;
    arrayOfInt1[0] = getColorForState(paramColorStateList, arrayOfInt2);
    int i = 0 + 1;
    arrayOfInt2 = SELECTED_HOVERED_FOCUSED_STATE_SET;
    arrayOfInt[i] = arrayOfInt2;
    arrayOfInt1[i] = getColorForState(paramColorStateList, arrayOfInt2);
    i++;
    arrayOfInt2 = SELECTED_FOCUSED_STATE_SET;
    arrayOfInt[i] = arrayOfInt2;
    arrayOfInt1[i] = getColorForState(paramColorStateList, arrayOfInt2);
    i++;
    arrayOfInt2 = SELECTED_HOVERED_STATE_SET;
    arrayOfInt[i] = arrayOfInt2;
    arrayOfInt1[i] = getColorForState(paramColorStateList, arrayOfInt2);
    i++;
    arrayOfInt[i] = SELECTED_STATE_SET;
    arrayOfInt1[i] = 0;
    i++;
    arrayOfInt2 = PRESSED_STATE_SET;
    arrayOfInt[i] = arrayOfInt2;
    arrayOfInt1[i] = getColorForState(paramColorStateList, arrayOfInt2);
    i++;
    arrayOfInt2 = HOVERED_FOCUSED_STATE_SET;
    arrayOfInt[i] = arrayOfInt2;
    arrayOfInt1[i] = getColorForState(paramColorStateList, arrayOfInt2);
    i++;
    arrayOfInt2 = FOCUSED_STATE_SET;
    arrayOfInt[i] = arrayOfInt2;
    arrayOfInt1[i] = getColorForState(paramColorStateList, arrayOfInt2);
    i++;
    arrayOfInt2 = HOVERED_STATE_SET;
    arrayOfInt[i] = arrayOfInt2;
    arrayOfInt1[i] = getColorForState(paramColorStateList, arrayOfInt2);
    i++;
    arrayOfInt[i] = StateSet.NOTHING;
    arrayOfInt1[i] = 0;
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private static int doubleAlpha(int paramInt)
  {
    return ColorUtils.setAlphaComponent(paramInt, Math.min(Color.alpha(paramInt) * 2, 255));
  }
  
  private static int getColorForState(ColorStateList paramColorStateList, int[] paramArrayOfInt)
  {
    int i;
    if (paramColorStateList != null) {
      i = paramColorStateList.getColorForState(paramArrayOfInt, paramColorStateList.getDefaultColor());
    } else {
      i = 0;
    }
    if (USE_FRAMEWORK_RIPPLE) {
      i = doubleAlpha(i);
    }
    return i;
  }
  
  public static ColorStateList sanitizeRippleDrawableColor(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null)
    {
      if ((Build.VERSION.SDK_INT >= 22) && (Build.VERSION.SDK_INT <= 27) && (Color.alpha(paramColorStateList.getDefaultColor()) == 0) && (Color.alpha(paramColorStateList.getColorForState(ENABLED_PRESSED_STATE_SET, 0)) != 0)) {
        Log.w(LOG_TAG, "Use a non-transparent color for the default color as it will be used to finish ripple animations.");
      }
      return paramColorStateList;
    }
    return ColorStateList.valueOf(0);
  }
  
  public static boolean shouldDrawRippleCompat(int[] paramArrayOfInt)
  {
    int k = 0;
    int i = 0;
    int n = paramArrayOfInt.length;
    boolean bool2 = false;
    int j = 0;
    while (j < n)
    {
      int i1 = paramArrayOfInt[j];
      int m;
      if (i1 == 16842910)
      {
        m = 1;
      }
      else if (i1 == 16842908)
      {
        i = 1;
        m = k;
      }
      else if (i1 == 16842919)
      {
        i = 1;
        m = k;
      }
      else
      {
        m = k;
        if (i1 == 16843623)
        {
          i = 1;
          m = k;
        }
      }
      j++;
      k = m;
    }
    boolean bool1 = bool2;
    if (k != 0)
    {
      bool1 = bool2;
      if (i != 0) {
        bool1 = true;
      }
    }
    return bool1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/ripple/RippleUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */