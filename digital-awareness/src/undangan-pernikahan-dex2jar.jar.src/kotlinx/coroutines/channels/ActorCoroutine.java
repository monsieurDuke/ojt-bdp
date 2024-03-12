package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\003\n\000\n\002\020\002\n\002\b\002\b\022\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B#\022\006\020\004\032\0020\005\022\f\020\006\032\b\022\004\022\0028\0000\007\022\006\020\b\032\0020\t¢\006\002\020\nJ\020\020\013\032\0020\t2\006\020\f\032\0020\rH\024J\022\020\016\032\0020\0172\b\020\020\032\004\030\0010\rH\024¨\006\021"}, d2={"Lkotlinx/coroutines/channels/ActorCoroutine;", "E", "Lkotlinx/coroutines/channels/ChannelCoroutine;", "Lkotlinx/coroutines/channels/ActorScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;Z)V", "handleJobException", "exception", "", "onCancelling", "", "cause", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
class ActorCoroutine<E>
  extends ChannelCoroutine<E>
  implements ActorScope<E>
{
  public ActorCoroutine(CoroutineContext paramCoroutineContext, Channel<E> paramChannel, boolean paramBoolean)
  {
    super(paramCoroutineContext, paramChannel, false, paramBoolean);
    initParentJob((Job)paramCoroutineContext.get((CoroutineContext.Key)Job.Key));
  }
  
  protected boolean handleJobException(Throwable paramThrowable)
  {
    CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), paramThrowable);
    return true;
  }
  
  protected void onCancelling(Throwable paramThrowable)
  {
    Channel localChannel = get_channel();
    CancellationException localCancellationException = null;
    Object localObject = null;
    if (paramThrowable != null) {
      for (;;)
      {
        if ((paramThrowable instanceof CancellationException)) {
          localCancellationException = (CancellationException)paramThrowable;
        }
        localObject = localCancellationException;
        if (localCancellationException == null)
        {
          localObject = DebugStringsKt.getClassSimpleName(this);
          Log5ECF72.a(localObject);
          LogE84000.a(localObject);
          Log229316.a(localObject);
          localObject = Intrinsics.stringPlus((String)localObject, " was cancelled");
          Log5ECF72.a(localObject);
          LogE84000.a(localObject);
          Log229316.a(localObject);
          localObject = ExceptionsKt.CancellationException((String)localObject, paramThrowable);
        }
      }
    }
    localChannel.cancel((CancellationException)localObject);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ActorCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */