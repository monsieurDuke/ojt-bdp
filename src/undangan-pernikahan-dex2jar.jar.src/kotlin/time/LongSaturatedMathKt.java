package kotlin.time;

import kotlin.Metadata;

@Metadata(d1={"\000\022\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\f\032*\020\000\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\0042\006\020\005\032\0020\001H\002ø\001\000¢\006\004\b\006\020\007\032\"\020\b\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\004H\000ø\001\000¢\006\004\b\t\020\n\032\"\020\013\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\004H\002ø\001\000¢\006\004\b\f\020\n\032 \020\r\032\0020\0042\006\020\016\032\0020\0012\006\020\017\032\0020\001H\000ø\001\000¢\006\002\020\n\002\004\n\002\b\031¨\006\020"}, d2={"checkInfiniteSumDefined", "", "longNs", "duration", "Lkotlin/time/Duration;", "durationNs", "checkInfiniteSumDefined-PjuGub4", "(JJJ)J", "saturatingAdd", "saturatingAdd-pTJri5U", "(JJ)J", "saturatingAddInHalves", "saturatingAddInHalves-pTJri5U", "saturatingDiff", "valueNs", "originNs", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class LongSaturatedMathKt
{
  private static final long checkInfiniteSumDefined-PjuGub4(long paramLong1, long paramLong2, long paramLong3)
  {
    if ((Duration.isInfinite-impl(paramLong2)) && ((paramLong1 ^ paramLong3) < 0L)) {
      throw new IllegalArgumentException("Summing infinities of different signs");
    }
    return paramLong1;
  }
  
  public static final long saturatingAdd-pTJri5U(long paramLong1, long paramLong2)
  {
    long l2 = Duration.getInWholeNanoseconds-impl(paramLong2);
    long l1 = Long.MAX_VALUE;
    if ((paramLong1 - 1L | 1L) == Long.MAX_VALUE) {
      return checkInfiniteSumDefined-PjuGub4(paramLong1, paramLong2, l2);
    }
    if ((1L | l2 - 1L) == Long.MAX_VALUE) {
      return saturatingAddInHalves-pTJri5U(paramLong1, paramLong2);
    }
    paramLong2 = paramLong1 + l2;
    if (((paramLong1 ^ paramLong2) & (l2 ^ paramLong2)) < 0L)
    {
      paramLong2 = l1;
      if (paramLong1 < 0L) {
        paramLong2 = Long.MIN_VALUE;
      }
      return paramLong2;
    }
    return paramLong2;
  }
  
  private static final long saturatingAddInHalves-pTJri5U(long paramLong1, long paramLong2)
  {
    long l = Duration.div-UwyO8pc(paramLong2, 2);
    if ((Duration.getInWholeNanoseconds-impl(l) - 1L | 1L) == Long.MAX_VALUE) {
      return (paramLong1 + Duration.toDouble-impl(paramLong2, DurationUnit.NANOSECONDS));
    }
    return saturatingAdd-pTJri5U(saturatingAdd-pTJri5U(paramLong1, l), l);
  }
  
  public static final long saturatingDiff(long paramLong1, long paramLong2)
  {
    if ((1L | paramLong2 - 1L) == Long.MAX_VALUE) {
      return Duration.unaryMinus-UwyO8pc(DurationKt.toDuration(paramLong2, DurationUnit.DAYS));
    }
    long l1 = paramLong1 - paramLong2;
    if (((l1 ^ paramLong1) & (l1 ^ paramLong2 ^ 0xFFFFFFFFFFFFFFFF)) < 0L)
    {
      l1 = 1000000;
      long l2 = paramLong1 / l1;
      long l3 = paramLong2 / l1;
      localCompanion = Duration.Companion;
      l2 = DurationKt.toDuration(l2 - l3, DurationUnit.MILLISECONDS);
      localCompanion = Duration.Companion;
      return Duration.plus-LRDsOJo(l2, DurationKt.toDuration(paramLong1 % l1 - paramLong2 % l1, DurationUnit.NANOSECONDS));
    }
    Duration.Companion localCompanion = Duration.Companion;
    return DurationKt.toDuration(l1, DurationUnit.NANOSECONDS);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/LongSaturatedMathKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */