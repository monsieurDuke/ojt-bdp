package kotlinx.coroutines;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.scheduling.Task;

@Metadata(d1={"\0004\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\000\n\000\n\002\020\003\n\002\b\016\b \030\000*\006\b\000\020\001 \0002\0060\002j\002`\003B\r\022\006\020\004\032\0020\005¢\006\002\020\006J\037\020\013\032\0020\f2\b\020\r\032\004\030\0010\0162\006\020\017\032\0020\020H\020¢\006\002\b\021J\031\020\022\032\004\030\0010\0202\b\020\023\032\004\030\0010\016H\020¢\006\002\b\024J\037\020\025\032\002H\001\"\004\b\001\020\0012\b\020\023\032\004\030\0010\016H\020¢\006\004\b\026\020\027J\032\020\030\032\0020\f2\b\020\031\032\004\030\0010\0202\b\020\032\032\004\030\0010\020J\006\020\033\032\0020\fJ\017\020\034\032\004\030\0010\016H ¢\006\002\b\035R\030\020\007\032\b\022\004\022\0028\0000\bX \004¢\006\006\032\004\b\t\020\nR\022\020\004\032\0020\0058\006@\006X\016¢\006\002\n\000¨\006\036"}, d2={"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", "", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelCompletedResult", "", "takenState", "", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "getExceptionalResult", "state", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class DispatchedTask<T>
  extends Task
{
  public int resumeMode;
  
  public DispatchedTask(int paramInt)
  {
    this.resumeMode = paramInt;
  }
  
  public void cancelCompletedResult$kotlinx_coroutines_core(Object paramObject, Throwable paramThrowable) {}
  
  public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();
  
  public Throwable getExceptionalResult$kotlinx_coroutines_core(Object paramObject)
  {
    boolean bool = paramObject instanceof CompletedExceptionally;
    Object localObject = null;
    if (bool) {
      paramObject = (CompletedExceptionally)paramObject;
    } else {
      paramObject = null;
    }
    if (paramObject == null) {
      paramObject = localObject;
    } else {
      paramObject = ((CompletedExceptionally)paramObject).cause;
    }
    return (Throwable)paramObject;
  }
  
  public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object paramObject)
  {
    return (T)paramObject;
  }
  
  public final void handleFatalException(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    if ((paramThrowable1 == null) && (paramThrowable2 == null)) {
      return;
    }
    if ((paramThrowable1 != null) && (paramThrowable2 != null)) {
      ExceptionsKt.addSuppressed(paramThrowable1, paramThrowable2);
    }
    if (paramThrowable1 == null) {
      paramThrowable1 = paramThrowable2;
    }
    paramThrowable2 = "Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers";
    Intrinsics.checkNotNull(paramThrowable1);
    paramThrowable1 = new CoroutinesInternalError(paramThrowable2, paramThrowable1);
    CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), (Throwable)paramThrowable1);
  }
  
  /* Error */
  public final void run()
  {
    // Byte code:
    //   0: invokestatic 119	kotlinx/coroutines/DebugKt:getASSERTIONS_ENABLED	()Z
    //   3: ifeq +33 -> 36
    //   6: aload_0
    //   7: getfield 51	kotlinx/coroutines/DispatchedTask:resumeMode	I
    //   10: iconst_m1
    //   11: if_icmpeq +8 -> 19
    //   14: iconst_1
    //   15: istore_1
    //   16: goto +5 -> 21
    //   19: iconst_0
    //   20: istore_1
    //   21: iload_1
    //   22: ifeq +6 -> 28
    //   25: goto +11 -> 36
    //   28: new 121	java/lang/AssertionError
    //   31: dup
    //   32: invokespecial 122	java/lang/AssertionError:<init>	()V
    //   35: athrow
    //   36: aload_0
    //   37: getfield 126	kotlinx/coroutines/DispatchedTask:taskContext	Lkotlinx/coroutines/scheduling/TaskContext;
    //   40: astore 8
    //   42: aconst_null
    //   43: astore 6
    //   45: aconst_null
    //   46: astore 5
    //   48: aload_0
    //   49: invokevirtual 99	kotlinx/coroutines/DispatchedTask:getDelegate$kotlinx_coroutines_core	()Lkotlin/coroutines/Continuation;
    //   52: checkcast 128	kotlinx/coroutines/internal/DispatchedContinuation
    //   55: astore_3
    //   56: aload_3
    //   57: getfield 131	kotlinx/coroutines/internal/DispatchedContinuation:continuation	Lkotlin/coroutines/Continuation;
    //   60: astore 11
    //   62: aload_3
    //   63: getfield 135	kotlinx/coroutines/internal/DispatchedContinuation:countOrElement	Ljava/lang/Object;
    //   66: astore_3
    //   67: aload 11
    //   69: invokeinterface 105 1 0
    //   74: astore 9
    //   76: aload 9
    //   78: aload_3
    //   79: invokestatic 141	kotlinx/coroutines/internal/ThreadContextKt:updateThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
    //   82: astore 10
    //   84: getstatic 145	kotlinx/coroutines/internal/ThreadContextKt:NO_THREAD_ELEMENTS	Lkotlinx/coroutines/internal/Symbol;
    //   87: astore_3
    //   88: aconst_null
    //   89: astore 7
    //   91: aload 10
    //   93: aload_3
    //   94: if_acmpeq +17 -> 111
    //   97: aload 11
    //   99: aload 9
    //   101: aload 10
    //   103: invokestatic 151	kotlinx/coroutines/CoroutineContextKt:updateUndispatchedCompletion	(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Lkotlinx/coroutines/UndispatchedCoroutine;
    //   106: astore 4
    //   108: goto +11 -> 119
    //   111: aconst_null
    //   112: checkcast 153	kotlinx/coroutines/UndispatchedCoroutine
    //   115: astore_3
    //   116: aconst_null
    //   117: astore 4
    //   119: aload 11
    //   121: invokeinterface 105 1 0
    //   126: astore 14
    //   128: aload_0
    //   129: invokevirtual 156	kotlinx/coroutines/DispatchedTask:takeState$kotlinx_coroutines_core	()Ljava/lang/Object;
    //   132: astore 13
    //   134: aload_0
    //   135: aload 13
    //   137: invokevirtual 158	kotlinx/coroutines/DispatchedTask:getExceptionalResult$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   140: astore 12
    //   142: aload 7
    //   144: astore_3
    //   145: aload 12
    //   147: ifnonnull +40 -> 187
    //   150: aload 7
    //   152: astore_3
    //   153: aload_0
    //   154: getfield 51	kotlinx/coroutines/DispatchedTask:resumeMode	I
    //   157: invokestatic 164	kotlinx/coroutines/DispatchedTaskKt:isCancellableMode	(I)Z
    //   160: ifeq +27 -> 187
    //   163: aload 14
    //   165: getstatic 170	kotlinx/coroutines/Job:Key	Lkotlinx/coroutines/Job$Key;
    //   168: checkcast 172	kotlin/coroutines/CoroutineContext$Key
    //   171: invokeinterface 178 2 0
    //   176: checkcast 166	kotlinx/coroutines/Job
    //   179: astore_3
    //   180: goto +7 -> 187
    //   183: astore_3
    //   184: goto +218 -> 402
    //   187: aload_3
    //   188: ifnull +97 -> 285
    //   191: aload_3
    //   192: invokeinterface 181 1 0
    //   197: ifne +88 -> 285
    //   200: aload_3
    //   201: invokeinterface 185 1 0
    //   206: astore_3
    //   207: aload_0
    //   208: aload 13
    //   210: aload_3
    //   211: checkcast 107	java/lang/Throwable
    //   214: invokevirtual 187	kotlinx/coroutines/DispatchedTask:cancelCompletedResult$kotlinx_coroutines_core	(Ljava/lang/Object;Ljava/lang/Throwable;)V
    //   217: getstatic 193	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   220: astore 7
    //   222: invokestatic 196	kotlinx/coroutines/DebugKt:getRECOVER_STACK_TRACES	()Z
    //   225: istore_2
    //   226: iload_2
    //   227: ifeq +36 -> 263
    //   230: aload 11
    //   232: instanceof 198
    //   235: ifne +6 -> 241
    //   238: goto +25 -> 263
    //   241: aload_3
    //   242: checkcast 107	java/lang/Throwable
    //   245: astore_3
    //   246: aload_3
    //   247: aload 11
    //   249: checkcast 198	kotlin/coroutines/jvm/internal/CoroutineStackFrame
    //   252: invokestatic 204	kotlinx/coroutines/internal/StackTraceRecoveryKt:access$recoverFromStackFrame	(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
    //   255: astore_3
    //   256: goto +12 -> 268
    //   259: astore_3
    //   260: goto +142 -> 402
    //   263: aload_3
    //   264: checkcast 107	java/lang/Throwable
    //   267: astore_3
    //   268: aload 11
    //   270: aload_3
    //   271: invokestatic 210	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   274: invokestatic 213	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   277: invokeinterface 216 2 0
    //   282: goto +53 -> 335
    //   285: aload 12
    //   287: ifnull +25 -> 312
    //   290: getstatic 193	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   293: astore_3
    //   294: aload 11
    //   296: aload 12
    //   298: invokestatic 210	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   301: invokestatic 213	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   304: invokeinterface 216 2 0
    //   309: goto +26 -> 335
    //   312: aload_0
    //   313: aload 13
    //   315: invokevirtual 218	kotlinx/coroutines/DispatchedTask:getSuccessfulResult$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   318: astore_3
    //   319: getstatic 193	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   322: astore 7
    //   324: aload 11
    //   326: aload_3
    //   327: invokestatic 213	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   330: invokeinterface 216 2 0
    //   335: getstatic 224	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   338: astore_3
    //   339: aload 4
    //   341: ifnull +11 -> 352
    //   344: aload 4
    //   346: invokevirtual 227	kotlinx/coroutines/UndispatchedCoroutine:clearThreadContext	()Z
    //   349: ifeq +10 -> 359
    //   352: aload 9
    //   354: aload 10
    //   356: invokestatic 231	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   359: getstatic 193	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   362: astore_3
    //   363: aload_0
    //   364: checkcast 2	kotlinx/coroutines/DispatchedTask
    //   367: astore_3
    //   368: aload 8
    //   370: invokeinterface 236 1 0
    //   375: getstatic 224	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   378: invokestatic 213	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   381: astore 4
    //   383: aload 6
    //   385: astore_3
    //   386: goto +85 -> 471
    //   389: astore 4
    //   391: aload 5
    //   393: astore_3
    //   394: goto +62 -> 456
    //   397: astore_3
    //   398: goto +4 -> 402
    //   401: astore_3
    //   402: aload 4
    //   404: ifnull +11 -> 415
    //   407: aload 4
    //   409: invokevirtual 227	kotlinx/coroutines/UndispatchedCoroutine:clearThreadContext	()Z
    //   412: ifeq +10 -> 422
    //   415: aload 9
    //   417: aload 10
    //   419: invokestatic 231	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   422: aload_3
    //   423: athrow
    //   424: astore_3
    //   425: getstatic 193	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   428: astore 4
    //   430: aload_0
    //   431: checkcast 2	kotlinx/coroutines/DispatchedTask
    //   434: astore 4
    //   436: aload 8
    //   438: invokeinterface 236 1 0
    //   443: getstatic 224	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   446: invokestatic 213	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   449: astore 4
    //   451: goto +20 -> 471
    //   454: astore 4
    //   456: getstatic 193	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   459: astore 5
    //   461: aload 4
    //   463: invokestatic 210	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   466: invokestatic 213	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   469: astore 4
    //   471: aload_0
    //   472: aload_3
    //   473: aload 4
    //   475: invokestatic 239	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   478: invokevirtual 241	kotlinx/coroutines/DispatchedTask:handleFatalException	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   481: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	482	0	this	DispatchedTask
    //   15	7	1	i	int
    //   225	2	2	bool	boolean
    //   55	125	3	localObject1	Object
    //   183	18	3	localObject2	Object
    //   206	50	3	localObject3	Object
    //   259	5	3	localObject4	Object
    //   267	127	3	localObject5	Object
    //   397	1	3	localObject6	Object
    //   401	22	3	localObject7	Object
    //   424	49	3	localThrowable1	Throwable
    //   106	276	4	localObject8	Object
    //   389	19	4	localObject9	Object
    //   428	22	4	localObject10	Object
    //   454	8	4	localThrowable2	Throwable
    //   469	5	4	localObject11	Object
    //   46	414	5	localCompanion1	kotlin.Result.Companion
    //   43	341	6	localObject12	Object
    //   89	234	7	localCompanion2	kotlin.Result.Companion
    //   40	397	8	localTaskContext	kotlinx.coroutines.scheduling.TaskContext
    //   74	342	9	localCoroutineContext1	kotlin.coroutines.CoroutineContext
    //   82	336	10	localObject13	Object
    //   60	265	11	localContinuation	Continuation
    //   140	157	12	localThrowable3	Throwable
    //   132	182	13	localObject14	Object
    //   126	38	14	localCoroutineContext2	kotlin.coroutines.CoroutineContext
    // Exception table:
    //   from	to	target	type
    //   153	180	183	finally
    //   230	238	259	finally
    //   241	246	259	finally
    //   359	383	389	finally
    //   246	256	397	finally
    //   263	268	397	finally
    //   268	282	397	finally
    //   290	309	397	finally
    //   312	335	397	finally
    //   335	339	397	finally
    //   119	142	401	finally
    //   191	226	401	finally
    //   48	88	424	finally
    //   97	108	424	finally
    //   111	116	424	finally
    //   344	352	424	finally
    //   352	359	424	finally
    //   407	415	424	finally
    //   415	422	424	finally
    //   422	424	424	finally
    //   425	451	454	finally
  }
  
  public abstract Object takeState$kotlinx_coroutines_core();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DispatchedTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */