package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.ranges.UIntRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000n\n\002\030\002\n\002\020\017\n\000\n\002\020\b\n\002\b\t\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\013\n\002\020\013\n\002\020\000\n\002\b!\n\002\030\002\n\002\b\022\n\002\020\005\n\002\b\003\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\005\n\002\020\t\n\002\b\003\n\002\020\n\n\002\b\003\n\002\020\016\n\002\b\016\b@\030\000 y2\b\022\004\022\0020\0000\001:\001yB\024\b\001\022\006\020\002\032\0020\003ø\001\000¢\006\004\b\004\020\005J\033\020\b\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b\n\020\013J\033\020\f\032\0020\0032\006\020\t\032\0020\rH\nø\001\000¢\006\004\b\016\020\017J\033\020\f\032\0020\0032\006\020\t\032\0020\000H\nø\001\000¢\006\004\b\020\020\013J\033\020\f\032\0020\0032\006\020\t\032\0020\021H\nø\001\000¢\006\004\b\022\020\023J\033\020\f\032\0020\0032\006\020\t\032\0020\024H\nø\001\000¢\006\004\b\025\020\026J\026\020\027\032\0020\000H\nø\001\001ø\001\000¢\006\004\b\030\020\005J\033\020\031\032\0020\0002\006\020\t\032\0020\rH\nø\001\000¢\006\004\b\032\020\017J\033\020\031\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\b\033\020\013J\033\020\031\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\b\034\020\035J\033\020\031\032\0020\0002\006\020\t\032\0020\024H\nø\001\000¢\006\004\b\036\020\026J\032\020\037\032\0020 2\b\020\t\032\004\030\0010!HÖ\003¢\006\004\b\"\020#J\033\020$\032\0020\0002\006\020\t\032\0020\rH\bø\001\000¢\006\004\b%\020\017J\033\020$\032\0020\0002\006\020\t\032\0020\000H\bø\001\000¢\006\004\b&\020\013J\033\020$\032\0020\0212\006\020\t\032\0020\021H\bø\001\000¢\006\004\b'\020\035J\033\020$\032\0020\0002\006\020\t\032\0020\024H\bø\001\000¢\006\004\b(\020\026J\020\020)\032\0020\003HÖ\001¢\006\004\b*\020\005J\026\020+\032\0020\000H\nø\001\001ø\001\000¢\006\004\b,\020\005J\026\020-\032\0020\000H\bø\001\001ø\001\000¢\006\004\b.\020\005J\033\020/\032\0020\0002\006\020\t\032\0020\rH\nø\001\000¢\006\004\b0\020\017J\033\020/\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\b1\020\013J\033\020/\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\b2\020\035J\033\020/\032\0020\0002\006\020\t\032\0020\024H\nø\001\000¢\006\004\b3\020\026J\033\0204\032\0020\r2\006\020\t\032\0020\rH\bø\001\000¢\006\004\b5\0206J\033\0204\032\0020\0002\006\020\t\032\0020\000H\bø\001\000¢\006\004\b7\020\013J\033\0204\032\0020\0212\006\020\t\032\0020\021H\bø\001\000¢\006\004\b8\020\035J\033\0204\032\0020\0242\006\020\t\032\0020\024H\bø\001\000¢\006\004\b9\020:J\033\020;\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b<\020\013J\033\020=\032\0020\0002\006\020\t\032\0020\rH\nø\001\000¢\006\004\b>\020\017J\033\020=\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\b?\020\013J\033\020=\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\b@\020\035J\033\020=\032\0020\0002\006\020\t\032\0020\024H\nø\001\000¢\006\004\bA\020\026J\033\020B\032\0020C2\006\020\t\032\0020\000H\nø\001\000¢\006\004\bD\020EJ\033\020F\032\0020\0002\006\020\t\032\0020\rH\nø\001\000¢\006\004\bG\020\017J\033\020F\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\bH\020\013J\033\020F\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\bI\020\035J\033\020F\032\0020\0002\006\020\t\032\0020\024H\nø\001\000¢\006\004\bJ\020\026J\036\020K\032\0020\0002\006\020L\032\0020\003H\fø\001\001ø\001\000¢\006\004\bM\020\013J\036\020N\032\0020\0002\006\020L\032\0020\003H\fø\001\001ø\001\000¢\006\004\bO\020\013J\033\020P\032\0020\0002\006\020\t\032\0020\rH\nø\001\000¢\006\004\bQ\020\017J\033\020P\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\bR\020\013J\033\020P\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\bS\020\035J\033\020P\032\0020\0002\006\020\t\032\0020\024H\nø\001\000¢\006\004\bT\020\026J\020\020U\032\0020VH\b¢\006\004\bW\020XJ\020\020Y\032\0020ZH\b¢\006\004\b[\020\\J\020\020]\032\0020^H\b¢\006\004\b_\020`J\020\020a\032\0020\003H\b¢\006\004\bb\020\005J\020\020c\032\0020dH\b¢\006\004\be\020fJ\020\020g\032\0020hH\b¢\006\004\bi\020jJ\017\020k\032\0020lH\026¢\006\004\bm\020nJ\026\020o\032\0020\rH\bø\001\001ø\001\000¢\006\004\bp\020XJ\026\020q\032\0020\000H\bø\001\001ø\001\000¢\006\004\br\020\005J\026\020s\032\0020\021H\bø\001\001ø\001\000¢\006\004\bt\020fJ\026\020u\032\0020\024H\bø\001\001ø\001\000¢\006\004\bv\020jJ\033\020w\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\bx\020\013R\026\020\002\032\0020\0038\000X\004¢\006\b\n\000\022\004\b\006\020\007\001\002\001\0020\003ø\001\000\002\b\n\002\b\031\n\002\b!¨\006z"}, d2={"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "getData$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-pVg5ArA", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(ILjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "inc", "inc-pVg5ArA", "inv", "inv-pVg5ArA", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(IB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(IS)S", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-pVg5ArA", "shr", "shr-pVg5ArA", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class UInt
  implements Comparable<UInt>
{
  public static final Companion Companion = new Companion(null);
  public static final int MAX_VALUE = -1;
  public static final int MIN_VALUE = 0;
  public static final int SIZE_BITS = 32;
  public static final int SIZE_BYTES = 4;
  private final int data;
  
  private static final int and-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 & paramInt2);
  }
  
  private static final int compareTo-7apg3OU(int paramInt, byte paramByte)
  {
    return UnsignedKt.uintCompare(paramInt, constructor-impl(paramByte & 0xFF));
  }
  
  private static final int compareTo-VKZWuLQ(int paramInt, long paramLong)
  {
    return UnsignedKt.ulongCompare(ULong.constructor-impl(paramInt & 0xFFFFFFFF), paramLong);
  }
  
  private int compareTo-WZ4Q5Ns(int paramInt)
  {
    return UnsignedKt.uintCompare(unbox-impl(), paramInt);
  }
  
  private static int compareTo-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return UnsignedKt.uintCompare(paramInt1, paramInt2);
  }
  
  private static final int compareTo-xj2QHRw(int paramInt, short paramShort)
  {
    return UnsignedKt.uintCompare(paramInt, constructor-impl(0xFFFF & paramShort));
  }
  
  public static int constructor-impl(int paramInt)
  {
    return paramInt;
  }
  
  private static final int dec-pVg5ArA(int paramInt)
  {
    return constructor-impl(paramInt - 1);
  }
  
  private static final int div-7apg3OU(int paramInt, byte paramByte)
  {
    return UnsignedKt.uintDivide-J1ME1BU(paramInt, constructor-impl(paramByte & 0xFF));
  }
  
  private static final long div-VKZWuLQ(int paramInt, long paramLong)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl(paramInt & 0xFFFFFFFF), paramLong);
  }
  
  private static final int div-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return UnsignedKt.uintDivide-J1ME1BU(paramInt1, paramInt2);
  }
  
  private static final int div-xj2QHRw(int paramInt, short paramShort)
  {
    return UnsignedKt.uintDivide-J1ME1BU(paramInt, constructor-impl(0xFFFF & paramShort));
  }
  
  public static boolean equals-impl(int paramInt, Object paramObject)
  {
    if (!(paramObject instanceof UInt)) {
      return false;
    }
    return paramInt == ((UInt)paramObject).unbox-impl();
  }
  
  public static final boolean equals-impl0(int paramInt1, int paramInt2)
  {
    boolean bool;
    if (paramInt1 == paramInt2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final int floorDiv-7apg3OU(int paramInt, byte paramByte)
  {
    return UnsignedKt.uintDivide-J1ME1BU(paramInt, constructor-impl(paramByte & 0xFF));
  }
  
  private static final long floorDiv-VKZWuLQ(int paramInt, long paramLong)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl(paramInt & 0xFFFFFFFF), paramLong);
  }
  
  private static final int floorDiv-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return UnsignedKt.uintDivide-J1ME1BU(paramInt1, paramInt2);
  }
  
  private static final int floorDiv-xj2QHRw(int paramInt, short paramShort)
  {
    return UnsignedKt.uintDivide-J1ME1BU(paramInt, constructor-impl(0xFFFF & paramShort));
  }
  
  public static int hashCode-impl(int paramInt)
  {
    return paramInt;
  }
  
  private static final int inc-pVg5ArA(int paramInt)
  {
    return constructor-impl(paramInt + 1);
  }
  
  private static final int inv-pVg5ArA(int paramInt)
  {
    return constructor-impl(paramInt ^ 0xFFFFFFFF);
  }
  
  private static final int minus-7apg3OU(int paramInt, byte paramByte)
  {
    return constructor-impl(paramInt - constructor-impl(paramByte & 0xFF));
  }
  
  private static final long minus-VKZWuLQ(int paramInt, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramInt & 0xFFFFFFFF) - paramLong);
  }
  
  private static final int minus-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 - paramInt2);
  }
  
  private static final int minus-xj2QHRw(int paramInt, short paramShort)
  {
    return constructor-impl(paramInt - constructor-impl(0xFFFF & paramShort));
  }
  
  private static final byte mod-7apg3OU(int paramInt, byte paramByte)
  {
    return UByte.constructor-impl((byte)UnsignedKt.uintRemainder-J1ME1BU(paramInt, constructor-impl(paramByte & 0xFF)));
  }
  
  private static final long mod-VKZWuLQ(int paramInt, long paramLong)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl(paramInt & 0xFFFFFFFF), paramLong);
  }
  
  private static final int mod-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(paramInt1, paramInt2);
  }
  
  private static final short mod-xj2QHRw(int paramInt, short paramShort)
  {
    return UShort.constructor-impl((short)UnsignedKt.uintRemainder-J1ME1BU(paramInt, constructor-impl(0xFFFF & paramShort)));
  }
  
  private static final int or-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 | paramInt2);
  }
  
  private static final int plus-7apg3OU(int paramInt, byte paramByte)
  {
    return constructor-impl(constructor-impl(paramByte & 0xFF) + paramInt);
  }
  
  private static final long plus-VKZWuLQ(int paramInt, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramInt & 0xFFFFFFFF) + paramLong);
  }
  
  private static final int plus-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 + paramInt2);
  }
  
  private static final int plus-xj2QHRw(int paramInt, short paramShort)
  {
    return constructor-impl(constructor-impl(0xFFFF & paramShort) + paramInt);
  }
  
  private static final UIntRange rangeTo-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return new UIntRange(paramInt1, paramInt2, null);
  }
  
  private static final int rem-7apg3OU(int paramInt, byte paramByte)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(paramInt, constructor-impl(paramByte & 0xFF));
  }
  
  private static final long rem-VKZWuLQ(int paramInt, long paramLong)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl(paramInt & 0xFFFFFFFF), paramLong);
  }
  
  private static final int rem-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(paramInt1, paramInt2);
  }
  
  private static final int rem-xj2QHRw(int paramInt, short paramShort)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(paramInt, constructor-impl(0xFFFF & paramShort));
  }
  
  private static final int shl-pVg5ArA(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 << paramInt2);
  }
  
  private static final int shr-pVg5ArA(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 >>> paramInt2);
  }
  
  private static final int times-7apg3OU(int paramInt, byte paramByte)
  {
    return constructor-impl(constructor-impl(paramByte & 0xFF) * paramInt);
  }
  
  private static final long times-VKZWuLQ(int paramInt, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramInt & 0xFFFFFFFF) * paramLong);
  }
  
  private static final int times-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 * paramInt2);
  }
  
  private static final int times-xj2QHRw(int paramInt, short paramShort)
  {
    return constructor-impl(constructor-impl(0xFFFF & paramShort) * paramInt);
  }
  
  private static final byte toByte-impl(int paramInt)
  {
    return (byte)paramInt;
  }
  
  private static final double toDouble-impl(int paramInt)
  {
    return UnsignedKt.uintToDouble(paramInt);
  }
  
  private static final float toFloat-impl(int paramInt)
  {
    return (float)UnsignedKt.uintToDouble(paramInt);
  }
  
  private static final int toInt-impl(int paramInt)
  {
    return paramInt;
  }
  
  private static final long toLong-impl(int paramInt)
  {
    return paramInt & 0xFFFFFFFF;
  }
  
  private static final short toShort-impl(int paramInt)
  {
    return (short)paramInt;
  }
  
  public static String toString-impl(int paramInt)
  {
    String str = String.valueOf(paramInt & 0xFFFFFFFF);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  private static final byte toUByte-w2LRezQ(int paramInt)
  {
    return UByte.constructor-impl((byte)paramInt);
  }
  
  private static final int toUInt-pVg5ArA(int paramInt)
  {
    return paramInt;
  }
  
  private static final long toULong-s-VKNKU(int paramInt)
  {
    return ULong.constructor-impl(paramInt & 0xFFFFFFFF);
  }
  
  private static final short toUShort-Mh2AYeg(int paramInt)
  {
    return UShort.constructor-impl((short)paramInt);
  }
  
  private static final int xor-WZ4Q5Ns(int paramInt1, int paramInt2)
  {
    return constructor-impl(paramInt1 ^ paramInt2);
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.data, paramObject);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.data);
  }
  
  public String toString()
  {
    String str = toString-impl(this.data);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\026\020\003\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\026\020\006\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\016\020\007\032\0020\bXT¢\006\002\n\000R\016\020\t\032\0020\bXT¢\006\002\n\000\002\b\n\002\b\031\n\002\b!¨\006\n"}, d2={"Lkotlin/UInt$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UInt;", "I", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */