package androidx.work.impl.utils;

import android.content.Context;
import android.content.Intent;
import androidx.work.ForegroundInfo;
import androidx.work.ForegroundUpdater;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WorkForegroundUpdater
  implements ForegroundUpdater
{
  private static final String TAG;
  final ForegroundProcessor mForegroundProcessor;
  private final TaskExecutor mTaskExecutor;
  final WorkSpecDao mWorkSpecDao;
  
  static
  {
    String str = Logger.tagWithPrefix("WMFgUpdater");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public WorkForegroundUpdater(WorkDatabase paramWorkDatabase, ForegroundProcessor paramForegroundProcessor, TaskExecutor paramTaskExecutor)
  {
    this.mForegroundProcessor = paramForegroundProcessor;
    this.mTaskExecutor = paramTaskExecutor;
    this.mWorkSpecDao = paramWorkDatabase.workSpecDao();
  }
  
  public ListenableFuture<Void> setForegroundAsync(final Context paramContext, final UUID paramUUID, final ForegroundInfo paramForegroundInfo)
  {
    final SettableFuture localSettableFuture = SettableFuture.create();
    this.mTaskExecutor.executeOnBackgroundThread(new Runnable()
    {
      public void run()
      {
        try
        {
          if (!localSettableFuture.isCancelled())
          {
            Object localObject = paramUUID.toString();
            WorkInfo.State localState = WorkForegroundUpdater.this.mWorkSpecDao.getState((String)localObject);
            if ((localState != null) && (!localState.isFinished()))
            {
              WorkForegroundUpdater.this.mForegroundProcessor.startForeground((String)localObject, paramForegroundInfo);
              localObject = SystemForegroundDispatcher.createNotifyIntent(paramContext, (String)localObject, paramForegroundInfo);
              paramContext.startService((Intent)localObject);
            }
            else
            {
              localObject = new java/lang/IllegalStateException;
              ((IllegalStateException)localObject).<init>("Calls to setForegroundAsync() must complete before a ListenableWorker signals completion of work by returning an instance of Result.");
              throw ((Throwable)localObject);
            }
          }
        }
        finally
        {
          localSettableFuture.setException(localThrowable);
        }
      }
    });
    return localSettableFuture;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/WorkForegroundUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */