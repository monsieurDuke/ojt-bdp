package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000\"\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\b\n\002\b\002\n\002\020(\n\000\b\000\030\000*\004\b\000\020\001*\004\b\001\020\0022\b\022\004\022\002H\0020\003B-\022\f\020\004\032\b\022\004\022\0028\0000\003\022\030\020\005\032\024\022\004\022\0020\007\022\004\022\0028\000\022\004\022\0028\0010\006¢\006\002\020\bJ\017\020\t\032\b\022\004\022\0028\0010\nH\002R\024\020\004\032\b\022\004\022\0028\0000\003X\004¢\006\002\n\000R \020\005\032\024\022\004\022\0020\007\022\004\022\0028\000\022\004\022\0028\0010\006X\004¢\006\002\n\000¨\006\013"}, d2={"Lkotlin/sequences/TransformingIndexedSequence;", "T", "R", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function2;", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class TransformingIndexedSequence<T, R>
  implements Sequence<R>
{
  private final Sequence<T> sequence;
  private final Function2<Integer, T, R> transformer;
  
  public TransformingIndexedSequence(Sequence<? extends T> paramSequence, Function2<? super Integer, ? super T, ? extends R> paramFunction2)
  {
    this.sequence = paramSequence;
    this.transformer = paramFunction2;
  }
  
  public Iterator<R> iterator()
  {
    (Iterator)new Iterator()
    {
      private int index;
      private final Iterator<T> iterator;
      final TransformingIndexedSequence<T, R> this$0;
      
      public final int getIndex()
      {
        return this.index;
      }
      
      public final Iterator<T> getIterator()
      {
        return this.iterator;
      }
      
      public boolean hasNext()
      {
        return this.iterator.hasNext();
      }
      
      public R next()
      {
        Function2 localFunction2 = TransformingIndexedSequence.access$getTransformer$p(this.this$0);
        int i = this.index;
        this.index = (i + 1);
        if (i < 0) {
          CollectionsKt.throwIndexOverflow();
        }
        return (R)localFunction2.invoke(Integer.valueOf(i), this.iterator.next());
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/TransformingIndexedSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */