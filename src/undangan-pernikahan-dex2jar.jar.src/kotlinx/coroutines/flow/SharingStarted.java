package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(d1={"\000 \n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\b\n\002\b\002\bæ\001\030\000 \b2\0020\001:\001\bJ\034\020\002\032\b\022\004\022\0020\0040\0032\f\020\005\032\b\022\004\022\0020\0070\006H&¨\006\t"}, d2={"Lkotlinx/coroutines/flow/SharingStarted;", "", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "Companion", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface SharingStarted
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract Flow<SharingCommand> command(StateFlow<Integer> paramStateFlow);
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\006\n\002\020\t\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\032\020\t\032\0020\0042\b\b\002\020\n\032\0020\0132\b\b\002\020\f\032\0020\013R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006R\021\020\007\032\0020\004¢\006\b\n\000\032\004\b\b\020\006¨\006\r"}, d2={"Lkotlinx/coroutines/flow/SharingStarted$Companion;", "", "()V", "Eagerly", "Lkotlinx/coroutines/flow/SharingStarted;", "getEagerly", "()Lkotlinx/coroutines/flow/SharingStarted;", "Lazily", "getLazily", "WhileSubscribed", "stopTimeoutMillis", "", "replayExpirationMillis", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Companion
  {
    static final Companion $$INSTANCE = new Companion();
    private static final SharingStarted Eagerly = (SharingStarted)new StartedEagerly();
    private static final SharingStarted Lazily = (SharingStarted)new StartedLazily();
    
    public final SharingStarted WhileSubscribed(long paramLong1, long paramLong2)
    {
      return (SharingStarted)new StartedWhileSubscribed(paramLong1, paramLong2);
    }
    
    public final SharingStarted getEagerly()
    {
      return Eagerly;
    }
    
    public final SharingStarted getLazily()
    {
      return Lazily;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SharingStarted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */