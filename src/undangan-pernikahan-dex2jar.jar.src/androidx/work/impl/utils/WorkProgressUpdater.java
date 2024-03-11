package androidx.work.impl.utils;

import android.content.Context;
import androidx.work.Data;
import androidx.work.Logger;
import androidx.work.ProgressUpdater;
import androidx.work.WorkInfo.State;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.model.WorkProgress;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WorkProgressUpdater
  implements ProgressUpdater
{
  static final String TAG;
  final TaskExecutor mTaskExecutor;
  final WorkDatabase mWorkDatabase;
  
  static
  {
    String str = Logger.tagWithPrefix("WorkProgressUpdater");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public WorkProgressUpdater(WorkDatabase paramWorkDatabase, TaskExecutor paramTaskExecutor)
  {
    this.mWorkDatabase = paramWorkDatabase;
    this.mTaskExecutor = paramTaskExecutor;
  }
  
  public ListenableFuture<Void> updateProgress(final Context paramContext, final UUID paramUUID, final Data paramData)
  {
    paramContext = SettableFuture.create();
    this.mTaskExecutor.executeOnBackgroundThread(new Runnable()
    {
      public void run()
      {
        Object localObject1 = paramUUID.toString();
        Object localObject4 = Logger.get();
        Object localObject3 = WorkProgressUpdater.TAG;
        String str = String.format("Updating progress for %s (%s)", new Object[] { paramUUID, paramData });
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        ((Logger)localObject4).debug((String)localObject3, str, new Throwable[0]);
        WorkProgressUpdater.this.mWorkDatabase.beginTransaction();
        try
        {
          localObject3 = WorkProgressUpdater.this.mWorkDatabase.workSpecDao().getWorkSpec((String)localObject1);
          if (localObject3 != null)
          {
            if (((WorkSpec)localObject3).state == WorkInfo.State.RUNNING)
            {
              localObject3 = new androidx/work/impl/model/WorkProgress;
              ((WorkProgress)localObject3).<init>((String)localObject1, paramData);
              WorkProgressUpdater.this.mWorkDatabase.workProgressDao().insert((WorkProgress)localObject3);
            }
            else
            {
              localObject3 = Logger.get();
              localObject4 = WorkProgressUpdater.TAG;
              localObject1 = String.format("Ignoring setProgressAsync(...). WorkSpec (%s) is not in a RUNNING state.", new Object[] { localObject1 });
              Log5ECF72.a(localObject1);
              LogE84000.a(localObject1);
              Log229316.a(localObject1);
              ((Logger)localObject3).warning((String)localObject4, (String)localObject1, new Throwable[0]);
            }
            paramContext.set(null);
            WorkProgressUpdater.this.mWorkDatabase.setTransactionSuccessful();
          }
          else
          {
            localObject1 = new java/lang/IllegalStateException;
            ((IllegalStateException)localObject1).<init>("Calls to setProgressAsync() must complete before a ListenableWorker signals completion of work by returning an instance of Result.");
            throw ((Throwable)localObject1);
          }
        }
        finally
        {
          try
          {
            Logger.get().error(WorkProgressUpdater.TAG, "Error updating Worker progress", new Throwable[] { localThrowable });
            paramContext.setException(localThrowable);
            return;
          }
          finally
          {
            WorkProgressUpdater.this.mWorkDatabase.endTransaction();
          }
        }
      }
    });
    return paramContext;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/WorkProgressUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */