package androidx.recyclerview.widget;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class AsyncListUtil<T>
{
  static final boolean DEBUG = false;
  static final String TAG = "AsyncListUtil";
  boolean mAllowScrollHints;
  private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback;
  final ThreadUtil.BackgroundCallback<T> mBackgroundProxy;
  final DataCallback<T> mDataCallback;
  int mDisplayedGeneration = 0;
  int mItemCount = 0;
  private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback;
  final ThreadUtil.MainThreadCallback<T> mMainThreadProxy;
  final SparseIntArray mMissingPositions = new SparseIntArray();
  final int[] mPrevRange = new int[2];
  int mRequestedGeneration = 0;
  private int mScrollHint = 0;
  final Class<T> mTClass;
  final TileList<T> mTileList;
  final int mTileSize;
  final int[] mTmpRange = new int[2];
  final int[] mTmpRangeExtended = new int[2];
  final ViewCallback mViewCallback;
  
  public AsyncListUtil(Class<T> paramClass, int paramInt, DataCallback<T> paramDataCallback, ViewCallback paramViewCallback)
  {
    ThreadUtil.MainThreadCallback local1 = new ThreadUtil.MainThreadCallback()
    {
      private boolean isRequestedGeneration(int paramAnonymousInt)
      {
        boolean bool;
        if (paramAnonymousInt == AsyncListUtil.this.mRequestedGeneration) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      private void recycleAllTiles()
      {
        for (int i = 0; i < AsyncListUtil.this.mTileList.size(); i++) {
          AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex(i));
        }
        AsyncListUtil.this.mTileList.clear();
      }
      
      public void addTile(int paramAnonymousInt, TileList.Tile<T> paramAnonymousTile)
      {
        if (!isRequestedGeneration(paramAnonymousInt))
        {
          AsyncListUtil.this.mBackgroundProxy.recycleTile(paramAnonymousTile);
          return;
        }
        TileList.Tile localTile = AsyncListUtil.this.mTileList.addOrReplace(paramAnonymousTile);
        if (localTile != null)
        {
          Log.e("AsyncListUtil", "duplicate tile @" + localTile.mStartPosition);
          AsyncListUtil.this.mBackgroundProxy.recycleTile(localTile);
        }
        int j = paramAnonymousTile.mStartPosition;
        int i = paramAnonymousTile.mItemCount;
        paramAnonymousInt = 0;
        while (paramAnonymousInt < AsyncListUtil.this.mMissingPositions.size())
        {
          int k = AsyncListUtil.this.mMissingPositions.keyAt(paramAnonymousInt);
          if ((paramAnonymousTile.mStartPosition <= k) && (k < j + i))
          {
            AsyncListUtil.this.mMissingPositions.removeAt(paramAnonymousInt);
            AsyncListUtil.this.mViewCallback.onItemLoaded(k);
          }
          else
          {
            paramAnonymousInt++;
          }
        }
      }
      
      public void removeTile(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (!isRequestedGeneration(paramAnonymousInt1)) {
          return;
        }
        TileList.Tile localTile = AsyncListUtil.this.mTileList.removeAtPos(paramAnonymousInt2);
        if (localTile == null)
        {
          Log.e("AsyncListUtil", "tile not found @" + paramAnonymousInt2);
          return;
        }
        AsyncListUtil.this.mBackgroundProxy.recycleTile(localTile);
      }
      
      public void updateItemCount(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (!isRequestedGeneration(paramAnonymousInt1)) {
          return;
        }
        AsyncListUtil.this.mItemCount = paramAnonymousInt2;
        AsyncListUtil.this.mViewCallback.onDataRefresh();
        AsyncListUtil localAsyncListUtil = AsyncListUtil.this;
        localAsyncListUtil.mDisplayedGeneration = localAsyncListUtil.mRequestedGeneration;
        recycleAllTiles();
        AsyncListUtil.this.mAllowScrollHints = false;
        AsyncListUtil.this.updateRange();
      }
    };
    this.mMainThreadCallback = local1;
    ThreadUtil.BackgroundCallback local2 = new ThreadUtil.BackgroundCallback()
    {
      private int mFirstRequiredTileStart;
      private int mGeneration;
      private int mItemCount;
      private int mLastRequiredTileStart;
      final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
      private TileList.Tile<T> mRecycledRoot;
      
      private TileList.Tile<T> acquireTile()
      {
        TileList.Tile localTile2 = this.mRecycledRoot;
        if (localTile2 != null)
        {
          TileList.Tile localTile1 = this.mRecycledRoot;
          this.mRecycledRoot = localTile2.mNext;
          return localTile1;
        }
        return new TileList.Tile(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
      }
      
      private void addTile(TileList.Tile<T> paramAnonymousTile)
      {
        this.mLoadedTiles.put(paramAnonymousTile.mStartPosition, true);
        AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, paramAnonymousTile);
      }
      
      private void flushTileCache(int paramAnonymousInt)
      {
        int m = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
        while (this.mLoadedTiles.size() >= m)
        {
          int i = this.mLoadedTiles.keyAt(0);
          SparseBooleanArray localSparseBooleanArray = this.mLoadedTiles;
          int n = localSparseBooleanArray.keyAt(localSparseBooleanArray.size() - 1);
          int j = this.mFirstRequiredTileStart - i;
          int k = n - this.mLastRequiredTileStart;
          if ((j > 0) && ((j >= k) || (paramAnonymousInt == 2)))
          {
            removeTile(i);
          }
          else
          {
            if ((k <= 0) || ((j >= k) && (paramAnonymousInt != 1))) {
              break label117;
            }
            removeTile(n);
          }
          continue;
          label117:
          return;
        }
      }
      
      private int getTileStart(int paramAnonymousInt)
      {
        return paramAnonymousInt - paramAnonymousInt % AsyncListUtil.this.mTileSize;
      }
      
      private boolean isTileLoaded(int paramAnonymousInt)
      {
        return this.mLoadedTiles.get(paramAnonymousInt);
      }
      
      private void log(String paramAnonymousString, Object... paramAnonymousVarArgs)
      {
        StringBuilder localStringBuilder = new StringBuilder().append("[BKGR] ");
        paramAnonymousString = String.format(paramAnonymousString, paramAnonymousVarArgs);
        Log5ECF72.a(paramAnonymousString);
        LogE84000.a(paramAnonymousString);
        Log229316.a(paramAnonymousString);
        Log.d("AsyncListUtil", paramAnonymousString);
      }
      
      private void removeTile(int paramAnonymousInt)
      {
        this.mLoadedTiles.delete(paramAnonymousInt);
        AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, paramAnonymousInt);
      }
      
      private void requestTiles(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, boolean paramAnonymousBoolean)
      {
        int i = paramAnonymousInt1;
        while (i <= paramAnonymousInt2)
        {
          int j;
          if (paramAnonymousBoolean) {
            j = paramAnonymousInt2 + paramAnonymousInt1 - i;
          } else {
            j = i;
          }
          AsyncListUtil.this.mBackgroundProxy.loadTile(j, paramAnonymousInt3);
          i += AsyncListUtil.this.mTileSize;
        }
      }
      
      public void loadTile(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (isTileLoaded(paramAnonymousInt1)) {
          return;
        }
        TileList.Tile localTile = acquireTile();
        localTile.mStartPosition = paramAnonymousInt1;
        localTile.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - localTile.mStartPosition);
        AsyncListUtil.this.mDataCallback.fillData(localTile.mItems, localTile.mStartPosition, localTile.mItemCount);
        flushTileCache(paramAnonymousInt2);
        addTile(localTile);
      }
      
      public void recycleTile(TileList.Tile<T> paramAnonymousTile)
      {
        AsyncListUtil.this.mDataCallback.recycleData(paramAnonymousTile.mItems, paramAnonymousTile.mItemCount);
        paramAnonymousTile.mNext = this.mRecycledRoot;
        this.mRecycledRoot = paramAnonymousTile;
      }
      
      public void refresh(int paramAnonymousInt)
      {
        this.mGeneration = paramAnonymousInt;
        this.mLoadedTiles.clear();
        this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
        AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
      }
      
      public void updateRange(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5)
      {
        if (paramAnonymousInt1 > paramAnonymousInt2) {
          return;
        }
        paramAnonymousInt1 = getTileStart(paramAnonymousInt1);
        paramAnonymousInt2 = getTileStart(paramAnonymousInt2);
        this.mFirstRequiredTileStart = getTileStart(paramAnonymousInt3);
        paramAnonymousInt3 = getTileStart(paramAnonymousInt4);
        this.mLastRequiredTileStart = paramAnonymousInt3;
        if (paramAnonymousInt5 == 1)
        {
          requestTiles(this.mFirstRequiredTileStart, paramAnonymousInt2, paramAnonymousInt5, true);
          requestTiles(AsyncListUtil.this.mTileSize + paramAnonymousInt2, this.mLastRequiredTileStart, paramAnonymousInt5, false);
        }
        else
        {
          requestTiles(paramAnonymousInt1, paramAnonymousInt3, paramAnonymousInt5, false);
          requestTiles(this.mFirstRequiredTileStart, paramAnonymousInt1 - AsyncListUtil.this.mTileSize, paramAnonymousInt5, true);
        }
      }
    };
    this.mBackgroundCallback = local2;
    this.mTClass = paramClass;
    this.mTileSize = paramInt;
    this.mDataCallback = paramDataCallback;
    this.mViewCallback = paramViewCallback;
    this.mTileList = new TileList(paramInt);
    paramClass = new MessageThreadUtil();
    this.mMainThreadProxy = paramClass.getMainThreadProxy(local1);
    this.mBackgroundProxy = paramClass.getBackgroundProxy(local2);
    refresh();
  }
  
  private boolean isRefreshPending()
  {
    boolean bool;
    if (this.mRequestedGeneration != this.mDisplayedGeneration) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public T getItem(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mItemCount))
    {
      Object localObject = this.mTileList.getItemAt(paramInt);
      if ((localObject == null) && (!isRefreshPending())) {
        this.mMissingPositions.put(paramInt, 0);
      }
      return (T)localObject;
    }
    throw new IndexOutOfBoundsException(paramInt + " is not within 0 and " + this.mItemCount);
  }
  
  public int getItemCount()
  {
    return this.mItemCount;
  }
  
  void log(String paramString, Object... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("[MAIN] ");
    paramString = String.format(paramString, paramVarArgs);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    Log.d("AsyncListUtil", paramString);
  }
  
  public void onRangeChanged()
  {
    if (isRefreshPending()) {
      return;
    }
    updateRange();
    this.mAllowScrollHints = true;
  }
  
  public void refresh()
  {
    this.mMissingPositions.clear();
    ThreadUtil.BackgroundCallback localBackgroundCallback = this.mBackgroundProxy;
    int i = this.mRequestedGeneration + 1;
    this.mRequestedGeneration = i;
    localBackgroundCallback.refresh(i);
  }
  
  void updateRange()
  {
    this.mViewCallback.getItemRangeInto(this.mTmpRange);
    Object localObject = this.mTmpRange;
    int i = localObject[0];
    int k = localObject[1];
    if ((i <= k) && (i >= 0))
    {
      if (k >= this.mItemCount) {
        return;
      }
      if (!this.mAllowScrollHints)
      {
        this.mScrollHint = 0;
      }
      else
      {
        arrayOfInt = this.mPrevRange;
        if (i <= arrayOfInt[1])
        {
          j = arrayOfInt[0];
          if (j <= k)
          {
            if (i < j)
            {
              this.mScrollHint = 1;
              break label121;
            }
            if (i <= j) {
              break label121;
            }
            this.mScrollHint = 2;
            break label121;
          }
        }
        this.mScrollHint = 0;
      }
      label121:
      int[] arrayOfInt = this.mPrevRange;
      arrayOfInt[0] = i;
      arrayOfInt[1] = k;
      this.mViewCallback.extendRangeInto((int[])localObject, this.mTmpRangeExtended, this.mScrollHint);
      localObject = this.mTmpRangeExtended;
      localObject[0] = Math.min(this.mTmpRange[0], Math.max(localObject[0], 0));
      localObject = this.mTmpRangeExtended;
      localObject[1] = Math.max(this.mTmpRange[1], Math.min(localObject[1], this.mItemCount - 1));
      localObject = this.mBackgroundProxy;
      arrayOfInt = this.mTmpRange;
      i = arrayOfInt[0];
      int j = arrayOfInt[1];
      arrayOfInt = this.mTmpRangeExtended;
      ((ThreadUtil.BackgroundCallback)localObject).updateRange(i, j, arrayOfInt[0], arrayOfInt[1], this.mScrollHint);
      return;
    }
  }
  
  public static abstract class DataCallback<T>
  {
    public abstract void fillData(T[] paramArrayOfT, int paramInt1, int paramInt2);
    
    public int getMaxCachedTiles()
    {
      return 10;
    }
    
    public void recycleData(T[] paramArrayOfT, int paramInt) {}
    
    public abstract int refreshData();
  }
  
  public static abstract class ViewCallback
  {
    public static final int HINT_SCROLL_ASC = 2;
    public static final int HINT_SCROLL_DESC = 1;
    public static final int HINT_SCROLL_NONE = 0;
    
    public void extendRangeInto(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
    {
      int j = paramArrayOfInt1[1] - paramArrayOfInt1[0] + 1;
      int i = j / 2;
      int m = paramArrayOfInt1[0];
      if (paramInt == 1) {
        k = j;
      } else {
        k = i;
      }
      paramArrayOfInt2[0] = (m - k);
      int k = paramArrayOfInt1[1];
      if (paramInt == 2) {
        paramInt = j;
      } else {
        paramInt = i;
      }
      paramArrayOfInt2[1] = (k + paramInt);
    }
    
    public abstract void getItemRangeInto(int[] paramArrayOfInt);
    
    public abstract void onDataRefresh();
    
    public abstract void onItemLoaded(int paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/AsyncListUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */