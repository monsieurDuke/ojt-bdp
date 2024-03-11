package com.google.android.material.timepicker;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

abstract interface TimePickerControls
{
  public abstract void setActiveSelection(int paramInt);
  
  public abstract void setHandRotation(float paramFloat);
  
  public abstract void setValues(String[] paramArrayOfString, int paramInt);
  
  public abstract void updateTime(int paramInt1, int paramInt2, int paramInt3);
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ActiveSelection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ClockPeriod {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/TimePickerControls.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */