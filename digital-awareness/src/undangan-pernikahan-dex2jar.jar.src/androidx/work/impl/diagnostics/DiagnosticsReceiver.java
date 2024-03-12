package androidx.work.impl.diagnostics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.Logger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.impl.workers.DiagnosticsWorker;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DiagnosticsReceiver
  extends BroadcastReceiver
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("DiagnosticsRcvr");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent == null) {
      return;
    }
    Logger.get().debug(TAG, "Requesting diagnostics", new Throwable[0]);
    try
    {
      WorkManager.getInstance(paramContext).enqueue(OneTimeWorkRequest.from(DiagnosticsWorker.class));
    }
    catch (IllegalStateException paramContext)
    {
      Logger.get().error(TAG, "WorkManager is not initialized", new Throwable[] { paramContext });
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/diagnostics/DiagnosticsReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */