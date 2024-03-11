package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\f\n\000\n\002\020\002\n\002\020\b\n\000\032\f\020\000\032\0020\001*\0020\002H\000¨\006\003"}, d2={"checkParallelism", "", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class LimitedDispatcherKt
{
  public static final void checkParallelism(int paramInt)
  {
    int i = 1;
    if (paramInt < 1) {
      i = 0;
    }
    if (i != 0) {
      return;
    }
    String str = Intrinsics.stringPlus("Expected positive parallelism level, but got ", Integer.valueOf(paramInt));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str.toString());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LimitedDispatcherKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */