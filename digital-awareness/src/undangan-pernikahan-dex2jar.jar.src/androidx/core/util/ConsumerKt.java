package androidx.core.util;

import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032\036\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\007¨\006\004"}, d2={"asConsumer", "Ljava/util/function/Consumer;", "T", "Lkotlin/coroutines/Continuation;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ConsumerKt
{
  public static final <T> Consumer<T> asConsumer(Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramContinuation, "<this>");
    return (Consumer)new ContinuationConsumer(paramContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/ConsumerKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */