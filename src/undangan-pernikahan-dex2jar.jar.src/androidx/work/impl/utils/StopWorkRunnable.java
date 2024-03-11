package androidx.work.impl.utils;

import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpecDao;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class StopWorkRunnable
  implements Runnable
{
  private static final String TAG;
  private final boolean mStopInForeground;
  private final WorkManagerImpl mWorkManagerImpl;
  private final String mWorkSpecId;
  
  static
  {
    String str = Logger.tagWithPrefix("StopWorkRunnable");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public StopWorkRunnable(WorkManagerImpl paramWorkManagerImpl, String paramString, boolean paramBoolean)
  {
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mWorkSpecId = paramString;
    this.mStopInForeground = paramBoolean;
  }
  
  public void run()
  {
    WorkDatabase localWorkDatabase = this.mWorkManagerImpl.getWorkDatabase();
    Object localObject1 = this.mWorkManagerImpl.getProcessor();
    Object localObject3 = localWorkDatabase.workSpecDao();
    localWorkDatabase.beginTransaction();
    try
    {
      boolean bool = ((Processor)localObject1).isEnqueuedInForeground(this.mWorkSpecId);
      if (this.mStopInForeground)
      {
        bool = this.mWorkManagerImpl.getProcessor().stopForegroundWork(this.mWorkSpecId);
      }
      else
      {
        if ((!bool) && (((WorkSpecDao)localObject3).getState(this.mWorkSpecId) == WorkInfo.State.RUNNING)) {
          ((WorkSpecDao)localObject3).setState(WorkInfo.State.ENQUEUED, new String[] { this.mWorkSpecId });
        }
        bool = this.mWorkManagerImpl.getProcessor().stopWork(this.mWorkSpecId);
      }
      localObject3 = Logger.get();
      String str = TAG;
      localObject1 = String.format("StopWorkRunnable for %s; Processor.stopWork = %s", new Object[] { this.mWorkSpecId, Boolean.valueOf(bool) });
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      ((Logger)localObject3).debug(str, (String)localObject1, new Throwable[0]);
      localWorkDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localWorkDatabase.endTransaction();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/StopWorkRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */