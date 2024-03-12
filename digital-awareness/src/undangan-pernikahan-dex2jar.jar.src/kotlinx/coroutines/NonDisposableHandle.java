package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\0000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\003\n\000\n\002\020\002\n\000\n\002\020\016\n\000\bÇ\002\030\0002\0020\0012\0020\002B\007\b\002¢\006\002\020\003J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\b\020\f\032\0020\rH\026J\b\020\016\032\0020\017H\026R\026\020\004\032\004\030\0010\0058VX\004¢\006\006\032\004\b\006\020\007¨\006\020"}, d2={"Lkotlinx/coroutines/NonDisposableHandle;", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/ChildHandle;", "()V", "parent", "Lkotlinx/coroutines/Job;", "getParent", "()Lkotlinx/coroutines/Job;", "childCancelled", "", "cause", "", "dispose", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class NonDisposableHandle
  implements DisposableHandle, ChildHandle
{
  public static final NonDisposableHandle INSTANCE = new NonDisposableHandle();
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    return false;
  }
  
  public void dispose() {}
  
  public Job getParent()
  {
    return null;
  }
  
  public String toString()
  {
    return "NonDisposableHandle";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/NonDisposableHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */