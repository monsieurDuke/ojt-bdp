package okio;

import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\032\027\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\004H\b¨\006\005"}, d2={"inflate", "Lokio/InflaterSource;", "Lokio/Source;", "inflater", "Ljava/util/zip/Inflater;", "okio"}, k=2, mv={1, 4, 0})
public final class _InflaterSourceExtensions
{
  public static final InflaterSource inflate(Source paramSource, Inflater paramInflater)
  {
    Intrinsics.checkNotNullParameter(paramSource, "$this$inflate");
    Intrinsics.checkNotNullParameter(paramInflater, "inflater");
    return new InflaterSource(paramSource, paramInflater);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/_InflaterSourceExtensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */