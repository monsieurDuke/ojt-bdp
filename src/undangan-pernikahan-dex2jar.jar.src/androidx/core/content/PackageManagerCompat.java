package androidx.core.content;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Log;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.os.UserManagerCompat;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class PackageManagerCompat
{
  public static final String ACTION_PERMISSION_REVOCATION_SETTINGS = "android.intent.action.AUTO_REVOKE_PERMISSIONS";
  public static final String LOG_TAG = "PackageManagerCompat";
  
  public static boolean areUnusedAppRestrictionsAvailable(PackageManager paramPackageManager)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = true;
    if (i >= 30) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if ((Build.VERSION.SDK_INT >= 23) && (Build.VERSION.SDK_INT < 30)) {
      j = 1;
    } else {
      j = 0;
    }
    paramPackageManager = getPermissionRevocationVerifierApp(paramPackageManager);
    Log5ECF72.a(paramPackageManager);
    LogE84000.a(paramPackageManager);
    Log229316.a(paramPackageManager);
    int k;
    if (paramPackageManager != null) {
      k = 1;
    } else {
      k = 0;
    }
    boolean bool1 = bool2;
    if (i == 0) {
      if ((j != 0) && (k != 0)) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  public static String getPermissionRevocationVerifierApp(PackageManager paramPackageManager)
  {
    Object localObject1 = new Intent("android.intent.action.AUTO_REVOKE_PERMISSIONS").setData(Uri.fromParts("package", "com.example", null));
    Object localObject2 = paramPackageManager.queryIntentActivities((Intent)localObject1, 0);
    localObject1 = null;
    Iterator localIterator = ((List)localObject2).iterator();
    while (localIterator.hasNext())
    {
      localObject2 = ((ResolveInfo)localIterator.next()).activityInfo.packageName;
      if (paramPackageManager.checkPermission("android.permission.PACKAGE_VERIFICATION_AGENT", (String)localObject2) == 0)
      {
        if (localObject1 != null) {
          return (String)localObject1;
        }
        localObject1 = localObject2;
      }
    }
    return (String)localObject1;
  }
  
  public static ListenableFuture<Integer> getUnusedAppRestrictionsStatus(Context paramContext)
  {
    ResolvableFuture localResolvableFuture = ResolvableFuture.create();
    boolean bool = UserManagerCompat.isUserUnlocked(paramContext);
    Integer localInteger = Integer.valueOf(0);
    if (!bool)
    {
      localResolvableFuture.set(localInteger);
      Log.e("PackageManagerCompat", "User is in locked direct boot mode");
      return localResolvableFuture;
    }
    if (!areUnusedAppRestrictionsAvailable(paramContext.getPackageManager()))
    {
      localResolvableFuture.set(Integer.valueOf(1));
      return localResolvableFuture;
    }
    int j = paramContext.getApplicationInfo().targetSdkVersion;
    if (j < 30)
    {
      localResolvableFuture.set(localInteger);
      Log.e("PackageManagerCompat", "Target SDK version below API 30");
      return localResolvableFuture;
    }
    int k = Build.VERSION.SDK_INT;
    int i = 4;
    if (k >= 31)
    {
      if (Api30Impl.areUnusedAppRestrictionsEnabled(paramContext))
      {
        if (j >= 31) {
          i = 5;
        }
        localResolvableFuture.set(Integer.valueOf(i));
      }
      else
      {
        localResolvableFuture.set(Integer.valueOf(2));
      }
      return localResolvableFuture;
    }
    if (Build.VERSION.SDK_INT == 30)
    {
      if (!Api30Impl.areUnusedAppRestrictionsEnabled(paramContext)) {
        i = 2;
      }
      localResolvableFuture.set(Integer.valueOf(i));
      return localResolvableFuture;
    }
    paramContext = new UnusedAppRestrictionsBackportServiceConnection(paramContext);
    Objects.requireNonNull(paramContext);
    localResolvableFuture.addListener(new PackageManagerCompat..ExternalSyntheticLambda0(paramContext), Executors.newSingleThreadExecutor());
    paramContext.connectAndFetchResult(localResolvableFuture);
    return localResolvableFuture;
  }
  
  private static class Api30Impl
  {
    static boolean areUnusedAppRestrictionsEnabled(Context paramContext)
    {
      return paramContext.getPackageManager().isAutoRevokeWhitelisted() ^ true;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface UnusedAppRestrictionsStatus {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/PackageManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */