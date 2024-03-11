package kotlinx.coroutines.channels;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000|\n\002\030\002\n\002\b\005\n\002\020\021\n\002\030\002\n\002\b\004\n\002\020\003\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\006\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\r\n\002\030\002\b\007\030\000 B*\004\b\000\020\0012\b\022\004\022\0028\0000G:\004CBDEB\021\b\026\022\006\020\002\032\0028\000¢\006\004\b\003\020\004B\007¢\006\004\b\003\020\005J?\020\n\032\016\022\n\022\b\022\004\022\0028\0000\0070\0062\024\020\b\032\020\022\n\022\b\022\004\022\0028\0000\007\030\0010\0062\f\020\t\032\b\022\004\022\0028\0000\007H\002¢\006\004\b\n\020\013J\031\020\017\032\0020\0162\b\020\r\032\004\030\0010\fH\027¢\006\004\b\017\020\020J\037\020\017\032\0020\0232\016\020\r\032\n\030\0010\021j\004\030\001`\022H\026¢\006\004\b\017\020\024J\031\020\025\032\0020\0162\b\020\r\032\004\030\0010\fH\026¢\006\004\b\025\020\020J\035\020\026\032\0020\0232\f\020\t\032\b\022\004\022\0028\0000\007H\002¢\006\004\b\026\020\027J)\020\033\032\0020\0232\030\020\032\032\024\022\006\022\004\030\0010\f\022\004\022\0020\0230\030j\002`\031H\026¢\006\004\b\033\020\034J\031\020\035\032\0020\0232\b\020\r\032\004\030\0010\fH\002¢\006\004\b\035\020\036J\031\020!\032\004\030\0010 2\006\020\037\032\0028\000H\002¢\006\004\b!\020\"J\025\020$\032\b\022\004\022\0028\0000#H\026¢\006\004\b$\020%JX\020.\032\0020\023\"\004\b\001\020&2\f\020(\032\b\022\004\022\0028\0010'2\006\020\037\032\0028\0002(\020-\032$\b\001\022\n\022\b\022\004\022\0028\0000*\022\n\022\b\022\004\022\0028\0010+\022\006\022\004\030\0010,0)H\002ø\001\000¢\006\004\b.\020/J?\0200\032\020\022\n\022\b\022\004\022\0028\0000\007\030\0010\0062\022\020\b\032\016\022\n\022\b\022\004\022\0028\0000\0070\0062\f\020\t\032\b\022\004\022\0028\0000\007H\002¢\006\004\b0\020\013J\033\0201\032\0020\0232\006\020\037\032\0028\000H@ø\001\000¢\006\004\b1\0202J&\0206\032\b\022\004\022\0020\023032\006\020\037\032\0028\000H\026ø\001\000ø\001\001ø\001\002¢\006\004\b4\0205R\024\0207\032\0020\0168VX\004¢\006\006\032\004\b7\0208R&\020<\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000*098VX\004¢\006\006\032\004\b:\020;R\027\020\002\032\0028\0008F¢\006\f\022\004\b?\020\005\032\004\b=\020>R\023\020A\032\004\030\0018\0008F¢\006\006\032\004\b@\020>\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006F"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "E", "value", "<init>", "(Ljava/lang/Object;)V", "()V", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "list", "subscriber", "addSubscriber", "([Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "close", "closeSubscriber", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "element", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "offerInternal", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "removeSubscriber", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "isClosedForSend", "()Z", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "getValue", "()Ljava/lang/Object;", "getValue$annotations", "getValueOrNull", "valueOrNull", "Companion", "Closed", "State", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k=1, mv={1, 6, 0}, xi=48)
public final class ConflatedBroadcastChannel<E>
  implements BroadcastChannel<E>
{
  @Deprecated
  private static final Closed CLOSED;
  private static final Companion Companion = new Companion(null);
  @Deprecated
  private static final State<Object> INITIAL_STATE;
  @Deprecated
  private static final Symbol UNDEFINED;
  private static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, Object.class, "_state");
  private static final AtomicIntegerFieldUpdater _updating$FU = AtomicIntegerFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, "_updating");
  private static final AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, Object.class, "onCloseHandler");
  private volatile Object _state = INITIAL_STATE;
  private volatile int _updating = 0;
  private volatile Object onCloseHandler = null;
  
  static
  {
    CLOSED = new Closed(null);
    Symbol localSymbol = new Symbol("UNDEFINED");
    UNDEFINED = localSymbol;
    INITIAL_STATE = new State(localSymbol, null);
  }
  
  public ConflatedBroadcastChannel() {}
  
  public ConflatedBroadcastChannel(E paramE)
  {
    this();
    _state$FU.lazySet(this, new State(paramE, null));
  }
  
  private final Subscriber<E>[] addSubscriber(Subscriber<E>[] paramArrayOfSubscriber, Subscriber<E> paramSubscriber)
  {
    if (paramArrayOfSubscriber == null)
    {
      int i = 0;
      paramArrayOfSubscriber = new Subscriber[1];
      while (i < 1)
      {
        paramArrayOfSubscriber[i] = paramSubscriber;
        i++;
      }
      return paramArrayOfSubscriber;
    }
    return (Subscriber[])ArraysKt.plus(paramArrayOfSubscriber, paramSubscriber);
  }
  
  private final void closeSubscriber(Subscriber<E> paramSubscriber)
  {
    Object localObject1;
    for (;;)
    {
      localObject1 = this._state;
      if ((localObject1 instanceof Closed)) {
        return;
      }
      if (!(localObject1 instanceof State)) {
        break;
      }
      Object localObject2 = ((State)localObject1).value;
      Subscriber[] arrayOfSubscriber = ((State)localObject1).subscribers;
      Intrinsics.checkNotNull(arrayOfSubscriber);
      localObject2 = new State(localObject2, removeSubscriber(arrayOfSubscriber, paramSubscriber));
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject1, localObject2)) {
        return;
      }
    }
    paramSubscriber = Intrinsics.stringPlus("Invalid state ", localObject1);
    Log5ECF72.a(paramSubscriber);
    LogE84000.a(paramSubscriber);
    Log229316.a(paramSubscriber);
    throw new IllegalStateException(paramSubscriber.toString());
  }
  
  private final void invokeOnCloseHandler(Throwable paramThrowable)
  {
    Object localObject = this.onCloseHandler;
    if ((localObject != null) && (localObject != AbstractChannelKt.HANDLER_INVOKED) && (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(onCloseHandler$FU, this, localObject, AbstractChannelKt.HANDLER_INVOKED))) {
      ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localObject, 1)).invoke(paramThrowable);
    }
  }
  
  private final Closed offerInternal(E paramE)
  {
    boolean bool = _updating$FU.compareAndSet(this, 0, 1);
    String str = null;
    if (!bool) {
      return null;
    }
    try
    {
      Object localObject1;
      for (;;)
      {
        localObject1 = this._state;
        if ((localObject1 instanceof Closed)) {}
        for (paramE = (Closed)localObject1;; paramE = str)
        {
          return paramE;
          if (!(localObject1 instanceof State)) {
            break label146;
          }
          Object localObject2 = new kotlinx/coroutines/channels/ConflatedBroadcastChannel$State;
          ((State)localObject2).<init>(paramE, ((State)localObject1).subscribers);
          if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject1, localObject2)) {
            break;
          }
          localObject2 = ((State)localObject1).subscribers;
          if (localObject2 != null)
          {
            int j = localObject2.length;
            int i = 0;
            while (i < j)
            {
              localObject1 = localObject2[i];
              i++;
              ((Subscriber)localObject1).offerInternal(paramE);
            }
          }
        }
      }
      label146:
      paramE = new java/lang/IllegalStateException;
      str = Intrinsics.stringPlus("Invalid state ", localObject1);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      paramE.<init>(str.toString());
      throw paramE;
    }
    finally
    {
      this._updating = 0;
    }
  }
  
  private final <R> void registerSelectSend(SelectInstance<? super R> paramSelectInstance, E paramE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    if (!paramSelectInstance.trySelect()) {
      return;
    }
    paramE = offerInternal(paramE);
    if (paramE == null)
    {
      UndispatchedKt.startCoroutineUnintercepted(paramFunction2, this, paramSelectInstance.getCompletion());
      return;
    }
    paramSelectInstance.resumeSelectWithException(paramE.getSendException());
  }
  
  private final Subscriber<E>[] removeSubscriber(Subscriber<E>[] paramArrayOfSubscriber, Subscriber<E> paramSubscriber)
  {
    int k = paramArrayOfSubscriber.length;
    int j = ArraysKt.indexOf(paramArrayOfSubscriber, paramSubscriber);
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (j >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (k == 1) {
      return null;
    }
    paramSubscriber = new Subscriber[k - 1];
    ArraysKt.copyInto$default(paramArrayOfSubscriber, paramSubscriber, 0, 0, j, 6, null);
    ArraysKt.copyInto$default(paramArrayOfSubscriber, paramSubscriber, j, j + 1, 0, 8, null);
    return paramSubscriber;
  }
  
  public void cancel(CancellationException paramCancellationException)
  {
    close((Throwable)paramCancellationException);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    Object localObject2;
    for (;;)
    {
      localObject2 = this._state;
      boolean bool = localObject2 instanceof Closed;
      int i = 0;
      if (bool) {
        return false;
      }
      if (!(localObject2 instanceof State)) {
        break;
      }
      Object localObject1;
      if (paramThrowable == null) {
        localObject1 = CLOSED;
      } else {
        localObject1 = new Closed(paramThrowable);
      }
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject2, localObject1))
      {
        localObject1 = ((State)localObject2).subscribers;
        if (localObject1 != null)
        {
          int j = localObject1.length;
          while (i < j)
          {
            localObject2 = localObject1[i];
            i++;
            ((Subscriber)localObject2).close(paramThrowable);
          }
        }
        invokeOnCloseHandler(paramThrowable);
        return true;
      }
    }
    paramThrowable = Intrinsics.stringPlus("Invalid state ", localObject2);
    Log5ECF72.a(paramThrowable);
    LogE84000.a(paramThrowable);
    Log229316.a(paramThrowable);
    throw new IllegalStateException(paramThrowable.toString());
  }
  
  public SelectClause2<E, SendChannel<E>> getOnSend()
  {
    (SelectClause2)new SelectClause2()
    {
      final ConflatedBroadcastChannel<E> this$0;
      
      public <R> void registerSelectClause2(SelectInstance<? super R> paramAnonymousSelectInstance, E paramAnonymousE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        ConflatedBroadcastChannel.access$registerSelectSend(this.this$0, paramAnonymousSelectInstance, paramAnonymousE, paramAnonymousFunction2);
      }
    };
  }
  
  public final E getValue()
  {
    Object localObject = this._state;
    if (!(localObject instanceof Closed))
    {
      if ((localObject instanceof State))
      {
        if (((State)localObject).value != UNDEFINED) {
          return (E)((State)localObject).value;
        }
        throw new IllegalStateException("No value");
      }
      localObject = Intrinsics.stringPlus("Invalid state ", localObject);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      throw new IllegalStateException(localObject.toString());
    }
    throw ((Closed)localObject).getValueException();
  }
  
  public final E getValueOrNull()
  {
    Object localObject2 = this._state;
    boolean bool = localObject2 instanceof Closed;
    Object localObject1 = null;
    if (!bool)
    {
      if (!(localObject2 instanceof State)) {
        break label52;
      }
      Symbol localSymbol = UNDEFINED;
      localObject2 = ((State)localObject2).value;
      if (localObject2 != localSymbol) {
        localObject1 = localObject2;
      }
    }
    return (E)localObject1;
    label52:
    localObject1 = Intrinsics.stringPlus("Invalid state ", localObject2);
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    throw new IllegalStateException(localObject1.toString());
  }
  
  public void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1)
  {
    AtomicReferenceFieldUpdater localAtomicReferenceFieldUpdater = onCloseHandler$FU;
    if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(localAtomicReferenceFieldUpdater, this, null, paramFunction1))
    {
      paramFunction1 = this.onCloseHandler;
      if (paramFunction1 == AbstractChannelKt.HANDLER_INVOKED) {
        throw new IllegalStateException("Another handler was already registered and successfully invoked");
      }
      paramFunction1 = Intrinsics.stringPlus("Another handler was already registered: ", paramFunction1);
      Log5ECF72.a(paramFunction1);
      LogE84000.a(paramFunction1);
      Log229316.a(paramFunction1);
      throw new IllegalStateException(paramFunction1);
    }
    Object localObject = this._state;
    if (((localObject instanceof Closed)) && (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(localAtomicReferenceFieldUpdater, this, paramFunction1, AbstractChannelKt.HANDLER_INVOKED))) {
      paramFunction1.invoke(((Closed)localObject).closeCause);
    }
  }
  
  public boolean isClosedForSend()
  {
    return this._state instanceof Closed;
  }
  
  @kotlin.Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'trySend' method", replaceWith=@ReplaceWith(expression="trySend(element).isSuccess", imports={}))
  public boolean offer(E paramE)
  {
    return BroadcastChannel.DefaultImpls.offer((BroadcastChannel)this, paramE);
  }
  
  public ReceiveChannel<E> openSubscription()
  {
    Subscriber localSubscriber = new Subscriber(this);
    Object localObject2;
    for (;;)
    {
      localObject2 = this._state;
      if ((localObject2 instanceof Closed))
      {
        localSubscriber.close(((Closed)localObject2).closeCause);
        return (ReceiveChannel)localSubscriber;
      }
      if (!(localObject2 instanceof State)) {
        break;
      }
      if (((State)localObject2).value != UNDEFINED) {
        localSubscriber.offerInternal(((State)localObject2).value);
      }
      localObject1 = new State(((State)localObject2).value, addSubscriber(((State)localObject2).subscribers, localSubscriber));
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject2, localObject1)) {
        return (ReceiveChannel)localSubscriber;
      }
    }
    Object localObject1 = Intrinsics.stringPlus("Invalid state ", localObject2);
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    throw new IllegalStateException(localObject1.toString());
  }
  
  public Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    paramE = offerInternal(paramE);
    if (paramE == null)
    {
      if (IntrinsicsKt.getCOROUTINE_SUSPENDED() == null) {
        return null;
      }
      return Unit.INSTANCE;
    }
    throw paramE.getSendException();
  }
  
  public Object trySend-JP2dKIU(E paramE)
  {
    paramE = offerInternal(paramE);
    if (paramE == null) {
      return ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
    }
    return ChannelResult.Companion.closed-JP2dKIU(paramE.getSendException());
  }
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\020\003\n\002\b\007\b\002\030\0002\0020\001B\017\022\b\020\002\032\004\030\0010\003¢\006\002\020\004R\022\020\002\032\004\030\0010\0038\006X\004¢\006\002\n\000R\021\020\005\032\0020\0038F¢\006\006\032\004\b\006\020\007R\021\020\b\032\0020\0038F¢\006\006\032\004\b\t\020\007¨\006\n"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "", "closeCause", "", "(Ljava/lang/Throwable;)V", "sendException", "getSendException", "()Ljava/lang/Throwable;", "valueException", "getValueException", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class Closed
  {
    public final Throwable closeCause;
    
    public Closed(Throwable paramThrowable)
    {
      this.closeCause = paramThrowable;
    }
    
    public final Throwable getSendException()
    {
      Throwable localThrowable2 = this.closeCause;
      Throwable localThrowable1 = localThrowable2;
      if (localThrowable2 == null) {
        localThrowable1 = (Throwable)new ClosedSendChannelException("Channel was closed");
      }
      return localThrowable1;
    }
    
    public final Throwable getValueException()
    {
      Throwable localThrowable2 = this.closeCause;
      Throwable localThrowable1 = localThrowable2;
      if (localThrowable2 == null) {
        localThrowable1 = (Throwable)new IllegalStateException("Channel was closed");
      }
      return localThrowable1;
    }
  }
  
  @Metadata(d1={"\000\036\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004X\004¢\006\002\n\000R\026\020\005\032\n\022\006\022\004\030\0010\0010\006X\004¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Companion;", "", "()V", "CLOSED", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "INITIAL_STATE", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "UNDEFINED", "Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class Companion {}
  
  @Metadata(d1={"\000\032\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\003\b\002\030\000*\004\b\001\020\0012\0020\002B%\022\b\020\003\032\004\030\0010\002\022\024\020\004\032\020\022\n\022\b\022\004\022\0028\0010\006\030\0010\005¢\006\002\020\007R \020\004\032\020\022\n\022\b\022\004\022\0028\0010\006\030\0010\0058\006X\004¢\006\004\n\002\020\bR\022\020\003\032\004\030\0010\0028\006X\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "E", "", "value", "subscribers", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "(Ljava/lang/Object;[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class State<E>
  {
    public final ConflatedBroadcastChannel.Subscriber<E>[] subscribers;
    public final Object value;
    
    public State(Object paramObject, ConflatedBroadcastChannel.Subscriber<E>[] paramArrayOfSubscriber)
    {
      this.value = paramObject;
      this.subscribers = paramArrayOfSubscriber;
    }
  }
  
  @Metadata(d1={"\000,\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\020\013\n\000\b\002\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\023\022\f\020\004\032\b\022\004\022\0028\0010\005¢\006\002\020\006J\025\020\007\032\0020\b2\006\020\t\032\0028\001H\026¢\006\002\020\nJ\020\020\013\032\0020\f2\006\020\r\032\0020\016H\024R\024\020\004\032\b\022\004\022\0028\0010\005X\004¢\006\002\n\000¨\006\017"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ConflatedChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "broadcastChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;)V", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "onCancelIdempotent", "", "wasClosed", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class Subscriber<E>
    extends ConflatedChannel<E>
    implements ReceiveChannel<E>
  {
    private final ConflatedBroadcastChannel<E> broadcastChannel;
    
    public Subscriber(ConflatedBroadcastChannel<E> paramConflatedBroadcastChannel)
    {
      super();
      this.broadcastChannel = paramConflatedBroadcastChannel;
    }
    
    public Object offerInternal(E paramE)
    {
      return super.offerInternal(paramE);
    }
    
    protected void onCancelIdempotent(boolean paramBoolean)
    {
      if (paramBoolean) {
        ConflatedBroadcastChannel.access$closeSubscriber(this.broadcastChannel, this);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ConflatedBroadcastChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */