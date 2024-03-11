package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import java.util.HashMap;
import java.util.Map;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class CommandHandler
  implements ExecutionListener
{
  static final String ACTION_CONSTRAINTS_CHANGED = "ACTION_CONSTRAINTS_CHANGED";
  static final String ACTION_DELAY_MET = "ACTION_DELAY_MET";
  static final String ACTION_EXECUTION_COMPLETED = "ACTION_EXECUTION_COMPLETED";
  static final String ACTION_RESCHEDULE = "ACTION_RESCHEDULE";
  static final String ACTION_SCHEDULE_WORK = "ACTION_SCHEDULE_WORK";
  static final String ACTION_STOP_WORK = "ACTION_STOP_WORK";
  private static final String KEY_NEEDS_RESCHEDULE = "KEY_NEEDS_RESCHEDULE";
  private static final String KEY_WORKSPEC_ID = "KEY_WORKSPEC_ID";
  private static final String TAG;
  static final long WORK_PROCESSING_TIME_IN_MS = 600000L;
  private final Context mContext;
  private final Object mLock;
  private final Map<String, ExecutionListener> mPendingDelayMet;
  
  static
  {
    String str = Logger.tagWithPrefix("CommandHandler");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  CommandHandler(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPendingDelayMet = new HashMap();
    this.mLock = new Object();
  }
  
  static Intent createConstraintsChangedIntent(Context paramContext)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_CONSTRAINTS_CHANGED");
    return paramContext;
  }
  
  static Intent createDelayMetIntent(Context paramContext, String paramString)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_DELAY_MET");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  static Intent createExecutionCompletedIntent(Context paramContext, String paramString, boolean paramBoolean)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_EXECUTION_COMPLETED");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    paramContext.putExtra("KEY_NEEDS_RESCHEDULE", paramBoolean);
    return paramContext;
  }
  
  static Intent createRescheduleIntent(Context paramContext)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_RESCHEDULE");
    return paramContext;
  }
  
  static Intent createScheduleWorkIntent(Context paramContext, String paramString)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_SCHEDULE_WORK");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  static Intent createStopWorkIntent(Context paramContext, String paramString)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_STOP_WORK");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  private void handleConstraintsChanged(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Logger localLogger = Logger.get();
    String str = TAG;
    paramIntent = String.format("Handling constraints changed %s", new Object[] { paramIntent });
    Log5ECF72.a(paramIntent);
    LogE84000.a(paramIntent);
    Log229316.a(paramIntent);
    localLogger.debug(str, paramIntent, new Throwable[0]);
    new ConstraintsCommandHandler(this.mContext, paramInt, paramSystemAlarmDispatcher).handleConstraintsChanged();
  }
  
  private void handleDelayMet(Intent arg1, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Object localObject = ???.getExtras();
    synchronized (this.mLock)
    {
      String str1 = ((Bundle)localObject).getString("KEY_WORKSPEC_ID");
      Logger localLogger = Logger.get();
      localObject = TAG;
      String str2 = String.format("Handing delay met for %s", new Object[] { str1 });
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      localLogger.debug((String)localObject, str2, new Throwable[0]);
      if (!this.mPendingDelayMet.containsKey(str1))
      {
        localObject = new androidx/work/impl/background/systemalarm/DelayMetCommandHandler;
        ((DelayMetCommandHandler)localObject).<init>(this.mContext, paramInt, str1, paramSystemAlarmDispatcher);
        this.mPendingDelayMet.put(str1, localObject);
        ((DelayMetCommandHandler)localObject).handleProcessWork();
      }
      else
      {
        paramSystemAlarmDispatcher = Logger.get();
        str1 = String.format("WorkSpec %s is already being handled for ACTION_DELAY_MET", new Object[] { str1 });
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        paramSystemAlarmDispatcher.debug((String)localObject, str1, new Throwable[0]);
      }
      return;
    }
  }
  
  private void handleExecutionCompleted(Intent paramIntent, int paramInt)
  {
    Object localObject = paramIntent.getExtras();
    String str = ((Bundle)localObject).getString("KEY_WORKSPEC_ID");
    boolean bool = ((Bundle)localObject).getBoolean("KEY_NEEDS_RESCHEDULE");
    Logger localLogger = Logger.get();
    localObject = TAG;
    paramIntent = String.format("Handling onExecutionCompleted %s, %s", new Object[] { paramIntent, Integer.valueOf(paramInt) });
    Log5ECF72.a(paramIntent);
    LogE84000.a(paramIntent);
    Log229316.a(paramIntent);
    localLogger.debug((String)localObject, paramIntent, new Throwable[0]);
    onExecuted(str, bool);
  }
  
  private void handleReschedule(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Logger localLogger = Logger.get();
    String str = TAG;
    paramIntent = String.format("Handling reschedule %s, %s", new Object[] { paramIntent, Integer.valueOf(paramInt) });
    Log5ECF72.a(paramIntent);
    LogE84000.a(paramIntent);
    Log229316.a(paramIntent);
    localLogger.debug(str, paramIntent, new Throwable[0]);
    paramSystemAlarmDispatcher.getWorkManager().rescheduleEligibleWork();
  }
  
  private void handleScheduleWorkIntent(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Object localObject2 = paramIntent.getExtras().getString("KEY_WORKSPEC_ID");
    Object localObject1 = Logger.get();
    paramIntent = TAG;
    Object localObject3 = String.format("Handling schedule work for %s", new Object[] { localObject2 });
    Log5ECF72.a(localObject3);
    LogE84000.a(localObject3);
    Log229316.a(localObject3);
    ((Logger)localObject1).debug(paramIntent, (String)localObject3, new Throwable[0]);
    localObject1 = paramSystemAlarmDispatcher.getWorkManager().getWorkDatabase();
    ((WorkDatabase)localObject1).beginTransaction();
    try
    {
      localObject3 = ((WorkDatabase)localObject1).workSpecDao().getWorkSpec((String)localObject2);
      if (localObject3 == null)
      {
        localObject3 = Logger.get();
        paramSystemAlarmDispatcher = new java/lang/StringBuilder;
        paramSystemAlarmDispatcher.<init>();
        ((Logger)localObject3).warning(paramIntent, "Skipping scheduling " + (String)localObject2 + " because it's no longer in the DB", new Throwable[0]);
        ((WorkDatabase)localObject1).endTransaction();
        return;
      }
      if (((WorkSpec)localObject3).state.isFinished())
      {
        localObject3 = Logger.get();
        paramSystemAlarmDispatcher = new java/lang/StringBuilder;
        paramSystemAlarmDispatcher.<init>();
        ((Logger)localObject3).warning(paramIntent, "Skipping scheduling " + (String)localObject2 + "because it is finished.", new Throwable[0]);
        ((WorkDatabase)localObject1).endTransaction();
        return;
      }
      long l = ((WorkSpec)localObject3).calculateNextRunTime();
      String str;
      if (!((WorkSpec)localObject3).hasConstraints())
      {
        localObject3 = Logger.get();
        str = String.format("Setting up Alarms for %s at %s", new Object[] { localObject2, Long.valueOf(l) });
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        ((Logger)localObject3).debug(paramIntent, str, new Throwable[0]);
        Alarms.setAlarm(this.mContext, paramSystemAlarmDispatcher.getWorkManager(), (String)localObject2, l);
      }
      else
      {
        localObject3 = Logger.get();
        str = String.format("Opportunistically setting an alarm for %s at %s", new Object[] { localObject2, Long.valueOf(l) });
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        ((Logger)localObject3).debug(paramIntent, str, new Throwable[0]);
        Alarms.setAlarm(this.mContext, paramSystemAlarmDispatcher.getWorkManager(), (String)localObject2, l);
        localObject2 = createConstraintsChangedIntent(this.mContext);
        paramIntent = new androidx/work/impl/background/systemalarm/SystemAlarmDispatcher$AddRunnable;
      }
      try
      {
        paramIntent.<init>(paramSystemAlarmDispatcher, (Intent)localObject2, paramInt);
        paramSystemAlarmDispatcher.postOnMainThread(paramIntent);
        ((WorkDatabase)localObject1).setTransactionSuccessful();
        ((WorkDatabase)localObject1).endTransaction();
        return;
      }
      finally {}
      ((WorkDatabase)localObject1).endTransaction();
    }
    finally {}
    throw paramIntent;
  }
  
  private void handleStopWork(Intent paramIntent, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    paramIntent = paramIntent.getExtras().getString("KEY_WORKSPEC_ID");
    Logger localLogger = Logger.get();
    String str2 = TAG;
    String str1 = String.format("Handing stopWork work for %s", new Object[] { paramIntent });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    localLogger.debug(str2, str1, new Throwable[0]);
    paramSystemAlarmDispatcher.getWorkManager().stopWork(paramIntent);
    Alarms.cancelAlarm(this.mContext, paramSystemAlarmDispatcher.getWorkManager(), paramIntent);
    paramSystemAlarmDispatcher.onExecuted(paramIntent, false);
  }
  
  private static boolean hasKeys(Bundle paramBundle, String... paramVarArgs)
  {
    if ((paramBundle != null) && (!paramBundle.isEmpty()))
    {
      int j = paramVarArgs.length;
      for (int i = 0; i < j; i++) {
        if (paramBundle.get(paramVarArgs[i]) == null) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  boolean hasPendingCommands()
  {
    synchronized (this.mLock)
    {
      boolean bool;
      if (!this.mPendingDelayMet.isEmpty()) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      ExecutionListener localExecutionListener = (ExecutionListener)this.mPendingDelayMet.remove(paramString);
      if (localExecutionListener != null) {
        localExecutionListener.onExecuted(paramString, paramBoolean);
      }
      return;
    }
  }
  
  void onHandleIntent(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    String str = paramIntent.getAction();
    if ("ACTION_CONSTRAINTS_CHANGED".equals(str))
    {
      handleConstraintsChanged(paramIntent, paramInt, paramSystemAlarmDispatcher);
    }
    else if ("ACTION_RESCHEDULE".equals(str))
    {
      handleReschedule(paramIntent, paramInt, paramSystemAlarmDispatcher);
    }
    else if (!hasKeys(paramIntent.getExtras(), new String[] { "KEY_WORKSPEC_ID" }))
    {
      paramSystemAlarmDispatcher = Logger.get();
      paramIntent = TAG;
      str = String.format("Invalid request for %s, requires %s.", new Object[] { str, "KEY_WORKSPEC_ID" });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      paramSystemAlarmDispatcher.error(paramIntent, str, new Throwable[0]);
    }
    else if ("ACTION_SCHEDULE_WORK".equals(str))
    {
      handleScheduleWorkIntent(paramIntent, paramInt, paramSystemAlarmDispatcher);
    }
    else if ("ACTION_DELAY_MET".equals(str))
    {
      handleDelayMet(paramIntent, paramInt, paramSystemAlarmDispatcher);
    }
    else if ("ACTION_STOP_WORK".equals(str))
    {
      handleStopWork(paramIntent, paramSystemAlarmDispatcher);
    }
    else if ("ACTION_EXECUTION_COMPLETED".equals(str))
    {
      handleExecutionCompleted(paramIntent, paramInt);
    }
    else
    {
      paramSystemAlarmDispatcher = Logger.get();
      str = TAG;
      paramIntent = String.format("Ignoring intent %s", new Object[] { paramIntent });
      Log5ECF72.a(paramIntent);
      LogE84000.a(paramIntent);
      Log229316.a(paramIntent);
      paramSystemAlarmDispatcher.warning(str, paramIntent, new Throwable[0]);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/CommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */