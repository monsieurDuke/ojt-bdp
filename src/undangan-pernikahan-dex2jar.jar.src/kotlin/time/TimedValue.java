package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000*\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\016\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\b\b\030\000*\004\b\000\020\0012\0020\002B\030\022\006\020\003\032\0028\000\022\006\020\004\032\0020\005ø\001\000¢\006\002\020\006J\016\020\r\032\0028\000HÆ\003¢\006\002\020\013J\026\020\016\032\0020\005HÆ\003ø\001\001ø\001\000¢\006\004\b\017\020\bJ-\020\020\032\b\022\004\022\0028\0000\0002\b\b\002\020\003\032\0028\0002\b\b\002\020\004\032\0020\005HÆ\001ø\001\000¢\006\004\b\021\020\022J\023\020\023\032\0020\0242\b\020\025\032\004\030\0010\002HÖ\003J\t\020\026\032\0020\027HÖ\001J\t\020\030\032\0020\031HÖ\001R\031\020\004\032\0020\005ø\001\000ø\001\001¢\006\n\n\002\020\t\032\004\b\007\020\bR\023\020\003\032\0028\000¢\006\n\n\002\020\f\032\004\b\n\020\013\002\b\n\002\b\031\n\002\b!¨\006\032"}, d2={"Lkotlin/time/TimedValue;", "T", "", "value", "duration", "Lkotlin/time/Duration;", "(Ljava/lang/Object;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getDuration-UwyO8pc", "()J", "J", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "component2-UwyO8pc", "copy", "copy-RFiDyg4", "(Ljava/lang/Object;J)Lkotlin/time/TimedValue;", "equals", "", "other", "hashCode", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class TimedValue<T>
{
  private final long duration;
  private final T value;
  
  private TimedValue(T paramT, long paramLong)
  {
    this.value = paramT;
    this.duration = paramLong;
  }
  
  public final T component1()
  {
    return (T)this.value;
  }
  
  public final long component2-UwyO8pc()
  {
    return this.duration;
  }
  
  public final TimedValue<T> copy-RFiDyg4(T paramT, long paramLong)
  {
    return new TimedValue(paramT, paramLong, null);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TimedValue)) {
      return false;
    }
    paramObject = (TimedValue)paramObject;
    if (!Intrinsics.areEqual(this.value, ((TimedValue)paramObject).value)) {
      return false;
    }
    return Duration.equals-impl0(this.duration, ((TimedValue)paramObject).duration);
  }
  
  public final long getDuration-UwyO8pc()
  {
    return this.duration;
  }
  
  public final T getValue()
  {
    return (T)this.value;
  }
  
  public int hashCode()
  {
    Object localObject = this.value;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    return i * 31 + Duration.hashCode-impl(this.duration);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("TimedValue(value=").append(this.value).append(", duration=");
    String str = Duration.toString-impl(this.duration);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/TimedValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */