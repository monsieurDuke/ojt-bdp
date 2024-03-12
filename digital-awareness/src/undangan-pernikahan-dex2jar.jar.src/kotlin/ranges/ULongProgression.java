package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.markers.KMappedMarker;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000:\n\002\030\002\n\002\020\034\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\007\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\020(\n\000\n\002\020\016\n\002\b\002\b\027\030\000 \0302\b\022\004\022\0020\0020\001:\001\030B\"\b\000\022\006\020\003\032\0020\002\022\006\020\004\032\0020\002\022\006\020\005\032\0020\006ø\001\000¢\006\002\020\007J\023\020\r\032\0020\0162\b\020\017\032\004\030\0010\020H\002J\b\020\021\032\0020\022H\026J\b\020\023\032\0020\016H\026J\022\020\024\032\b\022\004\022\0020\0020\025H\002ø\001\000J\b\020\026\032\0020\027H\026R\023\020\b\032\0020\002ø\001\000ø\001\001¢\006\004\n\002\020\tR\023\020\n\032\0020\002ø\001\000ø\001\001¢\006\004\n\002\020\tR\021\020\005\032\0020\006¢\006\b\n\000\032\004\b\013\020\fø\001\000\002\b\n\002\b\031\n\002\b!¨\006\031"}, d2={"Lkotlin/ranges/ULongProgression;", "", "Lkotlin/ULong;", "start", "endInclusive", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "J", "last", "getStep", "()J", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public class ULongProgression
  implements Iterable<ULong>, KMappedMarker
{
  public static final Companion Companion = new Companion(null);
  private final long first;
  private final long last;
  private final long step;
  
  private ULongProgression(long paramLong1, long paramLong2, long paramLong3)
  {
    if (paramLong3 != 0L)
    {
      if (paramLong3 != Long.MIN_VALUE)
      {
        this.first = paramLong1;
        this.last = UProgressionUtilKt.getProgressionLastElement-7ftBX0g(paramLong1, paramLong2, paramLong3);
        this.step = paramLong3;
        return;
      }
      throw new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
    }
    throw new IllegalArgumentException("Step must be non-zero.");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ULongProgression)) && (((isEmpty()) && (((ULongProgression)paramObject).isEmpty())) || ((this.first == ((ULongProgression)paramObject).first) && (this.last == ((ULongProgression)paramObject).last) && (this.step == ((ULongProgression)paramObject).step)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final long getFirst-s-VKNKU()
  {
    return this.first;
  }
  
  public final long getLast-s-VKNKU()
  {
    return this.last;
  }
  
  public final long getStep()
  {
    return this.step;
  }
  
  public int hashCode()
  {
    int i;
    if (isEmpty())
    {
      i = -1;
    }
    else
    {
      long l = this.first;
      i = (int)ULong.constructor-impl(l ^ ULong.constructor-impl(l >>> 32));
      l = this.last;
      int j = (int)ULong.constructor-impl(l ^ ULong.constructor-impl(l >>> 32));
      l = this.step;
      i = (i * 31 + j) * 31 + (int)(l >>> 32 ^ l);
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    long l3 = this.step;
    boolean bool = true;
    long l2 = this.first;
    long l1 = this.last;
    if (l3 > 0L) {
      if (UnsignedKt.ulongCompare(l2, l1) <= 0) {
        break label50;
      }
    } else if (UnsignedKt.ulongCompare(l2, l1) < 0) {
      return bool;
    }
    label50:
    bool = false;
    return bool;
  }
  
  public final Iterator<ULong> iterator()
  {
    return (Iterator)new ULongProgressionIterator(this.first, this.last, this.step, null);
  }
  
  public String toString()
  {
    Object localObject1;
    Object localObject2;
    long l;
    if (this.step > 0L)
    {
      localObject1 = new StringBuilder();
      localObject2 = ULong.toString-impl(this.first);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject1 = ((StringBuilder)localObject1).append(localObject2).append("..");
      localObject2 = ULong.toString-impl(this.last);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject1 = ((StringBuilder)localObject1).append(localObject2).append(" step ");
      l = this.step;
    }
    else
    {
      localObject2 = new StringBuilder();
      localObject1 = ULong.toString-impl(this.first);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localObject2 = ((StringBuilder)localObject2).append(localObject1).append(" downTo ");
      localObject1 = ULong.toString-impl(this.last);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localObject1 = ((StringBuilder)localObject2).append(localObject1).append(" step ");
      l = -this.step;
    }
    return l;
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J(\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\b\032\0020\tø\001\000¢\006\004\b\n\020\013\002\004\n\002\b\031¨\006\f"}, d2={"Lkotlin/ranges/ULongProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/ULongProgression;", "rangeStart", "Lkotlin/ULong;", "rangeEnd", "step", "", "fromClosedRange-7ftBX0g", "(JJJ)Lkotlin/ranges/ULongProgression;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final ULongProgression fromClosedRange-7ftBX0g(long paramLong1, long paramLong2, long paramLong3)
    {
      return new ULongProgression(paramLong1, paramLong2, paramLong3, null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/ULongProgression.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */