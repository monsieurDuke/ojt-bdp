package androidx.core.os;

import android.os.Build.VERSION;
import java.lang.annotation.Annotation;
import java.util.Locale;

public class BuildCompat
{
  @Deprecated
  public static boolean isAtLeastN()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastNMR1()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 25) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastO()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 26) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastOMR1()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 27) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastP()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 28) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected static boolean isAtLeastPreReleaseCodename(String paramString1, String paramString2)
  {
    boolean bool2 = "REL".equals(paramString2);
    boolean bool1 = false;
    if (bool2) {
      return false;
    }
    if (paramString2.toUpperCase(Locale.ROOT).compareTo(paramString1.toUpperCase(Locale.ROOT)) >= 0) {
      bool1 = true;
    }
    return bool1;
  }
  
  @Deprecated
  public static boolean isAtLeastQ()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 29) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastR()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 30) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastS()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT < 31) && ((Build.VERSION.SDK_INT < 30) || (!isAtLeastPreReleaseCodename("S", Build.VERSION.CODENAME)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastSv2()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT < 32) && ((Build.VERSION.SDK_INT < 31) || (!isAtLeastPreReleaseCodename("Sv2", Build.VERSION.CODENAME)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isAtLeastT()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT < 33) && ((Build.VERSION.SDK_INT < 32) || (!isAtLeastPreReleaseCodename("Tiramisu", Build.VERSION.CODENAME)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isAtLeastU()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 33) && (isAtLeastPreReleaseCodename("UpsideDownCake", Build.VERSION.CODENAME))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static @interface PrereleaseSdkCheck {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/BuildCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */