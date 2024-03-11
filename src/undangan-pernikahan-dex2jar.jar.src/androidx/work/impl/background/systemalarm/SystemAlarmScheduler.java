package androidx.work.impl.background.systemalarm;

import android.content.Context;
import androidx.work.Logger;
import androidx.work.impl.Scheduler;
import androidx.work.impl.model.WorkSpec;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class SystemAlarmScheduler
  implements Scheduler
{
  private static final String TAG;
  private final Context mContext;
  
  static
  {
    String str = Logger.tagWithPrefix("SystemAlarmScheduler");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public SystemAlarmScheduler(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }
  
  private void scheduleWorkSpec(WorkSpec paramWorkSpec)
  {
    Logger localLogger = Logger.get();
    String str2 = TAG;
    String str1 = String.format("Scheduling work with workSpecId %s", new Object[] { paramWorkSpec.id });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    localLogger.debug(str2, str1, new Throwable[0]);
    paramWorkSpec = CommandHandler.createScheduleWorkIntent(this.mContext, paramWorkSpec.id);
    this.mContext.startService(paramWorkSpec);
  }
  
  public void cancel(String paramString)
  {
    paramString = CommandHandler.createStopWorkIntent(this.mContext, paramString);
    this.mContext.startService(paramString);
  }
  
  public boolean hasLimitedSchedulingSlots()
  {
    return true;
  }
  
  public void schedule(WorkSpec... paramVarArgs)
  {
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      scheduleWorkSpec(paramVarArgs[i]);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/background/systemalarm/SystemAlarmScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */