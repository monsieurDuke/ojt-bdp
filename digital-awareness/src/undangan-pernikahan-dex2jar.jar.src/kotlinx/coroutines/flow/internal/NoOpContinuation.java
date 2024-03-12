package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

@Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\bÂ\002\030\0002\n\022\006\022\004\030\0010\0020\001B\007\b\002¢\006\002\020\003J \020\b\032\0020\t2\016\020\n\032\n\022\006\022\004\030\0010\0020\013H\026ø\001\000¢\006\002\020\fR\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\006\020\007\002\004\n\002\b\031¨\006\r"}, d2={"Lkotlinx/coroutines/flow/internal/NoOpContinuation;", "Lkotlin/coroutines/Continuation;", "", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class NoOpContinuation
  implements Continuation<Object>
{
  public static final NoOpContinuation INSTANCE = new NoOpContinuation();
  private static final CoroutineContext context = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
  
  public CoroutineContext getContext()
  {
    return context;
  }
  
  public void resumeWith(Object paramObject) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/NoOpContinuation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */