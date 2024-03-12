package androidx.work.impl.model;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.concurrent.Callable;

public final class PreferenceDao_Impl
  implements PreferenceDao
{
  private final RoomDatabase __db;
  private final EntityInsertionAdapter<Preference> __insertionAdapterOfPreference;
  
  public PreferenceDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfPreference = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, Preference paramAnonymousPreference)
      {
        if (paramAnonymousPreference.mKey == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousPreference.mKey);
        }
        if (paramAnonymousPreference.mValue == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(2);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(2, paramAnonymousPreference.mValue.longValue());
        }
      }
      
      public String createQuery()
      {
        return "INSERT OR REPLACE INTO `Preference` (`key`,`long_value`) VALUES (?,?)";
      }
    };
  }
  
  public Long getLongValue(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    Cursor localCursor = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      if (localCursor.moveToFirst())
      {
        if (localCursor.isNull(0)) {
          paramString = null;
        } else {
          paramString = Long.valueOf(localCursor.getLong(0));
        }
      }
      else {
        paramString = null;
      }
      return paramString;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public LiveData<Long> getObservableLongValue(String paramString)
  {
    final Object localObject = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
    if (paramString == null) {
      ((RoomSQLiteQuery)localObject).bindNull(1);
    } else {
      ((RoomSQLiteQuery)localObject).bindString(1, paramString);
    }
    paramString = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      public Long call()
        throws Exception
      {
        Cursor localCursor = DBUtil.query(PreferenceDao_Impl.this.__db, localObject, false, null);
        try
        {
          Long localLong;
          if (localCursor.moveToFirst())
          {
            if (localCursor.isNull(0)) {
              localLong = null;
            } else {
              localLong = Long.valueOf(localCursor.getLong(0));
            }
          }
          else {
            localLong = null;
          }
          return localLong;
        }
        finally
        {
          localCursor.close();
        }
      }
      
      protected void finalize()
      {
        localObject.release();
      }
    };
    return paramString.createLiveData(new String[] { "Preference" }, false, (Callable)localObject);
  }
  
  public void insertPreference(Preference paramPreference)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfPreference.insert(paramPreference);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/PreferenceDao_Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */