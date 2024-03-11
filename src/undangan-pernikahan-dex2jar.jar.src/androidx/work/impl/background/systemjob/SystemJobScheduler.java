package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo.State;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.IdGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class SystemJobScheduler
  implements Scheduler
{
  private static final String TAG;
  private final Context mContext;
  private final JobScheduler mJobScheduler;
  private final SystemJobInfoConverter mSystemJobInfoConverter;
  private final WorkManagerImpl mWorkManager;
  
  static
  {
    String str = Logger.tagWithPrefix("SystemJobScheduler");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public SystemJobScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl)
  {
    this(paramContext, paramWorkManagerImpl, (JobScheduler)paramContext.getSystemService("jobscheduler"), new SystemJobInfoConverter(paramContext));
  }
  
  public SystemJobScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl, JobScheduler paramJobScheduler, SystemJobInfoConverter paramSystemJobInfoConverter)
  {
    this.mContext = paramContext;
    this.mWorkManager = paramWorkManagerImpl;
    this.mJobScheduler = paramJobScheduler;
    this.mSystemJobInfoConverter = paramSystemJobInfoConverter;
  }
  
  public static void cancelAll(Context paramContext)
  {
    JobScheduler localJobScheduler = (JobScheduler)paramContext.getSystemService("jobscheduler");
    if (localJobScheduler != null)
    {
      paramContext = getPendingJobs(paramContext, localJobScheduler);
      if ((paramContext != null) && (!paramContext.isEmpty()))
      {
        paramContext = paramContext.iterator();
        while (paramContext.hasNext()) {
          cancelJobById(localJobScheduler, ((JobInfo)paramContext.next()).getId());
        }
      }
    }
  }
  
  /* Error */
  private static void cancelJobById(JobScheduler paramJobScheduler, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokevirtual 109	android/app/job/JobScheduler:cancel	(I)V
    //   5: goto +60 -> 65
    //   8: astore_3
    //   9: invokestatic 113	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   12: astore_0
    //   13: getstatic 40	androidx/work/impl/background/systemjob/SystemJobScheduler:TAG	Ljava/lang/String;
    //   16: astore 4
    //   18: invokestatic 119	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   21: ldc 121
    //   23: iconst_1
    //   24: anewarray 4	java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: iload_1
    //   30: invokestatic 127	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   33: aastore
    //   34: invokestatic 133	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   37: astore_2
    //   38: aload_2
    //   39: invokestatic 32	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   42: aload_2
    //   43: invokestatic 35	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   46: aload_2
    //   47: invokestatic 38	mt/Log229316:a	(Ljava/lang/Object;)V
    //   50: aload_0
    //   51: aload 4
    //   53: aload_2
    //   54: iconst_1
    //   55: anewarray 135	java/lang/Throwable
    //   58: dup
    //   59: iconst_0
    //   60: aload_3
    //   61: aastore
    //   62: invokevirtual 139	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   65: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	66	0	paramJobScheduler	JobScheduler
    //   0	66	1	paramInt	int
    //   37	17	2	str1	String
    //   8	53	3	localObject	Object
    //   16	36	4	str2	String
    // Exception table:
    //   from	to	target	type
    //   0	5	8	finally
  }
  
  private static List<Integer> getPendingJobIds(Context paramContext, JobScheduler paramJobScheduler, String paramString)
  {
    paramJobScheduler = getPendingJobs(paramContext, paramJobScheduler);
    if (paramJobScheduler == null) {
      return null;
    }
    paramContext = new ArrayList(2);
    paramJobScheduler = paramJobScheduler.iterator();
    while (paramJobScheduler.hasNext())
    {
      JobInfo localJobInfo = (JobInfo)paramJobScheduler.next();
      String str = getWorkSpecIdFromJobInfo(localJobInfo);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (paramString.equals(str)) {
        paramContext.add(Integer.valueOf(localJobInfo.getId()));
      }
    }
    return paramContext;
  }
  
  /* Error */
  private static List<JobInfo> getPendingJobs(Context paramContext, JobScheduler paramJobScheduler)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_1
    //   3: invokevirtual 163	android/app/job/JobScheduler:getAllPendingJobs	()Ljava/util/List;
    //   6: astore_1
    //   7: goto +25 -> 32
    //   10: astore_1
    //   11: invokestatic 113	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   14: getstatic 40	androidx/work/impl/background/systemjob/SystemJobScheduler:TAG	Ljava/lang/String;
    //   17: ldc -91
    //   19: iconst_1
    //   20: anewarray 135	java/lang/Throwable
    //   23: dup
    //   24: iconst_0
    //   25: aload_1
    //   26: aastore
    //   27: invokevirtual 139	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   30: aload_2
    //   31: astore_1
    //   32: aload_1
    //   33: ifnonnull +5 -> 38
    //   36: aconst_null
    //   37: areturn
    //   38: new 144	java/util/ArrayList
    //   41: dup
    //   42: aload_1
    //   43: invokeinterface 168 1 0
    //   48: invokespecial 146	java/util/ArrayList:<init>	(I)V
    //   51: astore_2
    //   52: new 170	android/content/ComponentName
    //   55: dup
    //   56: aload_0
    //   57: ldc -84
    //   59: invokespecial 175	android/content/ComponentName:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   62: astore_0
    //   63: aload_1
    //   64: invokeinterface 86 1 0
    //   69: astore_3
    //   70: aload_3
    //   71: invokeinterface 91 1 0
    //   76: ifeq +35 -> 111
    //   79: aload_3
    //   80: invokeinterface 95 1 0
    //   85: checkcast 97	android/app/job/JobInfo
    //   88: astore_1
    //   89: aload_0
    //   90: aload_1
    //   91: invokevirtual 179	android/app/job/JobInfo:getService	()Landroid/content/ComponentName;
    //   94: invokevirtual 180	android/content/ComponentName:equals	(Ljava/lang/Object;)Z
    //   97: ifeq +11 -> 108
    //   100: aload_2
    //   101: aload_1
    //   102: invokeinterface 157 2 0
    //   107: pop
    //   108: goto -38 -> 70
    //   111: aload_2
    //   112: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	113	0	paramContext	Context
    //   0	113	1	paramJobScheduler	JobScheduler
    //   1	111	2	localArrayList	ArrayList
    //   69	11	3	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   2	7	10	finally
  }
  
  private static String getWorkSpecIdFromJobInfo(JobInfo paramJobInfo)
  {
    paramJobInfo = paramJobInfo.getExtras();
    if (paramJobInfo != null) {
      try
      {
        if (paramJobInfo.containsKey("EXTRA_WORK_SPEC_ID"))
        {
          paramJobInfo = paramJobInfo.getString("EXTRA_WORK_SPEC_ID");
          return paramJobInfo;
        }
      }
      catch (NullPointerException paramJobInfo) {}
    }
    return null;
  }
  
  public static boolean reconcileJobs(Context paramContext, WorkManagerImpl paramWorkManagerImpl)
  {
    Object localObject2 = (JobScheduler)paramContext.getSystemService("jobscheduler");
    Object localObject3 = getPendingJobs(paramContext, (JobScheduler)localObject2);
    paramContext = paramWorkManagerImpl.getWorkDatabase().systemIdInfoDao().getWorkSpecIds();
    int i;
    if (localObject3 != null) {
      i = ((List)localObject3).size();
    } else {
      i = 0;
    }
    Object localObject1 = new HashSet(i);
    if ((localObject3 != null) && (!((List)localObject3).isEmpty()))
    {
      localObject3 = ((List)localObject3).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        JobInfo localJobInfo = (JobInfo)((Iterator)localObject3).next();
        String str = getWorkSpecIdFromJobInfo(localJobInfo);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        if (!TextUtils.isEmpty(str)) {
          ((Set)localObject1).add(str);
        } else {
          cancelJobById((JobScheduler)localObject2, localJobInfo.getId());
        }
      }
    }
    boolean bool2 = false;
    localObject2 = paramContext.iterator();
    boolean bool1;
    for (;;)
    {
      bool1 = bool2;
      if (!((Iterator)localObject2).hasNext()) {
        break;
      }
      if (!((Set)localObject1).contains((String)((Iterator)localObject2).next()))
      {
        Logger.get().debug(TAG, "Reconciling jobs", new Throwable[0]);
        bool1 = true;
        break;
      }
    }
    if (bool1)
    {
      paramWorkManagerImpl = paramWorkManagerImpl.getWorkDatabase();
      paramWorkManagerImpl.beginTransaction();
      try
      {
        localObject1 = paramWorkManagerImpl.workSpecDao();
        paramContext = paramContext.iterator();
        while (paramContext.hasNext()) {
          ((WorkSpecDao)localObject1).markWorkSpecScheduled((String)paramContext.next(), -1L);
        }
        paramWorkManagerImpl.setTransactionSuccessful();
      }
      finally
      {
        paramWorkManagerImpl.endTransaction();
      }
    }
    return bool1;
  }
  
  public void cancel(String paramString)
  {
    Object localObject = getPendingJobIds(this.mContext, this.mJobScheduler, paramString);
    if ((localObject != null) && (!((List)localObject).isEmpty()))
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        int i = ((Integer)((Iterator)localObject).next()).intValue();
        cancelJobById(this.mJobScheduler, i);
      }
      this.mWorkManager.getWorkDatabase().systemIdInfoDao().removeSystemIdInfo(paramString);
    }
  }
  
  public boolean hasLimitedSchedulingSlots()
  {
    return true;
  }
  
  public void schedule(WorkSpec... paramVarArgs)
  {
    WorkDatabase localWorkDatabase = this.mWorkManager.getWorkDatabase();
    IdGenerator localIdGenerator = new IdGenerator(localWorkDatabase);
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      WorkSpec localWorkSpec = paramVarArgs[i];
      localWorkDatabase.beginTransaction();
      try
      {
        Object localObject1 = localWorkDatabase.workSpecDao().getWorkSpec(localWorkSpec.id);
        Object localObject2;
        Object localObject3;
        if (localObject1 == null)
        {
          localObject2 = Logger.get();
          localObject1 = TAG;
          localObject3 = new java/lang/StringBuilder;
          ((StringBuilder)localObject3).<init>();
          ((Logger)localObject2).warning((String)localObject1, "Skipping scheduling " + localWorkSpec.id + " because it's no longer in the DB", new Throwable[0]);
          localWorkDatabase.setTransactionSuccessful();
          localWorkDatabase.endTransaction();
        }
        else if (((WorkSpec)localObject1).state != WorkInfo.State.ENQUEUED)
        {
          localObject3 = Logger.get();
          localObject1 = TAG;
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((Logger)localObject3).warning((String)localObject1, "Skipping scheduling " + localWorkSpec.id + " because it is no longer enqueued", new Throwable[0]);
          localWorkDatabase.setTransactionSuccessful();
          localWorkDatabase.endTransaction();
        }
        else
        {
          localObject1 = localWorkDatabase.systemIdInfoDao().getSystemIdInfo(localWorkSpec.id);
          int j;
          if (localObject1 != null) {
            j = ((SystemIdInfo)localObject1).systemId;
          } else {
            j = localIdGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
          }
          if (localObject1 == null)
          {
            localObject1 = new androidx/work/impl/model/SystemIdInfo;
            ((SystemIdInfo)localObject1).<init>(localWorkSpec.id, j);
            this.mWorkManager.getWorkDatabase().systemIdInfoDao().insertSystemIdInfo((SystemIdInfo)localObject1);
          }
          scheduleInternal(localWorkSpec, j);
          if (Build.VERSION.SDK_INT == 23)
          {
            localObject1 = getPendingJobIds(this.mContext, this.mJobScheduler, localWorkSpec.id);
            if (localObject1 != null)
            {
              j = ((List)localObject1).indexOf(Integer.valueOf(j));
              if (j >= 0) {
                ((List)localObject1).remove(j);
              }
              if (!((List)localObject1).isEmpty()) {
                j = ((Integer)((List)localObject1).get(0)).intValue();
              } else {
                j = localIdGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
              }
              scheduleInternal(localWorkSpec, j);
            }
          }
          localWorkDatabase.setTransactionSuccessful();
          localWorkDatabase.endTransaction();
        }
        i++;
      }
      finally
      {
        localWorkDatabase.endTransaction();
      }
    }
  }
  
  public void scheduleInternal(WorkSpec paramWorkSpec, int paramInt)
  {
    Object localObject4 = this.mSystemJobInfoConverter.convert(paramWorkSpec, paramInt);
    Object localObject2 = Logger.get();
    Object localObject1 = TAG;
    String str = String.format("Scheduling work ID %s Job ID %s", new Object[] { paramWorkSpec.id, Integer.valueOf(paramInt) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    ((Logger)localObject2).debug((String)localObject1, str, new Throwable[0]);
    try
    {
      try
      {
        if (this.mJobScheduler.schedule((JobInfo)localObject4) == 0)
        {
          localObject2 = Logger.get();
          localObject4 = String.format("Unable to schedule work ID %s", new Object[] { paramWorkSpec.id });
          Log5ECF72.a(localObject4);
          LogE84000.a(localObject4);
          Log229316.a(localObject4);
          ((Logger)localObject2).warning((String)localObject1, (String)localObject4, new Throwable[0]);
          if ((paramWorkSpec.expedited) && (paramWorkSpec.outOfQuotaPolicy == OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST))
          {
            paramWorkSpec.expedited = false;
            localObject2 = String.format("Scheduling a non-expedited job (work ID %s)", new Object[] { paramWorkSpec.id });
            Log5ECF72.a(localObject2);
            LogE84000.a(localObject2);
            Log229316.a(localObject2);
            Logger.get().debug((String)localObject1, (String)localObject2, new Throwable[0]);
            scheduleInternal(paramWorkSpec, paramInt);
          }
        }
      }
      finally
      {
        localObject4 = Logger.get();
        localObject1 = TAG;
        paramWorkSpec = String.format("Unable to schedule %s", new Object[] { paramWorkSpec });
        Log5ECF72.a(paramWorkSpec);
        LogE84000.a(paramWorkSpec);
        Log229316.a(paramWorkSpec);
        ((Logger)localObject4).error((String)localObject1, paramWorkSpec, new Throwable[] { localObject3 });
      }
      return;
    }
    catch (IllegalStateException paramWorkSpec)
    {
      localObject1 = getPendingJobs(this.mContext, this.mJobScheduler);
      if (localObject1 != null) {
        paramInt = ((List)localObject1).size();
      } else {
        paramInt = 0;
      }
      localObject1 = String.format(Locale.getDefault(), "JobScheduler 100 job limit exceeded.  We count %d WorkManager jobs in JobScheduler; we have %d tracked jobs in our DB; our Configuration limit is %d.", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(this.mWorkManager.getWorkDatabase().workSpecDao().getScheduledWork().size()), Integer.valueOf(this.mWorkManager.getConfiguration().getMaxSchedulerLimit()) });
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      Logger.get().error(TAG, (String)localObject1, new Throwable[0]);
      throw new IllegalStateException((String)localObject1, paramWorkSpec);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemjob/SystemJobScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */