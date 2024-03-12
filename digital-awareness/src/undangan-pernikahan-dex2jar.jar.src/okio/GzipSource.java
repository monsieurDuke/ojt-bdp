package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(bv={1, 0, 3}, d1={"\000R\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\005\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\016\n\000\n\002\020\b\n\002\b\005\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\030\0002\0020\001B\r\022\006\020\002\032\0020\001¢\006\002\020\003J \020\r\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\0222\006\020\023\032\0020\022H\002J\b\020\024\032\0020\016H\026J\b\020\025\032\0020\016H\002J\b\020\026\032\0020\016H\002J\030\020\027\032\0020\0302\006\020\031\032\0020\0322\006\020\033\032\0020\030H\026J\b\020\034\032\0020\035H\026J \020\036\032\0020\0162\006\020\037\032\0020\0322\006\020 \032\0020\0302\006\020\033\032\0020\030H\002R\016\020\004\032\0020\005X\004¢\006\002\n\000R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\b\032\0020\tX\004¢\006\002\n\000R\016\020\n\032\0020\013X\016¢\006\002\n\000R\016\020\002\032\0020\fX\004¢\006\002\n\000¨\006!"}, d2={"Lokio/GzipSource;", "Lokio/Source;", "source", "(Lokio/Source;)V", "crc", "Ljava/util/zip/CRC32;", "inflater", "Ljava/util/zip/Inflater;", "inflaterSource", "Lokio/InflaterSource;", "section", "", "Lokio/RealBufferedSource;", "checkEqual", "", "name", "", "expected", "", "actual", "close", "consumeHeader", "consumeTrailer", "read", "", "sink", "Lokio/Buffer;", "byteCount", "timeout", "Lokio/Timeout;", "updateCrc", "buffer", "offset", "okio"}, k=1, mv={1, 4, 0})
public final class GzipSource
  implements Source
{
  private final CRC32 crc;
  private final Inflater inflater;
  private final InflaterSource inflaterSource;
  private byte section;
  private final RealBufferedSource source;
  
  public GzipSource(Source paramSource)
  {
    paramSource = new RealBufferedSource(paramSource);
    this.source = paramSource;
    Inflater localInflater = new Inflater(true);
    this.inflater = localInflater;
    this.inflaterSource = new InflaterSource((BufferedSource)paramSource, localInflater);
    this.crc = new CRC32();
  }
  
  private final void checkEqual(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 == paramInt1) {
      return;
    }
    paramString = String.format("%s: actual 0x%08x != expected 0x%08x", Arrays.copyOf(new Object[] { paramString, Integer.valueOf(paramInt2), Integer.valueOf(paramInt1) }, 3));
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "java.lang.String.format(this, *args)");
    throw ((Throwable)new IOException(paramString));
  }
  
  private final void consumeHeader()
    throws IOException
  {
    this.source.require(10L);
    int m = this.source.bufferField.getByte(3L);
    int k = 1;
    int i;
    if ((m >> 1 & 0x1) == 1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      updateCrc(this.source.bufferField, 0L, 10L);
    }
    checkEqual("ID1ID2", 8075, this.source.readShort());
    this.source.skip(8L);
    int j;
    if ((m >> 2 & 0x1) == 1) {
      j = 1;
    } else {
      j = 0;
    }
    long l;
    if (j != 0)
    {
      this.source.require(2L);
      if (i != 0) {
        updateCrc(this.source.bufferField, 0L, 2L);
      }
      l = this.source.bufferField.readShortLe();
      this.source.require(l);
      if (i != 0) {
        updateCrc(this.source.bufferField, 0L, l);
      }
      this.source.skip(l);
    }
    if ((m >> 3 & 0x1) == 1) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      l = this.source.indexOf((byte)0);
      if (l != -1L)
      {
        if (i != 0) {
          updateCrc(this.source.bufferField, 0L, l + 1L);
        }
        this.source.skip(l + 1L);
      }
      else
      {
        throw ((Throwable)new EOFException());
      }
    }
    if ((m >> 4 & 0x1) == 1) {
      j = k;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      l = this.source.indexOf((byte)0);
      if (l != -1L)
      {
        if (i != 0) {
          updateCrc(this.source.bufferField, 0L, l + 1L);
        }
        this.source.skip(1L + l);
      }
      else
      {
        throw ((Throwable)new EOFException());
      }
    }
    if (i != 0)
    {
      checkEqual("FHCRC", this.source.readShortLe(), (short)(int)this.crc.getValue());
      this.crc.reset();
    }
  }
  
  private final void consumeTrailer()
    throws IOException
  {
    checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
    checkEqual("ISIZE", this.source.readIntLe(), (int)this.inflater.getBytesWritten());
  }
  
  private final void updateCrc(Buffer paramBuffer, long paramLong1, long paramLong2)
  {
    paramBuffer = paramBuffer.head;
    Intrinsics.checkNotNull(paramBuffer);
    long l1;
    long l2;
    Object localObject;
    for (;;)
    {
      l1 = paramLong1;
      l2 = paramLong2;
      localObject = paramBuffer;
      if (paramLong1 < paramBuffer.limit - paramBuffer.pos) {
        break;
      }
      paramLong1 -= paramBuffer.limit - paramBuffer.pos;
      paramBuffer = paramBuffer.next;
      Intrinsics.checkNotNull(paramBuffer);
    }
    while (l2 > 0L)
    {
      int j = (int)(((Segment)localObject).pos + l1);
      int i = (int)Math.min(((Segment)localObject).limit - j, l2);
      this.crc.update(((Segment)localObject).data, j, i);
      l2 -= i;
      l1 = 0L;
      localObject = ((Segment)localObject).next;
      Intrinsics.checkNotNull(localObject);
    }
  }
  
  public void close()
    throws IOException
  {
    this.inflaterSource.close();
  }
  
  public long read(Buffer paramBuffer, long paramLong)
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
      if (paramLong == 0L) {
        return 0L;
      }
      if (this.section == 0)
      {
        consumeHeader();
        this.section = 1;
      }
      if (this.section == 1)
      {
        long l = paramBuffer.size();
        paramLong = this.inflaterSource.read(paramBuffer, paramLong);
        if (paramLong != -1L)
        {
          updateCrc(paramBuffer, l, paramLong);
          return paramLong;
        }
        this.section = 2;
      }
      if (this.section == 2)
      {
        consumeTrailer();
        this.section = 3;
        if (!this.source.exhausted()) {
          throw ((Throwable)new IOException("gzip finished without exhausting source"));
        }
      }
      return -1L;
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public Timeout timeout()
  {
    return this.source.timeout();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/GzipSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */