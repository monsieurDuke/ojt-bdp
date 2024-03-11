package kotlinx.coroutines.flow;

import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.FusibleFlow;

@Metadata(d1={"\000L\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020 \n\002\b\006\n\002\020\001\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\0032\b\022\004\022\002H\0010\004B\035\022\f\020\005\032\b\022\004\022\0028\0000\002\022\b\020\006\032\004\030\0010\007¢\006\002\020\bJ\037\020\020\032\0020\0212\f\020\022\032\b\022\004\022\0028\0000\023HAø\001\000¢\006\002\020\024J&\020\025\032\b\022\004\022\0028\0000\0262\006\020\027\032\0020\0302\006\020\031\032\0020\0322\006\020\033\032\0020\034H\026R\020\020\006\032\004\030\0010\007X\004¢\006\002\n\000R\030\020\t\032\b\022\004\022\0028\0000\nX\005¢\006\006\032\004\b\013\020\fR\022\020\r\032\0028\000X\005¢\006\006\032\004\b\016\020\017\002\004\n\002\b\031¨\006\035"}, d2={"Lkotlinx/coroutines/flow/ReadonlyStateFlow;", "T", "Lkotlinx/coroutines/flow/StateFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "flow", "job", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/flow/StateFlow;Lkotlinx/coroutines/Job;)V", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "value", "getValue", "()Ljava/lang/Object;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fuse", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class ReadonlyStateFlow<T>
  implements StateFlow<T>, CancellableFlow<T>, FusibleFlow<T>
{
  private final StateFlow<T> $$delegate_0;
  private final Job job;
  
  public ReadonlyStateFlow(StateFlow<? extends T> paramStateFlow, Job paramJob)
  {
    this.job = paramJob;
    this.$$delegate_0 = paramStateFlow;
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<?> paramContinuation)
  {
    return this.$$delegate_0.collect(paramFlowCollector, paramContinuation);
  }
  
  public Flow<T> fuse(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return StateFlowKt.fuseStateFlow((StateFlow)this, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public List<T> getReplayCache()
  {
    return this.$$delegate_0.getReplayCache();
  }
  
  public T getValue()
  {
    return (T)this.$$delegate_0.getValue();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/ReadonlyStateFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */