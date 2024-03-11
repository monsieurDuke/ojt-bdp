package kotlinx.coroutines.internal;

import kotlin.Metadata;

@Metadata(d1={"\0008\n\000\n\002\020\000\n\002\b\005\n\002\020\b\n\002\b\t\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\032\020\020\020\032\0060\021j\002`\022*\0020\001H\001\"\034\020\000\032\0020\0018\000X\004¢\006\016\n\000\022\004\b\002\020\003\032\004\b\004\020\005\"\026\020\006\032\0020\0078\000XT¢\006\b\n\000\022\004\b\b\020\003\"\034\020\t\032\0020\0018\000X\004¢\006\016\n\000\022\004\b\n\020\003\032\004\b\013\020\005\"\026\020\f\032\0020\0078\000XT¢\006\b\n\000\022\004\b\r\020\003\"\026\020\016\032\0020\0078\000XT¢\006\b\n\000\022\004\b\017\020\003*\n\020\023\"\0020\0242\0020\024*\034\020\025\032\004\b\000\020\026\"\b\022\004\022\002H\0260\0272\b\022\004\022\002H\0260\027*\f\b\002\020\030\"\0020\0212\0020\021*\n\020\031\"\0020\0322\0020\032*\034\020\033\032\004\b\000\020\026\"\b\022\004\022\002H\0260\0342\b\022\004\022\002H\0260\034¨\006\035"}, d2={"CONDITION_FALSE", "", "getCONDITION_FALSE$annotations", "()V", "getCONDITION_FALSE", "()Ljava/lang/Object;", "FAILURE", "", "getFAILURE$annotations", "LIST_EMPTY", "getLIST_EMPTY$annotations", "getLIST_EMPTY", "SUCCESS", "getSUCCESS$annotations", "UNDECIDED", "getUNDECIDED$annotations", "unwrap", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "AbstractAtomicDesc", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;", "AddLastDesc", "T", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Node", "PrepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "RemoveFirstDesc", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class LockFreeLinkedListKt
{
  private static final Object CONDITION_FALSE = new Symbol("CONDITION_FALSE");
  public static final int FAILURE = 2;
  private static final Object LIST_EMPTY = new Symbol("LIST_EMPTY");
  public static final int SUCCESS = 1;
  public static final int UNDECIDED = 0;
  
  public static final Object getCONDITION_FALSE()
  {
    return CONDITION_FALSE;
  }
  
  public static final Object getLIST_EMPTY()
  {
    return LIST_EMPTY;
  }
  
  public static final LockFreeLinkedListNode unwrap(Object paramObject)
  {
    boolean bool = paramObject instanceof Removed;
    Object localObject2 = null;
    Object localObject1;
    if (bool) {
      localObject1 = (Removed)paramObject;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = ((Removed)localObject1).ref;
    }
    localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = (LockFreeLinkedListNode)paramObject;
    }
    return (LockFreeLinkedListNode)localObject2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LockFreeLinkedListKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */