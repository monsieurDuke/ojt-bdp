package kotlin.collections;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(d1={"\000\030\n\000\n\002\020 \n\000\n\002\020!\n\002\b\002\n\002\020\b\n\002\b\005\032\034\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\001\032#\020\000\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\007¢\006\002\b\004\032\035\020\005\032\0020\006*\006\022\002\b\0030\0012\006\020\007\032\0020\006H\002¢\006\002\b\b\032\035\020\t\032\0020\006*\006\022\002\b\0030\0012\006\020\007\032\0020\006H\002¢\006\002\b\n¨\006\013"}, d2={"asReversed", "", "T", "", "asReversedMutable", "reverseElementIndex", "", "index", "reverseElementIndex$CollectionsKt__ReversedViewsKt", "reversePositionIndex", "reversePositionIndex$CollectionsKt__ReversedViewsKt", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__ReversedViewsKt
  extends CollectionsKt__MutableCollectionsKt
{
  public static final <T> List<T> asReversed(List<? extends T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    return (List)new ReversedListReadOnly(paramList);
  }
  
  public static final <T> List<T> asReversedMutable(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    return (List)new ReversedList(paramList);
  }
  
  private static final int reverseElementIndex$CollectionsKt__ReversedViewsKt(List<?> paramList, int paramInt)
  {
    if (new IntRange(0, CollectionsKt.getLastIndex(paramList)).contains(paramInt)) {
      return CollectionsKt.getLastIndex(paramList) - paramInt;
    }
    throw new IndexOutOfBoundsException("Element index " + paramInt + " must be in range [" + new IntRange(0, CollectionsKt.getLastIndex(paramList)) + "].");
  }
  
  private static final int reversePositionIndex$CollectionsKt__ReversedViewsKt(List<?> paramList, int paramInt)
  {
    if (new IntRange(0, paramList.size()).contains(paramInt)) {
      return paramList.size() - paramInt;
    }
    throw new IndexOutOfBoundsException("Position index " + paramInt + " must be in range [" + new IntRange(0, paramList.size()) + "].");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt__ReversedViewsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */