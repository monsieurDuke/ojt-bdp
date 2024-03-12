package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlow;

@Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\002\020\b\n\002\030\002\n\002\b\006\n\002\020\013\n\002\b\002\b\002\030\0002\b\022\004\022\0020\0020\0012\b\022\004\022\0020\0020\003B\r\022\006\020\004\032\0020\002¢\006\002\020\005J\016\020\t\032\0020\n2\006\020\013\032\0020\002R\024\020\006\032\0020\0028VX\004¢\006\006\032\004\b\007\020\b¨\006\f"}, d2={"Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "initialValue", "(I)V", "value", "getValue", "()Ljava/lang/Integer;", "increment", "", "delta", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class SubscriptionCountStateFlow
  extends SharedFlowImpl<Integer>
  implements StateFlow<Integer>
{
  public SubscriptionCountStateFlow(int paramInt)
  {
    super(1, Integer.MAX_VALUE, BufferOverflow.DROP_OLDEST);
    tryEmit(Integer.valueOf(paramInt));
  }
  
  public Integer getValue()
  {
    try
    {
      int i = ((Number)getLastReplayedLocked()).intValue();
      return Integer.valueOf(i);
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean increment(int paramInt)
  {
    try
    {
      boolean bool = tryEmit(Integer.valueOf(((Number)getLastReplayedLocked()).intValue() + paramInt));
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/SubscriptionCountStateFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */