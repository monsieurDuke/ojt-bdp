package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000.\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\b\b\030\0002\f\022\b\022\006\022\002\b\0030\0020\001B\021\022\n\020\003\032\006\022\002\b\0030\004¢\006\002\020\005J\r\020\006\032\006\022\002\b\0030\004HÂ\003J\027\020\007\032\0020\0002\f\b\002\020\003\032\006\022\002\b\0030\004HÆ\001J\023\020\b\032\0020\t2\b\020\n\032\004\030\0010\013HÖ\003J\t\020\f\032\0020\rHÖ\001J\t\020\016\032\0020\017HÖ\001R\022\020\003\032\006\022\002\b\0030\004X\004¢\006\002\n\000¨\006\020"}, d2={"Lkotlinx/coroutines/internal/ThreadLocalKey;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/internal/ThreadLocalElement;", "threadLocal", "Ljava/lang/ThreadLocal;", "(Ljava/lang/ThreadLocal;)V", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ThreadLocalKey
  implements CoroutineContext.Key<ThreadLocalElement<?>>
{
  private final ThreadLocal<?> threadLocal;
  
  public ThreadLocalKey(ThreadLocal<?> paramThreadLocal)
  {
    this.threadLocal = paramThreadLocal;
  }
  
  private final ThreadLocal<?> component1()
  {
    return this.threadLocal;
  }
  
  public final ThreadLocalKey copy(ThreadLocal<?> paramThreadLocal)
  {
    return new ThreadLocalKey(paramThreadLocal);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ThreadLocalKey)) {
      return false;
    }
    paramObject = (ThreadLocalKey)paramObject;
    return Intrinsics.areEqual(this.threadLocal, ((ThreadLocalKey)paramObject).threadLocal);
  }
  
  public int hashCode()
  {
    return this.threadLocal.hashCode();
  }
  
  public String toString()
  {
    return "ThreadLocalKey(threadLocal=" + this.threadLocal + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ThreadLocalKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */