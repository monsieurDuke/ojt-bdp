package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\000\n\002\030\002\n\002\030\002\n\000\032\r\020\000\032\0020\001*\0020\002H\b¨\006\003"}, d2={"gzip", "Lokio/GzipSink;", "Lokio/Sink;", "okio"}, k=2, mv={1, 4, 0})
public final class _GzipSinkExtensions
{
  public static final GzipSink gzip(Sink paramSink)
  {
    Intrinsics.checkNotNullParameter(paramSink, "$this$gzip");
    return new GzipSink(paramSink);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/_GzipSinkExtensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */