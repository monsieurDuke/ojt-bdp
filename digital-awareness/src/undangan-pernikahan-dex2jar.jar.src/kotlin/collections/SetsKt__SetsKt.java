package kotlin.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000J\n\000\n\002\020\"\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\020#\n\002\020\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\000\n\002\b\005\032N\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\003\032\0020\0042\037\b\001\020\005\032\031\022\n\022\b\022\004\022\002H\0020\007\022\004\022\0020\b0\006¢\006\002\b\tH\bø\001\000\002\n\n\b\b\001\022\002\020\002 \001\032F\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\037\b\001\020\005\032\031\022\n\022\b\022\004\022\002H\0020\007\022\004\022\0020\b0\006¢\006\002\b\tH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\032\022\020\n\032\b\022\004\022\002H\0130\001\"\004\b\000\020\013\032\037\020\f\032\022\022\004\022\002H\0130\rj\b\022\004\022\002H\013`\016\"\004\b\000\020\013H\b\0325\020\f\032\022\022\004\022\002H\0130\rj\b\022\004\022\002H\013`\016\"\004\b\000\020\0132\022\020\017\032\n\022\006\b\001\022\002H\0130\020\"\002H\013¢\006\002\020\021\032\037\020\022\032\022\022\004\022\002H\0130\023j\b\022\004\022\002H\013`\024\"\004\b\000\020\013H\b\0325\020\022\032\022\022\004\022\002H\0130\023j\b\022\004\022\002H\013`\024\"\004\b\000\020\0132\022\020\017\032\n\022\006\b\001\022\002H\0130\020\"\002H\013¢\006\002\020\025\032\025\020\026\032\b\022\004\022\002H\0130\007\"\004\b\000\020\013H\b\032+\020\026\032\b\022\004\022\002H\0130\007\"\004\b\000\020\0132\022\020\017\032\n\022\006\b\001\022\002H\0130\020\"\002H\013¢\006\002\020\027\032\025\020\030\032\b\022\004\022\002H\0130\001\"\004\b\000\020\013H\b\032+\020\030\032\b\022\004\022\002H\0130\001\"\004\b\000\020\0132\022\020\017\032\n\022\006\b\001\022\002H\0130\020\"\002H\013¢\006\002\020\027\032'\020\031\032\b\022\004\022\002H\0130\001\"\b\b\000\020\013*\0020\0322\b\020\033\032\004\030\001H\013H\007¢\006\002\020\034\0325\020\031\032\b\022\004\022\002H\0130\001\"\b\b\000\020\013*\0020\0322\026\020\017\032\f\022\b\b\001\022\004\030\001H\0130\020\"\004\030\001H\013H\007¢\006\002\020\027\032\036\020\035\032\b\022\004\022\002H\0130\001\"\004\b\000\020\013*\b\022\004\022\002H\0130\001H\000\032!\020\036\032\b\022\004\022\002H\0130\001\"\004\b\000\020\013*\n\022\004\022\002H\013\030\0010\001H\b\002\007\n\005\b20\001¨\006\037"}, d2={"buildSet", "", "E", "capacity", "", "builderAction", "Lkotlin/Function1;", "", "", "Lkotlin/ExtensionFunctionType;", "emptySet", "T", "hashSetOf", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "elements", "", "([Ljava/lang/Object;)Ljava/util/HashSet;", "linkedSetOf", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "([Ljava/lang/Object;)Ljava/util/LinkedHashSet;", "mutableSetOf", "([Ljava/lang/Object;)Ljava/util/Set;", "setOf", "setOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/Set;", "optimizeReadOnlySet", "orEmpty", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/SetsKt")
class SetsKt__SetsKt
  extends SetsKt__SetsJVMKt
{
  private static final <E> Set<E> buildSet(int paramInt, Function1<? super Set<E>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Set localSet = SetsKt.createSetBuilder(paramInt);
    paramFunction1.invoke(localSet);
    return SetsKt.build(localSet);
  }
  
  private static final <E> Set<E> buildSet(Function1<? super Set<E>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Set localSet = SetsKt.createSetBuilder();
    paramFunction1.invoke(localSet);
    return SetsKt.build(localSet);
  }
  
  public static final <T> Set<T> emptySet()
  {
    return (Set)EmptySet.INSTANCE;
  }
  
  private static final <T> HashSet<T> hashSetOf()
  {
    return new HashSet();
  }
  
  public static final <T> HashSet<T> hashSetOf(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    return (HashSet)ArraysKt.toCollection(paramVarArgs, (Collection)new HashSet(MapsKt.mapCapacity(paramVarArgs.length)));
  }
  
  private static final <T> LinkedHashSet<T> linkedSetOf()
  {
    return new LinkedHashSet();
  }
  
  public static final <T> LinkedHashSet<T> linkedSetOf(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    return (LinkedHashSet)ArraysKt.toCollection(paramVarArgs, (Collection)new LinkedHashSet(MapsKt.mapCapacity(paramVarArgs.length)));
  }
  
  private static final <T> Set<T> mutableSetOf()
  {
    return (Set)new LinkedHashSet();
  }
  
  public static final <T> Set<T> mutableSetOf(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    return (Set)ArraysKt.toCollection(paramVarArgs, (Collection)new LinkedHashSet(MapsKt.mapCapacity(paramVarArgs.length)));
  }
  
  public static final <T> Set<T> optimizeReadOnlySet(Set<? extends T> paramSet)
  {
    Intrinsics.checkNotNullParameter(paramSet, "<this>");
    switch (paramSet.size())
    {
    default: 
      break;
    case 1: 
      paramSet = SetsKt.setOf(paramSet.iterator().next());
      break;
    case 0: 
      paramSet = SetsKt.emptySet();
    }
    return paramSet;
  }
  
  private static final <T> Set<T> orEmpty(Set<? extends T> paramSet)
  {
    if (paramSet == null) {
      paramSet = SetsKt.emptySet();
    }
    return paramSet;
  }
  
  private static final <T> Set<T> setOf()
  {
    return SetsKt.emptySet();
  }
  
  public static final <T> Set<T> setOf(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    if (paramVarArgs.length > 0) {
      paramVarArgs = ArraysKt.toSet(paramVarArgs);
    } else {
      paramVarArgs = SetsKt.emptySet();
    }
    return paramVarArgs;
  }
  
  public static final <T> Set<T> setOfNotNull(T paramT)
  {
    if (paramT != null) {
      paramT = SetsKt.setOf(paramT);
    } else {
      paramT = SetsKt.emptySet();
    }
    return paramT;
  }
  
  public static final <T> Set<T> setOfNotNull(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    return (Set)ArraysKt.filterNotNullTo(paramVarArgs, (Collection)new LinkedHashSet());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/SetsKt__SetsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */