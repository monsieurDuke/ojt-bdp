package androidx.core.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build.VERSION;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class AppOpsManagerCompat
{
  public static final int MODE_ALLOWED = 0;
  public static final int MODE_DEFAULT = 3;
  public static final int MODE_ERRORED = 2;
  public static final int MODE_IGNORED = 1;
  
  public static int checkOrNoteProxyOp(Context paramContext, int paramInt, String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 29)
    {
      AppOpsManager localAppOpsManager = Api29Impl.getSystemService(paramContext);
      int i = Api29Impl.checkOpNoThrow(localAppOpsManager, paramString1, Binder.getCallingUid(), paramString2);
      if (i != 0) {
        return i;
      }
      paramContext = Api29Impl.getOpPackageName(paramContext);
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      return Api29Impl.checkOpNoThrow(localAppOpsManager, paramString1, paramInt, paramContext);
    }
    return noteProxyOpNoThrow(paramContext, paramString1, paramString2);
  }
  
  public static int noteOp(Context paramContext, String paramString1, int paramInt, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.noteOp((AppOpsManager)paramContext.getSystemService("appops"), paramString1, paramInt, paramString2);
    }
    return 1;
  }
  
  public static int noteOpNoThrow(Context paramContext, String paramString1, int paramInt, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.noteOpNoThrow((AppOpsManager)paramContext.getSystemService("appops"), paramString1, paramInt, paramString2);
    }
    return 1;
  }
  
  public static int noteProxyOp(Context paramContext, String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.noteProxyOp((AppOpsManager)Api23Impl.getSystemService(paramContext, AppOpsManager.class), paramString1, paramString2);
    }
    return 1;
  }
  
  public static int noteProxyOpNoThrow(Context paramContext, String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.noteProxyOpNoThrow((AppOpsManager)Api23Impl.getSystemService(paramContext, AppOpsManager.class), paramString1, paramString2);
    }
    return 1;
  }
  
  public static String permissionToOp(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramString = Api23Impl.permissionToOp(paramString);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      return paramString;
    }
    return null;
  }
  
  static class Api19Impl
  {
    static int noteOp(AppOpsManager paramAppOpsManager, String paramString1, int paramInt, String paramString2)
    {
      return paramAppOpsManager.noteOp(paramString1, paramInt, paramString2);
    }
    
    static int noteOpNoThrow(AppOpsManager paramAppOpsManager, String paramString1, int paramInt, String paramString2)
    {
      return paramAppOpsManager.noteOpNoThrow(paramString1, paramInt, paramString2);
    }
  }
  
  static class Api23Impl
  {
    static <T> T getSystemService(Context paramContext, Class<T> paramClass)
    {
      return (T)paramContext.getSystemService(paramClass);
    }
    
    static int noteProxyOp(AppOpsManager paramAppOpsManager, String paramString1, String paramString2)
    {
      return paramAppOpsManager.noteProxyOp(paramString1, paramString2);
    }
    
    static int noteProxyOpNoThrow(AppOpsManager paramAppOpsManager, String paramString1, String paramString2)
    {
      return paramAppOpsManager.noteProxyOpNoThrow(paramString1, paramString2);
    }
    
    static String permissionToOp(String paramString)
    {
      paramString = AppOpsManager.permissionToOp(paramString);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      return paramString;
    }
  }
  
  static class Api29Impl
  {
    static int checkOpNoThrow(AppOpsManager paramAppOpsManager, String paramString1, int paramInt, String paramString2)
    {
      if (paramAppOpsManager == null) {
        return 1;
      }
      return paramAppOpsManager.checkOpNoThrow(paramString1, paramInt, paramString2);
    }
    
    static String getOpPackageName(Context paramContext)
    {
      return paramContext.getOpPackageName();
    }
    
    static AppOpsManager getSystemService(Context paramContext)
    {
      return (AppOpsManager)paramContext.getSystemService(AppOpsManager.class);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/AppOpsManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */