package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.Buffer.UnsafeCursor;
import okio.BufferedSink;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\000V\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\002\n\002\030\002\n\000\n\002\020\022\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\020\002\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\b\t\030\0002\0020\001B5\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\003\022\006\020\t\032\0020\003\022\006\020\n\032\0020\013¢\006\002\020\fJ\b\020\033\032\0020\034H\026J\030\020\035\032\0020\0342\006\020\036\032\0020\0372\b\020 \032\004\030\0010!J\030\020\"\032\0020\0342\006\020#\032\0020\0372\006\020$\032\0020!H\002J\026\020%\032\0020\0342\006\020&\032\0020\0372\006\020'\032\0020!J\016\020(\032\0020\0342\006\020$\032\0020!J\016\020)\032\0020\0342\006\020$\032\0020!R\016\020\002\032\0020\003X\004¢\006\002\n\000R\020\020\r\032\004\030\0010\016X\004¢\006\002\n\000R\020\020\017\032\004\030\0010\020X\004¢\006\002\n\000R\016\020\021\032\0020\022X\004¢\006\002\n\000R\020\020\023\032\004\030\0010\024X\016¢\006\002\n\000R\016\020\n\032\0020\013X\004¢\006\002\n\000R\016\020\t\032\0020\003X\004¢\006\002\n\000R\016\020\b\032\0020\003X\004¢\006\002\n\000R\021\020\006\032\0020\007¢\006\b\n\000\032\004\b\025\020\026R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\027\020\030R\016\020\031\032\0020\022X\004¢\006\002\n\000R\016\020\032\032\0020\003X\016¢\006\002\n\000¨\006*"}, d2={"Lokhttp3/internal/ws/WebSocketWriter;", "Ljava/io/Closeable;", "isClient", "", "sink", "Lokio/BufferedSink;", "random", "Ljava/util/Random;", "perMessageDeflate", "noContextTakeover", "minimumDeflateSize", "", "(ZLokio/BufferedSink;Ljava/util/Random;ZZJ)V", "maskCursor", "Lokio/Buffer$UnsafeCursor;", "maskKey", "", "messageBuffer", "Lokio/Buffer;", "messageDeflater", "Lokhttp3/internal/ws/MessageDeflater;", "getRandom", "()Ljava/util/Random;", "getSink", "()Lokio/BufferedSink;", "sinkBuffer", "writerClosed", "close", "", "writeClose", "code", "", "reason", "Lokio/ByteString;", "writeControlFrame", "opcode", "payload", "writeMessageFrame", "formatOpcode", "data", "writePing", "writePong", "okhttp"}, k=1, mv={1, 4, 0})
public final class WebSocketWriter
  implements Closeable
{
  private final boolean isClient;
  private final Buffer.UnsafeCursor maskCursor;
  private final byte[] maskKey;
  private final Buffer messageBuffer;
  private MessageDeflater messageDeflater;
  private final long minimumDeflateSize;
  private final boolean noContextTakeover;
  private final boolean perMessageDeflate;
  private final Random random;
  private final BufferedSink sink;
  private final Buffer sinkBuffer;
  private boolean writerClosed;
  
  public WebSocketWriter(boolean paramBoolean1, BufferedSink paramBufferedSink, Random paramRandom, boolean paramBoolean2, boolean paramBoolean3, long paramLong)
  {
    this.isClient = paramBoolean1;
    this.sink = paramBufferedSink;
    this.random = paramRandom;
    this.perMessageDeflate = paramBoolean2;
    this.noContextTakeover = paramBoolean3;
    this.minimumDeflateSize = paramLong;
    this.messageBuffer = new Buffer();
    this.sinkBuffer = paramBufferedSink.getBuffer();
    paramRandom = null;
    if (paramBoolean1) {
      paramBufferedSink = new byte[4];
    } else {
      paramBufferedSink = null;
    }
    this.maskKey = paramBufferedSink;
    paramBufferedSink = paramRandom;
    if (paramBoolean1) {
      paramBufferedSink = new Buffer.UnsafeCursor();
    }
    this.maskCursor = paramBufferedSink;
  }
  
  private final void writeControlFrame(int paramInt, ByteString paramByteString)
    throws IOException
  {
    if (!this.writerClosed)
    {
      int j = paramByteString.size();
      int i;
      if (j <= 125L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this.sinkBuffer.writeByte(paramInt | 0x80);
        if (this.isClient)
        {
          this.sinkBuffer.writeByte(j | 0x80);
          Object localObject = this.random;
          byte[] arrayOfByte = this.maskKey;
          Intrinsics.checkNotNull(arrayOfByte);
          ((Random)localObject).nextBytes(arrayOfByte);
          this.sinkBuffer.write(this.maskKey);
          if (j > 0)
          {
            long l = this.sinkBuffer.size();
            this.sinkBuffer.write(paramByteString);
            paramByteString = this.sinkBuffer;
            localObject = this.maskCursor;
            Intrinsics.checkNotNull(localObject);
            paramByteString.readAndWriteUnsafe((Buffer.UnsafeCursor)localObject);
            this.maskCursor.seek(l);
            WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
            this.maskCursor.close();
          }
        }
        else
        {
          this.sinkBuffer.writeByte(j);
          this.sinkBuffer.write(paramByteString);
        }
        this.sink.flush();
        return;
      }
      throw ((Throwable)new IllegalArgumentException("Payload size must be less than or equal to 125".toString()));
    }
    throw ((Throwable)new IOException("closed"));
  }
  
  public void close()
  {
    MessageDeflater localMessageDeflater = this.messageDeflater;
    if (localMessageDeflater != null) {
      localMessageDeflater.close();
    }
  }
  
  public final Random getRandom()
  {
    return this.random;
  }
  
  public final BufferedSink getSink()
  {
    return this.sink;
  }
  
  public final void writeClose(int paramInt, ByteString paramByteString)
    throws IOException
  {
    Object localObject = ByteString.EMPTY;
    if ((paramInt != 0) || (paramByteString != null))
    {
      if (paramInt != 0) {
        WebSocketProtocol.INSTANCE.validateCloseCode(paramInt);
      }
      localObject = new Buffer();
      ((Buffer)localObject).writeShort(paramInt);
      if (paramByteString != null) {
        ((Buffer)localObject).write(paramByteString);
      }
      localObject = ((Buffer)localObject).readByteString();
    }
    try
    {
      writeControlFrame(8, (ByteString)localObject);
      return;
    }
    finally
    {
      this.writerClosed = true;
    }
  }
  
  public final void writeMessageFrame(int paramInt, ByteString paramByteString)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteString, "data");
    if (!this.writerClosed)
    {
      this.messageBuffer.write(paramByteString);
      int i = paramInt | 0x80;
      paramInt = i;
      if (this.perMessageDeflate)
      {
        paramInt = i;
        if (paramByteString.size() >= this.minimumDeflateSize)
        {
          paramByteString = this.messageDeflater;
          if (paramByteString == null)
          {
            paramByteString = new MessageDeflater(this.noContextTakeover);
            this.messageDeflater = paramByteString;
          }
          paramByteString.deflate(this.messageBuffer);
          paramInt = i | 0x40;
        }
      }
      long l = this.messageBuffer.size();
      this.sinkBuffer.writeByte(paramInt);
      paramInt = 0;
      if (this.isClient) {
        paramInt = 0x0 | 0x80;
      }
      if (l <= 125L)
      {
        i = (int)l;
        this.sinkBuffer.writeByte(paramInt | i);
      }
      else if (l <= 65535L)
      {
        this.sinkBuffer.writeByte(paramInt | 0x7E);
        this.sinkBuffer.writeShort((int)l);
      }
      else
      {
        this.sinkBuffer.writeByte(paramInt | 0x7F);
        this.sinkBuffer.writeLong(l);
      }
      if (this.isClient)
      {
        paramByteString = this.random;
        Object localObject = this.maskKey;
        Intrinsics.checkNotNull(localObject);
        paramByteString.nextBytes((byte[])localObject);
        this.sinkBuffer.write(this.maskKey);
        if (l > 0L)
        {
          paramByteString = this.messageBuffer;
          localObject = this.maskCursor;
          Intrinsics.checkNotNull(localObject);
          paramByteString.readAndWriteUnsafe((Buffer.UnsafeCursor)localObject);
          this.maskCursor.seek(0L);
          WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
          this.maskCursor.close();
        }
      }
      this.sinkBuffer.write(this.messageBuffer, l);
      this.sink.emit();
      return;
    }
    throw ((Throwable)new IOException("closed"));
  }
  
  public final void writePing(ByteString paramByteString)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteString, "payload");
    writeControlFrame(9, paramByteString);
  }
  
  public final void writePong(ByteString paramByteString)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteString, "payload");
    writeControlFrame(10, paramByteString);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/ws/WebSocketWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */