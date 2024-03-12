package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.ranges.ULongRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000j\n\002\030\002\n\002\020\017\n\000\n\002\020\t\n\002\b\t\n\002\020\b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\r\n\002\020\013\n\002\020\000\n\002\b\"\n\002\030\002\n\002\b\022\n\002\020\005\n\002\b\003\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\007\n\002\020\n\n\002\b\003\n\002\020\016\n\002\b\016\b@\030\000 |2\b\022\004\022\0020\0000\001:\001|B\024\b\001\022\006\020\002\032\0020\003ø\001\000¢\006\004\b\004\020\005J\033\020\b\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b\n\020\013J\033\020\f\032\0020\r2\006\020\t\032\0020\016H\nø\001\000¢\006\004\b\017\020\020J\033\020\f\032\0020\r2\006\020\t\032\0020\021H\nø\001\000¢\006\004\b\022\020\023J\033\020\f\032\0020\r2\006\020\t\032\0020\000H\nø\001\000¢\006\004\b\024\020\025J\033\020\f\032\0020\r2\006\020\t\032\0020\026H\nø\001\000¢\006\004\b\027\020\030J\026\020\031\032\0020\000H\nø\001\001ø\001\000¢\006\004\b\032\020\005J\033\020\033\032\0020\0002\006\020\t\032\0020\016H\nø\001\000¢\006\004\b\034\020\035J\033\020\033\032\0020\0002\006\020\t\032\0020\021H\nø\001\000¢\006\004\b\036\020\037J\033\020\033\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\b \020\013J\033\020\033\032\0020\0002\006\020\t\032\0020\026H\nø\001\000¢\006\004\b!\020\"J\032\020#\032\0020$2\b\020\t\032\004\030\0010%HÖ\003¢\006\004\b&\020'J\033\020(\032\0020\0002\006\020\t\032\0020\016H\bø\001\000¢\006\004\b)\020\035J\033\020(\032\0020\0002\006\020\t\032\0020\021H\bø\001\000¢\006\004\b*\020\037J\033\020(\032\0020\0002\006\020\t\032\0020\000H\bø\001\000¢\006\004\b+\020\013J\033\020(\032\0020\0002\006\020\t\032\0020\026H\bø\001\000¢\006\004\b,\020\"J\020\020-\032\0020\rHÖ\001¢\006\004\b.\020/J\026\0200\032\0020\000H\nø\001\001ø\001\000¢\006\004\b1\020\005J\026\0202\032\0020\000H\bø\001\001ø\001\000¢\006\004\b3\020\005J\033\0204\032\0020\0002\006\020\t\032\0020\016H\nø\001\000¢\006\004\b5\020\035J\033\0204\032\0020\0002\006\020\t\032\0020\021H\nø\001\000¢\006\004\b6\020\037J\033\0204\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\b7\020\013J\033\0204\032\0020\0002\006\020\t\032\0020\026H\nø\001\000¢\006\004\b8\020\"J\033\0209\032\0020\0162\006\020\t\032\0020\016H\bø\001\000¢\006\004\b:\020;J\033\0209\032\0020\0212\006\020\t\032\0020\021H\bø\001\000¢\006\004\b<\020\023J\033\0209\032\0020\0002\006\020\t\032\0020\000H\bø\001\000¢\006\004\b=\020\013J\033\0209\032\0020\0262\006\020\t\032\0020\026H\bø\001\000¢\006\004\b>\020?J\033\020@\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\bA\020\013J\033\020B\032\0020\0002\006\020\t\032\0020\016H\nø\001\000¢\006\004\bC\020\035J\033\020B\032\0020\0002\006\020\t\032\0020\021H\nø\001\000¢\006\004\bD\020\037J\033\020B\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\bE\020\013J\033\020B\032\0020\0002\006\020\t\032\0020\026H\nø\001\000¢\006\004\bF\020\"J\033\020G\032\0020H2\006\020\t\032\0020\000H\nø\001\000¢\006\004\bI\020JJ\033\020K\032\0020\0002\006\020\t\032\0020\016H\nø\001\000¢\006\004\bL\020\035J\033\020K\032\0020\0002\006\020\t\032\0020\021H\nø\001\000¢\006\004\bM\020\037J\033\020K\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\bN\020\013J\033\020K\032\0020\0002\006\020\t\032\0020\026H\nø\001\000¢\006\004\bO\020\"J\036\020P\032\0020\0002\006\020Q\032\0020\rH\fø\001\001ø\001\000¢\006\004\bR\020\037J\036\020S\032\0020\0002\006\020Q\032\0020\rH\fø\001\001ø\001\000¢\006\004\bT\020\037J\033\020U\032\0020\0002\006\020\t\032\0020\016H\nø\001\000¢\006\004\bV\020\035J\033\020U\032\0020\0002\006\020\t\032\0020\021H\nø\001\000¢\006\004\bW\020\037J\033\020U\032\0020\0002\006\020\t\032\0020\000H\nø\001\000¢\006\004\bX\020\013J\033\020U\032\0020\0002\006\020\t\032\0020\026H\nø\001\000¢\006\004\bY\020\"J\020\020Z\032\0020[H\b¢\006\004\b\\\020]J\020\020^\032\0020_H\b¢\006\004\b`\020aJ\020\020b\032\0020cH\b¢\006\004\bd\020eJ\020\020f\032\0020\rH\b¢\006\004\bg\020/J\020\020h\032\0020\003H\b¢\006\004\bi\020\005J\020\020j\032\0020kH\b¢\006\004\bl\020mJ\017\020n\032\0020oH\026¢\006\004\bp\020qJ\026\020r\032\0020\016H\bø\001\001ø\001\000¢\006\004\bs\020]J\026\020t\032\0020\021H\bø\001\001ø\001\000¢\006\004\bu\020/J\026\020v\032\0020\000H\bø\001\001ø\001\000¢\006\004\bw\020\005J\026\020x\032\0020\026H\bø\001\001ø\001\000¢\006\004\by\020mJ\033\020z\032\0020\0002\006\020\t\032\0020\000H\fø\001\000¢\006\004\b{\020\013R\026\020\002\032\0020\0038\000X\004¢\006\b\n\000\022\004\b\006\020\007\001\002\001\0020\003ø\001\000\002\b\n\002\b\031\n\002\b!¨\006}"}, d2={"Lkotlin/ULong;", "", "data", "", "constructor-impl", "(J)J", "getData$annotations", "()V", "and", "other", "and-VKZWuLQ", "(JJ)J", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "dec", "dec-s-VKNKU", "div", "div-7apg3OU", "(JB)J", "div-WZ4Q5Ns", "(JI)J", "div-VKZWuLQ", "div-xj2QHRw", "(JS)J", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(J)I", "inc", "inc-s-VKNKU", "inv", "inv-s-VKNKU", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(JB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(JS)S", "or", "or-VKZWuLQ", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-s-VKNKU", "shr", "shr-s-VKNKU", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(J)B", "toDouble", "", "toDouble-impl", "(J)D", "toFloat", "", "toFloat-impl", "(J)F", "toInt", "toInt-impl", "toLong", "toLong-impl", "toShort", "", "toShort-impl", "(J)S", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-VKZWuLQ", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class ULong
  implements Comparable<ULong>
{
  public static final Companion Companion = new Companion(null);
  public static final long MAX_VALUE = -1L;
  public static final long MIN_VALUE = 0L;
  public static final int SIZE_BITS = 64;
  public static final int SIZE_BYTES = 8;
  private final long data;
  
  private static final long and-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return constructor-impl(paramLong1 & paramLong2);
  }
  
  private static final int compareTo-7apg3OU(long paramLong, byte paramByte)
  {
    return UnsignedKt.ulongCompare(paramLong, constructor-impl(paramByte & 0xFF));
  }
  
  private int compareTo-VKZWuLQ(long paramLong)
  {
    return UnsignedKt.ulongCompare(unbox-impl(), paramLong);
  }
  
  private static int compareTo-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return UnsignedKt.ulongCompare(paramLong1, paramLong2);
  }
  
  private static final int compareTo-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return UnsignedKt.ulongCompare(paramLong, constructor-impl(paramInt & 0xFFFFFFFF));
  }
  
  private static final int compareTo-xj2QHRw(long paramLong, short paramShort)
  {
    return UnsignedKt.ulongCompare(paramLong, constructor-impl(paramShort & 0xFFFF));
  }
  
  public static long constructor-impl(long paramLong)
  {
    return paramLong;
  }
  
  private static final long dec-s-VKNKU(long paramLong)
  {
    return constructor-impl(-1L + paramLong);
  }
  
  private static final long div-7apg3OU(long paramLong, byte paramByte)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong, constructor-impl(paramByte & 0xFF));
  }
  
  private static final long div-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong1, paramLong2);
  }
  
  private static final long div-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong, constructor-impl(paramInt & 0xFFFFFFFF));
  }
  
  private static final long div-xj2QHRw(long paramLong, short paramShort)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong, constructor-impl(paramShort & 0xFFFF));
  }
  
  public static boolean equals-impl(long paramLong, Object paramObject)
  {
    if (!(paramObject instanceof ULong)) {
      return false;
    }
    return paramLong == ((ULong)paramObject).unbox-impl();
  }
  
  public static final boolean equals-impl0(long paramLong1, long paramLong2)
  {
    boolean bool;
    if (paramLong1 == paramLong2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final long floorDiv-7apg3OU(long paramLong, byte paramByte)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong, constructor-impl(paramByte & 0xFF));
  }
  
  private static final long floorDiv-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong1, paramLong2);
  }
  
  private static final long floorDiv-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong, constructor-impl(paramInt & 0xFFFFFFFF));
  }
  
  private static final long floorDiv-xj2QHRw(long paramLong, short paramShort)
  {
    return UnsignedKt.ulongDivide-eb3DHEI(paramLong, constructor-impl(paramShort & 0xFFFF));
  }
  
  public static int hashCode-impl(long paramLong)
  {
    return (int)(paramLong >>> 32 ^ paramLong);
  }
  
  private static final long inc-s-VKNKU(long paramLong)
  {
    return constructor-impl(1L + paramLong);
  }
  
  private static final long inv-s-VKNKU(long paramLong)
  {
    return constructor-impl(paramLong ^ 0xFFFFFFFFFFFFFFFF);
  }
  
  private static final long minus-7apg3OU(long paramLong, byte paramByte)
  {
    return constructor-impl(paramLong - constructor-impl(paramByte & 0xFF));
  }
  
  private static final long minus-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return constructor-impl(paramLong1 - paramLong2);
  }
  
  private static final long minus-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return constructor-impl(paramLong - constructor-impl(paramInt & 0xFFFFFFFF));
  }
  
  private static final long minus-xj2QHRw(long paramLong, short paramShort)
  {
    return constructor-impl(paramLong - constructor-impl(paramShort & 0xFFFF));
  }
  
  private static final byte mod-7apg3OU(long paramLong, byte paramByte)
  {
    return UByte.constructor-impl((byte)(int)UnsignedKt.ulongRemainder-eb3DHEI(paramLong, constructor-impl(paramByte & 0xFF)));
  }
  
  private static final long mod-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(paramLong1, paramLong2);
  }
  
  private static final int mod-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return UInt.constructor-impl((int)UnsignedKt.ulongRemainder-eb3DHEI(paramLong, constructor-impl(paramInt & 0xFFFFFFFF)));
  }
  
  private static final short mod-xj2QHRw(long paramLong, short paramShort)
  {
    return UShort.constructor-impl((short)(int)UnsignedKt.ulongRemainder-eb3DHEI(paramLong, constructor-impl(paramShort & 0xFFFF)));
  }
  
  private static final long or-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return constructor-impl(paramLong1 | paramLong2);
  }
  
  private static final long plus-7apg3OU(long paramLong, byte paramByte)
  {
    return constructor-impl(constructor-impl(paramByte & 0xFF) + paramLong);
  }
  
  private static final long plus-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return constructor-impl(paramLong1 + paramLong2);
  }
  
  private static final long plus-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return constructor-impl(constructor-impl(paramInt & 0xFFFFFFFF) + paramLong);
  }
  
  private static final long plus-xj2QHRw(long paramLong, short paramShort)
  {
    return constructor-impl(constructor-impl(paramShort & 0xFFFF) + paramLong);
  }
  
  private static final ULongRange rangeTo-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return new ULongRange(paramLong1, paramLong2, null);
  }
  
  private static final long rem-7apg3OU(long paramLong, byte paramByte)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(paramLong, constructor-impl(paramByte & 0xFF));
  }
  
  private static final long rem-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(paramLong1, paramLong2);
  }
  
  private static final long rem-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(paramLong, constructor-impl(paramInt & 0xFFFFFFFF));
  }
  
  private static final long rem-xj2QHRw(long paramLong, short paramShort)
  {
    return UnsignedKt.ulongRemainder-eb3DHEI(paramLong, constructor-impl(paramShort & 0xFFFF));
  }
  
  private static final long shl-s-VKNKU(long paramLong, int paramInt)
  {
    return constructor-impl(paramLong << paramInt);
  }
  
  private static final long shr-s-VKNKU(long paramLong, int paramInt)
  {
    return constructor-impl(paramLong >>> paramInt);
  }
  
  private static final long times-7apg3OU(long paramLong, byte paramByte)
  {
    return constructor-impl(constructor-impl(paramByte & 0xFF) * paramLong);
  }
  
  private static final long times-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return constructor-impl(paramLong1 * paramLong2);
  }
  
  private static final long times-WZ4Q5Ns(long paramLong, int paramInt)
  {
    return constructor-impl(constructor-impl(paramInt & 0xFFFFFFFF) * paramLong);
  }
  
  private static final long times-xj2QHRw(long paramLong, short paramShort)
  {
    return constructor-impl(constructor-impl(paramShort & 0xFFFF) * paramLong);
  }
  
  private static final byte toByte-impl(long paramLong)
  {
    return (byte)(int)paramLong;
  }
  
  private static final double toDouble-impl(long paramLong)
  {
    return UnsignedKt.ulongToDouble(paramLong);
  }
  
  private static final float toFloat-impl(long paramLong)
  {
    return (float)UnsignedKt.ulongToDouble(paramLong);
  }
  
  private static final int toInt-impl(long paramLong)
  {
    return (int)paramLong;
  }
  
  private static final long toLong-impl(long paramLong)
  {
    return paramLong;
  }
  
  private static final short toShort-impl(long paramLong)
  {
    return (short)(int)paramLong;
  }
  
  public static String toString-impl(long paramLong)
  {
    String str = UnsignedKt.ulongToString(paramLong);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  private static final byte toUByte-w2LRezQ(long paramLong)
  {
    return UByte.constructor-impl((byte)(int)paramLong);
  }
  
  private static final int toUInt-pVg5ArA(long paramLong)
  {
    return UInt.constructor-impl((int)paramLong);
  }
  
  private static final long toULong-s-VKNKU(long paramLong)
  {
    return paramLong;
  }
  
  private static final short toUShort-Mh2AYeg(long paramLong)
  {
    return UShort.constructor-impl((short)(int)paramLong);
  }
  
  private static final long xor-VKZWuLQ(long paramLong1, long paramLong2)
  {
    return constructor-impl(paramLong1 ^ paramLong2);
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
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\026\020\003\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\026\020\006\032\0020\004XTø\001\000ø\001\001¢\006\004\n\002\020\005R\016\020\007\032\0020\bXT¢\006\002\n\000R\016\020\t\032\0020\bXT¢\006\002\n\000\002\b\n\002\b\031\n\002\b!¨\006\n"}, d2={"Lkotlin/ULong$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/ULong;", "J", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ULong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */