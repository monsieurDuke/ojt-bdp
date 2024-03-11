package kotlinx.coroutines.channels;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuation;
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
import kotlinx.coroutines.internal.LockFreeLinkedListNode.AddLastDesc;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000¦\001\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\003\n\002\020\003\n\000\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\007\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\020\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\013\b \030\000*\004\b\000\020\0012\b\022\004\022\0028\00006:\004defgB)\022 \020\005\032\034\022\004\022\0028\000\022\004\022\0020\003\030\0010\002j\n\022\004\022\0028\000\030\001`\004¢\006\004\b\006\020\007J\031\020\013\032\0020\n2\b\020\t\032\004\030\0010\bH\026¢\006\004\b\013\020\fJ\017\020\016\032\0020\rH\002¢\006\004\b\016\020\017J#\020\023\032\016\022\002\b\0030\021j\006\022\002\b\003`\0222\006\020\020\032\0028\000H\004¢\006\004\b\023\020\024J\035\020\026\032\b\022\004\022\0028\0000\0252\006\020\020\032\0028\000H\004¢\006\004\b\026\020\027J\031\020\033\032\004\030\0010\0322\006\020\031\032\0020\030H\024¢\006\004\b\033\020\034J\033\020\037\032\0020\0032\n\020\036\032\006\022\002\b\0030\035H\002¢\006\004\b\037\020 J#\020!\032\0020\b2\006\020\020\032\0028\0002\n\020\036\032\006\022\002\b\0030\035H\002¢\006\004\b!\020\"J\033\020!\032\0020\b2\n\020\036\032\006\022\002\b\0030\035H\002¢\006\004\b!\020#J)\020&\032\0020\0032\030\020%\032\024\022\006\022\004\030\0010\b\022\004\022\0020\0030\002j\002`$H\026¢\006\004\b&\020\007J\031\020'\032\0020\0032\b\020\t\032\004\030\0010\bH\002¢\006\004\b'\020(J\027\020)\032\0020\n2\006\020\020\032\0028\000H\026¢\006\004\b)\020*J\027\020+\032\0020\0322\006\020\020\032\0028\000H\024¢\006\004\b+\020,J#\020/\032\0020\0322\006\020\020\032\0028\0002\n\020.\032\006\022\002\b\0030-H\024¢\006\004\b/\0200J\027\0202\032\0020\0032\006\020\036\032\00201H\024¢\006\004\b2\0203JX\0209\032\0020\003\"\004\b\001\02042\f\020.\032\b\022\004\022\0028\0010-2\006\020\020\032\0028\0002(\0208\032$\b\001\022\n\022\b\022\004\022\0028\00006\022\n\022\b\022\004\022\0028\00107\022\006\022\004\030\0010\03205H\002ø\001\000¢\006\004\b9\020:J\033\020\031\032\0020\0032\006\020\020\032\0028\000H@ø\001\000¢\006\004\b\031\020;J\035\020=\032\b\022\002\b\003\030\0010<2\006\020\020\032\0028\000H\004¢\006\004\b=\020>J\033\020?\032\0020\0032\006\020\020\032\0028\000H@ø\001\000¢\006\004\b?\020;J\027\020@\032\n\022\004\022\0028\000\030\0010<H\024¢\006\004\b@\020AJ\021\020B\032\004\030\0010\030H\004¢\006\004\bB\020CJ\017\020E\032\0020DH\026¢\006\004\bE\020FJ$\020I\032\b\022\004\022\0020\0030G2\006\020\020\032\0028\000ø\001\000ø\001\001ø\001\002¢\006\004\bH\020,J+\020J\032\0020\003*\006\022\002\b\003072\006\020\020\032\0028\0002\n\020\036\032\006\022\002\b\0030\035H\002¢\006\004\bJ\020KR\024\020M\032\0020D8TX\004¢\006\006\032\004\bL\020FR\032\020P\032\b\022\002\b\003\030\0010\0358DX\004¢\006\006\032\004\bN\020OR\032\020R\032\b\022\002\b\003\030\0010\0358DX\004¢\006\006\032\004\bQ\020OR\024\020S\032\0020\n8$X¤\004¢\006\006\032\004\bS\020TR\024\020U\032\0020\n8$X¤\004¢\006\006\032\004\bU\020TR\021\020V\032\0020\n8F¢\006\006\032\004\bV\020TR\024\020W\032\0020\n8BX\004¢\006\006\032\004\bW\020TR#\020[\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\000060X8F¢\006\006\032\004\bY\020ZR.\020\005\032\034\022\004\022\0028\000\022\004\022\0020\003\030\0010\002j\n\022\004\022\0028\000\030\001`\0048\004X\004¢\006\006\n\004\b\005\020\\R\032\020^\032\0020]8\004X\004¢\006\f\n\004\b^\020_\032\004\b`\020aR\024\020c\032\0020D8BX\004¢\006\006\032\004\bb\020F\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006h"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel;", "E", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "", "countQueueSize", "()I", "element", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/AddLastDesc;", "describeSendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "describeTryOffer", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "closed", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "helpCloseAndGetSendException", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "offer", "(Ljava/lang/Object;)Z", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onClosedIdempotent", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendSuspend", "takeFirstReceiveOrPeekClosed", "()Lkotlinx/coroutines/channels/ReceiveOrClosed;", "takeFirstSendOrPeekClosed", "()Lkotlinx/coroutines/channels/Send;", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "trySend", "helpCloseAndResumeWithSendException", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "getBufferDebugString", "bufferDebugString", "getClosedForReceive", "()Lkotlinx/coroutines/channels/Closed;", "closedForReceive", "getClosedForSend", "closedForSend", "isBufferAlwaysFull", "()Z", "isBufferFull", "isClosedForSend", "isFullImpl", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueueDebugStateString", "queueDebugStateString", "SendBuffered", "SendBufferedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AbstractSendChannel<E>
  implements SendChannel<E>
{
  private static final AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(AbstractSendChannel.class, Object.class, "onCloseHandler");
  private volatile Object onCloseHandler;
  protected final Function1<E, Unit> onUndeliveredElement;
  private final LockFreeLinkedListHead queue;
  
  public AbstractSendChannel(Function1<? super E, Unit> paramFunction1)
  {
    this.onUndeliveredElement = paramFunction1;
    this.queue = new LockFreeLinkedListHead();
    this.onCloseHandler = null;
  }
  
  private final int countQueueSize()
  {
    int i = 0;
    LockFreeLinkedListHead localLockFreeLinkedListHead = this.queue;
    LockFreeLinkedListNode localLockFreeLinkedListNode = (LockFreeLinkedListNode)localLockFreeLinkedListHead.getNext();
    while (!Intrinsics.areEqual(localLockFreeLinkedListNode, localLockFreeLinkedListHead))
    {
      int j = i;
      if ((localLockFreeLinkedListNode instanceof LockFreeLinkedListNode)) {
        j = i + 1;
      }
      localLockFreeLinkedListNode = localLockFreeLinkedListNode.getNextNode();
      i = j;
    }
    return i;
  }
  
  private final String getQueueDebugStateString()
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode1 = this.queue.getNextNode();
    if (localLockFreeLinkedListNode1 == this.queue) {
      return "EmptyQueue";
    }
    String str1;
    if ((localLockFreeLinkedListNode1 instanceof Closed))
    {
      str1 = localLockFreeLinkedListNode1.toString();
    }
    else if ((localLockFreeLinkedListNode1 instanceof Receive))
    {
      str1 = "ReceiveQueued";
    }
    else if ((localLockFreeLinkedListNode1 instanceof Send))
    {
      str1 = "SendQueued";
    }
    else
    {
      str1 = Intrinsics.stringPlus("UNEXPECTED:", localLockFreeLinkedListNode1);
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
    }
    LockFreeLinkedListNode localLockFreeLinkedListNode2 = this.queue.getPrevNode();
    String str2 = str1;
    if (localLockFreeLinkedListNode2 != localLockFreeLinkedListNode1)
    {
      str1 = str1 + ",queueSize=" + countQueueSize();
      str2 = str1;
      if ((localLockFreeLinkedListNode2 instanceof Closed)) {
        str2 = str1 + ",closedForSend=" + localLockFreeLinkedListNode2;
      }
    }
    return str2;
  }
  
  private final void helpClose(Closed<?> paramClosed)
  {
    Object localObject1 = InlineList.constructor-impl$default(null, 1, null);
    for (;;)
    {
      Object localObject2 = paramClosed.getPrevNode();
      if ((localObject2 instanceof Receive)) {
        localObject2 = (Receive)localObject2;
      } else {
        localObject2 = null;
      }
      if (localObject2 == null)
      {
        if (localObject1 != null) {
          if (!(localObject1 instanceof ArrayList))
          {
            ((Receive)localObject1).resumeReceiveClosed(paramClosed);
          }
          else
          {
            if (localObject1 == null) {
              break label124;
            }
            localObject1 = (ArrayList)localObject1;
            int i = ((ArrayList)localObject1).size() - 1;
            if (i >= 0)
            {
              int j;
              do
              {
                j = i - 1;
                ((Receive)((ArrayList)localObject1).get(i)).resumeReceiveClosed(paramClosed);
                i = j;
              } while (j >= 0);
            }
          }
        }
        onClosedIdempotent((LockFreeLinkedListNode)paramClosed);
        return;
        label124:
        throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
      }
      if (!((Receive)localObject2).remove()) {
        ((Receive)localObject2).helpRemove();
      } else {
        localObject1 = InlineList.plus-FjFbRPM(localObject1, localObject2);
      }
    }
  }
  
  private final Throwable helpCloseAndGetSendException(E paramE, Closed<?> paramClosed)
  {
    helpClose(paramClosed);
    Function1 localFunction1 = this.onUndeliveredElement;
    if (localFunction1 != null) {
      for (;;)
      {
        paramE = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(localFunction1, paramE, null, 2, null);
        if (paramE != null) {
          break;
        }
      }
    }
    return paramClosed.getSendException();
    ExceptionsKt.addSuppressed((Throwable)paramE, paramClosed.getSendException());
    throw paramE;
  }
  
  private final Throwable helpCloseAndGetSendException(Closed<?> paramClosed)
  {
    helpClose(paramClosed);
    return paramClosed.getSendException();
  }
  
  private final void helpCloseAndResumeWithSendException(Continuation<?> paramContinuation, E paramE, Closed<?> paramClosed)
  {
    helpClose(paramClosed);
    paramClosed = paramClosed.getSendException();
    Function1 localFunction1 = this.onUndeliveredElement;
    if (localFunction1 != null) {
      for (;;)
      {
        paramE = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(localFunction1, paramE, null, 2, null);
        if (paramE != null) {
          break;
        }
      }
    }
    paramE = Result.Companion;
    paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramClosed)));
    return;
    ExceptionsKt.addSuppressed((Throwable)paramE, paramClosed);
    paramClosed = Result.Companion;
    paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)paramE)));
  }
  
  private final void invokeOnCloseHandler(Throwable paramThrowable)
  {
    Object localObject = this.onCloseHandler;
    if ((localObject != null) && (localObject != AbstractChannelKt.HANDLER_INVOKED) && (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(onCloseHandler$FU, this, localObject, AbstractChannelKt.HANDLER_INVOKED))) {
      ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localObject, 1)).invoke(paramThrowable);
    }
  }
  
  private final boolean isFullImpl()
  {
    boolean bool;
    if ((!(this.queue.getNextNode() instanceof ReceiveOrClosed)) && (isBufferFull())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final <R> void registerSelectSend(SelectInstance<? super R> paramSelectInstance, E paramE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    Object localObject;
    do
    {
      if (paramSelectInstance.isSelected()) {
        return;
      }
      if (isFullImpl())
      {
        SendSelect localSendSelect = new SendSelect(paramE, this, paramSelectInstance, paramFunction2);
        localObject = enqueueSend((Send)localSendSelect);
        if (localObject == null)
        {
          paramSelectInstance.disposeOnSelect((DisposableHandle)localSendSelect);
          return;
        }
        if (!(localObject instanceof Closed))
        {
          if ((localObject != AbstractChannelKt.ENQUEUE_FAILED) && (!(localObject instanceof Receive))) {
            throw new IllegalStateException(("enqueueSend returned " + localObject + ' ').toString());
          }
        }
        else {
          throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(paramE, (Closed)localObject));
        }
      }
      localObject = offerSelectInternal(paramE, paramSelectInstance);
      if (localObject == SelectKt.getALREADY_SELECTED()) {
        return;
      }
    } while ((localObject == AbstractChannelKt.OFFER_FAILED) || (localObject == AtomicKt.RETRY_ATOMIC));
    if (localObject == AbstractChannelKt.OFFER_SUCCESS)
    {
      UndispatchedKt.startCoroutineUnintercepted(paramFunction2, this, paramSelectInstance.getCompletion());
      return;
    }
    if ((localObject instanceof Closed)) {
      throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(paramE, (Closed)localObject));
    }
    paramSelectInstance = Intrinsics.stringPlus("offerSelectInternal returned ", localObject);
    Log5ECF72.a(paramSelectInstance);
    LogE84000.a(paramSelectInstance);
    Log229316.a(paramSelectInstance);
    throw new IllegalStateException(paramSelectInstance.toString());
  }
  
  private final Object sendSuspend(E paramE, Continuation<? super Unit> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
    CancellableContinuation localCancellableContinuation = (CancellableContinuation)localCancellableContinuationImpl;
    Object localObject1;
    do
    {
      if (access$isFullImpl(this))
      {
        if (this.onUndeliveredElement == null) {
          localObject1 = new SendElement(paramE, localCancellableContinuation);
        } else {
          localObject1 = (SendElement)new SendElementWithUndeliveredHandler(paramE, localCancellableContinuation, this.onUndeliveredElement);
        }
        Object localObject2 = enqueueSend((Send)localObject1);
        if (localObject2 == null)
        {
          CancellableContinuationKt.removeOnCancellation(localCancellableContinuation, (LockFreeLinkedListNode)localObject1);
          break;
        }
        if ((localObject2 instanceof Closed))
        {
          access$helpCloseAndResumeWithSendException(this, (Continuation)localCancellableContinuation, paramE, (Closed)localObject2);
          break;
        }
        if ((localObject2 != AbstractChannelKt.ENQUEUE_FAILED) && (!(localObject2 instanceof Receive)))
        {
          paramE = Intrinsics.stringPlus("enqueueSend returned ", localObject2);
          Log5ECF72.a(paramE);
          LogE84000.a(paramE);
          Log229316.a(paramE);
          throw new IllegalStateException(paramE.toString());
        }
      }
      localObject1 = offerInternal(paramE);
      if (localObject1 == AbstractChannelKt.OFFER_SUCCESS)
      {
        paramE = (Continuation)localCancellableContinuation;
        localObject1 = Result.Companion;
        paramE.resumeWith(Result.constructor-impl(Unit.INSTANCE));
        break;
      }
    } while (localObject1 == AbstractChannelKt.OFFER_FAILED);
    if ((localObject1 instanceof Closed))
    {
      access$helpCloseAndResumeWithSendException(this, (Continuation)localCancellableContinuation, paramE, (Closed)localObject1);
      paramE = localCancellableContinuationImpl.getResult();
      if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramE;
      }
      return Unit.INSTANCE;
    }
    paramE = Intrinsics.stringPlus("offerInternal returned ", localObject1);
    Log5ECF72.a(paramE);
    LogE84000.a(paramE);
    Log229316.a(paramE);
    throw new IllegalStateException(paramE.toString());
  }
  
  public boolean close(Throwable paramThrowable)
  {
    Closed localClosed = new Closed(paramThrowable);
    LockFreeLinkedListNode localLockFreeLinkedListNode2 = (LockFreeLinkedListNode)this.queue;
    LockFreeLinkedListNode localLockFreeLinkedListNode1;
    boolean bool1;
    do
    {
      localLockFreeLinkedListNode1 = localLockFreeLinkedListNode2.getPrevNode();
      boolean bool2 = localLockFreeLinkedListNode1 instanceof Closed;
      bool1 = true;
      if (!(bool2 ^ true))
      {
        bool1 = false;
        break;
      }
    } while (!localLockFreeLinkedListNode1.addNext((LockFreeLinkedListNode)localClosed, localLockFreeLinkedListNode2));
    if (!bool1) {
      localClosed = (Closed)this.queue.getPrevNode();
    }
    helpClose(localClosed);
    if (bool1) {
      invokeOnCloseHandler(paramThrowable);
    }
    return bool1;
  }
  
  protected final LockFreeLinkedListNode.AddLastDesc<?> describeSendBuffered(E paramE)
  {
    return (LockFreeLinkedListNode.AddLastDesc)new SendBufferedDesc(this.queue, paramE);
  }
  
  protected final TryOfferDesc<E> describeTryOffer(E paramE)
  {
    return new TryOfferDesc(paramE, this.queue);
  }
  
  protected Object enqueueSend(Send paramSend)
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode1;
    Object localObject;
    if (isBufferAlwaysFull())
    {
      localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)this.queue;
      do
      {
        localObject = localLockFreeLinkedListNode1.getPrevNode();
        if ((localObject instanceof ReceiveOrClosed)) {
          return localObject;
        }
      } while (!((LockFreeLinkedListNode)localObject).addNext((LockFreeLinkedListNode)paramSend, localLockFreeLinkedListNode1));
    }
    else
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode2 = (LockFreeLinkedListNode)this.queue;
      localObject = (LockFreeLinkedListNode.CondAddOp)new LockFreeLinkedListNode.CondAddOp((LockFreeLinkedListNode)paramSend)
      {
        final LockFreeLinkedListNode $node;
        
        public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
        {
          if (jdField_this.isBufferFull()) {
            paramAnonymousLockFreeLinkedListNode = null;
          } else {
            paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
          }
          return paramAnonymousLockFreeLinkedListNode;
        }
      };
      for (;;)
      {
        localLockFreeLinkedListNode1 = localLockFreeLinkedListNode2.getPrevNode();
        if ((localLockFreeLinkedListNode1 instanceof ReceiveOrClosed)) {
          return localLockFreeLinkedListNode1;
        }
        switch (localLockFreeLinkedListNode1.tryCondAddNext((LockFreeLinkedListNode)paramSend, localLockFreeLinkedListNode2, (LockFreeLinkedListNode.CondAddOp)localObject))
        {
        }
      }
      int i = 0;
      break label134;
      i = 1;
      label134:
      if (i == 0) {
        return AbstractChannelKt.ENQUEUE_FAILED;
      }
    }
    return null;
  }
  
  protected String getBufferDebugString()
  {
    return "";
  }
  
  protected final Closed<?> getClosedForReceive()
  {
    Object localObject1 = this.queue.getNextNode();
    boolean bool = localObject1 instanceof Closed;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (Closed)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      helpClose((Closed)localObject1);
    }
    return (Closed<?>)localObject1;
  }
  
  protected final Closed<?> getClosedForSend()
  {
    Object localObject1 = this.queue.getPrevNode();
    boolean bool = localObject1 instanceof Closed;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (Closed)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      helpClose((Closed)localObject1);
    }
    return (Closed<?>)localObject1;
  }
  
  public final SelectClause2<E, SendChannel<E>> getOnSend()
  {
    (SelectClause2)new SelectClause2()
    {
      final AbstractSendChannel<E> this$0;
      
      public <R> void registerSelectClause2(SelectInstance<? super R> paramAnonymousSelectInstance, E paramAnonymousE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        AbstractSendChannel.access$registerSelectSend(this.this$0, paramAnonymousSelectInstance, paramAnonymousE, paramAnonymousFunction2);
      }
    };
  }
  
  protected final LockFreeLinkedListHead getQueue()
  {
    return this.queue;
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
    Closed localClosed = getClosedForSend();
    if ((localClosed != null) && (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(localAtomicReferenceFieldUpdater, this, paramFunction1, AbstractChannelKt.HANDLER_INVOKED))) {
      paramFunction1.invoke(localClosed.closeCause);
    }
  }
  
  protected abstract boolean isBufferAlwaysFull();
  
  protected abstract boolean isBufferFull();
  
  public final boolean isClosedForSend()
  {
    boolean bool;
    if (getClosedForSend() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean offer(E paramE)
  {
    try
    {
      boolean bool = SendChannel.DefaultImpls.offer((SendChannel)this, paramE);
      return bool;
    }
    finally
    {
      Function1 localFunction1 = this.onUndeliveredElement;
      if (localFunction1 != null)
      {
        paramE = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(localFunction1, paramE, null, 2, null);
        if (paramE != null)
        {
          ExceptionsKt.addSuppressed((Throwable)paramE, localThrowable);
          throw paramE;
        }
      }
    }
  }
  
  protected Object offerInternal(E paramE)
  {
    for (;;)
    {
      ReceiveOrClosed localReceiveOrClosed = takeFirstReceiveOrPeekClosed();
      if (localReceiveOrClosed == null) {
        return AbstractChannelKt.OFFER_FAILED;
      }
      Symbol localSymbol = localReceiveOrClosed.tryResumeReceive(paramE, null);
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
        localReceiveOrClosed.completeResumeReceive(paramE);
        return localReceiveOrClosed.getOfferResult();
      }
    }
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    TryOfferDesc localTryOfferDesc = describeTryOffer(paramE);
    paramSelectInstance = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryOfferDesc);
    if (paramSelectInstance != null) {
      return paramSelectInstance;
    }
    paramSelectInstance = (ReceiveOrClosed)localTryOfferDesc.getResult();
    paramSelectInstance.completeResumeReceive(paramE);
    return paramSelectInstance.getOfferResult();
  }
  
  protected void onClosedIdempotent(LockFreeLinkedListNode paramLockFreeLinkedListNode) {}
  
  public final Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    if (offerInternal(paramE) == AbstractChannelKt.OFFER_SUCCESS) {
      return Unit.INSTANCE;
    }
    paramE = sendSuspend(paramE, paramContinuation);
    if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramE;
    }
    return Unit.INSTANCE;
  }
  
  protected final ReceiveOrClosed<?> sendBuffered(E paramE)
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)this.queue;
    LockFreeLinkedListNode localLockFreeLinkedListNode2 = (LockFreeLinkedListNode)new SendBuffered(paramE);
    do
    {
      paramE = localLockFreeLinkedListNode1.getPrevNode();
      if ((paramE instanceof ReceiveOrClosed)) {
        return (ReceiveOrClosed)paramE;
      }
    } while (!paramE.addNext(localLockFreeLinkedListNode2, localLockFreeLinkedListNode1));
    return null;
  }
  
  protected ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed()
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode3 = (LockFreeLinkedListNode)this.queue;
    for (;;)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)localLockFreeLinkedListNode3.getNext();
      LockFreeLinkedListNode localLockFreeLinkedListNode2;
      if (localLockFreeLinkedListNode1 == localLockFreeLinkedListNode3)
      {
        localLockFreeLinkedListNode1 = null;
      }
      else if (!(localLockFreeLinkedListNode1 instanceof ReceiveOrClosed))
      {
        localLockFreeLinkedListNode1 = null;
      }
      else if ((!((ReceiveOrClosed)localLockFreeLinkedListNode1 instanceof Closed)) || (localLockFreeLinkedListNode1.isRemoved()))
      {
        localLockFreeLinkedListNode2 = localLockFreeLinkedListNode1.removeOrNext();
        if (localLockFreeLinkedListNode2 != null) {
          break label72;
        }
      }
      return (ReceiveOrClosed)localLockFreeLinkedListNode1;
      label72:
      localLockFreeLinkedListNode2.helpRemovePrev();
    }
  }
  
  protected final Send takeFirstSendOrPeekClosed()
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode3 = (LockFreeLinkedListNode)this.queue;
    for (;;)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)localLockFreeLinkedListNode3.getNext();
      LockFreeLinkedListNode localLockFreeLinkedListNode2;
      if (localLockFreeLinkedListNode1 == localLockFreeLinkedListNode3)
      {
        localLockFreeLinkedListNode1 = null;
      }
      else if (!(localLockFreeLinkedListNode1 instanceof Send))
      {
        localLockFreeLinkedListNode1 = null;
      }
      else if ((!((Send)localLockFreeLinkedListNode1 instanceof Closed)) || (localLockFreeLinkedListNode1.isRemoved()))
      {
        localLockFreeLinkedListNode2 = localLockFreeLinkedListNode1.removeOrNext();
        if (localLockFreeLinkedListNode2 != null) {
          break label72;
        }
      }
      return (Send)localLockFreeLinkedListNode1;
      label72:
      localLockFreeLinkedListNode2.helpRemovePrev();
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = DebugStringsKt.getClassSimpleName(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append('@');
    str = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + '{' + getQueueDebugStateString() + '}' + getBufferDebugString();
  }
  
  public final Object trySend-JP2dKIU(E paramE)
  {
    paramE = offerInternal(paramE);
    if (paramE == AbstractChannelKt.OFFER_SUCCESS)
    {
      paramE = ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
    }
    else if (paramE == AbstractChannelKt.OFFER_FAILED)
    {
      paramE = getClosedForSend();
      if (paramE == null) {
        return ChannelResult.Companion.failure-PtdJZtk();
      }
      paramE = ChannelResult.Companion.closed-JP2dKIU(helpCloseAndGetSendException(paramE));
    }
    else
    {
      if (!(paramE instanceof Closed)) {
        break label88;
      }
      paramE = ChannelResult.Companion.closed-JP2dKIU(helpCloseAndGetSendException((Closed)paramE));
    }
    return paramE;
    label88:
    paramE = Intrinsics.stringPlus("trySend returned ", paramE);
    Log5ECF72.a(paramE);
    LogE84000.a(paramE);
    Log229316.a(paramE);
    throw new IllegalStateException(paramE.toString());
  }
  
  @Metadata(d1={"\0006\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\000\030\000*\006\b\001\020\001 \0012\0020\002B\r\022\006\020\003\032\0028\001¢\006\002\020\004J\b\020\n\032\0020\013H\026J\024\020\f\032\0020\0132\n\020\r\032\006\022\002\b\0030\016H\026J\b\020\017\032\0020\020H\026J\024\020\021\032\004\030\0010\0222\b\020\023\032\004\030\0010\024H\026R\022\020\003\032\0028\0018\006X\004¢\006\004\n\002\020\005R\026\020\006\032\004\030\0010\0078VX\004¢\006\006\032\004\b\b\020\t¨\006\025"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "E", "Lkotlinx/coroutines/channels/Send;", "element", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class SendBuffered<E>
    extends Send
  {
    public final E element;
    
    public SendBuffered(E paramE)
    {
      this.element = paramE;
    }
    
    public void completeResumeSend() {}
    
    public Object getPollResult()
    {
      return this.element;
    }
    
    public void resumeSendClosed(Closed<?> paramClosed)
    {
      if (!DebugKt.getASSERTIONS_ENABLED()) {
        return;
      }
      throw new AssertionError();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append("SendBuffered@");
      String str = DebugStringsKt.getHexAddress(this);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + '(' + this.element + ')';
    }
    
    public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      Symbol localSymbol = CancellableContinuationImplKt.RESUME_TOKEN;
      if (paramPrepareOp != null) {
        paramPrepareOp.finishPrepare();
      }
      return localSymbol;
    }
  }
  
  @Metadata(d1={"\000(\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\030\002\n\000\b\022\030\000*\004\b\001\020\0012\036\022\n\022\b\022\004\022\002H\0010\0030\002j\016\022\n\022\b\022\004\022\002H\0010\003`\004B\025\022\006\020\005\032\0020\006\022\006\020\007\032\0028\001¢\006\002\020\bJ\022\020\t\032\004\030\0010\n2\006\020\013\032\0020\fH\024¨\006\r"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "Lkotlinx/coroutines/internal/AddLastDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "element", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static class SendBufferedDesc<E>
    extends LockFreeLinkedListNode.AddLastDesc<AbstractSendChannel.SendBuffered<? extends E>>
  {
    public SendBufferedDesc(LockFreeLinkedListHead paramLockFreeLinkedListHead, E paramE)
    {
      super((LockFreeLinkedListNode)new AbstractSendChannel.SendBuffered(paramE));
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      if (!(paramLockFreeLinkedListNode instanceof Closed)) {
        if ((paramLockFreeLinkedListNode instanceof ReceiveOrClosed)) {
          paramLockFreeLinkedListNode = AbstractChannelKt.OFFER_FAILED;
        } else {
          paramLockFreeLinkedListNode = null;
        }
      }
      return paramLockFreeLinkedListNode;
    }
  }
  
  @Metadata(d1={"\000V\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\006\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\001\020\001*\004\b\002\020\0022\0020\0032\0020\004BV\022\006\020\005\032\0028\001\022\f\020\006\032\b\022\004\022\0028\0010\007\022\f\020\b\032\b\022\004\022\0028\0020\t\022(\020\n\032$\b\001\022\n\022\b\022\004\022\0028\0010\f\022\n\022\b\022\004\022\0028\0020\r\022\006\022\004\030\0010\0160\013ø\001\000¢\006\002\020\017J\b\020\024\032\0020\025H\026J\b\020\026\032\0020\025H\026J\024\020\027\032\0020\0252\n\020\030\032\006\022\002\b\0030\031H\026J\b\020\032\032\0020\033H\026J\024\020\034\032\004\030\0010\0352\b\020\036\032\004\030\0010\037H\026J\b\020 \032\0020\025H\026R7\020\n\032$\b\001\022\n\022\b\022\004\022\0028\0010\f\022\n\022\b\022\004\022\0028\0020\r\022\006\022\004\030\0010\0160\0138\006X\004ø\001\000¢\006\004\n\002\020\020R\026\020\006\032\b\022\004\022\0028\0010\0078\006X\004¢\006\002\n\000R\026\020\005\032\0028\001X\004¢\006\n\n\002\020\023\032\004\b\021\020\022R\026\020\b\032\b\022\004\022\0028\0020\t8\006X\004¢\006\002\n\000\002\004\n\002\b\031¨\006!"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$SendSelect;", "E", "R", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/DisposableHandle;", "pollResult", "channel", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/AbstractSendChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "getPollResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "completeResumeSend", "", "dispose", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "undeliveredElement", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class SendSelect<E, R>
    extends Send
    implements DisposableHandle
  {
    public final Function2<SendChannel<? super E>, Continuation<? super R>, Object> block;
    public final AbstractSendChannel<E> channel;
    private final E pollResult;
    public final SelectInstance<R> select;
    
    public SendSelect(E paramE, AbstractSendChannel<E> paramAbstractSendChannel, SelectInstance<? super R> paramSelectInstance, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramFunction2)
    {
      this.pollResult = paramE;
      this.channel = paramAbstractSendChannel;
      this.select = paramSelectInstance;
      this.block = paramFunction2;
    }
    
    public void completeResumeSend()
    {
      CancellableKt.startCoroutineCancellable$default(this.block, this.channel, this.select.getCompletion(), null, 4, null);
    }
    
    public void dispose()
    {
      if (!remove()) {
        return;
      }
      undeliveredElement();
    }
    
    public E getPollResult()
    {
      return (E)this.pollResult;
    }
    
    public void resumeSendClosed(Closed<?> paramClosed)
    {
      if (this.select.trySelect()) {
        this.select.resumeSelectWithException(paramClosed.getSendException());
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append("SendSelect@");
      String str = DebugStringsKt.getHexAddress(this);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + '(' + getPollResult() + ")[" + this.channel + ", " + this.select + ']';
    }
    
    public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      return (Symbol)this.select.trySelectOther(paramPrepareOp);
    }
    
    public void undeliveredElement()
    {
      Function1 localFunction1 = this.channel.onUndeliveredElement;
      if (localFunction1 != null) {
        OnUndeliveredElementKt.callUndeliveredElement(localFunction1, getPollResult(), this.select.getCompletion().getContext());
      }
    }
  }
  
  @Metadata(d1={"\0006\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\b\004\030\000*\004\b\001\020\0012\036\022\n\022\b\022\004\022\002H\0010\0030\002j\016\022\n\022\b\022\004\022\002H\0010\003`\004B\025\022\006\020\005\032\0028\001\022\006\020\006\032\0020\007¢\006\002\020\bJ\022\020\n\032\004\030\0010\0132\006\020\f\032\0020\rH\024J\026\020\016\032\004\030\0010\0132\n\020\017\032\0060\020j\002`\021H\026R\022\020\005\032\0028\0018\006X\004¢\006\004\n\002\020\t¨\006\022"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "element", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "Ljava/lang/Object;", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  protected static final class TryOfferDesc<E>
    extends LockFreeLinkedListNode.RemoveFirstDesc<ReceiveOrClosed<? super E>>
  {
    public final E element;
    
    public TryOfferDesc(E paramE, LockFreeLinkedListHead paramLockFreeLinkedListHead)
    {
      super();
      this.element = paramE;
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      if (!(paramLockFreeLinkedListNode instanceof Closed)) {
        if (!(paramLockFreeLinkedListNode instanceof ReceiveOrClosed)) {
          paramLockFreeLinkedListNode = AbstractChannelKt.OFFER_FAILED;
        } else {
          paramLockFreeLinkedListNode = null;
        }
      }
      return paramLockFreeLinkedListNode;
    }
    
    public Object onPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      paramPrepareOp = ((ReceiveOrClosed)paramPrepareOp.affected).tryResumeReceive(this.element, paramPrepareOp);
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
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/AbstractSendChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */