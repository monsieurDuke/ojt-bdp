package kotlin.time;

import kotlin.Metadata;

@Metadata(d1={"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\005\bg\030\0002\0020\001J\016\020\002\032\0020\003H&ø\001\000ø\001\001J\b\020\004\032\0020\005H\026J\b\020\006\032\0020\005H\026J\024\020\007\032\0020\0002\006\020\b\032\0020\003H\002ø\001\001J\024\020\t\032\0020\0002\006\020\b\032\0020\003H\002ø\001\001\002\b\n\002\b!\n\002\b\031¨\006\n"}, d2={"Lkotlin/time/TimeMark;", "", "elapsedNow", "Lkotlin/time/Duration;", "hasNotPassedNow", "", "hasPassedNow", "minus", "duration", "plus", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface TimeMark
{
  public abstract long elapsedNow-UwyO8pc();
  
  public abstract boolean hasNotPassedNow();
  
  public abstract boolean hasPassedNow();
  
  public abstract TimeMark minus-LRDsOJo(long paramLong);
  
  public abstract TimeMark plus-LRDsOJo(long paramLong);
  
  @Metadata(k=3, mv={1, 7, 1}, xi=48)
  public static final class DefaultImpls
  {
    public static boolean hasNotPassedNow(TimeMark paramTimeMark)
    {
      return Duration.isNegative-impl(paramTimeMark.elapsedNow-UwyO8pc());
    }
    
    public static boolean hasPassedNow(TimeMark paramTimeMark)
    {
      return Duration.isNegative-impl(paramTimeMark.elapsedNow-UwyO8pc()) ^ true;
    }
    
    public static TimeMark minus-LRDsOJo(TimeMark paramTimeMark, long paramLong)
    {
      return paramTimeMark.plus-LRDsOJo(Duration.unaryMinus-UwyO8pc(paramLong));
    }
    
    public static TimeMark plus-LRDsOJo(TimeMark paramTimeMark, long paramLong)
    {
      return (TimeMark)new AdjustedTimeMark(paramTimeMark, paramLong, null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/TimeMark.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */