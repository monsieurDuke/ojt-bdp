package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(d1={"\000\034\n\000\n\002\020\"\n\002\b\004\n\002\020\021\n\000\n\002\020\034\n\002\030\002\n\002\b\004\032,\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H\002¢\006\002\020\004\0324\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\016\020\005\032\n\022\006\b\001\022\002H\0020\006H\002¢\006\002\020\007\032-\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\bH\002\032-\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\tH\002\032,\020\n\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H\b¢\006\002\020\004\032,\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H\002¢\006\002\020\004\0324\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\016\020\005\032\n\022\006\b\001\022\002H\0020\006H\002¢\006\002\020\007\032-\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\bH\002\032-\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\tH\002\032,\020\f\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H\b¢\006\002\020\004¨\006\r"}, d2={"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/SetsKt")
class SetsKt___SetsKt
  extends SetsKt__SetsKt
{
  public static final <T> Set<T> minus(Set<? extends T> paramSet, Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "elements");
    paramIterable = BrittleContainsOptimizationKt.convertToSetForSetOperationWith(paramIterable, (Iterable)paramSet);
    if (paramIterable.isEmpty()) {
      return CollectionsKt.toSet((Iterable)paramSet);
    }
    if ((paramIterable instanceof Set))
    {
      Object localObject = (Iterable)paramSet;
      paramSet = (Collection)new LinkedHashSet();
      Iterator localIterator = ((Iterable)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = localIterator.next();
        if (!paramIterable.contains(localObject)) {
          paramSet.add(localObject);
        }
      }
      return (Set)paramSet;
    }
    paramSet = new LinkedHashSet((Collection)paramSet);
    paramSet.removeAll(paramIterable);
    return (Set)paramSet;
  }
  
  public static final <T> Set<T> minus(Set<? extends T> paramSet, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(paramSet.size()));
    int i = 0;
    paramSet = ((Iterable)paramSet).iterator();
    while (paramSet.hasNext())
    {
      Object localObject = paramSet.next();
      int j;
      int k;
      if ((i == 0) && (Intrinsics.areEqual(localObject, paramT)))
      {
        j = 1;
        k = 0;
      }
      else
      {
        k = 1;
        j = i;
      }
      i = j;
      if (k != 0)
      {
        ((Collection)localLinkedHashSet).add(localObject);
        i = j;
      }
    }
    return (Set)localLinkedHashSet;
  }
  
  public static final <T> Set<T> minus(Set<? extends T> paramSet, Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "elements");
    paramSet = new LinkedHashSet((Collection)paramSet);
    CollectionsKt.removeAll((Collection)paramSet, paramSequence);
    return (Set)paramSet;
  }
  
  public static final <T> Set<T> minus(Set<? extends T> paramSet, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "elements");
    paramSet = new LinkedHashSet((Collection)paramSet);
    CollectionsKt.removeAll((Collection)paramSet, paramArrayOfT);
    return (Set)paramSet;
  }
  
  private static final <T> Set<T> minusElement(Set<? extends T> paramSet, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    return SetsKt.minus(paramSet, paramT);
  }
  
  public static final <T> Set<T> plus(Set<? extends T> paramSet, Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "elements");
    Object localObject = CollectionsKt.collectionSizeOrNull(paramIterable);
    int i;
    if (localObject != null)
    {
      i = ((Number)localObject).intValue();
      i = paramSet.size() + i;
    }
    else
    {
      i = paramSet.size() * 2;
    }
    localObject = new LinkedHashSet(MapsKt.mapCapacity(i));
    ((LinkedHashSet)localObject).addAll((Collection)paramSet);
    CollectionsKt.addAll((Collection)localObject, paramIterable);
    return (Set)localObject;
  }
  
  public static final <T> Set<T> plus(Set<? extends T> paramSet, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(paramSet.size() + 1));
    localLinkedHashSet.addAll((Collection)paramSet);
    localLinkedHashSet.add(paramT);
    return (Set)localLinkedHashSet;
  }
  
  public static final <T> Set<T> plus(Set<? extends T> paramSet, Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "elements");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(paramSet.size() * 2));
    localLinkedHashSet.addAll((Collection)paramSet);
    CollectionsKt.addAll((Collection)localLinkedHashSet, paramSequence);
    return (Set)localLinkedHashSet;
  }
  
  public static final <T> Set<T> plus(Set<? extends T> paramSet, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "elements");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(paramSet.size() + paramArrayOfT.length));
    localLinkedHashSet.addAll((Collection)paramSet);
    CollectionsKt.addAll((Collection)localLinkedHashSet, paramArrayOfT);
    return (Set)localLinkedHashSet;
  }
  
  private static final <T> Set<T> plusElement(Set<? extends T> paramSet, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    return SetsKt.plus(paramSet, paramT);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/SetsKt___SetsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */