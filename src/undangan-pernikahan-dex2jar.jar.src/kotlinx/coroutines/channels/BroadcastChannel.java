package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;

@Metadata(d1={"\000*\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\003\n\002\020\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\bg\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002J\024\020\003\032\0020\0042\n\b\002\020\005\032\004\030\0010\006H'J\032\020\003\032\0020\0072\020\b\002\020\005\032\n\030\0010\bj\004\030\001`\tH&J\016\020\n\032\b\022\004\022\0028\0000\013H&¨\006\f"}, d2={"Lkotlinx/coroutines/channels/BroadcastChannel;", "E", "Lkotlinx/coroutines/channels/SendChannel;", "cancel", "", "cause", "", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface BroadcastChannel<E>
  extends SendChannel<E>
{
  public abstract void cancel(CancellationException paramCancellationException);
  
  public abstract ReceiveChannel<E> openSubscription();
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySend' method", replaceWith=@ReplaceWith(expression="trySend(element).isSuccess", imports={}))
    public static <E> boolean offer(BroadcastChannel<E> paramBroadcastChannel, E paramE)
    {
      return SendChannel.DefaultImpls.offer((SendChannel)paramBroadcastChannel, paramE);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/BroadcastChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */