package androidx.work.impl.background.systemalarm;

import android.content.BroadcastReceiver;
import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.work.Logger;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.utils.PackageManagerHelper;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ConstraintProxyUpdateReceiver
  extends BroadcastReceiver
{
  static final String ACTION = "androidx.work.impl.background.systemalarm.UpdateProxies";
  static final String KEY_BATTERY_CHARGING_PROXY_ENABLED = "KEY_BATTERY_CHARGING_PROXY_ENABLED";
  static final String KEY_BATTERY_NOT_LOW_PROXY_ENABLED = "KEY_BATTERY_NOT_LOW_PROXY_ENABLED";
  static final String KEY_NETWORK_STATE_PROXY_ENABLED = "KEY_NETWORK_STATE_PROXY_ENABLED";
  static final String KEY_STORAGE_NOT_LOW_PROXY_ENABLED = "KEY_STORAGE_NOT_LOW_PROXY_ENABLED";
  static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("ConstrntProxyUpdtRecvr");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static Intent newConstraintProxyUpdateIntent(Context paramContext, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    Intent localIntent = new Intent("androidx.work.impl.background.systemalarm.UpdateProxies");
    localIntent.setComponent(new ComponentName(paramContext, ConstraintProxyUpdateReceiver.class));
    localIntent.putExtra("KEY_BATTERY_NOT_LOW_PROXY_ENABLED", paramBoolean1).putExtra("KEY_BATTERY_CHARGING_PROXY_ENABLED", paramBoolean2).putExtra("KEY_STORAGE_NOT_LOW_PROXY_ENABLED", paramBoolean3).putExtra("KEY_NETWORK_STATE_PROXY_ENABLED", paramBoolean4);
    return localIntent;
  }
  
  public void onReceive(final Context paramContext, final Intent paramIntent)
  {
    final Object localObject;
    if (paramIntent != null) {
      localObject = paramIntent.getAction();
    } else {
      localObject = null;
    }
    if (!"androidx.work.impl.background.systemalarm.UpdateProxies".equals(localObject))
    {
      paramIntent = Logger.get();
      paramContext = TAG;
      localObject = String.format("Ignoring unknown action %s", new Object[] { localObject });
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      paramIntent.debug(paramContext, (String)localObject, new Throwable[0]);
    }
    else
    {
      localObject = goAsync();
      WorkManagerImpl.getInstance(paramContext).getWorkTaskExecutor().executeOnBackgroundThread(new Runnable()
      {
        public void run()
        {
          try
          {
            boolean bool2 = paramIntent.getBooleanExtra("KEY_BATTERY_NOT_LOW_PROXY_ENABLED", false);
            boolean bool3 = paramIntent.getBooleanExtra("KEY_BATTERY_CHARGING_PROXY_ENABLED", false);
            boolean bool1 = paramIntent.getBooleanExtra("KEY_STORAGE_NOT_LOW_PROXY_ENABLED", false);
            boolean bool4 = paramIntent.getBooleanExtra("KEY_NETWORK_STATE_PROXY_ENABLED", false);
            Logger localLogger = Logger.get();
            String str2 = ConstraintProxyUpdateReceiver.TAG;
            String str1 = String.format("Updating proxies: BatteryNotLowProxy enabled (%s), BatteryChargingProxy enabled (%s), StorageNotLowProxy (%s), NetworkStateProxy enabled (%s)", new Object[] { Boolean.valueOf(bool2), Boolean.valueOf(bool3), Boolean.valueOf(bool1), Boolean.valueOf(bool4) });
            Log5ECF72.a(str1);
            LogE84000.a(str1);
            Log229316.a(str1);
            localLogger.debug(str2, str1, new Throwable[0]);
            PackageManagerHelper.setComponentEnabled(paramContext, ConstraintProxy.BatteryNotLowProxy.class, bool2);
            PackageManagerHelper.setComponentEnabled(paramContext, ConstraintProxy.BatteryChargingProxy.class, bool3);
            PackageManagerHelper.setComponentEnabled(paramContext, ConstraintProxy.StorageNotLowProxy.class, bool1);
            PackageManagerHelper.setComponentEnabled(paramContext, ConstraintProxy.NetworkStateProxy.class, bool4);
            return;
          }
          finally
          {
            localObject.finish();
          }
        }
      });
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/ConstraintProxyUpdateReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */