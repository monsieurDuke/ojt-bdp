package com.google.android.material.slider;

public abstract interface LabelFormatter
{
  public static final int LABEL_FLOATING = 0;
  public static final int LABEL_GONE = 2;
  public static final int LABEL_VISIBLE = 3;
  public static final int LABEL_WITHIN_BOUNDS = 1;
  
  public abstract String getFormattedValue(float paramFloat);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/slider/LabelFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */