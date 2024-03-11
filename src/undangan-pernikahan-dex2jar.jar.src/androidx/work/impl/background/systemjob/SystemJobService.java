package androidx.work.impl.background.systemjob;

import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.work.Logger;
import androidx.work.WorkerParameters.RuntimeExtras;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkManagerImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class SystemJobService
  extends JobService
  implements ExecutionListener
{
  private static final String TAG;
  private final Map<String, JobParameters> mJobParameters = new HashMap();
  private WorkManagerImpl mWorkManagerImpl;
  
  static
  {
    String str = Logger.tagWithPrefix("SystemJobService");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  private static String getWorkSpecIdFromJobParameters(JobParameters paramJobParameters)
  {
    try
    {
      paramJobParameters = paramJobParameters.getExtras();
      if ((paramJobParameters != null) && (paramJobParameters.containsKey("EXTRA_WORK_SPEC_ID")))
      {
        paramJobParameters = paramJobParameters.getString("EXTRA_WORK_SPEC_ID");
        return paramJobParameters;
      }
    }
    catch (NullPointerException paramJobParameters) {}
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    try
    {
      WorkManagerImpl localWorkManagerImpl = WorkManagerImpl.getInstance(getApplicationContext());
      this.mWorkManagerImpl = localWorkManagerImpl;
      localWorkManagerImpl.getProcessor().addExecutionListener(this);
    }
    catch (IllegalStateException localIllegalStateException)
    {
      if (!Application.class.equals(getApplication().getClass())) {
        break label60;
      }
    }
    Logger.get().warning(TAG, "Could not find WorkManager instance; this may be because an auto-backup is in progress. Ignoring JobScheduler commands for now. Please make sure that you are initializing WorkManager if you have manually disabled WorkManagerInitializer.", new Throwable[0]);
    return;
    label60:
    throw new IllegalStateException("WorkManager needs to be initialized via a ContentProvider#onCreate() or an Application#onCreate().");
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    WorkManagerImpl localWorkManagerImpl = this.mWorkManagerImpl;
    if (localWorkManagerImpl != null) {
      localWorkManagerImpl.getProcessor().removeExecutionListener(this);
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    ??? = Logger.get();
    String str1 = TAG;
    String str2 = String.format("%s executed on JobScheduler", new Object[] { paramString });
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    ((Logger)???).debug(str1, str2, new Throwable[0]);
    synchronized (this.mJobParameters)
    {
      paramString = (JobParameters)this.mJobParameters.remove(paramString);
      if (paramString != null) {
        jobFinished(paramString, paramBoolean);
      }
      return;
    }
  }
  
  public boolean onStartJob(JobParameters paramJobParameters)
  {
    if (this.mWorkManagerImpl == null)
    {
      Logger.get().debug(TAG, "WorkManager is not initialized; requesting retry.", new Throwable[0]);
      jobFinished(paramJobParameters, true);
      return false;
    }
    String str1 = getWorkSpecIdFromJobParameters(paramJobParameters);
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    if (TextUtils.isEmpty(str1))
    {
      Logger.get().error(TAG, "WorkSpec id not found!", new Throwable[0]);
      return false;
    }
    synchronized (this.mJobParameters)
    {
      if (this.mJobParameters.containsKey(str1))
      {
        paramJobParameters = Logger.get();
        localObject2 = TAG;
        str1 = String.format("Job is already being executed by SystemJobService: %s", new Object[] { str1 });
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        paramJobParameters.debug((String)localObject2, str1, new Throwable[0]);
        return false;
      }
      Logger localLogger = Logger.get();
      Object localObject2 = TAG;
      String str2 = String.format("onStartJob for %s", new Object[] { str1 });
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      localLogger.debug((String)localObject2, str2, new Throwable[0]);
      this.mJobParameters.put(str1, paramJobParameters);
      ??? = null;
      if (Build.VERSION.SDK_INT >= 24)
      {
        localObject2 = new WorkerParameters.RuntimeExtras();
        if (paramJobParameters.getTriggeredContentUris() != null) {
          ((WorkerParameters.RuntimeExtras)localObject2).triggeredContentUris = Arrays.asList(paramJobParameters.getTriggeredContentUris());
        }
        if (paramJobParameters.getTriggeredContentAuthorities() != null) {
          ((WorkerParameters.RuntimeExtras)localObject2).triggeredContentAuthorities = Arrays.asList(paramJobParameters.getTriggeredContentAuthorities());
        }
        ??? = localObject2;
        if (Build.VERSION.SDK_INT >= 28)
        {
          ((WorkerParameters.RuntimeExtras)localObject2).network = paramJobParameters.getNetwork();
          ??? = localObject2;
        }
      }
      this.mWorkManagerImpl.startWork(str1, (WorkerParameters.RuntimeExtras)???);
      return true;
    }
  }
  
  public boolean onStopJob(JobParameters paramJobParameters)
  {
    if (this.mWorkManagerImpl == null)
    {
      Logger.get().debug(TAG, "WorkManager is not initialized; requesting retry.", new Throwable[0]);
      return true;
    }
    paramJobParameters = getWorkSpecIdFromJobParameters(paramJobParameters);
    Log5ECF72.a(paramJobParameters);
    LogE84000.a(paramJobParameters);
    Log229316.a(paramJobParameters);
    if (TextUtils.isEmpty(paramJobParameters))
    {
      Logger.get().error(TAG, "WorkSpec id not found!", new Throwable[0]);
      return false;
    }
    Logger localLogger = Logger.get();
    ??? = TAG;
    String str = String.format("onStopJob for %s", new Object[] { paramJobParameters });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localLogger.debug((String)???, str, new Throwable[0]);
    synchronized (this.mJobParameters)
    {
      this.mJobParameters.remove(paramJobParameters);
      this.mWorkManagerImpl.stopWork(paramJobParameters);
      return true ^ this.mWorkManagerImpl.getProcessor().isCancelled(paramJobParameters);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemjob/SystemJobService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */