package kotlin.system;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\024\n\000\n\002\020\t\n\000\n\002\030\002\n\002\020\002\n\002\b\002\032'\020\000\032\0020\0012\f\020\002\032\b\022\004\022\0020\0040\003H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\032'\020\005\032\0020\0012\f\020\002\032\b\022\004\022\0020\0040\003H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\002\007\n\005\b20\001¨\006\006"}, d2={"measureNanoTime", "", "block", "Lkotlin/Function0;", "", "measureTimeMillis", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class TimingKt
{
  public static final long measureNanoTime(Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    long l = System.nanoTime();
    paramFunction0.invoke();
    return System.nanoTime() - l;
  }
  
  public static final long measureTimeMillis(Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    long l = System.currentTimeMillis();
    paramFunction0.invoke();
    return System.currentTimeMillis() - l;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/system/TimingKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */