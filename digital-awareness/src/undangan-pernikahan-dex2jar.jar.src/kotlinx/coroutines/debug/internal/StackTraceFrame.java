package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;

@Metadata(d1={"\000\024\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\b\000\030\0002\0020\001B\027\022\b\020\002\032\004\030\0010\001\022\006\020\003\032\0020\004¢\006\002\020\005J\b\020\b\032\0020\004H\026R\026\020\002\032\004\030\0010\001X\004¢\006\b\n\000\032\004\b\006\020\007R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "stackTraceElement", "Ljava/lang/StackTraceElement;", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Ljava/lang/StackTraceElement;)V", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getStackTraceElement", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class StackTraceFrame
  implements CoroutineStackFrame
{
  private final CoroutineStackFrame callerFrame;
  private final StackTraceElement stackTraceElement;
  
  public StackTraceFrame(CoroutineStackFrame paramCoroutineStackFrame, StackTraceElement paramStackTraceElement)
  {
    this.callerFrame = paramCoroutineStackFrame;
    this.stackTraceElement = paramStackTraceElement;
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    return this.callerFrame;
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return this.stackTraceElement;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/StackTraceFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */