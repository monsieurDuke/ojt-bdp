package kotlinx.coroutines.debug.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import kotlin.Metadata;

@Metadata(d1={"\000\034\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\035\022\006\020\003\032\0028\000\022\016\020\004\032\n\022\004\022\0028\000\030\0010\005¢\006\002\020\006R\020\020\007\032\0020\b8\006X\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "T", "Ljava/lang/ref/WeakReference;", "ref", "queue", "Ljava/lang/ref/ReferenceQueue;", "(Ljava/lang/Object;Ljava/lang/ref/ReferenceQueue;)V", "hash", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class HashedWeakRef<T>
  extends WeakReference<T>
{
  public final int hash;
  
  public HashedWeakRef(T paramT, ReferenceQueue<T> paramReferenceQueue)
  {
    super(paramT, paramReferenceQueue);
    int i;
    if (paramT == null) {
      i = 0;
    } else {
      i = paramT.hashCode();
    }
    this.hash = i;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/HashedWeakRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */