package androidx.work.impl.utils;

import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.work.Constraints;
import androidx.work.Data.Builder;
import androidx.work.ExistingWorkPolicy;
import androidx.work.Logger;
import androidx.work.Operation;
import androidx.work.Operation.State.FAILURE;
import androidx.work.WorkInfo.State;
import androidx.work.WorkRequest;
import androidx.work.impl.OperationImpl;
import androidx.work.impl.Scheduler;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkContinuationImpl;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.model.Dependency;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkName;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpec.IdAndState;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTag;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class EnqueueRunnable
  implements Runnable
{
  private static final String TAG;
  private final OperationImpl mOperation;
  private final WorkContinuationImpl mWorkContinuation;
  
  static
  {
    String str = Logger.tagWithPrefix("EnqueueRunnable");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public EnqueueRunnable(WorkContinuationImpl paramWorkContinuationImpl)
  {
    this.mWorkContinuation = paramWorkContinuationImpl;
    this.mOperation = new OperationImpl();
  }
  
  private static boolean enqueueContinuation(WorkContinuationImpl paramWorkContinuationImpl)
  {
    Set localSet = WorkContinuationImpl.prerequisitesFor(paramWorkContinuationImpl);
    boolean bool = enqueueWorkWithPrerequisites(paramWorkContinuationImpl.getWorkManagerImpl(), paramWorkContinuationImpl.getWork(), (String[])localSet.toArray(new String[0]), paramWorkContinuationImpl.getName(), paramWorkContinuationImpl.getExistingWorkPolicy());
    paramWorkContinuationImpl.markEnqueued();
    return bool;
  }
  
  private static boolean enqueueWorkWithPrerequisites(WorkManagerImpl paramWorkManagerImpl, List<? extends WorkRequest> paramList, String[] paramArrayOfString, String paramString, ExistingWorkPolicy paramExistingWorkPolicy)
  {
    boolean bool2 = false;
    long l = System.currentTimeMillis();
    WorkDatabase localWorkDatabase = paramWorkManagerImpl.getWorkDatabase();
    int i;
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
      i = 1;
    } else {
      i = 0;
    }
    int n = 1;
    int k = 1;
    int i1 = 0;
    int j = 0;
    int i2 = 0;
    int m = 0;
    Object localObject1;
    Object localObject2;
    if (i != 0)
    {
      i2 = paramArrayOfString.length;
      for (n = 0; n < i2; n++)
      {
        localObject1 = paramArrayOfString[n];
        localObject2 = localWorkDatabase.workSpecDao().getWorkSpec((String)localObject1);
        if (localObject2 == null)
        {
          paramWorkManagerImpl = Logger.get();
          paramList = TAG;
          paramArrayOfString = String.format("Prerequisite %s doesn't exist; not enqueuing", new Object[] { localObject1 });
          Log5ECF72.a(paramArrayOfString);
          LogE84000.a(paramArrayOfString);
          Log229316.a(paramArrayOfString);
          paramWorkManagerImpl.error(paramList, paramArrayOfString, new Throwable[0]);
          return false;
        }
        localObject1 = ((WorkSpec)localObject2).state;
        if (localObject1 == WorkInfo.State.SUCCEEDED) {
          i1 = 1;
        } else {
          i1 = 0;
        }
        k &= i1;
        if (localObject1 == WorkInfo.State.FAILED) {
          j = 1;
        } else if (localObject1 == WorkInfo.State.CANCELLED) {
          m = 1;
        }
      }
    }
    else
    {
      bool2 = false;
      m = i2;
      j = i1;
      k = n;
    }
    boolean bool1 = TextUtils.isEmpty(paramString) ^ true;
    if ((bool1) && (i == 0)) {
      n = 1;
    } else {
      n = 0;
    }
    if (n != 0)
    {
      Object localObject3 = localWorkDatabase.workSpecDao().getWorkSpecIdAndStatesForName(paramString);
      if (!((List)localObject3).isEmpty())
      {
        if ((paramExistingWorkPolicy != ExistingWorkPolicy.APPEND) && (paramExistingWorkPolicy != ExistingWorkPolicy.APPEND_OR_REPLACE))
        {
          if (paramExistingWorkPolicy == ExistingWorkPolicy.KEEP)
          {
            paramExistingWorkPolicy = ((List)localObject3).iterator();
            while (paramExistingWorkPolicy.hasNext())
            {
              localObject1 = (WorkSpec.IdAndState)paramExistingWorkPolicy.next();
              if ((((WorkSpec.IdAndState)localObject1).state != WorkInfo.State.ENQUEUED) && (((WorkSpec.IdAndState)localObject1).state != WorkInfo.State.RUNNING)) {}
              return false;
            }
          }
          CancelWorkRunnable.forName(paramString, paramWorkManagerImpl, false).run();
          bool2 = true;
          paramExistingWorkPolicy = localWorkDatabase.workSpecDao();
          localObject1 = ((List)localObject3).iterator();
          while (((Iterator)localObject1).hasNext()) {
            paramExistingWorkPolicy.delete(((WorkSpec.IdAndState)((Iterator)localObject1).next()).id);
          }
          paramExistingWorkPolicy = paramArrayOfString;
          break label753;
        }
        localObject1 = localWorkDatabase.dependencyDao();
        localObject2 = new ArrayList();
        localObject3 = ((List)localObject3).iterator();
        n = i;
        while (((Iterator)localObject3).hasNext())
        {
          WorkSpec.IdAndState localIdAndState = (WorkSpec.IdAndState)((Iterator)localObject3).next();
          if (!((DependencyDao)localObject1).hasDependents(localIdAndState.id))
          {
            if (localIdAndState.state == WorkInfo.State.SUCCEEDED) {
              i1 = 1;
            } else {
              i1 = 0;
            }
            if (localIdAndState.state == WorkInfo.State.FAILED)
            {
              i = 1;
            }
            else
            {
              i = j;
              if (localIdAndState.state == WorkInfo.State.CANCELLED)
              {
                m = 1;
                i = j;
              }
            }
            ((List)localObject2).add(localIdAndState.id);
            k = i1 & k;
          }
          else
          {
            i = j;
          }
          j = i;
        }
        i1 = j;
        n = m;
        localObject1 = localObject2;
        if (paramExistingWorkPolicy == ExistingWorkPolicy.APPEND_OR_REPLACE) {
          if (m == 0)
          {
            i1 = j;
            n = m;
            localObject1 = localObject2;
            if (j == 0) {}
          }
          else
          {
            localObject1 = localWorkDatabase.workSpecDao();
            paramExistingWorkPolicy = ((WorkSpecDao)localObject1).getWorkSpecIdAndStatesForName(paramString);
            localObject2 = paramExistingWorkPolicy.iterator();
            while (((Iterator)localObject2).hasNext()) {
              ((WorkSpecDao)localObject1).delete(((WorkSpec.IdAndState)((Iterator)localObject2).next()).id);
            }
            localObject1 = Collections.emptyList();
            n = 0;
            i1 = 0;
          }
        }
        paramExistingWorkPolicy = (String[])((List)localObject1).toArray(paramArrayOfString);
        if (paramExistingWorkPolicy.length > 0) {
          i = 1;
        } else {
          i = 0;
        }
        j = i1;
        m = n;
        break label753;
      }
    }
    paramExistingWorkPolicy = paramArrayOfString;
    label753:
    paramArrayOfString = paramList.iterator();
    paramList = paramExistingWorkPolicy;
    while (paramArrayOfString.hasNext())
    {
      paramExistingWorkPolicy = (WorkRequest)paramArrayOfString.next();
      localObject1 = paramExistingWorkPolicy.getWorkSpec();
      if ((i != 0) && (k == 0))
      {
        if (j != 0) {
          ((WorkSpec)localObject1).state = WorkInfo.State.FAILED;
        } else if (m != 0) {
          ((WorkSpec)localObject1).state = WorkInfo.State.CANCELLED;
        } else {
          ((WorkSpec)localObject1).state = WorkInfo.State.BLOCKED;
        }
      }
      else if (!((WorkSpec)localObject1).isPeriodic()) {
        ((WorkSpec)localObject1).periodStartTime = l;
      } else {
        ((WorkSpec)localObject1).periodStartTime = 0L;
      }
      if ((Build.VERSION.SDK_INT >= 23) && (Build.VERSION.SDK_INT <= 25)) {
        tryDelegateConstrainedWorkSpec((WorkSpec)localObject1);
      } else if ((Build.VERSION.SDK_INT <= 22) && (usesScheduler(paramWorkManagerImpl, "androidx.work.impl.background.gcm.GcmScheduler"))) {
        tryDelegateConstrainedWorkSpec((WorkSpec)localObject1);
      }
      if (((WorkSpec)localObject1).state == WorkInfo.State.ENQUEUED) {
        bool2 = true;
      }
      localWorkDatabase.workSpecDao().insertWorkSpec((WorkSpec)localObject1);
      if (i != 0)
      {
        i1 = paramList.length;
        for (n = 0; n < i1; n++)
        {
          localObject1 = paramList[n];
          localObject1 = new Dependency(paramExistingWorkPolicy.getStringId(), (String)localObject1);
          localWorkDatabase.dependencyDao().insertDependency((Dependency)localObject1);
        }
      }
      localObject1 = paramExistingWorkPolicy.getTags().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (String)((Iterator)localObject1).next();
        localWorkDatabase.workTagDao().insert(new WorkTag((String)localObject2, paramExistingWorkPolicy.getStringId()));
      }
      if (bool1) {
        localWorkDatabase.workNameDao().insert(new WorkName(paramString, paramExistingWorkPolicy.getStringId()));
      }
    }
    return bool2;
  }
  
  private static boolean processContinuation(WorkContinuationImpl paramWorkContinuationImpl)
  {
    int i = 0;
    boolean bool = false;
    Object localObject1 = paramWorkContinuationImpl.getParents();
    if (localObject1 != null)
    {
      localObject1 = ((List)localObject1).iterator();
      for (;;)
      {
        i = bool;
        if (!((Iterator)localObject1).hasNext()) {
          break;
        }
        Object localObject2 = (WorkContinuationImpl)((Iterator)localObject1).next();
        if (!((WorkContinuationImpl)localObject2).isEnqueued())
        {
          bool |= processContinuation((WorkContinuationImpl)localObject2);
        }
        else
        {
          Logger localLogger = Logger.get();
          String str = TAG;
          localObject2 = TextUtils.join(", ", ((WorkContinuationImpl)localObject2).getIds());
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          localObject2 = String.format("Already enqueued work ids (%s).", new Object[] { localObject2 });
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          localLogger.warning(str, (String)localObject2, new Throwable[0]);
        }
      }
    }
    return i | enqueueContinuation(paramWorkContinuationImpl);
  }
  
  private static void tryDelegateConstrainedWorkSpec(WorkSpec paramWorkSpec)
  {
    Object localObject = paramWorkSpec.constraints;
    String str = paramWorkSpec.workerClassName;
    if ((!str.equals(ConstraintTrackingWorker.class.getName())) && ((((Constraints)localObject).requiresBatteryNotLow()) || (((Constraints)localObject).requiresStorageNotLow())))
    {
      localObject = new Data.Builder();
      ((Data.Builder)localObject).putAll(paramWorkSpec.input).putString("androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME", str);
      paramWorkSpec.workerClassName = ConstraintTrackingWorker.class.getName();
      paramWorkSpec.input = ((Data.Builder)localObject).build();
    }
  }
  
  private static boolean usesScheduler(WorkManagerImpl paramWorkManagerImpl, String paramString)
  {
    try
    {
      paramString = Class.forName(paramString);
      paramWorkManagerImpl = paramWorkManagerImpl.getSchedulers().iterator();
      while (paramWorkManagerImpl.hasNext())
      {
        boolean bool = paramString.isAssignableFrom(((Scheduler)paramWorkManagerImpl.next()).getClass());
        if (bool) {
          return true;
        }
      }
      return false;
    }
    catch (ClassNotFoundException paramWorkManagerImpl) {}
    return false;
  }
  
  public boolean addToDatabase()
  {
    WorkDatabase localWorkDatabase = this.mWorkContinuation.getWorkManagerImpl().getWorkDatabase();
    localWorkDatabase.beginTransaction();
    try
    {
      boolean bool = processContinuation(this.mWorkContinuation);
      localWorkDatabase.setTransactionSuccessful();
      return bool;
    }
    finally
    {
      localWorkDatabase.endTransaction();
    }
  }
  
  public Operation getOperation()
  {
    return this.mOperation;
  }
  
  public void run()
  {
    try
    {
      if (!this.mWorkContinuation.hasCycles())
      {
        if (addToDatabase())
        {
          PackageManagerHelper.setComponentEnabled(this.mWorkContinuation.getWorkManagerImpl().getApplicationContext(), RescheduleReceiver.class, true);
          scheduleWorkInBackground();
        }
        this.mOperation.setState(Operation.SUCCESS);
      }
      else
      {
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        String str = String.format("WorkContinuation has cycles (%s)", new Object[] { this.mWorkContinuation });
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        localIllegalStateException.<init>(str);
        throw localIllegalStateException;
      }
    }
    finally
    {
      this.mOperation.setState(new Operation.State.FAILURE(localThrowable));
    }
  }
  
  public void scheduleWorkInBackground()
  {
    WorkManagerImpl localWorkManagerImpl = this.mWorkContinuation.getWorkManagerImpl();
    Schedulers.schedule(localWorkManagerImpl.getConfiguration(), localWorkManagerImpl.getWorkDatabase(), localWorkManagerImpl.getSchedulers());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/EnqueueRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */