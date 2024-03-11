package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import java.util.Calendar;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class DaysOfWeekAdapter
  extends BaseAdapter
{
  private static final int CALENDAR_DAY_STYLE;
  private static final int NARROW_FORMAT = 4;
  private final Calendar calendar;
  private final int daysInWeek;
  private final int firstDayOfWeek;
  
  static
  {
    int i;
    if (Build.VERSION.SDK_INT >= 26) {
      i = 4;
    } else {
      i = 1;
    }
    CALENDAR_DAY_STYLE = i;
  }
  
  public DaysOfWeekAdapter()
  {
    Calendar localCalendar = UtcDates.getUtcCalendar();
    this.calendar = localCalendar;
    this.daysInWeek = localCalendar.getMaximum(7);
    this.firstDayOfWeek = localCalendar.getFirstDayOfWeek();
  }
  
  private int positionToDayOfWeek(int paramInt)
  {
    int i = this.firstDayOfWeek + paramInt;
    int j = this.daysInWeek;
    paramInt = i;
    if (i > j) {
      paramInt = i - j;
    }
    return paramInt;
  }
  
  public int getCount()
  {
    return this.daysInWeek;
  }
  
  public Integer getItem(int paramInt)
  {
    if (paramInt >= this.daysInWeek) {
      return null;
    }
    return Integer.valueOf(positionToDayOfWeek(paramInt));
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    TextView localTextView = (TextView)paramView;
    if (paramView == null) {
      localTextView = (TextView)LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.mtrl_calendar_day_of_week, paramViewGroup, false);
    }
    this.calendar.set(7, positionToDayOfWeek(paramInt));
    paramView = localTextView.getResources().getConfiguration().locale;
    localTextView.setText(this.calendar.getDisplayName(7, CALENDAR_DAY_STYLE, paramView));
    paramView = String.format(paramViewGroup.getContext().getString(R.string.mtrl_picker_day_of_week_column_header), new Object[] { this.calendar.getDisplayName(7, 2, Locale.getDefault()) });
    Log5ECF72.a(paramView);
    LogE84000.a(paramView);
    Log229316.a(paramView);
    localTextView.setContentDescription(paramView);
    return localTextView;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/DaysOfWeekAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */