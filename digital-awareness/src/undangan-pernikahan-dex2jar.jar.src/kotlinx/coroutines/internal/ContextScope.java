package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\016\n\000\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\b\032\0020\tH\026R\024\020\005\032\0020\003X\004¢\006\b\n\000\032\004\b\006\020\007¨\006\n"}, d2={"Lkotlinx/coroutines/internal/ContextScope;", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "coroutineContext", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ContextScope
  implements CoroutineScope
{
  private final CoroutineContext coroutineContext;
  
  public ContextScope(CoroutineContext paramCoroutineContext)
  {
    this.coroutineContext = paramCoroutineContext;
  }
  
  public CoroutineContext getCoroutineContext()
  {
    return this.coroutineContext;
  }
  
  public String toString()
  {
    return "CoroutineScope(coroutineContext=" + getCoroutineContext() + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ContextScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */