package androidx.core.os;

import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class EnvironmentCompat
{
  public static final String MEDIA_UNKNOWN = "unknown";
  private static final String TAG = "EnvironmentCompat";
  
  public static String getStorageState(File paramFile)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramFile = Api21Impl.getExternalStorageState(paramFile);
      Log5ECF72.a(paramFile);
      LogE84000.a(paramFile);
      Log229316.a(paramFile);
      return paramFile;
    }
    if (Build.VERSION.SDK_INT >= 19)
    {
      paramFile = Api19Impl.getStorageState(paramFile);
      Log5ECF72.a(paramFile);
      LogE84000.a(paramFile);
      Log229316.a(paramFile);
      return paramFile;
    }
    try
    {
      if (paramFile.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath()))
      {
        paramFile = Environment.getExternalStorageState();
        Log5ECF72.a(paramFile);
        LogE84000.a(paramFile);
        Log229316.a(paramFile);
        return paramFile;
      }
    }
    catch (IOException paramFile)
    {
      Log.w("EnvironmentCompat", "Failed to resolve canonical path: " + paramFile);
    }
    return "unknown";
  }
  
  static class Api19Impl
  {
    static String getStorageState(File paramFile)
    {
      paramFile = Environment.getStorageState(paramFile);
      Log5ECF72.a(paramFile);
      LogE84000.a(paramFile);
      Log229316.a(paramFile);
      return paramFile;
    }
  }
  
  static class Api21Impl
  {
    static String getExternalStorageState(File paramFile)
    {
      paramFile = Environment.getExternalStorageState(paramFile);
      Log5ECF72.a(paramFile);
      LogE84000.a(paramFile);
      Log229316.a(paramFile);
      return paramFile;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/EnvironmentCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */