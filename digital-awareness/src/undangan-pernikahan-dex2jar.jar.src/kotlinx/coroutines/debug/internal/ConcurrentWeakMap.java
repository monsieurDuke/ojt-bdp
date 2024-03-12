package kotlinx.coroutines.debug.internal;

import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap.Entry;
import kotlin.ranges.RangesKt;

@Metadata(d1={"\000B\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\016\n\002\020#\n\002\020'\n\002\b\005\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\b\000\030\000*\b\b\000\020\002*\0020\001*\b\b\001\020\003*\0020\0012\016\022\004\022\0028\000\022\004\022\0028\0010*:\003&'(B\021\022\b\b\002\020\005\032\0020\004¢\006\004\b\006\020\007J\033\020\013\032\0020\n2\n\020\t\032\006\022\002\b\0030\bH\002¢\006\004\b\013\020\fJ\017\020\r\032\0020\nH\026¢\006\004\b\r\020\016J\017\020\017\032\0020\nH\002¢\006\004\b\017\020\016J\032\020\021\032\004\030\0018\0012\006\020\020\032\0028\000H\002¢\006\004\b\021\020\022J!\020\024\032\004\030\0018\0012\006\020\020\032\0028\0002\006\020\023\032\0028\001H\026¢\006\004\b\024\020\025J#\020\026\032\004\030\0018\0012\006\020\020\032\0028\0002\b\020\023\032\004\030\0018\001H\002¢\006\004\b\026\020\025J\031\020\027\032\004\030\0018\0012\006\020\020\032\0028\000H\026¢\006\004\b\027\020\022J\r\020\030\032\0020\n¢\006\004\b\030\020\016R&\020\035\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\0320\0318VX\004¢\006\006\032\004\b\033\020\034R\032\020\037\032\b\022\004\022\0028\0000\0318VX\004¢\006\006\032\004\b\036\020\034R\024\020#\032\0020 8VX\004¢\006\006\032\004\b!\020\"R\034\020\005\032\n\022\004\022\0028\000\030\0010$8\002X\004¢\006\006\n\004\b\005\020%¨\006)"}, d2={"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "K", "V", "", "weakRefQueue", "<init>", "(Z)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "w", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "clear", "()V", "decrementSize", "key", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "value", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putSynchronized", "remove", "runWeakRefQueueCleaningLoopUntilInterrupted", "", "", "getEntries", "()Ljava/util/Set;", "entries", "getKeys", "keys", "", "getSize", "()I", "size", "Ljava/lang/ref/ReferenceQueue;", "Ljava/lang/ref/ReferenceQueue;", "Core", "Entry", "KeyValueSet", "kotlinx-coroutines-core", "Lkotlin/collections/AbstractMutableMap;"}, k=1, mv={1, 6, 0}, xi=48)
public final class ConcurrentWeakMap<K, V>
  extends AbstractMutableMap<K, V>
{
  private static final AtomicIntegerFieldUpdater _size$FU = AtomicIntegerFieldUpdater.newUpdater(ConcurrentWeakMap.class, "_size");
  private volatile int _size = 0;
  volatile Object core = new Core(16);
  private final ReferenceQueue<K> weakRefQueue;
  
  public ConcurrentWeakMap()
  {
    this(false, 1, null);
  }
  
  public ConcurrentWeakMap(boolean paramBoolean)
  {
    ReferenceQueue localReferenceQueue;
    if (paramBoolean) {
      localReferenceQueue = new ReferenceQueue();
    } else {
      localReferenceQueue = null;
    }
    this.weakRefQueue = localReferenceQueue;
  }
  
  private final void cleanWeakRef(HashedWeakRef<?> paramHashedWeakRef)
  {
    ((Core)this.core).cleanWeakRef(paramHashedWeakRef);
  }
  
  private final void decrementSize()
  {
    _size$FU.decrementAndGet(this);
  }
  
  /* Error */
  private final V putSynchronized(K paramK, V paramV)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 96	kotlinx/coroutines/debug/internal/ConcurrentWeakMap:core	Ljava/lang/Object;
    //   6: checkcast 7	kotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core
    //   9: astore_3
    //   10: aload_3
    //   11: aload_1
    //   12: aload_2
    //   13: aconst_null
    //   14: iconst_4
    //   15: aconst_null
    //   16: invokestatic 122	kotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core:putImpl$default	(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/coroutines/debug/internal/HashedWeakRef;ILjava/lang/Object;)Ljava/lang/Object;
    //   19: astore 5
    //   21: invokestatic 128	kotlinx/coroutines/debug/internal/ConcurrentWeakMapKt:access$getREHASH$p	()Lkotlinx/coroutines/internal/Symbol;
    //   24: astore 4
    //   26: aload 5
    //   28: aload 4
    //   30: if_acmpeq +8 -> 38
    //   33: aload_0
    //   34: monitorexit
    //   35: aload 5
    //   37: areturn
    //   38: aload_3
    //   39: invokevirtual 132	kotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core:rehash	()Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;
    //   42: astore_3
    //   43: aload_0
    //   44: aload_3
    //   45: putfield 96	kotlinx/coroutines/debug/internal/ConcurrentWeakMap:core	Ljava/lang/Object;
    //   48: goto -38 -> 10
    //   51: astore_1
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_1
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	ConcurrentWeakMap
    //   0	56	1	paramK	K
    //   0	56	2	paramV	V
    //   9	36	3	localCore	Core
    //   24	5	4	localSymbol	kotlinx.coroutines.internal.Symbol
    //   19	17	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	10	51	finally
    //   10	26	51	finally
    //   38	48	51	finally
  }
  
  public void clear()
  {
    Iterator localIterator = keySet().iterator();
    while (localIterator.hasNext()) {
      remove(localIterator.next());
    }
  }
  
  public V get(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return (V)((Core)this.core).getImpl(paramObject);
  }
  
  public Set<Map.Entry<K, V>> getEntries()
  {
    return (Set)new KeyValueSet((Function2)entries.1.INSTANCE);
  }
  
  public Set<K> getKeys()
  {
    return (Set)new KeyValueSet((Function2)keys.1.INSTANCE);
  }
  
  public int getSize()
  {
    return this._size;
  }
  
  public V put(K paramK, V paramV)
  {
    Object localObject2 = Core.putImpl$default((Core)this.core, paramK, paramV, null, 4, null);
    Object localObject1 = localObject2;
    if (localObject2 == ConcurrentWeakMapKt.access$getREHASH$p()) {
      localObject1 = putSynchronized(paramK, paramV);
    }
    if (localObject1 == null) {
      _size$FU.incrementAndGet(this);
    }
    return (V)localObject1;
  }
  
  public V remove(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    Object localObject2 = Core.putImpl$default((Core)this.core, paramObject, null, null, 4, null);
    Object localObject1 = localObject2;
    if (localObject2 == ConcurrentWeakMapKt.access$getREHASH$p()) {
      localObject1 = putSynchronized(paramObject, null);
    }
    if (localObject1 != null) {
      _size$FU.decrementAndGet(this);
    }
    return (V)localObject1;
  }
  
  public final void runWeakRefQueueCleaningLoopUntilInterrupted()
  {
    int i;
    if (this.weakRefQueue != null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      try
      {
        for (;;)
        {
          localObject = this.weakRefQueue.remove();
          if (localObject == null) {
            break;
          }
          cleanWeakRef((HashedWeakRef)localObject);
        }
        Object localObject = new java/lang/NullPointerException;
        ((NullPointerException)localObject).<init>("null cannot be cast to non-null type kotlinx.coroutines.debug.internal.HashedWeakRef<*>");
        throw ((Throwable)localObject);
      }
      catch (InterruptedException localInterruptedException)
      {
        Thread.currentThread().interrupt();
        return;
      }
    }
    throw new IllegalStateException("Must be created with weakRefQueue = true".toString());
  }
  
  @Metadata(d1={"\0008\n\002\030\002\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\t\n\002\030\002\n\000\n\002\020)\n\002\b\004\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\t\b\004\030\0002\0020\030:\001#B\017\022\006\020\002\032\0020\001¢\006\004\b\003\020\004J\031\020\b\032\0020\0072\n\020\006\032\006\022\002\b\0030\005¢\006\004\b\b\020\tJ\027\020\013\032\004\030\0018\0012\006\020\n\032\0028\000¢\006\004\b\013\020\fJ\027\020\016\032\0020\0012\006\020\r\032\0020\001H\002¢\006\004\b\016\020\017J3\020\024\032\b\022\004\022\0028\0020\023\"\004\b\002\020\0202\030\020\022\032\024\022\004\022\0028\000\022\004\022\0028\001\022\004\022\0028\0020\021¢\006\004\b\024\020\025J3\020\031\032\004\030\0010\0302\006\020\n\032\0028\0002\b\020\026\032\004\030\0018\0012\020\b\002\020\027\032\n\022\004\022\0028\000\030\0010\005¢\006\004\b\031\020\032J\035\020\034\032\0220\000R\016\022\004\022\0028\000\022\004\022\0028\0010\033¢\006\004\b\034\020\035J\027\020\036\032\0020\0072\006\020\016\032\0020\001H\002¢\006\004\b\036\020\037R\024\020\002\032\0020\0018\002X\004¢\006\006\n\004\b\002\020 R\024\020!\032\0020\0018\002X\004¢\006\006\n\004\b!\020 R\024\020\"\032\0020\0018\002X\004¢\006\006\n\004\b\"\020 ¨\006$"}, d2={"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "", "allocated", "<init>", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;I)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "weakRef", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "key", "getImpl", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "index", "(I)I", "E", "Lkotlin/Function2;", "factory", "", "keyValueIterator", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", "value", "weakKey0", "", "putImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/coroutines/debug/internal/HashedWeakRef;)Ljava/lang/Object;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "rehash", "()Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "removeCleanedAt", "(I)V", "I", "shift", "threshold", "KeyValueIterator", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class Core
  {
    private static final AtomicIntegerFieldUpdater load$FU = AtomicIntegerFieldUpdater.newUpdater(Core.class, "load");
    private final int allocated;
    AtomicReferenceArray keys;
    private volatile int load;
    private final int shift;
    private final int threshold;
    AtomicReferenceArray values;
    
    public Core()
    {
      int i;
      this.allocated = i;
      this.shift = (Integer.numberOfLeadingZeros(i) + 1);
      this.threshold = (i * 2 / 3);
      this.load = 0;
      this.keys = new AtomicReferenceArray(i);
      this.values = new AtomicReferenceArray(i);
    }
    
    private final int index(int paramInt)
    {
      return -1640531527 * paramInt >>> this.shift;
    }
    
    private final void removeCleanedAt(int paramInt)
    {
      for (;;)
      {
        Object localObject = this.values.get(paramInt);
        if (localObject == null) {
          return;
        }
        if ((localObject instanceof Marked)) {
          return;
        }
        if (ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0.m(this.values, paramInt, localObject, null))
        {
          ConcurrentWeakMap.access$decrementSize(ConcurrentWeakMap.this);
          return;
        }
      }
    }
    
    public final void cleanWeakRef(HashedWeakRef<?> paramHashedWeakRef)
    {
      int j;
      for (int i = index(paramHashedWeakRef.hash);; i = j - 1)
      {
        HashedWeakRef localHashedWeakRef = (HashedWeakRef)this.keys.get(i);
        if (localHashedWeakRef == null) {
          return;
        }
        if (localHashedWeakRef == paramHashedWeakRef)
        {
          removeCleanedAt(i);
          return;
        }
        j = i;
        if (i == 0) {
          j = this.allocated;
        }
      }
    }
    
    public final V getImpl(K paramK)
    {
      int j;
      for (int i = index(paramK.hashCode());; i = j - 1)
      {
        Object localObject = (HashedWeakRef)this.keys.get(i);
        if (localObject == null) {
          return null;
        }
        localObject = ((HashedWeakRef)localObject).get();
        if (Intrinsics.areEqual(paramK, localObject))
        {
          paramK = this.values.get(i);
          if ((paramK instanceof Marked)) {
            paramK = ((Marked)paramK).ref;
          }
          return paramK;
        }
        if (localObject == null) {
          removeCleanedAt(i);
        }
        j = i;
        if (i == 0) {
          j = this.allocated;
        }
      }
    }
    
    public final <E> Iterator<E> keyValueIterator(Function2<? super K, ? super V, ? extends E> paramFunction2)
    {
      return (Iterator)new KeyValueIterator(paramFunction2);
    }
    
    public final Object putImpl(K paramK, V paramV, HashedWeakRef<K> paramHashedWeakRef)
    {
      int i = index(paramK.hashCode());
      int j = 0;
      for (;;)
      {
        Object localObject = (HashedWeakRef)this.keys.get(i);
        if (localObject == null)
        {
          if (paramV == null) {
            return null;
          }
          k = j;
          if (j == 0) {
            for (;;)
            {
              j = this.load;
              if (j >= this.threshold) {
                return ConcurrentWeakMapKt.access$getREHASH$p();
              }
              if (load$FU.compareAndSet(this, j, j + 1))
              {
                k = 1;
                break;
              }
            }
          }
          localObject = paramHashedWeakRef;
          if (paramHashedWeakRef == null) {
            localObject = new HashedWeakRef(paramK, ConcurrentWeakMap.access$getWeakRefQueue$p(ConcurrentWeakMap.this));
          }
          if (!ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0.m(this.keys, i, null, localObject))
          {
            j = k;
            paramHashedWeakRef = (HashedWeakRef<K>)localObject;
          }
        }
        else
        {
          localObject = ((HashedWeakRef)localObject).get();
          if (!Intrinsics.areEqual(paramK, localObject)) {
            break label209;
          }
          if (j != 0) {
            load$FU.decrementAndGet(this);
          }
        }
        do
        {
          paramK = this.values.get(i);
          if ((paramK instanceof Marked)) {
            return ConcurrentWeakMapKt.access$getREHASH$p();
          }
        } while (!ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0.m(this.values, i, paramK, paramV));
        return paramK;
        label209:
        if (localObject == null) {
          removeCleanedAt(i);
        }
        int k = i;
        if (i == 0) {
          k = this.allocated;
        }
        i = k - 1;
      }
    }
    
    public final ConcurrentWeakMap<K, V>.Core rehash()
    {
      int i = Integer.highestOneBit(RangesKt.coerceAtLeast(ConcurrentWeakMap.this.size(), 4));
      Core localCore = new Core(ConcurrentWeakMap.this, i * 4);
      int k = this.allocated;
      i = 0;
      for (;;)
      {
        if (i < k)
        {
          int j = i + 1;
          HashedWeakRef localHashedWeakRef = (HashedWeakRef)this.keys.get(i);
          Object localObject1;
          if (localHashedWeakRef == null) {
            localObject1 = null;
          } else {
            localObject1 = localHashedWeakRef.get();
          }
          if ((localHashedWeakRef != null) && (localObject1 == null)) {
            removeCleanedAt(i);
          }
          Object localObject2;
          do
          {
            localObject2 = this.values.get(i);
            if ((localObject2 instanceof Marked))
            {
              localObject2 = ((Marked)localObject2).ref;
              break;
            }
          } while (!ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0.m(this.values, i, localObject2, ConcurrentWeakMapKt.access$mark(localObject2)));
          i = j;
          if (localObject1 != null)
          {
            i = j;
            if (localObject2 != null)
            {
              localObject1 = localCore.putImpl(localObject1, localObject2, localHashedWeakRef);
              if (localObject1 == ConcurrentWeakMapKt.access$getREHASH$p()) {
                break;
              }
              if (localObject1 == null) {
                i = 1;
              } else {
                i = 0;
              }
              if (i != 0) {
                i = j;
              } else {
                throw new AssertionError("Assertion failed");
              }
            }
          }
        }
      }
      return localCore;
    }
    
    @Metadata(d1={"\0000\n\002\030\002\n\000\n\002\020)\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020\002\n\000\n\002\020\013\n\002\b\003\n\002\020\001\n\000\b\004\030\000*\004\b\002\020\0012\b\022\004\022\002H\0010\002B\037\022\030\020\003\032\024\022\004\022\0028\000\022\004\022\0028\001\022\004\022\0028\0020\004¢\006\002\020\005J\b\020\013\032\0020\fH\002J\t\020\r\032\0020\016H\002J\016\020\017\032\0028\002H\002¢\006\002\020\020J\b\020\021\032\0020\022H\026R \020\003\032\024\022\004\022\0028\000\022\004\022\0028\001\022\004\022\0028\0020\004X\004¢\006\002\n\000R\016\020\006\032\0020\007X\016¢\006\002\n\000R\020\020\b\032\0028\000X.¢\006\004\n\002\020\tR\020\020\n\032\0028\001X.¢\006\004\n\002\020\t¨\006\023"}, d2={"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core$KeyValueIterator;", "E", "", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;Lkotlin/jvm/functions/Function2;)V", "index", "", "key", "Ljava/lang/Object;", "value", "findNext", "", "hasNext", "", "next", "()Ljava/lang/Object;", "remove", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
    private final class KeyValueIterator<E>
      implements Iterator<E>, KMutableIterator
    {
      private final Function2<K, V, E> factory;
      private int index;
      private K key;
      private V value;
      
      public KeyValueIterator()
      {
        Function2 localFunction2;
        this.factory = localFunction2;
        this.index = -1;
        findNext();
      }
      
      private final void findNext()
      {
        Object localObject1;
        do
        {
          do
          {
            int i = this.index + 1;
            this.index = i;
            if (i >= ConcurrentWeakMap.Core.access$getAllocated$p(ConcurrentWeakMap.Core.this)) {
              break;
            }
            localObject1 = (HashedWeakRef)ConcurrentWeakMap.Core.this.keys.get(this.index);
            if (localObject1 == null) {
              localObject1 = null;
            } else {
              localObject1 = ((HashedWeakRef)localObject1).get();
            }
          } while (localObject1 == null);
          this.key = localObject1;
          Object localObject2 = ConcurrentWeakMap.Core.this.values.get(this.index);
          localObject1 = localObject2;
          if ((localObject2 instanceof Marked)) {
            localObject1 = ((Marked)localObject2).ref;
          }
        } while (localObject1 == null);
        this.value = localObject1;
        return;
      }
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < ConcurrentWeakMap.Core.access$getAllocated$p(ConcurrentWeakMap.Core.this)) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public E next()
      {
        if (this.index < ConcurrentWeakMap.Core.access$getAllocated$p(ConcurrentWeakMap.Core.this))
        {
          Function2 localFunction2 = this.factory;
          Object localObject2 = this.key;
          Object localObject1 = localObject2;
          if (localObject2 == null)
          {
            Intrinsics.throwUninitializedPropertyAccessException("key");
            localObject1 = Unit.INSTANCE;
          }
          Object localObject3 = this.value;
          localObject2 = localObject3;
          if (localObject3 == null)
          {
            Intrinsics.throwUninitializedPropertyAccessException("value");
            localObject2 = Unit.INSTANCE;
          }
          localObject1 = localFunction2.invoke(localObject1, localObject2);
          findNext();
          return (E)localObject1;
        }
        throw new NoSuchElementException();
      }
      
      public Void remove()
      {
        ConcurrentWeakMapKt.access$noImpl();
        throw new KotlinNothingValueException();
      }
    }
  }
  
  @Metadata(d1={"\000\020\n\002\030\002\n\002\b\002\n\002\020'\n\002\b\013\b\002\030\000*\004\b\002\020\001*\004\b\003\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\003B\025\022\006\020\004\032\0028\002\022\006\020\005\032\0028\003¢\006\002\020\006J\025\020\013\032\0028\0032\006\020\f\032\0028\003H\026¢\006\002\020\rR\026\020\004\032\0028\002X\004¢\006\n\n\002\020\t\032\004\b\007\020\bR\026\020\005\032\0028\003X\004¢\006\n\n\002\020\t\032\004\b\n\020\b¨\006\016"}, d2={"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Entry;", "K", "V", "", "key", "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getValue", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class Entry<K, V>
    implements Map.Entry<K, V>, KMutableMap.Entry
  {
    private final K key;
    private final V value;
    
    public Entry(K paramK, V paramV)
    {
      this.key = paramK;
      this.value = paramV;
    }
    
    public K getKey()
    {
      return (K)this.key;
    }
    
    public V getValue()
    {
      return (V)this.value;
    }
    
    public V setValue(V paramV)
    {
      ConcurrentWeakMapKt.access$noImpl();
      throw new KotlinNothingValueException();
    }
  }
  
  @Metadata(d1={"\000*\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\003\n\002\020)\n\000\b\004\030\000*\004\b\002\020\0012\b\022\004\022\002H\0010\002B\037\022\030\020\003\032\024\022\004\022\0028\000\022\004\022\0028\001\022\004\022\0028\0020\004¢\006\002\020\005J\025\020\n\032\0020\0132\006\020\f\032\0028\002H\026¢\006\002\020\rJ\017\020\016\032\b\022\004\022\0028\0020\017H\002R \020\003\032\024\022\004\022\0028\000\022\004\022\0028\001\022\004\022\0028\0020\004X\004¢\006\002\n\000R\024\020\006\032\0020\0078VX\004¢\006\006\032\004\b\b\020\t¨\006\020"}, d2={"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$KeyValueSet;", "E", "Lkotlin/collections/AbstractMutableSet;", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;Lkotlin/jvm/functions/Function2;)V", "size", "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "iterator", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class KeyValueSet<E>
    extends AbstractMutableSet<E>
  {
    private final Function2<K, V, E> factory;
    
    public KeyValueSet()
    {
      Function2 localFunction2;
      this.factory = localFunction2;
    }
    
    public boolean add(E paramE)
    {
      ConcurrentWeakMapKt.access$noImpl();
      throw new KotlinNothingValueException();
    }
    
    public int getSize()
    {
      return ConcurrentWeakMap.this.size();
    }
    
    public Iterator<E> iterator()
    {
      return ((ConcurrentWeakMap.Core)ConcurrentWeakMap.this.core).keyValueIterator(this.factory);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/ConcurrentWeakMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */