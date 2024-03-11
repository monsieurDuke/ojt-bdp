package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\000\n\002\020\002\n\000\n\002\020(\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\0320\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H\bø\001\000\032\037\020\006\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\n\032\"\020\007\032\016\022\n\022\b\022\004\022\002H\0020\b0\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\002\007\n\005\b20\001¨\006\t"}, d2={"forEach", "", "T", "", "operation", "Lkotlin/Function1;", "iterator", "withIndex", "Lkotlin/collections/IndexedValue;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__IteratorsKt
  extends CollectionsKt__IteratorsJVMKt
{
  public static final <T> void forEach(Iterator<? extends T> paramIterator, Function1<? super T, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramIterator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "operation");
    while (paramIterator.hasNext()) {
      paramFunction1.invoke(paramIterator.next());
    }
  }
  
  private static final <T> Iterator<T> iterator(Iterator<? extends T> paramIterator)
  {
    Intrinsics.checkNotNullParameter(paramIterator, "<this>");
    return paramIterator;
  }
  
  public static final <T> Iterator<IndexedValue<T>> withIndex(Iterator<? extends T> paramIterator)
  {
    Intrinsics.checkNotNullParameter(paramIterator, "<this>");
    return (Iterator)new IndexingIterator(paramIterator);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt__IteratorsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */