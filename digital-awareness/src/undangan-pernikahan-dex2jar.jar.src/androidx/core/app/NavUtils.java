package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class NavUtils
{
  public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
  private static final String TAG = "NavUtils";
  
  public static Intent getParentActivityIntent(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      localObject = Api16Impl.getParentActivityIntent(paramActivity);
      if (localObject != null) {
        return (Intent)localObject;
      }
    }
    Object localObject = getParentActivityName(paramActivity);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    if (localObject == null) {
      return null;
    }
    ComponentName localComponentName = new ComponentName(paramActivity, (String)localObject);
    try
    {
      paramActivity = getParentActivityName(paramActivity, localComponentName);
      Log5ECF72.a(paramActivity);
      LogE84000.a(paramActivity);
      Log229316.a(paramActivity);
      if (paramActivity == null)
      {
        paramActivity = Intent.makeMainActivity(localComponentName);
      }
      else
      {
        paramActivity = new android/content/Intent;
        paramActivity.<init>();
        paramActivity = paramActivity.setComponent(localComponentName);
      }
      return paramActivity;
    }
    catch (PackageManager.NameNotFoundException paramActivity)
    {
      Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + (String)localObject + "' in manifest");
    }
    return null;
  }
  
  public static Intent getParentActivityIntent(Context paramContext, ComponentName paramComponentName)
    throws PackageManager.NameNotFoundException
  {
    String str = getParentActivityName(paramContext, paramComponentName);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    if (str == null) {
      return null;
    }
    paramComponentName = new ComponentName(paramComponentName.getPackageName(), str);
    paramContext = getParentActivityName(paramContext, paramComponentName);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    if (paramContext == null) {
      paramContext = Intent.makeMainActivity(paramComponentName);
    } else {
      paramContext = new Intent().setComponent(paramComponentName);
    }
    return paramContext;
  }
  
  public static Intent getParentActivityIntent(Context paramContext, Class<?> paramClass)
    throws PackageManager.NameNotFoundException
  {
    paramClass = getParentActivityName(paramContext, new ComponentName(paramContext, paramClass));
    Log5ECF72.a(paramClass);
    LogE84000.a(paramClass);
    Log229316.a(paramClass);
    if (paramClass == null) {
      return null;
    }
    paramClass = new ComponentName(paramContext, paramClass);
    paramContext = getParentActivityName(paramContext, paramClass);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    if (paramContext == null) {
      paramContext = Intent.makeMainActivity(paramClass);
    } else {
      paramContext = new Intent().setComponent(paramClass);
    }
    return paramContext;
  }
  
  public static String getParentActivityName(Activity paramActivity)
  {
    try
    {
      paramActivity = getParentActivityName(paramActivity, paramActivity.getComponentName());
      Log5ECF72.a(paramActivity);
      LogE84000.a(paramActivity);
      Log229316.a(paramActivity);
      return paramActivity;
    }
    catch (PackageManager.NameNotFoundException paramActivity)
    {
      throw new IllegalArgumentException(paramActivity);
    }
  }
  
  public static String getParentActivityName(Context paramContext, ComponentName paramComponentName)
    throws PackageManager.NameNotFoundException
  {
    Object localObject = paramContext.getPackageManager();
    int j;
    if (Build.VERSION.SDK_INT >= 24) {
      j = 0x80 | 0x200;
    } else {
      j = 0x80 | 0x200;
    }
    int i;
    if (Build.VERSION.SDK_INT >= 29)
    {
      i = j | 0x100C0000;
    }
    else
    {
      i = j;
      if (Build.VERSION.SDK_INT >= 24) {
        i = j | 0xC0000;
      }
    }
    paramComponentName = ((PackageManager)localObject).getActivityInfo(paramComponentName, i);
    if (Build.VERSION.SDK_INT >= 16)
    {
      localObject = paramComponentName.parentActivityName;
      if (localObject != null) {
        return (String)localObject;
      }
    }
    if (paramComponentName.metaData == null) {
      return null;
    }
    localObject = paramComponentName.metaData.getString("android.support.PARENT_ACTIVITY");
    if (localObject == null) {
      return null;
    }
    paramComponentName = (ComponentName)localObject;
    if (((String)localObject).charAt(0) == '.') {
      paramComponentName = paramContext.getPackageName() + (String)localObject;
    }
    return paramComponentName;
  }
  
  public static void navigateUpFromSameTask(Activity paramActivity)
  {
    Intent localIntent = getParentActivityIntent(paramActivity);
    if (localIntent != null)
    {
      navigateUpTo(paramActivity, localIntent);
      return;
    }
    throw new IllegalArgumentException("Activity " + paramActivity.getClass().getSimpleName() + " does not have a parent activity name specified. (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data>  element in your manifest?)");
  }
  
  public static void navigateUpTo(Activity paramActivity, Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      Api16Impl.navigateUpTo(paramActivity, paramIntent);
    }
    else
    {
      paramIntent.addFlags(67108864);
      paramActivity.startActivity(paramIntent);
      paramActivity.finish();
    }
  }
  
  public static boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.shouldUpRecreateTask(paramActivity, paramIntent);
    }
    paramActivity = paramActivity.getIntent().getAction();
    boolean bool;
    if ((paramActivity != null) && (!paramActivity.equals("android.intent.action.MAIN"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static class Api16Impl
  {
    static Intent getParentActivityIntent(Activity paramActivity)
    {
      return paramActivity.getParentActivityIntent();
    }
    
    static boolean navigateUpTo(Activity paramActivity, Intent paramIntent)
    {
      return paramActivity.navigateUpTo(paramIntent);
    }
    
    static boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
    {
      return paramActivity.shouldUpRecreateTask(paramIntent);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/NavUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */