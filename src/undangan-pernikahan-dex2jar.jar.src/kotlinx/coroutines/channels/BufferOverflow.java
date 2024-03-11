package kotlinx.coroutines.channels;

import kotlin.Metadata;

@Metadata(d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\005\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004j\002\b\005¨\006\006"}, d2={"Lkotlinx/coroutines/channels/BufferOverflow;", "", "(Ljava/lang/String;I)V", "SUSPEND", "DROP_OLDEST", "DROP_LATEST", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public enum BufferOverflow
{
  private static final BufferOverflow[] $VALUES = $values();
  
  static
  {
    DROP_OLDEST = new BufferOverflow("DROP_OLDEST", 1);
    DROP_LATEST = new BufferOverflow("DROP_LATEST", 2);
  }
  
  private BufferOverflow() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/BufferOverflow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */