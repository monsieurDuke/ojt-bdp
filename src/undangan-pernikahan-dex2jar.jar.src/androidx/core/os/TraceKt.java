package androidx.core.os;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\022\n\002\b\003\n\002\020\016\n\000\n\002\030\002\n\002\b\002\032-\020\000\032\002H\001\"\004\b\000\020\0012\006\020\002\032\0020\0032\f\020\004\032\b\022\004\022\002H\0010\005H\bø\001\000¢\006\002\020\006\002\007\n\005\b20\001¨\006\007"}, d2={"trace", "T", "sectionName", "", "block", "Lkotlin/Function0;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class TraceKt
{
  @Deprecated(message="Use androidx.tracing.Trace instead", replaceWith=@ReplaceWith(expression="trace(sectionName)", imports={"androidx.tracing.trace"}))
  public static final <T> T trace(String paramString, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramString, "sectionName");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    TraceCompat.beginSection(paramString);
    try
    {
      paramString = paramFunction0.invoke();
      return paramString;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      TraceCompat.endSection();
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/TraceKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */