package kotlinx.coroutines.flow;

import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\b\003\n\002\020\001\n\000\n\002\030\002\n\002\b\002\bf\030\000*\006\b\000\020\001 \0012\b\022\004\022\002H\0010\002J\037\020\007\032\0020\b2\f\020\t\032\b\022\004\022\0028\0000\nH¦@ø\001\000¢\006\002\020\013R\030\020\003\032\b\022\004\022\0028\0000\004X¦\004¢\006\006\032\004\b\005\020\006\002\004\n\002\b\031¨\006\f"}, d2={"Lkotlinx/coroutines/flow/SharedFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface SharedFlow<T>
  extends Flow<T>
{
  public abstract Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<?> paramContinuation);
  
  public abstract List<T> getReplayCache();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SharedFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */