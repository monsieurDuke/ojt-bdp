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

@Metadata(d1={"\000F\n\002\030\002\n\002\020\036\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\025\n\002\b\006\n\002\020\013\n\002\b\t\n\002\020\000\n\002\b\f\n\002\020(\n\002\b\003\n\002\020\002\n\002\b\004\n\002\020\016\n\002\b\004\b@\030\0002\b\022\004\022\0020\0020\001:\0012B\024\b\026\022\006\020\003\032\0020\004ø\001\000¢\006\004\b\005\020\006B\024\b\001\022\006\020\007\032\0020\bø\001\000¢\006\004\b\005\020\tJ\033\020\016\032\0020\0172\006\020\020\032\0020\002H\002ø\001\000¢\006\004\b\021\020\022J \020\023\032\0020\0172\f\020\024\032\b\022\004\022\0020\0020\001H\026ø\001\000¢\006\004\b\025\020\026J\032\020\027\032\0020\0172\b\020\030\032\004\030\0010\031HÖ\003¢\006\004\b\032\020\033J\036\020\034\032\0020\0022\006\020\035\032\0020\004H\002ø\001\001ø\001\000¢\006\004\b\036\020\037J\020\020 \032\0020\004HÖ\001¢\006\004\b!\020\013J\017\020\"\032\0020\017H\026¢\006\004\b#\020$J\031\020%\032\b\022\004\022\0020\0020&H\002ø\001\000¢\006\004\b'\020(J#\020)\032\0020*2\006\020\035\032\0020\0042\006\020+\032\0020\002H\002ø\001\000¢\006\004\b,\020-J\020\020.\032\0020/HÖ\001¢\006\004\b0\0201R\024\020\003\032\0020\0048VX\004¢\006\006\032\004\b\n\020\013R\026\020\007\032\0020\b8\000X\004¢\006\b\n\000\022\004\b\f\020\r\001\007\001\0020\bø\001\000\002\b\n\002\b\031\n\002\b!¨\0063"}, d2={"Lkotlin/UIntArray;", "", "Lkotlin/UInt;", "size", "", "constructor-impl", "(I)[I", "storage", "", "([I)[I", "getSize-impl", "([I)I", "getStorage$annotations", "()V", "contains", "", "element", "contains-WZ4Q5Ns", "([II)Z", "containsAll", "elements", "containsAll-impl", "([ILjava/util/Collection;)Z", "equals", "other", "", "equals-impl", "([ILjava/lang/Object;)Z", "get", "index", "get-pVg5ArA", "([II)I", "hashCode", "hashCode-impl", "isEmpty", "isEmpty-impl", "([I)Z", "iterator", "", "iterator-impl", "([I)Ljava/util/Iterator;", "set", "", "value", "set-VXSXFK8", "([III)V", "toString", "", "toString-impl", "([I)Ljava/lang/String;", "Iterator", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class UIntArray
  implements Collection<UInt>, KMappedMarker
{
  private final int[] storage;
  
  public static int[] constructor-impl(int paramInt)
  {
    return constructor-impl(new int[paramInt]);
  }
  
  public static int[] constructor-impl(int[] paramArrayOfInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "storage");
    return paramArrayOfInt;
  }
  
  public static boolean contains-WZ4Q5Ns(int[] paramArrayOfInt, int paramInt)
  {
    return ArraysKt.contains(paramArrayOfInt, paramInt);
  }
  
  public static boolean containsAll-impl(int[] paramArrayOfInt, Collection<UInt> paramCollection)
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
        if (((localObject instanceof UInt)) && (ArraysKt.contains(paramArrayOfInt, ((UInt)localObject).unbox-impl()))) {
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
  
  public static boolean equals-impl(int[] paramArrayOfInt, Object paramObject)
  {
    if (!(paramObject instanceof UIntArray)) {
      return false;
    }
    return Intrinsics.areEqual(paramArrayOfInt, ((UIntArray)paramObject).unbox-impl());
  }
  
  public static final boolean equals-impl0(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return Intrinsics.areEqual(paramArrayOfInt1, paramArrayOfInt2);
  }
  
  public static final int get-pVg5ArA(int[] paramArrayOfInt, int paramInt)
  {
    return UInt.constructor-impl(paramArrayOfInt[paramInt]);
  }
  
  public static int getSize-impl(int[] paramArrayOfInt)
  {
    return paramArrayOfInt.length;
  }
  
  public static int hashCode-impl(int[] paramArrayOfInt)
  {
    return Arrays.hashCode(paramArrayOfInt);
  }
  
  public static boolean isEmpty-impl(int[] paramArrayOfInt)
  {
    boolean bool;
    if (paramArrayOfInt.length == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static Iterator<UInt> iterator-impl(int[] paramArrayOfInt)
  {
    return (Iterator)new Iterator(paramArrayOfInt);
  }
  
  public static final void set-VXSXFK8(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    paramArrayOfInt[paramInt1] = paramInt2;
  }
  
  public static String toString-impl(int[] paramArrayOfInt)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("UIntArray(storage=");
    paramArrayOfInt = Arrays.toString(paramArrayOfInt);
    Log5ECF72.a(paramArrayOfInt);
    LogE84000.a(paramArrayOfInt);
    Log229316.a(paramArrayOfInt);
    return paramArrayOfInt + ')';
  }
  
  public boolean add-WZ4Q5Ns(int paramInt)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean addAll(Collection<? extends UInt> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public void clear()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean contains-WZ4Q5Ns(int paramInt)
  {
    return contains-WZ4Q5Ns(this.storage, paramInt);
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
  
  public Iterator<UInt> iterator()
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
  
  @Metadata(d1={"\000$\n\002\030\002\n\002\020(\n\002\030\002\n\000\n\002\020\025\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\002\b\004\b\002\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\t\020\b\032\0020\tH\002J\026\020\n\032\0020\002H\002ø\001\000ø\001\001¢\006\004\b\013\020\fR\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\006\032\0020\007X\016¢\006\002\n\000ø\001\001\002\b\n\002\b!\n\002\b\031¨\006\r"}, d2={"Lkotlin/UIntArray$Iterator;", "", "Lkotlin/UInt;", "array", "", "([I)V", "index", "", "hasNext", "", "next", "next-pVg5ArA", "()I", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Iterator
    implements Iterator<UInt>, KMappedMarker
  {
    private final int[] array;
    private int index;
    
    public Iterator(int[] paramArrayOfInt)
    {
      this.array = paramArrayOfInt;
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
    
    public int next-pVg5ArA()
    {
      int i = this.index;
      Object localObject = this.array;
      if (i < localObject.length)
      {
        this.index = (i + 1);
        return UInt.constructor-impl(localObject[i]);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UIntArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */