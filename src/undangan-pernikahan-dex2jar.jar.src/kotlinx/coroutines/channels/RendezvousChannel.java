package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\000$\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\b\020\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B'\022 \020\003\032\034\022\004\022\0028\000\022\004\022\0020\005\030\0010\004j\n\022\004\022\0028\000\030\001`\006¢\006\002\020\007R\024\020\b\032\0020\t8DX\004¢\006\006\032\004\b\b\020\nR\024\020\013\032\0020\t8DX\004¢\006\006\032\004\b\013\020\nR\024\020\f\032\0020\t8DX\004¢\006\006\032\004\b\f\020\nR\024\020\r\032\0020\t8DX\004¢\006\006\032\004\b\r\020\n¨\006\016"}, d2={"Lkotlinx/coroutines/channels/RendezvousChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class RendezvousChannel<E>
  extends AbstractChannel<E>
{
  public RendezvousChannel(Function1<? super E, Unit> paramFunction1)
  {
    super(paramFunction1);
  }
  
  protected final boolean isBufferAlwaysEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferAlwaysFull()
  {
    return true;
  }
  
  protected final boolean isBufferEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferFull()
  {
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/RendezvousChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */