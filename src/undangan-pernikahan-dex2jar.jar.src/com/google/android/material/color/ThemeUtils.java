package com.google.android.material.color;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.view.View;
import android.view.Window;

final class ThemeUtils
{
  static void applyThemeOverlay(Context paramContext, int paramInt)
  {
    paramContext.getTheme().applyStyle(paramInt, true);
    if ((paramContext instanceof Activity))
    {
      paramContext = getWindowDecorViewTheme((Activity)paramContext);
      if (paramContext != null) {
        paramContext.applyStyle(paramInt, true);
      }
    }
  }
  
  private static Resources.Theme getWindowDecorViewTheme(Activity paramActivity)
  {
    paramActivity = paramActivity.getWindow();
    if (paramActivity != null)
    {
      paramActivity = paramActivity.peekDecorView();
      if (paramActivity != null)
      {
        paramActivity = paramActivity.getContext();
        if (paramActivity != null) {
          return paramActivity.getTheme();
        }
      }
    }
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/ThemeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */