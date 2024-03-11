package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;

@Metadata(d1={"\000*\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\003\n\002\020\002\n\002\b\r\n\002\030\002\b \030\000*\016\b\000\020\001*\b\022\004\022\0028\0000\0002\b\022\004\022\0028\0000\033B!\022\006\020\003\032\0020\002\022\b\020\004\032\004\030\0018\000\022\006\020\006\032\0020\005¢\006\004\b\007\020\bJ\017\020\f\032\0020\tH\000¢\006\004\b\n\020\013J\r\020\016\032\0020\r¢\006\004\b\016\020\017J\017\020\021\032\0020\tH\000¢\006\004\b\020\020\013R\027\020\003\032\0020\0028\006¢\006\f\n\004\b\003\020\022\032\004\b\023\020\024R\024\020\027\032\0020\0058&X¦\004¢\006\006\032\004\b\025\020\026R\024\020\031\032\0020\t8VX\004¢\006\006\032\004\b\030\020\013¨\006\032"}, d2={"Lkotlinx/coroutines/internal/Segment;", "S", "", "id", "prev", "", "pointers", "<init>", "(JLkotlinx/coroutines/internal/Segment;I)V", "", "decPointers$kotlinx_coroutines_core", "()Z", "decPointers", "", "onSlotCleaned", "()V", "tryIncPointers$kotlinx_coroutines_core", "tryIncPointers", "J", "getId", "()J", "getMaxSlots", "()I", "maxSlots", "getRemoved", "removed", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class Segment<S extends Segment<S>>
  extends ConcurrentLinkedListNode<S>
{
  private static final AtomicIntegerFieldUpdater cleanedAndPointers$FU = AtomicIntegerFieldUpdater.newUpdater(Segment.class, "cleanedAndPointers");
  private volatile int cleanedAndPointers;
  private final long id;
  
  public Segment(long paramLong, S paramS, int paramInt)
  {
    super((ConcurrentLinkedListNode)paramS);
    this.id = paramLong;
    this.cleanedAndPointers = (paramInt << 16);
  }
  
  public final boolean decPointers$kotlinx_coroutines_core()
  {
    boolean bool;
    if ((cleanedAndPointers$FU.addAndGet(this, -65536) == getMaxSlots()) && (!isTail())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final long getId()
  {
    return this.id;
  }
  
  public abstract int getMaxSlots();
  
  public boolean getRemoved()
  {
    boolean bool;
    if ((this.cleanedAndPointers == getMaxSlots()) && (!isTail())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final void onSlotCleaned()
  {
    if ((cleanedAndPointers$FU.incrementAndGet(this) == getMaxSlots()) && (!isTail())) {
      remove();
    }
  }
  
  public final boolean tryIncPointers$kotlinx_coroutines_core()
  {
    int j;
    do
    {
      j = this.cleanedAndPointers;
      int i = getMaxSlots();
      bool = false;
      if ((j == i) && (!isTail())) {
        i = 0;
      } else {
        i = 1;
      }
      if (i == 0) {
        break;
      }
    } while (!cleanedAndPointers$FU.compareAndSet(this, j, j + 65536));
    boolean bool = true;
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/Segment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */