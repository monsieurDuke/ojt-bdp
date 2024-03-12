package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R.attr;
import com.google.android.material.resources.MaterialAttributes;

public class MaterialColors
{
  public static final float ALPHA_DISABLED = 0.38F;
  public static final float ALPHA_DISABLED_LOW = 0.12F;
  public static final float ALPHA_FULL = 1.0F;
  public static final float ALPHA_LOW = 0.32F;
  public static final float ALPHA_MEDIUM = 0.54F;
  private static final int TONE_ACCENT_CONTAINER_DARK = 30;
  private static final int TONE_ACCENT_CONTAINER_LIGHT = 90;
  private static final int TONE_ACCENT_DARK = 80;
  private static final int TONE_ACCENT_LIGHT = 40;
  private static final int TONE_ON_ACCENT_CONTAINER_DARK = 90;
  private static final int TONE_ON_ACCENT_CONTAINER_LIGHT = 10;
  private static final int TONE_ON_ACCENT_DARK = 20;
  private static final int TONE_ON_ACCENT_LIGHT = 100;
  
  public static int compositeARGBWithAlpha(int paramInt1, int paramInt2)
  {
    return ColorUtils.setAlphaComponent(paramInt1, Color.alpha(paramInt1) * paramInt2 / 255);
  }
  
  public static int getColor(Context paramContext, int paramInt1, int paramInt2)
  {
    paramContext = MaterialAttributes.resolve(paramContext, paramInt1);
    if (paramContext != null) {
      return paramContext.data;
    }
    return paramInt2;
  }
  
  public static int getColor(Context paramContext, int paramInt, String paramString)
  {
    return MaterialAttributes.resolveOrThrow(paramContext, paramInt, paramString);
  }
  
  public static int getColor(View paramView, int paramInt)
  {
    return MaterialAttributes.resolveOrThrow(paramView, paramInt);
  }
  
  public static int getColor(View paramView, int paramInt1, int paramInt2)
  {
    return getColor(paramView.getContext(), paramInt1, paramInt2);
  }
  
  private static int getColorRole(int paramInt1, int paramInt2)
  {
    Hct localHct = Hct.fromInt(paramInt1);
    localHct.setTone(paramInt2);
    return localHct.toInt();
  }
  
  public static ColorRoles getColorRoles(int paramInt, boolean paramBoolean)
  {
    ColorRoles localColorRoles;
    if (paramBoolean) {
      localColorRoles = new ColorRoles(getColorRole(paramInt, 40), getColorRole(paramInt, 100), getColorRole(paramInt, 90), getColorRole(paramInt, 10));
    } else {
      localColorRoles = new ColorRoles(getColorRole(paramInt, 80), getColorRole(paramInt, 20), getColorRole(paramInt, 30), getColorRole(paramInt, 90));
    }
    return localColorRoles;
  }
  
  public static ColorRoles getColorRoles(Context paramContext, int paramInt)
  {
    return getColorRoles(paramInt, MaterialAttributes.resolveBoolean(paramContext, R.attr.isLightTheme, true));
  }
  
  public static int harmonize(int paramInt1, int paramInt2)
  {
    return Blend.harmonize(paramInt1, paramInt2);
  }
  
  public static int harmonizeWithPrimary(Context paramContext, int paramInt)
  {
    return harmonize(paramInt, getColor(paramContext, R.attr.colorPrimary, MaterialColors.class.getCanonicalName()));
  }
  
  public static boolean isColorLight(int paramInt)
  {
    boolean bool;
    if ((paramInt != 0) && (ColorUtils.calculateLuminance(paramInt) > 0.5D)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static int layer(int paramInt1, int paramInt2)
  {
    return ColorUtils.compositeColors(paramInt2, paramInt1);
  }
  
  public static int layer(int paramInt1, int paramInt2, float paramFloat)
  {
    return layer(paramInt1, ColorUtils.setAlphaComponent(paramInt2, Math.round(Color.alpha(paramInt2) * paramFloat)));
  }
  
  public static int layer(View paramView, int paramInt1, int paramInt2)
  {
    return layer(paramView, paramInt1, paramInt2, 1.0F);
  }
  
  public static int layer(View paramView, int paramInt1, int paramInt2, float paramFloat)
  {
    return layer(getColor(paramView, paramInt1), getColor(paramView, paramInt2), paramFloat);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/MaterialColors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */