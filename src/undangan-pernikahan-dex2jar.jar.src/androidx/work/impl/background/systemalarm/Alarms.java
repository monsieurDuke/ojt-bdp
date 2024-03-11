package androidx.work.impl.background.systemalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.work.Logger;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.utils.IdGenerator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class Alarms
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("Alarms");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static void cancelAlarm(Context paramContext, WorkManagerImpl paramWorkManagerImpl, String paramString)
  {
    paramWorkManagerImpl = paramWorkManagerImpl.getWorkDatabase().systemIdInfoDao();
    Object localObject = paramWorkManagerImpl.getSystemIdInfo(paramString);
    if (localObject != null)
    {
      cancelExactAlarm(paramContext, paramString, ((SystemIdInfo)localObject).systemId);
      localObject = Logger.get();
      paramContext = TAG;
      String str = String.format("Removing SystemIdInfo for workSpecId (%s)", new Object[] { paramString });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      ((Logger)localObject).debug(paramContext, str, new Throwable[0]);
      paramWorkManagerImpl.removeSystemIdInfo(paramString);
    }
  }
  
  private static void cancelExactAlarm(Context paramContext, String paramString, int paramInt)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    Object localObject = CommandHandler.createDelayMetIntent(paramContext, paramString);
    int i = 536870912;
    if (Build.VERSION.SDK_INT >= 23) {
      i = 0x20000000 | 0x4000000;
    }
    PendingIntent localPendingIntent = PendingIntent.getService(paramContext, paramInt, (Intent)localObject, i);
    if ((localPendingIntent != null) && (localAlarmManager != null))
    {
      localObject = Logger.get();
      paramContext = TAG;
      paramString = String.format("Cancelling existing alarm with (workSpecId, systemId) (%s, %s)", new Object[] { paramString, Integer.valueOf(paramInt) });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      ((Logger)localObject).debug(paramContext, paramString, new Throwable[0]);
      localAlarmManager.cancel(localPendingIntent);
    }
  }
  
  public static void setAlarm(Context paramContext, WorkManagerImpl paramWorkManagerImpl, String paramString, long paramLong)
  {
    WorkDatabase localWorkDatabase = paramWorkManagerImpl.getWorkDatabase();
    paramWorkManagerImpl = localWorkDatabase.systemIdInfoDao();
    SystemIdInfo localSystemIdInfo = paramWorkManagerImpl.getSystemIdInfo(paramString);
    if (localSystemIdInfo != null)
    {
      cancelExactAlarm(paramContext, paramString, localSystemIdInfo.systemId);
      setExactAlarm(paramContext, paramString, localSystemIdInfo.systemId, paramLong);
    }
    else
    {
      int i = new IdGenerator(localWorkDatabase).nextAlarmManagerId();
      paramWorkManagerImpl.insertSystemIdInfo(new SystemIdInfo(paramString, i));
      setExactAlarm(paramContext, paramString, i, paramLong);
    }
  }
  
  private static void setExactAlarm(Context paramContext, String paramString, int paramInt, long paramLong)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    int i = 134217728;
    if (Build.VERSION.SDK_INT >= 23) {
      i = 0x8000000 | 0x4000000;
    }
    paramContext = PendingIntent.getService(paramContext, paramInt, CommandHandler.createDelayMetIntent(paramContext, paramString), i);
    if (localAlarmManager != null) {
      if (Build.VERSION.SDK_INT >= 19) {
        localAlarmManager.setExact(0, paramLong, paramContext);
      } else {
        localAlarmManager.set(0, paramLong, paramContext);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/Alarms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */