package kotlin;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000:\n\000\n\002\020\000\n\000\n\002\020\003\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\017\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\001\032.\020\004\032\b\022\004\022\002H\0060\005\"\004\b\000\020\0062\f\020\007\032\b\022\004\022\002H\0060\bH\bø\001\000ø\001\001¢\006\002\020\t\032\001\020\n\032\002H\006\"\004\b\000\020\006\"\004\b\001\020\013*\b\022\004\022\002H\0130\0052!\020\f\032\035\022\023\022\021H\013¢\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\002H\0060\r2!\020\021\032\035\022\023\022\0210\003¢\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH\bø\001\000ø\001\001\002\024\n\b\b\001\022\002\020\001 \000\n\b\b\001\022\002\020\002 \000¢\006\002\020\022\0323\020\023\032\002H\006\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052\006\020\024\032\002H\006H\bø\001\001¢\006\002\020\025\032^\020\026\032\002H\006\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052!\020\021\032\035\022\023\022\0210\003¢\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\002\020\027\032!\020\030\032\002H\013\"\004\b\000\020\013*\b\022\004\022\002H\0130\005H\bø\001\001¢\006\002\020\031\032`\020\032\032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\004\b\001\020\013*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\021H\013¢\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\002H\0060\rH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\002\020\027\032S\020\034\032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\004\b\001\020\013*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\021H\013¢\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\002H\0060\rH\bø\001\000ø\001\001¢\006\002\020\027\032Z\020\021\032\b\022\004\022\002H\0130\005\"\004\b\000\020\013*\b\022\004\022\002H\0130\0052!\020\035\032\035\022\023\022\0210\003¢\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\0020\0360\rH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\002\020\027\032Z\020\f\032\b\022\004\022\002H\0130\005\"\004\b\000\020\013*\b\022\004\022\002H\0130\0052!\020\035\032\035\022\023\022\021H\013¢\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\0020\0360\rH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\002\020\027\032d\020\037\032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\0210\003¢\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\002\020\027\032W\020 \032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\0210\003¢\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH\bø\001\000ø\001\001¢\006\002\020\027\032C\020\004\032\b\022\004\022\002H\0060\005\"\004\b\000\020\013\"\004\b\001\020\006*\002H\0132\027\020\007\032\023\022\004\022\002H\013\022\004\022\002H\0060\r¢\006\002\b!H\bø\001\000ø\001\001¢\006\002\020\027\032\030\020\"\032\0020\036*\006\022\002\b\0030\005H\001ø\001\001¢\006\002\020#\002\013\n\005\b20\001\n\002\b\031¨\006$"}, d2={"createFailure", "", "exception", "", "runCatching", "Lkotlin/Result;", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "fold", "T", "onSuccess", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "onFailure", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrDefault", "defaultValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrThrow", "(Ljava/lang/Object;)Ljava/lang/Object;", "map", "transform", "mapCatching", "action", "", "recover", "recoverCatching", "Lkotlin/ExtensionFunctionType;", "throwOnFailure", "(Ljava/lang/Object;)V", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ResultKt
{
  public static final Object createFailure(Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "exception");
    return new Result.Failure(paramThrowable);
  }
  
  private static final <R, T> R fold(Object paramObject, Function1<? super T, ? extends R> paramFunction1, Function1<? super Throwable, ? extends R> paramFunction11)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "onSuccess");
    Intrinsics.checkNotNullParameter(paramFunction11, "onFailure");
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable == null) {
      paramObject = paramFunction1.invoke(paramObject);
    } else {
      paramObject = paramFunction11.invoke(localThrowable);
    }
    return (R)paramObject;
  }
  
  private static final <R, T extends R> R getOrDefault(Object paramObject, R paramR)
  {
    if (Result.isFailure-impl(paramObject)) {
      return paramR;
    }
    return (R)paramObject;
  }
  
  private static final <R, T extends R> R getOrElse(Object paramObject, Function1<? super Throwable, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "onFailure");
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null) {
      paramObject = paramFunction1.invoke(localThrowable);
    }
    return (R)paramObject;
  }
  
  private static final <T> T getOrThrow(Object paramObject)
  {
    throwOnFailure(paramObject);
    return (T)paramObject;
  }
  
  private static final <R, T> Object map(Object paramObject, Function1<? super T, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    if (Result.isSuccess-impl(paramObject))
    {
      Result.Companion localCompanion = Result.Companion;
      paramObject = Result.constructor-impl(paramFunction1.invoke(paramObject));
    }
    else
    {
      paramObject = Result.constructor-impl(paramObject);
    }
    return paramObject;
  }
  
  /* Error */
  private static final <R, T> Object mapCatching(Object paramObject, Function1<? super T, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 91
    //   3: invokestatic 58	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: invokestatic 94	kotlin/Result:isSuccess-impl	(Ljava/lang/Object;)Z
    //   10: ifeq +37 -> 47
    //   13: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   16: astore_2
    //   17: aload_1
    //   18: aload_0
    //   19: invokeinterface 79 2 0
    //   24: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   27: astore_0
    //   28: goto +24 -> 52
    //   31: astore_0
    //   32: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   35: astore_1
    //   36: aload_0
    //   37: invokestatic 103	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   40: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   43: astore_0
    //   44: goto +8 -> 52
    //   47: aload_0
    //   48: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   51: astore_0
    //   52: aload_0
    //   53: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	paramObject	Object
    //   0	54	1	paramFunction1	Function1<? super T, ? extends R>
    //   16	1	2	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   13	28	31	finally
  }
  
  private static final <T> Object onFailure(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null) {
      paramFunction1.invoke(localThrowable);
    }
    return paramObject;
  }
  
  private static final <T> Object onSuccess(Object paramObject, Function1<? super T, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    if (Result.isSuccess-impl(paramObject)) {
      paramFunction1.invoke(paramObject);
    }
    return paramObject;
  }
  
  private static final <R, T extends R> Object recover(Object paramObject, Function1<? super Throwable, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null)
    {
      paramObject = Result.Companion;
      paramObject = Result.constructor-impl(paramFunction1.invoke(localThrowable));
    }
    return paramObject;
  }
  
  /* Error */
  private static final <R, T extends R> Object recoverCatching(Object paramObject, Function1<? super Throwable, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 91
    //   3: invokestatic 58	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: invokestatic 74	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   10: astore_2
    //   11: aload_2
    //   12: ifnonnull +6 -> 18
    //   15: goto +34 -> 49
    //   18: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   21: astore_0
    //   22: aload_1
    //   23: aload_2
    //   24: invokeinterface 79 2 0
    //   29: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   32: astore_0
    //   33: goto +16 -> 49
    //   36: astore_1
    //   37: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   40: astore_0
    //   41: aload_1
    //   42: invokestatic 103	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   45: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   48: astore_0
    //   49: aload_0
    //   50: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	51	0	paramObject	Object
    //   0	51	1	paramFunction1	Function1<? super Throwable, ? extends R>
    //   10	14	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   18	33	36	finally
  }
  
  /* Error */
  private static final <T, R> Object runCatching(T paramT, Function1<? super T, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 109
    //   3: invokestatic 58	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   9: astore_2
    //   10: aload_1
    //   11: aload_0
    //   12: invokeinterface 79 2 0
    //   17: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   20: astore_0
    //   21: goto +16 -> 37
    //   24: astore_0
    //   25: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   28: astore_1
    //   29: aload_0
    //   30: invokestatic 103	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   33: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   36: astore_0
    //   37: aload_0
    //   38: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	39	0	paramT	T
    //   0	39	1	paramFunction1	Function1<? super T, ? extends R>
    //   9	1	2	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   6	21	24	finally
  }
  
  /* Error */
  private static final <R> Object runCatching(kotlin.jvm.functions.Function0<? extends R> paramFunction0)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 109
    //   3: invokestatic 58	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   9: astore_1
    //   10: aload_0
    //   11: invokeinterface 115 1 0
    //   16: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   19: astore_0
    //   20: goto +16 -> 36
    //   23: astore_0
    //   24: getstatic 98	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   27: astore_1
    //   28: aload_0
    //   29: invokestatic 103	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   32: invokestatic 101	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   35: astore_0
    //   36: aload_0
    //   37: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	paramFunction0	kotlin.jvm.functions.Function0<? extends R>
    //   9	19	1	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   6	20	23	finally
  }
  
  public static final void throwOnFailure(Object paramObject)
  {
    if (!(paramObject instanceof Result.Failure)) {
      return;
    }
    throw ((Result.Failure)paramObject).exception;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ResultKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */