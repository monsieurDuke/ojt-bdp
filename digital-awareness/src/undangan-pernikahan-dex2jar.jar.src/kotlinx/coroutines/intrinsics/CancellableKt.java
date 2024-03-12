package kotlinx.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000:\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\003\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\032\034\020\000\032\0020\0012\n\020\002\032\006\022\002\b\0030\0032\006\020\004\032\0020\005H\002\032#\020\006\032\0020\0012\n\020\002\032\006\022\002\b\0030\0032\f\020\007\032\b\022\004\022\0020\0010\bH\b\032\036\020\t\032\0020\001*\b\022\004\022\0020\0010\0032\n\020\n\032\006\022\002\b\0030\003H\000\032>\020\t\032\0020\001\"\004\b\000\020\013*\030\b\001\022\n\022\b\022\004\022\002H\0130\003\022\006\022\004\030\0010\r0\f2\f\020\002\032\b\022\004\022\002H\0130\003H\007ø\001\000¢\006\002\020\016\032y\020\t\032\0020\001\"\004\b\000\020\017\"\004\b\001\020\013*\036\b\001\022\004\022\002H\017\022\n\022\b\022\004\022\002H\0130\003\022\006\022\004\030\0010\r0\0202\006\020\021\032\002H\0172\f\020\002\032\b\022\004\022\002H\0130\0032%\b\002\020\022\032\037\022\023\022\0210\005¢\006\f\b\023\022\b\b\024\022\004\b\b(\025\022\004\022\0020\001\030\0010\fH\000ø\001\000¢\006\002\020\026\002\004\n\002\b\031¨\006\027"}, d2={"dispatcherFailure", "", "completion", "Lkotlin/coroutines/Continuation;", "e", "", "runSafely", "block", "Lkotlin/Function0;", "startCoroutineCancellable", "fatalCompletion", "T", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "receiver", "onCancellation", "Lkotlin/ParameterName;", "name", "cause", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;Lkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CancellableKt
{
  private static final void dispatcherFailure(Continuation<?> paramContinuation, Throwable paramThrowable)
  {
    Result.Companion localCompanion = Result.Companion;
    paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramThrowable)));
    throw paramThrowable;
  }
  
  /* Error */
  private static final void runSafely(Continuation<?> paramContinuation, kotlin.jvm.functions.Function0<kotlin.Unit> paramFunction0)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface 72 1 0
    //   6: pop
    //   7: goto +9 -> 16
    //   10: astore_1
    //   11: aload_0
    //   12: aload_1
    //   13: invokestatic 74	kotlinx/coroutines/intrinsics/CancellableKt:dispatcherFailure	(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;)V
    //   16: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	17	0	paramContinuation	Continuation<?>
    //   0	17	1	paramFunction0	kotlin.jvm.functions.Function0<kotlin.Unit>
    // Exception table:
    //   from	to	target	type
    //   0	7	10	finally
  }
  
  /* Error */
  public static final void startCoroutineCancellable(Continuation<? super kotlin.Unit> paramContinuation, Continuation<?> paramContinuation1)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 82	kotlin/coroutines/intrinsics/IntrinsicsKt:intercepted	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   4: astore_0
    //   5: getstatic 46	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   8: astore_2
    //   9: aload_0
    //   10: getstatic 88	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   13: invokestatic 56	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   16: aconst_null
    //   17: iconst_2
    //   18: aconst_null
    //   19: invokestatic 94	kotlinx/coroutines/internal/DispatchedContinuationKt:resumeCancellableWith$default	(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)V
    //   22: goto +9 -> 31
    //   25: astore_0
    //   26: aload_1
    //   27: aload_0
    //   28: invokestatic 74	kotlinx/coroutines/intrinsics/CancellableKt:dispatcherFailure	(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;)V
    //   31: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	32	0	paramContinuation	Continuation<? super kotlin.Unit>
    //   0	32	1	paramContinuation1	Continuation<?>
    //   8	1	2	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   0	22	25	finally
  }
  
  /* Error */
  public static final <T> void startCoroutineCancellable(kotlin.jvm.functions.Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 99	kotlin/coroutines/intrinsics/IntrinsicsKt:createCoroutineUnintercepted	(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   5: invokestatic 82	kotlin/coroutines/intrinsics/IntrinsicsKt:intercepted	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   8: astore_0
    //   9: getstatic 46	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   12: astore_2
    //   13: aload_0
    //   14: getstatic 88	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   17: invokestatic 56	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   20: aconst_null
    //   21: iconst_2
    //   22: aconst_null
    //   23: invokestatic 94	kotlinx/coroutines/internal/DispatchedContinuationKt:resumeCancellableWith$default	(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)V
    //   26: goto +9 -> 35
    //   29: astore_0
    //   30: aload_1
    //   31: aload_0
    //   32: invokestatic 74	kotlinx/coroutines/intrinsics/CancellableKt:dispatcherFailure	(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;)V
    //   35: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	36	0	paramFunction1	kotlin.jvm.functions.Function1<? super Continuation<? super T>, ? extends Object>
    //   0	36	1	paramContinuation	Continuation<? super T>
    //   12	1	2	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   0	26	29	finally
  }
  
  /* Error */
  public static final <R, T> void startCoroutineCancellable(kotlin.jvm.functions.Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, R paramR, Continuation<? super T> paramContinuation, kotlin.jvm.functions.Function1<? super Throwable, kotlin.Unit> paramFunction1)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokestatic 103	kotlin/coroutines/intrinsics/IntrinsicsKt:createCoroutineUnintercepted	(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   6: invokestatic 82	kotlin/coroutines/intrinsics/IntrinsicsKt:intercepted	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   9: astore_0
    //   10: getstatic 46	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   13: astore_1
    //   14: aload_0
    //   15: getstatic 88	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   18: invokestatic 56	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   21: aload_3
    //   22: invokestatic 107	kotlinx/coroutines/internal/DispatchedContinuationKt:resumeCancellableWith	(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V
    //   25: goto +9 -> 34
    //   28: astore_0
    //   29: aload_2
    //   30: aload_0
    //   31: invokestatic 74	kotlinx/coroutines/intrinsics/CancellableKt:dispatcherFailure	(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;)V
    //   34: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	35	0	paramFunction2	kotlin.jvm.functions.Function2<? super R, ? super Continuation<? super T>, ? extends Object>
    //   0	35	1	paramR	R
    //   0	35	2	paramContinuation	Continuation<? super T>
    //   0	35	3	paramFunction1	kotlin.jvm.functions.Function1<? super Throwable, kotlin.Unit>
    // Exception table:
    //   from	to	target	type
    //   0	25	28	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/intrinsics/CancellableKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */