package androidx.recyclerview.widget;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 008A.java */
/* loaded from: classes.dex */
public class AsyncListUtil<T> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncListUtil";
    boolean mAllowScrollHints;
    private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback;
    final ThreadUtil.BackgroundCallback<T> mBackgroundProxy;
    final DataCallback<T> mDataCallback;
    private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback;
    final ThreadUtil.MainThreadCallback<T> mMainThreadProxy;
    final Class<T> mTClass;
    final TileList<T> mTileList;
    final int mTileSize;
    final ViewCallback mViewCallback;
    final int[] mTmpRange = new int[2];
    final int[] mPrevRange = new int[2];
    final int[] mTmpRangeExtended = new int[2];
    private int mScrollHint = 0;
    int mItemCount = 0;
    int mDisplayedGeneration = 0;
    int mRequestedGeneration = 0;
    final SparseIntArray mMissingPositions = new SparseIntArray();

    /* loaded from: classes.dex */
    public static abstract class DataCallback<T> {
        public abstract void fillData(T[] tArr, int i, int i2);

        public int getMaxCachedTiles() {
            return 10;
        }

        public void recycleData(T[] tArr, int itemCount) {
        }

        public abstract int refreshData();
    }

    /* loaded from: classes.dex */
    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        public void extendRangeInto(int[] range, int[] outRange, int scrollHint) {
            int i = (range[1] - range[0]) + 1;
            int i2 = i / 2;
            outRange[0] = range[0] - (scrollHint == 1 ? i : i2);
            outRange[1] = range[1] + (scrollHint == 2 ? i : i2);
        }

        public abstract void getItemRangeInto(int[] iArr);

        public abstract void onDataRefresh();

        public abstract void onItemLoaded(int i);
    }

    public AsyncListUtil(Class<T> cls, int tileSize, DataCallback<T> dataCallback, ViewCallback viewCallback) {
        ThreadUtil.MainThreadCallback<T> mainThreadCallback = new ThreadUtil.MainThreadCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.1
            private boolean isRequestedGeneration(int generation) {
                return generation == AsyncListUtil.this.mRequestedGeneration;
            }

            private void recycleAllTiles() {
                for (int i = 0; i < AsyncListUtil.this.mTileList.size(); i++) {
                    AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex(i));
                }
                AsyncListUtil.this.mTileList.clear();
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void addTile(int generation, TileList.Tile<T> tile) {
                if (!isRequestedGeneration(generation)) {
                    AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
                    return;
                }
                TileList.Tile<T> addOrReplace = AsyncListUtil.this.mTileList.addOrReplace(tile);
                if (addOrReplace != null) {
                    Log.e(AsyncListUtil.TAG, "duplicate tile @" + addOrReplace.mStartPosition);
                    AsyncListUtil.this.mBackgroundProxy.recycleTile(addOrReplace);
                }
                int i = tile.mStartPosition + tile.mItemCount;
                int i2 = 0;
                while (i2 < AsyncListUtil.this.mMissingPositions.size()) {
                    int keyAt = AsyncListUtil.this.mMissingPositions.keyAt(i2);
                    if (tile.mStartPosition > keyAt || keyAt >= i) {
                        i2++;
                    } else {
                        AsyncListUtil.this.mMissingPositions.removeAt(i2);
                        AsyncListUtil.this.mViewCallback.onItemLoaded(keyAt);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void removeTile(int generation, int position) {
                if (isRequestedGeneration(generation)) {
                    TileList.Tile<T> removeAtPos = AsyncListUtil.this.mTileList.removeAtPos(position);
                    if (removeAtPos == null) {
                        Log.e(AsyncListUtil.TAG, "tile not found @" + position);
                    } else {
                        AsyncListUtil.this.mBackgroundProxy.recycleTile(removeAtPos);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void updateItemCount(int generation, int itemCount) {
                if (isRequestedGeneration(generation)) {
                    AsyncListUtil.this.mItemCount = itemCount;
                    AsyncListUtil.this.mViewCallback.onDataRefresh();
                    AsyncListUtil asyncListUtil = AsyncListUtil.this;
                    asyncListUtil.mDisplayedGeneration = asyncListUtil.mRequestedGeneration;
                    recycleAllTiles();
                    AsyncListUtil.this.mAllowScrollHints = false;
                    AsyncListUtil.this.updateRange();
                }
            }
        };
        this.mMainThreadCallback = mainThreadCallback;
        ThreadUtil.BackgroundCallback<T> backgroundCallback = new ThreadUtil.BackgroundCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.2
            private int mFirstRequiredTileStart;
            private int mGeneration;
            private int mItemCount;
            private int mLastRequiredTileStart;
            final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
            private TileList.Tile<T> mRecycledRoot;

            private TileList.Tile<T> acquireTile() {
                TileList.Tile<T> tile = this.mRecycledRoot;
                if (tile == null) {
                    return new TileList.Tile<>(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
                }
                TileList.Tile<T> tile2 = this.mRecycledRoot;
                this.mRecycledRoot = tile.mNext;
                return tile2;
            }

            private void addTile(TileList.Tile<T> tile) {
                this.mLoadedTiles.put(tile.mStartPosition, true);
                AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, tile);
            }

            private void flushTileCache(int scrollHint) {
                int maxCachedTiles = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
                while (this.mLoadedTiles.size() >= maxCachedTiles) {
                    int keyAt = this.mLoadedTiles.keyAt(0);
                    SparseBooleanArray sparseBooleanArray = this.mLoadedTiles;
                    int keyAt2 = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
                    int i = this.mFirstRequiredTileStart - keyAt;
                    int i2 = keyAt2 - this.mLastRequiredTileStart;
                    if (i > 0 && (i >= i2 || scrollHint == 2)) {
                        removeTile(keyAt);
                    } else {
                        if (i2 <= 0) {
                            return;
                        }
                        if (i >= i2 && scrollHint != 1) {
                            return;
                        } else {
                            removeTile(keyAt2);
                        }
                    }
                }
            }

            private int getTileStart(int position) {
                return position - (position % AsyncListUtil.this.mTileSize);
            }

            private boolean isTileLoaded(int position) {
                return this.mLoadedTiles.get(position);
            }

            /* JADX WARN: Failed to parse debug info
            jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
            	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
             */
            private void log(String str, Object... objArr) {
                StringBuilder append = new StringBuilder().append("[BKGR] ");
                String format = String.format(str, objArr);
                Log5ECF72.a(format);
                LogE84000.a(format);
                Log229316.a(format);
                Log.d(AsyncListUtil.TAG, append.append(format).toString());
            }

            private void removeTile(int position) {
                this.mLoadedTiles.delete(position);
                AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, position);
            }

            private void requestTiles(int firstTileStart, int lastTileStart, int scrollHint, boolean backwards) {
                int i = firstTileStart;
                while (i <= lastTileStart) {
                    AsyncListUtil.this.mBackgroundProxy.loadTile(backwards ? (lastTileStart + firstTileStart) - i : i, scrollHint);
                    i += AsyncListUtil.this.mTileSize;
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void loadTile(int position, int scrollHint) {
                if (isTileLoaded(position)) {
                    return;
                }
                TileList.Tile<T> acquireTile = acquireTile();
                acquireTile.mStartPosition = position;
                acquireTile.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - acquireTile.mStartPosition);
                AsyncListUtil.this.mDataCallback.fillData(acquireTile.mItems, acquireTile.mStartPosition, acquireTile.mItemCount);
                flushTileCache(scrollHint);
                addTile(acquireTile);
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void recycleTile(TileList.Tile<T> tile) {
                AsyncListUtil.this.mDataCallback.recycleData(tile.mItems, tile.mItemCount);
                tile.mNext = this.mRecycledRoot;
                this.mRecycledRoot = tile;
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void refresh(int generation) {
                this.mGeneration = generation;
                this.mLoadedTiles.clear();
                this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
                AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void updateRange(int rangeStart, int rangeEnd, int extRangeStart, int extRangeEnd, int scrollHint) {
                if (rangeStart > rangeEnd) {
                    return;
                }
                int tileStart = getTileStart(rangeStart);
                int tileStart2 = getTileStart(rangeEnd);
                this.mFirstRequiredTileStart = getTileStart(extRangeStart);
                int tileStart3 = getTileStart(extRangeEnd);
                this.mLastRequiredTileStart = tileStart3;
                if (scrollHint == 1) {
                    requestTiles(this.mFirstRequiredTileStart, tileStart2, scrollHint, true);
                    requestTiles(AsyncListUtil.this.mTileSize + tileStart2, this.mLastRequiredTileStart, scrollHint, false);
                } else {
                    requestTiles(tileStart, tileStart3, scrollHint, false);
                    requestTiles(this.mFirstRequiredTileStart, tileStart - AsyncListUtil.this.mTileSize, scrollHint, true);
                }
            }
        };
        this.mBackgroundCallback = backgroundCallback;
        this.mTClass = cls;
        this.mTileSize = tileSize;
        this.mDataCallback = dataCallback;
        this.mViewCallback = viewCallback;
        this.mTileList = new TileList<>(tileSize);
        MessageThreadUtil messageThreadUtil = new MessageThreadUtil();
        this.mMainThreadProxy = messageThreadUtil.getMainThreadProxy(mainThreadCallback);
        this.mBackgroundProxy = messageThreadUtil.getBackgroundProxy(backgroundCallback);
        refresh();
    }

    private boolean isRefreshPending() {
        return this.mRequestedGeneration != this.mDisplayedGeneration;
    }

    public T getItem(int position) {
        if (position < 0 || position >= this.mItemCount) {
            throw new IndexOutOfBoundsException(position + " is not within 0 and " + this.mItemCount);
        }
        T itemAt = this.mTileList.getItemAt(position);
        if (itemAt == null && !isRefreshPending()) {
            this.mMissingPositions.put(position, 0);
        }
        return itemAt;
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    void log(String str, Object... objArr) {
        StringBuilder append = new StringBuilder().append("[MAIN] ");
        String format = String.format(str, objArr);
        Log5ECF72.a(format);
        LogE84000.a(format);
        Log229316.a(format);
        Log.d(TAG, append.append(format).toString());
    }

    public void onRangeChanged() {
        if (isRefreshPending()) {
            return;
        }
        updateRange();
        this.mAllowScrollHints = true;
    }

    public void refresh() {
        this.mMissingPositions.clear();
        ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
        int i = this.mRequestedGeneration + 1;
        this.mRequestedGeneration = i;
        backgroundCallback.refresh(i);
    }

    void updateRange() {
        int i;
        this.mViewCallback.getItemRangeInto(this.mTmpRange);
        int[] iArr = this.mTmpRange;
        int i2 = iArr[0];
        int i3 = iArr[1];
        if (i2 > i3 || i2 < 0 || i3 >= this.mItemCount) {
            return;
        }
        if (this.mAllowScrollHints) {
            int[] iArr2 = this.mPrevRange;
            if (i2 > iArr2[1] || (i = iArr2[0]) > i3) {
                this.mScrollHint = 0;
            } else if (i2 < i) {
                this.mScrollHint = 1;
            } else if (i2 > i) {
                this.mScrollHint = 2;
            }
        } else {
            this.mScrollHint = 0;
        }
        int[] iArr3 = this.mPrevRange;
        iArr3[0] = i2;
        iArr3[1] = i3;
        this.mViewCallback.extendRangeInto(iArr, this.mTmpRangeExtended, this.mScrollHint);
        int[] iArr4 = this.mTmpRangeExtended;
        iArr4[0] = Math.min(this.mTmpRange[0], Math.max(iArr4[0], 0));
        int[] iArr5 = this.mTmpRangeExtended;
        iArr5[1] = Math.max(this.mTmpRange[1], Math.min(iArr5[1], this.mItemCount - 1));
        ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
        int[] iArr6 = this.mTmpRange;
        int i4 = iArr6[0];
        int i5 = iArr6[1];
        int[] iArr7 = this.mTmpRangeExtended;
        backgroundCallback.updateRange(i4, i5, iArr7[0], iArr7[1], this.mScrollHint);
    }
}
