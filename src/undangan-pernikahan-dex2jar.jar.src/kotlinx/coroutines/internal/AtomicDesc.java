package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\004\b&\030\0002\0020\001B\005¢\006\002\020\002J\036\020\t\032\0020\n2\n\020\013\032\006\022\002\b\0030\0042\b\020\f\032\004\030\0010\001H&J\026\020\r\032\004\030\0010\0012\n\020\013\032\006\022\002\b\0030\004H&R\036\020\003\032\006\022\002\b\0030\004X.¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\b¨\006\016"}, d2={"Lkotlinx/coroutines/internal/AtomicDesc;", "", "()V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "setAtomicOp", "(Lkotlinx/coroutines/internal/AtomicOp;)V", "complete", "", "op", "failure", "prepare", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AtomicDesc
{
  public AtomicOp<?> atomicOp;
  
  public abstract void complete(AtomicOp<?> paramAtomicOp, Object paramObject);
  
  public final AtomicOp<?> getAtomicOp()
  {
    AtomicOp localAtomicOp = this.atomicOp;
    if (localAtomicOp != null) {
      return localAtomicOp;
    }
    Intrinsics.throwUninitializedPropertyAccessException("atomicOp");
    return null;
  }
  
  public abstract Object prepare(AtomicOp<?> paramAtomicOp);
  
  public final void setAtomicOp(AtomicOp<?> paramAtomicOp)
  {
    this.atomicOp = paramAtomicOp;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/AtomicDesc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */