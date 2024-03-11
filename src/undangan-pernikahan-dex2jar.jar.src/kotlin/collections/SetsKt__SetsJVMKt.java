package kotlin.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000B\n\000\n\002\020\"\n\002\b\002\n\002\020#\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\020\021\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\032\"\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\004H\001\032?\020\005\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\006\032\0020\0072\035\020\b\032\031\022\n\022\b\022\004\022\002H\0020\004\022\004\022\0020\n0\t¢\006\002\b\013H\bø\001\000\0327\020\005\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\035\020\b\032\031\022\n\022\b\022\004\022\002H\0020\004\022\004\022\0020\n0\t¢\006\002\b\013H\bø\001\000\032\024\020\f\032\b\022\004\022\002H\0020\004\"\004\b\000\020\002H\001\032\034\020\f\032\b\022\004\022\002H\0020\004\"\004\b\000\020\0022\006\020\006\032\0020\007H\001\032\037\020\r\032\b\022\004\022\002H\0160\001\"\004\b\000\020\0162\006\020\017\032\002H\016¢\006\002\020\020\032+\020\021\032\b\022\004\022\002H\0160\022\"\004\b\000\020\0162\022\020\023\032\n\022\006\b\001\022\002H\0160\024\"\002H\016¢\006\002\020\025\032G\020\021\032\b\022\004\022\002H\0160\022\"\004\b\000\020\0162\032\020\026\032\026\022\006\b\000\022\002H\0160\027j\n\022\006\b\000\022\002H\016`\0302\022\020\023\032\n\022\006\b\001\022\002H\0160\024\"\002H\016¢\006\002\020\031\002\007\n\005\b20\001¨\006\032"}, d2={"build", "", "E", "builder", "", "buildSetInternal", "capacity", "", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createSetBuilder", "setOf", "T", "element", "(Ljava/lang/Object;)Ljava/util/Set;", "sortedSetOf", "Ljava/util/TreeSet;", "elements", "", "([Ljava/lang/Object;)Ljava/util/TreeSet;", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/Comparator;[Ljava/lang/Object;)Ljava/util/TreeSet;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/SetsKt")
class SetsKt__SetsJVMKt
{
  public static final <E> Set<E> build(Set<E> paramSet)
  {
    Intrinsics.checkNotNullParameter(paramSet, "builder");
    return ((SetBuilder)paramSet).build();
  }
  
  private static final <E> Set<E> buildSetInternal(int paramInt, Function1<? super Set<E>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Set localSet = SetsKt.createSetBuilder(paramInt);
    paramFunction1.invoke(localSet);
    return SetsKt.build(localSet);
  }
  
  private static final <E> Set<E> buildSetInternal(Function1<? super Set<E>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Set localSet = SetsKt.createSetBuilder();
    paramFunction1.invoke(localSet);
    return SetsKt.build(localSet);
  }
  
  public static final <E> Set<E> createSetBuilder()
  {
    return (Set)new SetBuilder();
  }
  
  public static final <E> Set<E> createSetBuilder(int paramInt)
  {
    return (Set)new SetBuilder(paramInt);
  }
  
  public static final <T> Set<T> setOf(T paramT)
  {
    paramT = Collections.singleton(paramT);
    Intrinsics.checkNotNullExpressionValue(paramT, "singleton(element)");
    return paramT;
  }
  
  public static final <T> TreeSet<T> sortedSetOf(Comparator<? super T> paramComparator, T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    return (TreeSet)ArraysKt.toCollection(paramVarArgs, (Collection)new TreeSet(paramComparator));
  }
  
  public static final <T> TreeSet<T> sortedSetOf(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    return (TreeSet)ArraysKt.toCollection(paramVarArgs, (Collection)new TreeSet());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/SetsKt__SetsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */