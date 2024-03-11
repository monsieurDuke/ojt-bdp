package okhttp3.internal.http1;

import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Address;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.http.StatusLine.Companion;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000\001\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\006\n\002\020\016\n\002\b\t\030\000 ?2\0020\001:\007<=>?@ABB'\022\b\020\002\032\004\030\0010\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t¢\006\002\020\nJ\b\020\033\032\0020\034H\026J\030\020\035\032\0020\0362\006\020\037\032\0020\0272\006\020 \032\0020!H\026J\020\020\"\032\0020\0342\006\020#\032\0020$H\002J\b\020%\032\0020\034H\026J\b\020&\032\0020\034H\026J\b\020'\032\0020\036H\002J\020\020(\032\0020)2\006\020*\032\0020+H\002J\020\020,\032\0020)2\006\020-\032\0020!H\002J\b\020.\032\0020\036H\002J\b\020/\032\0020)H\002J\020\0200\032\0020)2\006\0201\032\0020\031H\026J\022\0202\032\004\030\001032\006\0204\032\0020\020H\026J\020\0205\032\0020!2\006\0201\032\0020\031H\026J\016\0206\032\0020\0342\006\0201\032\0020\031J\b\020\024\032\0020\025H\026J\026\0207\032\0020\0342\006\0208\032\0020\0252\006\0209\032\0020:J\020\020;\032\0020\0342\006\020\037\032\0020\027H\026R\020\020\002\032\004\030\0010\003X\004¢\006\002\n\000R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\013\020\fR\016\020\r\032\0020\016X\004¢\006\002\n\000R\021\020\017\032\0020\0208F¢\006\006\032\004\b\017\020\021R\016\020\b\032\0020\tX\004¢\006\002\n\000R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\022\032\0020\023X\016¢\006\002\n\000R\020\020\024\032\004\030\0010\025X\016¢\006\002\n\000R\030\020\026\032\0020\020*\0020\0278BX\004¢\006\006\032\004\b\026\020\030R\030\020\026\032\0020\020*\0020\0318BX\004¢\006\006\032\004\b\026\020\032¨\006C"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec;", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "connection", "Lokhttp3/internal/connection/RealConnection;", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", "(Lokhttp3/OkHttpClient;Lokhttp3/internal/connection/RealConnection;Lokio/BufferedSource;Lokio/BufferedSink;)V", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "headersReader", "Lokhttp3/internal/http1/HeadersReader;", "isClosed", "", "()Z", "state", "", "trailers", "Lokhttp3/Headers;", "isChunked", "Lokhttp3/Request;", "(Lokhttp3/Request;)Z", "Lokhttp3/Response;", "(Lokhttp3/Response;)Z", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "contentLength", "", "detachTimeout", "timeout", "Lokio/ForwardingTimeout;", "finishRequest", "flushRequest", "newChunkedSink", "newChunkedSource", "Lokio/Source;", "url", "Lokhttp3/HttpUrl;", "newFixedLengthSource", "length", "newKnownLengthSink", "newUnknownLengthSource", "openResponseBodySource", "response", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "reportedContentLength", "skipConnectBody", "writeRequest", "headers", "requestLine", "", "writeRequestHeaders", "AbstractSource", "ChunkedSink", "ChunkedSource", "Companion", "FixedLengthSource", "KnownLengthSink", "UnknownLengthSource", "okhttp"}, k=1, mv={1, 4, 0})
public final class Http1ExchangeCodec
  implements ExchangeCodec
{
  public static final Companion Companion = new Companion(null);
  private static final long NO_CHUNK_YET = -1L;
  private static final int STATE_CLOSED = 6;
  private static final int STATE_IDLE = 0;
  private static final int STATE_OPEN_REQUEST_BODY = 1;
  private static final int STATE_OPEN_RESPONSE_BODY = 4;
  private static final int STATE_READING_RESPONSE_BODY = 5;
  private static final int STATE_READ_RESPONSE_HEADERS = 3;
  private static final int STATE_WRITING_REQUEST_BODY = 2;
  private final OkHttpClient client;
  private final RealConnection connection;
  private final HeadersReader headersReader;
  private final BufferedSink sink;
  private final BufferedSource source;
  private int state;
  private Headers trailers;
  
  public Http1ExchangeCodec(OkHttpClient paramOkHttpClient, RealConnection paramRealConnection, BufferedSource paramBufferedSource, BufferedSink paramBufferedSink)
  {
    this.client = paramOkHttpClient;
    this.connection = paramRealConnection;
    this.source = paramBufferedSource;
    this.sink = paramBufferedSink;
    this.headersReader = new HeadersReader(paramBufferedSource);
  }
  
  private final void detachTimeout(ForwardingTimeout paramForwardingTimeout)
  {
    Timeout localTimeout = paramForwardingTimeout.delegate();
    paramForwardingTimeout.setDelegate(Timeout.NONE);
    localTimeout.clearDeadline();
    localTimeout.clearTimeout();
  }
  
  private final boolean isChunked(Request paramRequest)
  {
    return StringsKt.equals("chunked", paramRequest.header("Transfer-Encoding"), true);
  }
  
  private final boolean isChunked(Response paramResponse)
  {
    paramResponse = Response.header$default(paramResponse, "Transfer-Encoding", null, 2, null);
    Log5ECF72.a(paramResponse);
    LogE84000.a(paramResponse);
    Log229316.a(paramResponse);
    return StringsKt.equals("chunked", paramResponse, true);
  }
  
  private final Sink newChunkedSink()
  {
    int j = this.state;
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    if (i != 0)
    {
      this.state = 2;
      return (Sink)new ChunkedSink();
    }
    throw ((Throwable)new IllegalStateException(("state: " + this.state).toString()));
  }
  
  private final Source newChunkedSource(HttpUrl paramHttpUrl)
  {
    int i;
    if (this.state == 4) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.state = 5;
      return (Source)new ChunkedSource(paramHttpUrl);
    }
    throw ((Throwable)new IllegalStateException(("state: " + this.state).toString()));
  }
  
  private final Source newFixedLengthSource(long paramLong)
  {
    int i;
    if (this.state == 4) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.state = 5;
      return (Source)new FixedLengthSource(paramLong);
    }
    throw ((Throwable)new IllegalStateException(("state: " + this.state).toString()));
  }
  
  private final Sink newKnownLengthSink()
  {
    int j = this.state;
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    if (i != 0)
    {
      this.state = 2;
      return (Sink)new KnownLengthSink();
    }
    throw ((Throwable)new IllegalStateException(("state: " + this.state).toString()));
  }
  
  private final Source newUnknownLengthSource()
  {
    int i;
    if (this.state == 4) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.state = 5;
      getConnection().noNewExchanges$okhttp();
      return (Source)new UnknownLengthSource();
    }
    throw ((Throwable)new IllegalStateException(("state: " + this.state).toString()));
  }
  
  public void cancel()
  {
    getConnection().cancel();
  }
  
  public Sink createRequestBody(Request paramRequest, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    if ((paramRequest.body() != null) && (paramRequest.body().isDuplex())) {
      throw ((Throwable)new ProtocolException("Duplex connections are not supported for HTTP/1"));
    }
    if (isChunked(paramRequest))
    {
      paramRequest = newChunkedSink();
    }
    else
    {
      if (paramLong == -1L) {
        break label72;
      }
      paramRequest = newKnownLengthSink();
    }
    return paramRequest;
    label72:
    throw ((Throwable)new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!"));
  }
  
  public void finishRequest()
  {
    this.sink.flush();
  }
  
  public void flushRequest()
  {
    this.sink.flush();
  }
  
  public RealConnection getConnection()
  {
    return this.connection;
  }
  
  public final boolean isClosed()
  {
    boolean bool;
    if (this.state == 6) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Source openResponseBodySource(Response paramResponse)
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    if (!HttpHeaders.promisesBody(paramResponse))
    {
      paramResponse = newFixedLengthSource(0L);
    }
    else if (isChunked(paramResponse))
    {
      paramResponse = newChunkedSource(paramResponse.request().url());
    }
    else
    {
      long l = Util.headersContentLength(paramResponse);
      if (l != -1L) {
        paramResponse = newFixedLengthSource(l);
      } else {
        paramResponse = newUnknownLengthSource();
      }
    }
    return paramResponse;
  }
  
  public Response.Builder readResponseHeaders(boolean paramBoolean)
  {
    int k = this.state;
    int j = 1;
    int i = j;
    if (k != 1) {
      if (k == 3) {
        i = j;
      } else {
        i = 0;
      }
    }
    if (i != 0) {
      try
      {
        StatusLine localStatusLine = StatusLine.Companion.parse(this.headersReader.readLine());
        localObject = new okhttp3/Response$Builder;
        ((Response.Builder)localObject).<init>();
        localObject = ((Response.Builder)localObject).protocol(localStatusLine.protocol).code(localStatusLine.code).message(localStatusLine.message).headers(this.headersReader.readHeaders());
        if ((paramBoolean) && (localStatusLine.code == 100)) {
          localObject = null;
        } else if (localStatusLine.code == 100) {
          this.state = 3;
        } else {
          this.state = 4;
        }
        return (Response.Builder)localObject;
      }
      catch (EOFException localEOFException)
      {
        Object localObject = getConnection().route().address().url().redact();
        throw ((Throwable)new IOException("unexpected end of stream on " + (String)localObject, (Throwable)localEOFException));
      }
    }
    throw ((Throwable)new IllegalStateException(("state: " + this.state).toString()));
  }
  
  public long reportedContentLength(Response paramResponse)
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    long l;
    if (!HttpHeaders.promisesBody(paramResponse)) {
      l = 0L;
    } else if (isChunked(paramResponse)) {
      l = -1L;
    } else {
      l = Util.headersContentLength(paramResponse);
    }
    return l;
  }
  
  public final void skipConnectBody(Response paramResponse)
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    long l = Util.headersContentLength(paramResponse);
    if (l == -1L) {
      return;
    }
    paramResponse = newFixedLengthSource(l);
    Util.skipAll(paramResponse, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
    paramResponse.close();
  }
  
  public Headers trailers()
  {
    int i;
    if (this.state == 6) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      Headers localHeaders = this.trailers;
      if (localHeaders == null) {
        localHeaders = Util.EMPTY_HEADERS;
      }
      return localHeaders;
    }
    throw ((Throwable)new IllegalStateException("too early; can't read the trailers yet".toString()));
  }
  
  public final void writeRequest(Headers paramHeaders, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramHeaders, "headers");
    Intrinsics.checkNotNullParameter(paramString, "requestLine");
    int i = this.state;
    int j = 0;
    if (i == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.sink.writeUtf8(paramString).writeUtf8("\r\n");
      int k = paramHeaders.size();
      for (i = j; i < k; i++) {
        this.sink.writeUtf8(paramHeaders.name(i)).writeUtf8(": ").writeUtf8(paramHeaders.value(i)).writeUtf8("\r\n");
      }
      this.sink.writeUtf8("\r\n");
      this.state = 1;
      return;
    }
    throw ((Throwable)new IllegalStateException(("state: " + this.state).toString()));
  }
  
  public void writeRequestHeaders(Request paramRequest)
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    Object localObject = RequestLine.INSTANCE;
    Proxy.Type localType = getConnection().route().proxy().type();
    Intrinsics.checkNotNullExpressionValue(localType, "connection.route().proxy.type()");
    localObject = ((RequestLine)localObject).get(paramRequest, localType);
    writeRequest(paramRequest.headers(), (String)localObject);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\000\b¢\004\030\0002\0020\001B\005¢\006\002\020\002J\030\020\r\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\016H\026J\006\020\022\032\0020\023J\b\020\t\032\0020\024H\026R\032\020\003\032\0020\004X\016¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\bR\024\020\t\032\0020\nX\004¢\006\b\n\000\032\004\b\013\020\f¨\006\025"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokio/Source;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "timeout", "Lokio/ForwardingTimeout;", "getTimeout", "()Lokio/ForwardingTimeout;", "read", "", "sink", "Lokio/Buffer;", "byteCount", "responseBodyComplete", "", "Lokio/Timeout;", "okhttp"}, k=1, mv={1, 4, 0})
  private abstract class AbstractSource
    implements Source
  {
    private boolean closed;
    final Http1ExchangeCodec this$0;
    private final ForwardingTimeout timeout;
    
    public AbstractSource()
    {
      this.timeout = new ForwardingTimeout(Http1ExchangeCodec.access$getSource$p(this$1).timeout());
    }
    
    protected final boolean getClosed()
    {
      return this.closed;
    }
    
    protected final ForwardingTimeout getTimeout()
    {
      return this.timeout;
    }
    
    public long read(Buffer paramBuffer, long paramLong)
    {
      Intrinsics.checkNotNullParameter(paramBuffer, "sink");
      try
      {
        paramLong = Http1ExchangeCodec.access$getSource$p(this.this$0).read(paramBuffer, paramLong);
        return paramLong;
      }
      catch (IOException paramBuffer)
      {
        this.this$0.getConnection().noNewExchanges$okhttp();
        responseBodyComplete();
        throw ((Throwable)paramBuffer);
      }
    }
    
    public final void responseBodyComplete()
    {
      if (Http1ExchangeCodec.access$getState$p(this.this$0) == 6) {
        return;
      }
      if (Http1ExchangeCodec.access$getState$p(this.this$0) == 5)
      {
        Http1ExchangeCodec.access$detachTimeout(this.this$0, this.timeout);
        Http1ExchangeCodec.access$setState$p(this.this$0, 6);
        return;
      }
      throw ((Throwable)new IllegalStateException("state: " + Http1ExchangeCodec.access$getState$p(this.this$0)));
    }
    
    protected final void setClosed(boolean paramBoolean)
    {
      this.closed = paramBoolean;
    }
    
    public Timeout timeout()
    {
      return (Timeout)this.timeout;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\b\020\007\032\0020\bH\026J\b\020\t\032\0020\bH\026J\b\020\005\032\0020\nH\026J\030\020\013\032\0020\b2\006\020\f\032\0020\r2\006\020\016\032\0020\017H\026R\016\020\003\032\0020\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000¨\006\020"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink;", "Lokio/Sink;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "timeout", "Lokio/ForwardingTimeout;", "close", "", "flush", "Lokio/Timeout;", "write", "source", "Lokio/Buffer;", "byteCount", "", "okhttp"}, k=1, mv={1, 4, 0})
  private final class ChunkedSink
    implements Sink
  {
    private boolean closed;
    final Http1ExchangeCodec this$0;
    private final ForwardingTimeout timeout;
    
    public ChunkedSink()
    {
      this.timeout = new ForwardingTimeout(Http1ExchangeCodec.access$getSink$p(this$1).timeout());
    }
    
    public void close()
    {
      try
      {
        boolean bool = this.closed;
        if (bool) {
          return;
        }
        this.closed = true;
        Http1ExchangeCodec.access$getSink$p(this.this$0).writeUtf8("0\r\n\r\n");
        Http1ExchangeCodec.access$detachTimeout(this.this$0, this.timeout);
        Http1ExchangeCodec.access$setState$p(this.this$0, 3);
        return;
      }
      finally {}
    }
    
    public void flush()
    {
      try
      {
        boolean bool = this.closed;
        if (bool) {
          return;
        }
        Http1ExchangeCodec.access$getSink$p(this.this$0).flush();
        return;
      }
      finally {}
    }
    
    public Timeout timeout()
    {
      return (Timeout)this.timeout;
    }
    
    public void write(Buffer paramBuffer, long paramLong)
    {
      Intrinsics.checkNotNullParameter(paramBuffer, "source");
      if ((this.closed ^ true))
      {
        if (paramLong == 0L) {
          return;
        }
        Http1ExchangeCodec.access$getSink$p(this.this$0).writeHexadecimalUnsignedLong(paramLong);
        Http1ExchangeCodec.access$getSink$p(this.this$0).writeUtf8("\r\n");
        Http1ExchangeCodec.access$getSink$p(this.this$0).write(paramBuffer, paramLong);
        Http1ExchangeCodec.access$getSink$p(this.this$0).writeUtf8("\r\n");
        return;
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\020\013\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\b\004\030\0002\0060\001R\0020\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\b\020\n\032\0020\013H\026J\030\020\f\032\0020\0072\006\020\r\032\0020\0162\006\020\017\032\0020\007H\026J\b\020\020\032\0020\013H\002R\016\020\006\032\0020\007X\016¢\006\002\n\000R\016\020\b\032\0020\tX\016¢\006\002\n\000R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\021"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "url", "Lokhttp3/HttpUrl;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;Lokhttp3/HttpUrl;)V", "bytesRemainingInChunk", "", "hasMoreChunks", "", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "readChunkSize", "okhttp"}, k=1, mv={1, 4, 0})
  private final class ChunkedSource
    extends Http1ExchangeCodec.AbstractSource
  {
    private long bytesRemainingInChunk;
    private boolean hasMoreChunks;
    private final HttpUrl url;
    
    public ChunkedSource()
    {
      super();
      this.url = ((HttpUrl)localObject);
      this.bytesRemainingInChunk = -1L;
      this.hasMoreChunks = true;
    }
    
    private final void readChunkSize()
    {
      if (this.bytesRemainingInChunk != -1L) {
        Http1ExchangeCodec.access$getSource$p(Http1ExchangeCodec.this).readUtf8LineStrict();
      }
      try
      {
        this.bytesRemainingInChunk = Http1ExchangeCodec.access$getSource$p(Http1ExchangeCodec.this).readHexadecimalUnsignedLong();
        Object localObject1 = Http1ExchangeCodec.access$getSource$p(Http1ExchangeCodec.this).readUtf8LineStrict();
        if (localObject1 != null)
        {
          localObject1 = StringsKt.trim((CharSequence)localObject1).toString();
          if (this.bytesRemainingInChunk >= 0L)
          {
            int i;
            if (((CharSequence)localObject1).length() > 0) {
              i = 1;
            } else {
              i = 0;
            }
            if (i != 0)
            {
              boolean bool = StringsKt.startsWith$default((String)localObject1, ";", false, 2, null);
              if (!bool) {}
            }
            else
            {
              if (this.bytesRemainingInChunk == 0L)
              {
                this.hasMoreChunks = false;
                localObject1 = Http1ExchangeCodec.this;
                Http1ExchangeCodec.access$setTrailers$p((Http1ExchangeCodec)localObject1, Http1ExchangeCodec.access$getHeadersReader$p((Http1ExchangeCodec)localObject1).readHeaders());
                localObject1 = Http1ExchangeCodec.access$getClient$p(Http1ExchangeCodec.this);
                Intrinsics.checkNotNull(localObject1);
                localObject1 = ((OkHttpClient)localObject1).cookieJar();
                localObject3 = this.url;
                localObject2 = Http1ExchangeCodec.access$getTrailers$p(Http1ExchangeCodec.this);
                Intrinsics.checkNotNull(localObject2);
                HttpHeaders.receiveHeaders((CookieJar)localObject1, (HttpUrl)localObject3, (Headers)localObject2);
                responseBodyComplete();
              }
              return;
            }
          }
          Object localObject2 = new java/net/ProtocolException;
          Object localObject3 = new java/lang/StringBuilder;
          ((StringBuilder)localObject3).<init>();
          ((ProtocolException)localObject2).<init>("expected chunk size and optional extensions" + " but was \"" + this.bytesRemainingInChunk + (String)localObject1 + '"');
          throw ((Throwable)localObject2);
        }
        localObject1 = new java/lang/NullPointerException;
        ((NullPointerException)localObject1).<init>("null cannot be cast to non-null type kotlin.CharSequence");
        throw ((Throwable)localObject1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw ((Throwable)new ProtocolException(localNumberFormatException.getMessage()));
      }
    }
    
    public void close()
    {
      if (getClosed()) {
        return;
      }
      if ((this.hasMoreChunks) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS)))
      {
        Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
        responseBodyComplete();
      }
      setClosed(true);
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
        if ((getClosed() ^ true))
        {
          if (!this.hasMoreChunks) {
            return -1L;
          }
          long l = this.bytesRemainingInChunk;
          if ((l == 0L) || (l == -1L))
          {
            readChunkSize();
            if (!this.hasMoreChunks) {
              return -1L;
            }
          }
          paramLong = super.read(paramBuffer, Math.min(paramLong, this.bytesRemainingInChunk));
          if (paramLong != -1L)
          {
            this.bytesRemainingInChunk -= paramLong;
            return paramLong;
          }
          Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
          paramBuffer = new ProtocolException("unexpected end of stream");
          responseBodyComplete();
          throw ((Throwable)paramBuffer);
        }
        throw ((Throwable)new IllegalStateException("closed".toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\b\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\006XT¢\006\002\n\000R\016\020\007\032\0020\006XT¢\006\002\n\000R\016\020\b\032\0020\006XT¢\006\002\n\000R\016\020\t\032\0020\006XT¢\006\002\n\000R\016\020\n\032\0020\006XT¢\006\002\n\000R\016\020\013\032\0020\006XT¢\006\002\n\000R\016\020\f\032\0020\006XT¢\006\002\n\000¨\006\r"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$Companion;", "", "()V", "NO_CHUNK_YET", "", "STATE_CLOSED", "", "STATE_IDLE", "STATE_OPEN_REQUEST_BODY", "STATE_OPEN_RESPONSE_BODY", "STATE_READING_RESPONSE_BODY", "STATE_READ_RESPONSE_HEADERS", "STATE_WRITING_REQUEST_BODY", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\b\004\030\0002\0060\001R\0020\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\b\020\006\032\0020\007H\026J\030\020\b\032\0020\0042\006\020\t\032\0020\n2\006\020\013\032\0020\004H\026R\016\020\003\032\0020\004X\016¢\006\002\n\000¨\006\f"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$FixedLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "bytesRemaining", "", "(Lokhttp3/internal/http1/Http1ExchangeCodec;J)V", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 4, 0})
  private final class FixedLengthSource
    extends Http1ExchangeCodec.AbstractSource
  {
    private long bytesRemaining;
    
    public FixedLengthSource()
    {
      super();
      Object localObject;
      this.bytesRemaining = localObject;
      if (localObject == 0L) {
        responseBodyComplete();
      }
    }
    
    public void close()
    {
      if (getClosed()) {
        return;
      }
      if ((this.bytesRemaining != 0L) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS)))
      {
        Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
        responseBodyComplete();
      }
      setClosed(true);
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
        if ((getClosed() ^ true))
        {
          long l = this.bytesRemaining;
          if (l == 0L) {
            return -1L;
          }
          paramLong = super.read(paramBuffer, Math.min(l, paramLong));
          if (paramLong != -1L)
          {
            l = this.bytesRemaining - paramLong;
            this.bytesRemaining = l;
            if (l == 0L) {
              responseBodyComplete();
            }
            return paramLong;
          }
          Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
          paramBuffer = new ProtocolException("unexpected end of stream");
          responseBodyComplete();
          throw ((Throwable)paramBuffer);
        }
        throw ((Throwable)new IllegalStateException("closed".toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\b\020\007\032\0020\bH\026J\b\020\t\032\0020\bH\026J\b\020\005\032\0020\nH\026J\030\020\013\032\0020\b2\006\020\f\032\0020\r2\006\020\016\032\0020\017H\026R\016\020\003\032\0020\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000¨\006\020"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$KnownLengthSink;", "Lokio/Sink;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "timeout", "Lokio/ForwardingTimeout;", "close", "", "flush", "Lokio/Timeout;", "write", "source", "Lokio/Buffer;", "byteCount", "", "okhttp"}, k=1, mv={1, 4, 0})
  private final class KnownLengthSink
    implements Sink
  {
    private boolean closed;
    final Http1ExchangeCodec this$0;
    private final ForwardingTimeout timeout;
    
    public KnownLengthSink()
    {
      this.timeout = new ForwardingTimeout(Http1ExchangeCodec.access$getSink$p(this$1).timeout());
    }
    
    public void close()
    {
      if (this.closed) {
        return;
      }
      this.closed = true;
      Http1ExchangeCodec.access$detachTimeout(this.this$0, this.timeout);
      Http1ExchangeCodec.access$setState$p(this.this$0, 3);
    }
    
    public void flush()
    {
      if (this.closed) {
        return;
      }
      Http1ExchangeCodec.access$getSink$p(this.this$0).flush();
    }
    
    public Timeout timeout()
    {
      return (Timeout)this.timeout;
    }
    
    public void write(Buffer paramBuffer, long paramLong)
    {
      Intrinsics.checkNotNullParameter(paramBuffer, "source");
      if ((this.closed ^ true))
      {
        Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramLong);
        Http1ExchangeCodec.access$getSink$p(this.this$0).write(paramBuffer, paramLong);
        return;
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\002\b\004\030\0002\0060\001R\0020\002B\005¢\006\002\020\003J\b\020\006\032\0020\007H\026J\030\020\b\032\0020\t2\006\020\n\032\0020\0132\006\020\f\032\0020\tH\026R\016\020\004\032\0020\005X\016¢\006\002\n\000¨\006\r"}, d2={"Lokhttp3/internal/http1/Http1ExchangeCodec$UnknownLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "inputExhausted", "", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 4, 0})
  private final class UnknownLengthSource
    extends Http1ExchangeCodec.AbstractSource
  {
    private boolean inputExhausted;
    final Http1ExchangeCodec this$0;
    
    public UnknownLengthSource()
    {
      super();
    }
    
    public void close()
    {
      if (getClosed()) {
        return;
      }
      if (!this.inputExhausted) {
        responseBodyComplete();
      }
      setClosed(true);
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
        if ((getClosed() ^ true))
        {
          if (this.inputExhausted) {
            return -1L;
          }
          paramLong = super.read(paramBuffer, paramLong);
          if (paramLong == -1L)
          {
            this.inputExhausted = true;
            responseBodyComplete();
            return -1L;
          }
          return paramLong;
        }
        throw ((Throwable)new IllegalStateException("closed".toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http1/Http1ExchangeCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */