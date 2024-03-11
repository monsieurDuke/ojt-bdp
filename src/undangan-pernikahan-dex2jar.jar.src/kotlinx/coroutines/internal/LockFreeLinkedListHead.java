package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\001\n\002\b\003\b\026\030\0002\0020\001B\005¢\006\002\020\002J-\020\007\032\0020\b\"\016\b\000\020\t\030\001*\0060\001j\002`\n2\022\020\013\032\016\022\004\022\002H\t\022\004\022\0020\b0\fH\bJ\020\020\r\032\n\030\0010\001j\004\030\001`\nH\024J\006\020\016\032\0020\017J\r\020\020\032\0020\bH\000¢\006\002\b\021R\021\020\003\032\0020\0048F¢\006\006\032\004\b\003\020\005R\024\020\006\032\0020\0048VX\004¢\006\006\032\004\b\006\020\005¨\006\022"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "()V", "isEmpty", "", "()Z", "isRemoved", "forEach", "", "T", "Lkotlinx/coroutines/internal/Node;", "block", "Lkotlin/Function1;", "nextIfRemoved", "remove", "", "validate", "validate$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class LockFreeLinkedListHead
  extends LockFreeLinkedListNode
{
  public final boolean isEmpty()
  {
    boolean bool;
    if (getNext() == this) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isRemoved()
  {
    return false;
  }
  
  protected LockFreeLinkedListNode nextIfRemoved()
  {
    return null;
  }
  
  public final Void remove()
  {
    throw new IllegalStateException("head cannot be removed".toString());
  }
  
  public final void validate$kotlinx_coroutines_core()
  {
    Object localObject2 = (LockFreeLinkedListNode)this;
    LockFreeLinkedListNode localLockFreeLinkedListNode;
    for (Object localObject1 = (LockFreeLinkedListNode)getNext(); !Intrinsics.areEqual(localObject1, this); localObject1 = localLockFreeLinkedListNode)
    {
      localLockFreeLinkedListNode = ((LockFreeLinkedListNode)localObject1).getNextNode();
      ((LockFreeLinkedListNode)localObject1).validateNode$kotlinx_coroutines_core((LockFreeLinkedListNode)localObject2, localLockFreeLinkedListNode);
      localObject2 = localObject1;
    }
    validateNode$kotlinx_coroutines_core((LockFreeLinkedListNode)localObject2, (LockFreeLinkedListNode)getNext());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LockFreeLinkedListHead.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */