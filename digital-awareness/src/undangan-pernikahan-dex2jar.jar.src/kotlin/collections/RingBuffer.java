package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(d1={"\000>\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\t\n\002\020\002\n\002\b\b\n\002\020\013\n\000\n\002\020(\n\002\b\b\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\0060\003j\002`\004B\017\b\026\022\006\020\005\032\0020\006¢\006\002\020\007B\035\022\016\020\b\032\n\022\006\022\004\030\0010\n0\t\022\006\020\013\032\0020\006¢\006\002\020\fJ\023\020\023\032\0020\0242\006\020\025\032\0028\000¢\006\002\020\026J\024\020\027\032\b\022\004\022\0028\0000\0002\006\020\030\032\0020\006J\026\020\031\032\0028\0002\006\020\032\032\0020\006H\002¢\006\002\020\033J\006\020\034\032\0020\035J\017\020\036\032\b\022\004\022\0028\0000\037H\002J\016\020 \032\0020\0242\006\020!\032\0020\006J\025\020\"\032\n\022\006\022\004\030\0010\n0\tH\024¢\006\002\020#J'\020\"\032\b\022\004\022\002H\0010\t\"\004\b\001\020\0012\f\020$\032\b\022\004\022\002H\0010\tH\024¢\006\002\020%J\025\020&\032\0020\006*\0020\0062\006\020!\032\0020\006H\bR\030\020\b\032\n\022\006\022\004\030\0010\n0\tX\004¢\006\004\n\002\020\rR\016\020\005\032\0020\006X\004¢\006\002\n\000R\036\020\017\032\0020\0062\006\020\016\032\0020\006@RX\016¢\006\b\n\000\032\004\b\020\020\021R\016\020\022\032\0020\006X\016¢\006\002\n\000¨\006'"}, d2={"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "filledSize", "([Ljava/lang/Object;I)V", "[Ljava/lang/Object;", "<set-?>", "size", "getSize", "()I", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "expanded", "maxCapacity", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "forward", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class RingBuffer<T>
  extends AbstractList<T>
  implements RandomAccess
{
  private final Object[] buffer;
  private final int capacity;
  private int size;
  private int startIndex;
  
  public RingBuffer(int paramInt)
  {
    this(new Object[paramInt], 0);
  }
  
  public RingBuffer(Object[] paramArrayOfObject, int paramInt)
  {
    this.buffer = paramArrayOfObject;
    int j = 1;
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt <= paramArrayOfObject.length) {
        i = j;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this.capacity = paramArrayOfObject.length;
        this.size = paramInt;
        return;
      }
      throw new IllegalArgumentException(("ring buffer filled size: " + paramInt + " cannot be larger than the buffer size: " + paramArrayOfObject.length).toString());
    }
    throw new IllegalArgumentException(("ring buffer filled size should not be negative but it is " + paramInt).toString());
  }
  
  private final int forward(int paramInt1, int paramInt2)
  {
    return (paramInt1 + paramInt2) % access$getCapacity$p(this);
  }
  
  public final void add(T paramT)
  {
    if (!isFull())
    {
      this.buffer[((this.startIndex + size()) % access$getCapacity$p(this))] = paramT;
      this.size = (size() + 1);
      return;
    }
    throw new IllegalStateException("ring buffer is full");
  }
  
  public final RingBuffer<T> expanded(int paramInt)
  {
    int i = this.capacity;
    paramInt = RangesKt.coerceAtMost(i + (i >> 1) + 1, paramInt);
    Object[] arrayOfObject;
    if (this.startIndex == 0)
    {
      arrayOfObject = Arrays.copyOf(this.buffer, paramInt);
      Intrinsics.checkNotNullExpressionValue(arrayOfObject, "copyOf(this, newSize)");
    }
    else
    {
      arrayOfObject = toArray(new Object[paramInt]);
    }
    return new RingBuffer(arrayOfObject, size());
  }
  
  public T get(int paramInt)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    return (T)this.buffer[((this.startIndex + paramInt) % access$getCapacity$p(this))];
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public final boolean isFull()
  {
    boolean bool;
    if (size() == this.capacity) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Iterator<T> iterator()
  {
    (Iterator)new AbstractIterator()
    {
      private int count;
      private int index;
      final RingBuffer<T> this$0;
      
      protected void computeNext()
      {
        if (this.count == 0)
        {
          done();
        }
        else
        {
          setNext(RingBuffer.access$getBuffer$p(this.this$0)[this.index]);
          RingBuffer localRingBuffer = this.this$0;
          this.index = ((this.index + 1) % RingBuffer.access$getCapacity$p(localRingBuffer));
          this.count -= 1;
        }
      }
    };
  }
  
  public final void removeFirst(int paramInt)
  {
    int j = 1;
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt <= size()) {
        i = j;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt > 0)
        {
          i = this.startIndex;
          j = (i + paramInt) % access$getCapacity$p(this);
          if (i > j)
          {
            ArraysKt.fill(this.buffer, null, i, this.capacity);
            ArraysKt.fill(this.buffer, null, 0, j);
          }
          else
          {
            ArraysKt.fill(this.buffer, null, i, j);
          }
          this.startIndex = j;
          this.size = (size() - paramInt);
        }
        return;
      }
      throw new IllegalArgumentException(("n shouldn't be greater than the buffer size: n = " + paramInt + ", size = " + size()).toString());
    }
    throw new IllegalArgumentException(("n shouldn't be negative but it is " + paramInt).toString());
  }
  
  public Object[] toArray()
  {
    return toArray(new Object[size()]);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "array");
    if (paramArrayOfT.length < size())
    {
      paramArrayOfT = Arrays.copyOf(paramArrayOfT, size());
      Intrinsics.checkNotNullExpressionValue(paramArrayOfT, "copyOf(this, newSize)");
    }
    int k = size();
    int i = 0;
    for (int j = this.startIndex; (i < k) && (j < this.capacity); j++)
    {
      paramArrayOfT[i] = this.buffer[j];
      i++;
    }
    for (j = 0; i < k; j++)
    {
      paramArrayOfT[i] = this.buffer[j];
      i++;
    }
    if (paramArrayOfT.length > size()) {
      paramArrayOfT[size()] = null;
    }
    Intrinsics.checkNotNull(paramArrayOfT, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.RingBuffer.toArray>");
    return paramArrayOfT;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/RingBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */