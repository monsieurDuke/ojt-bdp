package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\"\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\032\034\020\000\032\0020\001*\b\022\004\022\0020\0030\002H\007ø\001\000¢\006\004\b\004\020\005\032\034\020\000\032\0020\001*\b\022\004\022\0020\0010\002H\007ø\001\000¢\006\004\b\006\020\005\032\034\020\000\032\0020\007*\b\022\004\022\0020\0070\002H\007ø\001\000¢\006\004\b\b\020\t\032\034\020\000\032\0020\001*\b\022\004\022\0020\n0\002H\007ø\001\000¢\006\004\b\013\020\005\002\004\n\002\b\031¨\006\f"}, d2={"sum", "Lkotlin/UInt;", "Lkotlin/sequences/Sequence;", "Lkotlin/UByte;", "sumOfUByte", "(Lkotlin/sequences/Sequence;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Lkotlin/sequences/Sequence;)J", "Lkotlin/UShort;", "sumOfUShort", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/sequences/USequencesKt")
class USequencesKt___USequencesKt
{
  public static final int sumOfUByte(Sequence<UByte> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    int i = 0;
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      i = UInt.constructor-impl(UInt.constructor-impl(((UByte)paramSequence.next()).unbox-impl() & 0xFF) + i);
    }
    return i;
  }
  
  public static final int sumOfUInt(Sequence<UInt> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    int i = 0;
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      i = UInt.constructor-impl(i + ((UInt)paramSequence.next()).unbox-impl());
    }
    return i;
  }
  
  public static final long sumOfULong(Sequence<ULong> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    long l = 0L;
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      l = ULong.constructor-impl(l + ((ULong)paramSequence.next()).unbox-impl());
    }
    return l;
  }
  
  public static final int sumOfUShort(Sequence<UShort> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    int i = 0;
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      i = UInt.constructor-impl(UInt.constructor-impl(0xFFFF & ((UShort)paramSequence.next()).unbox-impl()) + i);
    }
    return i;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/USequencesKt___USequencesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */