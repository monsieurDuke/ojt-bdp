package androidx.work.impl.constraints.trackers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class BroadcastReceiverConstraintTracker<T>
  extends ConstraintTracker<T>
{
  private static final String TAG;
  private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent != null) {
        BroadcastReceiverConstraintTracker.this.onBroadcastReceive(paramAnonymousContext, paramAnonymousIntent);
      }
    }
  };
  
  static
  {
    String str = Logger.tagWithPrefix("BrdcstRcvrCnstrntTrckr");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public BroadcastReceiverConstraintTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(paramContext, paramTaskExecutor);
  }
  
  public abstract IntentFilter getIntentFilter();
  
  public abstract void onBroadcastReceive(Context paramContext, Intent paramIntent);
  
  public void startTracking()
  {
    Logger localLogger = Logger.get();
    String str1 = TAG;
    String str2 = String.format("%s: registering receiver", new Object[] { getClass().getSimpleName() });
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    localLogger.debug(str1, str2, new Throwable[0]);
    this.mAppContext.registerReceiver(this.mBroadcastReceiver, getIntentFilter());
  }
  
  public void stopTracking()
  {
    Logger localLogger = Logger.get();
    String str1 = TAG;
    String str2 = String.format("%s: unregistering receiver", new Object[] { getClass().getSimpleName() });
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    localLogger.debug(str1, str2, new Throwable[0]);
    this.mAppContext.unregisterReceiver(this.mBroadcastReceiver);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/trackers/BroadcastReceiverConstraintTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */