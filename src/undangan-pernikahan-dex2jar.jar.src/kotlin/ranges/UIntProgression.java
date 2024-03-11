package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.markers.KMappedMarker;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0004\n\002\030\002\n\002\020\034\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\007\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020(\n\000\n\002\020\016\n\002\b\002\b\027\030\000 \0272\b\022\004\022\0020\0020\001:\001\027B\"\b\000\022\006\020\003\032\0020\002\022\006\020\004\032\0020\002\022\006\020\005\032\0020\006ø\001\000¢\006\002\020\007J\023\020\r\032\0020\0162\b\020\017\032\004\030\0010\020H\002J\b\020\021\032\0020\006H\026J\b\020\022\032\0020\016H\026J\022\020\023\032\b\022\004\022\0020\0020\024H\002ø\001\000J\b\020\025\032\0020\026H\026R\023\020\b\032\0020\002ø\001\000ø\001\001¢\006\004\n\002\020\tR\023\020\n\032\0020\002ø\001\000ø\001\001¢\006\004\n\002\020\tR\021\020\005\032\0020\006¢\006\b\n\000\032\004\b\013\020\fø\001\000\002\b\n\002\b\031\n\002\b!¨\006\030"}, d2={"Lkotlin/ranges/UIntProgression;", "", "Lkotlin/UInt;", "start", "endInclusive", "step", "", "(IIILkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "I", "last", "getStep", "()I", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public class UIntProgression
  implements Iterable<UInt>, KMappedMarker
{
  public static final Companion Companion = new Companion(null);
  private final int first;
  private final int last;
  private final int step;
  
  private UIntProgression(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 != 0)
    {
      if (paramInt3 != Integer.MIN_VALUE)
      {
        this.first = paramInt1;
        this.last = UProgressionUtilKt.getProgressionLastElement-Nkh28Cs(paramInt1, paramInt2, paramInt3);
        this.step = paramInt3;
        return;
      }
      throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
    }
    throw new IllegalArgumentException("Step must be non-zero.");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof UIntProgression)) && (((isEmpty()) && (((UIntProgression)paramObject).isEmpty())) || ((this.first == ((UIntProgression)paramObject).first) && (this.last == ((UIntProgression)paramObject).last) && (this.step == ((UIntProgression)paramObject).step)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final int getFirst-pVg5ArA()
  {
    return this.first;
  }
  
  public final int getLast-pVg5ArA()
  {
    return this.last;
  }
  
  public final int getStep()
  {
    return this.step;
  }
  
  public int hashCode()
  {
    int i;
    if (isEmpty()) {
      i = -1;
    } else {
      i = (this.first * 31 + this.last) * 31 + this.step;
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    int i = this.step;
    boolean bool = true;
    if (i > 0) {
      if (UnsignedKt.uintCompare(this.first, this.last) <= 0) {
        break label45;
      }
    } else if (UnsignedKt.uintCompare(this.first, this.last) < 0) {
      return bool;
    }
    label45:
    bool = false;
    return bool;
  }
  
  public final Iterator<UInt> iterator()
  {
    return (Iterator)new UIntProgressionIterator(this.first, this.last, this.step, null);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder;
    Object localObject;
    int i;
    if (this.step > 0)
    {
      localStringBuilder = new StringBuilder();
      localObject = UInt.toString-impl(this.first);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localStringBuilder = localStringBuilder.append(localObject).append("..");
      localObject = UInt.toString-impl(this.last);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localObject = localStringBuilder.append(localObject).append(" step ");
      i = this.step;
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localObject = UInt.toString-impl(this.first);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localStringBuilder = localStringBuilder.append(localObject).append(" downTo ");
      localObject = UInt.toString-impl(this.last);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localObject = localStringBuilder.append(localObject).append(" step ");
      i = -this.step;
    }
    return i;
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J(\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\b\032\0020\tø\001\000¢\006\004\b\n\020\013\002\004\n\002\b\031¨\006\f"}, d2={"Lkotlin/ranges/UIntProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/UIntProgression;", "rangeStart", "Lkotlin/UInt;", "rangeEnd", "step", "", "fromClosedRange-Nkh28Cs", "(III)Lkotlin/ranges/UIntProgression;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final UIntProgression fromClosedRange-Nkh28Cs(int paramInt1, int paramInt2, int paramInt3)
    {
      return new UIntProgression(paramInt1, paramInt2, paramInt3, null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/UIntProgression.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */