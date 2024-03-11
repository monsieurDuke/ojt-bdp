package kotlinx.coroutines.channels;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(d1={"\000\030\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\bg\030\000*\004\b\000\020\0012\0020\0022\b\022\004\022\002H\0010\003R\030\020\004\032\b\022\004\022\0028\0000\005X¦\004¢\006\006\032\004\b\006\020\007¨\006\b"}, d2={"Lkotlinx/coroutines/channels/ActorScope;", "E", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "channel", "Lkotlinx/coroutines/channels/Channel;", "getChannel", "()Lkotlinx/coroutines/channels/Channel;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface ActorScope<E>
  extends CoroutineScope, ReceiveChannel<E>
{
  public abstract Channel<E> getChannel();
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <E> SelectClause1<E> getOnReceiveOrNull(ActorScope<E> paramActorScope)
    {
      return ReceiveChannel.DefaultImpls.getOnReceiveOrNull((ReceiveChannel)paramActorScope);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith=@ReplaceWith(expression="tryReceive().getOrNull()", imports={}))
    public static <E> E poll(ActorScope<E> paramActorScope)
    {
      return (E)ReceiveChannel.DefaultImpls.poll((ReceiveChannel)paramActorScope);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith=@ReplaceWith(expression="receiveCatching().getOrNull()", imports={}))
    public static <E> Object receiveOrNull(ActorScope<E> paramActorScope, Continuation<? super E> paramContinuation)
    {
      return ReceiveChannel.DefaultImpls.receiveOrNull((ReceiveChannel)paramActorScope, paramContinuation);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ActorScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */