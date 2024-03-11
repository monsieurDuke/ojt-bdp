package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import com.google.android.material.R.attr;
import com.google.android.material.R.styleable;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;

final class CalendarStyle
{
  final CalendarItemStyle day;
  final CalendarItemStyle invalidDay;
  final Paint rangeFill;
  final CalendarItemStyle selectedDay;
  final CalendarItemStyle selectedYear;
  final CalendarItemStyle todayDay;
  final CalendarItemStyle todayYear;
  final CalendarItemStyle year;
  
  CalendarStyle(Context paramContext)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(paramContext, R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), R.styleable.MaterialCalendar);
    this.day = CalendarItemStyle.create(paramContext, localTypedArray.getResourceId(R.styleable.MaterialCalendar_dayStyle, 0));
    this.invalidDay = CalendarItemStyle.create(paramContext, localTypedArray.getResourceId(R.styleable.MaterialCalendar_dayInvalidStyle, 0));
    this.selectedDay = CalendarItemStyle.create(paramContext, localTypedArray.getResourceId(R.styleable.MaterialCalendar_daySelectedStyle, 0));
    this.todayDay = CalendarItemStyle.create(paramContext, localTypedArray.getResourceId(R.styleable.MaterialCalendar_dayTodayStyle, 0));
    ColorStateList localColorStateList = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.MaterialCalendar_rangeFillColor);
    this.year = CalendarItemStyle.create(paramContext, localTypedArray.getResourceId(R.styleable.MaterialCalendar_yearStyle, 0));
    this.selectedYear = CalendarItemStyle.create(paramContext, localTypedArray.getResourceId(R.styleable.MaterialCalendar_yearSelectedStyle, 0));
    this.todayYear = CalendarItemStyle.create(paramContext, localTypedArray.getResourceId(R.styleable.MaterialCalendar_yearTodayStyle, 0));
    paramContext = new Paint();
    this.rangeFill = paramContext;
    paramContext.setColor(localColorStateList.getDefaultColor());
    localTypedArray.recycle();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/CalendarStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */