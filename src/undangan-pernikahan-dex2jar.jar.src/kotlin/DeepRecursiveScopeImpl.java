package kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000B\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\007\n\002\020\002\n\002\b\005\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\000\020\001*\004\b\001\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\0032\b\022\004\022\002H\0020\004BK\0229\020\005\0325\b\001\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\003\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0010\004\022\006\022\004\030\0010\0070\006¢\006\002\b\b\022\006\020\t\032\0028\000ø\001\000¢\006\002\020\nJ\031\020\025\032\0028\0012\006\020\t\032\0028\000H@ø\001\000¢\006\002\020\026Jc\020\027\032\n\022\006\022\004\030\0010\0070\00429\020\030\0325\b\001\022\f\022\n\022\002\b\003\022\002\b\0030\003\022\006\022\004\030\0010\007\022\f\022\n\022\006\022\004\030\0010\0070\004\022\006\022\004\030\0010\0070\006¢\006\002\b\b2\016\020\013\032\n\022\006\022\004\030\0010\0070\004H\002ø\001\000¢\006\002\020\031J\036\020\032\032\0020\0332\f\020\022\032\b\022\004\022\0028\0010\023H\026ø\001\000¢\006\002\020\034J\013\020\035\032\0028\001¢\006\002\020\036J5\020\025\032\002H\037\"\004\b\002\020 \"\004\b\003\020\037*\016\022\004\022\002H \022\004\022\002H\0370!2\006\020\t\032\002H H@ø\001\000¢\006\002\020\"R\030\020\013\032\f\022\006\022\004\030\0010\007\030\0010\004X\016¢\006\002\n\000R\024\020\f\032\0020\r8VX\004¢\006\006\032\004\b\016\020\017RF\020\020\0325\b\001\022\f\022\n\022\002\b\003\022\002\b\0030\003\022\006\022\004\030\0010\007\022\f\022\n\022\006\022\004\030\0010\0070\004\022\006\022\004\030\0010\0070\006¢\006\002\b\bX\016ø\001\000¢\006\004\n\002\020\021R\036\020\022\032\n\022\006\022\004\030\0010\0070\023X\016ø\001\000ø\001\001¢\006\004\n\002\020\024R\020\020\t\032\004\030\0010\007X\016¢\006\002\n\000\002\b\n\002\b\031\n\002\b!¨\006#"}, d2={"Lkotlin/DeepRecursiveScopeImpl;", "T", "R", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function3;", "", "Lkotlin/ExtensionFunctionType;", "value", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;)V", "cont", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "function", "Lkotlin/jvm/functions/Function3;", "result", "Lkotlin/Result;", "Ljava/lang/Object;", "callRecursive", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "crossFunctionCompletion", "currentFunction", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "resumeWith", "", "(Ljava/lang/Object;)V", "runCallLoop", "()Ljava/lang/Object;", "S", "U", "Lkotlin/DeepRecursiveFunction;", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class DeepRecursiveScopeImpl<T, R>
  extends DeepRecursiveScope<T, R>
  implements Continuation<R>
{
  private Continuation<Object> cont;
  private Function3<? super DeepRecursiveScope<?, ?>, Object, ? super Continuation<Object>, ? extends Object> function;
  private Object result;
  private Object value;
  
  public DeepRecursiveScopeImpl(Function3<? super DeepRecursiveScope<T, R>, ? super T, ? super Continuation<? super R>, ? extends Object> paramFunction3, T paramT)
  {
    super(null);
    this.function = paramFunction3;
    this.value = paramT;
    Intrinsics.checkNotNull(this, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    this.cont = ((Continuation)this);
    this.result = DeepRecursiveKt.access$getUNDEFINED_RESULT$p();
  }
  
  private final Continuation<Object> crossFunctionCompletion(final Function3<? super DeepRecursiveScope<?, ?>, Object, ? super Continuation<Object>, ? extends Object> paramFunction3, final Continuation<Object> paramContinuation)
  {
    (Continuation)new Continuation()
    {
      final CoroutineContext $context;
      
      public CoroutineContext getContext()
      {
        return this.$context;
      }
      
      public void resumeWith(Object paramAnonymousObject)
      {
        DeepRecursiveScopeImpl.access$setFunction$p(jdField_this, paramFunction3);
        DeepRecursiveScopeImpl.access$setCont$p(jdField_this, paramContinuation);
        DeepRecursiveScopeImpl.access$setResult$p(jdField_this, paramAnonymousObject);
      }
    };
  }
  
  public Object callRecursive(T paramT, Continuation<? super R> paramContinuation)
  {
    Intrinsics.checkNotNull(paramContinuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    this.cont = paramContinuation;
    this.value = paramT;
    paramT = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramT;
  }
  
  public <U, S> Object callRecursive(DeepRecursiveFunction<U, S> paramDeepRecursiveFunction, U paramU, Continuation<? super S> paramContinuation)
  {
    paramDeepRecursiveFunction = paramDeepRecursiveFunction.getBlock$kotlin_stdlib();
    Intrinsics.checkNotNull(paramDeepRecursiveFunction, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.coroutines.SuspendFunction2<kotlin.DeepRecursiveScope<*, *>, kotlin.Any?, kotlin.Any?>{ kotlin.DeepRecursiveKt.DeepRecursiveFunctionBlock }");
    DeepRecursiveScopeImpl localDeepRecursiveScopeImpl = (DeepRecursiveScopeImpl)this;
    Function3 localFunction3 = localDeepRecursiveScopeImpl.function;
    if (paramDeepRecursiveFunction != localFunction3)
    {
      localDeepRecursiveScopeImpl.function = paramDeepRecursiveFunction;
      Intrinsics.checkNotNull(paramContinuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
      localDeepRecursiveScopeImpl.cont = localDeepRecursiveScopeImpl.crossFunctionCompletion(localFunction3, paramContinuation);
    }
    else
    {
      Intrinsics.checkNotNull(paramContinuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
      localDeepRecursiveScopeImpl.cont = paramContinuation;
    }
    localDeepRecursiveScopeImpl.value = paramU;
    paramDeepRecursiveFunction = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    if (paramDeepRecursiveFunction == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramDeepRecursiveFunction;
  }
  
  public CoroutineContext getContext()
  {
    return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
  }
  
  public void resumeWith(Object paramObject)
  {
    this.cont = null;
    this.result = paramObject;
  }
  
  /* Error */
  public final R runCallLoop()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 86	kotlin/DeepRecursiveScopeImpl:result	Ljava/lang/Object;
    //   4: astore_2
    //   5: aload_0
    //   6: getfield 79	kotlin/DeepRecursiveScopeImpl:cont	Lkotlin/coroutines/Continuation;
    //   9: astore_1
    //   10: aload_1
    //   11: ifnonnull +9 -> 20
    //   14: aload_2
    //   15: invokestatic 135	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   18: aload_2
    //   19: areturn
    //   20: invokestatic 84	kotlin/DeepRecursiveKt:access$getUNDEFINED_RESULT$p	()Ljava/lang/Object;
    //   23: aload_2
    //   24: invokestatic 141	kotlin/Result:equals-impl0	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   27: ifeq +81 -> 108
    //   30: aload_0
    //   31: getfield 70	kotlin/DeepRecursiveScopeImpl:function	Lkotlin/jvm/functions/Function3;
    //   34: astore_2
    //   35: aload_0
    //   36: getfield 72	kotlin/DeepRecursiveScopeImpl:value	Ljava/lang/Object;
    //   39: astore_3
    //   40: aload_2
    //   41: ldc -113
    //   43: invokestatic 77	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   46: aload_2
    //   47: iconst_3
    //   48: invokestatic 149	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   51: checkcast 151	kotlin/jvm/functions/Function3
    //   54: aload_0
    //   55: aload_3
    //   56: aload_1
    //   57: invokeinterface 155 4 0
    //   62: astore_3
    //   63: aload_3
    //   64: invokestatic 112	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   67: if_acmpeq -67 -> 0
    //   70: getstatic 159	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   73: astore_2
    //   74: aload_1
    //   75: aload_3
    //   76: invokestatic 163	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   79: invokeinterface 165 2 0
    //   84: goto -84 -> 0
    //   87: astore_2
    //   88: getstatic 159	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   91: astore_3
    //   92: aload_1
    //   93: aload_2
    //   94: invokestatic 169	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   97: invokestatic 163	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   100: invokeinterface 165 2 0
    //   105: goto -105 -> 0
    //   108: aload_0
    //   109: invokestatic 84	kotlin/DeepRecursiveKt:access$getUNDEFINED_RESULT$p	()Ljava/lang/Object;
    //   112: putfield 86	kotlin/DeepRecursiveScopeImpl:result	Ljava/lang/Object;
    //   115: aload_1
    //   116: aload_2
    //   117: invokeinterface 165 2 0
    //   122: goto -122 -> 0
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	125	0	this	DeepRecursiveScopeImpl
    //   9	107	1	localContinuation	Continuation
    //   4	70	2	localObject1	Object
    //   87	30	2	localThrowable	Throwable
    //   39	53	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   30	63	87	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/DeepRecursiveScopeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */