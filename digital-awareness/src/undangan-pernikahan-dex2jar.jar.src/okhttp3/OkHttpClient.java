package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okhttp3.internal.proxy.NullProxySelector;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.CertificateChainCleaner.Companion;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;

@Metadata(bv={1, 0, 3}, d1={"\000î\001\n\002\030\002\n\002\020\032\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\023\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\n\n\002\020\002\n\002\b\004\b\026\030\000 y2\0020\0012\0020\0022\0020\003:\002xyB\007\b\026¢\006\002\020\004B\017\b\000\022\006\020\005\032\0020\006¢\006\002\020\007J\r\020\b\032\0020\tH\007¢\006\002\bSJ\017\020\013\032\004\030\0010\fH\007¢\006\002\bTJ\r\020\016\032\0020\017H\007¢\006\002\bUJ\r\020\024\032\0020\025H\007¢\006\002\bVJ\r\020\027\032\0020\017H\007¢\006\002\bWJ\r\020\030\032\0020\031H\007¢\006\002\bXJ\023\020\033\032\b\022\004\022\0020\0350\034H\007¢\006\002\bYJ\r\020\037\032\0020 H\007¢\006\002\bZJ\r\020\"\032\0020#H\007¢\006\002\b[J\r\020%\032\0020&H\007¢\006\002\b\\J\r\020(\032\0020)H\007¢\006\002\b]J\r\020+\032\0020,H\007¢\006\002\b^J\r\020.\032\0020,H\007¢\006\002\b_J\r\020/\032\00200H\007¢\006\002\b`J\023\0202\032\b\022\004\022\002030\034H\007¢\006\002\baJ\023\0207\032\b\022\004\022\002030\034H\007¢\006\002\bbJ\b\020c\032\0020\006H\026J\020\020d\032\0020e2\006\020f\032\0020gH\026J\030\020h\032\0020i2\006\020f\032\0020g2\006\020j\032\0020kH\026J\r\0208\032\0020\017H\007¢\006\002\blJ\023\0209\032\b\022\004\022\0020:0\034H\007¢\006\002\bmJ\017\020;\032\004\030\0010<H\007¢\006\002\bnJ\r\020>\032\0020\tH\007¢\006\002\boJ\r\020?\032\0020@H\007¢\006\002\bpJ\r\020B\032\0020\017H\007¢\006\002\bqJ\r\020C\032\0020,H\007¢\006\002\brJ\r\020H\032\0020IH\007¢\006\002\bsJ\r\020K\032\0020LH\007¢\006\002\btJ\b\020u\032\0020vH\002J\r\020O\032\0020\017H\007¢\006\002\bwR\023\020\b\032\0020\t8G¢\006\b\n\000\032\004\b\b\020\nR\025\020\013\032\004\030\0010\f8G¢\006\b\n\000\032\004\b\013\020\rR\023\020\016\032\0020\0178G¢\006\b\n\000\032\004\b\016\020\020R\025\020\021\032\004\030\0010\0228G¢\006\b\n\000\032\004\b\021\020\023R\023\020\024\032\0020\0258G¢\006\b\n\000\032\004\b\024\020\026R\023\020\027\032\0020\0178G¢\006\b\n\000\032\004\b\027\020\020R\023\020\030\032\0020\0318G¢\006\b\n\000\032\004\b\030\020\032R\031\020\033\032\b\022\004\022\0020\0350\0348G¢\006\b\n\000\032\004\b\033\020\036R\023\020\037\032\0020 8G¢\006\b\n\000\032\004\b\037\020!R\023\020\"\032\0020#8G¢\006\b\n\000\032\004\b\"\020$R\023\020%\032\0020&8G¢\006\b\n\000\032\004\b%\020'R\023\020(\032\0020)8G¢\006\b\n\000\032\004\b(\020*R\023\020+\032\0020,8G¢\006\b\n\000\032\004\b+\020-R\023\020.\032\0020,8G¢\006\b\n\000\032\004\b.\020-R\023\020/\032\002008G¢\006\b\n\000\032\004\b/\0201R\031\0202\032\b\022\004\022\002030\0348G¢\006\b\n\000\032\004\b2\020\036R\023\0204\032\002058G¢\006\b\n\000\032\004\b4\0206R\031\0207\032\b\022\004\022\002030\0348G¢\006\b\n\000\032\004\b7\020\036R\023\0208\032\0020\0178G¢\006\b\n\000\032\004\b8\020\020R\031\0209\032\b\022\004\022\0020:0\0348G¢\006\b\n\000\032\004\b9\020\036R\025\020;\032\004\030\0010<8G¢\006\b\n\000\032\004\b;\020=R\023\020>\032\0020\t8G¢\006\b\n\000\032\004\b>\020\nR\023\020?\032\0020@8G¢\006\b\n\000\032\004\b?\020AR\023\020B\032\0020\0178G¢\006\b\n\000\032\004\bB\020\020R\023\020C\032\0020,8G¢\006\b\n\000\032\004\bC\020-R\021\020D\032\0020E¢\006\b\n\000\032\004\bF\020GR\023\020H\032\0020I8G¢\006\b\n\000\032\004\bH\020JR\021\020K\032\0020L8G¢\006\006\032\004\bK\020MR\020\020N\032\004\030\0010LX\004¢\006\002\n\000R\023\020O\032\0020\0178G¢\006\b\n\000\032\004\bO\020\020R\025\020P\032\004\030\0010Q8G¢\006\b\n\000\032\004\bP\020R¨\006z"}, d2={"Lokhttp3/OkHttpClient;", "", "Lokhttp3/Call$Factory;", "Lokhttp3/WebSocket$Factory;", "()V", "builder", "Lokhttp3/OkHttpClient$Builder;", "(Lokhttp3/OkHttpClient$Builder;)V", "authenticator", "Lokhttp3/Authenticator;", "()Lokhttp3/Authenticator;", "cache", "Lokhttp3/Cache;", "()Lokhttp3/Cache;", "callTimeoutMillis", "", "()I", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "certificatePinner", "Lokhttp3/CertificatePinner;", "()Lokhttp3/CertificatePinner;", "connectTimeoutMillis", "connectionPool", "Lokhttp3/ConnectionPool;", "()Lokhttp3/ConnectionPool;", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "()Ljava/util/List;", "cookieJar", "Lokhttp3/CookieJar;", "()Lokhttp3/CookieJar;", "dispatcher", "Lokhttp3/Dispatcher;", "()Lokhttp3/Dispatcher;", "dns", "Lokhttp3/Dns;", "()Lokhttp3/Dns;", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "()Lokhttp3/EventListener$Factory;", "followRedirects", "", "()Z", "followSslRedirects", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "()Ljavax/net/ssl/HostnameVerifier;", "interceptors", "Lokhttp3/Interceptor;", "minWebSocketMessageToCompress", "", "()J", "networkInterceptors", "pingIntervalMillis", "protocols", "Lokhttp3/Protocol;", "proxy", "Ljava/net/Proxy;", "()Ljava/net/Proxy;", "proxyAuthenticator", "proxySelector", "Ljava/net/ProxySelector;", "()Ljava/net/ProxySelector;", "readTimeoutMillis", "retryOnConnectionFailure", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase", "()Lokhttp3/internal/connection/RouteDatabase;", "socketFactory", "Ljavax/net/SocketFactory;", "()Ljavax/net/SocketFactory;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "()Ljavax/net/ssl/SSLSocketFactory;", "sslSocketFactoryOrNull", "writeTimeoutMillis", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "()Ljavax/net/ssl/X509TrustManager;", "-deprecated_authenticator", "-deprecated_cache", "-deprecated_callTimeoutMillis", "-deprecated_certificatePinner", "-deprecated_connectTimeoutMillis", "-deprecated_connectionPool", "-deprecated_connectionSpecs", "-deprecated_cookieJar", "-deprecated_dispatcher", "-deprecated_dns", "-deprecated_eventListenerFactory", "-deprecated_followRedirects", "-deprecated_followSslRedirects", "-deprecated_hostnameVerifier", "-deprecated_interceptors", "-deprecated_networkInterceptors", "newBuilder", "newCall", "Lokhttp3/Call;", "request", "Lokhttp3/Request;", "newWebSocket", "Lokhttp3/WebSocket;", "listener", "Lokhttp3/WebSocketListener;", "-deprecated_pingIntervalMillis", "-deprecated_protocols", "-deprecated_proxy", "-deprecated_proxyAuthenticator", "-deprecated_proxySelector", "-deprecated_readTimeoutMillis", "-deprecated_retryOnConnectionFailure", "-deprecated_socketFactory", "-deprecated_sslSocketFactory", "verifyClientState", "", "-deprecated_writeTimeoutMillis", "Builder", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public class OkHttpClient
  implements Cloneable, Call.Factory, WebSocket.Factory
{
  public static final Companion Companion = new Companion(null);
  private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS = Util.immutableListOf(new ConnectionSpec[] { ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT });
  private static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableListOf(new Protocol[] { Protocol.HTTP_2, Protocol.HTTP_1_1 });
  private final Authenticator authenticator;
  private final Cache cache;
  private final int callTimeoutMillis;
  private final CertificateChainCleaner certificateChainCleaner;
  private final CertificatePinner certificatePinner;
  private final int connectTimeoutMillis;
  private final ConnectionPool connectionPool;
  private final List<ConnectionSpec> connectionSpecs;
  private final CookieJar cookieJar;
  private final Dispatcher dispatcher;
  private final Dns dns;
  private final EventListener.Factory eventListenerFactory;
  private final boolean followRedirects;
  private final boolean followSslRedirects;
  private final HostnameVerifier hostnameVerifier;
  private final List<Interceptor> interceptors;
  private final long minWebSocketMessageToCompress;
  private final List<Interceptor> networkInterceptors;
  private final int pingIntervalMillis;
  private final List<Protocol> protocols;
  private final Proxy proxy;
  private final Authenticator proxyAuthenticator;
  private final ProxySelector proxySelector;
  private final int readTimeoutMillis;
  private final boolean retryOnConnectionFailure;
  private final RouteDatabase routeDatabase;
  private final SocketFactory socketFactory;
  private final SSLSocketFactory sslSocketFactoryOrNull;
  private final int writeTimeoutMillis;
  private final X509TrustManager x509TrustManager;
  
  public OkHttpClient()
  {
    this(new Builder());
  }
  
  public OkHttpClient(Builder paramBuilder)
  {
    this.dispatcher = paramBuilder.getDispatcher$okhttp();
    this.connectionPool = paramBuilder.getConnectionPool$okhttp();
    this.interceptors = Util.toImmutableList(paramBuilder.getInterceptors$okhttp());
    this.networkInterceptors = Util.toImmutableList(paramBuilder.getNetworkInterceptors$okhttp());
    this.eventListenerFactory = paramBuilder.getEventListenerFactory$okhttp();
    this.retryOnConnectionFailure = paramBuilder.getRetryOnConnectionFailure$okhttp();
    this.authenticator = paramBuilder.getAuthenticator$okhttp();
    this.followRedirects = paramBuilder.getFollowRedirects$okhttp();
    this.followSslRedirects = paramBuilder.getFollowSslRedirects$okhttp();
    this.cookieJar = paramBuilder.getCookieJar$okhttp();
    this.cache = paramBuilder.getCache$okhttp();
    this.dns = paramBuilder.getDns$okhttp();
    this.proxy = paramBuilder.getProxy$okhttp();
    if (paramBuilder.getProxy$okhttp() != null)
    {
      localObject1 = (ProxySelector)NullProxySelector.INSTANCE;
    }
    else
    {
      localObject1 = paramBuilder.getProxySelector$okhttp();
      if (localObject1 == null) {
        localObject1 = ProxySelector.getDefault();
      }
      if (localObject1 == null) {
        localObject1 = (ProxySelector)NullProxySelector.INSTANCE;
      }
    }
    this.proxySelector = ((ProxySelector)localObject1);
    this.proxyAuthenticator = paramBuilder.getProxyAuthenticator$okhttp();
    this.socketFactory = paramBuilder.getSocketFactory$okhttp();
    Object localObject2 = paramBuilder.getConnectionSpecs$okhttp();
    this.connectionSpecs = ((List)localObject2);
    this.protocols = paramBuilder.getProtocols$okhttp();
    this.hostnameVerifier = paramBuilder.getHostnameVerifier$okhttp();
    this.callTimeoutMillis = paramBuilder.getCallTimeout$okhttp();
    this.connectTimeoutMillis = paramBuilder.getConnectTimeout$okhttp();
    this.readTimeoutMillis = paramBuilder.getReadTimeout$okhttp();
    this.writeTimeoutMillis = paramBuilder.getWriteTimeout$okhttp();
    this.pingIntervalMillis = paramBuilder.getPingInterval$okhttp();
    this.minWebSocketMessageToCompress = paramBuilder.getMinWebSocketMessageToCompress$okhttp();
    Object localObject1 = paramBuilder.getRouteDatabase$okhttp();
    if (localObject1 == null) {
      localObject1 = new RouteDatabase();
    }
    this.routeDatabase = ((RouteDatabase)localObject1);
    localObject1 = (Iterable)localObject2;
    boolean bool = localObject1 instanceof Collection;
    int i = 1;
    if ((!bool) || (!((Collection)localObject1).isEmpty()))
    {
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        if (((ConnectionSpec)((Iterator)localObject1).next()).isTls()) {
          i = 0;
        }
      }
    }
    if (i != 0)
    {
      paramBuilder = (SSLSocketFactory)null;
      this.sslSocketFactoryOrNull = null;
      paramBuilder = (CertificateChainCleaner)null;
      this.certificateChainCleaner = null;
      paramBuilder = (X509TrustManager)null;
      this.x509TrustManager = null;
      this.certificatePinner = CertificatePinner.DEFAULT;
    }
    else if (paramBuilder.getSslSocketFactoryOrNull$okhttp() != null)
    {
      this.sslSocketFactoryOrNull = paramBuilder.getSslSocketFactoryOrNull$okhttp();
      localObject1 = paramBuilder.getCertificateChainCleaner$okhttp();
      Intrinsics.checkNotNull(localObject1);
      this.certificateChainCleaner = ((CertificateChainCleaner)localObject1);
      localObject2 = paramBuilder.getX509TrustManagerOrNull$okhttp();
      Intrinsics.checkNotNull(localObject2);
      this.x509TrustManager = ((X509TrustManager)localObject2);
      paramBuilder = paramBuilder.getCertificatePinner$okhttp();
      Intrinsics.checkNotNull(localObject1);
      this.certificatePinner = paramBuilder.withCertificateChainCleaner$okhttp((CertificateChainCleaner)localObject1);
    }
    else
    {
      localObject1 = Platform.Companion.get().platformTrustManager();
      this.x509TrustManager = ((X509TrustManager)localObject1);
      localObject2 = Platform.Companion.get();
      Intrinsics.checkNotNull(localObject1);
      this.sslSocketFactoryOrNull = ((Platform)localObject2).newSslSocketFactory((X509TrustManager)localObject1);
      localObject2 = CertificateChainCleaner.Companion;
      Intrinsics.checkNotNull(localObject1);
      localObject1 = ((CertificateChainCleaner.Companion)localObject2).get((X509TrustManager)localObject1);
      this.certificateChainCleaner = ((CertificateChainCleaner)localObject1);
      paramBuilder = paramBuilder.getCertificatePinner$okhttp();
      Intrinsics.checkNotNull(localObject1);
      this.certificatePinner = paramBuilder.withCertificateChainCleaner$okhttp((CertificateChainCleaner)localObject1);
    }
    verifyClientState();
  }
  
  private final void verifyClientState()
  {
    Object localObject = this.interceptors;
    if (localObject != null)
    {
      boolean bool = ((List)localObject).contains(null);
      int j = 1;
      if ((bool ^ true))
      {
        localObject = this.networkInterceptors;
        if (localObject != null)
        {
          if ((((List)localObject).contains(null) ^ true))
          {
            localObject = (Iterable)this.connectionSpecs;
            int i;
            if (((localObject instanceof Collection)) && (((Collection)localObject).isEmpty()))
            {
              i = 1;
            }
            else
            {
              localObject = ((Iterable)localObject).iterator();
              while (((Iterator)localObject).hasNext()) {
                if (((ConnectionSpec)((Iterator)localObject).next()).isTls())
                {
                  i = 0;
                  break label129;
                }
              }
              i = 1;
            }
            label129:
            if (i != 0)
            {
              if (this.sslSocketFactoryOrNull == null) {
                i = 1;
              } else {
                i = 0;
              }
              if (i != 0)
              {
                if (this.certificateChainCleaner == null) {
                  i = 1;
                } else {
                  i = 0;
                }
                if (i != 0)
                {
                  if (this.x509TrustManager == null) {
                    i = j;
                  } else {
                    i = 0;
                  }
                  if (i != 0)
                  {
                    if (!Intrinsics.areEqual(this.certificatePinner, CertificatePinner.DEFAULT)) {
                      throw ((Throwable)new IllegalStateException("Check failed.".toString()));
                    }
                  }
                  else {
                    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
                  }
                }
                else
                {
                  throw ((Throwable)new IllegalStateException("Check failed.".toString()));
                }
              }
              else
              {
                throw ((Throwable)new IllegalStateException("Check failed.".toString()));
              }
            }
            else
            {
              if (this.sslSocketFactoryOrNull == null) {
                break label327;
              }
              if (this.certificateChainCleaner == null) {
                break label310;
              }
              if (this.x509TrustManager == null) {
                break label293;
              }
            }
            return;
            label293:
            throw ((Throwable)new IllegalStateException("x509TrustManager == null".toString()));
            label310:
            throw ((Throwable)new IllegalStateException("certificateChainCleaner == null".toString()));
            label327:
            throw ((Throwable)new IllegalStateException("sslSocketFactory == null".toString()));
          }
          throw ((Throwable)new IllegalStateException(("Null network interceptor: " + this.networkInterceptors).toString()));
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
      }
      throw ((Throwable)new IllegalStateException(("Null interceptor: " + this.interceptors).toString()));
    }
    throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="authenticator", imports={}))
  public final Authenticator -deprecated_authenticator()
  {
    return this.authenticator;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="cache", imports={}))
  public final Cache -deprecated_cache()
  {
    return this.cache;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="callTimeoutMillis", imports={}))
  public final int -deprecated_callTimeoutMillis()
  {
    return this.callTimeoutMillis;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="certificatePinner", imports={}))
  public final CertificatePinner -deprecated_certificatePinner()
  {
    return this.certificatePinner;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="connectTimeoutMillis", imports={}))
  public final int -deprecated_connectTimeoutMillis()
  {
    return this.connectTimeoutMillis;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="connectionPool", imports={}))
  public final ConnectionPool -deprecated_connectionPool()
  {
    return this.connectionPool;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="connectionSpecs", imports={}))
  public final List<ConnectionSpec> -deprecated_connectionSpecs()
  {
    return this.connectionSpecs;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="cookieJar", imports={}))
  public final CookieJar -deprecated_cookieJar()
  {
    return this.cookieJar;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="dispatcher", imports={}))
  public final Dispatcher -deprecated_dispatcher()
  {
    return this.dispatcher;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="dns", imports={}))
  public final Dns -deprecated_dns()
  {
    return this.dns;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="eventListenerFactory", imports={}))
  public final EventListener.Factory -deprecated_eventListenerFactory()
  {
    return this.eventListenerFactory;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="followRedirects", imports={}))
  public final boolean -deprecated_followRedirects()
  {
    return this.followRedirects;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="followSslRedirects", imports={}))
  public final boolean -deprecated_followSslRedirects()
  {
    return this.followSslRedirects;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="hostnameVerifier", imports={}))
  public final HostnameVerifier -deprecated_hostnameVerifier()
  {
    return this.hostnameVerifier;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="interceptors", imports={}))
  public final List<Interceptor> -deprecated_interceptors()
  {
    return this.interceptors;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="networkInterceptors", imports={}))
  public final List<Interceptor> -deprecated_networkInterceptors()
  {
    return this.networkInterceptors;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="pingIntervalMillis", imports={}))
  public final int -deprecated_pingIntervalMillis()
  {
    return this.pingIntervalMillis;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="protocols", imports={}))
  public final List<Protocol> -deprecated_protocols()
  {
    return this.protocols;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="proxy", imports={}))
  public final Proxy -deprecated_proxy()
  {
    return this.proxy;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="proxyAuthenticator", imports={}))
  public final Authenticator -deprecated_proxyAuthenticator()
  {
    return this.proxyAuthenticator;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="proxySelector", imports={}))
  public final ProxySelector -deprecated_proxySelector()
  {
    return this.proxySelector;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="readTimeoutMillis", imports={}))
  public final int -deprecated_readTimeoutMillis()
  {
    return this.readTimeoutMillis;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="retryOnConnectionFailure", imports={}))
  public final boolean -deprecated_retryOnConnectionFailure()
  {
    return this.retryOnConnectionFailure;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="socketFactory", imports={}))
  public final SocketFactory -deprecated_socketFactory()
  {
    return this.socketFactory;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="sslSocketFactory", imports={}))
  public final SSLSocketFactory -deprecated_sslSocketFactory()
  {
    return sslSocketFactory();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="writeTimeoutMillis", imports={}))
  public final int -deprecated_writeTimeoutMillis()
  {
    return this.writeTimeoutMillis;
  }
  
  public final Authenticator authenticator()
  {
    return this.authenticator;
  }
  
  public final Cache cache()
  {
    return this.cache;
  }
  
  public final int callTimeoutMillis()
  {
    return this.callTimeoutMillis;
  }
  
  public final CertificateChainCleaner certificateChainCleaner()
  {
    return this.certificateChainCleaner;
  }
  
  public final CertificatePinner certificatePinner()
  {
    return this.certificatePinner;
  }
  
  public Object clone()
  {
    return super.clone();
  }
  
  public final int connectTimeoutMillis()
  {
    return this.connectTimeoutMillis;
  }
  
  public final ConnectionPool connectionPool()
  {
    return this.connectionPool;
  }
  
  public final List<ConnectionSpec> connectionSpecs()
  {
    return this.connectionSpecs;
  }
  
  public final CookieJar cookieJar()
  {
    return this.cookieJar;
  }
  
  public final Dispatcher dispatcher()
  {
    return this.dispatcher;
  }
  
  public final Dns dns()
  {
    return this.dns;
  }
  
  public final EventListener.Factory eventListenerFactory()
  {
    return this.eventListenerFactory;
  }
  
  public final boolean followRedirects()
  {
    return this.followRedirects;
  }
  
  public final boolean followSslRedirects()
  {
    return this.followSslRedirects;
  }
  
  public final RouteDatabase getRouteDatabase()
  {
    return this.routeDatabase;
  }
  
  public final HostnameVerifier hostnameVerifier()
  {
    return this.hostnameVerifier;
  }
  
  public final List<Interceptor> interceptors()
  {
    return this.interceptors;
  }
  
  public final long minWebSocketMessageToCompress()
  {
    return this.minWebSocketMessageToCompress;
  }
  
  public final List<Interceptor> networkInterceptors()
  {
    return this.networkInterceptors;
  }
  
  public Builder newBuilder()
  {
    return new Builder(this);
  }
  
  public Call newCall(Request paramRequest)
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    return (Call)new RealCall(this, paramRequest, false);
  }
  
  public WebSocket newWebSocket(Request paramRequest, WebSocketListener paramWebSocketListener)
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    Intrinsics.checkNotNullParameter(paramWebSocketListener, "listener");
    TaskRunner localTaskRunner = TaskRunner.INSTANCE;
    Random localRandom = new Random();
    long l = this.pingIntervalMillis;
    paramRequest = new RealWebSocket(localTaskRunner, paramRequest, paramWebSocketListener, localRandom, l, null, this.minWebSocketMessageToCompress);
    paramRequest.connect(this);
    return (WebSocket)paramRequest;
  }
  
  public final int pingIntervalMillis()
  {
    return this.pingIntervalMillis;
  }
  
  public final List<Protocol> protocols()
  {
    return this.protocols;
  }
  
  public final Proxy proxy()
  {
    return this.proxy;
  }
  
  public final Authenticator proxyAuthenticator()
  {
    return this.proxyAuthenticator;
  }
  
  public final ProxySelector proxySelector()
  {
    return this.proxySelector;
  }
  
  public final int readTimeoutMillis()
  {
    return this.readTimeoutMillis;
  }
  
  public final boolean retryOnConnectionFailure()
  {
    return this.retryOnConnectionFailure;
  }
  
  public final SocketFactory socketFactory()
  {
    return this.socketFactory;
  }
  
  public final SSLSocketFactory sslSocketFactory()
  {
    SSLSocketFactory localSSLSocketFactory = this.sslSocketFactoryOrNull;
    if (localSSLSocketFactory != null) {
      return localSSLSocketFactory;
    }
    throw ((Throwable)new IllegalStateException("CLEARTEXT-only client"));
  }
  
  public final int writeTimeoutMillis()
  {
    return this.writeTimeoutMillis;
  }
  
  public final X509TrustManager x509TrustManager()
  {
    return this.x509TrustManager;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000ø\001\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\005\n\002\020 \n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\b\n\002\030\002\n\002\b\005\n\002\020!\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\n\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\013\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\006\030\0002\0020\001B\017\b\020\022\006\020\002\032\0020\003¢\006\002\020\004B\005¢\006\002\020\005J?\020\001\032\0020\0002*\b\004\020\001\032#\022\027\022\0250¡\001¢\006\017\b¢\001\022\n\b£\001\022\005\b\b(¤\001\022\005\022\0030¥\0010 \001H\bø\001\000¢\006\003\b¦\001J\020\020\001\032\0020\0002\007\020§\001\032\0020]J?\020¨\001\032\0020\0002*\b\004\020\001\032#\022\027\022\0250¡\001¢\006\017\b¢\001\022\n\b£\001\022\005\b\b(¤\001\022\005\022\0030¥\0010 \001H\bø\001\000¢\006\003\b©\001J\020\020¨\001\032\0020\0002\007\020§\001\032\0020]J\016\020\006\032\0020\0002\006\020\006\032\0020\007J\007\020ª\001\032\0020\003J\020\020\f\032\0020\0002\b\020\f\032\004\030\0010\rJ\022\020\022\032\0020\0002\b\020«\001\032\0030¬\001H\007J\031\020\022\032\0020\0002\007\020­\001\032\0020`2\b\020®\001\032\0030¯\001J\016\020\036\032\0020\0002\006\020\036\032\0020\037J\022\020$\032\0020\0002\b\020«\001\032\0030¬\001H\007J\031\020$\032\0020\0002\007\020­\001\032\0020`2\b\020®\001\032\0030¯\001J\016\020'\032\0020\0002\006\020'\032\0020(J\024\020-\032\0020\0002\f\020-\032\b\022\004\022\0020/0.J\016\0204\032\0020\0002\006\0204\032\00205J\016\020:\032\0020\0002\006\020:\032\0020;J\016\020@\032\0020\0002\006\020@\032\0020AJ\021\020°\001\032\0020\0002\b\020°\001\032\0030±\001J\016\020F\032\0020\0002\006\020F\032\0020GJ\016\020L\032\0020\0002\006\020L\032\0020MJ\017\020R\032\0020\0002\007\020²\001\032\0020MJ\016\020U\032\0020\0002\006\020U\032\0020VJ\f\020[\032\b\022\004\022\0020]0\\J\017\020_\032\0020\0002\007\020³\001\032\0020`J\f\020e\032\b\022\004\022\0020]0\\J\022\020g\032\0020\0002\b\020«\001\032\0030¬\001H\007J\031\020g\032\0020\0002\007\020´\001\032\0020`2\b\020®\001\032\0030¯\001J\024\020j\032\0020\0002\f\020j\032\b\022\004\022\0020k0.J\020\020n\032\0020\0002\b\020n\032\004\030\0010oJ\016\020t\032\0020\0002\006\020t\032\0020\007J\016\020w\032\0020\0002\006\020w\032\0020xJ\022\020}\032\0020\0002\b\020«\001\032\0030¬\001H\007J\031\020}\032\0020\0002\007\020­\001\032\0020`2\b\020®\001\032\0030¯\001J\020\020\001\032\0020\0002\007\020\001\032\0020MJ\021\020\001\032\0020\0002\b\020\001\032\0030\001J\023\020µ\001\032\0020\0002\b\020µ\001\032\0030\001H\007J\033\020µ\001\032\0020\0002\b\020µ\001\032\0030\0012\b\020¶\001\032\0030\001J\023\020\001\032\0020\0002\b\020«\001\032\0030¬\001H\007J\032\020\001\032\0020\0002\007\020­\001\032\0020`2\b\020®\001\032\0030¯\001R\032\020\006\032\0020\007X\016¢\006\016\n\000\032\004\b\b\020\t\"\004\b\n\020\013R\034\020\f\032\004\030\0010\rX\016¢\006\016\n\000\032\004\b\016\020\017\"\004\b\020\020\021R\032\020\022\032\0020\023X\016¢\006\016\n\000\032\004\b\024\020\025\"\004\b\026\020\027R\034\020\030\032\004\030\0010\031X\016¢\006\016\n\000\032\004\b\032\020\033\"\004\b\034\020\035R\032\020\036\032\0020\037X\016¢\006\016\n\000\032\004\b \020!\"\004\b\"\020#R\032\020$\032\0020\023X\016¢\006\016\n\000\032\004\b%\020\025\"\004\b&\020\027R\032\020'\032\0020(X\016¢\006\016\n\000\032\004\b)\020*\"\004\b+\020,R \020-\032\b\022\004\022\0020/0.X\016¢\006\016\n\000\032\004\b0\0201\"\004\b2\0203R\032\0204\032\00205X\016¢\006\016\n\000\032\004\b6\0207\"\004\b8\0209R\032\020:\032\0020;X\016¢\006\016\n\000\032\004\b<\020=\"\004\b>\020?R\032\020@\032\0020AX\016¢\006\016\n\000\032\004\bB\020C\"\004\bD\020ER\032\020F\032\0020GX\016¢\006\016\n\000\032\004\bH\020I\"\004\bJ\020KR\032\020L\032\0020MX\016¢\006\016\n\000\032\004\bN\020O\"\004\bP\020QR\032\020R\032\0020MX\016¢\006\016\n\000\032\004\bS\020O\"\004\bT\020QR\032\020U\032\0020VX\016¢\006\016\n\000\032\004\bW\020X\"\004\bY\020ZR\032\020[\032\b\022\004\022\0020]0\\X\004¢\006\b\n\000\032\004\b^\0201R\032\020_\032\0020`X\016¢\006\016\n\000\032\004\ba\020b\"\004\bc\020dR\032\020e\032\b\022\004\022\0020]0\\X\004¢\006\b\n\000\032\004\bf\0201R\032\020g\032\0020\023X\016¢\006\016\n\000\032\004\bh\020\025\"\004\bi\020\027R \020j\032\b\022\004\022\0020k0.X\016¢\006\016\n\000\032\004\bl\0201\"\004\bm\0203R\034\020n\032\004\030\0010oX\016¢\006\016\n\000\032\004\bp\020q\"\004\br\020sR\032\020t\032\0020\007X\016¢\006\016\n\000\032\004\bu\020\t\"\004\bv\020\013R\034\020w\032\004\030\0010xX\016¢\006\016\n\000\032\004\by\020z\"\004\b{\020|R\032\020}\032\0020\023X\016¢\006\016\n\000\032\004\b~\020\025\"\004\b\020\027R\035\020\001\032\0020MX\016¢\006\020\n\000\032\005\b\001\020O\"\005\b\001\020QR\"\020\001\032\005\030\0010\001X\016¢\006\022\n\000\032\006\b\001\020\001\"\006\b\001\020\001R \020\001\032\0030\001X\016¢\006\022\n\000\032\006\b\001\020\001\"\006\b\001\020\001R\"\020\001\032\005\030\0010\001X\016¢\006\022\n\000\032\006\b\001\020\001\"\006\b\001\020\001R\035\020\001\032\0020\023X\016¢\006\020\n\000\032\005\b\001\020\025\"\005\b\001\020\027R\"\020\001\032\005\030\0010\001X\016¢\006\022\n\000\032\006\b\001\020\001\"\006\b\001\020\001\002\007\n\005\b20\001¨\006·\001"}, d2={"Lokhttp3/OkHttpClient$Builder;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "()V", "authenticator", "Lokhttp3/Authenticator;", "getAuthenticator$okhttp", "()Lokhttp3/Authenticator;", "setAuthenticator$okhttp", "(Lokhttp3/Authenticator;)V", "cache", "Lokhttp3/Cache;", "getCache$okhttp", "()Lokhttp3/Cache;", "setCache$okhttp", "(Lokhttp3/Cache;)V", "callTimeout", "", "getCallTimeout$okhttp", "()I", "setCallTimeout$okhttp", "(I)V", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "setCertificateChainCleaner$okhttp", "(Lokhttp3/internal/tls/CertificateChainCleaner;)V", "certificatePinner", "Lokhttp3/CertificatePinner;", "getCertificatePinner$okhttp", "()Lokhttp3/CertificatePinner;", "setCertificatePinner$okhttp", "(Lokhttp3/CertificatePinner;)V", "connectTimeout", "getConnectTimeout$okhttp", "setConnectTimeout$okhttp", "connectionPool", "Lokhttp3/ConnectionPool;", "getConnectionPool$okhttp", "()Lokhttp3/ConnectionPool;", "setConnectionPool$okhttp", "(Lokhttp3/ConnectionPool;)V", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "getConnectionSpecs$okhttp", "()Ljava/util/List;", "setConnectionSpecs$okhttp", "(Ljava/util/List;)V", "cookieJar", "Lokhttp3/CookieJar;", "getCookieJar$okhttp", "()Lokhttp3/CookieJar;", "setCookieJar$okhttp", "(Lokhttp3/CookieJar;)V", "dispatcher", "Lokhttp3/Dispatcher;", "getDispatcher$okhttp", "()Lokhttp3/Dispatcher;", "setDispatcher$okhttp", "(Lokhttp3/Dispatcher;)V", "dns", "Lokhttp3/Dns;", "getDns$okhttp", "()Lokhttp3/Dns;", "setDns$okhttp", "(Lokhttp3/Dns;)V", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "getEventListenerFactory$okhttp", "()Lokhttp3/EventListener$Factory;", "setEventListenerFactory$okhttp", "(Lokhttp3/EventListener$Factory;)V", "followRedirects", "", "getFollowRedirects$okhttp", "()Z", "setFollowRedirects$okhttp", "(Z)V", "followSslRedirects", "getFollowSslRedirects$okhttp", "setFollowSslRedirects$okhttp", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "getHostnameVerifier$okhttp", "()Ljavax/net/ssl/HostnameVerifier;", "setHostnameVerifier$okhttp", "(Ljavax/net/ssl/HostnameVerifier;)V", "interceptors", "", "Lokhttp3/Interceptor;", "getInterceptors$okhttp", "minWebSocketMessageToCompress", "", "getMinWebSocketMessageToCompress$okhttp", "()J", "setMinWebSocketMessageToCompress$okhttp", "(J)V", "networkInterceptors", "getNetworkInterceptors$okhttp", "pingInterval", "getPingInterval$okhttp", "setPingInterval$okhttp", "protocols", "Lokhttp3/Protocol;", "getProtocols$okhttp", "setProtocols$okhttp", "proxy", "Ljava/net/Proxy;", "getProxy$okhttp", "()Ljava/net/Proxy;", "setProxy$okhttp", "(Ljava/net/Proxy;)V", "proxyAuthenticator", "getProxyAuthenticator$okhttp", "setProxyAuthenticator$okhttp", "proxySelector", "Ljava/net/ProxySelector;", "getProxySelector$okhttp", "()Ljava/net/ProxySelector;", "setProxySelector$okhttp", "(Ljava/net/ProxySelector;)V", "readTimeout", "getReadTimeout$okhttp", "setReadTimeout$okhttp", "retryOnConnectionFailure", "getRetryOnConnectionFailure$okhttp", "setRetryOnConnectionFailure$okhttp", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase$okhttp", "()Lokhttp3/internal/connection/RouteDatabase;", "setRouteDatabase$okhttp", "(Lokhttp3/internal/connection/RouteDatabase;)V", "socketFactory", "Ljavax/net/SocketFactory;", "getSocketFactory$okhttp", "()Ljavax/net/SocketFactory;", "setSocketFactory$okhttp", "(Ljavax/net/SocketFactory;)V", "sslSocketFactoryOrNull", "Ljavax/net/ssl/SSLSocketFactory;", "getSslSocketFactoryOrNull$okhttp", "()Ljavax/net/ssl/SSLSocketFactory;", "setSslSocketFactoryOrNull$okhttp", "(Ljavax/net/ssl/SSLSocketFactory;)V", "writeTimeout", "getWriteTimeout$okhttp", "setWriteTimeout$okhttp", "x509TrustManagerOrNull", "Ljavax/net/ssl/X509TrustManager;", "getX509TrustManagerOrNull$okhttp", "()Ljavax/net/ssl/X509TrustManager;", "setX509TrustManagerOrNull$okhttp", "(Ljavax/net/ssl/X509TrustManager;)V", "addInterceptor", "block", "Lkotlin/Function1;", "Lokhttp3/Interceptor$Chain;", "Lkotlin/ParameterName;", "name", "chain", "Lokhttp3/Response;", "-addInterceptor", "interceptor", "addNetworkInterceptor", "-addNetworkInterceptor", "build", "duration", "Ljava/time/Duration;", "timeout", "unit", "Ljava/util/concurrent/TimeUnit;", "eventListener", "Lokhttp3/EventListener;", "followProtocolRedirects", "bytes", "interval", "sslSocketFactory", "trustManager", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Builder
  {
    private Authenticator authenticator = Authenticator.NONE;
    private Cache cache;
    private int callTimeout;
    private CertificateChainCleaner certificateChainCleaner;
    private CertificatePinner certificatePinner;
    private int connectTimeout;
    private ConnectionPool connectionPool = new ConnectionPool();
    private List<ConnectionSpec> connectionSpecs;
    private CookieJar cookieJar = CookieJar.NO_COOKIES;
    private Dispatcher dispatcher = new Dispatcher();
    private Dns dns = Dns.SYSTEM;
    private EventListener.Factory eventListenerFactory = Util.asFactory(EventListener.NONE);
    private boolean followRedirects = true;
    private boolean followSslRedirects = true;
    private HostnameVerifier hostnameVerifier;
    private final List<Interceptor> interceptors = (List)new ArrayList();
    private long minWebSocketMessageToCompress;
    private final List<Interceptor> networkInterceptors = (List)new ArrayList();
    private int pingInterval;
    private List<? extends Protocol> protocols;
    private Proxy proxy;
    private Authenticator proxyAuthenticator = Authenticator.NONE;
    private ProxySelector proxySelector;
    private int readTimeout;
    private boolean retryOnConnectionFailure = true;
    private RouteDatabase routeDatabase;
    private SocketFactory socketFactory;
    private SSLSocketFactory sslSocketFactoryOrNull;
    private int writeTimeout;
    private X509TrustManager x509TrustManagerOrNull;
    
    public Builder()
    {
      SocketFactory localSocketFactory = SocketFactory.getDefault();
      Intrinsics.checkNotNullExpressionValue(localSocketFactory, "SocketFactory.getDefault()");
      this.socketFactory = localSocketFactory;
      this.connectionSpecs = OkHttpClient.Companion.getDEFAULT_CONNECTION_SPECS$okhttp();
      this.protocols = OkHttpClient.Companion.getDEFAULT_PROTOCOLS$okhttp();
      this.hostnameVerifier = ((HostnameVerifier)OkHostnameVerifier.INSTANCE);
      this.certificatePinner = CertificatePinner.DEFAULT;
      this.connectTimeout = 10000;
      this.readTimeout = 10000;
      this.writeTimeout = 10000;
      this.minWebSocketMessageToCompress = 1024L;
    }
    
    public Builder(OkHttpClient paramOkHttpClient)
    {
      this();
      CollectionsKt.addAll((Collection)this.interceptors, (Iterable)paramOkHttpClient.interceptors());
      CollectionsKt.addAll((Collection)this.networkInterceptors, (Iterable)paramOkHttpClient.networkInterceptors());
      this.eventListenerFactory = paramOkHttpClient.eventListenerFactory();
      this.retryOnConnectionFailure = paramOkHttpClient.retryOnConnectionFailure();
      this.authenticator = paramOkHttpClient.authenticator();
      this.followRedirects = paramOkHttpClient.followRedirects();
      this.followSslRedirects = paramOkHttpClient.followSslRedirects();
      this.cookieJar = paramOkHttpClient.cookieJar();
      this.cache = paramOkHttpClient.cache();
      this.dns = paramOkHttpClient.dns();
      this.proxy = paramOkHttpClient.proxy();
      this.proxySelector = paramOkHttpClient.proxySelector();
      this.proxyAuthenticator = paramOkHttpClient.proxyAuthenticator();
      this.socketFactory = paramOkHttpClient.socketFactory();
      this.sslSocketFactoryOrNull = OkHttpClient.access$getSslSocketFactoryOrNull$p(paramOkHttpClient);
      this.x509TrustManagerOrNull = paramOkHttpClient.x509TrustManager();
      this.connectionSpecs = paramOkHttpClient.connectionSpecs();
      this.protocols = paramOkHttpClient.protocols();
      this.hostnameVerifier = paramOkHttpClient.hostnameVerifier();
      this.certificatePinner = paramOkHttpClient.certificatePinner();
      this.certificateChainCleaner = paramOkHttpClient.certificateChainCleaner();
      this.callTimeout = paramOkHttpClient.callTimeoutMillis();
      this.connectTimeout = paramOkHttpClient.connectTimeoutMillis();
      this.readTimeout = paramOkHttpClient.readTimeoutMillis();
      this.writeTimeout = paramOkHttpClient.writeTimeoutMillis();
      this.pingInterval = paramOkHttpClient.pingIntervalMillis();
      this.minWebSocketMessageToCompress = paramOkHttpClient.minWebSocketMessageToCompress();
      this.routeDatabase = paramOkHttpClient.getRouteDatabase();
    }
    
    public final Builder -addInterceptor(Function1<? super Interceptor.Chain, Response> paramFunction1)
    {
      Intrinsics.checkNotNullParameter(paramFunction1, "block");
      addInterceptor((Interceptor)new Interceptor()
      {
        final Function1 $block;
        
        public final Response intercept(Interceptor.Chain paramAnonymousChain)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousChain, "chain");
          return (Response)this.$block.invoke(paramAnonymousChain);
        }
      });
    }
    
    public final Builder -addNetworkInterceptor(Function1<? super Interceptor.Chain, Response> paramFunction1)
    {
      Intrinsics.checkNotNullParameter(paramFunction1, "block");
      addNetworkInterceptor((Interceptor)new Interceptor()
      {
        final Function1 $block;
        
        public final Response intercept(Interceptor.Chain paramAnonymousChain)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousChain, "chain");
          return (Response)this.$block.invoke(paramAnonymousChain);
        }
      });
    }
    
    public final Builder addInterceptor(Interceptor paramInterceptor)
    {
      Intrinsics.checkNotNullParameter(paramInterceptor, "interceptor");
      ((Collection)((Builder)this).interceptors).add(paramInterceptor);
      return (Builder)this;
    }
    
    public final Builder addNetworkInterceptor(Interceptor paramInterceptor)
    {
      Intrinsics.checkNotNullParameter(paramInterceptor, "interceptor");
      ((Collection)((Builder)this).networkInterceptors).add(paramInterceptor);
      return (Builder)this;
    }
    
    public final Builder authenticator(Authenticator paramAuthenticator)
    {
      Intrinsics.checkNotNullParameter(paramAuthenticator, "authenticator");
      ((Builder)this).authenticator = paramAuthenticator;
      return (Builder)this;
    }
    
    public final OkHttpClient build()
    {
      return new OkHttpClient(this);
    }
    
    public final Builder cache(Cache paramCache)
    {
      ((Builder)this).cache = paramCache;
      return (Builder)this;
    }
    
    public final Builder callTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "unit");
      ((Builder)this).callTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return (Builder)this;
    }
    
    public final Builder callTimeout(Duration paramDuration)
    {
      Intrinsics.checkNotNullParameter(paramDuration, "duration");
      ((Builder)this).callTimeout(paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return (Builder)this;
    }
    
    public final Builder certificatePinner(CertificatePinner paramCertificatePinner)
    {
      Intrinsics.checkNotNullParameter(paramCertificatePinner, "certificatePinner");
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramCertificatePinner, localBuilder.certificatePinner) ^ true))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.certificatePinner = paramCertificatePinner;
      return (Builder)this;
    }
    
    public final Builder connectTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "unit");
      ((Builder)this).connectTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return (Builder)this;
    }
    
    public final Builder connectTimeout(Duration paramDuration)
    {
      Intrinsics.checkNotNullParameter(paramDuration, "duration");
      ((Builder)this).connectTimeout(paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return (Builder)this;
    }
    
    public final Builder connectionPool(ConnectionPool paramConnectionPool)
    {
      Intrinsics.checkNotNullParameter(paramConnectionPool, "connectionPool");
      ((Builder)this).connectionPool = paramConnectionPool;
      return (Builder)this;
    }
    
    public final Builder connectionSpecs(List<ConnectionSpec> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "connectionSpecs");
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramList, localBuilder.connectionSpecs) ^ true))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.connectionSpecs = Util.toImmutableList(paramList);
      return (Builder)this;
    }
    
    public final Builder cookieJar(CookieJar paramCookieJar)
    {
      Intrinsics.checkNotNullParameter(paramCookieJar, "cookieJar");
      ((Builder)this).cookieJar = paramCookieJar;
      return (Builder)this;
    }
    
    public final Builder dispatcher(Dispatcher paramDispatcher)
    {
      Intrinsics.checkNotNullParameter(paramDispatcher, "dispatcher");
      ((Builder)this).dispatcher = paramDispatcher;
      return (Builder)this;
    }
    
    public final Builder dns(Dns paramDns)
    {
      Intrinsics.checkNotNullParameter(paramDns, "dns");
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramDns, localBuilder.dns) ^ true))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.dns = paramDns;
      return (Builder)this;
    }
    
    public final Builder eventListener(EventListener paramEventListener)
    {
      Intrinsics.checkNotNullParameter(paramEventListener, "eventListener");
      ((Builder)this).eventListenerFactory = Util.asFactory(paramEventListener);
      return (Builder)this;
    }
    
    public final Builder eventListenerFactory(EventListener.Factory paramFactory)
    {
      Intrinsics.checkNotNullParameter(paramFactory, "eventListenerFactory");
      ((Builder)this).eventListenerFactory = paramFactory;
      return (Builder)this;
    }
    
    public final Builder followRedirects(boolean paramBoolean)
    {
      ((Builder)this).followRedirects = paramBoolean;
      return (Builder)this;
    }
    
    public final Builder followSslRedirects(boolean paramBoolean)
    {
      ((Builder)this).followSslRedirects = paramBoolean;
      return (Builder)this;
    }
    
    public final Authenticator getAuthenticator$okhttp()
    {
      return this.authenticator;
    }
    
    public final Cache getCache$okhttp()
    {
      return this.cache;
    }
    
    public final int getCallTimeout$okhttp()
    {
      return this.callTimeout;
    }
    
    public final CertificateChainCleaner getCertificateChainCleaner$okhttp()
    {
      return this.certificateChainCleaner;
    }
    
    public final CertificatePinner getCertificatePinner$okhttp()
    {
      return this.certificatePinner;
    }
    
    public final int getConnectTimeout$okhttp()
    {
      return this.connectTimeout;
    }
    
    public final ConnectionPool getConnectionPool$okhttp()
    {
      return this.connectionPool;
    }
    
    public final List<ConnectionSpec> getConnectionSpecs$okhttp()
    {
      return this.connectionSpecs;
    }
    
    public final CookieJar getCookieJar$okhttp()
    {
      return this.cookieJar;
    }
    
    public final Dispatcher getDispatcher$okhttp()
    {
      return this.dispatcher;
    }
    
    public final Dns getDns$okhttp()
    {
      return this.dns;
    }
    
    public final EventListener.Factory getEventListenerFactory$okhttp()
    {
      return this.eventListenerFactory;
    }
    
    public final boolean getFollowRedirects$okhttp()
    {
      return this.followRedirects;
    }
    
    public final boolean getFollowSslRedirects$okhttp()
    {
      return this.followSslRedirects;
    }
    
    public final HostnameVerifier getHostnameVerifier$okhttp()
    {
      return this.hostnameVerifier;
    }
    
    public final List<Interceptor> getInterceptors$okhttp()
    {
      return this.interceptors;
    }
    
    public final long getMinWebSocketMessageToCompress$okhttp()
    {
      return this.minWebSocketMessageToCompress;
    }
    
    public final List<Interceptor> getNetworkInterceptors$okhttp()
    {
      return this.networkInterceptors;
    }
    
    public final int getPingInterval$okhttp()
    {
      return this.pingInterval;
    }
    
    public final List<Protocol> getProtocols$okhttp()
    {
      return this.protocols;
    }
    
    public final Proxy getProxy$okhttp()
    {
      return this.proxy;
    }
    
    public final Authenticator getProxyAuthenticator$okhttp()
    {
      return this.proxyAuthenticator;
    }
    
    public final ProxySelector getProxySelector$okhttp()
    {
      return this.proxySelector;
    }
    
    public final int getReadTimeout$okhttp()
    {
      return this.readTimeout;
    }
    
    public final boolean getRetryOnConnectionFailure$okhttp()
    {
      return this.retryOnConnectionFailure;
    }
    
    public final RouteDatabase getRouteDatabase$okhttp()
    {
      return this.routeDatabase;
    }
    
    public final SocketFactory getSocketFactory$okhttp()
    {
      return this.socketFactory;
    }
    
    public final SSLSocketFactory getSslSocketFactoryOrNull$okhttp()
    {
      return this.sslSocketFactoryOrNull;
    }
    
    public final int getWriteTimeout$okhttp()
    {
      return this.writeTimeout;
    }
    
    public final X509TrustManager getX509TrustManagerOrNull$okhttp()
    {
      return this.x509TrustManagerOrNull;
    }
    
    public final Builder hostnameVerifier(HostnameVerifier paramHostnameVerifier)
    {
      Intrinsics.checkNotNullParameter(paramHostnameVerifier, "hostnameVerifier");
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramHostnameVerifier, localBuilder.hostnameVerifier) ^ true))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.hostnameVerifier = paramHostnameVerifier;
      return (Builder)this;
    }
    
    public final List<Interceptor> interceptors()
    {
      return this.interceptors;
    }
    
    public final Builder minWebSocketMessageToCompress(long paramLong)
    {
      Builder localBuilder = (Builder)this;
      int i;
      if (paramLong >= 0L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.minWebSocketMessageToCompress = paramLong;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("minWebSocketMessageToCompress must be positive: " + paramLong).toString()));
    }
    
    public final List<Interceptor> networkInterceptors()
    {
      return this.networkInterceptors;
    }
    
    public final Builder pingInterval(long paramLong, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "unit");
      ((Builder)this).pingInterval = Util.checkDuration("interval", paramLong, paramTimeUnit);
      return (Builder)this;
    }
    
    public final Builder pingInterval(Duration paramDuration)
    {
      Intrinsics.checkNotNullParameter(paramDuration, "duration");
      ((Builder)this).pingInterval(paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return (Builder)this;
    }
    
    public final Builder protocols(List<? extends Protocol> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "protocols");
      Builder localBuilder = (Builder)this;
      List localList = CollectionsKt.toMutableList((Collection)paramList);
      boolean bool = localList.contains(Protocol.H2_PRIOR_KNOWLEDGE);
      int j = 0;
      int i;
      if ((!bool) && (!localList.contains(Protocol.HTTP_1_1))) {
        i = 0;
      } else {
        i = 1;
      }
      if (i != 0)
      {
        if (localList.contains(Protocol.H2_PRIOR_KNOWLEDGE))
        {
          i = j;
          if (localList.size() > 1) {}
        }
        else
        {
          i = 1;
        }
        if (i != 0)
        {
          if ((localList.contains(Protocol.HTTP_1_0) ^ true))
          {
            if (localList != null)
            {
              if ((localList.contains(null) ^ true))
              {
                localList.remove(Protocol.SPDY_3);
                if ((Intrinsics.areEqual(localList, localBuilder.protocols) ^ true))
                {
                  paramList = (RouteDatabase)null;
                  localBuilder.routeDatabase = null;
                }
                paramList = Collections.unmodifiableList(localList);
                Intrinsics.checkNotNullExpressionValue(paramList, "Collections.unmodifiableList(protocolsCopy)");
                localBuilder.protocols = paramList;
                return (Builder)this;
              }
              throw ((Throwable)new IllegalArgumentException("protocols must not contain null".toString()));
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Protocol?>");
          }
          throw ((Throwable)new IllegalArgumentException(("protocols must not contain http/1.0: " + localList).toString()));
        }
        throw ((Throwable)new IllegalArgumentException(("protocols containing h2_prior_knowledge cannot use other protocols: " + localList).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("protocols must contain h2_prior_knowledge or http/1.1: " + localList).toString()));
    }
    
    public final Builder proxy(Proxy paramProxy)
    {
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramProxy, localBuilder.proxy) ^ true))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.proxy = paramProxy;
      return (Builder)this;
    }
    
    public final Builder proxyAuthenticator(Authenticator paramAuthenticator)
    {
      Intrinsics.checkNotNullParameter(paramAuthenticator, "proxyAuthenticator");
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramAuthenticator, localBuilder.proxyAuthenticator) ^ true))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.proxyAuthenticator = paramAuthenticator;
      return (Builder)this;
    }
    
    public final Builder proxySelector(ProxySelector paramProxySelector)
    {
      Intrinsics.checkNotNullParameter(paramProxySelector, "proxySelector");
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramProxySelector, localBuilder.proxySelector) ^ true))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.proxySelector = paramProxySelector;
      return (Builder)this;
    }
    
    public final Builder readTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "unit");
      ((Builder)this).readTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return (Builder)this;
    }
    
    public final Builder readTimeout(Duration paramDuration)
    {
      Intrinsics.checkNotNullParameter(paramDuration, "duration");
      ((Builder)this).readTimeout(paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return (Builder)this;
    }
    
    public final Builder retryOnConnectionFailure(boolean paramBoolean)
    {
      ((Builder)this).retryOnConnectionFailure = paramBoolean;
      return (Builder)this;
    }
    
    public final void setAuthenticator$okhttp(Authenticator paramAuthenticator)
    {
      Intrinsics.checkNotNullParameter(paramAuthenticator, "<set-?>");
      this.authenticator = paramAuthenticator;
    }
    
    public final void setCache$okhttp(Cache paramCache)
    {
      this.cache = paramCache;
    }
    
    public final void setCallTimeout$okhttp(int paramInt)
    {
      this.callTimeout = paramInt;
    }
    
    public final void setCertificateChainCleaner$okhttp(CertificateChainCleaner paramCertificateChainCleaner)
    {
      this.certificateChainCleaner = paramCertificateChainCleaner;
    }
    
    public final void setCertificatePinner$okhttp(CertificatePinner paramCertificatePinner)
    {
      Intrinsics.checkNotNullParameter(paramCertificatePinner, "<set-?>");
      this.certificatePinner = paramCertificatePinner;
    }
    
    public final void setConnectTimeout$okhttp(int paramInt)
    {
      this.connectTimeout = paramInt;
    }
    
    public final void setConnectionPool$okhttp(ConnectionPool paramConnectionPool)
    {
      Intrinsics.checkNotNullParameter(paramConnectionPool, "<set-?>");
      this.connectionPool = paramConnectionPool;
    }
    
    public final void setConnectionSpecs$okhttp(List<ConnectionSpec> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "<set-?>");
      this.connectionSpecs = paramList;
    }
    
    public final void setCookieJar$okhttp(CookieJar paramCookieJar)
    {
      Intrinsics.checkNotNullParameter(paramCookieJar, "<set-?>");
      this.cookieJar = paramCookieJar;
    }
    
    public final void setDispatcher$okhttp(Dispatcher paramDispatcher)
    {
      Intrinsics.checkNotNullParameter(paramDispatcher, "<set-?>");
      this.dispatcher = paramDispatcher;
    }
    
    public final void setDns$okhttp(Dns paramDns)
    {
      Intrinsics.checkNotNullParameter(paramDns, "<set-?>");
      this.dns = paramDns;
    }
    
    public final void setEventListenerFactory$okhttp(EventListener.Factory paramFactory)
    {
      Intrinsics.checkNotNullParameter(paramFactory, "<set-?>");
      this.eventListenerFactory = paramFactory;
    }
    
    public final void setFollowRedirects$okhttp(boolean paramBoolean)
    {
      this.followRedirects = paramBoolean;
    }
    
    public final void setFollowSslRedirects$okhttp(boolean paramBoolean)
    {
      this.followSslRedirects = paramBoolean;
    }
    
    public final void setHostnameVerifier$okhttp(HostnameVerifier paramHostnameVerifier)
    {
      Intrinsics.checkNotNullParameter(paramHostnameVerifier, "<set-?>");
      this.hostnameVerifier = paramHostnameVerifier;
    }
    
    public final void setMinWebSocketMessageToCompress$okhttp(long paramLong)
    {
      this.minWebSocketMessageToCompress = paramLong;
    }
    
    public final void setPingInterval$okhttp(int paramInt)
    {
      this.pingInterval = paramInt;
    }
    
    public final void setProtocols$okhttp(List<? extends Protocol> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "<set-?>");
      this.protocols = paramList;
    }
    
    public final void setProxy$okhttp(Proxy paramProxy)
    {
      this.proxy = paramProxy;
    }
    
    public final void setProxyAuthenticator$okhttp(Authenticator paramAuthenticator)
    {
      Intrinsics.checkNotNullParameter(paramAuthenticator, "<set-?>");
      this.proxyAuthenticator = paramAuthenticator;
    }
    
    public final void setProxySelector$okhttp(ProxySelector paramProxySelector)
    {
      this.proxySelector = paramProxySelector;
    }
    
    public final void setReadTimeout$okhttp(int paramInt)
    {
      this.readTimeout = paramInt;
    }
    
    public final void setRetryOnConnectionFailure$okhttp(boolean paramBoolean)
    {
      this.retryOnConnectionFailure = paramBoolean;
    }
    
    public final void setRouteDatabase$okhttp(RouteDatabase paramRouteDatabase)
    {
      this.routeDatabase = paramRouteDatabase;
    }
    
    public final void setSocketFactory$okhttp(SocketFactory paramSocketFactory)
    {
      Intrinsics.checkNotNullParameter(paramSocketFactory, "<set-?>");
      this.socketFactory = paramSocketFactory;
    }
    
    public final void setSslSocketFactoryOrNull$okhttp(SSLSocketFactory paramSSLSocketFactory)
    {
      this.sslSocketFactoryOrNull = paramSSLSocketFactory;
    }
    
    public final void setWriteTimeout$okhttp(int paramInt)
    {
      this.writeTimeout = paramInt;
    }
    
    public final void setX509TrustManagerOrNull$okhttp(X509TrustManager paramX509TrustManager)
    {
      this.x509TrustManagerOrNull = paramX509TrustManager;
    }
    
    public final Builder socketFactory(SocketFactory paramSocketFactory)
    {
      Intrinsics.checkNotNullParameter(paramSocketFactory, "socketFactory");
      Builder localBuilder = (Builder)this;
      if ((paramSocketFactory instanceof SSLSocketFactory ^ true))
      {
        if ((Intrinsics.areEqual(paramSocketFactory, localBuilder.socketFactory) ^ true))
        {
          RouteDatabase localRouteDatabase = (RouteDatabase)null;
          localBuilder.routeDatabase = null;
        }
        localBuilder.socketFactory = paramSocketFactory;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException("socketFactory instanceof SSLSocketFactory".toString()));
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Use the sslSocketFactory overload that accepts a X509TrustManager.")
    public final Builder sslSocketFactory(SSLSocketFactory paramSSLSocketFactory)
    {
      Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
      Builder localBuilder = (Builder)this;
      if ((Intrinsics.areEqual(paramSSLSocketFactory, localBuilder.sslSocketFactoryOrNull) ^ true))
      {
        localObject = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.sslSocketFactoryOrNull = paramSSLSocketFactory;
      Object localObject = Platform.Companion.get().trustManager(paramSSLSocketFactory);
      if (localObject != null)
      {
        localBuilder.x509TrustManagerOrNull = ((X509TrustManager)localObject);
        localObject = Platform.Companion.get();
        paramSSLSocketFactory = localBuilder.x509TrustManagerOrNull;
        Intrinsics.checkNotNull(paramSSLSocketFactory);
        localBuilder.certificateChainCleaner = ((Platform)localObject).buildCertificateChainCleaner(paramSSLSocketFactory);
        return (Builder)this;
      }
      throw ((Throwable)new IllegalStateException("Unable to extract the trust manager on " + Platform.Companion.get() + ", " + "sslSocketFactory is " + paramSSLSocketFactory.getClass()));
    }
    
    public final Builder sslSocketFactory(SSLSocketFactory paramSSLSocketFactory, X509TrustManager paramX509TrustManager)
    {
      Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
      Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
      Builder localBuilder = (Builder)this;
      if (((Intrinsics.areEqual(paramSSLSocketFactory, localBuilder.sslSocketFactoryOrNull) ^ true)) || ((Intrinsics.areEqual(paramX509TrustManager, localBuilder.x509TrustManagerOrNull) ^ true)))
      {
        RouteDatabase localRouteDatabase = (RouteDatabase)null;
        localBuilder.routeDatabase = null;
      }
      localBuilder.sslSocketFactoryOrNull = paramSSLSocketFactory;
      localBuilder.certificateChainCleaner = CertificateChainCleaner.Companion.get(paramX509TrustManager);
      localBuilder.x509TrustManagerOrNull = paramX509TrustManager;
      return (Builder)this;
    }
    
    public final Builder writeTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "unit");
      ((Builder)this).writeTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return (Builder)this;
    }
    
    public final Builder writeTimeout(Duration paramDuration)
    {
      Intrinsics.checkNotNullParameter(paramDuration, "duration");
      ((Builder)this).writeTimeout(paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return (Builder)this;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\032\020\003\032\b\022\004\022\0020\0050\004X\004¢\006\b\n\000\032\004\b\006\020\007R\032\020\b\032\b\022\004\022\0020\t0\004X\004¢\006\b\n\000\032\004\b\n\020\007¨\006\013"}, d2={"Lokhttp3/OkHttpClient$Companion;", "", "()V", "DEFAULT_CONNECTION_SPECS", "", "Lokhttp3/ConnectionSpec;", "getDEFAULT_CONNECTION_SPECS$okhttp", "()Ljava/util/List;", "DEFAULT_PROTOCOLS", "Lokhttp3/Protocol;", "getDEFAULT_PROTOCOLS$okhttp", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final List<ConnectionSpec> getDEFAULT_CONNECTION_SPECS$okhttp()
    {
      return OkHttpClient.access$getDEFAULT_CONNECTION_SPECS$cp();
    }
    
    public final List<Protocol> getDEFAULT_PROTOCOLS$okhttp()
    {
      return OkHttpClient.access$getDEFAULT_PROTOCOLS$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/OkHttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */