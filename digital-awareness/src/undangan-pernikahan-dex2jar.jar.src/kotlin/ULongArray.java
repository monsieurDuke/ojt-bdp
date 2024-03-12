package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000F\n\002\030\002\n\002\020\036\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\026\n\002\b\006\n\002\020\013\n\002\b\t\n\002\020\000\n\002\b\f\n\002\020(\n\002\b\003\n\002\020\002\n\002\b\004\n\002\020\016\n\002\b\004\b@\030\0002\b\022\004\022\0020\0020\001:\0012B\024\b\026\022\006\020\003\032\0020\004ø\001\000¢\006\004\b\005\020\006B\024\b\001\022\006\020\007\032\0020\bø\001\000¢\006\004\b\005\020\tJ\033\020\016\032\0020\0172\006\020\020\032\0020\002H\002ø\001\000¢\006\004\b\021\020\022J \020\023\032\0020\0172\f\020\024\032\b\022\004\022\0020\0020\001H\026ø\001\000¢\006\004\b\025\020\026J\032\020\027\032\0020\0172\b\020\030\032\004\030\0010\031HÖ\003¢\006\004\b\032\020\033J\036\020\034\032\0020\0022\006\020\035\032\0020\004H\002ø\001\001ø\001\000¢\006\004\b\036\020\037J\020\020 \032\0020\004HÖ\001¢\006\004\b!\020\013J\017\020\"\032\0020\017H\026¢\006\004\b#\020$J\031\020%\032\b\022\004\022\0020\0020&H\002ø\001\000¢\006\004\b'\020(J#\020)\032\0020*2\006\020\035\032\0020\0042\006\020+\032\0020\002H\002ø\001\000¢\006\004\b,\020-J\020\020.\032\0020/HÖ\001¢\006\004\b0\0201R\024\020\003\032\0020\0048VX\004¢\006\006\032\004\b\n\020\013R\026\020\007\032\0020\b8\000X\004¢\006\b\n\000\022\004\b\f\020\r\001\007\001\0020\bø\001\000\002\b\n\002\b\031\n\002\b!¨\0063"}, d2={"Lkotlin/ULongArray;", "", "Lkotlin/ULong;", "size", "", "constructor-impl", "(I)[J", "storage", "", "([J)[J", "getSize-impl", "([J)I", "getStorage$annotations", "()V", "contains", "", "element", "contains-VKZWuLQ", "([JJ)Z", "containsAll", "elements", "containsAll-impl", "([JLjava/util/Collection;)Z", "equals", "other", "", "equals-impl", "([JLjava/lang/Object;)Z", "get", "index", "get-s-VKNKU", "([JI)J", "hashCode", "hashCode-impl", "isEmpty", "isEmpty-impl", "([J)Z", "iterator", "", "iterator-impl", "([J)Ljava/util/Iterator;", "set", "", "value", "set-k8EXiF4", "([JIJ)V", "toString", "", "toString-impl", "([J)Ljava/lang/String;", "Iterator", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class ULongArray
  implements Collection<ULong>, KMappedMarker
{
  private final long[] storage;
  
  public static long[] constructor-impl(int paramInt)
  {
    return constructor-impl(new long[paramInt]);
  }
  
  public static long[] constructor-impl(long[] paramArrayOfLong)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfLong, "storage");
    return paramArrayOfLong;
  }
  
  public static boolean contains-VKZWuLQ(long[] paramArrayOfLong, long paramLong)
  {
    return ArraysKt.contains(paramArrayOfLong, paramLong);
  }
  
  public static boolean containsAll-impl(long[] paramArrayOfLong, Collection<ULong> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    paramCollection = (Iterable)paramCollection;
    boolean bool2 = ((Collection)paramCollection).isEmpty();
    boolean bool1 = false;
    if (bool2)
    {
      bool1 = true;
    }
    else
    {
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext())
      {
        Object localObject = paramCollection.next();
        int i;
        if (((localObject instanceof ULong)) && (ArraysKt.contains(paramArrayOfLong, ((ULong)localObject).unbox-impl()))) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          return bool1;
        }
      }
      bool1 = true;
    }
    return bool1;
  }
  
  public static boolean equals-impl(long[] paramArrayOfLong, Object paramObject)
  {
    if (!(paramObject instanceof ULongArray)) {
      return false;
    }
    return Intrinsics.areEqual(paramArrayOfLong, ((ULongArray)paramObject).unbox-impl());
  }
  
  public static final boolean equals-impl0(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    return Intrinsics.areEqual(paramArrayOfLong1, paramArrayOfLong2);
  }
  
  public static final long get-s-VKNKU(long[] paramArrayOfLong, int paramInt)
  {
    return ULong.constructor-impl(paramArrayOfLong[paramInt]);
  }
  
  public static int getSize-impl(long[] paramArrayOfLong)
  {
    return paramArrayOfLong.length;
  }
  
  public static int hashCode-impl(long[] paramArrayOfLong)
  {
    return Arrays.hashCode(paramArrayOfLong);
  }
  
  public static boolean isEmpty-impl(long[] paramArrayOfLong)
  {
    boolean bool;
    if (paramArrayOfLong.length == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static Iterator<ULong> iterator-impl(long[] paramArrayOfLong)
  {
    return (Iterator)new Iterator(paramArrayOfLong);
  }
  
  public static final void set-k8EXiF4(long[] paramArrayOfLong, int paramInt, long paramLong)
  {
    paramArrayOfLong[paramInt] = paramLong;
  }
  
  public static String toString-impl(long[] paramArrayOfLong)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("ULongArray(storage=");
    paramArrayOfLong = Arrays.toString(paramArrayOfLong);
    Log5ECF72.a(paramArrayOfLong);
    LogE84000.a(paramArrayOfLong);
    Log229316.a(paramArrayOfLong);
    return paramArrayOfLong + ')';
  }
  
  public boolean add-VKZWuLQ(long paramLong)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean addAll(Collection<? extends ULong> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public void clear()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean contains-VKZWuLQ(long paramLong)
  {
    return contains-VKZWuLQ(this.storage, paramLong);
  }
  
  public boolean containsAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    return containsAll-impl(this.storage, paramCollection);
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.storage, paramObject);
  }
  
  public int getSize()
  {
    return getSize-impl(this.storage);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.storage);
  }
  
  public boolean isEmpty()
  {
    return isEmpty-impl(this.storage);
  }
  
  public Iterator<ULong> iterator()
  {
    return iterator-impl(this.storage);
  }
  
  public boolean remove(Object paramObject)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean removeAll(Collection<? extends Object> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean retainAll(Collection<? extends Object> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public Object[] toArray()
  {
    return CollectionToArray.toArray((Collection)this);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "array");
    return CollectionToArray.toArray((Collection)this, paramArrayOfT);
  }
  
  public String toString()
  {
    String str = toString-impl(this.storage);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  @Metadata(d1={"\000$\n\002\030\002\n\002\020(\n\002\030\002\n\000\n\002\020\026\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\002\b\004\b\002\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\t\020\b\032\0020\tH\002J\026\020\n\032\0020\002H\002ø\001\000ø\001\001¢\006\004\b\013\020\fR\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\006\032\0020\007X\016¢\006\002\n\000ø\001\001\002\b\n\002\b!\n\002\b\031¨\006\r"}, d2={"Lkotlin/ULongArray$Iterator;", "", "Lkotlin/ULong;", "array", "", "([J)V", "index", "", "hasNext", "", "next", "next-s-VKNKU", "()J", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Iterator
    implements Iterator<ULong>, KMappedMarker
  {
    private final long[] array;
    private int index;
    
    public Iterator(long[] paramArrayOfLong)
    {
      this.array = paramArrayOfLong;
    }
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.index < this.array.length) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public long next-s-VKNKU()
    {
      int i = this.index;
      Object localObject = this.array;
      if (i < localObject.length)
      {
        this.index = (i + 1);
        return ULong.constructor-impl(localObject[i]);
      }
      localObject = String.valueOf(this.index);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      throw new NoSuchElementException((String)localObject);
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ULongArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */