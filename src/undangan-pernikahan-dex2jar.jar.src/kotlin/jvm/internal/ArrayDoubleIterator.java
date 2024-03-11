package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.DoubleIterator;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\020\023\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\020\006\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\t\020\007\032\0020\bH\002J\b\020\t\032\0020\nH\026R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000¨\006\013"}, d2={"Lkotlin/jvm/internal/ArrayDoubleIterator;", "Lkotlin/collections/DoubleIterator;", "array", "", "([D)V", "index", "", "hasNext", "", "nextDouble", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class ArrayDoubleIterator
  extends DoubleIterator
{
  private final double[] array;
  private int index;
  
  public ArrayDoubleIterator(double[] paramArrayOfDouble)
  {
    this.array = paramArrayOfDouble;
  }
  
  public boolean hasNext()
  {
    boolean bool;
    if (this.index < this.array.length) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public double nextDouble()
  {
    try
    {
      double[] arrayOfDouble = this.array;
      int i = this.index;
      this.index = (i + 1);
      double d = arrayOfDouble[i];
      return d;
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      this.index -= 1;
      throw new NoSuchElementException(localArrayIndexOutOfBoundsException.getMessage());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/ArrayDoubleIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */