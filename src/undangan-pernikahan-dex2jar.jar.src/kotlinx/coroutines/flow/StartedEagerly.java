package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\b\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\005¢\006\002\020\002J\034\020\003\032\b\022\004\022\0020\0050\0042\f\020\006\032\b\022\004\022\0020\b0\007H\026J\b\020\t\032\0020\nH\026¨\006\013"}, d2={"Lkotlinx/coroutines/flow/StartedEagerly;", "Lkotlinx/coroutines/flow/SharingStarted;", "()V", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class StartedEagerly
  implements SharingStarted
{
  public Flow<SharingCommand> command(StateFlow<Integer> paramStateFlow)
  {
    return FlowKt.flowOf(SharingCommand.START);
  }
  
  public String toString()
  {
    return "SharingStarted.Eagerly";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/StartedEagerly.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */