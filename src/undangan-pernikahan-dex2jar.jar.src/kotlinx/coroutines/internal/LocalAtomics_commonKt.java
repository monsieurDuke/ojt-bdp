package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;

@Metadata(d1={"\000\022\n\000\n\002\020\b\n\002\030\002\n\002\030\002\n\002\b\005\".\020\000\032\0020\001*\0060\002j\002`\0032\006\020\000\032\0020\0018À\002@À\002X\016¢\006\f\032\004\b\004\020\005\"\004\b\006\020\007¨\006\b"}, d2={"value", "", "Ljava/util/concurrent/atomic/AtomicInteger;", "Lkotlinx/coroutines/internal/LocalAtomicInt;", "getValue", "(Ljava/util/concurrent/atomic/AtomicInteger;)I", "setValue", "(Ljava/util/concurrent/atomic/AtomicInteger;I)V", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class LocalAtomics_commonKt
{
  public static final int getValue(AtomicInteger paramAtomicInteger)
  {
    return paramAtomicInteger.get();
  }
  
  public static final void setValue(AtomicInteger paramAtomicInteger, int paramInt)
  {
    paramAtomicInteger.set(paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LocalAtomics_commonKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */