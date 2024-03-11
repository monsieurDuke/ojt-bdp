package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BeforeResumeCancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuation.DefaultImpls;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\001\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\b\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\017\n\002\030\002\n\000\n\002\030\002\n\002\b\016\b \030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003:\007STUVWXYB'\022 \020\004\032\034\022\004\022\0028\000\022\004\022\0020\006\030\0010\005j\n\022\004\022\0028\000\030\001`\007¢\006\002\020\bJ\022\020\031\032\0020\n2\b\020\032\032\004\030\0010\033H\007J\026\020\031\032\0020\0062\016\020\032\032\n\030\0010\034j\004\030\001`\035J\027\020\036\032\0020\n2\b\020\032\032\004\030\0010\033H\000¢\006\002\b\037J\016\020 \032\b\022\004\022\0028\0000!H\004J\026\020\"\032\0020\n2\f\020#\032\b\022\004\022\0028\0000$H\002J\026\020%\032\0020\n2\f\020#\032\b\022\004\022\0028\0000$H\024JR\020&\032\0020\n\"\004\b\001\020'2\f\020(\032\b\022\004\022\002H'0)2$\020*\032 \b\001\022\006\022\004\030\0010,\022\n\022\b\022\004\022\002H'0-\022\006\022\004\030\0010,0+2\006\020.\032\0020/H\002ø\001\000¢\006\002\0200J\017\0201\032\b\022\004\022\0028\00002H\002J\020\0203\032\0020\0062\006\0204\032\0020\nH\024J/\0205\032\0020\0062\f\0206\032\b\022\004\022\00208072\n\0209\032\006\022\002\b\0030:H\024ø\001\000ø\001\001¢\006\004\b;\020<J\b\020=\032\0020\006H\024J\b\020>\032\0020\006H\024J\n\020?\032\004\030\0010,H\024J\026\020@\032\004\030\0010,2\n\020(\032\006\022\002\b\0030)H\024J\021\020#\032\0028\000H@ø\001\000¢\006\002\020AJ\"\020B\032\b\022\004\022\0028\0000\027H@ø\001\000ø\001\000ø\001\002ø\001\001¢\006\004\bC\020AJ\037\020D\032\002H'\"\004\b\001\020'2\006\020.\032\0020/H@ø\001\000¢\006\002\020EJR\020F\032\0020\006\"\004\b\001\020'2\f\020(\032\b\022\004\022\002H'0)2\006\020.\032\0020/2$\020*\032 \b\001\022\006\022\004\030\0010,\022\n\022\b\022\004\022\002H'0-\022\006\022\004\030\0010,0+H\002ø\001\000¢\006\002\020GJ \020H\032\0020\0062\n\020I\032\006\022\002\b\0030J2\n\020#\032\006\022\002\b\0030$H\002J\020\020K\032\n\022\004\022\0028\000\030\0010LH\024J\034\020M\032\b\022\004\022\0028\0000\027ø\001\000ø\001\002ø\001\001¢\006\004\bN\020OJX\020P\032\0020\006\"\004\b\001\020'* \b\001\022\006\022\004\030\0010,\022\n\022\b\022\004\022\002H'0-\022\006\022\004\030\0010,0+2\f\020(\032\b\022\004\022\002H'0)2\006\020.\032\0020/2\b\020Q\032\004\030\0010,H\002ø\001\000¢\006\002\020RR\024\020\t\032\0020\n8DX\004¢\006\006\032\004\b\013\020\fR\022\020\r\032\0020\nX¤\004¢\006\006\032\004\b\r\020\fR\022\020\016\032\0020\nX¤\004¢\006\006\032\004\b\016\020\fR\024\020\017\032\0020\n8VX\004¢\006\006\032\004\b\017\020\fR\024\020\020\032\0020\n8VX\004¢\006\006\032\004\b\020\020\fR\024\020\021\032\0020\n8DX\004¢\006\006\032\004\b\021\020\fR\027\020\022\032\b\022\004\022\0028\0000\0238F¢\006\006\032\004\b\024\020\025R \020\026\032\016\022\n\022\b\022\004\022\0028\0000\0270\0238Fø\001\000¢\006\006\032\004\b\030\020\025\002\017\n\002\b\031\n\005\b¡\0360\001\n\002\b!¨\006Z"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/Channel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "hasReceiveOrClosed", "", "getHasReceiveOrClosed", "()Z", "isBufferAlwaysEmpty", "isBufferEmpty", "isClosedForReceive", "isEmpty", "isEmptyImpl", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "cancelInternal$kotlinx_coroutines_core", "describeTryPoll", "Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "enqueueReceive", "receive", "Lkotlinx/coroutines/channels/Receive;", "enqueueReceiveInternal", "enqueueReceiveSelect", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)Z", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "onCancelIdempotent", "wasClosed", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "onReceiveDequeued", "onReceiveEnqueued", "pollInternal", "pollSelectInternal", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveSuspend", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerSelectReceiveMode", "(Lkotlinx/coroutines/selects/SelectInstance;ILkotlin/jvm/functions/Function2;)V", "removeReceiveOnCancel", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "takeFirstReceiveOrPeekClosed", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "tryReceive", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "tryStartBlockUnintercepted", "value", "(Lkotlin/jvm/functions/Function2;Lkotlinx/coroutines/selects/SelectInstance;ILjava/lang/Object;)V", "Itr", "ReceiveElement", "ReceiveElementWithUndeliveredHandler", "ReceiveHasNext", "ReceiveSelect", "RemoveReceiveOnCancel", "TryPollDesc", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AbstractChannel<E>
  extends AbstractSendChannel<E>
  implements Channel<E>
{
  public AbstractChannel(Function1<? super E, Unit> paramFunction1)
  {
    super(paramFunction1);
  }
  
  private final boolean enqueueReceive(Receive<? super E> paramReceive)
  {
    boolean bool = enqueueReceiveInternal(paramReceive);
    if (bool) {
      onReceiveEnqueued();
    }
    return bool;
  }
  
  private final <R> boolean enqueueReceiveSelect(SelectInstance<? super R> paramSelectInstance, Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2, int paramInt)
  {
    paramFunction2 = new ReceiveSelect(this, paramSelectInstance, paramFunction2, paramInt);
    boolean bool = enqueueReceive((Receive)paramFunction2);
    if (bool) {
      paramSelectInstance.disposeOnSelect((DisposableHandle)paramFunction2);
    }
    return bool;
  }
  
  private final <R> Object receiveSuspend(int paramInt, Continuation<? super R> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
    CancellableContinuation localCancellableContinuation = (CancellableContinuation)localCancellableContinuationImpl;
    Object localObject1;
    if (this.onUndeliveredElement == null) {
      localObject1 = new ReceiveElement(localCancellableContinuation, paramInt);
    } else {
      localObject1 = (ReceiveElement)new ReceiveElementWithUndeliveredHandler(localCancellableContinuation, paramInt, this.onUndeliveredElement);
    }
    label151:
    for (;;)
    {
      if (access$enqueueReceive(this, (Receive)localObject1))
      {
        access$removeReceiveOnCancel(this, localCancellableContinuation, (Receive)localObject1);
      }
      else
      {
        Object localObject2 = pollInternal();
        if ((localObject2 instanceof Closed))
        {
          ((ReceiveElement)localObject1).resumeReceiveClosed((Closed)localObject2);
        }
        else
        {
          if (localObject2 == AbstractChannelKt.POLL_FAILED) {
            break label151;
          }
          localCancellableContinuation.resume(((ReceiveElement)localObject1).resumeValue(localObject2), ((ReceiveElement)localObject1).resumeOnCancellationFun(localObject2));
        }
      }
      localObject1 = localCancellableContinuationImpl.getResult();
      if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      return localObject1;
    }
  }
  
  private final <R> void registerSelectReceiveMode(SelectInstance<? super R> paramSelectInstance, int paramInt, Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    for (;;)
    {
      if (paramSelectInstance.isSelected()) {
        return;
      }
      if (isEmptyImpl())
      {
        if (!enqueueReceiveSelect(paramSelectInstance, paramFunction2, paramInt)) {}
      }
      else
      {
        Object localObject = pollSelectInternal(paramSelectInstance);
        if (localObject == SelectKt.getALREADY_SELECTED()) {
          return;
        }
        if ((localObject != AbstractChannelKt.POLL_FAILED) && (localObject != AtomicKt.RETRY_ATOMIC)) {
          tryStartBlockUnintercepted(paramFunction2, paramSelectInstance, paramInt, localObject);
        }
      }
    }
  }
  
  private final void removeReceiveOnCancel(CancellableContinuation<?> paramCancellableContinuation, Receive<?> paramReceive)
  {
    paramCancellableContinuation.invokeOnCancellation((Function1)new RemoveReceiveOnCancel(paramReceive));
  }
  
  private final <R> void tryStartBlockUnintercepted(Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2, SelectInstance<? super R> paramSelectInstance, int paramInt, Object paramObject)
  {
    if ((paramObject instanceof Closed))
    {
      switch (paramInt)
      {
      default: 
        break;
      case 1: 
        if (!paramSelectInstance.trySelect()) {
          return;
        }
        UndispatchedKt.startCoroutineUnintercepted(paramFunction2, ChannelResult.box-impl(ChannelResult.Companion.closed-JP2dKIU(((Closed)paramObject).closeCause)), paramSelectInstance.getCompletion());
        break;
      case 0: 
        throw StackTraceRecoveryKt.recoverStackTrace(((Closed)paramObject).getReceiveException());
      }
    }
    else if (paramInt == 1)
    {
      if ((paramObject instanceof Closed)) {
        paramObject = ChannelResult.Companion.closed-JP2dKIU(((Closed)paramObject).closeCause);
      } else {
        paramObject = ChannelResult.Companion.success-JP2dKIU(paramObject);
      }
      UndispatchedKt.startCoroutineUnintercepted(paramFunction2, ChannelResult.box-impl(paramObject), paramSelectInstance.getCompletion());
    }
    else
    {
      UndispatchedKt.startCoroutineUnintercepted(paramFunction2, paramObject, paramSelectInstance.getCompletion());
    }
  }
  
  public final void cancel(CancellationException paramCancellationException)
  {
    if (isClosedForReceive()) {
      return;
    }
    if (paramCancellationException == null)
    {
      paramCancellationException = DebugStringsKt.getClassSimpleName(this);
      Log5ECF72.a(paramCancellationException);
      LogE84000.a(paramCancellationException);
      Log229316.a(paramCancellationException);
      paramCancellationException = Intrinsics.stringPlus(paramCancellationException, " was cancelled");
      Log5ECF72.a(paramCancellationException);
      LogE84000.a(paramCancellationException);
      Log229316.a(paramCancellationException);
      paramCancellationException = new CancellationException(paramCancellationException);
    }
    cancelInternal$kotlinx_coroutines_core((Throwable)paramCancellationException);
  }
  
  public final boolean cancelInternal$kotlinx_coroutines_core(Throwable paramThrowable)
  {
    boolean bool = close(paramThrowable);
    onCancelIdempotent(bool);
    return bool;
  }
  
  protected final TryPollDesc<E> describeTryPoll()
  {
    return new TryPollDesc(getQueue());
  }
  
  protected boolean enqueueReceiveInternal(Receive<? super E> paramReceive)
  {
    boolean bool2 = isBufferAlwaysEmpty();
    boolean bool1 = false;
    LockFreeLinkedListNode localLockFreeLinkedListNode1;
    Object localObject;
    if (bool2)
    {
      localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)getQueue();
      do
      {
        localObject = localLockFreeLinkedListNode1.getPrevNode();
        if (!(localObject instanceof Send ^ true)) {
          break;
        }
      } while (!((LockFreeLinkedListNode)localObject).addNext((LockFreeLinkedListNode)paramReceive, localLockFreeLinkedListNode1));
      bool1 = true;
    }
    else
    {
      localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)getQueue();
      localObject = (LockFreeLinkedListNode.CondAddOp)new LockFreeLinkedListNode.CondAddOp((LockFreeLinkedListNode)paramReceive)
      {
        final LockFreeLinkedListNode $node;
        
        public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
        {
          if (jdField_this.isBufferEmpty()) {
            paramAnonymousLockFreeLinkedListNode = null;
          } else {
            paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
          }
          return paramAnonymousLockFreeLinkedListNode;
        }
      };
      for (;;)
      {
        LockFreeLinkedListNode localLockFreeLinkedListNode2 = localLockFreeLinkedListNode1.getPrevNode();
        if (!(localLockFreeLinkedListNode2 instanceof Send ^ true)) {
          break;
        }
        switch (localLockFreeLinkedListNode2.tryCondAddNext((LockFreeLinkedListNode)paramReceive, localLockFreeLinkedListNode1, (LockFreeLinkedListNode.CondAddOp)localObject))
        {
        }
      }
      return bool1;
      bool1 = true;
    }
    return bool1;
  }
  
  protected final boolean getHasReceiveOrClosed()
  {
    return getQueue().getNextNode() instanceof ReceiveOrClosed;
  }
  
  public final SelectClause1<E> getOnReceive()
  {
    (SelectClause1)new SelectClause1()
    {
      final AbstractChannel<E> this$0;
      
      public <R> void registerSelectClause1(SelectInstance<? super R> paramAnonymousSelectInstance, Function2<? super E, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        AbstractChannel.access$registerSelectReceiveMode(this.this$0, paramAnonymousSelectInstance, 0, paramAnonymousFunction2);
      }
    };
  }
  
  public final SelectClause1<ChannelResult<E>> getOnReceiveCatching()
  {
    (SelectClause1)new SelectClause1()
    {
      final AbstractChannel<E> this$0;
      
      public <R> void registerSelectClause1(SelectInstance<? super R> paramAnonymousSelectInstance, Function2<? super ChannelResult<? extends E>, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        AbstractChannel.access$registerSelectReceiveMode(this.this$0, paramAnonymousSelectInstance, 1, paramAnonymousFunction2);
      }
    };
  }
  
  public SelectClause1<E> getOnReceiveOrNull()
  {
    return Channel.DefaultImpls.getOnReceiveOrNull((Channel)this);
  }
  
  protected abstract boolean isBufferAlwaysEmpty();
  
  protected abstract boolean isBufferEmpty();
  
  public boolean isClosedForReceive()
  {
    boolean bool;
    if ((getClosedForReceive() != null) && (isBufferEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isEmpty()
  {
    return isEmptyImpl();
  }
  
  protected final boolean isEmptyImpl()
  {
    boolean bool;
    if ((!(getQueue().getNextNode() instanceof Send)) && (isBufferEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final ChannelIterator<E> iterator()
  {
    return (ChannelIterator)new Itr(this);
  }
  
  protected void onCancelIdempotent(boolean paramBoolean)
  {
    Closed localClosed = getClosedForSend();
    if (localClosed != null)
    {
      Object localObject = InlineList.constructor-impl$default(null, 1, null);
      for (;;)
      {
        LockFreeLinkedListNode localLockFreeLinkedListNode = localClosed.getPrevNode();
        if ((localLockFreeLinkedListNode instanceof LockFreeLinkedListHead))
        {
          onCancelIdempotentList-w-w6eGU(localObject, localClosed);
          return;
        }
        if ((DebugKt.getASSERTIONS_ENABLED()) && (!(localLockFreeLinkedListNode instanceof Send))) {
          throw new AssertionError();
        }
        if (!localLockFreeLinkedListNode.remove()) {
          localLockFreeLinkedListNode.helpRemove();
        } else {
          localObject = InlineList.plus-FjFbRPM(localObject, (Send)localLockFreeLinkedListNode);
        }
      }
    }
    throw new IllegalStateException("Cannot happen".toString());
  }
  
  protected void onCancelIdempotentList-w-w6eGU(Object paramObject, Closed<?> paramClosed)
  {
    if (paramObject != null) {
      if (!(paramObject instanceof ArrayList))
      {
        ((Send)paramObject).resumeSendClosed(paramClosed);
      }
      else
      {
        if (paramObject == null) {
          break label71;
        }
        paramObject = (ArrayList)paramObject;
        int i = ((ArrayList)paramObject).size() - 1;
        if (i >= 0)
        {
          int j;
          do
          {
            j = i - 1;
            ((Send)((ArrayList)paramObject).get(i)).resumeSendClosed(paramClosed);
            i = j;
          } while (j >= 0);
        }
      }
    }
    return;
    label71:
    throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
  }
  
  protected void onReceiveDequeued() {}
  
  protected void onReceiveEnqueued() {}
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith=@ReplaceWith(expression="tryReceive().getOrNull()", imports={}))
  public E poll()
  {
    return (E)Channel.DefaultImpls.poll((Channel)this);
  }
  
  protected Object pollInternal()
  {
    for (;;)
    {
      Send localSend = takeFirstSendOrPeekClosed();
      if (localSend == null) {
        return AbstractChannelKt.POLL_FAILED;
      }
      Symbol localSymbol = localSend.tryResumeSend(null);
      if (localSymbol != null)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          int i;
          if (localSymbol == CancellableContinuationImplKt.RESUME_TOKEN) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw new AssertionError();
          }
        }
        localSend.completeResumeSend();
        return localSend.getPollResult();
      }
      localSend.undeliveredElement();
    }
  }
  
  protected Object pollSelectInternal(SelectInstance<?> paramSelectInstance)
  {
    TryPollDesc localTryPollDesc = describeTryPoll();
    paramSelectInstance = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryPollDesc);
    if (paramSelectInstance != null) {
      return paramSelectInstance;
    }
    ((Send)localTryPollDesc.getResult()).completeResumeSend();
    return ((Send)localTryPollDesc.getResult()).getPollResult();
  }
  
  public final Object receive(Continuation<? super E> paramContinuation)
  {
    Object localObject = pollInternal();
    if ((localObject != AbstractChannelKt.POLL_FAILED) && (!(localObject instanceof Closed))) {
      return localObject;
    }
    return receiveSuspend(0, paramContinuation);
  }
  
  public final Object receiveCatching-JP2dKIU(Continuation<? super ChannelResult<? extends E>> paramContinuation)
  {
    if ((paramContinuation instanceof receiveCatching.1))
    {
      localObject1 = (receiveCatching.1)paramContinuation;
      if ((((receiveCatching.1)localObject1).label & 0x80000000) != 0)
      {
        ((receiveCatching.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super ChannelResult<? extends E>>)localObject1;
        break label50;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      int label;
      Object result;
      final AbstractChannel<E> this$0;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        paramAnonymousObject = this.this$0.receiveCatching-JP2dKIU((Continuation)this);
        if (paramAnonymousObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return paramAnonymousObject;
        }
        return ChannelResult.box-impl(paramAnonymousObject);
      }
    };
    label50:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      ResultKt.throwOnFailure(localObject1);
      paramContinuation = (Continuation<? super ChannelResult<? extends E>>)localObject1;
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject1);
      localObject1 = pollInternal();
      if (localObject1 != AbstractChannelKt.POLL_FAILED)
      {
        if ((localObject1 instanceof Closed)) {
          paramContinuation = ChannelResult.Companion.closed-JP2dKIU(((Closed)localObject1).closeCause);
        } else {
          paramContinuation = ChannelResult.Companion.success-JP2dKIU(localObject1);
        }
        return paramContinuation;
      }
      paramContinuation.label = 1;
      localObject1 = receiveSuspend(1, paramContinuation);
      paramContinuation = (Continuation<? super ChannelResult<? extends E>>)localObject1;
      if (localObject1 == localObject2) {
        return localObject2;
      }
      break;
    }
    return ((ChannelResult)paramContinuation).unbox-impl();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith=@ReplaceWith(expression="receiveCatching().getOrNull()", imports={}))
  public Object receiveOrNull(Continuation<? super E> paramContinuation)
  {
    return Channel.DefaultImpls.receiveOrNull((Channel)this, paramContinuation);
  }
  
  protected ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed()
  {
    ReceiveOrClosed localReceiveOrClosed = super.takeFirstReceiveOrPeekClosed();
    if ((localReceiveOrClosed != null) && (!(localReceiveOrClosed instanceof Closed))) {
      onReceiveDequeued();
    }
    return localReceiveOrClosed;
  }
  
  public final Object tryReceive-PtdJZtk()
  {
    Object localObject = pollInternal();
    if (localObject == AbstractChannelKt.POLL_FAILED) {
      return ChannelResult.Companion.failure-PtdJZtk();
    }
    if ((localObject instanceof Closed)) {
      return ChannelResult.Companion.closed-JP2dKIU(((Closed)localObject).closeCause);
    }
    return ChannelResult.Companion.success-JP2dKIU(localObject);
  }
  
  @Metadata(d1={"\000$\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\005\n\002\020\013\n\002\b\005\b\002\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\002B\023\022\f\020\003\032\b\022\004\022\0028\0010\004¢\006\002\020\005J\021\020\f\032\0020\rHBø\001\000¢\006\002\020\016J\022\020\017\032\0020\r2\b\020\006\032\004\030\0010\007H\002J\021\020\020\032\0020\rH@ø\001\000¢\006\002\020\016J\016\020\021\032\0028\001H\002¢\006\002\020\tR\026\020\003\032\b\022\004\022\0028\0010\0048\006X\004¢\006\002\n\000R\034\020\006\032\004\030\0010\007X\016¢\006\016\n\000\032\004\b\b\020\t\"\004\b\n\020\013\002\004\n\002\b\031¨\006\022"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "E", "Lkotlinx/coroutines/channels/ChannelIterator;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "(Lkotlinx/coroutines/channels/AbstractChannel;)V", "result", "", "getResult", "()Ljava/lang/Object;", "setResult", "(Ljava/lang/Object;)V", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasNextResult", "hasNextSuspend", "next", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class Itr<E>
    implements ChannelIterator<E>
  {
    public final AbstractChannel<E> channel;
    private Object result;
    
    public Itr(AbstractChannel<E> paramAbstractChannel)
    {
      this.channel = paramAbstractChannel;
      this.result = AbstractChannelKt.POLL_FAILED;
    }
    
    private final boolean hasNextResult(Object paramObject)
    {
      if ((paramObject instanceof Closed))
      {
        if (((Closed)paramObject).closeCause == null) {
          return false;
        }
        throw StackTraceRecoveryKt.recoverStackTrace(((Closed)paramObject).getReceiveException());
      }
      return true;
    }
    
    private final Object hasNextSuspend(Continuation<? super Boolean> paramContinuation)
    {
      CancellableContinuationImpl localCancellableContinuationImpl = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
      Object localObject2 = (CancellableContinuation)localCancellableContinuationImpl;
      Object localObject3 = new AbstractChannel.ReceiveHasNext(this, (CancellableContinuation)localObject2);
      label225:
      for (;;)
      {
        if (AbstractChannel.access$enqueueReceive(this.channel, (Receive)localObject3))
        {
          AbstractChannel.access$removeReceiveOnCancel(this.channel, (CancellableContinuation)localObject2, (Receive)localObject3);
        }
        else
        {
          localObject1 = this.channel.pollInternal();
          setResult(localObject1);
          if ((localObject1 instanceof Closed))
          {
            if (((Closed)localObject1).closeCause == null)
            {
              localObject2 = (Continuation)localObject2;
              localObject1 = Result.Companion;
              ((Continuation)localObject2).resumeWith(Result.constructor-impl(Boxing.boxBoolean(false)));
            }
            else
            {
              localObject2 = (Continuation)localObject2;
              localObject3 = Result.Companion;
              ((Continuation)localObject2).resumeWith(Result.constructor-impl(ResultKt.createFailure(((Closed)localObject1).getReceiveException())));
            }
          }
          else
          {
            if (localObject1 == AbstractChannelKt.POLL_FAILED) {
              break label225;
            }
            localObject3 = Boxing.boxBoolean(true);
            Function1 localFunction1 = this.channel.onUndeliveredElement;
            if (localFunction1 == null) {
              localObject1 = null;
            } else {
              localObject1 = OnUndeliveredElementKt.bindCancellationFun(localFunction1, localObject1, ((CancellableContinuation)localObject2).getContext());
            }
            ((CancellableContinuation)localObject2).resume(localObject3, (Function1)localObject1);
          }
        }
        Object localObject1 = localCancellableContinuationImpl.getResult();
        if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          DebugProbesKt.probeCoroutineSuspended(paramContinuation);
        }
        return localObject1;
      }
    }
    
    public final Object getResult()
    {
      return this.result;
    }
    
    public Object hasNext(Continuation<? super Boolean> paramContinuation)
    {
      if (getResult() != AbstractChannelKt.POLL_FAILED) {
        return Boxing.boxBoolean(hasNextResult(getResult()));
      }
      setResult(this.channel.pollInternal());
      if (getResult() != AbstractChannelKt.POLL_FAILED) {
        return Boxing.boxBoolean(hasNextResult(getResult()));
      }
      return hasNextSuspend(paramContinuation);
    }
    
    public E next()
    {
      Object localObject = this.result;
      if (!(localObject instanceof Closed))
      {
        if (localObject != AbstractChannelKt.POLL_FAILED)
        {
          this.result = AbstractChannelKt.POLL_FAILED;
          return (E)localObject;
        }
        throw new IllegalStateException("'hasNext' should be called prior to 'next' invocation");
      }
      throw StackTraceRecoveryKt.recoverStackTrace(((Closed)localObject).getReceiveException());
    }
    
    public final void setResult(Object paramObject)
    {
      this.result = paramObject;
    }
  }
  
  @Metadata(d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\022\030\000*\006\b\001\020\001 \0002\b\022\004\022\002H\0010\002B\035\022\016\020\003\032\n\022\006\022\004\030\0010\0050\004\022\006\020\006\032\0020\007¢\006\002\020\bJ\025\020\t\032\0020\n2\006\020\013\032\0028\001H\026¢\006\002\020\fJ\024\020\r\032\0020\n2\n\020\016\032\006\022\002\b\0030\017H\026J\025\020\020\032\004\030\0010\0052\006\020\013\032\0028\001¢\006\002\020\021J\b\020\022\032\0020\023H\026J!\020\024\032\004\030\0010\0252\006\020\013\032\0028\0012\b\020\026\032\004\030\0010\027H\026¢\006\002\020\030R\030\020\003\032\n\022\006\022\004\030\0010\0050\0048\006X\004¢\006\002\n\000R\020\020\006\032\0020\0078\006X\004¢\006\002\n\000¨\006\031"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "E", "Lkotlinx/coroutines/channels/Receive;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "(Lkotlinx/coroutines/CancellableContinuation;I)V", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "resumeValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static class ReceiveElement<E>
    extends Receive<E>
  {
    public final CancellableContinuation<Object> cont;
    public final int receiveMode;
    
    public ReceiveElement(CancellableContinuation<Object> paramCancellableContinuation, int paramInt)
    {
      this.cont = paramCancellableContinuation;
      this.receiveMode = paramInt;
    }
    
    public void completeResumeReceive(E paramE)
    {
      this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
    }
    
    public void resumeReceiveClosed(Closed<?> paramClosed)
    {
      Continuation localContinuation;
      Result.Companion localCompanion;
      if (this.receiveMode == 1)
      {
        localContinuation = (Continuation)this.cont;
        localCompanion = Result.Companion;
        localContinuation.resumeWith(Result.constructor-impl(ChannelResult.box-impl(ChannelResult.Companion.closed-JP2dKIU(paramClosed.closeCause))));
      }
      else
      {
        localContinuation = (Continuation)this.cont;
        localCompanion = Result.Companion;
        localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramClosed.getReceiveException())));
      }
    }
    
    public final Object resumeValue(E paramE)
    {
      if (this.receiveMode == 1) {
        paramE = ChannelResult.box-impl(ChannelResult.Companion.success-JP2dKIU(paramE));
      }
      return paramE;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append("ReceiveElement@");
      String str = DebugStringsKt.getHexAddress(this);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + "[receiveMode=" + this.receiveMode + ']';
    }
    
    public Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      CancellableContinuation localCancellableContinuation = this.cont;
      Object localObject = resumeValue(paramE);
      LockFreeLinkedListNode.AbstractAtomicDesc localAbstractAtomicDesc;
      if (paramPrepareOp == null) {
        localAbstractAtomicDesc = null;
      } else {
        localAbstractAtomicDesc = paramPrepareOp.desc;
      }
      paramE = localCancellableContinuation.tryResume(localObject, localAbstractAtomicDesc, resumeOnCancellationFun(paramE));
      if (paramE == null) {
        return null;
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (paramE == CancellableContinuationImplKt.RESUME_TOKEN) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      if (paramPrepareOp != null) {
        paramPrepareOp.finishPrepare();
      }
      return CancellableContinuationImplKt.RESUME_TOKEN;
    }
  }
  
  @Metadata(d1={"\0004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\003\n\002\b\003\b\002\030\000*\006\b\001\020\001 \0002\b\022\004\022\002H\0010\002B;\022\016\020\003\032\n\022\006\022\004\030\0010\0050\004\022\006\020\006\032\0020\007\022\034\020\b\032\030\022\004\022\0028\001\022\004\022\0020\n0\tj\b\022\004\022\0028\001`\013¢\006\002\020\fJ#\020\r\032\020\022\004\022\0020\016\022\004\022\0020\n\030\0010\t2\006\020\017\032\0028\001H\026¢\006\002\020\020R&\020\b\032\030\022\004\022\0028\001\022\004\022\0020\n0\tj\b\022\004\022\0028\001`\0138\006X\004¢\006\002\n\000¨\006\021"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElementWithUndeliveredHandler;", "E", "Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlinx/coroutines/CancellableContinuation;ILkotlin/jvm/functions/Function1;)V", "resumeOnCancellationFun", "", "value", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class ReceiveElementWithUndeliveredHandler<E>
    extends AbstractChannel.ReceiveElement<E>
  {
    public final Function1<E, Unit> onUndeliveredElement;
    
    public ReceiveElementWithUndeliveredHandler(CancellableContinuation<Object> paramCancellableContinuation, int paramInt, Function1<? super E, Unit> paramFunction1)
    {
      super(paramInt);
      this.onUndeliveredElement = paramFunction1;
    }
    
    public Function1<Throwable, Unit> resumeOnCancellationFun(E paramE)
    {
      return OnUndeliveredElementKt.bindCancellationFun(this.onUndeliveredElement, paramE, this.cont.getContext());
    }
  }
  
  @Metadata(d1={"\000L\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\003\n\002\030\002\n\002\020\003\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\022\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\002B!\022\f\020\003\032\b\022\004\022\0028\0010\004\022\f\020\005\032\b\022\004\022\0020\0070\006¢\006\002\020\bJ\025\020\t\032\0020\n2\006\020\013\032\0028\001H\026¢\006\002\020\fJ#\020\r\032\020\022\004\022\0020\017\022\004\022\0020\n\030\0010\0162\006\020\013\032\0028\001H\026¢\006\002\020\020J\024\020\021\032\0020\n2\n\020\022\032\006\022\002\b\0030\023H\026J\b\020\024\032\0020\025H\026J!\020\026\032\004\030\0010\0272\006\020\013\032\0028\0012\b\020\030\032\004\030\0010\031H\026¢\006\002\020\032R\026\020\005\032\b\022\004\022\0020\0070\0068\006X\004¢\006\002\n\000R\026\020\003\032\b\022\004\022\0028\0010\0048\006X\004¢\006\002\n\000¨\006\033"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveHasNext;", "E", "Lkotlinx/coroutines/channels/Receive;", "iterator", "Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/channels/AbstractChannel$Itr;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "resumeOnCancellationFun", "Lkotlin/Function1;", "", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static class ReceiveHasNext<E>
    extends Receive<E>
  {
    public final CancellableContinuation<Boolean> cont;
    public final AbstractChannel.Itr<E> iterator;
    
    public ReceiveHasNext(AbstractChannel.Itr<E> paramItr, CancellableContinuation<? super Boolean> paramCancellableContinuation)
    {
      this.iterator = paramItr;
      this.cont = paramCancellableContinuation;
    }
    
    public void completeResumeReceive(E paramE)
    {
      this.iterator.setResult(paramE);
      this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
    }
    
    public Function1<Throwable, Unit> resumeOnCancellationFun(E paramE)
    {
      Function1 localFunction1 = this.iterator.channel.onUndeliveredElement;
      if (localFunction1 == null) {
        paramE = null;
      } else {
        paramE = OnUndeliveredElementKt.bindCancellationFun(localFunction1, paramE, this.cont.getContext());
      }
      return paramE;
    }
    
    public void resumeReceiveClosed(Closed<?> paramClosed)
    {
      Object localObject;
      if (paramClosed.closeCause == null) {
        localObject = CancellableContinuation.DefaultImpls.tryResume$default(this.cont, Boolean.valueOf(false), null, 2, null);
      } else {
        localObject = this.cont.tryResumeWithException(paramClosed.getReceiveException());
      }
      if (localObject != null)
      {
        this.iterator.setResult(paramClosed);
        this.cont.completeResume(localObject);
      }
    }
    
    public String toString()
    {
      String str = DebugStringsKt.getHexAddress(this);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      str = Intrinsics.stringPlus("ReceiveHasNext@", str);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
    
    public Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      CancellableContinuation localCancellableContinuation = this.cont;
      int i = 1;
      LockFreeLinkedListNode.AbstractAtomicDesc localAbstractAtomicDesc;
      if (paramPrepareOp == null) {
        localAbstractAtomicDesc = null;
      } else {
        localAbstractAtomicDesc = paramPrepareOp.desc;
      }
      paramE = localCancellableContinuation.tryResume(Boolean.valueOf(true), localAbstractAtomicDesc, resumeOnCancellationFun(paramE));
      if (paramE == null) {
        return null;
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (paramE != CancellableContinuationImplKt.RESUME_TOKEN) {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      if (paramPrepareOp != null) {
        paramPrepareOp.finishPrepare();
      }
      return CancellableContinuationImplKt.RESUME_TOKEN;
    }
  }
  
  @Metadata(d1={"\000b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\004\n\002\030\002\n\002\020\003\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\001\020\001*\004\b\002\020\0022\b\022\004\022\002H\0020\0032\0020\004BR\022\f\020\005\032\b\022\004\022\0028\0020\006\022\f\020\007\032\b\022\004\022\0028\0010\b\022$\020\t\032 \b\001\022\006\022\004\030\0010\013\022\n\022\b\022\004\022\0028\0010\f\022\006\022\004\030\0010\0130\n\022\006\020\r\032\0020\016ø\001\000¢\006\002\020\017J\025\020\021\032\0020\0222\006\020\023\032\0028\002H\026¢\006\002\020\024J\b\020\025\032\0020\022H\026J#\020\026\032\020\022\004\022\0020\030\022\004\022\0020\022\030\0010\0272\006\020\023\032\0028\002H\026¢\006\002\020\031J\024\020\032\032\0020\0222\n\020\033\032\006\022\002\b\0030\034H\026J\b\020\035\032\0020\036H\026J!\020\037\032\004\030\0010 2\006\020\023\032\0028\0022\b\020!\032\004\030\0010\"H\026¢\006\002\020#R3\020\t\032 \b\001\022\006\022\004\030\0010\013\022\n\022\b\022\004\022\0028\0010\f\022\006\022\004\030\0010\0130\n8\006X\004ø\001\000¢\006\004\n\002\020\020R\026\020\005\032\b\022\004\022\0028\0020\0068\006X\004¢\006\002\n\000R\020\020\r\032\0020\0168\006X\004¢\006\002\n\000R\026\020\007\032\b\022\004\022\0028\0010\b8\006X\004¢\006\002\n\000\002\004\n\002\b\031¨\006$"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveSelect;", "R", "E", "Lkotlinx/coroutines/channels/Receive;", "Lkotlinx/coroutines/DisposableHandle;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)V", "Lkotlin/jvm/functions/Function2;", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "dispose", "resumeOnCancellationFun", "Lkotlin/Function1;", "", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class ReceiveSelect<R, E>
    extends Receive<E>
    implements DisposableHandle
  {
    public final Function2<Object, Continuation<? super R>, Object> block;
    public final AbstractChannel<E> channel;
    public final int receiveMode;
    public final SelectInstance<R> select;
    
    public ReceiveSelect(AbstractChannel<E> paramAbstractChannel, SelectInstance<? super R> paramSelectInstance, Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2, int paramInt)
    {
      this.channel = paramAbstractChannel;
      this.select = paramSelectInstance;
      this.block = paramFunction2;
      this.receiveMode = paramInt;
    }
    
    public void completeResumeReceive(E paramE)
    {
      Function2 localFunction2 = this.block;
      Object localObject;
      if (this.receiveMode == 1) {
        localObject = ChannelResult.box-impl(ChannelResult.Companion.success-JP2dKIU(paramE));
      } else {
        localObject = paramE;
      }
      CancellableKt.startCoroutineCancellable(localFunction2, localObject, this.select.getCompletion(), resumeOnCancellationFun(paramE));
    }
    
    public void dispose()
    {
      if (remove()) {
        this.channel.onReceiveDequeued();
      }
    }
    
    public Function1<Throwable, Unit> resumeOnCancellationFun(E paramE)
    {
      Function1 localFunction1 = this.channel.onUndeliveredElement;
      if (localFunction1 == null) {
        paramE = null;
      } else {
        paramE = OnUndeliveredElementKt.bindCancellationFun(localFunction1, paramE, this.select.getCompletion().getContext());
      }
      return paramE;
    }
    
    public void resumeReceiveClosed(Closed<?> paramClosed)
    {
      if (!this.select.trySelect()) {
        return;
      }
      switch (this.receiveMode)
      {
      default: 
        break;
      case 1: 
        CancellableKt.startCoroutineCancellable$default(this.block, ChannelResult.box-impl(ChannelResult.Companion.closed-JP2dKIU(paramClosed.closeCause)), this.select.getCompletion(), null, 4, null);
        break;
      case 0: 
        this.select.resumeSelectWithException(paramClosed.getReceiveException());
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append("ReceiveSelect@");
      String str = DebugStringsKt.getHexAddress(this);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + '[' + this.select + ",receiveMode=" + this.receiveMode + ']';
    }
    
    public Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      return (Symbol)this.select.trySelectOther(paramPrepareOp);
    }
  }
  
  @Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\004\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003¢\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\002J\b\020\t\032\0020\nH\026R\022\020\002\032\006\022\002\b\0030\003X\004¢\006\002\n\000¨\006\013"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$RemoveReceiveOnCancel;", "Lkotlinx/coroutines/BeforeResumeCancelHandler;", "receive", "Lkotlinx/coroutines/channels/Receive;", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/channels/Receive;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class RemoveReceiveOnCancel
    extends BeforeResumeCancelHandler
  {
    private final Receive<?> receive;
    
    public RemoveReceiveOnCancel()
    {
      Receive localReceive;
      this.receive = localReceive;
    }
    
    public void invoke(Throwable paramThrowable)
    {
      if (this.receive.remove()) {
        AbstractChannel.this.onReceiveDequeued();
      }
    }
    
    public String toString()
    {
      return "RemoveReceiveOnCancel[" + this.receive + ']';
    }
  }
  
  @Metadata(d1={"\000:\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\b\004\030\000*\004\b\001\020\0012\022\022\004\022\0020\0030\002j\b\022\004\022\0020\003`\004B\r\022\006\020\005\032\0020\006¢\006\002\020\007J\022\020\b\032\004\030\0010\t2\006\020\n\032\0020\013H\024J\026\020\f\032\004\030\0010\t2\n\020\r\032\0060\016j\002`\017H\026J\020\020\020\032\0020\0212\006\020\n\032\0020\013H\026¨\006\022"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "onRemoved", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  protected static final class TryPollDesc<E>
    extends LockFreeLinkedListNode.RemoveFirstDesc<Send>
  {
    public TryPollDesc(LockFreeLinkedListHead paramLockFreeLinkedListHead)
    {
      super();
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      if (!(paramLockFreeLinkedListNode instanceof Closed)) {
        if (!(paramLockFreeLinkedListNode instanceof Send)) {
          paramLockFreeLinkedListNode = AbstractChannelKt.POLL_FAILED;
        } else {
          paramLockFreeLinkedListNode = null;
        }
      }
      return paramLockFreeLinkedListNode;
    }
    
    public Object onPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      paramPrepareOp = ((Send)paramPrepareOp.affected).tryResumeSend(paramPrepareOp);
      if (paramPrepareOp == null) {
        return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
      }
      if (paramPrepareOp == AtomicKt.RETRY_ATOMIC) {
        return AtomicKt.RETRY_ATOMIC;
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (paramPrepareOp == CancellableContinuationImplKt.RESUME_TOKEN) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      return null;
    }
    
    public void onRemoved(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      ((Send)paramLockFreeLinkedListNode).undeliveredElement();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/AbstractChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */