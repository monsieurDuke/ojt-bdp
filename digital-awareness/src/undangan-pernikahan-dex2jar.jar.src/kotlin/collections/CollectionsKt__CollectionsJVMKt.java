package kotlin.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.builders.ListBuilder;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000R\n\000\n\002\020\013\n\000\n\002\020 \n\002\b\002\n\002\020!\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\005\n\002\020\021\n\002\020\000\n\000\n\002\020\036\n\002\b\f\n\002\020\034\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032\t\020\000\032\0020\001H\b\032\"\020\002\032\b\022\004\022\002H\0040\003\"\004\b\000\020\0042\f\020\005\032\b\022\004\022\002H\0040\006H\001\032?\020\007\032\b\022\004\022\002H\0040\003\"\004\b\000\020\0042\006\020\b\032\0020\t2\035\020\n\032\031\022\n\022\b\022\004\022\002H\0040\006\022\004\022\0020\f0\013¢\006\002\b\rH\bø\001\000\0327\020\007\032\b\022\004\022\002H\0040\003\"\004\b\000\020\0042\035\020\n\032\031\022\n\022\b\022\004\022\002H\0040\006\022\004\022\0020\f0\013¢\006\002\b\rH\bø\001\000\032\021\020\016\032\0020\t2\006\020\017\032\0020\tH\b\032\021\020\020\032\0020\t2\006\020\021\032\0020\tH\b\032\"\020\022\032\n\022\006\022\004\030\0010\0240\0232\n\020\025\032\006\022\002\b\0030\026H\b¢\006\002\020\027\0324\020\022\032\b\022\004\022\002H\0300\023\"\004\b\000\020\0302\n\020\025\032\006\022\002\b\0030\0262\f\020\031\032\b\022\004\022\002H\0300\023H\b¢\006\002\020\032\032\024\020\033\032\b\022\004\022\002H\0040\006\"\004\b\000\020\004H\001\032\034\020\033\032\b\022\004\022\002H\0040\006\"\004\b\000\020\0042\006\020\b\032\0020\tH\001\032\037\020\034\032\b\022\004\022\002H\0300\003\"\004\b\000\020\0302\006\020\035\032\002H\030¢\006\002\020\036\0321\020\037\032\f\022\b\b\001\022\004\030\0010\0240\023\"\004\b\000\020\030*\n\022\006\b\001\022\002H\0300\0232\006\020 \032\0020\001H\000¢\006\002\020!\032\036\020\"\032\b\022\004\022\002H\0300\003\"\004\b\000\020\030*\b\022\004\022\002H\0300#H\007\032&\020\"\032\b\022\004\022\002H\0300\003\"\004\b\000\020\030*\b\022\004\022\002H\0300#2\006\020$\032\0020%H\007\032\037\020&\032\b\022\004\022\002H\0300\003\"\004\b\000\020\030*\b\022\004\022\002H\0300'H\b\002\007\n\005\b20\001¨\006("}, d2={"brittleContainsOptimizationEnabled", "", "build", "", "E", "builder", "", "buildListInternal", "capacity", "", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "checkCountOverflow", "count", "checkIndexOverflow", "index", "copyToArrayImpl", "", "", "collection", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "T", "array", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "createListBuilder", "listOf", "element", "(Ljava/lang/Object;)Ljava/util/List;", "copyToArrayOfAny", "isVarargs", "([Ljava/lang/Object;Z)[Ljava/lang/Object;", "shuffled", "", "random", "Ljava/util/Random;", "toList", "Ljava/util/Enumeration;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__CollectionsJVMKt
{
  public static final boolean brittleContainsOptimizationEnabled()
  {
    return CollectionSystemProperties.brittleContainsOptimizationEnabled;
  }
  
  public static final <E> List<E> build(List<E> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "builder");
    return ((ListBuilder)paramList).build();
  }
  
  private static final <E> List<E> buildListInternal(int paramInt, Function1<? super List<E>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    List localList = CollectionsKt.createListBuilder(paramInt);
    paramFunction1.invoke(localList);
    return CollectionsKt.build(localList);
  }
  
  private static final <E> List<E> buildListInternal(Function1<? super List<E>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    List localList = CollectionsKt.createListBuilder();
    paramFunction1.invoke(localList);
    return CollectionsKt.build(localList);
  }
  
  private static final int checkCountOverflow(int paramInt)
  {
    if (paramInt < 0) {
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
        CollectionsKt.throwCountOverflow();
      } else {
        throw new ArithmeticException("Count overflow has happened.");
      }
    }
    return paramInt;
  }
  
  private static final int checkIndexOverflow(int paramInt)
  {
    if (paramInt < 0) {
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
        CollectionsKt.throwIndexOverflow();
      } else {
        throw new ArithmeticException("Index overflow has happened.");
      }
    }
    return paramInt;
  }
  
  private static final Object[] copyToArrayImpl(Collection<?> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "collection");
    return CollectionToArray.toArray(paramCollection);
  }
  
  private static final <T> T[] copyToArrayImpl(Collection<?> paramCollection, T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "collection");
    Intrinsics.checkNotNullParameter(paramArrayOfT, "array");
    paramCollection = CollectionToArray.toArray(paramCollection, paramArrayOfT);
    Intrinsics.checkNotNull(paramCollection, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.CollectionsKt__CollectionsJVMKt.copyToArrayImpl>");
    return paramCollection;
  }
  
  public static final <T> Object[] copyToArrayOfAny(T[] paramArrayOfT, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "<this>");
    if ((!paramBoolean) || (!Intrinsics.areEqual(paramArrayOfT.getClass(), Object[].class)))
    {
      paramArrayOfT = Arrays.copyOf(paramArrayOfT, paramArrayOfT.length, Object[].class);
      Intrinsics.checkNotNullExpressionValue(paramArrayOfT, "copyOf(this, this.size, Array<Any?>::class.java)");
    }
    return paramArrayOfT;
  }
  
  public static final <E> List<E> createListBuilder()
  {
    return (List)new ListBuilder();
  }
  
  public static final <E> List<E> createListBuilder(int paramInt)
  {
    return (List)new ListBuilder(paramInt);
  }
  
  public static final <T> List<T> listOf(T paramT)
  {
    paramT = Collections.singletonList(paramT);
    Intrinsics.checkNotNullExpressionValue(paramT, "singletonList(element)");
    return paramT;
  }
  
  public static final <T> List<T> shuffled(Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    paramIterable = CollectionsKt.toMutableList(paramIterable);
    Collections.shuffle(paramIterable);
    return paramIterable;
  }
  
  public static final <T> List<T> shuffled(Iterable<? extends T> paramIterable, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    paramIterable = CollectionsKt.toMutableList(paramIterable);
    Collections.shuffle(paramIterable, paramRandom);
    return paramIterable;
  }
  
  private static final <T> List<T> toList(Enumeration<T> paramEnumeration)
  {
    Intrinsics.checkNotNullParameter(paramEnumeration, "<this>");
    paramEnumeration = Collections.list(paramEnumeration);
    Intrinsics.checkNotNullExpressionValue(paramEnumeration, "list(this)");
    return (List)paramEnumeration;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt__CollectionsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */