package androidx.recyclerview.widget;

import android.util.SparseArray;
import java.lang.reflect.Array;

class TileList<T>
{
  Tile<T> mLastAccessedTile;
  final int mTileSize;
  private final SparseArray<Tile<T>> mTiles = new SparseArray(10);
  
  public TileList(int paramInt)
  {
    this.mTileSize = paramInt;
  }
  
  public Tile<T> addOrReplace(Tile<T> paramTile)
  {
    int i = this.mTiles.indexOfKey(paramTile.mStartPosition);
    if (i < 0)
    {
      this.mTiles.put(paramTile.mStartPosition, paramTile);
      return null;
    }
    Tile localTile = (Tile)this.mTiles.valueAt(i);
    this.mTiles.setValueAt(i, paramTile);
    if (this.mLastAccessedTile == localTile) {
      this.mLastAccessedTile = paramTile;
    }
    return localTile;
  }
  
  public void clear()
  {
    this.mTiles.clear();
  }
  
  public Tile<T> getAtIndex(int paramInt)
  {
    return (Tile)this.mTiles.valueAt(paramInt);
  }
  
  public T getItemAt(int paramInt)
  {
    Tile localTile = this.mLastAccessedTile;
    if ((localTile == null) || (!localTile.containsPosition(paramInt)))
    {
      int i = this.mTileSize;
      i = this.mTiles.indexOfKey(paramInt - paramInt % i);
      if (i < 0) {
        return null;
      }
      this.mLastAccessedTile = ((Tile)this.mTiles.valueAt(i));
    }
    return (T)this.mLastAccessedTile.getByPosition(paramInt);
  }
  
  public Tile<T> removeAtPos(int paramInt)
  {
    Tile localTile = (Tile)this.mTiles.get(paramInt);
    if (this.mLastAccessedTile == localTile) {
      this.mLastAccessedTile = null;
    }
    this.mTiles.delete(paramInt);
    return localTile;
  }
  
  public int size()
  {
    return this.mTiles.size();
  }
  
  public static class Tile<T>
  {
    public int mItemCount;
    public final T[] mItems;
    Tile<T> mNext;
    public int mStartPosition;
    
    public Tile(Class<T> paramClass, int paramInt)
    {
      this.mItems = ((Object[])Array.newInstance(paramClass, paramInt));
    }
    
    boolean containsPosition(int paramInt)
    {
      int i = this.mStartPosition;
      boolean bool;
      if ((i <= paramInt) && (paramInt < i + this.mItemCount)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    T getByPosition(int paramInt)
    {
      return (T)this.mItems[(paramInt - this.mStartPosition)];
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/TileList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */