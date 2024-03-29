package androidx.core.app;

import android.app.Service;
import android.os.Build.VERSION;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ServiceCompat
{
  public static final int START_STICKY = 1;
  public static final int STOP_FOREGROUND_DETACH = 2;
  public static final int STOP_FOREGROUND_REMOVE = 1;
  
  public static void stopForeground(Service paramService, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      Api24Impl.stopForeground(paramService, paramInt);
    }
    else
    {
      boolean bool;
      if ((paramInt & 0x1) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      paramService.stopForeground(bool);
    }
  }
  
  static class Api24Impl
  {
    static void stopForeground(Service paramService, int paramInt)
    {
      paramService.stopForeground(paramInt);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface StopForegroundFlags {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/ServiceCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */