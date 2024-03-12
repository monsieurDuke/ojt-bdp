package kotlinx.coroutines;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.Continuation<*>;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.ranges.RangesKt;
import kotlin.time.Duration;
import kotlin.time.Duration.Companion;

@Metadata(d1={"\000*\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\001\n\000\n\002\020\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\005\032\021\020\005\032\0020\006H@ø\001\000¢\006\002\020\007\032\031\020\000\032\0020\b2\006\020\t\032\0020\nH@ø\001\000¢\006\002\020\013\032!\020\000\032\0020\b2\006\020\f\032\0020\rH@ø\001\000ø\001\000ø\001\001¢\006\004\b\016\020\013\032\031\020\017\032\0020\n*\0020\rH\000ø\001\000ø\001\001¢\006\004\b\020\020\021\"\030\020\000\032\0020\001*\0020\0028@X\004¢\006\006\032\004\b\003\020\004\002\013\n\002\b\031\n\005\b¡\0360\001¨\006\022"}, d2={"delay", "Lkotlinx/coroutines/Delay;", "Lkotlin/coroutines/CoroutineContext;", "getDelay", "(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Delay;", "awaitCancellation", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "timeMillis", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "duration", "Lkotlin/time/Duration;", "delay-VtjQ1oo", "toDelayMillis", "toDelayMillis-LRDsOJo", "(J)J", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class DelayKt
{
  public static final Object awaitCancellation(Continuation<?> paramContinuation)
  {
    if ((paramContinuation instanceof awaitCancellation.1))
    {
      localObject1 = (awaitCancellation.1)paramContinuation;
      if ((((awaitCancellation.1)localObject1).label & 0x80000000) != 0)
      {
        ((awaitCancellation.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<?>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return DelayKt.awaitCancellation((Continuation)this);
      }
    };
    label47:
    Object localObject2 = paramContinuation.result;
    Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      ResultKt.throwOnFailure(localObject2);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject2);
      paramContinuation.label = 1;
      localObject2 = new CancellableContinuationImpl(IntrinsicsKt.intercepted((Continuation)paramContinuation), 1);
      ((CancellableContinuationImpl)localObject2).initCancellability();
      CancellableContinuation localCancellableContinuation = (CancellableContinuation)localObject2;
      localObject2 = ((CancellableContinuationImpl)localObject2).getResult();
      if (localObject2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended((Continuation)paramContinuation);
      }
      if (localObject2 == localObject1) {
        return localObject1;
      }
      break;
    }
    throw new KotlinNothingValueException();
  }
  
  public static final Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    if (paramLong <= 0L) {
      return Unit.INSTANCE;
    }
    Object localObject = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    ((CancellableContinuationImpl)localObject).initCancellability();
    CancellableContinuation localCancellableContinuation = (CancellableContinuation)localObject;
    if (paramLong < Long.MAX_VALUE) {
      getDelay(localCancellableContinuation.getContext()).scheduleResumeAfterDelay(paramLong, localCancellableContinuation);
    }
    localObject = ((CancellableContinuationImpl)localObject).getResult();
    if (localObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    if (localObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return localObject;
    }
    return Unit.INSTANCE;
  }
  
  public static final Object delay-VtjQ1oo(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    paramContinuation = delay(toDelayMillis-LRDsOJo(paramLong), paramContinuation);
    if (paramContinuation == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramContinuation;
    }
    return Unit.INSTANCE;
  }
  
  public static final Delay getDelay(CoroutineContext paramCoroutineContext)
  {
    paramCoroutineContext = paramCoroutineContext.get((CoroutineContext.Key)ContinuationInterceptor.Key);
    if ((paramCoroutineContext instanceof Delay)) {
      paramCoroutineContext = (Delay)paramCoroutineContext;
    } else {
      paramCoroutineContext = null;
    }
    Object localObject = paramCoroutineContext;
    if (paramCoroutineContext == null) {
      localObject = DefaultExecutorKt.getDefaultDelay();
    }
    return (Delay)localObject;
  }
  
  public static final long toDelayMillis-LRDsOJo(long paramLong)
  {
    if (Duration.compareTo-LRDsOJo(paramLong, Duration.Companion.getZERO-UwyO8pc()) > 0) {
      paramLong = RangesKt.coerceAtLeast(Duration.getInWholeMilliseconds-impl(paramLong), 1L);
    } else {
      paramLong = 0L;
    }
    return paramLong;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DelayKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */