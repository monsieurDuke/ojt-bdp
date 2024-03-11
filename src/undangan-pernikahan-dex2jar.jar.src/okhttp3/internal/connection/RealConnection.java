package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.CertificatePinner.Companion;
import okhttp3.CipherSuite;
import okhttp3.Connection;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Handshake.Companion;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Protocol.Companion;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.Route;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http1.Http1ExchangeCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Connection.Builder;
import okhttp3.internal.http2.Http2Connection.Companion;
import okhttp3.internal.http2.Http2Connection.Listener;
import okhttp3.internal.http2.Http2ExchangeCodec;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.http2.Settings;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket.Streams;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000ì\001\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020!\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\005\n\002\020\013\n\002\b\b\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\020 \n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\016\n\002\b\005\030\000 {2\0020\0012\0020\002:\001{B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006¢\006\002\020\007J\006\0205\032\00206J\030\0207\032\0020\0352\006\0208\032\002092\006\020\022\032\0020\023H\002J>\020:\032\002062\006\020;\032\0020\t2\006\020<\032\0020\t2\006\020=\032\0020\t2\006\020>\032\0020\t2\006\020?\032\0020\0352\006\020@\032\0020A2\006\020B\032\0020CJ%\020D\032\002062\006\020E\032\0020F2\006\020G\032\0020\0062\006\020H\032\0020IH\000¢\006\002\bJJ(\020K\032\002062\006\020;\032\0020\t2\006\020<\032\0020\t2\006\020@\032\0020A2\006\020B\032\0020CH\002J\020\020L\032\002062\006\020M\032\0020NH\002J0\020O\032\002062\006\020;\032\0020\t2\006\020<\032\0020\t2\006\020=\032\0020\t2\006\020@\032\0020A2\006\020B\032\0020CH\002J*\020P\032\004\030\0010Q2\006\020<\032\0020\t2\006\020=\032\0020\t2\006\020R\032\0020Q2\006\0208\032\00209H\002J\b\020S\032\0020QH\002J(\020T\032\002062\006\020M\032\0020N2\006\020>\032\0020\t2\006\020@\032\0020A2\006\020B\032\0020CH\002J\n\020\022\032\004\030\0010\023H\026J\r\020U\032\00206H\000¢\006\002\bVJ%\020W\032\0020\0352\006\020X\032\0020Y2\016\020Z\032\n\022\004\022\0020\006\030\0010[H\000¢\006\002\b\\J\016\020]\032\0020\0352\006\020^\032\0020\035J\035\020_\032\0020`2\006\020E\032\0020F2\006\020a\032\0020bH\000¢\006\002\bcJ\025\020d\032\0020e2\006\020f\032\0020gH\000¢\006\002\bhJ\r\020 \032\00206H\000¢\006\002\biJ\r\020!\032\00206H\000¢\006\002\bjJ\030\020k\032\002062\006\020l\032\0020\0252\006\020m\032\0020nH\026J\020\020o\032\002062\006\020p\032\0020qH\026J\b\020%\032\0020&H\026J\b\020\005\032\0020\006H\026J\026\020r\032\0020\0352\f\020s\032\b\022\004\022\0020\0060[H\002J\b\0201\032\0020(H\026J\020\020t\032\002062\006\020>\032\0020\tH\002J\020\020u\032\0020\0352\006\0208\032\00209H\002J\b\020v\032\0020wH\026J\037\020x\032\002062\006\020@\032\0020\r2\b\020y\032\004\030\0010IH\000¢\006\002\bzR\016\020\b\032\0020\tX\016¢\006\002\n\000R\035\020\n\032\016\022\n\022\b\022\004\022\0020\r0\f0\013¢\006\b\n\000\032\004\b\016\020\017R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\020\020\021R\020\020\022\032\004\030\0010\023X\016¢\006\002\n\000R\020\020\024\032\004\030\0010\025X\016¢\006\002\n\000R\032\020\026\032\0020\027X\016¢\006\016\n\000\032\004\b\030\020\031\"\004\b\032\020\033R\024\020\034\032\0020\0358@X\004¢\006\006\032\004\b\036\020\037R\016\020 \032\0020\035X\016¢\006\002\n\000R\032\020!\032\0020\035X\016¢\006\016\n\000\032\004\b\"\020\037\"\004\b#\020$R\020\020%\032\004\030\0010&X\016¢\006\002\n\000R\020\020'\032\004\030\0010(X\016¢\006\002\n\000R\016\020)\032\0020\tX\016¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000R\032\020*\032\0020\tX\016¢\006\016\n\000\032\004\b+\020,\"\004\b-\020.R\020\020/\032\004\030\00100X\016¢\006\002\n\000R\020\0201\032\004\030\0010(X\016¢\006\002\n\000R\020\0202\032\004\030\00103X\016¢\006\002\n\000R\016\0204\032\0020\tX\016¢\006\002\n\000¨\006|"}, d2={"Lokhttp3/internal/connection/RealConnection;", "Lokhttp3/internal/http2/Http2Connection$Listener;", "Lokhttp3/Connection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Route;)V", "allocationLimit", "", "calls", "", "Ljava/lang/ref/Reference;", "Lokhttp3/internal/connection/RealCall;", "getCalls", "()Ljava/util/List;", "getConnectionPool", "()Lokhttp3/internal/connection/RealConnectionPool;", "handshake", "Lokhttp3/Handshake;", "http2Connection", "Lokhttp3/internal/http2/Http2Connection;", "idleAtNs", "", "getIdleAtNs$okhttp", "()J", "setIdleAtNs$okhttp", "(J)V", "isMultiplexed", "", "isMultiplexed$okhttp", "()Z", "noCoalescedConnections", "noNewExchanges", "getNoNewExchanges", "setNoNewExchanges", "(Z)V", "protocol", "Lokhttp3/Protocol;", "rawSocket", "Ljava/net/Socket;", "refusedStreamCount", "routeFailureCount", "getRouteFailureCount$okhttp", "()I", "setRouteFailureCount$okhttp", "(I)V", "sink", "Lokio/BufferedSink;", "socket", "source", "Lokio/BufferedSource;", "successCount", "cancel", "", "certificateSupportHost", "url", "Lokhttp3/HttpUrl;", "connect", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "call", "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "connectFailed", "client", "Lokhttp3/OkHttpClient;", "failedRoute", "failure", "Ljava/io/IOException;", "connectFailed$okhttp", "connectSocket", "connectTls", "connectionSpecSelector", "Lokhttp3/internal/connection/ConnectionSpecSelector;", "connectTunnel", "createTunnel", "Lokhttp3/Request;", "tunnelRequest", "createTunnelRequest", "establishProtocol", "incrementSuccessCount", "incrementSuccessCount$okhttp", "isEligible", "address", "Lokhttp3/Address;", "routes", "", "isEligible$okhttp", "isHealthy", "doExtensiveChecks", "newCodec", "Lokhttp3/internal/http/ExchangeCodec;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "newCodec$okhttp", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "exchange", "Lokhttp3/internal/connection/Exchange;", "newWebSocketStreams$okhttp", "noCoalescedConnections$okhttp", "noNewExchanges$okhttp", "onSettings", "connection", "settings", "Lokhttp3/internal/http2/Settings;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "routeMatchesAny", "candidates", "startHttp2", "supportsUrl", "toString", "", "trackFailure", "e", "trackFailure$okhttp", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class RealConnection
  extends Http2Connection.Listener
  implements Connection
{
  public static final Companion Companion = new Companion(null);
  public static final long IDLE_CONNECTION_HEALTHY_NS = 10000000000L;
  private static final int MAX_TUNNEL_ATTEMPTS = 21;
  private static final String NPE_THROW_WITH_NULL = "throw with null exception";
  private int allocationLimit;
  private final List<Reference<RealCall>> calls;
  private final RealConnectionPool connectionPool;
  private Handshake handshake;
  private Http2Connection http2Connection;
  private long idleAtNs;
  private boolean noCoalescedConnections;
  private boolean noNewExchanges;
  private Protocol protocol;
  private Socket rawSocket;
  private int refusedStreamCount;
  private final Route route;
  private int routeFailureCount;
  private BufferedSink sink;
  private Socket socket;
  private BufferedSource source;
  private int successCount;
  
  public RealConnection(RealConnectionPool paramRealConnectionPool, Route paramRoute)
  {
    this.connectionPool = paramRealConnectionPool;
    this.route = paramRoute;
    this.allocationLimit = 1;
    this.calls = ((List)new ArrayList());
    this.idleAtNs = Long.MAX_VALUE;
  }
  
  private final boolean certificateSupportHost(HttpUrl paramHttpUrl, Handshake paramHandshake)
  {
    Object localObject = paramHandshake.peerCertificates();
    boolean bool2 = ((Collection)localObject).isEmpty();
    boolean bool1 = true;
    if ((bool2 ^ true))
    {
      paramHandshake = OkHostnameVerifier.INSTANCE;
      paramHttpUrl = paramHttpUrl.host();
      localObject = ((List)localObject).get(0);
      if (localObject != null)
      {
        if (paramHandshake.verify(paramHttpUrl, (X509Certificate)localObject)) {
          return bool1;
        }
      }
      else {
        throw new NullPointerException("null cannot be cast to non-null type java.security.cert.X509Certificate");
      }
    }
    bool1 = false;
    return bool1;
  }
  
  private final void connectSocket(int paramInt1, int paramInt2, Call paramCall, EventListener paramEventListener)
    throws IOException
  {
    Proxy localProxy = this.route.proxy();
    Object localObject = this.route.address();
    Proxy.Type localType = localProxy.type();
    if (localType != null) {}
    switch (RealConnection.WhenMappings.$EnumSwitchMapping$0[localType.ordinal()])
    {
    default: 
      break;
    case 1: 
    case 2: 
      localObject = ((Address)localObject).socketFactory().createSocket();
      Intrinsics.checkNotNull(localObject);
      break;
    }
    localObject = new Socket(localProxy);
    this.rawSocket = ((Socket)localObject);
    paramEventListener.connectStart(paramCall, this.route.socketAddress(), localProxy);
    ((Socket)localObject).setSoTimeout(paramInt2);
    try
    {
      Platform.Companion.get().connectSocket((Socket)localObject, this.route.socketAddress(), paramInt1);
      try
      {
        this.source = Okio.buffer(Okio.source((Socket)localObject));
        this.sink = Okio.buffer(Okio.sink((Socket)localObject));
      }
      catch (NullPointerException paramCall)
      {
        if (Intrinsics.areEqual(paramCall.getMessage(), "throw with null exception")) {
          break label183;
        }
      }
      return;
    }
    catch (ConnectException paramEventListener)
    {
      label183:
      paramCall = new ConnectException("Failed to connect to " + this.route.socketAddress());
      paramCall.initCause((Throwable)paramEventListener);
      throw ((Throwable)paramCall);
    }
    throw ((Throwable)new IOException((Throwable)paramCall));
  }
  
  private final void connectTls(ConnectionSpecSelector paramConnectionSpecSelector)
    throws IOException
  {
    Object localObject3 = this.route.address();
    Object localObject1 = ((Address)localObject3).sslSocketFactory();
    Object localObject2 = (SSLSocket)null;
    try
    {
      Intrinsics.checkNotNull(localObject1);
      localObject1 = ((SSLSocketFactory)localObject1).createSocket(this.rawSocket, ((Address)localObject3).url().host(), ((Address)localObject3).url().port(), true);
      if (localObject1 != null)
      {
        localObject1 = (SSLSocket)localObject1;
        localObject2 = localObject1;
        localObject1 = localObject2;
      }
      try
      {
        Object localObject4 = paramConnectionSpecSelector.configureSecureSocket((SSLSocket)localObject2);
        localObject1 = localObject2;
        if (((ConnectionSpec)localObject4).supportsTlsExtensions())
        {
          localObject1 = localObject2;
          Platform.Companion.get().configureTlsExtensions((SSLSocket)localObject2, ((Address)localObject3).url().host(), ((Address)localObject3).protocols());
        }
        localObject1 = localObject2;
        ((SSLSocket)localObject2).startHandshake();
        localObject1 = localObject2;
        paramConnectionSpecSelector = ((SSLSocket)localObject2).getSession();
        localObject1 = localObject2;
        Object localObject5 = Handshake.Companion;
        localObject1 = localObject2;
        Intrinsics.checkNotNullExpressionValue(paramConnectionSpecSelector, "sslSocketSession");
        localObject1 = localObject2;
        Object localObject6 = ((Handshake.Companion)localObject5).get(paramConnectionSpecSelector);
        localObject1 = localObject2;
        localObject5 = ((Address)localObject3).hostnameVerifier();
        localObject1 = localObject2;
        Intrinsics.checkNotNull(localObject5);
        localObject1 = localObject2;
        if (!((HostnameVerifier)localObject5).verify(((Address)localObject3).url().host(), paramConnectionSpecSelector))
        {
          localObject1 = localObject2;
          paramConnectionSpecSelector = ((Handshake)localObject6).peerCertificates();
          localObject1 = localObject2;
          if ((((Collection)paramConnectionSpecSelector).isEmpty() ^ true))
          {
            localObject1 = localObject2;
            paramConnectionSpecSelector = paramConnectionSpecSelector.get(0);
            if (paramConnectionSpecSelector == null)
            {
              localObject1 = localObject2;
              paramConnectionSpecSelector = new java/lang/NullPointerException;
              localObject1 = localObject2;
              paramConnectionSpecSelector.<init>("null cannot be cast to non-null type java.security.cert.X509Certificate");
              throw paramConnectionSpecSelector;
            }
            localObject1 = localObject2;
            localObject4 = (X509Certificate)paramConnectionSpecSelector;
            localObject1 = localObject2;
            paramConnectionSpecSelector = new javax/net/ssl/SSLPeerUnverifiedException;
            localObject1 = localObject2;
            localObject5 = new java/lang/StringBuilder;
            localObject1 = localObject2;
            ((StringBuilder)localObject5).<init>();
            localObject1 = localObject2;
            localObject3 = ((StringBuilder)localObject5).append("\n              |Hostname ").append(((Address)localObject3).url().host()).append(" not verified:\n              |    certificate: ").append(CertificatePinner.Companion.pin((Certificate)localObject4)).append("\n              |    DN: ");
            localObject1 = localObject2;
            localObject5 = ((X509Certificate)localObject4).getSubjectDN();
            localObject1 = localObject2;
            Intrinsics.checkNotNullExpressionValue(localObject5, "cert.subjectDN");
            localObject1 = localObject2;
            localObject3 = StringsKt.trimMargin$default(((Principal)localObject5).getName() + "\n              |    subjectAltNames: " + OkHostnameVerifier.INSTANCE.allSubjectAltNames((X509Certificate)localObject4) + "\n              ", null, 1, null);
            Log5ECF72.a(localObject3);
            LogE84000.a(localObject3);
            Log229316.a(localObject3);
            localObject1 = localObject2;
            paramConnectionSpecSelector.<init>((String)localObject3);
            throw ((Throwable)paramConnectionSpecSelector);
          }
          localObject1 = localObject2;
          paramConnectionSpecSelector = new javax/net/ssl/SSLPeerUnverifiedException;
          localObject1 = localObject2;
          localObject4 = new java/lang/StringBuilder;
          localObject1 = localObject2;
          ((StringBuilder)localObject4).<init>();
          localObject1 = localObject2;
          paramConnectionSpecSelector.<init>("Hostname " + ((Address)localObject3).url().host() + " not verified (no certificates)");
          throw ((Throwable)paramConnectionSpecSelector);
        }
        paramConnectionSpecSelector = null;
        localObject1 = localObject2;
        localObject5 = ((Address)localObject3).certificatePinner();
        localObject1 = localObject2;
        Intrinsics.checkNotNull(localObject5);
        localObject1 = localObject2;
        Handshake localHandshake = new okhttp3/Handshake;
        localObject1 = localObject2;
        TlsVersion localTlsVersion = ((Handshake)localObject6).tlsVersion();
        localObject1 = localObject2;
        CipherSuite localCipherSuite = ((Handshake)localObject6).cipherSuite();
        localObject1 = localObject2;
        List localList = ((Handshake)localObject6).localCertificates();
        localObject1 = localObject2;
        Lambda local1 = new okhttp3/internal/connection/RealConnection$connectTls$1;
        localObject1 = localObject2;
        local1.<init>((CertificatePinner)localObject5, (Handshake)localObject6, (Address)localObject3);
        localObject1 = localObject2;
        localHandshake.<init>(localTlsVersion, localCipherSuite, localList, (Function0)local1);
        localObject1 = localObject2;
        this.handshake = localHandshake;
        localObject1 = localObject2;
        localObject6 = ((Address)localObject3).url().host();
        localObject1 = localObject2;
        localObject3 = new okhttp3/internal/connection/RealConnection$connectTls$2;
        localObject1 = localObject2;
        ((connectTls.2)localObject3).<init>(this);
        localObject1 = localObject2;
        ((CertificatePinner)localObject5).check$okhttp((String)localObject6, (Function0)localObject3);
        localObject1 = localObject2;
        if (((ConnectionSpec)localObject4).supportsTlsExtensions())
        {
          localObject1 = localObject2;
          paramConnectionSpecSelector = Platform.Companion.get().getSelectedProtocol((SSLSocket)localObject2);
        }
        localObject1 = localObject2;
        this.socket = ((Socket)localObject2);
        localObject1 = localObject2;
        this.source = Okio.buffer(Okio.source((Socket)localObject2));
        localObject1 = localObject2;
        this.sink = Okio.buffer(Okio.sink((Socket)localObject2));
        if (paramConnectionSpecSelector != null)
        {
          localObject1 = localObject2;
          paramConnectionSpecSelector = Protocol.Companion.get(paramConnectionSpecSelector);
        }
        else
        {
          localObject1 = localObject2;
          paramConnectionSpecSelector = Protocol.HTTP_1_1;
        }
        localObject1 = localObject2;
        this.protocol = paramConnectionSpecSelector;
        if (localObject2 != null) {
          Platform.Companion.get().afterHandshake((SSLSocket)localObject2);
        }
        return;
      }
      finally
      {
        break label750;
      }
      localObject1 = localObject2;
      paramConnectionSpecSelector = new java/lang/NullPointerException;
      localObject1 = localObject2;
      paramConnectionSpecSelector.<init>("null cannot be cast to non-null type javax.net.ssl.SSLSocket");
      throw paramConnectionSpecSelector;
    }
    finally
    {
      localObject1 = localObject2;
    }
    label750:
    if (localObject1 != null) {
      Platform.Companion.get().afterHandshake((SSLSocket)localObject1);
    }
    if (localObject1 != null) {
      Util.closeQuietly((Socket)localObject1);
    }
    throw paramConnectionSpecSelector;
  }
  
  private final void connectTunnel(int paramInt1, int paramInt2, int paramInt3, Call paramCall, EventListener paramEventListener)
    throws IOException
  {
    Request localRequest = createTunnelRequest();
    HttpUrl localHttpUrl = localRequest.url();
    for (int i = 0; i < 21; i++)
    {
      connectSocket(paramInt1, paramInt2, paramCall, paramEventListener);
      localRequest = createTunnel(paramInt2, paramInt3, localRequest, localHttpUrl);
      if (localRequest == null) {
        break;
      }
      Object localObject = this.rawSocket;
      if (localObject != null) {
        Util.closeQuietly((Socket)localObject);
      }
      localObject = (Socket)null;
      this.rawSocket = null;
      localObject = (BufferedSink)null;
      this.sink = null;
      localObject = (BufferedSource)null;
      this.source = null;
      paramEventListener.connectEnd(paramCall, this.route.socketAddress(), this.route.proxy(), null);
    }
  }
  
  private final Request createTunnel(int paramInt1, int paramInt2, Request paramRequest, HttpUrl paramHttpUrl)
    throws IOException
  {
    Object localObject = new StringBuilder().append("CONNECT ");
    paramHttpUrl = Util.toHostHeader(paramHttpUrl, true);
    Log5ECF72.a(paramHttpUrl);
    LogE84000.a(paramHttpUrl);
    Log229316.a(paramHttpUrl);
    paramHttpUrl = paramHttpUrl + " HTTP/1.1";
    BufferedSource localBufferedSource;
    BufferedSink localBufferedSink;
    for (;;)
    {
      localBufferedSource = this.source;
      Intrinsics.checkNotNull(localBufferedSource);
      localBufferedSink = this.sink;
      Intrinsics.checkNotNull(localBufferedSink);
      Http1ExchangeCodec localHttp1ExchangeCodec = new Http1ExchangeCodec(null, this, localBufferedSource, localBufferedSink);
      localBufferedSource.timeout().timeout(paramInt1, TimeUnit.MILLISECONDS);
      localBufferedSink.timeout().timeout(paramInt2, TimeUnit.MILLISECONDS);
      localHttp1ExchangeCodec.writeRequest(paramRequest.headers(), paramHttpUrl);
      localHttp1ExchangeCodec.finishRequest();
      localObject = localHttp1ExchangeCodec.readResponseHeaders(false);
      Intrinsics.checkNotNull(localObject);
      localObject = ((Response.Builder)localObject).request(paramRequest).build();
      localHttp1ExchangeCodec.skipConnectBody((Response)localObject);
      switch (((Response)localObject).code())
      {
      default: 
        throw ((Throwable)new IOException("Unexpected response code for CONNECT: " + ((Response)localObject).code()));
      case 407: 
        paramRequest = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)localObject);
        if (paramRequest == null) {
          break label310;
        }
        localObject = Response.header$default((Response)localObject, "Connection", null, 2, null);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        if (StringsKt.equals("close", (String)localObject, true)) {
          return paramRequest;
        }
        break;
      }
    }
    label310:
    throw ((Throwable)new IOException("Failed to authenticate with proxy"));
    if ((localBufferedSource.getBuffer().exhausted()) && (localBufferedSink.getBuffer().exhausted())) {
      return null;
    }
    throw ((Throwable)new IOException("TLS tunnel buffered too many bytes!"));
  }
  
  private final Request createTunnelRequest()
    throws IOException
  {
    Object localObject1 = new Request.Builder().url(this.route.address().url()).method("CONNECT", null);
    Object localObject2 = Util.toHostHeader(this.route.address().url(), true);
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    localObject1 = ((Request.Builder)localObject1).header("Host", (String)localObject2).header("Proxy-Connection", "Keep-Alive").header("User-Agent", "okhttp/4.9.0").build();
    localObject2 = new Response.Builder().request((Request)localObject1).protocol(Protocol.HTTP_1_1).code(407).message("Preemptive Authenticate").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(-1L).header("Proxy-Authenticate", "OkHttp-Preemptive").build();
    localObject2 = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)localObject2);
    if (localObject2 != null) {
      localObject1 = localObject2;
    }
    return (Request)localObject1;
  }
  
  private final void establishProtocol(ConnectionSpecSelector paramConnectionSpecSelector, int paramInt, Call paramCall, EventListener paramEventListener)
    throws IOException
  {
    if (this.route.address().sslSocketFactory() == null)
    {
      if (this.route.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE))
      {
        this.socket = this.rawSocket;
        this.protocol = Protocol.H2_PRIOR_KNOWLEDGE;
        startHttp2(paramInt);
        return;
      }
      this.socket = this.rawSocket;
      this.protocol = Protocol.HTTP_1_1;
      return;
    }
    paramEventListener.secureConnectStart(paramCall);
    connectTls(paramConnectionSpecSelector);
    paramEventListener.secureConnectEnd(paramCall, this.handshake);
    if (this.protocol == Protocol.HTTP_2) {
      startHttp2(paramInt);
    }
  }
  
  private final boolean routeMatchesAny(List<Route> paramList)
  {
    paramList = (Iterable)paramList;
    boolean bool2 = paramList instanceof Collection;
    boolean bool1 = true;
    if ((bool2) && (((Collection)paramList).isEmpty()))
    {
      bool1 = false;
    }
    else
    {
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Route localRoute = (Route)paramList.next();
        int i;
        if ((localRoute.proxy().type() == Proxy.Type.DIRECT) && (this.route.proxy().type() == Proxy.Type.DIRECT) && (Intrinsics.areEqual(this.route.socketAddress(), localRoute.socketAddress()))) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          return bool1;
        }
      }
      bool1 = false;
    }
    return bool1;
  }
  
  private final void startHttp2(int paramInt)
    throws IOException
  {
    Socket localSocket = this.socket;
    Intrinsics.checkNotNull(localSocket);
    Object localObject = this.source;
    Intrinsics.checkNotNull(localObject);
    BufferedSink localBufferedSink = this.sink;
    Intrinsics.checkNotNull(localBufferedSink);
    localSocket.setSoTimeout(0);
    localObject = new Http2Connection.Builder(true, TaskRunner.INSTANCE).socket(localSocket, this.route.address().url().host(), (BufferedSource)localObject, localBufferedSink).listener((Http2Connection.Listener)this).pingIntervalMillis(paramInt).build();
    this.http2Connection = ((Http2Connection)localObject);
    this.allocationLimit = Http2Connection.Companion.getDEFAULT_SETTINGS().getMaxConcurrentStreams();
    Http2Connection.start$default((Http2Connection)localObject, false, null, 3, null);
  }
  
  private final boolean supportsUrl(HttpUrl paramHttpUrl)
  {
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      localObject = new StringBuilder().append("Thread ");
      paramHttpUrl = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramHttpUrl, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramHttpUrl.getName() + " MUST hold lock on " + this));
    }
    Object localObject = this.route.address().url();
    int j = paramHttpUrl.port();
    int i = ((HttpUrl)localObject).port();
    boolean bool2 = false;
    if (j != i) {
      return false;
    }
    if (Intrinsics.areEqual(paramHttpUrl.host(), ((HttpUrl)localObject).host())) {
      return true;
    }
    boolean bool1 = bool2;
    if (!this.noCoalescedConnections)
    {
      localObject = this.handshake;
      bool1 = bool2;
      if (localObject != null)
      {
        Intrinsics.checkNotNull(localObject);
        bool1 = bool2;
        if (certificateSupportHost(paramHttpUrl, (Handshake)localObject)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public final void cancel()
  {
    Socket localSocket = this.rawSocket;
    if (localSocket != null) {
      Util.closeQuietly(localSocket);
    }
  }
  
  /* Error */
  public final void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Call paramCall, EventListener paramEventListener)
  {
    // Byte code:
    //   0: aload 6
    //   2: ldc_w 876
    //   5: invokestatic 175	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload 7
    //   10: ldc_w 877
    //   13: invokestatic 175	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   16: aload_0
    //   17: getfield 554	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   20: ifnonnull +9 -> 29
    //   23: iconst_1
    //   24: istore 8
    //   26: goto +6 -> 32
    //   29: iconst_0
    //   30: istore 8
    //   32: iload 8
    //   34: ifeq +538 -> 572
    //   37: aconst_null
    //   38: checkcast 879	okhttp3/internal/connection/RouteException
    //   41: astore 10
    //   43: aload_0
    //   44: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   47: invokevirtual 261	okhttp3/Route:address	()Lokhttp3/Address;
    //   50: invokevirtual 882	okhttp3/Address:connectionSpecs	()Ljava/util/List;
    //   53: astore 9
    //   55: new 398	okhttp3/internal/connection/ConnectionSpecSelector
    //   58: dup
    //   59: aload 9
    //   61: invokespecial 885	okhttp3/internal/connection/ConnectionSpecSelector:<init>	(Ljava/util/List;)V
    //   64: astore 11
    //   66: aload_0
    //   67: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   70: invokevirtual 261	okhttp3/Route:address	()Lokhttp3/Address;
    //   73: invokevirtual 383	okhttp3/Address:sslSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   76: ifnonnull +120 -> 196
    //   79: aload 9
    //   81: getstatic 889	okhttp3/ConnectionSpec:CLEARTEXT	Lokhttp3/ConnectionSpec;
    //   84: invokeinterface 764 2 0
    //   89: ifeq +83 -> 172
    //   92: aload_0
    //   93: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   96: invokevirtual 261	okhttp3/Route:address	()Lokhttp3/Address;
    //   99: invokevirtual 388	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   102: invokevirtual 230	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   105: astore 9
    //   107: getstatic 319	okhttp3/internal/platform/Platform:Companion	Lokhttp3/internal/platform/Platform$Companion;
    //   110: invokevirtual 324	okhttp3/internal/platform/Platform$Companion:get	()Lokhttp3/internal/platform/Platform;
    //   113: aload 9
    //   115: invokevirtual 893	okhttp3/internal/platform/Platform:isCleartextTrafficPermitted	(Ljava/lang/String;)Z
    //   118: ifeq +6 -> 124
    //   121: goto +96 -> 217
    //   124: new 879	okhttp3/internal/connection/RouteException
    //   127: dup
    //   128: new 895	java/net/UnknownServiceException
    //   131: dup
    //   132: new 360	java/lang/StringBuilder
    //   135: dup
    //   136: invokespecial 361	java/lang/StringBuilder:<init>	()V
    //   139: ldc_w 897
    //   142: invokevirtual 367	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: aload 9
    //   147: invokevirtual 367	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: ldc_w 899
    //   153: invokevirtual 367	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: invokevirtual 372	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: invokespecial 900	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   162: checkcast 250	java/io/IOException
    //   165: invokespecial 903	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   168: checkcast 355	java/lang/Throwable
    //   171: athrow
    //   172: new 879	okhttp3/internal/connection/RouteException
    //   175: dup
    //   176: new 895	java/net/UnknownServiceException
    //   179: dup
    //   180: ldc_w 905
    //   183: invokespecial 900	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   186: checkcast 250	java/io/IOException
    //   189: invokespecial 903	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   192: checkcast 355	java/lang/Throwable
    //   195: athrow
    //   196: aload_0
    //   197: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   200: invokevirtual 261	okhttp3/Route:address	()Lokhttp3/Address;
    //   203: invokevirtual 410	okhttp3/Address:protocols	()Ljava/util/List;
    //   206: getstatic 760	okhttp3/Protocol:H2_PRIOR_KNOWLEDGE	Lokhttp3/Protocol;
    //   209: invokeinterface 764 2 0
    //   214: ifne +334 -> 548
    //   217: aload_0
    //   218: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   221: invokevirtual 908	okhttp3/Route:requiresTunnel	()Z
    //   224: ifeq +31 -> 255
    //   227: aload_0
    //   228: iload_1
    //   229: iload_2
    //   230: iload_3
    //   231: aload 6
    //   233: aload 7
    //   235: invokespecial 910	okhttp3/internal/connection/RealConnection:connectTunnel	(IIILokhttp3/Call;Lokhttp3/EventListener;)V
    //   238: aload_0
    //   239: getfield 301	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   242: astore 9
    //   244: aload 9
    //   246: ifnonnull +6 -> 252
    //   249: goto +53 -> 302
    //   252: goto +13 -> 265
    //   255: aload_0
    //   256: iload_1
    //   257: iload_2
    //   258: aload 6
    //   260: aload 7
    //   262: invokespecial 575	okhttp3/internal/connection/RealConnection:connectSocket	(IILokhttp3/Call;Lokhttp3/EventListener;)V
    //   265: aload_0
    //   266: aload 11
    //   268: iload 4
    //   270: aload 6
    //   272: aload 7
    //   274: invokespecial 912	okhttp3/internal/connection/RealConnection:establishProtocol	(Lokhttp3/internal/connection/ConnectionSpecSelector;ILokhttp3/Call;Lokhttp3/EventListener;)V
    //   277: aload 7
    //   279: aload 6
    //   281: aload_0
    //   282: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   285: invokevirtual 305	okhttp3/Route:socketAddress	()Ljava/net/InetSocketAddress;
    //   288: aload_0
    //   289: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   292: invokevirtual 258	okhttp3/Route:proxy	()Ljava/net/Proxy;
    //   295: aload_0
    //   296: getfield 554	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   299: invokevirtual 586	okhttp3/EventListener:connectEnd	(Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;)V
    //   302: aload_0
    //   303: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   306: invokevirtual 908	okhttp3/Route:requiresTunnel	()Z
    //   309: ifeq +37 -> 346
    //   312: aload_0
    //   313: getfield 301	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   316: ifnull +6 -> 322
    //   319: goto +27 -> 346
    //   322: new 879	okhttp3/internal/connection/RouteException
    //   325: dup
    //   326: new 914	java/net/ProtocolException
    //   329: dup
    //   330: ldc_w 916
    //   333: invokespecial 917	java/net/ProtocolException:<init>	(Ljava/lang/String;)V
    //   336: checkcast 250	java/io/IOException
    //   339: invokespecial 903	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   342: checkcast 355	java/lang/Throwable
    //   345: athrow
    //   346: aload_0
    //   347: invokestatic 922	java/lang/System:nanoTime	()J
    //   350: putfield 195	okhttp3/internal/connection/RealConnection:idleAtNs	J
    //   353: return
    //   354: astore 9
    //   356: goto +10 -> 366
    //   359: astore 9
    //   361: goto +5 -> 366
    //   364: astore 9
    //   366: aload_0
    //   367: getfield 203	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   370: astore 12
    //   372: aload 12
    //   374: ifnull +8 -> 382
    //   377: aload 12
    //   379: invokestatic 566	okhttp3/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   382: aload_0
    //   383: getfield 301	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   386: astore 12
    //   388: aload 12
    //   390: ifnull +8 -> 398
    //   393: aload 12
    //   395: invokestatic 566	okhttp3/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   398: aconst_null
    //   399: checkcast 296	java/net/Socket
    //   402: astore 12
    //   404: aload_0
    //   405: aconst_null
    //   406: putfield 203	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   409: aload_0
    //   410: aconst_null
    //   411: putfield 301	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   414: aconst_null
    //   415: checkcast 582	okio/BufferedSource
    //   418: astore 12
    //   420: aload_0
    //   421: aconst_null
    //   422: putfield 338	okhttp3/internal/connection/RealConnection:source	Lokio/BufferedSource;
    //   425: aconst_null
    //   426: checkcast 580	okio/BufferedSink
    //   429: astore 12
    //   431: aload_0
    //   432: aconst_null
    //   433: putfield 346	okhttp3/internal/connection/RealConnection:sink	Lokio/BufferedSink;
    //   436: aconst_null
    //   437: checkcast 210	okhttp3/Handshake
    //   440: astore 12
    //   442: aload_0
    //   443: aconst_null
    //   444: putfield 199	okhttp3/internal/connection/RealConnection:handshake	Lokhttp3/Handshake;
    //   447: aconst_null
    //   448: checkcast 541	okhttp3/Protocol
    //   451: astore 12
    //   453: aload_0
    //   454: aconst_null
    //   455: putfield 554	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   458: aconst_null
    //   459: checkcast 828	okhttp3/internal/http2/Http2Connection
    //   462: astore 12
    //   464: aload_0
    //   465: aconst_null
    //   466: putfield 826	okhttp3/internal/connection/RealConnection:http2Connection	Lokhttp3/internal/http2/Http2Connection;
    //   469: aload_0
    //   470: iconst_1
    //   471: putfield 184	okhttp3/internal/connection/RealConnection:allocationLimit	I
    //   474: aload 7
    //   476: aload 6
    //   478: aload_0
    //   479: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   482: invokevirtual 305	okhttp3/Route:socketAddress	()Ljava/net/InetSocketAddress;
    //   485: aload_0
    //   486: getfield 182	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   489: invokevirtual 258	okhttp3/Route:proxy	()Ljava/net/Proxy;
    //   492: aconst_null
    //   493: aload 9
    //   495: invokevirtual 925	okhttp3/EventListener:connectFailed	(Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;Ljava/io/IOException;)V
    //   498: aload 10
    //   500: ifnonnull +17 -> 517
    //   503: new 879	okhttp3/internal/connection/RouteException
    //   506: dup
    //   507: aload 9
    //   509: invokespecial 903	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   512: astore 10
    //   514: goto +10 -> 524
    //   517: aload 10
    //   519: aload 9
    //   521: invokevirtual 928	okhttp3/internal/connection/RouteException:addConnectException	(Ljava/io/IOException;)V
    //   524: iload 5
    //   526: ifeq +16 -> 542
    //   529: aload 11
    //   531: aload 9
    //   533: invokevirtual 932	okhttp3/internal/connection/ConnectionSpecSelector:connectionFailed	(Ljava/io/IOException;)Z
    //   536: ifeq +6 -> 542
    //   539: goto -322 -> 217
    //   542: aload 10
    //   544: checkcast 355	java/lang/Throwable
    //   547: athrow
    //   548: new 879	okhttp3/internal/connection/RouteException
    //   551: dup
    //   552: new 895	java/net/UnknownServiceException
    //   555: dup
    //   556: ldc_w 934
    //   559: invokespecial 900	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   562: checkcast 250	java/io/IOException
    //   565: invokespecial 903	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   568: checkcast 355	java/lang/Throwable
    //   571: athrow
    //   572: new 936	java/lang/IllegalStateException
    //   575: dup
    //   576: ldc_w 938
    //   579: invokevirtual 941	java/lang/Object:toString	()Ljava/lang/String;
    //   582: invokespecial 942	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   585: checkcast 355	java/lang/Throwable
    //   588: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	589	0	this	RealConnection
    //   0	589	1	paramInt1	int
    //   0	589	2	paramInt2	int
    //   0	589	3	paramInt3	int
    //   0	589	4	paramInt4	int
    //   0	589	5	paramBoolean	boolean
    //   0	589	6	paramCall	Call
    //   0	589	7	paramEventListener	EventListener
    //   24	9	8	i	int
    //   53	192	9	localObject1	Object
    //   354	1	9	localIOException1	IOException
    //   359	1	9	localIOException2	IOException
    //   364	168	9	localIOException3	IOException
    //   41	502	10	localRouteException	RouteException
    //   64	466	11	localConnectionSpecSelector	ConnectionSpecSelector
    //   370	93	12	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   265	302	354	java/io/IOException
    //   255	265	359	java/io/IOException
    //   217	244	364	java/io/IOException
  }
  
  public final void connectFailed$okhttp(OkHttpClient paramOkHttpClient, Route paramRoute, IOException paramIOException)
  {
    Intrinsics.checkNotNullParameter(paramOkHttpClient, "client");
    Intrinsics.checkNotNullParameter(paramRoute, "failedRoute");
    Intrinsics.checkNotNullParameter(paramIOException, "failure");
    if (paramRoute.proxy().type() != Proxy.Type.DIRECT)
    {
      Address localAddress = paramRoute.address();
      localAddress.proxySelector().connectFailed(localAddress.url().uri(), paramRoute.proxy().address(), paramIOException);
    }
    paramOkHttpClient.getRouteDatabase().failed(paramRoute);
  }
  
  public final List<Reference<RealCall>> getCalls()
  {
    return this.calls;
  }
  
  public final RealConnectionPool getConnectionPool()
  {
    return this.connectionPool;
  }
  
  public final long getIdleAtNs$okhttp()
  {
    return this.idleAtNs;
  }
  
  public final boolean getNoNewExchanges()
  {
    return this.noNewExchanges;
  }
  
  public final int getRouteFailureCount$okhttp()
  {
    return this.routeFailureCount;
  }
  
  public Handshake handshake()
  {
    return this.handshake;
  }
  
  public final void incrementSuccessCount$okhttp()
  {
    try
    {
      this.successCount += 1;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean isEligible$okhttp(Address paramAddress, List<Route> paramList)
  {
    Intrinsics.checkNotNullParameter(paramAddress, "address");
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      paramAddress = new StringBuilder().append("Thread ");
      paramList = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramList, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramList.getName() + " MUST hold lock on " + this));
    }
    if ((this.calls.size() < this.allocationLimit) && (!this.noNewExchanges))
    {
      if (!this.route.address().equalsNonHost$okhttp(paramAddress)) {
        return false;
      }
      if (Intrinsics.areEqual(paramAddress.url().host(), route().address().url().host())) {
        return true;
      }
      if (this.http2Connection == null) {
        return false;
      }
      if ((paramList != null) && (routeMatchesAny(paramList)))
      {
        if (paramAddress.hostnameVerifier() != OkHostnameVerifier.INSTANCE) {
          return false;
        }
        if (!supportsUrl(paramAddress.url())) {
          return false;
        }
        try
        {
          paramList = paramAddress.certificatePinner();
          Intrinsics.checkNotNull(paramList);
          paramAddress = paramAddress.url().host();
          Handshake localHandshake = handshake();
          Intrinsics.checkNotNull(localHandshake);
          paramList.check(paramAddress, localHandshake.peerCertificates());
          return true;
        }
        catch (SSLPeerUnverifiedException paramAddress)
        {
          return false;
        }
      }
      return false;
    }
    return false;
  }
  
  public final boolean isHealthy(boolean paramBoolean)
  {
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      localObject3 = new StringBuilder().append("Thread ");
      localObject1 = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localObject1, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(((Thread)localObject1).getName() + " MUST NOT hold lock on " + this));
    }
    long l2 = System.nanoTime();
    Object localObject4 = this.rawSocket;
    Intrinsics.checkNotNull(localObject4);
    Object localObject3 = this.socket;
    Intrinsics.checkNotNull(localObject3);
    Object localObject1 = this.source;
    Intrinsics.checkNotNull(localObject1);
    if ((!((Socket)localObject4).isClosed()) && (!((Socket)localObject3).isClosed()) && (!((Socket)localObject3).isInputShutdown()) && (!((Socket)localObject3).isOutputShutdown()))
    {
      localObject4 = this.http2Connection;
      if (localObject4 != null) {
        return ((Http2Connection)localObject4).isHealthy(l2);
      }
      try
      {
        long l1 = this.idleAtNs;
        if ((l2 - l1 >= 10000000000L) && (paramBoolean)) {
          return Util.isHealthy((Socket)localObject3, (BufferedSource)localObject1);
        }
        return true;
      }
      finally {}
    }
    return false;
  }
  
  public final boolean isMultiplexed$okhttp()
  {
    boolean bool;
    if (this.http2Connection != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final ExchangeCodec newCodec$okhttp(OkHttpClient paramOkHttpClient, RealInterceptorChain paramRealInterceptorChain)
    throws SocketException
  {
    Intrinsics.checkNotNullParameter(paramOkHttpClient, "client");
    Intrinsics.checkNotNullParameter(paramRealInterceptorChain, "chain");
    Socket localSocket = this.socket;
    Intrinsics.checkNotNull(localSocket);
    BufferedSource localBufferedSource = this.source;
    Intrinsics.checkNotNull(localBufferedSource);
    BufferedSink localBufferedSink = this.sink;
    Intrinsics.checkNotNull(localBufferedSink);
    Http2Connection localHttp2Connection = this.http2Connection;
    if (localHttp2Connection != null)
    {
      paramOkHttpClient = (ExchangeCodec)new Http2ExchangeCodec(paramOkHttpClient, this, paramRealInterceptorChain, localHttp2Connection);
    }
    else
    {
      localSocket.setSoTimeout(paramRealInterceptorChain.readTimeoutMillis());
      localBufferedSource.timeout().timeout(paramRealInterceptorChain.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
      localBufferedSink.timeout().timeout(paramRealInterceptorChain.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
      paramOkHttpClient = (ExchangeCodec)new Http1ExchangeCodec(paramOkHttpClient, this, localBufferedSource, localBufferedSink);
    }
    return paramOkHttpClient;
  }
  
  public final RealWebSocket.Streams newWebSocketStreams$okhttp(Exchange paramExchange)
    throws SocketException
  {
    Intrinsics.checkNotNullParameter(paramExchange, "exchange");
    Socket localSocket = this.socket;
    Intrinsics.checkNotNull(localSocket);
    final BufferedSource localBufferedSource = this.source;
    Intrinsics.checkNotNull(localBufferedSource);
    final BufferedSink localBufferedSink = this.sink;
    Intrinsics.checkNotNull(localBufferedSink);
    localSocket.setSoTimeout(0);
    noNewExchanges$okhttp();
    (RealWebSocket.Streams)new RealWebSocket.Streams(paramExchange, localBufferedSource, localBufferedSink)
    {
      final Exchange $exchange;
      
      public void close()
      {
        this.$exchange.bodyComplete(-1L, true, true, null);
      }
    };
  }
  
  public final void noCoalescedConnections$okhttp()
  {
    try
    {
      this.noCoalescedConnections = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void noNewExchanges$okhttp()
  {
    try
    {
      this.noNewExchanges = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onSettings(Http2Connection paramHttp2Connection, Settings paramSettings)
  {
    try
    {
      Intrinsics.checkNotNullParameter(paramHttp2Connection, "connection");
      Intrinsics.checkNotNullParameter(paramSettings, "settings");
      this.allocationLimit = paramSettings.getMaxConcurrentStreams();
      return;
    }
    finally
    {
      paramHttp2Connection = finally;
      throw paramHttp2Connection;
    }
  }
  
  public void onStream(Http2Stream paramHttp2Stream)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramHttp2Stream, "stream");
    paramHttp2Stream.close(ErrorCode.REFUSED_STREAM, null);
  }
  
  public Protocol protocol()
  {
    Protocol localProtocol = this.protocol;
    Intrinsics.checkNotNull(localProtocol);
    return localProtocol;
  }
  
  public Route route()
  {
    return this.route;
  }
  
  public final void setIdleAtNs$okhttp(long paramLong)
  {
    this.idleAtNs = paramLong;
  }
  
  public final void setNoNewExchanges(boolean paramBoolean)
  {
    this.noNewExchanges = paramBoolean;
  }
  
  public final void setRouteFailureCount$okhttp(int paramInt)
  {
    this.routeFailureCount = paramInt;
  }
  
  public Socket socket()
  {
    Socket localSocket = this.socket;
    Intrinsics.checkNotNull(localSocket);
    return localSocket;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Connection{").append(this.route.address().url().host()).append(':').append(this.route.address().url().port()).append(',').append(" proxy=").append(this.route.proxy()).append(" hostAddress=").append(this.route.socketAddress()).append(" cipherSuite=");
    Object localObject = this.handshake;
    if (localObject != null)
    {
      localObject = ((Handshake)localObject).cipherSuite();
      if (localObject != null) {}
    }
    else
    {
      localObject = "none";
    }
    return localObject + " protocol=" + this.protocol + '}';
  }
  
  public final void trackFailure$okhttp(RealCall paramRealCall, IOException paramIOException)
  {
    try
    {
      Intrinsics.checkNotNullParameter(paramRealCall, "call");
      if ((paramIOException instanceof StreamResetException))
      {
        if (((StreamResetException)paramIOException).errorCode == ErrorCode.REFUSED_STREAM)
        {
          int i = this.refusedStreamCount + 1;
          this.refusedStreamCount = i;
          if (i > 1)
          {
            this.noNewExchanges = true;
            this.routeFailureCount += 1;
          }
        }
        else if ((((StreamResetException)paramIOException).errorCode != ErrorCode.CANCEL) || (!paramRealCall.isCanceled()))
        {
          this.noNewExchanges = true;
          this.routeFailureCount += 1;
        }
      }
      else if ((!isMultiplexed$okhttp()) || ((paramIOException instanceof ConnectionShutdownException)))
      {
        this.noNewExchanges = true;
        if (this.successCount == 0)
        {
          if (paramIOException != null) {
            connectFailed$okhttp(paramRealCall.getClient(), this.route, paramIOException);
          }
          this.routeFailureCount += 1;
        }
      }
      return;
    }
    finally {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\b\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J&\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\004R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\006XT¢\006\002\n\000R\016\020\007\032\0020\bXT¢\006\002\n\000¨\006\022"}, d2={"Lokhttp3/internal/connection/RealConnection$Companion;", "", "()V", "IDLE_CONNECTION_HEALTHY_NS", "", "MAX_TUNNEL_ATTEMPTS", "", "NPE_THROW_WITH_NULL", "", "newTestConnection", "Lokhttp3/internal/connection/RealConnection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "socket", "Ljava/net/Socket;", "idleAtNs", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final RealConnection newTestConnection(RealConnectionPool paramRealConnectionPool, Route paramRoute, Socket paramSocket, long paramLong)
    {
      Intrinsics.checkNotNullParameter(paramRealConnectionPool, "connectionPool");
      Intrinsics.checkNotNullParameter(paramRoute, "route");
      Intrinsics.checkNotNullParameter(paramSocket, "socket");
      paramRealConnectionPool = new RealConnection(paramRealConnectionPool, paramRoute);
      RealConnection.access$setSocket$p(paramRealConnectionPool, paramSocket);
      paramRealConnectionPool.setIdleAtNs$okhttp(paramLong);
      return paramRealConnectionPool;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/connection/RealConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */