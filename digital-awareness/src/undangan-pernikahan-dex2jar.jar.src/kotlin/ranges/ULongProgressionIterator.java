package kotlin.ranges;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000 \n\002\030\002\n\002\020(\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\004\n\002\020\013\n\002\b\004\b\003\030\0002\b\022\004\022\0020\0020\001B \022\006\020\003\032\0020\002\022\006\020\004\032\0020\002\022\006\020\005\032\0020\006ø\001\000¢\006\002\020\007J\t\020\n\032\0020\013H\002J\026\020\f\032\0020\002H\002ø\001\001ø\001\000¢\006\004\b\r\020\016R\026\020\b\032\0020\002X\004ø\001\000ø\001\001¢\006\004\n\002\020\tR\016\020\n\032\0020\013X\016¢\006\002\n\000R\026\020\f\032\0020\002X\016ø\001\000ø\001\001¢\006\004\n\002\020\tR\026\020\005\032\0020\002X\004ø\001\000ø\001\001¢\006\004\n\002\020\tø\001\000\002\b\n\002\b\031\n\002\b!¨\006\017"}, d2={"Lkotlin/ranges/ULongProgressionIterator;", "", "Lkotlin/ULong;", "first", "last", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "finalElement", "J", "hasNext", "", "next", "next-s-VKNKU", "()J", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class ULongProgressionIterator
  implements Iterator<ULong>, KMappedMarker
{
  private final long finalElement;
  private boolean hasNext;
  private long next;
  private final long step;
  
  private ULongProgressionIterator(long paramLong1, long paramLong2, long paramLong3)
  {
    this.finalElement = paramLong2;
    boolean bool = true;
    if (paramLong3 > 0L) {
      if (UnsignedKt.ulongCompare(paramLong1, paramLong2) > 0) {
        break label41;
      }
    } else {
      if (UnsignedKt.ulongCompare(paramLong1, paramLong2) >= 0) {
        break label44;
      }
    }
    label41:
    bool = false;
    label44:
    this.hasNext = bool;
    this.step = ULong.constructor-impl(paramLong3);
    if (!this.hasNext) {
      paramLong1 = paramLong2;
    }
    this.next = paramLong1;
  }
  
  public boolean hasNext()
  {
    return this.hasNext;
  }
  
  public long next-s-VKNKU()
  {
    long l = this.next;
    if (l == this.finalElement)
    {
      if (this.hasNext) {
        this.hasNext = false;
      } else {
        throw new NoSuchElementException();
      }
    }
    else {
      this.next = ULong.constructor-impl(this.next + this.step);
    }
    return l;
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/ULongProgressionIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */