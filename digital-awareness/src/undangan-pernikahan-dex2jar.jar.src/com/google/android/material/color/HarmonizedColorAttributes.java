package com.google.android.material.color;

import com.google.android.material.R.attr;
import com.google.android.material.R.style;

public final class HarmonizedColorAttributes
{
  private static final int[] HARMONIZED_MATERIAL_ATTRIBUTES = { R.attr.colorError, R.attr.colorOnError, R.attr.colorErrorContainer, R.attr.colorOnErrorContainer };
  private final int[] attributes;
  private final int themeOverlay;
  
  private HarmonizedColorAttributes(int[] paramArrayOfInt, int paramInt)
  {
    if ((paramInt != 0) && (paramArrayOfInt.length == 0)) {
      throw new IllegalArgumentException("Theme overlay should be used with the accompanying int[] attributes.");
    }
    this.attributes = paramArrayOfInt;
    this.themeOverlay = paramInt;
  }
  
  public static HarmonizedColorAttributes create(int[] paramArrayOfInt)
  {
    return new HarmonizedColorAttributes(paramArrayOfInt, 0);
  }
  
  public static HarmonizedColorAttributes create(int[] paramArrayOfInt, int paramInt)
  {
    return new HarmonizedColorAttributes(paramArrayOfInt, paramInt);
  }
  
  public static HarmonizedColorAttributes createMaterialDefaults()
  {
    return create(HARMONIZED_MATERIAL_ATTRIBUTES, R.style.ThemeOverlay_Material3_HarmonizedColors);
  }
  
  public int[] getAttributes()
  {
    return this.attributes;
  }
  
  public int getThemeOverlay()
  {
    return this.themeOverlay;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/HarmonizedColorAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */