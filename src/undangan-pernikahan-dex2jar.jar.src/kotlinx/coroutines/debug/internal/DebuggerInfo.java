package kotlinx.coroutines.debug.internal;

import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlinx.coroutines.CoroutineId;
import kotlinx.coroutines.CoroutineName;

@Metadata(d1={"\0004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\004\n\002\020\016\n\002\b\003\n\002\020 \n\002\030\002\n\002\b\016\b\001\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006R\025\020\007\032\004\030\0010\b¢\006\n\n\002\020\013\032\004\b\t\020\nR\023\020\f\032\004\030\0010\r¢\006\b\n\000\032\004\b\016\020\017R\027\020\020\032\b\022\004\022\0020\0220\021¢\006\b\n\000\032\004\b\023\020\024R\023\020\025\032\004\030\0010\r¢\006\b\n\000\032\004\b\026\020\017R\023\020\027\032\004\030\0010\r¢\006\b\n\000\032\004\b\030\020\017R\023\020\031\032\004\030\0010\r¢\006\b\n\000\032\004\b\032\020\017R\021\020\033\032\0020\b¢\006\b\n\000\032\004\b\034\020\035R\021\020\036\032\0020\r¢\006\b\n\000\032\004\b\037\020\017¨\006 "}, d2={"Lkotlinx/coroutines/debug/internal/DebuggerInfo;", "Ljava/io/Serializable;", "source", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/CoroutineContext;)V", "coroutineId", "", "getCoroutineId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "dispatcher", "", "getDispatcher", "()Ljava/lang/String;", "lastObservedStackTrace", "", "Ljava/lang/StackTraceElement;", "getLastObservedStackTrace", "()Ljava/util/List;", "lastObservedThreadName", "getLastObservedThreadName", "lastObservedThreadState", "getLastObservedThreadState", "name", "getName", "sequenceNumber", "getSequenceNumber", "()J", "state", "getState", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DebuggerInfo
  implements Serializable
{
  private final Long coroutineId;
  private final String dispatcher;
  private final List<StackTraceElement> lastObservedStackTrace;
  private final String lastObservedThreadName;
  private final String lastObservedThreadState;
  private final String name;
  private final long sequenceNumber;
  private final String state;
  
  public DebuggerInfo(DebugCoroutineInfoImpl paramDebugCoroutineInfoImpl, CoroutineContext paramCoroutineContext)
  {
    Object localObject1 = (CoroutineId)paramCoroutineContext.get((CoroutineContext.Key)CoroutineId.Key);
    Object localObject2 = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = Long.valueOf(((CoroutineId)localObject1).getId());
    }
    this.coroutineId = ((Long)localObject1);
    localObject1 = (ContinuationInterceptor)paramCoroutineContext.get((CoroutineContext.Key)ContinuationInterceptor.Key);
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = localObject1.toString();
    }
    this.dispatcher = ((String)localObject1);
    paramCoroutineContext = (CoroutineName)paramCoroutineContext.get((CoroutineContext.Key)CoroutineName.Key);
    if (paramCoroutineContext == null) {
      paramCoroutineContext = null;
    } else {
      paramCoroutineContext = paramCoroutineContext.getName();
    }
    this.name = paramCoroutineContext;
    this.state = paramDebugCoroutineInfoImpl.getState();
    paramCoroutineContext = paramDebugCoroutineInfoImpl.lastObservedThread;
    if (paramCoroutineContext == null) {}
    do
    {
      paramCoroutineContext = null;
      break;
      paramCoroutineContext = paramCoroutineContext.getState();
    } while (paramCoroutineContext == null);
    paramCoroutineContext = paramCoroutineContext.toString();
    this.lastObservedThreadState = paramCoroutineContext;
    paramCoroutineContext = paramDebugCoroutineInfoImpl.lastObservedThread;
    if (paramCoroutineContext == null) {
      paramCoroutineContext = (CoroutineContext)localObject2;
    } else {
      paramCoroutineContext = paramCoroutineContext.getName();
    }
    this.lastObservedThreadName = paramCoroutineContext;
    this.lastObservedStackTrace = paramDebugCoroutineInfoImpl.lastObservedStackTrace();
    this.sequenceNumber = paramDebugCoroutineInfoImpl.sequenceNumber;
  }
  
  public final Long getCoroutineId()
  {
    return this.coroutineId;
  }
  
  public final String getDispatcher()
  {
    return this.dispatcher;
  }
  
  public final List<StackTraceElement> getLastObservedStackTrace()
  {
    return this.lastObservedStackTrace;
  }
  
  public final String getLastObservedThreadName()
  {
    return this.lastObservedThreadName;
  }
  
  public final String getLastObservedThreadState()
  {
    return this.lastObservedThreadState;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final long getSequenceNumber()
  {
    return this.sequenceNumber;
  }
  
  public final String getState()
  {
    return this.state;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/DebuggerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */