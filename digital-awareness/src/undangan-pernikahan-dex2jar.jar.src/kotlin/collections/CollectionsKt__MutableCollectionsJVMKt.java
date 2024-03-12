package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0002\n\000\n\002\020\002\n\000\n\002\020!\n\002\b\004\n\002\030\002\n\000\n\002\020\017\n\000\n\002\030\002\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\b\002\032&\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\004\032\002H\002H\b¢\006\002\020\005\032\031\020\006\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\b\032!\020\006\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\007\032\0020\bH\b\032 \020\t\032\0020\001\"\016\b\000\020\002*\b\022\004\022\002H\0020\n*\b\022\004\022\002H\0020\003\0326\020\t\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\030\020\013\032\024\022\004\022\002H\002\022\004\022\002H\002\022\004\022\0020\r0\fH\bø\001\000\0325\020\t\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\032\020\016\032\026\022\006\b\000\022\002H\0020\017j\n\022\006\b\000\022\002H\002`\020H\b\0322\020\021\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\032\020\016\032\026\022\006\b\000\022\002H\0020\017j\n\022\006\b\000\022\002H\002`\020\002\007\n\005\b20\001¨\006\022"}, d2={"fill", "", "T", "", "value", "(Ljava/util/List;Ljava/lang/Object;)V", "shuffle", "random", "Ljava/util/Random;", "sort", "", "comparison", "Lkotlin/Function2;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "sortWith", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__MutableCollectionsJVMKt
  extends CollectionsKt__IteratorsKt
{
  private static final <T> void fill(List<T> paramList, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Collections.fill(paramList, paramT);
  }
  
  private static final <T> void shuffle(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Collections.shuffle(paramList);
  }
  
  private static final <T> void shuffle(List<T> paramList, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    Collections.shuffle(paramList, paramRandom);
  }
  
  public static final <T extends Comparable<? super T>> void sort(List<T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    if (paramList.size() > 1) {
      Collections.sort(paramList);
    }
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use sortWith(comparator) instead.", replaceWith=@ReplaceWith(expression="this.sortWith(comparator)", imports={}))
  private static final <T> void sort(List<T> paramList, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    throw new NotImplementedError(null, 1, null);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use sortWith(Comparator(comparison)) instead.", replaceWith=@ReplaceWith(expression="this.sortWith(Comparator(comparison))", imports={}))
  private static final <T> void sort(List<T> paramList, Function2<? super T, ? super T, Integer> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "comparison");
    throw new NotImplementedError(null, 1, null);
  }
  
  public static final <T> void sortWith(List<T> paramList, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramList, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    if (paramList.size() > 1) {
      Collections.sort(paramList, paramComparator);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt__MutableCollectionsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */