package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\022\n\002\b\003\n\002\020\000\n\000\n\002\030\002\n\002\b\002\032:\020\000\032\002H\001\"\004\b\000\020\0012\006\020\002\032\0020\0032\f\020\004\032\b\022\004\022\002H\0010\005H\bø\001\000\002\n\n\b\b\001\022\002\020\002 \001¢\006\002\020\006\002\007\n\005\b20\001¨\006\007"}, d2={"synchronized", "R", "lock", "", "block", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/StandardKt")
class StandardKt__SynchronizedKt
  extends StandardKt__StandardKt
{
  private static final <R> R jdMethod_synchronized(Object paramObject, Function0<? extends R> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramObject, "lock");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/StandardKt__SynchronizedKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */