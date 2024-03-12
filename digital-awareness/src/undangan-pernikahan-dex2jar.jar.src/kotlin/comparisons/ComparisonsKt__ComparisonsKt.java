package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000<\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\017\n\000\n\002\020\021\n\002\b\005\n\002\020\b\n\002\b\013\n\002\020\000\n\002\b\b\n\002\030\002\n\002\030\002\n\002\b\003\032>\020\000\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\0022\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H\bø\001\000\032Y\020\000\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\00226\020\007\032\034\022\030\b\001\022\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\0050\b\"\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005¢\006\002\020\t\032Z\020\000\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n2\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H\bø\001\000\032>\020\f\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\0022\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H\bø\001\000\032Z\020\f\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n2\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H\bø\001\000\032-\020\r\032\0020\016\"\f\b\000\020\002*\006\022\002\b\0030\0062\b\020\017\032\004\030\001H\0022\b\020\020\032\004\030\001H\002¢\006\002\020\021\032A\020\022\032\0020\016\"\004\b\000\020\0022\006\020\017\032\002H\0022\006\020\020\032\002H\0022\030\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H\bø\001\000¢\006\002\020\023\032Y\020\022\032\0020\016\"\004\b\000\020\0022\006\020\017\032\002H\0022\006\020\020\032\002H\00226\020\007\032\034\022\030\b\001\022\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\0050\b\"\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005¢\006\002\020\024\032]\020\022\032\0020\016\"\004\b\000\020\002\"\004\b\001\020\n2\006\020\017\032\002H\0022\006\020\020\032\002H\0022\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H\bø\001\000¢\006\002\020\025\032G\020\026\032\0020\016\"\004\b\000\020\0022\006\020\017\032\002H\0022\006\020\020\032\002H\0022 \020\007\032\034\022\030\b\001\022\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\0050\bH\002¢\006\004\b\027\020\024\032&\020\030\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006\032-\020\031\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006H\b\032@\020\031\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\b\b\000\020\002*\0020\0322\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003\032-\020\033\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006H\b\032@\020\033\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\b\b\000\020\002*\0020\0322\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003\032&\020\034\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006\0320\020\035\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\032O\020\036\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003H\004\032R\020\037\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H\bø\001\000\032n\020\037\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H\bø\001\000\032R\020 \032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H\bø\001\000\032n\020 \032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H\bø\001\000\032p\020!\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\00328\b\004\020\"\0322\022\023\022\021H\002¢\006\f\b$\022\b\b%\022\004\b\b(\017\022\023\022\021H\002¢\006\f\b$\022\b\b%\022\004\b\b(\020\022\004\022\0020\0160#H\bø\001\000\032O\020&\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003H\004\002\007\n\005\b20\001¨\006'"}, d2={"compareBy", "Ljava/util/Comparator;", "T", "Lkotlin/Comparator;", "selector", "Lkotlin/Function1;", "", "selectors", "", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "K", "comparator", "compareByDescending", "compareValues", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareValuesBy", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "naturalOrder", "nullsFirst", "", "nullsLast", "reverseOrder", "reversed", "then", "thenBy", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "thenDescending", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt__ComparisonsKt
{
  private static final <T, K> Comparator<T> compareBy(Comparator<? super K> paramComparator, final Function1<? super T, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Comparator<? super K> $comparator;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        Comparator localComparator = this.$comparator;
        Function1 localFunction1 = paramFunction1;
        return localComparator.compare(localFunction1.invoke(paramAnonymousT1), localFunction1.invoke(paramAnonymousT2));
      }
    };
  }
  
  private static final <T> Comparator<T> compareBy(Function1<? super T, ? extends Comparable<?>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Function1<T, Comparable<?>> $selector;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        Function1 localFunction1 = this.$selector;
        return ComparisonsKt.compareValues((Comparable)localFunction1.invoke(paramAnonymousT1), (Comparable)localFunction1.invoke(paramAnonymousT2));
      }
    };
  }
  
  public static final <T> Comparator<T> compareBy(Function1<? super T, ? extends Comparable<?>>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "selectors");
    int i;
    if (paramVarArgs.length > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      (Comparator)new Comparator()
      {
        final Function1<T, Comparable<?>>[] $selectors;
        
        public final int compare(T paramAnonymousT1, T paramAnonymousT2)
        {
          return ComparisonsKt__ComparisonsKt.access$compareValuesByImpl(paramAnonymousT1, paramAnonymousT2, this.$selectors);
        }
      };
    }
    throw new IllegalArgumentException("Failed requirement.".toString());
  }
  
  private static final <T, K> Comparator<T> compareByDescending(Comparator<? super K> paramComparator, final Function1<? super T, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Comparator<? super K> $comparator;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        Comparator localComparator = this.$comparator;
        Function1 localFunction1 = paramFunction1;
        return localComparator.compare(localFunction1.invoke(paramAnonymousT2), localFunction1.invoke(paramAnonymousT1));
      }
    };
  }
  
  private static final <T> Comparator<T> compareByDescending(Function1<? super T, ? extends Comparable<?>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Function1<T, Comparable<?>> $selector;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        Function1 localFunction1 = this.$selector;
        return ComparisonsKt.compareValues((Comparable)localFunction1.invoke(paramAnonymousT2), (Comparable)localFunction1.invoke(paramAnonymousT1));
      }
    };
  }
  
  public static final <T extends Comparable<?>> int compareValues(T paramT1, T paramT2)
  {
    if (paramT1 == paramT2) {
      return 0;
    }
    if (paramT1 == null) {
      return -1;
    }
    if (paramT2 == null) {
      return 1;
    }
    return paramT1.compareTo(paramT2);
  }
  
  private static final <T, K> int compareValuesBy(T paramT1, T paramT2, Comparator<? super K> paramComparator, Function1<? super T, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    return paramComparator.compare(paramFunction1.invoke(paramT1), paramFunction1.invoke(paramT2));
  }
  
  private static final <T> int compareValuesBy(T paramT1, T paramT2, Function1<? super T, ? extends Comparable<?>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    return ComparisonsKt.compareValues((Comparable)paramFunction1.invoke(paramT1), (Comparable)paramFunction1.invoke(paramT2));
  }
  
  public static final <T> int compareValuesBy(T paramT1, T paramT2, Function1<? super T, ? extends Comparable<?>>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "selectors");
    int i;
    if (paramVarArgs.length > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return compareValuesByImpl$ComparisonsKt__ComparisonsKt(paramT1, paramT2, paramVarArgs);
    }
    throw new IllegalArgumentException("Failed requirement.".toString());
  }
  
  private static final <T> int compareValuesByImpl$ComparisonsKt__ComparisonsKt(T paramT1, T paramT2, Function1<? super T, ? extends Comparable<?>>[] paramArrayOfFunction1)
  {
    int j = paramArrayOfFunction1.length;
    for (int i = 0; i < j; i++)
    {
      Function1<? super T, ? extends Comparable<?>> localFunction1 = paramArrayOfFunction1[i];
      int k = ComparisonsKt.compareValues((Comparable)localFunction1.invoke(paramT1), (Comparable)localFunction1.invoke(paramT2));
      if (k != 0) {
        return k;
      }
    }
    return 0;
  }
  
  public static final <T extends Comparable<? super T>> Comparator<T> naturalOrder()
  {
    NaturalOrderComparator localNaturalOrderComparator = NaturalOrderComparator.INSTANCE;
    Intrinsics.checkNotNull(localNaturalOrderComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.naturalOrder>{ kotlin.TypeAliasesKt.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.naturalOrder> }");
    return (Comparator)localNaturalOrderComparator;
  }
  
  private static final <T extends Comparable<? super T>> Comparator<T> nullsFirst()
  {
    return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
  }
  
  public static final <T> Comparator<T> nullsFirst(Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    (Comparator)new Comparator()
    {
      final Comparator<? super T> $comparator;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i;
        if (paramAnonymousT1 == paramAnonymousT2) {
          i = 0;
        } else if (paramAnonymousT1 == null) {
          i = -1;
        } else if (paramAnonymousT2 == null) {
          i = 1;
        } else {
          i = this.$comparator.compare(paramAnonymousT1, paramAnonymousT2);
        }
        return i;
      }
    };
  }
  
  private static final <T extends Comparable<? super T>> Comparator<T> nullsLast()
  {
    return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
  }
  
  public static final <T> Comparator<T> nullsLast(Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    (Comparator)new Comparator()
    {
      final Comparator<? super T> $comparator;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i;
        if (paramAnonymousT1 == paramAnonymousT2) {
          i = 0;
        } else if (paramAnonymousT1 == null) {
          i = 1;
        } else if (paramAnonymousT2 == null) {
          i = -1;
        } else {
          i = this.$comparator.compare(paramAnonymousT1, paramAnonymousT2);
        }
        return i;
      }
    };
  }
  
  public static final <T extends Comparable<? super T>> Comparator<T> reverseOrder()
  {
    ReverseOrderComparator localReverseOrderComparator = ReverseOrderComparator.INSTANCE;
    Intrinsics.checkNotNull(localReverseOrderComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reverseOrder>{ kotlin.TypeAliasesKt.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reverseOrder> }");
    return (Comparator)localReverseOrderComparator;
  }
  
  public static final <T> Comparator<T> reversed(Comparator<T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    if ((paramComparator instanceof ReversedComparator))
    {
      paramComparator = ((ReversedComparator)paramComparator).getComparator();
    }
    else if (Intrinsics.areEqual(paramComparator, NaturalOrderComparator.INSTANCE))
    {
      paramComparator = ReverseOrderComparator.INSTANCE;
      Intrinsics.checkNotNull(paramComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reversed>{ kotlin.TypeAliasesKt.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reversed> }");
      paramComparator = (Comparator)paramComparator;
    }
    else if (Intrinsics.areEqual(paramComparator, ReverseOrderComparator.INSTANCE))
    {
      paramComparator = NaturalOrderComparator.INSTANCE;
      Intrinsics.checkNotNull(paramComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reversed>{ kotlin.TypeAliasesKt.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reversed> }");
      paramComparator = (Comparator)paramComparator;
    }
    else
    {
      paramComparator = (Comparator)new ReversedComparator(paramComparator);
    }
    return paramComparator;
  }
  
  public static final <T> Comparator<T> then(Comparator<T> paramComparator, final Comparator<? super T> paramComparator1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator1, "comparator");
    (Comparator)new Comparator()
    {
      final Comparator<T> $this_then;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = this.$this_then.compare(paramAnonymousT1, paramAnonymousT2);
        if (i == 0) {
          i = paramComparator1.compare(paramAnonymousT1, paramAnonymousT2);
        }
        return i;
      }
    };
  }
  
  private static final <T, K> Comparator<T> thenBy(Comparator<T> paramComparator, final Comparator<? super K> paramComparator1, final Function1<? super T, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator1, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Comparator<T> $this_thenBy;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = this.$this_thenBy.compare(paramAnonymousT1, paramAnonymousT2);
        if (i == 0)
        {
          Comparator localComparator = paramComparator1;
          Function1 localFunction1 = paramFunction1;
          i = localComparator.compare(localFunction1.invoke(paramAnonymousT1), localFunction1.invoke(paramAnonymousT2));
        }
        return i;
      }
    };
  }
  
  private static final <T> Comparator<T> thenBy(Comparator<T> paramComparator, final Function1<? super T, ? extends Comparable<?>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Comparator<T> $this_thenBy;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = this.$this_thenBy.compare(paramAnonymousT1, paramAnonymousT2);
        if (i == 0)
        {
          Function1 localFunction1 = paramFunction1;
          i = ComparisonsKt.compareValues((Comparable)localFunction1.invoke(paramAnonymousT1), (Comparable)localFunction1.invoke(paramAnonymousT2));
        }
        return i;
      }
    };
  }
  
  private static final <T, K> Comparator<T> thenByDescending(Comparator<T> paramComparator, final Comparator<? super K> paramComparator1, final Function1<? super T, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator1, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Comparator<T> $this_thenByDescending;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = this.$this_thenByDescending.compare(paramAnonymousT1, paramAnonymousT2);
        if (i == 0)
        {
          Comparator localComparator = paramComparator1;
          Function1 localFunction1 = paramFunction1;
          i = localComparator.compare(localFunction1.invoke(paramAnonymousT2), localFunction1.invoke(paramAnonymousT1));
        }
        return i;
      }
    };
  }
  
  private static final <T> Comparator<T> thenByDescending(Comparator<T> paramComparator, final Function1<? super T, ? extends Comparable<?>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    (Comparator)new Comparator()
    {
      final Comparator<T> $this_thenByDescending;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = this.$this_thenByDescending.compare(paramAnonymousT1, paramAnonymousT2);
        if (i == 0)
        {
          Function1 localFunction1 = paramFunction1;
          i = ComparisonsKt.compareValues((Comparable)localFunction1.invoke(paramAnonymousT2), (Comparable)localFunction1.invoke(paramAnonymousT1));
        }
        return i;
      }
    };
  }
  
  private static final <T> Comparator<T> thenComparator(Comparator<T> paramComparator, final Function2<? super T, ? super T, Integer> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "comparison");
    (Comparator)new Comparator()
    {
      final Comparator<T> $this_thenComparator;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = this.$this_thenComparator.compare(paramAnonymousT1, paramAnonymousT2);
        if (i == 0) {
          i = ((Number)paramFunction2.invoke(paramAnonymousT1, paramAnonymousT2)).intValue();
        }
        return i;
      }
    };
  }
  
  public static final <T> Comparator<T> thenDescending(Comparator<T> paramComparator, final Comparator<? super T> paramComparator1)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator1, "comparator");
    (Comparator)new Comparator()
    {
      final Comparator<T> $this_thenDescending;
      
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = this.$this_thenDescending.compare(paramAnonymousT1, paramAnonymousT2);
        if (i == 0) {
          i = paramComparator1.compare(paramAnonymousT2, paramAnonymousT1);
        }
        return i;
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/comparisons/ComparisonsKt__ComparisonsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */