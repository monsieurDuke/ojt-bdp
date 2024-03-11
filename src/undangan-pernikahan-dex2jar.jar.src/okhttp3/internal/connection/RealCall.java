package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.EventListener;
import okhttp3.EventListener.Factory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okio.AsyncTimeout;

@Metadata(bv={1, 0, 3}, d1={"\000§\001\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\000\n\002\b\004\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\013\n\002\b\004\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\013\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\006*\001.\030\0002\0020\001:\002deB\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bJ\016\0201\032\002022\006\020\020\032\0020\017J!\0203\032\002H4\"\n\b\000\0204*\004\030\001052\006\0206\032\002H4H\002¢\006\002\0207J\b\0208\032\00202H\002J\b\0209\032\00202H\026J\b\020:\032\0020\000H\026J\020\020;\032\0020<2\006\020=\032\0020>H\002J\020\020?\032\002022\006\020@\032\0020AH\026J\026\020B\032\002022\006\020C\032\0020\0052\006\020D\032\0020\007J\b\020E\032\0020FH\026J\025\020G\032\002022\006\020H\032\0020\007H\000¢\006\002\bIJ\r\020J\032\0020FH\000¢\006\002\bKJ\025\020L\032\0020\0362\006\020M\032\0020NH\000¢\006\002\bOJ\b\020P\032\0020\007H\026J\b\020Q\032\0020\007H\026J;\020R\032\002H4\"\n\b\000\0204*\004\030\001052\006\020\035\032\0020\0362\006\020S\032\0020\0072\006\020T\032\0020\0072\006\0206\032\002H4H\000¢\006\004\bU\020VJ\031\020W\032\004\030\001052\b\0206\032\004\030\00105H\000¢\006\002\bXJ\r\020Y\032\0020ZH\000¢\006\002\b[J\017\020\\\032\004\030\0010]H\000¢\006\002\b^J\b\020C\032\0020\005H\026J\006\020_\032\0020\007J\b\020-\032\0020`H\026J\006\0200\032\00202J!\020a\032\002H4\"\n\b\000\0204*\004\030\001052\006\020b\032\002H4H\002¢\006\002\0207J\b\020c\032\0020ZH\002R\020\020\t\032\004\030\0010\nX\016¢\006\002\n\000R\016\020\013\032\0020\007X\016¢\006\002\n\000R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\f\020\rR\"\020\020\032\004\030\0010\0172\b\020\016\032\004\030\0010\017@BX\016¢\006\b\n\000\032\004\b\021\020\022R\016\020\023\032\0020\024X\004¢\006\002\n\000R\034\020\025\032\004\030\0010\017X\016¢\006\016\n\000\032\004\b\026\020\022\"\004\b\027\020\030R\024\020\031\032\0020\032X\004¢\006\b\n\000\032\004\b\033\020\034R\020\020\035\032\004\030\0010\036X\016¢\006\002\n\000R\020\020\037\032\004\030\0010 X\016¢\006\002\n\000R\016\020!\032\0020\"X\004¢\006\002\n\000R\016\020#\032\0020\007X\016¢\006\002\n\000R\021\020\006\032\0020\007¢\006\b\n\000\032\004\b$\020%R\"\020&\032\004\030\0010\0362\b\020\016\032\004\030\0010\036@BX\016¢\006\b\n\000\032\004\b'\020(R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b)\020*R\016\020+\032\0020\007X\016¢\006\002\n\000R\016\020,\032\0020\007X\016¢\006\002\n\000R\020\020-\032\0020.X\004¢\006\004\n\002\020/R\016\0200\032\0020\007X\016¢\006\002\n\000¨\006f"}, d2={"Lokhttp3/internal/connection/RealCall;", "Lokhttp3/Call;", "client", "Lokhttp3/OkHttpClient;", "originalRequest", "Lokhttp3/Request;", "forWebSocket", "", "(Lokhttp3/OkHttpClient;Lokhttp3/Request;Z)V", "callStackTrace", "", "canceled", "getClient", "()Lokhttp3/OkHttpClient;", "<set-?>", "Lokhttp3/internal/connection/RealConnection;", "connection", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "connectionToCancel", "getConnectionToCancel", "setConnectionToCancel", "(Lokhttp3/internal/connection/RealConnection;)V", "eventListener", "Lokhttp3/EventListener;", "getEventListener$okhttp", "()Lokhttp3/EventListener;", "exchange", "Lokhttp3/internal/connection/Exchange;", "exchangeFinder", "Lokhttp3/internal/connection/ExchangeFinder;", "executed", "Ljava/util/concurrent/atomic/AtomicBoolean;", "expectMoreExchanges", "getForWebSocket", "()Z", "interceptorScopedExchange", "getInterceptorScopedExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "getOriginalRequest", "()Lokhttp3/Request;", "requestBodyOpen", "responseBodyOpen", "timeout", "okhttp3/internal/connection/RealCall$timeout$1", "Lokhttp3/internal/connection/RealCall$timeout$1;", "timeoutEarlyExit", "acquireConnectionNoEvents", "", "callDone", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "callStart", "cancel", "clone", "createAddress", "Lokhttp3/Address;", "url", "Lokhttp3/HttpUrl;", "enqueue", "responseCallback", "Lokhttp3/Callback;", "enterNetworkInterceptorExchange", "request", "newExchangeFinder", "execute", "Lokhttp3/Response;", "exitNetworkInterceptorExchange", "closeExchange", "exitNetworkInterceptorExchange$okhttp", "getResponseWithInterceptorChain", "getResponseWithInterceptorChain$okhttp", "initExchange", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "initExchange$okhttp", "isCanceled", "isExecuted", "messageDone", "requestDone", "responseDone", "messageDone$okhttp", "(Lokhttp3/internal/connection/Exchange;ZZLjava/io/IOException;)Ljava/io/IOException;", "noMoreExchanges", "noMoreExchanges$okhttp", "redactedUrl", "", "redactedUrl$okhttp", "releaseConnectionNoEvents", "Ljava/net/Socket;", "releaseConnectionNoEvents$okhttp", "retryAfterFailure", "Lokio/AsyncTimeout;", "timeoutExit", "cause", "toLoggableString", "AsyncCall", "CallReference", "okhttp"}, k=1, mv={1, 4, 0})
public final class RealCall
  implements Call
{
  private Object callStackTrace;
  private volatile boolean canceled;
  private final OkHttpClient client;
  private RealConnection connection;
  private final RealConnectionPool connectionPool;
  private volatile RealConnection connectionToCancel;
  private final EventListener eventListener;
  private volatile Exchange exchange;
  private ExchangeFinder exchangeFinder;
  private final AtomicBoolean executed;
  private boolean expectMoreExchanges;
  private final boolean forWebSocket;
  private Exchange interceptorScopedExchange;
  private final Request originalRequest;
  private boolean requestBodyOpen;
  private boolean responseBodyOpen;
  private final timeout.1 timeout;
  private boolean timeoutEarlyExit;
  
  public RealCall(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean)
  {
    this.client = paramOkHttpClient;
    this.originalRequest = paramRequest;
    this.forWebSocket = paramBoolean;
    this.connectionPool = paramOkHttpClient.connectionPool().getDelegate$okhttp();
    this.eventListener = paramOkHttpClient.eventListenerFactory().create((Call)this);
    paramRequest = new AsyncTimeout()
    {
      final RealCall this$0;
      
      protected void timedOut()
      {
        this.this$0.cancel();
      }
    };
    paramRequest.timeout(paramOkHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    paramOkHttpClient = Unit.INSTANCE;
    this.timeout = paramRequest;
    this.executed = new AtomicBoolean();
    this.expectMoreExchanges = true;
  }
  
  private final <E extends IOException> E callDone(E paramE)
  {
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      paramE = new StringBuilder().append("Thread ");
      localObject1 = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localObject1, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(((Thread)localObject1).getName() + " MUST NOT hold lock on " + this));
    }
    Object localObject1 = this.connection;
    Object localObject2;
    if (localObject1 != null)
    {
      if ((Util.assertionsEnabled) && (Thread.holdsLock(localObject1)))
      {
        paramE = new StringBuilder().append("Thread ");
        localObject2 = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(localObject2, "Thread.currentThread()");
        throw ((Throwable)new AssertionError(((Thread)localObject2).getName() + " MUST NOT hold lock on " + localObject1));
      }
      try
      {
        localObject2 = releaseConnectionNoEvents$okhttp();
        if (this.connection == null)
        {
          if (localObject2 != null) {
            Util.closeQuietly((Socket)localObject2);
          }
          this.eventListener.connectionReleased((Call)this, (Connection)localObject1);
        }
        else
        {
          int i;
          if (localObject2 == null) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw ((Throwable)new IllegalStateException("Check failed.".toString()));
          }
        }
      }
      finally {}
    }
    localObject1 = timeoutExit(paramE);
    if (paramE != null)
    {
      localObject2 = this.eventListener;
      paramE = (Call)this;
      Intrinsics.checkNotNull(localObject1);
      ((EventListener)localObject2).callFailed(paramE, (IOException)localObject1);
    }
    else
    {
      this.eventListener.callEnd((Call)this);
    }
    return (E)localObject1;
  }
  
  private final void callStart()
  {
    this.callStackTrace = Platform.Companion.get().getStackTraceForCloseable("response.body().close()");
    this.eventListener.callStart((Call)this);
  }
  
  private final Address createAddress(HttpUrl paramHttpUrl)
  {
    SSLSocketFactory localSSLSocketFactory = (SSLSocketFactory)null;
    HostnameVerifier localHostnameVerifier = (HostnameVerifier)null;
    CertificatePinner localCertificatePinner = (CertificatePinner)null;
    if (paramHttpUrl.isHttps())
    {
      localSSLSocketFactory = this.client.sslSocketFactory();
      localHostnameVerifier = this.client.hostnameVerifier();
      localCertificatePinner = this.client.certificatePinner();
    }
    String str = paramHttpUrl.host();
    int i = paramHttpUrl.port();
    paramHttpUrl = this.client.dns();
    SocketFactory localSocketFactory = this.client.socketFactory();
    return new Address(str, i, paramHttpUrl, localSocketFactory, localSSLSocketFactory, localHostnameVerifier, localCertificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
  }
  
  private final <E extends IOException> E timeoutExit(E paramE)
  {
    if (this.timeoutEarlyExit) {
      return paramE;
    }
    if (!this.timeout.exit()) {
      return paramE;
    }
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    if (paramE != null) {
      localInterruptedIOException.initCause((Throwable)paramE);
    }
    return (IOException)localInterruptedIOException;
  }
  
  private final String toLoggableString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (isCanceled()) {
      str = "canceled ";
    } else {
      str = "";
    }
    localStringBuilder = localStringBuilder.append(str);
    if (this.forWebSocket) {
      str = "web socket";
    } else {
      str = "call";
    }
    return str + " to " + redactedUrl$okhttp();
  }
  
  public final void acquireConnectionNoEvents(RealConnection paramRealConnection)
  {
    Intrinsics.checkNotNullParameter(paramRealConnection, "connection");
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(paramRealConnection)))
    {
      StringBuilder localStringBuilder = new StringBuilder().append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(localThread.getName() + " MUST hold lock on " + paramRealConnection));
    }
    int i;
    if (this.connection == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.connection = paramRealConnection;
      paramRealConnection.getCalls().add(new CallReference(this, this.callStackTrace));
      return;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  public void cancel()
  {
    if (this.canceled) {
      return;
    }
    this.canceled = true;
    Object localObject = this.exchange;
    if (localObject != null) {
      ((Exchange)localObject).cancel();
    }
    localObject = this.connectionToCancel;
    if (localObject != null) {
      ((RealConnection)localObject).cancel();
    }
    this.eventListener.canceled((Call)this);
  }
  
  public RealCall clone()
  {
    return new RealCall(this.client, this.originalRequest, this.forWebSocket);
  }
  
  public void enqueue(Callback paramCallback)
  {
    Intrinsics.checkNotNullParameter(paramCallback, "responseCallback");
    if (this.executed.compareAndSet(false, true))
    {
      callStart();
      this.client.dispatcher().enqueue$okhttp(new AsyncCall(paramCallback));
      return;
    }
    throw ((Throwable)new IllegalStateException("Already Executed".toString()));
  }
  
  public final void enterNetworkInterceptorExchange(Request paramRequest, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    int i;
    if (this.interceptorScopedExchange == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      try
      {
        if ((this.responseBodyOpen ^ true))
        {
          if ((true ^ this.requestBodyOpen))
          {
            Object localObject = Unit.INSTANCE;
            if (paramBoolean)
            {
              localObject = this.connectionPool;
              paramRequest = createAddress(paramRequest.url());
              this.exchangeFinder = new ExchangeFinder((RealConnectionPool)localObject, paramRequest, this, this.eventListener);
            }
            return;
          }
          paramRequest = new java/lang/IllegalStateException;
          paramRequest.<init>("Check failed.".toString());
          throw ((Throwable)paramRequest);
        }
        paramRequest = new java/lang/IllegalStateException;
        paramRequest.<init>("cannot make a new request because the previous response is still open: please call response.close()".toString());
        throw ((Throwable)paramRequest);
      }
      finally {}
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  public Response execute()
  {
    if (this.executed.compareAndSet(false, true))
    {
      this.timeout.enter();
      callStart();
      try
      {
        this.client.dispatcher().executed$okhttp(this);
        Response localResponse = getResponseWithInterceptorChain$okhttp();
        return localResponse;
      }
      finally
      {
        this.client.dispatcher().finished$okhttp(this);
      }
    }
    throw ((Throwable)new IllegalStateException("Already Executed".toString()));
  }
  
  public final void exitNetworkInterceptorExchange$okhttp(boolean paramBoolean)
  {
    try
    {
      if (this.expectMoreExchanges)
      {
        localObject1 = Unit.INSTANCE;
        if (paramBoolean)
        {
          localObject1 = this.exchange;
          if (localObject1 != null) {
            ((Exchange)localObject1).detachWithViolence();
          }
        }
        localObject1 = (Exchange)null;
        this.interceptorScopedExchange = null;
        return;
      }
      Object localObject1 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject1).<init>("released".toString());
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  public final OkHttpClient getClient()
  {
    return this.client;
  }
  
  public final RealConnection getConnection()
  {
    return this.connection;
  }
  
  public final RealConnection getConnectionToCancel()
  {
    return this.connectionToCancel;
  }
  
  public final EventListener getEventListener$okhttp()
  {
    return this.eventListener;
  }
  
  public final boolean getForWebSocket()
  {
    return this.forWebSocket;
  }
  
  public final Exchange getInterceptorScopedExchange$okhttp()
  {
    return this.interceptorScopedExchange;
  }
  
  public final Request getOriginalRequest()
  {
    return this.originalRequest;
  }
  
  /* Error */
  public final Response getResponseWithInterceptorChain$okhttp()
    throws IOException
  {
    // Byte code:
    //   0: new 508	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 509	java/util/ArrayList:<init>	()V
    //   7: checkcast 418	java/util/List
    //   10: astore 4
    //   12: aload 4
    //   14: checkcast 511	java/util/Collection
    //   17: aload_0
    //   18: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   21: invokevirtual 514	okhttp3/OkHttpClient:interceptors	()Ljava/util/List;
    //   24: checkcast 516	java/lang/Iterable
    //   27: invokestatic 522	kotlin/collections/CollectionsKt:addAll	(Ljava/util/Collection;Ljava/lang/Iterable;)Z
    //   30: pop
    //   31: aload 4
    //   33: checkcast 511	java/util/Collection
    //   36: new 524	okhttp3/internal/http/RetryAndFollowUpInterceptor
    //   39: dup
    //   40: aload_0
    //   41: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   44: invokespecial 527	okhttp3/internal/http/RetryAndFollowUpInterceptor:<init>	(Lokhttp3/OkHttpClient;)V
    //   47: invokeinterface 528 2 0
    //   52: pop
    //   53: aload 4
    //   55: checkcast 511	java/util/Collection
    //   58: new 530	okhttp3/internal/http/BridgeInterceptor
    //   61: dup
    //   62: aload_0
    //   63: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   66: invokevirtual 534	okhttp3/OkHttpClient:cookieJar	()Lokhttp3/CookieJar;
    //   69: invokespecial 537	okhttp3/internal/http/BridgeInterceptor:<init>	(Lokhttp3/CookieJar;)V
    //   72: invokeinterface 528 2 0
    //   77: pop
    //   78: aload 4
    //   80: checkcast 511	java/util/Collection
    //   83: new 539	okhttp3/internal/cache/CacheInterceptor
    //   86: dup
    //   87: aload_0
    //   88: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   91: invokevirtual 543	okhttp3/OkHttpClient:cache	()Lokhttp3/Cache;
    //   94: invokespecial 546	okhttp3/internal/cache/CacheInterceptor:<init>	(Lokhttp3/Cache;)V
    //   97: invokeinterface 528 2 0
    //   102: pop
    //   103: aload 4
    //   105: checkcast 511	java/util/Collection
    //   108: getstatic 551	okhttp3/internal/connection/ConnectInterceptor:INSTANCE	Lokhttp3/internal/connection/ConnectInterceptor;
    //   111: invokeinterface 528 2 0
    //   116: pop
    //   117: aload_0
    //   118: getfield 142	okhttp3/internal/connection/RealCall:forWebSocket	Z
    //   121: ifne +22 -> 143
    //   124: aload 4
    //   126: checkcast 511	java/util/Collection
    //   129: aload_0
    //   130: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   133: invokevirtual 554	okhttp3/OkHttpClient:networkInterceptors	()Ljava/util/List;
    //   136: checkcast 516	java/lang/Iterable
    //   139: invokestatic 522	kotlin/collections/CollectionsKt:addAll	(Ljava/util/Collection;Ljava/lang/Iterable;)Z
    //   142: pop
    //   143: aload 4
    //   145: checkcast 511	java/util/Collection
    //   148: new 556	okhttp3/internal/http/CallServerInterceptor
    //   151: dup
    //   152: aload_0
    //   153: getfield 142	okhttp3/internal/connection/RealCall:forWebSocket	Z
    //   156: invokespecial 558	okhttp3/internal/http/CallServerInterceptor:<init>	(Z)V
    //   159: invokeinterface 528 2 0
    //   164: pop
    //   165: new 560	okhttp3/internal/http/RealInterceptorChain
    //   168: dup
    //   169: aload_0
    //   170: aload 4
    //   172: iconst_0
    //   173: aconst_null
    //   174: aload_0
    //   175: getfield 140	okhttp3/internal/connection/RealCall:originalRequest	Lokhttp3/Request;
    //   178: aload_0
    //   179: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   182: invokevirtual 563	okhttp3/OkHttpClient:connectTimeoutMillis	()I
    //   185: aload_0
    //   186: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   189: invokevirtual 566	okhttp3/OkHttpClient:readTimeoutMillis	()I
    //   192: aload_0
    //   193: getfield 138	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   196: invokevirtual 569	okhttp3/OkHttpClient:writeTimeoutMillis	()I
    //   199: invokespecial 572	okhttp3/internal/http/RealInterceptorChain:<init>	(Lokhttp3/internal/connection/RealCall;Ljava/util/List;ILokhttp3/internal/connection/Exchange;Lokhttp3/Request;III)V
    //   202: astore 4
    //   204: iconst_0
    //   205: istore_2
    //   206: iload_2
    //   207: istore_1
    //   208: aload 4
    //   210: aload_0
    //   211: getfield 140	okhttp3/internal/connection/RealCall:originalRequest	Lokhttp3/Request;
    //   214: invokevirtual 576	okhttp3/internal/http/RealInterceptorChain:proceed	(Lokhttp3/Request;)Lokhttp3/Response;
    //   217: astore 4
    //   219: iload_2
    //   220: istore_1
    //   221: aload_0
    //   222: invokevirtual 394	okhttp3/internal/connection/RealCall:isCanceled	()Z
    //   225: istore_3
    //   226: iload_3
    //   227: ifne +12 -> 239
    //   230: aload_0
    //   231: aconst_null
    //   232: invokevirtual 578	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
    //   235: pop
    //   236: aload 4
    //   238: areturn
    //   239: iload_2
    //   240: istore_1
    //   241: aload 4
    //   243: checkcast 580	java/io/Closeable
    //   246: invokestatic 583	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   249: iload_2
    //   250: istore_1
    //   251: new 392	java/io/IOException
    //   254: astore 4
    //   256: iload_2
    //   257: istore_1
    //   258: aload 4
    //   260: ldc_w 585
    //   263: invokespecial 586	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   266: iload_2
    //   267: istore_1
    //   268: aload 4
    //   270: checkcast 254	java/lang/Throwable
    //   273: athrow
    //   274: astore 4
    //   276: goto +56 -> 332
    //   279: astore 4
    //   281: iconst_1
    //   282: istore_2
    //   283: iload_2
    //   284: istore_1
    //   285: aload_0
    //   286: aload 4
    //   288: invokevirtual 578	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
    //   291: astore 4
    //   293: aload 4
    //   295: ifnonnull +25 -> 320
    //   298: iload_2
    //   299: istore_1
    //   300: new 588	java/lang/NullPointerException
    //   303: astore 4
    //   305: iload_2
    //   306: istore_1
    //   307: aload 4
    //   309: ldc_w 590
    //   312: invokespecial 591	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   315: iload_2
    //   316: istore_1
    //   317: aload 4
    //   319: athrow
    //   320: iload_2
    //   321: istore_1
    //   322: aload 4
    //   324: checkcast 254	java/lang/Throwable
    //   327: astore 4
    //   329: goto -14 -> 315
    //   332: iload_1
    //   333: ifne +9 -> 342
    //   336: aload_0
    //   337: aconst_null
    //   338: invokevirtual 578	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
    //   341: pop
    //   342: aload 4
    //   344: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	345	0	this	RealCall
    //   207	126	1	i	int
    //   205	116	2	j	int
    //   225	2	3	bool	boolean
    //   10	259	4	localObject1	Object
    //   274	1	4	localObject2	Object
    //   279	8	4	localIOException	IOException
    //   291	52	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   208	219	274	finally
    //   221	226	274	finally
    //   241	249	274	finally
    //   251	256	274	finally
    //   258	266	274	finally
    //   268	274	274	finally
    //   285	293	274	finally
    //   300	305	274	finally
    //   307	315	274	finally
    //   317	320	274	finally
    //   322	329	274	finally
    //   208	219	279	java/io/IOException
    //   221	226	279	java/io/IOException
    //   241	249	279	java/io/IOException
    //   251	256	279	java/io/IOException
    //   258	266	279	java/io/IOException
    //   268	274	279	java/io/IOException
  }
  
  public final Exchange initExchange$okhttp(RealInterceptorChain paramRealInterceptorChain)
  {
    Intrinsics.checkNotNullParameter(paramRealInterceptorChain, "chain");
    try
    {
      if (this.expectMoreExchanges)
      {
        if ((this.responseBodyOpen ^ true))
        {
          if ((this.requestBodyOpen ^ true))
          {
            Object localObject = Unit.INSTANCE;
            localObject = this.exchangeFinder;
            Intrinsics.checkNotNull(localObject);
            paramRealInterceptorChain = ((ExchangeFinder)localObject).find(this.client, paramRealInterceptorChain);
            localObject = new Exchange(this, this.eventListener, (ExchangeFinder)localObject, paramRealInterceptorChain);
            this.interceptorScopedExchange = ((Exchange)localObject);
            this.exchange = ((Exchange)localObject);
            try
            {
              this.requestBodyOpen = true;
              this.responseBodyOpen = true;
              paramRealInterceptorChain = Unit.INSTANCE;
              if (!this.canceled) {
                return (Exchange)localObject;
              }
              throw ((Throwable)new IOException("Canceled"));
            }
            finally {}
          }
          paramRealInterceptorChain = new java/lang/IllegalStateException;
          paramRealInterceptorChain.<init>("Check failed.".toString());
          throw ((Throwable)paramRealInterceptorChain);
        }
        paramRealInterceptorChain = new java/lang/IllegalStateException;
        paramRealInterceptorChain.<init>("Check failed.".toString());
        throw ((Throwable)paramRealInterceptorChain);
      }
      paramRealInterceptorChain = new java/lang/IllegalStateException;
      paramRealInterceptorChain.<init>("released".toString());
      throw ((Throwable)paramRealInterceptorChain);
    }
    finally {}
  }
  
  public boolean isCanceled()
  {
    return this.canceled;
  }
  
  public boolean isExecuted()
  {
    return this.executed.get();
  }
  
  public final <E extends IOException> E messageDone$okhttp(Exchange paramExchange, boolean paramBoolean1, boolean paramBoolean2, E paramE)
  {
    Intrinsics.checkNotNullParameter(paramExchange, "exchange");
    boolean bool = Intrinsics.areEqual(paramExchange, this.exchange);
    int k = 1;
    if ((bool ^ true)) {
      return paramE;
    }
    int m = 0;
    int n = 0;
    if (paramBoolean1) {
      try
      {
        if (this.requestBodyOpen) {
          break label83;
        }
      }
      finally
      {
        break label207;
      }
    }
    int j = m;
    int i = n;
    if (paramBoolean2)
    {
      j = m;
      i = n;
      if (this.responseBodyOpen)
      {
        label83:
        if (paramBoolean1) {
          this.requestBodyOpen = false;
        }
        if (paramBoolean2) {
          this.responseBodyOpen = false;
        }
        paramBoolean1 = this.requestBodyOpen;
        if ((!paramBoolean1) && (!this.responseBodyOpen)) {
          i = 1;
        } else {
          i = 0;
        }
        j = i;
        if ((!paramBoolean1) && (!this.responseBodyOpen) && (!this.expectMoreExchanges)) {
          i = k;
        } else {
          i = 0;
        }
      }
    }
    paramExchange = Unit.INSTANCE;
    if (j != 0)
    {
      paramExchange = (Exchange)null;
      this.exchange = null;
      paramExchange = this.connection;
      if (paramExchange != null) {
        paramExchange.incrementSuccessCount$okhttp();
      }
    }
    if (i != 0) {
      return callDone(paramE);
    }
    return paramE;
    label207:
    throw paramExchange;
  }
  
  public final IOException noMoreExchanges$okhttp(IOException paramIOException)
  {
    int i = 0;
    try
    {
      if (this.expectMoreExchanges)
      {
        int j = 0;
        this.expectMoreExchanges = false;
        i = j;
        if (!this.requestBodyOpen)
        {
          i = j;
          if (!this.responseBodyOpen) {
            i = 1;
          }
        }
      }
      Unit localUnit = Unit.INSTANCE;
      if (i != 0) {
        return callDone(paramIOException);
      }
      return paramIOException;
    }
    finally {}
  }
  
  public final String redactedUrl$okhttp()
  {
    return this.originalRequest.url().redact();
  }
  
  public final Socket releaseConnectionNoEvents$okhttp()
  {
    RealConnection localRealConnection = this.connection;
    Intrinsics.checkNotNull(localRealConnection);
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(localRealConnection)))
    {
      localObject2 = new StringBuilder().append("Thread ");
      localObject1 = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localObject1, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(((Thread)localObject1).getName() + " MUST hold lock on " + localRealConnection));
    }
    Object localObject1 = localRealConnection.getCalls();
    int i = 0;
    Object localObject2 = ((List)localObject1).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      if (Intrinsics.areEqual((RealCall)((Reference)((Iterator)localObject2).next()).get(), (RealCall)this)) {
        break label149;
      }
      i++;
    }
    i = -1;
    label149:
    int j;
    if (i != -1) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      ((List)localObject1).remove(i);
      localObject2 = (RealConnection)null;
      this.connection = null;
      if (((List)localObject1).isEmpty())
      {
        localRealConnection.setIdleAtNs$okhttp(System.nanoTime());
        if (this.connectionPool.connectionBecameIdle(localRealConnection)) {
          return localRealConnection.socket();
        }
      }
      return null;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  public Request request()
  {
    return this.originalRequest;
  }
  
  public final boolean retryAfterFailure()
  {
    ExchangeFinder localExchangeFinder = this.exchangeFinder;
    Intrinsics.checkNotNull(localExchangeFinder);
    return localExchangeFinder.retryAfterFailure();
  }
  
  public final void setConnectionToCancel(RealConnection paramRealConnection)
  {
    this.connectionToCancel = paramRealConnection;
  }
  
  public AsyncTimeout timeout()
  {
    return (AsyncTimeout)this.timeout;
  }
  
  public final void timeoutEarlyExit()
  {
    if ((this.timeoutEarlyExit ^ true))
    {
      this.timeoutEarlyExit = true;
      this.timeout.exit();
      return;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\004\b\004\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\016\020\026\032\0020\0272\006\020\030\032\0020\031J\022\020\032\032\0020\0272\n\020\033\032\0060\000R\0020\006J\b\020\034\032\0020\027H\026R\021\020\005\032\0020\0068F¢\006\006\032\004\b\007\020\bR\036\020\013\032\0020\n2\006\020\t\032\0020\n@BX\016¢\006\b\n\000\032\004\b\f\020\rR\021\020\016\032\0020\0178F¢\006\006\032\004\b\020\020\021R\021\020\022\032\0020\0238F¢\006\006\032\004\b\024\020\025R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\035"}, d2={"Lokhttp3/internal/connection/RealCall$AsyncCall;", "Ljava/lang/Runnable;", "responseCallback", "Lokhttp3/Callback;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/Callback;)V", "call", "Lokhttp3/internal/connection/RealCall;", "getCall", "()Lokhttp3/internal/connection/RealCall;", "<set-?>", "Ljava/util/concurrent/atomic/AtomicInteger;", "callsPerHost", "getCallsPerHost", "()Ljava/util/concurrent/atomic/AtomicInteger;", "host", "", "getHost", "()Ljava/lang/String;", "request", "Lokhttp3/Request;", "getRequest", "()Lokhttp3/Request;", "executeOn", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "reuseCallsPerHostFrom", "other", "run", "okhttp"}, k=1, mv={1, 4, 0})
  public final class AsyncCall
    implements Runnable
  {
    private volatile AtomicInteger callsPerHost;
    private final Callback responseCallback;
    
    public AsyncCall()
    {
      this.responseCallback = ((Callback)localObject);
      this.callsPerHost = new AtomicInteger(0);
    }
    
    /* Error */
    public final void executeOn(java.util.concurrent.ExecutorService paramExecutorService)
    {
      // Byte code:
      //   0: aload_1
      //   1: ldc 79
      //   3: invokestatic 59	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_0
      //   7: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   10: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   13: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   16: astore_2
      //   17: getstatic 95	okhttp3/internal/Util:assertionsEnabled	Z
      //   20: ifeq +67 -> 87
      //   23: aload_2
      //   24: invokestatic 101	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
      //   27: ifne +6 -> 33
      //   30: goto +57 -> 87
      //   33: new 103	java/lang/StringBuilder
      //   36: dup
      //   37: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   40: ldc 106
      //   42: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   45: astore_1
      //   46: invokestatic 114	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   49: astore_3
      //   50: aload_3
      //   51: ldc 116
      //   53: invokestatic 119	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
      //   56: new 121	java/lang/AssertionError
      //   59: dup
      //   60: aload_1
      //   61: aload_3
      //   62: invokevirtual 124	java/lang/Thread:getName	()Ljava/lang/String;
      //   65: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   68: ldc 126
      //   70: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   73: aload_2
      //   74: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   77: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   80: invokespecial 135	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
      //   83: checkcast 137	java/lang/Throwable
      //   86: athrow
      //   87: aload_1
      //   88: aload_0
      //   89: checkcast 6	java/lang/Runnable
      //   92: invokeinterface 143 2 0
      //   97: goto +76 -> 173
      //   100: astore_1
      //   101: goto +73 -> 174
      //   104: astore_1
      //   105: new 145	java/io/InterruptedIOException
      //   108: astore_2
      //   109: aload_2
      //   110: ldc -109
      //   112: invokespecial 150	java/io/InterruptedIOException:<init>	(Ljava/lang/String;)V
      //   115: aload_2
      //   116: aload_1
      //   117: checkcast 137	java/lang/Throwable
      //   120: invokevirtual 154	java/io/InterruptedIOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
      //   123: pop
      //   124: aload_0
      //   125: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   128: aload_2
      //   129: checkcast 156	java/io/IOException
      //   132: invokevirtual 160	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
      //   135: pop
      //   136: aload_0
      //   137: getfield 66	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   140: aload_0
      //   141: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   144: checkcast 162	okhttp3/Call
      //   147: aload_2
      //   148: checkcast 156	java/io/IOException
      //   151: invokeinterface 168 3 0
      //   156: aload_0
      //   157: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   160: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   163: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   166: aload_0
      //   167: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   170: goto -73 -> 97
      //   173: return
      //   174: aload_0
      //   175: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   178: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   181: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   184: aload_0
      //   185: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   188: aload_1
      //   189: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	190	0	this	AsyncCall
      //   0	190	1	paramExecutorService	java.util.concurrent.ExecutorService
      //   16	132	2	localObject	Object
      //   49	13	3	localThread	Thread
      // Exception table:
      //   from	to	target	type
      //   87	97	100	finally
      //   105	156	100	finally
      //   87	97	104	java/util/concurrent/RejectedExecutionException
    }
    
    public final RealCall getCall()
    {
      return RealCall.this;
    }
    
    public final AtomicInteger getCallsPerHost()
    {
      return this.callsPerHost;
    }
    
    public final String getHost()
    {
      return RealCall.this.getOriginalRequest().url().host();
    }
    
    public final Request getRequest()
    {
      return RealCall.this.getOriginalRequest();
    }
    
    public final void reuseCallsPerHostFrom(AsyncCall paramAsyncCall)
    {
      Intrinsics.checkNotNullParameter(paramAsyncCall, "other");
      this.callsPerHost = paramAsyncCall.callsPerHost;
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: new 103	java/lang/StringBuilder
      //   3: dup
      //   4: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   7: ldc -66
      //   9: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   12: aload_0
      //   13: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   16: invokevirtual 193	okhttp3/internal/connection/RealCall:redactedUrl$okhttp	()Ljava/lang/String;
      //   19: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   22: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   25: astore_3
      //   26: invokestatic 114	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   29: astore 5
      //   31: aload 5
      //   33: ldc -62
      //   35: invokestatic 119	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
      //   38: aload 5
      //   40: invokevirtual 124	java/lang/Thread:getName	()Ljava/lang/String;
      //   43: astore 4
      //   45: aload 5
      //   47: aload_3
      //   48: invokevirtual 197	java/lang/Thread:setName	(Ljava/lang/String;)V
      //   51: iconst_0
      //   52: istore_2
      //   53: iconst_0
      //   54: istore_1
      //   55: aload_0
      //   56: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   59: invokestatic 201	okhttp3/internal/connection/RealCall:access$getTimeout$p	(Lokhttp3/internal/connection/RealCall;)Lokhttp3/internal/connection/RealCall$timeout$1;
      //   62: invokevirtual 206	okhttp3/internal/connection/RealCall$timeout$1:enter	()V
      //   65: aload_0
      //   66: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   69: invokevirtual 210	okhttp3/internal/connection/RealCall:getResponseWithInterceptorChain$okhttp	()Lokhttp3/Response;
      //   72: astore_3
      //   73: iconst_1
      //   74: istore_2
      //   75: iconst_1
      //   76: istore_1
      //   77: aload_0
      //   78: getfield 66	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   81: aload_0
      //   82: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   85: checkcast 162	okhttp3/Call
      //   88: aload_3
      //   89: invokeinterface 214 3 0
      //   94: aload_0
      //   95: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   98: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   101: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   104: astore_3
      //   105: aload_3
      //   106: aload_0
      //   107: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   110: goto +193 -> 303
      //   113: astore_3
      //   114: aload_0
      //   115: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   118: invokevirtual 217	okhttp3/internal/connection/RealCall:cancel	()V
      //   121: iload_1
      //   122: ifne +64 -> 186
      //   125: new 156	java/io/IOException
      //   128: astore 6
      //   130: new 103	java/lang/StringBuilder
      //   133: astore 7
      //   135: aload 7
      //   137: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   140: aload 6
      //   142: aload 7
      //   144: ldc -37
      //   146: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   149: aload_3
      //   150: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   153: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   156: invokespecial 220	java/io/IOException:<init>	(Ljava/lang/String;)V
      //   159: aload 6
      //   161: checkcast 137	java/lang/Throwable
      //   164: aload_3
      //   165: invokestatic 226	kotlin/ExceptionsKt:addSuppressed	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
      //   168: aload_0
      //   169: getfield 66	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   172: aload_0
      //   173: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   176: checkcast 162	okhttp3/Call
      //   179: aload 6
      //   181: invokeinterface 168 3 0
      //   186: aload_3
      //   187: athrow
      //   188: astore_3
      //   189: goto +122 -> 311
      //   192: astore 6
      //   194: iload_2
      //   195: ifeq +76 -> 271
      //   198: getstatic 232	okhttp3/internal/platform/Platform:Companion	Lokhttp3/internal/platform/Platform$Companion;
      //   201: invokevirtual 238	okhttp3/internal/platform/Platform$Companion:get	()Lokhttp3/internal/platform/Platform;
      //   204: astore_3
      //   205: new 103	java/lang/StringBuilder
      //   208: astore 7
      //   210: aload 7
      //   212: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   215: aload 7
      //   217: ldc -16
      //   219: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   222: astore 7
      //   224: aload_0
      //   225: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   228: invokestatic 244	okhttp3/internal/connection/RealCall:access$toLoggableString	(Lokhttp3/internal/connection/RealCall;)Ljava/lang/String;
      //   231: astore 8
      //   233: aload 8
      //   235: invokestatic 249	mt/Log5ECF72:a	(Ljava/lang/Object;)V
      //   238: aload 8
      //   240: invokestatic 252	mt/LogE84000:a	(Ljava/lang/Object;)V
      //   243: aload 8
      //   245: invokestatic 255	mt/Log229316:a	(Ljava/lang/Object;)V
      //   248: aload_3
      //   249: aload 7
      //   251: aload 8
      //   253: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   256: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   259: iconst_4
      //   260: aload 6
      //   262: checkcast 137	java/lang/Throwable
      //   265: invokevirtual 259	okhttp3/internal/platform/Platform:log	(Ljava/lang/String;ILjava/lang/Throwable;)V
      //   268: goto +21 -> 289
      //   271: aload_0
      //   272: getfield 66	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   275: aload_0
      //   276: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   279: checkcast 162	okhttp3/Call
      //   282: aload 6
      //   284: invokeinterface 168 3 0
      //   289: aload_0
      //   290: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   293: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   296: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   299: astore_3
      //   300: goto -195 -> 105
      //   303: aload 5
      //   305: aload 4
      //   307: invokevirtual 197	java/lang/Thread:setName	(Ljava/lang/String;)V
      //   310: return
      //   311: aload_0
      //   312: getfield 61	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   315: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   318: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   321: aload_0
      //   322: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   325: aload_3
      //   326: athrow
      //   327: astore_3
      //   328: aload 5
      //   330: aload 4
      //   332: invokevirtual 197	java/lang/Thread:setName	(Ljava/lang/String;)V
      //   335: aload_3
      //   336: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	337	0	this	AsyncCall
      //   54	68	1	i	int
      //   52	143	2	j	int
      //   25	81	3	localObject1	Object
      //   113	74	3	localObject2	Object
      //   188	1	3	localObject3	Object
      //   204	122	3	localObject4	Object
      //   327	9	3	localObject5	Object
      //   43	288	4	str1	String
      //   29	300	5	localThread	Thread
      //   128	52	6	localIOException1	IOException
      //   192	91	6	localIOException2	IOException
      //   133	117	7	localStringBuilder	StringBuilder
      //   231	21	8	str2	String
      // Exception table:
      //   from	to	target	type
      //   65	73	113	finally
      //   77	94	113	finally
      //   114	121	188	finally
      //   125	186	188	finally
      //   186	188	188	finally
      //   198	233	188	finally
      //   248	268	188	finally
      //   271	289	188	finally
      //   65	73	192	java/io/IOException
      //   77	94	192	java/io/IOException
      //   55	65	327	finally
      //   94	105	327	finally
      //   105	110	327	finally
      //   289	300	327	finally
      //   311	327	327	finally
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\004\b\000\030\0002\b\022\004\022\0020\0020\001B\027\022\006\020\003\032\0020\002\022\b\020\004\032\004\030\0010\005¢\006\002\020\006R\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\007\020\b¨\006\t"}, d2={"Lokhttp3/internal/connection/RealCall$CallReference;", "Ljava/lang/ref/WeakReference;", "Lokhttp3/internal/connection/RealCall;", "referent", "callStackTrace", "", "(Lokhttp3/internal/connection/RealCall;Ljava/lang/Object;)V", "getCallStackTrace", "()Ljava/lang/Object;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class CallReference
    extends WeakReference<RealCall>
  {
    private final Object callStackTrace;
    
    public CallReference(RealCall paramRealCall, Object paramObject)
    {
      super();
      this.callStackTrace = paramObject;
    }
    
    public final Object getCallStackTrace()
    {
      return this.callStackTrace;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/connection/RealCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */