package kotlinx.coroutines.channels;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(d1={"kotlinx/coroutines/channels/ChannelsKt__ChannelsKt", "kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt", "kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt"}, k=4, mv={1, 6, 0}, xi=48)
public final class ChannelsKt
{
  public static final String DEFAULT_CLOSE_MESSAGE = "Channel was closed";
  
  public static final void cancelConsumed(ReceiveChannel<?> paramReceiveChannel, Throwable paramThrowable)
  {
    ChannelsKt__Channels_commonKt.cancelConsumed(paramReceiveChannel, paramThrowable);
  }
  
  public static final <E, R> R consume(BroadcastChannel<E> paramBroadcastChannel, Function1<? super ReceiveChannel<? extends E>, ? extends R> paramFunction1)
  {
    return (R)ChannelsKt__Channels_commonKt.consume(paramBroadcastChannel, paramFunction1);
  }
  
  public static final <E, R> R consume(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super ReceiveChannel<? extends E>, ? extends R> paramFunction1)
  {
    return (R)ChannelsKt__Channels_commonKt.consume(paramReceiveChannel, paramFunction1);
  }
  
  public static final <E> Object consumeEach(BroadcastChannel<E> paramBroadcastChannel, Function1<? super E, Unit> paramFunction1, Continuation<? super Unit> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEach(paramBroadcastChannel, paramFunction1, paramContinuation);
  }
  
  public static final <E> Object consumeEach(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Unit> paramFunction1, Continuation<? super Unit> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEach(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  public static final Function1<Throwable, Unit> consumes(ReceiveChannel<?> paramReceiveChannel)
  {
    return ChannelsKt__DeprecatedKt.consumes(paramReceiveChannel);
  }
  
  public static final Function1<Throwable, Unit> consumesAll(ReceiveChannel<?>... paramVarArgs)
  {
    return ChannelsKt__DeprecatedKt.consumesAll(paramVarArgs);
  }
  
  public static final <E, K> ReceiveChannel<E> distinctBy(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super K>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__DeprecatedKt.distinctBy(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  public static final <E> ReceiveChannel<E> filter(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__DeprecatedKt.filter(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  public static final <E> ReceiveChannel<E> filterNotNull(ReceiveChannel<? extends E> paramReceiveChannel)
  {
    return ChannelsKt__DeprecatedKt.filterNotNull(paramReceiveChannel);
  }
  
  public static final <E, R> ReceiveChannel<R> map(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__DeprecatedKt.map(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  public static final <E, R> ReceiveChannel<R> mapIndexed(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return ChannelsKt__DeprecatedKt.mapIndexed(paramReceiveChannel, paramCoroutineContext, paramFunction3);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'onReceiveCatching'")
  public static final <E> SelectClause1<E> onReceiveOrNull(ReceiveChannel<? extends E> paramReceiveChannel)
  {
    return ChannelsKt__Channels_commonKt.onReceiveOrNull(paramReceiveChannel);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'receiveCatching'", replaceWith=@ReplaceWith(expression="receiveCatching().getOrNull()", imports={}))
  public static final <E> Object receiveOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.receiveOrNull(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySendBlocking'. Consider handling the result of 'trySendBlocking' explicitly and rethrow exception if necessary", replaceWith=@ReplaceWith(expression="trySendBlocking(element)", imports={}))
  public static final <E> void sendBlocking(SendChannel<? super E> paramSendChannel, E paramE)
  {
    ChannelsKt__ChannelsKt.sendBlocking(paramSendChannel, paramE);
  }
  
  public static final <E, C extends SendChannel<? super E>> Object toChannel(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__DeprecatedKt.toChannel(paramReceiveChannel, paramC, paramContinuation);
  }
  
  public static final <E, C extends Collection<? super E>> Object toCollection(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__DeprecatedKt.toCollection(paramReceiveChannel, paramC, paramContinuation);
  }
  
  public static final <E> Object toList(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super List<? extends E>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toList(paramReceiveChannel, paramContinuation);
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> Object toMap(ReceiveChannel<? extends Pair<? extends K, ? extends V>> paramReceiveChannel, M paramM, Continuation<? super M> paramContinuation)
  {
    return ChannelsKt__DeprecatedKt.toMap(paramReceiveChannel, paramM, paramContinuation);
  }
  
  public static final <E> Object toMutableSet(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super Set<E>> paramContinuation)
  {
    return ChannelsKt__DeprecatedKt.toMutableSet(paramReceiveChannel, paramContinuation);
  }
  
  public static final <E> Object trySendBlocking(SendChannel<? super E> paramSendChannel, E paramE)
  {
    return ChannelsKt__ChannelsKt.trySendBlocking(paramSendChannel, paramE);
  }
  
  public static final <E, R, V> ReceiveChannel<V> zip(ReceiveChannel<? extends E> paramReceiveChannel, ReceiveChannel<? extends R> paramReceiveChannel1, CoroutineContext paramCoroutineContext, Function2<? super E, ? super R, ? extends V> paramFunction2)
  {
    return ChannelsKt__DeprecatedKt.zip(paramReceiveChannel, paramReceiveChannel1, paramCoroutineContext, paramFunction2);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ChannelsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */