package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CharIterator;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\020\031\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\020\f\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\t\020\007\032\0020\bH\002J\b\020\t\032\0020\nH\026R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000¨\006\013"}, d2={"Lkotlin/jvm/internal/ArrayCharIterator;", "Lkotlin/collections/CharIterator;", "array", "", "([C)V", "index", "", "hasNext", "", "nextChar", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class ArrayCharIterator
  extends CharIterator
{
  private final char[] array;
  private int index;
  
  public ArrayCharIterator(char[] paramArrayOfChar)
  {
    this.array = paramArrayOfChar;
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
  
  public char nextChar()
  {
    try
    {
      char[] arrayOfChar = this.array;
      int i = this.index;
      this.index = (i + 1);
      char c = arrayOfChar[i];
      return c;
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      this.index -= 1;
      throw new NoSuchElementException(localArrayIndexOutOfBoundsException.getMessage());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/ArrayCharIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */