package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\002R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlinx/coroutines/DisposeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/DisposableHandle;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DisposeOnCompletion
  extends JobNode
{
  private final DisposableHandle handle;
  
  public DisposeOnCompletion(DisposableHandle paramDisposableHandle)
  {
    this.handle = paramDisposableHandle;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.handle.dispose();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DisposeOnCompletion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */