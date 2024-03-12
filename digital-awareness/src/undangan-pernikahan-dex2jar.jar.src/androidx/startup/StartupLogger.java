package androidx.startup;

import android.util.Log;

public final class StartupLogger
{
  static final boolean DEBUG = false;
  private static final String TAG = "StartupLogger";
  
  public static void e(String paramString, Throwable paramThrowable)
  {
    Log.e("StartupLogger", paramString, paramThrowable);
  }
  
  public static void i(String paramString)
  {
    Log.i("StartupLogger", paramString);
  }
  
  public static void w(String paramString)
  {
    Log.w("StartupLogger", paramString);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/startup/StartupLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */