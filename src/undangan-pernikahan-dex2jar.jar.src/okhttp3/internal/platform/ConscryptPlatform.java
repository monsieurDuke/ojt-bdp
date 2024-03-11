package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Protocol;
import org.conscrypt.Conscrypt;
import org.conscrypt.Conscrypt.Version;
import org.conscrypt.ConscryptHostnameVerifier;

@Metadata(bv={1, 0, 3}, d1={"\000H\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\030\000 \0302\0020\001:\002\030\031B\007\b\002¢\006\002\020\002J-\020\005\032\0020\0062\006\020\007\032\0020\b2\b\020\t\032\004\030\0010\n2\021\020\013\032\r\022\t\022\0070\r¢\006\002\b\0160\fH\026J\022\020\017\032\004\030\0010\n2\006\020\007\032\0020\bH\026J\b\020\020\032\0020\021H\026J\020\020\022\032\0020\0232\006\020\024\032\0020\025H\026J\b\020\026\032\0020\025H\026J\022\020\024\032\004\030\0010\0252\006\020\027\032\0020\023H\026R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\032"}, d2={"Lokhttp3/internal/platform/ConscryptPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "platformTrustManager", "sslSocketFactory", "Companion", "DisabledHostnameVerifier", "okhttp"}, k=1, mv={1, 4, 0})
public final class ConscryptPlatform
  extends Platform
{
  public static final Companion Companion;
  private static final boolean isSupported;
  private final Provider provider;
  
  static
  {
    Companion localCompanion = new Companion(null);
    Companion = localCompanion;
    boolean bool1 = false;
    boolean bool2 = false;
    label56:
    try
    {
      Class.forName("org.conscrypt.Conscrypt$Version", false, localCompanion.getClass().getClassLoader());
      if (Conscrypt.isAvailable())
      {
        boolean bool3 = localCompanion.atLeastVersion(2, 1, 0);
        if (bool3)
        {
          bool1 = true;
          break label56;
        }
      }
      bool1 = bool2;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}catch (NoClassDefFoundError localNoClassDefFoundError) {}
    isSupported = bool1;
  }
  
  private ConscryptPlatform()
  {
    Provider localProvider = Conscrypt.newProvider();
    Intrinsics.checkNotNullExpressionValue(localProvider, "Conscrypt.newProvider()");
    this.provider = localProvider;
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    if (Conscrypt.isConscrypt(paramSSLSocket))
    {
      Conscrypt.setUseSessionTickets(paramSSLSocket, true);
      paramString = ((Collection)Platform.Companion.alpnProtocolNames(paramList)).toArray(new String[0]);
      if (paramString != null) {
        Conscrypt.setApplicationProtocols(paramSSLSocket, (String[])paramString);
      } else {
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
      }
    }
    else
    {
      super.configureTlsExtensions(paramSSLSocket, paramString, paramList);
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    if (Conscrypt.isConscrypt(paramSSLSocket))
    {
      paramSSLSocket = Conscrypt.getApplicationProtocol(paramSSLSocket);
      Log5ECF72.a(paramSSLSocket);
      LogE84000.a(paramSSLSocket);
      Log229316.a(paramSSLSocket);
    }
    else
    {
      paramSSLSocket = super.getSelectedProtocol(paramSSLSocket);
    }
    return paramSSLSocket;
  }
  
  public SSLContext newSSLContext()
  {
    SSLContext localSSLContext = SSLContext.getInstance("TLS", this.provider);
    Intrinsics.checkNotNullExpressionValue(localSSLContext, "SSLContext.getInstance(\"TLS\", provider)");
    return localSSLContext;
  }
  
  public SSLSocketFactory newSslSocketFactory(X509TrustManager paramX509TrustManager)
  {
    Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
    SSLContext localSSLContext = newSSLContext();
    localSSLContext.init(null, new TrustManager[] { (TrustManager)paramX509TrustManager }, null);
    paramX509TrustManager = localSSLContext.getSocketFactory();
    Intrinsics.checkNotNullExpressionValue(paramX509TrustManager, "newSSLContext().apply {\n…null)\n    }.socketFactory");
    return paramX509TrustManager;
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
    Intrinsics.checkNotNullExpressionValue(localObject2, "TrustManagerFactory.getI…(null as KeyStore?)\n    }");
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
      if (localObject1 != null)
      {
        localObject1 = (X509TrustManager)localObject1;
        Conscrypt.setHostnameVerifier((TrustManager)localObject1, (ConscryptHostnameVerifier)DisabledHostnameVerifier.INSTANCE);
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
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
    return null;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\b\n\002\b\003\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\"\020\006\032\0020\0042\006\020\007\032\0020\b2\b\b\002\020\t\032\0020\b2\b\b\002\020\n\032\0020\bJ\b\020\013\032\004\030\0010\fR\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\003\020\005¨\006\r"}, d2={"Lokhttp3/internal/platform/ConscryptPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "atLeastVersion", "major", "", "minor", "patch", "buildIfSupported", "Lokhttp3/internal/platform/ConscryptPlatform;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final boolean atLeastVersion(int paramInt1, int paramInt2, int paramInt3)
    {
      Conscrypt.Version localVersion = Conscrypt.version();
      int i = localVersion.major();
      boolean bool1 = true;
      boolean bool2 = true;
      boolean bool3 = true;
      if (i != paramInt1)
      {
        if (localVersion.major() > paramInt1) {
          bool1 = bool3;
        } else {
          bool1 = false;
        }
        return bool1;
      }
      if (localVersion.minor() != paramInt2)
      {
        if (localVersion.minor() <= paramInt2) {
          bool1 = false;
        }
        return bool1;
      }
      if (localVersion.patch() >= paramInt3) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    
    public final ConscryptPlatform buildIfSupported()
    {
      boolean bool = ((Companion)this).isSupported();
      ConscryptPlatform localConscryptPlatform = null;
      if (bool) {
        localConscryptPlatform = new ConscryptPlatform(null);
      }
      return localConscryptPlatform;
    }
    
    public final boolean isSupported()
    {
      return ConscryptPlatform.access$isSupported$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\021\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\002\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J3\020\003\032\0020\0042\020\020\005\032\f\022\006\b\001\022\0020\007\030\0010\0062\b\020\b\032\004\030\0010\t2\b\020\n\032\004\030\0010\013H\026¢\006\002\020\fJ\032\020\003\032\0020\0042\b\020\b\032\004\030\0010\t2\b\020\n\032\004\030\0010\013¨\006\r"}, d2={"Lokhttp3/internal/platform/ConscryptPlatform$DisabledHostnameVerifier;", "Lorg/conscrypt/ConscryptHostnameVerifier;", "()V", "verify", "", "certs", "", "Ljava/security/cert/X509Certificate;", "hostname", "", "session", "Ljavax/net/ssl/SSLSession;", "([Ljava/security/cert/X509Certificate;Ljava/lang/String;Ljavax/net/ssl/SSLSession;)Z", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class DisabledHostnameVerifier
    implements ConscryptHostnameVerifier
  {
    public static final DisabledHostnameVerifier INSTANCE = new DisabledHostnameVerifier();
    
    public final boolean verify(String paramString, SSLSession paramSSLSession)
    {
      return true;
    }
    
    public boolean verify(X509Certificate[] paramArrayOfX509Certificate, String paramString, SSLSession paramSSLSession)
    {
      return true;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/ConscryptPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */