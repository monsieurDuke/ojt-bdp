package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\000\"\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\001\n\000\n\002\020\000\n\000\032\b\020\b\032\0020\tH\002\032\016\020\n\032\0020\003*\004\030\0010\013H\002\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\016\020\002\032\0020\003X\004¢\006\002\n\000\"\016\020\004\032\0020\003X\004¢\006\002\n\000\"\016\020\005\032\0020\001XT¢\006\002\n\000\"\016\020\006\032\0020\007X\004¢\006\002\n\000¨\006\f"}, d2={"MAGIC", "", "MARKED_NULL", "Lkotlinx/coroutines/debug/internal/Marked;", "MARKED_TRUE", "MIN_CAPACITY", "REHASH", "Lkotlinx/coroutines/internal/Symbol;", "noImpl", "", "mark", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ConcurrentWeakMapKt
{
  private static final int MAGIC = -1640531527;
  private static final Marked MARKED_NULL = new Marked(null);
  private static final Marked MARKED_TRUE = new Marked(Boolean.valueOf(true));
  private static final int MIN_CAPACITY = 16;
  private static final Symbol REHASH = new Symbol("REHASH");
  
  private static final Marked mark(Object paramObject)
  {
    if (paramObject == null) {
      paramObject = MARKED_NULL;
    } else if (Intrinsics.areEqual(paramObject, Boolean.valueOf(true))) {
      paramObject = MARKED_TRUE;
    } else {
      paramObject = new Marked(paramObject);
    }
    return (Marked)paramObject;
  }
  
  private static final Void noImpl()
  {
    throw new UnsupportedOperationException("not implemented");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/ConcurrentWeakMapKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */