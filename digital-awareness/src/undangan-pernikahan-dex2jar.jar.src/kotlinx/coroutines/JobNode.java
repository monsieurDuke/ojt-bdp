package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0008\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\016\n\000\b \030\0002\0020\0012\0020\0022\0020\003B\005¢\006\002\020\004J\b\020\022\032\0020\023H\026J\b\020\024\032\0020\025H\026R\024\020\005\032\0020\0068VX\004¢\006\006\032\004\b\005\020\007R\032\020\b\032\0020\tX.¢\006\016\n\000\032\004\b\n\020\013\"\004\b\f\020\rR\026\020\016\032\004\030\0010\0178VX\004¢\006\006\032\004\b\020\020\021¨\006\026"}, d2={"Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/CompletionHandlerBase;", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/Incomplete;", "()V", "isActive", "", "()Z", "job", "Lkotlinx/coroutines/JobSupport;", "getJob", "()Lkotlinx/coroutines/JobSupport;", "setJob", "(Lkotlinx/coroutines/JobSupport;)V", "list", "Lkotlinx/coroutines/NodeList;", "getList", "()Lkotlinx/coroutines/NodeList;", "dispose", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class JobNode
  extends CompletionHandlerBase
  implements DisposableHandle, Incomplete
{
  public JobSupport job;
  
  public void dispose()
  {
    getJob().removeNode$kotlinx_coroutines_core(this);
  }
  
  public final JobSupport getJob()
  {
    JobSupport localJobSupport = this.job;
    if (localJobSupport != null) {
      return localJobSupport;
    }
    Intrinsics.throwUninitializedPropertyAccessException("job");
    return null;
  }
  
  public NodeList getList()
  {
    return null;
  }
  
  public boolean isActive()
  {
    return true;
  }
  
  public final void setJob(JobSupport paramJobSupport)
  {
    this.job = paramJobSupport;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = DebugStringsKt.getClassSimpleName(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append('@');
    str = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append("[job@");
    str = DebugStringsKt.getHexAddress(getJob());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + ']';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/JobNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */