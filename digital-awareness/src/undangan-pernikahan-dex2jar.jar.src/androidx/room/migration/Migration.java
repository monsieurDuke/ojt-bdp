package androidx.room.migration;

import androidx.sqlite.db.SupportSQLiteDatabase;

public abstract class Migration
{
  public final int endVersion;
  public final int startVersion;
  
  public Migration(int paramInt1, int paramInt2)
  {
    this.startVersion = paramInt1;
    this.endVersion = paramInt2;
  }
  
  public abstract void migrate(SupportSQLiteDatabase paramSupportSQLiteDatabase);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/migration/Migration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */