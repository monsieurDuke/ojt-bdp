package kotlin.sequences;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;

@Metadata(d1={"\000.\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\002\n\002\b\004\n\002\020\034\n\002\b\002\n\002\020(\n\002\b\002\n\002\030\002\n\002\b\002\b'\030\000*\006\b\000\020\001 \0002\0020\002B\007\b\000¢\006\002\020\003J\031\020\004\032\0020\0052\006\020\006\032\0028\000H¦@ø\001\000¢\006\002\020\007J\037\020\b\032\0020\0052\f\020\t\032\b\022\004\022\0028\0000\nH@ø\001\000¢\006\002\020\013J\037\020\b\032\0020\0052\f\020\f\032\b\022\004\022\0028\0000\rH¦@ø\001\000¢\006\002\020\016J\037\020\b\032\0020\0052\f\020\017\032\b\022\004\022\0028\0000\020H@ø\001\000¢\006\002\020\021\002\004\n\002\b\031¨\006\022"}, d2={"Lkotlin/sequences/SequenceScope;", "T", "", "()V", "yield", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "yieldAll", "elements", "", "(Ljava/lang/Iterable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "iterator", "", "(Ljava/util/Iterator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sequence", "Lkotlin/sequences/Sequence;", "(Lkotlin/sequences/Sequence;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class SequenceScope<T>
{
  public abstract Object yield(T paramT, Continuation<? super Unit> paramContinuation);
  
  public final Object yieldAll(Iterable<? extends T> paramIterable, Continuation<? super Unit> paramContinuation)
  {
    if (((paramIterable instanceof Collection)) && (((Collection)paramIterable).isEmpty())) {
      return Unit.INSTANCE;
    }
    paramIterable = yieldAll(paramIterable.iterator(), paramContinuation);
    if (paramIterable == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramIterable;
    }
    return Unit.INSTANCE;
  }
  
  public abstract Object yieldAll(Iterator<? extends T> paramIterator, Continuation<? super Unit> paramContinuation);
  
  public final Object yieldAll(Sequence<? extends T> paramSequence, Continuation<? super Unit> paramContinuation)
  {
    paramSequence = yieldAll(paramSequence.iterator(), paramContinuation);
    if (paramSequence == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramSequence;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/SequenceScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */