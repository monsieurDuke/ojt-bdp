package kotlin.time;

import kotlin.Metadata;

@Metadata(d1={"\000\024\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\r\b\003\030\0002\0020\001B\030\022\006\020\002\032\0020\001\022\006\020\003\032\0020\004ø\001\000¢\006\002\020\005J\025\020\013\032\0020\004H\026ø\001\001ø\001\000¢\006\004\b\f\020\007J\033\020\r\032\0020\0012\006\020\016\032\0020\004H\002ø\001\000¢\006\004\b\017\020\020R\031\020\003\032\0020\004ø\001\000ø\001\001¢\006\n\n\002\020\b\032\004\b\006\020\007R\021\020\002\032\0020\001¢\006\b\n\000\032\004\b\t\020\n\002\b\n\002\b\031\n\002\b!¨\006\021"}, d2={"Lkotlin/time/AdjustedTimeMark;", "Lkotlin/time/TimeMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/TimeMark;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment-UwyO8pc", "()J", "J", "getMark", "()Lkotlin/time/TimeMark;", "elapsedNow", "elapsedNow-UwyO8pc", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/TimeMark;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class AdjustedTimeMark
  implements TimeMark
{
  private final long adjustment;
  private final TimeMark mark;
  
  private AdjustedTimeMark(TimeMark paramTimeMark, long paramLong)
  {
    this.mark = paramTimeMark;
    this.adjustment = paramLong;
  }
  
  public long elapsedNow-UwyO8pc()
  {
    return Duration.minus-LRDsOJo(this.mark.elapsedNow-UwyO8pc(), this.adjustment);
  }
  
  public final long getAdjustment-UwyO8pc()
  {
    return this.adjustment;
  }
  
  public final TimeMark getMark()
  {
    return this.mark;
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
    return (TimeMark)new AdjustedTimeMark(this.mark, Duration.plus-LRDsOJo(this.adjustment, paramLong), null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/AdjustedTimeMark.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */