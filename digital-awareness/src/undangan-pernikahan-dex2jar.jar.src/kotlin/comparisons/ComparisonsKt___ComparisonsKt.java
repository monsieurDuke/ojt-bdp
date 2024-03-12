package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\030\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\021\n\002\b\003\032G\020\000\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\006\020\004\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007¢\006\002\020\b\032?\020\000\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007¢\006\002\020\t\032K\020\000\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\022\020\n\032\n\022\006\b\001\022\002H\0010\013\"\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007¢\006\002\020\f\032G\020\r\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\006\020\004\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007¢\006\002\020\b\032?\020\r\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007¢\006\002\020\t\032K\020\r\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\022\020\n\032\n\022\006\b\001\022\002H\0010\013\"\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007¢\006\002\020\f¨\006\016"}, d2={"maxOf", "T", "a", "b", "c", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "other", "", "(Ljava/lang/Object;[Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "minOf", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt___ComparisonsKt
  extends ComparisonsKt___ComparisonsJvmKt
{
  public static final <T> T maxOf(T paramT1, T paramT2, T paramT3, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (T)ComparisonsKt.maxOf(paramT1, ComparisonsKt.maxOf(paramT2, paramT3, paramComparator), paramComparator);
  }
  
  public static final <T> T maxOf(T paramT1, T paramT2, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    if (paramComparator.compare(paramT1, paramT2) < 0) {
      paramT1 = paramT2;
    }
    return paramT1;
  }
  
  public static final <T> T maxOf(T paramT, T[] paramArrayOfT, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "other");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int j = paramArrayOfT.length;
    int i = 0;
    for (T ? = paramT; i < j; ? = paramT)
    {
      T ? = paramArrayOfT[i];
      paramT = ?;
      if (paramComparator.compare(?, ?) < 0) {
        paramT = ?;
      }
      i++;
    }
    return ?;
  }
  
  public static final <T> T minOf(T paramT1, T paramT2, T paramT3, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (T)ComparisonsKt.minOf(paramT1, ComparisonsKt.minOf(paramT2, paramT3, paramComparator), paramComparator);
  }
  
  public static final <T> T minOf(T paramT1, T paramT2, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    if (paramComparator.compare(paramT1, paramT2) > 0) {
      paramT1 = paramT2;
    }
    return paramT1;
  }
  
  public static final <T> T minOf(T paramT, T[] paramArrayOfT, Comparator<? super T> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "other");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int j = paramArrayOfT.length;
    int i = 0;
    while (i < j)
    {
      T ? = paramArrayOfT[i];
      T ? = paramT;
      if (paramComparator.compare(paramT, ?) > 0) {
        ? = ?;
      }
      i++;
      paramT = ?;
    }
    return paramT;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/comparisons/ComparisonsKt___ComparisonsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */