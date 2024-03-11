package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

@Metadata(d1={"\0000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\002\n\000\n\002\020\t\n\000\n\002\020\013\n\002\b\002\n\002\020\021\n\002\b\002\b\000\030\0002\f\022\b\022\006\022\002\b\0030\0020\001B\005¢\006\002\020\003J\024\020\t\032\0020\n2\n\020\013\032\006\022\002\b\0030\002H\026J'\020\f\032\020\022\f\022\n\022\004\022\0020\006\030\0010\0050\r2\n\020\013\032\006\022\002\b\0030\002H\026¢\006\002\020\016R\032\020\004\032\n\022\004\022\0020\006\030\0010\0058\006@\006X\016¢\006\002\n\000R\022\020\007\032\0020\b8\006@\006X\016¢\006\002\n\000¨\006\017"}, d2={"Lkotlinx/coroutines/flow/SharedFlowSlot;", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "()V", "cont", "Lkotlin/coroutines/Continuation;", "", "index", "", "allocateLocked", "", "flow", "freeLocked", "", "(Lkotlinx/coroutines/flow/SharedFlowImpl;)[Lkotlin/coroutines/Continuation;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class SharedFlowSlot
  extends AbstractSharedFlowSlot<SharedFlowImpl<?>>
{
  public Continuation<? super Unit> cont;
  public long index = -1L;
  
  public boolean allocateLocked(SharedFlowImpl<?> paramSharedFlowImpl)
  {
    if (this.index >= 0L) {
      return false;
    }
    this.index = paramSharedFlowImpl.updateNewCollectorIndexLocked$kotlinx_coroutines_core();
    return true;
  }
  
  public Continuation<Unit>[] freeLocked(SharedFlowImpl<?> paramSharedFlowImpl)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (this.index >= 0L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    long l = this.index;
    this.index = -1L;
    this.cont = null;
    return paramSharedFlowImpl.updateCollectorIndexLocked$kotlinx_coroutines_core(l);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SharedFlowSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */