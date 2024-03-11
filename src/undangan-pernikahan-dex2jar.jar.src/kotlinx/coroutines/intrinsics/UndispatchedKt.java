package kotlinx.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1={"\000@\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\003\n\002\020\013\n\000\n\002\030\002\n\000\0329\020\000\032\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\0042\032\020\005\032\026\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\006H\b\032>\020\b\032\0020\001\"\004\b\000\020\002*\030\b\001\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0062\f\020\003\032\b\022\004\022\002H\0020\004H\000ø\001\000¢\006\002\020\t\032R\020\b\032\0020\001\"\004\b\000\020\n\"\004\b\001\020\002*\036\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0132\006\020\f\032\002H\n2\f\020\003\032\b\022\004\022\002H\0020\004H\000ø\001\000¢\006\002\020\r\032>\020\016\032\0020\001\"\004\b\000\020\002*\030\b\001\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0062\f\020\003\032\b\022\004\022\002H\0020\004H\000ø\001\000¢\006\002\020\t\032R\020\016\032\0020\001\"\004\b\000\020\n\"\004\b\001\020\002*\036\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0132\006\020\f\032\002H\n2\f\020\003\032\b\022\004\022\002H\0020\004H\000ø\001\000¢\006\002\020\r\032Y\020\017\032\004\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\n*\b\022\004\022\002H\0020\0202\006\020\f\032\002H\n2'\020\005\032#\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\013¢\006\002\b\021H\000ø\001\000¢\006\002\020\022\032Y\020\023\032\004\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\n*\b\022\004\022\002H\0020\0202\006\020\f\032\002H\n2'\020\005\032#\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\013¢\006\002\b\021H\000ø\001\000¢\006\002\020\022\032?\020\024\032\004\030\0010\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0202\022\020\025\032\016\022\004\022\0020\026\022\004\022\0020\0270\0062\016\020\030\032\n\022\006\022\004\030\0010\0070\031H\b\002\004\n\002\b\031¨\006\032"}, d2={"startDirect", "", "T", "completion", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function1;", "", "startCoroutineUndispatched", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "startCoroutineUnintercepted", "startUndispatchedOrReturn", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/internal/ScopeCoroutine;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "startUndispatchedOrReturnIgnoreTimeout", "undispatchedResult", "shouldThrow", "", "", "startBlock", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class UndispatchedKt
{
  public static final <T> void startCoroutineUndispatched(Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    Continuation localContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    try
    {
      CoroutineContext localCoroutineContext = paramContinuation.getContext();
      paramContinuation = ThreadContextKt.updateThreadContext(localCoroutineContext, null);
      return;
    }
    finally
    {
      try
      {
        paramFunction1 = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction1, 1)).invoke(localContinuation);
        ThreadContextKt.restoreThreadContext(localCoroutineContext, paramContinuation);
        if (paramFunction1 != IntrinsicsKt.getCOROUTINE_SUSPENDED())
        {
          paramContinuation = Result.Companion;
          localContinuation.resumeWith(Result.constructor-impl(paramFunction1));
        }
      }
      finally
      {
        ThreadContextKt.restoreThreadContext(localCoroutineContext, paramContinuation);
      }
      paramContinuation = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramFunction1)));
    }
  }
  
  public static final <R, T> void startCoroutineUndispatched(Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, R paramR, Continuation<? super T> paramContinuation)
  {
    Continuation localContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    try
    {
      paramContinuation = paramContinuation.getContext();
      Object localObject = ThreadContextKt.updateThreadContext(paramContinuation, null);
      return;
    }
    finally
    {
      try
      {
        paramFunction2 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction2, 2)).invoke(paramR, localContinuation);
        ThreadContextKt.restoreThreadContext(paramContinuation, localObject);
        if (paramFunction2 != IntrinsicsKt.getCOROUTINE_SUSPENDED())
        {
          paramR = Result.Companion;
          localContinuation.resumeWith(Result.constructor-impl(paramFunction2));
        }
      }
      finally
      {
        ThreadContextKt.restoreThreadContext(paramContinuation, localObject);
      }
      paramFunction2 = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramR)));
    }
  }
  
  /* Error */
  public static final <T> void startCoroutineUnintercepted(Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 47	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineCreated	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   4: astore_1
    //   5: aload_0
    //   6: iconst_1
    //   7: invokestatic 65	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   10: checkcast 67	kotlin/jvm/functions/Function1
    //   13: aload_1
    //   14: invokeinterface 71 2 0
    //   19: astore_0
    //   20: aload_0
    //   21: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   24: if_acmpeq +17 -> 41
    //   27: getstatic 87	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   30: astore_2
    //   31: aload_1
    //   32: aload_0
    //   33: invokestatic 90	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   36: invokeinterface 94 2 0
    //   41: goto +21 -> 62
    //   44: astore_0
    //   45: getstatic 87	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   48: astore_2
    //   49: aload_1
    //   50: aload_0
    //   51: invokestatic 100	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   54: invokestatic 90	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   57: invokeinterface 94 2 0
    //   62: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	63	0	paramFunction1	Function1<? super Continuation<? super T>, ? extends Object>
    //   0	63	1	paramContinuation	Continuation<? super T>
    //   30	19	2	localCompanion	kotlin.Result.Companion
    // Exception table:
    //   from	to	target	type
    //   5	20	44	finally
  }
  
  /* Error */
  public static final <R, T> void startCoroutineUnintercepted(Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, R paramR, Continuation<? super T> paramContinuation)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokestatic 47	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineCreated	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   4: astore_2
    //   5: aload_0
    //   6: iconst_2
    //   7: invokestatic 65	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   10: checkcast 105	kotlin/jvm/functions/Function2
    //   13: aload_1
    //   14: aload_2
    //   15: invokeinterface 108 3 0
    //   20: astore_1
    //   21: aload_1
    //   22: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   25: if_acmpeq +17 -> 42
    //   28: getstatic 87	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   31: astore_0
    //   32: aload_2
    //   33: aload_1
    //   34: invokestatic 90	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   37: invokeinterface 94 2 0
    //   42: goto +21 -> 63
    //   45: astore_0
    //   46: getstatic 87	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   49: astore_1
    //   50: aload_2
    //   51: aload_0
    //   52: invokestatic 100	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   55: invokestatic 90	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   58: invokeinterface 94 2 0
    //   63: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	paramFunction2	Function2<? super R, ? super Continuation<? super T>, ? extends Object>
    //   0	64	1	paramR	R
    //   0	64	2	paramContinuation	Continuation<? super T>
    // Exception table:
    //   from	to	target	type
    //   5	21	45	finally
  }
  
  private static final <T> void startDirect(Continuation<? super T> paramContinuation, Function1<? super Continuation<? super T>, ? extends Object> paramFunction1)
  {
    paramContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    try
    {
      localObject = paramFunction1.invoke(paramContinuation);
      if (localObject != IntrinsicsKt.getCOROUTINE_SUSPENDED())
      {
        paramFunction1 = Result.Companion;
        paramContinuation.resumeWith(Result.constructor-impl(localObject));
      }
      return;
    }
    finally
    {
      Object localObject = Result.Companion;
      paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramFunction1)));
    }
  }
  
  /* Error */
  public static final <T, R> Object startUndispatchedOrReturn(kotlinx.coroutines.internal.ScopeCoroutine<? super T> paramScopeCoroutine, R paramR, Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    // Byte code:
    //   0: aload_2
    //   1: iconst_2
    //   2: invokestatic 65	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   5: checkcast 105	kotlin/jvm/functions/Function2
    //   8: aload_1
    //   9: aload_0
    //   10: checkcast 49	kotlin/coroutines/Continuation
    //   13: invokeinterface 108 3 0
    //   18: astore_1
    //   19: goto +16 -> 35
    //   22: astore_1
    //   23: new 113	kotlinx/coroutines/CompletedExceptionally
    //   26: dup
    //   27: aload_1
    //   28: iconst_0
    //   29: iconst_2
    //   30: aconst_null
    //   31: invokespecial 117	kotlinx/coroutines/CompletedExceptionally:<init>	(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
    //   34: astore_1
    //   35: aload_1
    //   36: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   39: if_acmpne +10 -> 49
    //   42: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   45: astore_0
    //   46: goto +87 -> 133
    //   49: aload_0
    //   50: aload_1
    //   51: invokevirtual 122	kotlinx/coroutines/internal/ScopeCoroutine:makeCompletingOnce$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   54: astore_1
    //   55: aload_1
    //   56: getstatic 128	kotlinx/coroutines/JobSupportKt:COMPLETING_WAITING_CHILDREN	Lkotlinx/coroutines/internal/Symbol;
    //   59: if_acmpne +10 -> 69
    //   62: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   65: astore_0
    //   66: goto +67 -> 133
    //   69: aload_1
    //   70: instanceof 113
    //   73: ifeq +55 -> 128
    //   76: aload_1
    //   77: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   80: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   83: astore_2
    //   84: aload_1
    //   85: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   88: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   91: astore_1
    //   92: aload_0
    //   93: getfield 135	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   96: astore_2
    //   97: aload_1
    //   98: astore_0
    //   99: invokestatic 141	kotlinx/coroutines/DebugKt:getRECOVER_STACK_TRACES	()Z
    //   102: ifeq +24 -> 126
    //   105: aload_2
    //   106: instanceof 143
    //   109: ifne +8 -> 117
    //   112: aload_1
    //   113: astore_0
    //   114: goto +12 -> 126
    //   117: aload_1
    //   118: aload_2
    //   119: checkcast 143	kotlin/coroutines/jvm/internal/CoroutineStackFrame
    //   122: invokestatic 149	kotlinx/coroutines/internal/StackTraceRecoveryKt:access$recoverFromStackFrame	(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
    //   125: astore_0
    //   126: aload_0
    //   127: athrow
    //   128: aload_1
    //   129: invokestatic 152	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   132: astore_0
    //   133: aload_0
    //   134: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	135	0	paramScopeCoroutine	kotlinx.coroutines.internal.ScopeCoroutine<? super T>
    //   0	135	1	paramR	R
    //   0	135	2	paramFunction2	Function2<? super R, ? super Continuation<? super T>, ? extends Object>
    // Exception table:
    //   from	to	target	type
    //   0	19	22	finally
  }
  
  /* Error */
  public static final <T, R> Object startUndispatchedOrReturnIgnoreTimeout(kotlinx.coroutines.internal.ScopeCoroutine<? super T> paramScopeCoroutine, R paramR, Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: aload_2
    //   3: iconst_2
    //   4: invokestatic 65	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   7: checkcast 105	kotlin/jvm/functions/Function2
    //   10: aload_1
    //   11: aload_0
    //   12: checkcast 49	kotlin/coroutines/Continuation
    //   15: invokeinterface 108 3 0
    //   20: astore_1
    //   21: goto +16 -> 37
    //   24: astore_1
    //   25: new 113	kotlinx/coroutines/CompletedExceptionally
    //   28: dup
    //   29: aload_1
    //   30: iconst_0
    //   31: iconst_2
    //   32: aconst_null
    //   33: invokespecial 117	kotlinx/coroutines/CompletedExceptionally:<init>	(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
    //   36: astore_1
    //   37: aload_1
    //   38: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   41: if_acmpne +10 -> 51
    //   44: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   47: astore_0
    //   48: goto +170 -> 218
    //   51: aload_0
    //   52: aload_1
    //   53: invokevirtual 122	kotlinx/coroutines/internal/ScopeCoroutine:makeCompletingOnce$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   56: astore_2
    //   57: aload_2
    //   58: getstatic 128	kotlinx/coroutines/JobSupportKt:COMPLETING_WAITING_CHILDREN	Lkotlinx/coroutines/internal/Symbol;
    //   61: if_acmpne +10 -> 71
    //   64: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   67: astore_0
    //   68: goto +150 -> 218
    //   71: aload_2
    //   72: instanceof 113
    //   75: ifeq +138 -> 213
    //   78: aload_2
    //   79: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   82: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   85: astore 4
    //   87: aload 4
    //   89: instanceof 154
    //   92: ifeq +15 -> 107
    //   95: aload 4
    //   97: checkcast 154	kotlinx/coroutines/TimeoutCancellationException
    //   100: getfield 158	kotlinx/coroutines/TimeoutCancellationException:coroutine	Lkotlinx/coroutines/Job;
    //   103: aload_0
    //   104: if_acmpeq +5 -> 109
    //   107: iconst_1
    //   108: istore_3
    //   109: iload_3
    //   110: ifeq +47 -> 157
    //   113: aload_2
    //   114: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   117: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   120: astore_1
    //   121: aload_0
    //   122: getfield 135	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   125: astore_2
    //   126: aload_1
    //   127: astore_0
    //   128: invokestatic 141	kotlinx/coroutines/DebugKt:getRECOVER_STACK_TRACES	()Z
    //   131: ifeq +24 -> 155
    //   134: aload_2
    //   135: instanceof 143
    //   138: ifne +8 -> 146
    //   141: aload_1
    //   142: astore_0
    //   143: goto +12 -> 155
    //   146: aload_1
    //   147: aload_2
    //   148: checkcast 143	kotlin/coroutines/jvm/internal/CoroutineStackFrame
    //   151: invokestatic 149	kotlinx/coroutines/internal/StackTraceRecoveryKt:access$recoverFromStackFrame	(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
    //   154: astore_0
    //   155: aload_0
    //   156: athrow
    //   157: aload_1
    //   158: instanceof 113
    //   161: ifeq +47 -> 208
    //   164: aload_1
    //   165: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   168: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   171: astore_1
    //   172: aload_0
    //   173: getfield 135	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   176: astore_2
    //   177: aload_1
    //   178: astore_0
    //   179: invokestatic 141	kotlinx/coroutines/DebugKt:getRECOVER_STACK_TRACES	()Z
    //   182: ifeq +24 -> 206
    //   185: aload_2
    //   186: instanceof 143
    //   189: ifne +8 -> 197
    //   192: aload_1
    //   193: astore_0
    //   194: goto +12 -> 206
    //   197: aload_1
    //   198: aload_2
    //   199: checkcast 143	kotlin/coroutines/jvm/internal/CoroutineStackFrame
    //   202: invokestatic 149	kotlinx/coroutines/internal/StackTraceRecoveryKt:access$recoverFromStackFrame	(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
    //   205: astore_0
    //   206: aload_0
    //   207: athrow
    //   208: aload_1
    //   209: astore_0
    //   210: goto +8 -> 218
    //   213: aload_2
    //   214: invokestatic 152	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   217: astore_0
    //   218: aload_0
    //   219: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	220	0	paramScopeCoroutine	kotlinx.coroutines.internal.ScopeCoroutine<? super T>
    //   0	220	1	paramR	R
    //   0	220	2	paramFunction2	Function2<? super R, ? super Continuation<? super T>, ? extends Object>
    //   1	109	3	i	int
    //   85	11	4	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   2	21	24	finally
  }
  
  /* Error */
  private static final <T> Object undispatchedResult(kotlinx.coroutines.internal.ScopeCoroutine<? super T> paramScopeCoroutine, Function1<? super Throwable, Boolean> paramFunction1, kotlin.jvm.functions.Function0<? extends Object> paramFunction0)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokeinterface 164 1 0
    //   6: astore_2
    //   7: goto +16 -> 23
    //   10: astore_2
    //   11: new 113	kotlinx/coroutines/CompletedExceptionally
    //   14: dup
    //   15: aload_2
    //   16: iconst_0
    //   17: iconst_2
    //   18: aconst_null
    //   19: invokespecial 117	kotlinx/coroutines/CompletedExceptionally:<init>	(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
    //   22: astore_2
    //   23: aload_2
    //   24: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   27: if_acmpne +7 -> 34
    //   30: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   33: areturn
    //   34: aload_0
    //   35: aload_2
    //   36: invokevirtual 122	kotlinx/coroutines/internal/ScopeCoroutine:makeCompletingOnce$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   39: astore_3
    //   40: aload_3
    //   41: getstatic 128	kotlinx/coroutines/JobSupportKt:COMPLETING_WAITING_CHILDREN	Lkotlinx/coroutines/internal/Symbol;
    //   44: if_acmpne +7 -> 51
    //   47: invokestatic 81	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   50: areturn
    //   51: aload_3
    //   52: instanceof 113
    //   55: ifeq +125 -> 180
    //   58: aload_1
    //   59: aload_3
    //   60: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   63: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   66: invokeinterface 71 2 0
    //   71: checkcast 166	java/lang/Boolean
    //   74: invokevirtual 169	java/lang/Boolean:booleanValue	()Z
    //   77: ifeq +47 -> 124
    //   80: aload_3
    //   81: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   84: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   87: astore_1
    //   88: aload_0
    //   89: getfield 135	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   92: astore_2
    //   93: aload_1
    //   94: astore_0
    //   95: invokestatic 141	kotlinx/coroutines/DebugKt:getRECOVER_STACK_TRACES	()Z
    //   98: ifeq +24 -> 122
    //   101: aload_2
    //   102: instanceof 143
    //   105: ifne +8 -> 113
    //   108: aload_1
    //   109: astore_0
    //   110: goto +12 -> 122
    //   113: aload_1
    //   114: aload_2
    //   115: checkcast 143	kotlin/coroutines/jvm/internal/CoroutineStackFrame
    //   118: invokestatic 149	kotlinx/coroutines/internal/StackTraceRecoveryKt:access$recoverFromStackFrame	(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
    //   121: astore_0
    //   122: aload_0
    //   123: athrow
    //   124: aload_2
    //   125: instanceof 113
    //   128: ifeq +47 -> 175
    //   131: aload_2
    //   132: checkcast 113	kotlinx/coroutines/CompletedExceptionally
    //   135: getfield 132	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   138: astore_1
    //   139: aload_0
    //   140: getfield 135	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   143: astore_2
    //   144: aload_1
    //   145: astore_0
    //   146: invokestatic 141	kotlinx/coroutines/DebugKt:getRECOVER_STACK_TRACES	()Z
    //   149: ifeq +24 -> 173
    //   152: aload_2
    //   153: instanceof 143
    //   156: ifne +8 -> 164
    //   159: aload_1
    //   160: astore_0
    //   161: goto +12 -> 173
    //   164: aload_1
    //   165: aload_2
    //   166: checkcast 143	kotlin/coroutines/jvm/internal/CoroutineStackFrame
    //   169: invokestatic 149	kotlinx/coroutines/internal/StackTraceRecoveryKt:access$recoverFromStackFrame	(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
    //   172: astore_0
    //   173: aload_0
    //   174: athrow
    //   175: aload_2
    //   176: astore_0
    //   177: goto +8 -> 185
    //   180: aload_3
    //   181: invokestatic 152	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   184: astore_0
    //   185: aload_0
    //   186: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	187	0	paramScopeCoroutine	kotlinx.coroutines.internal.ScopeCoroutine<? super T>
    //   0	187	1	paramFunction1	Function1<? super Throwable, Boolean>
    //   0	187	2	paramFunction0	kotlin.jvm.functions.Function0<? extends Object>
    //   39	142	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	7	10	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/intrinsics/UndispatchedKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */