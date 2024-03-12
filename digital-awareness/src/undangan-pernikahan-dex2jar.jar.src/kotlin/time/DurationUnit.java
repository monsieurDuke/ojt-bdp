package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

@Metadata(d1={"\000\022\n\002\030\002\n\002\020\020\n\000\n\002\030\002\n\002\b\013\b\001\030\0002\b\022\004\022\0020\0000\001B\017\b\002\022\006\020\002\032\0020\003¢\006\002\020\004R\024\020\002\032\0020\003X\004¢\006\b\n\000\032\004\b\005\020\006j\002\b\007j\002\b\bj\002\b\tj\002\b\nj\002\b\013j\002\b\fj\002\b\r¨\006\016"}, d2={"Lkotlin/time/DurationUnit;", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Ljava/lang/String;ILjava/util/concurrent/TimeUnit;)V", "getTimeUnit$kotlin_stdlib", "()Ljava/util/concurrent/TimeUnit;", "NANOSECONDS", "MICROSECONDS", "MILLISECONDS", "SECONDS", "MINUTES", "HOURS", "DAYS", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public enum DurationUnit
{
  private static final DurationUnit[] $VALUES = $values();
  private final TimeUnit timeUnit;
  
  static
  {
    MICROSECONDS = new DurationUnit("MICROSECONDS", 1, TimeUnit.MICROSECONDS);
    MILLISECONDS = new DurationUnit("MILLISECONDS", 2, TimeUnit.MILLISECONDS);
    SECONDS = new DurationUnit("SECONDS", 3, TimeUnit.SECONDS);
    MINUTES = new DurationUnit("MINUTES", 4, TimeUnit.MINUTES);
    HOURS = new DurationUnit("HOURS", 5, TimeUnit.HOURS);
    DAYS = new DurationUnit("DAYS", 6, TimeUnit.DAYS);
  }
  
  private DurationUnit(TimeUnit paramTimeUnit)
  {
    this.timeUnit = paramTimeUnit;
  }
  
  public final TimeUnit getTimeUnit$kotlin_stdlib()
  {
    return this.timeUnit;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/DurationUnit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */