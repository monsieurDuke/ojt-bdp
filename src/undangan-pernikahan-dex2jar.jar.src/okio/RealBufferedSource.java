package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okio.internal.BufferKt;

@Metadata(bv={1, 0, 3}, d1={"\000\001\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\006\n\002\020\013\n\000\n\002\020\002\n\002\b\002\n\002\020\t\n\000\n\002\020\005\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\003\n\002\030\002\n\002\020\022\n\000\n\002\030\002\n\002\b\013\n\002\020\n\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\005\032\0020\006H\026J\b\020\016\032\0020\017H\026J\b\020\020\032\0020\rH\026J\020\020\021\032\0020\0222\006\020\023\032\0020\024H\026J\030\020\021\032\0020\0222\006\020\023\032\0020\0242\006\020\025\032\0020\022H\026J \020\021\032\0020\0222\006\020\023\032\0020\0242\006\020\025\032\0020\0222\006\020\026\032\0020\022H\026J\020\020\021\032\0020\0222\006\020\027\032\0020\030H\026J\030\020\021\032\0020\0222\006\020\027\032\0020\0302\006\020\025\032\0020\022H\026J\020\020\031\032\0020\0222\006\020\032\032\0020\030H\026J\030\020\031\032\0020\0222\006\020\032\032\0020\0302\006\020\025\032\0020\022H\026J\b\020\033\032\0020\034H\026J\b\020\035\032\0020\rH\026J\b\020\036\032\0020\001H\026J\030\020\037\032\0020\r2\006\020 \032\0020\0222\006\020\027\032\0020\030H\026J(\020\037\032\0020\r2\006\020 \032\0020\0222\006\020\027\032\0020\0302\006\020!\032\0020\"2\006\020#\032\0020\"H\026J\020\020$\032\0020\"2\006\020%\032\0020&H\026J\020\020$\032\0020\"2\006\020%\032\0020'H\026J \020$\032\0020\"2\006\020%\032\0020'2\006\020 \032\0020\"2\006\020#\032\0020\"H\026J\030\020$\032\0020\0222\006\020%\032\0020\0062\006\020#\032\0020\022H\026J\020\020(\032\0020\0222\006\020%\032\0020)H\026J\b\020*\032\0020\024H\026J\b\020+\032\0020'H\026J\020\020+\032\0020'2\006\020#\032\0020\022H\026J\b\020,\032\0020\030H\026J\020\020,\032\0020\0302\006\020#\032\0020\022H\026J\b\020-\032\0020\022H\026J\020\020.\032\0020\0172\006\020%\032\0020'H\026J\030\020.\032\0020\0172\006\020%\032\0020\0062\006\020#\032\0020\022H\026J\b\020/\032\0020\022H\026J\b\0200\032\0020\"H\026J\b\0201\032\0020\"H\026J\b\0202\032\0020\022H\026J\b\0203\032\0020\022H\026J\b\0204\032\00205H\026J\b\0206\032\00205H\026J\020\0207\032\002082\006\0209\032\0020:H\026J\030\0207\032\002082\006\020#\032\0020\0222\006\0209\032\0020:H\026J\b\020;\032\00208H\026J\020\020;\032\002082\006\020#\032\0020\022H\026J\b\020<\032\0020\"H\026J\n\020=\032\004\030\00108H\026J\b\020>\032\00208H\026J\020\020>\032\002082\006\020?\032\0020\022H\026J\020\020@\032\0020\r2\006\020#\032\0020\022H\026J\020\020A\032\0020\0172\006\020#\032\0020\022H\026J\020\020B\032\0020\"2\006\020C\032\0020DH\026J\020\020E\032\0020\0172\006\020#\032\0020\022H\026J\b\020F\032\0020GH\026J\b\020H\032\00208H\026R\033\020\005\032\0020\0068Ö\002X\004¢\006\f\022\004\b\007\020\b\032\004\b\t\020\nR\020\020\013\032\0020\0068\006X\004¢\006\002\n\000R\022\020\f\032\0020\r8\006@\006X\016¢\006\002\n\000R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006I"}, d2={"Lokio/RealBufferedSource;", "Lokio/BufferedSource;", "source", "Lokio/Source;", "(Lokio/Source;)V", "buffer", "Lokio/Buffer;", "getBuffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "bufferField", "closed", "", "close", "", "exhausted", "indexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "indexOfElement", "targetBytes", "inputStream", "Ljava/io/InputStream;", "isOpen", "peek", "rangeEquals", "offset", "bytesOffset", "", "byteCount", "read", "sink", "Ljava/nio/ByteBuffer;", "", "readAll", "Lokio/Sink;", "readByte", "readByteArray", "readByteString", "readDecimalLong", "readFully", "readHexadecimalUnsignedLong", "readInt", "readIntLe", "readLong", "readLongLe", "readShort", "", "readShortLe", "readString", "", "charset", "Ljava/nio/charset/Charset;", "readUtf8", "readUtf8CodePoint", "readUtf8Line", "readUtf8LineStrict", "limit", "request", "require", "select", "options", "Lokio/Options;", "skip", "timeout", "Lokio/Timeout;", "toString", "okio"}, k=1, mv={1, 4, 0})
public final class RealBufferedSource
  implements BufferedSource
{
  public final Buffer bufferField;
  public boolean closed;
  public final Source source;
  
  public RealBufferedSource(Source paramSource)
  {
    this.source = paramSource;
    this.bufferField = new Buffer();
  }
  
  public Buffer buffer()
  {
    return this.bufferField;
  }
  
  public void close()
  {
    if (!this.closed)
    {
      this.closed = true;
      this.source.close();
      this.bufferField.clear();
    }
  }
  
  public boolean exhausted()
  {
    boolean bool2 = this.closed;
    boolean bool1 = true;
    if ((bool2 ^ true))
    {
      if ((!this.bufferField.exhausted()) || (this.source.read(this.bufferField, ' ') != -1L)) {
        bool1 = false;
      }
      return bool1;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public Buffer getBuffer()
  {
    return this.bufferField;
  }
  
  public long indexOf(byte paramByte)
  {
    return indexOf(paramByte, 0L, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong)
  {
    return indexOf(paramByte, paramLong, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong1, long paramLong2)
  {
    boolean bool = this.closed;
    int i = 1;
    if ((bool ^ true))
    {
      if ((0L > paramLong1) || (paramLong2 < paramLong1)) {
        i = 0;
      }
      if (i != 0)
      {
        long l2;
        for (;;)
        {
          l2 = -1L;
          if (paramLong1 >= paramLong2) {
            break;
          }
          l1 = this.bufferField.indexOf(paramByte, paramLong1, paramLong2);
          if (l1 != -1L) {
            break label146;
          }
          long l3 = this.bufferField.size();
          l1 = l2;
          if (l3 >= paramLong2) {
            break label146;
          }
          if (this.source.read(this.bufferField, ' ') == -1L)
          {
            l1 = l2;
            break label146;
          }
          paramLong1 = Math.max(paramLong1, l3);
        }
        long l1 = l2;
        label146:
        return l1;
      }
      throw ((Throwable)new IllegalArgumentException(("fromIndex=" + paramLong1 + " toIndex=" + paramLong2).toString()));
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public long indexOf(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    return indexOf(paramByteString, 0L);
  }
  
  public long indexOf(ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    if ((this.closed ^ true)) {
      for (;;)
      {
        long l = this.bufferField.indexOf(paramByteString, paramLong);
        if (l != -1L)
        {
          paramLong = l;
        }
        else
        {
          l = this.bufferField.size();
          if (this.source.read(this.bufferField, ' ') != -1L) {
            break label80;
          }
          paramLong = -1L;
        }
        return paramLong;
        label80:
        paramLong = Math.max(paramLong, l - paramByteString.size() + 1L);
      }
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public long indexOfElement(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "targetBytes");
    return indexOfElement(paramByteString, 0L);
  }
  
  public long indexOfElement(ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "targetBytes");
    if ((this.closed ^ true)) {
      for (;;)
      {
        long l = this.bufferField.indexOfElement(paramByteString, paramLong);
        if (l != -1L)
        {
          paramLong = l;
        }
        else
        {
          l = this.bufferField.size();
          if (this.source.read(this.bufferField, ' ') != -1L) {
            break label80;
          }
          paramLong = -1L;
        }
        return paramLong;
        label80:
        paramLong = Math.max(paramLong, l);
      }
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public InputStream inputStream()
  {
    (InputStream)new InputStream()
    {
      final RealBufferedSource this$0;
      
      public int available()
      {
        if (!this.this$0.closed) {
          return (int)Math.min(this.this$0.bufferField.size(), Integer.MAX_VALUE);
        }
        throw ((Throwable)new IOException("closed"));
      }
      
      public void close()
      {
        this.this$0.close();
      }
      
      public int read()
      {
        if (!this.this$0.closed)
        {
          if ((this.this$0.bufferField.size() == 0L) && (this.this$0.source.read(this.this$0.bufferField, ' ') == -1L)) {
            return -1;
          }
          return this.this$0.bufferField.readByte() & 0xFF;
        }
        throw ((Throwable)new IOException("closed"));
      }
      
      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousArrayOfByte, "data");
        if (!this.this$0.closed)
        {
          _Util.checkOffsetAndCount(paramAnonymousArrayOfByte.length, paramAnonymousInt1, paramAnonymousInt2);
          if ((this.this$0.bufferField.size() == 0L) && (this.this$0.source.read(this.this$0.bufferField, ' ') == -1L)) {
            return -1;
          }
          return this.this$0.bufferField.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
        }
        throw ((Throwable)new IOException("closed"));
      }
      
      public String toString()
      {
        return this.this$0 + ".inputStream()";
      }
    };
  }
  
  public boolean isOpen()
  {
    return this.closed ^ true;
  }
  
  public BufferedSource peek()
  {
    return Okio.buffer((Source)new PeekSource((BufferedSource)this));
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    return rangeEquals(paramLong, paramByteString, 0, paramByteString.size());
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    boolean bool2 = this.closed;
    boolean bool1 = true;
    if ((bool2 ^ true))
    {
      if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (paramByteString.size() - paramInt1 >= paramInt2)) {
        for (int i = 0; i < paramInt2; i++)
        {
          long l = i + paramLong;
          if (!request(1L + l))
          {
            bool1 = false;
            break;
          }
          if (this.bufferField.getByte(l) != paramByteString.getByte(paramInt1 + i))
          {
            bool1 = false;
            break;
          }
        }
      } else {
        bool1 = false;
      }
      return bool1;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public int read(ByteBuffer paramByteBuffer)
  {
    Intrinsics.checkNotNullParameter(paramByteBuffer, "sink");
    if ((this.bufferField.size() == 0L) && (this.source.read(this.bufferField, ' ') == -1L)) {
      return -1;
    }
    return this.bufferField.read(paramByteBuffer);
  }
  
  public int read(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    if ((this.bufferField.size() == 0L) && (this.source.read(this.bufferField, ' ') == -1L))
    {
      paramInt1 = -1;
    }
    else
    {
      long l = this.bufferField.size();
      paramInt2 = (int)Math.min(paramInt2, l);
      paramInt1 = this.bufferField.read(paramArrayOfByte, paramInt1, paramInt2);
    }
    return paramInt1;
  }
  
  public long read(Buffer paramBuffer, long paramLong)
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
        long l2 = this.bufferField.size();
        long l1 = -1L;
        if ((l2 == 0L) && (this.source.read(this.bufferField, ' ') == -1L))
        {
          paramLong = l1;
        }
        else
        {
          paramLong = Math.min(paramLong, this.bufferField.size());
          paramLong = this.bufferField.read(paramBuffer, paramLong);
        }
        return paramLong;
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public long readAll(Sink paramSink)
  {
    Intrinsics.checkNotNullParameter(paramSink, "sink");
    for (long l1 = 0L; this.source.read(this.bufferField, ' ') != -1L; l1 = l2)
    {
      long l3 = this.bufferField.completeSegmentByteCount();
      l2 = l1;
      if (l3 > 0L)
      {
        l2 = l1 + l3;
        paramSink.write(this.bufferField, l3);
      }
    }
    long l2 = l1;
    if (this.bufferField.size() > 0L)
    {
      l2 = l1 + this.bufferField.size();
      paramSink.write(this.bufferField, this.bufferField.size());
    }
    return l2;
  }
  
  public byte readByte()
  {
    require(1L);
    return this.bufferField.readByte();
  }
  
  public byte[] readByteArray()
  {
    this.bufferField.writeAll(this.source);
    return this.bufferField.readByteArray();
  }
  
  public byte[] readByteArray(long paramLong)
  {
    require(paramLong);
    return this.bufferField.readByteArray(paramLong);
  }
  
  public ByteString readByteString()
  {
    this.bufferField.writeAll(this.source);
    return this.bufferField.readByteString();
  }
  
  public ByteString readByteString(long paramLong)
  {
    require(paramLong);
    return this.bufferField.readByteString(paramLong);
  }
  
  public long readDecimalLong()
  {
    require(1L);
    long l = 0L;
    while (request(l + 1L))
    {
      int i = this.bufferField.getByte(l);
      if (((i >= (byte)48) && (i <= (byte)57)) || ((l == 0L) && (i == (byte)45)))
      {
        l += 1L;
      }
      else if (l == 0L)
      {
        StringBuilder localStringBuilder = new StringBuilder().append("Expected leading [0-9] or '-' character but was 0x");
        String str = Integer.toString(i, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        throw ((Throwable)new NumberFormatException(str));
      }
    }
    return this.bufferField.readDecimalLong();
  }
  
  public void readFully(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    try
    {
      require(paramLong);
      this.bufferField.readFully(paramBuffer, paramLong);
      return;
    }
    catch (EOFException localEOFException)
    {
      paramBuffer.writeAll((Source)this.bufferField);
      throw ((Throwable)localEOFException);
    }
  }
  
  public void readFully(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    try
    {
      require(paramArrayOfByte.length);
      this.bufferField.readFully(paramArrayOfByte);
      return;
    }
    catch (EOFException localEOFException)
    {
      int i = 0;
      while (this.bufferField.size() > 0L)
      {
        int j = this.bufferField.read(paramArrayOfByte, i, (int)this.bufferField.size());
        if (j != -1) {
          i += j;
        } else {
          throw ((Throwable)new AssertionError());
        }
      }
      throw ((Throwable)localEOFException);
    }
  }
  
  public long readHexadecimalUnsignedLong()
  {
    require(1L);
    int i = 0;
    while (request(i + 1))
    {
      int j = this.bufferField.getByte(i);
      if (((j >= (byte)48) && (j <= (byte)57)) || ((j >= (byte)97) && (j <= (byte)102)) || ((j >= (byte)65) && (j <= (byte)70)))
      {
        i++;
      }
      else if (i == 0)
      {
        StringBuilder localStringBuilder = new StringBuilder().append("Expected leading [0-9a-fA-F] character but was 0x");
        String str = Integer.toString(j, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        throw ((Throwable)new NumberFormatException(str));
      }
    }
    return this.bufferField.readHexadecimalUnsignedLong();
  }
  
  public int readInt()
  {
    require(4L);
    return this.bufferField.readInt();
  }
  
  public int readIntLe()
  {
    require(4L);
    return this.bufferField.readIntLe();
  }
  
  public long readLong()
  {
    require(8L);
    return this.bufferField.readLong();
  }
  
  public long readLongLe()
  {
    require(8L);
    return this.bufferField.readLongLe();
  }
  
  public short readShort()
  {
    require(2L);
    return this.bufferField.readShort();
  }
  
  public short readShortLe()
  {
    require(2L);
    return this.bufferField.readShortLe();
  }
  
  public String readString(long paramLong, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    require(paramLong);
    return this.bufferField.readString(paramLong, paramCharset);
  }
  
  public String readString(Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    this.bufferField.writeAll(this.source);
    return this.bufferField.readString(paramCharset);
  }
  
  public String readUtf8()
  {
    this.bufferField.writeAll(this.source);
    return this.bufferField.readUtf8();
  }
  
  public String readUtf8(long paramLong)
  {
    require(paramLong);
    return this.bufferField.readUtf8(paramLong);
  }
  
  public int readUtf8CodePoint()
  {
    require(1L);
    int i = this.bufferField.getByte(0L);
    if ((i & 0xE0) == 192) {
      require(2L);
    } else if ((i & 0xF0) == 224) {
      require(3L);
    } else if ((i & 0xF8) == 240) {
      require(4L);
    }
    return this.bufferField.readUtf8CodePoint();
  }
  
  public String readUtf8Line()
  {
    long l = indexOf((byte)10);
    String str;
    if (l == -1L)
    {
      if (this.bufferField.size() != 0L) {
        str = readUtf8(this.bufferField.size());
      } else {
        str = null;
      }
    }
    else
    {
      str = BufferKt.readUtf8Line(this.bufferField, l);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
    }
    return str;
  }
  
  public String readUtf8LineStrict()
  {
    return readUtf8LineStrict(Long.MAX_VALUE);
  }
  
  public String readUtf8LineStrict(long paramLong)
  {
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramLong == Long.MAX_VALUE) {
        l1 = Long.MAX_VALUE;
      } else {
        l1 = paramLong + 1L;
      }
      byte b = (byte)10;
      long l2 = indexOf(b, 0L, l1);
      if (l2 != -1L)
      {
        localObject = BufferKt.readUtf8Line(this.bufferField, l2);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
      }
      else
      {
        if ((l1 >= Long.MAX_VALUE) || (!request(l1)) || (this.bufferField.getByte(l1 - 1L) != (byte)13) || (!request(1L + l1)) || (this.bufferField.getByte(l1) != b)) {
          break label181;
        }
        localObject = BufferKt.readUtf8Line(this.bufferField, l1);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
      }
      return (String)localObject;
      label181:
      Buffer localBuffer = new Buffer();
      Object localObject = this.bufferField;
      long l1 = this.bufferField.size();
      ((Buffer)localObject).copyTo(localBuffer, 0L, Math.min(32, l1));
      throw ((Throwable)new EOFException("\\n not found: limit=" + Math.min(this.bufferField.size(), paramLong) + " content=" + localBuffer.readByteString().hex() + "…"));
    }
    throw ((Throwable)new IllegalArgumentException(("limit < 0: " + paramLong).toString()));
  }
  
  public boolean request(long paramLong)
  {
    boolean bool = false;
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
        while (this.bufferField.size() < paramLong) {
          if (this.source.read(this.bufferField, ' ') == -1L) {
            break label74;
          }
        }
        bool = true;
        label74:
        return bool;
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public void require(long paramLong)
  {
    if (request(paramLong)) {
      return;
    }
    throw ((Throwable)new EOFException());
  }
  
  public int select(Options paramOptions)
  {
    Intrinsics.checkNotNullParameter(paramOptions, "options");
    if ((this.closed ^ true))
    {
      int i;
      for (;;)
      {
        i = BufferKt.selectPrefix(this.bufferField, paramOptions, true);
        switch (i)
        {
        default: 
          int j = paramOptions.getByteStrings$okio()[i].size();
          this.bufferField.skip(j);
          break;
        case -1: 
          i = -1;
          break;
        case -2: 
          if (this.source.read(this.bufferField, ' ') == -1L)
          {
            i = -1;
            break;
          }
          break;
        }
      }
      return i;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public void skip(long paramLong)
  {
    if ((this.closed ^ true))
    {
      while (paramLong > 0L)
      {
        if ((this.bufferField.size() == 0L) && (this.source.read(this.bufferField, ' ') == -1L)) {
          throw ((Throwable)new EOFException());
        }
        long l = Math.min(paramLong, this.bufferField.size());
        this.bufferField.skip(l);
        paramLong -= l;
      }
      return;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public Timeout timeout()
  {
    return this.source.timeout();
  }
  
  public String toString()
  {
    return "buffer(" + this.source + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/RealBufferedSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */