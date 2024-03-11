package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlin.ranges.RangesKt;

@Metadata(d1={"\000$\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\003\b\000\030\000*\004\b\000\020\0012\0020\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\006\020\b\032\0020\004J\030\020\t\032\004\030\0018\0002\006\020\n\032\0020\004H\002¢\006\002\020\013J\035\020\f\032\0020\r2\006\020\n\032\0020\0042\b\020\016\032\004\030\0018\000¢\006\002\020\017R\024\020\006\032\b\022\004\022\0028\0000\007X\016¢\006\002\n\000¨\006\020"}, d2={"Lkotlinx/coroutines/internal/ResizableAtomicArray;", "T", "", "initialLength", "", "(I)V", "array", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "currentLength", "get", "index", "(I)Ljava/lang/Object;", "setSynchronized", "", "value", "(ILjava/lang/Object;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ResizableAtomicArray<T>
{
  private volatile AtomicReferenceArray<T> array;
  
  public ResizableAtomicArray(int paramInt)
  {
    this.array = new AtomicReferenceArray(paramInt);
  }
  
  public final int currentLength()
  {
    return this.array.length();
  }
  
  public final T get(int paramInt)
  {
    Object localObject = this.array;
    if (paramInt < ((AtomicReferenceArray)localObject).length()) {
      localObject = ((AtomicReferenceArray)localObject).get(paramInt);
    } else {
      localObject = null;
    }
    return (T)localObject;
  }
  
  public final void setSynchronized(int paramInt, T paramT)
  {
    AtomicReferenceArray localAtomicReferenceArray2 = this.array;
    int k = localAtomicReferenceArray2.length();
    if (paramInt < k)
    {
      localAtomicReferenceArray2.set(paramInt, paramT);
    }
    else
    {
      AtomicReferenceArray localAtomicReferenceArray1 = new AtomicReferenceArray(RangesKt.coerceAtLeast(paramInt + 1, k * 2));
      int j;
      for (int i = 0; i < k; i = j)
      {
        j = i + 1;
        localAtomicReferenceArray1.set(i, localAtomicReferenceArray2.get(i));
      }
      localAtomicReferenceArray1.set(paramInt, paramT);
      this.array = localAtomicReferenceArray1;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ResizableAtomicArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */