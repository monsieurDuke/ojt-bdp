package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DebugKt;

@Metadata(d1={"\0004\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\013\n\002\b\006\n\002\020\t\n\000\n\002\030\002\n\002\b\n\n\002\030\002\n\000\n\002\020 \n\002\b\026\b\000\030\000 /*\b\b\000\020\002*\0020\0012\0020\001:\002/0B\027\022\006\020\004\032\0020\003\022\006\020\006\032\0020\005¢\006\004\b\007\020\bJ\025\020\n\032\0020\0032\006\020\t\032\0028\000¢\006\004\b\n\020\013J'\020\017\032\022\022\004\022\0028\0000\000j\b\022\004\022\0028\000`\0162\006\020\r\032\0020\fH\002¢\006\004\b\017\020\020J'\020\021\032\022\022\004\022\0028\0000\000j\b\022\004\022\0028\000`\0162\006\020\r\032\0020\fH\002¢\006\004\b\021\020\020J\r\020\022\032\0020\005¢\006\004\b\022\020\023J3\020\025\032\026\022\004\022\0028\000\030\0010\000j\n\022\004\022\0028\000\030\001`\0162\006\020\024\032\0020\0032\006\020\t\032\0028\000H\002¢\006\004\b\025\020\026J\r\020\027\032\0020\005¢\006\004\b\027\020\023J-\020\034\032\b\022\004\022\0028\0010\033\"\004\b\001\020\0302\022\020\032\032\016\022\004\022\0028\000\022\004\022\0028\0010\031¢\006\004\b\034\020\035J\017\020\036\032\0020\fH\002¢\006\004\b\036\020\037J\023\020 \032\b\022\004\022\0028\0000\000¢\006\004\b \020!J\017\020\"\032\004\030\0010\001¢\006\004\b\"\020#J3\020&\032\026\022\004\022\0028\000\030\0010\000j\n\022\004\022\0028\000\030\001`\0162\006\020$\032\0020\0032\006\020%\032\0020\003H\002¢\006\004\b&\020'R\024\020\004\032\0020\0038\002X\004¢\006\006\n\004\b\004\020(R\021\020)\032\0020\0058F¢\006\006\032\004\b)\020\023R\024\020*\032\0020\0038\002X\004¢\006\006\n\004\b*\020(R\024\020\006\032\0020\0058\002X\004¢\006\006\n\004\b\006\020+R\021\020.\032\0020\0038F¢\006\006\032\004\b,\020-¨\0061"}, d2={"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "", "E", "", "capacity", "", "singleConsumer", "<init>", "(IZ)V", "element", "addLast", "(Ljava/lang/Object;)I", "", "state", "Lkotlinx/coroutines/internal/Core;", "allocateNextCopy", "(J)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "allocateOrGetNextCopy", "close", "()Z", "index", "fillPlaceholder", "(ILjava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "isClosed", "R", "Lkotlin/Function1;", "transform", "", "map", "(Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "markFrozen", "()J", "next", "()Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "removeFirstOrNull", "()Ljava/lang/Object;", "oldHead", "newHead", "removeSlowPath", "(II)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "I", "isEmpty", "mask", "Z", "getSize", "()I", "size", "Companion", "Placeholder", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class LockFreeTaskQueueCore<E>
{
  public static final int ADD_CLOSED = 2;
  public static final int ADD_FROZEN = 1;
  public static final int ADD_SUCCESS = 0;
  public static final int CAPACITY_BITS = 30;
  public static final long CLOSED_MASK = 2305843009213693952L;
  public static final int CLOSED_SHIFT = 61;
  public static final Companion Companion = new Companion(null);
  public static final long FROZEN_MASK = 1152921504606846976L;
  public static final int FROZEN_SHIFT = 60;
  public static final long HEAD_MASK = 1073741823L;
  public static final int HEAD_SHIFT = 0;
  public static final int INITIAL_CAPACITY = 8;
  public static final int MAX_CAPACITY_MASK = 1073741823;
  public static final int MIN_ADD_SPIN_CAPACITY = 1024;
  public static final Symbol REMOVE_FROZEN = new Symbol("REMOVE_FROZEN");
  public static final long TAIL_MASK = 1152921503533105152L;
  public static final int TAIL_SHIFT = 30;
  private static final AtomicReferenceFieldUpdater _next$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeTaskQueueCore.class, Object.class, "_next");
  private static final AtomicLongFieldUpdater _state$FU = AtomicLongFieldUpdater.newUpdater(LockFreeTaskQueueCore.class, "_state");
  private volatile Object _next;
  private volatile long _state;
  private AtomicReferenceArray array;
  private final int capacity;
  private final int mask;
  private final boolean singleConsumer;
  
  public LockFreeTaskQueueCore(int paramInt, boolean paramBoolean)
  {
    this.capacity = paramInt;
    this.singleConsumer = paramBoolean;
    int k = paramInt - 1;
    this.mask = k;
    this._next = null;
    this._state = 0L;
    this.array = new AtomicReferenceArray(paramInt);
    int j = 0;
    int i;
    if (k <= 1073741823) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      i = j;
      if ((k & paramInt) == 0) {
        i = 1;
      }
      if (i != 0) {
        return;
      }
      throw new IllegalStateException("Check failed.".toString());
    }
    throw new IllegalStateException("Check failed.".toString());
  }
  
  private final LockFreeTaskQueueCore<E> allocateNextCopy(long paramLong)
  {
    LockFreeTaskQueueCore localLockFreeTaskQueueCore = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
    Object localObject1 = Companion;
    int i = (int)((0x3FFFFFFF & paramLong) >> 0);
    int j = (int)((0xFFFFFFFC0000000 & paramLong) >> 30);
    for (;;)
    {
      int k = this.mask;
      if ((i & k) == (j & k)) {
        break;
      }
      Object localObject2 = this.array.get(k & i);
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new Placeholder(i);
      }
      localLockFreeTaskQueueCore.array.set(localLockFreeTaskQueueCore.mask & i, localObject1);
      i++;
    }
    localLockFreeTaskQueueCore._state = Companion.wo(paramLong, 1152921504606846976L);
    return localLockFreeTaskQueueCore;
  }
  
  private final LockFreeTaskQueueCore<E> allocateOrGetNextCopy(long paramLong)
  {
    for (;;)
    {
      LockFreeTaskQueueCore localLockFreeTaskQueueCore = (LockFreeTaskQueueCore)this._next;
      if (localLockFreeTaskQueueCore != null) {
        return localLockFreeTaskQueueCore;
      }
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, null, allocateNextCopy(paramLong));
    }
  }
  
  private final LockFreeTaskQueueCore<E> fillPlaceholder(int paramInt, E paramE)
  {
    Object localObject = this.array.get(this.mask & paramInt);
    if (((localObject instanceof Placeholder)) && (((Placeholder)localObject).index == paramInt))
    {
      this.array.set(this.mask & paramInt, paramE);
      return this;
    }
    return null;
  }
  
  private final long markFrozen()
  {
    for (;;)
    {
      long l2 = this._state;
      if ((l2 & 0x1000000000000000) != 0L) {
        return l2;
      }
      long l1 = l2 | 0x1000000000000000;
      if (_state$FU.compareAndSet(this, l2, l1)) {
        return l1;
      }
    }
  }
  
  private final LockFreeTaskQueueCore<E> removeSlowPath(int paramInt1, int paramInt2)
  {
    LockFreeTaskQueueCore localLockFreeTaskQueueCore = this;
    for (;;)
    {
      long l = localLockFreeTaskQueueCore._state;
      Companion localCompanion = Companion;
      int i = 0;
      int j = (int)((0x3FFFFFFF & l) >> 0);
      int k = (int)((0xFFFFFFFC0000000 & l) >> 30);
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (j == paramInt1) {
          i = 1;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      if ((l & 0x1000000000000000) != 0L) {
        return next();
      }
      if (_state$FU.compareAndSet(this, l, Companion.updateHead(l, paramInt2)))
      {
        this.array.set(j & this.mask, null);
        return null;
      }
    }
  }
  
  public final int addLast(E paramE)
  {
    label209:
    for (;;)
    {
      long l = this._state;
      if ((0x3000000000000000 & l) != 0L) {
        return Companion.addFailReason(l);
      }
      Object localObject = Companion;
      int i = (int)((l & 0x3FFFFFFF) >> 0);
      int j = (int)((l & 0xFFFFFFFC0000000) >> 30);
      int k = this.mask;
      if ((j + 2 & k) == (i & k)) {
        return 1;
      }
      if ((!this.singleConsumer) && (this.array.get(j & k) != null))
      {
        k = this.capacity;
        if ((k >= 1024) && ((0x3FFFFFFF & j - i) <= k >> 1)) {
          break label209;
        }
        return 1;
      }
      else if (_state$FU.compareAndSet(this, l, Companion.updateTail(l, j + 1 & 0x3FFFFFFF)))
      {
        this.array.set(j & k, paramE);
        localObject = this;
        for (;;)
        {
          if ((((LockFreeTaskQueueCore)localObject)._state & 0x1000000000000000) != 0L)
          {
            localObject = ((LockFreeTaskQueueCore)localObject).next().fillPlaceholder(j, paramE);
            if (localObject != null) {}
          }
          else
          {
            return 0;
          }
        }
      }
    }
  }
  
  public final boolean close()
  {
    for (;;)
    {
      long l = this._state;
      if ((l & 0x2000000000000000) != 0L) {
        return true;
      }
      if ((0x1000000000000000 & l) != 0L) {
        return false;
      }
      if (_state$FU.compareAndSet(this, l, l | 0x2000000000000000)) {
        return true;
      }
    }
  }
  
  public final int getSize()
  {
    Companion localCompanion = Companion;
    long l = this._state;
    int i = (int)((0x3FFFFFFF & l) >> 0);
    return (int)((0xFFFFFFFC0000000 & l) >> 30) - i & 0x3FFFFFFF;
  }
  
  public final boolean isClosed()
  {
    boolean bool;
    if ((this._state & 0x2000000000000000) != 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isEmpty()
  {
    Companion localCompanion = Companion;
    long l = this._state;
    boolean bool = false;
    if ((int)((0x3FFFFFFF & l) >> 0) == (int)((0xFFFFFFFC0000000 & l) >> 30)) {
      bool = true;
    }
    return bool;
  }
  
  public final <R> List<R> map(Function1<? super E, ? extends R> paramFunction1)
  {
    ArrayList localArrayList = new ArrayList(this.capacity);
    Object localObject = Companion;
    long l = this._state;
    int i = (int)((0x3FFFFFFF & l) >> 0);
    int j = (int)((0xFFFFFFFC0000000 & l) >> 30);
    for (;;)
    {
      int k = this.mask;
      if ((i & k) == (j & k)) {
        break;
      }
      localObject = this.array.get(k & i);
      if ((localObject != null) && (!(localObject instanceof Placeholder))) {
        localArrayList.add(paramFunction1.invoke(localObject));
      }
      i++;
    }
    return (List)localArrayList;
  }
  
  public final LockFreeTaskQueueCore<E> next()
  {
    return allocateOrGetNextCopy(markFrozen());
  }
  
  public final Object removeFirstOrNull()
  {
    LockFreeTaskQueueCore localLockFreeTaskQueueCore = this;
    int m = 0;
    Object localObject;
    int i;
    int j;
    do
    {
      long l = localLockFreeTaskQueueCore._state;
      if ((0x1000000000000000 & l) != 0L) {
        return REMOVE_FROZEN;
      }
      localObject = Companion;
      i = (int)((0x3FFFFFFF & l) >> 0);
      j = (int)((0xFFFFFFFC0000000 & l) >> 30);
      int k = this.mask;
      if ((j & k) == (i & k)) {
        return null;
      }
      localObject = this.array.get(k & i);
      if (localObject == null)
      {
        if (this.singleConsumer) {
          return null;
        }
        break;
      }
      if ((localObject instanceof Placeholder)) {
        return null;
      }
      j = 0x3FFFFFFF & i + 1;
      if (_state$FU.compareAndSet(this, l, Companion.updateHead(l, j)))
      {
        this.array.set(this.mask & i, null);
        return localObject;
      }
    } while (!this.singleConsumer);
    localLockFreeTaskQueueCore = this;
    for (;;)
    {
      localLockFreeTaskQueueCore = localLockFreeTaskQueueCore.removeSlowPath(i, j);
      if (localLockFreeTaskQueueCore == null) {
        return localObject;
      }
    }
  }
  
  @Metadata(d1={"\0000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020\t\n\002\b\t\n\002\030\002\n\002\b\n\n\002\030\002\n\002\030\002\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\n\020\026\032\0020\004*\0020\tJ\022\020\027\032\0020\t*\0020\t2\006\020\030\032\0020\004J\022\020\031\032\0020\t*\0020\t2\006\020\032\032\0020\004JP\020\033\032\002H\034\"\004\b\001\020\034*\0020\t26\020\035\0322\022\023\022\0210\004¢\006\f\b\037\022\b\b \022\004\b\b(!\022\023\022\0210\004¢\006\f\b\037\022\b\b \022\004\b\b(\"\022\004\022\002H\0340\036H\b¢\006\002\020#J\025\020$\032\0020\t*\0020\t2\006\020%\032\0020\tH\004R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000R\016\020\b\032\0020\tXT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\016\020\013\032\0020\tXT¢\006\002\n\000R\016\020\f\032\0020\004XT¢\006\002\n\000R\016\020\r\032\0020\tXT¢\006\002\n\000R\016\020\016\032\0020\004XT¢\006\002\n\000R\016\020\017\032\0020\004XT¢\006\002\n\000R\016\020\020\032\0020\004XT¢\006\002\n\000R\016\020\021\032\0020\004XT¢\006\002\n\000R\020\020\022\032\0020\0238\006X\004¢\006\002\n\000R\016\020\024\032\0020\tXT¢\006\002\n\000R\016\020\025\032\0020\004XT¢\006\002\n\000¨\006&"}, d2={"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Companion;", "", "()V", "ADD_CLOSED", "", "ADD_FROZEN", "ADD_SUCCESS", "CAPACITY_BITS", "CLOSED_MASK", "", "CLOSED_SHIFT", "FROZEN_MASK", "FROZEN_SHIFT", "HEAD_MASK", "HEAD_SHIFT", "INITIAL_CAPACITY", "MAX_CAPACITY_MASK", "MIN_ADD_SPIN_CAPACITY", "REMOVE_FROZEN", "Lkotlinx/coroutines/internal/Symbol;", "TAIL_MASK", "TAIL_SHIFT", "addFailReason", "updateHead", "newHead", "updateTail", "newTail", "withState", "T", "block", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "head", "tail", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "wo", "other", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Companion
  {
    public final int addFailReason(long paramLong)
    {
      int i;
      if ((0x2000000000000000 & paramLong) != 0L) {
        i = 2;
      } else {
        i = 1;
      }
      return i;
    }
    
    public final long updateHead(long paramLong, int paramInt)
    {
      return wo(paramLong, 1073741823L) | paramInt << 0;
    }
    
    public final long updateTail(long paramLong, int paramInt)
    {
      return wo(paramLong, 1152921503533105152L) | paramInt << 30;
    }
    
    public final <T> T withState(long paramLong, Function2<? super Integer, ? super Integer, ? extends T> paramFunction2)
    {
      return (T)paramFunction2.invoke(Integer.valueOf((int)((0x3FFFFFFF & paramLong) >> 0)), Integer.valueOf((int)((0xFFFFFFFC0000000 & paramLong) >> 30)));
    }
    
    public final long wo(long paramLong1, long paramLong2)
    {
      return (paramLong2 ^ 0xFFFFFFFFFFFFFFFF) & paramLong1;
    }
  }
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\002\b\002\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006\005"}, d2={"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Placeholder;", "", "index", "", "(I)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Placeholder
  {
    public final int index;
    
    public Placeholder(int paramInt)
    {
      this.index = paramInt;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LockFreeTaskQueueCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */