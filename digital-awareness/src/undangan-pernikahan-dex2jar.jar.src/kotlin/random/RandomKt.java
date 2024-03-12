package kotlin.random;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000:\n\000\n\002\030\002\n\000\n\002\020\b\n\002\020\t\n\000\n\002\020\016\n\000\n\002\020\000\n\002\b\002\n\002\020\002\n\002\020\006\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\003\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\007\032\020\020\000\032\0020\0012\006\020\002\032\0020\004H\007\032\030\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\bH\000\032\030\020\n\032\0020\0132\006\020\007\032\0020\f2\006\020\t\032\0020\fH\000\032\030\020\n\032\0020\0132\006\020\007\032\0020\0032\006\020\t\032\0020\003H\000\032\030\020\n\032\0020\0132\006\020\007\032\0020\0042\006\020\t\032\0020\004H\000\032\020\020\r\032\0020\0032\006\020\016\032\0020\003H\000\032\024\020\017\032\0020\003*\0020\0012\006\020\020\032\0020\021H\007\032\024\020\022\032\0020\004*\0020\0012\006\020\020\032\0020\023H\007\032\024\020\024\032\0020\003*\0020\0032\006\020\025\032\0020\003H\000¨\006\026"}, d2={"Random", "Lkotlin/random/Random;", "seed", "", "", "boundsErrorMessage", "", "from", "", "until", "checkRangeBounds", "", "", "fastLog2", "value", "nextInt", "range", "Lkotlin/ranges/IntRange;", "nextLong", "Lkotlin/ranges/LongRange;", "takeUpperBits", "bitCount", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class RandomKt
{
  public static final Random Random(int paramInt)
  {
    return (Random)new XorWowRandom(paramInt, paramInt >> 31);
  }
  
  public static final Random Random(long paramLong)
  {
    return (Random)new XorWowRandom((int)paramLong, (int)(paramLong >> 32));
  }
  
  public static final String boundsErrorMessage(Object paramObject1, Object paramObject2)
  {
    Intrinsics.checkNotNullParameter(paramObject1, "from");
    Intrinsics.checkNotNullParameter(paramObject2, "until");
    return "Random range is empty: [" + paramObject1 + ", " + paramObject2 + ").";
  }
  
  public static final void checkRangeBounds(double paramDouble1, double paramDouble2)
  {
    int i;
    if (paramDouble2 > paramDouble1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return;
    }
    String str = boundsErrorMessage(Double.valueOf(paramDouble1), Double.valueOf(paramDouble2));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str.toString());
  }
  
  public static final void checkRangeBounds(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt2 > paramInt1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return;
    }
    String str = boundsErrorMessage(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str.toString());
  }
  
  public static final void checkRangeBounds(long paramLong1, long paramLong2)
  {
    int i;
    if (paramLong2 > paramLong1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return;
    }
    String str = boundsErrorMessage(Long.valueOf(paramLong1), Long.valueOf(paramLong2));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str.toString());
  }
  
  public static final int fastLog2(int paramInt)
  {
    return 31 - Integer.numberOfLeadingZeros(paramInt);
  }
  
  public static final int nextInt(Random paramRandom, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramRandom, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    if (!paramIntRange.isEmpty())
    {
      int i;
      if (paramIntRange.getLast() < Integer.MAX_VALUE) {
        i = paramRandom.nextInt(paramIntRange.getFirst(), paramIntRange.getLast() + 1);
      } else if (paramIntRange.getFirst() > Integer.MIN_VALUE) {
        i = paramRandom.nextInt(paramIntRange.getFirst() - 1, paramIntRange.getLast()) + 1;
      } else {
        i = paramRandom.nextInt();
      }
      return i;
    }
    throw new IllegalArgumentException("Cannot get random in empty range: " + paramIntRange);
  }
  
  public static final long nextLong(Random paramRandom, LongRange paramLongRange)
  {
    Intrinsics.checkNotNullParameter(paramRandom, "<this>");
    Intrinsics.checkNotNullParameter(paramLongRange, "range");
    if (!paramLongRange.isEmpty())
    {
      long l;
      if (paramLongRange.getLast() < Long.MAX_VALUE) {
        l = paramRandom.nextLong(paramLongRange.getFirst(), paramLongRange.getLast() + 1L);
      } else if (paramLongRange.getFirst() > Long.MIN_VALUE) {
        l = paramRandom.nextLong(paramLongRange.getFirst() - 1L, paramLongRange.getLast()) + 1L;
      } else {
        l = paramRandom.nextLong();
      }
      return l;
    }
    throw new IllegalArgumentException("Cannot get random in empty range: " + paramLongRange);
  }
  
  public static final int takeUpperBits(int paramInt1, int paramInt2)
  {
    return paramInt1 >>> 32 - paramInt2 & -paramInt2 >> 31;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/random/RandomKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */