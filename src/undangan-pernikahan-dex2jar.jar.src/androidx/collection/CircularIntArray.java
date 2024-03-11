package androidx.collection;

public final class CircularIntArray
{
  private int mCapacityBitmask;
  private int[] mElements;
  private int mHead;
  private int mTail;
  
  public CircularIntArray()
  {
    this(8);
  }
  
  public CircularIntArray(int paramInt)
  {
    if (paramInt >= 1)
    {
      if (paramInt <= 1073741824)
      {
        if (Integer.bitCount(paramInt) != 1) {
          paramInt = Integer.highestOneBit(paramInt - 1) << 1;
        }
        this.mCapacityBitmask = (paramInt - 1);
        this.mElements = new int[paramInt];
        return;
      }
      throw new IllegalArgumentException("capacity must be <= 2^30");
    }
    throw new IllegalArgumentException("capacity must be >= 1");
  }
  
  private void doubleCapacity()
  {
    int[] arrayOfInt2 = this.mElements;
    int j = arrayOfInt2.length;
    int k = this.mHead;
    int m = j - k;
    int i = j << 1;
    if (i >= 0)
    {
      int[] arrayOfInt1 = new int[i];
      System.arraycopy(arrayOfInt2, k, arrayOfInt1, 0, m);
      System.arraycopy(this.mElements, 0, arrayOfInt1, m, this.mHead);
      this.mElements = arrayOfInt1;
      this.mHead = 0;
      this.mTail = j;
      this.mCapacityBitmask = (i - 1);
      return;
    }
    throw new RuntimeException("Max array capacity exceeded");
  }
  
  public void addFirst(int paramInt)
  {
    int i = this.mHead - 1 & this.mCapacityBitmask;
    this.mHead = i;
    this.mElements[i] = paramInt;
    if (i == this.mTail) {
      doubleCapacity();
    }
  }
  
  public void addLast(int paramInt)
  {
    int[] arrayOfInt = this.mElements;
    int i = this.mTail;
    arrayOfInt[i] = paramInt;
    paramInt = this.mCapacityBitmask & i + 1;
    this.mTail = paramInt;
    if (paramInt == this.mHead) {
      doubleCapacity();
    }
  }
  
  public void clear()
  {
    this.mTail = this.mHead;
  }
  
  public int get(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < size())) {
      return this.mElements[(this.mHead + paramInt & this.mCapacityBitmask)];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int getFirst()
  {
    int i = this.mHead;
    if (i != this.mTail) {
      return this.mElements[i];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int getLast()
  {
    int i = this.mHead;
    int j = this.mTail;
    if (i != j) {
      return this.mElements[(j - 1 & this.mCapacityBitmask)];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.mHead == this.mTail) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int popFirst()
  {
    int i = this.mHead;
    if (i != this.mTail)
    {
      int j = this.mElements[i];
      this.mHead = (i + 1 & this.mCapacityBitmask);
      return j;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int popLast()
  {
    int j = this.mHead;
    int i = this.mTail;
    if (j != i)
    {
      i = this.mCapacityBitmask & i - 1;
      j = this.mElements[i];
      this.mTail = i;
      return j;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void removeFromEnd(int paramInt)
  {
    if (paramInt <= 0) {
      return;
    }
    if (paramInt <= size())
    {
      this.mTail = (this.mTail - paramInt & this.mCapacityBitmask);
      return;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void removeFromStart(int paramInt)
  {
    if (paramInt <= 0) {
      return;
    }
    if (paramInt <= size())
    {
      this.mHead = (this.mHead + paramInt & this.mCapacityBitmask);
      return;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int size()
  {
    return this.mTail - this.mHead & this.mCapacityBitmask;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/collection/CircularIntArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */