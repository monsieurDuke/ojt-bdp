package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000F\n\000\n\002\030\002\n\002\020\034\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\036\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\032\034\020\000\032\0020\001*\b\022\004\022\0020\0030\002H\007ø\001\000¢\006\004\b\004\020\005\032\034\020\000\032\0020\001*\b\022\004\022\0020\0010\002H\007ø\001\000¢\006\004\b\006\020\005\032\034\020\000\032\0020\007*\b\022\004\022\0020\0070\002H\007ø\001\000¢\006\004\b\b\020\t\032\034\020\000\032\0020\001*\b\022\004\022\0020\n0\002H\007ø\001\000¢\006\004\b\013\020\005\032\032\020\f\032\0020\r*\b\022\004\022\0020\0030\016H\007ø\001\000¢\006\002\020\017\032\032\020\020\032\0020\021*\b\022\004\022\0020\0010\016H\007ø\001\000¢\006\002\020\022\032\032\020\023\032\0020\024*\b\022\004\022\0020\0070\016H\007ø\001\000¢\006\002\020\025\032\032\020\026\032\0020\027*\b\022\004\022\0020\n0\016H\007ø\001\000¢\006\002\020\030\002\004\n\002\b\031¨\006\031"}, d2={"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/UCollectionsKt")
class UCollectionsKt___UCollectionsKt
{
  public static final int sumOfUByte(Iterable<UByte> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    int i = 0;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      i = UInt.constructor-impl(UInt.constructor-impl(((UByte)paramIterable.next()).unbox-impl() & 0xFF) + i);
    }
    return i;
  }
  
  public static final int sumOfUInt(Iterable<UInt> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    int i = 0;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      i = UInt.constructor-impl(i + ((UInt)paramIterable.next()).unbox-impl());
    }
    return i;
  }
  
  public static final long sumOfULong(Iterable<ULong> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    long l = 0L;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      l = ULong.constructor-impl(l + ((ULong)paramIterable.next()).unbox-impl());
    }
    return l;
  }
  
  public static final int sumOfUShort(Iterable<UShort> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    int i = 0;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      i = UInt.constructor-impl(UInt.constructor-impl(0xFFFF & ((UShort)paramIterable.next()).unbox-impl()) + i);
    }
    return i;
  }
  
  public static final byte[] toUByteArray(Collection<UByte> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    byte[] arrayOfByte = UByteArray.constructor-impl(paramCollection.size());
    int i = 0;
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      UByteArray.set-VurrAj0(arrayOfByte, i, ((UByte)paramCollection.next()).unbox-impl());
      i++;
    }
    return arrayOfByte;
  }
  
  public static final int[] toUIntArray(Collection<UInt> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    int[] arrayOfInt = UIntArray.constructor-impl(paramCollection.size());
    int i = 0;
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      UIntArray.set-VXSXFK8(arrayOfInt, i, ((UInt)paramCollection.next()).unbox-impl());
      i++;
    }
    return arrayOfInt;
  }
  
  public static final long[] toULongArray(Collection<ULong> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    long[] arrayOfLong = ULongArray.constructor-impl(paramCollection.size());
    int i = 0;
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      ULongArray.set-k8EXiF4(arrayOfLong, i, ((ULong)paramCollection.next()).unbox-impl());
      i++;
    }
    return arrayOfLong;
  }
  
  public static final short[] toUShortArray(Collection<UShort> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    short[] arrayOfShort = UShortArray.constructor-impl(paramCollection.size());
    int i = 0;
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      UShortArray.set-01HTLdE(arrayOfShort, i, ((UShort)paramCollection.next()).unbox-impl());
      i++;
    }
    return arrayOfShort;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/UCollectionsKt___UCollectionsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */