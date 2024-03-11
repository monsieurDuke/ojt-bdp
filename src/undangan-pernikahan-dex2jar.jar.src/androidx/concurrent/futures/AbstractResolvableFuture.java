package androidx.concurrent.futures;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class AbstractResolvableFuture<V>
  implements ListenableFuture<V>
{
  static final AtomicHelper ATOMIC_HELPER;
  static final boolean GENERATE_CANCELLATION_CAUSES;
  private static final Object NULL = new Object();
  private static final long SPIN_THRESHOLD_NANOS = 1000L;
  private static final Logger log;
  volatile Listener listeners;
  volatile Object value;
  volatile Waiter waiters;
  
  /* Error */
  static
  {
    // Byte code:
    //   0: ldc 56
    //   2: ldc 58
    //   4: invokestatic 64	java/lang/System:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   7: astore_0
    //   8: aload_0
    //   9: invokestatic 70	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   12: aload_0
    //   13: invokestatic 73	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   16: aload_0
    //   17: invokestatic 76	mt/Log229316:a	(Ljava/lang/Object;)V
    //   20: aload_0
    //   21: invokestatic 82	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   24: putstatic 84	androidx/concurrent/futures/AbstractResolvableFuture:GENERATE_CANCELLATION_CAUSES	Z
    //   27: ldc 2
    //   29: invokevirtual 90	java/lang/Class:getName	()Ljava/lang/String;
    //   32: invokestatic 96	java/util/logging/Logger:getLogger	(Ljava/lang/String;)Ljava/util/logging/Logger;
    //   35: putstatic 98	androidx/concurrent/futures/AbstractResolvableFuture:log	Ljava/util/logging/Logger;
    //   38: aconst_null
    //   39: astore_1
    //   40: new 25	androidx/concurrent/futures/AbstractResolvableFuture$SafeAtomicHelper
    //   43: astore_0
    //   44: aload_0
    //   45: ldc 34
    //   47: ldc 100
    //   49: ldc 102
    //   51: invokestatic 108	java/util/concurrent/atomic/AtomicReferenceFieldUpdater:newUpdater	(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;)Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
    //   54: ldc 34
    //   56: ldc 34
    //   58: ldc 110
    //   60: invokestatic 108	java/util/concurrent/atomic/AtomicReferenceFieldUpdater:newUpdater	(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;)Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
    //   63: ldc 2
    //   65: ldc 34
    //   67: ldc 111
    //   69: invokestatic 108	java/util/concurrent/atomic/AtomicReferenceFieldUpdater:newUpdater	(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;)Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
    //   72: ldc 2
    //   74: ldc 22
    //   76: ldc 112
    //   78: invokestatic 108	java/util/concurrent/atomic/AtomicReferenceFieldUpdater:newUpdater	(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;)Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
    //   81: ldc 2
    //   83: ldc 5
    //   85: ldc 113
    //   87: invokestatic 108	java/util/concurrent/atomic/AtomicReferenceFieldUpdater:newUpdater	(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;)Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
    //   90: invokespecial 117	androidx/concurrent/futures/AbstractResolvableFuture$SafeAtomicHelper:<init>	(Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;)V
    //   93: goto +12 -> 105
    //   96: astore_1
    //   97: new 31	androidx/concurrent/futures/AbstractResolvableFuture$SynchronizedHelper
    //   100: dup
    //   101: invokespecial 119	androidx/concurrent/futures/AbstractResolvableFuture$SynchronizedHelper:<init>	()V
    //   104: astore_0
    //   105: aload_0
    //   106: putstatic 121	androidx/concurrent/futures/AbstractResolvableFuture:ATOMIC_HELPER	Landroidx/concurrent/futures/AbstractResolvableFuture$AtomicHelper;
    //   109: aload_1
    //   110: ifnull +15 -> 125
    //   113: getstatic 98	androidx/concurrent/futures/AbstractResolvableFuture:log	Ljava/util/logging/Logger;
    //   116: getstatic 127	java/util/logging/Level:SEVERE	Ljava/util/logging/Level;
    //   119: ldc -127
    //   121: aload_1
    //   122: invokevirtual 132	java/util/logging/Logger:log	(Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   125: new 5	java/lang/Object
    //   128: dup
    //   129: invokespecial 133	java/lang/Object:<init>	()V
    //   132: putstatic 135	androidx/concurrent/futures/AbstractResolvableFuture:NULL	Ljava/lang/Object;
    //   135: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   7	99	0	localObject1	Object
    //   39	1	1	localObject2	Object
    //   96	26	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   40	93	96	finally
  }
  
  private void addDoneString(StringBuilder paramStringBuilder)
  {
    try
    {
      Object localObject = getUninterruptibly(this);
      paramStringBuilder.append("SUCCESS, result=[").append(userObjectToString(localObject)).append("]");
    }
    catch (RuntimeException localRuntimeException)
    {
      paramStringBuilder.append("UNKNOWN, cause=[").append(localRuntimeException.getClass()).append(" thrown from get()]");
    }
    catch (CancellationException localCancellationException)
    {
      paramStringBuilder.append("CANCELLED");
    }
    catch (ExecutionException localExecutionException)
    {
      paramStringBuilder.append("FAILURE, cause=[").append(localExecutionException.getCause()).append("]");
    }
  }
  
  private static CancellationException cancellationExceptionWithCause(String paramString, Throwable paramThrowable)
  {
    paramString = new CancellationException(paramString);
    paramString.initCause(paramThrowable);
    return paramString;
  }
  
  static <T> T checkNotNull(T paramT)
  {
    if (paramT != null) {
      return paramT;
    }
    throw new NullPointerException();
  }
  
  private Listener clearListeners(Listener paramListener)
  {
    for (;;)
    {
      Listener localListener2 = this.listeners;
      if (ATOMIC_HELPER.casListeners(this, localListener2, Listener.TOMBSTONE))
      {
        Listener localListener1 = paramListener;
        for (paramListener = localListener2; paramListener != null; paramListener = localListener2)
        {
          localListener2 = paramListener.next;
          paramListener.next = localListener1;
          localListener1 = paramListener;
        }
        return localListener1;
      }
    }
  }
  
  static void complete(AbstractResolvableFuture<?> paramAbstractResolvableFuture)
  {
    Object localObject2 = null;
    Object localObject1 = paramAbstractResolvableFuture;
    paramAbstractResolvableFuture = (AbstractResolvableFuture<?>)localObject2;
    ((AbstractResolvableFuture)localObject1).releaseWaiters();
    ((AbstractResolvableFuture)localObject1).afterDone();
    paramAbstractResolvableFuture = ((AbstractResolvableFuture)localObject1).clearListeners(paramAbstractResolvableFuture);
    label95:
    for (;;)
    {
      localObject1 = paramAbstractResolvableFuture;
      if (localObject1 == null) {
        return;
      }
      paramAbstractResolvableFuture = ((Listener)localObject1).next;
      localObject2 = ((Listener)localObject1).task;
      if ((localObject2 instanceof SetFuture))
      {
        SetFuture localSetFuture = (SetFuture)localObject2;
        localObject1 = localSetFuture.owner;
        if (((AbstractResolvableFuture)localObject1).value == localSetFuture)
        {
          localObject2 = getFutureValue(localSetFuture.future);
          if (ATOMIC_HELPER.casValue((AbstractResolvableFuture)localObject1, localSetFuture, localObject2)) {
            break;
          }
        }
        break label95;
      }
      executeListener((Runnable)localObject2, ((Listener)localObject1).executor);
    }
  }
  
  private static void executeListener(Runnable paramRunnable, Executor paramExecutor)
  {
    try
    {
      paramExecutor.execute(paramRunnable);
    }
    catch (RuntimeException localRuntimeException)
    {
      log.log(Level.SEVERE, "RuntimeException while executing runnable " + paramRunnable + " with executor " + paramExecutor, localRuntimeException);
    }
  }
  
  private V getDoneValue(Object paramObject)
    throws ExecutionException
  {
    if (!(paramObject instanceof Cancellation))
    {
      if (!(paramObject instanceof Failure))
      {
        if (paramObject == NULL) {
          return null;
        }
        return (V)paramObject;
      }
      throw new ExecutionException(((Failure)paramObject).exception);
    }
    throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation)paramObject).cause);
  }
  
  /* Error */
  static Object getFutureValue(ListenableFuture<?> paramListenableFuture)
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 2
    //   4: ifeq +63 -> 67
    //   7: aload_0
    //   8: checkcast 2	androidx/concurrent/futures/AbstractResolvableFuture
    //   11: getfield 231	androidx/concurrent/futures/AbstractResolvableFuture:value	Ljava/lang/Object;
    //   14: astore_2
    //   15: aload_2
    //   16: astore_0
    //   17: aload_2
    //   18: instanceof 14
    //   21: ifeq +44 -> 65
    //   24: aload_2
    //   25: checkcast 14	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation
    //   28: astore_3
    //   29: aload_2
    //   30: astore_0
    //   31: aload_3
    //   32: getfield 286	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:wasInterrupted	Z
    //   35: ifeq +30 -> 65
    //   38: aload_3
    //   39: getfield 279	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:cause	Ljava/lang/Throwable;
    //   42: ifnull +19 -> 61
    //   45: new 14	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation
    //   48: dup
    //   49: iconst_0
    //   50: aload_3
    //   51: getfield 279	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:cause	Ljava/lang/Throwable;
    //   54: invokespecial 289	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:<init>	(ZLjava/lang/Throwable;)V
    //   57: astore_0
    //   58: goto +7 -> 65
    //   61: getstatic 293	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:CAUSELESS_CANCELLED	Landroidx/concurrent/futures/AbstractResolvableFuture$Cancellation;
    //   64: astore_0
    //   65: aload_0
    //   66: areturn
    //   67: aload_0
    //   68: invokeinterface 297 1 0
    //   73: istore_1
    //   74: getstatic 84	androidx/concurrent/futures/AbstractResolvableFuture:GENERATE_CANCELLATION_CAUSES	Z
    //   77: iconst_1
    //   78: ixor
    //   79: iload_1
    //   80: iand
    //   81: ifeq +7 -> 88
    //   84: getstatic 293	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:CAUSELESS_CANCELLED	Landroidx/concurrent/futures/AbstractResolvableFuture$Cancellation;
    //   87: areturn
    //   88: aload_0
    //   89: invokestatic 148	androidx/concurrent/futures/AbstractResolvableFuture:getUninterruptibly	(Ljava/util/concurrent/Future;)Ljava/lang/Object;
    //   92: astore_2
    //   93: aload_2
    //   94: ifnonnull +12 -> 106
    //   97: getstatic 135	androidx/concurrent/futures/AbstractResolvableFuture:NULL	Ljava/lang/Object;
    //   100: astore_2
    //   101: aload_2
    //   102: astore_0
    //   103: goto +5 -> 108
    //   106: aload_2
    //   107: astore_0
    //   108: aload_0
    //   109: areturn
    //   110: astore_0
    //   111: new 17	androidx/concurrent/futures/AbstractResolvableFuture$Failure
    //   114: dup
    //   115: aload_0
    //   116: invokespecial 298	androidx/concurrent/futures/AbstractResolvableFuture$Failure:<init>	(Ljava/lang/Throwable;)V
    //   119: areturn
    //   120: astore_2
    //   121: iload_1
    //   122: ifne +39 -> 161
    //   125: new 17	androidx/concurrent/futures/AbstractResolvableFuture$Failure
    //   128: dup
    //   129: new 300	java/lang/IllegalArgumentException
    //   132: dup
    //   133: new 152	java/lang/StringBuilder
    //   136: dup
    //   137: invokespecial 258	java/lang/StringBuilder:<init>	()V
    //   140: ldc_w 302
    //   143: invokevirtual 156	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: aload_0
    //   147: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   150: invokevirtual 265	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: aload_2
    //   154: invokespecial 305	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   157: invokespecial 298	androidx/concurrent/futures/AbstractResolvableFuture$Failure:<init>	(Ljava/lang/Throwable;)V
    //   160: areturn
    //   161: new 14	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation
    //   164: dup
    //   165: iconst_0
    //   166: aload_2
    //   167: invokespecial 289	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:<init>	(ZLjava/lang/Throwable;)V
    //   170: areturn
    //   171: astore_0
    //   172: new 17	androidx/concurrent/futures/AbstractResolvableFuture$Failure
    //   175: dup
    //   176: aload_0
    //   177: invokevirtual 181	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   180: invokespecial 298	androidx/concurrent/futures/AbstractResolvableFuture$Failure:<init>	(Ljava/lang/Throwable;)V
    //   183: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	paramListenableFuture	ListenableFuture<?>
    //   73	49	1	bool	boolean
    //   14	93	2	localObject	Object
    //   120	47	2	localCancellationException	CancellationException
    //   28	23	3	localCancellation	Cancellation
    // Exception table:
    //   from	to	target	type
    //   88	93	110	finally
    //   97	101	110	finally
    //   88	93	120	java/util/concurrent/CancellationException
    //   97	101	120	java/util/concurrent/CancellationException
    //   88	93	171	java/util/concurrent/ExecutionException
    //   97	101	171	java/util/concurrent/ExecutionException
  }
  
  /* Error */
  private static <V> V getUninterruptibly(Future<V> paramFuture)
    throws ExecutionException
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: invokeinterface 314 1 0
    //   8: astore_2
    //   9: iload_1
    //   10: ifeq +9 -> 19
    //   13: invokestatic 318	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   16: invokevirtual 321	java/lang/Thread:interrupt	()V
    //   19: aload_2
    //   20: areturn
    //   21: astore_0
    //   22: iload_1
    //   23: ifeq +9 -> 32
    //   26: invokestatic 318	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   29: invokevirtual 321	java/lang/Thread:interrupt	()V
    //   32: aload_0
    //   33: athrow
    //   34: astore_2
    //   35: iconst_1
    //   36: istore_1
    //   37: goto -35 -> 2
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	paramFuture	Future<V>
    //   1	36	1	i	int
    //   8	12	2	localObject	Object
    //   34	1	2	localInterruptedException	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   2	9	21	finally
    //   2	9	34	java/lang/InterruptedException
  }
  
  private void releaseWaiters()
  {
    for (;;)
    {
      Waiter localWaiter = this.waiters;
      if (ATOMIC_HELPER.casWaiters(this, localWaiter, Waiter.TOMBSTONE))
      {
        while (localWaiter != null)
        {
          localWaiter.unpark();
          localWaiter = localWaiter.next;
        }
        return;
      }
    }
  }
  
  private void removeWaiter(Waiter paramWaiter)
  {
    paramWaiter.thread = null;
    Object localObject1 = null;
    Object localObject2 = this.waiters;
    paramWaiter = (Waiter)localObject2;
    if (localObject2 == Waiter.TOMBSTONE) {
      return;
    }
    for (;;)
    {
      if (paramWaiter == null) {
        return;
      }
      Waiter localWaiter = paramWaiter.next;
      if (paramWaiter.thread != null)
      {
        localObject2 = paramWaiter;
      }
      else
      {
        if (localObject1 != null)
        {
          ((Waiter)localObject1).next = localWaiter;
          localObject2 = localObject1;
          if (((Waiter)localObject1).thread != null) {
            break label84;
          }
          break;
        }
        localObject2 = localObject1;
        if (!ATOMIC_HELPER.casWaiters(this, paramWaiter, localWaiter)) {
          break;
        }
      }
      label84:
      paramWaiter = localWaiter;
      localObject1 = localObject2;
    }
  }
  
  private String userObjectToString(Object paramObject)
  {
    if (paramObject == this) {
      return "this future";
    }
    paramObject = String.valueOf(paramObject);
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    return (String)paramObject;
  }
  
  public final void addListener(Runnable paramRunnable, Executor paramExecutor)
  {
    checkNotNull(paramRunnable);
    checkNotNull(paramExecutor);
    Object localObject = this.listeners;
    if (localObject != Listener.TOMBSTONE)
    {
      Listener localListener2 = new Listener(paramRunnable, paramExecutor);
      Listener localListener1;
      do
      {
        localListener2.next = ((Listener)localObject);
        if (ATOMIC_HELPER.casListeners(this, (Listener)localObject, localListener2)) {
          return;
        }
        localListener1 = this.listeners;
        localObject = localListener1;
      } while (localListener1 != Listener.TOMBSTONE);
    }
    executeListener(paramRunnable, paramExecutor);
  }
  
  protected void afterDone() {}
  
  public final boolean cancel(boolean paramBoolean)
  {
    Object localObject1 = this.value;
    boolean bool1 = false;
    boolean bool2 = false;
    int i;
    if (localObject1 == null) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i | localObject1 instanceof SetFuture) != 0)
    {
      Cancellation localCancellation;
      if (GENERATE_CANCELLATION_CAUSES) {
        localCancellation = new Cancellation(paramBoolean, new CancellationException("Future.cancel() was called."));
      } else if (paramBoolean) {
        localCancellation = Cancellation.CAUSELESS_INTERRUPTED;
      } else {
        localCancellation = Cancellation.CAUSELESS_CANCELLED;
      }
      AbstractResolvableFuture localAbstractResolvableFuture = this;
      bool1 = bool2;
      Object localObject2;
      do
      {
        while (ATOMIC_HELPER.casValue(localAbstractResolvableFuture, localObject1, localCancellation))
        {
          boolean bool3 = true;
          bool2 = true;
          if (paramBoolean) {
            localAbstractResolvableFuture.interruptTask();
          }
          complete(localAbstractResolvableFuture);
          bool1 = bool3;
          if (!(localObject1 instanceof SetFuture)) {
            return bool1;
          }
          localObject1 = ((SetFuture)localObject1).future;
          if ((localObject1 instanceof AbstractResolvableFuture))
          {
            localAbstractResolvableFuture = (AbstractResolvableFuture)localObject1;
            localObject1 = localAbstractResolvableFuture.value;
            if (localObject1 == null) {
              i = 1;
            } else {
              i = 0;
            }
            if ((i | localObject1 instanceof SetFuture) != 0)
            {
              bool1 = bool2;
              continue;
            }
          }
          else
          {
            ((ListenableFuture)localObject1).cancel(paramBoolean);
          }
          return bool3;
        }
        localObject2 = localAbstractResolvableFuture.value;
        localObject1 = localObject2;
      } while ((localObject2 instanceof SetFuture));
    }
    return bool1;
  }
  
  public final V get()
    throws InterruptedException, ExecutionException
  {
    if (!Thread.interrupted())
    {
      Object localObject = this.value;
      int i;
      if (localObject != null) {
        i = 1;
      } else {
        i = 0;
      }
      if ((i & (localObject instanceof SetFuture ^ true)) != 0) {
        return (V)getDoneValue(localObject);
      }
      localObject = this.waiters;
      if (localObject != Waiter.TOMBSTONE)
      {
        Waiter localWaiter2 = new Waiter();
        Waiter localWaiter1;
        do
        {
          localWaiter2.setNext((Waiter)localObject);
          if (ATOMIC_HELPER.casWaiters(this, (Waiter)localObject, localWaiter2))
          {
            do
            {
              LockSupport.park(this);
              if (Thread.interrupted()) {
                break;
              }
              localObject = this.value;
              if (localObject != null) {
                i = 1;
              } else {
                i = 0;
              }
            } while ((i & (localObject instanceof SetFuture ^ true)) == 0);
            return (V)getDoneValue(localObject);
            removeWaiter(localWaiter2);
            throw new InterruptedException();
          }
          localWaiter1 = this.waiters;
          localObject = localWaiter1;
        } while (localWaiter1 != Waiter.TOMBSTONE);
      }
      return (V)getDoneValue(this.value);
    }
    throw new InterruptedException();
  }
  
  public final V get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, TimeoutException, ExecutionException
  {
    long l3 = paramTimeUnit.toNanos(paramLong);
    if (!Thread.interrupted())
    {
      Object localObject1 = this.value;
      int i;
      if (localObject1 != null) {
        i = 1;
      } else {
        i = 0;
      }
      if ((i & (localObject1 instanceof SetFuture ^ true)) != 0) {
        return (V)getDoneValue(localObject1);
      }
      long l2;
      if (l3 > 0L) {
        l2 = System.nanoTime() + l3;
      } else {
        l2 = 0L;
      }
      long l1 = l3;
      if (l3 >= 1000L)
      {
        localObject1 = this.waiters;
        if (localObject1 != Waiter.TOMBSTONE)
        {
          localObject2 = new Waiter();
          for (;;)
          {
            ((Waiter)localObject2).setNext((Waiter)localObject1);
            if (ATOMIC_HELPER.casWaiters(this, (Waiter)localObject1, (Waiter)localObject2))
            {
              for (;;)
              {
                LockSupport.parkNanos(this, l3);
                if (Thread.interrupted()) {
                  break;
                }
                localObject1 = this.value;
                if (localObject1 != null) {
                  i = 1;
                } else {
                  i = 0;
                }
                if ((i & (localObject1 instanceof SetFuture ^ true)) != 0) {
                  return (V)getDoneValue(localObject1);
                }
                l1 = l2 - System.nanoTime();
                if (l1 < 1000L)
                {
                  removeWaiter((Waiter)localObject2);
                  break label259;
                }
                l3 = l1;
              }
              removeWaiter((Waiter)localObject2);
              throw new InterruptedException();
            }
            localObject1 = this.waiters;
            if (localObject1 == Waiter.TOMBSTONE) {
              break;
            }
          }
        }
        return (V)getDoneValue(this.value);
      }
      label259:
      while (l1 > 0L)
      {
        localObject1 = this.value;
        if (localObject1 != null) {
          i = 1;
        } else {
          i = 0;
        }
        if ((i & (localObject1 instanceof SetFuture ^ true)) != 0) {
          return (V)getDoneValue(localObject1);
        }
        if (!Thread.interrupted()) {
          l1 = l2 - System.nanoTime();
        } else {
          throw new InterruptedException();
        }
      }
      Object localObject2 = toString();
      String str = paramTimeUnit.toString().toLowerCase(Locale.ROOT);
      localObject1 = "Waited " + paramLong + " " + paramTimeUnit.toString().toLowerCase(Locale.ROOT);
      if (l1 + 1000L < 0L)
      {
        localObject1 = (String)localObject1 + " (plus ";
        l1 = -l1;
        paramLong = paramTimeUnit.convert(l1, TimeUnit.NANOSECONDS);
        l1 -= paramTimeUnit.toNanos(paramLong);
        if ((paramLong != 0L) && (l1 <= 1000L)) {
          i = 0;
        } else {
          i = 1;
        }
        paramTimeUnit = (TimeUnit)localObject1;
        if (paramLong > 0L)
        {
          paramTimeUnit = (String)localObject1 + paramLong + " " + str;
          if (i != 0) {
            paramTimeUnit = paramTimeUnit + ",";
          }
          paramTimeUnit = paramTimeUnit + " ";
        }
        localObject1 = paramTimeUnit;
        if (i != 0) {
          localObject1 = paramTimeUnit + l1 + " nanoseconds ";
        }
        paramTimeUnit = (String)localObject1 + "delay)";
      }
      else
      {
        paramTimeUnit = (TimeUnit)localObject1;
      }
      if (isDone()) {
        throw new TimeoutException(paramTimeUnit + " but future completed as timeout expired");
      }
      throw new TimeoutException(paramTimeUnit + " for " + (String)localObject2);
    }
    throw new InterruptedException();
  }
  
  protected void interruptTask() {}
  
  public final boolean isCancelled()
  {
    return this.value instanceof Cancellation;
  }
  
  public final boolean isDone()
  {
    Object localObject = this.value;
    int i;
    if (localObject != null) {
      i = 1;
    } else {
      i = 0;
    }
    return (true ^ localObject instanceof SetFuture) & i;
  }
  
  final void maybePropagateCancellationTo(Future<?> paramFuture)
  {
    int i;
    if (paramFuture != null) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i & isCancelled()) != 0) {
      paramFuture.cancel(wasInterrupted());
    }
  }
  
  protected String pendingToString()
  {
    Object localObject = this.value;
    if ((localObject instanceof SetFuture)) {
      return "setFuture=[" + userObjectToString(((SetFuture)localObject).future) + "]";
    }
    if ((this instanceof ScheduledFuture)) {
      return "remaining delay=[" + ((ScheduledFuture)this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }
    return null;
  }
  
  protected boolean set(V paramV)
  {
    if (paramV == null) {
      paramV = NULL;
    }
    if (ATOMIC_HELPER.casValue(this, null, paramV))
    {
      complete(this);
      return true;
    }
    return false;
  }
  
  protected boolean setException(Throwable paramThrowable)
  {
    paramThrowable = new Failure((Throwable)checkNotNull(paramThrowable));
    if (ATOMIC_HELPER.casValue(this, null, paramThrowable))
    {
      complete(this);
      return true;
    }
    return false;
  }
  
  /* Error */
  protected boolean setFuture(ListenableFuture<? extends V> paramListenableFuture)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 349	androidx/concurrent/futures/AbstractResolvableFuture:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   4: pop
    //   5: aload_0
    //   6: getfield 231	androidx/concurrent/futures/AbstractResolvableFuture:value	Ljava/lang/Object;
    //   9: astore_3
    //   10: aload_3
    //   11: astore_2
    //   12: aload_3
    //   13: ifnonnull +107 -> 120
    //   16: aload_1
    //   17: invokeinterface 478 1 0
    //   22: ifeq +28 -> 50
    //   25: aload_1
    //   26: invokestatic 239	androidx/concurrent/futures/AbstractResolvableFuture:getFutureValue	(Lcom/google/common/util/concurrent/ListenableFuture;)Ljava/lang/Object;
    //   29: astore_1
    //   30: getstatic 121	androidx/concurrent/futures/AbstractResolvableFuture:ATOMIC_HELPER	Landroidx/concurrent/futures/AbstractResolvableFuture$AtomicHelper;
    //   33: aload_0
    //   34: aconst_null
    //   35: aload_1
    //   36: invokevirtual 243	androidx/concurrent/futures/AbstractResolvableFuture$AtomicHelper:casValue	(Landroidx/concurrent/futures/AbstractResolvableFuture;Ljava/lang/Object;Ljava/lang/Object;)Z
    //   39: ifeq +9 -> 48
    //   42: aload_0
    //   43: invokestatic 363	androidx/concurrent/futures/AbstractResolvableFuture:complete	(Landroidx/concurrent/futures/AbstractResolvableFuture;)V
    //   46: iconst_1
    //   47: ireturn
    //   48: iconst_0
    //   49: ireturn
    //   50: new 28	androidx/concurrent/futures/AbstractResolvableFuture$SetFuture
    //   53: dup
    //   54: aload_0
    //   55: aload_1
    //   56: invokespecial 481	androidx/concurrent/futures/AbstractResolvableFuture$SetFuture:<init>	(Landroidx/concurrent/futures/AbstractResolvableFuture;Lcom/google/common/util/concurrent/ListenableFuture;)V
    //   59: astore_2
    //   60: getstatic 121	androidx/concurrent/futures/AbstractResolvableFuture:ATOMIC_HELPER	Landroidx/concurrent/futures/AbstractResolvableFuture$AtomicHelper;
    //   63: aload_0
    //   64: aconst_null
    //   65: aload_2
    //   66: invokevirtual 243	androidx/concurrent/futures/AbstractResolvableFuture$AtomicHelper:casValue	(Landroidx/concurrent/futures/AbstractResolvableFuture;Ljava/lang/Object;Ljava/lang/Object;)Z
    //   69: ifeq +46 -> 115
    //   72: aload_1
    //   73: aload_2
    //   74: getstatic 487	androidx/concurrent/futures/DirectExecutor:INSTANCE	Landroidx/concurrent/futures/DirectExecutor;
    //   77: invokeinterface 489 3 0
    //   82: goto +31 -> 113
    //   85: astore_3
    //   86: new 17	androidx/concurrent/futures/AbstractResolvableFuture$Failure
    //   89: astore_1
    //   90: aload_1
    //   91: aload_3
    //   92: invokespecial 298	androidx/concurrent/futures/AbstractResolvableFuture$Failure:<init>	(Ljava/lang/Throwable;)V
    //   95: goto +8 -> 103
    //   98: astore_1
    //   99: getstatic 493	androidx/concurrent/futures/AbstractResolvableFuture$Failure:FALLBACK_INSTANCE	Landroidx/concurrent/futures/AbstractResolvableFuture$Failure;
    //   102: astore_1
    //   103: getstatic 121	androidx/concurrent/futures/AbstractResolvableFuture:ATOMIC_HELPER	Landroidx/concurrent/futures/AbstractResolvableFuture$AtomicHelper;
    //   106: aload_0
    //   107: aload_2
    //   108: aload_1
    //   109: invokevirtual 243	androidx/concurrent/futures/AbstractResolvableFuture$AtomicHelper:casValue	(Landroidx/concurrent/futures/AbstractResolvableFuture;Ljava/lang/Object;Ljava/lang/Object;)Z
    //   112: pop
    //   113: iconst_1
    //   114: ireturn
    //   115: aload_0
    //   116: getfield 231	androidx/concurrent/futures/AbstractResolvableFuture:value	Ljava/lang/Object;
    //   119: astore_2
    //   120: aload_2
    //   121: instanceof 14
    //   124: ifeq +17 -> 141
    //   127: aload_1
    //   128: aload_2
    //   129: checkcast 14	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation
    //   132: getfield 286	androidx/concurrent/futures/AbstractResolvableFuture$Cancellation:wasInterrupted	Z
    //   135: invokeinterface 365 2 0
    //   140: pop
    //   141: iconst_0
    //   142: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	143	0	this	AbstractResolvableFuture
    //   0	143	1	paramListenableFuture	ListenableFuture<? extends V>
    //   11	118	2	localObject1	Object
    //   9	4	3	localObject2	Object
    //   85	7	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   72	82	85	finally
    //   86	95	98	finally
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(super.toString()).append("[status=");
    if (isCancelled())
    {
      localStringBuilder.append("CANCELLED");
    }
    else if (isDone())
    {
      addDoneString(localStringBuilder);
    }
    else
    {
      String str2;
      try
      {
        String str1 = pendingToString();
      }
      catch (RuntimeException localRuntimeException)
      {
        str2 = "Exception thrown from implementation: " + localRuntimeException.getClass();
      }
      if ((str2 != null) && (!str2.isEmpty())) {
        localStringBuilder.append("PENDING, info=[").append(str2).append("]");
      } else if (isDone()) {
        addDoneString(localStringBuilder);
      } else {
        localStringBuilder.append("PENDING");
      }
    }
    return "]";
  }
  
  protected final boolean wasInterrupted()
  {
    Object localObject = this.value;
    boolean bool;
    if (((localObject instanceof Cancellation)) && (((Cancellation)localObject).wasInterrupted)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static abstract class AtomicHelper
  {
    abstract boolean casListeners(AbstractResolvableFuture<?> paramAbstractResolvableFuture, AbstractResolvableFuture.Listener paramListener1, AbstractResolvableFuture.Listener paramListener2);
    
    abstract boolean casValue(AbstractResolvableFuture<?> paramAbstractResolvableFuture, Object paramObject1, Object paramObject2);
    
    abstract boolean casWaiters(AbstractResolvableFuture<?> paramAbstractResolvableFuture, AbstractResolvableFuture.Waiter paramWaiter1, AbstractResolvableFuture.Waiter paramWaiter2);
    
    abstract void putNext(AbstractResolvableFuture.Waiter paramWaiter1, AbstractResolvableFuture.Waiter paramWaiter2);
    
    abstract void putThread(AbstractResolvableFuture.Waiter paramWaiter, Thread paramThread);
  }
  
  private static final class Cancellation
  {
    static final Cancellation CAUSELESS_CANCELLED;
    static final Cancellation CAUSELESS_INTERRUPTED;
    final Throwable cause;
    final boolean wasInterrupted;
    
    static
    {
      if (AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES)
      {
        CAUSELESS_CANCELLED = null;
        CAUSELESS_INTERRUPTED = null;
      }
      else
      {
        CAUSELESS_CANCELLED = new Cancellation(false, null);
        CAUSELESS_INTERRUPTED = new Cancellation(true, null);
      }
    }
    
    Cancellation(boolean paramBoolean, Throwable paramThrowable)
    {
      this.wasInterrupted = paramBoolean;
      this.cause = paramThrowable;
    }
  }
  
  private static final class Failure
  {
    static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.")
    {
      public Throwable fillInStackTrace()
      {
        return this;
      }
    });
    final Throwable exception;
    
    Failure(Throwable paramThrowable)
    {
      this.exception = ((Throwable)AbstractResolvableFuture.checkNotNull(paramThrowable));
    }
  }
  
  private static final class Listener
  {
    static final Listener TOMBSTONE = new Listener(null, null);
    final Executor executor;
    Listener next;
    final Runnable task;
    
    Listener(Runnable paramRunnable, Executor paramExecutor)
    {
      this.task = paramRunnable;
      this.executor = paramExecutor;
    }
  }
  
  private static final class SafeAtomicHelper
    extends AbstractResolvableFuture.AtomicHelper
  {
    final AtomicReferenceFieldUpdater<AbstractResolvableFuture, AbstractResolvableFuture.Listener> listenersUpdater;
    final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater;
    final AtomicReferenceFieldUpdater<AbstractResolvableFuture.Waiter, AbstractResolvableFuture.Waiter> waiterNextUpdater;
    final AtomicReferenceFieldUpdater<AbstractResolvableFuture.Waiter, Thread> waiterThreadUpdater;
    final AtomicReferenceFieldUpdater<AbstractResolvableFuture, AbstractResolvableFuture.Waiter> waitersUpdater;
    
    SafeAtomicHelper(AtomicReferenceFieldUpdater<AbstractResolvableFuture.Waiter, Thread> paramAtomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<AbstractResolvableFuture.Waiter, AbstractResolvableFuture.Waiter> paramAtomicReferenceFieldUpdater1, AtomicReferenceFieldUpdater<AbstractResolvableFuture, AbstractResolvableFuture.Waiter> paramAtomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, AbstractResolvableFuture.Listener> paramAtomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> paramAtomicReferenceFieldUpdater4)
    {
      super();
      this.waiterThreadUpdater = paramAtomicReferenceFieldUpdater;
      this.waiterNextUpdater = paramAtomicReferenceFieldUpdater1;
      this.waitersUpdater = paramAtomicReferenceFieldUpdater2;
      this.listenersUpdater = paramAtomicReferenceFieldUpdater3;
      this.valueUpdater = paramAtomicReferenceFieldUpdater4;
    }
    
    boolean casListeners(AbstractResolvableFuture<?> paramAbstractResolvableFuture, AbstractResolvableFuture.Listener paramListener1, AbstractResolvableFuture.Listener paramListener2)
    {
      return AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(this.listenersUpdater, paramAbstractResolvableFuture, paramListener1, paramListener2);
    }
    
    boolean casValue(AbstractResolvableFuture<?> paramAbstractResolvableFuture, Object paramObject1, Object paramObject2)
    {
      return AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(this.valueUpdater, paramAbstractResolvableFuture, paramObject1, paramObject2);
    }
    
    boolean casWaiters(AbstractResolvableFuture<?> paramAbstractResolvableFuture, AbstractResolvableFuture.Waiter paramWaiter1, AbstractResolvableFuture.Waiter paramWaiter2)
    {
      return AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(this.waitersUpdater, paramAbstractResolvableFuture, paramWaiter1, paramWaiter2);
    }
    
    void putNext(AbstractResolvableFuture.Waiter paramWaiter1, AbstractResolvableFuture.Waiter paramWaiter2)
    {
      this.waiterNextUpdater.lazySet(paramWaiter1, paramWaiter2);
    }
    
    void putThread(AbstractResolvableFuture.Waiter paramWaiter, Thread paramThread)
    {
      this.waiterThreadUpdater.lazySet(paramWaiter, paramThread);
    }
  }
  
  private static final class SetFuture<V>
    implements Runnable
  {
    final ListenableFuture<? extends V> future;
    final AbstractResolvableFuture<V> owner;
    
    SetFuture(AbstractResolvableFuture<V> paramAbstractResolvableFuture, ListenableFuture<? extends V> paramListenableFuture)
    {
      this.owner = paramAbstractResolvableFuture;
      this.future = paramListenableFuture;
    }
    
    public void run()
    {
      if (this.owner.value != this) {
        return;
      }
      Object localObject = AbstractResolvableFuture.getFutureValue(this.future);
      if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(this.owner, this, localObject)) {
        AbstractResolvableFuture.complete(this.owner);
      }
    }
  }
  
  private static final class SynchronizedHelper
    extends AbstractResolvableFuture.AtomicHelper
  {
    SynchronizedHelper()
    {
      super();
    }
    
    boolean casListeners(AbstractResolvableFuture<?> paramAbstractResolvableFuture, AbstractResolvableFuture.Listener paramListener1, AbstractResolvableFuture.Listener paramListener2)
    {
      try
      {
        if (paramAbstractResolvableFuture.listeners == paramListener1)
        {
          paramAbstractResolvableFuture.listeners = paramListener2;
          return true;
        }
        return false;
      }
      finally {}
    }
    
    boolean casValue(AbstractResolvableFuture<?> paramAbstractResolvableFuture, Object paramObject1, Object paramObject2)
    {
      try
      {
        if (paramAbstractResolvableFuture.value == paramObject1)
        {
          paramAbstractResolvableFuture.value = paramObject2;
          return true;
        }
        return false;
      }
      finally {}
    }
    
    boolean casWaiters(AbstractResolvableFuture<?> paramAbstractResolvableFuture, AbstractResolvableFuture.Waiter paramWaiter1, AbstractResolvableFuture.Waiter paramWaiter2)
    {
      try
      {
        if (paramAbstractResolvableFuture.waiters == paramWaiter1)
        {
          paramAbstractResolvableFuture.waiters = paramWaiter2;
          return true;
        }
        return false;
      }
      finally {}
    }
    
    void putNext(AbstractResolvableFuture.Waiter paramWaiter1, AbstractResolvableFuture.Waiter paramWaiter2)
    {
      paramWaiter1.next = paramWaiter2;
    }
    
    void putThread(AbstractResolvableFuture.Waiter paramWaiter, Thread paramThread)
    {
      paramWaiter.thread = paramThread;
    }
  }
  
  private static final class Waiter
  {
    static final Waiter TOMBSTONE = new Waiter(false);
    volatile Waiter next;
    volatile Thread thread;
    
    Waiter()
    {
      AbstractResolvableFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
    }
    
    Waiter(boolean paramBoolean) {}
    
    void setNext(Waiter paramWaiter)
    {
      AbstractResolvableFuture.ATOMIC_HELPER.putNext(this, paramWaiter);
    }
    
    void unpark()
    {
      Thread localThread = this.thread;
      if (localThread != null)
      {
        this.thread = null;
        LockSupport.unpark(localThread);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/concurrent/futures/AbstractResolvableFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */