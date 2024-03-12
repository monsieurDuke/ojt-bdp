package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause2;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000d\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\013\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\022\b\020\030\000*\004\b\000\020\0012\b\022\004\022\0020\0030\0022\b\022\004\022\002H\0010\004B+\022\006\020\005\032\0020\006\022\f\020\007\032\b\022\004\022\0028\0000\004\022\006\020\b\032\0020\t\022\006\020\n\032\0020\t¢\006\002\020\013J\b\020\"\032\0020\003H\026J\022\020\"\032\0020\t2\b\020#\032\004\030\0010$H\007J\026\020\"\032\0020\0032\016\020#\032\n\030\0010%j\004\030\001`&J\020\020'\032\0020\0032\006\020#\032\0020$H\026J\023\020(\032\0020\t2\b\020#\032\004\030\0010$H\001J.\020)\032\0020\0032#\020*\032\037\022\025\022\023\030\0010$¢\006\f\b,\022\b\b-\022\004\b\b(#\022\004\022\0020\0030+H\001J\017\020.\032\b\022\004\022\0028\0000/H\003J\026\0200\032\0020\t2\006\0201\032\0028\000H\001¢\006\002\0202J\020\0203\032\004\030\0018\000H\001¢\006\002\0204J\021\0205\032\0028\000HAø\001\000¢\006\002\0206J\"\0207\032\b\022\004\022\0028\0000\031HAø\001\000ø\001\000ø\001\001ø\001\002¢\006\004\b8\0206J\023\0209\032\004\030\0018\000HAø\001\000¢\006\002\0206J\031\020:\032\0020\0032\006\0201\032\0028\000HAø\001\000¢\006\002\020;J\037\020<\032\b\022\004\022\0028\0000\031H\001ø\001\000ø\001\001ø\001\002¢\006\004\b=\0204J'\020>\032\b\022\004\022\0020\0030\0312\006\0201\032\0028\000H\001ø\001\000ø\001\001ø\001\002¢\006\004\b?\020@R\032\020\007\032\b\022\004\022\0028\0000\004X\004¢\006\b\n\000\032\004\b\f\020\rR\027\020\016\032\b\022\004\022\0028\0000\0048F¢\006\006\032\004\b\017\020\rR\024\020\020\032\0020\t8\026X\005¢\006\006\032\004\b\020\020\021R\024\020\022\032\0020\t8\026X\005¢\006\006\032\004\b\022\020\021R\024\020\023\032\0020\t8\026X\005¢\006\006\032\004\b\023\020\021R\030\020\024\032\b\022\004\022\0028\0000\025X\005¢\006\006\032\004\b\026\020\027R!\020\030\032\016\022\n\022\b\022\004\022\0028\0000\0310\025X\005ø\001\000¢\006\006\032\004\b\032\020\027R\034\020\033\032\n\022\006\022\004\030\0018\0000\0258VX\005¢\006\006\032\004\b\034\020\027R$\020\035\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000\0370\036X\005¢\006\006\032\004\b \020!\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006A"}, d2={"Lkotlinx/coroutines/channels/ChannelCoroutine;", "E", "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/Channel;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "_channel", "initParentJob", "", "active", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;ZZ)V", "get_channel", "()Lkotlinx/coroutines/channels/Channel;", "channel", "getChannel", "isClosedForReceive", "()Z", "isClosedForSend", "isEmpty", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "close", "invokeOnClose", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "offer", "element", "(Ljava/lang/Object;)Z", "poll", "()Ljava/lang/Object;", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveOrNull", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryReceive", "tryReceive-PtdJZtk", "trySend", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class ChannelCoroutine<E>
  extends AbstractCoroutine<Unit>
  implements Channel<E>
{
  private final Channel<E> _channel;
  
  public ChannelCoroutine(CoroutineContext paramCoroutineContext, Channel<E> paramChannel, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramCoroutineContext, paramBoolean1, paramBoolean2);
    this._channel = paramChannel;
  }
  
  public final void cancel(CancellationException paramCancellationException)
  {
    if (isCancelled()) {
      return;
    }
    if (paramCancellationException == null)
    {
      paramCancellationException = JobSupport.access$cancellationExceptionMessage((JobSupport)this);
      Log5ECF72.a(paramCancellationException);
      LogE84000.a(paramCancellationException);
      Log229316.a(paramCancellationException);
      paramCancellationException = (CancellationException)new JobCancellationException(paramCancellationException, null, (Job)this);
    }
    cancelInternal((Throwable)paramCancellationException);
  }
  
  public void cancelInternal(Throwable paramThrowable)
  {
    paramThrowable = JobSupport.toCancellationException$default((JobSupport)this, paramThrowable, null, 1, null);
    this._channel.cancel(paramThrowable);
    cancelCoroutine((Throwable)paramThrowable);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    return this._channel.close(paramThrowable);
  }
  
  public final Channel<E> getChannel()
  {
    return (Channel)this;
  }
  
  public SelectClause1<E> getOnReceive()
  {
    return this._channel.getOnReceive();
  }
  
  public SelectClause1<ChannelResult<E>> getOnReceiveCatching()
  {
    return this._channel.getOnReceiveCatching();
  }
  
  public SelectClause1<E> getOnReceiveOrNull()
  {
    return this._channel.getOnReceiveOrNull();
  }
  
  public SelectClause2<E, SendChannel<E>> getOnSend()
  {
    return this._channel.getOnSend();
  }
  
  protected final Channel<E> get_channel()
  {
    return this._channel;
  }
  
  public void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1)
  {
    this._channel.invokeOnClose(paramFunction1);
  }
  
  public boolean isClosedForReceive()
  {
    return this._channel.isClosedForReceive();
  }
  
  public boolean isClosedForSend()
  {
    return this._channel.isClosedForSend();
  }
  
  public boolean isEmpty()
  {
    return this._channel.isEmpty();
  }
  
  public ChannelIterator<E> iterator()
  {
    return this._channel.iterator();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySend' method", replaceWith=@ReplaceWith(expression="trySend(element).isSuccess", imports={}))
  public boolean offer(E paramE)
  {
    return this._channel.offer(paramE);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith=@ReplaceWith(expression="tryReceive().getOrNull()", imports={}))
  public E poll()
  {
    return (E)this._channel.poll();
  }
  
  public Object receive(Continuation<? super E> paramContinuation)
  {
    return this._channel.receive(paramContinuation);
  }
  
  public Object receiveCatching-JP2dKIU(Continuation<? super ChannelResult<? extends E>> paramContinuation)
  {
    paramContinuation = this._channel.receiveCatching-JP2dKIU(paramContinuation);
    IntrinsicsKt.getCOROUTINE_SUSPENDED();
    return paramContinuation;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith=@ReplaceWith(expression="receiveCatching().getOrNull()", imports={}))
  public Object receiveOrNull(Continuation<? super E> paramContinuation)
  {
    return this._channel.receiveOrNull(paramContinuation);
  }
  
  public Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    return this._channel.send(paramE, paramContinuation);
  }
  
  public Object tryReceive-PtdJZtk()
  {
    return this._channel.tryReceive-PtdJZtk();
  }
  
  public Object trySend-JP2dKIU(E paramE)
  {
    return this._channel.trySend-JP2dKIU(paramE);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ChannelCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */