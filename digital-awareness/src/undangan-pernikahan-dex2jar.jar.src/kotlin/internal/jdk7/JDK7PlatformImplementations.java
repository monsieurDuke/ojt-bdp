package kotlin.internal.jdk7;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.internal.PlatformImplementations;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\002\b\002\n\002\020 \n\000\b\020\030\0002\0020\001B\005¢\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\006H\026J\026\020\b\032\b\022\004\022\0020\0060\t2\006\020\007\032\0020\006H\026¨\006\n"}, d2={"Lkotlin/internal/jdk7/JDK7PlatformImplementations;", "Lkotlin/internal/PlatformImplementations;", "()V", "addSuppressed", "", "cause", "", "exception", "getSuppressed", "", "kotlin-stdlib-jdk7"}, k=1, mv={1, 6, 0}, xi=48)
public class JDK7PlatformImplementations
  extends PlatformImplementations
{
  public void addSuppressed(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    Intrinsics.checkNotNullParameter(paramThrowable1, "cause");
    Intrinsics.checkNotNullParameter(paramThrowable2, "exception");
    paramThrowable1.addSuppressed(paramThrowable2);
  }
  
  public List<Throwable> getSuppressed(Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "exception");
    paramThrowable = paramThrowable.getSuppressed();
    Intrinsics.checkNotNullExpressionValue(paramThrowable, "exception.suppressed");
    return ArraysKt.asList((Object[])paramThrowable);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/internal/jdk7/JDK7PlatformImplementations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */