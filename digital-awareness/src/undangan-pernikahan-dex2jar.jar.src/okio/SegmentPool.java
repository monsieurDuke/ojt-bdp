package okio;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\b\006\n\002\020\021\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\003\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\020\032\n\022\006\022\004\030\0010\0060\016H\002J\020\020\021\032\0020\0222\006\020\023\032\0020\006H\007J\b\020\024\032\0020\006H\007R\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000R\024\020\007\032\0020\004XD¢\006\b\n\000\032\004\b\b\020\tR\021\020\n\032\0020\0048F¢\006\006\032\004\b\013\020\tR\036\020\f\032\020\022\f\022\n\022\006\022\004\030\0010\0060\0160\rX\004¢\006\004\n\002\020\017¨\006\025"}, d2={"Lokio/SegmentPool;", "", "()V", "HASH_BUCKET_COUNT", "", "LOCK", "Lokio/Segment;", "MAX_SIZE", "getMAX_SIZE", "()I", "byteCount", "getByteCount", "hashBuckets", "", "Ljava/util/concurrent/atomic/AtomicReference;", "[Ljava/util/concurrent/atomic/AtomicReference;", "firstRef", "recycle", "", "segment", "take", "okio"}, k=1, mv={1, 4, 0})
public final class SegmentPool
{
  private static final int HASH_BUCKET_COUNT;
  public static final SegmentPool INSTANCE = new SegmentPool();
  private static final Segment LOCK;
  private static final int MAX_SIZE = 65536;
  private static final AtomicReference<Segment>[] hashBuckets;
  
  static
  {
    int i = 0;
    LOCK = new Segment(new byte[0], 0, 0, false, false);
    int j = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() * 2 - 1);
    HASH_BUCKET_COUNT = j;
    AtomicReference[] arrayOfAtomicReference = new AtomicReference[j];
    while (i < j)
    {
      arrayOfAtomicReference[i] = new AtomicReference();
      i++;
    }
    hashBuckets = arrayOfAtomicReference;
  }
  
  private final AtomicReference<Segment> firstRef()
  {
    Thread localThread = Thread.currentThread();
    Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
    int i = (int)(localThread.getId() & HASH_BUCKET_COUNT - 1L);
    return hashBuckets[i];
  }
  
  @JvmStatic
  public static final void recycle(Segment paramSegment)
  {
    Intrinsics.checkNotNullParameter(paramSegment, "segment");
    int i;
    if ((paramSegment.next == null) && (paramSegment.prev == null)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramSegment.shared) {
        return;
      }
      AtomicReference localAtomicReference = INSTANCE.firstRef();
      Segment localSegment = (Segment)localAtomicReference.get();
      if (localSegment == LOCK) {
        return;
      }
      if (localSegment != null) {
        i = localSegment.limit;
      } else {
        i = 0;
      }
      if (i >= MAX_SIZE) {
        return;
      }
      paramSegment.next = localSegment;
      paramSegment.pos = 0;
      paramSegment.limit = (i + 8192);
      if (!SegmentPool..ExternalSyntheticBackportWithForwarding0.m(localAtomicReference, localSegment, paramSegment))
      {
        localSegment = (Segment)null;
        paramSegment.next = null;
      }
      return;
    }
    throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
  }
  
  @JvmStatic
  public static final Segment take()
  {
    Object localObject = INSTANCE.firstRef();
    Segment localSegment2 = LOCK;
    Segment localSegment1 = (Segment)((AtomicReference)localObject).getAndSet(localSegment2);
    if (localSegment1 == localSegment2) {
      return new Segment();
    }
    if (localSegment1 == null)
    {
      ((AtomicReference)localObject).set(null);
      return new Segment();
    }
    ((AtomicReference)localObject).set(localSegment1.next);
    localObject = (Segment)null;
    localSegment1.next = null;
    localSegment1.limit = 0;
    return localSegment1;
  }
  
  public final int getByteCount()
  {
    Segment localSegment = (Segment)firstRef().get();
    if (localSegment != null) {
      return localSegment.limit;
    }
    return 0;
  }
  
  public final int getMAX_SIZE()
  {
    return MAX_SIZE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/SegmentPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */