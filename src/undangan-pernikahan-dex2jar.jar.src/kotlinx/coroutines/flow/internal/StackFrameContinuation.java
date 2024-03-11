package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;

@Metadata(d1={"\0006\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\0060\003j\002`\004B\033\022\f\020\005\032\b\022\004\022\0028\0000\002\022\006\020\006\032\0020\007¢\006\002\020\bJ\020\020\016\032\n\030\0010\017j\004\030\001`\020H\026J\036\020\021\032\0020\0222\f\020\023\032\b\022\004\022\0028\0000\024H\026ø\001\000¢\006\002\020\025R\034\020\t\032\n\030\0010\003j\004\030\001`\0048VX\004¢\006\006\032\004\b\n\020\013R\024\020\006\032\0020\007X\004¢\006\b\n\000\032\004\b\f\020\rR\024\020\005\032\b\022\004\022\0028\0000\002X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\026"}, d2={"Lkotlinx/coroutines/flow/internal/StackFrameContinuation;", "T", "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "uCont", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class StackFrameContinuation<T>
  implements Continuation<T>, CoroutineStackFrame
{
  private final CoroutineContext context;
  private final Continuation<T> uCont;
  
  public StackFrameContinuation(Continuation<? super T> paramContinuation, CoroutineContext paramCoroutineContext)
  {
    this.uCont = paramContinuation;
    this.context = paramCoroutineContext;
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    Object localObject = this.uCont;
    if ((localObject instanceof CoroutineStackFrame)) {
      localObject = (CoroutineStackFrame)localObject;
    } else {
      localObject = null;
    }
    return (CoroutineStackFrame)localObject;
  }
  
  public CoroutineContext getContext()
  {
    return this.context;
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  public void resumeWith(Object paramObject)
  {
    this.uCont.resumeWith(paramObject);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/StackFrameContinuation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */