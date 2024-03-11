package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0006\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\020\000\n\002\b\003\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\037\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006\022\b\020\007\032\004\030\0010\b¢\006\002\020\tJ\022\020\r\032\0020\0162\b\020\017\032\004\030\0010\020H\024J\013\020\021\032\0028\000¢\006\002\020\022R\016\020\005\032\0020\006X\004¢\006\002\n\000R\020\020\007\032\004\030\0010\bX\004¢\006\002\n\000R\024\020\n\032\0020\0138TX\004¢\006\006\032\004\b\n\020\f¨\006\023"}, d2={"Lkotlinx/coroutines/BlockingCoroutine;", "T", "Lkotlinx/coroutines/AbstractCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "blockedThread", "Ljava/lang/Thread;", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Thread;Lkotlinx/coroutines/EventLoop;)V", "isScopedCoroutine", "", "()Z", "afterCompletion", "", "state", "", "joinBlocking", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class BlockingCoroutine<T>
  extends AbstractCoroutine<T>
{
  private final Thread blockedThread;
  private final EventLoop eventLoop;
  
  public BlockingCoroutine(CoroutineContext paramCoroutineContext, Thread paramThread, EventLoop paramEventLoop)
  {
    super(paramCoroutineContext, true, true);
    this.blockedThread = paramThread;
    this.eventLoop = paramEventLoop;
  }
  
  protected void afterCompletion(Object paramObject)
  {
    if (!Intrinsics.areEqual(Thread.currentThread(), this.blockedThread))
    {
      Thread localThread = this.blockedThread;
      paramObject = AbstractTimeSourceKt.getTimeSource();
      if (paramObject == null)
      {
        paramObject = null;
      }
      else
      {
        ((AbstractTimeSource)paramObject).unpark(localThread);
        paramObject = Unit.INSTANCE;
      }
      if (paramObject == null) {
        LockSupport.unpark(localThread);
      }
    }
  }
  
  protected boolean isScopedCoroutine()
  {
    return true;
  }
  
  /* Error */
  public final T joinBlocking()
  {
    // Byte code:
    //   0: invokestatic 62	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   3: astore 4
    //   5: aload 4
    //   7: ifnonnull +6 -> 13
    //   10: goto +8 -> 18
    //   13: aload 4
    //   15: invokevirtual 82	kotlinx/coroutines/AbstractTimeSource:registerTimeLoopThread	()V
    //   18: aload_0
    //   19: getfield 42	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   22: astore 4
    //   24: aconst_null
    //   25: astore 5
    //   27: aload 4
    //   29: ifnonnull +6 -> 35
    //   32: goto +11 -> 43
    //   35: aload 4
    //   37: iconst_0
    //   38: iconst_1
    //   39: aconst_null
    //   40: invokestatic 88	kotlinx/coroutines/EventLoop:incrementUseCount$default	(Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
    //   43: invokestatic 91	java/lang/Thread:interrupted	()Z
    //   46: ifne +159 -> 205
    //   49: aload_0
    //   50: getfield 42	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   53: astore 4
    //   55: aload 4
    //   57: ifnonnull +10 -> 67
    //   60: ldc2_w 92
    //   63: lstore_1
    //   64: goto +9 -> 73
    //   67: aload 4
    //   69: invokevirtual 97	kotlinx/coroutines/EventLoop:processNextEvent	()J
    //   72: lstore_1
    //   73: aload_0
    //   74: invokevirtual 100	kotlinx/coroutines/BlockingCoroutine:isCompleted	()Z
    //   77: istore_3
    //   78: iload_3
    //   79: ifeq +85 -> 164
    //   82: aload_0
    //   83: getfield 42	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   86: astore 4
    //   88: aload 4
    //   90: ifnonnull +6 -> 96
    //   93: goto +11 -> 104
    //   96: aload 4
    //   98: iconst_0
    //   99: iconst_1
    //   100: aconst_null
    //   101: invokestatic 103	kotlinx/coroutines/EventLoop:decrementUseCount$default	(Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
    //   104: invokestatic 62	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   107: astore 4
    //   109: aload 4
    //   111: ifnonnull +6 -> 117
    //   114: goto +8 -> 122
    //   117: aload 4
    //   119: invokevirtual 106	kotlinx/coroutines/AbstractTimeSource:unregisterTimeLoopThread	()V
    //   122: aload_0
    //   123: invokevirtual 109	kotlinx/coroutines/BlockingCoroutine:getState$kotlinx_coroutines_core	()Ljava/lang/Object;
    //   126: invokestatic 115	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   129: astore 6
    //   131: aload 5
    //   133: astore 4
    //   135: aload 6
    //   137: instanceof 117
    //   140: ifeq +10 -> 150
    //   143: aload 6
    //   145: checkcast 117	kotlinx/coroutines/CompletedExceptionally
    //   148: astore 4
    //   150: aload 4
    //   152: ifnonnull +6 -> 158
    //   155: aload 6
    //   157: areturn
    //   158: aload 4
    //   160: getfield 121	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   163: athrow
    //   164: invokestatic 62	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   167: astore 4
    //   169: aload 4
    //   171: ifnonnull +9 -> 180
    //   174: aconst_null
    //   175: astore 4
    //   177: goto +15 -> 192
    //   180: aload 4
    //   182: aload_0
    //   183: lload_1
    //   184: invokevirtual 125	kotlinx/coroutines/AbstractTimeSource:parkNanos	(Ljava/lang/Object;J)V
    //   187: getstatic 74	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   190: astore 4
    //   192: aload 4
    //   194: ifnonnull -151 -> 43
    //   197: aload_0
    //   198: lload_1
    //   199: invokestatic 126	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
    //   202: goto -159 -> 43
    //   205: new 128	java/lang/InterruptedException
    //   208: astore 4
    //   210: aload 4
    //   212: invokespecial 130	java/lang/InterruptedException:<init>	()V
    //   215: aload_0
    //   216: aload 4
    //   218: checkcast 132	java/lang/Throwable
    //   221: invokevirtual 136	kotlinx/coroutines/BlockingCoroutine:cancelCoroutine	(Ljava/lang/Throwable;)Z
    //   224: pop
    //   225: aload 4
    //   227: checkcast 132	java/lang/Throwable
    //   230: athrow
    //   231: astore 4
    //   233: aload_0
    //   234: getfield 42	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   237: astore 5
    //   239: aload 5
    //   241: ifnonnull +6 -> 247
    //   244: goto +11 -> 255
    //   247: aload 5
    //   249: iconst_0
    //   250: iconst_1
    //   251: aconst_null
    //   252: invokestatic 103	kotlinx/coroutines/EventLoop:decrementUseCount$default	(Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
    //   255: aload 4
    //   257: athrow
    //   258: astore 5
    //   260: invokestatic 62	kotlinx/coroutines/AbstractTimeSourceKt:getTimeSource	()Lkotlinx/coroutines/AbstractTimeSource;
    //   263: astore 4
    //   265: aload 4
    //   267: ifnonnull +6 -> 273
    //   270: goto +8 -> 278
    //   273: aload 4
    //   275: invokevirtual 106	kotlinx/coroutines/AbstractTimeSource:unregisterTimeLoopThread	()V
    //   278: aload 5
    //   280: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	281	0	this	BlockingCoroutine
    //   63	136	1	l	long
    //   77	2	3	bool	boolean
    //   3	223	4	localObject1	Object
    //   231	25	4	localObject2	Object
    //   263	11	4	localAbstractTimeSource	AbstractTimeSource
    //   25	223	5	localEventLoop	EventLoop
    //   258	21	5	localObject3	Object
    //   129	27	6	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   43	55	231	finally
    //   67	73	231	finally
    //   73	78	231	finally
    //   164	169	231	finally
    //   180	192	231	finally
    //   197	202	231	finally
    //   205	231	231	finally
    //   18	24	258	finally
    //   35	43	258	finally
    //   82	88	258	finally
    //   96	104	258	finally
    //   233	239	258	finally
    //   247	255	258	finally
    //   255	258	258	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/BlockingCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */