package kotlin.ranges;

import kotlin.Metadata;

@Metadata(d1={"\000,\n\002\030\002\n\002\030\002\n\002\020\006\n\002\b\t\n\002\020\013\n\002\b\003\n\002\020\000\n\000\n\002\020\b\n\002\b\005\n\002\020\016\n\000\b\002\030\0002\b\022\004\022\0020\0020\001B\025\022\006\020\003\032\0020\002\022\006\020\004\032\0020\002¢\006\002\020\005J\021\020\013\032\0020\f2\006\020\r\032\0020\002H\002J\023\020\016\032\0020\f2\b\020\017\032\004\030\0010\020H\002J\b\020\021\032\0020\022H\026J\b\020\023\032\0020\fH\026J\030\020\024\032\0020\f2\006\020\025\032\0020\0022\006\020\026\032\0020\002H\026J\b\020\027\032\0020\030H\026R\016\020\006\032\0020\002X\004¢\006\002\n\000R\016\020\007\032\0020\002X\004¢\006\002\n\000R\024\020\004\032\0020\0028VX\004¢\006\006\032\004\b\b\020\tR\024\020\003\032\0020\0028VX\004¢\006\006\032\004\b\n\020\t¨\006\031"}, d2={"Lkotlin/ranges/ClosedDoubleRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "start", "endInclusive", "(DD)V", "_endInclusive", "_start", "getEndInclusive", "()Ljava/lang/Double;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "lessThanOrEquals", "a", "b", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class ClosedDoubleRange
  implements ClosedFloatingPointRange<Double>
{
  private final double _endInclusive;
  private final double _start;
  
  public ClosedDoubleRange(double paramDouble1, double paramDouble2)
  {
    this._start = paramDouble1;
    this._endInclusive = paramDouble2;
  }
  
  public boolean contains(double paramDouble)
  {
    boolean bool;
    if ((paramDouble >= this._start) && (paramDouble <= this._endInclusive)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = paramObject instanceof ClosedDoubleRange;
    boolean bool1 = true;
    if (bool2) {
      if ((!isEmpty()) || (!((ClosedDoubleRange)paramObject).isEmpty()))
      {
        int i;
        if (this._start == ((ClosedDoubleRange)paramObject)._start) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          break label85;
        }
        if (this._endInclusive == ((ClosedDoubleRange)paramObject)._endInclusive) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          return bool1;
        }
      }
    }
    label85:
    bool1 = false;
    return bool1;
  }
  
  public Double getEndInclusive()
  {
    return Double.valueOf(this._endInclusive);
  }
  
  public Double getStart()
  {
    return Double.valueOf(this._start);
  }
  
  public int hashCode()
  {
    int i;
    if (isEmpty()) {
      i = -1;
    } else {
      i = Double.valueOf(this._start).hashCode() * 31 + Double.valueOf(this._endInclusive).hashCode();
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this._start > this._endInclusive) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean lessThanOrEquals(double paramDouble1, double paramDouble2)
  {
    boolean bool;
    if (paramDouble1 <= paramDouble2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    return this._start + ".." + this._endInclusive;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/ClosedDoubleRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */