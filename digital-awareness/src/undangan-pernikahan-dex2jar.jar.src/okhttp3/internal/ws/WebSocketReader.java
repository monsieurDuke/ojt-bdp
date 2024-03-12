package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.Buffer.UnsafeCursor;
import okio.BufferedSource;
import okio.ByteString;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000P\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\004\n\002\020\002\n\002\b\b\030\0002\0020\001:\001&B-\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\003\022\006\020\t\032\0020\003¢\006\002\020\nJ\b\020\036\032\0020\037H\026J\006\020 \032\0020\037J\b\020!\032\0020\037H\002J\b\020\"\032\0020\037H\002J\b\020#\032\0020\037H\002J\b\020$\032\0020\037H\002J\b\020%\032\0020\037H\002R\016\020\013\032\0020\003X\016¢\006\002\n\000R\016\020\f\032\0020\rX\004¢\006\002\n\000R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\016\032\0020\017X\016¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\020\032\0020\003X\016¢\006\002\n\000R\016\020\021\032\0020\003X\016¢\006\002\n\000R\020\020\022\032\004\030\0010\023X\004¢\006\002\n\000R\020\020\024\032\004\030\0010\025X\004¢\006\002\n\000R\016\020\026\032\0020\rX\004¢\006\002\n\000R\020\020\027\032\004\030\0010\030X\016¢\006\002\n\000R\016\020\t\032\0020\003X\004¢\006\002\n\000R\016\020\031\032\0020\032X\016¢\006\002\n\000R\016\020\b\032\0020\003X\004¢\006\002\n\000R\016\020\033\032\0020\003X\016¢\006\002\n\000R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\034\020\035¨\006'"}, d2={"Lokhttp3/internal/ws/WebSocketReader;", "Ljava/io/Closeable;", "isClient", "", "source", "Lokio/BufferedSource;", "frameCallback", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "perMessageDeflate", "noContextTakeover", "(ZLokio/BufferedSource;Lokhttp3/internal/ws/WebSocketReader$FrameCallback;ZZ)V", "closed", "controlFrameBuffer", "Lokio/Buffer;", "frameLength", "", "isControlFrame", "isFinalFrame", "maskCursor", "Lokio/Buffer$UnsafeCursor;", "maskKey", "", "messageFrameBuffer", "messageInflater", "Lokhttp3/internal/ws/MessageInflater;", "opcode", "", "readingCompressedMessage", "getSource", "()Lokio/BufferedSource;", "close", "", "processNextFrame", "readControlFrame", "readHeader", "readMessage", "readMessageFrame", "readUntilNonControlFrame", "FrameCallback", "okhttp"}, k=1, mv={1, 4, 0})
public final class WebSocketReader
  implements Closeable
{
  private boolean closed;
  private final Buffer controlFrameBuffer;
  private final FrameCallback frameCallback;
  private long frameLength;
  private final boolean isClient;
  private boolean isControlFrame;
  private boolean isFinalFrame;
  private final Buffer.UnsafeCursor maskCursor;
  private final byte[] maskKey;
  private final Buffer messageFrameBuffer;
  private MessageInflater messageInflater;
  private final boolean noContextTakeover;
  private int opcode;
  private final boolean perMessageDeflate;
  private boolean readingCompressedMessage;
  private final BufferedSource source;
  
  public WebSocketReader(boolean paramBoolean1, BufferedSource paramBufferedSource, FrameCallback paramFrameCallback, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.isClient = paramBoolean1;
    this.source = paramBufferedSource;
    this.frameCallback = paramFrameCallback;
    this.perMessageDeflate = paramBoolean2;
    this.noContextTakeover = paramBoolean3;
    this.controlFrameBuffer = new Buffer();
    this.messageFrameBuffer = new Buffer();
    paramFrameCallback = null;
    if (paramBoolean1) {
      paramBufferedSource = null;
    } else {
      paramBufferedSource = new byte[4];
    }
    this.maskKey = paramBufferedSource;
    if (paramBoolean1) {
      paramBufferedSource = paramFrameCallback;
    } else {
      paramBufferedSource = new Buffer.UnsafeCursor();
    }
    this.maskCursor = paramBufferedSource;
  }
  
  private final void readControlFrame()
    throws IOException
  {
    long l = this.frameLength;
    Object localObject1;
    Object localObject2;
    if (l > 0L)
    {
      this.source.readFully(this.controlFrameBuffer, l);
      if (!this.isClient)
      {
        localObject1 = this.controlFrameBuffer;
        localObject2 = this.maskCursor;
        Intrinsics.checkNotNull(localObject2);
        ((Buffer)localObject1).readAndWriteUnsafe((Buffer.UnsafeCursor)localObject2);
        this.maskCursor.seek(0L);
        localObject1 = WebSocketProtocol.INSTANCE;
        Buffer.UnsafeCursor localUnsafeCursor = this.maskCursor;
        localObject2 = this.maskKey;
        Intrinsics.checkNotNull(localObject2);
        ((WebSocketProtocol)localObject1).toggleMask(localUnsafeCursor, (byte[])localObject2);
        this.maskCursor.close();
      }
    }
    switch (this.opcode)
    {
    default: 
      localObject2 = new StringBuilder().append("Unknown control opcode: ");
      localObject1 = Util.toHexString(this.opcode);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      throw ((Throwable)new ProtocolException((String)localObject1));
    case 10: 
      this.frameCallback.onReadPong(this.controlFrameBuffer.readByteString());
      break;
    case 9: 
      this.frameCallback.onReadPing(this.controlFrameBuffer.readByteString());
      break;
    case 8: 
      int i = 1005;
      localObject1 = "";
      l = this.controlFrameBuffer.size();
      if (l == 1L) {
        break label326;
      }
      if (l != 0L)
      {
        i = this.controlFrameBuffer.readShort();
        localObject1 = this.controlFrameBuffer.readUtf8();
        localObject2 = WebSocketProtocol.INSTANCE.closeCodeExceptionMessage(i);
        if (localObject2 != null) {
          throw ((Throwable)new ProtocolException((String)localObject2));
        }
      }
      this.frameCallback.onReadClose(i, (String)localObject1);
      this.closed = true;
    }
    return;
    label326:
    throw ((Throwable)new ProtocolException("Malformed close payload length of 1."));
  }
  
  private final void readHeader()
    throws IOException, ProtocolException
  {
    if (!this.closed)
    {
      long l = this.source.timeout().timeoutNanos();
      this.source.timeout().clearTimeout();
      try
      {
        int j = Util.and(this.source.readByte(), 255);
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
        int k = j & 0xF;
        this.opcode = k;
        boolean bool3 = true;
        boolean bool1;
        if ((j & 0x80) != 0) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.isFinalFrame = bool1;
        boolean bool2;
        if ((j & 0x8) != 0) {
          bool2 = true;
        } else {
          bool2 = false;
        }
        this.isControlFrame = bool2;
        if ((bool2) && (!bool1)) {
          throw ((Throwable)new ProtocolException("Control frames must be final."));
        }
        int i;
        if ((j & 0x40) != 0) {
          i = 1;
        } else {
          i = 0;
        }
        switch (k)
        {
        default: 
          if (i != 0) {
            break label585;
          }
          break;
        case 1: 
        case 2: 
          if (i != 0)
          {
            if (this.perMessageDeflate) {
              bool1 = true;
            } else {
              throw ((Throwable)new ProtocolException("Unexpected rsv1 flag"));
            }
          }
          else {
            bool1 = false;
          }
          this.readingCompressedMessage = bool1;
        }
        if ((j & 0x20) != 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0)
        {
          if ((j & 0x10) != 0) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0)
          {
            i = Util.and(this.source.readByte(), 255);
            if ((i & 0x80) != 0) {
              bool1 = bool3;
            } else {
              bool1 = false;
            }
            Object localObject1;
            if (bool1 == this.isClient)
            {
              if (this.isClient) {
                localObject1 = "Server-sent frames must not be masked.";
              } else {
                localObject1 = "Client-sent frames must be masked.";
              }
              throw ((Throwable)new ProtocolException((String)localObject1));
            }
            l = i & 0x7F;
            this.frameLength = l;
            Object localObject3;
            if (l == 126)
            {
              this.frameLength = Util.and(this.source.readShort(), 65535);
            }
            else if (l == 127)
            {
              l = this.source.readLong();
              this.frameLength = l;
              if (l < 0L)
              {
                localObject1 = new StringBuilder().append("Frame length 0x");
                localObject3 = Util.toHexString(this.frameLength);
                Log5ECF72.a(localObject3);
                LogE84000.a(localObject3);
                Log229316.a(localObject3);
                throw ((Throwable)new ProtocolException((String)localObject3 + " > 0x7FFFFFFFFFFFFFFF"));
              }
            }
            if ((this.isControlFrame) && (this.frameLength > 125L)) {
              throw ((Throwable)new ProtocolException("Control frame must be less than 125B."));
            }
            if (bool1)
            {
              localObject3 = this.source;
              localObject1 = this.maskKey;
              Intrinsics.checkNotNull(localObject1);
              ((BufferedSource)localObject3).readFully((byte[])localObject1);
            }
            return;
          }
          throw ((Throwable)new ProtocolException("Unexpected rsv3 flag"));
        }
        throw ((Throwable)new ProtocolException("Unexpected rsv2 flag"));
        label585:
        throw ((Throwable)new ProtocolException("Unexpected rsv1 flag"));
      }
      finally
      {
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
      }
    }
    throw ((Throwable)new IOException("closed"));
  }
  
  private final void readMessage()
    throws IOException
  {
    while (!this.closed)
    {
      long l = this.frameLength;
      Object localObject1;
      Object localObject2;
      if (l > 0L)
      {
        this.source.readFully(this.messageFrameBuffer, l);
        if (!this.isClient)
        {
          localObject1 = this.messageFrameBuffer;
          localObject2 = this.maskCursor;
          Intrinsics.checkNotNull(localObject2);
          ((Buffer)localObject1).readAndWriteUnsafe((Buffer.UnsafeCursor)localObject2);
          this.maskCursor.seek(this.messageFrameBuffer.size() - this.frameLength);
          WebSocketProtocol localWebSocketProtocol = WebSocketProtocol.INSTANCE;
          localObject2 = this.maskCursor;
          localObject1 = this.maskKey;
          Intrinsics.checkNotNull(localObject1);
          localWebSocketProtocol.toggleMask((Buffer.UnsafeCursor)localObject2, (byte[])localObject1);
          this.maskCursor.close();
        }
      }
      if (this.isFinalFrame) {
        return;
      }
      readUntilNonControlFrame();
      if (this.opcode != 0)
      {
        localObject1 = new StringBuilder().append("Expected continuation opcode. Got: ");
        localObject2 = Util.toHexString(this.opcode);
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        throw ((Throwable)new ProtocolException((String)localObject2));
      }
    }
    throw ((Throwable)new IOException("closed"));
  }
  
  private final void readMessageFrame()
    throws IOException
  {
    int i = this.opcode;
    Object localObject;
    if ((i != 1) && (i != 2))
    {
      localObject = new StringBuilder().append("Unknown opcode: ");
      String str = Util.toHexString(i);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      throw ((Throwable)new ProtocolException(str));
    }
    readMessage();
    if (this.readingCompressedMessage)
    {
      localObject = this.messageInflater;
      if (localObject == null)
      {
        localObject = new MessageInflater(this.noContextTakeover);
        this.messageInflater = ((MessageInflater)localObject);
      }
      ((MessageInflater)localObject).inflate(this.messageFrameBuffer);
    }
    if (i == 1) {
      this.frameCallback.onReadMessage(this.messageFrameBuffer.readUtf8());
    } else {
      this.frameCallback.onReadMessage(this.messageFrameBuffer.readByteString());
    }
  }
  
  private final void readUntilNonControlFrame()
    throws IOException
  {
    while (!this.closed)
    {
      readHeader();
      if (!this.isControlFrame) {
        break;
      }
      readControlFrame();
    }
  }
  
  public void close()
    throws IOException
  {
    MessageInflater localMessageInflater = this.messageInflater;
    if (localMessageInflater != null) {
      localMessageInflater.close();
    }
  }
  
  public final BufferedSource getSource()
  {
    return this.source;
  }
  
  public final void processNextFrame()
    throws IOException
  {
    readHeader();
    if (this.isControlFrame) {
      readControlFrame();
    } else {
      readMessageFrame();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\004\bf\030\0002\0020\001J\030\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\007H&J\020\020\b\032\0020\0032\006\020\t\032\0020\007H&J\020\020\b\032\0020\0032\006\020\n\032\0020\013H&J\020\020\f\032\0020\0032\006\020\r\032\0020\013H&J\020\020\016\032\0020\0032\006\020\r\032\0020\013H&¨\006\017"}, d2={"Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "", "onReadClose", "", "code", "", "reason", "", "onReadMessage", "text", "bytes", "Lokio/ByteString;", "onReadPing", "payload", "onReadPong", "okhttp"}, k=1, mv={1, 4, 0})
  public static abstract interface FrameCallback
  {
    public abstract void onReadClose(int paramInt, String paramString);
    
    public abstract void onReadMessage(String paramString)
      throws IOException;
    
    public abstract void onReadMessage(ByteString paramByteString)
      throws IOException;
    
    public abstract void onReadPing(ByteString paramByteString);
    
    public abstract void onReadPong(ByteString paramByteString);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/ws/WebSocketReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */