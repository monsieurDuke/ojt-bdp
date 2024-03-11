package androidx.work.impl.constraints.trackers;

import android.content.Context;
import androidx.work.Logger;
import androidx.work.impl.constraints.ConstraintListener;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class ConstraintTracker<T>
{
  private static final String TAG;
  protected final Context mAppContext;
  T mCurrentState;
  private final Set<ConstraintListener<T>> mListeners = new LinkedHashSet();
  private final Object mLock = new Object();
  protected final TaskExecutor mTaskExecutor;
  
  static
  {
    String str = Logger.tagWithPrefix("ConstraintTracker");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  ConstraintTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    this.mAppContext = paramContext.getApplicationContext();
    this.mTaskExecutor = paramTaskExecutor;
  }
  
  public void addListener(ConstraintListener<T> paramConstraintListener)
  {
    synchronized (this.mLock)
    {
      if (this.mListeners.add(paramConstraintListener))
      {
        if (this.mListeners.size() == 1)
        {
          this.mCurrentState = getInitialState();
          Logger localLogger = Logger.get();
          String str2 = TAG;
          String str1 = String.format("%s: initial state = %s", new Object[] { getClass().getSimpleName(), this.mCurrentState });
          Log5ECF72.a(str1);
          LogE84000.a(str1);
          Log229316.a(str1);
          localLogger.debug(str2, str1, new Throwable[0]);
          startTracking();
        }
        paramConstraintListener.onConstraintChanged(this.mCurrentState);
      }
      return;
    }
  }
  
  public abstract T getInitialState();
  
  public void removeListener(ConstraintListener<T> paramConstraintListener)
  {
    synchronized (this.mLock)
    {
      if ((this.mListeners.remove(paramConstraintListener)) && (this.mListeners.isEmpty())) {
        stopTracking();
      }
      return;
    }
  }
  
  public void setState(T paramT)
  {
    synchronized (this.mLock)
    {
      Object localObject2 = this.mCurrentState;
      if ((localObject2 != paramT) && ((localObject2 == null) || (!localObject2.equals(paramT))))
      {
        this.mCurrentState = paramT;
        localObject2 = new java/util/ArrayList;
        ((ArrayList)localObject2).<init>(this.mListeners);
        Executor localExecutor = this.mTaskExecutor.getMainThreadExecutor();
        paramT = new androidx/work/impl/constraints/trackers/ConstraintTracker$1;
        paramT.<init>(this, (List)localObject2);
        localExecutor.execute(paramT);
        return;
      }
      return;
    }
  }
  
  public abstract void startTracking();
  
  public abstract void stopTracking();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/trackers/ConstraintTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */