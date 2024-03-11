package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\bg\030\000 \0042\0020\001:\002\004\005J\b\020\002\032\0020\003H&¨\006\006"}, d2={"Lkotlin/time/TimeSource;", "", "markNow", "Lkotlin/time/TimeMark;", "Companion", "Monotonic", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface TimeSource
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract TimeMark markNow();
  
  @Metadata(d1={"\000\f\n\002\030\002\n\002\020\000\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002¨\006\003"}, d2={"Lkotlin/time/TimeSource$Companion;", "", "()V", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    static final Companion $$INSTANCE = new Companion();
  }
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\016\n\002\b\002\bÆ\002\030\0002\0020\001:\001\tB\007\b\002¢\006\002\020\002J\025\020\003\032\0020\004H\026ø\001\000ø\001\001¢\006\004\b\005\020\006J\b\020\007\032\0020\bH\026\002\b\n\002\b!\n\002\b\031¨\006\n"}, d2={"Lkotlin/time/TimeSource$Monotonic;", "Lkotlin/time/TimeSource;", "()V", "markNow", "Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "markNow-z9LOYto", "()J", "toString", "", "ValueTimeMark", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Monotonic
    implements TimeSource
  {
    public static final Monotonic INSTANCE = new Monotonic();
    
    public long markNow-z9LOYto()
    {
      return MonotonicTimeSource.INSTANCE.markNow-z9LOYto();
    }
    
    public String toString()
    {
      return MonotonicTimeSource.INSTANCE.toString();
    }
    
    @Metadata(d1={"\000<\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\000\n\002\b\b\n\002\020\b\n\002\b\t\n\002\020\016\n\002\b\003\b@\030\0002\0020\001B\030\b\000\022\n\020\002\032\0060\003j\002`\004ø\001\000¢\006\004\b\005\020\006J\025\020\007\032\0020\bH\026ø\001\001ø\001\000¢\006\004\b\t\020\006J\032\020\n\032\0020\0132\b\020\f\032\004\030\0010\rHÖ\003¢\006\004\b\016\020\017J\017\020\020\032\0020\013H\026¢\006\004\b\021\020\022J\017\020\023\032\0020\013H\026¢\006\004\b\024\020\022J\020\020\025\032\0020\026HÖ\001¢\006\004\b\027\020\030J\033\020\031\032\0020\0002\006\020\032\032\0020\bH\002ø\001\000¢\006\004\b\033\020\034J\033\020\035\032\0020\0002\006\020\032\032\0020\bH\002ø\001\000¢\006\004\b\036\020\034J\020\020\037\032\0020 HÖ\001¢\006\004\b!\020\"R\022\020\002\032\0060\003j\002`\004X\004¢\006\002\n\000\001\002\001\0060\003j\002`\004ø\001\000\002\b\n\002\b\031\n\002\b!¨\006#"}, d2={"Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "Lkotlin/time/TimeMark;", "reading", "", "Lkotlin/time/ValueTimeMarkReading;", "constructor-impl", "(J)J", "elapsedNow", "Lkotlin/time/Duration;", "elapsedNow-UwyO8pc", "equals", "", "other", "", "equals-impl", "(JLjava/lang/Object;)Z", "hasNotPassedNow", "hasNotPassedNow-impl", "(J)Z", "hasPassedNow", "hasPassedNow-impl", "hashCode", "", "hashCode-impl", "(J)I", "minus", "duration", "minus-LRDsOJo", "(JJ)J", "plus", "plus-LRDsOJo", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
    @JvmInline
    public static final class ValueTimeMark
      implements TimeMark
    {
      private final long reading;
      
      public static long constructor-impl(long paramLong)
      {
        return paramLong;
      }
      
      public static long elapsedNow-UwyO8pc(long paramLong)
      {
        return MonotonicTimeSource.INSTANCE.elapsedFrom-6eNON_k(paramLong);
      }
      
      public static boolean equals-impl(long paramLong, Object paramObject)
      {
        if (!(paramObject instanceof ValueTimeMark)) {
          return false;
        }
        return paramLong == ((ValueTimeMark)paramObject).unbox-impl();
      }
      
      public static final boolean equals-impl0(long paramLong1, long paramLong2)
      {
        boolean bool;
        if (paramLong1 == paramLong2) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public static boolean hasNotPassedNow-impl(long paramLong)
      {
        return Duration.isNegative-impl(elapsedNow-UwyO8pc(paramLong));
      }
      
      public static boolean hasPassedNow-impl(long paramLong)
      {
        return Duration.isNegative-impl(elapsedNow-UwyO8pc(paramLong)) ^ true;
      }
      
      public static int hashCode-impl(long paramLong)
      {
        return (int)(paramLong >>> 32 ^ paramLong);
      }
      
      public static long minus-LRDsOJo(long paramLong1, long paramLong2)
      {
        return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(paramLong1, Duration.unaryMinus-UwyO8pc(paramLong2));
      }
      
      public static long plus-LRDsOJo(long paramLong1, long paramLong2)
      {
        return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(paramLong1, paramLong2);
      }
      
      public static String toString-impl(long paramLong)
      {
        return "ValueTimeMark(reading=" + paramLong + ')';
      }
      
      public long elapsedNow-UwyO8pc()
      {
        return elapsedNow-UwyO8pc(this.reading);
      }
      
      public boolean equals(Object paramObject)
      {
        return equals-impl(this.reading, paramObject);
      }
      
      public boolean hasNotPassedNow()
      {
        return hasNotPassedNow-impl(this.reading);
      }
      
      public boolean hasPassedNow()
      {
        return hasPassedNow-impl(this.reading);
      }
      
      public int hashCode()
      {
        return hashCode-impl(this.reading);
      }
      
      public long minus-LRDsOJo(long paramLong)
      {
        return minus-LRDsOJo(this.reading, paramLong);
      }
      
      public long plus-LRDsOJo(long paramLong)
      {
        return plus-LRDsOJo(this.reading, paramLong);
      }
      
      public String toString()
      {
        String str = toString-impl(this.reading);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        return str;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/TimeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */