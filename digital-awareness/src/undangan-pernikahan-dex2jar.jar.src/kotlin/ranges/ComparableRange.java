package kotlin.ranges;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000*\n\002\030\002\n\000\n\002\020\017\n\002\030\002\n\002\b\b\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\b\022\030\000*\016\b\000\020\001*\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\025\022\006\020\004\032\0028\000\022\006\020\005\032\0028\000¢\006\002\020\006J\023\020\013\032\0020\f2\b\020\r\032\004\030\0010\016H\002J\b\020\017\032\0020\020H\026J\b\020\021\032\0020\022H\026R\026\020\005\032\0028\000X\004¢\006\n\n\002\020\t\032\004\b\007\020\bR\026\020\004\032\0028\000X\004¢\006\n\n\002\020\t\032\004\b\n\020\b¨\006\023"}, d2={"Lkotlin/ranges/ComparableRange;", "T", "", "Lkotlin/ranges/ClosedRange;", "start", "endInclusive", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)V", "getEndInclusive", "()Ljava/lang/Comparable;", "Ljava/lang/Comparable;", "getStart", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
class ComparableRange<T extends Comparable<? super T>>
  implements ClosedRange<T>
{
  private final T endInclusive;
  private final T start;
  
  public ComparableRange(T paramT1, T paramT2)
  {
    this.start = paramT1;
    this.endInclusive = paramT2;
  }
  
  public boolean contains(T paramT)
  {
    return ClosedRange.DefaultImpls.contains(this, paramT);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ComparableRange)) && (((isEmpty()) && (((ComparableRange)paramObject).isEmpty())) || ((Intrinsics.areEqual(getStart(), ((ComparableRange)paramObject).getStart())) && (Intrinsics.areEqual(getEndInclusive(), ((ComparableRange)paramObject).getEndInclusive()))))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public T getEndInclusive()
  {
    return this.endInclusive;
  }
  
  public T getStart()
  {
    return this.start;
  }
  
  public int hashCode()
  {
    int i;
    if (isEmpty()) {
      i = -1;
    } else {
      i = getStart().hashCode() * 31 + getEndInclusive().hashCode();
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    return ClosedRange.DefaultImpls.isEmpty(this);
  }
  
  public String toString()
  {
    return getStart() + ".." + getEndInclusive();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/ComparableRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */