package androidx.work.impl;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.arch.core.util.Function;
import androidx.core.os.BuildCompat;
import androidx.lifecycle.LiveData;
import androidx.work.Configuration;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.Logger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.R;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkQuery;
import androidx.work.WorkRequest;
import androidx.work.WorkerParameters;
import androidx.work.impl.background.greedy.GreedyScheduler;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.CancelWorkRunnable;
import androidx.work.impl.utils.ForceStopRunnable;
import androidx.work.impl.utils.LiveDataUtils;
import androidx.work.impl.utils.PreferenceUtils;
import androidx.work.impl.utils.PruneWorkRunnable;
import androidx.work.impl.utils.RawQueries;
import androidx.work.impl.utils.StartWorkRunnable;
import androidx.work.impl.utils.StatusRunnable;
import androidx.work.impl.utils.StopWorkRunnable;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import androidx.work.multiprocess.RemoteWorkManager;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 00AF.java */
/* loaded from: classes.dex */
public class WorkManagerImpl extends WorkManager {
    public static final int MAX_PRE_JOB_SCHEDULER_API_LEVEL = 22;
    public static final int MIN_JOB_SCHEDULER_API_LEVEL = 23;
    public static final String REMOTE_WORK_MANAGER_CLIENT = "androidx.work.multiprocess.RemoteWorkManagerClient";
    private static final String TAG;
    private static WorkManagerImpl sDefaultInstance;
    private static WorkManagerImpl sDelegatedInstance;
    private static final Object sLock;
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

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    static {
        String tagWithPrefix = Logger.tagWithPrefix("WorkManagerImpl");
        Log5ECF72.a(tagWithPrefix);
        LogE84000.a(tagWithPrefix);
        Log229316.a(tagWithPrefix);
        TAG = tagWithPrefix;
        sDelegatedInstance = null;
        sDefaultInstance = null;
        sLock = new Object();
    }

    public WorkManagerImpl(Context context, Configuration configuration, TaskExecutor workTaskExecutor) {
        this(context, configuration, workTaskExecutor, context.getResources().getBoolean(R.bool.workmanager_test_configuration));
    }

    public WorkManagerImpl(Context context, Configuration configuration, TaskExecutor workTaskExecutor, WorkDatabase database) {
        Context applicationContext = context.getApplicationContext();
        Logger.setLogger(new Logger.LogcatLogger(configuration.getMinimumLoggingLevel()));
        List<Scheduler> createSchedulers = createSchedulers(applicationContext, configuration, workTaskExecutor);
        internalInit(context, configuration, workTaskExecutor, database, createSchedulers, new Processor(context, configuration, workTaskExecutor, database, createSchedulers));
    }

    public WorkManagerImpl(Context context, Configuration configuration, TaskExecutor workTaskExecutor, WorkDatabase workDatabase, List<Scheduler> schedulers, Processor processor) {
        internalInit(context, configuration, workTaskExecutor, workDatabase, schedulers, processor);
    }

    public WorkManagerImpl(Context context, Configuration configuration, TaskExecutor workTaskExecutor, boolean useTestDatabase) {
        this(context, configuration, workTaskExecutor, WorkDatabase.create(context.getApplicationContext(), workTaskExecutor.getBackgroundExecutor(), useTestDatabase));
    }

    @Deprecated
    public static WorkManagerImpl getInstance() {
        synchronized (sLock) {
            WorkManagerImpl workManagerImpl = sDelegatedInstance;
            if (workManagerImpl != null) {
                return workManagerImpl;
            }
            return sDefaultInstance;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static WorkManagerImpl getInstance(Context context) {
        WorkManagerImpl workManagerImpl;
        synchronized (sLock) {
            workManagerImpl = getInstance();
            if (workManagerImpl == null) {
                Context applicationContext = context.getApplicationContext();
                if (!(applicationContext instanceof Configuration.Provider)) {
                    throw new IllegalStateException("WorkManager is not initialized properly.  You have explicitly disabled WorkManagerInitializer in your manifest, have not manually called WorkManager#initialize at this point, and your Application does not implement Configuration.Provider.");
                }
                initialize(applicationContext, ((Configuration.Provider) applicationContext).getWorkManagerConfiguration());
                workManagerImpl = getInstance(applicationContext);
            }
        }
        return workManagerImpl;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0016, code lost:
    
        r1 = r4.getApplicationContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x001d, code lost:
    
        if (androidx.work.impl.WorkManagerImpl.sDefaultInstance != null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x001f, code lost:
    
        androidx.work.impl.WorkManagerImpl.sDefaultInstance = new androidx.work.impl.WorkManagerImpl(r1, r5, new androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor(r5.getTaskExecutor()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
    
        androidx.work.impl.WorkManagerImpl.sDelegatedInstance = androidx.work.impl.WorkManagerImpl.sDefaultInstance;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void initialize(android.content.Context r4, androidx.work.Configuration r5) {
        /*
            java.lang.Object r0 = androidx.work.impl.WorkManagerImpl.sLock
            monitor-enter(r0)
            androidx.work.impl.WorkManagerImpl r1 = androidx.work.impl.WorkManagerImpl.sDelegatedInstance     // Catch: java.lang.Throwable -> L35
            if (r1 == 0) goto L14
            androidx.work.impl.WorkManagerImpl r2 = androidx.work.impl.WorkManagerImpl.sDefaultInstance     // Catch: java.lang.Throwable -> L35
            if (r2 != 0) goto Lc
            goto L14
        Lc:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L35
            java.lang.String r2 = "WorkManager is already initialized.  Did you try to initialize it manually without disabling WorkManagerInitializer? See WorkManager#initialize(Context, Configuration) or the class level Javadoc for more information."
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L35
            throw r1     // Catch: java.lang.Throwable -> L35
        L14:
            if (r1 != 0) goto L33
            android.content.Context r1 = r4.getApplicationContext()     // Catch: java.lang.Throwable -> L35
            r4 = r1
            androidx.work.impl.WorkManagerImpl r1 = androidx.work.impl.WorkManagerImpl.sDefaultInstance     // Catch: java.lang.Throwable -> L35
            if (r1 != 0) goto L2f
            androidx.work.impl.WorkManagerImpl r1 = new androidx.work.impl.WorkManagerImpl     // Catch: java.lang.Throwable -> L35
            androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor r2 = new androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor     // Catch: java.lang.Throwable -> L35
            java.util.concurrent.Executor r3 = r5.getTaskExecutor()     // Catch: java.lang.Throwable -> L35
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L35
            r1.<init>(r4, r5, r2)     // Catch: java.lang.Throwable -> L35
            androidx.work.impl.WorkManagerImpl.sDefaultInstance = r1     // Catch: java.lang.Throwable -> L35
        L2f:
            androidx.work.impl.WorkManagerImpl r1 = androidx.work.impl.WorkManagerImpl.sDefaultInstance     // Catch: java.lang.Throwable -> L35
            androidx.work.impl.WorkManagerImpl.sDelegatedInstance = r1     // Catch: java.lang.Throwable -> L35
        L33:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L35
            return
        L35:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L35
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.WorkManagerImpl.initialize(android.content.Context, androidx.work.Configuration):void");
    }

    private void internalInit(Context context, Configuration configuration, TaskExecutor workTaskExecutor, WorkDatabase workDatabase, List<Scheduler> schedulers, Processor processor) {
        Context context2 = context.getApplicationContext();
        this.mContext = context2;
        this.mConfiguration = configuration;
        this.mWorkTaskExecutor = workTaskExecutor;
        this.mWorkDatabase = workDatabase;
        this.mSchedulers = schedulers;
        this.mProcessor = processor;
        this.mPreferenceUtils = new PreferenceUtils(workDatabase);
        this.mForceStopRunnableCompleted = false;
        if (Build.VERSION.SDK_INT >= 24 && context2.isDeviceProtectedStorage()) {
            throw new IllegalStateException("Cannot initialize WorkManager in direct boot mode");
        }
        this.mWorkTaskExecutor.executeOnBackgroundThread(new ForceStopRunnable(context2, this));
    }

    public static void setDelegate(WorkManagerImpl delegate) {
        synchronized (sLock) {
            sDelegatedInstance = delegate;
        }
    }

    private void tryInitializeMultiProcessSupport() {
        try {
            this.mRemoteWorkManager = (RemoteWorkManager) Class.forName(REMOTE_WORK_MANAGER_CLIENT).getConstructor(Context.class, WorkManagerImpl.class).newInstance(this.mContext, this);
        } catch (Throwable th) {
            Logger.get().debug(TAG, "Unable to initialize multi-process support", th);
        }
    }

    @Override // androidx.work.WorkManager
    public WorkContinuation beginUniqueWork(String uniqueWorkName, ExistingWorkPolicy existingWorkPolicy, List<OneTimeWorkRequest> work) {
        if (work.isEmpty()) {
            throw new IllegalArgumentException("beginUniqueWork needs at least one OneTimeWorkRequest.");
        }
        return new WorkContinuationImpl(this, uniqueWorkName, existingWorkPolicy, work);
    }

    @Override // androidx.work.WorkManager
    public WorkContinuation beginWith(List<OneTimeWorkRequest> work) {
        if (work.isEmpty()) {
            throw new IllegalArgumentException("beginWith needs at least one OneTimeWorkRequest.");
        }
        return new WorkContinuationImpl(this, work);
    }

    @Override // androidx.work.WorkManager
    public Operation cancelAllWork() {
        CancelWorkRunnable forAll = CancelWorkRunnable.forAll(this);
        this.mWorkTaskExecutor.executeOnBackgroundThread(forAll);
        return forAll.getOperation();
    }

    @Override // androidx.work.WorkManager
    public Operation cancelAllWorkByTag(final String tag) {
        CancelWorkRunnable forTag = CancelWorkRunnable.forTag(tag, this);
        this.mWorkTaskExecutor.executeOnBackgroundThread(forTag);
        return forTag.getOperation();
    }

    @Override // androidx.work.WorkManager
    public Operation cancelUniqueWork(String uniqueWorkName) {
        CancelWorkRunnable forName = CancelWorkRunnable.forName(uniqueWorkName, this, true);
        this.mWorkTaskExecutor.executeOnBackgroundThread(forName);
        return forName.getOperation();
    }

    @Override // androidx.work.WorkManager
    public Operation cancelWorkById(UUID id) {
        CancelWorkRunnable forId = CancelWorkRunnable.forId(id, this);
        this.mWorkTaskExecutor.executeOnBackgroundThread(forId);
        return forId.getOperation();
    }

    @Override // androidx.work.WorkManager
    public PendingIntent createCancelPendingIntent(UUID id) {
        return PendingIntent.getService(this.mContext, 0, SystemForegroundDispatcher.createCancelWorkIntent(this.mContext, id.toString()), BuildCompat.isAtLeastS() ? 134217728 | 33554432 : 134217728);
    }

    public List<Scheduler> createSchedulers(Context context, Configuration configuration, TaskExecutor taskExecutor) {
        return Arrays.asList(Schedulers.createBestAvailableBackgroundScheduler(context, this), new GreedyScheduler(context, configuration, taskExecutor, this));
    }

    public WorkContinuationImpl createWorkContinuationForUniquePeriodicWork(String uniqueWorkName, ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy, PeriodicWorkRequest periodicWork) {
        return new WorkContinuationImpl(this, uniqueWorkName, existingPeriodicWorkPolicy == ExistingPeriodicWorkPolicy.KEEP ? ExistingWorkPolicy.KEEP : ExistingWorkPolicy.REPLACE, Collections.singletonList(periodicWork));
    }

    @Override // androidx.work.WorkManager
    public Operation enqueue(List<? extends WorkRequest> requests) {
        if (requests.isEmpty()) {
            throw new IllegalArgumentException("enqueue needs at least one WorkRequest.");
        }
        return new WorkContinuationImpl(this, requests).enqueue();
    }

    @Override // androidx.work.WorkManager
    public Operation enqueueUniquePeriodicWork(String uniqueWorkName, ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy, PeriodicWorkRequest periodicWork) {
        return createWorkContinuationForUniquePeriodicWork(uniqueWorkName, existingPeriodicWorkPolicy, periodicWork).enqueue();
    }

    @Override // androidx.work.WorkManager
    public Operation enqueueUniqueWork(String uniqueWorkName, ExistingWorkPolicy existingWorkPolicy, List<OneTimeWorkRequest> work) {
        return new WorkContinuationImpl(this, uniqueWorkName, existingWorkPolicy, work).enqueue();
    }

    public Context getApplicationContext() {
        return this.mContext;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    @Override // androidx.work.WorkManager
    public ListenableFuture<Long> getLastCancelAllTimeMillis() {
        final SettableFuture create = SettableFuture.create();
        final PreferenceUtils preferenceUtils = this.mPreferenceUtils;
        this.mWorkTaskExecutor.executeOnBackgroundThread(new Runnable() { // from class: androidx.work.impl.WorkManagerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    create.set(Long.valueOf(preferenceUtils.getLastCancelAllTimeMillis()));
                } catch (Throwable th) {
                    create.setException(th);
                }
            }
        });
        return create;
    }

    @Override // androidx.work.WorkManager
    public LiveData<Long> getLastCancelAllTimeMillisLiveData() {
        return this.mPreferenceUtils.getLastCancelAllTimeMillisLiveData();
    }

    public PreferenceUtils getPreferenceUtils() {
        return this.mPreferenceUtils;
    }

    public Processor getProcessor() {
        return this.mProcessor;
    }

    public RemoteWorkManager getRemoteWorkManager() {
        if (this.mRemoteWorkManager == null) {
            synchronized (sLock) {
                if (this.mRemoteWorkManager == null) {
                    tryInitializeMultiProcessSupport();
                    if (this.mRemoteWorkManager == null && !TextUtils.isEmpty(this.mConfiguration.getDefaultProcessName())) {
                        throw new IllegalStateException("Invalid multiprocess configuration. Define an `implementation` dependency on :work:work-multiprocess library");
                    }
                }
            }
        }
        return this.mRemoteWorkManager;
    }

    public List<Scheduler> getSchedulers() {
        return this.mSchedulers;
    }

    public WorkDatabase getWorkDatabase() {
        return this.mWorkDatabase;
    }

    @Override // androidx.work.WorkManager
    public ListenableFuture<WorkInfo> getWorkInfoById(UUID id) {
        StatusRunnable<WorkInfo> forUUID = StatusRunnable.forUUID(this, id);
        this.mWorkTaskExecutor.getBackgroundExecutor().execute(forUUID);
        return forUUID.getFuture();
    }

    @Override // androidx.work.WorkManager
    public LiveData<WorkInfo> getWorkInfoByIdLiveData(UUID id) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.mWorkDatabase.workSpecDao().getWorkStatusPojoLiveDataForIds(Collections.singletonList(id.toString())), new Function<List<WorkSpec.WorkInfoPojo>, WorkInfo>() { // from class: androidx.work.impl.WorkManagerImpl.2
            @Override // androidx.arch.core.util.Function
            public WorkInfo apply(List<WorkSpec.WorkInfoPojo> input) {
                if (input == null || input.size() <= 0) {
                    return null;
                }
                return input.get(0).toWorkInfo();
            }
        }, this.mWorkTaskExecutor);
    }

    @Override // androidx.work.WorkManager
    public ListenableFuture<List<WorkInfo>> getWorkInfos(WorkQuery workQuery) {
        StatusRunnable<List<WorkInfo>> forWorkQuerySpec = StatusRunnable.forWorkQuerySpec(this, workQuery);
        this.mWorkTaskExecutor.getBackgroundExecutor().execute(forWorkQuerySpec);
        return forWorkQuerySpec.getFuture();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LiveData<List<WorkInfo>> getWorkInfosById(List<String> workSpecIds) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.mWorkDatabase.workSpecDao().getWorkStatusPojoLiveDataForIds(workSpecIds), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
    }

    @Override // androidx.work.WorkManager
    public ListenableFuture<List<WorkInfo>> getWorkInfosByTag(String tag) {
        StatusRunnable<List<WorkInfo>> forTag = StatusRunnable.forTag(this, tag);
        this.mWorkTaskExecutor.getBackgroundExecutor().execute(forTag);
        return forTag.getFuture();
    }

    @Override // androidx.work.WorkManager
    public LiveData<List<WorkInfo>> getWorkInfosByTagLiveData(String tag) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.mWorkDatabase.workSpecDao().getWorkStatusPojoLiveDataForTag(tag), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
    }

    @Override // androidx.work.WorkManager
    public ListenableFuture<List<WorkInfo>> getWorkInfosForUniqueWork(String uniqueWorkName) {
        StatusRunnable<List<WorkInfo>> forUniqueWork = StatusRunnable.forUniqueWork(this, uniqueWorkName);
        this.mWorkTaskExecutor.getBackgroundExecutor().execute(forUniqueWork);
        return forUniqueWork.getFuture();
    }

    @Override // androidx.work.WorkManager
    public LiveData<List<WorkInfo>> getWorkInfosForUniqueWorkLiveData(String uniqueWorkName) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.mWorkDatabase.workSpecDao().getWorkStatusPojoLiveDataForName(uniqueWorkName), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
    }

    @Override // androidx.work.WorkManager
    public LiveData<List<WorkInfo>> getWorkInfosLiveData(WorkQuery workQuery) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.mWorkDatabase.rawWorkInfoDao().getWorkInfoPojosLiveData(RawQueries.workQueryToRawQuery(workQuery)), WorkSpec.WORK_INFO_MAPPER, this.mWorkTaskExecutor);
    }

    public TaskExecutor getWorkTaskExecutor() {
        return this.mWorkTaskExecutor;
    }

    public void onForceStopRunnableCompleted() {
        synchronized (sLock) {
            this.mForceStopRunnableCompleted = true;
            BroadcastReceiver.PendingResult pendingResult = this.mRescheduleReceiverResult;
            if (pendingResult != null) {
                pendingResult.finish();
                this.mRescheduleReceiverResult = null;
            }
        }
    }

    @Override // androidx.work.WorkManager
    public Operation pruneWork() {
        PruneWorkRunnable pruneWorkRunnable = new PruneWorkRunnable(this);
        this.mWorkTaskExecutor.executeOnBackgroundThread(pruneWorkRunnable);
        return pruneWorkRunnable.getOperation();
    }

    public void rescheduleEligibleWork() {
        if (Build.VERSION.SDK_INT >= 23) {
            SystemJobScheduler.cancelAll(getApplicationContext());
        }
        getWorkDatabase().workSpecDao().resetScheduledState();
        Schedulers.schedule(getConfiguration(), getWorkDatabase(), getSchedulers());
    }

    public void setReschedulePendingResult(BroadcastReceiver.PendingResult rescheduleReceiverResult) {
        synchronized (sLock) {
            this.mRescheduleReceiverResult = rescheduleReceiverResult;
            if (this.mForceStopRunnableCompleted) {
                rescheduleReceiverResult.finish();
                this.mRescheduleReceiverResult = null;
            }
        }
    }

    public void startWork(String workSpecId) {
        startWork(workSpecId, null);
    }

    public void startWork(String workSpecId, WorkerParameters.RuntimeExtras runtimeExtras) {
        this.mWorkTaskExecutor.executeOnBackgroundThread(new StartWorkRunnable(this, workSpecId, runtimeExtras));
    }

    public void stopForegroundWork(String workSpecId) {
        this.mWorkTaskExecutor.executeOnBackgroundThread(new StopWorkRunnable(this, workSpecId, true));
    }

    public void stopWork(String workSpecId) {
        this.mWorkTaskExecutor.executeOnBackgroundThread(new StopWorkRunnable(this, workSpecId, false));
    }
}
