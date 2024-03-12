package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1={"\000@\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\013\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\020\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\n\bf\030\000*\006\b\000\020\001 \0012\0020\002J\b\020\024\032\0020\025H\027J\024\020\024\032\0020\0042\n\b\002\020\026\032\004\030\0010\027H'J\032\020\024\032\0020\0252\020\b\002\020\026\032\n\030\0010\030j\004\030\001`\031H&J\017\020\032\032\b\022\004\022\0028\0000\033H¦\002J\017\020\034\032\004\030\0018\000H\027¢\006\002\020\035J\021\020\036\032\0028\000H¦@ø\001\000¢\006\002\020\037J\"\020 \032\b\022\004\022\0028\0000\017H¦@ø\001\000ø\001\000ø\001\001ø\001\002¢\006\004\b!\020\037J\023\020\"\032\004\030\0018\000H@ø\001\000¢\006\002\020\037J\036\020#\032\b\022\004\022\0028\0000\017H&ø\001\000ø\001\001ø\001\002¢\006\004\b$\020\035R\032\020\003\032\0020\0048&X§\004¢\006\f\022\004\b\005\020\006\032\004\b\003\020\007R\032\020\b\032\0020\0048&X§\004¢\006\f\022\004\b\t\020\006\032\004\b\b\020\007R\030\020\n\032\b\022\004\022\0028\0000\013X¦\004¢\006\006\032\004\b\f\020\rR!\020\016\032\016\022\n\022\b\022\004\022\0028\0000\0170\013X¦\004ø\001\000¢\006\006\032\004\b\020\020\rR\"\020\021\032\n\022\006\022\004\030\0018\0000\0138VX\004¢\006\f\022\004\b\022\020\006\032\004\b\023\020\r\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006%"}, d2={"Lkotlinx/coroutines/channels/ReceiveChannel;", "E", "", "isClosedForReceive", "", "isClosedForReceive$annotations", "()V", "()Z", "isEmpty", "isEmpty$annotations", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull$annotations", "getOnReceiveOrNull", "cancel", "", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "poll", "()Ljava/lang/Object;", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveOrNull", "tryReceive", "tryReceive-PtdJZtk", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface ReceiveChannel<E>
{
  public abstract void cancel(CancellationException paramCancellationException);
  
  public abstract SelectClause1<E> getOnReceive();
  
  public abstract SelectClause1<ChannelResult<E>> getOnReceiveCatching();
  
  public abstract SelectClause1<E> getOnReceiveOrNull();
  
  public abstract boolean isClosedForReceive();
  
  public abstract boolean isEmpty();
  
  public abstract ChannelIterator<E> iterator();
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith=@ReplaceWith(expression="tryReceive().getOrNull()", imports={}))
  public abstract E poll();
  
  public abstract Object receive(Continuation<? super E> paramContinuation);
  
  public abstract Object receiveCatching-JP2dKIU(Continuation<? super ChannelResult<? extends E>> paramContinuation);
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith=@ReplaceWith(expression="receiveCatching().getOrNull()", imports={}))
  public abstract Object receiveOrNull(Continuation<? super E> paramContinuation);
  
  public abstract Object tryReceive-PtdJZtk();
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <E> SelectClause1<E> getOnReceiveOrNull(ReceiveChannel<? extends E> paramReceiveChannel)
    {
      return (SelectClause1)new ReceiveChannel.onReceiveOrNull.1(paramReceiveChannel);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith=@ReplaceWith(expression="tryReceive().getOrNull()", imports={}))
    public static <E> E poll(ReceiveChannel<? extends E> paramReceiveChannel)
    {
      paramReceiveChannel = paramReceiveChannel.tryReceive-PtdJZtk();
      if (ChannelResult.isSuccess-impl(paramReceiveChannel)) {
        return (E)ChannelResult.getOrThrow-impl(paramReceiveChannel);
      }
      paramReceiveChannel = ChannelResult.exceptionOrNull-impl(paramReceiveChannel);
      if (paramReceiveChannel == null) {
        return null;
      }
      throw StackTraceRecoveryKt.recoverStackTrace(paramReceiveChannel);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith=@ReplaceWith(expression="receiveCatching().getOrNull()", imports={}))
    public static <E> Object receiveOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
    {
      if ((paramContinuation instanceof ReceiveChannel.receiveOrNull.1))
      {
        localObject1 = (ReceiveChannel.receiveOrNull.1)paramContinuation;
        if ((((ReceiveChannel.receiveOrNull.1)localObject1).label & 0x80000000) != 0)
        {
          ((ReceiveChannel.receiveOrNull.1)localObject1).label += Integer.MIN_VALUE;
          paramContinuation = (Continuation<? super E>)localObject1;
          break label47;
        }
      }
      paramContinuation = new ReceiveChannel.receiveOrNull.1(paramContinuation);
      label47:
      Object localObject2 = paramContinuation.result;
      Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      switch (paramContinuation.label)
      {
      default: 
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      case 1: 
        ResultKt.throwOnFailure(localObject2);
        paramReceiveChannel = ((ChannelResult)localObject2).unbox-impl();
        break;
      case 0: 
        ResultKt.throwOnFailure(localObject2);
        paramContinuation.label = 1;
        paramContinuation = paramReceiveChannel.receiveCatching-JP2dKIU(paramContinuation);
        paramReceiveChannel = paramContinuation;
        if (paramContinuation == localObject1) {
          return localObject1;
        }
        break;
      }
      return ChannelResult.getOrNull-impl(paramReceiveChannel);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ReceiveChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */