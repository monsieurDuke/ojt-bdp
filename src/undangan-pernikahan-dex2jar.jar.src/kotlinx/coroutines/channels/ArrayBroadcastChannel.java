package kotlinx.coroutines.channels;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectInstance<*>;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(d1={"\000\001\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\003\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\005\n\002\020\t\n\002\b\006\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\021\n\002\b\002\n\002\020\016\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\020\n\002\020!\n\002\030\002\n\002\b\b\n\002\030\002\n\002\030\002\b\000\030\000*\004\b\000\020\0012\b\022\004\022\0028\0000L2\b\022\004\022\0028\0000M:\001JB\017\022\006\020\003\032\0020\002¢\006\004\b\004\020\005J\031\020\t\032\0020\b2\b\020\007\032\004\030\0010\006H\027¢\006\004\b\t\020\nJ\037\020\t\032\0020\r2\016\020\007\032\n\030\0010\013j\004\030\001`\fH\026¢\006\004\b\t\020\016J\031\020\017\032\0020\b2\b\020\007\032\004\030\0010\006H\002¢\006\004\b\017\020\nJ\017\020\020\032\0020\rH\002¢\006\004\b\020\020\021J\031\020\022\032\0020\b2\b\020\007\032\004\030\0010\006H\026¢\006\004\b\022\020\nJ\017\020\024\032\0020\023H\002¢\006\004\b\024\020\025J\027\020\027\032\0028\0002\006\020\026\032\0020\023H\002¢\006\004\b\027\020\030J\027\020\033\032\0020\0322\006\020\031\032\0028\000H\024¢\006\004\b\033\020\034J#\020\037\032\0020\0322\006\020\031\032\0028\0002\n\020\036\032\006\022\002\b\0030\035H\024¢\006\004\b\037\020 J\025\020\"\032\b\022\004\022\0028\0000!H\026¢\006\004\b\"\020#J4\020'\032\0020\r2\020\b\002\020%\032\n\022\004\022\0028\000\030\0010$2\020\b\002\020&\032\n\022\004\022\0028\000\030\0010$H\020¢\006\004\b'\020(R\034\020*\032\n\022\006\022\004\030\0010\0320)8\002X\004¢\006\006\n\004\b*\020+R\024\020/\032\0020,8TX\004¢\006\006\032\004\b-\020.R\030\0202\032\00600j\002`18\002X\004¢\006\006\n\004\b2\0203R\027\020\003\032\0020\0028\006¢\006\f\n\004\b\003\0204\032\004\b5\0206R$\020;\032\0020\0232\006\0207\032\0020\0238B@BX\016¢\006\f\032\004\b8\020\025\"\004\b9\020:R\024\020<\032\0020\b8TX\004¢\006\006\032\004\b<\020=R\024\020>\032\0020\b8TX\004¢\006\006\032\004\b>\020=R$\020A\032\0020\0022\006\0207\032\0020\0028B@BX\016¢\006\f\032\004\b?\0206\"\004\b@\020\005R6\020D\032\036\022\n\022\b\022\004\022\0028\0000$0Bj\016\022\n\022\b\022\004\022\0028\0000$`C8\002X\004¢\006\f\n\004\bD\020E\022\004\bF\020\021R$\020I\032\0020\0232\006\0207\032\0020\0238B@BX\016¢\006\f\032\004\bG\020\025\"\004\bH\020:¨\006K"}, d2={"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "E", "", "capacity", "<init>", "(I)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "checkSubOffers", "()V", "close", "", "computeMinHead", "()J", "index", "elementAt", "(J)Ljava/lang/Object;", "element", "", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "addSub", "removeSub", "updateHead", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;)V", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "I", "getCapacity", "()I", "value", "getHead", "setHead", "(J)V", "head", "isBufferAlwaysFull", "()Z", "isBufferFull", "getSize", "setSize", "size", "", "Lkotlinx/coroutines/internal/SubscribersList;", "subscribers", "Ljava/util/List;", "getSubscribers$annotations", "getTail", "setTail", "tail", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k=1, mv={1, 6, 0}, xi=48)
public final class ArrayBroadcastChannel<E>
  extends AbstractSendChannel<E>
  implements BroadcastChannel<E>
{
  private volatile long _head;
  private volatile int _size;
  private volatile long _tail;
  private final Object[] buffer;
  private final ReentrantLock bufferLock;
  private final int capacity;
  private final List<Subscriber<E>> subscribers;
  
  public ArrayBroadcastChannel(int paramInt)
  {
    super(null);
    this.capacity = paramInt;
    int i = 1;
    if (paramInt < 1) {
      i = 0;
    }
    if (i != 0)
    {
      this.bufferLock = new ReentrantLock();
      this.buffer = new Object[paramInt];
      this._head = 0L;
      this._tail = 0L;
      this._size = 0;
      this.subscribers = ConcurrentKt.subscriberList();
      return;
    }
    throw new IllegalArgumentException(("ArrayBroadcastChannel capacity must be at least 1, but " + getCapacity() + " was specified").toString());
  }
  
  private final boolean cancelInternal(Throwable paramThrowable)
  {
    boolean bool = close(paramThrowable);
    Iterator localIterator = this.subscribers.iterator();
    while (localIterator.hasNext()) {
      ((Subscriber)localIterator.next()).cancelInternal$kotlinx_coroutines_core(paramThrowable);
    }
    return bool;
  }
  
  private final void checkSubOffers()
  {
    int j = 0;
    int i = 0;
    Iterator localIterator = this.subscribers.iterator();
    while (localIterator.hasNext())
    {
      Subscriber localSubscriber = (Subscriber)localIterator.next();
      int k = 1;
      i = k;
      if (localSubscriber.checkOffer())
      {
        j = 1;
        i = k;
      }
    }
    if ((j != 0) || (i == 0)) {
      updateHead$default(this, null, null, 3, null);
    }
  }
  
  private final long computeMinHead()
  {
    long l = Long.MAX_VALUE;
    Iterator localIterator = this.subscribers.iterator();
    while (localIterator.hasNext()) {
      l = RangesKt.coerceAtMost(l, ((Subscriber)localIterator.next()).getSubHead());
    }
    return l;
  }
  
  private final E elementAt(long paramLong)
  {
    return (E)this.buffer[((int)(paramLong % this.capacity))];
  }
  
  private final long getHead()
  {
    return this._head;
  }
  
  private final int getSize()
  {
    return this._size;
  }
  
  private final long getTail()
  {
    return this._tail;
  }
  
  private final void setHead(long paramLong)
  {
    this._head = paramLong;
  }
  
  private final void setSize(int paramInt)
  {
    this._size = paramInt;
  }
  
  private final void setTail(long paramLong)
  {
    this._tail = paramLong;
  }
  
  private final void updateHead(Subscriber<E> paramSubscriber1, Subscriber<E> paramSubscriber2)
  {
    Lock localLock;
    for (;;)
    {
      localLock = (Lock)this.bufferLock;
      localLock.lock();
      boolean bool;
      if (paramSubscriber1 != null) {
        try
        {
          paramSubscriber1.setSubHead(getTail());
          bool = this.subscribers.isEmpty();
          this.subscribers.add(paramSubscriber1);
          if (!bool)
          {
            localLock.unlock();
            return;
          }
        }
        finally
        {
          break;
        }
      }
      long l1;
      long l2;
      if (paramSubscriber2 != null)
      {
        this.subscribers.remove(paramSubscriber2);
        l1 = getHead();
        l2 = paramSubscriber2.getSubHead();
        if (l1 != l2)
        {
          localLock.unlock();
          return;
        }
      }
      try
      {
        l1 = computeMinHead();
        long l3 = getTail();
        l2 = getHead();
        long l4 = RangesKt.coerceAtMost(l1, l3);
        if (l4 <= l2)
        {
          localLock.unlock();
          return;
        }
        int i = getSize();
        for (;;)
        {
          label158:
          if (l2 >= l4) {
            break label386;
          }
          this.buffer[((int)(l2 % getCapacity()))] = null;
          int j;
          if (i >= getCapacity()) {
            j = 1;
          } else {
            j = 0;
          }
          l2 += 1L;
          setHead(l2);
          int k = i - 1;
          setSize(k);
          if (j != 0) {
            for (;;)
            {
              paramSubscriber1 = takeFirstSendOrPeekClosed();
              if (paramSubscriber1 == null)
              {
                i = k;
                break label158;
              }
              if ((paramSubscriber1 instanceof Closed))
              {
                i = k;
                break label158;
              }
              paramSubscriber2 = paramSubscriber1.tryResumeSend(null);
              if (paramSubscriber2 != null)
              {
                bool = DebugKt.getASSERTIONS_ENABLED();
                if (bool)
                {
                  if (paramSubscriber2 == CancellableContinuationImplKt.RESUME_TOKEN) {
                    i = 1;
                  } else {
                    i = 0;
                  }
                  if (i == 0)
                  {
                    paramSubscriber1 = new java/lang/AssertionError;
                    paramSubscriber1.<init>();
                    throw paramSubscriber1;
                  }
                }
                paramSubscriber2 = this.buffer;
                i = getCapacity();
                l1 = i;
                try
                {
                  paramSubscriber2[((int)(l3 % l1))] = paramSubscriber1.getPollResult();
                  setSize(k + 1);
                  setTail(l3 + 1L);
                  paramSubscriber2 = Unit.INSTANCE;
                  localLock.unlock();
                  paramSubscriber1.completeResumeSend();
                  checkSubOffers();
                  paramSubscriber1 = null;
                  paramSubscriber2 = null;
                }
                finally
                {
                  break label395;
                }
              }
            }
          }
          i = k;
        }
        label386:
        localLock.unlock();
        return;
      }
      finally {}
    }
    label395:
    localLock.unlock();
    throw paramSubscriber1;
  }
  
  public void cancel(CancellationException paramCancellationException)
  {
    cancelInternal((Throwable)paramCancellationException);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    if (!super.close(paramThrowable)) {
      return false;
    }
    checkSubOffers();
    return true;
  }
  
  protected String getBufferDebugString()
  {
    return "(buffer:capacity=" + this.buffer.length + ",size=" + getSize() + ')';
  }
  
  public final int getCapacity()
  {
    return this.capacity;
  }
  
  protected boolean isBufferAlwaysFull()
  {
    return false;
  }
  
  protected boolean isBufferFull()
  {
    boolean bool;
    if (getSize() >= this.capacity) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected Object offerInternal(E paramE)
  {
    Lock localLock = (Lock)this.bufferLock;
    localLock.lock();
    try
    {
      Closed localClosed = getClosedForSend();
      if (localClosed == null)
      {
        int i = getSize();
        if (i >= getCapacity())
        {
          paramE = AbstractChannelKt.OFFER_FAILED;
          return paramE;
        }
        long l = getTail();
        this.buffer[((int)(l % getCapacity()))] = paramE;
        setSize(i + 1);
        setTail(1L + l);
        paramE = Unit.INSTANCE;
        localLock.unlock();
        checkSubOffers();
        return AbstractChannelKt.OFFER_SUCCESS;
      }
      return localClosed;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    Lock localLock = (Lock)this.bufferLock;
    localLock.lock();
    try
    {
      Closed localClosed = getClosedForSend();
      if (localClosed == null)
      {
        int i = getSize();
        if (i >= getCapacity())
        {
          paramE = AbstractChannelKt.OFFER_FAILED;
          return paramE;
        }
        if (!paramSelectInstance.trySelect())
        {
          paramE = SelectKt.getALREADY_SELECTED();
          return paramE;
        }
        long l = getTail();
        this.buffer[((int)(l % getCapacity()))] = paramE;
        setSize(i + 1);
        setTail(1L + l);
        paramE = Unit.INSTANCE;
        localLock.unlock();
        checkSubOffers();
        return AbstractChannelKt.OFFER_SUCCESS;
      }
      return localClosed;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  public ReceiveChannel<E> openSubscription()
  {
    Subscriber localSubscriber = new Subscriber(this);
    updateHead$default(this, localSubscriber, null, 2, null);
    return (ReceiveChannel)localSubscriber;
  }
  
  @Metadata(d1={"\000J\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\003\n\002\b\004\n\002\020\000\n\002\b\003\n\002\030\002\n\002\b\b\n\002\020\t\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\b\002\030\000*\004\b\001\020\0012\b\022\004\022\0028\0010'2\b\022\004\022\0028\0010(B\025\022\f\020\003\032\b\022\004\022\0028\0010\002¢\006\004\b\004\020\005J\r\020\007\032\0020\006¢\006\004\b\007\020\bJ\031\020\013\032\0020\0062\b\020\n\032\004\030\0010\tH\026¢\006\004\b\013\020\fJ\017\020\r\032\0020\006H\002¢\006\004\b\r\020\bJ\021\020\017\032\004\030\0010\016H\002¢\006\004\b\017\020\020J\021\020\021\032\004\030\0010\016H\024¢\006\004\b\021\020\020J\035\020\024\032\004\030\0010\0162\n\020\023\032\006\022\002\b\0030\022H\024¢\006\004\b\024\020\025R\032\020\003\032\b\022\004\022\0028\0010\0028\002X\004¢\006\006\n\004\b\003\020\026R\024\020\027\032\0020\0068TX\004¢\006\006\032\004\b\027\020\bR\024\020\030\032\0020\0068TX\004¢\006\006\032\004\b\030\020\bR\024\020\031\032\0020\0068TX\004¢\006\006\032\004\b\031\020\bR\024\020\032\032\0020\0068TX\004¢\006\006\032\004\b\032\020\bR$\020!\032\0020\0332\006\020\034\032\0020\0338F@FX\016¢\006\f\032\004\b\035\020\036\"\004\b\037\020 R\030\020$\032\0060\"j\002`#8\002X\004¢\006\006\n\004\b$\020%¨\006&"}, d2={"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "broadcastChannel", "<init>", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "", "checkOffer", "()Z", "", "cause", "close", "(Ljava/lang/Throwable;)Z", "needsToCheckOfferWithoutLock", "", "peekUnderLock", "()Ljava/lang/Object;", "pollInternal", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "isBufferAlwaysEmpty", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "", "value", "getSubHead", "()J", "setSubHead", "(J)V", "subHead", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class Subscriber<E>
    extends AbstractChannel<E>
    implements ReceiveChannel<E>
  {
    private volatile long _subHead;
    private final ArrayBroadcastChannel<E> broadcastChannel;
    private final ReentrantLock subLock;
    
    public Subscriber(ArrayBroadcastChannel<E> paramArrayBroadcastChannel)
    {
      super();
      this.broadcastChannel = paramArrayBroadcastChannel;
      this.subLock = new ReentrantLock();
      this._subHead = 0L;
    }
    
    private final boolean needsToCheckOfferWithoutLock()
    {
      if (getClosedForReceive() != null) {
        return false;
      }
      return (!isBufferEmpty()) || (this.broadcastChannel.getClosedForReceive() != null);
    }
    
    private final Object peekUnderLock()
    {
      long l = getSubHead();
      Closed localClosed = this.broadcastChannel.getClosedForReceive();
      if (l >= ArrayBroadcastChannel.access$getTail(this.broadcastChannel))
      {
        if (localClosed == null) {
          localClosed = getClosedForReceive();
        }
        localObject = localClosed;
        if (localClosed == null) {
          localObject = AbstractChannelKt.POLL_FAILED;
        }
        return localObject;
      }
      Object localObject = ArrayBroadcastChannel.access$elementAt(this.broadcastChannel, l);
      localClosed = getClosedForReceive();
      if (localClosed != null) {
        return localClosed;
      }
      return localObject;
    }
    
    public final boolean checkOffer()
    {
      boolean bool = false;
      Object localObject3 = null;
      Object localObject4 = null;
      for (;;)
      {
        Object localObject1 = localObject3;
        if (needsToCheckOfferWithoutLock()) {
          if (!this.subLock.tryLock()) {
            localObject1 = localObject3;
          } else {
            try
            {
              localObject1 = peekUnderLock();
              Symbol localSymbol = AbstractChannelKt.POLL_FAILED;
              if (localObject1 == localSymbol) {}
              ReceiveOrClosed localReceiveOrClosed;
              do
              {
                this.subLock.unlock();
                break;
                if ((localObject1 instanceof Closed))
                {
                  localObject1 = (Closed)localObject1;
                  this.subLock.unlock();
                  break label207;
                }
                localReceiveOrClosed = takeFirstReceiveOrPeekClosed();
                if (localReceiveOrClosed == null) {}
                while ((localReceiveOrClosed instanceof Closed))
                {
                  localObject1 = localObject4;
                  break;
                }
                localSymbol = localReceiveOrClosed.tryResumeReceive(localObject1, null);
              } while (localSymbol == null);
              if (DebugKt.getASSERTIONS_ENABLED())
              {
                int i;
                if (localSymbol == CancellableContinuationImplKt.RESUME_TOKEN) {
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
              setSubHead(1L + getSubHead());
              bool = true;
              this.subLock.unlock();
              localReceiveOrClosed.completeResumeReceive(localObject1);
            }
            finally
            {
              this.subLock.unlock();
            }
          }
        }
      }
      label207:
      if (localObject2 != null) {
        close(((Closed)localObject2).closeCause);
      }
      return bool;
    }
    
    public boolean close(Throwable paramThrowable)
    {
      boolean bool = super.close(paramThrowable);
      if (bool)
      {
        ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, this, 1, null);
        paramThrowable = (Lock)this.subLock;
        paramThrowable.lock();
        try
        {
          setSubHead(ArrayBroadcastChannel.access$getTail(this.broadcastChannel));
          Unit localUnit = Unit.INSTANCE;
        }
        finally
        {
          paramThrowable.unlock();
        }
      }
      return bool;
    }
    
    public final long getSubHead()
    {
      return this._subHead;
    }
    
    protected boolean isBufferAlwaysEmpty()
    {
      return false;
    }
    
    protected boolean isBufferAlwaysFull()
    {
      throw new IllegalStateException("Should not be used".toString());
    }
    
    protected boolean isBufferEmpty()
    {
      boolean bool;
      if (getSubHead() >= ArrayBroadcastChannel.access$getTail(this.broadcastChannel)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    protected boolean isBufferFull()
    {
      throw new IllegalStateException("Should not be used".toString());
    }
    
    protected Object pollInternal()
    {
      int i = 0;
      Object localObject1 = (Lock)this.subLock;
      ((Lock)localObject1).lock();
      try
      {
        Object localObject2 = peekUnderLock();
        if ((!(localObject2 instanceof Closed)) && (localObject2 != AbstractChannelKt.POLL_FAILED))
        {
          setSubHead(1L + getSubHead());
          i = 1;
        }
        ((Lock)localObject1).unlock();
        if ((localObject2 instanceof Closed)) {
          localObject1 = (Closed)localObject2;
        } else {
          localObject1 = null;
        }
        if (localObject1 != null) {
          close(((Closed)localObject1).closeCause);
        }
        if (checkOffer()) {
          i = 1;
        }
        if (i != 0) {
          ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
        }
        return localObject2;
      }
      finally
      {
        ((Lock)localObject1).unlock();
      }
    }
    
    protected Object pollSelectInternal(SelectInstance<?> paramSelectInstance)
    {
      int i = 0;
      Lock localLock = (Lock)this.subLock;
      localLock.lock();
      try
      {
        Object localObject = peekUnderLock();
        if ((localObject instanceof Closed))
        {
          paramSelectInstance = (SelectInstance<?>)localObject;
        }
        else if (localObject == AbstractChannelKt.POLL_FAILED)
        {
          paramSelectInstance = (SelectInstance<?>)localObject;
        }
        else if (!paramSelectInstance.trySelect())
        {
          paramSelectInstance = SelectKt.getALREADY_SELECTED();
        }
        else
        {
          setSubHead(1L + getSubHead());
          i = 1;
          paramSelectInstance = (SelectInstance<?>)localObject;
        }
        localLock.unlock();
        if ((paramSelectInstance instanceof Closed)) {
          localObject = (Closed)paramSelectInstance;
        } else {
          localObject = null;
        }
        if (localObject != null) {
          close(((Closed)localObject).closeCause);
        }
        if (checkOffer()) {
          i = 1;
        }
        if (i != 0) {
          ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
        }
        return paramSelectInstance;
      }
      finally
      {
        localLock.unlock();
      }
    }
    
    public final void setSubHead(long paramLong)
    {
      this._subHead = paramLong;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ArrayBroadcastChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */