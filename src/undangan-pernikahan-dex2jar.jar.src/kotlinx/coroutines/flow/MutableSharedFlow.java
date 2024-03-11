package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000,\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\004\n\002\020\013\n\002\b\002\bf\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003J\031\020\t\032\0020\n2\006\020\013\032\0028\000H¦@ø\001\000¢\006\002\020\fJ\b\020\r\032\0020\nH'J\025\020\016\032\0020\0172\006\020\013\032\0028\000H&¢\006\002\020\020R\030\020\004\032\b\022\004\022\0020\0060\005X¦\004¢\006\006\032\004\b\007\020\b\002\004\n\002\b\031¨\006\021"}, d2={"Lkotlinx/coroutines/flow/MutableSharedFlow;", "T", "Lkotlinx/coroutines/flow/SharedFlow;", "Lkotlinx/coroutines/flow/FlowCollector;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetReplayCache", "tryEmit", "", "(Ljava/lang/Object;)Z", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface MutableSharedFlow<T>
  extends SharedFlow<T>, FlowCollector<T>
{
  public abstract Object emit(T paramT, Continuation<? super Unit> paramContinuation);
  
  public abstract StateFlow<Integer> getSubscriptionCount();
  
  public abstract void resetReplayCache();
  
  public abstract boolean tryEmit(T paramT);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/MutableSharedFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */