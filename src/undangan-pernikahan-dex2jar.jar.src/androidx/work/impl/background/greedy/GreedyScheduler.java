package androidx.work.impl.background.greedy;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.ProcessUtils;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class GreedyScheduler
  implements Scheduler, WorkConstraintsCallback, ExecutionListener
{
  private static final String TAG;
  private final Set<WorkSpec> mConstrainedWorkSpecs = new HashSet();
  private final Context mContext;
  private DelayedWorkTracker mDelayedWorkTracker;
  Boolean mInDefaultProcess;
  private final Object mLock;
  private boolean mRegisteredExecutionListener;
  private final WorkConstraintsTracker mWorkConstraintsTracker;
  private final WorkManagerImpl mWorkManagerImpl;
  
  static
  {
    String str = Logger.tagWithPrefix("GreedyScheduler");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public GreedyScheduler(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, WorkManagerImpl paramWorkManagerImpl)
  {
    this.mContext = paramContext;
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mWorkConstraintsTracker = new WorkConstraintsTracker(paramContext, paramTaskExecutor, this);
    this.mDelayedWorkTracker = new DelayedWorkTracker(this, paramConfiguration.getRunnableScheduler());
    this.mLock = new Object();
  }
  
  public GreedyScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl, WorkConstraintsTracker paramWorkConstraintsTracker)
  {
    this.mContext = paramContext;
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mWorkConstraintsTracker = paramWorkConstraintsTracker;
    this.mLock = new Object();
  }
  
  private void checkDefaultProcess()
  {
    Configuration localConfiguration = this.mWorkManagerImpl.getConfiguration();
    this.mInDefaultProcess = Boolean.valueOf(ProcessUtils.isDefaultProcess(this.mContext, localConfiguration));
  }
  
  private void registerExecutionListenerIfNeeded()
  {
    if (!this.mRegisteredExecutionListener)
    {
      this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
      this.mRegisteredExecutionListener = true;
    }
  }
  
  private void removeConstraintTrackingFor(String paramString)
  {
    synchronized (this.mLock)
    {
      Object localObject2 = this.mConstrainedWorkSpecs.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        WorkSpec localWorkSpec = (WorkSpec)((Iterator)localObject2).next();
        if (localWorkSpec.id.equals(paramString))
        {
          localObject2 = Logger.get();
          String str = TAG;
          paramString = String.format("Stopping tracking for %s", new Object[] { paramString });
          Log5ECF72.a(paramString);
          LogE84000.a(paramString);
          Log229316.a(paramString);
          ((Logger)localObject2).debug(str, paramString, new Throwable[0]);
          this.mConstrainedWorkSpecs.remove(localWorkSpec);
          this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
          break;
        }
      }
      return;
    }
  }
  
  public void cancel(String paramString)
  {
    if (this.mInDefaultProcess == null) {
      checkDefaultProcess();
    }
    if (!this.mInDefaultProcess.booleanValue())
    {
      Logger.get().info(TAG, "Ignoring schedule request in non-main process", new Throwable[0]);
      return;
    }
    registerExecutionListenerIfNeeded();
    Object localObject = Logger.get();
    String str2 = TAG;
    String str1 = String.format("Cancelling work ID %s", new Object[] { paramString });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    ((Logger)localObject).debug(str2, str1, new Throwable[0]);
    localObject = this.mDelayedWorkTracker;
    if (localObject != null) {
      ((DelayedWorkTracker)localObject).unschedule(paramString);
    }
    this.mWorkManagerImpl.stopWork(paramString);
  }
  
  public boolean hasLimitedSchedulingSlots()
  {
    return false;
  }
  
  public void onAllConstraintsMet(List<String> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      String str3 = (String)paramList.next();
      Logger localLogger = Logger.get();
      String str2 = TAG;
      String str1 = String.format("Constraints met: Scheduling work ID %s", new Object[] { str3 });
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
      localLogger.debug(str2, str1, new Throwable[0]);
      this.mWorkManagerImpl.startWork(str3);
    }
  }
  
  public void onAllConstraintsNotMet(List<String> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (String)localIterator.next();
      Logger localLogger = Logger.get();
      String str1 = TAG;
      String str2 = String.format("Constraints not met: Cancelling work ID %s", new Object[] { paramList });
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      localLogger.debug(str1, str2, new Throwable[0]);
      this.mWorkManagerImpl.stopWork(paramList);
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    removeConstraintTrackingFor(paramString);
  }
  
  public void schedule(WorkSpec... arg1)
  {
    if (this.mInDefaultProcess == null) {
      checkDefaultProcess();
    }
    if (!this.mInDefaultProcess.booleanValue())
    {
      Logger.get().info(TAG, "Ignoring schedule request in a secondary process", new Throwable[0]);
      return;
    }
    registerExecutionListenerIfNeeded();
    HashSet localHashSet = new HashSet();
    Object localObject2 = new HashSet();
    int i = ???.length;
    Object localObject3;
    Object localObject4;
    for (int j = 0; j < i; j++)
    {
      localObject3 = ???[j];
      long l1 = ((WorkSpec)localObject3).calculateNextRunTime();
      long l2 = System.currentTimeMillis();
      if (((WorkSpec)localObject3).state == WorkInfo.State.ENQUEUED) {
        if (l2 < l1)
        {
          localObject4 = this.mDelayedWorkTracker;
          if (localObject4 != null) {
            ((DelayedWorkTracker)localObject4).schedule((WorkSpec)localObject3);
          }
        }
        else
        {
          Object localObject5;
          if (((WorkSpec)localObject3).hasConstraints())
          {
            if ((Build.VERSION.SDK_INT >= 23) && (((WorkSpec)localObject3).constraints.requiresDeviceIdle()))
            {
              localObject5 = Logger.get();
              localObject4 = TAG;
              localObject3 = String.format("Ignoring WorkSpec %s, Requires device idle.", new Object[] { localObject3 });
              Log5ECF72.a(localObject3);
              LogE84000.a(localObject3);
              Log229316.a(localObject3);
              ((Logger)localObject5).debug((String)localObject4, (String)localObject3, new Throwable[0]);
            }
            else if ((Build.VERSION.SDK_INT >= 24) && (((WorkSpec)localObject3).constraints.hasContentUriTriggers()))
            {
              localObject5 = Logger.get();
              localObject4 = TAG;
              localObject3 = String.format("Ignoring WorkSpec %s, Requires ContentUri triggers.", new Object[] { localObject3 });
              Log5ECF72.a(localObject3);
              LogE84000.a(localObject3);
              Log229316.a(localObject3);
              ((Logger)localObject5).debug((String)localObject4, (String)localObject3, new Throwable[0]);
            }
            else
            {
              localHashSet.add(localObject3);
              ((Set)localObject2).add(((WorkSpec)localObject3).id);
            }
          }
          else
          {
            Logger localLogger = Logger.get();
            localObject4 = TAG;
            localObject5 = String.format("Starting work for %s", new Object[] { ((WorkSpec)localObject3).id });
            Log5ECF72.a(localObject5);
            LogE84000.a(localObject5);
            Log229316.a(localObject5);
            localLogger.debug((String)localObject4, (String)localObject5, new Throwable[0]);
            this.mWorkManagerImpl.startWork(((WorkSpec)localObject3).id);
          }
        }
      }
    }
    synchronized (this.mLock)
    {
      if (!localHashSet.isEmpty())
      {
        localObject4 = Logger.get();
        localObject3 = TAG;
        localObject2 = TextUtils.join(",", (Iterable)localObject2);
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        localObject2 = String.format("Starting tracking for [%s]", new Object[] { localObject2 });
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        ((Logger)localObject4).debug((String)localObject3, (String)localObject2, new Throwable[0]);
        this.mConstrainedWorkSpecs.addAll(localHashSet);
        this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
      }
      return;
    }
  }
  
  public void setDelayedWorkTracker(DelayedWorkTracker paramDelayedWorkTracker)
  {
    this.mDelayedWorkTracker = paramDelayedWorkTracker;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/greedy/GreedyScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */