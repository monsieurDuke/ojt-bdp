package androidx.constraintlayout.core;

final class Pools
{
  private static final boolean DEBUG = false;
  
  static abstract interface Pool<T>
  {
    public abstract T acquire();
    
    public abstract boolean release(T paramT);
    
    public abstract void releaseAll(T[] paramArrayOfT, int paramInt);
  }
  
  static class SimplePool<T>
    implements Pools.Pool<T>
  {
    private final Object[] mPool;
    private int mPoolSize;
    
    SimplePool(int paramInt)
    {
      if (paramInt > 0)
      {
        this.mPool = new Object[paramInt];
        return;
      }
      throw new IllegalArgumentException("The max pool size must be > 0");
    }
    
    private boolean isInPool(T paramT)
    {
      for (int i = 0; i < this.mPoolSize; i++) {
        if (this.mPool[i] == paramT) {
          return true;
        }
      }
      return false;
    }
    
    public T acquire()
    {
      int i = this.mPoolSize;
      if (i > 0)
      {
        int j = i - 1;
        Object[] arrayOfObject = this.mPool;
        Object localObject = arrayOfObject[j];
        arrayOfObject[j] = null;
        this.mPoolSize = (i - 1);
        return (T)localObject;
      }
      return null;
    }
    
    public boolean release(T paramT)
    {
      int i = this.mPoolSize;
      Object[] arrayOfObject = this.mPool;
      if (i < arrayOfObject.length)
      {
        arrayOfObject[i] = paramT;
        this.mPoolSize = (i + 1);
        return true;
      }
      return false;
    }
    
    public void releaseAll(T[] paramArrayOfT, int paramInt)
    {
      int i = paramInt;
      if (paramInt > paramArrayOfT.length) {
        i = paramArrayOfT.length;
      }
      for (paramInt = 0; paramInt < i; paramInt++)
      {
        T ? = paramArrayOfT[paramInt];
        int j = this.mPoolSize;
        Object[] arrayOfObject = this.mPool;
        if (j < arrayOfObject.length)
        {
          arrayOfObject[j] = ?;
          this.mPoolSize = (j + 1);
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/Pools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */