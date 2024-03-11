package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000j\n\002\030\002\n\002\020\017\n\000\n\002\020\n\n\002\b\t\n\002\020\b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\r\n\002\020\013\n\002\020\000\n\002\b!\n\002\030\002\n\002\b\r\n\002\020\005\n\002\b\003\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\005\n\002\020\t\n\002\b\005\n\002\020\016\n\002\b\016\b@\030\000 t2\b\022\004\022\0020\0000\001:\001tB\024\b\001\022\006\020\002\032\0020\003ø\001\000¢\006\004\b\004\020\005J\033\020\b\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b\n\020\013J\033\020\f\032\0020\r2\006\020\t\032\0020\016H\nø\001\000¢\006\004\b\017\020\020J\033\020\f\032\0020\r2\006\020\t\032\0020\021H\nø\001\000¢\006\004\b\022\020\023J\033\020\f\032\0020\r2\006\020\t\032\0020\024H\nø\001\000¢\006\004\b\025\020\026J\033\020\f\032\0020\r2\006\020\t\032\0020\000H\nø\001\000¢\006\004\b\027\020\030J\026\020\031\032\0020\000H\nø\001\001ø\001\000¢\006\004\b\032\020\005J\033\020\033\032\0020\0212\006\020\t\032\0020\016H\nø\001\000¢\006\004\b\034\020\020J\033\020\033\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\b\035\020\023J\033\020\033\032\0020\0242\006\020\t\032\0020\024H\nø\001\000¢\006\004\b\036\020\037J\033\020\033\032\0020\0212\006\020\t\032\0020\000H\nø\001\000¢\006\004\b \020\030J\032\020!\032\0020\"2\b\020\t\032\004\030\0010#HÖ\003¢\006\004\b$\020%J\033\020&\032\0020\0212\006\020\t\032\0020\016H\bø\001\000¢\006\004\b'\020\020J\033\020&\032\0020\0212\006\020\t\032\0020\021H\bø\001\000¢\006\004\b(\020\023J\033\020&\032\0020\0242\006\020\t\032\0020\024H\bø\001\000¢\006\004\b)\020\037J\033\020&\032\0020\0212\006\020\t\032\0020\000H\bø\001\000¢\006\004\b*\020\030J\020\020+\032\0020\rHÖ\001¢\006\004\b,\020-J\026\020.\032\0020\000H\nø\001\001ø\001\000¢\006\004\b/\020\005J\026\0200\032\0020\000H\bø\001\001ø\001\000¢\006\004\b1\020\005J\033\0202\032\0020\0212\006\020\t\032\0020\016H\nø\001\000¢\006\004\b3\020\020J\033\0202\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\b4\020\023J\033\0202\032\0020\0242\006\020\t\032\0020\024H\nø\001\000¢\006\004\b5\020\037J\033\0202\032\0020\0212\006\020\t\032\0020\000H\nø\001\000¢\006\004\b6\020\030J\033\0207\032\0020\0162\006\020\t\032\0020\016H\bø\001\000¢\006\004\b8\0209J\033\0207\032\0020\0212\006\020\t\032\0020\021H\bø\001\000¢\006\004\b:\020\023J\033\0207\032\0020\0242\006\020\t\032\0020\024H\bø\001\000¢\006\004\b;\020\037J\033\0207\032\0020\0002\006\020\t\032\0020\000H\bø\001\000¢\006\004\b<\020\013J\033\020=\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b>\020\013J\033\020?\032\0020\0212\006\020\t\032\0020\016H\nø\001\000¢\006\004\b@\020\020J\033\020?\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\bA\020\023J\033\020?\032\0020\0242\006\020\t\032\0020\024H\nø\001\000¢\006\004\bB\020\037J\033\020?\032\0020\0212\006\020\t\032\0020\000H\nø\001\000¢\006\004\bC\020\030J\033\020D\032\0020E2\006\020\t\032\0020\000H\nø\001\000¢\006\004\bF\020GJ\033\020H\032\0020\0212\006\020\t\032\0020\016H\nø\001\000¢\006\004\bI\020\020J\033\020H\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\bJ\020\023J\033\020H\032\0020\0242\006\020\t\032\0020\024H\nø\001\000¢\006\004\bK\020\037J\033\020H\032\0020\0212\006\020\t\032\0020\000H\nø\001\000¢\006\004\bL\020\030J\033\020M\032\0020\0212\006\020\t\032\0020\016H\nø\001\000¢\006\004\bN\020\020J\033\020M\032\0020\0212\006\020\t\032\0020\021H\nø\001\000¢\006\004\bO\020\023J\033\020M\032\0020\0242\006\020\t\032\0020\024H\nø\001\000¢\006\004\bP\020\037J\033\020M\032\0020\0212\006\020\t\032\0020\000H\nø\001\000¢\006\004\bQ\020\030J\020\020R\032\0020SH\b¢\006\004\bT\020UJ\020\020V\032\0020WH\b¢\006\004\bX\020YJ\020\020Z\032\0020[H\b¢\006\004\b\\\020]J\020\020^\032\0020\rH\b¢\006\004\b_\020-J\020\020`\032\0020aH\b¢\006\004\bb\020cJ\020\020d\032\0020\003H\b¢\006\004\be\020\005J\017\020f\032\0020gH\026¢\006\004\bh\020iJ\026\020j\032\0020\016H\bø\001\001ø\001\000¢\006\004\bk\020UJ\026\020l\032\0020\021H\bø\001\001ø\001\000¢\006\004\bm\020-J\026\020n\032\0020\024H\bø\001\001ø\001\000¢\006\004\bo\020cJ\026\020p\032\0020\000H\bø\001\001ø\001\000¢\006\004\bq\020\005J\033\020r\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\bs\020\013R\026\020\002\032\0020\0038\000X\004¢\006\b\n\000\022\004\b\006\020\007\001\002\001\0020\003ø\001\000\002\b\n\002\b\031\n\002\b!¨\006u"}, d2={"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "getData$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-Mh2AYeg", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(SLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(S)I", "inc", "inc-Mh2AYeg", "inv", "inv-Mh2AYeg", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(SB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class UShort
  implements Comparable<UShort>
{
  public static final Companion Companion = new Companion(null);
  public static final short MAX_VALUE = -1;
  public static final short MIN_VALUE = 0;
  public static final int SIZE_BITS = 16;
  public static final int SIZE_BYTES = 2;
  private final short data;
  
  private static final short and-xj2QHRw(short paramShort1, short paramShort2)
  {
    return constructor-impl((short)(paramShort1 & paramShort2));
  }
  
  private static final int compareTo-7apg3OU(short paramShort, byte paramByte)
  {
    return Intrinsics.compare(0xFFFF & paramShort, paramByte & 0xFF);
  }
  
  private static final int compareTo-VKZWuLQ(short paramShort, long paramLong)
  {
    return UnsignedKt.ulongCompare(ULong.constructor-impl(paramShort & 0xFFFF), paramLong);
  }
  
  private static final int compareTo-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UnsignedKt.uintCompare(UInt.constructor-impl(0xFFFF & paramShort), paramInt);
  }
  
  private int compareTo-xj2QHRw(short paramShort)
  {
    return Intrinsics.compare(unbox-impl() & 0xFFFF, 0xFFFF & paramShort);
  }
  
  private static int compareTo-xj2QHRw(short paramShort1, short paramShort2)
  {
    return Intrinsics.compare(paramShort1 & 0xFFFF, 0xFFFF & paramShort2);
  }
  
  public static short constructor-impl(short paramShort)
  {
    return paramShort;
  }
  
  private static final short dec-Mh2AYeg(short paramShort)
  {
    return constructor-impl((short)(paramShort - 1));
  }
  
  private static final int div-7apg3OU(short paramShort, byte paramByte)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), UInt.constructor-impl(paramByte & 0xFF));
  }
  
  private static final long div-VKZWuLQ(short paramShort, long paramLong)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl(paramShort & 0xFFFF), paramLong);
  }
  
  private static final int div-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), paramInt);
  }
  
  private static final int div-xj2QHRw(short paramShort1, short paramShort2)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramShort1 & 0xFFFF), UInt.constructor-impl(0xFFFF & paramShort2));
  }
  
  public static boolean equals-impl(short paramShort, Object paramObject)
  {
    if (!(paramObject instanceof UShort)) {
      return false;
    }
    return paramShort == ((UShort)paramObject).unbox-impl();
  }
  
  public static final boolean equals-impl0(short paramShort1, short paramShort2)
  {
    boolean bool;
    if (paramShort1 == paramShort2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final int floorDiv-7apg3OU(short paramShort, byte paramByte)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), UInt.constructor-impl(paramByte & 0xFF));
  }
  
  private static final long floorDiv-VKZWuLQ(short paramShort, long paramLong)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl(paramShort & 0xFFFF), paramLong);
  }
  
  private static final int floorDiv-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), paramInt);
  }
  
  private static final int floorDiv-xj2QHRw(short paramShort1, short paramShort2)
  {
    return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(paramShort1 & 0xFFFF), UInt.constructor-impl(0xFFFF & paramShort2));
  }
  
  public static int hashCode-impl(short paramShort)
  {
    return paramShort;
  }
  
  private static final short inc-Mh2AYeg(short paramShort)
  {
    return constructor-impl((short)(paramShort + 1));
  }
  
  private static final short inv-Mh2AYeg(short paramShort)
  {
    return constructor-impl((short)(paramShort ^ 0xFFFFFFFF));
  }
  
  private static final int minus-7apg3OU(short paramShort, byte paramByte)
  {
    return UInt.constructor-impl(UInt.constructor-impl(0xFFFF & paramShort) - UInt.constructor-impl(paramByte & 0xFF));
  }
  
  private static final long minus-VKZWuLQ(short paramShort, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramShort & 0xFFFF) - paramLong);
  }
  
  private static final int minus-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UInt.constructor-impl(UInt.constructor-impl(0xFFFF & paramShort) - paramInt);
  }
  
  private static final int minus-xj2QHRw(short paramShort1, short paramShort2)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramShort1 & 0xFFFF) - UInt.constructor-impl(0xFFFF & paramShort2));
  }
  
  private static final byte mod-7apg3OU(short paramShort, byte paramByte)
  {
    return UByte.constructor-impl((byte)UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), UInt.constructor-impl(paramByte & 0xFF)));
  }
  
  private static final long mod-VKZWuLQ(short paramShort, long paramLong)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl(paramShort & 0xFFFF), paramLong);
  }
  
  private static final int mod-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), paramInt);
  }
  
  private static final short mod-xj2QHRw(short paramShort1, short paramShort2)
  {
    return constructor-impl((short)UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramShort1 & 0xFFFF), UInt.constructor-impl(0xFFFF & paramShort2)));
  }
  
  private static final short or-xj2QHRw(short paramShort1, short paramShort2)
  {
    return constructor-impl((short)(paramShort1 | paramShort2));
  }
  
  private static final int plus-7apg3OU(short paramShort, byte paramByte)
  {
    return UInt.constructor-impl(UInt.constructor-impl(0xFFFF & paramShort) + UInt.constructor-impl(paramByte & 0xFF));
  }
  
  private static final long plus-VKZWuLQ(short paramShort, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramShort & 0xFFFF) + paramLong);
  }
  
  private static final int plus-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UInt.constructor-impl(UInt.constructor-impl(0xFFFF & paramShort) + paramInt);
  }
  
  private static final int plus-xj2QHRw(short paramShort1, short paramShort2)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramShort1 & 0xFFFF) + UInt.constructor-impl(0xFFFF & paramShort2));
  }
  
  private static final UIntRange rangeTo-xj2QHRw(short paramShort1, short paramShort2)
  {
    return new UIntRange(UInt.constructor-impl(paramShort1 & 0xFFFF), UInt.constructor-impl(0xFFFF & paramShort2), null);
  }
  
  private static final int rem-7apg3OU(short paramShort, byte paramByte)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), UInt.constructor-impl(paramByte & 0xFF));
  }
  
  private static final long rem-VKZWuLQ(short paramShort, long paramLong)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl(paramShort & 0xFFFF), paramLong);
  }
  
  private static final int rem-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(0xFFFF & paramShort), paramInt);
  }
  
  private static final int rem-xj2QHRw(short paramShort1, short paramShort2)
  {
    return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(paramShort1 & 0xFFFF), UInt.constructor-impl(0xFFFF & paramShort2));
  }
  
  private static final int times-7apg3OU(short paramShort, byte paramByte)
  {
    return UInt.constructor-impl(UInt.constructor-impl(0xFFFF & paramShort) * UInt.constructor-impl(paramByte & 0xFF));
  }
  
  private static final long times-VKZWuLQ(short paramShort, long paramLong)
  {
    return ULong.constructor-impl(ULong.constructor-impl(paramShort & 0xFFFF) * paramLong);
  }
  
  private static final int times-WZ4Q5Ns(short paramShort, int paramInt)
  {
    return UInt.constructor-impl(UInt.constructor-impl(0xFFFF & paramShort) * paramInt);
  }
  
  private static final int times-xj2QHRw(short paramShort1, short paramShort2)
  {
    return UInt.constructor-impl(UInt.constructor-impl(paramShort1 & 0xFFFF) * UInt.constructor-impl(0xFFFF & paramShort2));
  }
  
  private static final byte toByte-impl(short paramShort)
  {
    return (byte)paramShort;
  }
  
  private static final double toDouble-impl(short paramShort)
  {
    return 0xFFFF & paramShort;
  }
  
  private static final float toFloat-impl(short paramShort)
  {
    return 0xFFFF & paramShort;
  }
  
  private static final int toInt-impl(short paramShort)
  {
    return 0xFFFF & paramShort;
  }
  
  private static final long toLong-impl(short paramShort)
  {
    return paramShort & 0xFFFF;
  }
  
  private static final short toShort-impl(short paramShort)
  {
    return paramShort;
  }
  
  public static String toString-impl(short paramShort)
  {
    String str = String.valueOf(0xFFFF & paramShort);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  private static final byte toUByte-w2LRezQ(short paramShort)
  {
    return UByte.constructor-impl((byte)paramShort);
  }
  
  private static final int toUInt-pVg5ArA(short paramShort)
  {
    return UInt.constructor-impl(0xFFFF & paramShort);
  }
  
  private static final long toULong-s-VKNKU(short paramShort)
  {
    return ULong.constructor-impl(paramShort & 0xFFFF);
  }
  
  private static final short toUShort-Mh2AYeg(short paramShort)
  {
    return paramShort;
  }
  
  private static final short xor-xj2QHRw(short paramShort1, short paramShort2)
  {
    return constructor-impl((short)(paramShort1 ^ paramShort2));
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
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\026\020\003\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\026\020\006\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\016\020\007\032\0020\bXT¢\006\002\n\000R\016\020\t\032\0020\bXT¢\006\002\n\000\002\b\n\002\b\031\n\002\b!¨\006\n"}, d2={"Lkotlin/UShort$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UShort;", "S", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */