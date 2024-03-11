package kotlin.collections;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000d\n\000\n\002\020 \n\000\n\002\020\034\n\000\n\002\030\002\n\002\b\002\n\002\020\037\n\002\b\004\n\002\020\017\n\000\n\002\020\006\n\000\n\002\020\007\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\020!\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032(\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\006\022\002\b\0030\0032\f\020\004\032\b\022\004\022\002H\0020\005\032A\020\006\032\002H\007\"\020\b\000\020\007*\n\022\006\b\000\022\002H\0020\b\"\004\b\001\020\002*\006\022\002\b\0030\0032\006\020\t\032\002H\0072\f\020\004\032\b\022\004\022\002H\0020\005¢\006\002\020\n\032)\020\013\032\004\030\001H\f\"\016\b\000\020\f*\b\022\004\022\002H\f0\r*\b\022\004\022\002H\f0\003H\007¢\006\002\020\016\032\031\020\013\032\004\030\0010\017*\b\022\004\022\0020\0170\003H\007¢\006\002\020\020\032\031\020\013\032\004\030\0010\021*\b\022\004\022\0020\0210\003H\007¢\006\002\020\022\032G\020\023\032\004\030\001H\f\"\004\b\000\020\f\"\016\b\001\020\002*\b\022\004\022\002H\0020\r*\b\022\004\022\002H\f0\0032\022\020\024\032\016\022\004\022\002H\f\022\004\022\002H\0020\025H\bø\001\000¢\006\002\020\026\032;\020\027\032\004\030\001H\f\"\004\b\000\020\f*\b\022\004\022\002H\f0\0032\032\020\030\032\026\022\006\b\000\022\002H\f0\031j\n\022\006\b\000\022\002H\f`\032H\007¢\006\002\020\033\032)\020\034\032\004\030\001H\f\"\016\b\000\020\f*\b\022\004\022\002H\f0\r*\b\022\004\022\002H\f0\003H\007¢\006\002\020\016\032\031\020\034\032\004\030\0010\017*\b\022\004\022\0020\0170\003H\007¢\006\002\020\020\032\031\020\034\032\004\030\0010\021*\b\022\004\022\0020\0210\003H\007¢\006\002\020\022\032G\020\035\032\004\030\001H\f\"\004\b\000\020\f\"\016\b\001\020\002*\b\022\004\022\002H\0020\r*\b\022\004\022\002H\f0\0032\022\020\024\032\016\022\004\022\002H\f\022\004\022\002H\0020\025H\bø\001\000¢\006\002\020\026\032;\020\036\032\004\030\001H\f\"\004\b\000\020\f*\b\022\004\022\002H\f0\0032\032\020\030\032\026\022\006\b\000\022\002H\f0\031j\n\022\006\b\000\022\002H\f`\032H\007¢\006\002\020\033\032\026\020\037\032\0020 \"\004\b\000\020\f*\b\022\004\022\002H\f0!\0325\020\"\032\0020#\"\004\b\000\020\f*\b\022\004\022\002H\f0\0032\022\020\024\032\016\022\004\022\002H\f\022\004\022\0020#0\025H\bø\001\000¢\006\002\b$\0325\020\"\032\0020%\"\004\b\000\020\f*\b\022\004\022\002H\f0\0032\022\020\024\032\016\022\004\022\002H\f\022\004\022\0020%0\025H\bø\001\000¢\006\002\b&\032&\020'\032\b\022\004\022\002H\f0(\"\016\b\000\020\f*\b\022\004\022\002H\f0\r*\b\022\004\022\002H\f0\003\0328\020'\032\b\022\004\022\002H\f0(\"\004\b\000\020\f*\b\022\004\022\002H\f0\0032\032\020\030\032\026\022\006\b\000\022\002H\f0\031j\n\022\006\b\000\022\002H\f`\032\002\007\n\005\b20\001¨\006)"}, d2={"filterIsInstance", "", "R", "", "klass", "Ljava/lang/Class;", "filterIsInstanceTo", "C", "", "destination", "(Ljava/lang/Iterable;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "max", "T", "", "(Ljava/lang/Iterable;)Ljava/lang/Comparable;", "", "(Ljava/lang/Iterable;)Ljava/lang/Double;", "", "(Ljava/lang/Iterable;)Ljava/lang/Float;", "maxBy", "selector", "Lkotlin/Function1;", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/lang/Object;", "min", "minBy", "minWith", "reverse", "", "", "sumOf", "Ljava/math/BigDecimal;", "sumOfBigDecimal", "Ljava/math/BigInteger;", "sumOfBigInteger", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt___CollectionsJvmKt
  extends CollectionsKt__ReversedViewsKt
{
  public static final <R> List<R> filterIsInstance(Iterable<?> paramIterable, Class<R> paramClass)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramClass, "klass");
    return (List)CollectionsKt.filterIsInstanceTo(paramIterable, (Collection)new ArrayList(), paramClass);
  }
  
  public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(Iterable<?> paramIterable, C paramC, Class<R> paramClass)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramClass, "klass");
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      paramIterable = localIterator.next();
      if (paramClass.isInstance(paramIterable)) {
        paramC.add(paramIterable);
      }
    }
    return paramC;
  }
  
  public static final <T> void reverse(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Collections.reverse(paramList);
  }
  
  private static final <T> BigDecimal sumOfBigDecimal(Iterable<? extends T> paramIterable, Function1<? super T, ? extends BigDecimal> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigDecimal localBigDecimal = BigDecimal.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    Iterator localIterator = paramIterable.iterator();
    paramIterable = localBigDecimal;
    while (localIterator.hasNext())
    {
      paramIterable = paramIterable.add((BigDecimal)paramFunction1.invoke(localIterator.next()));
      Intrinsics.checkNotNullExpressionValue(paramIterable, "this.add(other)");
    }
    return paramIterable;
  }
  
  private static final <T> BigInteger sumOfBigInteger(Iterable<? extends T> paramIterable, Function1<? super T, ? extends BigInteger> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigInteger localBigInteger = BigInteger.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    Iterator localIterator = paramIterable.iterator();
    paramIterable = localBigInteger;
    while (localIterator.hasNext())
    {
      paramIterable = paramIterable.add((BigInteger)paramFunction1.invoke(localIterator.next()));
      Intrinsics.checkNotNullExpressionValue(paramIterable, "this.add(other)");
    }
    return paramIterable;
  }
  
  public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    return (SortedSet)CollectionsKt.toCollection(paramIterable, (Collection)new TreeSet());
  }
  
  public static final <T> SortedSet<T> toSortedSet(Iterable<? extends T> paramIterable, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (SortedSet)CollectionsKt.toCollection(paramIterable, (Collection)new TreeSet(paramComparator));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt___CollectionsJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */