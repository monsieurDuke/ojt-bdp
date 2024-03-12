package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.LongIterator;

@Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\b\005\n\002\020\013\n\002\b\005\b\000\030\0002\0020\001B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003¢\006\002\020\006J\t\020\b\032\0020\tH\002J\b\020\r\032\0020\003H\026R\016\020\007\032\0020\003X\004¢\006\002\n\000R\016\020\b\032\0020\tX\016¢\006\002\n\000R\016\020\n\032\0020\003X\016¢\006\002\n\000R\021\020\005\032\0020\003¢\006\b\n\000\032\004\b\013\020\f¨\006\016"}, d2={"Lkotlin/ranges/LongProgressionIterator;", "Lkotlin/collections/LongIterator;", "first", "", "last", "step", "(JJJ)V", "finalElement", "hasNext", "", "next", "getStep", "()J", "nextLong", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class LongProgressionIterator
  extends LongIterator
{
  private final long finalElement;
  private boolean hasNext;
  private long next;
  private final long step;
  
  public LongProgressionIterator(long paramLong1, long paramLong2, long paramLong3)
  {
    this.step = paramLong3;
    this.finalElement = paramLong2;
    boolean bool = true;
    if (paramLong3 > 0L) {
      if (paramLong1 > paramLong2) {
        break label43;
      }
    } else {
      if (paramLong1 >= paramLong2) {
        break label46;
      }
    }
    label43:
    bool = false;
    label46:
    this.hasNext = bool;
    if (!bool) {
      paramLong1 = paramLong2;
    }
    this.next = paramLong1;
  }
  
  public final long getStep()
  {
    return this.step;
  }
  
  public boolean hasNext()
  {
    return this.hasNext;
  }
  
  public long nextLong()
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
      this.next += this.step;
    }
    return l;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/LongProgressionIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */