package androidx.work.impl.utils;

import android.content.Context;
import androidx.core.os.BuildCompat;
import androidx.work.ForegroundInfo;
import androidx.work.ForegroundUpdater;
import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WorkForegroundRunnable
  implements Runnable
{
  static final String TAG;
  final Context mContext;
  final ForegroundUpdater mForegroundUpdater;
  final SettableFuture<Void> mFuture = SettableFuture.create();
  final TaskExecutor mTaskExecutor;
  final WorkSpec mWorkSpec;
  final ListenableWorker mWorker;
  
  static
  {
    String str = Logger.tagWithPrefix("WorkForegroundRunnable");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public WorkForegroundRunnable(Context paramContext, WorkSpec paramWorkSpec, ListenableWorker paramListenableWorker, ForegroundUpdater paramForegroundUpdater, TaskExecutor paramTaskExecutor)
  {
    this.mContext = paramContext;
    this.mWorkSpec = paramWorkSpec;
    this.mWorker = paramListenableWorker;
    this.mForegroundUpdater = paramForegroundUpdater;
    this.mTaskExecutor = paramTaskExecutor;
  }
  
  public ListenableFuture<Void> getFuture()
  {
    return this.mFuture;
  }
  
  public void run()
  {
    if ((this.mWorkSpec.expedited) && (!BuildCompat.isAtLeastS()))
    {
      final SettableFuture localSettableFuture = SettableFuture.create();
      this.mTaskExecutor.getMainThreadExecutor().execute(new Runnable()
      {
        public void run()
        {
          localSettableFuture.setFuture(WorkForegroundRunnable.this.mWorker.getForegroundInfoAsync());
        }
      });
      localSettableFuture.addListener(new Runnable()
      {
        public void run()
        {
          try
          {
            Object localObject1 = (ForegroundInfo)localSettableFuture.get();
            Object localObject2;
            if (localObject1 != null)
            {
              localObject2 = Logger.get();
              String str2 = WorkForegroundRunnable.TAG;
              String str1 = String.format("Updating notification for %s", new Object[] { WorkForegroundRunnable.this.mWorkSpec.workerClassName });
              Log5ECF72.a(str1);
              LogE84000.a(str1);
              Log229316.a(str1);
              ((Logger)localObject2).debug(str2, str1, new Throwable[0]);
              WorkForegroundRunnable.this.mWorker.setRunInForeground(true);
              WorkForegroundRunnable.this.mFuture.setFuture(WorkForegroundRunnable.this.mForegroundUpdater.setForegroundAsync(WorkForegroundRunnable.this.mContext, WorkForegroundRunnable.this.mWorker.getId(), (ForegroundInfo)localObject1));
            }
            else
            {
              localObject1 = String.format("Worker was marked important (%s) but did not provide ForegroundInfo", new Object[] { WorkForegroundRunnable.this.mWorkSpec.workerClassName });
              Log5ECF72.a(localObject1);
              LogE84000.a(localObject1);
              Log229316.a(localObject1);
              localObject2 = new java/lang/IllegalStateException;
              ((IllegalStateException)localObject2).<init>((String)localObject1);
              throw ((Throwable)localObject2);
            }
          }
          finally
          {
            WorkForegroundRunnable.this.mFuture.setException(localThrowable);
          }
        }
      }, this.mTaskExecutor.getMainThreadExecutor());
      return;
    }
    this.mFuture.set(null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/WorkForegroundRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */