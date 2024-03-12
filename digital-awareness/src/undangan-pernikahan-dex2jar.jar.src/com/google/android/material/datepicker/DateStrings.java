package com.google.android.material.datepicker;

import android.os.Build.VERSION;
import android.text.format.DateUtils;
import androidx.core.util.Pair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class DateStrings
{
  static Pair<String, String> getDateRangeString(Long paramLong1, Long paramLong2)
  {
    return getDateRangeString(paramLong1, paramLong2, null);
  }
  
  static Pair<String, String> getDateRangeString(Long paramLong1, Long paramLong2, SimpleDateFormat paramSimpleDateFormat)
  {
    if ((paramLong1 == null) && (paramLong2 == null)) {
      return Pair.create(null, null);
    }
    if (paramLong1 == null)
    {
      paramLong1 = getDateString(paramLong2.longValue(), paramSimpleDateFormat);
      Log5ECF72.a(paramLong1);
      LogE84000.a(paramLong1);
      Log229316.a(paramLong1);
      return Pair.create(null, paramLong1);
    }
    if (paramLong2 == null)
    {
      paramLong1 = getDateString(paramLong1.longValue(), paramSimpleDateFormat);
      Log5ECF72.a(paramLong1);
      LogE84000.a(paramLong1);
      Log229316.a(paramLong1);
      return Pair.create(paramLong1, null);
    }
    Calendar localCalendar1 = UtcDates.getTodayCalendar();
    Calendar localCalendar3 = UtcDates.getUtcCalendar();
    localCalendar3.setTimeInMillis(paramLong1.longValue());
    Calendar localCalendar2 = UtcDates.getUtcCalendar();
    localCalendar2.setTimeInMillis(paramLong2.longValue());
    if (paramSimpleDateFormat != null)
    {
      paramLong1 = new Date(paramLong1.longValue());
      paramLong2 = new Date(paramLong2.longValue());
      return Pair.create(paramSimpleDateFormat.format(paramLong1), paramSimpleDateFormat.format(paramLong2));
    }
    if (localCalendar3.get(1) == localCalendar2.get(1))
    {
      if (localCalendar3.get(1) == localCalendar1.get(1))
      {
        paramLong1 = getMonthDay(paramLong1.longValue(), Locale.getDefault());
        Log5ECF72.a(paramLong1);
        LogE84000.a(paramLong1);
        Log229316.a(paramLong1);
        paramLong2 = getMonthDay(paramLong2.longValue(), Locale.getDefault());
        Log5ECF72.a(paramLong2);
        LogE84000.a(paramLong2);
        Log229316.a(paramLong2);
        return Pair.create(paramLong1, paramLong2);
      }
      paramLong1 = getMonthDay(paramLong1.longValue(), Locale.getDefault());
      Log5ECF72.a(paramLong1);
      LogE84000.a(paramLong1);
      Log229316.a(paramLong1);
      paramLong2 = getYearMonthDay(paramLong2.longValue(), Locale.getDefault());
      Log5ECF72.a(paramLong2);
      LogE84000.a(paramLong2);
      Log229316.a(paramLong2);
      return Pair.create(paramLong1, paramLong2);
    }
    paramLong1 = getYearMonthDay(paramLong1.longValue(), Locale.getDefault());
    Log5ECF72.a(paramLong1);
    LogE84000.a(paramLong1);
    Log229316.a(paramLong1);
    paramLong2 = getYearMonthDay(paramLong2.longValue(), Locale.getDefault());
    Log5ECF72.a(paramLong2);
    LogE84000.a(paramLong2);
    Log229316.a(paramLong2);
    return Pair.create(paramLong1, paramLong2);
  }
  
  static String getDateString(long paramLong)
  {
    String str = getDateString(paramLong, null);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  static String getDateString(long paramLong, SimpleDateFormat paramSimpleDateFormat)
  {
    Calendar localCalendar1 = UtcDates.getTodayCalendar();
    Calendar localCalendar2 = UtcDates.getUtcCalendar();
    localCalendar2.setTimeInMillis(paramLong);
    if (paramSimpleDateFormat != null) {
      return paramSimpleDateFormat.format(new Date(paramLong));
    }
    if (localCalendar1.get(1) == localCalendar2.get(1))
    {
      paramSimpleDateFormat = getMonthDay(paramLong);
      Log5ECF72.a(paramSimpleDateFormat);
      LogE84000.a(paramSimpleDateFormat);
      Log229316.a(paramSimpleDateFormat);
      return paramSimpleDateFormat;
    }
    paramSimpleDateFormat = getYearMonthDay(paramLong);
    Log5ECF72.a(paramSimpleDateFormat);
    LogE84000.a(paramSimpleDateFormat);
    Log229316.a(paramSimpleDateFormat);
    return paramSimpleDateFormat;
  }
  
  static String getMonthDay(long paramLong)
  {
    String str = getMonthDay(paramLong, Locale.getDefault());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  static String getMonthDay(long paramLong, Locale paramLocale)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return UtcDates.getAbbrMonthDayFormat(paramLocale).format(new Date(paramLong));
    }
    return UtcDates.getMediumNoYear(paramLocale).format(new Date(paramLong));
  }
  
  static String getMonthDayOfWeekDay(long paramLong)
  {
    String str = getMonthDayOfWeekDay(paramLong, Locale.getDefault());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  static String getMonthDayOfWeekDay(long paramLong, Locale paramLocale)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return UtcDates.getAbbrMonthWeekdayDayFormat(paramLocale).format(new Date(paramLong));
    }
    return UtcDates.getFullFormat(paramLocale).format(new Date(paramLong));
  }
  
  static String getYearMonth(long paramLong)
  {
    String str = DateUtils.formatDateTime(null, paramLong, 8228);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  static String getYearMonthDay(long paramLong)
  {
    String str = getYearMonthDay(paramLong, Locale.getDefault());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  static String getYearMonthDay(long paramLong, Locale paramLocale)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return UtcDates.getYearAbbrMonthDayFormat(paramLocale).format(new Date(paramLong));
    }
    return UtcDates.getMediumFormat(paramLocale).format(new Date(paramLong));
  }
  
  static String getYearMonthDayOfWeekDay(long paramLong)
  {
    String str = getYearMonthDayOfWeekDay(paramLong, Locale.getDefault());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  static String getYearMonthDayOfWeekDay(long paramLong, Locale paramLocale)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return UtcDates.getYearAbbrMonthWeekdayDayFormat(paramLocale).format(new Date(paramLong));
    }
    return UtcDates.getFullFormat(paramLocale).format(new Date(paramLong));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/DateStrings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */