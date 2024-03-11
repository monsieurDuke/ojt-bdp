package androidx.core.util;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\020\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\000\032\020\020\000\032\0020\001*\b\022\004\022\0020\0030\002¨\006\004"}, d2={"asRunnable", "Ljava/lang/Runnable;", "Lkotlin/coroutines/Continuation;", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class RunnableKt
{
  public static final Runnable asRunnable(Continuation<? super Unit> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramContinuation, "<this>");
    return (Runnable)new ContinuationRunnable(paramContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/RunnableKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */