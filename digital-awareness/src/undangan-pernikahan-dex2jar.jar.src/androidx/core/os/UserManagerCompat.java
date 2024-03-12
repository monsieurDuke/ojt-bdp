package androidx.core.os;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.UserManager;

public class UserManagerCompat
{
  public static boolean isUserUnlocked(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return Api24Impl.isUserUnlocked(paramContext);
    }
    return true;
  }
  
  static class Api24Impl
  {
    static boolean isUserUnlocked(Context paramContext)
    {
      return ((UserManager)paramContext.getSystemService(UserManager.class)).isUserUnlocked();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/UserManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */