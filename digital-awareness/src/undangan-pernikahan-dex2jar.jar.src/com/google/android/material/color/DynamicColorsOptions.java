package com.google.android.material.color;

import android.app.Activity;

public class DynamicColorsOptions
{
  private static final DynamicColors.Precondition ALWAYS_ALLOW = new DynamicColors.Precondition()
  {
    public boolean shouldApplyDynamicColors(Activity paramAnonymousActivity, int paramAnonymousInt)
    {
      return true;
    }
  };
  private static final DynamicColors.OnAppliedCallback NO_OP_CALLBACK = new DynamicColors.OnAppliedCallback()
  {
    public void onApplied(Activity paramAnonymousActivity) {}
  };
  private final DynamicColors.OnAppliedCallback onAppliedCallback;
  private final DynamicColors.Precondition precondition;
  private final int themeOverlay;
  
  private DynamicColorsOptions(Builder paramBuilder)
  {
    this.themeOverlay = paramBuilder.themeOverlay;
    this.precondition = paramBuilder.precondition;
    this.onAppliedCallback = paramBuilder.onAppliedCallback;
  }
  
  public DynamicColors.OnAppliedCallback getOnAppliedCallback()
  {
    return this.onAppliedCallback;
  }
  
  public DynamicColors.Precondition getPrecondition()
  {
    return this.precondition;
  }
  
  public int getThemeOverlay()
  {
    return this.themeOverlay;
  }
  
  public static class Builder
  {
    private DynamicColors.OnAppliedCallback onAppliedCallback = DynamicColorsOptions.NO_OP_CALLBACK;
    private DynamicColors.Precondition precondition = DynamicColorsOptions.ALWAYS_ALLOW;
    private int themeOverlay;
    
    public DynamicColorsOptions build()
    {
      return new DynamicColorsOptions(this, null);
    }
    
    public Builder setOnAppliedCallback(DynamicColors.OnAppliedCallback paramOnAppliedCallback)
    {
      this.onAppliedCallback = paramOnAppliedCallback;
      return this;
    }
    
    public Builder setPrecondition(DynamicColors.Precondition paramPrecondition)
    {
      this.precondition = paramPrecondition;
      return this;
    }
    
    public Builder setThemeOverlay(int paramInt)
    {
      this.themeOverlay = paramInt;
      return this;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/DynamicColorsOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */