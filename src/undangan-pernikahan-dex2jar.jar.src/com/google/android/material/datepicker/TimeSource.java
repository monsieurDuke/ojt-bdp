package com.google.android.material.datepicker;

import java.util.Calendar;
import java.util.TimeZone;

class TimeSource
{
  private static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource(null, null);
  private final Long fixedTimeMs;
  private final TimeZone fixedTimeZone;
  
  private TimeSource(Long paramLong, TimeZone paramTimeZone)
  {
    this.fixedTimeMs = paramLong;
    this.fixedTimeZone = paramTimeZone;
  }
  
  static TimeSource fixed(long paramLong)
  {
    return new TimeSource(Long.valueOf(paramLong), null);
  }
  
  static TimeSource fixed(long paramLong, TimeZone paramTimeZone)
  {
    return new TimeSource(Long.valueOf(paramLong), paramTimeZone);
  }
  
  static TimeSource system()
  {
    return SYSTEM_TIME_SOURCE;
  }
  
  Calendar now()
  {
    return now(this.fixedTimeZone);
  }
  
  Calendar now(TimeZone paramTimeZone)
  {
    if (paramTimeZone == null) {
      paramTimeZone = Calendar.getInstance();
    } else {
      paramTimeZone = Calendar.getInstance(paramTimeZone);
    }
    Long localLong = this.fixedTimeMs;
    if (localLong != null) {
      paramTimeZone.setTimeInMillis(localLong.longValue());
    }
    return paramTimeZone;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/TimeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */