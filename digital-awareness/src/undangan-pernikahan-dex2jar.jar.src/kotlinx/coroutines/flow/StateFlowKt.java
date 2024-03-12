package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\000@\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\032\037\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b2\006\020\t\032\002H\b¢\006\002\020\n\0326\020\013\032\b\022\004\022\002H\b0\f\"\004\b\000\020\b*\b\022\004\022\002H\b0\r2\006\020\016\032\0020\0172\006\020\020\032\0020\0212\006\020\022\032\0020\023H\000\0322\020\024\032\002H\b\"\004\b\000\020\b*\b\022\004\022\002H\b0\0072\022\020\025\032\016\022\004\022\002H\b\022\004\022\002H\b0\026H\b¢\006\002\020\027\032-\020\030\032\0020\031\"\004\b\000\020\b*\b\022\004\022\002H\b0\0072\022\020\025\032\016\022\004\022\002H\b\022\004\022\002H\b0\026H\b\0322\020\032\032\002H\b\"\004\b\000\020\b*\b\022\004\022\002H\b0\0072\022\020\025\032\016\022\004\022\002H\b\022\004\022\002H\b0\026H\b¢\006\002\020\027\"\026\020\000\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\005\020\003¨\006\033"}, d2={"NONE", "Lkotlinx/coroutines/internal/Symbol;", "getNONE$annotations", "()V", "PENDING", "getPENDING$annotations", "MutableStateFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "T", "value", "(Ljava/lang/Object;)Lkotlinx/coroutines/flow/MutableStateFlow;", "fuseStateFlow", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/StateFlow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "getAndUpdate", "function", "Lkotlin/Function1;", "(Lkotlinx/coroutines/flow/MutableStateFlow;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "update", "", "updateAndGet", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class StateFlowKt
{
  private static final Symbol NONE = new Symbol("NONE");
  private static final Symbol PENDING = new Symbol("PENDING");
  
  public static final <T> MutableStateFlow<T> MutableStateFlow(T paramT)
  {
    if (paramT == null) {
      paramT = NullSurrogateKt.NULL;
    }
    return (MutableStateFlow)new StateFlowImpl(paramT);
  }
  
  public static final <T> Flow<T> fuseStateFlow(StateFlow<? extends T> paramStateFlow, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int j = 1;
    int i;
    if (bool)
    {
      if (paramInt != -1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if ((paramInt >= 0) && (paramInt < 2)) {
      i = j;
    } else {
      i = 0;
    }
    if (((i != 0) || (paramInt == -2)) && (paramBufferOverflow == BufferOverflow.DROP_OLDEST)) {
      return (Flow)paramStateFlow;
    }
    return SharedFlowKt.fuseSharedFlow((SharedFlow)paramStateFlow, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public static final <T> T getAndUpdate(MutableStateFlow<T> paramMutableStateFlow, Function1<? super T, ? extends T> paramFunction1)
  {
    for (;;)
    {
      Object localObject = paramMutableStateFlow.getValue();
      if (paramMutableStateFlow.compareAndSet(localObject, paramFunction1.invoke(localObject))) {
        return (T)localObject;
      }
    }
  }
  
  public static final <T> void update(MutableStateFlow<T> paramMutableStateFlow, Function1<? super T, ? extends T> paramFunction1)
  {
    for (;;)
    {
      Object localObject = paramMutableStateFlow.getValue();
      if (paramMutableStateFlow.compareAndSet(localObject, paramFunction1.invoke(localObject))) {
        return;
      }
    }
  }
  
  public static final <T> T updateAndGet(MutableStateFlow<T> paramMutableStateFlow, Function1<? super T, ? extends T> paramFunction1)
  {
    for (;;)
    {
      Object localObject2 = paramMutableStateFlow.getValue();
      Object localObject1 = paramFunction1.invoke(localObject2);
      if (paramMutableStateFlow.compareAndSet(localObject2, localObject1)) {
        return (T)localObject1;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/StateFlowKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */