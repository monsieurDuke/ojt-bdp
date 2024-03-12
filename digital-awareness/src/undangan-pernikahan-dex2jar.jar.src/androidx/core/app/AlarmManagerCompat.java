package androidx.core.app;

import android.app.AlarmManager;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.PendingIntent;
import android.os.Build.VERSION;

public final class AlarmManagerCompat
{
  public static void setAlarmClock(AlarmManager paramAlarmManager, long paramLong, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setAlarmClock(paramAlarmManager, Api21Impl.createAlarmClockInfo(paramLong, paramPendingIntent1), paramPendingIntent2);
    } else {
      setExact(paramAlarmManager, 0, paramLong, paramPendingIntent2);
    }
  }
  
  public static void setAndAllowWhileIdle(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      Api23Impl.setAndAllowWhileIdle(paramAlarmManager, paramInt, paramLong, paramPendingIntent);
    } else {
      paramAlarmManager.set(paramInt, paramLong, paramPendingIntent);
    }
  }
  
  public static void setExact(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      Api19Impl.setExact(paramAlarmManager, paramInt, paramLong, paramPendingIntent);
    } else {
      paramAlarmManager.set(paramInt, paramLong, paramPendingIntent);
    }
  }
  
  public static void setExactAndAllowWhileIdle(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      Api23Impl.setExactAndAllowWhileIdle(paramAlarmManager, paramInt, paramLong, paramPendingIntent);
    } else {
      setExact(paramAlarmManager, paramInt, paramLong, paramPendingIntent);
    }
  }
  
  static class Api19Impl
  {
    static void setExact(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
    {
      paramAlarmManager.setExact(paramInt, paramLong, paramPendingIntent);
    }
  }
  
  static class Api21Impl
  {
    static AlarmManager.AlarmClockInfo createAlarmClockInfo(long paramLong, PendingIntent paramPendingIntent)
    {
      return new AlarmManager.AlarmClockInfo(paramLong, paramPendingIntent);
    }
    
    static void setAlarmClock(AlarmManager paramAlarmManager, Object paramObject, PendingIntent paramPendingIntent)
    {
      paramAlarmManager.setAlarmClock((AlarmManager.AlarmClockInfo)paramObject, paramPendingIntent);
    }
  }
  
  static class Api23Impl
  {
    static void setAndAllowWhileIdle(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
    {
      paramAlarmManager.setAndAllowWhileIdle(paramInt, paramLong, paramPendingIntent);
    }
    
    static void setExactAndAllowWhileIdle(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
    {
      paramAlarmManager.setExactAndAllowWhileIdle(paramInt, paramLong, paramPendingIntent);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/AlarmManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */