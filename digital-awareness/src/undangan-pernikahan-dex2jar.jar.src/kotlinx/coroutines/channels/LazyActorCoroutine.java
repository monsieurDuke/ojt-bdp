package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1={"\000Z\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\006\n\002\020\013\n\000\n\002\020\003\n\002\b\007\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\024\022\004\022\002H\001\022\n\022\b\022\004\022\002H\0010\0040\003BM\022\006\020\005\032\0020\006\022\f\020\007\032\b\022\004\022\0028\0000\b\022-\020\t\032)\b\001\022\n\022\b\022\004\022\0028\0000\013\022\n\022\b\022\004\022\0020\r0\f\022\006\022\004\030\0010\0160\n¢\006\002\b\017ø\001\000¢\006\002\020\020J\022\020\025\032\0020\0262\b\020\027\032\004\030\0010\030H\026J\025\020\031\032\0020\0262\006\020\032\032\0028\000H\026¢\006\002\020\033J\b\020\034\032\0020\rH\024JV\020\035\032\0020\r\"\004\b\001\020\0362\f\020\037\032\b\022\004\022\002H\0360 2\006\020!\032\0028\0002(\020\t\032$\b\001\022\n\022\b\022\004\022\0028\0000\004\022\n\022\b\022\004\022\002H\0360\f\022\006\022\004\030\0010\0160\nH\026ø\001\000¢\006\002\020\"J\031\020#\032\0020\r2\006\020\032\032\0028\000H@ø\001\000¢\006\002\020$J&\020%\032\b\022\004\022\0020\r0&2\006\020\032\032\0028\000H\026ø\001\000ø\001\001ø\001\002¢\006\004\b'\020(R\024\020\021\032\b\022\004\022\0020\r0\fX\016¢\006\002\n\000R&\020\022\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000\0040\0038VX\004¢\006\006\032\004\b\023\020\024\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006)"}, d2={"Lkotlinx/coroutines/channels/LazyActorCoroutine;", "E", "Lkotlinx/coroutines/channels/ActorCoroutine;", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ActorScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;Lkotlin/jvm/functions/Function2;)V", "continuation", "onSend", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "close", "", "cause", "", "offer", "element", "(Ljava/lang/Object;)Z", "onStart", "registerSelectClause2", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "param", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "trySend", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class LazyActorCoroutine<E>
  extends ActorCoroutine<E>
  implements SelectClause2<E, SendChannel<? super E>>
{
  private Continuation<? super Unit> continuation = IntrinsicsKt.createCoroutineUnintercepted(paramFunction2, this, (Continuation)this);
  
  public LazyActorCoroutine(CoroutineContext paramCoroutineContext, Channel<E> paramChannel, Function2<? super ActorScope<E>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    super(paramCoroutineContext, paramChannel, false);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    boolean bool = super.close(paramThrowable);
    start();
    return bool;
  }
  
  public SelectClause2<E, SendChannel<E>> getOnSend()
  {
    return (SelectClause2)this;
  }
  
  public boolean offer(E paramE)
  {
    start();
    return super.offer(paramE);
  }
  
  protected void onStart()
  {
    CancellableKt.startCoroutineCancellable(this.continuation, (Continuation)this);
  }
  
  public <R> void registerSelectClause2(SelectInstance<? super R> paramSelectInstance, E paramE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    start();
    super.getOnSend().registerSelectClause2(paramSelectInstance, paramE, paramFunction2);
  }
  
  public Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    start();
    paramE = super.send(paramE, paramContinuation);
    if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramE;
    }
    return Unit.INSTANCE;
  }
  
  public Object trySend-JP2dKIU(E paramE)
  {
    start();
    return super.trySend-JP2dKIU(paramE);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/LazyActorCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */