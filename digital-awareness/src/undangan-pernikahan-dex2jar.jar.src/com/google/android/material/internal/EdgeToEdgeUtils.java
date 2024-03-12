package com.google.android.material.internal;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.Window;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.google.android.material.color.MaterialColors;

public class EdgeToEdgeUtils
{
  private static final int EDGE_TO_EDGE_BAR_ALPHA = 128;
  
  public static void applyEdgeToEdge(Window paramWindow, boolean paramBoolean)
  {
    applyEdgeToEdge(paramWindow, paramBoolean, null, null);
  }
  
  public static void applyEdgeToEdge(Window paramWindow, boolean paramBoolean, Integer paramInteger1, Integer paramInteger2)
  {
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    int j = 0;
    if ((paramInteger1 != null) && (paramInteger1.intValue() != 0)) {
      i = 0;
    } else {
      i = 1;
    }
    if ((paramInteger2 == null) || (paramInteger2.intValue() == 0)) {
      j = 1;
    }
    Integer localInteger1;
    Integer localInteger2;
    if (i == 0)
    {
      localInteger1 = paramInteger1;
      localInteger2 = paramInteger2;
      if (j == 0) {}
    }
    else
    {
      int k = MaterialColors.getColor(paramWindow.getContext(), 16842801, -16777216);
      if (i != 0) {
        paramInteger1 = Integer.valueOf(k);
      }
      localInteger1 = paramInteger1;
      localInteger2 = paramInteger2;
      if (j != 0)
      {
        localInteger2 = Integer.valueOf(k);
        localInteger1 = paramInteger1;
      }
    }
    WindowCompat.setDecorFitsSystemWindows(paramWindow, paramBoolean ^ true);
    j = getStatusBarColor(paramWindow.getContext(), paramBoolean);
    int i = getNavigationBarColor(paramWindow.getContext(), paramBoolean);
    paramWindow.setStatusBarColor(j);
    paramWindow.setNavigationBarColor(i);
    paramBoolean = isUsingLightSystemBar(j, MaterialColors.isColorLight(localInteger1.intValue()));
    boolean bool = isUsingLightSystemBar(i, MaterialColors.isColorLight(localInteger2.intValue()));
    paramWindow = WindowCompat.getInsetsController(paramWindow, paramWindow.getDecorView());
    if (paramWindow != null)
    {
      paramWindow.setAppearanceLightStatusBars(paramBoolean);
      paramWindow.setAppearanceLightNavigationBars(bool);
    }
  }
  
  private static int getNavigationBarColor(Context paramContext, boolean paramBoolean)
  {
    if ((paramBoolean) && (Build.VERSION.SDK_INT < 27)) {
      return ColorUtils.setAlphaComponent(MaterialColors.getColor(paramContext, 16843858, -16777216), 128);
    }
    if (paramBoolean) {
      return 0;
    }
    return MaterialColors.getColor(paramContext, 16843858, -16777216);
  }
  
  private static int getStatusBarColor(Context paramContext, boolean paramBoolean)
  {
    if ((paramBoolean) && (Build.VERSION.SDK_INT < 23)) {
      return ColorUtils.setAlphaComponent(MaterialColors.getColor(paramContext, 16843857, -16777216), 128);
    }
    if (paramBoolean) {
      return 0;
    }
    return MaterialColors.getColor(paramContext, 16843857, -16777216);
  }
  
  private static boolean isUsingLightSystemBar(int paramInt, boolean paramBoolean)
  {
    if ((!MaterialColors.isColorLight(paramInt)) && ((paramInt != 0) || (!paramBoolean))) {
      paramBoolean = false;
    } else {
      paramBoolean = true;
    }
    return paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/EdgeToEdgeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */