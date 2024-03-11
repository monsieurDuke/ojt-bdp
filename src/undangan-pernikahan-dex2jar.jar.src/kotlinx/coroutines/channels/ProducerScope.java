package kotlinx.coroutines.channels;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1={"\000\022\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\bf\030\000*\006\b\000\020\001 \0002\0020\0022\b\022\004\022\002H\0010\003R\030\020\004\032\b\022\004\022\0028\0000\003X¦\004¢\006\006\032\004\b\005\020\006¨\006\007"}, d2={"Lkotlinx/coroutines/channels/ProducerScope;", "E", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlinx/coroutines/channels/SendChannel;", "channel", "getChannel", "()Lkotlinx/coroutines/channels/SendChannel;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface ProducerScope<E>
  extends CoroutineScope, SendChannel<E>
{
  public abstract SendChannel<E> getChannel();
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySend' method", replaceWith=@ReplaceWith(expression="trySend(element).isSuccess", imports={}))
    public static <E> boolean offer(ProducerScope<? super E> paramProducerScope, E paramE)
    {
      return SendChannel.DefaultImpls.offer((SendChannel)paramProducerScope, paramE);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ProducerScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */