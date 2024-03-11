package androidx.sqlite.db.framework;

import android.database.sqlite.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;

class FrameworkSQLiteStatement
  extends FrameworkSQLiteProgram
  implements SupportSQLiteStatement
{
  private final SQLiteStatement mDelegate;
  
  FrameworkSQLiteStatement(SQLiteStatement paramSQLiteStatement)
  {
    super(paramSQLiteStatement);
    this.mDelegate = paramSQLiteStatement;
  }
  
  public void execute()
  {
    this.mDelegate.execute();
  }
  
  public long executeInsert()
  {
    return this.mDelegate.executeInsert();
  }
  
  public int executeUpdateDelete()
  {
    return this.mDelegate.executeUpdateDelete();
  }
  
  public long simpleQueryForLong()
  {
    return this.mDelegate.simpleQueryForLong();
  }
  
  public String simpleQueryForString()
  {
    return this.mDelegate.simpleQueryForString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/sqlite/db/framework/FrameworkSQLiteStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */