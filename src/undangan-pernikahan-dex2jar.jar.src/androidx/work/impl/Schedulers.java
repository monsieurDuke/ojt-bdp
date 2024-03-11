package androidx.work.impl;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.impl.background.systemalarm.SystemAlarmScheduler;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.background.systemjob.SystemJobService;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.PackageManagerHelper;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Schedulers
{
  public static final String GCM_SCHEDULER = "androidx.work.impl.background.gcm.GcmScheduler";
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("Schedulers");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  static Scheduler createBestAvailableBackgroundScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramWorkManagerImpl = new SystemJobScheduler(paramContext, paramWorkManagerImpl);
      PackageManagerHelper.setComponentEnabled(paramContext, SystemJobService.class, true);
      Logger.get().debug(TAG, "Created SystemJobScheduler and enabled SystemJobService", new Throwable[0]);
    }
    else
    {
      Scheduler localScheduler = tryCreateGcmBasedScheduler(paramContext);
      paramWorkManagerImpl = localScheduler;
      if (localScheduler == null)
      {
        paramWorkManagerImpl = new SystemAlarmScheduler(paramContext);
        PackageManagerHelper.setComponentEnabled(paramContext, SystemAlarmService.class, true);
        Logger.get().debug(TAG, "Created SystemAlarmScheduler", new Throwable[0]);
      }
    }
    return paramWorkManagerImpl;
  }
  
  public static void schedule(Configuration paramConfiguration, WorkDatabase paramWorkDatabase, List<Scheduler> paramList)
  {
    if ((paramList != null) && (paramList.size() != 0))
    {
      Object localObject2 = paramWorkDatabase.workSpecDao();
      paramWorkDatabase.beginTransaction();
      try
      {
        Object localObject1 = ((WorkSpecDao)localObject2).getEligibleWorkForScheduling(paramConfiguration.getMaxSchedulerLimit());
        paramConfiguration = ((WorkSpecDao)localObject2).getAllEligibleWorkSpecsForScheduling(200);
        if ((localObject1 != null) && (((List)localObject1).size() > 0))
        {
          long l = System.currentTimeMillis();
          Iterator localIterator = ((List)localObject1).iterator();
          while (localIterator.hasNext()) {
            ((WorkSpecDao)localObject2).markWorkSpecScheduled(((WorkSpec)localIterator.next()).id, l);
          }
        }
        paramWorkDatabase.setTransactionSuccessful();
        paramWorkDatabase.endTransaction();
        if ((localObject1 != null) && (((List)localObject1).size() > 0))
        {
          paramWorkDatabase = new WorkSpec[((List)localObject1).size()];
          localObject1 = (WorkSpec[])((List)localObject1).toArray(paramWorkDatabase);
          localObject2 = paramList.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            paramWorkDatabase = (Scheduler)((Iterator)localObject2).next();
            if (paramWorkDatabase.hasLimitedSchedulingSlots()) {
              paramWorkDatabase.schedule((WorkSpec[])localObject1);
            }
          }
        }
        if ((paramConfiguration != null) && (paramConfiguration.size() > 0))
        {
          paramConfiguration = (WorkSpec[])paramConfiguration.toArray(new WorkSpec[paramConfiguration.size()]);
          paramList = paramList.iterator();
          while (paramList.hasNext())
          {
            paramWorkDatabase = (Scheduler)paramList.next();
            if (!paramWorkDatabase.hasLimitedSchedulingSlots()) {
              paramWorkDatabase.schedule(paramConfiguration);
            }
          }
        }
        return;
      }
      finally
      {
        paramWorkDatabase.endTransaction();
      }
    }
  }
  
  private static Scheduler tryCreateGcmBasedScheduler(Context paramContext)
  {
    try
    {
      Scheduler localScheduler = (Scheduler)Class.forName("androidx.work.impl.background.gcm.GcmScheduler").getConstructor(new Class[] { Context.class }).newInstance(new Object[] { paramContext });
      Logger localLogger = Logger.get();
      String str = TAG;
      paramContext = String.format("Created %s", new Object[] { "androidx.work.impl.background.gcm.GcmScheduler" });
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      localLogger.debug(str, paramContext, new Throwable[0]);
      return localScheduler;
    }
    finally
    {
      Logger.get().debug(TAG, "Unable to create GCM Scheduler", new Throwable[] { paramContext });
    }
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/Schedulers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */