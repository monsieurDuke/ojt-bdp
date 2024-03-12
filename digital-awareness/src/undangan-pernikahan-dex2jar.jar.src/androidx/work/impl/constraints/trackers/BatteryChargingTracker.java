package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class BatteryChargingTracker
  extends BroadcastReceiverConstraintTracker<Boolean>
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("BatteryChrgTracker");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public BatteryChargingTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(paramContext, paramTaskExecutor);
  }
  
  private boolean isBatteryChangedIntentCharging(Intent paramIntent)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = true;
    boolean bool2 = true;
    if (i >= 23)
    {
      i = paramIntent.getIntExtra("status", -1);
      bool1 = bool2;
      if (i != 2) {
        if (i == 5) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
      }
    }
    else if (paramIntent.getIntExtra("plugged", 0) == 0)
    {
      bool1 = false;
    }
    return bool1;
  }
  
  public Boolean getInitialState()
  {
    Object localObject = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    localObject = this.mAppContext.registerReceiver(null, (IntentFilter)localObject);
    if (localObject == null)
    {
      Logger.get().error(TAG, "getInitialState - null intent received", new Throwable[0]);
      return null;
    }
    return Boolean.valueOf(isBatteryChangedIntentCharging((Intent)localObject));
  }
  
  public IntentFilter getIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    if (Build.VERSION.SDK_INT >= 23)
    {
      localIntentFilter.addAction("android.os.action.CHARGING");
      localIntentFilter.addAction("android.os.action.DISCHARGING");
    }
    else
    {
      localIntentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
      localIntentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
    }
    return localIntentFilter;
  }
  
  public void onBroadcastReceive(Context paramContext, Intent paramIntent)
  {
    paramContext = paramIntent.getAction();
    if (paramContext == null) {
      return;
    }
    paramIntent = Logger.get();
    String str2 = TAG;
    String str1 = String.format("Received %s", new Object[] { paramContext });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    paramIntent.debug(str2, str1, new Throwable[0]);
    int i = -1;
    switch (paramContext.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramContext.equals("android.intent.action.ACTION_POWER_CONNECTED"))
      {
        i = 2;
        break;
        if (paramContext.equals("android.os.action.CHARGING"))
        {
          i = 0;
          break;
          if (paramContext.equals("android.os.action.DISCHARGING"))
          {
            i = 1;
            break;
            if (paramContext.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
              i = 3;
            }
          }
        }
      }
    }
    switch (i)
    {
    default: 
      break;
    case 3: 
      setState(Boolean.valueOf(false));
      break;
    case 2: 
      setState(Boolean.valueOf(true));
      break;
    case 1: 
      setState(Boolean.valueOf(false));
      break;
    case 0: 
      setState(Boolean.valueOf(true));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/trackers/BatteryChargingTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */