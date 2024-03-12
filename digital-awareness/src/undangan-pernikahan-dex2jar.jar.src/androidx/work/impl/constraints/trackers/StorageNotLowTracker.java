package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class StorageNotLowTracker
  extends BroadcastReceiverConstraintTracker<Boolean>
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("StorageNotLowTracker");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public StorageNotLowTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(paramContext, paramTaskExecutor);
  }
  
  public Boolean getInitialState()
  {
    Object localObject = this.mAppContext.registerReceiver(null, getIntentFilter());
    int i = 1;
    Boolean localBoolean = Boolean.valueOf(true);
    if ((localObject != null) && (((Intent)localObject).getAction() != null))
    {
      localObject = ((Intent)localObject).getAction();
      switch (((String)localObject).hashCode())
      {
      }
      for (;;)
      {
        break;
        if (((String)localObject).equals("android.intent.action.DEVICE_STORAGE_OK"))
        {
          i = 0;
          break label99;
          if (((String)localObject).equals("android.intent.action.DEVICE_STORAGE_LOW")) {
            break label99;
          }
        }
      }
      i = -1;
      switch (i)
      {
      default: 
        return null;
      case 1: 
        label99:
        return Boolean.valueOf(false);
      }
      return localBoolean;
    }
    return localBoolean;
  }
  
  public IntentFilter getIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
    localIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
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
      if (paramContext.equals("android.intent.action.DEVICE_STORAGE_OK"))
      {
        i = 0;
        break;
        if (paramContext.equals("android.intent.action.DEVICE_STORAGE_LOW")) {
          i = 1;
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/trackers/StorageNotLowTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */