package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000 \n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020(\n\002\b\002\b\000\030\000*\004\b\000\020\001*\004\b\001\020\0022\b\022\004\022\002H\0020\003B'\022\f\020\004\032\b\022\004\022\0028\0000\003\022\022\020\005\032\016\022\004\022\0028\000\022\004\022\0028\0010\006¢\006\002\020\007J3\020\b\032\b\022\004\022\002H\t0\003\"\004\b\002\020\t2\030\020\n\032\024\022\004\022\0028\001\022\n\022\b\022\004\022\002H\t0\0130\006H\000¢\006\002\b\fJ\017\020\n\032\b\022\004\022\0028\0010\013H\002R\024\020\004\032\b\022\004\022\0028\0000\003X\004¢\006\002\n\000R\032\020\005\032\016\022\004\022\0028\000\022\004\022\0028\0010\006X\004¢\006\002\n\000¨\006\r"}, d2={"Lkotlin/sequences/TransformingSequence;", "T", "R", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function1;", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "flatten", "E", "iterator", "", "flatten$kotlin_stdlib", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class TransformingSequence<T, R>
  implements Sequence<R>
{
  private final Sequence<T> sequence;
  private final Function1<T, R> transformer;
  
  public TransformingSequence(Sequence<? extends T> paramSequence, Function1<? super T, ? extends R> paramFunction1)
  {
    this.sequence = paramSequence;
    this.transformer = paramFunction1;
  }
  
  public final <E> Sequence<E> flatten$kotlin_stdlib(Function1<? super R, ? extends Iterator<? extends E>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "iterator");
    return (Sequence)new FlatteningSequence(this.sequence, this.transformer, paramFunction1);
  }
  
  public Iterator<R> iterator()
  {
    (Iterator)new Iterator()
    {
      private final Iterator<T> iterator;
      final TransformingSequence<T, R> this$0;
      
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
        return (R)TransformingSequence.access$getTransformer$p(this.this$0).invoke(this.iterator.next());
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/TransformingSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */