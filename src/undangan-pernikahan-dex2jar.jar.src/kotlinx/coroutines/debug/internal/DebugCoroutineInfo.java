package kotlinx.coroutines.debug.internal;

import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;

@Metadata(d1={"\000D\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020 \n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\003\n\002\020\016\n\002\b\003\b\001\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\007\020\bR\023\020\t\032\004\030\0010\n¢\006\b\n\000\032\004\b\013\020\fR\027\020\r\032\b\022\004\022\0020\0170\016¢\006\b\n\000\032\004\b\020\020\021R\023\020\022\032\004\030\0010\n¢\006\b\n\000\032\004\b\023\020\fR\031\020\024\032\b\022\004\022\0020\0170\0168G¢\006\b\n\000\032\004\b\024\020\021R\023\020\025\032\004\030\0010\026¢\006\b\n\000\032\004\b\027\020\030R\021\020\031\032\0020\032¢\006\b\n\000\032\004\b\033\020\034R\021\020\035\032\0020\036¢\006\b\n\000\032\004\b\037\020 ¨\006!"}, d2={"Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "", "source", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/CoroutineContext;)V", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "creationStackBottom", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getCreationStackBottom", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "creationStackTrace", "", "Ljava/lang/StackTraceElement;", "getCreationStackTrace", "()Ljava/util/List;", "lastObservedFrame", "getLastObservedFrame", "lastObservedStackTrace", "lastObservedThread", "Ljava/lang/Thread;", "getLastObservedThread", "()Ljava/lang/Thread;", "sequenceNumber", "", "getSequenceNumber", "()J", "state", "", "getState", "()Ljava/lang/String;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DebugCoroutineInfo
{
  private final CoroutineContext context;
  private final CoroutineStackFrame creationStackBottom;
  private final List<StackTraceElement> creationStackTrace;
  private final CoroutineStackFrame lastObservedFrame;
  private final List<StackTraceElement> lastObservedStackTrace;
  private final Thread lastObservedThread;
  private final long sequenceNumber;
  private final String state;
  
  public DebugCoroutineInfo(DebugCoroutineInfoImpl paramDebugCoroutineInfoImpl, CoroutineContext paramCoroutineContext)
  {
    this.context = paramCoroutineContext;
    this.creationStackBottom = ((CoroutineStackFrame)paramDebugCoroutineInfoImpl.getCreationStackBottom());
    this.sequenceNumber = paramDebugCoroutineInfoImpl.sequenceNumber;
    this.creationStackTrace = paramDebugCoroutineInfoImpl.getCreationStackTrace();
    this.state = paramDebugCoroutineInfoImpl.getState();
    this.lastObservedThread = paramDebugCoroutineInfoImpl.lastObservedThread;
    this.lastObservedFrame = paramDebugCoroutineInfoImpl.getLastObservedFrame$kotlinx_coroutines_core();
    this.lastObservedStackTrace = paramDebugCoroutineInfoImpl.lastObservedStackTrace();
  }
  
  public final CoroutineContext getContext()
  {
    return this.context;
  }
  
  public final CoroutineStackFrame getCreationStackBottom()
  {
    return this.creationStackBottom;
  }
  
  public final List<StackTraceElement> getCreationStackTrace()
  {
    return this.creationStackTrace;
  }
  
  public final CoroutineStackFrame getLastObservedFrame()
  {
    return this.lastObservedFrame;
  }
  
  public final Thread getLastObservedThread()
  {
    return this.lastObservedThread;
  }
  
  public final long getSequenceNumber()
  {
    return this.sequenceNumber;
  }
  
  public final String getState()
  {
    return this.state;
  }
  
  public final List<StackTraceElement> lastObservedStackTrace()
  {
    return this.lastObservedStackTrace;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/DebugCoroutineInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */