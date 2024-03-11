package androidx.work.impl.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import androidx.work.Configuration;
import androidx.work.Logger;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ProcessUtils
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("ProcessUtils");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static String getProcessName(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      paramContext = Application.getProcessName();
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      return paramContext;
    }
    try
    {
      Object localObject3 = Class.forName("android.app.ActivityThread", false, ProcessUtils.class.getClassLoader());
      Object localObject1;
      if (Build.VERSION.SDK_INT >= 18)
      {
        localObject1 = ((Class)localObject3).getDeclaredMethod("currentProcessName", new Class[0]);
        ((Method)localObject1).setAccessible(true);
        localObject1 = ((Method)localObject1).invoke(null, new Object[0]);
      }
      else
      {
        localObject1 = ((Class)localObject3).getDeclaredMethod("currentActivityThread", new Class[0]);
        ((Method)localObject1).setAccessible(true);
        localObject3 = ((Class)localObject3).getDeclaredMethod("getProcessName", new Class[0]);
        ((Method)localObject3).setAccessible(true);
        localObject1 = ((Method)localObject3).invoke(((Method)localObject1).invoke(null, new Object[0]), new Object[0]);
      }
      if ((localObject1 instanceof String))
      {
        localObject1 = (String)localObject1;
        return (String)localObject1;
      }
    }
    finally
    {
      Logger.get().debug(TAG, "Unable to check ActivityThread for processName", new Throwable[] { localObject2 });
    }
    int i = Process.myPid();
    paramContext = (ActivityManager)paramContext.getSystemService("activity");
    if (paramContext != null)
    {
      paramContext = paramContext.getRunningAppProcesses();
      if ((paramContext != null) && (!paramContext.isEmpty()))
      {
        paramContext = paramContext.iterator();
        while (paramContext.hasNext())
        {
          ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)paramContext.next();
          if (localRunningAppProcessInfo.pid == i) {
            return localRunningAppProcessInfo.processName;
          }
        }
      }
    }
    return null;
  }
  
  public static boolean isDefaultProcess(Context paramContext, Configuration paramConfiguration)
  {
    String str = getProcessName(paramContext);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    if (!TextUtils.isEmpty(paramConfiguration.getDefaultProcessName())) {
      return TextUtils.equals(str, paramConfiguration.getDefaultProcessName());
    }
    return TextUtils.equals(str, paramContext.getApplicationInfo().processName);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/ProcessUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */