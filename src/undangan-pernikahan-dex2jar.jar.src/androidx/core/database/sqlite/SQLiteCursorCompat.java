package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteCursor;
import android.os.Build.VERSION;

public final class SQLiteCursorCompat
{
  public static void setFillWindowForwardOnly(SQLiteCursor paramSQLiteCursor, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      Api28Impl.setFillWindowForwardOnly(paramSQLiteCursor, paramBoolean);
    }
  }
  
  static class Api28Impl
  {
    static void setFillWindowForwardOnly(SQLiteCursor paramSQLiteCursor, boolean paramBoolean)
    {
      paramSQLiteCursor.setFillWindowForwardOnly(paramBoolean);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/database/sqlite/SQLiteCursorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */