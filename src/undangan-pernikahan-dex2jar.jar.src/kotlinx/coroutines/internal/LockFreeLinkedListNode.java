package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000j\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\003\n\002\020\b\n\002\b\007\n\002\020\000\n\002\b\f\b\027\030\0002\0020C:\005JKLMNB\007¢\006\004\b\001\020\002J\031\020\006\032\0020\0052\n\020\004\032\0060\000j\002`\003¢\006\004\b\006\020\007J,\020\013\032\0020\t2\n\020\004\032\0060\000j\002`\0032\016\b\004\020\n\032\b\022\004\022\0020\t0\bH\b¢\006\004\b\013\020\fJ4\020\017\032\0020\t2\n\020\004\032\0060\000j\002`\0032\026\020\016\032\022\022\b\022\0060\000j\002`\003\022\004\022\0020\t0\rH\b¢\006\004\b\017\020\020JD\020\021\032\0020\t2\n\020\004\032\0060\000j\002`\0032\026\020\016\032\022\022\b\022\0060\000j\002`\003\022\004\022\0020\t0\r2\016\b\004\020\n\032\b\022\004\022\0020\t0\bH\b¢\006\004\b\021\020\022J'\020\024\032\0020\t2\n\020\004\032\0060\000j\002`\0032\n\020\023\032\0060\000j\002`\003H\001¢\006\004\b\024\020\025J\031\020\026\032\0020\t2\n\020\004\032\0060\000j\002`\003¢\006\004\b\026\020\027J\"\020\032\032\n\030\0010\000j\004\030\001`\0032\b\020\031\032\004\030\0010\030H\020¢\006\004\b\032\020\033J)\020\036\032\b\022\004\022\0028\0000\035\"\f\b\000\020\034*\0060\000j\002`\0032\006\020\004\032\0028\000¢\006\004\b\036\020\037J\027\020!\032\f\022\b\022\0060\000j\002`\0030 ¢\006\004\b!\020\"J \020$\032\0060\000j\002`\0032\n\020#\032\0060\000j\002`\003H\020¢\006\004\b$\020%J\033\020&\032\0020\0052\n\020\023\032\0060\000j\002`\003H\002¢\006\004\b&\020\007J\r\020'\032\0020\005¢\006\004\b'\020\002J\017\020(\032\0020\005H\001¢\006\004\b(\020\002J,\020*\032\0020)2\n\020\004\032\0060\000j\002`\0032\016\b\004\020\n\032\b\022\004\022\0020\t0\bH\b¢\006\004\b*\020+J\027\020,\032\n\030\0010\000j\004\030\001`\003H\024¢\006\004\b,\020-J\017\020.\032\0020\tH\026¢\006\004\b.\020/J.\0200\032\004\030\0018\000\"\006\b\000\020\034\030\0012\022\020\016\032\016\022\004\022\0028\000\022\004\022\0020\t0\rH\b¢\006\004\b0\0201J\025\0202\032\n\030\0010\000j\004\030\001`\003¢\006\004\b2\020-J\027\0203\032\n\030\0010\000j\004\030\001`\003H\001¢\006\004\b3\020-J\017\0205\032\00204H\002¢\006\004\b5\0206J\017\0208\032\00207H\026¢\006\004\b8\0209J/\020<\032\0020;2\n\020\004\032\0060\000j\002`\0032\n\020\023\032\0060\000j\002`\0032\006\020:\032\0020)H\001¢\006\004\b<\020=J'\020A\032\0020\0052\n\020>\032\0060\000j\002`\0032\n\020\023\032\0060\000j\002`\003H\000¢\006\004\b?\020@R\024\020B\032\0020\t8VX\004¢\006\006\032\004\bB\020/R\021\020\023\032\0020C8F¢\006\006\032\004\bD\020ER\025\020G\032\0060\000j\002`\0038F¢\006\006\032\004\bF\020-R\025\020I\032\0060\000j\002`\0038F¢\006\006\032\004\bH\020-¨\006O"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "<init>", "()V", "Lkotlinx/coroutines/internal/Node;", "node", "", "addLast", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "Lkotlin/Function0;", "", "condition", "addLastIf", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function0;)Z", "Lkotlin/Function1;", "predicate", "addLastIfPrev", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function1;)Z", "addLastIfPrevAndIf", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Z", "next", "addNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "addOneIfEmpty", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "correctPrev", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "T", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "describeAddLast", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "describeRemoveFirst", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "current", "findPrevNonRemoved", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "finishAdd", "helpRemove", "helpRemovePrev", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "makeCondAddOp", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function0;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "nextIfRemoved", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "remove", "()Z", "removeFirstIfIsInstanceOfOrPeekIf", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "removeFirstOrNull", "removeOrNext", "Lkotlinx/coroutines/internal/Removed;", "removed", "()Lkotlinx/coroutines/internal/Removed;", "", "toString", "()Ljava/lang/String;", "condAdd", "", "tryCondAddNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;)I", "prev", "validateNode$kotlinx_coroutines_core", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "validateNode", "isRemoved", "", "getNext", "()Ljava/lang/Object;", "getNextNode", "nextNode", "getPrevNode", "prevNode", "AbstractAtomicDesc", "AddLastDesc", "CondAddOp", "PrepareOp", "RemoveFirstDesc", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class LockFreeLinkedListNode
{
  static final AtomicReferenceFieldUpdater _next$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_next");
  static final AtomicReferenceFieldUpdater _prev$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_prev");
  private static final AtomicReferenceFieldUpdater _removedRef$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_removedRef");
  volatile Object _next = this;
  volatile Object _prev = this;
  private volatile Object _removedRef = null;
  
  private final LockFreeLinkedListNode correctPrev(OpDescriptor paramOpDescriptor)
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode = (LockFreeLinkedListNode)this._prev;
    Object localObject1 = localLockFreeLinkedListNode;
    Object localObject2 = null;
    for (;;)
    {
      Object localObject3 = ((LockFreeLinkedListNode)localObject1)._next;
      if (localObject3 == this)
      {
        if (localLockFreeLinkedListNode == localObject1) {
          return (LockFreeLinkedListNode)localObject1;
        }
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_prev$FU, this, localLockFreeLinkedListNode, localObject1)) {
          break;
        }
        return (LockFreeLinkedListNode)localObject1;
      }
      if (isRemoved()) {
        return null;
      }
      if (localObject3 == paramOpDescriptor) {
        return (LockFreeLinkedListNode)localObject1;
      }
      if ((localObject3 instanceof OpDescriptor))
      {
        if ((paramOpDescriptor != null) && (paramOpDescriptor.isEarlierThan((OpDescriptor)localObject3))) {
          return null;
        }
        ((OpDescriptor)localObject3).perform(localObject1);
        break;
      }
      if ((localObject3 instanceof Removed))
      {
        if (localObject2 != null)
        {
          if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_next$FU, localObject2, localObject1, ((Removed)localObject3).ref)) {
            break;
          }
          localObject1 = localObject2;
          localObject2 = null;
          continue;
        }
        localObject1 = (LockFreeLinkedListNode)((LockFreeLinkedListNode)localObject1)._prev;
        continue;
      }
      localObject2 = localObject1;
      localObject1 = (LockFreeLinkedListNode)localObject3;
    }
  }
  
  private final LockFreeLinkedListNode findPrevNonRemoved(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    for (;;)
    {
      if (!paramLockFreeLinkedListNode.isRemoved()) {
        return paramLockFreeLinkedListNode;
      }
      paramLockFreeLinkedListNode = (LockFreeLinkedListNode)paramLockFreeLinkedListNode._prev;
    }
  }
  
  private final void finishAdd(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    for (;;)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode = (LockFreeLinkedListNode)paramLockFreeLinkedListNode._prev;
      if (getNext() != paramLockFreeLinkedListNode) {
        return;
      }
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_prev$FU, paramLockFreeLinkedListNode, localLockFreeLinkedListNode, this))
      {
        if (isRemoved()) {
          paramLockFreeLinkedListNode.correctPrev(null);
        }
        return;
      }
    }
  }
  
  private final Removed removed()
  {
    Removed localRemoved2 = (Removed)this._removedRef;
    Removed localRemoved1 = localRemoved2;
    if (localRemoved2 == null)
    {
      localRemoved1 = new Removed(this);
      _removedRef$FU.lazySet(this, localRemoved1);
    }
    return localRemoved1;
  }
  
  public final void addLast(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    while (!getPrevNode().addNext(paramLockFreeLinkedListNode, this)) {}
  }
  
  public final boolean addLastIf(LockFreeLinkedListNode paramLockFreeLinkedListNode, final Function0<Boolean> paramFunction0)
  {
    paramFunction0 = (CondAddOp)new CondAddOp(paramLockFreeLinkedListNode)
    {
      final LockFreeLinkedListNode $node;
      
      public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
      {
        if (((Boolean)paramFunction0.invoke()).booleanValue()) {
          paramAnonymousLockFreeLinkedListNode = null;
        } else {
          paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
        }
        return paramAnonymousLockFreeLinkedListNode;
      }
    };
    for (;;)
    {
      switch (getPrevNode().tryCondAddNext(paramLockFreeLinkedListNode, this, paramFunction0))
      {
      }
    }
    return false;
    return true;
  }
  
  public final boolean addLastIfPrev(LockFreeLinkedListNode paramLockFreeLinkedListNode, Function1<? super LockFreeLinkedListNode, Boolean> paramFunction1)
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode;
    do
    {
      localLockFreeLinkedListNode = getPrevNode();
      if (!((Boolean)paramFunction1.invoke(localLockFreeLinkedListNode)).booleanValue()) {
        return false;
      }
    } while (!localLockFreeLinkedListNode.addNext(paramLockFreeLinkedListNode, this));
    return true;
  }
  
  public final boolean addLastIfPrevAndIf(LockFreeLinkedListNode paramLockFreeLinkedListNode, Function1<? super LockFreeLinkedListNode, Boolean> paramFunction1, Function0<Boolean> paramFunction0)
  {
    CondAddOp localCondAddOp = (CondAddOp)new CondAddOp(paramLockFreeLinkedListNode)
    {
      final LockFreeLinkedListNode $node;
      
      public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
      {
        if (((Boolean)paramFunction0.invoke()).booleanValue()) {
          paramAnonymousLockFreeLinkedListNode = null;
        } else {
          paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
        }
        return paramAnonymousLockFreeLinkedListNode;
      }
    };
    for (;;)
    {
      paramFunction0 = getPrevNode();
      if (!((Boolean)paramFunction1.invoke(paramFunction0)).booleanValue()) {
        return false;
      }
      switch (paramFunction0.tryCondAddNext(paramLockFreeLinkedListNode, this, localCondAddOp))
      {
      }
    }
    return false;
    return true;
  }
  
  public final boolean addNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
  {
    _prev$FU.lazySet(paramLockFreeLinkedListNode1, this);
    AtomicReferenceFieldUpdater localAtomicReferenceFieldUpdater = _next$FU;
    localAtomicReferenceFieldUpdater.lazySet(paramLockFreeLinkedListNode1, paramLockFreeLinkedListNode2);
    if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(localAtomicReferenceFieldUpdater, this, paramLockFreeLinkedListNode2, paramLockFreeLinkedListNode1)) {
      return false;
    }
    paramLockFreeLinkedListNode1.finishAdd(paramLockFreeLinkedListNode2);
    return true;
  }
  
  public final boolean addOneIfEmpty(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    _prev$FU.lazySet(paramLockFreeLinkedListNode, this);
    _next$FU.lazySet(paramLockFreeLinkedListNode, this);
    for (;;)
    {
      if (getNext() != this) {
        return false;
      }
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, this, paramLockFreeLinkedListNode))
      {
        paramLockFreeLinkedListNode.finishAdd(this);
        return true;
      }
    }
  }
  
  public final <T extends LockFreeLinkedListNode> AddLastDesc<T> describeAddLast(T paramT)
  {
    return new AddLastDesc(this, paramT);
  }
  
  public final RemoveFirstDesc<LockFreeLinkedListNode> describeRemoveFirst()
  {
    return new RemoveFirstDesc(this);
  }
  
  public final Object getNext()
  {
    for (;;)
    {
      Object localObject = this._next;
      if (!(localObject instanceof OpDescriptor)) {
        return localObject;
      }
      ((OpDescriptor)localObject).perform(this);
    }
  }
  
  public final LockFreeLinkedListNode getNextNode()
  {
    return LockFreeLinkedListKt.unwrap(getNext());
  }
  
  public final LockFreeLinkedListNode getPrevNode()
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode2 = correctPrev(null);
    LockFreeLinkedListNode localLockFreeLinkedListNode1 = localLockFreeLinkedListNode2;
    if (localLockFreeLinkedListNode2 == null) {
      localLockFreeLinkedListNode1 = findPrevNonRemoved((LockFreeLinkedListNode)this._prev);
    }
    return localLockFreeLinkedListNode1;
  }
  
  public final void helpRemove()
  {
    ((Removed)getNext()).ref.helpRemovePrev();
  }
  
  public final void helpRemovePrev()
  {
    Object localObject;
    for (LockFreeLinkedListNode localLockFreeLinkedListNode = this;; localLockFreeLinkedListNode = ((Removed)localObject).ref)
    {
      localObject = localLockFreeLinkedListNode.getNext();
      if (!(localObject instanceof Removed))
      {
        localLockFreeLinkedListNode.correctPrev(null);
        return;
      }
    }
  }
  
  public boolean isRemoved()
  {
    return getNext() instanceof Removed;
  }
  
  public final CondAddOp makeCondAddOp(LockFreeLinkedListNode paramLockFreeLinkedListNode, Function0<Boolean> paramFunction0)
  {
    (CondAddOp)new CondAddOp(paramLockFreeLinkedListNode)
    {
      final LockFreeLinkedListNode $node;
      
      public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
      {
        if (((Boolean)paramFunction0.invoke()).booleanValue()) {
          paramAnonymousLockFreeLinkedListNode = null;
        } else {
          paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
        }
        return paramAnonymousLockFreeLinkedListNode;
      }
    };
  }
  
  protected LockFreeLinkedListNode nextIfRemoved()
  {
    Object localObject1 = getNext();
    boolean bool = localObject1 instanceof Removed;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (Removed)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = ((Removed)localObject1).ref;
    }
    return (LockFreeLinkedListNode)localObject1;
  }
  
  public boolean remove()
  {
    boolean bool;
    if (removeOrNext() == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final LockFreeLinkedListNode removeFirstOrNull()
  {
    for (;;)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode = (LockFreeLinkedListNode)getNext();
      if (localLockFreeLinkedListNode == this) {
        return null;
      }
      if (localLockFreeLinkedListNode.remove()) {
        return localLockFreeLinkedListNode;
      }
      localLockFreeLinkedListNode.helpRemove();
    }
  }
  
  public final LockFreeLinkedListNode removeOrNext()
  {
    for (;;)
    {
      Object localObject = getNext();
      if ((localObject instanceof Removed)) {
        return ((Removed)localObject).ref;
      }
      if (localObject == this) {
        return (LockFreeLinkedListNode)localObject;
      }
      Removed localRemoved = ((LockFreeLinkedListNode)localObject).removed();
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, localObject, localRemoved))
      {
        ((LockFreeLinkedListNode)localObject).correctPrev(null);
        return null;
      }
    }
  }
  
  public String toString()
  {
    // Byte code:
    //   0: new 235	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 236	java/lang/StringBuilder:<init>	()V
    //   7: new 23	kotlinx/coroutines/internal/LockFreeLinkedListNode$toString$1
    //   10: dup
    //   11: aload_0
    //   12: invokespecial 239	kotlinx/coroutines/internal/LockFreeLinkedListNode$toString$1:<init>	(Ljava/lang/Object;)V
    //   15: invokevirtual 243	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   18: bipush 64
    //   20: invokevirtual 246	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   23: astore_2
    //   24: aload_0
    //   25: invokestatic 252	kotlinx/coroutines/DebugStringsKt:getHexAddress	(Ljava/lang/Object;)Ljava/lang/String;
    //   28: astore_1
    //   29: aload_1
    //   30: invokestatic 257	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   33: aload_1
    //   34: invokestatic 260	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   37: aload_1
    //   38: invokestatic 263	mt/Log229316:a	(Ljava/lang/Object;)V
    //   41: aload_2
    //   42: aload_1
    //   43: invokevirtual 266	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: invokevirtual 268	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	50	0	this	LockFreeLinkedListNode
    //   28	15	1	str	String
    //   23	19	2	localStringBuilder	StringBuilder
  }
  
  public final int tryCondAddNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2, CondAddOp paramCondAddOp)
  {
    _prev$FU.lazySet(paramLockFreeLinkedListNode1, this);
    AtomicReferenceFieldUpdater localAtomicReferenceFieldUpdater = _next$FU;
    localAtomicReferenceFieldUpdater.lazySet(paramLockFreeLinkedListNode1, paramLockFreeLinkedListNode2);
    paramCondAddOp.oldNext = paramLockFreeLinkedListNode2;
    if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(localAtomicReferenceFieldUpdater, this, paramLockFreeLinkedListNode2, paramCondAddOp)) {
      return 0;
    }
    int i;
    if (paramCondAddOp.perform(this) == null) {
      i = 1;
    } else {
      i = 2;
    }
    return i;
  }
  
  public final void validateNode$kotlinx_coroutines_core(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
  {
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int j = 1;
    int i;
    if (bool)
    {
      if (paramLockFreeLinkedListNode1 == this._prev) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (paramLockFreeLinkedListNode2 == this._next) {
        i = j;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
  }
  
  @Metadata(d1={"\000B\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\005\n\002\030\002\n\002\b\004\n\002\020\013\n\000\n\002\030\002\n\002\b\002\b&\030\0002\0020\001B\005¢\006\002\020\002J\034\020\n\032\0020\0132\n\020\f\032\006\022\002\b\0030\r2\b\020\016\032\004\030\0010\017J\026\020\016\032\004\030\0010\0172\n\020\020\032\0060\004j\002`\005H\024J \020\021\032\0020\0132\n\020\020\032\0060\004j\002`\0052\n\020\022\032\0060\004j\002`\005H$J\020\020\023\032\0020\0132\006\020\024\032\0020\025H&J\022\020\026\032\004\030\0010\0172\006\020\024\032\0020\025H\026J\024\020\027\032\0020\0132\n\020\020\032\0060\004j\002`\005H\026J\024\020\030\032\004\030\0010\0172\n\020\f\032\006\022\002\b\0030\rJ\034\020\031\032\0020\0322\n\020\020\032\0060\004j\002`\0052\006\020\022\032\0020\017H\024J\030\020\033\032\n\030\0010\004j\004\030\001`\0052\006\020\f\032\0020\034H\024J \020\035\032\0020\0172\n\020\020\032\0060\004j\002`\0052\n\020\022\032\0060\004j\002`\005H&R\032\020\003\032\n\030\0010\004j\004\030\001`\005X¤\004¢\006\006\032\004\b\006\020\007R\032\020\b\032\n\030\0010\004j\004\030\001`\005X¤\004¢\006\006\032\004\b\t\020\007¨\006\036"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "()V", "affectedNode", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "originalNext", "getOriginalNext", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "", "affected", "finishOnSuccess", "next", "finishPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "onPrepare", "onRemoved", "prepare", "retry", "", "takeAffectedNode", "Lkotlinx/coroutines/internal/OpDescriptor;", "updatedNext", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static abstract class AbstractAtomicDesc
    extends AtomicDesc
  {
    public final void complete(AtomicOp<?> paramAtomicOp, Object paramObject)
    {
      int k = 1;
      int j = 1;
      int i;
      if (paramObject == null) {
        i = 1;
      } else {
        i = 0;
      }
      LockFreeLinkedListNode localLockFreeLinkedListNode2 = getAffectedNode();
      if (localLockFreeLinkedListNode2 == null)
      {
        paramAtomicOp = (AbstractAtomicDesc)this;
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          if (i == 0) {
            i = j;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw new AssertionError();
          }
        }
        return;
      }
      LockFreeLinkedListNode localLockFreeLinkedListNode1 = getOriginalNext();
      if (localLockFreeLinkedListNode1 == null)
      {
        paramAtomicOp = (AbstractAtomicDesc)this;
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          if (i == 0) {
            i = k;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw new AssertionError();
          }
        }
        return;
      }
      if (i != 0) {
        paramObject = updatedNext(localLockFreeLinkedListNode2, localLockFreeLinkedListNode1);
      } else {
        paramObject = localLockFreeLinkedListNode1;
      }
      if ((AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._next$FU, localLockFreeLinkedListNode2, paramAtomicOp, paramObject)) && (i != 0)) {
        finishOnSuccess(localLockFreeLinkedListNode2, localLockFreeLinkedListNode1);
      }
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      return null;
    }
    
    protected abstract void finishOnSuccess(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2);
    
    public abstract void finishPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp);
    
    protected abstract LockFreeLinkedListNode getAffectedNode();
    
    protected abstract LockFreeLinkedListNode getOriginalNext();
    
    public Object onPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      finishPrepare(paramPrepareOp);
      return null;
    }
    
    public void onRemoved(LockFreeLinkedListNode paramLockFreeLinkedListNode) {}
    
    public final Object prepare(AtomicOp<?> paramAtomicOp)
    {
      for (;;)
      {
        LockFreeLinkedListNode localLockFreeLinkedListNode = takeAffectedNode((OpDescriptor)paramAtomicOp);
        if (localLockFreeLinkedListNode == null) {
          return AtomicKt.RETRY_ATOMIC;
        }
        Object localObject1 = localLockFreeLinkedListNode._next;
        if (localObject1 == paramAtomicOp) {
          return null;
        }
        if (paramAtomicOp.isDecided()) {
          return null;
        }
        if ((localObject1 instanceof OpDescriptor))
        {
          if (paramAtomicOp.isEarlierThan((OpDescriptor)localObject1)) {
            return AtomicKt.RETRY_ATOMIC;
          }
          ((OpDescriptor)localObject1).perform(localLockFreeLinkedListNode);
        }
        else
        {
          Object localObject2 = failure(localLockFreeLinkedListNode);
          if (localObject2 != null) {
            return localObject2;
          }
          if (!retry(localLockFreeLinkedListNode, localObject1))
          {
            localObject2 = new LockFreeLinkedListNode.PrepareOp(localLockFreeLinkedListNode, (LockFreeLinkedListNode)localObject1, this);
            if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._next$FU, localLockFreeLinkedListNode, localObject1, localObject2)) {
              try
              {
                Object localObject3 = ((LockFreeLinkedListNode.PrepareOp)localObject2).perform(localLockFreeLinkedListNode);
                if (localObject3 != LockFreeLinkedList_commonKt.REMOVE_PREPARED)
                {
                  if (DebugKt.getASSERTIONS_ENABLED())
                  {
                    int i;
                    if (localObject3 == null) {
                      i = 1;
                    } else {
                      i = 0;
                    }
                    if (i == 0)
                    {
                      paramAtomicOp = new java/lang/AssertionError;
                      paramAtomicOp.<init>();
                      throw paramAtomicOp;
                    }
                  }
                  return null;
                }
              }
              finally
              {
                AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._next$FU, localLockFreeLinkedListNode, localObject2, localObject1);
              }
            }
          }
        }
      }
    }
    
    protected boolean retry(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      return false;
    }
    
    protected LockFreeLinkedListNode takeAffectedNode(OpDescriptor paramOpDescriptor)
    {
      paramOpDescriptor = getAffectedNode();
      Intrinsics.checkNotNull(paramOpDescriptor);
      return paramOpDescriptor;
    }
    
    public abstract Object updatedNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2);
  }
  
  @Metadata(d1={"\0006\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\007\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\f\n\002\030\002\b\026\030\000*\f\b\000\020\003*\0060\001j\002`\0022\0020!B\033\022\n\020\004\032\0060\001j\002`\002\022\006\020\005\032\0028\000¢\006\004\b\006\020\007J'\020\013\032\0020\n2\n\020\b\032\0060\001j\002`\0022\n\020\t\032\0060\001j\002`\002H\024¢\006\004\b\013\020\007J\027\020\016\032\0020\n2\006\020\r\032\0020\fH\026¢\006\004\b\016\020\017J#\020\022\032\0020\0212\n\020\b\032\0060\001j\002`\0022\006\020\t\032\0020\020H\024¢\006\004\b\022\020\023J\037\020\026\032\n\030\0010\001j\004\030\001`\0022\006\020\025\032\0020\024H\004¢\006\004\b\026\020\027J'\020\030\032\0020\0202\n\020\b\032\0060\001j\002`\0022\n\020\t\032\0060\001j\002`\002H\026¢\006\004\b\030\020\031R\034\020\034\032\n\030\0010\001j\004\030\001`\0028DX\004¢\006\006\032\004\b\032\020\033R\024\020\005\032\0028\0008\006X\004¢\006\006\n\004\b\005\020\035R\030\020\037\032\0060\001j\002`\0028DX\004¢\006\006\032\004\b\036\020\033R\030\020\004\032\0060\001j\002`\0028\006X\004¢\006\006\n\004\b\004\020\035¨\006 "}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "T", "queue", "node", "<init>", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "affected", "next", "", "finishOnSuccess", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "prepareOp", "finishPrepare", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "", "", "retry", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "takeAffectedNode", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "updatedNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "affectedNode", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "getOriginalNext", "originalNext", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;"}, k=1, mv={1, 6, 0}, xi=48)
  public static class AddLastDesc<T extends LockFreeLinkedListNode>
    extends LockFreeLinkedListNode.AbstractAtomicDesc
  {
    private static final AtomicReferenceFieldUpdater _affectedNode$FU = AtomicReferenceFieldUpdater.newUpdater(AddLastDesc.class, Object.class, "_affectedNode");
    private volatile Object _affectedNode;
    public final T node;
    public final LockFreeLinkedListNode queue;
    
    public AddLastDesc(LockFreeLinkedListNode paramLockFreeLinkedListNode, T paramT)
    {
      this.queue = paramLockFreeLinkedListNode;
      this.node = paramT;
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if ((paramT._next == paramT) && (paramT._prev == paramT)) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      this._affectedNode = null;
    }
    
    protected void finishOnSuccess(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      LockFreeLinkedListNode.access$finishAdd(this.node, this.queue);
    }
    
    public void finishPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_affectedNode$FU, this, null, paramPrepareOp.affected);
    }
    
    protected final LockFreeLinkedListNode getAffectedNode()
    {
      return (LockFreeLinkedListNode)this._affectedNode;
    }
    
    protected final LockFreeLinkedListNode getOriginalNext()
    {
      return this.queue;
    }
    
    protected boolean retry(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      boolean bool;
      if (paramObject != this.queue) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    protected final LockFreeLinkedListNode takeAffectedNode(OpDescriptor paramOpDescriptor)
    {
      return LockFreeLinkedListNode.access$correctPrev(this.queue, paramOpDescriptor);
    }
    
    public Object updatedNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      paramLockFreeLinkedListNode2 = this.node;
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._prev$FU, paramLockFreeLinkedListNode2, this.node, paramLockFreeLinkedListNode1);
      paramLockFreeLinkedListNode1 = this.node;
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._next$FU, paramLockFreeLinkedListNode1, this.node, this.queue);
      return this.node;
    }
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\020\000\n\000\b!\030\0002\f\022\b\022\0060\002j\002`\0030\001B\021\022\n\020\004\032\0060\002j\002`\003¢\006\002\020\005J\036\020\007\032\0020\b2\n\020\t\032\0060\002j\002`\0032\b\020\n\032\004\030\0010\013H\026R\024\020\004\032\0060\002j\002`\0038\006X\004¢\006\002\n\000R\032\020\006\032\n\030\0010\002j\004\030\001`\0038\006@\006X\016¢\006\002\n\000¨\006\f"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "newNode", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "oldNext", "complete", "", "affected", "failure", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static abstract class CondAddOp
    extends AtomicOp<LockFreeLinkedListNode>
  {
    public final LockFreeLinkedListNode newNode;
    public LockFreeLinkedListNode oldNext;
    
    public CondAddOp(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      this.newNode = paramLockFreeLinkedListNode;
    }
    
    public void complete(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      int i;
      if (paramObject == null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramObject = this.newNode;
      } else {
        paramObject = this.oldNext;
      }
      if ((paramObject != null) && (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._next$FU, paramLockFreeLinkedListNode, this, paramObject)) && (i != 0))
      {
        paramLockFreeLinkedListNode = this.newNode;
        paramObject = this.oldNext;
        Intrinsics.checkNotNull(paramObject);
        LockFreeLinkedListNode.access$finishAdd(paramLockFreeLinkedListNode, (LockFreeLinkedListNode)paramObject);
      }
    }
  }
  
  @Metadata(d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\000\n\000\n\002\020\016\n\000\030\0002\0020\001B%\022\n\020\002\032\0060\003j\002`\004\022\n\020\005\032\0060\003j\002`\004\022\006\020\006\032\0020\007¢\006\002\020\bJ\006\020\r\032\0020\016J\024\020\017\032\004\030\0010\0202\b\020\002\032\004\030\0010\020H\026J\b\020\021\032\0020\022H\026R\024\020\002\032\0060\003j\002`\0048\006X\004¢\006\002\n\000R\030\020\t\032\006\022\002\b\0030\n8VX\004¢\006\006\032\004\b\013\020\fR\020\020\006\032\0020\0078\006X\004¢\006\002\n\000R\024\020\005\032\0060\003j\002`\0048\006X\004¢\006\002\n\000¨\006\023"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "next", "desc", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "finishPrepare", "", "perform", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class PrepareOp
    extends OpDescriptor
  {
    public final LockFreeLinkedListNode affected;
    public final LockFreeLinkedListNode.AbstractAtomicDesc desc;
    public final LockFreeLinkedListNode next;
    
    public PrepareOp(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2, LockFreeLinkedListNode.AbstractAtomicDesc paramAbstractAtomicDesc)
    {
      this.affected = paramLockFreeLinkedListNode1;
      this.next = paramLockFreeLinkedListNode2;
      this.desc = paramAbstractAtomicDesc;
    }
    
    public final void finishPrepare()
    {
      this.desc.finishPrepare(this);
    }
    
    public AtomicOp<?> getAtomicOp()
    {
      return this.desc.getAtomicOp();
    }
    
    public Object perform(Object paramObject)
    {
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (paramObject == this.affected) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      if (paramObject != null)
      {
        Object localObject = (LockFreeLinkedListNode)paramObject;
        localObject = this.desc.onPrepare(this);
        if (localObject == LockFreeLinkedList_commonKt.REMOVE_PREPARED)
        {
          LockFreeLinkedListNode localLockFreeLinkedListNode = this.next;
          Removed localRemoved = LockFreeLinkedListNode.access$removed(localLockFreeLinkedListNode);
          localObject = (LockFreeLinkedListNode)paramObject;
          if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._next$FU, localObject, this, localRemoved))
          {
            this.desc.onRemoved((LockFreeLinkedListNode)paramObject);
            LockFreeLinkedListNode.access$correctPrev(localLockFreeLinkedListNode, null);
          }
          return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
        }
        if (localObject != null) {
          localObject = getAtomicOp().decide(localObject);
        } else {
          localObject = getAtomicOp().getConsensus();
        }
        if (localObject == AtomicKt.NO_DECISION) {
          localObject = getAtomicOp();
        } else if (localObject == null) {
          localObject = this.desc.updatedNext((LockFreeLinkedListNode)paramObject, this.next);
        } else {
          localObject = this.next;
        }
        paramObject = (LockFreeLinkedListNode)paramObject;
        AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode._next$FU, paramObject, this, localObject);
        return null;
      }
      throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
    }
    
    public String toString()
    {
      return "PrepareOp(op=" + getAtomicOp() + ')';
    }
  }
  
  @Metadata(d1={"\000>\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\021\n\002\030\002\b\026\030\000*\004\b\000\020\0012\0020(B\023\022\n\020\004\032\0060\002j\002`\003¢\006\004\b\005\020\006J\035\020\t\032\004\030\0010\b2\n\020\007\032\0060\002j\002`\003H\024¢\006\004\b\t\020\nJ'\020\r\032\0020\f2\n\020\007\032\0060\002j\002`\0032\n\020\013\032\0060\002j\002`\003H\004¢\006\004\b\r\020\016J\027\020\021\032\0020\f2\006\020\020\032\0020\017H\026¢\006\004\b\021\020\022J#\020\024\032\0020\0232\n\020\007\032\0060\002j\002`\0032\006\020\013\032\0020\bH\004¢\006\004\b\024\020\025J\037\020\030\032\n\030\0010\002j\004\030\001`\0032\006\020\027\032\0020\026H\004¢\006\004\b\030\020\031J%\020\032\032\0020\b2\n\020\007\032\0060\002j\002`\0032\n\020\013\032\0060\002j\002`\003¢\006\004\b\032\020\033R\034\020\036\032\n\030\0010\002j\004\030\001`\0038DX\004¢\006\006\032\004\b\034\020\035R\034\020 \032\n\030\0010\002j\004\030\001`\0038DX\004¢\006\006\032\004\b\037\020\035R\030\020\004\032\0060\002j\002`\0038\006X\004¢\006\006\n\004\b\004\020!R\027\020&\032\0028\0008F¢\006\f\022\004\b$\020%\032\004\b\"\020#¨\006'"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "T", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "queue", "<init>", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "affected", "", "failure", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "next", "", "finishOnSuccess", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "prepareOp", "finishPrepare", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "", "retry", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "takeAffectedNode", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "updatedNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "affectedNode", "getOriginalNext", "originalNext", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "getResult", "()Ljava/lang/Object;", "getResult$annotations", "()V", "result", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;"}, k=1, mv={1, 6, 0}, xi=48)
  public static class RemoveFirstDesc<T>
    extends LockFreeLinkedListNode.AbstractAtomicDesc
  {
    private static final AtomicReferenceFieldUpdater _affectedNode$FU = AtomicReferenceFieldUpdater.newUpdater(RemoveFirstDesc.class, Object.class, "_affectedNode");
    private static final AtomicReferenceFieldUpdater _originalNext$FU = AtomicReferenceFieldUpdater.newUpdater(RemoveFirstDesc.class, Object.class, "_originalNext");
    private volatile Object _affectedNode;
    private volatile Object _originalNext;
    public final LockFreeLinkedListNode queue;
    
    public RemoveFirstDesc(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      this.queue = paramLockFreeLinkedListNode;
      this._affectedNode = null;
      this._originalNext = null;
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      if (paramLockFreeLinkedListNode == this.queue) {
        paramLockFreeLinkedListNode = LockFreeLinkedListKt.getLIST_EMPTY();
      } else {
        paramLockFreeLinkedListNode = null;
      }
      return paramLockFreeLinkedListNode;
    }
    
    protected final void finishOnSuccess(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      LockFreeLinkedListNode.access$correctPrev(paramLockFreeLinkedListNode2, null);
    }
    
    public void finishPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_affectedNode$FU, this, null, paramPrepareOp.affected);
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_originalNext$FU, this, null, paramPrepareOp.next);
    }
    
    protected final LockFreeLinkedListNode getAffectedNode()
    {
      return (LockFreeLinkedListNode)this._affectedNode;
    }
    
    protected final LockFreeLinkedListNode getOriginalNext()
    {
      return (LockFreeLinkedListNode)this._originalNext;
    }
    
    public final T getResult()
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode = getAffectedNode();
      Intrinsics.checkNotNull(localLockFreeLinkedListNode);
      return (T)localLockFreeLinkedListNode;
    }
    
    protected final boolean retry(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      if (!(paramObject instanceof Removed)) {
        return false;
      }
      ((Removed)paramObject).ref.helpRemovePrev();
      return true;
    }
    
    protected final LockFreeLinkedListNode takeAffectedNode(OpDescriptor paramOpDescriptor)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode = this.queue;
      Object localObject;
      for (;;)
      {
        localObject = localLockFreeLinkedListNode._next;
        if (!(localObject instanceof OpDescriptor)) {
          break;
        }
        if (paramOpDescriptor.isEarlierThan((OpDescriptor)localObject)) {
          return null;
        }
        ((OpDescriptor)localObject).perform(this.queue);
      }
      return (LockFreeLinkedListNode)localObject;
    }
    
    public final Object updatedNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      return LockFreeLinkedListNode.access$removed(paramLockFreeLinkedListNode2);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LockFreeLinkedListNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */