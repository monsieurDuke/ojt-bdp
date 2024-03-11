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

@Metadata(d1={"\000.\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\b\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\000\n\000\n\002\030\002\n\002\b\002\032\020\020\017\032\0020\0202\b\b\002\020\021\032\0020\022\032B\020\023\032\002H\024\"\004\b\000\020\024*\0020\0202\n\b\002\020\025\032\004\030\0010\0262\f\020\027\032\b\022\004\022\002H\0240\030HHø\001\000\002\n\n\b\b\001\022\002\020\002 \001¢\006\002\020\031\"\026\020\000\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0078\002X\004¢\006\b\n\000\022\004\b\b\020\003\"\026\020\t\032\0020\0078\002X\004¢\006\b\n\000\022\004\b\n\020\003\"\026\020\013\032\0020\0078\002X\004¢\006\b\n\000\022\004\b\f\020\003\"\026\020\r\032\0020\0078\002X\004¢\006\b\n\000\022\004\b\016\020\003\002\004\n\002\b\031¨\006\032"}, d2={"EMPTY_LOCKED", "Lkotlinx/coroutines/sync/Empty;", "getEMPTY_LOCKED$annotations", "()V", "EMPTY_UNLOCKED", "getEMPTY_UNLOCKED$annotations", "LOCKED", "Lkotlinx/coroutines/internal/Symbol;", "getLOCKED$annotations", "LOCK_FAIL", "getLOCK_FAIL$annotations", "UNLOCKED", "getUNLOCKED$annotations", "UNLOCK_FAIL", "getUNLOCK_FAIL$annotations", "Mutex", "Lkotlinx/coroutines/sync/Mutex;", "locked", "", "withLock", "T", "owner", "", "action", "Lkotlin/Function0;", "(Lkotlinx/coroutines/sync/Mutex;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class MutexKt
{
  private static final Empty EMPTY_LOCKED;
  private static final Empty EMPTY_UNLOCKED;
  private static final Symbol LOCKED;
  private static final Symbol LOCK_FAIL = new Symbol("LOCK_FAIL");
  private static final Symbol UNLOCKED;
  private static final Symbol UNLOCK_FAIL = new Symbol("UNLOCK_FAIL");
  
  static
  {
    Symbol localSymbol2 = new Symbol("LOCKED");
    LOCKED = localSymbol2;
    Symbol localSymbol1 = new Symbol("UNLOCKED");
    UNLOCKED = localSymbol1;
    EMPTY_LOCKED = new Empty(localSymbol2);
    EMPTY_UNLOCKED = new Empty(localSymbol1);
  }
  
  public static final Mutex Mutex(boolean paramBoolean)
  {
    return (Mutex)new MutexImpl(paramBoolean);
  }
  
  public static final <T> Object withLock(Mutex paramMutex, Object paramObject, Function0<? extends T> paramFunction0, Continuation<? super T> paramContinuation)
  {
    if ((paramContinuation instanceof withLock.1))
    {
      localObject1 = (withLock.1)paramContinuation;
      if ((((withLock.1)localObject1).label & 0x80000000) != 0)
      {
        ((withLock.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super T>)localObject1;
        break label52;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return MutexKt.withLock(null, null, null, (Continuation)this);
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
      paramFunction0 = (Function0)paramContinuation.L$2;
      paramObject = paramContinuation.L$1;
      paramMutex = (Mutex)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject1);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject1);
      paramContinuation.L$0 = paramMutex;
      paramContinuation.L$1 = paramObject;
      paramContinuation.L$2 = paramFunction0;
      paramContinuation.label = 1;
      if (paramMutex.lock(paramObject, paramContinuation) == localObject2) {
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
      paramMutex.unlock(paramObject);
      InlineMarker.finallyEnd(1);
    }
  }
  
  private static final <T> Object withLock$$forInline(Mutex paramMutex, Object paramObject, Function0<? extends T> paramFunction0, Continuation<? super T> paramContinuation)
  {
    InlineMarker.mark(0);
    paramMutex.lock(paramObject, paramContinuation);
    InlineMarker.mark(1);
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramMutex.unlock(paramObject);
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/sync/MutexKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */