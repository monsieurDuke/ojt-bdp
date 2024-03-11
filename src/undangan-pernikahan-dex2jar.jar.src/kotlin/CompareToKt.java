package kotlin;

import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\020\n\000\n\002\020\b\n\000\n\002\020\017\n\002\b\003\032&\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\004\032\002H\002H\f¢\006\002\020\005¨\006\006"}, d2={"compareTo", "", "T", "", "other", "(Ljava/lang/Comparable;Ljava/lang/Object;)I", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class CompareToKt
{
  private static final <T> int compareTo(Comparable<? super T> paramComparable, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramComparable, "<this>");
    return paramComparable.compareTo(paramT);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/CompareToKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */