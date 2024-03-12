package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000f\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\006\n\002\020\013\n\000\n\002\020\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\002\020\022\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\030\002\n\002\b\021\n\002\030\002\n\002\b\006\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\005\032\0020\006H\026J\b\020\016\032\0020\017H\026J\b\020\020\032\0020\001H\026J\b\020\021\032\0020\001H\026J\b\020\022\032\0020\017H\026J\b\020\023\032\0020\rH\026J\b\020\024\032\0020\025H\026J\b\020\026\032\0020\027H\026J\b\020\030\032\0020\031H\026J\020\020\032\032\0020\0332\006\020\034\032\0020\035H\026J\020\020\032\032\0020\0012\006\020\034\032\0020\036H\026J \020\032\032\0020\0012\006\020\034\032\0020\0362\006\020\037\032\0020\0332\006\020 \032\0020\033H\026J\030\020\032\032\0020\0172\006\020\034\032\0020\0062\006\020 \032\0020!H\026J\020\020\032\032\0020\0012\006\020\"\032\0020#H\026J \020\032\032\0020\0012\006\020\"\032\0020#2\006\020\037\032\0020\0332\006\020 \032\0020\033H\026J\030\020\032\032\0020\0012\006\020\034\032\0020$2\006\020 \032\0020!H\026J\020\020%\032\0020!2\006\020\034\032\0020$H\026J\020\020&\032\0020\0012\006\020'\032\0020\033H\026J\020\020(\032\0020\0012\006\020)\032\0020!H\026J\020\020*\032\0020\0012\006\020)\032\0020!H\026J\020\020+\032\0020\0012\006\020,\032\0020\033H\026J\020\020-\032\0020\0012\006\020,\032\0020\033H\026J\020\020.\032\0020\0012\006\020)\032\0020!H\026J\020\020/\032\0020\0012\006\020)\032\0020!H\026J\020\0200\032\0020\0012\006\0201\032\0020\033H\026J\020\0202\032\0020\0012\006\0201\032\0020\033H\026J\030\0203\032\0020\0012\006\0204\032\0020\0312\006\0205\032\00206H\026J(\0203\032\0020\0012\006\0204\032\0020\0312\006\0207\032\0020\0332\006\0208\032\0020\0332\006\0205\032\00206H\026J\020\0209\032\0020\0012\006\0204\032\0020\031H\026J \0209\032\0020\0012\006\0204\032\0020\0312\006\0207\032\0020\0332\006\0208\032\0020\033H\026J\020\020:\032\0020\0012\006\020;\032\0020\033H\026R\033\020\005\032\0020\0068Ö\002X\004¢\006\f\022\004\b\007\020\b\032\004\b\t\020\nR\020\020\013\032\0020\0068\006X\004¢\006\002\n\000R\022\020\f\032\0020\r8\006@\006X\016¢\006\002\n\000R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006<"}, d2={"Lokio/RealBufferedSink;", "Lokio/BufferedSink;", "sink", "Lokio/Sink;", "(Lokio/Sink;)V", "buffer", "Lokio/Buffer;", "getBuffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "bufferField", "closed", "", "close", "", "emit", "emitCompleteSegments", "flush", "isOpen", "outputStream", "Ljava/io/OutputStream;", "timeout", "Lokio/Timeout;", "toString", "", "write", "", "source", "Ljava/nio/ByteBuffer;", "", "offset", "byteCount", "", "byteString", "Lokio/ByteString;", "Lokio/Source;", "writeAll", "writeByte", "b", "writeDecimalLong", "v", "writeHexadecimalUnsignedLong", "writeInt", "i", "writeIntLe", "writeLong", "writeLongLe", "writeShort", "s", "writeShortLe", "writeString", "string", "charset", "Ljava/nio/charset/Charset;", "beginIndex", "endIndex", "writeUtf8", "writeUtf8CodePoint", "codePoint", "okio"}, k=1, mv={1, 4, 0})
public final class RealBufferedSink
  implements BufferedSink
{
  public final Buffer bufferField;
  public boolean closed;
  public final Sink sink;
  
  public RealBufferedSink(Sink paramSink)
  {
    this.sink = paramSink;
    this.bufferField = new Buffer();
  }
  
  public Buffer buffer()
  {
    return this.bufferField;
  }
  
  /* Error */
  public void close()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 96	okio/RealBufferedSink:closed	Z
    //   4: ifeq +6 -> 10
    //   7: goto +80 -> 87
    //   10: aconst_null
    //   11: checkcast 98	java/lang/Throwable
    //   14: astore_2
    //   15: aload_2
    //   16: astore_1
    //   17: aload_0
    //   18: getfield 93	okio/RealBufferedSink:bufferField	Lokio/Buffer;
    //   21: invokevirtual 102	okio/Buffer:size	()J
    //   24: lconst_0
    //   25: lcmp
    //   26: ifle +29 -> 55
    //   29: aload_0
    //   30: getfield 88	okio/RealBufferedSink:sink	Lokio/Sink;
    //   33: aload_0
    //   34: getfield 93	okio/RealBufferedSink:bufferField	Lokio/Buffer;
    //   37: aload_0
    //   38: getfield 93	okio/RealBufferedSink:bufferField	Lokio/Buffer;
    //   41: invokevirtual 102	okio/Buffer:size	()J
    //   44: invokeinterface 107 4 0
    //   49: aload_2
    //   50: astore_1
    //   51: goto +4 -> 55
    //   54: astore_1
    //   55: aload_0
    //   56: getfield 88	okio/RealBufferedSink:sink	Lokio/Sink;
    //   59: invokeinterface 109 1 0
    //   64: aload_1
    //   65: astore_2
    //   66: goto +12 -> 78
    //   69: astore_3
    //   70: aload_1
    //   71: astore_2
    //   72: aload_1
    //   73: ifnonnull +5 -> 78
    //   76: aload_3
    //   77: astore_2
    //   78: aload_0
    //   79: iconst_1
    //   80: putfield 96	okio/RealBufferedSink:closed	Z
    //   83: aload_2
    //   84: ifnonnull +4 -> 88
    //   87: return
    //   88: aload_2
    //   89: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	90	0	this	RealBufferedSink
    //   16	35	1	localObject1	Object
    //   54	19	1	localObject2	Object
    //   14	75	2	localObject3	Object
    //   69	8	3	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   17	49	54	finally
    //   55	64	69	finally
  }
  
  public BufferedSink emit()
  {
    if ((this.closed ^ true))
    {
      long l = this.bufferField.size();
      if (l > 0L) {
        this.sink.write(this.bufferField, l);
      }
      return (BufferedSink)this;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink emitCompleteSegments()
  {
    if ((this.closed ^ true))
    {
      long l = this.bufferField.completeSegmentByteCount();
      if (l > 0L) {
        this.sink.write(this.bufferField, l);
      }
      return (BufferedSink)this;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public void flush()
  {
    if ((this.closed ^ true))
    {
      if (this.bufferField.size() > 0L) {
        this.sink.write(this.bufferField, this.bufferField.size());
      }
      this.sink.flush();
      return;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public Buffer getBuffer()
  {
    return this.bufferField;
  }
  
  public boolean isOpen()
  {
    return this.closed ^ true;
  }
  
  public OutputStream outputStream()
  {
    (OutputStream)new OutputStream()
    {
      final RealBufferedSink this$0;
      
      public void close()
      {
        this.this$0.close();
      }
      
      public void flush()
      {
        if (!this.this$0.closed) {
          this.this$0.flush();
        }
      }
      
      public String toString()
      {
        return this.this$0 + ".outputStream()";
      }
      
      public void write(int paramAnonymousInt)
      {
        if (!this.this$0.closed)
        {
          this.this$0.bufferField.writeByte((byte)paramAnonymousInt);
          this.this$0.emitCompleteSegments();
          return;
        }
        throw ((Throwable)new IOException("closed"));
      }
      
      public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousArrayOfByte, "data");
        if (!this.this$0.closed)
        {
          this.this$0.bufferField.write(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
          this.this$0.emitCompleteSegments();
          return;
        }
        throw ((Throwable)new IOException("closed"));
      }
    };
  }
  
  public Timeout timeout()
  {
    return this.sink.timeout();
  }
  
  public String toString()
  {
    return "buffer(" + this.sink + ')';
  }
  
  public int write(ByteBuffer paramByteBuffer)
  {
    Intrinsics.checkNotNullParameter(paramByteBuffer, "source");
    if ((this.closed ^ true))
    {
      int i = this.bufferField.write(paramByteBuffer);
      emitCompleteSegments();
      return i;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink write(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "byteString");
    if ((this.closed ^ true))
    {
      this.bufferField.write(paramByteString);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink write(ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "byteString");
    if ((this.closed ^ true))
    {
      this.bufferField.write(paramByteString, paramInt1, paramInt2);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink write(Source paramSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramSource, "source");
    while (paramLong > 0L)
    {
      long l = paramSource.read(this.bufferField, paramLong);
      if (l != -1L)
      {
        paramLong -= l;
        emitCompleteSegments();
      }
      else
      {
        throw ((Throwable)new EOFException());
      }
    }
    return (BufferedSink)this;
  }
  
  public BufferedSink write(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "source");
    if ((this.closed ^ true))
    {
      this.bufferField.write(paramArrayOfByte);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "source");
    if ((this.closed ^ true))
    {
      this.bufferField.write(paramArrayOfByte, paramInt1, paramInt2);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public void write(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "source");
    if ((this.closed ^ true))
    {
      this.bufferField.write(paramBuffer, paramLong);
      emitCompleteSegments();
      return;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public long writeAll(Source paramSource)
  {
    Intrinsics.checkNotNullParameter(paramSource, "source");
    long l1 = 0L;
    for (;;)
    {
      long l2 = paramSource.read(this.bufferField, ' ');
      if (l2 == -1L) {
        return l1;
      }
      l1 += l2;
      emitCompleteSegments();
    }
  }
  
  public BufferedSink writeByte(int paramInt)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeByte(paramInt);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeDecimalLong(long paramLong)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeDecimalLong(paramLong);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeHexadecimalUnsignedLong(long paramLong)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeHexadecimalUnsignedLong(paramLong);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeInt(int paramInt)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeInt(paramInt);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeIntLe(int paramInt)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeIntLe(paramInt);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeLong(long paramLong)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeLong(paramLong);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeLongLe(long paramLong)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeLongLe(paramLong);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeShort(int paramInt)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeShort(paramInt);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeShortLe(int paramInt)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeShortLe(paramInt);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeString(String paramString, int paramInt1, int paramInt2, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    if ((this.closed ^ true))
    {
      this.bufferField.writeString(paramString, paramInt1, paramInt2, paramCharset);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeString(String paramString, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    if ((this.closed ^ true))
    {
      this.bufferField.writeString(paramString, paramCharset);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeUtf8(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    if ((this.closed ^ true))
    {
      this.bufferField.writeUtf8(paramString);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeUtf8(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    if ((this.closed ^ true))
    {
      this.bufferField.writeUtf8(paramString, paramInt1, paramInt2);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public BufferedSink writeUtf8CodePoint(int paramInt)
  {
    if ((this.closed ^ true))
    {
      this.bufferField.writeUtf8CodePoint(paramInt);
      return emitCompleteSegments();
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/RealBufferedSink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */