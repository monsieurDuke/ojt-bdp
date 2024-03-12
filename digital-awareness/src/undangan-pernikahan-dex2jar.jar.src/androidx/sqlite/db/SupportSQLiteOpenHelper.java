package androidx.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import java.io.Closeable;
import java.io.File;

public abstract interface SupportSQLiteOpenHelper
  extends Closeable
{
  public abstract void close();
  
  public abstract String getDatabaseName();
  
  public abstract SupportSQLiteDatabase getReadableDatabase();
  
  public abstract SupportSQLiteDatabase getWritableDatabase();
  
  public abstract void setWriteAheadLoggingEnabled(boolean paramBoolean);
  
  public static abstract class Callback
  {
    private static final String TAG = "SupportSQLite";
    public final int version;
    
    public Callback(int paramInt)
    {
      this.version = paramInt;
    }
    
    private void deleteDatabaseFile(String paramString)
    {
      if ((!paramString.equalsIgnoreCase(":memory:")) && (paramString.trim().length() != 0))
      {
        Log.w("SupportSQLite", "deleting the database file: " + paramString);
        try
        {
          Object localObject;
          if (Build.VERSION.SDK_INT >= 16)
          {
            localObject = new java/io/File;
            ((File)localObject).<init>(paramString);
            SQLiteDatabase.deleteDatabase((File)localObject);
          }
          else
          {
            try
            {
              localObject = new java/io/File;
              ((File)localObject).<init>(paramString);
              if (!((File)localObject).delete())
              {
                localObject = new java/lang/StringBuilder;
                ((StringBuilder)localObject).<init>();
                Log.e("SupportSQLite", "Could not delete the database file " + paramString);
              }
            }
            catch (Exception paramString)
            {
              Log.e("SupportSQLite", "error while deleting corrupted database file", paramString);
            }
          }
        }
        catch (Exception paramString)
        {
          Log.w("SupportSQLite", "delete failed: ", paramString);
        }
        return;
      }
    }
    
    public void onConfigure(SupportSQLiteDatabase paramSupportSQLiteDatabase) {}
    
    /* Error */
    public void onCorruption(SupportSQLiteDatabase paramSupportSQLiteDatabase)
    {
      // Byte code:
      //   0: ldc 11
      //   2: new 43	java/lang/StringBuilder
      //   5: dup
      //   6: invokespecial 44	java/lang/StringBuilder:<init>	()V
      //   9: ldc 101
      //   11: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   14: aload_1
      //   15: invokeinterface 106 1 0
      //   20: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   23: invokevirtual 53	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   26: invokestatic 83	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   29: pop
      //   30: aload_1
      //   31: invokeinterface 109 1 0
      //   36: ifne +14 -> 50
      //   39: aload_0
      //   40: aload_1
      //   41: invokeinterface 106 1 0
      //   46: invokespecial 111	androidx/sqlite/db/SupportSQLiteOpenHelper$Callback:deleteDatabaseFile	(Ljava/lang/String;)V
      //   49: return
      //   50: aconst_null
      //   51: astore_2
      //   52: aconst_null
      //   53: astore_3
      //   54: aload_1
      //   55: invokeinterface 115 1 0
      //   60: astore 4
      //   62: aload 4
      //   64: astore_2
      //   65: goto +8 -> 73
      //   68: astore_2
      //   69: goto +15 -> 84
      //   72: astore_3
      //   73: aload_2
      //   74: astore_3
      //   75: aload_1
      //   76: invokeinterface 118 1 0
      //   81: goto +61 -> 142
      //   84: aload_3
      //   85: ifnull +44 -> 129
      //   88: aload_3
      //   89: invokeinterface 124 1 0
      //   94: astore_1
      //   95: aload_1
      //   96: invokeinterface 129 1 0
      //   101: ifeq +25 -> 126
      //   104: aload_0
      //   105: aload_1
      //   106: invokeinterface 133 1 0
      //   111: checkcast 135	android/util/Pair
      //   114: getfield 139	android/util/Pair:second	Ljava/lang/Object;
      //   117: checkcast 29	java/lang/String
      //   120: invokespecial 111	androidx/sqlite/db/SupportSQLiteOpenHelper$Callback:deleteDatabaseFile	(Ljava/lang/String;)V
      //   123: goto -28 -> 95
      //   126: goto +13 -> 139
      //   129: aload_0
      //   130: aload_1
      //   131: invokeinterface 106 1 0
      //   136: invokespecial 111	androidx/sqlite/db/SupportSQLiteOpenHelper$Callback:deleteDatabaseFile	(Ljava/lang/String;)V
      //   139: aload_2
      //   140: athrow
      //   141: astore_3
      //   142: aload_2
      //   143: ifnull +44 -> 187
      //   146: aload_2
      //   147: invokeinterface 124 1 0
      //   152: astore_1
      //   153: aload_1
      //   154: invokeinterface 129 1 0
      //   159: ifeq +25 -> 184
      //   162: aload_0
      //   163: aload_1
      //   164: invokeinterface 133 1 0
      //   169: checkcast 135	android/util/Pair
      //   172: getfield 139	android/util/Pair:second	Ljava/lang/Object;
      //   175: checkcast 29	java/lang/String
      //   178: invokespecial 111	androidx/sqlite/db/SupportSQLiteOpenHelper$Callback:deleteDatabaseFile	(Ljava/lang/String;)V
      //   181: goto -28 -> 153
      //   184: goto +13 -> 197
      //   187: aload_0
      //   188: aload_1
      //   189: invokeinterface 106 1 0
      //   194: invokespecial 111	androidx/sqlite/db/SupportSQLiteOpenHelper$Callback:deleteDatabaseFile	(Ljava/lang/String;)V
      //   197: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	198	0	this	Callback
      //   0	198	1	paramSupportSQLiteDatabase	SupportSQLiteDatabase
      //   51	14	2	localObject1	Object
      //   68	79	2	localObject2	Object
      //   53	1	3	localObject3	Object
      //   72	1	3	localSQLiteException	SQLiteException
      //   74	15	3	localObject4	Object
      //   141	1	3	localIOException	java.io.IOException
      //   60	3	4	localList	java.util.List
      // Exception table:
      //   from	to	target	type
      //   54	62	68	finally
      //   75	81	68	finally
      //   54	62	72	android/database/sqlite/SQLiteException
      //   75	81	141	java/io/IOException
    }
    
    public abstract void onCreate(SupportSQLiteDatabase paramSupportSQLiteDatabase);
    
    public void onDowngrade(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt1, int paramInt2)
    {
      throw new SQLiteException("Can't downgrade database from version " + paramInt1 + " to " + paramInt2);
    }
    
    public void onOpen(SupportSQLiteDatabase paramSupportSQLiteDatabase) {}
    
    public abstract void onUpgrade(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt1, int paramInt2);
  }
  
  public static class Configuration
  {
    public final SupportSQLiteOpenHelper.Callback callback;
    public final Context context;
    public final String name;
    public final boolean useNoBackupDirectory;
    
    Configuration(Context paramContext, String paramString, SupportSQLiteOpenHelper.Callback paramCallback)
    {
      this(paramContext, paramString, paramCallback, false);
    }
    
    Configuration(Context paramContext, String paramString, SupportSQLiteOpenHelper.Callback paramCallback, boolean paramBoolean)
    {
      this.context = paramContext;
      this.name = paramString;
      this.callback = paramCallback;
      this.useNoBackupDirectory = paramBoolean;
    }
    
    public static Builder builder(Context paramContext)
    {
      return new Builder(paramContext);
    }
    
    public static class Builder
    {
      SupportSQLiteOpenHelper.Callback mCallback;
      Context mContext;
      String mName;
      boolean mUseNoBackUpDirectory;
      
      Builder(Context paramContext)
      {
        this.mContext = paramContext;
      }
      
      public SupportSQLiteOpenHelper.Configuration build()
      {
        if (this.mCallback != null)
        {
          if (this.mContext != null)
          {
            if ((this.mUseNoBackUpDirectory) && (TextUtils.isEmpty(this.mName))) {
              throw new IllegalArgumentException("Must set a non-null database name to a configuration that uses the no backup directory.");
            }
            return new SupportSQLiteOpenHelper.Configuration(this.mContext, this.mName, this.mCallback, this.mUseNoBackUpDirectory);
          }
          throw new IllegalArgumentException("Must set a non-null context to create the configuration.");
        }
        throw new IllegalArgumentException("Must set a callback to create the configuration.");
      }
      
      public Builder callback(SupportSQLiteOpenHelper.Callback paramCallback)
      {
        this.mCallback = paramCallback;
        return this;
      }
      
      public Builder name(String paramString)
      {
        this.mName = paramString;
        return this;
      }
      
      public Builder noBackupDirectory(boolean paramBoolean)
      {
        this.mUseNoBackUpDirectory = paramBoolean;
        return this;
      }
    }
  }
  
  public static abstract interface Factory
  {
    public abstract SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration paramConfiguration);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/sqlite/db/SupportSQLiteOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */