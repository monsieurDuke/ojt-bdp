package androidx.core.os;

import android.os.Build.VERSION;
import android.os.Message;

public final class MessageCompat
{
  private static boolean sTryIsAsynchronous = true;
  private static boolean sTrySetAsynchronous = true;
  
  public static boolean isAsynchronous(Message paramMessage)
  {
    if (Build.VERSION.SDK_INT >= 22) {
      return Api22Impl.isAsynchronous(paramMessage);
    }
    if ((sTryIsAsynchronous) && (Build.VERSION.SDK_INT >= 16)) {
      try
      {
        boolean bool = Api22Impl.isAsynchronous(paramMessage);
        return bool;
      }
      catch (NoSuchMethodError paramMessage)
      {
        sTryIsAsynchronous = false;
      }
    }
    return false;
  }
  
  public static void setAsynchronous(Message paramMessage, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 22)
    {
      Api22Impl.setAsynchronous(paramMessage, paramBoolean);
      return;
    }
    if ((sTrySetAsynchronous) && (Build.VERSION.SDK_INT >= 16)) {
      try
      {
        Api22Impl.setAsynchronous(paramMessage, paramBoolean);
      }
      catch (NoSuchMethodError paramMessage)
      {
        sTrySetAsynchronous = false;
      }
    }
  }
  
  static class Api22Impl
  {
    static boolean isAsynchronous(Message paramMessage)
    {
      return paramMessage.isAsynchronous();
    }
    
    static void setAsynchronous(Message paramMessage, boolean paramBoolean)
    {
      paramMessage.setAsynchronous(paramBoolean);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/MessageCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */