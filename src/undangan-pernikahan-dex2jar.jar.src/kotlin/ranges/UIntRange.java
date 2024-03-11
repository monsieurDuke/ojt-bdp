package kotlin.ranges;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UnsignedKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\005\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\020\016\n\002\b\002\b\007\030\000 \0272\0020\0012\b\022\004\022\0020\0030\002:\001\027B\030\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003ø\001\000¢\006\002\020\006J\033\020\n\032\0020\0132\006\020\f\032\0020\003H\002ø\001\000¢\006\004\b\r\020\016J\023\020\017\032\0020\0132\b\020\020\032\004\030\0010\021H\002J\b\020\022\032\0020\023H\026J\b\020\024\032\0020\013H\026J\b\020\025\032\0020\026H\026R\032\020\005\032\0020\0038VX\004ø\001\000ø\001\001¢\006\006\032\004\b\007\020\bR\032\020\004\032\0020\0038VX\004ø\001\000ø\001\001¢\006\006\032\004\b\t\020\bø\001\000\002\b\n\002\b\031\n\002\b!¨\006\030"}, d2={"Lkotlin/ranges/UIntRange;", "Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/UInt;", "start", "endInclusive", "(IILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive-pVg5ArA", "()I", "getStart-pVg5ArA", "contains", "", "value", "contains-WZ4Q5Ns", "(I)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class UIntRange
  extends UIntProgression
  implements ClosedRange<UInt>
{
  public static final Companion Companion = new Companion(null);
  private static final UIntRange EMPTY = new UIntRange(-1, 0, null);
  
  private UIntRange(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, 1, null);
  }
  
  public boolean contains-WZ4Q5Ns(int paramInt)
  {
    boolean bool;
    if ((UnsignedKt.uintCompare(getFirst-pVg5ArA(), paramInt) <= 0) && (UnsignedKt.uintCompare(paramInt, getLast-pVg5ArA()) <= 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof UIntRange)) && (((isEmpty()) && (((UIntRange)paramObject).isEmpty())) || ((getFirst-pVg5ArA() == ((UIntRange)paramObject).getFirst-pVg5ArA()) && (getLast-pVg5ArA() == ((UIntRange)paramObject).getLast-pVg5ArA())))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int getEndInclusive-pVg5ArA()
  {
    return getLast-pVg5ArA();
  }
  
  public int getStart-pVg5ArA()
  {
    return getFirst-pVg5ArA();
  }
  
  public int hashCode()
  {
    int i;
    if (isEmpty()) {
      i = -1;
    } else {
      i = getFirst-pVg5ArA() * 31 + getLast-pVg5ArA();
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (UnsignedKt.uintCompare(getFirst-pVg5ArA(), getLast-pVg5ArA()) > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    Object localObject2 = new StringBuilder();
    Object localObject1 = UInt.toString-impl(getFirst-pVg5ArA());
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    localObject1 = ((StringBuilder)localObject2).append(localObject1).append("..");
    localObject2 = UInt.toString-impl(getLast-pVg5ArA());
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    return (String)localObject2;
  }
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006¨\006\007"}, d2={"Lkotlin/ranges/UIntRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/UIntRange;", "getEMPTY", "()Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final UIntRange getEMPTY()
    {
      return UIntRange.access$getEMPTY$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/UIntRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */