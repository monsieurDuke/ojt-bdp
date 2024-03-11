package com.google.android.material.switchmaterial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class SwitchMaterial
  extends SwitchCompat
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CompoundButton_Switch;
  private static final int[][] ENABLED_CHECKED_STATES;
  private final ElevationOverlayProvider elevationOverlayProvider;
  private ColorStateList materialThemeColorsThumbTintList;
  private ColorStateList materialThemeColorsTrackTintList;
  private boolean useMaterialThemeColors;
  
  static
  {
    int[] arrayOfInt2 = { -16842910, 16842912 };
    int[] arrayOfInt1 = { -16842910, -16842912 };
    ENABLED_CHECKED_STATES = new int[][] { { 16842910, 16842912 }, { 16842910, -16842912 }, arrayOfInt2, arrayOfInt1 };
  }
  
  public SwitchMaterial(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SwitchMaterial(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.switchStyle);
  }
  
  public SwitchMaterial(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = getContext();
    this.elevationOverlayProvider = new ElevationOverlayProvider(paramContext);
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.SwitchMaterial, paramInt, i, new int[0]);
    this.useMaterialThemeColors = paramContext.getBoolean(R.styleable.SwitchMaterial_useMaterialThemeColors, false);
    paramContext.recycle();
  }
  
  private ColorStateList getMaterialThemeColorsThumbTintList()
  {
    if (this.materialThemeColorsThumbTintList == null)
    {
      int i = MaterialColors.getColor(this, R.attr.colorSurface);
      int j = MaterialColors.getColor(this, R.attr.colorControlActivated);
      float f2 = getResources().getDimension(R.dimen.mtrl_switch_thumb_elevation);
      float f1 = f2;
      if (this.elevationOverlayProvider.isThemeElevationOverlayEnabled()) {
        f1 = f2 + ViewUtils.getParentAbsoluteElevation(this);
      }
      int k = this.elevationOverlayProvider.compositeOverlayIfNeeded(i, f1);
      int[][] arrayOfInt1 = ENABLED_CHECKED_STATES;
      int[] arrayOfInt = new int[arrayOfInt1.length];
      arrayOfInt[0] = MaterialColors.layer(i, j, 1.0F);
      arrayOfInt[1] = k;
      arrayOfInt[2] = MaterialColors.layer(i, j, 0.38F);
      arrayOfInt[3] = k;
      this.materialThemeColorsThumbTintList = new ColorStateList(arrayOfInt1, arrayOfInt);
    }
    return this.materialThemeColorsThumbTintList;
  }
  
  private ColorStateList getMaterialThemeColorsTrackTintList()
  {
    if (this.materialThemeColorsTrackTintList == null)
    {
      int[][] arrayOfInt = ENABLED_CHECKED_STATES;
      int[] arrayOfInt1 = new int[arrayOfInt.length];
      int j = MaterialColors.getColor(this, R.attr.colorSurface);
      int k = MaterialColors.getColor(this, R.attr.colorControlActivated);
      int i = MaterialColors.getColor(this, R.attr.colorOnSurface);
      arrayOfInt1[0] = MaterialColors.layer(j, k, 0.54F);
      arrayOfInt1[1] = MaterialColors.layer(j, i, 0.32F);
      arrayOfInt1[2] = MaterialColors.layer(j, k, 0.12F);
      arrayOfInt1[3] = MaterialColors.layer(j, i, 0.12F);
      this.materialThemeColorsTrackTintList = new ColorStateList(arrayOfInt, arrayOfInt1);
    }
    return this.materialThemeColorsTrackTintList;
  }
  
  public boolean isUseMaterialThemeColors()
  {
    return this.useMaterialThemeColors;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if ((this.useMaterialThemeColors) && (getThumbTintList() == null)) {
      setThumbTintList(getMaterialThemeColorsThumbTintList());
    }
    if ((this.useMaterialThemeColors) && (getTrackTintList() == null)) {
      setTrackTintList(getMaterialThemeColorsTrackTintList());
    }
  }
  
  public void setUseMaterialThemeColors(boolean paramBoolean)
  {
    this.useMaterialThemeColors = paramBoolean;
    if (paramBoolean)
    {
      setThumbTintList(getMaterialThemeColorsThumbTintList());
      setTrackTintList(getMaterialThemeColorsTrackTintList());
    }
    else
    {
      setThumbTintList(null);
      setTrackTintList(null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/switchmaterial/SwitchMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */