package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

@Metadata(d1={"\000J\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\005\032;\020\006\032\0020\007*\006\022\002\b\0030\b2\b\020\t\032\004\030\0010\n2\006\020\013\032\0020\f2\b\b\002\020\r\032\0020\0072\f\020\016\032\b\022\004\022\0020\0200\017H\b\032U\020\021\032\0020\020\"\004\b\000\020\022*\b\022\004\022\002H\0220\0232\f\020\024\032\b\022\004\022\002H\0220\0252%\b\002\020\026\032\037\022\023\022\0210\030¢\006\f\b\031\022\b\b\032\022\004\b\b(\033\022\004\022\0020\020\030\0010\027H\007ø\001\000¢\006\002\020\034\032\022\020\035\032\0020\007*\b\022\004\022\0020\0200\bH\000\"\026\020\000\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\005\020\003\002\004\n\002\b\031¨\006\036"}, d2={"REUSABLE_CLAIMED", "Lkotlinx/coroutines/internal/Symbol;", "getREUSABLE_CLAIMED$annotations", "()V", "UNDEFINED", "getUNDEFINED$annotations", "executeUnconfined", "", "Lkotlinx/coroutines/internal/DispatchedContinuation;", "contState", "", "mode", "", "doYield", "block", "Lkotlin/Function0;", "", "resumeCancellableWith", "T", "Lkotlin/coroutines/Continuation;", "result", "Lkotlin/Result;", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "yieldUndispatched", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class DispatchedContinuationKt
{
  public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");
  private static final Symbol UNDEFINED = new Symbol("UNDEFINED");
  
  private static final boolean executeUnconfined(DispatchedContinuation<?> paramDispatchedContinuation, Object paramObject, int paramInt, boolean paramBoolean, Function0<Unit> paramFunction0)
  {
    boolean bool2 = DebugKt.getASSERTIONS_ENABLED();
    boolean bool1 = false;
    if (bool2)
    {
      int i;
      if (paramInt != -1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
    if ((paramBoolean) && (localEventLoop.isUnconfinedQueueEmpty())) {
      return false;
    }
    if (localEventLoop.isUnconfinedLoopActive())
    {
      paramDispatchedContinuation._state = paramObject;
      paramDispatchedContinuation.resumeMode = paramInt;
      localEventLoop.dispatchUnconfined((DispatchedTask)paramDispatchedContinuation);
      paramBoolean = true;
    }
    else
    {
      paramDispatchedContinuation = (DispatchedTask)paramDispatchedContinuation;
      localEventLoop.incrementUseCount(true);
      try
      {
        paramFunction0.invoke();
        do
        {
          paramBoolean = localEventLoop.processUnconfinedEvent();
        } while (paramBoolean);
        InlineMarker.finallyStart(1);
      }
      finally {}
    }
    try
    {
      paramDispatchedContinuation.handleFatalException((Throwable)paramObject, null);
      InlineMarker.finallyStart(1);
      localEventLoop.decrementUseCount(true);
      InlineMarker.finallyEnd(1);
      paramBoolean = bool1;
      return paramBoolean;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      localEventLoop.decrementUseCount(true);
      InlineMarker.finallyEnd(1);
    }
  }
  
  /* Error */
  public static final <T> void resumeCancellableWith(kotlin.coroutines.Continuation<? super T> paramContinuation, Object paramObject, kotlin.jvm.functions.Function1<? super Throwable, Unit> paramFunction1)
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 91
    //   4: ifeq +394 -> 398
    //   7: aload_0
    //   8: checkcast 91	kotlinx/coroutines/internal/DispatchedContinuation
    //   11: astore 8
    //   13: aload_1
    //   14: aload_2
    //   15: invokestatic 144	kotlinx/coroutines/CompletionStateKt:toState	(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;
    //   18: astore 6
    //   20: aload 8
    //   22: getfield 148	kotlinx/coroutines/internal/DispatchedContinuation:dispatcher	Lkotlinx/coroutines/CoroutineDispatcher;
    //   25: aload 8
    //   27: invokevirtual 152	kotlinx/coroutines/internal/DispatchedContinuation:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   30: invokevirtual 158	kotlinx/coroutines/CoroutineDispatcher:isDispatchNeeded	(Lkotlin/coroutines/CoroutineContext;)Z
    //   33: ifeq +37 -> 70
    //   36: aload 8
    //   38: aload 6
    //   40: putfield 95	kotlinx/coroutines/internal/DispatchedContinuation:_state	Ljava/lang/Object;
    //   43: aload 8
    //   45: iconst_1
    //   46: putfield 99	kotlinx/coroutines/internal/DispatchedContinuation:resumeMode	I
    //   49: aload 8
    //   51: getfield 148	kotlinx/coroutines/internal/DispatchedContinuation:dispatcher	Lkotlinx/coroutines/CoroutineDispatcher;
    //   54: aload 8
    //   56: invokevirtual 152	kotlinx/coroutines/internal/DispatchedContinuation:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   59: aload 8
    //   61: checkcast 160	java/lang/Runnable
    //   64: invokevirtual 164	kotlinx/coroutines/CoroutineDispatcher:dispatch	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V
    //   67: goto +320 -> 387
    //   70: invokestatic 67	kotlinx/coroutines/DebugKt:getASSERTIONS_ENABLED	()Z
    //   73: ifeq +3 -> 76
    //   76: getstatic 77	kotlinx/coroutines/ThreadLocalEventLoop:INSTANCE	Lkotlinx/coroutines/ThreadLocalEventLoop;
    //   79: invokevirtual 81	kotlinx/coroutines/ThreadLocalEventLoop:getEventLoop$kotlinx_coroutines_core	()Lkotlinx/coroutines/EventLoop;
    //   82: astore_2
    //   83: aload_2
    //   84: invokevirtual 89	kotlinx/coroutines/EventLoop:isUnconfinedLoopActive	()Z
    //   87: ifeq +28 -> 115
    //   90: aload 8
    //   92: aload 6
    //   94: putfield 95	kotlinx/coroutines/internal/DispatchedContinuation:_state	Ljava/lang/Object;
    //   97: aload 8
    //   99: iconst_1
    //   100: putfield 99	kotlinx/coroutines/internal/DispatchedContinuation:resumeMode	I
    //   103: aload_2
    //   104: aload 8
    //   106: checkcast 101	kotlinx/coroutines/DispatchedTask
    //   109: invokevirtual 105	kotlinx/coroutines/EventLoop:dispatchUnconfined	(Lkotlinx/coroutines/DispatchedTask;)V
    //   112: goto +275 -> 387
    //   115: aload 8
    //   117: checkcast 101	kotlinx/coroutines/DispatchedTask
    //   120: astore 5
    //   122: aload_2
    //   123: iconst_1
    //   124: invokevirtual 109	kotlinx/coroutines/EventLoop:incrementUseCount	(Z)V
    //   127: aload 8
    //   129: invokevirtual 152	kotlinx/coroutines/internal/DispatchedContinuation:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   132: getstatic 170	kotlinx/coroutines/Job:Key	Lkotlinx/coroutines/Job$Key;
    //   135: checkcast 172	kotlin/coroutines/CoroutineContext$Key
    //   138: invokeinterface 178 2 0
    //   143: checkcast 166	kotlinx/coroutines/Job
    //   146: astore_0
    //   147: aload_0
    //   148: ifnull +68 -> 216
    //   151: aload_0
    //   152: invokeinterface 181 1 0
    //   157: ifne +59 -> 216
    //   160: aload_0
    //   161: invokeinterface 185 1 0
    //   166: astore_0
    //   167: aload 8
    //   169: aload 6
    //   171: aload_0
    //   172: checkcast 187	java/lang/Throwable
    //   175: invokevirtual 191	kotlinx/coroutines/internal/DispatchedContinuation:cancelCompletedResult$kotlinx_coroutines_core	(Ljava/lang/Object;Ljava/lang/Throwable;)V
    //   178: aload 8
    //   180: checkcast 193	kotlin/coroutines/Continuation
    //   183: astore 7
    //   185: getstatic 199	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   188: astore 6
    //   190: aload 7
    //   192: aload_0
    //   193: checkcast 187	java/lang/Throwable
    //   196: invokestatic 205	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   199: invokestatic 209	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   202: invokeinterface 213 2 0
    //   207: iconst_1
    //   208: istore_3
    //   209: goto +9 -> 218
    //   212: astore_0
    //   213: goto +162 -> 375
    //   216: iconst_0
    //   217: istore_3
    //   218: iload_3
    //   219: ifne +137 -> 356
    //   222: aload 8
    //   224: getfield 216	kotlinx/coroutines/internal/DispatchedContinuation:continuation	Lkotlin/coroutines/Continuation;
    //   227: astore_0
    //   228: aload 8
    //   230: getfield 219	kotlinx/coroutines/internal/DispatchedContinuation:countOrElement	Ljava/lang/Object;
    //   233: astore 7
    //   235: aload_0
    //   236: invokeinterface 220 1 0
    //   241: astore 6
    //   243: aload 6
    //   245: aload 7
    //   247: invokestatic 226	kotlinx/coroutines/internal/ThreadContextKt:updateThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
    //   250: astore 7
    //   252: getstatic 229	kotlinx/coroutines/internal/ThreadContextKt:NO_THREAD_ELEMENTS	Lkotlinx/coroutines/internal/Symbol;
    //   255: astore 9
    //   257: aload 7
    //   259: aload 9
    //   261: if_acmpeq +15 -> 276
    //   264: aload_0
    //   265: aload 6
    //   267: aload 7
    //   269: invokestatic 235	kotlinx/coroutines/CoroutineContextKt:updateUndispatchedCompletion	(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Lkotlinx/coroutines/UndispatchedCoroutine;
    //   272: astore_0
    //   273: goto +10 -> 283
    //   276: aconst_null
    //   277: checkcast 237	kotlinx/coroutines/UndispatchedCoroutine
    //   280: astore_0
    //   281: aconst_null
    //   282: astore_0
    //   283: aload 8
    //   285: getfield 216	kotlinx/coroutines/internal/DispatchedContinuation:continuation	Lkotlin/coroutines/Continuation;
    //   288: astore 8
    //   290: aload 8
    //   292: aload_1
    //   293: invokeinterface 213 2 0
    //   298: getstatic 242	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   301: astore_1
    //   302: aload_0
    //   303: ifnull +10 -> 313
    //   306: aload_0
    //   307: invokevirtual 245	kotlinx/coroutines/UndispatchedCoroutine:clearThreadContext	()Z
    //   310: ifeq +10 -> 320
    //   313: aload 6
    //   315: aload 7
    //   317: invokestatic 249	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   320: goto +36 -> 356
    //   323: astore_1
    //   324: goto +4 -> 328
    //   327: astore_1
    //   328: aload_0
    //   329: ifnull +10 -> 339
    //   332: aload_0
    //   333: invokevirtual 245	kotlinx/coroutines/UndispatchedCoroutine:clearThreadContext	()Z
    //   336: ifeq +10 -> 346
    //   339: aload 6
    //   341: aload 7
    //   343: invokestatic 249	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   346: aload_1
    //   347: athrow
    //   348: astore_0
    //   349: goto +26 -> 375
    //   352: astore_0
    //   353: goto +22 -> 375
    //   356: aload_2
    //   357: invokevirtual 118	kotlinx/coroutines/EventLoop:processUnconfinedEvent	()Z
    //   360: istore 4
    //   362: iload 4
    //   364: ifne -8 -> 356
    //   367: goto +15 -> 382
    //   370: astore_0
    //   371: goto +4 -> 375
    //   374: astore_0
    //   375: aload 5
    //   377: aload_0
    //   378: aconst_null
    //   379: invokevirtual 128	kotlinx/coroutines/DispatchedTask:handleFatalException	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   382: aload_2
    //   383: iconst_1
    //   384: invokevirtual 131	kotlinx/coroutines/EventLoop:decrementUseCount	(Z)V
    //   387: goto +18 -> 405
    //   390: astore_0
    //   391: aload_2
    //   392: iconst_1
    //   393: invokevirtual 131	kotlinx/coroutines/EventLoop:decrementUseCount	(Z)V
    //   396: aload_0
    //   397: athrow
    //   398: aload_0
    //   399: aload_1
    //   400: invokeinterface 213 2 0
    //   405: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	406	0	paramContinuation	kotlin.coroutines.Continuation<? super T>
    //   0	406	1	paramObject	Object
    //   0	406	2	paramFunction1	kotlin.jvm.functions.Function1<? super Throwable, Unit>
    //   208	11	3	i	int
    //   360	3	4	bool	boolean
    //   120	256	5	localDispatchedTask	DispatchedTask
    //   18	322	6	localObject1	Object
    //   183	159	7	localObject2	Object
    //   11	280	8	localObject3	Object
    //   255	5	9	localSymbol	Symbol
    // Exception table:
    //   from	to	target	type
    //   151	207	212	finally
    //   290	298	323	finally
    //   298	302	323	finally
    //   283	290	327	finally
    //   264	273	348	finally
    //   276	281	348	finally
    //   243	257	352	finally
    //   306	313	370	finally
    //   313	320	370	finally
    //   332	339	370	finally
    //   339	346	370	finally
    //   346	348	370	finally
    //   356	362	370	finally
    //   127	147	374	finally
    //   222	243	374	finally
    //   375	382	390	finally
  }
  
  public static final boolean yieldUndispatched(DispatchedContinuation<? super Unit> paramDispatchedContinuation)
  {
    Object localObject = Unit.INSTANCE;
    if (DebugKt.getASSERTIONS_ENABLED()) {}
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
    boolean bool3 = localEventLoop.isUnconfinedQueueEmpty();
    boolean bool2 = false;
    boolean bool1 = false;
    if (bool3)
    {
      bool1 = bool2;
    }
    else if (localEventLoop.isUnconfinedLoopActive())
    {
      paramDispatchedContinuation._state = localObject;
      paramDispatchedContinuation.resumeMode = 1;
      localEventLoop.dispatchUnconfined((DispatchedTask)paramDispatchedContinuation);
      bool1 = true;
    }
    else
    {
      localObject = (DispatchedTask)paramDispatchedContinuation;
      localEventLoop.incrementUseCount(true);
      try
      {
        paramDispatchedContinuation.run();
        for (;;)
        {
          bool2 = localEventLoop.processUnconfinedEvent();
          if (!bool2) {
            break;
          }
        }
      }
      finally {}
    }
    try
    {
      ((DispatchedTask)localObject).handleFatalException(paramDispatchedContinuation, null);
      return bool1;
    }
    finally
    {
      localEventLoop.decrementUseCount(true);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/DispatchedContinuationKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */