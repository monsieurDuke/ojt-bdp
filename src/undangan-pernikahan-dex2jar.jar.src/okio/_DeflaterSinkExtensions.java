package okio;

import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\032\027\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\004H\b¨\006\005"}, d2={"deflate", "Lokio/DeflaterSink;", "Lokio/Sink;", "deflater", "Ljava/util/zip/Deflater;", "okio"}, k=2, mv={1, 4, 0})
public final class _DeflaterSinkExtensions
{
  public static final DeflaterSink deflate(Sink paramSink, Deflater paramDeflater)
  {
    Intrinsics.checkNotNullParameter(paramSink, "$this$deflate");
    Intrinsics.checkNotNullParameter(paramDeflater, "deflater");
    return new DeflaterSink(paramSink, paramDeflater);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/_DeflaterSinkExtensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */