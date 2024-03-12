package kotlinx.coroutines.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

@Metadata(d1={"\000<\n\002\030\002\n\002\030\002\n\002\020\017\n\002\b\004\n\002\020\002\n\002\b\003\n\002\030\002\n\002\020\013\n\002\b\007\n\002\020\021\n\002\b\004\n\002\020\b\n\002\b\030\n\002\020\000\n\002\030\002\b\027\030\000*\022\b\000\020\003*\0020\001*\b\022\004\022\0028\0000\0022\00602j\002`3B\007¢\006\004\b\004\020\005J\027\020\b\032\0020\0072\006\020\006\032\0028\000H\001¢\006\004\b\b\020\tJ\025\020\n\032\0020\0072\006\020\006\032\0028\000¢\006\004\b\n\020\tJ.\020\016\032\0020\f2\006\020\006\032\0028\0002\024\020\r\032\020\022\006\022\004\030\0018\000\022\004\022\0020\f0\013H\b¢\006\004\b\016\020\017J\r\020\020\032\0020\007¢\006\004\b\020\020\005J\021\020\021\032\004\030\0018\000H\001¢\006\004\b\021\020\022J\017\020\023\032\004\030\0018\000¢\006\004\b\023\020\022J\027\020\025\032\n\022\006\022\004\030\0018\0000\024H\002¢\006\004\b\025\020\026J\025\020\027\032\0020\f2\006\020\006\032\0028\000¢\006\004\b\027\020\030J\027\020\033\032\0028\0002\006\020\032\032\0020\031H\001¢\006\004\b\033\020\034J&\020\036\032\004\030\0018\0002\022\020\035\032\016\022\004\022\0028\000\022\004\022\0020\f0\013H\b¢\006\004\b\036\020\037J\017\020 \032\004\030\0018\000¢\006\004\b \020\022J\030\020\"\032\0020\0072\006\020!\032\0020\031H\020¢\006\004\b\"\020#J\030\020$\032\0020\0072\006\020!\032\0020\031H\020¢\006\004\b$\020#J\037\020&\032\0020\0072\006\020!\032\0020\0312\006\020%\032\0020\031H\002¢\006\004\b&\020'R \020(\032\f\022\006\022\004\030\0018\000\030\0010\0248\002@\002X\016¢\006\006\n\004\b(\020)R\021\020*\032\0020\f8F¢\006\006\032\004\b*\020+R$\0200\032\0020\0312\006\020,\032\0020\0318F@BX\016¢\006\f\032\004\b-\020.\"\004\b/\020#¨\0061"}, d2={"Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "", "T", "<init>", "()V", "node", "", "addImpl", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V", "addLast", "Lkotlin/Function1;", "", "cond", "addLastIf", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;Lkotlin/jvm/functions/Function1;)Z", "clear", "firstImpl", "()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "peek", "", "realloc", "()[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "remove", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)Z", "", "index", "removeAtImpl", "(I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "predicate", "removeFirstIf", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "removeFirstOrNull", "i", "siftDownFrom", "(I)V", "siftUpFrom", "j", "swap", "(II)V", "a", "[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "isEmpty", "()Z", "value", "getSize", "()I", "setSize", "size", "kotlinx-coroutines-core", "", "Lkotlinx/coroutines/internal/SynchronizedObject;"}, k=1, mv={1, 6, 0}, xi=48)
public class ThreadSafeHeap<T extends ThreadSafeHeapNode,  extends Comparable<? super T>>
{
  private volatile int _size = 0;
  private T[] a;
  
  private final T[] realloc()
  {
    Object localObject = this.a;
    if (localObject == null)
    {
      localObject = new ThreadSafeHeapNode[4];
      this.a = ((ThreadSafeHeapNode[])localObject);
    }
    else if (getSize() >= localObject.length)
    {
      localObject = Arrays.copyOf((Object[])localObject, getSize() * 2);
      Intrinsics.checkNotNullExpressionValue(localObject, "copyOf(this, newSize)");
      this.a = ((ThreadSafeHeapNode[])localObject);
      localObject = (ThreadSafeHeapNode[])localObject;
    }
    return (T[])localObject;
  }
  
  private final void setSize(int paramInt)
  {
    this._size = paramInt;
  }
  
  private final void siftDownFrom(int paramInt)
  {
    for (int i = paramInt;; i = paramInt)
    {
      int j = i * 2 + 1;
      if (j >= getSize()) {
        return;
      }
      Object localObject1 = this.a;
      Intrinsics.checkNotNull(localObject1);
      paramInt = j;
      if (j + 1 < getSize())
      {
        localObject2 = localObject1[(j + 1)];
        Intrinsics.checkNotNull(localObject2);
        Comparable localComparable = (Comparable)localObject2;
        localObject2 = localObject1[j];
        Intrinsics.checkNotNull(localObject2);
        paramInt = j;
        if (localComparable.compareTo(localObject2) < 0) {
          paramInt = j + 1;
        }
      }
      Object localObject2 = localObject1[i];
      Intrinsics.checkNotNull(localObject2);
      localObject2 = (Comparable)localObject2;
      localObject1 = localObject1[paramInt];
      Intrinsics.checkNotNull(localObject1);
      if (((Comparable)localObject2).compareTo(localObject1) <= 0) {
        return;
      }
      swap(i, paramInt);
    }
  }
  
  private final void siftUpFrom(int paramInt)
  {
    for (;;)
    {
      if (paramInt <= 0) {
        return;
      }
      Object localObject2 = this.a;
      Intrinsics.checkNotNull(localObject2);
      int i = (paramInt - 1) / 2;
      Object localObject1 = localObject2[i];
      Intrinsics.checkNotNull(localObject1);
      localObject1 = (Comparable)localObject1;
      localObject2 = localObject2[paramInt];
      Intrinsics.checkNotNull(localObject2);
      if (((Comparable)localObject1).compareTo(localObject2) <= 0) {
        return;
      }
      swap(paramInt, i);
      paramInt = i;
    }
  }
  
  private final void swap(int paramInt1, int paramInt2)
  {
    ThreadSafeHeapNode[] arrayOfThreadSafeHeapNode = this.a;
    Intrinsics.checkNotNull(arrayOfThreadSafeHeapNode);
    ThreadSafeHeapNode localThreadSafeHeapNode1 = arrayOfThreadSafeHeapNode[paramInt2];
    Intrinsics.checkNotNull(localThreadSafeHeapNode1);
    ThreadSafeHeapNode localThreadSafeHeapNode2 = arrayOfThreadSafeHeapNode[paramInt1];
    Intrinsics.checkNotNull(localThreadSafeHeapNode2);
    arrayOfThreadSafeHeapNode[paramInt1] = localThreadSafeHeapNode1;
    arrayOfThreadSafeHeapNode[paramInt2] = localThreadSafeHeapNode2;
    localThreadSafeHeapNode1.setIndex(paramInt1);
    localThreadSafeHeapNode2.setIndex(paramInt2);
  }
  
  public final void addImpl(T paramT)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (paramT.getHeap() == null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    paramT.setHeap(this);
    ThreadSafeHeapNode[] arrayOfThreadSafeHeapNode = realloc();
    int i = getSize();
    setSize(i + 1);
    arrayOfThreadSafeHeapNode[i] = paramT;
    paramT.setIndex(i);
    siftUpFrom(i);
  }
  
  public final void addLast(T paramT)
  {
    try
    {
      addImpl(paramT);
      paramT = Unit.INSTANCE;
      return;
    }
    finally
    {
      paramT = finally;
      throw paramT;
    }
  }
  
  public final boolean addLastIf(T paramT, Function1<? super T, Boolean> paramFunction1)
  {
    try
    {
      boolean bool;
      if (((Boolean)paramFunction1.invoke(firstImpl())).booleanValue())
      {
        addImpl(paramT);
        bool = true;
      }
      else
      {
        bool = false;
      }
      return bool;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public final void clear()
  {
    try
    {
      Object localObject1 = this.a;
      if (localObject1 != null) {
        ArraysKt.fill$default((Object[])localObject1, null, 0, 0, 6, null);
      }
      this._size = 0;
      localObject1 = Unit.INSTANCE;
      return;
    }
    finally {}
  }
  
  public final T firstImpl()
  {
    Object localObject = this.a;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = localObject[0];
    }
    return (T)localObject;
  }
  
  public final int getSize()
  {
    return this._size;
  }
  
  public final boolean isEmpty()
  {
    boolean bool;
    if (getSize() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final T peek()
  {
    try
    {
      ThreadSafeHeapNode localThreadSafeHeapNode = firstImpl();
      return localThreadSafeHeapNode;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean remove(T paramT)
  {
    try
    {
      ThreadSafeHeap localThreadSafeHeap = paramT.getHeap();
      boolean bool = true;
      int i = 0;
      if (localThreadSafeHeap == null)
      {
        bool = false;
      }
      else
      {
        int j = paramT.getIndex();
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          if (j >= 0) {
            i = 1;
          }
          if (i == 0)
          {
            paramT = new java/lang/AssertionError;
            paramT.<init>();
            throw paramT;
          }
        }
        removeAtImpl(j);
      }
      return bool;
    }
    finally {}
  }
  
  public final T removeAtImpl(int paramInt)
  {
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int j = 0;
    int i;
    if (bool)
    {
      if (getSize() > 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    ThreadSafeHeapNode[] arrayOfThreadSafeHeapNode = this.a;
    Intrinsics.checkNotNull(arrayOfThreadSafeHeapNode);
    setSize(getSize() - 1);
    if (paramInt < getSize())
    {
      swap(paramInt, getSize());
      i = (paramInt - 1) / 2;
      if (paramInt > 0)
      {
        localObject = arrayOfThreadSafeHeapNode[paramInt];
        Intrinsics.checkNotNull(localObject);
        localObject = (Comparable)localObject;
        ThreadSafeHeapNode localThreadSafeHeapNode = arrayOfThreadSafeHeapNode[i];
        Intrinsics.checkNotNull(localThreadSafeHeapNode);
        if (((Comparable)localObject).compareTo(localThreadSafeHeapNode) < 0)
        {
          swap(paramInt, i);
          siftUpFrom(i);
          break label149;
        }
      }
      siftDownFrom(paramInt);
    }
    label149:
    Object localObject = arrayOfThreadSafeHeapNode[getSize()];
    Intrinsics.checkNotNull(localObject);
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      paramInt = j;
      if (((ThreadSafeHeapNode)localObject).getHeap() == this) {
        paramInt = 1;
      }
      if (paramInt == 0) {
        throw new AssertionError();
      }
    }
    ((ThreadSafeHeapNode)localObject).setHeap(null);
    ((ThreadSafeHeapNode)localObject).setIndex(-1);
    arrayOfThreadSafeHeapNode[getSize()] = null;
    return (T)localObject;
  }
  
  public final T removeFirstIf(Function1<? super T, Boolean> paramFunction1)
  {
    try
    {
      ThreadSafeHeapNode localThreadSafeHeapNode = firstImpl();
      Object localObject = null;
      if (localThreadSafeHeapNode == null)
      {
        InlineMarker.finallyStart(2);
        InlineMarker.finallyEnd(2);
        return null;
      }
      if (((Boolean)paramFunction1.invoke(localThreadSafeHeapNode)).booleanValue())
      {
        paramFunction1 = removeAtImpl(0);
      }
      else
      {
        paramFunction1 = (ThreadSafeHeapNode)null;
        paramFunction1 = (Function1<? super T, Boolean>)localObject;
      }
      return paramFunction1;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public final T removeFirstOrNull()
  {
    try
    {
      ThreadSafeHeapNode localThreadSafeHeapNode1;
      if (getSize() > 0)
      {
        localThreadSafeHeapNode1 = removeAtImpl(0);
      }
      else
      {
        localThreadSafeHeapNode1 = null;
        ThreadSafeHeapNode localThreadSafeHeapNode2 = (ThreadSafeHeapNode)null;
      }
      return localThreadSafeHeapNode1;
    }
    finally {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ThreadSafeHeap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */