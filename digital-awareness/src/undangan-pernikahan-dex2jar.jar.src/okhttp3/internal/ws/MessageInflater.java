package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.InflaterSource;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\003\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\013\032\0020\fH\026J\016\020\r\032\0020\f2\006\020\016\032\0020\006R\016\020\005\032\0020\006X\004¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000R\016\020\t\032\0020\nX\004¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\017"}, d2={"Lokhttp3/internal/ws/MessageInflater;", "Ljava/io/Closeable;", "noContextTakeover", "", "(Z)V", "deflatedBytes", "Lokio/Buffer;", "inflater", "Ljava/util/zip/Inflater;", "inflaterSource", "Lokio/InflaterSource;", "close", "", "inflate", "buffer", "okhttp"}, k=1, mv={1, 4, 0})
public final class MessageInflater
  implements Closeable
{
  private final Buffer deflatedBytes;
  private final Inflater inflater;
  private final InflaterSource inflaterSource;
  private final boolean noContextTakeover;
  
  public MessageInflater(boolean paramBoolean)
  {
    this.noContextTakeover = paramBoolean;
    Buffer localBuffer = new Buffer();
    this.deflatedBytes = localBuffer;
    Inflater localInflater = new Inflater(true);
    this.inflater = localInflater;
    this.inflaterSource = new InflaterSource((Source)localBuffer, localInflater);
  }
  
  public void close()
    throws IOException
  {
    this.inflaterSource.close();
  }
  
  public final void inflate(Buffer paramBuffer)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "buffer");
    int i;
    if (this.deflatedBytes.size() == 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (this.noContextTakeover) {
        this.inflater.reset();
      }
      this.deflatedBytes.writeAll((Source)paramBuffer);
      this.deflatedBytes.writeInt(65535);
      long l2 = this.inflater.getBytesRead();
      long l1 = this.deflatedBytes.size();
      do
      {
        this.inflaterSource.readOrInflate(paramBuffer, Long.MAX_VALUE);
      } while (this.inflater.getBytesRead() < l2 + l1);
      return;
    }
    throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/ws/MessageInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */