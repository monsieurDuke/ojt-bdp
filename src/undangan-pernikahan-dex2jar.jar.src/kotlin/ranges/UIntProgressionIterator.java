package kotlin.ranges;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000 \n\002\030\002\n\002\020(\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\004\n\002\020\013\n\002\b\004\b\003\030\0002\b\022\004\022\0020\0020\001B \022\006\020\003\032\0020\002\022\006\020\004\032\0020\002\022\006\020\005\032\0020\006ø\001\000¢\006\002\020\007J\t\020\n\032\0020\013H\002J\026\020\f\032\0020\002H\002ø\001\001ø\001\000¢\006\004\b\r\020\016R\026\020\b\032\0020\002X\004ø\001\000ø\001\001¢\006\004\n\002\020\tR\016\020\n\032\0020\013X\016¢\006\002\n\000R\026\020\f\032\0020\002X\016ø\001\000ø\001\001¢\006\004\n\002\020\tR\026\020\005\032\0020\002X\004ø\001\000ø\001\001¢\006\004\n\002\020\tø\001\000\002\b\n\002\b\031\n\002\b!¨\006\017"}, d2={"Lkotlin/ranges/UIntProgressionIterator;", "", "Lkotlin/UInt;", "first", "last", "step", "", "(IIILkotlin/jvm/internal/DefaultConstructorMarker;)V", "finalElement", "I", "hasNext", "", "next", "next-pVg5ArA", "()I", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class UIntProgressionIterator
  implements Iterator<UInt>, KMappedMarker
{
  private final int finalElement;
  private boolean hasNext;
  private int next;
  private final int step;
  
  private UIntProgressionIterator(int paramInt1, int paramInt2, int paramInt3)
  {
    this.finalElement = paramInt2;
    boolean bool = true;
    int i = UnsignedKt.uintCompare(paramInt1, paramInt2);
    if (paramInt3 > 0) {
      if (i > 0) {
        break label39;
      }
    } else {
      if (i >= 0) {
        break label42;
      }
    }
    label39:
    bool = false;
    label42:
    this.hasNext = bool;
    this.step = UInt.constructor-impl(paramInt3);
    if (!this.hasNext) {
      paramInt1 = paramInt2;
    }
    this.next = paramInt1;
  }
  
  public boolean hasNext()
  {
    return this.hasNext;
  }
  
  public int next-pVg5ArA()
  {
    int i = this.next;
    if (i == this.finalElement)
    {
      if (this.hasNext) {
        this.hasNext = false;
      } else {
        throw new NoSuchElementException();
      }
    }
    else {
      this.next = UInt.constructor-impl(this.next + this.step);
    }
    return i;
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/UIntProgressionIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */