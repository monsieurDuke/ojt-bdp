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

@Metadata(d1={"\000F\n\002\030\002\n\002\020\036\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\027\n\002\b\006\n\002\020\013\n\002\b\t\n\002\020\000\n\002\b\f\n\002\020(\n\002\b\003\n\002\020\002\n\002\b\004\n\002\020\016\n\002\b\004\b@\030\0002\b\022\004\022\0020\0020\001:\0012B\024\b\026\022\006\020\003\032\0020\004ø\001\000¢\006\004\b\005\020\006B\024\b\001\022\006\020\007\032\0020\bø\001\000¢\006\004\b\005\020\tJ\033\020\016\032\0020\0172\006\020\020\032\0020\002H\002ø\001\000¢\006\004\b\021\020\022J \020\023\032\0020\0172\f\020\024\032\b\022\004\022\0020\0020\001H\026ø\001\000¢\006\004\b\025\020\026J\032\020\027\032\0020\0172\b\020\030\032\004\030\0010\031HÖ\003¢\006\004\b\032\020\033J\036\020\034\032\0020\0022\006\020\035\032\0020\004H\002ø\001\001ø\001\000¢\006\004\b\036\020\037J\020\020 \032\0020\004HÖ\001¢\006\004\b!\020\013J\017\020\"\032\0020\017H\026¢\006\004\b#\020$J\031\020%\032\b\022\004\022\0020\0020&H\002ø\001\000¢\006\004\b'\020(J#\020)\032\0020*2\006\020\035\032\0020\0042\006\020+\032\0020\002H\002ø\001\000¢\006\004\b,\020-J\020\020.\032\0020/HÖ\001¢\006\004\b0\0201R\024\020\003\032\0020\0048VX\004¢\006\006\032\004\b\n\020\013R\026\020\007\032\0020\b8\000X\004¢\006\b\n\000\022\004\b\f\020\r\001\007\001\0020\bø\001\000\002\b\n\002\b\031\n\002\b!¨\0063"}, d2={"Lkotlin/UShortArray;", "", "Lkotlin/UShort;", "size", "", "constructor-impl", "(I)[S", "storage", "", "([S)[S", "getSize-impl", "([S)I", "getStorage$annotations", "()V", "contains", "", "element", "contains-xj2QHRw", "([SS)Z", "containsAll", "elements", "containsAll-impl", "([SLjava/util/Collection;)Z", "equals", "other", "", "equals-impl", "([SLjava/lang/Object;)Z", "get", "index", "get-Mh2AYeg", "([SI)S", "hashCode", "hashCode-impl", "isEmpty", "isEmpty-impl", "([S)Z", "iterator", "", "iterator-impl", "([S)Ljava/util/Iterator;", "set", "", "value", "set-01HTLdE", "([SIS)V", "toString", "", "toString-impl", "([S)Ljava/lang/String;", "Iterator", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class UShortArray
  implements Collection<UShort>, KMappedMarker
{
  private final short[] storage;
  
  public static short[] constructor-impl(int paramInt)
  {
    return constructor-impl(new short[paramInt]);
  }
  
  public static short[] constructor-impl(short[] paramArrayOfShort)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfShort, "storage");
    return paramArrayOfShort;
  }
  
  public static boolean contains-xj2QHRw(short[] paramArrayOfShort, short paramShort)
  {
    return ArraysKt.contains(paramArrayOfShort, paramShort);
  }
  
  public static boolean containsAll-impl(short[] paramArrayOfShort, Collection<UShort> paramCollection)
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
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        paramCollection = localIterator.next();
        int i;
        if (((paramCollection instanceof UShort)) && (ArraysKt.contains(paramArrayOfShort, ((UShort)paramCollection).unbox-impl()))) {
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
  
  public static boolean equals-impl(short[] paramArrayOfShort, Object paramObject)
  {
    if (!(paramObject instanceof UShortArray)) {
      return false;
    }
    return Intrinsics.areEqual(paramArrayOfShort, ((UShortArray)paramObject).unbox-impl());
  }
  
  public static final boolean equals-impl0(short[] paramArrayOfShort1, short[] paramArrayOfShort2)
  {
    return Intrinsics.areEqual(paramArrayOfShort1, paramArrayOfShort2);
  }
  
  public static final short get-Mh2AYeg(short[] paramArrayOfShort, int paramInt)
  {
    return UShort.constructor-impl(paramArrayOfShort[paramInt]);
  }
  
  public static int getSize-impl(short[] paramArrayOfShort)
  {
    return paramArrayOfShort.length;
  }
  
  public static int hashCode-impl(short[] paramArrayOfShort)
  {
    return Arrays.hashCode(paramArrayOfShort);
  }
  
  public static boolean isEmpty-impl(short[] paramArrayOfShort)
  {
    boolean bool;
    if (paramArrayOfShort.length == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static Iterator<UShort> iterator-impl(short[] paramArrayOfShort)
  {
    return (Iterator)new Iterator(paramArrayOfShort);
  }
  
  public static final void set-01HTLdE(short[] paramArrayOfShort, int paramInt, short paramShort)
  {
    paramArrayOfShort[paramInt] = paramShort;
  }
  
  public static String toString-impl(short[] paramArrayOfShort)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("UShortArray(storage=");
    paramArrayOfShort = Arrays.toString(paramArrayOfShort);
    Log5ECF72.a(paramArrayOfShort);
    LogE84000.a(paramArrayOfShort);
    Log229316.a(paramArrayOfShort);
    return paramArrayOfShort + ')';
  }
  
  public boolean add-xj2QHRw(short paramShort)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean addAll(Collection<? extends UShort> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public void clear()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean contains-xj2QHRw(short paramShort)
  {
    return contains-xj2QHRw(this.storage, paramShort);
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
  
  public Iterator<UShort> iterator()
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
  
  @Metadata(d1={"\000$\n\002\030\002\n\002\020(\n\002\030\002\n\000\n\002\020\027\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\002\b\004\b\002\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\t\020\b\032\0020\tH\002J\026\020\n\032\0020\002H\002ø\001\000ø\001\001¢\006\004\b\013\020\fR\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\006\032\0020\007X\016¢\006\002\n\000ø\001\001\002\b\n\002\b!\n\002\b\031¨\006\r"}, d2={"Lkotlin/UShortArray$Iterator;", "", "Lkotlin/UShort;", "array", "", "([S)V", "index", "", "hasNext", "", "next", "next-Mh2AYeg", "()S", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Iterator
    implements Iterator<UShort>, KMappedMarker
  {
    private final short[] array;
    private int index;
    
    public Iterator(short[] paramArrayOfShort)
    {
      this.array = paramArrayOfShort;
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
    
    public short next-Mh2AYeg()
    {
      int i = this.index;
      Object localObject = this.array;
      if (i < localObject.length)
      {
        this.index = (i + 1);
        return UShort.constructor-impl(localObject[i]);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UShortArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */