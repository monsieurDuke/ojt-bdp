package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.material.R.layout;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class MonthAdapter
  extends BaseAdapter
{
  static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendar().getMaximum(4);
  final CalendarConstraints calendarConstraints;
  CalendarStyle calendarStyle;
  final DateSelector<?> dateSelector;
  final Month month;
  private Collection<Long> previouslySelectedDates;
  
  MonthAdapter(Month paramMonth, DateSelector<?> paramDateSelector, CalendarConstraints paramCalendarConstraints)
  {
    this.month = paramMonth;
    this.dateSelector = paramDateSelector;
    this.calendarConstraints = paramCalendarConstraints;
    this.previouslySelectedDates = paramDateSelector.getSelectedDays();
  }
  
  private void initializeStyles(Context paramContext)
  {
    if (this.calendarStyle == null) {
      this.calendarStyle = new CalendarStyle(paramContext);
    }
  }
  
  private boolean isSelected(long paramLong)
  {
    Iterator localIterator = this.dateSelector.getSelectedDays().iterator();
    while (localIterator.hasNext())
    {
      long l = ((Long)localIterator.next()).longValue();
      if (UtcDates.canonicalYearMonthDay(paramLong) == UtcDates.canonicalYearMonthDay(l)) {
        return true;
      }
    }
    return false;
  }
  
  private void updateSelectedState(TextView paramTextView, long paramLong)
  {
    if (paramTextView == null) {
      return;
    }
    CalendarItemStyle localCalendarItemStyle;
    if (this.calendarConstraints.getDateValidator().isValid(paramLong))
    {
      paramTextView.setEnabled(true);
      if (isSelected(paramLong)) {
        localCalendarItemStyle = this.calendarStyle.selectedDay;
      } else if (UtcDates.getTodayCalendar().getTimeInMillis() == paramLong) {
        localCalendarItemStyle = this.calendarStyle.todayDay;
      } else {
        localCalendarItemStyle = this.calendarStyle.day;
      }
    }
    else
    {
      paramTextView.setEnabled(false);
      localCalendarItemStyle = this.calendarStyle.invalidDay;
    }
    localCalendarItemStyle.styleItem(paramTextView);
  }
  
  private void updateSelectedStateForDate(MaterialCalendarGridView paramMaterialCalendarGridView, long paramLong)
  {
    if (Month.create(paramLong).equals(this.month))
    {
      int i = this.month.getDayOfMonth(paramLong);
      updateSelectedState((TextView)paramMaterialCalendarGridView.getChildAt(paramMaterialCalendarGridView.getAdapter().dayToPosition(i) - paramMaterialCalendarGridView.getFirstVisiblePosition()), paramLong);
    }
  }
  
  int dayToPosition(int paramInt)
  {
    return firstPositionInMonth() + (paramInt - 1);
  }
  
  int firstPositionInMonth()
  {
    return this.month.daysFromStartOfWeekToFirstOfMonth();
  }
  
  public int getCount()
  {
    return this.month.daysInMonth + firstPositionInMonth();
  }
  
  public Long getItem(int paramInt)
  {
    if ((paramInt >= this.month.daysFromStartOfWeekToFirstOfMonth()) && (paramInt <= lastPositionInMonth())) {
      return Long.valueOf(this.month.getDay(positionToDay(paramInt)));
    }
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt / this.month.daysInWeek;
  }
  
  public TextView getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    initializeStyles(paramViewGroup.getContext());
    TextView localTextView = (TextView)paramView;
    if (paramView == null) {
      localTextView = (TextView)LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.mtrl_calendar_day, paramViewGroup, false);
    }
    int i = paramInt - firstPositionInMonth();
    if ((i >= 0) && (i < this.month.daysInMonth))
    {
      i++;
      localTextView.setTag(this.month);
      paramView = String.format(localTextView.getResources().getConfiguration().locale, "%d", new Object[] { Integer.valueOf(i) });
      Log5ECF72.a(paramView);
      LogE84000.a(paramView);
      Log229316.a(paramView);
      localTextView.setText(paramView);
      long l = this.month.getDay(i);
      if (this.month.year == Month.current().year)
      {
        paramView = DateStrings.getMonthDayOfWeekDay(l);
        Log5ECF72.a(paramView);
        LogE84000.a(paramView);
        Log229316.a(paramView);
        localTextView.setContentDescription(paramView);
      }
      else
      {
        paramView = DateStrings.getYearMonthDayOfWeekDay(l);
        Log5ECF72.a(paramView);
        LogE84000.a(paramView);
        Log229316.a(paramView);
        localTextView.setContentDescription(paramView);
      }
      localTextView.setVisibility(0);
      localTextView.setEnabled(true);
    }
    else
    {
      localTextView.setVisibility(8);
      localTextView.setEnabled(false);
    }
    paramView = getItem(paramInt);
    if (paramView == null) {
      return localTextView;
    }
    updateSelectedState(localTextView, paramView.longValue());
    return localTextView;
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  boolean isFirstInRow(int paramInt)
  {
    boolean bool;
    if (paramInt % this.month.daysInWeek == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isLastInRow(int paramInt)
  {
    boolean bool;
    if ((paramInt + 1) % this.month.daysInWeek == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  int lastPositionInMonth()
  {
    return this.month.daysFromStartOfWeekToFirstOfMonth() + this.month.daysInMonth - 1;
  }
  
  int positionToDay(int paramInt)
  {
    return paramInt - this.month.daysFromStartOfWeekToFirstOfMonth() + 1;
  }
  
  public void updateSelectedStates(MaterialCalendarGridView paramMaterialCalendarGridView)
  {
    Object localObject = this.previouslySelectedDates.iterator();
    while (((Iterator)localObject).hasNext()) {
      updateSelectedStateForDate(paramMaterialCalendarGridView, ((Long)((Iterator)localObject).next()).longValue());
    }
    localObject = this.dateSelector;
    if (localObject != null)
    {
      localObject = ((DateSelector)localObject).getSelectedDays().iterator();
      while (((Iterator)localObject).hasNext()) {
        updateSelectedStateForDate(paramMaterialCalendarGridView, ((Long)((Iterator)localObject).next()).longValue());
      }
      this.previouslySelectedDates = this.dateSelector.getSelectedDays();
    }
  }
  
  boolean withinMonth(int paramInt)
  {
    boolean bool;
    if ((paramInt >= firstPositionInMonth()) && (paramInt <= lastPositionInMonth())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/MonthAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */