package kotlinx.coroutines.channels;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.SystemPropsKt;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(d1={"\000\022\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\bf\030\000 \004*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003:\001\004¨\006\005"}, d2={"Lkotlinx/coroutines/channels/Channel;", "E", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Factory", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface Channel<E>
  extends SendChannel<E>, ReceiveChannel<E>
{
  public static final int BUFFERED = -2;
  public static final int CONFLATED = -1;
  public static final String DEFAULT_BUFFER_PROPERTY_NAME = "kotlinx.coroutines.channels.defaultBuffer";
  public static final Factory Factory = Factory.$$INSTANCE;
  public static final int OPTIONAL_CHANNEL = -3;
  public static final int RENDEZVOUS = 0;
  public static final int UNLIMITED = Integer.MAX_VALUE;
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <E> SelectClause1<E> getOnReceiveOrNull(Channel<E> paramChannel)
    {
      return ReceiveChannel.DefaultImpls.getOnReceiveOrNull((ReceiveChannel)paramChannel);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySend' method", replaceWith=@ReplaceWith(expression="trySend(element).isSuccess", imports={}))
    public static <E> boolean offer(Channel<E> paramChannel, E paramE)
    {
      return SendChannel.DefaultImpls.offer((SendChannel)paramChannel, paramE);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith=@ReplaceWith(expression="tryReceive().getOrNull()", imports={}))
    public static <E> E poll(Channel<E> paramChannel)
    {
      return (E)ReceiveChannel.DefaultImpls.poll((ReceiveChannel)paramChannel);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith=@ReplaceWith(expression="receiveCatching().getOrNull()", imports={}))
    public static <E> Object receiveOrNull(Channel<E> paramChannel, Continuation<? super E> paramContinuation)
    {
      return ReceiveChannel.DefaultImpls.receiveOrNull((ReceiveChannel)paramChannel, paramContinuation);
    }
  }
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\005\n\002\020\016\n\002\b\004\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\024\020\005\032\0020\004X\004¢\006\b\n\000\032\004\b\006\020\007R\016\020\b\032\0020\004XT¢\006\002\n\000R\016\020\t\032\0020\nXT¢\006\002\n\000R\016\020\013\032\0020\004XT¢\006\002\n\000R\016\020\f\032\0020\004XT¢\006\002\n\000R\016\020\r\032\0020\004XT¢\006\002\n\000¨\006\016"}, d2={"Lkotlinx/coroutines/channels/Channel$Factory;", "", "()V", "BUFFERED", "", "CHANNEL_DEFAULT_CAPACITY", "getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core", "()I", "CONFLATED", "DEFAULT_BUFFER_PROPERTY_NAME", "", "OPTIONAL_CHANNEL", "RENDEZVOUS", "UNLIMITED", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Factory
  {
    static final Factory $$INSTANCE = new Factory();
    public static final int BUFFERED = -2;
    private static final int CHANNEL_DEFAULT_CAPACITY = SystemPropsKt.systemProp("kotlinx.coroutines.channels.defaultBuffer", 64, 1, 2147483646);
    public static final int CONFLATED = -1;
    public static final String DEFAULT_BUFFER_PROPERTY_NAME = "kotlinx.coroutines.channels.defaultBuffer";
    public static final int OPTIONAL_CHANNEL = -3;
    public static final int RENDEZVOUS = 0;
    public static final int UNLIMITED = Integer.MAX_VALUE;
    
    public final int getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core()
    {
      return CHANNEL_DEFAULT_CAPACITY;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/Channel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */