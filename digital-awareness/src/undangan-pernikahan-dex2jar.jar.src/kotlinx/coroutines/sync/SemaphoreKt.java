package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(d1={"\0000\n\000\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\b\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\t\n\002\b\004\n\002\030\002\n\002\b\002\032\030\020\017\032\0020\0202\006\020\021\032\0020\0072\b\b\002\020\022\032\0020\007\032\032\020\023\032\0020\0242\006\020\025\032\0020\0262\b\020\027\032\004\030\0010\024H\002\0326\020\030\032\002H\031\"\004\b\000\020\031*\0020\0202\f\020\032\032\b\022\004\022\002H\0310\033HHø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\034\"\026\020\000\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0078\002X\004¢\006\b\n\000\022\004\b\b\020\003\"\026\020\t\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\n\020\003\"\026\020\013\032\0020\0078\002X\004¢\006\b\n\000\022\004\b\f\020\003\"\026\020\r\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\016\020\003\002\004\n\002\b\031¨\006\035"}, d2={"BROKEN", "Lkotlinx/coroutines/internal/Symbol;", "getBROKEN$annotations", "()V", "CANCELLED", "getCANCELLED$annotations", "MAX_SPIN_CYCLES", "", "getMAX_SPIN_CYCLES$annotations", "PERMIT", "getPERMIT$annotations", "SEGMENT_SIZE", "getSEGMENT_SIZE$annotations", "TAKEN", "getTAKEN$annotations", "Semaphore", "Lkotlinx/coroutines/sync/Semaphore;", "permits", "acquiredPermits", "createSegment", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "id", "", "prev", "withPermit", "T", "action", "Lkotlin/Function0;", "(Lkotlinx/coroutines/sync/Semaphore;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SemaphoreKt
{
  private static final Symbol BROKEN = new Symbol("BROKEN");
  private static final Symbol CANCELLED = new Symbol("CANCELLED");
  private static final int MAX_SPIN_CYCLES = SystemPropsKt.systemProp$default("kotlinx.coroutines.semaphore.maxSpinCycles", 100, 0, 0, 12, null);
  private static final Symbol PERMIT = new Symbol("PERMIT");
  private static final int SEGMENT_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.semaphore.segmentSize", 16, 0, 0, 12, null);
  private static final Symbol TAKEN = new Symbol("TAKEN");
  
  public static final Semaphore Semaphore(int paramInt1, int paramInt2)
  {
    return (Semaphore)new SemaphoreImpl(paramInt1, paramInt2);
  }
  
  private static final SemaphoreSegment createSegment(long paramLong, SemaphoreSegment paramSemaphoreSegment)
  {
    return new SemaphoreSegment(paramLong, paramSemaphoreSegment, 0);
  }
  
  public static final <T> Object withPermit(Semaphore paramSemaphore, Function0<? extends T> paramFunction0, Continuation<? super T> paramContinuation)
  {
    if ((paramContinuation instanceof withPermit.1))
    {
      localObject1 = (withPermit.1)paramContinuation;
      if ((((withPermit.1)localObject1).label & 0x80000000) != 0)
      {
        ((withPermit.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super T>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return SemaphoreKt.withPermit(null, null, (Continuation)this);
      }
    };
    label47:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      paramFunction0 = (Function0)paramContinuation.L$1;
      paramSemaphore = (Semaphore)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject1);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject1);
      paramContinuation.L$0 = paramSemaphore;
      paramContinuation.L$1 = paramFunction0;
      paramContinuation.label = 1;
      if (paramSemaphore.acquire(paramContinuation) == localObject2) {
        return localObject2;
      }
      break;
    }
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramSemaphore.release();
      InlineMarker.finallyEnd(1);
    }
  }
  
  private static final <T> Object withPermit$$forInline(Semaphore paramSemaphore, Function0<? extends T> paramFunction0, Continuation<? super T> paramContinuation)
  {
    InlineMarker.mark(0);
    paramSemaphore.acquire(paramContinuation);
    InlineMarker.mark(1);
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramSemaphore.release();
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/sync/SemaphoreKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */