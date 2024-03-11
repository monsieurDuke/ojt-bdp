package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;

@Metadata(d1={"\000,\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\021\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\002\b\003\n\002\020\002\n\002\b\007\b\020\030\000*\b\b\000\020\001*\0020\0022\0020\002B\005¢\006\002\020\003J\023\020\r\032\0020\0162\006\020\017\032\0028\000¢\006\002\020\020J\006\020\021\032\0020\016J\b\020\022\032\0020\016H\002J\r\020\023\032\004\030\0018\000¢\006\002\020\024R\030\020\004\032\n\022\006\022\004\030\0010\0020\005X\016¢\006\004\n\002\020\006R\016\020\007\032\0020\bX\016¢\006\002\n\000R\021\020\t\032\0020\n8F¢\006\006\032\004\b\t\020\013R\016\020\f\032\0020\bX\016¢\006\002\n\000¨\006\025"}, d2={"Lkotlinx/coroutines/internal/ArrayQueue;", "T", "", "()V", "elements", "", "[Ljava/lang/Object;", "head", "", "isEmpty", "", "()Z", "tail", "addLast", "", "element", "(Ljava/lang/Object;)V", "clear", "ensureCapacity", "removeFirstOrNull", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class ArrayQueue<T>
{
  private Object[] elements = new Object[16];
  private int head;
  private int tail;
  
  private final void ensureCapacity()
  {
    Object[] arrayOfObject2 = this.elements;
    int k = arrayOfObject2.length;
    Object[] arrayOfObject1 = new Object[k << 1];
    ArraysKt.copyInto$default(arrayOfObject2, arrayOfObject1, 0, this.head, 0, 10, null);
    arrayOfObject2 = this.elements;
    int j = arrayOfObject2.length;
    int i = this.head;
    ArraysKt.copyInto$default(arrayOfObject2, arrayOfObject1, j - i, 0, i, 4, null);
    this.elements = arrayOfObject1;
    this.head = 0;
    this.tail = k;
  }
  
  public final void addLast(T paramT)
  {
    Object[] arrayOfObject = this.elements;
    int i = this.tail;
    arrayOfObject[i] = paramT;
    i = arrayOfObject.length - 1 & i + 1;
    this.tail = i;
    if (i == this.head) {
      ensureCapacity();
    }
  }
  
  public final void clear()
  {
    this.head = 0;
    this.tail = 0;
    this.elements = new Object[this.elements.length];
  }
  
  public final boolean isEmpty()
  {
    boolean bool;
    if (this.head == this.tail) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final T removeFirstOrNull()
  {
    int i = this.head;
    if (i == this.tail) {
      return null;
    }
    Object[] arrayOfObject = this.elements;
    Object localObject = arrayOfObject[i];
    arrayOfObject[i] = null;
    this.head = (i + 1 & arrayOfObject.length - 1);
    if (localObject != null) {
      return (T)localObject;
    }
    throw new NullPointerException("null cannot be cast to non-null type T of kotlinx.coroutines.internal.ArrayQueue");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */