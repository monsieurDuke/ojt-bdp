package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;

@Metadata(d1={"\000R\n\000\n\002\020\013\n\000\n\002\020\037\n\000\n\002\020\021\n\000\n\002\020\034\n\002\030\002\n\000\n\002\020\035\n\000\n\002\030\002\n\002\b\002\n\002\020!\n\000\n\002\020\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\036\n\002\b\t\032-\020\000\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\016\020\004\032\n\022\006\b\001\022\002H\0020\005¢\006\002\020\006\032&\020\000\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007\032&\020\000\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\b\0329\020\t\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\n2\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f2\006\020\r\032\0020\001H\002¢\006\002\b\016\0329\020\t\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f2\006\020\r\032\0020\001H\002¢\006\002\b\016\032(\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\006\020\022\032\002H\002H\n¢\006\002\020\023\032.\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\005H\n¢\006\002\020\024\032)\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007H\n\032)\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\bH\n\032(\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\006\020\022\032\002H\002H\n¢\006\002\020\023\032.\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\005H\n¢\006\002\020\024\032)\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007H\n\032)\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\bH\n\032-\020\026\032\0020\001\"\t\b\000\020\002¢\006\002\b\027*\n\022\006\b\001\022\002H\0020\0032\006\020\022\032\002H\002H\b¢\006\002\020\030\032&\020\026\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\006\020\031\032\0020\032H\b¢\006\002\020\033\032-\020\034\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\016\020\004\032\n\022\006\b\001\022\002H\0020\005¢\006\002\020\006\032&\020\034\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007\032&\020\034\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\b\032.\020\034\032\0020\001\"\t\b\000\020\002¢\006\002\b\027*\n\022\006\b\001\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\035H\b\032*\020\034\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\n2\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032*\020\034\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032\035\020\036\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\017H\007¢\006\002\020\037\032\037\020 \032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\017H\007¢\006\002\020\037\032\035\020!\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\017H\007¢\006\002\020\037\032\037\020\"\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\017H\007¢\006\002\020\037\032-\020#\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\016\020\004\032\n\022\006\b\001\022\002H\0020\005¢\006\002\020\006\032&\020#\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007\032&\020#\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\b\032.\020#\032\0020\001\"\t\b\000\020\002¢\006\002\b\027*\n\022\006\b\001\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\035H\b\032*\020#\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\n2\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032*\020#\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032\025\020$\032\0020\001*\006\022\002\b\0030\003H\002¢\006\002\b%¨\006&"}, d2={"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "removeFirst", "(Ljava/util/List;)Ljava/lang/Object;", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__MutableCollectionsKt
  extends CollectionsKt__MutableCollectionsJVMKt
{
  public static final <T> boolean addAll(Collection<? super T> paramCollection, Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "elements");
    if ((paramIterable instanceof Collection)) {
      return paramCollection.addAll((Collection)paramIterable);
    }
    boolean bool = false;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      if (paramCollection.add(paramIterable.next())) {
        bool = true;
      }
    }
    return bool;
  }
  
  public static final <T> boolean addAll(Collection<? super T> paramCollection, Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "elements");
    boolean bool = false;
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      if (paramCollection.add(paramSequence.next())) {
        bool = true;
      }
    }
    return bool;
  }
  
  public static final <T> boolean addAll(Collection<? super T> paramCollection, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "elements");
    return paramCollection.addAll((Collection)ArraysKt.asList(paramArrayOfT));
  }
  
  private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(Iterable<? extends T> paramIterable, Function1<? super T, Boolean> paramFunction1, boolean paramBoolean)
  {
    boolean bool = false;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      if (((Boolean)paramFunction1.invoke(paramIterable.next())).booleanValue() == paramBoolean)
      {
        paramIterable.remove();
        bool = true;
      }
    }
    return bool;
  }
  
  private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(List<T> paramList, Function1<? super T, Boolean> paramFunction1, boolean paramBoolean)
  {
    if (!(paramList instanceof RandomAccess))
    {
      Intrinsics.checkNotNull(paramList, "null cannot be cast to non-null type kotlin.collections.MutableIterable<T of kotlin.collections.CollectionsKt__MutableCollectionsKt.filterInPlace>");
      return filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable(paramList), paramFunction1, paramBoolean);
    }
    int i = 0;
    IntIterator localIntIterator = new IntRange(0, CollectionsKt.getLastIndex(paramList)).iterator();
    int j;
    while (localIntIterator.hasNext())
    {
      j = localIntIterator.nextInt();
      Object localObject = paramList.get(j);
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue() != paramBoolean)
      {
        if (i != j) {
          paramList.set(i, localObject);
        }
        i++;
      }
    }
    if (i < paramList.size())
    {
      j = CollectionsKt.getLastIndex(paramList);
      if (i <= j) {
        for (;;)
        {
          paramList.remove(j);
          if (j == i) {
            break;
          }
          j--;
        }
      }
      return true;
    }
    return false;
  }
  
  private static final <T> void minusAssign(Collection<? super T> paramCollection, Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "elements");
    CollectionsKt.removeAll(paramCollection, paramIterable);
  }
  
  private static final <T> void minusAssign(Collection<? super T> paramCollection, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    paramCollection.remove(paramT);
  }
  
  private static final <T> void minusAssign(Collection<? super T> paramCollection, Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "elements");
    CollectionsKt.removeAll(paramCollection, paramSequence);
  }
  
  private static final <T> void minusAssign(Collection<? super T> paramCollection, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "elements");
    CollectionsKt.removeAll(paramCollection, paramArrayOfT);
  }
  
  private static final <T> void plusAssign(Collection<? super T> paramCollection, Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "elements");
    CollectionsKt.addAll(paramCollection, paramIterable);
  }
  
  private static final <T> void plusAssign(Collection<? super T> paramCollection, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    paramCollection.add(paramT);
  }
  
  private static final <T> void plusAssign(Collection<? super T> paramCollection, Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "elements");
    CollectionsKt.addAll(paramCollection, paramSequence);
  }
  
  private static final <T> void plusAssign(Collection<? super T> paramCollection, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "elements");
    CollectionsKt.addAll(paramCollection, paramArrayOfT);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use removeAt(index) instead.", replaceWith=@ReplaceWith(expression="removeAt(index)", imports={}))
  private static final <T> T remove(List<T> paramList, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    return (T)paramList.remove(paramInt);
  }
  
  private static final <T> boolean remove(Collection<? extends T> paramCollection, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    return TypeIntrinsics.asMutableCollection(paramCollection).remove(paramT);
  }
  
  public static final <T> boolean removeAll(Iterable<? extends T> paramIterable, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramIterable, paramFunction1, true);
  }
  
  public static final <T> boolean removeAll(Collection<? super T> paramCollection, Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "elements");
    paramIterable = BrittleContainsOptimizationKt.convertToSetForSetOperationWith(paramIterable, (Iterable)paramCollection);
    return TypeIntrinsics.asMutableCollection(paramCollection).removeAll(paramIterable);
  }
  
  private static final <T> boolean removeAll(Collection<? extends T> paramCollection1, Collection<? extends T> paramCollection2)
  {
    Intrinsics.checkNotNullParameter(paramCollection1, "<this>");
    Intrinsics.checkNotNullParameter(paramCollection2, "elements");
    return TypeIntrinsics.asMutableCollection(paramCollection1).removeAll(paramCollection2);
  }
  
  public static final <T> boolean removeAll(Collection<? super T> paramCollection, Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "elements");
    paramSequence = BrittleContainsOptimizationKt.convertToSetForSetOperation(paramSequence);
    boolean bool2 = paramSequence.isEmpty();
    boolean bool1 = true;
    if ((!(bool2 ^ true)) || (!paramCollection.removeAll(paramSequence))) {
      bool1 = false;
    }
    return bool1;
  }
  
  public static final <T> boolean removeAll(Collection<? super T> paramCollection, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "elements");
    int i = paramArrayOfT.length;
    boolean bool2 = false;
    if (i == 0) {
      i = 1;
    } else {
      i = 0;
    }
    boolean bool1 = bool2;
    if ((i ^ 0x1) != 0)
    {
      bool1 = bool2;
      if (paramCollection.removeAll(BrittleContainsOptimizationKt.convertToSetForSetOperation(paramArrayOfT))) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static final <T> boolean removeAll(List<T> paramList, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramList, paramFunction1, true);
  }
  
  public static final <T> T removeFirst(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    if (!paramList.isEmpty()) {
      return (T)paramList.remove(0);
    }
    throw new NoSuchElementException("List is empty.");
  }
  
  public static final <T> T removeFirstOrNull(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    if (paramList.isEmpty()) {
      paramList = null;
    } else {
      paramList = paramList.remove(0);
    }
    return paramList;
  }
  
  public static final <T> T removeLast(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    if (!paramList.isEmpty()) {
      return (T)paramList.remove(CollectionsKt.getLastIndex(paramList));
    }
    throw new NoSuchElementException("List is empty.");
  }
  
  public static final <T> T removeLastOrNull(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    if (paramList.isEmpty()) {
      paramList = null;
    } else {
      paramList = paramList.remove(CollectionsKt.getLastIndex(paramList));
    }
    return paramList;
  }
  
  public static final <T> boolean retainAll(Iterable<? extends T> paramIterable, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramIterable, paramFunction1, false);
  }
  
  public static final <T> boolean retainAll(Collection<? super T> paramCollection, Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "elements");
    paramIterable = BrittleContainsOptimizationKt.convertToSetForSetOperationWith(paramIterable, (Iterable)paramCollection);
    return TypeIntrinsics.asMutableCollection(paramCollection).retainAll(paramIterable);
  }
  
  private static final <T> boolean retainAll(Collection<? extends T> paramCollection1, Collection<? extends T> paramCollection2)
  {
    Intrinsics.checkNotNullParameter(paramCollection1, "<this>");
    Intrinsics.checkNotNullParameter(paramCollection2, "elements");
    return TypeIntrinsics.asMutableCollection(paramCollection1).retainAll(paramCollection2);
  }
  
  public static final <T> boolean retainAll(Collection<? super T> paramCollection, Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "elements");
    paramSequence = BrittleContainsOptimizationKt.convertToSetForSetOperation(paramSequence);
    if ((paramSequence.isEmpty() ^ true)) {
      return paramCollection.retainAll(paramSequence);
    }
    return retainNothing$CollectionsKt__MutableCollectionsKt(paramCollection);
  }
  
  public static final <T> boolean retainAll(Collection<? super T> paramCollection, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "elements");
    int i;
    if (paramArrayOfT.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i ^ 0x1) != 0) {
      return paramCollection.retainAll(BrittleContainsOptimizationKt.convertToSetForSetOperation(paramArrayOfT));
    }
    return retainNothing$CollectionsKt__MutableCollectionsKt(paramCollection);
  }
  
  public static final <T> boolean retainAll(List<T> paramList, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramList, paramFunction1, false);
  }
  
  private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(Collection<?> paramCollection)
  {
    boolean bool = paramCollection.isEmpty();
    paramCollection.clear();
    return bool ^ true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt__MutableCollectionsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */