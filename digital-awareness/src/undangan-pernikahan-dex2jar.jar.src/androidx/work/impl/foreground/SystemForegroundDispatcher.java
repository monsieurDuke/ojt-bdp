package androidx.work.impl.foreground;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class SystemForegroundDispatcher
  implements WorkConstraintsCallback, ExecutionListener
{
  private static final String ACTION_CANCEL_WORK = "ACTION_CANCEL_WORK";
  private static final String ACTION_NOTIFY = "ACTION_NOTIFY";
  private static final String ACTION_START_FOREGROUND = "ACTION_START_FOREGROUND";
  private static final String ACTION_STOP_FOREGROUND = "ACTION_STOP_FOREGROUND";
  private static final String KEY_FOREGROUND_SERVICE_TYPE = "KEY_FOREGROUND_SERVICE_TYPE";
  private static final String KEY_NOTIFICATION = "KEY_NOTIFICATION";
  private static final String KEY_NOTIFICATION_ID = "KEY_NOTIFICATION_ID";
  private static final String KEY_WORKSPEC_ID = "KEY_WORKSPEC_ID";
  static final String TAG;
  private Callback mCallback;
  final WorkConstraintsTracker mConstraintsTracker;
  private Context mContext;
  String mCurrentForegroundWorkSpecId;
  final Map<String, ForegroundInfo> mForegroundInfoById;
  final Object mLock;
  private final TaskExecutor mTaskExecutor;
  final Set<WorkSpec> mTrackedWorkSpecs;
  private WorkManagerImpl mWorkManagerImpl;
  final Map<String, WorkSpec> mWorkSpecById;
  
  static
  {
    String str = Logger.tagWithPrefix("SystemFgDispatcher");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  SystemForegroundDispatcher(Context paramContext)
  {
    this.mContext = paramContext;
    this.mLock = new Object();
    paramContext = WorkManagerImpl.getInstance(this.mContext);
    this.mWorkManagerImpl = paramContext;
    paramContext = paramContext.getWorkTaskExecutor();
    this.mTaskExecutor = paramContext;
    this.mCurrentForegroundWorkSpecId = null;
    this.mForegroundInfoById = new LinkedHashMap();
    this.mTrackedWorkSpecs = new HashSet();
    this.mWorkSpecById = new HashMap();
    this.mConstraintsTracker = new WorkConstraintsTracker(this.mContext, paramContext, this);
    this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
  }
  
  SystemForegroundDispatcher(Context paramContext, WorkManagerImpl paramWorkManagerImpl, WorkConstraintsTracker paramWorkConstraintsTracker)
  {
    this.mContext = paramContext;
    this.mLock = new Object();
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mTaskExecutor = paramWorkManagerImpl.getWorkTaskExecutor();
    this.mCurrentForegroundWorkSpecId = null;
    this.mForegroundInfoById = new LinkedHashMap();
    this.mTrackedWorkSpecs = new HashSet();
    this.mWorkSpecById = new HashMap();
    this.mConstraintsTracker = paramWorkConstraintsTracker;
    this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
  }
  
  public static Intent createCancelWorkIntent(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, SystemForegroundService.class);
    localIntent.setAction("ACTION_CANCEL_WORK");
    paramContext = String.format("workspec://%s", new Object[] { paramString });
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    localIntent.setData(Uri.parse(paramContext));
    localIntent.putExtra("KEY_WORKSPEC_ID", paramString);
    return localIntent;
  }
  
  public static Intent createNotifyIntent(Context paramContext, String paramString, ForegroundInfo paramForegroundInfo)
  {
    paramContext = new Intent(paramContext, SystemForegroundService.class);
    paramContext.setAction("ACTION_NOTIFY");
    paramContext.putExtra("KEY_NOTIFICATION_ID", paramForegroundInfo.getNotificationId());
    paramContext.putExtra("KEY_FOREGROUND_SERVICE_TYPE", paramForegroundInfo.getForegroundServiceType());
    paramContext.putExtra("KEY_NOTIFICATION", paramForegroundInfo.getNotification());
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  public static Intent createStartForegroundIntent(Context paramContext, String paramString, ForegroundInfo paramForegroundInfo)
  {
    paramContext = new Intent(paramContext, SystemForegroundService.class);
    paramContext.setAction("ACTION_START_FOREGROUND");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    paramContext.putExtra("KEY_NOTIFICATION_ID", paramForegroundInfo.getNotificationId());
    paramContext.putExtra("KEY_FOREGROUND_SERVICE_TYPE", paramForegroundInfo.getForegroundServiceType());
    paramContext.putExtra("KEY_NOTIFICATION", paramForegroundInfo.getNotification());
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  public static Intent createStopForegroundIntent(Context paramContext)
  {
    paramContext = new Intent(paramContext, SystemForegroundService.class);
    paramContext.setAction("ACTION_STOP_FOREGROUND");
    return paramContext;
  }
  
  private void handleCancelWork(Intent paramIntent)
  {
    Logger localLogger = Logger.get();
    String str1 = TAG;
    String str2 = String.format("Stopping foreground work for %s", new Object[] { paramIntent });
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    localLogger.info(str1, str2, new Throwable[0]);
    paramIntent = paramIntent.getStringExtra("KEY_WORKSPEC_ID");
    if ((paramIntent != null) && (!TextUtils.isEmpty(paramIntent))) {
      this.mWorkManagerImpl.cancelWorkById(UUID.fromString(paramIntent));
    }
  }
  
  private void handleNotify(Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("KEY_NOTIFICATION_ID", 0);
    int j = paramIntent.getIntExtra("KEY_FOREGROUND_SERVICE_TYPE", 0);
    String str1 = paramIntent.getStringExtra("KEY_WORKSPEC_ID");
    paramIntent = (Notification)paramIntent.getParcelableExtra("KEY_NOTIFICATION");
    Logger localLogger = Logger.get();
    String str2 = TAG;
    Object localObject = String.format("Notifying with (id: %s, workSpecId: %s, notificationType: %s)", new Object[] { Integer.valueOf(i), str1, Integer.valueOf(j) });
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    localLogger.debug(str2, (String)localObject, new Throwable[0]);
    if ((paramIntent != null) && (this.mCallback != null))
    {
      localObject = new ForegroundInfo(i, paramIntent, j);
      this.mForegroundInfoById.put(str1, localObject);
      if (TextUtils.isEmpty(this.mCurrentForegroundWorkSpecId))
      {
        this.mCurrentForegroundWorkSpecId = str1;
        this.mCallback.startForeground(i, j, paramIntent);
      }
      else
      {
        this.mCallback.notify(i, paramIntent);
        if ((j != 0) && (Build.VERSION.SDK_INT >= 29))
        {
          i = 0;
          paramIntent = this.mForegroundInfoById.entrySet().iterator();
          while (paramIntent.hasNext()) {
            i |= ((ForegroundInfo)((Map.Entry)paramIntent.next()).getValue()).getForegroundServiceType();
          }
          paramIntent = (ForegroundInfo)this.mForegroundInfoById.get(this.mCurrentForegroundWorkSpecId);
          if (paramIntent != null) {
            this.mCallback.startForeground(paramIntent.getNotificationId(), i, paramIntent.getNotification());
          }
        }
      }
    }
  }
  
  private void handleStartForeground(final Intent paramIntent)
  {
    Logger localLogger = Logger.get();
    String str2 = TAG;
    final String str1 = String.format("Started foreground service %s", new Object[] { paramIntent });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    localLogger.info(str2, str1, new Throwable[0]);
    str1 = paramIntent.getStringExtra("KEY_WORKSPEC_ID");
    paramIntent = this.mWorkManagerImpl.getWorkDatabase();
    this.mTaskExecutor.executeOnBackgroundThread(new Runnable()
    {
      public void run()
      {
        WorkSpec localWorkSpec = paramIntent.workSpecDao().getWorkSpec(str1);
        if ((localWorkSpec != null) && (localWorkSpec.hasConstraints())) {
          synchronized (SystemForegroundDispatcher.this.mLock)
          {
            SystemForegroundDispatcher.this.mWorkSpecById.put(str1, localWorkSpec);
            SystemForegroundDispatcher.this.mTrackedWorkSpecs.add(localWorkSpec);
            SystemForegroundDispatcher.this.mConstraintsTracker.replace(SystemForegroundDispatcher.this.mTrackedWorkSpecs);
          }
        }
      }
    });
  }
  
  WorkManagerImpl getWorkManager()
  {
    return this.mWorkManagerImpl;
  }
  
  void handleStop(Intent paramIntent)
  {
    Logger.get().info(TAG, "Stopping foreground service", new Throwable[0]);
    paramIntent = this.mCallback;
    if (paramIntent != null) {
      paramIntent.stop();
    }
  }
  
  public void onAllConstraintsMet(List<String> paramList) {}
  
  public void onAllConstraintsNotMet(List<String> paramList)
  {
    if (!paramList.isEmpty())
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        paramList = Logger.get();
        String str1 = TAG;
        String str3 = String.format("Constraints unmet for WorkSpec %s", new Object[] { str2 });
        Log5ECF72.a(str3);
        LogE84000.a(str3);
        Log229316.a(str3);
        paramList.debug(str1, str3, new Throwable[0]);
        this.mWorkManagerImpl.stopForegroundWork(str2);
      }
    }
  }
  
  void onDestroy()
  {
    this.mCallback = null;
    synchronized (this.mLock)
    {
      this.mConstraintsTracker.reset();
      this.mWorkManagerImpl.getProcessor().removeExecutionListener(this);
      return;
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    paramBoolean = false;
    synchronized (this.mLock)
    {
      Object localObject2 = (WorkSpec)this.mWorkSpecById.remove(paramString);
      if (localObject2 != null) {
        paramBoolean = this.mTrackedWorkSpecs.remove(localObject2);
      }
      if (paramBoolean) {
        this.mConstraintsTracker.replace(this.mTrackedWorkSpecs);
      }
      localObject2 = (ForegroundInfo)this.mForegroundInfoById.remove(paramString);
      if ((paramString.equals(this.mCurrentForegroundWorkSpecId)) && (this.mForegroundInfoById.size() > 0))
      {
        localObject3 = this.mForegroundInfoById.entrySet().iterator();
        for (??? = (Map.Entry)((Iterator)localObject3).next(); ((Iterator)localObject3).hasNext(); ??? = (Map.Entry)((Iterator)localObject3).next()) {}
        this.mCurrentForegroundWorkSpecId = ((String)((Map.Entry)???).getKey());
        if (this.mCallback != null)
        {
          ??? = (ForegroundInfo)((Map.Entry)???).getValue();
          this.mCallback.startForeground(((ForegroundInfo)???).getNotificationId(), ((ForegroundInfo)???).getForegroundServiceType(), ((ForegroundInfo)???).getNotification());
          this.mCallback.cancelNotification(((ForegroundInfo)???).getNotificationId());
        }
      }
      Object localObject3 = this.mCallback;
      if ((localObject2 != null) && (localObject3 != null))
      {
        ??? = Logger.get();
        String str = TAG;
        paramString = String.format("Removing Notification (id: %s, workSpecId: %s ,notificationType: %s)", new Object[] { Integer.valueOf(((ForegroundInfo)localObject2).getNotificationId()), paramString, Integer.valueOf(((ForegroundInfo)localObject2).getForegroundServiceType()) });
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        ((Logger)???).debug(str, paramString, new Throwable[0]);
        ((Callback)localObject3).cancelNotification(((ForegroundInfo)localObject2).getNotificationId());
      }
      return;
    }
  }
  
  void onStartCommand(Intent paramIntent)
  {
    String str = paramIntent.getAction();
    if ("ACTION_START_FOREGROUND".equals(str))
    {
      handleStartForeground(paramIntent);
      handleNotify(paramIntent);
    }
    else if ("ACTION_NOTIFY".equals(str))
    {
      handleNotify(paramIntent);
    }
    else if ("ACTION_CANCEL_WORK".equals(str))
    {
      handleCancelWork(paramIntent);
    }
    else if ("ACTION_STOP_FOREGROUND".equals(str))
    {
      handleStop(paramIntent);
    }
  }
  
  void setCallback(Callback paramCallback)
  {
    if (this.mCallback != null)
    {
      Logger.get().error(TAG, "A callback already exists.", new Throwable[0]);
      return;
    }
    this.mCallback = paramCallback;
  }
  
  static abstract interface Callback
  {
    public abstract void cancelNotification(int paramInt);
    
    public abstract void notify(int paramInt, Notification paramNotification);
    
    public abstract void startForeground(int paramInt1, int paramInt2, Notification paramNotification);
    
    public abstract void stop();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/foreground/SystemForegroundDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */