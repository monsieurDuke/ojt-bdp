package androidx.room.paging;

import android.database.Cursor;
import androidx.paging.PositionalDataSource;
import androidx.paging.PositionalDataSource.LoadInitialCallback;
import androidx.paging.PositionalDataSource.LoadInitialParams;
import androidx.paging.PositionalDataSource.LoadRangeCallback;
import androidx.paging.PositionalDataSource.LoadRangeParams;
import androidx.room.InvalidationTracker;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class LimitOffsetDataSource<T>
  extends PositionalDataSource<T>
{
  private final String mCountQuery;
  private final RoomDatabase mDb;
  private final boolean mInTransaction;
  private final String mLimitOffsetQuery;
  private final InvalidationTracker.Observer mObserver;
  private final RoomSQLiteQuery mSourceQuery;
  
  protected LimitOffsetDataSource(RoomDatabase paramRoomDatabase, RoomSQLiteQuery paramRoomSQLiteQuery, boolean paramBoolean, String... paramVarArgs)
  {
    this.mDb = paramRoomDatabase;
    this.mSourceQuery = paramRoomSQLiteQuery;
    this.mInTransaction = paramBoolean;
    this.mCountQuery = ("SELECT COUNT(*) FROM ( " + paramRoomSQLiteQuery.getSql() + " )");
    this.mLimitOffsetQuery = ("SELECT * FROM ( " + paramRoomSQLiteQuery.getSql() + " ) LIMIT ? OFFSET ?");
    paramRoomSQLiteQuery = new InvalidationTracker.Observer(paramVarArgs)
    {
      public void onInvalidated(Set<String> paramAnonymousSet)
      {
        LimitOffsetDataSource.this.invalidate();
      }
    };
    this.mObserver = paramRoomSQLiteQuery;
    paramRoomDatabase.getInvalidationTracker().addWeakObserver(paramRoomSQLiteQuery);
  }
  
  protected LimitOffsetDataSource(RoomDatabase paramRoomDatabase, SupportSQLiteQuery paramSupportSQLiteQuery, boolean paramBoolean, String... paramVarArgs)
  {
    this(paramRoomDatabase, RoomSQLiteQuery.copyFrom(paramSupportSQLiteQuery), paramBoolean, paramVarArgs);
  }
  
  private RoomSQLiteQuery getSQLiteQuery(int paramInt1, int paramInt2)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire(this.mLimitOffsetQuery, this.mSourceQuery.getArgCount() + 2);
    localRoomSQLiteQuery.copyArgumentsFrom(this.mSourceQuery);
    localRoomSQLiteQuery.bindLong(localRoomSQLiteQuery.getArgCount() - 1, paramInt2);
    localRoomSQLiteQuery.bindLong(localRoomSQLiteQuery.getArgCount(), paramInt1);
    return localRoomSQLiteQuery;
  }
  
  protected abstract List<T> convertRows(Cursor paramCursor);
  
  public int countItems()
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire(this.mCountQuery, this.mSourceQuery.getArgCount());
    localRoomSQLiteQuery.copyArgumentsFrom(this.mSourceQuery);
    Cursor localCursor = this.mDb.query(localRoomSQLiteQuery);
    try
    {
      if (localCursor.moveToFirst())
      {
        int i = localCursor.getInt(0);
        return i;
      }
      return 0;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public boolean isInvalid()
  {
    this.mDb.getInvalidationTracker().refreshVersionsSync();
    return super.isInvalid();
  }
  
  public void loadInitial(PositionalDataSource.LoadInitialParams paramLoadInitialParams, PositionalDataSource.LoadInitialCallback<T> paramLoadInitialCallback)
  {
    List localList2 = Collections.emptyList();
    int i = 0;
    Object localObject4 = null;
    Object localObject3 = null;
    List localList1 = null;
    Object localObject5 = null;
    this.mDb.beginTransaction();
    Object localObject1 = localObject4;
    Object localObject2 = localList1;
    try
    {
      int j = countItems();
      localObject1 = localList2;
      localObject2 = localObject5;
      if (j != 0)
      {
        localObject1 = localObject4;
        localObject2 = localList1;
        i = computeInitialLoadPosition(paramLoadInitialParams, j);
        localObject1 = localObject4;
        localObject2 = localList1;
        paramLoadInitialParams = getSQLiteQuery(i, computeInitialLoadSize(paramLoadInitialParams, i, j));
        localObject1 = paramLoadInitialParams;
        localObject2 = localList1;
        localObject3 = this.mDb.query(paramLoadInitialParams);
        localObject1 = paramLoadInitialParams;
        localObject2 = localObject3;
        localList1 = convertRows((Cursor)localObject3);
        localObject1 = paramLoadInitialParams;
        localObject2 = localObject3;
        this.mDb.setTransactionSuccessful();
        localObject1 = localList1;
        localObject2 = localObject3;
        localObject3 = paramLoadInitialParams;
      }
      if (localObject2 != null) {
        ((Cursor)localObject2).close();
      }
      this.mDb.endTransaction();
      if (localObject3 != null) {
        ((RoomSQLiteQuery)localObject3).release();
      }
      paramLoadInitialCallback.onResult((List)localObject1, i, j);
      return;
    }
    finally
    {
      if (localObject2 != null) {
        ((Cursor)localObject2).close();
      }
      this.mDb.endTransaction();
      if (localObject1 != null) {
        ((RoomSQLiteQuery)localObject1).release();
      }
    }
  }
  
  public List<T> loadRange(int paramInt1, int paramInt2)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = getSQLiteQuery(paramInt1, paramInt2);
    if (this.mInTransaction)
    {
      this.mDb.beginTransaction();
      localObject1 = null;
      try
      {
        Cursor localCursor = this.mDb.query(localRoomSQLiteQuery);
        localObject1 = localCursor;
        List localList2 = convertRows(localCursor);
        localObject1 = localCursor;
        this.mDb.setTransactionSuccessful();
        if (localCursor != null) {
          localCursor.close();
        }
        this.mDb.endTransaction();
        localRoomSQLiteQuery.release();
        return localList2;
      }
      finally
      {
        if (localObject1 != null) {
          ((Cursor)localObject1).close();
        }
        this.mDb.endTransaction();
        localRoomSQLiteQuery.release();
      }
    }
    Object localObject1 = this.mDb.query(localRoomSQLiteQuery);
    try
    {
      List localList1 = convertRows((Cursor)localObject1);
      return localList1;
    }
    finally
    {
      ((Cursor)localObject1).close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public void loadRange(PositionalDataSource.LoadRangeParams paramLoadRangeParams, PositionalDataSource.LoadRangeCallback<T> paramLoadRangeCallback)
  {
    paramLoadRangeCallback.onResult(loadRange(paramLoadRangeParams.startPosition, paramLoadRangeParams.loadSize));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/paging/LimitOffsetDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */