package okio;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\b\020\007\032\0020\bH\026J\030\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\nH\026J\b\020\004\032\0020\005H\026J\b\020\016\032\0020\017H\026R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\020"}, d2={"Lokio/InputStreamSource;", "Lokio/Source;", "input", "Ljava/io/InputStream;", "timeout", "Lokio/Timeout;", "(Ljava/io/InputStream;Lokio/Timeout;)V", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "toString", "", "okio"}, k=1, mv={1, 4, 0})
final class InputStreamSource
  implements Source
{
  private final InputStream input;
  private final Timeout timeout;
  
  public InputStreamSource(InputStream paramInputStream, Timeout paramTimeout)
  {
    this.input = paramInputStream;
    this.timeout = paramTimeout;
  }
  
  public void close()
  {
    this.input.close();
  }
  
  public long read(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    if (paramLong == 0L) {
      return 0L;
    }
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      try
      {
        this.timeout.throwIfReached();
        Segment localSegment = paramBuffer.writableSegment$okio(1);
        i = (int)Math.min(paramLong, 8192 - localSegment.limit);
        i = this.input.read(localSegment.data, localSegment.limit, i);
        if (i == -1)
        {
          if (localSegment.pos == localSegment.limit)
          {
            paramBuffer.head = localSegment.pop();
            SegmentPool.recycle(localSegment);
          }
          return -1L;
        }
        localSegment.limit += i;
        paramBuffer.setSize$okio(paramBuffer.size() + i);
        return i;
      }
      catch (AssertionError paramBuffer)
      {
        if (Okio.isAndroidGetsocknameError(paramBuffer)) {
          throw ((Throwable)new IOException((Throwable)paramBuffer));
        }
        throw ((Throwable)paramBuffer);
      }
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public Timeout timeout()
  {
    return this.timeout;
  }
  
  public String toString()
  {
    return "source(" + this.input + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/InputStreamSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */