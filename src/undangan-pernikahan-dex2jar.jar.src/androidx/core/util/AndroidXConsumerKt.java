package androidx.core.util;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032\034\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003¨\006\004"}, d2={"asAndroidXConsumer", "Landroidx/core/util/Consumer;", "T", "Lkotlin/coroutines/Continuation;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class AndroidXConsumerKt
{
  public static final <T> Consumer<T> asAndroidXConsumer(Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramContinuation, "<this>");
    return (Consumer)new AndroidXContinuationConsumer(paramContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/AndroidXConsumerKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */