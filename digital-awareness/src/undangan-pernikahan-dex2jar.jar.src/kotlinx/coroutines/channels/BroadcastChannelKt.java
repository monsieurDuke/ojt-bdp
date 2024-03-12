package kotlinx.coroutines.channels;

import kotlin.Metadata;

@Metadata(d1={"\000\020\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\032\034\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\003\032\0020\004H\007¨\006\005"}, d2={"BroadcastChannel", "Lkotlinx/coroutines/channels/BroadcastChannel;", "E", "capacity", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class BroadcastChannelKt
{
  public static final <E> BroadcastChannel<E> BroadcastChannel(int paramInt)
  {
    BroadcastChannel localBroadcastChannel;
    switch (paramInt)
    {
    default: 
      localBroadcastChannel = (BroadcastChannel)new ArrayBroadcastChannel(paramInt);
      break;
    case 2147483647: 
      throw new IllegalArgumentException("Unsupported UNLIMITED capacity for BroadcastChannel");
    case 0: 
      throw new IllegalArgumentException("Unsupported 0 capacity for BroadcastChannel");
    case -1: 
      localBroadcastChannel = (BroadcastChannel)new ConflatedBroadcastChannel();
      break;
    case -2: 
      localBroadcastChannel = (BroadcastChannel)new ArrayBroadcastChannel(Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core());
    }
    return localBroadcastChannel;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/BroadcastChannelKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */