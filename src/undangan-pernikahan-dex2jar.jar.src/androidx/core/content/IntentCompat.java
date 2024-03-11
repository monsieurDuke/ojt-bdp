package androidx.core.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import androidx.core.util.Preconditions;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class IntentCompat
{
  public static final String ACTION_CREATE_REMINDER = "android.intent.action.CREATE_REMINDER";
  public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
  public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
  public static final String EXTRA_START_PLAYBACK = "android.intent.extra.START_PLAYBACK";
  public static final String EXTRA_TIME = "android.intent.extra.TIME";
  
  public static Intent createManageUnusedAppRestrictionsIntent(Context paramContext, String paramString)
  {
    if (PackageManagerCompat.areUnusedAppRestrictionsAvailable(paramContext.getPackageManager()))
    {
      if (Build.VERSION.SDK_INT >= 31) {
        return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", paramString, null));
      }
      paramString = new Intent("android.intent.action.AUTO_REVOKE_PERMISSIONS").setData(Uri.fromParts("package", paramString, null));
      if (Build.VERSION.SDK_INT >= 30) {
        return paramString;
      }
      paramContext = PackageManagerCompat.getPermissionRevocationVerifierApp(paramContext.getPackageManager());
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      return paramString.setPackage((String)Preconditions.checkNotNull(paramContext));
    }
    throw new UnsupportedOperationException("Unused App Restriction features are not available on this device");
  }
  
  public static Intent makeMainSelectorActivity(String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 15) {
      return Api15Impl.makeMainSelectorActivity(paramString1, paramString2);
    }
    paramString1 = new Intent(paramString1);
    paramString1.addCategory(paramString2);
    return paramString1;
  }
  
  static class Api15Impl
  {
    static Intent makeMainSelectorActivity(String paramString1, String paramString2)
    {
      return Intent.makeMainSelectorActivity(paramString1, paramString2);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/IntentCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */