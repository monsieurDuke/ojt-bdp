package kotlinx.coroutines;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;

@Metadata(d1={"\000X\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\t\n\002\b\006\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\b\n\002\020\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\006\bÀ\002\030\0002\0020\0012\0060\002j\002`\003B\007\b\002¢\006\002\020\004J\b\020\035\032\0020\036H\002J\b\020\037\032\0020\021H\002J\024\020 \032\0020\0362\n\020!\032\0060\002j\002`\003H\026J\r\020\"\032\0020\036H\000¢\006\002\b#J$\020$\032\0020%2\006\020&\032\0020\b2\n\020'\032\0060\002j\002`\0032\006\020(\032\0020)H\026J\b\020*\032\0020\025H\002J\030\020+\032\0020\0362\006\020,\032\0020\b2\006\020-\032\0020.H\024J\b\020/\032\0020\036H\026J\b\0200\032\0020\036H\026J\b\0201\032\0020\036H\002J\016\0202\032\0020\0362\006\0203\032\0020\bR\016\020\005\032\0020\006XT¢\006\002\n\000R\016\020\007\032\0020\bXT¢\006\002\n\000R\016\020\t\032\0020\006XT¢\006\002\n\000R\016\020\n\032\0020\bX\004¢\006\002\n\000R\016\020\013\032\0020\006XT¢\006\002\n\000R\016\020\f\032\0020\006XT¢\006\002\n\000R\016\020\r\032\0020\006XT¢\006\002\n\000R\016\020\016\032\0020\017XT¢\006\002\n\000R\026\020\020\032\004\030\0010\021X\016¢\006\b\n\000\022\004\b\022\020\004R\016\020\023\032\0020\006X\016¢\006\002\n\000R\024\020\024\032\0020\0258BX\004¢\006\006\032\004\b\024\020\026R\024\020\027\032\0020\0258BX\004¢\006\006\032\004\b\027\020\026R\024\020\030\032\0020\0258@X\004¢\006\006\032\004\b\031\020\026R\024\020\032\032\0020\0218TX\004¢\006\006\032\004\b\033\020\034¨\0064"}, d2={"Lkotlinx/coroutines/DefaultExecutor;", "Lkotlinx/coroutines/EventLoopImplBase;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "()V", "ACTIVE", "", "DEFAULT_KEEP_ALIVE_MS", "", "FRESH", "KEEP_ALIVE_NANOS", "SHUTDOWN", "SHUTDOWN_ACK", "SHUTDOWN_REQ", "THREAD_NAME", "", "_thread", "Ljava/lang/Thread;", "get_thread$annotations", "debugStatus", "isShutDown", "", "()Z", "isShutdownRequested", "isThreadPresent", "isThreadPresent$kotlinx_coroutines_core", "thread", "getThread", "()Ljava/lang/Thread;", "acknowledgeShutdownIfNeeded", "", "createThreadSync", "enqueue", "task", "ensureStarted", "ensureStarted$kotlinx_coroutines_core", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", "context", "Lkotlin/coroutines/CoroutineContext;", "notifyStartup", "reschedule", "now", "delayedTask", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "run", "shutdown", "shutdownError", "shutdownForTests", "timeout", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DefaultExecutor
  extends EventLoopImplBase
  implements Runnable
{
  private static final int ACTIVE = 1;
  private static final long DEFAULT_KEEP_ALIVE_MS = 1000L;
  private static final int FRESH = 0;
  public static final DefaultExecutor INSTANCE;
  private static final long KEEP_ALIVE_NANOS;
  private static final int SHUTDOWN = 4;
  private static final int SHUTDOWN_ACK = 3;
  private static final int SHUTDOWN_REQ = 2;
  public static final String THREAD_NAME = "kotlinx.coroutines.DefaultExecutor";
  private static volatile Thread _thread;
  private static volatile int debugStatus;
  
  static
  {
    Object localObject = new DefaultExecutor();
    INSTANCE = (DefaultExecutor)localObject;
    EventLoop.incrementUseCount$default((EventLoop)localObject, false, 1, null);
    TimeUnit localTimeUnit = TimeUnit.MILLISECONDS;
    Long localLong;
    try
    {
      localObject = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
    }
    catch (SecurityException localSecurityException)
    {
      localLong = Long.valueOf(1000L);
    }
    KEEP_ALIVE_NANOS = localTimeUnit.toNanos(localLong.longValue());
  }
  
  private final void acknowledgeShutdownIfNeeded()
  {
    try
    {
      boolean bool = isShutdownRequested();
      if (!bool) {
        return;
      }
      debugStatus = 3;
      resetAll();
      ((Object)this).notifyAll();
      return;
    }
    finally {}
  }
  
  private final Thread createThreadSync()
  {
    try
    {
      Thread localThread2 = _thread;
      Thread localThread1 = localThread2;
      if (localThread2 == null)
      {
        localThread1 = new java/lang/Thread;
        localThread1.<init>((Runnable)this, "kotlinx.coroutines.DefaultExecutor");
        _thread = localThread1;
        localThread1.setDaemon(true);
        localThread1.start();
      }
      return localThread1;
    }
    finally {}
  }
  
  private final boolean isShutDown()
  {
    boolean bool;
    if (debugStatus == 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final boolean isShutdownRequested()
  {
    int i = debugStatus;
    boolean bool;
    if ((i != 2) && (i != 3)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private final boolean notifyStartup()
  {
    try
    {
      boolean bool = isShutdownRequested();
      if (bool) {
        return false;
      }
      debugStatus = 1;
      ((Object)this).notifyAll();
      return true;
    }
    finally {}
  }
  
  private final void shutdownError()
  {
    throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
  }
  
  public void enqueue(Runnable paramRunnable)
  {
    if (isShutDown()) {
      shutdownError();
    }
    super.enqueue(paramRunnable);
  }
  
  public final void ensureStarted$kotlinx_coroutines_core()
  {
    try
    {
      boolean bool = DebugKt.getASSERTIONS_ENABLED();
      int j = 1;
      int i;
      AssertionError localAssertionError;
      if (bool)
      {
        if (_thread == null) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0)
        {
          localAssertionError = new java/lang/AssertionError;
          localAssertionError.<init>();
          throw localAssertionError;
        }
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        i = j;
        if (debugStatus != 0) {
          if (debugStatus == 3) {
            i = j;
          } else {
            i = 0;
          }
        }
        if (i == 0)
        {
          localAssertionError = new java/lang/AssertionError;
          localAssertionError.<init>();
          throw localAssertionError;
        }
      }
      debugStatus = 0;
      createThreadSync();
      while (debugStatus == 0) {
        ((Object)this).wait();
      }
      return;
    }
    finally {}
  }
  
  protected Thread getThread()
  {
    Thread localThread2 = _thread;
    Thread localThread1 = localThread2;
    if (localThread2 == null) {
      localThread1 = createThreadSync();
    }
    return localThread1;
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
  {
    return scheduleInvokeOnTimeout(paramLong, paramRunnable);
  }
  
  public final boolean isThreadPresent$kotlinx_coroutines_core()
  {
    boolean bool;
    if (_thread != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected void reschedule(long paramLong, EventLoopImplBase.DelayedTask paramDelayedTask)
  {
    shutdownError();
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: getstatic 185	kotlinx/coroutines/ThreadLocalEventLoop:INSTANCE	Lkotlinx/coroutines/ThreadLocalEventLoop;
    //   3: aload_0
    //   4: checkcast 87	kotlinx/coroutines/EventLoop
    //   7: invokevirtual 189	kotlinx/coroutines/ThreadLocalEventLoop:setEventLoop$kotlinx_coroutines_core	(Lkotlinx/coroutines/EventLoop;)V
    //   10: invokestatic 195	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   13: astore 10
    //   15: aload 10
    //   17: ifnonnull +6 -> 23
    //   20: goto +8 -> 28
    //   23: aload 10
    //   25: invokevirtual 200	kotlinx/coroutines/AbstractTimeSource:registerTimeLoopThread	()V
    //   28: ldc2_w 201
    //   31: lstore_1
    //   32: aload_0
    //   33: invokespecial 204	kotlinx/coroutines/DefaultExecutor:notifyStartup	()Z
    //   36: istore 9
    //   38: iload 9
    //   40: ifne +42 -> 82
    //   43: aconst_null
    //   44: putstatic 135	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   47: aload_0
    //   48: invokespecial 206	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   51: invokestatic 195	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   54: astore 10
    //   56: aload 10
    //   58: ifnonnull +6 -> 64
    //   61: goto +8 -> 69
    //   64: aload 10
    //   66: invokevirtual 209	kotlinx/coroutines/AbstractTimeSource:unregisterTimeLoopThread	()V
    //   69: aload_0
    //   70: invokevirtual 212	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   73: ifne +8 -> 81
    //   76: aload_0
    //   77: invokevirtual 214	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   80: pop
    //   81: return
    //   82: invokestatic 217	java/lang/Thread:interrupted	()Z
    //   85: pop
    //   86: aload_0
    //   87: invokevirtual 220	kotlinx/coroutines/DefaultExecutor:processNextEvent	()J
    //   90: lstore 7
    //   92: lload 7
    //   94: ldc2_w 201
    //   97: lcmp
    //   98: ifne +129 -> 227
    //   101: invokestatic 195	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   104: astore 10
    //   106: aload 10
    //   108: ifnonnull +9 -> 117
    //   111: aconst_null
    //   112: astore 10
    //   114: goto +13 -> 127
    //   117: aload 10
    //   119: invokevirtual 223	kotlinx/coroutines/AbstractTimeSource:nanoTime	()J
    //   122: invokestatic 109	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   125: astore 10
    //   127: aload 10
    //   129: ifnonnull +11 -> 140
    //   132: invokestatic 226	java/lang/System:nanoTime	()J
    //   135: lstore 5
    //   137: goto +10 -> 147
    //   140: aload 10
    //   142: invokevirtual 113	java/lang/Long:longValue	()J
    //   145: lstore 5
    //   147: lload_1
    //   148: lstore_3
    //   149: lload_1
    //   150: ldc2_w 201
    //   153: lcmp
    //   154: ifne +12 -> 166
    //   157: getstatic 119	kotlinx/coroutines/DefaultExecutor:KEEP_ALIVE_NANOS	J
    //   160: lstore_1
    //   161: lload 5
    //   163: lload_1
    //   164: ladd
    //   165: lstore_3
    //   166: lload_3
    //   167: lload 5
    //   169: lsub
    //   170: lstore_1
    //   171: lload_1
    //   172: lconst_0
    //   173: lcmp
    //   174: ifgt +42 -> 216
    //   177: aconst_null
    //   178: putstatic 135	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   181: aload_0
    //   182: invokespecial 206	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   185: invokestatic 195	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   188: astore 10
    //   190: aload 10
    //   192: ifnonnull +6 -> 198
    //   195: goto +8 -> 203
    //   198: aload 10
    //   200: invokevirtual 209	kotlinx/coroutines/AbstractTimeSource:unregisterTimeLoopThread	()V
    //   203: aload_0
    //   204: invokevirtual 212	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   207: ifne +8 -> 215
    //   210: aload_0
    //   211: invokevirtual 214	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   214: pop
    //   215: return
    //   216: lload 7
    //   218: lload_1
    //   219: invokestatic 232	kotlin/ranges/RangesKt:coerceAtMost	(JJ)J
    //   222: lstore 5
    //   224: goto +11 -> 235
    //   227: ldc2_w 201
    //   230: lstore_3
    //   231: lload 7
    //   233: lstore 5
    //   235: lload_3
    //   236: lstore_1
    //   237: lload 5
    //   239: lconst_0
    //   240: lcmp
    //   241: ifle -159 -> 82
    //   244: aload_0
    //   245: invokespecial 123	kotlinx/coroutines/DefaultExecutor:isShutdownRequested	()Z
    //   248: istore 9
    //   250: iload 9
    //   252: ifeq +42 -> 294
    //   255: aconst_null
    //   256: putstatic 135	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   259: aload_0
    //   260: invokespecial 206	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   263: invokestatic 195	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   266: astore 10
    //   268: aload 10
    //   270: ifnonnull +6 -> 276
    //   273: goto +8 -> 281
    //   276: aload 10
    //   278: invokevirtual 209	kotlinx/coroutines/AbstractTimeSource:unregisterTimeLoopThread	()V
    //   281: aload_0
    //   282: invokevirtual 212	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   285: ifne +8 -> 293
    //   288: aload_0
    //   289: invokevirtual 214	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   292: pop
    //   293: return
    //   294: invokestatic 195	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   297: astore 10
    //   299: aload 10
    //   301: ifnonnull +9 -> 310
    //   304: aconst_null
    //   305: astore 10
    //   307: goto +16 -> 323
    //   310: aload 10
    //   312: aload_0
    //   313: lload 5
    //   315: invokevirtual 236	kotlinx/coroutines/AbstractTimeSource:parkNanos	(Ljava/lang/Object;J)V
    //   318: getstatic 241	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   321: astore 10
    //   323: lload_3
    //   324: lstore_1
    //   325: aload 10
    //   327: ifnonnull -245 -> 82
    //   330: aload_0
    //   331: lload 5
    //   333: invokestatic 244	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
    //   336: lload_3
    //   337: lstore_1
    //   338: goto -256 -> 82
    //   341: astore 10
    //   343: aconst_null
    //   344: putstatic 135	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   347: aload_0
    //   348: invokespecial 206	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   351: invokestatic 195	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   354: astore 11
    //   356: aload 11
    //   358: ifnonnull +6 -> 364
    //   361: goto +8 -> 369
    //   364: aload 11
    //   366: invokevirtual 209	kotlinx/coroutines/AbstractTimeSource:unregisterTimeLoopThread	()V
    //   369: aload_0
    //   370: invokevirtual 212	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   373: ifne +8 -> 381
    //   376: aload_0
    //   377: invokevirtual 214	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   380: pop
    //   381: aload 10
    //   383: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	384	0	this	DefaultExecutor
    //   31	307	1	l1	long
    //   148	189	3	l2	long
    //   135	197	5	l3	long
    //   90	142	7	l4	long
    //   36	215	9	bool	boolean
    //   13	313	10	localObject1	Object
    //   341	41	10	localObject2	Object
    //   354	11	11	localAbstractTimeSource	AbstractTimeSource
    // Exception table:
    //   from	to	target	type
    //   32	38	341	finally
    //   82	92	341	finally
    //   101	106	341	finally
    //   117	127	341	finally
    //   132	137	341	finally
    //   140	147	341	finally
    //   157	161	341	finally
    //   216	224	341	finally
    //   244	250	341	finally
    //   294	299	341	finally
    //   310	323	341	finally
    //   330	336	341	finally
  }
  
  public void shutdown()
  {
    debugStatus = 4;
    super.shutdown();
  }
  
  public final void shutdownForTests(long paramLong)
  {
    try
    {
      long l = System.currentTimeMillis();
      if (!isShutdownRequested()) {
        debugStatus = 2;
      }
      while ((debugStatus != 3) && (_thread != null))
      {
        Thread localThread = _thread;
        if (localThread != null)
        {
          Object localObject1 = AbstractTimeSourceKt.getTimeSource();
          if (localObject1 == null)
          {
            localObject1 = null;
          }
          else
          {
            ((AbstractTimeSource)localObject1).unpark(localThread);
            localObject1 = Unit.INSTANCE;
          }
          if (localObject1 == null) {
            LockSupport.unpark(localThread);
          }
        }
        if (l + paramLong - System.currentTimeMillis() <= 0L) {
          break;
        }
        ((Object)this).wait(paramLong);
      }
      debugStatus = 0;
      return;
    }
    finally {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DefaultExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */