package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000n\n\002\030\002\n\002\020\017\n\000\n\002\020\005\n\002\b\t\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\013\n\002\020\013\n\002\020\000\n\002\b!\n\002\030\002\n\002\b\017\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\005\n\002\020\t\n\002\b\003\n\002\020\n\n\002\b\003\n\002\020\016\n\002\b\016\b@\030\000 t2\b\022\004\022\0020\0000\001:\001tB\024\b\001\022\006\020\002\032\0020\003ø\001\000¢\006\004\b\004\020\005J\033\020\b\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b\n\020\013J\033\020\f\032\0020\r2\006\020\t\032\0020\000H\nø\001\000¢\006\004\b\016\020\017J\033\020\f\032\0020\r2\006\020\t\032\0020\020H\nø\001\000¢\006\004\b\021\020\022J\033\020\f\032\0020\r2\006\020\t\032\0020\023H\nø\001\000¢\006\004\b\024\020\025J\033\020\f\032\0020\r2\006\020\t\032\0020\026H\nø\001\000¢\006\004\b\027\020\030J\026\020\031\032\0020\000H\nø\001\001ø\001\000¢\006\004\b\032\020\005J\033\020\033\032\0020\0202\006\020\t\032\0020\000H\nø\001\000¢\006\004\b\034\020\017J\033\020\033\032\0020\0202\006\020\t\032\0020\020H\nø\001\000¢\006\004\b\035\020\022J\033\020\033\032\0020\0232\006\020\t\032\0020\023H\nø\001\000¢\006\004\b\036\020\037J\033\020\033\032\0020\0202\006\020\t\032\0020\026H\nø\001\000¢\006\004\b \020\030J\032\020!\032\0020\"2\b\020\t\032\004\030\0010#HÖ\003¢\006\004\b$\020%J\033\020&\032\0020\0202\006\020\t\032\0020\000H\bø\001\000¢\006\004\b'\020\017J\033\020&\032\0020\0202\006\020\t\032\0020\020H\bø\001\000¢\006\004\b(\020\022J\033\020&\032\0020\0232\006\020\t\032\0020\023H\bø\001\000¢\006\004\b)\020\037J\033\020&\032\0020\0202\006\020\t\032\0020\026H\bø\001\000¢\006\004\b*\020\030J\020\020+\032\0020\rHÖ\001¢\006\004\b,\020-J\026\020.\032\0020\000H\nø\001\001ø\001\000¢\006\004\b/\020\005J\026\0200\032\0020\000H\bø\001\001ø\001\000¢\006\004\b1\020\005J\033\0202\032\0020\0202\006\020\t\032\0020\000H\nø\001\000¢\006\004\b3\020\017J\033\0202\032\0020\0202\006\020\t\032\0020\020H\nø\001\000¢\006\004\b4\020\022J\033\0202\032\0020\0232\006\020\t\032\0020\023H\nø\001\000¢\006\004\b5\020\037J\033\0202\032\0020\0202\006\020\t\032\0020\026H\nø\001\000¢\006\004\b6\020\030J\033\0207\032\0020\0002\006\020\t\032\0020\000H\bø\001\000¢\006\004\b8\020\013J\033\0207\032\0020\0202\006\020\t\032\0020\020H\bø\001\000¢\006\004\b9\020\022J\033\0207\032\0020\0232\006\020\t\032\0020\023H\bø\001\000¢\006\004\b:\020\037J\033\0207\032\0020\0262\006\020\t\032\0020\026H\bø\001\000¢\006\004\b;\020<J\033\020=\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b>\020\013J\033\020?\032\0020\0202\006\020\t\032\0020\000H\nø\001\000¢\006\004\b@\020\017J\033\020?\032\0020\0202\006\020\t\032\0020\020H\nø\001\000¢\006\004\bA\020\022J\033\020?\032\0020\0232\006\020\t\032\0020\023H\nø\001\000¢\006\004\bB\020\037J\033\020?\032\0020\0202\006\020\t\032\0020\026H\nø\001\000¢\006\004\bC\020\030J\033\020D\032\0020E2\006\020\t\032\0020\000H\nø\001\000¢\006\004\bF\020GJ\033\020H\032\0020\0202\006\020\t\032\0020\000H\nø\001\000¢\006\004\bI\020\017J\033\020H\032\0020\0202\006\020\t\032\0020\020H\nø\001\000¢\006\004\bJ\020\022J\033\020H\032\0020\0232\006\020\t\032\0020\023H\nø\001\000¢\006\004\bK\020\037J\033\020H\032\0020\0202\006\020\t\032\0020\026H\nø\001\000¢\006\004\bL\020\030J\033\020M\032\0020\0202\006\020\t\032\0020\000H\nø\001\000¢\006\004\bN\020\017J\033\020M\032\0020\0202\006\020\t\032\0020\020H\nø\001\000¢\006\004\bO\020\022J\033\020M\032\0020\0232\006\020\t\032\0020\023H\nø\001\000¢\006\004\bP\020\037J\033\020M\032\0020\0202\006\020\t\032\0020\026H\nø\001\000¢\006\004\bQ\020\030J\020\020R\032\0020\003H\b¢\006\004\bS\020\005J\020\020T\032\0020UH\b¢\006\004\bV\020WJ\020\020X\032\0020YH\b¢\006\004\bZ\020[J\020\020\\\032\0020\rH\b¢\006\004\b]\020-J\020\020^\032\0020_H\b¢\006\004\b`\020aJ\020\020b\032\0020cH\b¢\006\004\bd\020eJ\017\020f\032\0020gH\026¢\006\004\bh\020iJ\026\020j\032\0020\000H\bø\001\001ø\001\000¢\006\004\bk\020\005J\026\020l\032\0020\020H\bø\001\001ø\001\000¢\006\004\bm\020-J\026\020n\032\0020\023H\bø\001\001ø\001\000¢\006\004\bo\020aJ\026\020p\032\0020\026H\bø\001\001ø\001\000¢\006\004\bq\020eJ\033\020r\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\bs\020\013R\026\020\002\032\0020\0038\000X\004¢\006\b\n\000\022\004\b\006\020\007\001\002\001\0020\003ø\001\000\002\b\n\002\b\031\n\002\b!¨\006u"}, d2={"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "getData$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-w2LRezQ", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(BLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(B)I", "inc", "inc-w2LRezQ", "inv", "inv-w2LRezQ", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(BS)S", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class UByte
  implements Comparable<UByte>
{
  public static final Companion Companion = new Companion(null);
  public static final byte MAX_VALUE = -1;
  public static final byte MIN_VALUE = 0;
  public static final int SIZE_BITS = 8;
  public static final int SIZE_BYTES = 1;
  private final byte data;
  
  private static final byte and-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return constructor-impl((byte)(paramByte1 & paramByte2));
  }
  
  private int compareTo-7apg3OU(byte paramByte)
  {
    return Intrinsics.compare(unbox-impl() & 0xFF, paramByte & 0xFF);
  }
  
  private static int compareTo-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return Intrinsics.compare(paramByte1 & 0xFF, paramByte2 & 0xFF);
  }
  
  private static final int compareTo-VKZWuLQ(byte paramByte, long paramLong)
  {
    return UnsignedKt.ulongCompare(ULong.constructor-impl(paramByte & 0xFF), paramLong);
  }
  
  private static final int compareTo-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UnsignedKt.uintCompare(UInt.constructor-impl(paramByte & 0xFF), paramInt);
  }
  
  private static final int compareTo-xj2QHRw(byte paramByte, short paramShort)
  {
    return Intrinsics.compare(paramByte & 0xFF, 0xFFFF & paramShort);
  }
  
  public static byte constructor-impl(byte paramByte)
  {
    return paramByte;
  }
  
  private static final byte dec-w2LRezQ(byte paramByte)
  {
    return constructor-impl((byte)(paramByte - 1));
  }
  
  private static final int div-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramByte1 & 0xFF), UInt.constructor-impl(paramByte2 & 0xFF));
  }
  
  private static final long div-VKZWuLQ(byte paramByte, long paramLong)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl(paramByte & 0xFF), paramLong);
  }
  
  private static final int div-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), paramInt);
  }
  
  private static final int div-xj2QHRw(byte paramByte, short paramShort)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), UInt.constructor-impl(0xFFFF & paramShort));
  }
  
  public static boolean equals-impl(byte paramByte, Object paramObject)
  {
    if (!(paramObject instanceof UByte)) {
      return false;
    }
    return paramByte == ((UByte)paramObject).unbox-impl();
  }
  
  public static final boolean equals-impl0(byte paramByte1, byte paramByte2)
  {
    boolean bool;
    if (paramByte1 == paramByte2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final int floorDiv-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramByte1 & 0xFF), UInt.constructor-impl(paramByte2 & 0xFF));
  }
  
  private static final long floorDiv-VKZWuLQ(byte paramByte, long paramLong)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl(paramByte & 0xFF), paramLong);
  }
  
  private static final int floorDiv-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), paramInt);
  }
  
  private static final int floorDiv-xj2QHRw(byte paramByte, short paramShort)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), UInt.constructor-impl(0xFFFF & paramShort));
  }
  
  public static int hashCode-impl(byte paramByte)
  {
    return paramByte;
  }
  
  private static final byte inc-w2LRezQ(byte paramByte)
  {
    return constructor-impl((byte)(paramByte + 1));
  }
  
  private static final byte inv-w2LRezQ(byte paramByte)
  {
    return constructor-impl((byte)(paramByte ^ 0xFFFFFFFF));
  }
  
  private static final int minus-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte1 & 0xFF) - UInt.constructor-impl(paramByte2 & 0xFF));
  }
  
  private static final long minus-VKZWuLQ(byte paramByte, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramByte & 0xFF) - paramLong);
  }
  
  private static final int minus-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte & 0xFF) - paramInt);
  }
  
  private static final int minus-xj2QHRw(byte paramByte, short paramShort)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte & 0xFF) - UInt.constructor-impl(0xFFFF & paramShort));
  }
  
  private static final byte mod-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return constructor-impl((byte)UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramByte1 & 0xFF), UInt.constructor-impl(paramByte2 & 0xFF)));
  }
  
  private static final long mod-VKZWuLQ(byte paramByte, long paramLong)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl(paramByte & 0xFF), paramLong);
  }
  
  private static final int mod-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), paramInt);
  }
  
  private static final short mod-xj2QHRw(byte paramByte, short paramShort)
  {
    return UShort.constructor-impl((short)UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), UInt.constructor-impl(0xFFFF & paramShort)));
  }
  
  private static final byte or-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return constructor-impl((byte)(paramByte1 | paramByte2));
  }
  
  private static final int plus-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte1 & 0xFF) + UInt.constructor-impl(paramByte2 & 0xFF));
  }
  
  private static final long plus-VKZWuLQ(byte paramByte, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramByte & 0xFF) + paramLong);
  }
  
  private static final int plus-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte & 0xFF) + paramInt);
  }
  
  private static final int plus-xj2QHRw(byte paramByte, short paramShort)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte & 0xFF) + UInt.constructor-impl(0xFFFF & paramShort));
  }
  
  private static final UIntRange rangeTo-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return new UIntRange(UInt.constructor-impl(paramByte1 & 0xFF), UInt.constructor-impl(paramByte2 & 0xFF), null);
  }
  
  private static final int rem-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramByte1 & 0xFF), UInt.constructor-impl(paramByte2 & 0xFF));
  }
  
  private static final long rem-VKZWuLQ(byte paramByte, long paramLong)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl(paramByte & 0xFF), paramLong);
  }
  
  private static final int rem-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), paramInt);
  }
  
  private static final int rem-xj2QHRw(byte paramByte, short paramShort)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramByte & 0xFF), UInt.constructor-impl(0xFFFF & paramShort));
  }
  
  private static final int times-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte1 & 0xFF) * UInt.constructor-impl(paramByte2 & 0xFF));
  }
  
  private static final long times-VKZWuLQ(byte paramByte, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramByte & 0xFF) * paramLong);
  }
  
  private static final int times-WZ4Q5Ns(byte paramByte, int paramInt)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte & 0xFF) * paramInt);
  }
  
  private static final int times-xj2QHRw(byte paramByte, short paramShort)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramByte & 0xFF) * UInt.constructor-impl(0xFFFF & paramShort));
  }
  
  private static final byte toByte-impl(byte paramByte)
  {
    return paramByte;
  }
  
  private static final double toDouble-impl(byte paramByte)
  {
    return paramByte & 0xFF;
  }
  
  private static final float toFloat-impl(byte paramByte)
  {
    return paramByte & 0xFF;
  }
  
  private static final int toInt-impl(byte paramByte)
  {
    return paramByte & 0xFF;
  }
  
  private static final long toLong-impl(byte paramByte)
  {
    return paramByte & 0xFF;
  }
  
  private static final short toShort-impl(byte paramByte)
  {
    return (short)((short)paramByte & 0xFF);
  }
  
  public static String toString-impl(byte paramByte)
  {
    String str = String.valueOf(paramByte & 0xFF);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  private static final byte toUByte-w2LRezQ(byte paramByte)
  {
    return paramByte;
  }
  
  private static final int toUInt-pVg5ArA(byte paramByte)
  {
    return UInt.constructor-impl(paramByte & 0xFF);
  }
  
  private static final long toULong-s-VKNKU(byte paramByte)
  {
    return ULong.constructor-impl(paramByte & 0xFF);
  }
  
  private static final short toUShort-Mh2AYeg(byte paramByte)
  {
    return UShort.constructor-impl((short)((short)paramByte & 0xFF));
  }
  
  private static final byte xor-7apg3OU(byte paramByte1, byte paramByte2)
  {
    return constructor-impl((byte)(paramByte1 ^ paramByte2));
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
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\026\020\003\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\026\020\006\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\016\020\007\032\0020\bXT¢\006\002\n\000R\016\020\t\032\0020\bXT¢\006\002\n\000\002\b\n\002\b\031\n\002\b!¨\006\n"}, d2={"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */