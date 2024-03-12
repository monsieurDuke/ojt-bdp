package kotlin.collections;

import java.util.Enumeration;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000\016\n\000\n\002\020(\n\000\n\002\030\002\n\000\032\037\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\002¨\006\004"}, d2={"iterator", "", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__IteratorsJVMKt
  extends CollectionsKt__IterablesKt
{
  public static final <T> Iterator<T> iterator(Enumeration<T> paramEnumeration)
  {
    Intrinsics.checkNotNullParameter(paramEnumeration, "<this>");
    (Iterator)new Iterator()
    {
      final Enumeration<T> $this_iterator;
      
      public boolean hasNext()
      {
        return this.$this_iterator.hasMoreElements();
      }
      
      public T next()
      {
        return (T)this.$this_iterator.nextElement();
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt__IteratorsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */