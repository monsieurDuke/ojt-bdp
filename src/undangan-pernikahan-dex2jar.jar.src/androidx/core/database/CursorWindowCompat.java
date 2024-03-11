package androidx.core.database;

import android.database.CursorWindow;
import android.os.Build.VERSION;

public final class CursorWindowCompat
{
  public static CursorWindow create(String paramString, long paramLong)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.createCursorWindow(paramString, paramLong);
    }
    if (Build.VERSION.SDK_INT >= 15) {
      return Api15Impl.createCursorWindow(paramString);
    }
    return new CursorWindow(false);
  }
  
  static class Api15Impl
  {
    static CursorWindow createCursorWindow(String paramString)
    {
      return new CursorWindow(paramString);
    }
  }
  
  static class Api28Impl
  {
    static CursorWindow createCursorWindow(String paramString, long paramLong)
    {
      return new CursorWindow(paramString, paramLong);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/database/CursorWindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */