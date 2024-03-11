package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000\034\n\002\030\002\n\000\n\002\020\034\n\002\030\002\n\000\n\002\030\002\n\002\020(\n\002\b\003\b\000\030\000*\006\b\000\020\001 \0012\016\022\n\022\b\022\004\022\002H\0010\0030\002B\031\022\022\020\004\032\016\022\n\022\b\022\004\022\0028\0000\0060\005¢\006\002\020\007J\025\020\b\032\016\022\n\022\b\022\004\022\0028\0000\0030\006H\002R\032\020\004\032\016\022\n\022\b\022\004\022\0028\0000\0060\005X\004¢\006\002\n\000¨\006\t"}, d2={"Lkotlin/collections/IndexingIterable;", "T", "", "Lkotlin/collections/IndexedValue;", "iteratorFactory", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)V", "iterator", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class IndexingIterable<T>
  implements Iterable<IndexedValue<? extends T>>, KMappedMarker
{
  private final Function0<Iterator<T>> iteratorFactory;
  
  public IndexingIterable(Function0<? extends Iterator<? extends T>> paramFunction0)
  {
    this.iteratorFactory = paramFunction0;
  }
  
  public Iterator<IndexedValue<T>> iterator()
  {
    return (Iterator)new IndexingIterator((Iterator)this.iteratorFactory.invoke());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/IndexingIterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */