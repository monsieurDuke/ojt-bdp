package okhttp3.internal.http1;

import kotlin.Metadata;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okio.BufferedSource;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\002\030\000 \r2\0020\001:\001\rB\r\022\006\020\002\032\0020\003¢\006\002\020\004J\006\020\t\032\0020\nJ\006\020\013\032\0020\fR\016\020\005\032\0020\006X\016¢\006\002\n\000R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\007\020\b¨\006\016"}, d2={"Lokhttp3/internal/http1/HeadersReader;", "", "source", "Lokio/BufferedSource;", "(Lokio/BufferedSource;)V", "headerLimit", "", "getSource", "()Lokio/BufferedSource;", "readHeaders", "Lokhttp3/Headers;", "readLine", "", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class HeadersReader
{
  public static final Companion Companion = new Companion(null);
  private static final int HEADER_LIMIT = 262144;
  private long headerLimit;
  private final BufferedSource source;
  
  public HeadersReader(BufferedSource paramBufferedSource)
  {
    this.source = paramBufferedSource;
    this.headerLimit = 262144;
  }
  
  public final BufferedSource getSource()
  {
    return this.source;
  }
  
  public final Headers readHeaders()
  {
    Headers.Builder localBuilder = new Headers.Builder();
    for (;;)
    {
      String str = readLine();
      int i;
      if (((CharSequence)str).length() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return localBuilder.build();
      }
      localBuilder.addLenient$okhttp(str);
    }
  }
  
  public final String readLine()
  {
    String str = this.source.readUtf8LineStrict(this.headerLimit);
    this.headerLimit -= str.length();
    return str;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lokhttp3/internal/http1/HeadersReader$Companion;", "", "()V", "HEADER_LIMIT", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http1/HeadersReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */