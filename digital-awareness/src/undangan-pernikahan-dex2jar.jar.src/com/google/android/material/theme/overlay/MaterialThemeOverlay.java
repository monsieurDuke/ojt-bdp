package com.google.android.material.theme.overlay;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.view.ContextThemeWrapper;
import com.google.android.material.R.attr;

public class MaterialThemeOverlay
{
  private static final int[] ANDROID_THEME_OVERLAY_ATTRS = { 16842752, R.attr.theme };
  private static final int[] MATERIAL_THEME_OVERLAY_ATTR = { R.attr.materialThemeOverlay };
  
  private static int obtainAndroidThemeOverlayId(Context paramContext, AttributeSet paramAttributeSet)
  {
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, ANDROID_THEME_OVERLAY_ATTRS);
    int i = paramContext.getResourceId(0, 0);
    int j = paramContext.getResourceId(1, 0);
    paramContext.recycle();
    if (i == 0) {
      i = j;
    }
    return i;
  }
  
  private static int obtainMaterialThemeOverlayId(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, MATERIAL_THEME_OVERLAY_ATTR, paramInt1, paramInt2);
    paramInt1 = paramContext.getResourceId(0, 0);
    paramContext.recycle();
    return paramInt1;
  }
  
  public static Context wrap(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramInt2 = obtainMaterialThemeOverlayId(paramContext, paramAttributeSet, paramInt1, paramInt2);
    if (((paramContext instanceof ContextThemeWrapper)) && (((ContextThemeWrapper)paramContext).getThemeResId() == paramInt2)) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if ((paramInt2 != 0) && (paramInt1 == 0))
    {
      ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(paramContext, paramInt2);
      paramInt1 = obtainAndroidThemeOverlayId(paramContext, paramAttributeSet);
      if (paramInt1 != 0) {
        localContextThemeWrapper.getTheme().applyStyle(paramInt1, true);
      }
      return localContextThemeWrapper;
    }
    return paramContext;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/theme/overlay/MaterialThemeOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */