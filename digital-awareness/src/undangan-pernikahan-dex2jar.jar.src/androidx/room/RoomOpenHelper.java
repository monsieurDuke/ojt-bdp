package androidx.room;

import android.database.Cursor;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class RoomOpenHelper
  extends SupportSQLiteOpenHelper.Callback
{
  private DatabaseConfiguration mConfiguration;
  private final Delegate mDelegate;
  private final String mIdentityHash;
  private final String mLegacyHash;
  
  public RoomOpenHelper(DatabaseConfiguration paramDatabaseConfiguration, Delegate paramDelegate, String paramString)
  {
    this(paramDatabaseConfiguration, paramDelegate, "", paramString);
  }
  
  public RoomOpenHelper(DatabaseConfiguration paramDatabaseConfiguration, Delegate paramDelegate, String paramString1, String paramString2)
  {
    super(paramDelegate.version);
    this.mConfiguration = paramDatabaseConfiguration;
    this.mDelegate = paramDelegate;
    this.mIdentityHash = paramString1;
    this.mLegacyHash = paramString2;
  }
  
  private void checkIdentity(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    ValidationResult localValidationResult;
    Cursor localCursor;
    if (hasRoomMasterTable(paramSupportSQLiteDatabase))
    {
      localValidationResult = null;
      localCursor = paramSupportSQLiteDatabase.query(new SimpleSQLiteQuery("SELECT identity_hash FROM room_master_table WHERE id = 42 LIMIT 1"));
      paramSupportSQLiteDatabase = localValidationResult;
    }
    try
    {
      if (localCursor.moveToFirst()) {
        paramSupportSQLiteDatabase = localCursor.getString(0);
      }
      localCursor.close();
      if ((!this.mIdentityHash.equals(paramSupportSQLiteDatabase)) && (!this.mLegacyHash.equals(paramSupportSQLiteDatabase))) {
        throw new IllegalStateException("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.");
      }
    }
    finally
    {
      localCursor.close();
    }
    if (localValidationResult.isValid)
    {
      this.mDelegate.onPostMigrate(paramSupportSQLiteDatabase);
      updateIdentity(paramSupportSQLiteDatabase);
      return;
    }
    throw new IllegalStateException("Pre-packaged database has an invalid schema: " + localValidationResult.expectedFoundMsg);
  }
  
  private void createMasterTableIfNotExists(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    paramSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
  }
  
  private static boolean hasEmptySchema(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query("SELECT count(*) FROM sqlite_master WHERE name != 'android_metadata'");
    try
    {
      boolean bool3 = paramSupportSQLiteDatabase.moveToFirst();
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (bool3)
      {
        int i = paramSupportSQLiteDatabase.getInt(0);
        bool1 = bool2;
        if (i == 0) {
          bool1 = true;
        }
      }
      return bool1;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static boolean hasRoomMasterTable(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name='room_master_table'");
    try
    {
      boolean bool3 = paramSupportSQLiteDatabase.moveToFirst();
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (bool3)
      {
        int i = paramSupportSQLiteDatabase.getInt(0);
        bool1 = bool2;
        if (i != 0) {
          bool1 = true;
        }
      }
      return bool1;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private void updateIdentity(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    createMasterTableIfNotExists(paramSupportSQLiteDatabase);
    String str = RoomMasterTable.createInsertQuery(this.mIdentityHash);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    paramSupportSQLiteDatabase.execSQL(str);
  }
  
  public void onConfigure(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    super.onConfigure(paramSupportSQLiteDatabase);
  }
  
  public void onCreate(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    boolean bool = hasEmptySchema(paramSupportSQLiteDatabase);
    this.mDelegate.createAllTables(paramSupportSQLiteDatabase);
    if (!bool)
    {
      ValidationResult localValidationResult = this.mDelegate.onValidateSchema(paramSupportSQLiteDatabase);
      if (!localValidationResult.isValid) {
        throw new IllegalStateException("Pre-packaged database has an invalid schema: " + localValidationResult.expectedFoundMsg);
      }
    }
    updateIdentity(paramSupportSQLiteDatabase);
    this.mDelegate.onCreate(paramSupportSQLiteDatabase);
  }
  
  public void onDowngrade(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt1, int paramInt2)
  {
    onUpgrade(paramSupportSQLiteDatabase, paramInt1, paramInt2);
  }
  
  public void onOpen(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    super.onOpen(paramSupportSQLiteDatabase);
    checkIdentity(paramSupportSQLiteDatabase);
    this.mDelegate.onOpen(paramSupportSQLiteDatabase);
    this.mConfiguration = null;
  }
  
  public void onUpgrade(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt1, int paramInt2)
  {
    int j = 0;
    Object localObject = this.mConfiguration;
    int i = j;
    if (localObject != null)
    {
      localObject = ((DatabaseConfiguration)localObject).migrationContainer.findMigrationPath(paramInt1, paramInt2);
      i = j;
      if (localObject != null)
      {
        this.mDelegate.onPreMigrate(paramSupportSQLiteDatabase);
        localObject = ((List)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((Migration)((Iterator)localObject).next()).migrate(paramSupportSQLiteDatabase);
        }
        localObject = this.mDelegate.onValidateSchema(paramSupportSQLiteDatabase);
        if (((ValidationResult)localObject).isValid)
        {
          this.mDelegate.onPostMigrate(paramSupportSQLiteDatabase);
          updateIdentity(paramSupportSQLiteDatabase);
          i = 1;
        }
        else
        {
          throw new IllegalStateException("Migration didn't properly handle: " + ((ValidationResult)localObject).expectedFoundMsg);
        }
      }
    }
    if (i == 0)
    {
      localObject = this.mConfiguration;
      if ((localObject != null) && (!((DatabaseConfiguration)localObject).isMigrationRequired(paramInt1, paramInt2)))
      {
        this.mDelegate.dropAllTables(paramSupportSQLiteDatabase);
        this.mDelegate.createAllTables(paramSupportSQLiteDatabase);
      }
      else
      {
        throw new IllegalStateException("A migration from " + paramInt1 + " to " + paramInt2 + " was required but not found. Please provide the necessary Migration path via RoomDatabase.Builder.addMigration(Migration ...) or allow for destructive migrations via one of the RoomDatabase.Builder.fallbackToDestructiveMigration* methods.");
      }
    }
  }
  
  public static abstract class Delegate
  {
    public final int version;
    
    public Delegate(int paramInt)
    {
      this.version = paramInt;
    }
    
    protected abstract void createAllTables(SupportSQLiteDatabase paramSupportSQLiteDatabase);
    
    protected abstract void dropAllTables(SupportSQLiteDatabase paramSupportSQLiteDatabase);
    
    protected abstract void onCreate(SupportSQLiteDatabase paramSupportSQLiteDatabase);
    
    protected abstract void onOpen(SupportSQLiteDatabase paramSupportSQLiteDatabase);
    
    protected void onPostMigrate(SupportSQLiteDatabase paramSupportSQLiteDatabase) {}
    
    protected void onPreMigrate(SupportSQLiteDatabase paramSupportSQLiteDatabase) {}
    
    protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase paramSupportSQLiteDatabase)
    {
      validateMigration(paramSupportSQLiteDatabase);
      return new RoomOpenHelper.ValidationResult(true, null);
    }
    
    @Deprecated
    protected void validateMigration(SupportSQLiteDatabase paramSupportSQLiteDatabase)
    {
      throw new UnsupportedOperationException("validateMigration is deprecated");
    }
  }
  
  public static class ValidationResult
  {
    public final String expectedFoundMsg;
    public final boolean isValid;
    
    public ValidationResult(boolean paramBoolean, String paramString)
    {
      this.isValid = paramBoolean;
      this.expectedFoundMsg = paramString;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/RoomOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */