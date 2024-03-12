package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000\032\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\000\032\021\020\000\032\0020\0012\006\020\002\032\0020\001H\b\032\030\020\003\032\0020\004*\0020\0052\n\020\006\032\006\022\002\b\0030\007H\000¨\006\b"}, d2={"checkIndexOverflow", "", "index", "checkOwnership", "", "Lkotlinx/coroutines/flow/internal/AbortFlowException;", "owner", "Lkotlinx/coroutines/flow/FlowCollector;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class FlowExceptions_commonKt
{
  public static final int checkIndexOverflow(int paramInt)
  {
    if (paramInt >= 0) {
      return paramInt;
    }
    throw new ArithmeticException("Index overflow has happened");
  }
  
  public static final void checkOwnership(AbortFlowException paramAbortFlowException, FlowCollector<?> paramFlowCollector)
  {
    if (paramAbortFlowException.getOwner() == paramFlowCollector) {
      return;
    }
    throw paramAbortFlowException;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/FlowExceptions_commonKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */