package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\016\n\002\b\003\b\000\030\0002\0020\0012\0020\002B\005¢\006\002\020\003J\016\020\n\032\0020\0132\006\020\f\032\0020\013J\b\020\r\032\0020\013H\026R\024\020\004\032\0020\0058VX\004¢\006\006\032\004\b\004\020\006R\024\020\007\032\0020\0008VX\004¢\006\006\032\004\b\b\020\t¨\006\016"}, d2={"Lkotlinx/coroutines/NodeList;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/Incomplete;", "()V", "isActive", "", "()Z", "list", "getList", "()Lkotlinx/coroutines/NodeList;", "getString", "", "state", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class NodeList
  extends LockFreeLinkedListHead
  implements Incomplete
{
  public NodeList getList()
  {
    return this;
  }
  
  public final String getString(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("List{");
    localStringBuilder.append(paramString);
    localStringBuilder.append("}[");
    int i = 1;
    LockFreeLinkedListHead localLockFreeLinkedListHead = (LockFreeLinkedListHead)this;
    paramString = (LockFreeLinkedListNode)localLockFreeLinkedListHead.getNext();
    while (!Intrinsics.areEqual(paramString, localLockFreeLinkedListHead))
    {
      int j = i;
      if ((paramString instanceof JobNode))
      {
        JobNode localJobNode = (JobNode)paramString;
        if (i != 0) {
          i = 0;
        } else {
          localStringBuilder.append(", ");
        }
        localStringBuilder.append(localJobNode);
        j = i;
      }
      paramString = paramString.getNextNode();
      i = j;
    }
    localStringBuilder.append("]");
    paramString = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramString, "StringBuilder().apply(builderAction).toString()");
    return paramString;
  }
  
  public boolean isActive()
  {
    return true;
  }
  
  public String toString()
  {
    String str;
    if (DebugKt.getDEBUG()) {
      str = getString("Active");
    } else {
      str = super.toString();
    }
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/NodeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */