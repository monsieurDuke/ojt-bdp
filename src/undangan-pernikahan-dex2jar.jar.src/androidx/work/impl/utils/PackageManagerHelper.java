package androidx.work.impl.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.work.Logger;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class PackageManagerHelper
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("PackageManagerHelper");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static boolean isComponentExplicitlyEnabled(Context paramContext, Class<?> paramClass)
  {
    return isComponentExplicitlyEnabled(paramContext, paramClass.getName());
  }
  
  public static boolean isComponentExplicitlyEnabled(Context paramContext, String paramString)
  {
    int i = paramContext.getPackageManager().getComponentEnabledSetting(new ComponentName(paramContext, paramString));
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public static void setComponentEnabled(Context paramContext, Class<?> paramClass, boolean paramBoolean)
  {
    String str1 = "enabled";
    try
    {
      localObject1 = paramContext.getPackageManager();
      Object localObject2 = new android/content/ComponentName;
      ((ComponentName)localObject2).<init>(paramContext, paramClass.getName());
      int i;
      if (paramBoolean) {
        i = 1;
      } else {
        i = 2;
      }
      ((PackageManager)localObject1).setComponentEnabledSetting((ComponentName)localObject2, i, 1);
      localObject1 = Logger.get();
      localObject2 = TAG;
      str2 = paramClass.getName();
      if (paramBoolean) {
        paramContext = "enabled";
      } else {
        paramContext = "disabled";
      }
      paramContext = String.format("%s %s", new Object[] { str2, paramContext });
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      ((Logger)localObject1).debug((String)localObject2, paramContext, new Throwable[0]);
    }
    catch (Exception localException)
    {
      Object localObject1 = Logger.get();
      String str2 = TAG;
      paramClass = paramClass.getName();
      if (paramBoolean) {
        paramContext = str1;
      } else {
        paramContext = "disabled";
      }
      paramContext = String.format("%s could not be %s", new Object[] { paramClass, paramContext });
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      ((Logger)localObject1).debug(str2, paramContext, new Throwable[] { localException });
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/PackageManagerHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */