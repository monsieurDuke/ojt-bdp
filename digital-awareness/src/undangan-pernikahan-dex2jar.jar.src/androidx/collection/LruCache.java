package androidx.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class LruCache<K, V>
{
  private int createCount;
  private int evictionCount;
  private int hitCount;
  private final LinkedHashMap<K, V> map;
  private int maxSize;
  private int missCount;
  private int putCount;
  private int size;
  
  public LruCache(int paramInt)
  {
    if (paramInt > 0)
    {
      this.maxSize = paramInt;
      this.map = new LinkedHashMap(0, 0.75F, true);
      return;
    }
    throw new IllegalArgumentException("maxSize <= 0");
  }
  
  private int safeSizeOf(K paramK, V paramV)
  {
    int i = sizeOf(paramK, paramV);
    if (i >= 0) {
      return i;
    }
    throw new IllegalStateException("Negative size: " + paramK + "=" + paramV);
  }
  
  protected V create(K paramK)
  {
    return null;
  }
  
  public final int createCount()
  {
    try
    {
      int i = this.createCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected void entryRemoved(boolean paramBoolean, K paramK, V paramV1, V paramV2) {}
  
  public final void evictAll()
  {
    trimToSize(-1);
  }
  
  public final int evictionCount()
  {
    try
    {
      int i = this.evictionCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final V get(K paramK)
  {
    if (paramK != null) {
      try
      {
        Object localObject1 = this.map.get(paramK);
        if (localObject1 != null)
        {
          this.hitCount += 1;
          return (V)localObject1;
        }
        this.missCount += 1;
        Object localObject2 = create(paramK);
        if (localObject2 == null) {
          return null;
        }
        try
        {
          this.createCount += 1;
          localObject1 = this.map.put(paramK, localObject2);
          if (localObject1 != null) {
            this.map.put(paramK, localObject1);
          } else {
            this.size += safeSizeOf(paramK, localObject2);
          }
          if (localObject1 != null)
          {
            entryRemoved(false, paramK, localObject2, localObject1);
            return (V)localObject1;
          }
          trimToSize(this.maxSize);
          return (V)localObject2;
        }
        finally {}
        throw new NullPointerException("key == null");
      }
      finally {}
    }
  }
  
  public final int hitCount()
  {
    try
    {
      int i = this.hitCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int maxSize()
  {
    try
    {
      int i = this.maxSize;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int missCount()
  {
    try
    {
      int i = this.missCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final V put(K paramK, V paramV)
  {
    if ((paramK != null) && (paramV != null)) {
      try
      {
        this.putCount += 1;
        this.size += safeSizeOf(paramK, paramV);
        Object localObject = this.map.put(paramK, paramV);
        if (localObject != null) {
          this.size -= safeSizeOf(paramK, localObject);
        }
        if (localObject != null) {
          entryRemoved(false, paramK, localObject, paramV);
        }
        trimToSize(this.maxSize);
        return (V)localObject;
      }
      finally {}
    }
    throw new NullPointerException("key == null || value == null");
  }
  
  public final int putCount()
  {
    try
    {
      int i = this.putCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final V remove(K paramK)
  {
    if (paramK != null) {
      try
      {
        Object localObject = this.map.remove(paramK);
        if (localObject != null) {
          this.size -= safeSizeOf(paramK, localObject);
        }
        if (localObject != null) {
          entryRemoved(false, paramK, localObject, null);
        }
        return (V)localObject;
      }
      finally {}
    }
    throw new NullPointerException("key == null");
  }
  
  public void resize(int paramInt)
  {
    if (paramInt > 0) {
      try
      {
        this.maxSize = paramInt;
        trimToSize(paramInt);
        return;
      }
      finally {}
    }
    throw new IllegalArgumentException("maxSize <= 0");
  }
  
  public final int size()
  {
    try
    {
      int i = this.size;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected int sizeOf(K paramK, V paramV)
  {
    return 1;
  }
  
  public final Map<K, V> snapshot()
  {
    try
    {
      LinkedHashMap localLinkedHashMap = new LinkedHashMap(this.map);
      return localLinkedHashMap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String toString()
  {
    try
    {
      int i = this.hitCount;
      int j = this.missCount + i;
      if (j != 0) {
        i = i * 100 / j;
      } else {
        i = 0;
      }
      String str = String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[] { Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(i) });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
    finally {}
  }
  
  public void trimToSize(int paramInt)
  {
    for (;;)
    {
      try
      {
        if ((this.size >= 0) && ((!this.map.isEmpty()) || (this.size == 0)))
        {
          if ((this.size > paramInt) && (!this.map.isEmpty()))
          {
            localObject3 = (Map.Entry)this.map.entrySet().iterator().next();
            localObject1 = ((Map.Entry)localObject3).getKey();
            localObject3 = ((Map.Entry)localObject3).getValue();
            this.map.remove(localObject1);
            this.size -= safeSizeOf(localObject1, localObject3);
            this.evictionCount += 1;
            entryRemoved(true, localObject1, localObject3, null);
            continue;
          }
          return;
        }
        Object localObject3 = new java/lang/IllegalStateException;
        Object localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((IllegalStateException)localObject3).<init>(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
        throw ((Throwable)localObject3);
      }
      finally {}
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/collection/LruCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */