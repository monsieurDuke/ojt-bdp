package kotlin.time;

import kotlin.Metadata;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\006\n\002\b\002\b'\030\0002\0020\001:\001\013B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\007\032\0020\bH\026J\b\020\t\032\0020\nH$R\024\020\002\032\0020\003X\004¢\006\b\n\000\032\004\b\005\020\006¨\006\f"}, d2={"Lkotlin/time/AbstractDoubleTimeSource;", "Lkotlin/time/TimeSource;", "unit", "Lkotlin/time/DurationUnit;", "(Lkotlin/time/DurationUnit;)V", "getUnit", "()Lkotlin/time/DurationUnit;", "markNow", "Lkotlin/time/TimeMark;", "read", "", "DoubleTimeMark", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class AbstractDoubleTimeSource
  implements TimeSource
{
  private final DurationUnit unit;
  
  public AbstractDoubleTimeSource(DurationUnit paramDurationUnit)
  {
    this.unit = paramDurationUnit;
  }
  
  protected final DurationUnit getUnit()
  {
    return this.unit;
  }
  
  public TimeMark markNow()
  {
    return (TimeMark)new DoubleTimeMark(read(), this, Duration.Companion.getZERO-UwyO8pc(), null);
  }
  
  protected abstract double read();
  
  @Metadata(d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\020\006\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\n\b\002\030\0002\0020\001B \022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007ø\001\000¢\006\002\020\bJ\025\020\n\032\0020\007H\026ø\001\001ø\001\000¢\006\004\b\013\020\fJ\033\020\r\032\0020\0012\006\020\016\032\0020\007H\002ø\001\000¢\006\004\b\017\020\020R\026\020\006\032\0020\007X\004ø\001\000ø\001\001¢\006\004\n\002\020\tR\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000\002\b\n\002\b\031\n\002\b!¨\006\021"}, d2={"Lkotlin/time/AbstractDoubleTimeSource$DoubleTimeMark;", "Lkotlin/time/TimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractDoubleTimeSource;", "offset", "Lkotlin/time/Duration;", "(DLkotlin/time/AbstractDoubleTimeSource;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "J", "elapsedNow", "elapsedNow-UwyO8pc", "()J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/TimeMark;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class DoubleTimeMark
    implements TimeMark
  {
    private final long offset;
    private final double startedAt;
    private final AbstractDoubleTimeSource timeSource;
    
    private DoubleTimeMark(double paramDouble, AbstractDoubleTimeSource paramAbstractDoubleTimeSource, long paramLong)
    {
      this.startedAt = paramDouble;
      this.timeSource = paramAbstractDoubleTimeSource;
      this.offset = paramLong;
    }
    
    public long elapsedNow-UwyO8pc()
    {
      return Duration.minus-LRDsOJo(DurationKt.toDuration(this.timeSource.read() - this.startedAt, this.timeSource.getUnit()), this.offset);
    }
    
    public boolean hasNotPassedNow()
    {
      return TimeMark.DefaultImpls.hasNotPassedNow(this);
    }
    
    public boolean hasPassedNow()
    {
      return TimeMark.DefaultImpls.hasPassedNow(this);
    }
    
    public TimeMark minus-LRDsOJo(long paramLong)
    {
      return TimeMark.DefaultImpls.minus-LRDsOJo(this, paramLong);
    }
    
    public TimeMark plus-LRDsOJo(long paramLong)
    {
      return (TimeMark)new DoubleTimeMark(this.startedAt, this.timeSource, Duration.plus-LRDsOJo(this.offset, paramLong), null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/AbstractDoubleTimeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */