package kotlinx.coroutines.channels;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(d1={"\000<\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\004\n\002\020\003\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\003\bf\030\000*\006\b\000\020\001 \0002\0020\002J\024\020\f\032\0020\0042\n\b\002\020\r\032\004\030\0010\016H&J-\020\017\032\0020\0202#\020\021\032\037\022\025\022\023\030\0010\016¢\006\f\b\023\022\b\b\024\022\004\b\b(\r\022\004\022\0020\0200\022H'J\025\020\025\032\0020\0042\006\020\026\032\0028\000H\027¢\006\002\020\027J\031\020\030\032\0020\0202\006\020\026\032\0028\000H¦@ø\001\000¢\006\002\020\031J&\020\032\032\b\022\004\022\0020\0200\0332\006\020\026\032\0028\000H&ø\001\000ø\001\001ø\001\002¢\006\004\b\034\020\035R\032\020\003\032\0020\0048&X§\004¢\006\f\022\004\b\005\020\006\032\004\b\003\020\007R$\020\b\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000\0000\tX¦\004¢\006\006\032\004\b\n\020\013\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006\036"}, d2={"Lkotlinx/coroutines/channels/SendChannel;", "E", "", "isClosedForSend", "", "isClosedForSend$annotations", "()V", "()Z", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "close", "cause", "", "invokeOnClose", "", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "offer", "element", "(Ljava/lang/Object;)Z", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "trySend", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface SendChannel<E>
{
  public abstract boolean close(Throwable paramThrowable);
  
  public abstract SelectClause2<E, SendChannel<E>> getOnSend();
  
  public abstract void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1);
  
  public abstract boolean isClosedForSend();
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySend' method", replaceWith=@ReplaceWith(expression="trySend(element).isSuccess", imports={}))
  public abstract boolean offer(E paramE);
  
  public abstract Object send(E paramE, Continuation<? super Unit> paramContinuation);
  
  public abstract Object trySend-JP2dKIU(E paramE);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySend' method", replaceWith=@ReplaceWith(expression="trySend(element).isSuccess", imports={}))
    public static <E> boolean offer(SendChannel<? super E> paramSendChannel, E paramE)
    {
      paramSendChannel = paramSendChannel.trySend-JP2dKIU(paramE);
      if (ChannelResult.isSuccess-impl(paramSendChannel)) {
        return true;
      }
      paramSendChannel = ChannelResult.exceptionOrNull-impl(paramSendChannel);
      if (paramSendChannel == null) {
        return false;
      }
      throw StackTraceRecoveryKt.recoverStackTrace(paramSendChannel);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/SendChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */