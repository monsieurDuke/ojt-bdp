package androidx.work.impl.workers;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.work.ListenableWorker.Result;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DiagnosticsWorker
  extends Worker
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("DiagnosticsWrkr");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public DiagnosticsWorker(Context paramContext, WorkerParameters paramWorkerParameters)
  {
    super(paramContext, paramWorkerParameters);
  }
  
  private static String workSpecRow(WorkSpec paramWorkSpec, String paramString1, Integer paramInteger, String paramString2)
  {
    paramWorkSpec = String.format("\n%s\t %s\t %s\t %s\t %s\t %s\t", new Object[] { paramWorkSpec.id, paramWorkSpec.workerClassName, paramInteger, paramWorkSpec.state.name(), paramString1, paramString2 });
    Log5ECF72.a(paramWorkSpec);
    LogE84000.a(paramWorkSpec);
    Log229316.a(paramWorkSpec);
    return paramWorkSpec;
  }
  
  private static String workSpecRows(WorkNameDao paramWorkNameDao, WorkTagDao paramWorkTagDao, SystemIdInfoDao paramSystemIdInfoDao, List<WorkSpec> paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (Build.VERSION.SDK_INT >= 23) {
      localObject1 = "Job Id";
    } else {
      localObject1 = "Alarm Id";
    }
    Object localObject1 = String.format("\n Id \t Class Name\t %s\t State\t Unique Name\t Tags\t", new Object[] { localObject1 });
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    localStringBuilder.append((String)localObject1);
    localObject1 = paramList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      WorkSpec localWorkSpec = (WorkSpec)((Iterator)localObject1).next();
      paramList = null;
      Object localObject2 = paramSystemIdInfoDao.getSystemIdInfo(localWorkSpec.id);
      if (localObject2 != null) {
        paramList = Integer.valueOf(((SystemIdInfo)localObject2).systemId);
      }
      localObject2 = paramWorkNameDao.getNamesForWorkSpecId(localWorkSpec.id);
      Object localObject3 = paramWorkTagDao.getTagsForWorkSpecId(localWorkSpec.id);
      localObject2 = TextUtils.join(",", (Iterable)localObject2);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject3 = TextUtils.join(",", (Iterable)localObject3);
      Log5ECF72.a(localObject3);
      LogE84000.a(localObject3);
      paramList = workSpecRow(localWorkSpec, (String)localObject2, paramList, (String)localObject3);
      Log5ECF72.a(paramList);
      Log229316.a(paramList);
      localStringBuilder.append(paramList);
    }
    return localStringBuilder.toString();
  }
  
  public ListenableWorker.Result doWork()
  {
    Object localObject2 = WorkManagerImpl.getInstance(getApplicationContext()).getWorkDatabase();
    Object localObject3 = ((WorkDatabase)localObject2).workSpecDao();
    WorkNameDao localWorkNameDao = ((WorkDatabase)localObject2).workNameDao();
    Object localObject1 = ((WorkDatabase)localObject2).workTagDao();
    localObject2 = ((WorkDatabase)localObject2).systemIdInfoDao();
    Object localObject6 = ((WorkSpecDao)localObject3).getRecentlyCompletedWork(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1L));
    Object localObject4 = ((WorkSpecDao)localObject3).getRunningWork();
    localObject3 = ((WorkSpecDao)localObject3).getAllEligibleWorkSpecsForScheduling(200);
    Object localObject5;
    if ((localObject6 != null) && (!((List)localObject6).isEmpty()))
    {
      Logger localLogger = Logger.get();
      localObject5 = TAG;
      localLogger.info((String)localObject5, "Recently completed work:\n\n", new Throwable[0]);
      localLogger = Logger.get();
      localObject6 = workSpecRows(localWorkNameDao, (WorkTagDao)localObject1, (SystemIdInfoDao)localObject2, (List)localObject6);
      Log5ECF72.a(localObject6);
      LogE84000.a(localObject6);
      Log229316.a(localObject6);
      localLogger.info((String)localObject5, (String)localObject6, new Throwable[0]);
    }
    if ((localObject4 != null) && (!((List)localObject4).isEmpty()))
    {
      localObject6 = Logger.get();
      localObject5 = TAG;
      ((Logger)localObject6).info((String)localObject5, "Running work:\n\n", new Throwable[0]);
      localObject6 = Logger.get();
      localObject4 = workSpecRows(localWorkNameDao, (WorkTagDao)localObject1, (SystemIdInfoDao)localObject2, (List)localObject4);
      Log5ECF72.a(localObject4);
      LogE84000.a(localObject4);
      Log229316.a(localObject4);
      ((Logger)localObject6).info((String)localObject5, (String)localObject4, new Throwable[0]);
    }
    if ((localObject3 != null) && (!((List)localObject3).isEmpty()))
    {
      localObject5 = Logger.get();
      localObject4 = TAG;
      ((Logger)localObject5).info((String)localObject4, "Enqueued work:\n\n", new Throwable[0]);
      localObject5 = Logger.get();
      localObject1 = workSpecRows(localWorkNameDao, (WorkTagDao)localObject1, (SystemIdInfoDao)localObject2, (List)localObject3);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      ((Logger)localObject5).info((String)localObject4, (String)localObject1, new Throwable[0]);
    }
    return ListenableWorker.Result.success();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/workers/DiagnosticsWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */