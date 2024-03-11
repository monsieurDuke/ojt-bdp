package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CharIterator;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\020\f\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\005\b\000\030\0002\0020\001B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\006¢\006\002\020\007J\t\020\t\032\0020\nH\002J\b\020\016\032\0020\003H\026R\016\020\b\032\0020\006X\004¢\006\002\n\000R\016\020\t\032\0020\nX\016¢\006\002\n\000R\016\020\013\032\0020\006X\016¢\006\002\n\000R\021\020\005\032\0020\006¢\006\b\n\000\032\004\b\f\020\r¨\006\017"}, d2={"Lkotlin/ranges/CharProgressionIterator;", "Lkotlin/collections/CharIterator;", "first", "", "last", "step", "", "(CCI)V", "finalElement", "hasNext", "", "next", "getStep", "()I", "nextChar", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class CharProgressionIterator
  extends CharIterator
{
  private final int finalElement;
  private boolean hasNext;
  private int next;
  private final int step;
  
  public CharProgressionIterator(char paramChar1, char paramChar2, int paramInt)
  {
    this.step = paramInt;
    this.finalElement = paramChar2;
    boolean bool = true;
    int i = Intrinsics.compare(paramChar1, paramChar2);
    if (paramInt > 0) {
      if (i > 0) {
        break label44;
      }
    } else {
      if (i >= 0) {
        break label47;
      }
    }
    label44:
    bool = false;
    label47:
    this.hasNext = bool;
    if (!bool) {
      paramChar1 = paramChar2;
    }
    this.next = paramChar1;
  }
  
  public final int getStep()
  {
    return this.step;
  }
  
  public boolean hasNext()
  {
    return this.hasNext;
  }
  
  public char nextChar()
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
      this.next += this.step;
    }
    return (char)i;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ranges/CharProgressionIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */