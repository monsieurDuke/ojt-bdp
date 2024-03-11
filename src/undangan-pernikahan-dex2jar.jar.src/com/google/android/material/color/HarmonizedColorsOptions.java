package com.google.android.material.color;

import com.google.android.material.R.attr;

public class HarmonizedColorsOptions
{
  private final int colorAttributeToHarmonizeWith;
  private final HarmonizedColorAttributes colorAttributes;
  private final int[] colorResourceIds;
  
  private HarmonizedColorsOptions(Builder paramBuilder)
  {
    this.colorResourceIds = paramBuilder.colorResourceIds;
    this.colorAttributes = paramBuilder.colorAttributes;
    this.colorAttributeToHarmonizeWith = paramBuilder.colorAttributeToHarmonizeWith;
  }
  
  public static HarmonizedColorsOptions createMaterialDefaults()
  {
    return new Builder().setColorAttributes(HarmonizedColorAttributes.createMaterialDefaults()).build();
  }
  
  public int getColorAttributeToHarmonizeWith()
  {
    return this.colorAttributeToHarmonizeWith;
  }
  
  public HarmonizedColorAttributes getColorAttributes()
  {
    return this.colorAttributes;
  }
  
  public int[] getColorResourceIds()
  {
    return this.colorResourceIds;
  }
  
  int getThemeOverlayResourceId(int paramInt)
  {
    HarmonizedColorAttributes localHarmonizedColorAttributes = this.colorAttributes;
    if ((localHarmonizedColorAttributes != null) && (localHarmonizedColorAttributes.getThemeOverlay() != 0)) {
      paramInt = this.colorAttributes.getThemeOverlay();
    }
    return paramInt;
  }
  
  public static class Builder
  {
    private int colorAttributeToHarmonizeWith = R.attr.colorPrimary;
    private HarmonizedColorAttributes colorAttributes;
    private int[] colorResourceIds = new int[0];
    
    public HarmonizedColorsOptions build()
    {
      return new HarmonizedColorsOptions(this, null);
    }
    
    public Builder setColorAttributeToHarmonizeWith(int paramInt)
    {
      this.colorAttributeToHarmonizeWith = paramInt;
      return this;
    }
    
    public Builder setColorAttributes(HarmonizedColorAttributes paramHarmonizedColorAttributes)
    {
      this.colorAttributes = paramHarmonizedColorAttributes;
      return this;
    }
    
    public Builder setColorResourceIds(int[] paramArrayOfInt)
    {
      this.colorResourceIds = paramArrayOfInt;
      return this;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/HarmonizedColorsOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */