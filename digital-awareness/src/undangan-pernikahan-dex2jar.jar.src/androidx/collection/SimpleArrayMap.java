package androidx.collection;

import java.util.ConcurrentModificationException;
import java.util.Map;

public class SimpleArrayMap<K, V>
{
  private static final int BASE_SIZE = 4;
  private static final int CACHE_SIZE = 10;
  private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
  private static final boolean DEBUG = false;
  private static final String TAG = "ArrayMap";
  static Object[] mBaseCache;
  static int mBaseCacheSize;
  static Object[] mTwiceBaseCache;
  static int mTwiceBaseCacheSize;
  Object[] mArray;
  int[] mHashes;
  int mSize;
  
  public SimpleArrayMap()
  {
    this.mHashes = ContainerHelpers.EMPTY_INTS;
    this.mArray = ContainerHelpers.EMPTY_OBJECTS;
    this.mSize = 0;
  }
  
  public SimpleArrayMap(int paramInt)
  {
    if (paramInt == 0)
    {
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
    }
    else
    {
      allocArrays(paramInt);
    }
    this.mSize = 0;
  }
  
  public SimpleArrayMap(SimpleArrayMap<K, V> paramSimpleArrayMap)
  {
    this();
    if (paramSimpleArrayMap != null) {
      putAll(paramSimpleArrayMap);
    }
  }
  
  private void allocArrays(int paramInt)
  {
    if (paramInt == 8) {
      try
      {
        Object[] arrayOfObject1 = mTwiceBaseCache;
        if (arrayOfObject1 != null)
        {
          this.mArray = arrayOfObject1;
          mTwiceBaseCache = (Object[])arrayOfObject1[0];
          this.mHashes = ((int[])arrayOfObject1[1]);
          arrayOfObject1[1] = null;
          arrayOfObject1[0] = null;
          mTwiceBaseCacheSize -= 1;
          return;
        }
      }
      finally {}
    }
    if (paramInt == 4) {
      try
      {
        Object[] arrayOfObject2 = mBaseCache;
        if (arrayOfObject2 != null)
        {
          this.mArray = arrayOfObject2;
          mBaseCache = (Object[])arrayOfObject2[0];
          this.mHashes = ((int[])arrayOfObject2[1]);
          arrayOfObject2[1] = null;
          arrayOfObject2[0] = null;
          mBaseCacheSize -= 1;
          return;
        }
      }
      finally {}
    }
    this.mHashes = new int[paramInt];
    this.mArray = new Object[paramInt << 1];
  }
  
  private static int binarySearchHashes(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    try
    {
      paramInt1 = ContainerHelpers.binarySearch(paramArrayOfInt, paramInt1, paramInt2);
      return paramInt1;
    }
    catch (ArrayIndexOutOfBoundsException paramArrayOfInt)
    {
      throw new ConcurrentModificationException();
    }
  }
  
  private static void freeArrays(int[] paramArrayOfInt, Object[] paramArrayOfObject, int paramInt)
  {
    if (paramArrayOfInt.length == 8) {
      try
      {
        if (mTwiceBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = mTwiceBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          for (paramInt = (paramInt << 1) - 1; paramInt >= 2; paramInt--) {
            paramArrayOfObject[paramInt] = null;
          }
          mTwiceBaseCache = paramArrayOfObject;
          mTwiceBaseCacheSize += 1;
        }
      }
      finally {}
    }
    if (paramArrayOfInt.length == 4) {
      try
      {
        if (mBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = mBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          for (paramInt = (paramInt << 1) - 1; paramInt >= 2; paramInt--) {
            paramArrayOfObject[paramInt] = null;
          }
          mBaseCache = paramArrayOfObject;
          mBaseCacheSize += 1;
        }
      }
      finally {}
    }
  }
  
  public void clear()
  {
    if (this.mSize > 0)
    {
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      int i = this.mSize;
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      this.mSize = 0;
      freeArrays(arrayOfInt, arrayOfObject, i);
    }
    if (this.mSize <= 0) {
      return;
    }
    throw new ConcurrentModificationException();
  }
  
  public boolean containsKey(Object paramObject)
  {
    boolean bool;
    if (indexOfKey(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean containsValue(Object paramObject)
  {
    boolean bool;
    if (indexOfValue(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void ensureCapacity(int paramInt)
  {
    int i = this.mSize;
    if (this.mHashes.length < paramInt)
    {
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      allocArrays(paramInt);
      if (this.mSize > 0)
      {
        System.arraycopy(arrayOfInt, 0, this.mHashes, 0, i);
        System.arraycopy(arrayOfObject, 0, this.mArray, 0, i << 1);
      }
      freeArrays(arrayOfInt, arrayOfObject, i);
    }
    if (this.mSize == i) {
      return;
    }
    throw new ConcurrentModificationException();
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    Object localObject1;
    int i;
    Object localObject3;
    Object localObject2;
    boolean bool;
    if ((paramObject instanceof SimpleArrayMap))
    {
      localObject1 = (SimpleArrayMap)paramObject;
      if (size() != ((SimpleArrayMap)localObject1).size()) {
        return false;
      }
      i = 0;
      try
      {
        while (i < this.mSize)
        {
          paramObject = keyAt(i);
          localObject3 = valueAt(i);
          localObject2 = ((SimpleArrayMap)localObject1).get(paramObject);
          if (localObject3 == null)
          {
            if ((localObject2 != null) || (!((SimpleArrayMap)localObject1).containsKey(paramObject))) {
              return false;
            }
          }
          else
          {
            bool = localObject3.equals(localObject2);
            if (!bool) {
              return false;
            }
          }
          i++;
        }
        return true;
      }
      catch (ClassCastException paramObject)
      {
        return false;
      }
      catch (NullPointerException paramObject)
      {
        return false;
      }
    }
    if ((paramObject instanceof Map))
    {
      paramObject = (Map)paramObject;
      if (size() != ((Map)paramObject).size()) {
        return false;
      }
      i = 0;
      try
      {
        while (i < this.mSize)
        {
          localObject3 = keyAt(i);
          localObject2 = valueAt(i);
          localObject1 = ((Map)paramObject).get(localObject3);
          if (localObject2 == null)
          {
            if ((localObject1 != null) || (!((Map)paramObject).containsKey(localObject3))) {
              return false;
            }
          }
          else
          {
            bool = localObject2.equals(localObject1);
            if (!bool) {
              return false;
            }
          }
          i++;
        }
        return true;
      }
      catch (ClassCastException paramObject)
      {
        return false;
      }
      catch (NullPointerException paramObject)
      {
        return false;
      }
    }
    return false;
  }
  
  public V get(Object paramObject)
  {
    return (V)getOrDefault(paramObject, null);
  }
  
  public V getOrDefault(Object paramObject, V paramV)
  {
    int i = indexOfKey(paramObject);
    if (i >= 0) {
      paramV = this.mArray[((i << 1) + 1)];
    }
    return paramV;
  }
  
  public int hashCode()
  {
    int[] arrayOfInt = this.mHashes;
    Object[] arrayOfObject = this.mArray;
    int k = 0;
    int j = 0;
    int i = 1;
    int n = this.mSize;
    while (j < n)
    {
      Object localObject = arrayOfObject[i];
      int i1 = arrayOfInt[j];
      int m;
      if (localObject == null) {
        m = 0;
      } else {
        m = localObject.hashCode();
      }
      k += (i1 ^ m);
      j++;
      i += 2;
    }
    return k;
  }
  
  int indexOf(Object paramObject, int paramInt)
  {
    int k = this.mSize;
    if (k == 0) {
      return -1;
    }
    int j = binarySearchHashes(this.mHashes, k, paramInt);
    if (j < 0) {
      return j;
    }
    if (paramObject.equals(this.mArray[(j << 1)])) {
      return j;
    }
    for (int i = j + 1; (i < k) && (this.mHashes[i] == paramInt); i++) {
      if (paramObject.equals(this.mArray[(i << 1)])) {
        return i;
      }
    }
    j--;
    while ((j >= 0) && (this.mHashes[j] == paramInt))
    {
      if (paramObject.equals(this.mArray[(j << 1)])) {
        return j;
      }
      j--;
    }
    return i ^ 0xFFFFFFFF;
  }
  
  public int indexOfKey(Object paramObject)
  {
    int i;
    if (paramObject == null) {
      i = indexOfNull();
    } else {
      i = indexOf(paramObject, paramObject.hashCode());
    }
    return i;
  }
  
  int indexOfNull()
  {
    int k = this.mSize;
    if (k == 0) {
      return -1;
    }
    int j = binarySearchHashes(this.mHashes, k, 0);
    if (j < 0) {
      return j;
    }
    if (this.mArray[(j << 1)] == null) {
      return j;
    }
    for (int i = j + 1; (i < k) && (this.mHashes[i] == 0); i++) {
      if (this.mArray[(i << 1)] == null) {
        return i;
      }
    }
    j--;
    while ((j >= 0) && (this.mHashes[j] == 0))
    {
      if (this.mArray[(j << 1)] == null) {
        return j;
      }
      j--;
    }
    return i ^ 0xFFFFFFFF;
  }
  
  int indexOfValue(Object paramObject)
  {
    int j = this.mSize * 2;
    Object[] arrayOfObject = this.mArray;
    int i;
    if (paramObject == null) {
      for (i = 1; i < j; i += 2) {
        if (arrayOfObject[i] == null) {
          return i >> 1;
        }
      }
    } else {
      for (i = 1; i < j; i += 2) {
        if (paramObject.equals(arrayOfObject[i])) {
          return i >> 1;
        }
      }
    }
    return -1;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.mSize <= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public K keyAt(int paramInt)
  {
    return (K)this.mArray[(paramInt << 1)];
  }
  
  public V put(K paramK, V paramV)
  {
    int k = this.mSize;
    int j;
    if (paramK == null)
    {
      j = 0;
      i = indexOfNull();
    }
    else
    {
      j = paramK.hashCode();
      i = indexOf(paramK, j);
    }
    Object localObject;
    if (i >= 0)
    {
      i = (i << 1) + 1;
      paramK = this.mArray;
      localObject = paramK[i];
      paramK[i] = paramV;
      return (V)localObject;
    }
    int m = i ^ 0xFFFFFFFF;
    if (k >= this.mHashes.length)
    {
      i = 4;
      if (k >= 8) {
        i = (k >> 1) + k;
      } else if (k >= 4) {
        i = 8;
      }
      localObject = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      allocArrays(i);
      if (k == this.mSize)
      {
        int[] arrayOfInt = this.mHashes;
        if (arrayOfInt.length > 0)
        {
          System.arraycopy(localObject, 0, arrayOfInt, 0, localObject.length);
          System.arraycopy(arrayOfObject, 0, this.mArray, 0, arrayOfObject.length);
        }
        freeArrays((int[])localObject, arrayOfObject, k);
      }
      else
      {
        throw new ConcurrentModificationException();
      }
    }
    if (m < k)
    {
      localObject = this.mHashes;
      System.arraycopy(localObject, m, localObject, m + 1, k - m);
      localObject = this.mArray;
      System.arraycopy(localObject, m << 1, localObject, m + 1 << 1, this.mSize - m << 1);
    }
    int i = this.mSize;
    if (k == i)
    {
      localObject = this.mHashes;
      if (m < localObject.length)
      {
        localObject[m] = j;
        localObject = this.mArray;
        localObject[(m << 1)] = paramK;
        localObject[((m << 1) + 1)] = paramV;
        this.mSize = (i + 1);
        return null;
      }
    }
    throw new ConcurrentModificationException();
  }
  
  public void putAll(SimpleArrayMap<? extends K, ? extends V> paramSimpleArrayMap)
  {
    int j = paramSimpleArrayMap.mSize;
    ensureCapacity(this.mSize + j);
    if (this.mSize == 0)
    {
      if (j > 0)
      {
        System.arraycopy(paramSimpleArrayMap.mHashes, 0, this.mHashes, 0, j);
        System.arraycopy(paramSimpleArrayMap.mArray, 0, this.mArray, 0, j << 1);
        this.mSize = j;
      }
    }
    else {
      for (int i = 0; i < j; i++) {
        put(paramSimpleArrayMap.keyAt(i), paramSimpleArrayMap.valueAt(i));
      }
    }
  }
  
  public V putIfAbsent(K paramK, V paramV)
  {
    Object localObject2 = get(paramK);
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = put(paramK, paramV);
    }
    return (V)localObject1;
  }
  
  public V remove(Object paramObject)
  {
    int i = indexOfKey(paramObject);
    if (i >= 0) {
      return (V)removeAt(i);
    }
    return null;
  }
  
  public boolean remove(Object paramObject1, Object paramObject2)
  {
    int i = indexOfKey(paramObject1);
    if (i >= 0)
    {
      paramObject1 = valueAt(i);
      if ((paramObject2 == paramObject1) || ((paramObject2 != null) && (paramObject2.equals(paramObject1))))
      {
        removeAt(i);
        return true;
      }
    }
    return false;
  }
  
  public V removeAt(int paramInt)
  {
    Object localObject = this.mArray;
    V ? = localObject[((paramInt << 1) + 1)];
    int k = this.mSize;
    if (k <= 1)
    {
      freeArrays(this.mHashes, (Object[])localObject, k);
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      paramInt = 0;
    }
    else
    {
      int j = k - 1;
      localObject = this.mHashes;
      int m = localObject.length;
      int i = 8;
      if ((m > 8) && (this.mSize < localObject.length / 3))
      {
        if (k > 8) {
          i = k + (k >> 1);
        }
        localObject = this.mHashes;
        Object[] arrayOfObject = this.mArray;
        allocArrays(i);
        if (k == this.mSize)
        {
          if (paramInt > 0)
          {
            System.arraycopy(localObject, 0, this.mHashes, 0, paramInt);
            System.arraycopy(arrayOfObject, 0, this.mArray, 0, paramInt << 1);
          }
          if (paramInt < j)
          {
            System.arraycopy(localObject, paramInt + 1, this.mHashes, paramInt, j - paramInt);
            System.arraycopy(arrayOfObject, paramInt + 1 << 1, this.mArray, paramInt << 1, j - paramInt << 1);
          }
          paramInt = j;
        }
        else
        {
          throw new ConcurrentModificationException();
        }
      }
      else
      {
        if (paramInt < j)
        {
          System.arraycopy(localObject, paramInt + 1, localObject, paramInt, j - paramInt);
          localObject = this.mArray;
          System.arraycopy(localObject, paramInt + 1 << 1, localObject, paramInt << 1, j - paramInt << 1);
        }
        localObject = this.mArray;
        localObject[(j << 1)] = null;
        localObject[((j << 1) + 1)] = null;
        paramInt = j;
      }
    }
    if (k == this.mSize)
    {
      this.mSize = paramInt;
      return ?;
    }
    throw new ConcurrentModificationException();
  }
  
  public V replace(K paramK, V paramV)
  {
    int i = indexOfKey(paramK);
    if (i >= 0) {
      return (V)setValueAt(i, paramV);
    }
    return null;
  }
  
  public boolean replace(K paramK, V paramV1, V paramV2)
  {
    int i = indexOfKey(paramK);
    if (i >= 0)
    {
      paramK = valueAt(i);
      if ((paramK == paramV1) || ((paramV1 != null) && (paramV1.equals(paramK))))
      {
        setValueAt(i, paramV2);
        return true;
      }
    }
    return false;
  }
  
  public V setValueAt(int paramInt, V paramV)
  {
    paramInt = (paramInt << 1) + 1;
    Object[] arrayOfObject = this.mArray;
    Object localObject = arrayOfObject[paramInt];
    arrayOfObject[paramInt] = paramV;
    return (V)localObject;
  }
  
  public int size()
  {
    return this.mSize;
  }
  
  public String toString()
  {
    if (isEmpty()) {
      return "{}";
    }
    StringBuilder localStringBuilder = new StringBuilder(this.mSize * 28);
    localStringBuilder.append('{');
    for (int i = 0; i < this.mSize; i++)
    {
      if (i > 0) {
        localStringBuilder.append(", ");
      }
      Object localObject = keyAt(i);
      if (localObject != this) {
        localStringBuilder.append(localObject);
      } else {
        localStringBuilder.append("(this Map)");
      }
      localStringBuilder.append('=');
      localObject = valueAt(i);
      if (localObject != this) {
        localStringBuilder.append(localObject);
      } else {
        localStringBuilder.append("(this Map)");
      }
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public V valueAt(int paramInt)
  {
    return (V)this.mArray[((paramInt << 1) + 1)];
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/collection/SimpleArrayMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */