package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\030\n\000\n\002\020\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\020\000\n\000\032\021\020\000\032\0020\0012\006\020\002\032\0020\003H\b\032\"\020\000\032\0020\0012\006\020\002\032\0020\0032\f\020\004\032\b\022\004\022\0020\0060\005H\bø\001\000\002\007\n\005\b20\001¨\006\007"}, d2={"assert", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/PreconditionsKt")
class PreconditionsKt__AssertionsJVMKt
{
  private static final void jdMethod_assert(boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw new AssertionError("Assertion failed");
  }
  
  private static final void jdMethod_assert(boolean paramBoolean, Function0<? extends Object> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "lazyMessage");
    if (paramBoolean) {
      return;
    }
    throw new AssertionError(paramFunction0.invoke());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/PreconditionsKt__AssertionsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */