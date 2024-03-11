package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.channels.BufferOverflow;

@Metadata(d1={"\000&\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\000\020\0012\0020\002B+\022\f\020\003\032\b\022\004\022\0028\0000\004\022\006\020\005\032\0020\006\022\006\020\007\032\0020\b\022\006\020\t\032\0020\n¢\006\002\020\013R\020\020\t\032\0020\n8\006X\004¢\006\002\n\000R\020\020\005\032\0020\0068\006X\004¢\006\002\n\000R\020\020\007\032\0020\b8\006X\004¢\006\002\n\000R\026\020\003\032\b\022\004\022\0028\0000\0048\006X\004¢\006\002\n\000¨\006\f"}, d2={"Lkotlinx/coroutines/flow/SharingConfig;", "T", "", "upstream", "Lkotlinx/coroutines/flow/Flow;", "extraBufferCapacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/Flow;ILkotlinx/coroutines/channels/BufferOverflow;Lkotlin/coroutines/CoroutineContext;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class SharingConfig<T>
{
  public final CoroutineContext context;
  public final int extraBufferCapacity;
  public final BufferOverflow onBufferOverflow;
  public final Flow<T> upstream;
  
  public SharingConfig(Flow<? extends T> paramFlow, int paramInt, BufferOverflow paramBufferOverflow, CoroutineContext paramCoroutineContext)
  {
    this.upstream = paramFlow;
    this.extraBufferCapacity = paramInt;
    this.onBufferOverflow = paramBufferOverflow;
    this.context = paramCoroutineContext;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SharingConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */