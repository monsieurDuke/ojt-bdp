package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000&\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\021\n\002\030\002\n\002\020\002\n\002\b\002\b \030\000*\004\b\000\020\0012\0020\002B\005¢\006\002\020\003J\025\020\004\032\0020\0052\006\020\006\032\0028\000H&¢\006\002\020\007J#\020\b\032\020\022\f\022\n\022\004\022\0020\013\030\0010\n0\t2\006\020\006\032\0028\000H&¢\006\002\020\f¨\006\r"}, d2={"Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "F", "", "()V", "allocateLocked", "", "flow", "(Ljava/lang/Object;)Z", "freeLocked", "", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/Object;)[Lkotlin/coroutines/Continuation;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AbstractSharedFlowSlot<F>
{
  public abstract boolean allocateLocked(F paramF);
  
  public abstract Continuation<Unit>[] freeLocked(F paramF);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/AbstractSharedFlowSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */