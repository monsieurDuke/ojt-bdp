package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(d1={"\000\022\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\003\032\0020\004H\026¨\006\005"}, d2={"Lkotlinx/coroutines/scheduling/NanoTimeSource;", "Lkotlinx/coroutines/scheduling/SchedulerTimeSource;", "()V", "nanoTime", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class NanoTimeSource
  extends SchedulerTimeSource
{
  public static final NanoTimeSource INSTANCE = new NanoTimeSource();
  
  public long nanoTime()
  {
    return System.nanoTime();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/scheduling/NanoTimeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */