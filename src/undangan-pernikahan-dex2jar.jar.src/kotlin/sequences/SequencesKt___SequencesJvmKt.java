package kotlin.sequences;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000V\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\037\n\002\b\004\n\002\020\017\n\000\n\002\020\006\n\000\n\002\020\007\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032(\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\006\022\002\b\0030\0012\f\020\003\032\b\022\004\022\002H\0020\004\032A\020\005\032\002H\006\"\020\b\000\020\006*\n\022\006\b\000\022\002H\0020\007\"\004\b\001\020\002*\006\022\002\b\0030\0012\006\020\b\032\002H\0062\f\020\003\032\b\022\004\022\002H\0020\004¢\006\002\020\t\032)\020\n\032\004\030\001H\013\"\016\b\000\020\013*\b\022\004\022\002H\0130\f*\b\022\004\022\002H\0130\001H\007¢\006\002\020\r\032\031\020\n\032\004\030\0010\016*\b\022\004\022\0020\0160\001H\007¢\006\002\020\017\032\031\020\n\032\004\030\0010\020*\b\022\004\022\0020\0200\001H\007¢\006\002\020\021\032G\020\022\032\004\030\001H\013\"\004\b\000\020\013\"\016\b\001\020\002*\b\022\004\022\002H\0020\f*\b\022\004\022\002H\0130\0012\022\020\023\032\016\022\004\022\002H\013\022\004\022\002H\0020\024H\bø\001\000¢\006\002\020\025\032;\020\026\032\004\030\001H\013\"\004\b\000\020\013*\b\022\004\022\002H\0130\0012\032\020\027\032\026\022\006\b\000\022\002H\0130\030j\n\022\006\b\000\022\002H\013`\031H\007¢\006\002\020\032\032)\020\033\032\004\030\001H\013\"\016\b\000\020\013*\b\022\004\022\002H\0130\f*\b\022\004\022\002H\0130\001H\007¢\006\002\020\r\032\031\020\033\032\004\030\0010\016*\b\022\004\022\0020\0160\001H\007¢\006\002\020\017\032\031\020\033\032\004\030\0010\020*\b\022\004\022\0020\0200\001H\007¢\006\002\020\021\032G\020\034\032\004\030\001H\013\"\004\b\000\020\013\"\016\b\001\020\002*\b\022\004\022\002H\0020\f*\b\022\004\022\002H\0130\0012\022\020\023\032\016\022\004\022\002H\013\022\004\022\002H\0020\024H\bø\001\000¢\006\002\020\025\032;\020\035\032\004\030\001H\013\"\004\b\000\020\013*\b\022\004\022\002H\0130\0012\032\020\027\032\026\022\006\b\000\022\002H\0130\030j\n\022\006\b\000\022\002H\013`\031H\007¢\006\002\020\032\0325\020\036\032\0020\037\"\004\b\000\020\013*\b\022\004\022\002H\0130\0012\022\020\023\032\016\022\004\022\002H\013\022\004\022\0020\0370\024H\bø\001\000¢\006\002\b \0325\020\036\032\0020!\"\004\b\000\020\013*\b\022\004\022\002H\0130\0012\022\020\023\032\016\022\004\022\002H\013\022\004\022\0020!0\024H\bø\001\000¢\006\002\b\"\032&\020#\032\b\022\004\022\002H\0130$\"\016\b\000\020\013*\b\022\004\022\002H\0130\f*\b\022\004\022\002H\0130\001\0328\020#\032\b\022\004\022\002H\0130$\"\004\b\000\020\013*\b\022\004\022\002H\0130\0012\032\020\027\032\026\022\006\b\000\022\002H\0130\030j\n\022\006\b\000\022\002H\013`\031\002\007\n\005\b20\001¨\006%"}, d2={"filterIsInstance", "Lkotlin/sequences/Sequence;", "R", "klass", "Ljava/lang/Class;", "filterIsInstanceTo", "C", "", "destination", "(Lkotlin/sequences/Sequence;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "max", "T", "", "(Lkotlin/sequences/Sequence;)Ljava/lang/Comparable;", "", "(Lkotlin/sequences/Sequence;)Ljava/lang/Double;", "", "(Lkotlin/sequences/Sequence;)Ljava/lang/Float;", "maxBy", "selector", "Lkotlin/Function1;", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Lkotlin/sequences/Sequence;Ljava/util/Comparator;)Ljava/lang/Object;", "min", "minBy", "minWith", "sumOf", "Ljava/math/BigDecimal;", "sumOfBigDecimal", "Ljava/math/BigInteger;", "sumOfBigInteger", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/sequences/SequencesKt")
class SequencesKt___SequencesJvmKt
  extends SequencesKt__SequencesKt
{
  public static final <R> Sequence<R> filterIsInstance(Sequence<?> paramSequence, Class<R> paramClass)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramClass, "klass");
    paramSequence = SequencesKt.filter(paramSequence, (Function1)new Lambda(paramClass)
    {
      final Class<R> $klass;
      
      public final Boolean invoke(Object paramAnonymousObject)
      {
        return Boolean.valueOf(this.$klass.isInstance(paramAnonymousObject));
      }
    });
    Intrinsics.checkNotNull(paramSequence, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesJvmKt.filterIsInstance>");
    return paramSequence;
  }
  
  public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(Sequence<?> paramSequence, C paramC, Class<R> paramClass)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramClass, "klass");
    Iterator localIterator = paramSequence.iterator();
    while (localIterator.hasNext())
    {
      paramSequence = localIterator.next();
      if (paramClass.isInstance(paramSequence)) {
        paramC.add(paramSequence);
      }
    }
    return paramC;
  }
  
  private static final <T> BigDecimal sumOfBigDecimal(Sequence<? extends T> paramSequence, Function1<? super T, ? extends BigDecimal> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigDecimal localBigDecimal = BigDecimal.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    Iterator localIterator = paramSequence.iterator();
    paramSequence = localBigDecimal;
    while (localIterator.hasNext())
    {
      paramSequence = paramSequence.add((BigDecimal)paramFunction1.invoke(localIterator.next()));
      Intrinsics.checkNotNullExpressionValue(paramSequence, "this.add(other)");
    }
    return paramSequence;
  }
  
  private static final <T> BigInteger sumOfBigInteger(Sequence<? extends T> paramSequence, Function1<? super T, ? extends BigInteger> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigInteger localBigInteger = BigInteger.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    Iterator localIterator = paramSequence.iterator();
    paramSequence = localBigInteger;
    while (localIterator.hasNext())
    {
      paramSequence = paramSequence.add((BigInteger)paramFunction1.invoke(localIterator.next()));
      Intrinsics.checkNotNullExpressionValue(paramSequence, "this.add(other)");
    }
    return paramSequence;
  }
  
  public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    return (SortedSet)SequencesKt.toCollection(paramSequence, (Collection)new TreeSet());
  }
  
  public static final <T> SortedSet<T> toSortedSet(Sequence<? extends T> paramSequence, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (SortedSet)SequencesKt.toCollection(paramSequence, (Collection)new TreeSet(paramComparator));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/SequencesKt___SequencesJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */