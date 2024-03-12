package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\016\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\n\032\0020\013H\026R\024\020\002\032\0020\003X\004¢\006\b\n\000\032\004\b\002\020\005R\026\020\006\032\004\030\0010\0078VX\004¢\006\006\032\004\b\b\020\t¨\006\f"}, d2={"Lkotlinx/coroutines/Empty;", "Lkotlinx/coroutines/Incomplete;", "isActive", "", "(Z)V", "()Z", "list", "Lkotlinx/coroutines/NodeList;", "getList", "()Lkotlinx/coroutines/NodeList;", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class Empty
  implements Incomplete
{
  private final boolean isActive;
  
  public Empty(boolean paramBoolean)
  {
    this.isActive = paramBoolean;
  }
  
  public NodeList getList()
  {
    return null;
  }
  
  public boolean isActive()
  {
    return this.isActive;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Empty{");
    String str;
    if (isActive()) {
      str = "Active";
    } else {
      str = "New";
    }
    return str + '}';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/Empty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */