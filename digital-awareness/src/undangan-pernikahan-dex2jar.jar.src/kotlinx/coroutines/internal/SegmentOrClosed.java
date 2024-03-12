package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000*\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\b\004\n\002\020\013\n\002\b\f\n\002\020\b\n\002\b\003\n\002\020\016\n\002\b\003\b@\030\000*\016\b\000\020\001*\b\022\004\022\002H\0010\0022\0020\003B\024\022\b\020\004\032\004\030\0010\003ø\001\000¢\006\004\b\005\020\006J\032\020\020\032\0020\b2\b\020\021\032\004\030\0010\003HÖ\003¢\006\004\b\022\020\023J\020\020\024\032\0020\025HÖ\001¢\006\004\b\026\020\027J\020\020\030\032\0020\031HÖ\001¢\006\004\b\032\020\033R\021\020\007\032\0020\b8F¢\006\006\032\004\b\t\020\nR\027\020\013\032\0028\0008F¢\006\f\022\004\b\f\020\r\032\004\b\016\020\017R\020\020\004\032\004\030\0010\003X\004¢\006\002\n\000\001\004\001\004\030\0010\003ø\001\000\002\004\n\002\b\031¨\006\034"}, d2={"Lkotlinx/coroutines/internal/SegmentOrClosed;", "S", "Lkotlinx/coroutines/internal/Segment;", "", "value", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "isClosed", "", "isClosed-impl", "(Ljava/lang/Object;)Z", "segment", "getSegment$annotations", "()V", "getSegment-impl", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/Segment;", "equals", "other", "equals-impl", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(Ljava/lang/Object;)I", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
@JvmInline
public final class SegmentOrClosed<S extends Segment<S>>
{
  private final Object value;
  
  public static <S extends Segment<S>> Object constructor-impl(Object paramObject)
  {
    return paramObject;
  }
  
  public static boolean equals-impl(Object paramObject1, Object paramObject2)
  {
    if (!(paramObject2 instanceof SegmentOrClosed)) {
      return false;
    }
    return Intrinsics.areEqual(paramObject1, ((SegmentOrClosed)paramObject2).unbox-impl());
  }
  
  public static final boolean equals-impl0(Object paramObject1, Object paramObject2)
  {
    return Intrinsics.areEqual(paramObject1, paramObject2);
  }
  
  public static final S getSegment-impl(Object paramObject)
  {
    if (paramObject != ConcurrentLinkedListKt.access$getCLOSED$p())
    {
      if (paramObject != null) {
        return (Segment)paramObject;
      }
      throw new NullPointerException("null cannot be cast to non-null type S of kotlinx.coroutines.internal.SegmentOrClosed");
    }
    throw new IllegalStateException("Does not contain segment".toString());
  }
  
  public static int hashCode-impl(Object paramObject)
  {
    int i;
    if (paramObject == null) {
      i = 0;
    } else {
      i = paramObject.hashCode();
    }
    return i;
  }
  
  public static final boolean isClosed-impl(Object paramObject)
  {
    boolean bool;
    if (paramObject == ConcurrentLinkedListKt.access$getCLOSED$p()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static String toString-impl(Object paramObject)
  {
    return "SegmentOrClosed(value=" + paramObject + ')';
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.value, paramObject);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.value);
  }
  
  public String toString()
  {
    String str = toString-impl(this.value);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/SegmentOrClosed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */