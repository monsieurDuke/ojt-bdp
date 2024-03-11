package okhttp3.internal.platform;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.platform.android.AndroidLog;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.BasicTrustRootIndex;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\000t\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\005\n\002\020\013\n\002\b\004\n\002\020\003\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\005\b\026\030\000 /2\0020\001:\001/B\005¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\006H\026J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\026J\020\020\013\032\0020\f2\006\020\t\032\0020\nH\026J-\020\r\032\0020\0042\006\020\005\032\0020\0062\b\020\016\032\004\030\0010\0172\021\020\020\032\r\022\t\022\0070\022¢\006\002\b\0230\021H\026J \020\024\032\0020\0042\006\020\025\032\0020\0262\006\020\027\032\0020\0302\006\020\031\032\0020\032H\026J\006\020\033\032\0020\017J\022\020\034\032\004\030\0010\0172\006\020\005\032\0020\006H\026J\022\020\035\032\004\030\0010\0012\006\020\036\032\0020\017H\026J\020\020\037\032\0020 2\006\020\016\032\0020\017H\026J&\020!\032\0020\0042\006\020\"\032\0020\0172\b\b\002\020#\032\0020\0322\n\b\002\020$\032\004\030\0010%H\026J\032\020&\032\0020\0042\006\020\"\032\0020\0172\b\020'\032\004\030\0010\001H\026J\b\020(\032\0020)H\026J\020\020*\032\0020+2\006\020\t\032\0020\nH\026J\b\020,\032\0020\nH\026J\b\020-\032\0020\017H\026J\022\020\t\032\004\030\0010\n2\006\020.\032\0020+H\026¨\0060"}, d2={"Lokhttp3/internal/platform/Platform;", "", "()V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "connectSocket", "socket", "Ljava/net/Socket;", "address", "Ljava/net/InetSocketAddress;", "connectTimeout", "", "getPrefix", "getSelectedProtocol", "getStackTraceForCloseable", "closer", "isCleartextTrafficPermitted", "", "log", "message", "level", "t", "", "logCloseableLeak", "stackTrace", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "platformTrustManager", "toString", "sslSocketFactory", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public class Platform
{
  public static final Companion Companion;
  public static final int INFO = 4;
  public static final int WARN = 5;
  private static final Logger logger = Logger.getLogger(OkHttpClient.class.getName());
  private static volatile Platform platform;
  
  static
  {
    Companion localCompanion = new Companion(null);
    Companion = localCompanion;
    platform = Companion.access$findPlatform(localCompanion);
  }
  
  @JvmStatic
  public static final Platform get()
  {
    return Companion.get();
  }
  
  public void afterHandshake(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
  }
  
  public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager paramX509TrustManager)
  {
    Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
    return (CertificateChainCleaner)new BasicCertificateChainCleaner(buildTrustRootIndex(paramX509TrustManager));
  }
  
  public TrustRootIndex buildTrustRootIndex(X509TrustManager paramX509TrustManager)
  {
    Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
    paramX509TrustManager = paramX509TrustManager.getAcceptedIssuers();
    Intrinsics.checkNotNullExpressionValue(paramX509TrustManager, "trustManager.acceptedIssuers");
    return (TrustRootIndex)new BasicTrustRootIndex((X509Certificate[])Arrays.copyOf(paramX509TrustManager, paramX509TrustManager.length));
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
  }
  
  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramSocket, "socket");
    Intrinsics.checkNotNullParameter(paramInetSocketAddress, "address");
    paramSocket.connect((SocketAddress)paramInetSocketAddress, paramInt);
  }
  
  public final String getPrefix()
  {
    return "OkHttp";
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    return null;
  }
  
  public Object getStackTraceForCloseable(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "closer");
    if (logger.isLoggable(Level.FINE)) {
      paramString = new Throwable(paramString);
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public boolean isCleartextTrafficPermitted(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    return true;
  }
  
  public void log(String paramString, int paramInt, Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramString, "message");
    Level localLevel;
    if (paramInt == 5) {
      localLevel = Level.WARNING;
    } else {
      localLevel = Level.INFO;
    }
    logger.log(localLevel, paramString, paramThrowable);
  }
  
  public void logCloseableLeak(String paramString, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramString, "message");
    String str = paramString;
    paramString = str;
    if (paramObject == null) {
      paramString = str + " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);";
    }
    log(paramString, 5, (Throwable)paramObject);
  }
  
  public SSLContext newSSLContext()
  {
    SSLContext localSSLContext = SSLContext.getInstance("TLS");
    Intrinsics.checkNotNullExpressionValue(localSSLContext, "SSLContext.getInstance(\"TLS\")");
    return localSSLContext;
  }
  
  public SSLSocketFactory newSslSocketFactory(X509TrustManager paramX509TrustManager)
  {
    Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
    try
    {
      SSLContext localSSLContext = newSSLContext();
      localSSLContext.init(null, new TrustManager[] { (TrustManager)paramX509TrustManager }, null);
      paramX509TrustManager = localSSLContext.getSocketFactory();
      Intrinsics.checkNotNullExpressionValue(paramX509TrustManager, "newSSLContext().apply {\n…ll)\n      }.socketFactory");
      return paramX509TrustManager;
    }
    catch (GeneralSecurityException paramX509TrustManager)
    {
      throw ((Throwable)new AssertionError("No System TLS: " + paramX509TrustManager, (Throwable)paramX509TrustManager));
    }
  }
  
  public X509TrustManager platformTrustManager()
  {
    Object localObject1 = TrustManagerFactory.getDefaultAlgorithm();
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    Object localObject2 = TrustManagerFactory.getInstance((String)localObject1);
    localObject1 = (KeyStore)null;
    ((TrustManagerFactory)localObject2).init(null);
    Intrinsics.checkNotNullExpressionValue(localObject2, "factory");
    localObject2 = ((TrustManagerFactory)localObject2).getTrustManagers();
    Intrinsics.checkNotNull(localObject2);
    int j = localObject2.length;
    int i = 1;
    if ((j != 1) || (!(localObject2[0] instanceof X509TrustManager))) {
      i = 0;
    }
    if (i != 0)
    {
      localObject1 = localObject2[0];
      if (localObject1 != null) {
        return (X509TrustManager)localObject1;
      }
      throw new NullPointerException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
    }
    localObject1 = new StringBuilder().append("Unexpected default trust managers: ");
    localObject2 = Arrays.toString((Object[])localObject2);
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    Intrinsics.checkNotNullExpressionValue(localObject2, "java.util.Arrays.toString(this)");
    throw ((Throwable)new IllegalStateException(((String)localObject2).toString()));
  }
  
  public String toString()
  {
    String str = getClass().getSimpleName();
    Intrinsics.checkNotNullExpressionValue(str, "javaClass.simpleName");
    return str;
  }
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
    Object localObject = null;
    try
    {
      Class localClass = Class.forName("sun.security.ssl.SSLContextImpl");
      Intrinsics.checkNotNullExpressionValue(localClass, "sslContextClass");
      paramSSLSocketFactory = Util.readFieldOrNull(paramSSLSocketFactory, localClass, "context");
      if (paramSSLSocketFactory != null) {
        paramSSLSocketFactory = (X509TrustManager)Util.readFieldOrNull(paramSSLSocketFactory, X509TrustManager.class, "trustManager");
      } else {
        return null;
      }
    }
    catch (ClassNotFoundException paramSSLSocketFactory)
    {
      paramSSLSocketFactory = (SSLSocketFactory)localObject;
    }
    return paramSSLSocketFactory;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000H\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\013\n\002\b\005\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020 \n\002\020\016\n\000\n\002\030\002\n\000\n\002\020\022\n\002\b\005\n\002\020\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\032\020\021\032\b\022\004\022\0020\0230\0222\f\020\024\032\b\022\004\022\0020\0250\022J\024\020\026\032\0020\0272\f\020\024\032\b\022\004\022\0020\0250\022J\b\020\030\032\0020\020H\002J\b\020\031\032\0020\020H\002J\b\020\032\032\0020\020H\002J\b\020\033\032\0020\020H\007J\020\020\034\032\0020\0352\b\b\002\020\017\032\0020\020R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\021\020\006\032\0020\0078F¢\006\006\032\004\b\006\020\bR\024\020\t\032\0020\0078BX\004¢\006\006\032\004\b\t\020\bR\024\020\n\032\0020\0078BX\004¢\006\006\032\004\b\n\020\bR\024\020\013\032\0020\0078BX\004¢\006\006\032\004\b\013\020\bR\026\020\f\032\n \016*\004\030\0010\r0\rX\004¢\006\002\n\000R\016\020\017\032\0020\020X\016¢\006\002\n\000¨\006\036"}, d2={"Lokhttp3/internal/platform/Platform$Companion;", "", "()V", "INFO", "", "WARN", "isAndroid", "", "()Z", "isBouncyCastlePreferred", "isConscryptPreferred", "isOpenJSSEPreferred", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "platform", "Lokhttp3/internal/platform/Platform;", "alpnProtocolNames", "", "", "protocols", "Lokhttp3/Protocol;", "concatLengthPrefixed", "", "findAndroidPlatform", "findJvmPlatform", "findPlatform", "get", "resetForTests", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final Platform findAndroidPlatform()
    {
      AndroidLog.INSTANCE.enable();
      Platform localPlatform = Android10Platform.Companion.buildIfSupported();
      if (localPlatform == null)
      {
        localPlatform = AndroidPlatform.Companion.buildIfSupported();
        Intrinsics.checkNotNull(localPlatform);
      }
      return localPlatform;
    }
    
    private final Platform findJvmPlatform()
    {
      if (((Companion)this).isConscryptPreferred())
      {
        localObject = ConscryptPlatform.Companion.buildIfSupported();
        if (localObject != null) {
          return (Platform)localObject;
        }
      }
      if (((Companion)this).isBouncyCastlePreferred())
      {
        localObject = BouncyCastlePlatform.Companion.buildIfSupported();
        if (localObject != null) {
          return (Platform)localObject;
        }
      }
      if (((Companion)this).isOpenJSSEPreferred())
      {
        localObject = OpenJSSEPlatform.Companion.buildIfSupported();
        if (localObject != null) {
          return (Platform)localObject;
        }
      }
      Object localObject = Jdk9Platform.Companion.buildIfSupported();
      if (localObject != null) {
        return (Platform)localObject;
      }
      localObject = Jdk8WithJettyBootPlatform.Companion.buildIfSupported();
      if (localObject != null) {
        return (Platform)localObject;
      }
      return new Platform();
    }
    
    private final Platform findPlatform()
    {
      Platform localPlatform;
      if (((Companion)this).isAndroid()) {
        localPlatform = ((Companion)this).findAndroidPlatform();
      } else {
        localPlatform = ((Companion)this).findJvmPlatform();
      }
      return localPlatform;
    }
    
    private final boolean isBouncyCastlePreferred()
    {
      Provider localProvider = java.security.Security.getProviders()[0];
      Intrinsics.checkNotNullExpressionValue(localProvider, "Security.getProviders()[0]");
      return Intrinsics.areEqual("BC", localProvider.getName());
    }
    
    private final boolean isConscryptPreferred()
    {
      Provider localProvider = java.security.Security.getProviders()[0];
      Intrinsics.checkNotNullExpressionValue(localProvider, "Security.getProviders()[0]");
      return Intrinsics.areEqual("Conscrypt", localProvider.getName());
    }
    
    private final boolean isOpenJSSEPreferred()
    {
      Provider localProvider = java.security.Security.getProviders()[0];
      Intrinsics.checkNotNullExpressionValue(localProvider, "Security.getProviders()[0]");
      return Intrinsics.areEqual("OpenJSSE", localProvider.getName());
    }
    
    public final List<String> alpnProtocolNames(List<? extends Protocol> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "protocols");
      Object localObject1 = (Iterable)paramList;
      paramList = (Collection)new ArrayList();
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = ((Iterator)localObject1).next();
        int i;
        if ((Protocol)localObject2 != Protocol.HTTP_1_0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          paramList.add(localObject2);
        }
      }
      localObject1 = (Iterable)paramList;
      paramList = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        paramList.add(((Protocol)((Iterator)localObject1).next()).toString());
      }
      paramList = (List)paramList;
      return paramList;
    }
    
    public final byte[] concatLengthPrefixed(List<? extends Protocol> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "protocols");
      Buffer localBuffer = new Buffer();
      paramList = ((Companion)this).alpnProtocolNames(paramList).iterator();
      while (paramList.hasNext())
      {
        String str = (String)paramList.next();
        localBuffer.writeByte(str.length());
        localBuffer.writeUtf8(str);
      }
      return localBuffer.readByteArray();
    }
    
    @JvmStatic
    public final Platform get()
    {
      return Platform.access$getPlatform$cp();
    }
    
    public final boolean isAndroid()
    {
      String str = System.getProperty("java.vm.name");
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return Intrinsics.areEqual("Dalvik", str);
    }
    
    public final void resetForTests(Platform paramPlatform)
    {
      Intrinsics.checkNotNullParameter(paramPlatform, "platform");
      Platform.access$setPlatform$cp(paramPlatform);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */