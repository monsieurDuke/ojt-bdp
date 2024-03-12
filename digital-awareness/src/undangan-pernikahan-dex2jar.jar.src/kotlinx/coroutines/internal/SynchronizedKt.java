package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;

@Metadata(d1={"\000\034\n\002\b\003\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032.\020\000\032\002H\001\"\004\b\000\020\0012\n\020\002\032\0060\003j\002`\0042\f\020\005\032\b\022\004\022\002H\0010\006H\b¢\006\002\020\007*\020\b\007\020\b\"\0020\0032\0020\003B\002\b\t¨\006\n"}, d2={"synchronized", "T", "lock", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "block", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "SynchronizedObject", "Lkotlinx/coroutines/InternalCoroutinesApi;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SynchronizedKt
{
  public static final <T> T jdMethod_synchronized(Object paramObject, Function0<? extends T> paramFunction0)
  {
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/SynchronizedKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */