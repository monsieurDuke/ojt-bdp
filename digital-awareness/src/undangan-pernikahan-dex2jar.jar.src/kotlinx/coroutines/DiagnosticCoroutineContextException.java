package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;

@Metadata(d1={"\000\"\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\0060\001j\002`\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\b\020\006\032\0020\007H\026J\b\020\b\032\0020\tH\026R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\n"}, d2={"Lkotlinx/coroutines/DiagnosticCoroutineContextException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "fillInStackTrace", "", "getLocalizedMessage", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class DiagnosticCoroutineContextException
  extends RuntimeException
{
  private final CoroutineContext context;
  
  public DiagnosticCoroutineContextException(CoroutineContext paramCoroutineContext)
  {
    this.context = paramCoroutineContext;
  }
  
  public Throwable fillInStackTrace()
  {
    setStackTrace(new StackTraceElement[0]);
    return (Throwable)this;
  }
  
  public String getLocalizedMessage()
  {
    return this.context.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DiagnosticCoroutineContextException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */