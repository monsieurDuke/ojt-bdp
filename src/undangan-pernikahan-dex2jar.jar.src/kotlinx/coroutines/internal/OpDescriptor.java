package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlinx.coroutines.DebugStringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\016\n\000\b&\030\0002\0020\001B\005¢\006\002\020\002J\016\020\007\032\0020\b2\006\020\t\032\0020\000J\024\020\n\032\004\030\0010\0012\b\020\013\032\004\030\0010\001H&J\b\020\f\032\0020\rH\026R\030\020\003\032\b\022\002\b\003\030\0010\004X¦\004¢\006\006\032\004\b\005\020\006¨\006\016"}, d2={"Lkotlinx/coroutines/internal/OpDescriptor;", "", "()V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "isEarlierThan", "", "that", "perform", "affected", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class OpDescriptor
{
  public abstract AtomicOp<?> getAtomicOp();
  
  public final boolean isEarlierThan(OpDescriptor paramOpDescriptor)
  {
    AtomicOp localAtomicOp = getAtomicOp();
    boolean bool = false;
    if (localAtomicOp == null) {
      return false;
    }
    paramOpDescriptor = paramOpDescriptor.getAtomicOp();
    if (paramOpDescriptor == null) {
      return false;
    }
    if (localAtomicOp.getOpSequence() < paramOpDescriptor.getOpSequence()) {
      bool = true;
    }
    return bool;
  }
  
  public abstract Object perform(Object paramObject);
  
  public String toString()
  {
    Object localObject2 = new StringBuilder();
    Object localObject1 = DebugStringsKt.getClassSimpleName(this);
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    localObject1 = ((StringBuilder)localObject2).append((String)localObject1).append('@');
    localObject2 = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    return (String)localObject2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/OpDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */