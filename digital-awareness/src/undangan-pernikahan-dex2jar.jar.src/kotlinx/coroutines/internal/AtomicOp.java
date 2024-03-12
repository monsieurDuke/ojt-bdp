package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;

@Metadata(d1={"\000*\n\002\030\002\n\002\b\004\n\002\020\000\n\000\n\002\020\002\n\002\b\r\n\002\020\013\n\002\b\002\n\002\020\t\n\002\b\004\n\002\030\002\b'\030\000*\006\b\000\020\001 \0002\0020\035B\007¢\006\004\b\002\020\003J!\020\b\032\0020\0072\006\020\004\032\0028\0002\b\020\006\032\004\030\0010\005H&¢\006\004\b\b\020\tJ\031\020\013\032\004\030\0010\0052\b\020\n\032\004\030\0010\005¢\006\004\b\013\020\fJ\031\020\r\032\004\030\0010\0052\b\020\004\032\004\030\0010\005¢\006\004\b\r\020\fJ\031\020\016\032\004\030\0010\0052\006\020\004\032\0028\000H&¢\006\004\b\016\020\fR\030\020\021\032\006\022\002\b\0030\0008VX\004¢\006\006\032\004\b\017\020\020R\023\020\024\032\004\030\0010\0058F¢\006\006\032\004\b\022\020\023R\021\020\026\032\0020\0258F¢\006\006\032\004\b\026\020\027R\024\020\033\032\0020\0308VX\004¢\006\006\032\004\b\031\020\032¨\006\034"}, d2={"Lkotlinx/coroutines/internal/AtomicOp;", "T", "<init>", "()V", "affected", "", "failure", "", "complete", "(Ljava/lang/Object;Ljava/lang/Object;)V", "decision", "decide", "(Ljava/lang/Object;)Ljava/lang/Object;", "perform", "prepare", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "atomicOp", "getConsensus", "()Ljava/lang/Object;", "consensus", "", "isDecided", "()Z", "", "getOpSequence", "()J", "opSequence", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/OpDescriptor;"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AtomicOp<T>
  extends OpDescriptor
{
  private static final AtomicReferenceFieldUpdater _consensus$FU = AtomicReferenceFieldUpdater.newUpdater(AtomicOp.class, Object.class, "_consensus");
  private volatile Object _consensus = AtomicKt.NO_DECISION;
  
  public abstract void complete(T paramT, Object paramObject);
  
  public final Object decide(Object paramObject)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (paramObject != AtomicKt.NO_DECISION) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    Object localObject = this._consensus;
    if (localObject != AtomicKt.NO_DECISION) {
      return localObject;
    }
    if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_consensus$FU, this, AtomicKt.NO_DECISION, paramObject)) {
      return paramObject;
    }
    return this._consensus;
  }
  
  public AtomicOp<?> getAtomicOp()
  {
    return this;
  }
  
  public final Object getConsensus()
  {
    return this._consensus;
  }
  
  public long getOpSequence()
  {
    return 0L;
  }
  
  public final boolean isDecided()
  {
    boolean bool;
    if (this._consensus != AtomicKt.NO_DECISION) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final Object perform(Object paramObject)
  {
    Object localObject2 = this._consensus;
    Object localObject1 = localObject2;
    if (localObject2 == AtomicKt.NO_DECISION) {
      localObject1 = decide(prepare(paramObject));
    }
    complete(paramObject, localObject1);
    return localObject1;
  }
  
  public abstract Object prepare(T paramT);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/AtomicOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */