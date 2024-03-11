package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;

@Metadata(d1={"\0004\n\000\n\002\030\002\n\002\b\005\n\002\020\t\n\002\b\002\n\002\020\002\n\000\n\002\020\000\n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\032\t\020\006\032\0020\007H\b\032\t\020\b\032\0020\007H\b\032\031\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\007H\b\032\t\020\016\032\0020\nH\b\032\t\020\017\032\0020\nH\b\032\t\020\020\032\0020\nH\b\032\021\020\021\032\0020\n2\006\020\022\032\0020\023H\b\032\t\020\024\032\0020\nH\b\032\031\020\025\032\0060\026j\002`\0272\n\020\030\032\0060\026j\002`\027H\b\"\034\020\000\032\004\030\0010\001X\016¢\006\016\n\000\032\004\b\002\020\003\"\004\b\004\020\005¨\006\031"}, d2={"timeSource", "Lkotlinx/coroutines/AbstractTimeSource;", "getTimeSource", "()Lkotlinx/coroutines/AbstractTimeSource;", "setTimeSource", "(Lkotlinx/coroutines/AbstractTimeSource;)V", "currentTimeMillis", "", "nanoTime", "parkNanos", "", "blocker", "", "nanos", "registerTimeLoopThread", "trackTask", "unTrackTask", "unpark", "thread", "Ljava/lang/Thread;", "unregisterTimeLoopThread", "wrapTask", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class AbstractTimeSourceKt
{
  private static AbstractTimeSource timeSource;
  
  private static final long currentTimeMillis()
  {
    Object localObject = getTimeSource();
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = Long.valueOf(((AbstractTimeSource)localObject).currentTimeMillis());
    }
    long l;
    if (localObject == null) {
      l = System.currentTimeMillis();
    } else {
      l = ((Long)localObject).longValue();
    }
    return l;
  }
  
  public static final AbstractTimeSource getTimeSource()
  {
    return timeSource;
  }
  
  private static final long nanoTime()
  {
    Object localObject = getTimeSource();
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = Long.valueOf(((AbstractTimeSource)localObject).nanoTime());
    }
    long l;
    if (localObject == null) {
      l = System.nanoTime();
    } else {
      l = ((Long)localObject).longValue();
    }
    return l;
  }
  
  private static final void parkNanos(Object paramObject, long paramLong)
  {
    Object localObject = getTimeSource();
    if (localObject == null)
    {
      localObject = null;
    }
    else
    {
      ((AbstractTimeSource)localObject).parkNanos(paramObject, paramLong);
      localObject = Unit.INSTANCE;
    }
    if (localObject == null) {
      LockSupport.parkNanos(paramObject, paramLong);
    }
  }
  
  private static final void registerTimeLoopThread()
  {
    AbstractTimeSource localAbstractTimeSource = getTimeSource();
    if (localAbstractTimeSource != null) {
      localAbstractTimeSource.registerTimeLoopThread();
    }
  }
  
  public static final void setTimeSource(AbstractTimeSource paramAbstractTimeSource)
  {
    timeSource = paramAbstractTimeSource;
  }
  
  private static final void trackTask()
  {
    AbstractTimeSource localAbstractTimeSource = getTimeSource();
    if (localAbstractTimeSource != null) {
      localAbstractTimeSource.trackTask();
    }
  }
  
  private static final void unTrackTask()
  {
    AbstractTimeSource localAbstractTimeSource = getTimeSource();
    if (localAbstractTimeSource != null) {
      localAbstractTimeSource.unTrackTask();
    }
  }
  
  private static final void unpark(Thread paramThread)
  {
    Object localObject = getTimeSource();
    if (localObject == null)
    {
      localObject = null;
    }
    else
    {
      ((AbstractTimeSource)localObject).unpark(paramThread);
      localObject = Unit.INSTANCE;
    }
    if (localObject == null) {
      LockSupport.unpark(paramThread);
    }
  }
  
  private static final void unregisterTimeLoopThread()
  {
    AbstractTimeSource localAbstractTimeSource = getTimeSource();
    if (localAbstractTimeSource != null) {
      localAbstractTimeSource.unregisterTimeLoopThread();
    }
  }
  
  private static final Runnable wrapTask(Runnable paramRunnable)
  {
    Object localObject = getTimeSource();
    if (localObject != null) {
      for (;;)
      {
        Runnable localRunnable = ((AbstractTimeSource)localObject).wrapTask(paramRunnable);
        localObject = localRunnable;
        if (localRunnable != null) {
          break;
        }
      }
    }
    localObject = paramRunnable;
    return (Runnable)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/AbstractTimeSourceKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */