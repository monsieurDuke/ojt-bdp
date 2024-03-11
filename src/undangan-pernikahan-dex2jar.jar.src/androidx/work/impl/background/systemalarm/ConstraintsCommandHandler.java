package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import androidx.work.Logger;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class ConstraintsCommandHandler
{
  private static final String TAG;
  private final Context mContext;
  private final SystemAlarmDispatcher mDispatcher;
  private final int mStartId;
  private final WorkConstraintsTracker mWorkConstraintsTracker;
  
  static
  {
    String str = Logger.tagWithPrefix("ConstraintsCmdHandler");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  ConstraintsCommandHandler(Context paramContext, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    this.mContext = paramContext;
    this.mStartId = paramInt;
    this.mDispatcher = paramSystemAlarmDispatcher;
    this.mWorkConstraintsTracker = new WorkConstraintsTracker(paramContext, paramSystemAlarmDispatcher.getTaskExecutor(), null);
  }
  
  void handleConstraintsChanged()
  {
    Object localObject2 = this.mDispatcher.getWorkManager().getWorkDatabase().workSpecDao().getScheduledWork();
    ConstraintProxy.updateAll(this.mContext, (List)localObject2);
    this.mWorkConstraintsTracker.replace((Iterable)localObject2);
    Object localObject1 = new ArrayList(((List)localObject2).size());
    long l = System.currentTimeMillis();
    localObject2 = ((List)localObject2).iterator();
    Object localObject3;
    String str1;
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (WorkSpec)((Iterator)localObject2).next();
      str1 = ((WorkSpec)localObject3).id;
      if ((l >= ((WorkSpec)localObject3).calculateNextRunTime()) && ((!((WorkSpec)localObject3).hasConstraints()) || (this.mWorkConstraintsTracker.areAllConstraintsMet(str1)))) {
        ((List)localObject1).add(localObject3);
      }
    }
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      String str2 = ((WorkSpec)((Iterator)localObject1).next()).id;
      localObject3 = CommandHandler.createDelayMetIntent(this.mContext, str2);
      localObject2 = Logger.get();
      str1 = TAG;
      str2 = String.format("Creating a delay_met command for workSpec with id (%s)", new Object[] { str2 });
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      ((Logger)localObject2).debug(str1, str2, new Throwable[0]);
      this.mDispatcher.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(this.mDispatcher, (Intent)localObject3, this.mStartId));
    }
    this.mWorkConstraintsTracker.reset();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/ConstraintsCommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */