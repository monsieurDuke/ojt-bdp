package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlinx.coroutines.CancelHandler;

@Metadata(d1={"\000*\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\023\020\007\032\0020\b2\b\020\t\032\004\030\0010\nH\002J\b\020\013\032\0020\fH\026R\016\020\004\032\0020\005X\004¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\r"}, d2={"Lkotlinx/coroutines/sync/CancelSemaphoreAcquisitionHandler;", "Lkotlinx/coroutines/CancelHandler;", "segment", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "index", "", "(Lkotlinx/coroutines/sync/SemaphoreSegment;I)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class CancelSemaphoreAcquisitionHandler
  extends CancelHandler
{
  private final int index;
  private final SemaphoreSegment segment;
  
  public CancelSemaphoreAcquisitionHandler(SemaphoreSegment paramSemaphoreSegment, int paramInt)
  {
    this.segment = paramSemaphoreSegment;
    this.index = paramInt;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.segment.cancel(this.index);
  }
  
  public String toString()
  {
    return "CancelSemaphoreAcquisitionHandler[" + this.segment + ", " + this.index + ']';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/sync/CancelSemaphoreAcquisitionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */