package okio;

import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\000\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\b\020\007\032\0020\bH\026J\b\020\t\032\0020\bH\026J\b\020\004\032\0020\005H\026J\b\020\n\032\0020\013H\026J\030\020\f\032\0020\b2\006\020\r\032\0020\0162\006\020\017\032\0020\020H\026R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\021"}, d2={"Lokio/OutputStreamSink;", "Lokio/Sink;", "out", "Ljava/io/OutputStream;", "timeout", "Lokio/Timeout;", "(Ljava/io/OutputStream;Lokio/Timeout;)V", "close", "", "flush", "toString", "", "write", "source", "Lokio/Buffer;", "byteCount", "", "okio"}, k=1, mv={1, 4, 0})
final class OutputStreamSink
  implements Sink
{
  private final OutputStream out;
  private final Timeout timeout;
  
  public OutputStreamSink(OutputStream paramOutputStream, Timeout paramTimeout)
  {
    this.out = paramOutputStream;
    this.timeout = paramTimeout;
  }
  
  public void close()
  {
    this.out.close();
  }
  
  public void flush()
  {
    this.out.flush();
  }
  
  public Timeout timeout()
  {
    return this.timeout;
  }
  
  public String toString()
  {
    return "sink(" + this.out + ')';
  }
  
  public void write(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "source");
    _Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramLong);
    while (paramLong > 0L)
    {
      this.timeout.throwIfReached();
      Segment localSegment = paramBuffer.head;
      Intrinsics.checkNotNull(localSegment);
      int i = (int)Math.min(paramLong, localSegment.limit - localSegment.pos);
      this.out.write(localSegment.data, localSegment.pos, i);
      localSegment.pos += i;
      paramLong -= i;
      paramBuffer.setSize$okio(paramBuffer.size() - i);
      if (localSegment.pos == localSegment.limit)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/OutputStreamSink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */