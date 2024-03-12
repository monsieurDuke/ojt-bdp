package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\0008\n\002\030\002\n\002\020\t\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020\002\n\002\b\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\b\n\002\020\016\n\002\b\006\n\002\030\002\b\002\030\0002\b\022\004\022\0020\0000\037B!\022\006\020\002\032\0020\001\022\b\020\003\032\004\030\0010\000\022\006\020\005\032\0020\004¢\006\004\b\006\020\007J\025\020\n\032\0020\t2\006\020\b\032\0020\004¢\006\004\b\n\020\013J,\020\020\032\0020\0172\006\020\b\032\0020\0042\b\020\r\032\004\030\0010\f2\b\020\016\032\004\030\0010\fH\b¢\006\004\b\020\020\021J\032\020\022\032\004\030\0010\f2\006\020\b\032\0020\004H\b¢\006\004\b\022\020\023J$\020\024\032\004\030\0010\f2\006\020\b\032\0020\0042\b\020\016\032\004\030\0010\fH\b¢\006\004\b\024\020\025J\"\020\026\032\0020\t2\006\020\b\032\0020\0042\b\020\016\032\004\030\0010\fH\b¢\006\004\b\026\020\027J\017\020\031\032\0020\030H\026¢\006\004\b\031\020\032R\024\020\035\032\0020\0048VX\004¢\006\006\032\004\b\033\020\034¨\006\036"}, d2={"Lkotlinx/coroutines/sync/SemaphoreSegment;", "", "id", "prev", "", "pointers", "<init>", "(JLkotlinx/coroutines/sync/SemaphoreSegment;I)V", "index", "", "cancel", "(I)V", "", "expected", "value", "", "cas", "(ILjava/lang/Object;Ljava/lang/Object;)Z", "get", "(I)Ljava/lang/Object;", "getAndSet", "(ILjava/lang/Object;)Ljava/lang/Object;", "set", "(ILjava/lang/Object;)V", "", "toString", "()Ljava/lang/String;", "getMaxSlots", "()I", "maxSlots", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/Segment;"}, k=1, mv={1, 6, 0}, xi=48)
final class SemaphoreSegment
  extends Segment<SemaphoreSegment>
{
  AtomicReferenceArray acquirers = new AtomicReferenceArray(SemaphoreKt.access$getSEGMENT_SIZE$p());
  
  public SemaphoreSegment(long paramLong, SemaphoreSegment paramSemaphoreSegment, int paramInt)
  {
    super(paramLong, (Segment)paramSemaphoreSegment, paramInt);
  }
  
  public final void cancel(int paramInt)
  {
    Symbol localSymbol = SemaphoreKt.access$getCANCELLED$p();
    this.acquirers.set(paramInt, localSymbol);
    onSlotCleaned();
  }
  
  public final boolean cas(int paramInt, Object paramObject1, Object paramObject2)
  {
    return ConcurrentWeakMap.Core..ExternalSyntheticBackportWithForwarding0.m(this.acquirers, paramInt, paramObject1, paramObject2);
  }
  
  public final Object get(int paramInt)
  {
    return this.acquirers.get(paramInt);
  }
  
  public final Object getAndSet(int paramInt, Object paramObject)
  {
    return this.acquirers.getAndSet(paramInt, paramObject);
  }
  
  public int getMaxSlots()
  {
    return SemaphoreKt.access$getSEGMENT_SIZE$p();
  }
  
  public final void set(int paramInt, Object paramObject)
  {
    this.acquirers.set(paramInt, paramObject);
  }
  
  public String toString()
  {
    return "SemaphoreSegment[id=" + getId() + ", hashCode=" + hashCode() + ']';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/sync/SemaphoreSegment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */