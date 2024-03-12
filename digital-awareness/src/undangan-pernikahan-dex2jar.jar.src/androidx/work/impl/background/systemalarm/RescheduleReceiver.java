package androidx.work.impl.background.systemalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.work.Logger;
import androidx.work.impl.WorkManagerImpl;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class RescheduleReceiver
  extends BroadcastReceiver
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("RescheduleReceiver");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Logger localLogger = Logger.get();
    String str = TAG;
    paramIntent = String.format("Received intent %s", new Object[] { paramIntent });
    Log5ECF72.a(paramIntent);
    LogE84000.a(paramIntent);
    Log229316.a(paramIntent);
    localLogger.debug(str, paramIntent, new Throwable[0]);
    if (Build.VERSION.SDK_INT >= 23) {
      try
      {
        WorkManagerImpl.getInstance(paramContext).setReschedulePendingResult(goAsync());
      }
      catch (IllegalStateException paramContext)
      {
        Logger.get().error(TAG, "Cannot reschedule jobs. WorkManager needs to be initialized via a ContentProvider#onCreate() or an Application#onCreate().", new Throwable[] { paramContext });
      }
    } else {
      paramContext.startService(CommandHandler.createRescheduleIntent(paramContext));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/RescheduleReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */