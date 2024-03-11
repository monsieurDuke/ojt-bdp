package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\003\n\000\b\000\030\0002\0060\001j\002`\002B\021\022\n\020\003\032\006\022\002\b\0030\004¢\006\002\020\005J\b\020\b\032\0020\tH\026R\025\020\003\032\006\022\002\b\0030\004¢\006\b\n\000\032\004\b\006\020\007¨\006\n"}, d2={"Lkotlinx/coroutines/flow/internal/AbortFlowException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "owner", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;)V", "getOwner", "()Lkotlinx/coroutines/flow/FlowCollector;", "fillInStackTrace", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class AbortFlowException
  extends CancellationException
{
  private final FlowCollector<?> owner;
  
  public AbortFlowException(FlowCollector<?> paramFlowCollector)
  {
    super("Flow was aborted, no more elements needed");
    this.owner = paramFlowCollector;
  }
  
  public Throwable fillInStackTrace()
  {
    if (DebugKt.getDEBUG()) {
      return super.fillInStackTrace();
    }
    setStackTrace(new StackTraceElement[0]);
    return (Throwable)this;
  }
  
  public final FlowCollector<?> getOwner()
  {
    return this.owner;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/AbortFlowException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */