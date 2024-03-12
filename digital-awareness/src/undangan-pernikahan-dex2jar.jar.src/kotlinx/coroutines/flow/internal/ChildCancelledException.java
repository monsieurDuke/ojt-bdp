package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;

@Metadata(d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\003\n\000\b\000\030\0002\0060\001j\002`\002B\005¢\006\002\020\003J\b\020\004\032\0020\005H\026¨\006\006"}, d2={"Lkotlinx/coroutines/flow/internal/ChildCancelledException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "()V", "fillInStackTrace", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ChildCancelledException
  extends CancellationException
{
  public ChildCancelledException()
  {
    super("Child of the scoped flow was cancelled");
  }
  
  public Throwable fillInStackTrace()
  {
    if (DebugKt.getDEBUG()) {
      return super.fillInStackTrace();
    }
    setStackTrace(new StackTraceElement[0]);
    return (Throwable)this;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/ChildCancelledException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */