package androidx.core.content.pm;

import android.content.pm.PermissionInfo;
import android.os.Build.VERSION;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionInfoCompat
{
  public static int getProtection(PermissionInfo paramPermissionInfo)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getProtection(paramPermissionInfo);
    }
    return paramPermissionInfo.protectionLevel & 0xF;
  }
  
  public static int getProtectionFlags(PermissionInfo paramPermissionInfo)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getProtectionFlags(paramPermissionInfo);
    }
    return paramPermissionInfo.protectionLevel & 0xFFFFFFF0;
  }
  
  static class Api28Impl
  {
    static int getProtection(PermissionInfo paramPermissionInfo)
    {
      return paramPermissionInfo.getProtection();
    }
    
    static int getProtectionFlags(PermissionInfo paramPermissionInfo)
    {
      return paramPermissionInfo.getProtectionFlags();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Protection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ProtectionFlags {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/pm/PermissionInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */