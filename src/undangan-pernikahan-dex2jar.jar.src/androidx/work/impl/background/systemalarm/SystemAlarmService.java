package androidx.work.impl.background.systemalarm;

import android.content.Intent;
import androidx.lifecycle.LifecycleService;
import androidx.work.Logger;
import androidx.work.impl.utils.WakeLocks;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class SystemAlarmService
  extends LifecycleService
  implements SystemAlarmDispatcher.CommandsCompletedListener
{
  private static final String TAG;
  private SystemAlarmDispatcher mDispatcher;
  private boolean mIsShutdown;
  
  static
  {
    String str = Logger.tagWithPrefix("SystemAlarmService");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  private void initializeDispatcher()
  {
    SystemAlarmDispatcher localSystemAlarmDispatcher = new SystemAlarmDispatcher(this);
    this.mDispatcher = localSystemAlarmDispatcher;
    localSystemAlarmDispatcher.setCompletedListener(this);
  }
  
  public void onAllCommandsCompleted()
  {
    this.mIsShutdown = true;
    Logger.get().debug(TAG, "All commands completed in dispatcher", new Throwable[0]);
    WakeLocks.checkWakeLocks();
    stopSelf();
  }
  
  public void onCreate()
  {
    super.onCreate();
    initializeDispatcher();
    this.mIsShutdown = false;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.mIsShutdown = true;
    this.mDispatcher.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    super.onStartCommand(paramIntent, paramInt1, paramInt2);
    if (this.mIsShutdown)
    {
      Logger.get().info(TAG, "Re-initializing SystemAlarmDispatcher after a request to shut-down.", new Throwable[0]);
      this.mDispatcher.onDestroy();
      initializeDispatcher();
      this.mIsShutdown = false;
    }
    if (paramIntent != null) {
      this.mDispatcher.add(paramIntent, paramInt2);
    }
    return 3;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/SystemAlarmService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */