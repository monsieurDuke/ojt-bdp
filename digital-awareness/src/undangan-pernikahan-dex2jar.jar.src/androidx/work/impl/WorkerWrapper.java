package androidx.work.impl;

import android.content.Context;
import androidx.work.Configuration;
import androidx.work.Data;
import androidx.work.InputMerger;
import androidx.work.InputMergerFactory;
import androidx.work.ListenableWorker;
import androidx.work.ListenableWorker.Result;
import androidx.work.ListenableWorker.Result.Failure;
import androidx.work.ListenableWorker.Result.Retry;
import androidx.work.ListenableWorker.Result.Success;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.WorkerFactory;
import androidx.work.WorkerParameters;
import androidx.work.WorkerParameters.RuntimeExtras;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.utils.PackageManagerHelper;
import androidx.work.impl.utils.WorkForegroundRunnable;
import androidx.work.impl.utils.WorkForegroundUpdater;
import androidx.work.impl.utils.WorkProgressUpdater;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WorkerWrapper
  implements Runnable
{
  static final String TAG;
  Context mAppContext;
  private Configuration mConfiguration;
  private DependencyDao mDependencyDao;
  private ForegroundProcessor mForegroundProcessor;
  SettableFuture<Boolean> mFuture = SettableFuture.create();
  ListenableFuture<ListenableWorker.Result> mInnerFuture = null;
  private volatile boolean mInterrupted;
  ListenableWorker.Result mResult = ListenableWorker.Result.failure();
  private WorkerParameters.RuntimeExtras mRuntimeExtras;
  private List<Scheduler> mSchedulers;
  private List<String> mTags;
  private WorkDatabase mWorkDatabase;
  private String mWorkDescription;
  WorkSpec mWorkSpec;
  private WorkSpecDao mWorkSpecDao;
  private String mWorkSpecId;
  private WorkTagDao mWorkTagDao;
  TaskExecutor mWorkTaskExecutor;
  ListenableWorker mWorker;
  
  static
  {
    String str = Logger.tagWithPrefix("WorkerWrapper");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  WorkerWrapper(Builder paramBuilder)
  {
    this.mAppContext = paramBuilder.mAppContext;
    this.mWorkTaskExecutor = paramBuilder.mWorkTaskExecutor;
    this.mForegroundProcessor = paramBuilder.mForegroundProcessor;
    this.mWorkSpecId = paramBuilder.mWorkSpecId;
    this.mSchedulers = paramBuilder.mSchedulers;
    this.mRuntimeExtras = paramBuilder.mRuntimeExtras;
    this.mWorker = paramBuilder.mWorker;
    this.mConfiguration = paramBuilder.mConfiguration;
    paramBuilder = paramBuilder.mWorkDatabase;
    this.mWorkDatabase = paramBuilder;
    this.mWorkSpecDao = paramBuilder.workSpecDao();
    this.mDependencyDao = this.mWorkDatabase.dependencyDao();
    this.mWorkTagDao = this.mWorkDatabase.workTagDao();
  }
  
  private String createWorkDescription(List<String> paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder("Work [ id=").append(this.mWorkSpecId).append(", tags={ ");
    int i = 1;
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      String str = (String)paramList.next();
      if (i != 0) {
        i = 0;
      } else {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append(str);
    }
    localStringBuilder.append(" } ]");
    return localStringBuilder.toString();
  }
  
  private void handleResult(ListenableWorker.Result paramResult)
  {
    Object localObject1;
    Object localObject2;
    if ((paramResult instanceof ListenableWorker.Result.Success))
    {
      paramResult = Logger.get();
      localObject1 = TAG;
      localObject2 = String.format("Worker result SUCCESS for %s", new Object[] { this.mWorkDescription });
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      paramResult.info((String)localObject1, (String)localObject2, new Throwable[0]);
      if (this.mWorkSpec.isPeriodic()) {
        resetPeriodicAndResolve();
      } else {
        setSucceededAndResolve();
      }
    }
    else if ((paramResult instanceof ListenableWorker.Result.Retry))
    {
      localObject2 = Logger.get();
      localObject1 = TAG;
      paramResult = String.format("Worker result RETRY for %s", new Object[] { this.mWorkDescription });
      Log5ECF72.a(paramResult);
      LogE84000.a(paramResult);
      Log229316.a(paramResult);
      ((Logger)localObject2).info((String)localObject1, paramResult, new Throwable[0]);
      rescheduleAndResolve();
    }
    else
    {
      localObject1 = Logger.get();
      paramResult = TAG;
      localObject2 = String.format("Worker result FAILURE for %s", new Object[] { this.mWorkDescription });
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      ((Logger)localObject1).info(paramResult, (String)localObject2, new Throwable[0]);
      if (this.mWorkSpec.isPeriodic()) {
        resetPeriodicAndResolve();
      } else {
        setFailedAndResolve();
      }
    }
  }
  
  private void iterativelyFailWorkAndDependents(String paramString)
  {
    LinkedList localLinkedList = new LinkedList();
    localLinkedList.add(paramString);
    while (!localLinkedList.isEmpty())
    {
      paramString = (String)localLinkedList.remove();
      if (this.mWorkSpecDao.getState(paramString) != WorkInfo.State.CANCELLED) {
        this.mWorkSpecDao.setState(WorkInfo.State.FAILED, new String[] { paramString });
      }
      localLinkedList.addAll(this.mDependencyDao.getDependentWorkIds(paramString));
    }
  }
  
  private void rescheduleAndResolve()
  {
    this.mWorkDatabase.beginTransaction();
    try
    {
      this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, new String[] { this.mWorkSpecId });
      this.mWorkSpecDao.setPeriodStartTime(this.mWorkSpecId, System.currentTimeMillis());
      this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1L);
      this.mWorkDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.mWorkDatabase.endTransaction();
      resolve(true);
    }
  }
  
  private void resetPeriodicAndResolve()
  {
    this.mWorkDatabase.beginTransaction();
    try
    {
      this.mWorkSpecDao.setPeriodStartTime(this.mWorkSpecId, System.currentTimeMillis());
      this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, new String[] { this.mWorkSpecId });
      this.mWorkSpecDao.resetWorkSpecRunAttemptCount(this.mWorkSpecId);
      this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1L);
      this.mWorkDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.mWorkDatabase.endTransaction();
      resolve(false);
    }
  }
  
  private void resolve(boolean paramBoolean)
  {
    this.mWorkDatabase.beginTransaction();
    try
    {
      if (!this.mWorkDatabase.workSpecDao().hasUnfinishedWork()) {
        PackageManagerHelper.setComponentEnabled(this.mAppContext, RescheduleReceiver.class, false);
      }
      if (paramBoolean)
      {
        this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, new String[] { this.mWorkSpecId });
        this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1L);
      }
      if (this.mWorkSpec != null)
      {
        ListenableWorker localListenableWorker = this.mWorker;
        if ((localListenableWorker != null) && (localListenableWorker.isRunInForeground())) {
          this.mForegroundProcessor.stopForeground(this.mWorkSpecId);
        }
      }
      this.mWorkDatabase.setTransactionSuccessful();
      this.mWorkDatabase.endTransaction();
      this.mFuture.set(Boolean.valueOf(paramBoolean));
      return;
    }
    finally
    {
      this.mWorkDatabase.endTransaction();
    }
  }
  
  private void resolveIncorrectStatus()
  {
    Object localObject = this.mWorkSpecDao.getState(this.mWorkSpecId);
    Logger localLogger;
    String str;
    if (localObject == WorkInfo.State.RUNNING)
    {
      localLogger = Logger.get();
      str = TAG;
      localObject = String.format("Status for %s is RUNNING;not doing any work and rescheduling for later execution", new Object[] { this.mWorkSpecId });
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localLogger.debug(str, (String)localObject, new Throwable[0]);
      resolve(true);
    }
    else
    {
      localLogger = Logger.get();
      str = TAG;
      localObject = String.format("Status for %s is %s; not doing any work", new Object[] { this.mWorkSpecId, localObject });
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localLogger.debug(str, (String)localObject, new Throwable[0]);
      resolve(false);
    }
  }
  
  private void runWorker()
  {
    if (tryCheckForInterruptionAndResolve()) {
      return;
    }
    this.mWorkDatabase.beginTransaction();
    try
    {
      final Object localObject1 = this.mWorkSpecDao.getWorkSpec(this.mWorkSpecId);
      this.mWorkSpec = ((WorkSpec)localObject1);
      Object localObject4;
      if (localObject1 == null)
      {
        localObject1 = Logger.get();
        localObject3 = TAG;
        localObject4 = String.format("Didn't find WorkSpec for id %s", new Object[] { this.mWorkSpecId });
        Log5ECF72.a(localObject4);
        LogE84000.a(localObject4);
        Log229316.a(localObject4);
        ((Logger)localObject1).error((String)localObject3, (String)localObject4, new Throwable[0]);
        resolve(false);
        this.mWorkDatabase.setTransactionSuccessful();
        return;
      }
      if (((WorkSpec)localObject1).state != WorkInfo.State.ENQUEUED)
      {
        resolveIncorrectStatus();
        this.mWorkDatabase.setTransactionSuccessful();
        localObject4 = Logger.get();
        localObject3 = TAG;
        localObject1 = String.format("%s is not in ENQUEUED state. Nothing more to do.", new Object[] { this.mWorkSpec.workerClassName });
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        ((Logger)localObject4).debug((String)localObject3, (String)localObject1, new Throwable[0]);
        return;
      }
      if ((this.mWorkSpec.isPeriodic()) || (this.mWorkSpec.isBackedOff()))
      {
        long l = System.currentTimeMillis();
        int i;
        if (this.mWorkSpec.periodStartTime == 0L) {
          i = 1;
        } else {
          i = 0;
        }
        if ((i == 0) && (l < this.mWorkSpec.calculateNextRunTime()))
        {
          localObject3 = Logger.get();
          localObject1 = TAG;
          localObject4 = String.format("Delaying execution for %s because it is being executed before schedule.", new Object[] { this.mWorkSpec.workerClassName });
          Log5ECF72.a(localObject4);
          LogE84000.a(localObject4);
          Log229316.a(localObject4);
          ((Logger)localObject3).debug((String)localObject1, (String)localObject4, new Throwable[0]);
          resolve(true);
          this.mWorkDatabase.setTransactionSuccessful();
          return;
        }
      }
      this.mWorkDatabase.setTransactionSuccessful();
      this.mWorkDatabase.endTransaction();
      if (this.mWorkSpec.isPeriodic())
      {
        localObject1 = this.mWorkSpec.input;
      }
      else
      {
        localObject1 = this.mConfiguration.getInputMergerFactory();
        localObject3 = this.mWorkSpec.inputMergerClassName;
        localObject1 = ((InputMergerFactory)localObject1).createInputMergerWithDefaultFallback((String)localObject3);
        if (localObject1 == null)
        {
          localObject1 = Logger.get();
          localObject4 = TAG;
          localObject3 = String.format("Could not create Input Merger %s", new Object[] { this.mWorkSpec.inputMergerClassName });
          Log5ECF72.a(localObject3);
          LogE84000.a(localObject3);
          Log229316.a(localObject3);
          ((Logger)localObject1).error((String)localObject4, (String)localObject3, new Throwable[0]);
          setFailedAndResolve();
          return;
        }
        localObject3 = new ArrayList();
        ((List)localObject3).add(this.mWorkSpec.input);
        ((List)localObject3).addAll(this.mWorkSpecDao.getInputsFromPrerequisites(this.mWorkSpecId));
        localObject1 = ((InputMerger)localObject1).merge((List)localObject3);
      }
      final Object localObject3 = new WorkerParameters(UUID.fromString(this.mWorkSpecId), (Data)localObject1, this.mTags, this.mRuntimeExtras, this.mWorkSpec.runAttemptCount, this.mConfiguration.getExecutor(), this.mWorkTaskExecutor, this.mConfiguration.getWorkerFactory(), new WorkProgressUpdater(this.mWorkDatabase, this.mWorkTaskExecutor), new WorkForegroundUpdater(this.mWorkDatabase, this.mForegroundProcessor, this.mWorkTaskExecutor));
      if (this.mWorker == null) {
        this.mWorker = this.mConfiguration.getWorkerFactory().createWorkerWithDefaultFallback(this.mAppContext, this.mWorkSpec.workerClassName, (WorkerParameters)localObject3);
      }
      localObject1 = this.mWorker;
      if (localObject1 == null)
      {
        localObject3 = Logger.get();
        localObject4 = TAG;
        localObject1 = String.format("Could not create Worker %s", new Object[] { this.mWorkSpec.workerClassName });
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        ((Logger)localObject3).error((String)localObject4, (String)localObject1, new Throwable[0]);
        setFailedAndResolve();
        return;
      }
      if (((ListenableWorker)localObject1).isUsed())
      {
        localObject3 = Logger.get();
        localObject4 = TAG;
        localObject1 = String.format("Received an already-used Worker %s; WorkerFactory should return new instances", new Object[] { this.mWorkSpec.workerClassName });
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        ((Logger)localObject3).error((String)localObject4, (String)localObject1, new Throwable[0]);
        setFailedAndResolve();
        return;
      }
      this.mWorker.setUsed();
      if (trySetRunning())
      {
        if (tryCheckForInterruptionAndResolve()) {
          return;
        }
        localObject1 = SettableFuture.create();
        localObject3 = new WorkForegroundRunnable(this.mAppContext, this.mWorkSpec, this.mWorker, ((WorkerParameters)localObject3).getForegroundUpdater(), this.mWorkTaskExecutor);
        this.mWorkTaskExecutor.getMainThreadExecutor().execute((Runnable)localObject3);
        localObject3 = ((WorkForegroundRunnable)localObject3).getFuture();
        ((ListenableFuture)localObject3).addListener(new Runnable()
        {
          public void run()
          {
            try
            {
              localObject3.get();
              Logger localLogger = Logger.get();
              Object localObject = WorkerWrapper.TAG;
              String str = String.format("Starting work for %s", new Object[] { WorkerWrapper.this.mWorkSpec.workerClassName });
              Log5ECF72.a(str);
              LogE84000.a(str);
              Log229316.a(str);
              localLogger.debug((String)localObject, str, new Throwable[0]);
              localObject = WorkerWrapper.this;
              ((WorkerWrapper)localObject).mInnerFuture = ((WorkerWrapper)localObject).mWorker.startWork();
            }
            finally
            {
              localObject1.setException(localThrowable);
            }
          }
        }, this.mWorkTaskExecutor.getMainThreadExecutor());
        ((SettableFuture)localObject1).addListener(new Runnable()
        {
          /* Error */
          public void run()
          {
            // Byte code:
            //   0: aload_0
            //   1: getfield 23	androidx/work/impl/WorkerWrapper$2:val$future	Landroidx/work/impl/utils/futures/SettableFuture;
            //   4: invokevirtual 41	androidx/work/impl/utils/futures/SettableFuture:get	()Ljava/lang/Object;
            //   7: checkcast 43	androidx/work/ListenableWorker$Result
            //   10: astore_1
            //   11: aload_1
            //   12: ifnonnull +59 -> 71
            //   15: invokestatic 48	androidx/work/Logger:get	()Landroidx/work/Logger;
            //   18: astore_2
            //   19: getstatic 51	androidx/work/impl/WorkerWrapper:TAG	Ljava/lang/String;
            //   22: astore_1
            //   23: ldc 53
            //   25: iconst_1
            //   26: anewarray 4	java/lang/Object
            //   29: dup
            //   30: iconst_0
            //   31: aload_0
            //   32: getfield 21	androidx/work/impl/WorkerWrapper$2:this$0	Landroidx/work/impl/WorkerWrapper;
            //   35: getfield 57	androidx/work/impl/WorkerWrapper:mWorkSpec	Landroidx/work/impl/model/WorkSpec;
            //   38: getfield 62	androidx/work/impl/model/WorkSpec:workerClassName	Ljava/lang/String;
            //   41: aastore
            //   42: invokestatic 68	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
            //   45: astore_3
            //   46: aload_3
            //   47: invokestatic 74	mt/Log5ECF72:a	(Ljava/lang/Object;)V
            //   50: aload_3
            //   51: invokestatic 77	mt/LogE84000:a	(Ljava/lang/Object;)V
            //   54: aload_3
            //   55: invokestatic 80	mt/Log229316:a	(Ljava/lang/Object;)V
            //   58: aload_2
            //   59: aload_1
            //   60: aload_3
            //   61: iconst_0
            //   62: anewarray 82	java/lang/Throwable
            //   65: invokevirtual 86	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
            //   68: goto +192 -> 260
            //   71: invokestatic 48	androidx/work/Logger:get	()Landroidx/work/Logger;
            //   74: astore 4
            //   76: getstatic 51	androidx/work/impl/WorkerWrapper:TAG	Ljava/lang/String;
            //   79: astore_2
            //   80: ldc 88
            //   82: iconst_2
            //   83: anewarray 4	java/lang/Object
            //   86: dup
            //   87: iconst_0
            //   88: aload_0
            //   89: getfield 21	androidx/work/impl/WorkerWrapper$2:this$0	Landroidx/work/impl/WorkerWrapper;
            //   92: getfield 57	androidx/work/impl/WorkerWrapper:mWorkSpec	Landroidx/work/impl/model/WorkSpec;
            //   95: getfield 62	androidx/work/impl/model/WorkSpec:workerClassName	Ljava/lang/String;
            //   98: aastore
            //   99: dup
            //   100: iconst_1
            //   101: aload_1
            //   102: aastore
            //   103: invokestatic 68	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
            //   106: astore_3
            //   107: aload_3
            //   108: invokestatic 74	mt/Log5ECF72:a	(Ljava/lang/Object;)V
            //   111: aload_3
            //   112: invokestatic 77	mt/LogE84000:a	(Ljava/lang/Object;)V
            //   115: aload_3
            //   116: invokestatic 80	mt/Log229316:a	(Ljava/lang/Object;)V
            //   119: aload 4
            //   121: aload_2
            //   122: aload_3
            //   123: iconst_0
            //   124: anewarray 82	java/lang/Throwable
            //   127: invokevirtual 91	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
            //   130: aload_0
            //   131: getfield 21	androidx/work/impl/WorkerWrapper$2:this$0	Landroidx/work/impl/WorkerWrapper;
            //   134: aload_1
            //   135: putfield 95	androidx/work/impl/WorkerWrapper:mResult	Landroidx/work/ListenableWorker$Result;
            //   138: goto +122 -> 260
            //   141: astore_1
            //   142: goto +126 -> 268
            //   145: astore_1
            //   146: goto +4 -> 150
            //   149: astore_1
            //   150: invokestatic 48	androidx/work/Logger:get	()Landroidx/work/Logger;
            //   153: astore 4
            //   155: getstatic 51	androidx/work/impl/WorkerWrapper:TAG	Ljava/lang/String;
            //   158: astore_2
            //   159: ldc 97
            //   161: iconst_1
            //   162: anewarray 4	java/lang/Object
            //   165: dup
            //   166: iconst_0
            //   167: aload_0
            //   168: getfield 25	androidx/work/impl/WorkerWrapper$2:val$workDescription	Ljava/lang/String;
            //   171: aastore
            //   172: invokestatic 68	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
            //   175: astore_3
            //   176: aload_3
            //   177: invokestatic 74	mt/Log5ECF72:a	(Ljava/lang/Object;)V
            //   180: aload_3
            //   181: invokestatic 77	mt/LogE84000:a	(Ljava/lang/Object;)V
            //   184: aload_3
            //   185: invokestatic 80	mt/Log229316:a	(Ljava/lang/Object;)V
            //   188: aload 4
            //   190: aload_2
            //   191: aload_3
            //   192: iconst_1
            //   193: anewarray 82	java/lang/Throwable
            //   196: dup
            //   197: iconst_0
            //   198: aload_1
            //   199: aastore
            //   200: invokevirtual 86	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
            //   203: goto +57 -> 260
            //   206: astore_2
            //   207: invokestatic 48	androidx/work/Logger:get	()Landroidx/work/Logger;
            //   210: astore_3
            //   211: getstatic 51	androidx/work/impl/WorkerWrapper:TAG	Ljava/lang/String;
            //   214: astore 4
            //   216: ldc 99
            //   218: iconst_1
            //   219: anewarray 4	java/lang/Object
            //   222: dup
            //   223: iconst_0
            //   224: aload_0
            //   225: getfield 25	androidx/work/impl/WorkerWrapper$2:val$workDescription	Ljava/lang/String;
            //   228: aastore
            //   229: invokestatic 68	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
            //   232: astore_1
            //   233: aload_1
            //   234: invokestatic 74	mt/Log5ECF72:a	(Ljava/lang/Object;)V
            //   237: aload_1
            //   238: invokestatic 77	mt/LogE84000:a	(Ljava/lang/Object;)V
            //   241: aload_1
            //   242: invokestatic 80	mt/Log229316:a	(Ljava/lang/Object;)V
            //   245: aload_3
            //   246: aload 4
            //   248: aload_1
            //   249: iconst_1
            //   250: anewarray 82	java/lang/Throwable
            //   253: dup
            //   254: iconst_0
            //   255: aload_2
            //   256: aastore
            //   257: invokevirtual 102	androidx/work/Logger:info	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
            //   260: aload_0
            //   261: getfield 21	androidx/work/impl/WorkerWrapper$2:this$0	Landroidx/work/impl/WorkerWrapper;
            //   264: invokevirtual 105	androidx/work/impl/WorkerWrapper:onWorkFinished	()V
            //   267: return
            //   268: aload_0
            //   269: getfield 21	androidx/work/impl/WorkerWrapper$2:this$0	Landroidx/work/impl/WorkerWrapper;
            //   272: invokevirtual 105	androidx/work/impl/WorkerWrapper:onWorkFinished	()V
            //   275: aload_1
            //   276: athrow
            // Local variable table:
            //   start	length	slot	name	signature
            //   0	277	0	this	2
            //   10	125	1	localObject1	Object
            //   141	1	1	localObject2	Object
            //   145	1	1	localExecutionException	java.util.concurrent.ExecutionException
            //   149	50	1	localInterruptedException	InterruptedException
            //   232	44	1	str	String
            //   18	173	2	localObject3	Object
            //   206	50	2	localCancellationException	java.util.concurrent.CancellationException
            //   45	201	3	localObject4	Object
            //   74	173	4	localObject5	Object
            // Exception table:
            //   from	to	target	type
            //   0	11	141	finally
            //   15	46	141	finally
            //   58	68	141	finally
            //   71	107	141	finally
            //   119	138	141	finally
            //   150	176	141	finally
            //   188	203	141	finally
            //   207	233	141	finally
            //   245	260	141	finally
            //   0	11	145	java/util/concurrent/ExecutionException
            //   15	46	145	java/util/concurrent/ExecutionException
            //   58	68	145	java/util/concurrent/ExecutionException
            //   71	107	145	java/util/concurrent/ExecutionException
            //   119	138	145	java/util/concurrent/ExecutionException
            //   0	11	149	java/lang/InterruptedException
            //   15	46	149	java/lang/InterruptedException
            //   58	68	149	java/lang/InterruptedException
            //   71	107	149	java/lang/InterruptedException
            //   119	138	149	java/lang/InterruptedException
            //   0	11	206	java/util/concurrent/CancellationException
            //   15	46	206	java/util/concurrent/CancellationException
            //   58	68	206	java/util/concurrent/CancellationException
            //   71	107	206	java/util/concurrent/CancellationException
            //   119	138	206	java/util/concurrent/CancellationException
          }
        }, this.mWorkTaskExecutor.getBackgroundExecutor());
      }
      else
      {
        resolveIncorrectStatus();
      }
      return;
    }
    finally
    {
      this.mWorkDatabase.endTransaction();
    }
  }
  
  private void setSucceededAndResolve()
  {
    this.mWorkDatabase.beginTransaction();
    try
    {
      this.mWorkSpecDao.setState(WorkInfo.State.SUCCEEDED, new String[] { this.mWorkSpecId });
      Object localObject1 = ((ListenableWorker.Result.Success)this.mResult).getOutputData();
      this.mWorkSpecDao.setOutput(this.mWorkSpecId, (Data)localObject1);
      long l = System.currentTimeMillis();
      localObject1 = this.mDependencyDao.getDependentWorkIds(this.mWorkSpecId).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        String str1 = (String)((Iterator)localObject1).next();
        if ((this.mWorkSpecDao.getState(str1) == WorkInfo.State.BLOCKED) && (this.mDependencyDao.hasCompletedAllPrerequisites(str1)))
        {
          Logger localLogger = Logger.get();
          String str2 = TAG;
          String str3 = String.format("Setting status to enqueued for %s", new Object[] { str1 });
          Log5ECF72.a(str3);
          LogE84000.a(str3);
          Log229316.a(str3);
          localLogger.info(str2, str3, new Throwable[0]);
          this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, new String[] { str1 });
          this.mWorkSpecDao.setPeriodStartTime(str1, l);
        }
      }
      this.mWorkDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.mWorkDatabase.endTransaction();
      resolve(false);
    }
  }
  
  private boolean tryCheckForInterruptionAndResolve()
  {
    if (this.mInterrupted)
    {
      Logger localLogger = Logger.get();
      String str = TAG;
      Object localObject = String.format("Work interrupted for %s", new Object[] { this.mWorkDescription });
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localLogger.debug(str, (String)localObject, new Throwable[0]);
      localObject = this.mWorkSpecDao.getState(this.mWorkSpecId);
      if (localObject == null) {
        resolve(false);
      } else {
        resolve(((WorkInfo.State)localObject).isFinished() ^ true);
      }
      return true;
    }
    return false;
  }
  
  private boolean trySetRunning()
  {
    boolean bool = false;
    this.mWorkDatabase.beginTransaction();
    try
    {
      if (this.mWorkSpecDao.getState(this.mWorkSpecId) == WorkInfo.State.ENQUEUED)
      {
        this.mWorkSpecDao.setState(WorkInfo.State.RUNNING, new String[] { this.mWorkSpecId });
        this.mWorkSpecDao.incrementWorkSpecRunAttemptCount(this.mWorkSpecId);
        bool = true;
      }
      this.mWorkDatabase.setTransactionSuccessful();
      return bool;
    }
    finally
    {
      this.mWorkDatabase.endTransaction();
    }
  }
  
  public ListenableFuture<Boolean> getFuture()
  {
    return this.mFuture;
  }
  
  public void interrupt()
  {
    this.mInterrupted = true;
    tryCheckForInterruptionAndResolve();
    boolean bool = false;
    Object localObject = this.mInnerFuture;
    if (localObject != null)
    {
      bool = ((ListenableFuture)localObject).isDone();
      this.mInnerFuture.cancel(true);
    }
    localObject = this.mWorker;
    if ((localObject != null) && (!bool))
    {
      ((ListenableWorker)localObject).stop();
    }
    else
    {
      localObject = String.format("WorkSpec %s is already done. Not interrupting.", new Object[] { this.mWorkSpec });
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      Logger.get().debug(TAG, (String)localObject, new Throwable[0]);
    }
  }
  
  void onWorkFinished()
  {
    if (!tryCheckForInterruptionAndResolve())
    {
      this.mWorkDatabase.beginTransaction();
      try
      {
        WorkInfo.State localState = this.mWorkSpecDao.getState(this.mWorkSpecId);
        this.mWorkDatabase.workProgressDao().delete(this.mWorkSpecId);
        if (localState == null) {
          resolve(false);
        } else if (localState == WorkInfo.State.RUNNING) {
          handleResult(this.mResult);
        } else if (!localState.isFinished()) {
          rescheduleAndResolve();
        }
        this.mWorkDatabase.setTransactionSuccessful();
      }
      finally
      {
        this.mWorkDatabase.endTransaction();
      }
    }
    Object localObject2 = this.mSchedulers;
    if (localObject2 != null)
    {
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext()) {
        ((Scheduler)((Iterator)localObject2).next()).cancel(this.mWorkSpecId);
      }
      Schedulers.schedule(this.mConfiguration, this.mWorkDatabase, this.mSchedulers);
    }
  }
  
  public void run()
  {
    List localList = this.mWorkTagDao.getTagsForWorkSpecId(this.mWorkSpecId);
    this.mTags = localList;
    this.mWorkDescription = createWorkDescription(localList);
    runWorker();
  }
  
  void setFailedAndResolve()
  {
    this.mWorkDatabase.beginTransaction();
    try
    {
      iterativelyFailWorkAndDependents(this.mWorkSpecId);
      Data localData = ((ListenableWorker.Result.Failure)this.mResult).getOutputData();
      this.mWorkSpecDao.setOutput(this.mWorkSpecId, localData);
      this.mWorkDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.mWorkDatabase.endTransaction();
      resolve(false);
    }
  }
  
  public static class Builder
  {
    Context mAppContext;
    Configuration mConfiguration;
    ForegroundProcessor mForegroundProcessor;
    WorkerParameters.RuntimeExtras mRuntimeExtras = new WorkerParameters.RuntimeExtras();
    List<Scheduler> mSchedulers;
    WorkDatabase mWorkDatabase;
    String mWorkSpecId;
    TaskExecutor mWorkTaskExecutor;
    ListenableWorker mWorker;
    
    public Builder(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, ForegroundProcessor paramForegroundProcessor, WorkDatabase paramWorkDatabase, String paramString)
    {
      this.mAppContext = paramContext.getApplicationContext();
      this.mWorkTaskExecutor = paramTaskExecutor;
      this.mForegroundProcessor = paramForegroundProcessor;
      this.mConfiguration = paramConfiguration;
      this.mWorkDatabase = paramWorkDatabase;
      this.mWorkSpecId = paramString;
    }
    
    public WorkerWrapper build()
    {
      return new WorkerWrapper(this);
    }
    
    public Builder withRuntimeExtras(WorkerParameters.RuntimeExtras paramRuntimeExtras)
    {
      if (paramRuntimeExtras != null) {
        this.mRuntimeExtras = paramRuntimeExtras;
      }
      return this;
    }
    
    public Builder withSchedulers(List<Scheduler> paramList)
    {
      this.mSchedulers = paramList;
      return this;
    }
    
    public Builder withWorker(ListenableWorker paramListenableWorker)
    {
      this.mWorker = paramListenableWorker;
      return this;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/WorkerWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */