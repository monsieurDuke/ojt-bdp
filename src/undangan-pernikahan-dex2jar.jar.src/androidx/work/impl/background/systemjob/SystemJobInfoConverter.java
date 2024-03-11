package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobInfo.TriggerContentUri;
import android.content.ComponentName;
import android.content.Context;
import android.net.NetworkRequest.Builder;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import androidx.core.os.BuildCompat;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ContentUriTriggers;
import androidx.work.ContentUriTriggers.Trigger;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.model.WorkSpec;
import java.util.Iterator;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class SystemJobInfoConverter
{
  static final String EXTRA_IS_PERIODIC = "EXTRA_IS_PERIODIC";
  static final String EXTRA_WORK_SPEC_ID = "EXTRA_WORK_SPEC_ID";
  private static final String TAG;
  private final ComponentName mWorkServiceComponent;
  
  static
  {
    String str = Logger.tagWithPrefix("SystemJobInfoConverter");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  SystemJobInfoConverter(Context paramContext)
  {
    this.mWorkServiceComponent = new ComponentName(paramContext.getApplicationContext(), SystemJobService.class);
  }
  
  private static JobInfo.TriggerContentUri convertContentUriTrigger(ContentUriTriggers.Trigger paramTrigger)
  {
    int i;
    if (paramTrigger.shouldTriggerForDescendants()) {
      i = 1;
    } else {
      i = 0;
    }
    return new JobInfo.TriggerContentUri(paramTrigger.getUri(), i);
  }
  
  static int convertNetworkType(NetworkType paramNetworkType)
  {
    switch (1.$SwitchMap$androidx$work$NetworkType[paramNetworkType.ordinal()])
    {
    default: 
      break;
    case 5: 
      if (Build.VERSION.SDK_INT >= 26) {
        return 4;
      }
      break;
    case 4: 
      if (Build.VERSION.SDK_INT >= 24) {
        return 3;
      }
      break;
    case 3: 
      return 2;
    case 2: 
      return 1;
    case 1: 
      return 0;
    }
    Logger localLogger = Logger.get();
    String str = TAG;
    paramNetworkType = String.format("API version too low. Cannot convert network type value %s", new Object[] { paramNetworkType });
    Log5ECF72.a(paramNetworkType);
    LogE84000.a(paramNetworkType);
    Log229316.a(paramNetworkType);
    localLogger.debug(str, paramNetworkType, new Throwable[0]);
    return 1;
  }
  
  static void setRequiredNetwork(JobInfo.Builder paramBuilder, NetworkType paramNetworkType)
  {
    if ((Build.VERSION.SDK_INT >= 30) && (paramNetworkType == NetworkType.TEMPORARILY_UNMETERED)) {
      paramBuilder.setRequiredNetwork(new NetworkRequest.Builder().addCapability(25).build());
    } else {
      paramBuilder.setRequiredNetworkType(convertNetworkType(paramNetworkType));
    }
  }
  
  JobInfo convert(WorkSpec paramWorkSpec, int paramInt)
  {
    Constraints localConstraints = paramWorkSpec.constraints;
    Object localObject = new PersistableBundle();
    ((PersistableBundle)localObject).putString("EXTRA_WORK_SPEC_ID", paramWorkSpec.id);
    ((PersistableBundle)localObject).putBoolean("EXTRA_IS_PERIODIC", paramWorkSpec.isPeriodic());
    JobInfo.Builder localBuilder = new JobInfo.Builder(paramInt, this.mWorkServiceComponent).setRequiresCharging(localConstraints.requiresCharging()).setRequiresDeviceIdle(localConstraints.requiresDeviceIdle()).setExtras((PersistableBundle)localObject);
    setRequiredNetwork(localBuilder, localConstraints.getRequiredNetworkType());
    if (!localConstraints.requiresDeviceIdle())
    {
      if (paramWorkSpec.backoffPolicy == BackoffPolicy.LINEAR) {
        paramInt = 0;
      } else {
        paramInt = 1;
      }
      localBuilder.setBackoffCriteria(paramWorkSpec.backoffDelayDuration, paramInt);
    }
    long l = Math.max(paramWorkSpec.calculateNextRunTime() - System.currentTimeMillis(), 0L);
    if (Build.VERSION.SDK_INT <= 28) {
      localBuilder.setMinimumLatency(l);
    } else if (l > 0L) {
      localBuilder.setMinimumLatency(l);
    } else if (!paramWorkSpec.expedited) {
      localBuilder.setImportantWhileForeground(true);
    }
    if ((Build.VERSION.SDK_INT >= 24) && (localConstraints.hasContentUriTriggers()))
    {
      localObject = localConstraints.getContentUriTriggers().getTriggers().iterator();
      while (((Iterator)localObject).hasNext()) {
        localBuilder.addTriggerContentUri(convertContentUriTrigger((ContentUriTriggers.Trigger)((Iterator)localObject).next()));
      }
      localBuilder.setTriggerContentUpdateDelay(localConstraints.getTriggerContentUpdateDelay());
      localBuilder.setTriggerContentMaxDelay(localConstraints.getTriggerMaxContentDelay());
    }
    int i = 0;
    localBuilder.setPersisted(false);
    if (Build.VERSION.SDK_INT >= 26)
    {
      localBuilder.setRequiresBatteryNotLow(localConstraints.requiresBatteryNotLow());
      localBuilder.setRequiresStorageNotLow(localConstraints.requiresStorageNotLow());
    }
    if (paramWorkSpec.runAttemptCount > 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (l > 0L) {
      i = 1;
    }
    if ((BuildCompat.isAtLeastS()) && (paramWorkSpec.expedited) && (paramInt == 0) && (i == 0)) {
      localBuilder.setExpedited(true);
    }
    return localBuilder.build();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemjob/SystemJobInfoConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */