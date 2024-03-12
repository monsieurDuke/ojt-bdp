package androidx.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E>
  implements Collection<E>, Set<E>
{
  private static final int BASE_SIZE = 4;
  private static final int CACHE_SIZE = 10;
  private static final boolean DEBUG = false;
  private static final int[] INT = new int[0];
  private static final Object[] OBJECT = new Object[0];
  private static final String TAG = "ArraySet";
  private static Object[] sBaseCache;
  private static int sBaseCacheSize;
  private static Object[] sTwiceBaseCache;
  private static int sTwiceBaseCacheSize;
  Object[] mArray;
  private MapCollections<E, E> mCollections;
  private int[] mHashes;
  int mSize;
  
  public ArraySet()
  {
    this(0);
  }
  
  public ArraySet(int paramInt)
  {
    if (paramInt == 0)
    {
      this.mHashes = INT;
      this.mArray = OBJECT;
    }
    else
    {
      allocArrays(paramInt);
    }
    this.mSize = 0;
  }
  
  public ArraySet(ArraySet<E> paramArraySet)
  {
    this();
    if (paramArraySet != null) {
      addAll(paramArraySet);
    }
  }
  
  public ArraySet(Collection<E> paramCollection)
  {
    this();
    if (paramCollection != null) {
      addAll(paramCollection);
    }
  }
  
  private void allocArrays(int paramInt)
  {
    if (paramInt == 8) {
      try
      {
        Object[] arrayOfObject1 = sTwiceBaseCache;
        if (arrayOfObject1 != null)
        {
          this.mArray = arrayOfObject1;
          sTwiceBaseCache = (Object[])arrayOfObject1[0];
          this.mHashes = ((int[])arrayOfObject1[1]);
          arrayOfObject1[1] = null;
          arrayOfObject1[0] = null;
          sTwiceBaseCacheSize -= 1;
          return;
        }
      }
      finally {}
    }
    if (paramInt == 4) {
      try
      {
        Object[] arrayOfObject2 = sBaseCache;
        if (arrayOfObject2 != null)
        {
          this.mArray = arrayOfObject2;
          sBaseCache = (Object[])arrayOfObject2[0];
          this.mHashes = ((int[])arrayOfObject2[1]);
          arrayOfObject2[1] = null;
          arrayOfObject2[0] = null;
          sBaseCacheSize -= 1;
          return;
        }
      }
      finally {}
    }
    this.mHashes = new int[paramInt];
    this.mArray = new Object[paramInt];
  }
  
  private static void freeArrays(int[] paramArrayOfInt, Object[] paramArrayOfObject, int paramInt)
  {
    if (paramArrayOfInt.length == 8) {
      try
      {
        if (sTwiceBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = sTwiceBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          paramInt--;
          while (paramInt >= 2)
          {
            paramArrayOfObject[paramInt] = null;
            paramInt--;
          }
          sTwiceBaseCache = paramArrayOfObject;
          sTwiceBaseCacheSize += 1;
        }
      }
      finally {}
    }
    if (paramArrayOfInt.length == 4) {
      try
      {
        if (sBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = sBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          paramInt--;
          while (paramInt >= 2)
          {
            paramArrayOfObject[paramInt] = null;
            paramInt--;
          }
          sBaseCache = paramArrayOfObject;
          sBaseCacheSize += 1;
        }
      }
      finally {}
    }
  }
  
  private MapCollections<E, E> getCollection()
  {
    if (this.mCollections == null) {
      this.mCollections = new MapCollections()
      {
        protected void colClear()
        {
          ArraySet.this.clear();
        }
        
        protected Object colGetEntry(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          return ArraySet.this.mArray[paramAnonymousInt1];
        }
        
        protected Map<E, E> colGetMap()
        {
          throw new UnsupportedOperationException("not a map");
        }
        
        protected int colGetSize()
        {
          return ArraySet.this.mSize;
        }
        
        protected int colIndexOfKey(Object paramAnonymousObject)
        {
          return ArraySet.this.indexOf(paramAnonymousObject);
        }
        
        protected int colIndexOfValue(Object paramAnonymousObject)
        {
          return ArraySet.this.indexOf(paramAnonymousObject);
        }
        
        protected void colPut(E paramAnonymousE1, E paramAnonymousE2)
        {
          ArraySet.this.add(paramAnonymousE1);
        }
        
        protected void colRemoveAt(int paramAnonymousInt)
        {
          ArraySet.this.removeAt(paramAnonymousInt);
        }
        
        protected E colSetValue(int paramAnonymousInt, E paramAnonymousE)
        {
          throw new UnsupportedOperationException("not a map");
        }
      };
    }
    return this.mCollections;
  }
  
  private int indexOf(Object paramObject, int paramInt)
  {
    int j = this.mSize;
    if (j == 0) {
      return -1;
    }
    int k = ContainerHelpers.binarySearch(this.mHashes, j, paramInt);
    if (k < 0) {
      return k;
    }
    if (paramObject.equals(this.mArray[k])) {
      return k;
    }
    for (int i = k + 1; (i < j) && (this.mHashes[i] == paramInt); i++) {
      if (paramObject.equals(this.mArray[i])) {
        return i;
      }
    }
    for (j = k - 1; (j >= 0) && (this.mHashes[j] == paramInt); j--) {
      if (paramObject.equals(this.mArray[j])) {
        return j;
      }
    }
    return i ^ 0xFFFFFFFF;
  }
  
  private int indexOfNull()
  {
    int k = this.mSize;
    if (k == 0) {
      return -1;
    }
    int j = ContainerHelpers.binarySearch(this.mHashes, k, 0);
    if (j < 0) {
      return j;
    }
    if (this.mArray[j] == null) {
      return j;
    }
    for (int i = j + 1; (i < k) && (this.mHashes[i] == 0); i++) {
      if (this.mArray[i] == null) {
        return i;
      }
    }
    j--;
    while ((j >= 0) && (this.mHashes[j] == 0))
    {
      if (this.mArray[j] == null) {
        return j;
      }
      j--;
    }
    return i ^ 0xFFFFFFFF;
  }
  
  public boolean add(E paramE)
  {
    int j;
    if (paramE == null)
    {
      j = 0;
      i = indexOfNull();
    }
    else
    {
      j = paramE.hashCode();
      i = indexOf(paramE, j);
    }
    if (i >= 0) {
      return false;
    }
    int k = i ^ 0xFFFFFFFF;
    int m = this.mSize;
    Object localObject;
    if (m >= this.mHashes.length)
    {
      i = 4;
      if (m >= 8) {
        i = (m >> 1) + m;
      } else if (m >= 4) {
        i = 8;
      }
      localObject = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      allocArrays(i);
      int[] arrayOfInt = this.mHashes;
      if (arrayOfInt.length > 0)
      {
        System.arraycopy(localObject, 0, arrayOfInt, 0, localObject.length);
        System.arraycopy(arrayOfObject, 0, this.mArray, 0, arrayOfObject.length);
      }
      freeArrays((int[])localObject, arrayOfObject, this.mSize);
    }
    int i = this.mSize;
    if (k < i)
    {
      localObject = this.mHashes;
      System.arraycopy(localObject, k, localObject, k + 1, i - k);
      localObject = this.mArray;
      System.arraycopy(localObject, k, localObject, k + 1, this.mSize - k);
    }
    this.mHashes[k] = j;
    this.mArray[k] = paramE;
    this.mSize += 1;
    return true;
  }
  
  public void addAll(ArraySet<? extends E> paramArraySet)
  {
    int j = paramArraySet.mSize;
    ensureCapacity(this.mSize + j);
    if (this.mSize == 0)
    {
      if (j > 0)
      {
        System.arraycopy(paramArraySet.mHashes, 0, this.mHashes, 0, j);
        System.arraycopy(paramArraySet.mArray, 0, this.mArray, 0, j);
        this.mSize = j;
      }
    }
    else {
      for (int i = 0; i < j; i++) {
        add(paramArraySet.valueAt(i));
      }
    }
  }
  
  public boolean addAll(Collection<? extends E> paramCollection)
  {
    ensureCapacity(this.mSize + paramCollection.size());
    boolean bool = false;
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      bool |= add(paramCollection.next());
    }
    return bool;
  }
  
  public void clear()
  {
    int i = this.mSize;
    if (i != 0)
    {
      freeArrays(this.mHashes, this.mArray, i);
      this.mHashes = INT;
      this.mArray = OBJECT;
      this.mSize = 0;
    }
  }
  
  public boolean contains(Object paramObject)
  {
    boolean bool;
    if (indexOf(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean containsAll(Collection<?> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (!contains(paramCollection.next())) {
        return false;
      }
    }
    return true;
  }
  
  public void ensureCapacity(int paramInt)
  {
    if (this.mHashes.length < paramInt)
    {
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      allocArrays(paramInt);
      paramInt = this.mSize;
      if (paramInt > 0)
      {
        System.arraycopy(arrayOfInt, 0, this.mHashes, 0, paramInt);
        System.arraycopy(arrayOfObject, 0, this.mArray, 0, this.mSize);
      }
      freeArrays(arrayOfInt, arrayOfObject, this.mSize);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject instanceof Set))
    {
      paramObject = (Set)paramObject;
      if (size() != ((Set)paramObject).size()) {
        return false;
      }
      int i = 0;
      try
      {
        while (i < this.mSize)
        {
          boolean bool = ((Set)paramObject).contains(valueAt(i));
          if (!bool) {
            return false;
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
  
  public int hashCode()
  {
    int[] arrayOfInt = this.mHashes;
    int j = 0;
    int i = 0;
    int k = this.mSize;
    while (i < k)
    {
      j += arrayOfInt[i];
      i++;
    }
    return j;
  }
  
  public int indexOf(Object paramObject)
  {
    int i;
    if (paramObject == null) {
      i = indexOfNull();
    } else {
      i = indexOf(paramObject, paramObject.hashCode());
    }
    return i;
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
  
  public Iterator<E> iterator()
  {
    return getCollection().getKeySet().iterator();
  }
  
  public boolean remove(Object paramObject)
  {
    int i = indexOf(paramObject);
    if (i >= 0)
    {
      removeAt(i);
      return true;
    }
    return false;
  }
  
  public boolean removeAll(ArraySet<? extends E> paramArraySet)
  {
    int j = paramArraySet.mSize;
    int k = this.mSize;
    for (int i = 0; i < j; i++) {
      remove(paramArraySet.valueAt(i));
    }
    boolean bool;
    if (k != this.mSize) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean removeAll(Collection<?> paramCollection)
  {
    boolean bool = false;
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      bool |= remove(paramCollection.next());
    }
    return bool;
  }
  
  public E removeAt(int paramInt)
  {
    Object localObject = this.mArray;
    E ? = localObject[paramInt];
    int j = this.mSize;
    if (j <= 1)
    {
      freeArrays(this.mHashes, (Object[])localObject, j);
      this.mHashes = INT;
      this.mArray = OBJECT;
      this.mSize = 0;
    }
    else
    {
      localObject = this.mHashes;
      int k = localObject.length;
      int i = 8;
      if ((k > 8) && (j < localObject.length / 3))
      {
        if (j > 8) {
          i = j + (j >> 1);
        }
        localObject = this.mHashes;
        Object[] arrayOfObject = this.mArray;
        allocArrays(i);
        this.mSize -= 1;
        if (paramInt > 0)
        {
          System.arraycopy(localObject, 0, this.mHashes, 0, paramInt);
          System.arraycopy(arrayOfObject, 0, this.mArray, 0, paramInt);
        }
        i = this.mSize;
        if (paramInt < i)
        {
          System.arraycopy(localObject, paramInt + 1, this.mHashes, paramInt, i - paramInt);
          System.arraycopy(arrayOfObject, paramInt + 1, this.mArray, paramInt, this.mSize - paramInt);
        }
      }
      else
      {
        i = j - 1;
        this.mSize = i;
        if (paramInt < i)
        {
          System.arraycopy(localObject, paramInt + 1, localObject, paramInt, i - paramInt);
          localObject = this.mArray;
          System.arraycopy(localObject, paramInt + 1, localObject, paramInt, this.mSize - paramInt);
        }
        this.mArray[this.mSize] = null;
      }
    }
    return ?;
  }
  
  public boolean retainAll(Collection<?> paramCollection)
  {
    boolean bool = false;
    for (int i = this.mSize - 1; i >= 0; i--) {
      if (!paramCollection.contains(this.mArray[i]))
      {
        removeAt(i);
        bool = true;
      }
    }
    return bool;
  }
  
  public int size()
  {
    return this.mSize;
  }
  
  public Object[] toArray()
  {
    int i = this.mSize;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(this.mArray, 0, arrayOfObject, 0, i);
    return arrayOfObject;
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    Object localObject = paramArrayOfT;
    if (paramArrayOfT.length < this.mSize) {
      localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.mSize);
    }
    System.arraycopy(this.mArray, 0, localObject, 0, this.mSize);
    int j = localObject.length;
    int i = this.mSize;
    if (j > i) {
      localObject[i] = null;
    }
    return (T[])localObject;
  }
  
  public String toString()
  {
    if (isEmpty()) {
      return "{}";
    }
    StringBuilder localStringBuilder = new StringBuilder(this.mSize * 14);
    localStringBuilder.append('{');
    for (int i = 0; i < this.mSize; i++)
    {
      if (i > 0) {
        localStringBuilder.append(", ");
      }
      Object localObject = valueAt(i);
      if (localObject != this) {
        localStringBuilder.append(localObject);
      } else {
        localStringBuilder.append("(this Set)");
      }
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public E valueAt(int paramInt)
  {
    return (E)this.mArray[paramInt];
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/collection/ArraySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */