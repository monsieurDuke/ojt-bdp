package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\020\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\000\030\0002\0020\001B\027\b\026\022\006\020\002\032\0020\001\022\006\020\003\032\0020\004¢\006\002\020\005B\027\b\000\022\006\020\002\032\0020\006\022\006\020\003\032\0020\004¢\006\002\020\007J\b\020\f\032\0020\rH\026J\030\020\016\032\0020\0172\006\020\020\032\0020\0212\006\020\022\032\0020\017H\026J\026\020\023\032\0020\0172\006\020\020\032\0020\0212\006\020\022\032\0020\017J\006\020\024\032\0020\013J\b\020\025\032\0020\rH\002J\b\020\026\032\0020\027H\026R\016\020\b\032\0020\tX\016¢\006\002\n\000R\016\020\n\032\0020\013X\016¢\006\002\n\000R\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\002\032\0020\006X\004¢\006\002\n\000¨\006\030"}, d2={"Lokio/InflaterSource;", "Lokio/Source;", "source", "inflater", "Ljava/util/zip/Inflater;", "(Lokio/Source;Ljava/util/zip/Inflater;)V", "Lokio/BufferedSource;", "(Lokio/BufferedSource;Ljava/util/zip/Inflater;)V", "bufferBytesHeldByInflater", "", "closed", "", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "readOrInflate", "refill", "releaseBytesAfterInflate", "timeout", "Lokio/Timeout;", "okio"}, k=1, mv={1, 4, 0})
public final class InflaterSource
  implements Source
{
  private int bufferBytesHeldByInflater;
  private boolean closed;
  private final Inflater inflater;
  private final BufferedSource source;
  
  public InflaterSource(BufferedSource paramBufferedSource, Inflater paramInflater)
  {
    this.source = paramBufferedSource;
    this.inflater = paramInflater;
  }
  
  public InflaterSource(Source paramSource, Inflater paramInflater)
  {
    this(Okio.buffer(paramSource), paramInflater);
  }
  
  private final void releaseBytesAfterInflate()
  {
    int i = this.bufferBytesHeldByInflater;
    if (i == 0) {
      return;
    }
    i -= this.inflater.getRemaining();
    this.bufferBytesHeldByInflater -= i;
    this.source.skip(i);
  }
  
  public void close()
    throws IOException
  {
    if (this.closed) {
      return;
    }
    this.inflater.end();
    this.closed = true;
    this.source.close();
  }
  
  public long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    do
    {
      long l = readOrInflate(paramBuffer, paramLong);
      if (l > 0L) {
        return l;
      }
      if ((this.inflater.finished()) || (this.inflater.needsDictionary())) {
        break;
      }
    } while (!this.source.exhausted());
    throw ((Throwable)new EOFException("source exhausted prematurely"));
    return -1L;
  }
  
  public final long readOrInflate(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if ((this.closed ^ true))
      {
        if (paramLong == 0L) {
          return 0L;
        }
        try
        {
          Segment localSegment = paramBuffer.writableSegment$okio(1);
          i = (int)Math.min(paramLong, 8192 - localSegment.limit);
          refill();
          i = this.inflater.inflate(localSegment.data, localSegment.limit, i);
          releaseBytesAfterInflate();
          if (i > 0)
          {
            localSegment.limit += i;
            paramBuffer.setSize$okio(paramBuffer.size() + i);
            return i;
          }
          if (localSegment.pos == localSegment.limit)
          {
            paramBuffer.head = localSegment.pop();
            SegmentPool.recycle(localSegment);
          }
          return 0L;
        }
        catch (DataFormatException paramBuffer)
        {
          throw ((Throwable)new IOException((Throwable)paramBuffer));
        }
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public final boolean refill()
    throws IOException
  {
    if (!this.inflater.needsInput()) {
      return false;
    }
    if (this.source.exhausted()) {
      return true;
    }
    Segment localSegment = this.source.getBuffer().head;
    Intrinsics.checkNotNull(localSegment);
    this.bufferBytesHeldByInflater = (localSegment.limit - localSegment.pos);
    this.inflater.setInput(localSegment.data, localSegment.pos, this.bufferBytesHeldByInflater);
    return false;
  }
  
  public Timeout timeout()
  {
    return this.source.timeout();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/InflaterSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */