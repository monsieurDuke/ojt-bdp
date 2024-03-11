package androidx.work.impl.workers;

import android.content.Context;
import androidx.work.ListenableWorker;
import androidx.work.ListenableWorker.Result;
import androidx.work.Logger;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.Executor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ConstraintTrackingWorker
  extends ListenableWorker
  implements WorkConstraintsCallback
{
  public static final String ARGUMENT_CLASS_NAME = "androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME";
  private static final String TAG;
  volatile boolean mAreConstraintsUnmet;
  private ListenableWorker mDelegate;
  SettableFuture<ListenableWorker.Result> mFuture;
  final Object mLock;
  private WorkerParameters mWorkerParameters;
  
  static
  {
    String str = Logger.tagWithPrefix("ConstraintTrkngWrkr");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public ConstraintTrackingWorker(Context paramContext, WorkerParameters paramWorkerParameters)
  {
    super(paramContext, paramWorkerParameters);
    this.mWorkerParameters = paramWorkerParameters;
    this.mLock = new Object();
    this.mAreConstraintsUnmet = false;
    this.mFuture = SettableFuture.create();
  }
  
  public ListenableWorker getDelegate()
  {
    return this.mDelegate;
  }
  
  public TaskExecutor getTaskExecutor()
  {
    return WorkManagerImpl.getInstance(getApplicationContext()).getWorkTaskExecutor();
  }
  
  public WorkDatabase getWorkDatabase()
  {
    return WorkManagerImpl.getInstance(getApplicationContext()).getWorkDatabase();
  }
  
  public boolean isRunInForeground()
  {
    ListenableWorker localListenableWorker = this.mDelegate;
    boolean bool;
    if ((localListenableWorker != null) && (localListenableWorker.isRunInForeground())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onAllConstraintsMet(List<String> paramList) {}
  
  public void onAllConstraintsNotMet(List<String> paramList)
  {
    Logger localLogger = Logger.get();
    ??? = TAG;
    paramList = String.format("Constraints changed for %s", new Object[] { paramList });
    Log5ECF72.a(paramList);
    LogE84000.a(paramList);
    Log229316.a(paramList);
    localLogger.debug((String)???, paramList, new Throwable[0]);
    synchronized (this.mLock)
    {
      this.mAreConstraintsUnmet = true;
      return;
    }
  }
  
  public void onStopped()
  {
    super.onStopped();
    ListenableWorker localListenableWorker = this.mDelegate;
    if ((localListenableWorker != null) && (!localListenableWorker.isStopped())) {
      this.mDelegate.stop();
    }
  }
  
  void setFutureFailed()
  {
    this.mFuture.set(ListenableWorker.Result.failure());
  }
  
  void setFutureRetry()
  {
    this.mFuture.set(ListenableWorker.Result.retry());
  }
  
  /* Error */
  void setupAndRunConstraintTrackingWork()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 152	androidx/work/impl/workers/ConstraintTrackingWorker:getInputData	()Landroidx/work/Data;
    //   4: ldc 14
    //   6: invokevirtual 157	androidx/work/Data:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   9: astore_1
    //   10: aload_1
    //   11: invokestatic 163	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   14: ifeq +23 -> 37
    //   17: invokestatic 109	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   20: getstatic 50	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   23: ldc -91
    //   25: iconst_0
    //   26: anewarray 119	java/lang/Throwable
    //   29: invokevirtual 168	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   32: aload_0
    //   33: invokevirtual 170	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   36: return
    //   37: aload_0
    //   38: invokevirtual 174	androidx/work/impl/workers/ConstraintTrackingWorker:getWorkerFactory	()Landroidx/work/WorkerFactory;
    //   41: aload_0
    //   42: invokevirtual 83	androidx/work/impl/workers/ConstraintTrackingWorker:getApplicationContext	()Landroid/content/Context;
    //   45: aload_1
    //   46: aload_0
    //   47: getfield 57	androidx/work/impl/workers/ConstraintTrackingWorker:mWorkerParameters	Landroidx/work/WorkerParameters;
    //   50: invokevirtual 180	androidx/work/WorkerFactory:createWorkerWithDefaultFallback	(Landroid/content/Context;Ljava/lang/String;Landroidx/work/WorkerParameters;)Landroidx/work/ListenableWorker;
    //   53: astore_2
    //   54: aload_0
    //   55: aload_2
    //   56: putfield 77	androidx/work/impl/workers/ConstraintTrackingWorker:mDelegate	Landroidx/work/ListenableWorker;
    //   59: aload_2
    //   60: ifnonnull +23 -> 83
    //   63: invokestatic 109	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   66: getstatic 50	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   69: ldc -91
    //   71: iconst_0
    //   72: anewarray 119	java/lang/Throwable
    //   75: invokevirtual 123	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   78: aload_0
    //   79: invokevirtual 170	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   82: return
    //   83: aload_0
    //   84: invokevirtual 181	androidx/work/impl/workers/ConstraintTrackingWorker:getWorkDatabase	()Landroidx/work/impl/WorkDatabase;
    //   87: invokevirtual 187	androidx/work/impl/WorkDatabase:workSpecDao	()Landroidx/work/impl/model/WorkSpecDao;
    //   90: aload_0
    //   91: invokevirtual 191	androidx/work/impl/workers/ConstraintTrackingWorker:getId	()Ljava/util/UUID;
    //   94: invokevirtual 197	java/util/UUID:toString	()Ljava/lang/String;
    //   97: invokeinterface 203 2 0
    //   102: astore_2
    //   103: aload_2
    //   104: ifnonnull +8 -> 112
    //   107: aload_0
    //   108: invokevirtual 170	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   111: return
    //   112: new 205	androidx/work/impl/constraints/WorkConstraintsTracker
    //   115: dup
    //   116: aload_0
    //   117: invokevirtual 83	androidx/work/impl/workers/ConstraintTrackingWorker:getApplicationContext	()Landroid/content/Context;
    //   120: aload_0
    //   121: invokevirtual 207	androidx/work/impl/workers/ConstraintTrackingWorker:getTaskExecutor	()Landroidx/work/impl/utils/taskexecutor/TaskExecutor;
    //   124: aload_0
    //   125: invokespecial 210	androidx/work/impl/constraints/WorkConstraintsTracker:<init>	(Landroid/content/Context;Landroidx/work/impl/utils/taskexecutor/TaskExecutor;Landroidx/work/impl/constraints/WorkConstraintsCallback;)V
    //   128: astore_3
    //   129: aload_3
    //   130: aload_2
    //   131: invokestatic 216	java/util/Collections:singletonList	(Ljava/lang/Object;)Ljava/util/List;
    //   134: invokevirtual 220	androidx/work/impl/constraints/WorkConstraintsTracker:replace	(Ljava/lang/Iterable;)V
    //   137: aload_3
    //   138: aload_0
    //   139: invokevirtual 191	androidx/work/impl/workers/ConstraintTrackingWorker:getId	()Ljava/util/UUID;
    //   142: invokevirtual 197	java/util/UUID:toString	()Ljava/lang/String;
    //   145: invokevirtual 224	androidx/work/impl/constraints/WorkConstraintsTracker:areAllConstraintsMet	(Ljava/lang/String;)Z
    //   148: ifeq +180 -> 328
    //   151: invokestatic 109	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   154: astore 4
    //   156: getstatic 50	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   159: astore_2
    //   160: ldc -30
    //   162: iconst_1
    //   163: anewarray 59	java/lang/Object
    //   166: dup
    //   167: iconst_0
    //   168: aload_1
    //   169: aastore
    //   170: invokestatic 117	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   173: astore_3
    //   174: aload_3
    //   175: invokestatic 42	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   178: aload_3
    //   179: invokestatic 45	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   182: aload_3
    //   183: invokestatic 48	mt/Log229316:a	(Ljava/lang/Object;)V
    //   186: aload 4
    //   188: aload_2
    //   189: aload_3
    //   190: iconst_0
    //   191: anewarray 119	java/lang/Throwable
    //   194: invokevirtual 123	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   197: aload_0
    //   198: getfield 77	androidx/work/impl/workers/ConstraintTrackingWorker:mDelegate	Landroidx/work/ListenableWorker;
    //   201: invokevirtual 230	androidx/work/ListenableWorker:startWork	()Lcom/google/common/util/concurrent/ListenableFuture;
    //   204: astore_2
    //   205: new 10	androidx/work/impl/workers/ConstraintTrackingWorker$2
    //   208: astore_3
    //   209: aload_3
    //   210: aload_0
    //   211: aload_2
    //   212: invokespecial 233	androidx/work/impl/workers/ConstraintTrackingWorker$2:<init>	(Landroidx/work/impl/workers/ConstraintTrackingWorker;Lcom/google/common/util/concurrent/ListenableFuture;)V
    //   215: aload_2
    //   216: aload_3
    //   217: aload_0
    //   218: invokevirtual 237	androidx/work/impl/workers/ConstraintTrackingWorker:getBackgroundExecutor	()Ljava/util/concurrent/Executor;
    //   221: invokeinterface 243 3 0
    //   226: goto +150 -> 376
    //   229: astore_3
    //   230: invokestatic 109	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   233: astore 4
    //   235: getstatic 50	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   238: astore_2
    //   239: ldc -11
    //   241: iconst_1
    //   242: anewarray 59	java/lang/Object
    //   245: dup
    //   246: iconst_0
    //   247: aload_1
    //   248: aastore
    //   249: invokestatic 117	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   252: astore_1
    //   253: aload_1
    //   254: invokestatic 42	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   257: aload_1
    //   258: invokestatic 45	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   261: aload_1
    //   262: invokestatic 48	mt/Log229316:a	(Ljava/lang/Object;)V
    //   265: aload 4
    //   267: aload_2
    //   268: aload_1
    //   269: iconst_1
    //   270: anewarray 119	java/lang/Throwable
    //   273: dup
    //   274: iconst_0
    //   275: aload_3
    //   276: aastore
    //   277: invokevirtual 123	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   280: aload_0
    //   281: getfield 63	androidx/work/impl/workers/ConstraintTrackingWorker:mLock	Ljava/lang/Object;
    //   284: astore_1
    //   285: aload_1
    //   286: monitorenter
    //   287: aload_0
    //   288: getfield 65	androidx/work/impl/workers/ConstraintTrackingWorker:mAreConstraintsUnmet	Z
    //   291: ifeq +23 -> 314
    //   294: invokestatic 109	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   297: aload_2
    //   298: ldc -9
    //   300: iconst_0
    //   301: anewarray 119	java/lang/Throwable
    //   304: invokevirtual 123	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   307: aload_0
    //   308: invokevirtual 249	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureRetry	()V
    //   311: goto +7 -> 318
    //   314: aload_0
    //   315: invokevirtual 170	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   318: aload_1
    //   319: monitorexit
    //   320: goto -94 -> 226
    //   323: astore_2
    //   324: aload_1
    //   325: monitorexit
    //   326: aload_2
    //   327: athrow
    //   328: invokestatic 109	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   331: astore_3
    //   332: getstatic 50	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   335: astore_2
    //   336: ldc -5
    //   338: iconst_1
    //   339: anewarray 59	java/lang/Object
    //   342: dup
    //   343: iconst_0
    //   344: aload_1
    //   345: aastore
    //   346: invokestatic 117	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   349: astore_1
    //   350: aload_1
    //   351: invokestatic 42	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   354: aload_1
    //   355: invokestatic 45	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   358: aload_1
    //   359: invokestatic 48	mt/Log229316:a	(Ljava/lang/Object;)V
    //   362: aload_3
    //   363: aload_2
    //   364: aload_1
    //   365: iconst_0
    //   366: anewarray 119	java/lang/Throwable
    //   369: invokevirtual 123	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   372: aload_0
    //   373: invokevirtual 249	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureRetry	()V
    //   376: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	377	0	this	ConstraintTrackingWorker
    //   53	245	2	localObject2	Object
    //   323	4	2	localObject3	Object
    //   335	29	2	str	String
    //   128	89	3	localObject4	Object
    //   229	47	3	localObject5	Object
    //   331	32	3	localLogger1	Logger
    //   154	112	4	localLogger2	Logger
    // Exception table:
    //   from	to	target	type
    //   197	226	229	finally
    //   287	311	323	finally
    //   314	318	323	finally
    //   318	320	323	finally
    //   324	326	323	finally
  }
  
  public ListenableFuture<ListenableWorker.Result> startWork()
  {
    getBackgroundExecutor().execute(new Runnable()
    {
      public void run()
      {
        ConstraintTrackingWorker.this.setupAndRunConstraintTrackingWork();
      }
    });
    return this.mFuture;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/workers/ConstraintTrackingWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */