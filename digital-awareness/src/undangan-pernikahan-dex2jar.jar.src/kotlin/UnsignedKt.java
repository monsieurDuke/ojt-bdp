package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0000\n\000\n\002\030\002\n\000\n\002\020\006\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\t\n\002\020\t\n\002\b\007\n\002\020\016\n\002\b\002\032\030\020\000\032\0020\0012\006\020\002\032\0020\003H\001ø\001\000¢\006\002\020\004\032\030\020\005\032\0020\0062\006\020\002\032\0020\003H\001ø\001\000¢\006\002\020\007\032\030\020\b\032\0020\t2\006\020\n\032\0020\t2\006\020\013\032\0020\tH\001\032\"\020\f\032\0020\0012\006\020\n\032\0020\0012\006\020\013\032\0020\001H\001ø\001\000¢\006\004\b\r\020\016\032\"\020\017\032\0020\0012\006\020\n\032\0020\0012\006\020\013\032\0020\001H\001ø\001\000¢\006\004\b\020\020\016\032\020\020\021\032\0020\0032\006\020\002\032\0020\tH\001\032\030\020\022\032\0020\t2\006\020\n\032\0020\0232\006\020\013\032\0020\023H\001\032\"\020\024\032\0020\0062\006\020\n\032\0020\0062\006\020\013\032\0020\006H\001ø\001\000¢\006\004\b\025\020\026\032\"\020\027\032\0020\0062\006\020\n\032\0020\0062\006\020\013\032\0020\006H\001ø\001\000¢\006\004\b\030\020\026\032\020\020\031\032\0020\0032\006\020\002\032\0020\023H\001\032\020\020\032\032\0020\0332\006\020\002\032\0020\023H\000\032\030\020\032\032\0020\0332\006\020\002\032\0020\0232\006\020\034\032\0020\tH\000\002\004\n\002\b\031¨\006\035"}, d2={"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class UnsignedKt
{
  public static final int doubleToUInt(double paramDouble)
  {
    boolean bool = Double.isNaN(paramDouble);
    int i = -1;
    if (bool) {
      i = 0;
    } else if (paramDouble <= uintToDouble(0)) {
      i = 0;
    } else if (paramDouble < uintToDouble(-1)) {
      if (paramDouble <= 2.147483647E9D) {
        i = UInt.constructor-impl((int)paramDouble);
      } else {
        i = UInt.constructor-impl(UInt.constructor-impl((int)(paramDouble - Integer.MAX_VALUE)) + UInt.constructor-impl(Integer.MAX_VALUE));
      }
    }
    return i;
  }
  
  public static final long doubleToULong(double paramDouble)
  {
    boolean bool = Double.isNaN(paramDouble);
    long l = -1L;
    if (bool) {
      l = 0L;
    } else if (paramDouble <= ulongToDouble(0L)) {
      l = 0L;
    } else if (paramDouble < ulongToDouble(-1L)) {
      if (paramDouble < 9.223372036854776E18D) {
        l = ULong.constructor-impl(paramDouble);
      } else {
        l = ULong.constructor-impl(ULong.constructor-impl((paramDouble - 9.223372036854776E18D)) - Long.MIN_VALUE);
      }
    }
    return l;
  }
  
  public static final int uintCompare(int paramInt1, int paramInt2)
  {
    return Intrinsics.compare(paramInt1 ^ 0x80000000, 0x80000000 ^ paramInt2);
  }
  
  public static final int uintDivide-J1ME1BU(int paramInt1, int paramInt2)
  {
    return UInt.constructor-impl((int)((paramInt1 & 0xFFFFFFFF) / (0xFFFFFFFF & paramInt2)));
  }
  
  public static final int uintRemainder-J1ME1BU(int paramInt1, int paramInt2)
  {
    return UInt.constructor-impl((int)((paramInt1 & 0xFFFFFFFF) % (0xFFFFFFFF & paramInt2)));
  }
  
  public static final double uintToDouble(int paramInt)
  {
    return (0x7FFFFFFF & paramInt) + (paramInt >>> 31 << 30) * 2;
  }
  
  public static final int ulongCompare(long paramLong1, long paramLong2)
  {
    return Intrinsics.compare(paramLong1 ^ 0x8000000000000000, 0x8000000000000000 ^ paramLong2);
  }
  
  public static final long ulongDivide-eb3DHEI(long paramLong1, long paramLong2)
  {
    long l = 0L;
    if (paramLong2 < 0L)
    {
      if (ulongCompare(paramLong1, paramLong2) < 0) {
        paramLong1 = l;
      } else {
        paramLong1 = 1L;
      }
      return ULong.constructor-impl(paramLong1);
    }
    if (paramLong1 >= 0L) {
      return ULong.constructor-impl(paramLong1 / paramLong2);
    }
    int i = 1;
    l = (paramLong1 >>> 1) / paramLong2 << 1;
    if (ulongCompare(ULong.constructor-impl(paramLong1 - l * paramLong2), ULong.constructor-impl(paramLong2)) < 0) {
      i = 0;
    }
    return ULong.constructor-impl(i + l);
  }
  
  public static final long ulongRemainder-eb3DHEI(long paramLong1, long paramLong2)
  {
    long l1 = 0L;
    if (paramLong2 < 0L)
    {
      if (ulongCompare(paramLong1, paramLong2) >= 0) {
        paramLong1 = ULong.constructor-impl(paramLong1 - paramLong2);
      }
      return paramLong1;
    }
    if (paramLong1 >= 0L) {
      return ULong.constructor-impl(paramLong1 % paramLong2);
    }
    long l2 = paramLong1 - ((paramLong1 >>> 1) / paramLong2 << 1) * paramLong2;
    paramLong1 = l1;
    if (ulongCompare(ULong.constructor-impl(l2), ULong.constructor-impl(paramLong2)) >= 0) {
      paramLong1 = paramLong2;
    }
    return ULong.constructor-impl(l2 - paramLong1);
  }
  
  public static final double ulongToDouble(long paramLong)
  {
    return (paramLong >>> 11) * 'ࠀ' + (0x7FF & paramLong);
  }
  
  public static final String ulongToString(long paramLong)
  {
    String str = ulongToString(paramLong, 10);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public static final String ulongToString(long paramLong, int paramInt)
  {
    if (paramLong >= 0L)
    {
      localObject1 = Long.toString(paramLong, CharsKt.checkRadix(paramInt));
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this, checkRadix(radix))");
      return (String)localObject1;
    }
    long l2 = (paramLong >>> 1) / paramInt << 1;
    long l3 = paramLong - paramInt * l2;
    long l1 = l2;
    paramLong = l3;
    if (l3 >= paramInt)
    {
      paramLong = l3 - paramInt;
      l1 = l2 + 1L;
    }
    Object localObject1 = new StringBuilder();
    Object localObject2 = Long.toString(l1, CharsKt.checkRadix(paramInt));
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    Intrinsics.checkNotNullExpressionValue(localObject2, "toString(this, checkRadix(radix))");
    localObject2 = ((StringBuilder)localObject1).append((String)localObject2);
    localObject1 = Long.toString(paramLong, CharsKt.checkRadix(paramInt));
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this, checkRadix(radix))");
    return (String)localObject1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UnsignedKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */