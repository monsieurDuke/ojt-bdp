package androidx.room.util;

import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ViewInfo
{
  public final String name;
  public final String sql;
  
  public ViewInfo(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.sql = paramString2;
  }
  
  public static ViewInfo read(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query("SELECT name, sql FROM sqlite_master WHERE type = 'view' AND name = '" + paramString + "'");
    try
    {
      if (paramSupportSQLiteDatabase.moveToFirst())
      {
        paramString = new ViewInfo(paramSupportSQLiteDatabase.getString(0), paramSupportSQLiteDatabase.getString(1));
        return paramString;
      }
      paramString = new ViewInfo(paramString, null);
      return paramString;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (ViewInfo)paramObject;
      String str = this.name;
      if (str != null ? str.equals(((ViewInfo)paramObject).name) : ((ViewInfo)paramObject).name == null)
      {
        str = this.sql;
        if (str != null) {
          if (!str.equals(((ViewInfo)paramObject).sql)) {
            break label95;
          }
        } else {
          if (((ViewInfo)paramObject).sql == null) {
            break label97;
          }
        }
      }
      label95:
      bool = false;
      label97:
      return bool;
    }
    return false;
  }
  
  public int hashCode()
  {
    String str = this.name;
    int j = 0;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    str = this.sql;
    if (str != null) {
      j = str.hashCode();
    }
    return i * 31 + j;
  }
  
  public String toString()
  {
    return "ViewInfo{name='" + this.name + '\'' + ", sql='" + this.sql + '\'' + '}';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/util/ViewInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */