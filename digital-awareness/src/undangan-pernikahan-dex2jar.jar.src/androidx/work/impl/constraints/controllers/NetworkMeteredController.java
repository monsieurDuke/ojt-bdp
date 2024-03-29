package androidx.work.impl.constraints.controllers;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Constraints;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class NetworkMeteredController
  extends ConstraintController<NetworkState>
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("NetworkMeteredCtrlr");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public NetworkMeteredController(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(Trackers.getInstance(paramContext, paramTaskExecutor).getNetworkStateTracker());
  }
  
  boolean hasConstraint(WorkSpec paramWorkSpec)
  {
    boolean bool;
    if (paramWorkSpec.constraints.getRequiredNetworkType() == NetworkType.METERED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isConstrained(NetworkState paramNetworkState)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = true;
    if (i < 26)
    {
      Logger.get().debug(TAG, "Metered network constraint is not supported before API 26, only checking for connected state.", new Throwable[0]);
      return paramNetworkState.isConnected() ^ true;
    }
    boolean bool1 = bool2;
    if (paramNetworkState.isConnected()) {
      if (!paramNetworkState.isMetered()) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/controllers/NetworkMeteredController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */