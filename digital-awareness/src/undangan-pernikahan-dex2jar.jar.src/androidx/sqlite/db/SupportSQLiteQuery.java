package androidx.sqlite.db;

public abstract interface SupportSQLiteQuery
{
  public abstract void bindTo(SupportSQLiteProgram paramSupportSQLiteProgram);
  
  public abstract int getArgCount();
  
  public abstract String getSql();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/sqlite/db/SupportSQLiteQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */