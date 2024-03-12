package kotlinx.coroutines.channels;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectInstance<*>;
import kotlinx.coroutines.selects.SelectKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000T\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\003\n\002\020\013\n\002\b\006\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\006\n\002\030\002\n\000\b\020\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B'\022 \020\003\032\034\022\004\022\0028\000\022\004\022\0020\005\030\0010\004j\n\022\004\022\0028\000\030\001`\006¢\006\002\020\007J\026\020\030\032\0020\r2\f\020\031\032\b\022\004\022\0028\0000\032H\024J\025\020\033\032\0020\0272\006\020\034\032\0028\000H\024¢\006\002\020\035J!\020\036\032\0020\0272\006\020\034\032\0028\0002\n\020\037\032\006\022\002\b\0030 H\024¢\006\002\020!J\020\020\"\032\0020\0052\006\020#\032\0020\rH\024J\n\020$\032\004\030\0010\027H\024J\026\020%\032\004\030\0010\0272\n\020\037\032\006\022\002\b\0030 H\024J\024\020&\032\004\030\0010'2\b\020\034\032\004\030\0010\027H\002R\024\020\b\032\0020\t8TX\004¢\006\006\032\004\b\n\020\013R\024\020\f\032\0020\r8DX\004¢\006\006\032\004\b\f\020\016R\024\020\017\032\0020\r8DX\004¢\006\006\032\004\b\017\020\016R\024\020\020\032\0020\r8DX\004¢\006\006\032\004\b\020\020\016R\024\020\021\032\0020\r8DX\004¢\006\006\032\004\b\021\020\016R\024\020\022\032\0020\r8VX\004¢\006\006\032\004\b\022\020\016R\022\020\023\032\0060\024j\002`\025X\004¢\006\002\n\000R\020\020\026\032\004\030\0010\027X\016¢\006\002\n\000¨\006("}, d2={"Lkotlinx/coroutines/channels/ConflatedChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "bufferDebugString", "", "getBufferDebugString", "()Ljava/lang/String;", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "isEmpty", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "value", "", "enqueueReceiveInternal", "receive", "Lkotlinx/coroutines/channels/Receive;", "offerInternal", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotent", "wasClosed", "pollInternal", "pollSelectInternal", "updateValueLocked", "Lkotlinx/coroutines/internal/UndeliveredElementException;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class ConflatedChannel<E>
  extends AbstractChannel<E>
{
  private final ReentrantLock lock = new ReentrantLock();
  private Object value = AbstractChannelKt.EMPTY;
  
  public ConflatedChannel(Function1<? super E, Unit> paramFunction1)
  {
    super(paramFunction1);
  }
  
  private final UndeliveredElementException updateValueLocked(Object paramObject)
  {
    Object localObject1 = this.value;
    Object localObject2 = AbstractChannelKt.EMPTY;
    UndeliveredElementException localUndeliveredElementException = null;
    if (localObject1 != localObject2)
    {
      localObject2 = this.onUndeliveredElement;
      if (localObject2 != null) {
        localUndeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default((Function1)localObject2, localObject1, null, 2, null);
      }
    }
    this.value = paramObject;
    return localUndeliveredElementException;
  }
  
  protected boolean enqueueReceiveInternal(Receive<? super E> paramReceive)
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      boolean bool = super.enqueueReceiveInternal(paramReceive);
      return bool;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected String getBufferDebugString()
  {
    return "(value=" + this.value + ')';
  }
  
  protected final boolean isBufferAlwaysEmpty()
  {
    return false;
  }
  
  protected final boolean isBufferAlwaysFull()
  {
    return false;
  }
  
  protected final boolean isBufferEmpty()
  {
    boolean bool;
    if (this.value == AbstractChannelKt.EMPTY) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected final boolean isBufferFull()
  {
    return false;
  }
  
  public boolean isEmpty()
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      boolean bool = isEmptyImpl();
      return bool;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object offerInternal(E paramE)
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      Object localObject1 = getClosedForSend();
      if (localObject1 == null)
      {
        if (this.value == AbstractChannelKt.EMPTY)
        {
          do
          {
            localObject1 = takeFirstReceiveOrPeekClosed();
            if (localObject1 == null) {
              break;
            }
            boolean bool = localObject1 instanceof Closed;
            if (bool) {
              return localObject1;
            }
            localObject2 = ((ReceiveOrClosed)localObject1).tryResumeReceive(paramE, null);
          } while (localObject2 == null);
          if (DebugKt.getASSERTIONS_ENABLED())
          {
            int i;
            if (localObject2 == CancellableContinuationImplKt.RESUME_TOKEN) {
              i = 1;
            } else {
              i = 0;
            }
            if (i == 0)
            {
              paramE = new java/lang/AssertionError;
              paramE.<init>();
              throw paramE;
            }
          }
          Object localObject2 = Unit.INSTANCE;
          localLock.unlock();
          ((ReceiveOrClosed)localObject1).completeResumeReceive(paramE);
          return ((ReceiveOrClosed)localObject1).getOfferResult();
        }
        paramE = updateValueLocked(paramE);
        if (paramE == null)
        {
          paramE = AbstractChannelKt.OFFER_SUCCESS;
          return paramE;
        }
        throw paramE;
      }
      return localObject1;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      Object localObject = getClosedForSend();
      if (localObject == null)
      {
        if (this.value == AbstractChannelKt.EMPTY)
        {
          do
          {
            AbstractSendChannel.TryOfferDesc localTryOfferDesc = describeTryOffer(paramE);
            localObject = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryOfferDesc);
            if (localObject == null)
            {
              paramSelectInstance = localTryOfferDesc.getResult();
              localObject = Unit.INSTANCE;
              localLock.unlock();
              Intrinsics.checkNotNull(paramSelectInstance);
              ((ReceiveOrClosed)paramSelectInstance).completeResumeReceive(paramE);
              return ((ReceiveOrClosed)paramSelectInstance).getOfferResult();
            }
            if (localObject == AbstractChannelKt.OFFER_FAILED) {
              break;
            }
          } while (localObject == AtomicKt.RETRY_ATOMIC);
          if ((localObject != SelectKt.getALREADY_SELECTED()) && (!(localObject instanceof Closed)))
          {
            paramE = new java/lang/IllegalStateException;
            paramSelectInstance = Intrinsics.stringPlus("performAtomicTrySelect(describeTryOffer) returned ", localObject);
            Log5ECF72.a(paramSelectInstance);
            LogE84000.a(paramSelectInstance);
            Log229316.a(paramSelectInstance);
            paramE.<init>(paramSelectInstance.toString());
            throw paramE;
          }
          return localObject;
        }
        if (!paramSelectInstance.trySelect())
        {
          paramE = SelectKt.getALREADY_SELECTED();
          return paramE;
        }
        paramE = updateValueLocked(paramE);
        if (paramE == null)
        {
          paramE = AbstractChannelKt.OFFER_SUCCESS;
          return paramE;
        }
        throw paramE;
      }
      return localObject;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected void onCancelIdempotent(boolean paramBoolean)
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      UndeliveredElementException localUndeliveredElementException = updateValueLocked(AbstractChannelKt.EMPTY);
      Unit localUnit = Unit.INSTANCE;
      localLock.unlock();
      super.onCancelIdempotent(paramBoolean);
      if (localUndeliveredElementException == null) {
        return;
      }
      throw localUndeliveredElementException;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object pollInternal()
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      if (this.value == AbstractChannelKt.EMPTY)
      {
        localObject3 = getClosedForSend();
        localObject1 = localObject3;
        if (localObject3 == null) {
          localObject1 = AbstractChannelKt.POLL_FAILED;
        }
        return localObject1;
      }
      Object localObject3 = this.value;
      this.value = AbstractChannelKt.EMPTY;
      Object localObject1 = Unit.INSTANCE;
      return localObject3;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object pollSelectInternal(SelectInstance<?> paramSelectInstance)
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      if (this.value == AbstractChannelKt.EMPTY)
      {
        localObject = getClosedForSend();
        paramSelectInstance = (SelectInstance<?>)localObject;
        if (localObject == null) {
          paramSelectInstance = AbstractChannelKt.POLL_FAILED;
        }
        return paramSelectInstance;
      }
      if (!paramSelectInstance.trySelect())
      {
        paramSelectInstance = SelectKt.getALREADY_SELECTED();
        return paramSelectInstance;
      }
      paramSelectInstance = this.value;
      this.value = AbstractChannelKt.EMPTY;
      Object localObject = Unit.INSTANCE;
      return paramSelectInstance;
    }
    finally
    {
      localLock.unlock();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ConflatedChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */