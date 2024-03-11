package androidx.sqlite.db;

public abstract interface SupportSQLiteStatement
  extends SupportSQLiteProgram
{
  public abstract void execute();
  
  public abstract long executeInsert();
  
  public abstract int executeUpdateDelete();
  
  public abstract long simpleQueryForLong();
  
  public abstract String simpleQueryForString();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/sqlite/db/SupportSQLiteStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */