package com.google.android.material.datepicker;

import android.content.res.Resources;
import com.google.android.material.R.string;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class UtcDates
{
  static final String UTC = "UTC";
  static AtomicReference<TimeSource> timeSourceRef = new AtomicReference();
  
  static long canonicalYearMonthDay(long paramLong)
  {
    Calendar localCalendar = getUtcCalendar();
    localCalendar.setTimeInMillis(paramLong);
    return getDayCopy(localCalendar).getTimeInMillis();
  }
  
  private static int findCharactersInDateFormatPattern(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    while ((paramInt2 >= 0) && (paramInt2 < paramString1.length()) && (paramString2.indexOf(paramString1.charAt(paramInt2)) == -1))
    {
      int i = paramInt2;
      if (paramString1.charAt(paramInt2) == '\'')
      {
        paramInt2 += paramInt1;
        for (;;)
        {
          i = paramInt2;
          if (paramInt2 < 0) {
            break;
          }
          i = paramInt2;
          if (paramInt2 >= paramString1.length()) {
            break;
          }
          i = paramInt2;
          if (paramString1.charAt(paramInt2) == '\'') {
            break;
          }
          paramInt2 += paramInt1;
        }
      }
      paramInt2 = i + paramInt1;
    }
    return paramInt2;
  }
  
  static android.icu.text.DateFormat getAbbrMonthDayFormat(Locale paramLocale)
  {
    return getAndroidFormat("MMMd", paramLocale);
  }
  
  static android.icu.text.DateFormat getAbbrMonthWeekdayDayFormat(Locale paramLocale)
  {
    return getAndroidFormat("MMMEd", paramLocale);
  }
  
  private static android.icu.text.DateFormat getAndroidFormat(String paramString, Locale paramLocale)
  {
    paramString = android.icu.text.DateFormat.getInstanceForSkeleton(paramString, paramLocale);
    paramString.setTimeZone(getUtcAndroidTimeZone());
    return paramString;
  }
  
  static Calendar getDayCopy(Calendar paramCalendar)
  {
    paramCalendar = getUtcCalendarOf(paramCalendar);
    Calendar localCalendar = getUtcCalendar();
    localCalendar.set(paramCalendar.get(1), paramCalendar.get(2), paramCalendar.get(5));
    return localCalendar;
  }
  
  private static java.text.DateFormat getFormat(int paramInt, Locale paramLocale)
  {
    paramLocale = java.text.DateFormat.getDateInstance(paramInt, paramLocale);
    paramLocale.setTimeZone(getTimeZone());
    return paramLocale;
  }
  
  static java.text.DateFormat getFullFormat()
  {
    return getFullFormat(Locale.getDefault());
  }
  
  static java.text.DateFormat getFullFormat(Locale paramLocale)
  {
    return getFormat(0, paramLocale);
  }
  
  static java.text.DateFormat getMediumFormat()
  {
    return getMediumFormat(Locale.getDefault());
  }
  
  static java.text.DateFormat getMediumFormat(Locale paramLocale)
  {
    return getFormat(2, paramLocale);
  }
  
  static java.text.DateFormat getMediumNoYear()
  {
    return getMediumNoYear(Locale.getDefault());
  }
  
  static java.text.DateFormat getMediumNoYear(Locale paramLocale)
  {
    paramLocale = (SimpleDateFormat)getMediumFormat(paramLocale);
    String str = removeYearFromDateFormatPattern(paramLocale.toPattern());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    paramLocale.applyPattern(str);
    return paramLocale;
  }
  
  static SimpleDateFormat getSimpleFormat(String paramString)
  {
    return getSimpleFormat(paramString, Locale.getDefault());
  }
  
  private static SimpleDateFormat getSimpleFormat(String paramString, Locale paramLocale)
  {
    paramString = new SimpleDateFormat(paramString, paramLocale);
    paramString.setTimeZone(getTimeZone());
    return paramString;
  }
  
  static SimpleDateFormat getTextInputFormat()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(((SimpleDateFormat)java.text.DateFormat.getDateInstance(3, Locale.getDefault())).toPattern().replaceAll("\\s+", ""), Locale.getDefault());
    localSimpleDateFormat.setTimeZone(getTimeZone());
    localSimpleDateFormat.setLenient(false);
    return localSimpleDateFormat;
  }
  
  static String getTextInputHint(Resources paramResources, SimpleDateFormat paramSimpleDateFormat)
  {
    paramSimpleDateFormat = paramSimpleDateFormat.toPattern();
    String str1 = paramResources.getString(R.string.mtrl_picker_text_input_year_abbr);
    String str2 = paramResources.getString(R.string.mtrl_picker_text_input_month_abbr);
    String str3 = paramResources.getString(R.string.mtrl_picker_text_input_day_abbr);
    paramResources = paramSimpleDateFormat;
    if (paramSimpleDateFormat.replaceAll("[^y]", "").length() == 1) {
      paramResources = paramSimpleDateFormat.replace("y", "yyyy");
    }
    return paramResources.replace("d", str3).replace("M", str2).replace("y", str1);
  }
  
  static TimeSource getTimeSource()
  {
    TimeSource localTimeSource = (TimeSource)timeSourceRef.get();
    if (localTimeSource == null) {
      localTimeSource = TimeSource.system();
    }
    return localTimeSource;
  }
  
  private static java.util.TimeZone getTimeZone()
  {
    return java.util.TimeZone.getTimeZone("UTC");
  }
  
  static Calendar getTodayCalendar()
  {
    Calendar localCalendar = getTimeSource().now();
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    localCalendar.setTimeZone(getTimeZone());
    return localCalendar;
  }
  
  private static android.icu.util.TimeZone getUtcAndroidTimeZone()
  {
    return android.icu.util.TimeZone.getTimeZone("UTC");
  }
  
  static Calendar getUtcCalendar()
  {
    return getUtcCalendarOf(null);
  }
  
  static Calendar getUtcCalendarOf(Calendar paramCalendar)
  {
    Calendar localCalendar = Calendar.getInstance(getTimeZone());
    if (paramCalendar == null) {
      localCalendar.clear();
    } else {
      localCalendar.setTimeInMillis(paramCalendar.getTimeInMillis());
    }
    return localCalendar;
  }
  
  static android.icu.text.DateFormat getYearAbbrMonthDayFormat(Locale paramLocale)
  {
    return getAndroidFormat("yMMMd", paramLocale);
  }
  
  static android.icu.text.DateFormat getYearAbbrMonthWeekdayDayFormat(Locale paramLocale)
  {
    return getAndroidFormat("yMMMEd", paramLocale);
  }
  
  private static String removeYearFromDateFormatPattern(String paramString)
  {
    int i = findCharactersInDateFormatPattern(paramString, "yY", 1, 0);
    if (i >= paramString.length()) {
      return paramString;
    }
    String str = "EMd";
    int j = findCharactersInDateFormatPattern(paramString, "EMd", 1, i);
    if (j < paramString.length()) {
      str = "EMd" + ",";
    }
    return paramString.replace(paramString.substring(findCharactersInDateFormatPattern(paramString, str, -1, i) + 1, j), " ").trim();
  }
  
  static void setTimeSource(TimeSource paramTimeSource)
  {
    timeSourceRef.set(paramTimeSource);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/UtcDates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */