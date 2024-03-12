package com.google.android.material.elevation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R.attr;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;

public class ElevationOverlayProvider
{
  private static final float FORMULA_MULTIPLIER = 4.5F;
  private static final float FORMULA_OFFSET = 2.0F;
  private static final int OVERLAY_ACCENT_COLOR_ALPHA = (int)Math.round(5.1000000000000005D);
  private final int colorSurface;
  private final float displayDensity;
  private final int elevationOverlayAccentColor;
  private final int elevationOverlayColor;
  private final boolean elevationOverlayEnabled;
  
  public ElevationOverlayProvider(Context paramContext)
  {
    this(MaterialAttributes.resolveBoolean(paramContext, R.attr.elevationOverlayEnabled, false), MaterialColors.getColor(paramContext, R.attr.elevationOverlayColor, 0), MaterialColors.getColor(paramContext, R.attr.elevationOverlayAccentColor, 0), MaterialColors.getColor(paramContext, R.attr.colorSurface, 0), paramContext.getResources().getDisplayMetrics().density);
  }
  
  public ElevationOverlayProvider(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    this.elevationOverlayEnabled = paramBoolean;
    this.elevationOverlayColor = paramInt1;
    this.elevationOverlayAccentColor = paramInt2;
    this.colorSurface = paramInt3;
    this.displayDensity = paramFloat;
  }
  
  private boolean isThemeSurfaceColor(int paramInt)
  {
    boolean bool;
    if (ColorUtils.setAlphaComponent(paramInt, 255) == this.colorSurface) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int calculateOverlayAlpha(float paramFloat)
  {
    return Math.round(calculateOverlayAlphaFraction(paramFloat) * 255.0F);
  }
  
  public float calculateOverlayAlphaFraction(float paramFloat)
  {
    float f = this.displayDensity;
    if ((f > 0.0F) && (paramFloat > 0.0F)) {
      return Math.min(((float)Math.log1p(paramFloat / f) * 4.5F + 2.0F) / 100.0F, 1.0F);
    }
    return 0.0F;
  }
  
  public int compositeOverlay(int paramInt, float paramFloat)
  {
    paramFloat = calculateOverlayAlphaFraction(paramFloat);
    int j = Color.alpha(paramInt);
    int i = MaterialColors.layer(ColorUtils.setAlphaComponent(paramInt, 255), this.elevationOverlayColor, paramFloat);
    paramInt = i;
    if (paramFloat > 0.0F)
    {
      int k = this.elevationOverlayAccentColor;
      paramInt = i;
      if (k != 0) {
        paramInt = MaterialColors.layer(i, ColorUtils.setAlphaComponent(k, OVERLAY_ACCENT_COLOR_ALPHA));
      }
    }
    return ColorUtils.setAlphaComponent(paramInt, j);
  }
  
  public int compositeOverlay(int paramInt, float paramFloat, View paramView)
  {
    return compositeOverlay(paramInt, paramFloat + getParentAbsoluteElevation(paramView));
  }
  
  public int compositeOverlayIfNeeded(int paramInt, float paramFloat)
  {
    if ((this.elevationOverlayEnabled) && (isThemeSurfaceColor(paramInt))) {
      return compositeOverlay(paramInt, paramFloat);
    }
    return paramInt;
  }
  
  public int compositeOverlayIfNeeded(int paramInt, float paramFloat, View paramView)
  {
    return compositeOverlayIfNeeded(paramInt, paramFloat + getParentAbsoluteElevation(paramView));
  }
  
  public int compositeOverlayWithThemeSurfaceColorIfNeeded(float paramFloat)
  {
    return compositeOverlayIfNeeded(this.colorSurface, paramFloat);
  }
  
  public int compositeOverlayWithThemeSurfaceColorIfNeeded(float paramFloat, View paramView)
  {
    return compositeOverlayWithThemeSurfaceColorIfNeeded(paramFloat + getParentAbsoluteElevation(paramView));
  }
  
  public float getParentAbsoluteElevation(View paramView)
  {
    return ViewUtils.getParentAbsoluteElevation(paramView);
  }
  
  public int getThemeElevationOverlayColor()
  {
    return this.elevationOverlayColor;
  }
  
  public int getThemeSurfaceColor()
  {
    return this.colorSurface;
  }
  
  public boolean isThemeElevationOverlayEnabled()
  {
    return this.elevationOverlayEnabled;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/elevation/ElevationOverlayProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */