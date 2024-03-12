package kotlinx.coroutines.channels;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectInstance<*>;
import kotlinx.coroutines.selects.SelectKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000n\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\n\002\020\000\n\002\b\006\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\002\n\002\020\021\n\002\b\002\n\002\020\016\n\002\b\f\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\b\020\030\000*\004\b\000\020\0012\b\022\004\022\0028\0000BB9\022\006\020\003\032\0020\002\022\006\020\005\032\0020\004\022 \020\t\032\034\022\004\022\0028\000\022\004\022\0020\007\030\0010\006j\n\022\004\022\0028\000\030\001`\b¢\006\004\b\n\020\013J\037\020\016\032\0020\0072\006\020\f\032\0020\0022\006\020\r\032\0028\000H\002¢\006\004\b\016\020\017J\035\020\023\032\0020\0222\f\020\021\032\b\022\004\022\0028\0000\020H\024¢\006\004\b\023\020\024J\031\020\030\032\004\030\0010\0272\006\020\026\032\0020\025H\024¢\006\004\b\030\020\031J\027\020\032\032\0020\0072\006\020\f\032\0020\002H\002¢\006\004\b\032\020\033J\027\020\034\032\0020\0272\006\020\r\032\0028\000H\024¢\006\004\b\034\020\035J#\020 \032\0020\0272\006\020\r\032\0028\0002\n\020\037\032\006\022\002\b\0030\036H\024¢\006\004\b \020!J\027\020#\032\0020\0072\006\020\"\032\0020\022H\024¢\006\004\b#\020$J\021\020%\032\004\030\0010\027H\024¢\006\004\b%\020&J\035\020'\032\004\030\0010\0272\n\020\037\032\006\022\002\b\0030\036H\024¢\006\004\b'\020(J\031\020*\032\004\030\0010)2\006\020\f\032\0020\002H\002¢\006\004\b*\020+R\036\020-\032\n\022\006\022\004\030\0010\0270,8\002@\002X\016¢\006\006\n\004\b-\020.R\024\0202\032\0020/8TX\004¢\006\006\032\004\b0\0201R\024\020\003\032\0020\0028\002X\004¢\006\006\n\004\b\003\0203R\026\0204\032\0020\0028\002@\002X\016¢\006\006\n\004\b4\0203R\024\0205\032\0020\0228DX\004¢\006\006\032\004\b5\0206R\024\0207\032\0020\0228DX\004¢\006\006\032\004\b7\0206R\024\0208\032\0020\0228DX\004¢\006\006\032\004\b8\0206R\024\0209\032\0020\0228DX\004¢\006\006\032\004\b9\0206R\024\020:\032\0020\0228VX\004¢\006\006\032\004\b:\0206R\024\020;\032\0020\0228VX\004¢\006\006\032\004\b;\0206R\030\020>\032\0060<j\002`=8\002X\004¢\006\006\n\004\b>\020?R\024\020\005\032\0020\0048\002X\004¢\006\006\n\004\b\005\020@¨\006A"}, d2={"Lkotlinx/coroutines/channels/ArrayChannel;", "E", "", "capacity", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(ILkotlinx/coroutines/channels/BufferOverflow;Lkotlin/jvm/functions/Function1;)V", "currentSize", "element", "enqueueElement", "(ILjava/lang/Object;)V", "Lkotlinx/coroutines/channels/Receive;", "receive", "", "enqueueReceiveInternal", "(Lkotlinx/coroutines/channels/Receive;)Z", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "ensureCapacity", "(I)V", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "wasClosed", "onCancelIdempotent", "(Z)V", "pollInternal", "()Ljava/lang/Object;", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/Symbol;", "updateBufferSize", "(I)Lkotlinx/coroutines/internal/Symbol;", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "I", "head", "isBufferAlwaysEmpty", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "isClosedForReceive", "isEmpty", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/channels/BufferOverflow;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;"}, k=1, mv={1, 6, 0}, xi=48)
public class ArrayChannel<E>
  extends AbstractChannel<E>
{
  private Object[] buffer;
  private final int capacity;
  private int head;
  private final ReentrantLock lock;
  private final BufferOverflow onBufferOverflow;
  private volatile int size;
  
  public ArrayChannel(int paramInt, BufferOverflow paramBufferOverflow, Function1<? super E, Unit> paramFunction1)
  {
    super(paramFunction1);
    this.capacity = paramInt;
    this.onBufferOverflow = paramBufferOverflow;
    int i = 1;
    if (paramInt < 1) {
      i = 0;
    }
    if (i != 0)
    {
      this.lock = new ReentrantLock();
      paramBufferOverflow = new Object[Math.min(paramInt, 8)];
      ArraysKt.fill$default(paramBufferOverflow, AbstractChannelKt.EMPTY, 0, 0, 6, null);
      this.buffer = paramBufferOverflow;
      this.size = 0;
      return;
    }
    throw new IllegalArgumentException(("ArrayChannel capacity must be at least 1, but " + paramInt + " was specified").toString());
  }
  
  private final void enqueueElement(int paramInt, E paramE)
  {
    Object[] arrayOfObject;
    if (paramInt < this.capacity)
    {
      ensureCapacity(paramInt);
      arrayOfObject = this.buffer;
      arrayOfObject[((this.head + paramInt) % arrayOfObject.length)] = paramE;
    }
    else
    {
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (this.onBufferOverflow == BufferOverflow.DROP_OLDEST) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw new AssertionError();
        }
      }
      arrayOfObject = this.buffer;
      int i = this.head;
      arrayOfObject[(i % arrayOfObject.length)] = null;
      arrayOfObject[((i + paramInt) % arrayOfObject.length)] = paramE;
      this.head = ((i + 1) % arrayOfObject.length);
    }
  }
  
  private final void ensureCapacity(int paramInt)
  {
    Object[] arrayOfObject1 = this.buffer;
    if (paramInt >= arrayOfObject1.length)
    {
      int k = Math.min(arrayOfObject1.length * 2, this.capacity);
      Object[] arrayOfObject2 = new Object[k];
      int j;
      for (int i = 0; i < paramInt; i = j)
      {
        j = i + 1;
        arrayOfObject1 = this.buffer;
        arrayOfObject2[i] = arrayOfObject1[((this.head + i) % arrayOfObject1.length)];
      }
      ArraysKt.fill(arrayOfObject2, AbstractChannelKt.EMPTY, paramInt, k);
      this.buffer = arrayOfObject2;
      this.head = 0;
    }
  }
  
  private final Symbol updateBufferSize(int paramInt)
  {
    int i = this.capacity;
    Symbol localSymbol = null;
    if (paramInt < i)
    {
      this.size = (paramInt + 1);
      return null;
    }
    BufferOverflow localBufferOverflow = this.onBufferOverflow;
    switch (WhenMappings.$EnumSwitchMapping$0[localBufferOverflow.ordinal()])
    {
    default: 
      throw new NoWhenBranchMatchedException();
    case 3: 
      break;
    case 2: 
      localSymbol = AbstractChannelKt.OFFER_SUCCESS;
      break;
    case 1: 
      localSymbol = AbstractChannelKt.OFFER_FAILED;
    }
    return localSymbol;
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
  
  protected Object enqueueSend(Send paramSend)
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      paramSend = super.enqueueSend(paramSend);
      return paramSend;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected String getBufferDebugString()
  {
    return "(buffer:capacity=" + this.capacity + ",size=" + this.size + ')';
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
    if (this.size == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected final boolean isBufferFull()
  {
    boolean bool;
    if ((this.size == this.capacity) && (this.onBufferOverflow == BufferOverflow.SUSPEND)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isClosedForReceive()
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      boolean bool = super.isClosedForReceive();
      return bool;
    }
    finally
    {
      localLock.unlock();
    }
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
      int j = this.size;
      Object localObject1 = getClosedForSend();
      if (localObject1 == null)
      {
        localObject1 = updateBufferSize(j);
        if (localObject1 == null)
        {
          if (j == 0)
          {
            do
            {
              localObject1 = takeFirstReceiveOrPeekClosed();
              if (localObject1 == null) {
                break;
              }
              if ((localObject1 instanceof Closed))
              {
                this.size = j;
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
            this.size = j;
            Object localObject2 = Unit.INSTANCE;
            localLock.unlock();
            ((ReceiveOrClosed)localObject1).completeResumeReceive(paramE);
            return ((ReceiveOrClosed)localObject1).getOfferResult();
          }
          enqueueElement(j, paramE);
          paramE = AbstractChannelKt.OFFER_SUCCESS;
          return paramE;
        }
        return localObject1;
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
      int i = this.size;
      Object localObject = getClosedForSend();
      if (localObject == null)
      {
        localObject = updateBufferSize(i);
        if (localObject == null)
        {
          if (i == 0)
          {
            do
            {
              AbstractSendChannel.TryOfferDesc localTryOfferDesc = describeTryOffer(paramE);
              localObject = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryOfferDesc);
              if (localObject == null)
              {
                this.size = i;
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
            this.size = i;
            return localObject;
          }
          if (!paramSelectInstance.trySelect())
          {
            this.size = i;
            paramE = SelectKt.getALREADY_SELECTED();
            return paramE;
          }
          enqueueElement(i, paramE);
          paramE = AbstractChannelKt.OFFER_SUCCESS;
          return paramE;
        }
        return localObject;
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
    Function1 localFunction1 = this.onUndeliveredElement;
    Object localObject1 = null;
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      int j = this.size;
      int i = 0;
      while (i < j)
      {
        Object localObject4 = this.buffer[this.head];
        localObject3 = localObject1;
        if (localFunction1 != null)
        {
          localObject3 = localObject1;
          if (localObject4 != AbstractChannelKt.EMPTY) {
            localObject3 = OnUndeliveredElementKt.callUndeliveredElementCatchingException(localFunction1, localObject4, (UndeliveredElementException)localObject1);
          }
        }
        this.buffer[this.head] = AbstractChannelKt.EMPTY;
        this.head = ((this.head + 1) % this.buffer.length);
        i++;
        localObject1 = localObject3;
      }
      this.size = 0;
      Object localObject3 = Unit.INSTANCE;
      localLock.unlock();
      super.onCancelIdempotent(paramBoolean);
      if (localObject1 == null) {
        return;
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object pollInternal()
  {
    Object localObject1 = null;
    Object localObject5 = null;
    int j = 0;
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      int k = this.size;
      if (k == 0)
      {
        localObject3 = getClosedForSend();
        localObject1 = localObject3;
        if (localObject3 == null) {
          localObject1 = AbstractChannelKt.POLL_FAILED;
        }
        return localObject1;
      }
      Object localObject3 = this.buffer;
      int i = this.head;
      Object localObject6 = localObject3[i];
      localObject3[i] = null;
      this.size = (k - 1);
      Object localObject4 = AbstractChannelKt.POLL_FAILED;
      i = j;
      localObject3 = localObject4;
      if (k == this.capacity)
      {
        localObject1 = localObject5;
        for (;;)
        {
          localObject3 = takeFirstSendOrPeekClosed();
          if (localObject3 == null)
          {
            i = j;
            localObject3 = localObject4;
            break;
          }
          localObject1 = localObject3;
          localObject3 = ((Send)localObject1).tryResumeSend(null);
          if (localObject3 != null)
          {
            if (DebugKt.getASSERTIONS_ENABLED())
            {
              if (localObject3 == CancellableContinuationImplKt.RESUME_TOKEN) {
                i = 1;
              } else {
                i = 0;
              }
              if (i == 0)
              {
                localObject1 = new java/lang/AssertionError;
                ((AssertionError)localObject1).<init>();
                throw ((Throwable)localObject1);
              }
            }
            i = 1;
            localObject3 = ((Send)localObject1).getPollResult();
            break;
          }
          ((Send)localObject1).undeliveredElement();
        }
      }
      if ((localObject3 != AbstractChannelKt.POLL_FAILED) && (!(localObject3 instanceof Closed)))
      {
        this.size = k;
        localObject4 = this.buffer;
        localObject4[((this.head + k) % localObject4.length)] = localObject3;
      }
      this.head = ((this.head + 1) % this.buffer.length);
      localObject3 = Unit.INSTANCE;
      localLock.unlock();
      if (i != 0)
      {
        Intrinsics.checkNotNull(localObject1);
        ((Send)localObject1).completeResumeSend();
      }
      return localObject6;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object pollSelectInternal(SelectInstance<?> paramSelectInstance)
  {
    Object localObject3 = null;
    int j = 0;
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      int k = this.size;
      if (k == 0)
      {
        localObject1 = getClosedForSend();
        paramSelectInstance = (SelectInstance<?>)localObject1;
        if (localObject1 == null) {
          paramSelectInstance = AbstractChannelKt.POLL_FAILED;
        }
        return paramSelectInstance;
      }
      Object localObject1 = this.buffer;
      int i = this.head;
      Object localObject4 = localObject1[i];
      localObject1[i] = null;
      this.size = (k - 1);
      Symbol localSymbol = AbstractChannelKt.POLL_FAILED;
      localObject1 = localObject3;
      i = j;
      Object localObject2 = localSymbol;
      if (k == this.capacity)
      {
        do
        {
          localObject2 = describeTryPoll();
          localObject1 = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localObject2);
          if (localObject1 == null)
          {
            localObject1 = ((AbstractChannel.TryPollDesc)localObject2).getResult();
            i = 1;
            Intrinsics.checkNotNull(localObject1);
            localObject2 = ((Send)localObject1).getPollResult();
            break;
          }
          if (localObject1 == AbstractChannelKt.POLL_FAILED)
          {
            localObject1 = localObject3;
            i = j;
            localObject2 = localSymbol;
            break;
          }
        } while (localObject1 == AtomicKt.RETRY_ATOMIC);
        if (localObject1 == SelectKt.getALREADY_SELECTED())
        {
          this.size = k;
          this.buffer[this.head] = localObject4;
          return localObject1;
        }
        if ((localObject1 instanceof Closed))
        {
          localObject3 = localObject1;
          i = 1;
          localObject2 = localObject1;
          localObject1 = localObject3;
        }
        else
        {
          paramSelectInstance = new java/lang/IllegalStateException;
          localObject1 = Intrinsics.stringPlus("performAtomicTrySelect(describeTryOffer) returned ", localObject1);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          paramSelectInstance.<init>(localObject1.toString());
          throw paramSelectInstance;
        }
      }
      if ((localObject2 != AbstractChannelKt.POLL_FAILED) && (!(localObject2 instanceof Closed)))
      {
        this.size = k;
        paramSelectInstance = this.buffer;
        paramSelectInstance[((this.head + k) % paramSelectInstance.length)] = localObject2;
      }
      else if (!paramSelectInstance.trySelect())
      {
        this.size = k;
        this.buffer[this.head] = localObject4;
        paramSelectInstance = SelectKt.getALREADY_SELECTED();
        return paramSelectInstance;
      }
      this.head = ((this.head + 1) % this.buffer.length);
      paramSelectInstance = Unit.INSTANCE;
      localLock.unlock();
      if (i != 0)
      {
        Intrinsics.checkNotNull(localObject1);
        ((Send)localObject1).completeResumeSend();
      }
      return localObject4;
    }
    finally
    {
      localLock.unlock();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ArrayChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */