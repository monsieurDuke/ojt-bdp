package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(d1={"\000,\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\004\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\034\020\016\032\0020\0172\n\020\020\032\006\022\002\b\0030\t2\b\020\021\032\004\030\0010\001J\016\020\022\032\0020\0172\006\020\002\032\0020\003R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000R \020\007\032\022\022\016\022\f\022\006\022\004\030\0010\001\030\0010\t0\bX\004¢\006\004\n\002\020\nR\016\020\013\032\0020\005X\016¢\006\002\n\000R\030\020\f\032\n\022\006\022\004\030\0010\0010\bX\004¢\006\004\n\002\020\r¨\006\023"}, d2={"Lkotlinx/coroutines/internal/ThreadState;", "", "context", "Lkotlin/coroutines/CoroutineContext;", "n", "", "(Lkotlin/coroutines/CoroutineContext;I)V", "elements", "", "Lkotlinx/coroutines/ThreadContextElement;", "[Lkotlinx/coroutines/ThreadContextElement;", "i", "values", "[Ljava/lang/Object;", "append", "", "element", "value", "restore", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class ThreadState
{
  public final CoroutineContext context;
  private final ThreadContextElement<Object>[] elements;
  private int i;
  private final Object[] values;
  
  public ThreadState(CoroutineContext paramCoroutineContext, int paramInt)
  {
    this.context = paramCoroutineContext;
    this.values = new Object[paramInt];
    this.elements = new ThreadContextElement[paramInt];
  }
  
  public final void append(ThreadContextElement<?> paramThreadContextElement, Object paramObject)
  {
    Object[] arrayOfObject = this.values;
    int j = this.i;
    arrayOfObject[j] = paramObject;
    paramObject = this.elements;
    this.i = (j + 1);
    paramObject[j] = paramThreadContextElement;
  }
  
  public final void restore(CoroutineContext paramCoroutineContext)
  {
    int j = this.elements.length - 1;
    if (j >= 0)
    {
      int k;
      do
      {
        k = j - 1;
        ThreadContextElement localThreadContextElement = this.elements[j];
        Intrinsics.checkNotNull(localThreadContextElement);
        localThreadContextElement.restoreThreadContext(paramCoroutineContext, this.values[j]);
        j = k;
      } while (k >= 0);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ThreadState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */