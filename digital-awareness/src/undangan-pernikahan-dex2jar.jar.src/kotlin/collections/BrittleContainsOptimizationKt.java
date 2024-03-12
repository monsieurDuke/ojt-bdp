package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(d1={"\000 \n\000\n\002\020\036\n\000\n\002\020\021\n\000\n\002\020\034\n\002\030\002\n\002\b\003\n\002\020\013\n\000\032#\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\000¢\006\002\020\004\032\036\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\005H\000\032\036\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\006H\000\032,\020\007\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0052\f\020\b\032\b\022\004\022\002H\0020\005H\000\032\030\020\t\032\0020\n\"\004\b\000\020\002*\b\022\004\022\002H\0020\001H\002¨\006\013"}, d2={"convertToSetForSetOperation", "", "T", "", "([Ljava/lang/Object;)Ljava/util/Collection;", "", "Lkotlin/sequences/Sequence;", "convertToSetForSetOperationWith", "source", "safeToConvertToSet", "", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class BrittleContainsOptimizationKt
{
  public static final <T> Collection<T> convertToSetForSetOperation(Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    if ((paramIterable instanceof Set))
    {
      paramIterable = (Collection)paramIterable;
    }
    else if ((paramIterable instanceof Collection))
    {
      if (safeToConvertToSet((Collection)paramIterable)) {
        paramIterable = (Collection)CollectionsKt.toHashSet(paramIterable);
      } else {
        paramIterable = (Collection)paramIterable;
      }
    }
    else
    {
      if (CollectionSystemProperties.brittleContainsOptimizationEnabled) {
        paramIterable = CollectionsKt.toHashSet(paramIterable);
      } else {
        paramIterable = CollectionsKt.toList(paramIterable);
      }
      paramIterable = (Collection)paramIterable;
    }
    return paramIterable;
  }
  
  public static final <T> Collection<T> convertToSetForSetOperation(Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    if (CollectionSystemProperties.brittleContainsOptimizationEnabled) {
      paramSequence = SequencesKt.toHashSet(paramSequence);
    } else {
      paramSequence = SequencesKt.toList(paramSequence);
    }
    return (Collection)paramSequence;
  }
  
  public static final <T> Collection<T> convertToSetForSetOperation(T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "<this>");
    if (CollectionSystemProperties.brittleContainsOptimizationEnabled) {
      paramArrayOfT = ArraysKt.toHashSet(paramArrayOfT);
    } else {
      paramArrayOfT = ArraysKt.asList(paramArrayOfT);
    }
    return (Collection)paramArrayOfT;
  }
  
  public static final <T> Collection<T> convertToSetForSetOperationWith(Iterable<? extends T> paramIterable1, Iterable<? extends T> paramIterable2)
  {
    Intrinsics.checkNotNullParameter(paramIterable1, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable2, "source");
    if ((paramIterable1 instanceof Set))
    {
      paramIterable1 = (Collection)paramIterable1;
    }
    else if ((paramIterable1 instanceof Collection))
    {
      if (((paramIterable2 instanceof Collection)) && (((Collection)paramIterable2).size() < 2)) {
        paramIterable1 = (Collection)paramIterable1;
      } else if (safeToConvertToSet((Collection)paramIterable1)) {
        paramIterable1 = (Collection)CollectionsKt.toHashSet(paramIterable1);
      } else {
        paramIterable1 = (Collection)paramIterable1;
      }
    }
    else
    {
      if (CollectionSystemProperties.brittleContainsOptimizationEnabled) {
        paramIterable1 = CollectionsKt.toHashSet(paramIterable1);
      } else {
        paramIterable1 = CollectionsKt.toList(paramIterable1);
      }
      paramIterable1 = (Collection)paramIterable1;
    }
    return paramIterable1;
  }
  
  private static final <T> boolean safeToConvertToSet(Collection<? extends T> paramCollection)
  {
    boolean bool;
    if ((CollectionSystemProperties.brittleContainsOptimizationEnabled) && (paramCollection.size() > 2) && ((paramCollection instanceof ArrayList))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/BrittleContainsOptimizationKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */