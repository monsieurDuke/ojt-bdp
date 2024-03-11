package okhttp3.internal.connection;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.ws.RealWebSocket.Streams;
import okio.Buffer;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\000z\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\007\n\002\030\002\n\000\n\002\020\t\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\030\0002\0020\001:\002ABB%\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t¢\006\002\020\nJ7\020\034\032\002H\035\"\n\b\000\020\035*\004\030\0010\0362\006\020\037\032\0020 2\006\020!\032\0020\0262\006\020\"\032\0020\0262\006\020#\032\002H\035¢\006\002\020$J\006\020%\032\0020&J\026\020'\032\0020(2\006\020)\032\0020*2\006\020+\032\0020\026J\006\020,\032\0020&J\006\020-\032\0020&J\006\020.\032\0020&J\006\020/\032\00200J\006\0201\032\0020&J\006\0202\032\0020&J\016\0203\032\002042\006\0205\032\00206J\020\0207\032\004\030\001082\006\0209\032\0020\026J\016\020:\032\0020&2\006\0205\032\00206J\006\020;\032\0020&J\020\020<\032\0020&2\006\020#\032\0020\036H\002J\006\020=\032\0020>J\006\020?\032\0020&J\016\020@\032\0020&2\006\020)\032\0020*R\024\020\002\032\0020\003X\004¢\006\b\n\000\032\004\b\013\020\fR\016\020\b\032\0020\tX\004¢\006\002\n\000R\024\020\r\032\0020\016X\004¢\006\b\n\000\032\004\b\017\020\020R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\021\020\022R\024\020\006\032\0020\007X\004¢\006\b\n\000\032\004\b\023\020\024R\024\020\025\032\0020\0268@X\004¢\006\006\032\004\b\027\020\030R\036\020\032\032\0020\0262\006\020\031\032\0020\026@BX\016¢\006\b\n\000\032\004\b\033\020\030¨\006C"}, d2={"Lokhttp3/internal/connection/Exchange;", "", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "finder", "Lokhttp3/internal/connection/ExchangeFinder;", "codec", "Lokhttp3/internal/http/ExchangeCodec;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;Lokhttp3/internal/connection/ExchangeFinder;Lokhttp3/internal/http/ExchangeCodec;)V", "getCall$okhttp", "()Lokhttp3/internal/connection/RealCall;", "connection", "Lokhttp3/internal/connection/RealConnection;", "getConnection$okhttp", "()Lokhttp3/internal/connection/RealConnection;", "getEventListener$okhttp", "()Lokhttp3/EventListener;", "getFinder$okhttp", "()Lokhttp3/internal/connection/ExchangeFinder;", "isCoalescedConnection", "", "isCoalescedConnection$okhttp", "()Z", "<set-?>", "isDuplex", "isDuplex$okhttp", "bodyComplete", "E", "Ljava/io/IOException;", "bytesRead", "", "responseDone", "requestDone", "e", "(JZZLjava/io/IOException;)Ljava/io/IOException;", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "Lokhttp3/Request;", "duplex", "detachWithViolence", "finishRequest", "flushRequest", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "noNewExchangesOnConnection", "noRequestBody", "openResponseBody", "Lokhttp3/ResponseBody;", "response", "Lokhttp3/Response;", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "responseHeadersEnd", "responseHeadersStart", "trackFailure", "trailers", "Lokhttp3/Headers;", "webSocketUpgradeFailed", "writeRequestHeaders", "RequestBodySink", "ResponseBodySource", "okhttp"}, k=1, mv={1, 4, 0})
public final class Exchange
{
  private final RealCall call;
  private final ExchangeCodec codec;
  private final RealConnection connection;
  private final EventListener eventListener;
  private final ExchangeFinder finder;
  private boolean isDuplex;
  
  public Exchange(RealCall paramRealCall, EventListener paramEventListener, ExchangeFinder paramExchangeFinder, ExchangeCodec paramExchangeCodec)
  {
    this.call = paramRealCall;
    this.eventListener = paramEventListener;
    this.finder = paramExchangeFinder;
    this.codec = paramExchangeCodec;
    this.connection = paramExchangeCodec.getConnection();
  }
  
  private final void trackFailure(IOException paramIOException)
  {
    this.finder.trackFailure(paramIOException);
    this.codec.getConnection().trackFailure$okhttp(this.call, paramIOException);
  }
  
  public final <E extends IOException> E bodyComplete(long paramLong, boolean paramBoolean1, boolean paramBoolean2, E paramE)
  {
    if (paramE != null) {
      trackFailure(paramE);
    }
    if (paramBoolean2) {
      if (paramE != null) {
        this.eventListener.requestFailed((Call)this.call, paramE);
      } else {
        this.eventListener.requestBodyEnd((Call)this.call, paramLong);
      }
    }
    if (paramBoolean1) {
      if (paramE != null) {
        this.eventListener.responseFailed((Call)this.call, paramE);
      } else {
        this.eventListener.responseBodyEnd((Call)this.call, paramLong);
      }
    }
    return this.call.messageDone$okhttp(this, paramBoolean2, paramBoolean1, paramE);
  }
  
  public final void cancel()
  {
    this.codec.cancel();
  }
  
  public final Sink createRequestBody(Request paramRequest, boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    this.isDuplex = paramBoolean;
    RequestBody localRequestBody = paramRequest.body();
    Intrinsics.checkNotNull(localRequestBody);
    long l = localRequestBody.contentLength();
    this.eventListener.requestBodyStart((Call)this.call);
    return (Sink)new RequestBodySink(this.codec.createRequestBody(paramRequest, l), l);
  }
  
  public final void detachWithViolence()
  {
    this.codec.cancel();
    this.call.messageDone$okhttp(this, true, true, null);
  }
  
  public final void finishRequest()
    throws IOException
  {
    try
    {
      this.codec.finishRequest();
      return;
    }
    catch (IOException localIOException)
    {
      this.eventListener.requestFailed((Call)this.call, localIOException);
      trackFailure(localIOException);
      throw ((Throwable)localIOException);
    }
  }
  
  public final void flushRequest()
    throws IOException
  {
    try
    {
      this.codec.flushRequest();
      return;
    }
    catch (IOException localIOException)
    {
      this.eventListener.requestFailed((Call)this.call, localIOException);
      trackFailure(localIOException);
      throw ((Throwable)localIOException);
    }
  }
  
  public final RealCall getCall$okhttp()
  {
    return this.call;
  }
  
  public final RealConnection getConnection$okhttp()
  {
    return this.connection;
  }
  
  public final EventListener getEventListener$okhttp()
  {
    return this.eventListener;
  }
  
  public final ExchangeFinder getFinder$okhttp()
  {
    return this.finder;
  }
  
  public final boolean isCoalescedConnection$okhttp()
  {
    return Intrinsics.areEqual(this.finder.getAddress$okhttp().url().host(), this.connection.route().address().url().host()) ^ true;
  }
  
  public final boolean isDuplex$okhttp()
  {
    return this.isDuplex;
  }
  
  public final RealWebSocket.Streams newWebSocketStreams()
    throws SocketException
  {
    this.call.timeoutEarlyExit();
    return this.codec.getConnection().newWebSocketStreams$okhttp(this);
  }
  
  public final void noNewExchangesOnConnection()
  {
    this.codec.getConnection().noNewExchanges$okhttp();
  }
  
  public final void noRequestBody()
  {
    this.call.messageDone$okhttp(this, true, false, null);
  }
  
  public final ResponseBody openResponseBody(Response paramResponse)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    try
    {
      String str = Response.header$default(paramResponse, "Content-Type", null, 2, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      long l = this.codec.reportedContentLength(paramResponse);
      Object localObject = this.codec.openResponseBodySource(paramResponse);
      paramResponse = new okhttp3/internal/connection/Exchange$ResponseBodySource;
      paramResponse.<init>(this, (Source)localObject, l);
      localObject = new okhttp3/internal/http/RealResponseBody;
      ((RealResponseBody)localObject).<init>(str, l, Okio.buffer((Source)paramResponse));
      paramResponse = (ResponseBody)localObject;
      return paramResponse;
    }
    catch (IOException paramResponse)
    {
      this.eventListener.responseFailed((Call)this.call, paramResponse);
      trackFailure(paramResponse);
      throw ((Throwable)paramResponse);
    }
  }
  
  public final Response.Builder readResponseHeaders(boolean paramBoolean)
    throws IOException
  {
    try
    {
      Response.Builder localBuilder = this.codec.readResponseHeaders(paramBoolean);
      if (localBuilder != null) {
        localBuilder.initExchange$okhttp(this);
      }
      return localBuilder;
    }
    catch (IOException localIOException)
    {
      this.eventListener.responseFailed((Call)this.call, localIOException);
      trackFailure(localIOException);
      throw ((Throwable)localIOException);
    }
  }
  
  public final void responseHeadersEnd(Response paramResponse)
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    this.eventListener.responseHeadersEnd((Call)this.call, paramResponse);
  }
  
  public final void responseHeadersStart()
  {
    this.eventListener.responseHeadersStart((Call)this.call);
  }
  
  public final Headers trailers()
    throws IOException
  {
    return this.codec.trailers();
  }
  
  public final void webSocketUpgradeFailed()
  {
    bodyComplete(-1L, true, true, null);
  }
  
  public final void writeRequestHeaders(Request paramRequest)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    try
    {
      this.eventListener.requestHeadersStart((Call)this.call);
      this.codec.writeRequestHeaders(paramRequest);
      this.eventListener.requestHeadersEnd((Call)this.call, paramRequest);
      return;
    }
    catch (IOException paramRequest)
    {
      this.eventListener.requestFailed((Call)this.call, paramRequest);
      trackFailure(paramRequest);
      throw ((Throwable)paramRequest);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\b\004\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\b\020\013\032\0020\fH\026J!\020\r\032\002H\016\"\n\b\000\020\016*\004\030\0010\0172\006\020\020\032\002H\016H\002¢\006\002\020\021J\b\020\022\032\0020\fH\026J\030\020\023\032\0020\f2\006\020\024\032\0020\0252\006\020\026\032\0020\005H\026R\016\020\007\032\0020\005X\016¢\006\002\n\000R\016\020\b\032\0020\tX\016¢\006\002\n\000R\016\020\n\032\0020\tX\016¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\027"}, d2={"Lokhttp3/internal/connection/Exchange$RequestBodySink;", "Lokio/ForwardingSink;", "delegate", "Lokio/Sink;", "contentLength", "", "(Lokhttp3/internal/connection/Exchange;Lokio/Sink;J)V", "bytesReceived", "closed", "", "completed", "close", "", "complete", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "flush", "write", "source", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 4, 0})
  private final class RequestBodySink
    extends ForwardingSink
  {
    private long bytesReceived;
    private boolean closed;
    private boolean completed;
    private final long contentLength;
    
    public RequestBodySink(long paramLong)
    {
      super();
      Object localObject;
      this.contentLength = localObject;
    }
    
    private final <E extends IOException> E complete(E paramE)
    {
      if (this.completed) {
        return paramE;
      }
      this.completed = true;
      return Exchange.this.bodyComplete(this.bytesReceived, false, true, paramE);
    }
    
    public void close()
      throws IOException
    {
      if (this.closed) {
        return;
      }
      this.closed = true;
      long l = this.contentLength;
      if ((l != -1L) && (this.bytesReceived != l)) {
        throw ((Throwable)new ProtocolException("unexpected end of stream"));
      }
      try
      {
        super.close();
        complete(null);
        return;
      }
      catch (IOException localIOException)
      {
        throw ((Throwable)complete(localIOException));
      }
    }
    
    public void flush()
      throws IOException
    {
      try
      {
        super.flush();
        return;
      }
      catch (IOException localIOException)
      {
        throw ((Throwable)complete(localIOException));
      }
    }
    
    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramBuffer, "source");
      if ((this.closed ^ true))
      {
        long l = this.contentLength;
        if ((l != -1L) && (this.bytesReceived + paramLong > l)) {
          throw ((Throwable)new ProtocolException("expected " + this.contentLength + " bytes but received " + (this.bytesReceived + paramLong)));
        }
        try
        {
          super.write(paramBuffer, paramLong);
          this.bytesReceived += paramLong;
          return;
        }
        catch (IOException paramBuffer)
        {
          throw ((Throwable)complete(paramBuffer));
        }
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\020\013\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\b\004\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\b\020\f\032\0020\rH\026J\037\020\016\032\002H\017\"\n\b\000\020\017*\004\030\0010\0202\006\020\021\032\002H\017¢\006\002\020\022J\030\020\023\032\0020\0052\006\020\024\032\0020\0252\006\020\026\032\0020\005H\026R\016\020\007\032\0020\005X\016¢\006\002\n\000R\016\020\b\032\0020\tX\016¢\006\002\n\000R\016\020\n\032\0020\tX\016¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000R\016\020\013\032\0020\tX\016¢\006\002\n\000¨\006\027"}, d2={"Lokhttp3/internal/connection/Exchange$ResponseBodySource;", "Lokio/ForwardingSource;", "delegate", "Lokio/Source;", "contentLength", "", "(Lokhttp3/internal/connection/Exchange;Lokio/Source;J)V", "bytesReceived", "closed", "", "completed", "invokeStartEvent", "close", "", "complete", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 4, 0})
  public final class ResponseBodySource
    extends ForwardingSource
  {
    private long bytesReceived;
    private boolean closed;
    private boolean completed;
    private final long contentLength;
    private boolean invokeStartEvent;
    
    public ResponseBodySource(long paramLong)
    {
      super();
      Object localObject;
      this.contentLength = localObject;
      this.invokeStartEvent = true;
      if (localObject == 0L) {
        complete(null);
      }
    }
    
    public void close()
      throws IOException
    {
      if (this.closed) {
        return;
      }
      this.closed = true;
      try
      {
        super.close();
        complete(null);
        return;
      }
      catch (IOException localIOException)
      {
        throw ((Throwable)complete(localIOException));
      }
    }
    
    public final <E extends IOException> E complete(E paramE)
    {
      if (this.completed) {
        return paramE;
      }
      this.completed = true;
      if ((paramE == null) && (this.invokeStartEvent))
      {
        this.invokeStartEvent = false;
        Exchange.this.getEventListener$okhttp().responseBodyStart((Call)Exchange.this.getCall$okhttp());
      }
      return Exchange.this.bodyComplete(this.bytesReceived, true, false, paramE);
    }
    
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramBuffer, "sink");
      if ((this.closed ^ true)) {
        try
        {
          long l1 = delegate().read(paramBuffer, paramLong);
          if (this.invokeStartEvent)
          {
            this.invokeStartEvent = false;
            Exchange.this.getEventListener$okhttp().responseBodyStart((Call)Exchange.this.getCall$okhttp());
          }
          if (l1 == -1L)
          {
            complete(null);
            return -1L;
          }
          paramLong = this.bytesReceived + l1;
          long l2 = this.contentLength;
          if ((l2 != -1L) && (paramLong > l2))
          {
            paramBuffer = new java/net/ProtocolException;
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            paramBuffer.<init>("expected " + this.contentLength + " bytes but received " + paramLong);
            throw ((Throwable)paramBuffer);
          }
          this.bytesReceived = paramLong;
          if (paramLong == l2) {
            complete(null);
          }
          return l1;
        }
        catch (IOException paramBuffer)
        {
          throw ((Throwable)complete(paramBuffer));
        }
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/connection/Exchange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */