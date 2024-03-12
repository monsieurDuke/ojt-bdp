package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;

@Deprecated(level=DeprecationLevel.ERROR, message="This is internal API and may be removed in the future releases")
@Metadata(d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\000\n\002\020\003\n\000\bg\030\0002\0020\001J\020\020\b\032\0020\t2\006\020\n\032\0020\013H'R\034\020\002\032\004\030\0010\0038&X§\004¢\006\f\022\004\b\004\020\005\032\004\b\006\020\007¨\006\f"}, d2={"Lkotlinx/coroutines/ChildHandle;", "Lkotlinx/coroutines/DisposableHandle;", "parent", "Lkotlinx/coroutines/Job;", "getParent$annotations", "()V", "getParent", "()Lkotlinx/coroutines/Job;", "childCancelled", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface ChildHandle
  extends DisposableHandle
{
  public abstract boolean childCancelled(Throwable paramThrowable);
  
  public abstract Job getParent();
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ChildHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */