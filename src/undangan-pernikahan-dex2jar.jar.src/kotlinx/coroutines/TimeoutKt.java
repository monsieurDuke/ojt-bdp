package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(d1={"\000>\n\000\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\032\030\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\005H\000\032_\020\006\032\004\030\0010\007\"\004\b\000\020\b\"\b\b\001\020\t*\002H\b2\022\020\004\032\016\022\004\022\002H\b\022\004\022\002H\t0\n2'\020\013\032#\b\001\022\004\022\0020\r\022\n\022\b\022\004\022\002H\t0\016\022\006\022\004\030\0010\0070\f¢\006\002\b\017H\002ø\001\000¢\006\002\020\020\032U\020\021\032\002H\t\"\004\b\000\020\t2\006\020\022\032\0020\0032'\020\013\032#\b\001\022\004\022\0020\r\022\n\022\b\022\004\022\002H\t0\016\022\006\022\004\030\0010\0070\f¢\006\002\b\017H@ø\001\000\002\n\n\b\b\001\022\002\020\002 \001¢\006\002\020\023\032]\020\021\032\002H\t\"\004\b\000\020\t2\006\020\024\032\0020\0252'\020\013\032#\b\001\022\004\022\0020\r\022\n\022\b\022\004\022\002H\t0\016\022\006\022\004\030\0010\0070\f¢\006\002\b\017H@ø\001\000ø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\002 \001¢\006\004\b\026\020\023\032J\020\027\032\004\030\001H\t\"\004\b\000\020\t2\006\020\022\032\0020\0032'\020\013\032#\b\001\022\004\022\0020\r\022\n\022\b\022\004\022\002H\t0\016\022\006\022\004\030\0010\0070\f¢\006\002\b\017H@ø\001\000¢\006\002\020\023\032R\020\027\032\004\030\001H\t\"\004\b\000\020\t2\006\020\024\032\0020\0252'\020\013\032#\b\001\022\004\022\0020\r\022\n\022\b\022\004\022\002H\t0\016\022\006\022\004\030\0010\0070\f¢\006\002\b\017H@ø\001\000ø\001\000ø\001\001¢\006\004\b\030\020\023\002\013\n\002\b\031\n\005\b¡\0360\001¨\006\031"}, d2={"TimeoutCancellationException", "Lkotlinx/coroutines/TimeoutCancellationException;", "time", "", "coroutine", "Lkotlinx/coroutines/Job;", "setupTimeout", "", "U", "T", "Lkotlinx/coroutines/TimeoutCoroutine;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/TimeoutCoroutine;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "withTimeout", "timeMillis", "(JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "timeout", "Lkotlin/time/Duration;", "withTimeout-KLykuaI", "withTimeoutOrNull", "withTimeoutOrNull-KLykuaI", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class TimeoutKt
{
  public static final TimeoutCancellationException TimeoutCancellationException(long paramLong, Job paramJob)
  {
    return new TimeoutCancellationException("Timed out waiting for " + paramLong + " ms", paramJob);
  }
  
  private static final <U, T extends U> Object setupTimeout(TimeoutCoroutine<U, ? super T> paramTimeoutCoroutine, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    CoroutineContext localCoroutineContext = paramTimeoutCoroutine.uCont.getContext();
    JobKt.disposeOnCompletion((Job)paramTimeoutCoroutine, DelayKt.getDelay(localCoroutineContext).invokeOnTimeout(paramTimeoutCoroutine.time, (Runnable)paramTimeoutCoroutine, paramTimeoutCoroutine.getContext()));
    return UndispatchedKt.startUndispatchedOrReturnIgnoreTimeout((ScopeCoroutine)paramTimeoutCoroutine, paramTimeoutCoroutine, paramFunction2);
  }
  
  public static final <T> Object withTimeout(long paramLong, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    if (paramLong > 0L)
    {
      paramFunction2 = setupTimeout(new TimeoutCoroutine(paramLong, paramContinuation), paramFunction2);
      if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      return paramFunction2;
    }
    throw new TimeoutCancellationException("Timed out immediately");
  }
  
  public static final <T> Object withTimeout-KLykuaI(long paramLong, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    return withTimeout(DelayKt.toDelayMillis-LRDsOJo(paramLong), paramFunction2, paramContinuation);
  }
  
  public static final <T> Object withTimeoutOrNull(long paramLong, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    if ((paramContinuation instanceof withTimeoutOrNull.1))
    {
      localObject1 = (withTimeoutOrNull.1)paramContinuation;
      if ((((withTimeoutOrNull.1)localObject1).label & 0x80000000) != 0)
      {
        ((withTimeoutOrNull.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super T>)localObject1;
        break label52;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      long J$0;
      Object L$0;
      Object L$1;
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return TimeoutKt.withTimeoutOrNull(0L, null, (Continuation)this);
      }
    };
    label52:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      paramLong = paramContinuation.J$0;
      paramFunction2 = (Ref.ObjectRef)paramContinuation.L$1;
      paramContinuation = (Function2)paramContinuation.L$0;
      try
      {
        ResultKt.throwOnFailure(localObject1);
        paramFunction2 = (Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object>)localObject1;
      }
      catch (TimeoutCancellationException paramContinuation)
      {
        break label239;
      }
    case 0: 
      ResultKt.throwOnFailure(localObject1);
      if (paramLong <= 0L) {
        return null;
      }
      localObject1 = new Ref.ObjectRef();
    }
    try
    {
      paramContinuation.L$0 = paramFunction2;
      paramContinuation.L$1 = localObject1;
      paramContinuation.J$0 = paramLong;
      paramContinuation.label = 1;
      Continuation localContinuation = (Continuation)paramContinuation;
      TimeoutCoroutine localTimeoutCoroutine = new kotlinx/coroutines/TimeoutCoroutine;
      localTimeoutCoroutine.<init>(paramLong, localContinuation);
      ((Ref.ObjectRef)localObject1).element = localTimeoutCoroutine;
      paramFunction2 = setupTimeout(localTimeoutCoroutine, paramFunction2);
      if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended((Continuation)paramContinuation);
      }
      if (paramFunction2 == localObject2) {
        return localObject2;
      }
      return paramFunction2;
    }
    catch (TimeoutCancellationException paramContinuation)
    {
      paramFunction2 = (Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object>)localObject1;
    }
    label239:
    if (paramContinuation.coroutine == paramFunction2.element) {
      return null;
    }
    throw paramContinuation;
  }
  
  public static final <T> Object withTimeoutOrNull-KLykuaI(long paramLong, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    return withTimeoutOrNull(DelayKt.toDelayMillis-LRDsOJo(paramLong), paramFunction2, paramContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/TimeoutKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */