package androidx.work.impl.utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.ApplicationExitInfo;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.core.os.BuildCompat;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ForceStopRunnable
  implements Runnable
{
  static final String ACTION_FORCE_STOP_RESCHEDULE = "ACTION_FORCE_STOP_RESCHEDULE";
  private static final int ALARM_ID = -1;
  private static final long BACKOFF_DURATION_MS = 300L;
  static final int MAX_ATTEMPTS = 3;
  private static final String TAG;
  private static final long TEN_YEARS = TimeUnit.DAYS.toMillis(3650L);
  private final Context mContext;
  private int mRetryCount;
  private final WorkManagerImpl mWorkManager;
  
  static
  {
    String str = Logger.tagWithPrefix("ForceStopRunnable");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public ForceStopRunnable(Context paramContext, WorkManagerImpl paramWorkManagerImpl)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mWorkManager = paramWorkManagerImpl;
    this.mRetryCount = 0;
  }
  
  static Intent getIntent(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setComponent(new ComponentName(paramContext, BroadcastReceiver.class));
    localIntent.setAction("ACTION_FORCE_STOP_RESCHEDULE");
    return localIntent;
  }
  
  private static PendingIntent getPendingIntent(Context paramContext, int paramInt)
  {
    return PendingIntent.getBroadcast(paramContext, -1, getIntent(paramContext), paramInt);
  }
  
  static void setAlarm(Context paramContext)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    int i = 134217728;
    if (BuildCompat.isAtLeastS()) {
      i = 0x8000000 | 0x2000000;
    }
    paramContext = getPendingIntent(paramContext, i);
    long l = System.currentTimeMillis() + TEN_YEARS;
    if (localAlarmManager != null) {
      if (Build.VERSION.SDK_INT >= 19) {
        localAlarmManager.setExact(0, l, paramContext);
      } else {
        localAlarmManager.set(0, l, paramContext);
      }
    }
  }
  
  public boolean cleanUp()
  {
    boolean bool1 = false;
    if (Build.VERSION.SDK_INT >= 23) {
      bool1 = SystemJobScheduler.reconcileJobs(this.mContext, this.mWorkManager);
    }
    WorkDatabase localWorkDatabase = this.mWorkManager.getWorkDatabase();
    WorkSpecDao localWorkSpecDao = localWorkDatabase.workSpecDao();
    WorkProgressDao localWorkProgressDao = localWorkDatabase.workProgressDao();
    localWorkDatabase.beginTransaction();
    try
    {
      Object localObject2 = localWorkSpecDao.getRunningWork();
      boolean bool3 = true;
      int i;
      if ((localObject2 != null) && (!((List)localObject2).isEmpty())) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        Iterator localIterator = ((List)localObject2).iterator();
        while (localIterator.hasNext())
        {
          localObject2 = (WorkSpec)localIterator.next();
          localWorkSpecDao.setState(WorkInfo.State.ENQUEUED, new String[] { ((WorkSpec)localObject2).id });
          localWorkSpecDao.markWorkSpecScheduled(((WorkSpec)localObject2).id, -1L);
        }
      }
      localWorkProgressDao.deleteAll();
      localWorkDatabase.setTransactionSuccessful();
      localWorkDatabase.endTransaction();
      boolean bool2 = bool3;
      if (i == 0) {
        if (bool1) {
          bool2 = bool3;
        } else {
          bool2 = false;
        }
      }
      return bool2;
    }
    finally
    {
      localWorkDatabase.endTransaction();
    }
  }
  
  public void forceStopRunnable()
  {
    boolean bool = cleanUp();
    if (shouldRescheduleWorkers())
    {
      Logger.get().debug(TAG, "Rescheduling Workers.", new Throwable[0]);
      this.mWorkManager.rescheduleEligibleWork();
      this.mWorkManager.getPreferenceUtils().setNeedsReschedule(false);
    }
    else if (isForceStopped())
    {
      Logger.get().debug(TAG, "Application was force-stopped, rescheduling.", new Throwable[0]);
      this.mWorkManager.rescheduleEligibleWork();
    }
    else if (bool)
    {
      Logger.get().debug(TAG, "Found unfinished work, scheduling it.", new Throwable[0]);
      Schedulers.schedule(this.mWorkManager.getConfiguration(), this.mWorkManager.getWorkDatabase(), this.mWorkManager.getSchedulers());
    }
  }
  
  public boolean isForceStopped()
  {
    int i = 536870912;
    try
    {
      if (BuildCompat.isAtLeastS()) {
        i = 0x20000000 | 0x2000000;
      }
      Object localObject = getPendingIntent(this.mContext, i);
      if (Build.VERSION.SDK_INT >= 30)
      {
        if (localObject != null) {
          ((PendingIntent)localObject).cancel();
        }
        localObject = ((ActivityManager)this.mContext.getSystemService("activity")).getHistoricalProcessExitReasons(null, 0, 0);
        if ((localObject != null) && (!((List)localObject).isEmpty())) {
          for (i = 0; i < ((List)localObject).size(); i++) {
            if (((ApplicationExitInfo)((List)localObject).get(i)).getReason() == 10) {
              return true;
            }
          }
        }
      }
      else if (localObject == null)
      {
        setAlarm(this.mContext);
        return true;
      }
      return false;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}catch (SecurityException localSecurityException) {}
    Logger.get().warning(TAG, "Ignoring exception", new Throwable[] { localSecurityException });
    return true;
  }
  
  public boolean multiProcessChecks()
  {
    Object localObject = this.mWorkManager.getConfiguration();
    if (TextUtils.isEmpty(((Configuration)localObject).getDefaultProcessName()))
    {
      Logger.get().debug(TAG, "The default process name was not specified.", new Throwable[0]);
      return true;
    }
    boolean bool = ProcessUtils.isDefaultProcess(this.mContext, (Configuration)localObject);
    Logger localLogger = Logger.get();
    localObject = TAG;
    String str = String.format("Is default app process = %s", new Object[] { Boolean.valueOf(bool) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localLogger.debug((String)localObject, str, new Throwable[0]);
    return bool;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 366	androidx/work/impl/utils/ForceStopRunnable:multiProcessChecks	()Z
    //   4: istore_2
    //   5: iload_2
    //   6: ifne +11 -> 17
    //   9: aload_0
    //   10: getfield 81	androidx/work/impl/utils/ForceStopRunnable:mWorkManager	Landroidx/work/impl/WorkManagerImpl;
    //   13: invokevirtual 369	androidx/work/impl/WorkManagerImpl:onForceStopRunnableCompleted	()V
    //   16: return
    //   17: aload_0
    //   18: getfield 79	androidx/work/impl/utils/ForceStopRunnable:mContext	Landroid/content/Context;
    //   21: invokestatic 374	androidx/work/impl/WorkDatabasePathHelper:migrateDatabase	(Landroid/content/Context;)V
    //   24: invokestatic 243	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   27: getstatic 52	androidx/work/impl/utils/ForceStopRunnable:TAG	Ljava/lang/String;
    //   30: ldc_w 376
    //   33: iconst_0
    //   34: anewarray 247	java/lang/Throwable
    //   37: invokevirtual 251	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   40: aload_0
    //   41: invokevirtual 378	androidx/work/impl/utils/ForceStopRunnable:forceStopRunnable	()V
    //   44: goto +137 -> 181
    //   47: astore 5
    //   49: goto +25 -> 74
    //   52: astore 5
    //   54: goto +20 -> 74
    //   57: astore 5
    //   59: goto +15 -> 74
    //   62: astore 5
    //   64: goto +10 -> 74
    //   67: astore 5
    //   69: goto +5 -> 74
    //   72: astore 5
    //   74: aload_0
    //   75: getfield 83	androidx/work/impl/utils/ForceStopRunnable:mRetryCount	I
    //   78: iconst_1
    //   79: iadd
    //   80: istore_1
    //   81: aload_0
    //   82: iload_1
    //   83: putfield 83	androidx/work/impl/utils/ForceStopRunnable:mRetryCount	I
    //   86: iload_1
    //   87: iconst_3
    //   88: if_icmplt +104 -> 192
    //   91: invokestatic 243	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   94: astore 7
    //   96: getstatic 52	androidx/work/impl/utils/ForceStopRunnable:TAG	Ljava/lang/String;
    //   99: astore 6
    //   101: aload 7
    //   103: aload 6
    //   105: ldc_w 380
    //   108: iconst_1
    //   109: anewarray 247	java/lang/Throwable
    //   112: dup
    //   113: iconst_0
    //   114: aload 5
    //   116: aastore
    //   117: invokevirtual 383	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   120: new 385	java/lang/IllegalStateException
    //   123: astore 7
    //   125: aload 7
    //   127: ldc_w 380
    //   130: aload 5
    //   132: invokespecial 388	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   135: aload_0
    //   136: getfield 81	androidx/work/impl/utils/ForceStopRunnable:mWorkManager	Landroidx/work/impl/WorkManagerImpl;
    //   139: invokevirtual 275	androidx/work/impl/WorkManagerImpl:getConfiguration	()Landroidx/work/Configuration;
    //   142: invokevirtual 392	androidx/work/Configuration:getExceptionHandler	()Landroidx/work/InitializationExceptionHandler;
    //   145: astore 5
    //   147: aload 5
    //   149: ifnull +40 -> 189
    //   152: invokestatic 243	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   155: aload 6
    //   157: ldc_w 394
    //   160: iconst_1
    //   161: anewarray 247	java/lang/Throwable
    //   164: dup
    //   165: iconst_0
    //   166: aload 7
    //   168: aastore
    //   169: invokevirtual 251	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   172: aload 5
    //   174: aload 7
    //   176: invokeinterface 400 2 0
    //   181: aload_0
    //   182: getfield 81	androidx/work/impl/utils/ForceStopRunnable:mWorkManager	Landroidx/work/impl/WorkManagerImpl;
    //   185: invokevirtual 369	androidx/work/impl/WorkManagerImpl:onForceStopRunnableCompleted	()V
    //   188: return
    //   189: aload 7
    //   191: athrow
    //   192: iload_1
    //   193: i2l
    //   194: lstore_3
    //   195: invokestatic 243	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   198: astore 6
    //   200: getstatic 52	androidx/work/impl/utils/ForceStopRunnable:TAG	Ljava/lang/String;
    //   203: astore 8
    //   205: ldc_w 402
    //   208: iconst_1
    //   209: anewarray 4	java/lang/Object
    //   212: dup
    //   213: iconst_0
    //   214: lload_3
    //   215: ldc2_w 18
    //   218: lmul
    //   219: invokestatic 407	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   222: aastore
    //   223: invokestatic 351	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   226: astore 7
    //   228: aload 7
    //   230: invokestatic 44	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   233: aload 7
    //   235: invokestatic 47	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   238: aload 7
    //   240: invokestatic 50	mt/Log229316:a	(Ljava/lang/Object;)V
    //   243: aload 6
    //   245: aload 8
    //   247: aload 7
    //   249: iconst_1
    //   250: anewarray 247	java/lang/Throwable
    //   253: dup
    //   254: iconst_0
    //   255: aload 5
    //   257: aastore
    //   258: invokevirtual 251	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   261: aload_0
    //   262: aload_0
    //   263: getfield 83	androidx/work/impl/utils/ForceStopRunnable:mRetryCount	I
    //   266: i2l
    //   267: ldc2_w 18
    //   270: lmul
    //   271: invokevirtual 411	androidx/work/impl/utils/ForceStopRunnable:sleep	(J)V
    //   274: goto -257 -> 17
    //   277: astore 5
    //   279: aload_0
    //   280: getfield 81	androidx/work/impl/utils/ForceStopRunnable:mWorkManager	Landroidx/work/impl/WorkManagerImpl;
    //   283: invokevirtual 369	androidx/work/impl/WorkManagerImpl:onForceStopRunnableCompleted	()V
    //   286: aload 5
    //   288: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	289	0	this	ForceStopRunnable
    //   80	113	1	i	int
    //   4	2	2	bool	boolean
    //   194	21	3	l	long
    //   47	1	5	localSQLiteAccessPermException	android.database.sqlite.SQLiteAccessPermException
    //   52	1	5	localSQLiteConstraintException	android.database.sqlite.SQLiteConstraintException
    //   57	1	5	localSQLiteTableLockedException	android.database.sqlite.SQLiteTableLockedException
    //   62	1	5	localSQLiteDatabaseLockedException	android.database.sqlite.SQLiteDatabaseLockedException
    //   67	1	5	localSQLiteDatabaseCorruptException	android.database.sqlite.SQLiteDatabaseCorruptException
    //   72	59	5	localSQLiteCantOpenDatabaseException	android.database.sqlite.SQLiteCantOpenDatabaseException
    //   145	111	5	localInitializationExceptionHandler	androidx.work.InitializationExceptionHandler
    //   277	10	5	localObject1	Object
    //   99	145	6	localObject2	Object
    //   94	154	7	localObject3	Object
    //   203	43	8	str	String
    // Exception table:
    //   from	to	target	type
    //   40	44	47	android/database/sqlite/SQLiteAccessPermException
    //   40	44	52	android/database/sqlite/SQLiteConstraintException
    //   40	44	57	android/database/sqlite/SQLiteTableLockedException
    //   40	44	62	android/database/sqlite/SQLiteDatabaseLockedException
    //   40	44	67	android/database/sqlite/SQLiteDatabaseCorruptException
    //   40	44	72	android/database/sqlite/SQLiteCantOpenDatabaseException
    //   0	5	277	finally
    //   17	40	277	finally
    //   40	44	277	finally
    //   74	86	277	finally
    //   91	147	277	finally
    //   152	181	277	finally
    //   189	192	277	finally
    //   195	228	277	finally
    //   243	274	277	finally
  }
  
  boolean shouldRescheduleWorkers()
  {
    return this.mWorkManager.getPreferenceUtils().getNeedsReschedule();
  }
  
  public void sleep(long paramLong)
  {
    try
    {
      Thread.sleep(paramLong);
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public static class BroadcastReceiver
    extends BroadcastReceiver
  {
    private static final String TAG;
    
    static
    {
      String str = Logger.tagWithPrefix("ForceStopRunnable$Rcvr");
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      TAG = str;
    }
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent != null) && ("ACTION_FORCE_STOP_RESCHEDULE".equals(paramIntent.getAction())))
      {
        Logger.get().verbose(TAG, "Rescheduling alarm that keeps track of force-stops.", new Throwable[0]);
        ForceStopRunnable.setAlarm(paramContext);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/ForceStopRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */