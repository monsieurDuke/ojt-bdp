package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R.string;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class TimePickerClockPresenter
  implements ClockHandView.OnRotateListener, TimePickerView.OnSelectionChange, TimePickerView.OnPeriodChangeListener, ClockHandView.OnActionUpListener, TimePickerPresenter
{
  private static final int DEGREES_PER_HOUR = 30;
  private static final int DEGREES_PER_MINUTE = 6;
  private static final String[] HOUR_CLOCK_24_VALUES = { "00", "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22" };
  private static final String[] HOUR_CLOCK_VALUES = { "12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
  private static final String[] MINUTE_CLOCK_VALUES = { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
  private boolean broadcasting = false;
  private float hourRotation;
  private float minuteRotation;
  private final TimeModel time;
  private final TimePickerView timePickerView;
  
  public TimePickerClockPresenter(TimePickerView paramTimePickerView, TimeModel paramTimeModel)
  {
    this.timePickerView = paramTimePickerView;
    this.time = paramTimeModel;
    initialize();
  }
  
  private int getDegreesPerHour()
  {
    int i;
    if (this.time.format == 1) {
      i = 15;
    } else {
      i = 30;
    }
    return i;
  }
  
  private String[] getHourClockValues()
  {
    String[] arrayOfString;
    if (this.time.format == 1) {
      arrayOfString = HOUR_CLOCK_24_VALUES;
    } else {
      arrayOfString = HOUR_CLOCK_VALUES;
    }
    return arrayOfString;
  }
  
  private void performHapticFeedback(int paramInt1, int paramInt2)
  {
    if ((this.time.minute != paramInt2) || (this.time.hour != paramInt1))
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramInt1 = 4;
      } else {
        paramInt1 = 1;
      }
      this.timePickerView.performHapticFeedback(paramInt1);
    }
  }
  
  private void updateTime()
  {
    this.timePickerView.updateTime(this.time.period, this.time.getHourForDisplay(), this.time.minute);
  }
  
  private void updateValues()
  {
    updateValues(HOUR_CLOCK_VALUES, "%d");
    updateValues(HOUR_CLOCK_24_VALUES, "%d");
    updateValues(MINUTE_CLOCK_VALUES, "%02d");
  }
  
  private void updateValues(String[] paramArrayOfString, String paramString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      String str = TimeModel.formatText(this.timePickerView.getResources(), paramArrayOfString[i], paramString);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      paramArrayOfString[i] = str;
    }
  }
  
  public void hide()
  {
    this.timePickerView.setVisibility(8);
  }
  
  public void initialize()
  {
    if (this.time.format == 0) {
      this.timePickerView.showToggle();
    }
    this.timePickerView.addOnRotateListener(this);
    this.timePickerView.setOnSelectionChangeListener(this);
    this.timePickerView.setOnPeriodChangeListener(this);
    this.timePickerView.setOnActionUpListener(this);
    updateValues();
    invalidate();
  }
  
  public void invalidate()
  {
    int i = this.time.getHourForDisplay();
    this.hourRotation = (getDegreesPerHour() * i);
    this.minuteRotation = (this.time.minute * 6);
    setSelection(this.time.selection, false);
    updateTime();
  }
  
  public void onActionUp(float paramFloat, boolean paramBoolean)
  {
    this.broadcasting = true;
    int j = this.time.minute;
    int k = this.time.hour;
    int i;
    if (this.time.selection == 10)
    {
      this.timePickerView.setHandRotation(this.hourRotation, false);
      AccessibilityManager localAccessibilityManager = (AccessibilityManager)ContextCompat.getSystemService(this.timePickerView.getContext(), AccessibilityManager.class);
      if ((localAccessibilityManager != null) && (localAccessibilityManager.isTouchExplorationEnabled())) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        setSelection(12, true);
      }
    }
    else
    {
      i = Math.round(paramFloat);
      if (!paramBoolean)
      {
        i = (i + 15) / 30;
        this.time.setMinute(i * 5);
        this.minuteRotation = (this.time.minute * 6);
      }
      this.timePickerView.setHandRotation(this.minuteRotation, paramBoolean);
    }
    this.broadcasting = false;
    updateTime();
    performHapticFeedback(k, j);
  }
  
  public void onPeriodChange(int paramInt)
  {
    this.time.setPeriod(paramInt);
  }
  
  public void onRotate(float paramFloat, boolean paramBoolean)
  {
    if (this.broadcasting) {
      return;
    }
    int j = this.time.hour;
    int k = this.time.minute;
    int m = Math.round(paramFloat);
    if (this.time.selection == 12)
    {
      this.time.setMinute((m + 3) / 6);
      this.minuteRotation = ((float)Math.floor(this.time.minute * 6));
    }
    else
    {
      int i = getDegreesPerHour() / 2;
      this.time.setHour((m + i) / getDegreesPerHour());
      this.hourRotation = (this.time.getHourForDisplay() * getDegreesPerHour());
    }
    if (!paramBoolean)
    {
      updateTime();
      performHapticFeedback(j, k);
    }
  }
  
  public void onSelectionChanged(int paramInt)
  {
    setSelection(paramInt, true);
  }
  
  void setSelection(int paramInt, boolean paramBoolean)
  {
    boolean bool;
    if (paramInt == 12) {
      bool = true;
    } else {
      bool = false;
    }
    this.timePickerView.setAnimateOnTouchUp(bool);
    this.time.selection = paramInt;
    TimePickerView localTimePickerView = this.timePickerView;
    if (bool) {
      localObject = MINUTE_CLOCK_VALUES;
    } else {
      localObject = getHourClockValues();
    }
    int i;
    if (bool) {
      i = R.string.material_minute_suffix;
    } else {
      i = R.string.material_hour_suffix;
    }
    localTimePickerView.setValues((String[])localObject, i);
    Object localObject = this.timePickerView;
    float f;
    if (bool) {
      f = this.minuteRotation;
    } else {
      f = this.hourRotation;
    }
    ((TimePickerView)localObject).setHandRotation(f, paramBoolean);
    this.timePickerView.setActiveSelection(paramInt);
    this.timePickerView.setMinuteHourDelegate(new ClickActionDelegate(this.timePickerView.getContext(), R.string.material_hour_selection)
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        paramAnonymousView = paramAnonymousView.getResources();
        int i = R.string.material_hour_suffix;
        String str = String.valueOf(TimePickerClockPresenter.this.time.getHourForDisplay());
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramAnonymousAccessibilityNodeInfoCompat.setContentDescription(paramAnonymousView.getString(i, new Object[] { str }));
      }
    });
    this.timePickerView.setHourClickDelegate(new ClickActionDelegate(this.timePickerView.getContext(), R.string.material_minute_selection)
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        paramAnonymousView = paramAnonymousView.getResources();
        int i = R.string.material_minute_suffix;
        String str = String.valueOf(TimePickerClockPresenter.this.time.minute);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramAnonymousAccessibilityNodeInfoCompat.setContentDescription(paramAnonymousView.getString(i, new Object[] { str }));
      }
    });
  }
  
  public void show()
  {
    this.timePickerView.setVisibility(0);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/TimePickerClockPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */