package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000\001\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\006\n\002\020\t\n\002\b\t\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\013\n\002\020\002\n\002\b\f\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\b\n\002\020 \n\002\030\002\n\002\b\006\030\000 _2\0020\001:\004_`abB1\b\000\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\007\022\b\020\t\032\004\030\0010\n¢\006\002\020\013J\016\020@\032\0020A2\006\020B\032\0020#J\r\020C\032\0020AH\000¢\006\002\bDJ\r\020E\032\0020AH\000¢\006\002\bFJ\030\020G\032\0020A2\006\020H\032\0020\0172\b\020\024\032\004\030\0010\025J\032\020I\032\0020\0072\006\020\016\032\0020\0172\b\020\024\032\004\030\0010\025H\002J\016\020J\032\0020A2\006\020\016\032\0020\017J\016\020K\032\0020A2\006\020L\032\0020\nJ\006\020M\032\0020NJ\006\020O\032\0020PJ\006\020,\032\0020QJ\026\020R\032\0020A2\006\0204\032\0020S2\006\020T\032\0020\003J\026\020U\032\0020A2\006\020\t\032\0020\n2\006\020\b\032\0020\007J\016\020V\032\0020A2\006\020\016\032\0020\017J\006\020W\032\0020\nJ\006\020L\032\0020\nJ\r\020X\032\0020AH\000¢\006\002\bYJ$\020Z\032\0020A2\f\020[\032\b\022\004\022\0020]0\\2\006\020\006\032\0020\0072\006\020^\032\0020\007J\006\020>\032\0020QR\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\f\020\rR\036\020\016\032\004\030\0010\0178@X\016¢\006\016\n\000\032\004\b\020\020\021\"\004\b\022\020\023R\034\020\024\032\004\030\0010\025X\016¢\006\016\n\000\032\004\b\026\020\027\"\004\b\030\020\031R\016\020\032\032\0020\007X\016¢\006\002\n\000R\024\020\033\032\b\022\004\022\0020\n0\034X\004¢\006\002\n\000R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\035\020\036R\021\020\037\032\0020\0078F¢\006\006\032\004\b\037\020 R\021\020!\032\0020\0078F¢\006\006\032\004\b!\020 R$\020$\032\0020#2\006\020\"\032\0020#@@X\016¢\006\016\n\000\032\004\b%\020&\"\004\b'\020(R$\020)\032\0020#2\006\020\"\032\0020#@@X\016¢\006\016\n\000\032\004\b*\020&\"\004\b+\020(R\030\020,\032\0060-R\0020\000X\004¢\006\b\n\000\032\004\b.\020/R\030\0200\032\00601R\0020\000X\004¢\006\b\n\000\032\004\b2\0203R\030\0204\032\00605R\0020\000X\004¢\006\b\n\000\032\004\b6\0207R$\0208\032\0020#2\006\020\"\032\0020#@@X\016¢\006\016\n\000\032\004\b9\020&\"\004\b:\020(R$\020;\032\0020#2\006\020\"\032\0020#@@X\016¢\006\016\n\000\032\004\b<\020&\"\004\b=\020(R\030\020>\032\0060-R\0020\000X\004¢\006\b\n\000\032\004\b?\020/¨\006c"}, d2={"Lokhttp3/internal/http2/Http2Stream;", "", "id", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "outFinished", "", "inFinished", "headers", "Lokhttp3/Headers;", "(ILokhttp3/internal/http2/Http2Connection;ZZLokhttp3/Headers;)V", "getConnection", "()Lokhttp3/internal/http2/Http2Connection;", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "getErrorCode$okhttp", "()Lokhttp3/internal/http2/ErrorCode;", "setErrorCode$okhttp", "(Lokhttp3/internal/http2/ErrorCode;)V", "errorException", "Ljava/io/IOException;", "getErrorException$okhttp", "()Ljava/io/IOException;", "setErrorException$okhttp", "(Ljava/io/IOException;)V", "hasResponseHeaders", "headersQueue", "Ljava/util/ArrayDeque;", "getId", "()I", "isLocallyInitiated", "()Z", "isOpen", "<set-?>", "", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "setReadBytesAcknowledged$okhttp", "(J)V", "readBytesTotal", "getReadBytesTotal", "setReadBytesTotal$okhttp", "readTimeout", "Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "getReadTimeout$okhttp", "()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "sink", "Lokhttp3/internal/http2/Http2Stream$FramingSink;", "getSink$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSink;", "source", "Lokhttp3/internal/http2/Http2Stream$FramingSource;", "getSource$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSource;", "writeBytesMaximum", "getWriteBytesMaximum", "setWriteBytesMaximum$okhttp", "writeBytesTotal", "getWriteBytesTotal", "setWriteBytesTotal$okhttp", "writeTimeout", "getWriteTimeout$okhttp", "addBytesToWriteWindow", "", "delta", "cancelStreamIfNecessary", "cancelStreamIfNecessary$okhttp", "checkOutNotClosed", "checkOutNotClosed$okhttp", "close", "rstStatusCode", "closeInternal", "closeLater", "enqueueTrailers", "trailers", "getSink", "Lokio/Sink;", "getSource", "Lokio/Source;", "Lokio/Timeout;", "receiveData", "Lokio/BufferedSource;", "length", "receiveHeaders", "receiveRstStream", "takeHeaders", "waitForIo", "waitForIo$okhttp", "writeHeaders", "responseHeaders", "", "Lokhttp3/internal/http2/Header;", "flushHeaders", "Companion", "FramingSink", "FramingSource", "StreamTimeout", "okhttp"}, k=1, mv={1, 4, 0})
public final class Http2Stream
{
  public static final Companion Companion = new Companion(null);
  public static final long EMIT_BUFFER_SIZE = 16384L;
  private final Http2Connection connection;
  private ErrorCode errorCode;
  private IOException errorException;
  private boolean hasResponseHeaders;
  private final ArrayDeque<Headers> headersQueue;
  private final int id;
  private long readBytesAcknowledged;
  private long readBytesTotal;
  private final StreamTimeout readTimeout;
  private final FramingSink sink;
  private final FramingSource source;
  private long writeBytesMaximum;
  private long writeBytesTotal;
  private final StreamTimeout writeTimeout;
  
  public Http2Stream(int paramInt, Http2Connection paramHttp2Connection, boolean paramBoolean1, boolean paramBoolean2, Headers paramHeaders)
  {
    this.id = paramInt;
    this.connection = paramHttp2Connection;
    this.writeBytesMaximum = paramHttp2Connection.getPeerSettings().getInitialWindowSize();
    ArrayDeque localArrayDeque = new ArrayDeque();
    this.headersQueue = localArrayDeque;
    long l = paramHttp2Connection.getOkHttpSettings().getInitialWindowSize();
    this.source = new FramingSource(l, paramBoolean2);
    this.sink = new FramingSink(paramBoolean1);
    this.readTimeout = new StreamTimeout();
    this.writeTimeout = new StreamTimeout();
    if (paramHeaders != null)
    {
      if ((isLocallyInitiated() ^ true)) {
        ((Collection)localArrayDeque).add(paramHeaders);
      } else {
        throw ((Throwable)new IllegalStateException("locally-initiated streams shouldn't have headers yet".toString()));
      }
    }
    else {
      if (!isLocallyInitiated()) {
        break label164;
      }
    }
    return;
    label164:
    throw ((Throwable)new IllegalStateException("remotely-initiated streams should have headers".toString()));
  }
  
  private final boolean closeInternal(ErrorCode paramErrorCode, IOException paramIOException)
  {
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      paramIOException = new StringBuilder().append("Thread ");
      paramErrorCode = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramErrorCode, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramErrorCode.getName() + " MUST NOT hold lock on " + this));
    }
    try
    {
      ErrorCode localErrorCode = this.errorCode;
      if (localErrorCode != null) {
        return false;
      }
      if (this.source.getFinished$okhttp())
      {
        boolean bool = this.sink.getFinished();
        if (bool) {
          return false;
        }
      }
      this.errorCode = paramErrorCode;
      this.errorException = paramIOException;
      ((Object)this).notifyAll();
      paramErrorCode = Unit.INSTANCE;
      this.connection.removeStream$okhttp(this.id);
      return true;
    }
    finally {}
  }
  
  public final void addBytesToWriteWindow(long paramLong)
  {
    this.writeBytesMaximum += paramLong;
    if (paramLong > 0L) {
      ((Object)this).notifyAll();
    }
  }
  
  public final void cancelStreamIfNecessary$okhttp()
    throws IOException
  {
    Object localObject1;
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      localObject1 = new StringBuilder().append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(localThread.getName() + " MUST NOT hold lock on " + this));
    }
    try
    {
      int i;
      if ((!this.source.getFinished$okhttp()) && (this.source.getClosed$okhttp()) && ((this.sink.getFinished()) || (this.sink.getClosed()))) {
        i = 1;
      } else {
        i = 0;
      }
      boolean bool = isOpen();
      localObject1 = Unit.INSTANCE;
      if (i != 0) {
        close(ErrorCode.CANCEL, null);
      } else if (!bool) {
        this.connection.removeStream$okhttp(this.id);
      }
      return;
    }
    finally {}
  }
  
  public final void checkOutNotClosed$okhttp()
    throws IOException
  {
    if (!this.sink.getClosed())
    {
      if (!this.sink.getFinished())
      {
        if (this.errorCode != null)
        {
          Object localObject = this.errorException;
          if (localObject == null)
          {
            localObject = this.errorCode;
            Intrinsics.checkNotNull(localObject);
            localObject = new StreamResetException((ErrorCode)localObject);
          }
          throw ((Throwable)localObject);
        }
        return;
      }
      throw ((Throwable)new IOException("stream finished"));
    }
    throw ((Throwable)new IOException("stream closed"));
  }
  
  public final void close(ErrorCode paramErrorCode, IOException paramIOException)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramErrorCode, "rstStatusCode");
    if (!closeInternal(paramErrorCode, paramIOException)) {
      return;
    }
    this.connection.writeSynReset$okhttp(this.id, paramErrorCode);
  }
  
  public final void closeLater(ErrorCode paramErrorCode)
  {
    Intrinsics.checkNotNullParameter(paramErrorCode, "errorCode");
    if (!closeInternal(paramErrorCode, null)) {
      return;
    }
    this.connection.writeSynResetLater$okhttp(this.id, paramErrorCode);
  }
  
  public final void enqueueTrailers(Headers paramHeaders)
  {
    Intrinsics.checkNotNullParameter(paramHeaders, "trailers");
    try
    {
      boolean bool = this.sink.getFinished();
      int i = 1;
      if ((bool ^ true))
      {
        if (paramHeaders.size() == 0) {
          i = 0;
        }
        if (i != 0)
        {
          this.sink.setTrailers(paramHeaders);
          paramHeaders = Unit.INSTANCE;
          return;
        }
        paramHeaders = new java/lang/IllegalArgumentException;
        paramHeaders.<init>("trailers.size() == 0".toString());
        throw ((Throwable)paramHeaders);
      }
      paramHeaders = new java/lang/IllegalStateException;
      paramHeaders.<init>("already finished".toString());
      throw ((Throwable)paramHeaders);
    }
    finally {}
  }
  
  public final Http2Connection getConnection()
  {
    return this.connection;
  }
  
  public final ErrorCode getErrorCode$okhttp()
  {
    try
    {
      ErrorCode localErrorCode = this.errorCode;
      return localErrorCode;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final IOException getErrorException$okhttp()
  {
    return this.errorException;
  }
  
  public final int getId()
  {
    return this.id;
  }
  
  public final long getReadBytesAcknowledged()
  {
    return this.readBytesAcknowledged;
  }
  
  public final long getReadBytesTotal()
  {
    return this.readBytesTotal;
  }
  
  public final StreamTimeout getReadTimeout$okhttp()
  {
    return this.readTimeout;
  }
  
  public final Sink getSink()
  {
    try
    {
      int i;
      if ((!this.hasResponseHeaders) && (!isLocallyInitiated())) {
        i = 0;
      } else {
        i = 1;
      }
      if (i != 0)
      {
        localObject1 = Unit.INSTANCE;
        return (Sink)this.sink;
      }
      Object localObject1 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject1).<init>("reply before requesting the sink".toString());
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  public final FramingSink getSink$okhttp()
  {
    return this.sink;
  }
  
  public final Source getSource()
  {
    return (Source)this.source;
  }
  
  public final FramingSource getSource$okhttp()
  {
    return this.source;
  }
  
  public final long getWriteBytesMaximum()
  {
    return this.writeBytesMaximum;
  }
  
  public final long getWriteBytesTotal()
  {
    return this.writeBytesTotal;
  }
  
  public final StreamTimeout getWriteTimeout$okhttp()
  {
    return this.writeTimeout;
  }
  
  public final boolean isLocallyInitiated()
  {
    int i = this.id;
    boolean bool2 = true;
    boolean bool1;
    if ((i & 0x1) == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (this.connection.getClient$okhttp() == bool1) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    return bool1;
  }
  
  public final boolean isOpen()
  {
    try
    {
      ErrorCode localErrorCode = this.errorCode;
      if (localErrorCode != null) {
        return false;
      }
      if (((this.source.getFinished$okhttp()) || (this.source.getClosed$okhttp())) && ((this.sink.getFinished()) || (this.sink.getClosed())))
      {
        boolean bool = this.hasResponseHeaders;
        if (bool) {
          return false;
        }
      }
      return true;
    }
    finally {}
  }
  
  public final Timeout readTimeout()
  {
    return (Timeout)this.readTimeout;
  }
  
  public final void receiveData(BufferedSource paramBufferedSource, int paramInt)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBufferedSource, "source");
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      paramBufferedSource = new StringBuilder().append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(localThread.getName() + " MUST NOT hold lock on " + this));
    }
    this.source.receive$okhttp(paramBufferedSource, paramInt);
  }
  
  public final void receiveHeaders(Headers paramHeaders, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramHeaders, "headers");
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      StringBuilder localStringBuilder = new StringBuilder().append("Thread ");
      paramHeaders = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramHeaders, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramHeaders.getName() + " MUST NOT hold lock on " + this));
    }
    try
    {
      if ((this.hasResponseHeaders) && (paramBoolean))
      {
        this.source.setTrailers(paramHeaders);
      }
      else
      {
        this.hasResponseHeaders = true;
        ((Collection)this.headersQueue).add(paramHeaders);
      }
      if (paramBoolean) {
        this.source.setFinished$okhttp(true);
      }
      paramBoolean = isOpen();
      ((Object)this).notifyAll();
      paramHeaders = Unit.INSTANCE;
      if (!paramBoolean) {
        this.connection.removeStream$okhttp(this.id);
      }
      return;
    }
    finally {}
  }
  
  public final void receiveRstStream(ErrorCode paramErrorCode)
  {
    try
    {
      Intrinsics.checkNotNullParameter(paramErrorCode, "errorCode");
      if (this.errorCode == null)
      {
        this.errorCode = paramErrorCode;
        ((Object)this).notifyAll();
      }
      return;
    }
    finally
    {
      paramErrorCode = finally;
      throw paramErrorCode;
    }
  }
  
  public final void setErrorCode$okhttp(ErrorCode paramErrorCode)
  {
    this.errorCode = paramErrorCode;
  }
  
  public final void setErrorException$okhttp(IOException paramIOException)
  {
    this.errorException = paramIOException;
  }
  
  public final void setReadBytesAcknowledged$okhttp(long paramLong)
  {
    this.readBytesAcknowledged = paramLong;
  }
  
  public final void setReadBytesTotal$okhttp(long paramLong)
  {
    this.readBytesTotal = paramLong;
  }
  
  public final void setWriteBytesMaximum$okhttp(long paramLong)
  {
    this.writeBytesMaximum = paramLong;
  }
  
  public final void setWriteBytesTotal$okhttp(long paramLong)
  {
    this.writeBytesTotal = paramLong;
  }
  
  /* Error */
  public final Headers takeHeaders()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 184	okhttp3/internal/http2/Http2Stream:readTimeout	Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   6: invokevirtual 373	okhttp3/internal/http2/Http2Stream$StreamTimeout:enter	()V
    //   9: aload_0
    //   10: getfield 166	okhttp3/internal/http2/Http2Stream:headersQueue	Ljava/util/ArrayDeque;
    //   13: invokevirtual 376	java/util/ArrayDeque:isEmpty	()Z
    //   16: istore_1
    //   17: iload_1
    //   18: ifeq +21 -> 39
    //   21: aload_0
    //   22: getfield 254	okhttp3/internal/http2/Http2Stream:errorCode	Lokhttp3/internal/http2/ErrorCode;
    //   25: ifnonnull +14 -> 39
    //   28: aload_0
    //   29: invokevirtual 378	okhttp3/internal/http2/Http2Stream:waitForIo$okhttp	()V
    //   32: goto -23 -> 9
    //   35: astore_2
    //   36: goto +92 -> 128
    //   39: aload_0
    //   40: getfield 184	okhttp3/internal/http2/Http2Stream:readTimeout	Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   43: invokevirtual 381	okhttp3/internal/http2/Http2Stream$StreamTimeout:exitAndThrowIfTimedOut	()V
    //   46: aload_0
    //   47: getfield 166	okhttp3/internal/http2/Http2Stream:headersQueue	Ljava/util/ArrayDeque;
    //   50: checkcast 190	java/util/Collection
    //   53: invokeinterface 382 1 0
    //   58: iconst_1
    //   59: ixor
    //   60: ifeq +27 -> 87
    //   63: aload_0
    //   64: getfield 166	okhttp3/internal/http2/Http2Stream:headersQueue	Ljava/util/ArrayDeque;
    //   67: invokevirtual 386	java/util/ArrayDeque:removeFirst	()Ljava/lang/Object;
    //   70: astore_2
    //   71: aload_2
    //   72: ldc_w 388
    //   75: invokestatic 238	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
    //   78: aload_2
    //   79: checkcast 321	okhttp3/Headers
    //   82: astore_2
    //   83: aload_0
    //   84: monitorexit
    //   85: aload_2
    //   86: areturn
    //   87: aload_0
    //   88: getfield 262	okhttp3/internal/http2/Http2Stream:errorException	Ljava/io/IOException;
    //   91: astore_2
    //   92: aload_2
    //   93: ifnull +11 -> 104
    //   96: aload_2
    //   97: checkcast 207	java/lang/Throwable
    //   100: astore_2
    //   101: goto +24 -> 125
    //   104: aload_0
    //   105: getfield 254	okhttp3/internal/http2/Http2Stream:errorCode	Lokhttp3/internal/http2/ErrorCode;
    //   108: astore_2
    //   109: aload_2
    //   110: invokestatic 297	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
    //   113: new 299	okhttp3/internal/http2/StreamResetException
    //   116: dup
    //   117: aload_2
    //   118: invokespecial 301	okhttp3/internal/http2/StreamResetException:<init>	(Lokhttp3/internal/http2/ErrorCode;)V
    //   121: astore_2
    //   122: goto -26 -> 96
    //   125: aload_2
    //   126: athrow
    //   127: astore_2
    //   128: aload_0
    //   129: getfield 184	okhttp3/internal/http2/Http2Stream:readTimeout	Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   132: invokevirtual 381	okhttp3/internal/http2/Http2Stream$StreamTimeout:exitAndThrowIfTimedOut	()V
    //   135: aload_2
    //   136: athrow
    //   137: astore_2
    //   138: aload_0
    //   139: monitorexit
    //   140: aload_2
    //   141: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	142	0	this	Http2Stream
    //   16	2	1	bool	boolean
    //   35	1	2	localObject1	Object
    //   70	56	2	localObject2	Object
    //   127	9	2	localObject3	Object
    //   137	4	2	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   21	32	35	finally
    //   9	17	127	finally
    //   2	9	137	finally
    //   39	46	137	finally
    //   46	83	137	finally
    //   87	92	137	finally
    //   96	101	137	finally
    //   104	122	137	finally
    //   125	127	137	finally
    //   128	137	137	finally
  }
  
  public final Headers trailers()
    throws IOException
  {
    try
    {
      if (this.errorCode != null)
      {
        localObject1 = this.errorException;
        if (localObject1 != null)
        {
          localObject1 = (Throwable)localObject1;
        }
        else
        {
          StreamResetException localStreamResetException = new okhttp3/internal/http2/StreamResetException;
          localObject1 = this.errorCode;
          Intrinsics.checkNotNull(localObject1);
          localStreamResetException.<init>((ErrorCode)localObject1);
          localObject1 = (Throwable)localStreamResetException;
        }
        throw ((Throwable)localObject1);
      }
      int i;
      if ((this.source.getFinished$okhttp()) && (this.source.getReceiveBuffer().exhausted()) && (this.source.getReadBuffer().exhausted())) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localObject1 = this.source.getTrailers();
        if (localObject1 == null) {
          localObject1 = Util.EMPTY_HEADERS;
        }
        return (Headers)localObject1;
      }
      Object localObject1 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject1).<init>("too early; can't read the trailers yet".toString());
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  public final void waitForIo$okhttp()
    throws InterruptedIOException
  {
    try
    {
      ((Object)this).wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      Thread.currentThread().interrupt();
      throw ((Throwable)new InterruptedIOException());
    }
  }
  
  public final void writeHeaders(List<Header> paramList, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramList, "responseHeaders");
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      ??? = new StringBuilder().append("Thread ");
      paramList = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramList, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramList.getName() + " MUST NOT hold lock on " + this));
    }
    boolean bool1 = paramBoolean2;
    boolean bool2 = true;
    try
    {
      this.hasResponseHeaders = true;
      if (paramBoolean1) {
        this.sink.setFinished(true);
      }
      ??? = Unit.INSTANCE;
      paramBoolean2 = bool1;
      if (!bool1) {
        synchronized (this.connection)
        {
          if (this.connection.getWriteBytesTotal() >= this.connection.getWriteBytesMaximum()) {
            paramBoolean2 = bool2;
          } else {
            paramBoolean2 = false;
          }
          Unit localUnit = Unit.INSTANCE;
        }
      }
      this.connection.writeHeaders$okhttp(this.id, paramBoolean1, paramList);
      if (paramBoolean2) {
        this.connection.flush();
      }
      return;
    }
    finally {}
  }
  
  public final Timeout writeTimeout()
  {
    return (Timeout)this.writeTimeout;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lokhttp3/internal/http2/Http2Stream$Companion;", "", "()V", "EMIT_BUFFER_SIZE", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion {}
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\t\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020\t\n\000\b\004\030\0002\0020\001B\017\022\b\b\002\020\002\032\0020\003¢\006\002\020\004J\b\020\024\032\0020\025H\026J\020\020\026\032\0020\0252\006\020\027\032\0020\003H\002J\b\020\030\032\0020\025H\026J\b\020\031\032\0020\032H\026J\030\020\033\032\0020\0252\006\020\034\032\0020\r2\006\020\035\032\0020\036H\026R\032\020\005\032\0020\003X\016¢\006\016\n\000\032\004\b\006\020\007\"\004\b\b\020\tR\032\020\002\032\0020\003X\016¢\006\016\n\000\032\004\b\n\020\007\"\004\b\013\020\tR\016\020\f\032\0020\rX\004¢\006\002\n\000R\034\020\016\032\004\030\0010\017X\016¢\006\016\n\000\032\004\b\020\020\021\"\004\b\022\020\023¨\006\037"}, d2={"Lokhttp3/internal/http2/Http2Stream$FramingSink;", "Lokio/Sink;", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;Z)V", "closed", "getClosed", "()Z", "setClosed", "(Z)V", "getFinished", "setFinished", "sendBuffer", "Lokio/Buffer;", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "emitFrame", "outFinishedOnLastFrame", "flush", "timeout", "Lokio/Timeout;", "write", "source", "byteCount", "", "okhttp"}, k=1, mv={1, 4, 0})
  public final class FramingSink
    implements Sink
  {
    private boolean closed;
    private boolean finished;
    private final Buffer sendBuffer;
    private Headers trailers;
    
    public FramingSink()
    {
      boolean bool;
      this.finished = bool;
      this.sendBuffer = new Buffer();
    }
    
    private final void emitFrame(boolean paramBoolean)
      throws IOException
    {
      long l;
      Object localObject1;
      synchronized (Http2Stream.this)
      {
        Http2Stream.this.getWriteTimeout$okhttp().enter();
      }
    }
    
    public void close()
      throws IOException
    {
      Object localObject2 = Http2Stream.this;
      if ((Util.assertionsEnabled) && (Thread.holdsLock(localObject2)))
      {
        ??? = new StringBuilder().append("Thread ");
        Thread localThread = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
        throw ((Throwable)new AssertionError(localThread.getName() + " MUST NOT hold lock on " + localObject2));
      }
      synchronized (Http2Stream.this)
      {
        boolean bool = this.closed;
        if (bool) {
          return;
        }
        if (Http2Stream.this.getErrorCode$okhttp() == null) {
          bool = true;
        } else {
          bool = false;
        }
        localObject2 = Unit.INSTANCE;
        if (!Http2Stream.this.getSink$okhttp().finished)
        {
          int i;
          if (this.sendBuffer.size() > 0L) {
            i = 1;
          } else {
            i = 0;
          }
          int j;
          if (this.trailers != null) {
            j = 1;
          } else {
            j = 0;
          }
          if (j != 0)
          {
            while (this.sendBuffer.size() > 0L) {
              emitFrame(false);
            }
            ??? = Http2Stream.this.getConnection();
            i = Http2Stream.this.getId();
            localObject2 = this.trailers;
            Intrinsics.checkNotNull(localObject2);
            ((Http2Connection)???).writeHeaders$okhttp(i, bool, Util.toHeaderList((Headers)localObject2));
          }
          else
          {
            if (i != 0) {
              while (this.sendBuffer.size() > 0L) {
                emitFrame(true);
              }
            }
            if (bool) {
              Http2Stream.this.getConnection().writeData(Http2Stream.this.getId(), true, null, 0L);
            }
          }
        }
        synchronized (Http2Stream.this)
        {
          this.closed = true;
          localObject2 = Unit.INSTANCE;
          Http2Stream.this.getConnection().flush();
          Http2Stream.this.cancelStreamIfNecessary$okhttp();
          return;
        }
      }
    }
    
    public void flush()
      throws IOException
    {
      Http2Stream localHttp2Stream = Http2Stream.this;
      Object localObject2;
      if ((Util.assertionsEnabled) && (Thread.holdsLock(localHttp2Stream)))
      {
        localObject2 = new StringBuilder().append("Thread ");
        ??? = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(???, "Thread.currentThread()");
        throw ((Throwable)new AssertionError(((Thread)???).getName() + " MUST NOT hold lock on " + localHttp2Stream));
      }
      synchronized (Http2Stream.this)
      {
        Http2Stream.this.checkOutNotClosed$okhttp();
        localObject2 = Unit.INSTANCE;
        while (this.sendBuffer.size() > 0L)
        {
          emitFrame(false);
          Http2Stream.this.getConnection().flush();
        }
        return;
      }
    }
    
    public final boolean getClosed()
    {
      return this.closed;
    }
    
    public final boolean getFinished()
    {
      return this.finished;
    }
    
    public final Headers getTrailers()
    {
      return this.trailers;
    }
    
    public final void setClosed(boolean paramBoolean)
    {
      this.closed = paramBoolean;
    }
    
    public final void setFinished(boolean paramBoolean)
    {
      this.finished = paramBoolean;
    }
    
    public final void setTrailers(Headers paramHeaders)
    {
      this.trailers = paramHeaders;
    }
    
    public Timeout timeout()
    {
      return (Timeout)Http2Stream.this.getWriteTimeout$okhttp();
    }
    
    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramBuffer, "source");
      Http2Stream localHttp2Stream = Http2Stream.this;
      if ((Util.assertionsEnabled) && (Thread.holdsLock(localHttp2Stream)))
      {
        StringBuilder localStringBuilder = new StringBuilder().append("Thread ");
        paramBuffer = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(paramBuffer, "Thread.currentThread()");
        throw ((Throwable)new AssertionError(paramBuffer.getName() + " MUST NOT hold lock on " + localHttp2Stream));
      }
      this.sendBuffer.write(paramBuffer, paramLong);
      while (this.sendBuffer.size() >= 16384L) {
        emitFrame(false);
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\020\013\n\002\b\t\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\b\004\030\0002\0020\001B\027\b\000\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\b\020\032\032\0020\033H\026J\030\020\034\032\0020\0032\006\020\035\032\0020\0172\006\020\036\032\0020\003H\026J\035\020\037\032\0020\0332\006\020 \032\0020!2\006\020\036\032\0020\003H\000¢\006\002\b\"J\b\020#\032\0020$H\026J\020\020%\032\0020\0332\006\020\034\032\0020\003H\002R\032\020\007\032\0020\005X\016¢\006\016\n\000\032\004\b\b\020\t\"\004\b\n\020\013R\032\020\004\032\0020\005X\016¢\006\016\n\000\032\004\b\f\020\t\"\004\b\r\020\013R\016\020\002\032\0020\003X\004¢\006\002\n\000R\021\020\016\032\0020\017¢\006\b\n\000\032\004\b\020\020\021R\021\020\022\032\0020\017¢\006\b\n\000\032\004\b\023\020\021R\034\020\024\032\004\030\0010\025X\016¢\006\016\n\000\032\004\b\026\020\027\"\004\b\030\020\031¨\006&"}, d2={"Lokhttp3/internal/http2/Http2Stream$FramingSource;", "Lokio/Source;", "maxByteCount", "", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;JZ)V", "closed", "getClosed$okhttp", "()Z", "setClosed$okhttp", "(Z)V", "getFinished$okhttp", "setFinished$okhttp", "readBuffer", "Lokio/Buffer;", "getReadBuffer", "()Lokio/Buffer;", "receiveBuffer", "getReceiveBuffer", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "read", "sink", "byteCount", "receive", "source", "Lokio/BufferedSource;", "receive$okhttp", "timeout", "Lokio/Timeout;", "updateConnectionFlowControl", "okhttp"}, k=1, mv={1, 4, 0})
  public final class FramingSource
    implements Source
  {
    private boolean closed;
    private boolean finished;
    private final long maxByteCount;
    private final Buffer readBuffer;
    private final Buffer receiveBuffer;
    private Headers trailers;
    
    public FramingSource(boolean paramBoolean)
    {
      this.maxByteCount = ???;
      boolean bool1;
      this.finished = bool1;
      this.receiveBuffer = new Buffer();
      this.readBuffer = new Buffer();
    }
    
    private final void updateConnectionFlowControl(long paramLong)
    {
      Http2Stream localHttp2Stream = Http2Stream.this;
      if ((Util.assertionsEnabled) && (Thread.holdsLock(localHttp2Stream)))
      {
        StringBuilder localStringBuilder = new StringBuilder().append("Thread ");
        Thread localThread = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
        throw ((Throwable)new AssertionError(localThread.getName() + " MUST NOT hold lock on " + localHttp2Stream));
      }
      Http2Stream.this.getConnection().updateConnectionFlowControl$okhttp(paramLong);
    }
    
    public void close()
      throws IOException
    {
      synchronized (Http2Stream.this)
      {
        this.closed = true;
        long l = this.readBuffer.size();
        this.readBuffer.clear();
        Object localObject1 = Http2Stream.this;
        if (localObject1 != null)
        {
          ((Object)localObject1).notifyAll();
          localObject1 = Unit.INSTANCE;
          if (l > 0L) {
            updateConnectionFlowControl(l);
          }
          Http2Stream.this.cancelStreamIfNecessary$okhttp();
          return;
        }
        localObject1 = new java/lang/NullPointerException;
        ((NullPointerException)localObject1).<init>("null cannot be cast to non-null type java.lang.Object");
        throw ((Throwable)localObject1);
      }
    }
    
    public final boolean getClosed$okhttp()
    {
      return this.closed;
    }
    
    public final boolean getFinished$okhttp()
    {
      return this.finished;
    }
    
    public final Buffer getReadBuffer()
    {
      return this.readBuffer;
    }
    
    public final Buffer getReceiveBuffer()
    {
      return this.receiveBuffer;
    }
    
    public final Headers getTrailers()
    {
      return this.trailers;
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
      if (i != 0) {
        for (;;)
        {
          int j = 0;
          long l2 = -1L;
          Object localObject1 = (IOException)null;
          synchronized (Http2Stream.this)
          {
            Http2Stream.this.getReadTimeout$okhttp().enter();
            try
            {
              Object localObject2;
              if (Http2Stream.this.getErrorCode$okhttp() != null)
              {
                localObject1 = Http2Stream.this.getErrorException$okhttp();
                if (localObject1 == null) {
                  for (;;)
                  {
                    localObject1 = new okhttp3/internal/http2/StreamResetException;
                    localObject2 = Http2Stream.this.getErrorCode$okhttp();
                    Intrinsics.checkNotNull(localObject2);
                    ((StreamResetException)localObject1).<init>((ErrorCode)localObject2);
                    localObject1 = (IOException)localObject1;
                  }
                }
              }
              if (!this.closed)
              {
                long l1;
                if (this.readBuffer.size() > 0L)
                {
                  localObject2 = this.readBuffer;
                  l2 = ((Buffer)localObject2).read(paramBuffer, Math.min(paramLong, ((Buffer)localObject2).size()));
                  localObject2 = Http2Stream.this;
                  ((Http2Stream)localObject2).setReadBytesTotal$okhttp(((Http2Stream)localObject2).getReadBytesTotal() + l2);
                  long l3 = Http2Stream.this.getReadBytesTotal() - Http2Stream.this.getReadBytesAcknowledged();
                  i = j;
                  l1 = l2;
                  if (localObject1 == null)
                  {
                    i = j;
                    l1 = l2;
                    if (l3 >= Http2Stream.this.getConnection().getOkHttpSettings().getInitialWindowSize() / 2)
                    {
                      Http2Stream.this.getConnection().writeWindowUpdateLater$okhttp(Http2Stream.this.getId(), l3);
                      localObject2 = Http2Stream.this;
                      ((Http2Stream)localObject2).setReadBytesAcknowledged$okhttp(((Http2Stream)localObject2).getReadBytesTotal());
                      i = j;
                      l1 = l2;
                    }
                  }
                }
                else
                {
                  i = j;
                  l1 = l2;
                  if (!this.finished)
                  {
                    i = j;
                    l1 = l2;
                    if (localObject1 == null)
                    {
                      Http2Stream.this.waitForIo$okhttp();
                      i = 1;
                      l1 = l2;
                    }
                  }
                }
                Http2Stream.this.getReadTimeout$okhttp().exitAndThrowIfTimedOut();
                localObject2 = Unit.INSTANCE;
                if (i == 0)
                {
                  if (l1 != -1L)
                  {
                    updateConnectionFlowControl(l1);
                    return l1;
                  }
                  if (localObject1 == null) {
                    return -1L;
                  }
                  Intrinsics.checkNotNull(localObject1);
                  throw ((Throwable)localObject1);
                }
              }
              else
              {
                paramBuffer = new java/io/IOException;
                paramBuffer.<init>("stream closed");
                throw ((Throwable)paramBuffer);
              }
            }
            finally
            {
              Http2Stream.this.getReadTimeout$okhttp().exitAndThrowIfTimedOut();
            }
          }
        }
      }
      throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
    }
    
    public final void receive$okhttp(BufferedSource paramBufferedSource, long paramLong)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramBufferedSource, "source");
      ??? = Http2Stream.this;
      Object localObject;
      if ((Util.assertionsEnabled) && (Thread.holdsLock(???)))
      {
        paramBufferedSource = new StringBuilder().append("Thread ");
        localObject = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(localObject, "Thread.currentThread()");
        throw ((Throwable)new AssertionError(((Thread)localObject).getName() + " MUST NOT hold lock on " + ???));
      }
      if (paramLong > 0L) {
        synchronized (Http2Stream.this)
        {
          boolean bool = this.finished;
          int i;
          if (this.readBuffer.size() + paramLong > this.maxByteCount) {
            i = 1;
          } else {
            i = 0;
          }
          localObject = Unit.INSTANCE;
          if (i != 0)
          {
            paramBufferedSource.skip(paramLong);
            Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
            return;
          }
          if (bool)
          {
            paramBufferedSource.skip(paramLong);
            return;
          }
          long l = paramBufferedSource.read(this.receiveBuffer, paramLong);
          if (l != -1L)
          {
            l = paramLong - l;
            paramLong = 0L;
            synchronized (Http2Stream.this)
            {
              if (this.closed)
              {
                paramLong = this.receiveBuffer.size();
                this.receiveBuffer.clear();
              }
              else
              {
                if (this.readBuffer.size() == 0L) {
                  i = 1;
                } else {
                  i = 0;
                }
                this.readBuffer.writeAll((Source)this.receiveBuffer);
                if (i != 0)
                {
                  localObject = Http2Stream.this;
                  if (localObject != null)
                  {
                    ((Object)localObject).notifyAll();
                  }
                  else
                  {
                    paramBufferedSource = new java/lang/NullPointerException;
                    paramBufferedSource.<init>("null cannot be cast to non-null type java.lang.Object");
                    throw paramBufferedSource;
                  }
                }
              }
              localObject = Unit.INSTANCE;
              if (paramLong > 0L) {
                updateConnectionFlowControl(paramLong);
              }
              paramLong = l;
            }
          }
          throw ((Throwable)new EOFException());
        }
      }
    }
    
    public final void setClosed$okhttp(boolean paramBoolean)
    {
      this.closed = paramBoolean;
    }
    
    public final void setFinished$okhttp(boolean paramBoolean)
    {
      this.finished = paramBoolean;
    }
    
    public final void setTrailers(Headers paramHeaders)
    {
      this.trailers = paramHeaders;
    }
    
    public Timeout timeout()
    {
      return (Timeout)Http2Stream.this.getReadTimeout$okhttp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\003\b\004\030\0002\0020\001B\005¢\006\002\020\002J\006\020\003\032\0020\004J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\006H\024J\b\020\b\032\0020\004H\024¨\006\t"}, d2={"Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "Lokio/AsyncTimeout;", "(Lokhttp3/internal/http2/Http2Stream;)V", "exitAndThrowIfTimedOut", "", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "okhttp"}, k=1, mv={1, 4, 0})
  public final class StreamTimeout
    extends AsyncTimeout
  {
    final Http2Stream this$0;
    
    public final void exitAndThrowIfTimedOut()
      throws IOException
    {
      if (!exit()) {
        return;
      }
      throw ((Throwable)newTimeoutException(null));
    }
    
    protected IOException newTimeoutException(IOException paramIOException)
    {
      SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException("timeout");
      if (paramIOException != null) {
        localSocketTimeoutException.initCause((Throwable)paramIOException);
      }
      return (IOException)localSocketTimeoutException;
    }
    
    protected void timedOut()
    {
      this.this$0.closeLater(ErrorCode.CANCEL);
      this.this$0.getConnection().sendDegradedPingLater$okhttp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http2/Http2Stream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */