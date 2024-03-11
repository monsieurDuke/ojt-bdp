package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

@Metadata(d1={"\000,\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\002\020\001\n\002\b\r\n\002\020\000\n\002\b\t\b \030\000*\016\b\000\020\001*\b\022\004\022\0028\0000\0002\0020\032B\021\022\b\020\002\032\004\030\0018\000¢\006\004\b\003\020\004J\r\020\006\032\0020\005¢\006\004\b\006\020\007J\r\020\t\032\0020\b¢\006\004\b\t\020\nJ \020\016\032\004\030\0018\0002\f\020\r\032\b\022\004\022\0020\f0\013H\b¢\006\004\b\016\020\017J\r\020\020\032\0020\005¢\006\004\b\020\020\007J\025\020\022\032\0020\b2\006\020\021\032\0028\000¢\006\004\b\022\020\023R\021\020\024\032\0020\b8F¢\006\006\032\004\b\024\020\nR\026\020\027\032\004\030\0018\0008BX\004¢\006\006\032\004\b\025\020\026R\023\020\031\032\004\030\0018\0008F¢\006\006\032\004\b\030\020\026R\026\020\035\032\004\030\0010\0328BX\004¢\006\006\032\004\b\033\020\034R\023\020\002\032\004\030\0018\0008F¢\006\006\032\004\b\036\020\026R\024\020 \032\0020\b8&X¦\004¢\006\006\032\004\b\037\020\nR\024\020\"\032\0028\0008BX\004¢\006\006\032\004\b!\020\026¨\006#"}, d2={"Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "N", "prev", "<init>", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)V", "", "cleanPrev", "()V", "", "markAsClosed", "()Z", "Lkotlin/Function0;", "", "onClosedAction", "nextOrIfClosed", "(Lkotlin/jvm/functions/Function0;)Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "remove", "value", "trySetNext", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)Z", "isTail", "getLeftmostAliveNode", "()Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "leftmostAliveNode", "getNext", "next", "", "getNextOrClosed", "()Ljava/lang/Object;", "nextOrClosed", "getPrev", "getRemoved", "removed", "getRightmostAliveNode", "rightmostAliveNode", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class ConcurrentLinkedListNode<N extends ConcurrentLinkedListNode<N>>
{
  private static final AtomicReferenceFieldUpdater _next$FU = AtomicReferenceFieldUpdater.newUpdater(ConcurrentLinkedListNode.class, Object.class, "_next");
  private static final AtomicReferenceFieldUpdater _prev$FU = AtomicReferenceFieldUpdater.newUpdater(ConcurrentLinkedListNode.class, Object.class, "_prev");
  private volatile Object _next = null;
  private volatile Object _prev;
  
  public ConcurrentLinkedListNode(N paramN)
  {
    this._prev = paramN;
  }
  
  private final N getLeftmostAliveNode()
  {
    for (ConcurrentLinkedListNode localConcurrentLinkedListNode = getPrev(); (localConcurrentLinkedListNode != null) && (localConcurrentLinkedListNode.getRemoved()); localConcurrentLinkedListNode = (ConcurrentLinkedListNode)localConcurrentLinkedListNode._prev) {}
    return localConcurrentLinkedListNode;
  }
  
  private final Object getNextOrClosed()
  {
    return this._next;
  }
  
  private final N getRightmostAliveNode()
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(isTail() ^ true))) {
      throw new AssertionError();
    }
    ConcurrentLinkedListNode localConcurrentLinkedListNode = getNext();
    Intrinsics.checkNotNull(localConcurrentLinkedListNode);
    while (localConcurrentLinkedListNode.getRemoved())
    {
      localConcurrentLinkedListNode = localConcurrentLinkedListNode.getNext();
      Intrinsics.checkNotNull(localConcurrentLinkedListNode);
    }
    return localConcurrentLinkedListNode;
  }
  
  public final void cleanPrev()
  {
    _prev$FU.lazySet(this, null);
  }
  
  public final N getNext()
  {
    Object localObject = access$getNextOrClosed(this);
    if (localObject == ConcurrentLinkedListKt.access$getCLOSED$p()) {
      return null;
    }
    localObject = (ConcurrentLinkedListNode)localObject;
    return (N)localObject;
  }
  
  public final N getPrev()
  {
    return (ConcurrentLinkedListNode)this._prev;
  }
  
  public abstract boolean getRemoved();
  
  public final boolean isTail()
  {
    boolean bool;
    if (getNext() == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean markAsClosed()
  {
    return AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, null, ConcurrentLinkedListKt.access$getCLOSED$p());
  }
  
  public final N nextOrIfClosed(Function0 paramFunction0)
  {
    Object localObject = access$getNextOrClosed(this);
    if (localObject != ConcurrentLinkedListKt.access$getCLOSED$p())
    {
      paramFunction0 = (ConcurrentLinkedListNode)localObject;
      return paramFunction0;
    }
    paramFunction0.invoke();
    throw new KotlinNothingValueException();
  }
  
  public final void remove()
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!getRemoved())) {
      throw new AssertionError();
    }
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(isTail() ^ true))) {
      throw new AssertionError();
    }
    ConcurrentLinkedListNode localConcurrentLinkedListNode2;
    ConcurrentLinkedListNode localConcurrentLinkedListNode1;
    do
    {
      localConcurrentLinkedListNode2 = getLeftmostAliveNode();
      localConcurrentLinkedListNode1 = getRightmostAliveNode();
      localConcurrentLinkedListNode1._prev = localConcurrentLinkedListNode2;
      if (localConcurrentLinkedListNode2 != null) {
        localConcurrentLinkedListNode2._next = localConcurrentLinkedListNode1;
      }
    } while ((localConcurrentLinkedListNode1.getRemoved()) || ((localConcurrentLinkedListNode2 != null) && (localConcurrentLinkedListNode2.getRemoved())));
  }
  
  public final boolean trySetNext(N paramN)
  {
    return AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, null, paramN);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ConcurrentLinkedListNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */