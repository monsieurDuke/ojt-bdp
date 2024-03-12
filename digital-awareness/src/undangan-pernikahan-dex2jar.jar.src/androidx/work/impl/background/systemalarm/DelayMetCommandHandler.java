package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.WorkTimer;
import androidx.work.impl.utils.WorkTimer.TimeLimitExceededListener;
import java.util.Collections;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DelayMetCommandHandler
  implements WorkConstraintsCallback, ExecutionListener, WorkTimer.TimeLimitExceededListener
{
  private static final int STATE_INITIAL = 0;
  private static final int STATE_START_REQUESTED = 1;
  private static final int STATE_STOP_REQUESTED = 2;
  private static final String TAG;
  private final Context mContext;
  private int mCurrentState;
  private final SystemAlarmDispatcher mDispatcher;
  private boolean mHasConstraints;
  private final Object mLock;
  private final int mStartId;
  private PowerManager.WakeLock mWakeLock;
  private final WorkConstraintsTracker mWorkConstraintsTracker;
  private final String mWorkSpecId;
  
  static
  {
    String str = Logger.tagWithPrefix("DelayMetCommandHandler");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  DelayMetCommandHandler(Context paramContext, int paramInt, String paramString, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    this.mContext = paramContext;
    this.mStartId = paramInt;
    this.mDispatcher = paramSystemAlarmDispatcher;
    this.mWorkSpecId = paramString;
    this.mWorkConstraintsTracker = new WorkConstraintsTracker(paramContext, paramSystemAlarmDispatcher.getTaskExecutor(), this);
    this.mHasConstraints = false;
    this.mCurrentState = 0;
    this.mLock = new Object();
  }
  
  private void cleanUp()
  {
    synchronized (this.mLock)
    {
      this.mWorkConstraintsTracker.reset();
      this.mDispatcher.getWorkTimer().stopTimer(this.mWorkSpecId);
      Object localObject2 = this.mWakeLock;
      if ((localObject2 != null) && (((PowerManager.WakeLock)localObject2).isHeld()))
      {
        localObject2 = Logger.get();
        String str2 = TAG;
        String str1 = String.format("Releasing wakelock %s for WorkSpec %s", new Object[] { this.mWakeLock, this.mWorkSpecId });
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        ((Logger)localObject2).debug(str2, str1, new Throwable[0]);
        this.mWakeLock.release();
      }
      return;
    }
  }
  
  private void stopWork()
  {
    synchronized (this.mLock)
    {
      Object localObject5;
      Object localObject2;
      Object localObject4;
      if (this.mCurrentState < 2)
      {
        this.mCurrentState = 2;
        localObject5 = Logger.get();
        localObject2 = TAG;
        localObject4 = String.format("Stopping work for WorkSpec %s", new Object[] { this.mWorkSpecId });
        Log5ECF72.a(localObject4);
        LogE84000.a(localObject4);
        Log229316.a(localObject4);
        ((Logger)localObject5).debug((String)localObject2, (String)localObject4, new Throwable[0]);
        Intent localIntent = CommandHandler.createStopWorkIntent(this.mContext, this.mWorkSpecId);
        localObject5 = this.mDispatcher;
        localObject4 = new androidx/work/impl/background/systemalarm/SystemAlarmDispatcher$AddRunnable;
        ((SystemAlarmDispatcher.AddRunnable)localObject4).<init>(this.mDispatcher, localIntent, this.mStartId);
        ((SystemAlarmDispatcher)localObject5).postOnMainThread((Runnable)localObject4);
        if (this.mDispatcher.getProcessor().isEnqueued(this.mWorkSpecId))
        {
          localObject4 = Logger.get();
          localObject5 = String.format("WorkSpec %s needs to be rescheduled", new Object[] { this.mWorkSpecId });
          Log5ECF72.a(localObject5);
          LogE84000.a(localObject5);
          Log229316.a(localObject5);
          ((Logger)localObject4).debug((String)localObject2, (String)localObject5, new Throwable[0]);
          localObject2 = CommandHandler.createScheduleWorkIntent(this.mContext, this.mWorkSpecId);
          localObject5 = this.mDispatcher;
          localObject4 = new androidx/work/impl/background/systemalarm/SystemAlarmDispatcher$AddRunnable;
          ((SystemAlarmDispatcher.AddRunnable)localObject4).<init>(this.mDispatcher, (Intent)localObject2, this.mStartId);
          ((SystemAlarmDispatcher)localObject5).postOnMainThread((Runnable)localObject4);
        }
        else
        {
          localObject5 = Logger.get();
          localObject4 = String.format("Processor does not have WorkSpec %s. No need to reschedule ", new Object[] { this.mWorkSpecId });
          Log5ECF72.a(localObject4);
          LogE84000.a(localObject4);
          Log229316.a(localObject4);
          ((Logger)localObject5).debug((String)localObject2, (String)localObject4, new Throwable[0]);
        }
      }
      else
      {
        localObject4 = Logger.get();
        localObject2 = TAG;
        localObject5 = String.format("Already stopped work for %s", new Object[] { this.mWorkSpecId });
        Log5ECF72.a(localObject5);
        LogE84000.a(localObject5);
        Log229316.a(localObject5);
        ((Logger)localObject4).debug((String)localObject2, (String)localObject5, new Throwable[0]);
      }
      return;
    }
  }
  
  void handleProcessWork()
  {
    Object localObject = this.mContext;
    String str1 = String.format("%s (%s)", new Object[] { this.mWorkSpecId, Integer.valueOf(this.mStartId) });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    this.mWakeLock = WakeLocks.newWakeLock((Context)localObject, str1);
    localObject = Logger.get();
    str1 = TAG;
    String str2 = String.format("Acquiring wakelock %s for WorkSpec %s", new Object[] { this.mWakeLock, this.mWorkSpecId });
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    ((Logger)localObject).debug(str1, str2, new Throwable[0]);
    this.mWakeLock.acquire();
    localObject = this.mDispatcher.getWorkManager().getWorkDatabase().workSpecDao().getWorkSpec(this.mWorkSpecId);
    if (localObject == null)
    {
      stopWork();
      return;
    }
    boolean bool = ((WorkSpec)localObject).hasConstraints();
    this.mHasConstraints = bool;
    if (!bool)
    {
      localObject = Logger.get();
      str2 = String.format("No constraints for %s", new Object[] { this.mWorkSpecId });
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      ((Logger)localObject).debug(str1, str2, new Throwable[0]);
      onAllConstraintsMet(Collections.singletonList(this.mWorkSpecId));
    }
    else
    {
      this.mWorkConstraintsTracker.replace(Collections.singletonList(localObject));
    }
  }
  
  public void onAllConstraintsMet(List<String> arg1)
  {
    if (!???.contains(this.mWorkSpecId)) {
      return;
    }
    synchronized (this.mLock)
    {
      Object localObject3;
      Object localObject2;
      String str;
      if (this.mCurrentState == 0)
      {
        this.mCurrentState = 1;
        localObject3 = Logger.get();
        localObject2 = TAG;
        str = String.format("onAllConstraintsMet for %s", new Object[] { this.mWorkSpecId });
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        ((Logger)localObject3).debug((String)localObject2, str, new Throwable[0]);
        if (this.mDispatcher.getProcessor().startWork(this.mWorkSpecId)) {
          this.mDispatcher.getWorkTimer().startTimer(this.mWorkSpecId, 600000L, this);
        } else {
          cleanUp();
        }
      }
      else
      {
        localObject2 = Logger.get();
        str = TAG;
        localObject3 = String.format("Already started work for %s", new Object[] { this.mWorkSpecId });
        Log5ECF72.a(localObject3);
        LogE84000.a(localObject3);
        Log229316.a(localObject3);
        ((Logger)localObject2).debug(str, (String)localObject3, new Throwable[0]);
      }
      return;
    }
  }
  
  public void onAllConstraintsNotMet(List<String> paramList)
  {
    stopWork();
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    Logger localLogger = Logger.get();
    String str = TAG;
    paramString = String.format("onExecuted %s, %s", new Object[] { paramString, Boolean.valueOf(paramBoolean) });
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    localLogger.debug(str, paramString, new Throwable[0]);
    cleanUp();
    if (paramBoolean)
    {
      paramString = CommandHandler.createScheduleWorkIntent(this.mContext, this.mWorkSpecId);
      this.mDispatcher.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(this.mDispatcher, paramString, this.mStartId));
    }
    if (this.mHasConstraints)
    {
      paramString = CommandHandler.createConstraintsChangedIntent(this.mContext);
      this.mDispatcher.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(this.mDispatcher, paramString, this.mStartId));
    }
  }
  
  public void onTimeLimitExceeded(String paramString)
  {
    Logger localLogger = Logger.get();
    String str = TAG;
    paramString = String.format("Exceeded time limits on execution for %s", new Object[] { paramString });
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    localLogger.debug(str, paramString, new Throwable[0]);
    stopWork();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/DelayMetCommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */