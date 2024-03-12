package kotlinx.coroutines.android;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.view.Choreographer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(d1={"\000@\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\000\032\021\020\b\032\0020\001H@ø\001\000¢\006\002\020\t\032\036\020\n\032\0020\0132\006\020\006\032\0020\0072\f\020\f\032\b\022\004\022\0020\0010\rH\002\032\026\020\016\032\0020\0132\f\020\f\032\b\022\004\022\0020\0010\rH\002\032\035\020\017\032\0020\003*\0020\0202\n\b\002\020\021\032\004\030\0010\022H\007¢\006\002\b\023\032\024\020\024\032\0020\020*\0020\0252\006\020\026\032\0020\027H\001\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\030\020\002\032\004\030\0010\0038\000X\004¢\006\b\n\000\022\004\b\004\020\005\"\020\020\006\032\004\030\0010\007X\016¢\006\002\n\000\002\004\n\002\b\031¨\006\030"}, d2={"MAX_DELAY", "", "Main", "Lkotlinx/coroutines/android/HandlerDispatcher;", "getMain$annotations", "()V", "choreographer", "Landroid/view/Choreographer;", "awaitFrame", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postFrameCallback", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "updateChoreographerAndPostFrameCallback", "asCoroutineDispatcher", "Landroid/os/Handler;", "name", "", "from", "asHandler", "Landroid/os/Looper;", "async", "", "kotlinx-coroutines-android"}, k=2, mv={1, 6, 0}, xi=48)
public final class HandlerDispatcherKt
{
  private static final long MAX_DELAY = 4611686018427387903L;
  public static final HandlerDispatcher Main;
  private static volatile Choreographer choreographer;
  
  /* Error */
  static
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: getstatic 56	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   5: astore_0
    //   6: new 58	kotlinx/coroutines/android/HandlerContext
    //   9: astore_0
    //   10: aload_0
    //   11: invokestatic 64	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   14: iconst_1
    //   15: invokestatic 67	kotlinx/coroutines/android/HandlerDispatcherKt:asHandler	(Landroid/os/Looper;Z)Landroid/os/Handler;
    //   18: aconst_null
    //   19: iconst_2
    //   20: aconst_null
    //   21: invokespecial 71	kotlinx/coroutines/android/HandlerContext:<init>	(Landroid/os/Handler;Ljava/lang/String;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
    //   24: aload_0
    //   25: invokestatic 75	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   28: astore_0
    //   29: goto +16 -> 45
    //   32: astore_0
    //   33: getstatic 56	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   36: astore_2
    //   37: aload_0
    //   38: invokestatic 81	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   41: invokestatic 75	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   44: astore_0
    //   45: aload_0
    //   46: invokestatic 85	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
    //   49: ifeq +8 -> 57
    //   52: aload_1
    //   53: astore_0
    //   54: goto +3 -> 57
    //   57: aload_0
    //   58: checkcast 87	kotlinx/coroutines/android/HandlerDispatcher
    //   61: putstatic 89	kotlinx/coroutines/android/HandlerDispatcherKt:Main	Lkotlinx/coroutines/android/HandlerDispatcher;
    //   64: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   5	24	0	localObject1	Object
    //   32	6	0	localThrowable	Throwable
    //   44	14	0	localObject2	Object
    //   1	52	1	localObject3	Object
    //   36	1	2	localCompanion	kotlin.Result.Companion
    // Exception table:
    //   from	to	target	type
    //   2	29	32	finally
  }
  
  public static final Handler asHandler(Looper paramLooper, boolean paramBoolean)
  {
    if ((paramBoolean) && (Build.VERSION.SDK_INT >= 16))
    {
      if (Build.VERSION.SDK_INT >= 28)
      {
        paramLooper = Handler.class.getDeclaredMethod("createAsync", new Class[] { Looper.class }).invoke(null, new Object[] { paramLooper });
        if (paramLooper != null) {
          return (Handler)paramLooper;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.os.Handler");
      }
      try
      {
        Constructor localConstructor = Handler.class.getDeclaredConstructor(new Class[] { Looper.class, Handler.Callback.class, Boolean.TYPE });
        return (Handler)localConstructor.newInstance(new Object[] { paramLooper, null, Boolean.valueOf(true) });
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        return new Handler(paramLooper);
      }
    }
    return new Handler(paramLooper);
  }
  
  public static final Object awaitFrame(Continuation<? super Long> paramContinuation)
  {
    Object localObject2 = choreographer;
    if (localObject2 != null)
    {
      localObject1 = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
      ((CancellableContinuationImpl)localObject1).initCancellability();
      access$postFrameCallback((Choreographer)localObject2, (CancellableContinuation)localObject1);
      localObject1 = ((CancellableContinuationImpl)localObject1).getResult();
      if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      return localObject1;
    }
    localObject2 = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    ((CancellableContinuationImpl)localObject2).initCancellability();
    Object localObject1 = (CancellableContinuation)localObject2;
    Dispatchers.getMain().dispatch((CoroutineContext)EmptyCoroutineContext.INSTANCE, (Runnable)new Runnable()
    {
      final CancellableContinuation $cont$inlined;
      
      public final void run()
      {
        HandlerDispatcherKt.access$updateChoreographerAndPostFrameCallback(this.$cont$inlined);
      }
    });
    localObject1 = ((CancellableContinuationImpl)localObject2).getResult();
    if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return localObject1;
  }
  
  public static final HandlerDispatcher from(Handler paramHandler)
  {
    return from$default(paramHandler, null, 1, null);
  }
  
  public static final HandlerDispatcher from(Handler paramHandler, String paramString)
  {
    return (HandlerDispatcher)new HandlerContext(paramHandler, paramString);
  }
  
  private static final void postFrameCallback(Choreographer paramChoreographer, CancellableContinuation<? super Long> paramCancellableContinuation)
  {
    paramChoreographer.postFrameCallback(new HandlerDispatcherKt..ExternalSyntheticLambda0(paramCancellableContinuation));
  }
  
  private static final void postFrameCallback$lambda-6(CancellableContinuation paramCancellableContinuation, long paramLong)
  {
    paramCancellableContinuation.resumeUndispatched((CoroutineDispatcher)Dispatchers.getMain(), Long.valueOf(paramLong));
  }
  
  private static final void updateChoreographerAndPostFrameCallback(CancellableContinuation<? super Long> paramCancellableContinuation)
  {
    Choreographer localChoreographer2 = choreographer;
    Choreographer localChoreographer1 = localChoreographer2;
    if (localChoreographer2 == null)
    {
      localChoreographer1 = Choreographer.getInstance();
      Intrinsics.checkNotNull(localChoreographer1);
      choreographer = localChoreographer1;
    }
    postFrameCallback(localChoreographer1, paramCancellableContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/android/HandlerDispatcherKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */