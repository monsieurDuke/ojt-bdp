package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(d1={"\0000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\002\b\002\n\002\020\002\n\002\b\007\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\b \030\0002\0020\001B\005¢\006\002\020\002J\b\020\003\032\0020\004H&J\b\020\005\032\0020\004H&J\030\020\006\032\0020\0072\006\020\b\032\0020\0012\006\020\t\032\0020\004H&J\b\020\n\032\0020\007H&J\b\020\013\032\0020\007H&J\b\020\f\032\0020\007H&J\020\020\r\032\0020\0072\006\020\016\032\0020\017H&J\b\020\020\032\0020\007H&J\030\020\021\032\0060\022j\002`\0232\n\020\024\032\0060\022j\002`\023H&¨\006\025"}, d2={"Lkotlinx/coroutines/AbstractTimeSource;", "", "()V", "currentTimeMillis", "", "nanoTime", "parkNanos", "", "blocker", "nanos", "registerTimeLoopThread", "trackTask", "unTrackTask", "unpark", "thread", "Ljava/lang/Thread;", "unregisterTimeLoopThread", "wrapTask", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AbstractTimeSource
{
  public abstract long currentTimeMillis();
  
  public abstract long nanoTime();
  
  public abstract void parkNanos(Object paramObject, long paramLong);
  
  public abstract void registerTimeLoopThread();
  
  public abstract void trackTask();
  
  public abstract void unTrackTask();
  
  public abstract void unpark(Thread paramThread);
  
  public abstract void unregisterTimeLoopThread();
  
  public abstract Runnable wrapTask(Runnable paramRunnable);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/AbstractTimeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */