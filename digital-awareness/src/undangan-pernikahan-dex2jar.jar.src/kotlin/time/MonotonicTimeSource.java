package kotlin.time;

import kotlin.Metadata;

@Metadata(d1={"\000(\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\n\n\002\020\016\n\000\bÁ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J \020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\b\032\0020\tø\001\000¢\006\004\b\n\020\013J\030\020\f\032\0020\t2\006\020\007\032\0020\006ø\001\000¢\006\004\b\r\020\016J\025\020\017\032\0020\006H\026ø\001\001ø\001\000¢\006\004\b\020\020\021J\b\020\022\032\0020\004H\002J\b\020\023\032\0020\024H\026R\016\020\003\032\0020\004X\004¢\006\002\n\000\002\b\n\002\b\031\n\002\b!¨\006\025"}, d2={"Lkotlin/time/MonotonicTimeSource;", "Lkotlin/time/TimeSource;", "()V", "zero", "", "adjustReading", "Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "timeMark", "duration", "Lkotlin/time/Duration;", "adjustReading-6QKq23U", "(JJ)J", "elapsedFrom", "elapsedFrom-6eNON_k", "(J)J", "markNow", "markNow-z9LOYto", "()J", "read", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class MonotonicTimeSource
  implements TimeSource
{
  public static final MonotonicTimeSource INSTANCE = new MonotonicTimeSource();
  private static final long zero = System.nanoTime();
  
  private final long read()
  {
    return System.nanoTime() - zero;
  }
  
  public final long adjustReading-6QKq23U(long paramLong1, long paramLong2)
  {
    return TimeSource.Monotonic.ValueTimeMark.constructor-impl(LongSaturatedMathKt.saturatingAdd-pTJri5U(paramLong1, paramLong2));
  }
  
  public final long elapsedFrom-6eNON_k(long paramLong)
  {
    return LongSaturatedMathKt.saturatingDiff(read(), paramLong);
  }
  
  public long markNow-z9LOYto()
  {
    return TimeSource.Monotonic.ValueTimeMark.constructor-impl(read());
  }
  
  public String toString()
  {
    return "TimeSource(System.nanoTime())";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/MonotonicTimeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */