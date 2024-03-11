package com.google.android.material.motion;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.TypedValue;
import androidx.core.graphics.PathParser;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.google.android.material.resources.MaterialAttributes;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MotionUtils
{
  private static final String EASING_TYPE_CUBIC_BEZIER = "cubic-bezier";
  private static final String EASING_TYPE_FORMAT_END = ")";
  private static final String EASING_TYPE_FORMAT_START = "(";
  private static final String EASING_TYPE_PATH = "path";
  
  private static float getControlPoint(String[] paramArrayOfString, int paramInt)
  {
    float f = Float.parseFloat(paramArrayOfString[paramInt]);
    if ((f >= 0.0F) && (f <= 1.0F)) {
      return f;
    }
    throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + f);
  }
  
  private static String getEasingContent(String paramString1, String paramString2)
  {
    return paramString1.substring(paramString2.length() + "(".length(), paramString1.length() - ")".length());
  }
  
  private static boolean isEasingType(String paramString1, String paramString2)
  {
    boolean bool;
    if ((paramString1.startsWith(paramString2 + "(")) && (paramString1.endsWith(")"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static int resolveThemeDuration(Context paramContext, int paramInt1, int paramInt2)
  {
    return MaterialAttributes.resolveInteger(paramContext, paramInt1, paramInt2);
  }
  
  public static TimeInterpolator resolveThemeInterpolator(Context paramContext, int paramInt, TimeInterpolator paramTimeInterpolator)
  {
    TypedValue localTypedValue = new TypedValue();
    if (paramContext.getTheme().resolveAttribute(paramInt, localTypedValue, true))
    {
      if (localTypedValue.type == 3)
      {
        paramContext = String.valueOf(localTypedValue.string);
        Log5ECF72.a(paramContext);
        LogE84000.a(paramContext);
        Log229316.a(paramContext);
        if (isEasingType(paramContext, "cubic-bezier"))
        {
          paramContext = getEasingContent(paramContext, "cubic-bezier");
          Log5ECF72.a(paramContext);
          LogE84000.a(paramContext);
          Log229316.a(paramContext);
          paramContext = paramContext.split(",");
          if (paramContext.length == 4) {
            return PathInterpolatorCompat.create(getControlPoint(paramContext, 0), getControlPoint(paramContext, 1), getControlPoint(paramContext, 2), getControlPoint(paramContext, 3));
          }
          throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + paramContext.length);
        }
        if (isEasingType(paramContext, "path"))
        {
          paramContext = getEasingContent(paramContext, "path");
          Log5ECF72.a(paramContext);
          LogE84000.a(paramContext);
          Log229316.a(paramContext);
          return PathInterpolatorCompat.create(PathParser.createPathFromPathData(paramContext));
        }
        throw new IllegalArgumentException("Invalid motion easing type: " + paramContext);
      }
      throw new IllegalArgumentException("Motion easing theme attribute must be a string");
    }
    return paramTimeInterpolator;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/motion/MotionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */