package kotlinx.coroutines.flow;

import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.FusibleFlow;

@Metadata(d1={"\000L\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020 \n\002\b\003\n\002\020\001\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\0032\b\022\004\022\002H\0010\004B\035\022\f\020\005\032\b\022\004\022\0028\0000\002\022\b\020\006\032\004\030\0010\007¢\006\002\020\bJ\037\020\r\032\0020\0162\f\020\017\032\b\022\004\022\0028\0000\020HAø\001\000¢\006\002\020\021J&\020\022\032\b\022\004\022\0028\0000\0232\006\020\024\032\0020\0252\006\020\026\032\0020\0272\006\020\030\032\0020\031H\026R\020\020\006\032\004\030\0010\007X\004¢\006\002\n\000R\030\020\t\032\b\022\004\022\0028\0000\nX\005¢\006\006\032\004\b\013\020\f\002\004\n\002\b\031¨\006\032"}, d2={"Lkotlinx/coroutines/flow/ReadonlySharedFlow;", "T", "Lkotlinx/coroutines/flow/SharedFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "flow", "job", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/flow/SharedFlow;Lkotlinx/coroutines/Job;)V", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fuse", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class ReadonlySharedFlow<T>
  implements SharedFlow<T>, CancellableFlow<T>, FusibleFlow<T>
{
  private final SharedFlow<T> $$delegate_0;
  private final Job job;
  
  public ReadonlySharedFlow(SharedFlow<? extends T> paramSharedFlow, Job paramJob)
  {
    this.job = paramJob;
    this.$$delegate_0 = paramSharedFlow;
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<?> paramContinuation)
  {
    return this.$$delegate_0.collect(paramFlowCollector, paramContinuation);
  }
  
  public Flow<T> fuse(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return SharedFlowKt.fuseSharedFlow((SharedFlow)this, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public List<T> getReplayCache()
  {
    return this.$$delegate_0.getReplayCache();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/ReadonlySharedFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */