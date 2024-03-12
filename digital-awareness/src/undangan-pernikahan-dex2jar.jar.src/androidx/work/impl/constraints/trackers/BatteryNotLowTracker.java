package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class BatteryNotLowTracker
  extends BroadcastReceiverConstraintTracker<Boolean>
{
  static final float BATTERY_LOW_THRESHOLD = 0.15F;
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("BatteryNotLowTracker");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public BatteryNotLowTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(paramContext, paramTaskExecutor);
  }
  
  public Boolean getInitialState()
  {
    Object localObject = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    localObject = this.mAppContext.registerReceiver(null, (IntentFilter)localObject);
    boolean bool = false;
    if (localObject == null)
    {
      Logger.get().error(TAG, "getInitialState - null intent received", new Throwable[0]);
      return null;
    }
    int k = ((Intent)localObject).getIntExtra("status", -1);
    int j = ((Intent)localObject).getIntExtra("level", -1);
    int i = ((Intent)localObject).getIntExtra("scale", -1);
    float f = j / i;
    if ((k == 1) || (f > 0.15F)) {
      bool = true;
    }
    return Boolean.valueOf(bool);
  }
  
  public IntentFilter getIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.BATTERY_OKAY");
    localIntentFilter.addAction("android.intent.action.BATTERY_LOW");
    return localIntentFilter;
  }
  
  public void onBroadcastReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction() == null) {
      return;
    }
    paramContext = Logger.get();
    String str1 = TAG;
    String str2 = String.format("Received %s", new Object[] { paramIntent.getAction() });
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    paramContext.debug(str1, str2, new Throwable[0]);
    paramContext = paramIntent.getAction();
    int i = -1;
    switch (paramContext.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramContext.equals("android.intent.action.BATTERY_LOW"))
      {
        i = 1;
        break;
        if (paramContext.equals("android.intent.action.BATTERY_OKAY")) {
          i = 0;
        }
      }
    }
    switch (i)
    {
    default: 
      break;
    case 1: 
      setState(Boolean.valueOf(false));
      break;
    case 0: 
      setState(Boolean.valueOf(true));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/trackers/BatteryNotLowTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */