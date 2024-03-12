package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;

@Metadata(d1={"\000p\n\002\b\002\n\002\020\017\n\002\b\002\n\002\020\005\n\002\020\006\n\002\020\007\n\002\020\b\n\002\020\t\n\002\020\n\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\030\002\n\000\n\002\020\f\n\002\b\b\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\035\032'\020\000\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\006\020\003\032\002H\001¢\006\002\020\004\032\022\020\000\032\0020\005*\0020\0052\006\020\003\032\0020\005\032\022\020\000\032\0020\006*\0020\0062\006\020\003\032\0020\006\032\022\020\000\032\0020\007*\0020\0072\006\020\003\032\0020\007\032\022\020\000\032\0020\b*\0020\b2\006\020\003\032\0020\b\032\022\020\000\032\0020\t*\0020\t2\006\020\003\032\0020\t\032\022\020\000\032\0020\n*\0020\n2\006\020\003\032\0020\n\032'\020\013\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\006\020\f\032\002H\001¢\006\002\020\004\032\022\020\013\032\0020\005*\0020\0052\006\020\f\032\0020\005\032\022\020\013\032\0020\006*\0020\0062\006\020\f\032\0020\006\032\022\020\013\032\0020\007*\0020\0072\006\020\f\032\0020\007\032\022\020\013\032\0020\b*\0020\b2\006\020\f\032\0020\b\032\022\020\013\032\0020\t*\0020\t2\006\020\f\032\0020\t\032\022\020\013\032\0020\n*\0020\n2\006\020\f\032\0020\n\0323\020\r\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\b\020\003\032\004\030\001H\0012\b\020\f\032\004\030\001H\001¢\006\002\020\016\032/\020\r\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\f\020\017\032\b\022\004\022\002H\0010\020H\007¢\006\002\020\021\032-\020\r\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\f\020\017\032\b\022\004\022\002H\0010\022¢\006\002\020\023\032\032\020\r\032\0020\005*\0020\0052\006\020\003\032\0020\0052\006\020\f\032\0020\005\032\032\020\r\032\0020\006*\0020\0062\006\020\003\032\0020\0062\006\020\f\032\0020\006\032\032\020\r\032\0020\007*\0020\0072\006\020\003\032\0020\0072\006\020\f\032\0020\007\032\032\020\r\032\0020\b*\0020\b2\006\020\003\032\0020\b2\006\020\f\032\0020\b\032\030\020\r\032\0020\b*\0020\b2\f\020\017\032\b\022\004\022\0020\b0\022\032\032\020\r\032\0020\t*\0020\t2\006\020\003\032\0020\t2\006\020\f\032\0020\t\032\030\020\r\032\0020\t*\0020\t2\f\020\017\032\b\022\004\022\0020\t0\022\032\032\020\r\032\0020\n*\0020\n2\006\020\003\032\0020\n2\006\020\f\032\0020\n\032\034\020\024\032\0020\025*\0020\0262\b\020\027\032\004\030\0010\030H\n¢\006\002\020\031\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\006H\002¢\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\007H\002¢\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\bH\002¢\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\tH\002¢\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\nH\002¢\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\005H\002¢\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\007H\002¢\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\bH\002¢\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\tH\002¢\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\nH\002¢\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\005H\002¢\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\006H\002¢\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\bH\002¢\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\tH\002¢\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\nH\002¢\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\005H\002¢\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\006H\002¢\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\007H\002¢\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\tH\002¢\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\nH\002¢\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\005H\002¢\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\006H\002¢\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\007H\002¢\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\bH\002¢\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\nH\002¢\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\005H\002¢\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\006H\002¢\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\007H\002¢\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\bH\002¢\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\tH\002¢\006\002\b \032\034\020\024\032\0020\025*\0020!2\b\020\027\032\004\030\0010\bH\n¢\006\002\020\"\032\034\020\024\032\0020\025*\0020#2\b\020\027\032\004\030\0010\tH\n¢\006\002\020$\032\025\020%\032\0020&*\0020\0052\006\020'\032\0020\005H\004\032\025\020%\032\0020&*\0020\0052\006\020'\032\0020\bH\004\032\025\020%\032\0020(*\0020\0052\006\020'\032\0020\tH\004\032\025\020%\032\0020&*\0020\0052\006\020'\032\0020\nH\004\032\025\020%\032\0020)*\0020\0302\006\020'\032\0020\030H\004\032\025\020%\032\0020&*\0020\b2\006\020'\032\0020\005H\004\032\025\020%\032\0020&*\0020\b2\006\020'\032\0020\bH\004\032\025\020%\032\0020(*\0020\b2\006\020'\032\0020\tH\004\032\025\020%\032\0020&*\0020\b2\006\020'\032\0020\nH\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\005H\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\bH\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\tH\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\nH\004\032\025\020%\032\0020&*\0020\n2\006\020'\032\0020\005H\004\032\025\020%\032\0020&*\0020\n2\006\020'\032\0020\bH\004\032\025\020%\032\0020(*\0020\n2\006\020'\032\0020\tH\004\032\025\020%\032\0020&*\0020\n2\006\020'\032\0020\nH\004\032\f\020*\032\0020\030*\0020)H\007\032\f\020*\032\0020\b*\0020&H\007\032\f\020*\032\0020\t*\0020(H\007\032\023\020+\032\004\030\0010\030*\0020)H\007¢\006\002\020,\032\023\020+\032\004\030\0010\b*\0020&H\007¢\006\002\020-\032\023\020+\032\004\030\0010\t*\0020(H\007¢\006\002\020.\032\f\020/\032\0020\030*\0020)H\007\032\f\020/\032\0020\b*\0020&H\007\032\f\020/\032\0020\t*\0020(H\007\032\023\0200\032\004\030\0010\030*\0020)H\007¢\006\002\020,\032\023\0200\032\004\030\0010\b*\0020&H\007¢\006\002\020-\032\023\0200\032\004\030\0010\t*\0020(H\007¢\006\002\020.\032\r\0201\032\0020\030*\0020\026H\b\032\024\0201\032\0020\030*\0020\0262\006\0201\032\00202H\007\032\r\0201\032\0020\b*\0020!H\b\032\024\0201\032\0020\b*\0020!2\006\0201\032\00202H\007\032\r\0201\032\0020\t*\0020#H\b\032\024\0201\032\0020\t*\0020#2\006\0201\032\00202H\007\032\024\0203\032\004\030\0010\030*\0020\026H\b¢\006\002\0204\032\033\0203\032\004\030\0010\030*\0020\0262\006\0201\032\00202H\007¢\006\002\0205\032\024\0203\032\004\030\0010\b*\0020!H\b¢\006\002\0206\032\033\0203\032\004\030\0010\b*\0020!2\006\0201\032\00202H\007¢\006\002\0207\032\024\0203\032\004\030\0010\t*\0020#H\b¢\006\002\0208\032\033\0203\032\004\030\0010\t*\0020#2\006\0201\032\00202H\007¢\006\002\0209\032\n\020:\032\0020)*\0020)\032\n\020:\032\0020&*\0020&\032\n\020:\032\0020(*\0020(\032\025\020;\032\0020)*\0020)2\006\020;\032\0020\bH\004\032\025\020;\032\0020&*\0020&2\006\020;\032\0020\bH\004\032\025\020;\032\0020(*\0020(2\006\020;\032\0020\tH\004\032\023\020<\032\004\030\0010\005*\0020\006H\000¢\006\002\020=\032\023\020<\032\004\030\0010\005*\0020\007H\000¢\006\002\020>\032\023\020<\032\004\030\0010\005*\0020\bH\000¢\006\002\020?\032\023\020<\032\004\030\0010\005*\0020\tH\000¢\006\002\020@\032\023\020<\032\004\030\0010\005*\0020\nH\000¢\006\002\020A\032\023\020B\032\004\030\0010\b*\0020\006H\000¢\006\002\020C\032\023\020B\032\004\030\0010\b*\0020\007H\000¢\006\002\020D\032\023\020B\032\004\030\0010\b*\0020\tH\000¢\006\002\020E\032\023\020F\032\004\030\0010\t*\0020\006H\000¢\006\002\020G\032\023\020F\032\004\030\0010\t*\0020\007H\000¢\006\002\020H\032\023\020I\032\004\030\0010\n*\0020\006H\000¢\006\002\020J\032\023\020I\032\004\030\0010\n*\0020\007H\000¢\006\002\020K\032\023\020I\032\004\030\0010\n*\0020\bH\000¢\006\002\020L\032\023\020I\032\004\030\0010\n*\0020\tH\000¢\006\002\020M\032\025\020N\032\0020!*\0020\0052\006\020'\032\0020\005H\004\032\025\020N\032\0020!*\0020\0052\006\020'\032\0020\bH\004\032\025\020N\032\0020#*\0020\0052\006\020'\032\0020\tH\004\032\025\020N\032\0020!*\0020\0052\006\020'\032\0020\nH\004\032\025\020N\032\0020\026*\0020\0302\006\020'\032\0020\030H\004\032\025\020N\032\0020!*\0020\b2\006\020'\032\0020\005H\004\032\025\020N\032\0020!*\0020\b2\006\020'\032\0020\bH\004\032\025\020N\032\0020#*\0020\b2\006\020'\032\0020\tH\004\032\025\020N\032\0020!*\0020\b2\006\020'\032\0020\nH\004\032\025\020N\032\0020#*\0020\t2\006\020'\032\0020\005H\004\032\025\020N\032\0020#*\0020\t2\006\020'\032\0020\bH\004\032\025\020N\032\0020#*\0020\t2\006\020'\032\0020\tH\004\032\025\020N\032\0020#*\0020\t2\006\020'\032\0020\nH\004\032\025\020N\032\0020!*\0020\n2\006\020'\032\0020\005H\004\032\025\020N\032\0020!*\0020\n2\006\020'\032\0020\bH\004\032\025\020N\032\0020#*\0020\n2\006\020'\032\0020\tH\004\032\025\020N\032\0020!*\0020\n2\006\020'\032\0020\nH\004¨\006O"}, d2={"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "first", "firstOrNull", "(Lkotlin/ranges/CharProgression;)Ljava/lang/Character;", "(Lkotlin/ranges/IntProgression;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongProgression;)Ljava/lang/Long;", "last", "lastOrNull", "random", "Lkotlin/random/Random;", "randomOrNull", "(Lkotlin/ranges/CharRange;)Ljava/lang/Character;", "(Lkotlin/ranges/CharRange;Lkotlin/random/Random;)Ljava/lang/Character;", "(Lkotlin/ranges/IntRange;)Ljava/lang/Integer;", "(Lkotlin/ranges/IntRange;Lkotlin/random/Random;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongRange;)Ljava/lang/Long;", "(Lkotlin/ranges/LongRange;Lkotlin/random/Random;)Ljava/lang/Long;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/ranges/RangesKt")
class RangesKt___RangesKt
  extends RangesKt__RangesKt
{
  public static final boolean byteRangeContains(ClosedRange<Byte> paramClosedRange, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    Byte localByte = RangesKt.toByteExactOrNull(paramInt);
    boolean bool;
    if (localByte != null) {
      bool = paramClosedRange.contains((Comparable)localByte);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean byteRangeContains(ClosedRange<Byte> paramClosedRange, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    Byte localByte = RangesKt.toByteExactOrNull(paramLong);
    boolean bool;
    if (localByte != null) {
      bool = paramClosedRange.contains((Comparable)localByte);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean byteRangeContains(ClosedRange<Byte> paramClosedRange, short paramShort)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    Byte localByte = RangesKt.toByteExactOrNull(paramShort);
    boolean bool;
    if (localByte != null) {
      bool = paramClosedRange.contains((Comparable)localByte);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final byte coerceAtLeast(byte paramByte1, byte paramByte2)
  {
    if (paramByte1 >= paramByte2) {
      paramByte2 = paramByte1;
    }
    return paramByte2;
  }
  
  public static final double coerceAtLeast(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 >= paramDouble2) {
      paramDouble2 = paramDouble1;
    }
    return paramDouble2;
  }
  
  public static final float coerceAtLeast(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 >= paramFloat2) {
      paramFloat2 = paramFloat1;
    }
    return paramFloat2;
  }
  
  public static final int coerceAtLeast(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {
      paramInt1 = paramInt2;
    }
    return paramInt1;
  }
  
  public static final long coerceAtLeast(long paramLong1, long paramLong2)
  {
    if (paramLong1 < paramLong2) {
      paramLong1 = paramLong2;
    }
    return paramLong1;
  }
  
  public static final <T extends Comparable<? super T>> T coerceAtLeast(T paramT1, T paramT2)
  {
    Intrinsics.checkNotNullParameter(paramT1, "<this>");
    Intrinsics.checkNotNullParameter(paramT2, "minimumValue");
    if (paramT1.compareTo(paramT2) < 0) {
      paramT1 = paramT2;
    }
    return paramT1;
  }
  
  public static final short coerceAtLeast(short paramShort1, short paramShort2)
  {
    if (paramShort1 < paramShort2) {
      paramShort1 = paramShort2;
    }
    return paramShort1;
  }
  
  public static final byte coerceAtMost(byte paramByte1, byte paramByte2)
  {
    if (paramByte1 > paramByte2) {
      paramByte1 = paramByte2;
    }
    return paramByte1;
  }
  
  public static final double coerceAtMost(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 > paramDouble2) {
      paramDouble1 = paramDouble2;
    }
    return paramDouble1;
  }
  
  public static final float coerceAtMost(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 > paramFloat2) {
      paramFloat1 = paramFloat2;
    }
    return paramFloat1;
  }
  
  public static final int coerceAtMost(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      paramInt1 = paramInt2;
    }
    return paramInt1;
  }
  
  public static final long coerceAtMost(long paramLong1, long paramLong2)
  {
    if (paramLong1 > paramLong2) {
      paramLong1 = paramLong2;
    }
    return paramLong1;
  }
  
  public static final <T extends Comparable<? super T>> T coerceAtMost(T paramT1, T paramT2)
  {
    Intrinsics.checkNotNullParameter(paramT1, "<this>");
    Intrinsics.checkNotNullParameter(paramT2, "maximumValue");
    if (paramT1.compareTo(paramT2) > 0) {
      paramT1 = paramT2;
    }
    return paramT1;
  }
  
  public static final short coerceAtMost(short paramShort1, short paramShort2)
  {
    if (paramShort1 > paramShort2) {
      paramShort1 = paramShort2;
    }
    return paramShort1;
  }
  
  public static final byte coerceIn(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    if (paramByte2 <= paramByte3)
    {
      if (paramByte1 < paramByte2) {
        return paramByte2;
      }
      if (paramByte1 > paramByte3) {
        return paramByte3;
      }
      return paramByte1;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + paramByte3 + " is less than minimum " + paramByte2 + '.');
  }
  
  public static final double coerceIn(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble2 <= paramDouble3)
    {
      if (paramDouble1 < paramDouble2) {
        return paramDouble2;
      }
      if (paramDouble1 > paramDouble3) {
        return paramDouble3;
      }
      return paramDouble1;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + paramDouble3 + " is less than minimum " + paramDouble2 + '.');
  }
  
  public static final float coerceIn(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat2 <= paramFloat3)
    {
      if (paramFloat1 < paramFloat2) {
        return paramFloat2;
      }
      if (paramFloat1 > paramFloat3) {
        return paramFloat3;
      }
      return paramFloat1;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + paramFloat3 + " is less than minimum " + paramFloat2 + '.');
  }
  
  public static final int coerceIn(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 <= paramInt3)
    {
      if (paramInt1 < paramInt2) {
        return paramInt2;
      }
      if (paramInt1 > paramInt3) {
        return paramInt3;
      }
      return paramInt1;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + paramInt3 + " is less than minimum " + paramInt2 + '.');
  }
  
  public static final int coerceIn(int paramInt, ClosedRange<Integer> paramClosedRange)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return ((Number)RangesKt.coerceIn((Comparable)Integer.valueOf(paramInt), (ClosedFloatingPointRange)paramClosedRange)).intValue();
    }
    if (!paramClosedRange.isEmpty())
    {
      if (paramInt < ((Number)paramClosedRange.getStart()).intValue()) {
        paramInt = ((Number)paramClosedRange.getStart()).intValue();
      } else if (paramInt > ((Number)paramClosedRange.getEndInclusive()).intValue()) {
        paramInt = ((Number)paramClosedRange.getEndInclusive()).intValue();
      }
      return paramInt;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: " + paramClosedRange + '.');
  }
  
  public static final long coerceIn(long paramLong1, long paramLong2, long paramLong3)
  {
    if (paramLong2 <= paramLong3)
    {
      if (paramLong1 < paramLong2) {
        return paramLong2;
      }
      if (paramLong1 > paramLong3) {
        return paramLong3;
      }
      return paramLong1;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + paramLong3 + " is less than minimum " + paramLong2 + '.');
  }
  
  public static final long coerceIn(long paramLong, ClosedRange<Long> paramClosedRange)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return ((Number)RangesKt.coerceIn((Comparable)Long.valueOf(paramLong), (ClosedFloatingPointRange)paramClosedRange)).longValue();
    }
    if (!paramClosedRange.isEmpty())
    {
      if (paramLong < ((Number)paramClosedRange.getStart()).longValue()) {
        paramLong = ((Number)paramClosedRange.getStart()).longValue();
      } else if (paramLong > ((Number)paramClosedRange.getEndInclusive()).longValue()) {
        paramLong = ((Number)paramClosedRange.getEndInclusive()).longValue();
      }
      return paramLong;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: " + paramClosedRange + '.');
  }
  
  public static final <T extends Comparable<? super T>> T coerceIn(T paramT1, T paramT2, T paramT3)
  {
    Intrinsics.checkNotNullParameter(paramT1, "<this>");
    if ((paramT2 != null) && (paramT3 != null))
    {
      if (paramT2.compareTo(paramT3) <= 0)
      {
        if (paramT1.compareTo(paramT2) < 0) {
          return paramT2;
        }
        if (paramT1.compareTo(paramT3) > 0) {
          return paramT3;
        }
      }
      else
      {
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + paramT3 + " is less than minimum " + paramT2 + '.');
      }
    }
    else
    {
      if ((paramT2 != null) && (paramT1.compareTo(paramT2) < 0)) {
        return paramT2;
      }
      if ((paramT3 != null) && (paramT1.compareTo(paramT3) > 0)) {
        return paramT3;
      }
    }
    return paramT1;
  }
  
  public static final <T extends Comparable<? super T>> T coerceIn(T paramT, ClosedFloatingPointRange<T> paramClosedFloatingPointRange)
  {
    Intrinsics.checkNotNullParameter(paramT, "<this>");
    Intrinsics.checkNotNullParameter(paramClosedFloatingPointRange, "range");
    if (!paramClosedFloatingPointRange.isEmpty())
    {
      if ((paramClosedFloatingPointRange.lessThanOrEquals(paramT, paramClosedFloatingPointRange.getStart())) && (!paramClosedFloatingPointRange.lessThanOrEquals(paramClosedFloatingPointRange.getStart(), paramT))) {
        paramT = paramClosedFloatingPointRange.getStart();
      } else if ((paramClosedFloatingPointRange.lessThanOrEquals(paramClosedFloatingPointRange.getEndInclusive(), paramT)) && (!paramClosedFloatingPointRange.lessThanOrEquals(paramT, paramClosedFloatingPointRange.getEndInclusive()))) {
        paramT = paramClosedFloatingPointRange.getEndInclusive();
      }
      return paramT;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: " + paramClosedFloatingPointRange + '.');
  }
  
  public static final <T extends Comparable<? super T>> T coerceIn(T paramT, ClosedRange<T> paramClosedRange)
  {
    Intrinsics.checkNotNullParameter(paramT, "<this>");
    Intrinsics.checkNotNullParameter(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return RangesKt.coerceIn(paramT, (ClosedFloatingPointRange)paramClosedRange);
    }
    if (!paramClosedRange.isEmpty())
    {
      if (paramT.compareTo(paramClosedRange.getStart()) < 0) {
        paramT = paramClosedRange.getStart();
      } else if (paramT.compareTo(paramClosedRange.getEndInclusive()) > 0) {
        paramT = paramClosedRange.getEndInclusive();
      }
      return paramT;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: " + paramClosedRange + '.');
  }
  
  public static final short coerceIn(short paramShort1, short paramShort2, short paramShort3)
  {
    if (paramShort2 <= paramShort3)
    {
      if (paramShort1 < paramShort2) {
        return paramShort2;
      }
      if (paramShort1 > paramShort3) {
        return paramShort3;
      }
      return paramShort1;
    }
    throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + paramShort3 + " is less than minimum " + paramShort2 + '.');
  }
  
  private static final boolean contains(CharRange paramCharRange, Character paramCharacter)
  {
    Intrinsics.checkNotNullParameter(paramCharRange, "<this>");
    boolean bool;
    if ((paramCharacter != null) && (paramCharRange.contains(paramCharacter.charValue()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean contains(IntRange paramIntRange, Integer paramInteger)
  {
    Intrinsics.checkNotNullParameter(paramIntRange, "<this>");
    boolean bool;
    if ((paramInteger != null) && (paramIntRange.contains(paramInteger.intValue()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean contains(LongRange paramLongRange, Long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramLongRange, "<this>");
    boolean bool;
    if ((paramLong != null) && (paramLongRange.contains(paramLong.longValue()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean doubleRangeContains(ClosedRange<Double> paramClosedRange, float paramFloat)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Double.valueOf(paramFloat));
  }
  
  public static final CharProgression downTo(char paramChar1, char paramChar2)
  {
    return CharProgression.Companion.fromClosedRange(paramChar1, paramChar2, -1);
  }
  
  public static final IntProgression downTo(byte paramByte1, byte paramByte2)
  {
    return IntProgression.Companion.fromClosedRange(paramByte1, paramByte2, -1);
  }
  
  public static final IntProgression downTo(byte paramByte, int paramInt)
  {
    return IntProgression.Companion.fromClosedRange(paramByte, paramInt, -1);
  }
  
  public static final IntProgression downTo(byte paramByte, short paramShort)
  {
    return IntProgression.Companion.fromClosedRange(paramByte, paramShort, -1);
  }
  
  public static final IntProgression downTo(int paramInt, byte paramByte)
  {
    return IntProgression.Companion.fromClosedRange(paramInt, paramByte, -1);
  }
  
  public static final IntProgression downTo(int paramInt1, int paramInt2)
  {
    return IntProgression.Companion.fromClosedRange(paramInt1, paramInt2, -1);
  }
  
  public static final IntProgression downTo(int paramInt, short paramShort)
  {
    return IntProgression.Companion.fromClosedRange(paramInt, paramShort, -1);
  }
  
  public static final IntProgression downTo(short paramShort, byte paramByte)
  {
    return IntProgression.Companion.fromClosedRange(paramShort, paramByte, -1);
  }
  
  public static final IntProgression downTo(short paramShort, int paramInt)
  {
    return IntProgression.Companion.fromClosedRange(paramShort, paramInt, -1);
  }
  
  public static final IntProgression downTo(short paramShort1, short paramShort2)
  {
    return IntProgression.Companion.fromClosedRange(paramShort1, paramShort2, -1);
  }
  
  public static final LongProgression downTo(byte paramByte, long paramLong)
  {
    return LongProgression.Companion.fromClosedRange(paramByte, paramLong, -1L);
  }
  
  public static final LongProgression downTo(int paramInt, long paramLong)
  {
    return LongProgression.Companion.fromClosedRange(paramInt, paramLong, -1L);
  }
  
  public static final LongProgression downTo(long paramLong, byte paramByte)
  {
    return LongProgression.Companion.fromClosedRange(paramLong, paramByte, -1L);
  }
  
  public static final LongProgression downTo(long paramLong, int paramInt)
  {
    return LongProgression.Companion.fromClosedRange(paramLong, paramInt, -1L);
  }
  
  public static final LongProgression downTo(long paramLong1, long paramLong2)
  {
    return LongProgression.Companion.fromClosedRange(paramLong1, paramLong2, -1L);
  }
  
  public static final LongProgression downTo(long paramLong, short paramShort)
  {
    return LongProgression.Companion.fromClosedRange(paramLong, paramShort, -1L);
  }
  
  public static final LongProgression downTo(short paramShort, long paramLong)
  {
    return LongProgression.Companion.fromClosedRange(paramShort, paramLong, -1L);
  }
  
  public static final char first(CharProgression paramCharProgression)
  {
    Intrinsics.checkNotNullParameter(paramCharProgression, "<this>");
    if (!paramCharProgression.isEmpty()) {
      return paramCharProgression.getFirst();
    }
    throw new NoSuchElementException("Progression " + paramCharProgression + " is empty.");
  }
  
  public static final int first(IntProgression paramIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramIntProgression, "<this>");
    if (!paramIntProgression.isEmpty()) {
      return paramIntProgression.getFirst();
    }
    throw new NoSuchElementException("Progression " + paramIntProgression + " is empty.");
  }
  
  public static final long first(LongProgression paramLongProgression)
  {
    Intrinsics.checkNotNullParameter(paramLongProgression, "<this>");
    if (!paramLongProgression.isEmpty()) {
      return paramLongProgression.getFirst();
    }
    throw new NoSuchElementException("Progression " + paramLongProgression + " is empty.");
  }
  
  public static final Character firstOrNull(CharProgression paramCharProgression)
  {
    Intrinsics.checkNotNullParameter(paramCharProgression, "<this>");
    if (paramCharProgression.isEmpty()) {
      paramCharProgression = null;
    } else {
      paramCharProgression = Character.valueOf(paramCharProgression.getFirst());
    }
    return paramCharProgression;
  }
  
  public static final Integer firstOrNull(IntProgression paramIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramIntProgression, "<this>");
    if (paramIntProgression.isEmpty()) {
      paramIntProgression = null;
    } else {
      paramIntProgression = Integer.valueOf(paramIntProgression.getFirst());
    }
    return paramIntProgression;
  }
  
  public static final Long firstOrNull(LongProgression paramLongProgression)
  {
    Intrinsics.checkNotNullParameter(paramLongProgression, "<this>");
    if (paramLongProgression.isEmpty()) {
      paramLongProgression = null;
    } else {
      paramLongProgression = Long.valueOf(paramLongProgression.getFirst());
    }
    return paramLongProgression;
  }
  
  public static final boolean floatRangeContains(ClosedRange<Float> paramClosedRange, double paramDouble)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Float.valueOf((float)paramDouble));
  }
  
  public static final boolean intRangeContains(ClosedRange<Integer> paramClosedRange, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Integer.valueOf(paramByte));
  }
  
  public static final boolean intRangeContains(ClosedRange<Integer> paramClosedRange, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    Integer localInteger = RangesKt.toIntExactOrNull(paramLong);
    boolean bool;
    if (localInteger != null) {
      bool = paramClosedRange.contains((Comparable)localInteger);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean intRangeContains(ClosedRange<Integer> paramClosedRange, short paramShort)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Integer.valueOf(paramShort));
  }
  
  public static final char last(CharProgression paramCharProgression)
  {
    Intrinsics.checkNotNullParameter(paramCharProgression, "<this>");
    if (!paramCharProgression.isEmpty()) {
      return paramCharProgression.getLast();
    }
    throw new NoSuchElementException("Progression " + paramCharProgression + " is empty.");
  }
  
  public static final int last(IntProgression paramIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramIntProgression, "<this>");
    if (!paramIntProgression.isEmpty()) {
      return paramIntProgression.getLast();
    }
    throw new NoSuchElementException("Progression " + paramIntProgression + " is empty.");
  }
  
  public static final long last(LongProgression paramLongProgression)
  {
    Intrinsics.checkNotNullParameter(paramLongProgression, "<this>");
    if (!paramLongProgression.isEmpty()) {
      return paramLongProgression.getLast();
    }
    throw new NoSuchElementException("Progression " + paramLongProgression + " is empty.");
  }
  
  public static final Character lastOrNull(CharProgression paramCharProgression)
  {
    Intrinsics.checkNotNullParameter(paramCharProgression, "<this>");
    if (paramCharProgression.isEmpty()) {
      paramCharProgression = null;
    } else {
      paramCharProgression = Character.valueOf(paramCharProgression.getLast());
    }
    return paramCharProgression;
  }
  
  public static final Integer lastOrNull(IntProgression paramIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramIntProgression, "<this>");
    if (paramIntProgression.isEmpty()) {
      paramIntProgression = null;
    } else {
      paramIntProgression = Integer.valueOf(paramIntProgression.getLast());
    }
    return paramIntProgression;
  }
  
  public static final Long lastOrNull(LongProgression paramLongProgression)
  {
    Intrinsics.checkNotNullParameter(paramLongProgression, "<this>");
    if (paramLongProgression.isEmpty()) {
      paramLongProgression = null;
    } else {
      paramLongProgression = Long.valueOf(paramLongProgression.getLast());
    }
    return paramLongProgression;
  }
  
  public static final boolean longRangeContains(ClosedRange<Long> paramClosedRange, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Long.valueOf(paramByte));
  }
  
  public static final boolean longRangeContains(ClosedRange<Long> paramClosedRange, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Long.valueOf(paramInt));
  }
  
  public static final boolean longRangeContains(ClosedRange<Long> paramClosedRange, short paramShort)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Long.valueOf(paramShort));
  }
  
  private static final char random(CharRange paramCharRange)
  {
    Intrinsics.checkNotNullParameter(paramCharRange, "<this>");
    return RangesKt.random(paramCharRange, (Random)Random.Default);
  }
  
  public static final char random(CharRange paramCharRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramCharRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    try
    {
      int i = paramRandom.nextInt(paramCharRange.getFirst(), paramCharRange.getLast() + '\001');
      return (char)i;
    }
    catch (IllegalArgumentException paramCharRange)
    {
      throw new NoSuchElementException(paramCharRange.getMessage());
    }
  }
  
  private static final int random(IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramIntRange, "<this>");
    return RangesKt.random(paramIntRange, (Random)Random.Default);
  }
  
  public static final int random(IntRange paramIntRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramIntRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    try
    {
      int i = RandomKt.nextInt(paramRandom, paramIntRange);
      return i;
    }
    catch (IllegalArgumentException paramIntRange)
    {
      throw new NoSuchElementException(paramIntRange.getMessage());
    }
  }
  
  private static final long random(LongRange paramLongRange)
  {
    Intrinsics.checkNotNullParameter(paramLongRange, "<this>");
    return RangesKt.random(paramLongRange, (Random)Random.Default);
  }
  
  public static final long random(LongRange paramLongRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramLongRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    try
    {
      long l = RandomKt.nextLong(paramRandom, paramLongRange);
      return l;
    }
    catch (IllegalArgumentException paramLongRange)
    {
      throw new NoSuchElementException(paramLongRange.getMessage());
    }
  }
  
  private static final Character randomOrNull(CharRange paramCharRange)
  {
    Intrinsics.checkNotNullParameter(paramCharRange, "<this>");
    return RangesKt.randomOrNull(paramCharRange, (Random)Random.Default);
  }
  
  public static final Character randomOrNull(CharRange paramCharRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramCharRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    if (paramCharRange.isEmpty()) {
      return null;
    }
    return Character.valueOf((char)paramRandom.nextInt(paramCharRange.getFirst(), paramCharRange.getLast() + '\001'));
  }
  
  private static final Integer randomOrNull(IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramIntRange, "<this>");
    return RangesKt.randomOrNull(paramIntRange, (Random)Random.Default);
  }
  
  public static final Integer randomOrNull(IntRange paramIntRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramIntRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    if (paramIntRange.isEmpty()) {
      return null;
    }
    return Integer.valueOf(RandomKt.nextInt(paramRandom, paramIntRange));
  }
  
  private static final Long randomOrNull(LongRange paramLongRange)
  {
    Intrinsics.checkNotNullParameter(paramLongRange, "<this>");
    return RangesKt.randomOrNull(paramLongRange, (Random)Random.Default);
  }
  
  public static final Long randomOrNull(LongRange paramLongRange, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramLongRange, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    if (paramLongRange.isEmpty()) {
      return null;
    }
    return Long.valueOf(RandomKt.nextLong(paramRandom, paramLongRange));
  }
  
  public static final CharProgression reversed(CharProgression paramCharProgression)
  {
    Intrinsics.checkNotNullParameter(paramCharProgression, "<this>");
    return CharProgression.Companion.fromClosedRange(paramCharProgression.getLast(), paramCharProgression.getFirst(), -paramCharProgression.getStep());
  }
  
  public static final IntProgression reversed(IntProgression paramIntProgression)
  {
    Intrinsics.checkNotNullParameter(paramIntProgression, "<this>");
    return IntProgression.Companion.fromClosedRange(paramIntProgression.getLast(), paramIntProgression.getFirst(), -paramIntProgression.getStep());
  }
  
  public static final LongProgression reversed(LongProgression paramLongProgression)
  {
    Intrinsics.checkNotNullParameter(paramLongProgression, "<this>");
    return LongProgression.Companion.fromClosedRange(paramLongProgression.getLast(), paramLongProgression.getFirst(), -paramLongProgression.getStep());
  }
  
  public static final boolean shortRangeContains(ClosedRange<Short> paramClosedRange, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    return paramClosedRange.contains((Comparable)Short.valueOf((short)paramByte));
  }
  
  public static final boolean shortRangeContains(ClosedRange<Short> paramClosedRange, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    Short localShort = RangesKt.toShortExactOrNull(paramInt);
    boolean bool;
    if (localShort != null) {
      bool = paramClosedRange.contains((Comparable)localShort);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean shortRangeContains(ClosedRange<Short> paramClosedRange, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramClosedRange, "<this>");
    Short localShort = RangesKt.toShortExactOrNull(paramLong);
    boolean bool;
    if (localShort != null) {
      bool = paramClosedRange.contains((Comparable)localShort);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final CharProgression step(CharProgression paramCharProgression, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharProgression, "<this>");
    boolean bool;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt.checkStepIsPositive(bool, (Number)Integer.valueOf(paramInt));
    CharProgression.Companion localCompanion = CharProgression.Companion;
    char c2 = paramCharProgression.getFirst();
    char c1 = paramCharProgression.getLast();
    if (paramCharProgression.getStep() <= 0) {
      paramInt = -paramInt;
    }
    return localCompanion.fromClosedRange(c2, c1, paramInt);
  }
  
  public static final IntProgression step(IntProgression paramIntProgression, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramIntProgression, "<this>");
    boolean bool;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt.checkStepIsPositive(bool, (Number)Integer.valueOf(paramInt));
    IntProgression.Companion localCompanion = IntProgression.Companion;
    int i = paramIntProgression.getFirst();
    int j = paramIntProgression.getLast();
    if (paramIntProgression.getStep() <= 0) {
      paramInt = -paramInt;
    }
    return localCompanion.fromClosedRange(i, j, paramInt);
  }
  
  public static final LongProgression step(LongProgression paramLongProgression, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramLongProgression, "<this>");
    boolean bool;
    if (paramLong > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt.checkStepIsPositive(bool, (Number)Long.valueOf(paramLong));
    LongProgression.Companion localCompanion = LongProgression.Companion;
    long l2 = paramLongProgression.getFirst();
    long l1 = paramLongProgression.getLast();
    if (paramLongProgression.getStep() <= 0L) {
      paramLong = -paramLong;
    }
    return localCompanion.fromClosedRange(l2, l1, paramLong);
  }
  
  public static final Byte toByteExactOrNull(double paramDouble)
  {
    int j = 0;
    int i = j;
    if (paramDouble <= 127.0D)
    {
      i = j;
      if (-128.0D <= paramDouble) {
        i = 1;
      }
    }
    Byte localByte;
    if (i != 0) {
      localByte = Byte.valueOf((byte)(int)paramDouble);
    } else {
      localByte = null;
    }
    return localByte;
  }
  
  public static final Byte toByteExactOrNull(float paramFloat)
  {
    int j = 0;
    int i = j;
    if (paramFloat <= 127.0F)
    {
      i = j;
      if (-128.0F <= paramFloat) {
        i = 1;
      }
    }
    Byte localByte;
    if (i != 0) {
      localByte = Byte.valueOf((byte)(int)paramFloat);
    } else {
      localByte = null;
    }
    return localByte;
  }
  
  public static final Byte toByteExactOrNull(int paramInt)
  {
    Byte localByte;
    if (new IntRange(-128, 127).contains(paramInt)) {
      localByte = Byte.valueOf((byte)paramInt);
    } else {
      localByte = null;
    }
    return localByte;
  }
  
  public static final Byte toByteExactOrNull(long paramLong)
  {
    Byte localByte;
    if (new LongRange(-128L, 127L).contains(paramLong)) {
      localByte = Byte.valueOf((byte)(int)paramLong);
    } else {
      localByte = null;
    }
    return localByte;
  }
  
  public static final Byte toByteExactOrNull(short paramShort)
  {
    Byte localByte;
    if (RangesKt.intRangeContains((ClosedRange)new IntRange(-128, 127), paramShort)) {
      localByte = Byte.valueOf((byte)paramShort);
    } else {
      localByte = null;
    }
    return localByte;
  }
  
  public static final Integer toIntExactOrNull(double paramDouble)
  {
    int j = 0;
    int i = j;
    if (paramDouble <= 2.147483647E9D)
    {
      i = j;
      if (-2.147483648E9D <= paramDouble) {
        i = 1;
      }
    }
    Integer localInteger;
    if (i != 0) {
      localInteger = Integer.valueOf((int)paramDouble);
    } else {
      localInteger = null;
    }
    return localInteger;
  }
  
  public static final Integer toIntExactOrNull(float paramFloat)
  {
    int j = 0;
    int i = j;
    if (paramFloat <= 2.14748365E9F)
    {
      i = j;
      if (-2.14748365E9F <= paramFloat) {
        i = 1;
      }
    }
    Integer localInteger;
    if (i != 0) {
      localInteger = Integer.valueOf((int)paramFloat);
    } else {
      localInteger = null;
    }
    return localInteger;
  }
  
  public static final Integer toIntExactOrNull(long paramLong)
  {
    Integer localInteger;
    if (new LongRange(-2147483648L, 2147483647L).contains(paramLong)) {
      localInteger = Integer.valueOf((int)paramLong);
    } else {
      localInteger = null;
    }
    return localInteger;
  }
  
  public static final Long toLongExactOrNull(double paramDouble)
  {
    int j = 0;
    int i = j;
    if (paramDouble <= 9.223372036854776E18D)
    {
      i = j;
      if (-9.223372036854776E18D <= paramDouble) {
        i = 1;
      }
    }
    Long localLong;
    if (i != 0) {
      localLong = Long.valueOf(paramDouble);
    } else {
      localLong = null;
    }
    return localLong;
  }
  
  public static final Long toLongExactOrNull(float paramFloat)
  {
    int j = 0;
    int i = j;
    if (paramFloat <= 9.223372E18F)
    {
      i = j;
      if (-9.223372E18F <= paramFloat) {
        i = 1;
      }
    }
    Long localLong;
    if (i != 0) {
      localLong = Long.valueOf(paramFloat);
    } else {
      localLong = null;
    }
    return localLong;
  }
  
  public static final Short toShortExactOrNull(double paramDouble)
  {
    int j = 0;
    int i = j;
    if (paramDouble <= 32767.0D)
    {
      i = j;
      if (-32768.0D <= paramDouble) {
        i = 1;
      }
    }
    Short localShort;
    if (i != 0) {
      localShort = Short.valueOf((short)(int)paramDouble);
    } else {
      localShort = null;
    }
    return localShort;
  }
  
  public static final Short toShortExactOrNull(float paramFloat)
  {
    int j = 0;
    int i = j;
    if (paramFloat <= 32767.0F)
    {
      i = j;
      if (-32768.0F <= paramFloat) {
        i = 1;
      }
    }
    Short localShort;
    if (i != 0) {
      localShort = Short.valueOf((short)(int)paramFloat);
    } else {
      localShort = null;
    }
    return localShort;
  }
  
  public static final Short toShortExactOrNull(int paramInt)
  {
    Short localShort;
    if (new IntRange(32768, 32767).contains(paramInt)) {
      localShort = Short.valueOf((short)paramInt);
    } else {
      localShort = null;
    }
    return localShort;
  }
  
  public static final Short toShortExactOrNull(long paramLong)
  {
    Short localShort;
    if (new LongRange(-32768L, 32767L).contains(paramLong)) {
      localShort = Short.valueOf((short)(int)paramLong);
    } else {
      localShort = null;
    }
    return localShort;
  }
  
  public static final CharRange until(char paramChar1, char paramChar2)
  {
    if (Intrinsics.compare(paramChar2, 0) <= 0) {
      return CharRange.Companion.getEMPTY();
    }
    return new CharRange(paramChar1, (char)(paramChar2 - '\001'));
  }
  
  public static final IntRange until(byte paramByte1, byte paramByte2)
  {
    return new IntRange(paramByte1, paramByte2 - 1);
  }
  
  public static final IntRange until(byte paramByte, int paramInt)
  {
    if (paramInt <= Integer.MIN_VALUE) {
      return IntRange.Companion.getEMPTY();
    }
    return new IntRange(paramByte, paramInt - 1);
  }
  
  public static final IntRange until(byte paramByte, short paramShort)
  {
    return new IntRange(paramByte, paramShort - 1);
  }
  
  public static final IntRange until(int paramInt, byte paramByte)
  {
    return new IntRange(paramInt, paramByte - 1);
  }
  
  public static final IntRange until(int paramInt1, int paramInt2)
  {
    if (paramInt2 <= Integer.MIN_VALUE) {
      return IntRange.Companion.getEMPTY();
    }
    return new IntRange(paramInt1, paramInt2 - 1);
  }
  
  public static final IntRange until(int paramInt, short paramShort)
  {
    return new IntRange(paramInt, paramShort - 1);
  }
  
  public static final IntRange until(short paramShort, byte paramByte)
  {
    return new IntRange(paramShort, paramByte - 1);
  }
  
  public static final IntRange until(short paramShort, int paramInt)
  {
    if (paramInt <= Integer.MIN_VALUE) {
      return IntRange.Companion.getEMPTY();
    }
    return new IntRange(paramShort, paramInt - 1);
  }
  
  public static final IntRange until(short paramShort1, short paramShort2)
  {
    return new IntRange(paramShort1, paramShort2 - 1);
  }
  
  public static final LongRange until(byte paramByte, long paramLong)
  {
    if (paramLong <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramByte, paramLong - 1L);
  }
  
  public static final LongRange until(int paramInt, long paramLong)
  {
    if (paramLong <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramInt, paramLong - 1L);
  }
  
  public static final LongRange until(long paramLong, byte paramByte)
  {
    return new LongRange(paramLong, paramByte - 1L);
  }
  
  public static final LongRange until(long paramLong, int paramInt)
  {
    return new LongRange(paramLong, paramInt - 1L);
  }
  
  public static final LongRange until(long paramLong1, long paramLong2)
  {
    if (paramLong2 <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramLong1, paramLong2 - 1L);
  }
  
  public static final LongRange until(long paramLong, short paramShort)
  {
    return new LongRange(paramLong, paramShort - 1L);
  }
  
  public static final LongRange until(short paramShort, long paramLong)
  {
    if (paramLong <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramShort, paramLong - 1L);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/RangesKt___RangesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */