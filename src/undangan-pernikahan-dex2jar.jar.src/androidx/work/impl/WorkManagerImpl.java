package androidx.work.impl;

import android.app.PendingIntent;
import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.arch.core.util.Function;
import androidx.core.os.BuildCompat;
import androidx.lifecycle.LiveData;
import androidx.work.Configuration;
import androidx.work.Configuration.Provider;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.Logger;
import androidx.work.Logger.LogcatLogger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.R.bool;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkQuery;
import androidx.work.WorkRequest;
import androidx.work.WorkerParameters.RuntimeExtras;
import androidx.work.impl.background.greedy.GreedyScheduler;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.model.RawWorkInfoDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpec.WorkInfoPojo;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.CancelWorkRunnable;
import androidx.work.impl.utils.ForceStopRunnable;
import androidx.work.impl.utils.LiveDataUtils;
import androidx.work.impl.utils.PreferenceUtils;
import androidx.work.impl.utils.PruneWorkRunnable;
import androidx.work.impl.utils.RawQueries;
import androidx.work.impl.utils.SerialExecutor;
import androidx.work.impl.utils.StartWorkRunnable;
import androidx.work.impl.utils.StatusRunnable;
import androidx.work.impl.utils.StopWorkRunnable;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor;
import androidx.work.multiprocess.RemoteWorkManager;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WorkManagerImpl
  extends WorkManager
{
  public static final int MAX_PRE_JOB_SCHEDULER_API_LEVEL = 22;
  public static final int MIN_JOB_SCHEDULER_API_LEVEL = 23;
  public static final String REMOTE_WORK_MANAGER_CLIENT = "androidx.work.multiprocess.RemoteWorkManagerClient";
  private static final String TAG;
  private static WorkManagerImpl sDefaultInstance = null;
  private static WorkManagerImpl sDelegatedInstance;
  private static final Object sLock = new Object();
  private Configuration mConfiguration;
  private Context mContext;
  private boolean mForceStopRunnableCompleted;
  private PreferenceUtils mPreferenceUtils;
  private Processor mProcessor;
  private volatile RemoteWorkManager mRemoteWorkManager;
  private BroadcastReceiver.PendingResult mRescheduleReceiverResult;
  private List<Scheduler> mSchedulers;
  private WorkDatabase mWorkDatabase;
  private TaskExecutor mWorkTaskExecutor;
  
  static
  {
    String str = Logger.tagWithPrefix("WorkManagerImpl");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
    sDelegatedInstance = null;
  }
  
  public WorkManagerImpl(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor)
  {
    this(paramContext, paramConfiguration, paramTaskExecutor, paramContext.getResources().getBoolean(R.bool.workmanager_test_configuration));
  }
  
  public WorkManagerImpl(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, WorkDatabase paramWorkDatabase)
  {
    Object localObject = paramContext.getApplicationContext();
    Logger.setLogger(new Logger.LogcatLogger(paramConfiguration.getMinimumLoggingLevel()));
    localObject = createSchedulers((Context)localObject, paramConfiguration, paramTaskExecutor);
    internalInit(paramContext, paramConfiguration, paramTaskExecutor, paramWorkDatabase, (List)localObject, new Processor(paramContext, paramConfiguration, paramTaskExecutor, paramWorkDatabase, (List)localObject));
  }
  
  public WorkManagerImpl(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, WorkDatabase paramWorkDatabase, List<Scheduler> paramList, Processor paramProcessor)
  {
    internalInit(paramContext, paramConfiguration, paramTaskExecutor, paramWorkDatabase, paramList, paramProcessor);
  }
  
  public WorkManagerImpl(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, boolean paramBoolean)
  {
    this(paramContext, paramConfiguration, paramTaskExecutor, WorkDatabase.create(paramContext.getApplicationContext(), paramTaskExecutor.getBackgroundExecutor(), paramBoolean));
  }
  
  @Deprecated
  public static WorkManagerImpl getInstance()
  {
    synchronized (sLock)
    {
      WorkManagerImpl localWorkManagerImpl = sDelegatedInstance;
      if (localWorkManagerImpl != null) {
        return localWorkManagerImpl;
      }
      localWorkManagerImpl = sDefaultInstance;
      return localWorkManagerImpl;
    }
  }
  
  public static WorkManagerImpl getInstance(Context paramContext)
  {
    synchronized (sLock)
    {
      WorkManagerImpl localWorkManagerImpl2 = getInstance();
      WorkManagerImpl localWorkManagerImpl1 = localWorkManagerImpl2;
      if (localWorkManagerImpl2 == null)
      {
        paramContext = paramContext.getApplicationContext();
        if ((paramContext instanceof Configuration.Provider))
        {
          initialize(paramContext, ((Configuration.Provider)paramContext).getWorkManagerConfiguration());
          localWorkManagerImpl1 = getInstance(paramContext);
        }
        else
        {
          paramContext = new java/lang/IllegalStateException;
          paramContext.<init>("WorkManager is not initialized properly.  You have explicitly disabled WorkManagerInitializer in your manifest, have not manually called WorkManager#initialize at this point, and your Application does not implement Configuration.Provider.");
          throw paramContext;
        }
      }
      return localWorkManagerImpl1;
    }
  }
  
  public static void initialize(Context paramContext, Configuration paramConfiguration)
  {
    synchronized (sLock)
    {
      WorkManagerImpl localWorkManagerImpl = sDelegatedInstance;
      if ((localWorkManagerImpl != null) && (sDefaultInstance != null))
      {
        paramContext = new java/lang/IllegalStateException;
        paramContext.<init>("WorkManager is already initialized.  Did you try to initialize it manually without disabling WorkManagerInitializer? See WorkManager#initialize(Context, Configuration) or the class level Javadoc for more information.");
        throw paramContext;
      }
      if (localWorkManagerImpl == null)
      {
        paramContext = paramContext.getApplicationContext();
        if (sDefaultInstance == null)
        {
          localWorkManagerImpl = new androidx/work/impl/WorkManagerImpl;
          WorkManagerTaskExecutor localWorkManagerTaskExecutor = new androidx/work/impl/utils/taskexecutor/WorkManagerTaskExecutor;
          localWorkManagerTaskExecutor.<init>(paramConfiguration.getTaskExecutor());
          localWorkManagerImpl.<init>(paramContext, paramConfiguration, localWorkManagerTaskExecutor);
          sDefaultInstance = localWorkManagerImpl;
        }
        sDelegatedInstance = sDefaultInstance;
      }
      return;
    }
  }
  
  private void internalInit(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, WorkDatabase paramWorkDatabase, List<Scheduler> paramList, Processor paramProcessor)
  {
    paramContext = paramContext.getApplicationContext();
    this.mContext = paramContext;
    this.mConfiguration = paramConfiguration;
    this.mWorkTaskExecutor = paramTaskExecutor;
    this.mWorkDatabase = paramWorkDatabase;
    this.mSchedulers = paramList;
    this.mProcessor = paramProcessor;
    this.mPreferenceUtils = new PreferenceUtils(paramWorkDatabase);
    this.mForceStopRunnableCompleted = false;
    if ((Build.VERSION.SDK_INT >= 24) && (paramContext.isDeviceProtectedStorage())) {
      throw new IllegalStateException("Cannot initialize WorkManager in direct boot mode");
    }
    this.mWorkTaskExecutor.executeOnBackgroundThread(new ForceStopRunnable(paramContext, this));
  }
  
  public static void setDelegate(WorkManagerImpl paramWorkManagerImpl)
  {
    synchronized (sLock)
    {
      sDelegatedInstance = paramWorkManagerImpl;
      return;
    }
  }
  
  /* Error */
  private void tryInitializeMultiProcessSupport()
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 17
    //   3: invokestatic 240	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   6: iconst_2
    //   7: anewarray 236	java/lang/Class
    //   10: dup
    //   11: iconst_0
    //   12: ldc 83
    //   14: aastore
    //   15: dup
    //   16: iconst_1
    //   17: ldc 2
    //   19: aastore
    //   20: invokevirtual 244	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   23: iconst_2
    //   24: anewarray 74	java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: getfield 192	androidx/work/impl/WorkManagerImpl:mContext	Landroid/content/Context;
    //   33: aastore
    //   34: dup
    //   35: iconst_1
    //   36: aload_0
    //   37: aastore
    //   38: invokevirtual 250	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   41: checkcast 252	androidx/work/multiprocess/RemoteWorkManager
    //   44: putfield 254	androidx/work/impl/WorkManagerImpl:mRemoteWorkManager	Landroidx/work/multiprocess/RemoteWorkManager;
    //   47: goto +24 -> 71
    //   50: astore_1
    //   51: invokestatic 258	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   54: getstatic 68	androidx/work/impl/WorkManagerImpl:TAG	Ljava/lang/String;
    //   57: ldc_w 260
    //   60: iconst_1
    //   61: anewarray 262	java/lang/Throwable
    //   64: dup
    //   65: iconst_0
    //   66: aload_1
    //   67: aastore
    //   68: invokevirtual 266	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   71: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	WorkManagerImpl
    //   50	17	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	47	50	finally
  }
  
  public WorkContinuation beginUniqueWork(String paramString, ExistingWorkPolicy paramExistingWorkPolicy, List<OneTimeWorkRequest> paramList)
  {
    if (!paramList.isEmpty()) {
      return new WorkContinuationImpl(this, paramString, paramExistingWorkPolicy, paramList);
    }
    throw new IllegalArgumentException("beginUniqueWork needs at least one OneTimeWorkRequest.");
  }
  
  public WorkContinuation beginWith(List<OneTimeWorkRequest> paramList)
  {
    if (!paramList.isEmpty()) {
      return new WorkContinuationImpl(this, paramList);
    }
    throw new IllegalArgumentException("beginWith needs at least one OneTimeWorkRequest.");
  }
  
  public Operation cancelAllWork()
  {
    CancelWorkRunnable localCancelWorkRunnable = CancelWorkRunnable.forAll(this);
    this.mWorkTaskExecutor.executeOnBackgroundThread(localCancelWorkRunnable);
    return localCancelWorkRunnable.getOperation();
  }
  
  public Operation cancelAllWorkByTag(String paramString)
  {
    paramString = CancelWorkRunnable.forTag(paramString, this);
    this.mWorkTaskExecutor.executeOnBackgroundThread(paramString);
    return paramString.getOperation();
  }
  
  public Operation cancelUniqueWork(String paramString)
  {
    paramString = CancelWorkRunnable.forName(paramString, this, true);
    this.mWorkTaskExecutor.executeOnBackgroundThread(paramString);
    return paramString.getOperation();
  }
  
  public Operation cancelWorkById(UUID paramUUID)
  {
    paramUUID = CancelWorkRunnable.forId(paramUUID, this);
    this.mWorkTaskExecutor.executeOnBackgroundThread(paramUUID);
    return paramUUID.getOperation();
  }
  
  public PendingIntent createCancelPendingIntent(UUID paramUUID)
  {
    paramUUID = SystemForegroundDispatcher.createCancelWorkIntent(this.mContext, paramUUID.toString());
    int i = 134217728;
    if (BuildCompat.isAtLeastS()) {
      i = 0x8000000 | 0x2000000;
    }
    return PendingIntent.getService(this.mContext, 0, paramUUID, i);
  }
  
  public List<Scheduler> createSchedulers(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor)
  {
    return Arrays.asList(new Scheduler[] { Schedulers.createBestAvailableBackgroundScheduler(paramContext, this), new GreedyScheduler(paramContext, paramConfiguration, paramTaskExecutor, this) });
  }
  
  public WorkContinuationImpl createWorkContinuationForUniquePeriodicWork(String paramString, ExistingPeriodicWorkPolicy paramExistingPeriodicWorkPolicy, PeriodicWorkRequest paramPeriodicWorkRequest)
  {
    if (paramExistingPeriodicWorkPolicy == ExistingPeriodicWorkPolicy.KEEP) {
      paramExistingPeriodicWorkPolicy = ExistingWorkPolicy.KEEP;
    } else {
      paramExistingPeriodicWorkPolicy = ExistingWorkPolicy.REPLACE;
    }
    return new WorkContinuationImpl(this, paramString, paramExistingPeriodicWorkPolicy, Collections.singletonList(paramPeriodicWorkRequest));
  }
  
  public Operation enqueue(List<? extends WorkRequest> paramList)
  {
    if (!paramList.isEmpty()) {
      return new WorkContinuationImpl(this, paramList).enqueue();
    }
    throw new IllegalArgumentException("enqueue needs at least one WorkRequest.");
  }
  
  public Operation enqueueUniquePeriodicWork(String paramString, ExistingPeriodicWorkPolicy paramExistingPeriodicWorkPolicy, PeriodicWorkRequest paramPeriodicWorkRequest)
  {
    return createWorkContinuationForUniquePeriodicWork(paramString, paramExistingPeriodicWorkPolicy, paramPeriodicWorkRequest).enqueue();
  }
  
  public Operation enqueueUniqueWork(String paramString, ExistingWorkPolicy paramExistingWorkPolicy, List<OneTimeWorkRequest> paramList)
  {
    return new WorkContinuationImpl(this, paramString, paramExistingWorkPolicy, paramList).enqueue();
  }
  
  public Context getApplicationContext()
  {
    return this.mContext;
  }
  
  public Configuration getConfiguration()
  {
    return this.mConfiguration;
  }
  
  public ListenableFuture<Long> getLastCancelAllTimeMillis()
  {
    final SettableFuture localSettableFuture = SettableFuture.create();
    final PreferenceUtils localPreferenceUtils = this.mPreferenceUtils;
    this.mWorkTaskExecutor.executeOnBackgroundThread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 23	androidx/work/impl/WorkManagerImpl$1:val$future	Landroidx/work/impl/utils/futures/SettableFuture;
        //   4: aload_0
        //   5: getfield 25	androidx/work/impl/WorkManagerImpl$1:val$preferenceUtils	Landroidx/work/impl/utils/PreferenceUtils;
        //   8: invokevirtual 35	androidx/work/impl/utils/PreferenceUtils:getLastCancelAllTimeMillis	()J
        //   11: invokestatic 41	java/lang/Long:valueOf	(J)Ljava/lang/Long;
        //   14: invokevirtual 47	androidx/work/impl/utils/futures/SettableFuture:set	(Ljava/lang/Object;)Z
        //   17: pop
        //   18: goto +13 -> 31
        //   21: astore_1
        //   22: aload_0
        //   23: getfield 23	androidx/work/impl/WorkManagerImpl$1:val$future	Landroidx/work/impl/utils/futures/SettableFuture;
        //   26: aload_1
        //   27: invokevirtual 51	androidx/work/impl/utils/futures/SettableFuture:setException	(Ljava/lang/Throwable;)Z
        //   30: pop
        //   31: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	32	0	this	1
        //   21	6	1	localThrowable	Throwable
        // Exception table:
        //   from	to	target	type
        //   0	18	21	finally
      }
    });
    return localSettableFuture;
  }
  
  public LiveData<Long> getLastCancelAllTimeMillisLiveData()
  {
    return this.mPreferenceUtils.getLastCancelAllTimeMillisLiveData();
  }
  
  public PreferenceUtils getPreferenceUtils()
  {
    return this.mPreferenceUtils;
  }
  
  public Processor getProcessor()
  {
    return this.mProcessor;
  }
  
  public RemoteWorkManager getRemoteWorkManager()
  {
    if (this.mRemoteWorkManager == null) {
      synchronized (sLock)
      {
        if (this.mRemoteWorkManager == null)
        {
          tryInitializeMultiProcessSupport();
          if ((this.mRemoteWorkManager == null) && (!TextUtils.isEmpty(this.mConfiguration.getDefaultProcessName())))
          {
            IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
            localIllegalStateException.<init>("Invalid multiprocess configuration. Define an `implementation` dependency on :work:work-multiprocess library");
            throw localIllegalStateException;
          }
        }
      }
    }
    return this.mRemoteWorkManager;
  }
  
  public List<Scheduler> getSchedulers()
  {
    return this.mSchedulers;
  }
  
  public WorkDatabase getWorkDatabase()
  {
    return this.mWorkDatabase;
  }
  
  public ListenableFuture<WorkInfo> getWorkInfoById(UUID paramUUID)
  {
    paramUUID = StatusRunnable.forUUID(this, paramUUID);
    this.mWorkTaskExecutor.getBackgroundExecutor().execute(paramUUID);
    return paramUUID.getFuture();
  }
  
  public LiveData<WorkInfo> getWorkInfoByIdLiveData(UUID paramUUID)
  {
    WorkSpecDao localWorkSpecDao = this.mWorkDatabase.workSpecDao();
    LiveDataUtils.dedupedMappedLiveDataFor(localWorkSpecDao.getWorkStatusPojoLiveDataForIds(Collections.singletonList(paramUUID.toString())), new Function()
    {
      public WorkInfo apply(List<WorkSpec.WorkInfoPojo> paramAnonymousList)
      {
        Object localObject2 = null;
        Object localObject1 = localObject2;
        if (paramAnonymousList != null)
        {
          localObject1 = localObject2;
          if (paramAnonymousList.size() > 0) {
            localObject1 = ((WorkSpec.WorkInfoPojo)paramAnonymousList.get(0)).toWorkInfo();
          }
        }
        return (WorkInfo)localObject1;
      }
    }, this.mWorkTaskExecutor);
  }
  
  public ListenableFuture<List<WorkInfo>> getWorkInfos(WorkQuery paramWorkQuery)
  {
    paramWorkQuery = StatusRunnable.forWorkQuerySpec(this, paramWorkQuery);
    this.mWorkTaskExecutor.getBackgroundExecutor().execute(paramWorkQuery);
    return paramWorkQuery.getFuture();
  }
  
  LiveData<List<WorkInfo>> getWorkInfosById(List<String> paramList)
  {
    WorkSpecDao localWorkSpecDao = this.mWorkDatabase.workSpecDao();
    return LiveDataUtils.dedupedMappedLiveDataFor(localWorkSpecDao.getWorkStatusPojoLiveDataForIds(paramList), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
  }
  
  public ListenableFuture<List<WorkInfo>> getWorkInfosByTag(String paramString)
  {
    paramString = StatusRunnable.forTag(this, paramString);
    this.mWorkTaskExecutor.getBackgroundExecutor().execute(paramString);
    return paramString.getFuture();
  }
  
  public LiveData<List<WorkInfo>> getWorkInfosByTagLiveData(String paramString)
  {
    WorkSpecDao localWorkSpecDao = this.mWorkDatabase.workSpecDao();
    return LiveDataUtils.dedupedMappedLiveDataFor(localWorkSpecDao.getWorkStatusPojoLiveDataForTag(paramString), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
  }
  
  public ListenableFuture<List<WorkInfo>> getWorkInfosForUniqueWork(String paramString)
  {
    paramString = StatusRunnable.forUniqueWork(this, paramString);
    this.mWorkTaskExecutor.getBackgroundExecutor().execute(paramString);
    return paramString.getFuture();
  }
  
  public LiveData<List<WorkInfo>> getWorkInfosForUniqueWorkLiveData(String paramString)
  {
    WorkSpecDao localWorkSpecDao = this.mWorkDatabase.workSpecDao();
    return LiveDataUtils.dedupedMappedLiveDataFor(localWorkSpecDao.getWorkStatusPojoLiveDataForName(paramString), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
  }
  
  public LiveData<List<WorkInfo>> getWorkInfosLiveData(WorkQuery paramWorkQuery)
  {
    RawWorkInfoDao localRawWorkInfoDao = this.mWorkDatabase.rawWorkInfoDao();
    return LiveDataUtils.dedupedMappedLiveDataFor(localRawWorkInfoDao.getWorkInfoPojosLiveData(RawQueries.workQueryToRawQuery(paramWorkQuery)), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
  }
  
  public TaskExecutor getWorkTaskExecutor()
  {
    return this.mWorkTaskExecutor;
  }
  
  public void onForceStopRunnableCompleted()
  {
    synchronized (sLock)
    {
      this.mForceStopRunnableCompleted = true;
      BroadcastReceiver.PendingResult localPendingResult = this.mRescheduleReceiverResult;
      if (localPendingResult != null)
      {
        localPendingResult.finish();
        this.mRescheduleReceiverResult = null;
      }
      return;
    }
  }
  
  public Operation pruneWork()
  {
    PruneWorkRunnable localPruneWorkRunnable = new PruneWorkRunnable(this);
    this.mWorkTaskExecutor.executeOnBackgroundThread(localPruneWorkRunnable);
    return localPruneWorkRunnable.getOperation();
  }
  
  public void rescheduleEligibleWork()
  {
    if (Build.VERSION.SDK_INT >= 23) {
      SystemJobScheduler.cancelAll(getApplicationContext());
    }
    getWorkDatabase().workSpecDao().resetScheduledState();
    Schedulers.schedule(getConfiguration(), getWorkDatabase(), getSchedulers());
  }
  
  public void setReschedulePendingResult(BroadcastReceiver.PendingResult paramPendingResult)
  {
    synchronized (sLock)
    {
      this.mRescheduleReceiverResult = paramPendingResult;
      if (this.mForceStopRunnableCompleted)
      {
        paramPendingResult.finish();
        this.mRescheduleReceiverResult = null;
      }
      return;
    }
  }
  
  public void startWork(String paramString)
  {
    startWork(paramString, null);
  }
  
  public void startWork(String paramString, WorkerParameters.RuntimeExtras paramRuntimeExtras)
  {
    this.mWorkTaskExecutor.executeOnBackgroundThread(new StartWorkRunnable(this, paramString, paramRuntimeExtras));
  }
  
  public void stopForegroundWork(String paramString)
  {
    this.mWorkTaskExecutor.executeOnBackgroundThread(new StopWorkRunnable(this, paramString, true));
  }
  
  public void stopWork(String paramString)
  {
    this.mWorkTaskExecutor.executeOnBackgroundThread(new StopWorkRunnable(this, paramString, false));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/WorkManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */