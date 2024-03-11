package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000b\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\016\n\002\030\002\n\002\b\t\n\002\020\013\n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\f\n\002\030\002\n\002\b\006\n\002\020\b\n\002\020\t\n\002\b\n\032\036\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\001H\007ø\001\000¢\006\004\b\003\020\004\032\036\020\000\032\0020\005*\0020\0052\006\020\002\032\0020\005H\007ø\001\000¢\006\004\b\006\020\007\032\036\020\000\032\0020\b*\0020\b2\006\020\002\032\0020\bH\007ø\001\000¢\006\004\b\t\020\n\032\036\020\000\032\0020\013*\0020\0132\006\020\002\032\0020\013H\007ø\001\000¢\006\004\b\f\020\r\032\036\020\016\032\0020\001*\0020\0012\006\020\017\032\0020\001H\007ø\001\000¢\006\004\b\020\020\004\032\036\020\016\032\0020\005*\0020\0052\006\020\017\032\0020\005H\007ø\001\000¢\006\004\b\021\020\007\032\036\020\016\032\0020\b*\0020\b2\006\020\017\032\0020\bH\007ø\001\000¢\006\004\b\022\020\n\032\036\020\016\032\0020\013*\0020\0132\006\020\017\032\0020\013H\007ø\001\000¢\006\004\b\023\020\r\032&\020\024\032\0020\001*\0020\0012\006\020\002\032\0020\0012\006\020\017\032\0020\001H\007ø\001\000¢\006\004\b\025\020\026\032&\020\024\032\0020\005*\0020\0052\006\020\002\032\0020\0052\006\020\017\032\0020\005H\007ø\001\000¢\006\004\b\027\020\030\032$\020\024\032\0020\005*\0020\0052\f\020\031\032\b\022\004\022\0020\0050\032H\007ø\001\000¢\006\004\b\033\020\034\032&\020\024\032\0020\b*\0020\b2\006\020\002\032\0020\b2\006\020\017\032\0020\bH\007ø\001\000¢\006\004\b\035\020\036\032$\020\024\032\0020\b*\0020\b2\f\020\031\032\b\022\004\022\0020\b0\032H\007ø\001\000¢\006\004\b\037\020 \032&\020\024\032\0020\013*\0020\0132\006\020\002\032\0020\0132\006\020\017\032\0020\013H\007ø\001\000¢\006\004\b!\020\"\032\037\020#\032\0020$*\0020%2\006\020&\032\0020\001H\002ø\001\000¢\006\004\b'\020(\032\037\020#\032\0020$*\0020%2\b\020)\032\004\030\0010\005H\nø\001\000¢\006\002\b*\032\037\020#\032\0020$*\0020%2\006\020&\032\0020\bH\002ø\001\000¢\006\004\b+\020,\032\037\020#\032\0020$*\0020%2\006\020&\032\0020\013H\002ø\001\000¢\006\004\b-\020.\032\037\020#\032\0020$*\0020/2\006\020&\032\0020\001H\002ø\001\000¢\006\004\b0\0201\032\037\020#\032\0020$*\0020/2\006\020&\032\0020\005H\002ø\001\000¢\006\004\b2\0203\032\037\020#\032\0020$*\0020/2\b\020)\032\004\030\0010\bH\nø\001\000¢\006\002\b4\032\037\020#\032\0020$*\0020/2\006\020&\032\0020\013H\002ø\001\000¢\006\004\b5\0206\032\037\0207\032\00208*\0020\0012\006\0209\032\0020\001H\004ø\001\000¢\006\004\b:\020;\032\037\0207\032\00208*\0020\0052\006\0209\032\0020\005H\004ø\001\000¢\006\004\b<\020=\032\037\0207\032\0020>*\0020\b2\006\0209\032\0020\bH\004ø\001\000¢\006\004\b?\020@\032\037\0207\032\00208*\0020\0132\006\0209\032\0020\013H\004ø\001\000¢\006\004\bA\020B\032\024\020C\032\0020\005*\00208H\007ø\001\000¢\006\002\020D\032\024\020C\032\0020\b*\0020>H\007ø\001\000¢\006\002\020E\032\021\020F\032\004\030\0010\005*\00208H\007ø\001\000\032\021\020F\032\004\030\0010\b*\0020>H\007ø\001\000\032\024\020G\032\0020\005*\00208H\007ø\001\000¢\006\002\020D\032\024\020G\032\0020\b*\0020>H\007ø\001\000¢\006\002\020E\032\021\020H\032\004\030\0010\005*\00208H\007ø\001\000\032\021\020H\032\004\030\0010\b*\0020>H\007ø\001\000\032\025\020I\032\0020\005*\0020%H\bø\001\000¢\006\002\020J\032\034\020I\032\0020\005*\0020%2\006\020I\032\0020KH\007ø\001\000¢\006\002\020L\032\025\020I\032\0020\b*\0020/H\bø\001\000¢\006\002\020M\032\034\020I\032\0020\b*\0020/2\006\020I\032\0020KH\007ø\001\000¢\006\002\020N\032\022\020O\032\004\030\0010\005*\0020%H\bø\001\000\032\031\020O\032\004\030\0010\005*\0020%2\006\020I\032\0020KH\007ø\001\000\032\022\020O\032\004\030\0010\b*\0020/H\bø\001\000\032\031\020O\032\004\030\0010\b*\0020/2\006\020I\032\0020KH\007ø\001\000\032\f\020P\032\00208*\00208H\007\032\f\020P\032\0020>*\0020>H\007\032\025\020Q\032\00208*\002082\006\020Q\032\0020RH\004\032\025\020Q\032\0020>*\0020>2\006\020Q\032\0020SH\004\032\037\020T\032\0020%*\0020\0012\006\0209\032\0020\001H\004ø\001\000¢\006\004\bU\020V\032\037\020T\032\0020%*\0020\0052\006\0209\032\0020\005H\004ø\001\000¢\006\004\bW\020X\032\037\020T\032\0020/*\0020\b2\006\0209\032\0020\bH\004ø\001\000¢\006\004\bY\020Z\032\037\020T\032\0020%*\0020\0132\006\0209\032\0020\013H\004ø\001\000¢\006\004\b[\020\\\002\004\n\002\b\031¨\006]"}, d2={"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", "range", "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "first", "(Lkotlin/ranges/UIntProgression;)I", "(Lkotlin/ranges/ULongProgression;)J", "firstOrNull", "last", "lastOrNull", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "randomOrNull", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/ranges/URangesKt")
class URangesKt___URangesKt
{
  public static final short coerceAtLeast-5PvTz6A(short paramShort1, short paramShort2)
  {
    if (Intrinsics.compare(paramShort1 & 0xFFFF, 0xFFFF & paramShort2) >= 0) {
      paramShort2 = paramShort1;
    }
    return paramShort2;
  }
  
  public static final int coerceAtLeast-J1ME1BU(int paramInt1, int paramInt2)
  {
    if (UnsignedKt.uintCompare(paramInt1, paramInt2) >= 0) {
      paramInt2 = paramInt1;
    }
    return paramInt2;
  }
  
  public static final byte coerceAtLeast-Kr8caGY(byte paramByte1, byte paramByte2)
  {
    if (Intrinsics.compare(paramByte1 & 0xFF, paramByte2 & 0xFF) < 0) {
      paramByte1 = paramByte2;
    }
    return paramByte1;
  }
  
  public static final long coerceAtLeast-eb3DHEI(long paramLong1, long paramLong2)
  {
    if (UnsignedKt.ulongCompare(paramLong1, paramLong2) < 0) {
      paramLong1 = paramLong2;
    }
    return paramLong1;
  }
  
  public static final short coerceAtMost-5PvTz6A(short paramShort1, short paramShort2)
  {
    if (Intrinsics.compare(paramShort1 & 0xFFFF, 0xFFFF & paramShort2) > 0) {
      paramShort1 = paramShort2;
    }
    return paramShort1;
  }
  
  public static final int coerceAtMost-J1ME1BU(int paramInt1, int paramInt2)
  {
    if (UnsignedKt.uintCompare(paramInt1, paramInt2) <= 0) {
      paramInt2 = paramInt1;
    }
    return paramInt2;
  }
  
  public static final byte coerceAtMost-Kr8caGY(byte paramByte1, byte paramByte2)
  {
    if (Intrinsics.compare(paramByte1 & 0xFF, paramByte2 & 0xFF) > 0) {
      paramByte1 = paramByte2;
    }
    return paramByte1;
  }
  
  public static final long coerceAtMost-eb3DHEI(long paramLong1, long paramLong2)
  {
    if (UnsignedKt.ulongCompare(paramLong1, paramLong2) > 0) {
      paramLong1 = paramLong2;
    }
    return paramLong1;
  }
  
  public static final long coerceIn-JPwROB0(long paramLong, ClosedRange<ULong> paramClosedRange)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return ((ULong)RangesKt.coerceIn(ULong.box-impl(paramLong), (ClosedFloatingPointRange)paramClosedRange)).unbox-impl();
    }
    if (!paramClosedRange.isEmpty())
    {
      if (UnsignedKt.ulongCompare(paramLong, ((ULong)paramClosedRange.getStart()).unbox-impl()) < 0) {
        paramLong = ((ULong)paramClosedRange.getStart()).unbox-impl();
      } else if (UnsignedKt.ulongCompare(paramLong, ((ULong)paramClosedRange.getEndInclusive()).unbox-impl()) > 0) {
        paramLong = ((ULong)paramClosedRange.getEndInclusive()).unbox-impl();
      }
      return paramLong;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: " + paramClosedRange + '.');
  }
  
  public static final short coerceIn-VKSA0NQ(short paramShort1, short paramShort2, short paramShort3)
  {
    if (Intrinsics.compare(paramShort2 & 0xFFFF, paramShort3 & 0xFFFF) <= 0)
    {
      if (Intrinsics.compare(paramShort1 & 0xFFFF, paramShort2 & 0xFFFF) < 0) {
        return paramShort2;
      }
      if (Intrinsics.compare(paramShort1 & 0xFFFF, 0xFFFF & paramShort3) > 0) {
        return paramShort3;
      }
      return paramShort1;
    }
    Object localObject1 = new StringBuilder().append("Cannot coerce value to an empty range: maximum ");
    Object localObject2 = UShort.toString-impl(paramShort3);
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    localObject2 = ((StringBuilder)localObject1).append(localObject2).append(" is less than minimum ");
    localObject1 = UShort.toString-impl(paramShort2);
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    throw new IllegalArgumentException(localObject1 + '.');
  }
  
  public static final int coerceIn-WZ9TVnA(int paramInt1, int paramInt2, int paramInt3)
  {
    if (UnsignedKt.uintCompare(paramInt2, paramInt3) <= 0)
    {
      if (UnsignedKt.uintCompare(paramInt1, paramInt2) < 0) {
        return paramInt2;
      }
      if (UnsignedKt.uintCompare(paramInt1, paramInt3) > 0) {
        return paramInt3;
      }
      return paramInt1;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("Cannot coerce value to an empty range: maximum ");
    String str = UInt.toString-impl(paramInt3);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append(" is less than minimum ");
    str = UInt.toString-impl(paramInt2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str + '.');
  }
  
  public static final byte coerceIn-b33U2AM(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    if (Intrinsics.compare(paramByte2 & 0xFF, paramByte3 & 0xFF) <= 0)
    {
      if (Intrinsics.compare(paramByte1 & 0xFF, paramByte2 & 0xFF) < 0) {
        return paramByte2;
      }
      if (Intrinsics.compare(paramByte1 & 0xFF, paramByte3 & 0xFF) > 0) {
        return paramByte3;
      }
      return paramByte1;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("Cannot coerce value to an empty range: maximum ");
    String str = UByte.toString-impl(paramByte3);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append(" is less than minimum ");
    str = UByte.toString-impl(paramByte2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str + '.');
  }
  
  public static final long coerceIn-sambcqE(long paramLong1, long paramLong2, long paramLong3)
  {
    if (UnsignedKt.ulongCompare(paramLong2, paramLong3) <= 0)
    {
      if (UnsignedKt.ulongCompare(paramLong1, paramLong2) < 0) {
        return paramLong2;
      }
      if (UnsignedKt.ulongCompare(paramLong1, paramLong3) > 0) {
        return paramLong3;
      }
      return paramLong1;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("Cannot coerce value to an empty range: maximum ");
    String str = ULong.toString-impl(paramLong3);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append(" is less than minimum ");
    str = ULong.toString-impl(paramLong2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str + '.');
  }
  
  public static final int coerceIn-wuiCnnA(int paramInt, ClosedRange<UInt> paramClosedRange)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return ((UInt)RangesKt.coerceIn(UInt.box-impl(paramInt), (ClosedFloatingPointRange)paramClosedRange)).unbox-impl();
    }
    if (!paramClosedRange.isEmpty())
    {
      if (UnsignedKt.uintCompare(paramInt, ((UInt)paramClosedRange.getStart()).unbox-impl()) < 0) {
        paramInt = ((UInt)paramClosedRange.getStart()).unbox-impl();
      } else if (UnsignedKt.uintCompare(paramInt, ((UInt)paramClosedRange.getEndInclusive()).unbox-impl()) > 0) {
        paramInt = ((UInt)paramClosedRange.getEndInclusive()).unbox-impl();
      }
      return paramInt;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: " + paramClosedRange + '.');
  }
  
  public static final boolean contains-68kG9v0(UIntRange paramUIntRange, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "$this$contains");
    return paramUIntRange.contains-WZ4Q5Ns(UInt.constructor-impl(paramByte & 0xFF));
  }
  
  private static final boolean contains-GYNo2lE(ULongRange paramULongRange, ULong paramULong)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "$this$contains");
    boolean bool;
    if ((paramULong != null) && (paramULongRange.contains-VKZWuLQ(paramULong.unbox-impl()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean contains-Gab390E(ULongRange paramULongRange, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "$this$contains");
    return paramULongRange.contains-VKZWuLQ(ULong.constructor-impl(paramInt & 0xFFFFFFFF));
  }
  
  public static final boolean contains-ULb-yJY(ULongRange paramULongRange, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "$this$contains");
    return paramULongRange.contains-VKZWuLQ(ULong.constructor-impl(paramByte & 0xFF));
  }
  
  public static final boolean contains-ZsK3CEQ(UIntRange paramUIntRange, short paramShort)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "$this$contains");
    return paramUIntRange.contains-WZ4Q5Ns(UInt.constructor-impl(0xFFFF & paramShort));
  }
  
  private static final boolean contains-biwQdVI(UIntRange paramUIntRange, UInt paramUInt)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "$this$contains");
    boolean bool;
    if ((paramUInt != null) && (paramUIntRange.contains-WZ4Q5Ns(paramUInt.unbox-impl()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean contains-fz5IDCE(UIntRange paramUIntRange, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "$this$contains");
    boolean bool;
    if ((ULong.constructor-impl(paramLong >>> 32) == 0L) && (paramUIntRange.contains-WZ4Q5Ns(UInt.constructor-impl((int)paramLong)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean contains-uhHAxoY(ULongRange paramULongRange, short paramShort)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "$this$contains");
    return paramULongRange.contains-VKZWuLQ(ULong.constructor-impl(paramShort & 0xFFFF));
  }
  
  public static final UIntProgression downTo-5PvTz6A(short paramShort1, short paramShort2)
  {
    return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl(paramShort1 & 0xFFFF), UInt.constructor-impl(0xFFFF & paramShort2), -1);
  }
  
  public static final UIntProgression downTo-J1ME1BU(int paramInt1, int paramInt2)
  {
    return UIntProgression.Companion.fromClosedRange-Nkh28Cs(paramInt1, paramInt2, -1);
  }
  
  public static final UIntProgression downTo-Kr8caGY(byte paramByte1, byte paramByte2)
  {
    return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl(paramByte1 & 0xFF), UInt.constructor-impl(paramByte2 & 0xFF), -1);
  }
  
  public static final ULongProgression downTo-eb3DHEI(long paramLong1, long paramLong2)
  {
    return ULongProgression.Companion.fromClosedRange-7ftBX0g(paramLong1, paramLong2, -1L);
  }
  
  public static final int first(UIntProgression paramUIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramUIntProgression, "<this>");
    if (!paramUIntProgression.isEmpty()) {
      return paramUIntProgression.getFirst-pVg5ArA();
    }
    throw new NoSuchElementException("Progression " + paramUIntProgression + " is empty.");
  }
  
  public static final long first(ULongProgression paramULongProgression)
  {
    Intrinsics.checkNotNullParameter(paramULongProgression, "<this>");
    if (!paramULongProgression.isEmpty()) {
      return paramULongProgression.getFirst-s-VKNKU();
    }
    throw new NoSuchElementException("Progression " + paramULongProgression + " is empty.");
  }
  
  public static final UInt firstOrNull(UIntProgression paramUIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramUIntProgression, "<this>");
    if (paramUIntProgression.isEmpty()) {
      paramUIntProgression = null;
    } else {
      paramUIntProgression = UInt.box-impl(paramUIntProgression.getFirst-pVg5ArA());
    }
    return paramUIntProgression;
  }
  
  public static final ULong firstOrNull(ULongProgression paramULongProgression)
  {
    Intrinsics.checkNotNullParameter(paramULongProgression, "<this>");
    if (paramULongProgression.isEmpty()) {
      paramULongProgression = null;
    } else {
      paramULongProgression = ULong.box-impl(paramULongProgression.getFirst-s-VKNKU());
    }
    return paramULongProgression;
  }
  
  public static final int last(UIntProgression paramUIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramUIntProgression, "<this>");
    if (!paramUIntProgression.isEmpty()) {
      return paramUIntProgression.getLast-pVg5ArA();
    }
    throw new NoSuchElementException("Progression " + paramUIntProgression + " is empty.");
  }
  
  public static final long last(ULongProgression paramULongProgression)
  {
    Intrinsics.checkNotNullParameter(paramULongProgression, "<this>");
    if (!paramULongProgression.isEmpty()) {
      return paramULongProgression.getLast-s-VKNKU();
    }
    throw new NoSuchElementException("Progression " + paramULongProgression + " is empty.");
  }
  
  public static final UInt lastOrNull(UIntProgression paramUIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramUIntProgression, "<this>");
    if (paramUIntProgression.isEmpty()) {
      paramUIntProgression = null;
    } else {
      paramUIntProgression = UInt.box-impl(paramUIntProgression.getLast-pVg5ArA());
    }
    return paramUIntProgression;
  }
  
  public static final ULong lastOrNull(ULongProgression paramULongProgression)
  {
    Intrinsics.checkNotNullParameter(paramULongProgression, "<this>");
    if (paramULongProgression.isEmpty()) {
      paramULongProgression = null;
    } else {
      paramULongProgression = ULong.box-impl(paramULongProgression.getLast-s-VKNKU());
    }
    return paramULongProgression;
  }
  
  private static final int random(UIntRange paramUIntRange)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "<this>");
    return URangesKt.random(paramUIntRange, (Random)Random.Default);
  }
  
  public static final int random(UIntRange paramUIntRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    try
    {
      int i = URandomKt.nextUInt(paramRandom, paramUIntRange);
      return i;
    }
    catch (IllegalArgumentException paramUIntRange)
    {
      throw new NoSuchElementException(paramUIntRange.getMessage());
    }
  }
  
  private static final long random(ULongRange paramULongRange)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "<this>");
    return URangesKt.random(paramULongRange, (Random)Random.Default);
  }
  
  public static final long random(ULongRange paramULongRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    try
    {
      long l = URandomKt.nextULong(paramRandom, paramULongRange);
      return l;
    }
    catch (IllegalArgumentException paramULongRange)
    {
      throw new NoSuchElementException(paramULongRange.getMessage());
    }
  }
  
  private static final UInt randomOrNull(UIntRange paramUIntRange)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "<this>");
    return URangesKt.randomOrNull(paramUIntRange, (Random)Random.Default);
  }
  
  public static final UInt randomOrNull(UIntRange paramUIntRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramUIntRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    if (paramUIntRange.isEmpty()) {
      return null;
    }
    return UInt.box-impl(URandomKt.nextUInt(paramRandom, paramUIntRange));
  }
  
  private static final ULong randomOrNull(ULongRange paramULongRange)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "<this>");
    return URangesKt.randomOrNull(paramULongRange, (Random)Random.Default);
  }
  
  public static final ULong randomOrNull(ULongRange paramULongRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramULongRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    if (paramULongRange.isEmpty()) {
      return null;
    }
    return ULong.box-impl(URandomKt.nextULong(paramRandom, paramULongRange));
  }
  
  public static final UIntProgression reversed(UIntProgression paramUIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramUIntProgression, "<this>");
    return UIntProgression.Companion.fromClosedRange-Nkh28Cs(paramUIntProgression.getLast-pVg5ArA(), paramUIntProgression.getFirst-pVg5ArA(), -paramUIntProgression.getStep());
  }
  
  public static final ULongProgression reversed(ULongProgression paramULongProgression)
  {
    Intrinsics.checkNotNullParameter(paramULongProgression, "<this>");
    return ULongProgression.Companion.fromClosedRange-7ftBX0g(paramULongProgression.getLast-s-VKNKU(), paramULongProgression.getFirst-s-VKNKU(), -paramULongProgression.getStep());
  }
  
  public static final UIntProgression step(UIntProgression paramUIntProgression, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramUIntProgression, "<this>");
    boolean bool;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt.checkStepIsPositive(bool, (Number)Integer.valueOf(paramInt));
    UIntProgression.Companion localCompanion = UIntProgression.Companion;
    int i = paramUIntProgression.getFirst-pVg5ArA();
    int j = paramUIntProgression.getLast-pVg5ArA();
    if (paramUIntProgression.getStep() <= 0) {
      paramInt = -paramInt;
    }
    return localCompanion.fromClosedRange-Nkh28Cs(i, j, paramInt);
  }
  
  public static final ULongProgression step(ULongProgression paramULongProgression, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramULongProgression, "<this>");
    boolean bool;
    if (paramLong > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt.checkStepIsPositive(bool, (Number)Long.valueOf(paramLong));
    ULongProgression.Companion localCompanion = ULongProgression.Companion;
    long l1 = paramULongProgression.getFirst-s-VKNKU();
    long l2 = paramULongProgression.getLast-s-VKNKU();
    if (paramULongProgression.getStep() <= 0L) {
      paramLong = -paramLong;
    }
    return localCompanion.fromClosedRange-7ftBX0g(l1, l2, paramLong);
  }
  
  public static final UIntRange until-5PvTz6A(short paramShort1, short paramShort2)
  {
    if (Intrinsics.compare(paramShort2 & 0xFFFF, 0) <= 0) {
      return UIntRange.Companion.getEMPTY();
    }
    return new UIntRange(UInt.constructor-impl(paramShort1 & 0xFFFF), UInt.constructor-impl(UInt.constructor-impl(0xFFFF & paramShort2) - 1), null);
  }
  
  public static final UIntRange until-J1ME1BU(int paramInt1, int paramInt2)
  {
    if (UnsignedKt.uintCompare(paramInt2, 0) <= 0) {
      return UIntRange.Companion.getEMPTY();
    }
    return new UIntRange(paramInt1, UInt.constructor-impl(paramInt2 - 1), null);
  }
  
  public static final UIntRange until-Kr8caGY(byte paramByte1, byte paramByte2)
  {
    if (Intrinsics.compare(paramByte2 & 0xFF, 0) <= 0) {
      return UIntRange.Companion.getEMPTY();
    }
    return new UIntRange(UInt.constructor-impl(paramByte1 & 0xFF), UInt.constructor-impl(UInt.constructor-impl(paramByte2 & 0xFF) - 1), null);
  }
  
  public static final ULongRange until-eb3DHEI(long paramLong1, long paramLong2)
  {
    if (UnsignedKt.ulongCompare(paramLong2, 0L) <= 0) {
      return ULongRange.Companion.getEMPTY();
    }
    return new ULongRange(paramLong1, ULong.constructor-impl(paramLong2 - ULong.constructor-impl(1 & 0xFFFFFFFF)), null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/URangesKt___URangesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */