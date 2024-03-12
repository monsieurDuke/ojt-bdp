package androidx.work.impl.background.systemalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.Constraints;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.model.WorkSpec;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

abstract class ConstraintProxy
  extends BroadcastReceiver
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("ConstraintProxy");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  static void updateAll(Context paramContext, List<WorkSpec> paramList)
  {
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool1 = false;
    Iterator localIterator = paramList.iterator();
    boolean bool5;
    boolean bool6;
    boolean bool7;
    boolean bool8;
    for (;;)
    {
      bool5 = bool4;
      bool6 = bool3;
      bool7 = bool2;
      bool8 = bool1;
      if (!localIterator.hasNext()) {
        break;
      }
      paramList = ((WorkSpec)localIterator.next()).constraints;
      bool4 |= paramList.requiresBatteryNotLow();
      bool3 |= paramList.requiresCharging();
      bool2 |= paramList.requiresStorageNotLow();
      int i;
      if (paramList.getRequiredNetworkType() != NetworkType.NOT_REQUIRED) {
        i = 1;
      } else {
        i = 0;
      }
      bool1 |= i;
      if ((bool4) && (bool3) && (bool2) && (bool1))
      {
        bool5 = bool4;
        bool6 = bool3;
        bool7 = bool2;
        bool8 = bool1;
        break;
      }
    }
    paramContext.sendBroadcast(ConstraintProxyUpdateReceiver.newConstraintProxyUpdateIntent(paramContext, bool5, bool6, bool7, bool8));
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Logger localLogger = Logger.get();
    String str = TAG;
    paramIntent = String.format("onReceive : %s", new Object[] { paramIntent });
    Log5ECF72.a(paramIntent);
    LogE84000.a(paramIntent);
    Log229316.a(paramIntent);
    localLogger.debug(str, paramIntent, new Throwable[0]);
    paramContext.startService(CommandHandler.createConstraintsChangedIntent(paramContext));
  }
  
  public static class BatteryChargingProxy
    extends ConstraintProxy
  {}
  
  public static class BatteryNotLowProxy
    extends ConstraintProxy
  {}
  
  public static class NetworkStateProxy
    extends ConstraintProxy
  {}
  
  public static class StorageNotLowProxy
    extends ConstraintProxy
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/ConstraintProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */