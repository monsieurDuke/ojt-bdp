package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Protocol;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCSSLSocket;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\030\000 \0272\0020\001:\001\027B\007\b\002¢\006\002\020\002J-\020\005\032\0020\0062\006\020\007\032\0020\b2\b\020\t\032\004\030\0010\n2\021\020\013\032\r\022\t\022\0070\r¢\006\002\b\0160\fH\026J\022\020\017\032\004\030\0010\n2\006\020\007\032\0020\bH\026J\b\020\020\032\0020\021H\026J\b\020\022\032\0020\023H\026J\022\020\024\032\004\030\0010\0232\006\020\025\032\0020\026H\026R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\030"}, d2={"Lokhttp3/internal/platform/BouncyCastlePlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "platformTrustManager", "Ljavax/net/ssl/X509TrustManager;", "trustManager", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class BouncyCastlePlatform
  extends Platform
{
  public static final Companion Companion;
  private static final boolean isSupported;
  private final Provider provider = (Provider)new BouncyCastleJsseProvider();
  
  static
  {
    Companion localCompanion = new Companion(null);
    Companion = localCompanion;
    boolean bool = false;
    try
    {
      Class.forName("org.bouncycastle.jsse.provider.BouncyCastleJsseProvider", false, localCompanion.getClass().getClassLoader());
      bool = true;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    isSupported = bool;
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    if ((paramSSLSocket instanceof BCSSLSocket))
    {
      paramString = ((BCSSLSocket)paramSSLSocket).getParameters();
      paramList = Platform.Companion.alpnProtocolNames(paramList);
      Intrinsics.checkNotNullExpressionValue(paramString, "sslParameters");
      paramList = ((Collection)paramList).toArray(new String[0]);
      if (paramList != null)
      {
        paramString.setApplicationProtocols((String[])paramList);
        ((BCSSLSocket)paramSSLSocket).setParameters(paramString);
      }
      else
      {
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
    if ((paramSSLSocket instanceof BCSSLSocket))
    {
      String str = ((BCSSLSocket)paramSSLSocket).getApplicationProtocol();
      if (str != null)
      {
        int i = str.hashCode();
        paramSSLSocket = str;
        switch (i)
        {
        default: 
          break;
        case 0: 
          if (!str.equals("")) {
            break;
          }
        }
      }
      else
      {
        return null;
      }
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
  
  public X509TrustManager platformTrustManager()
  {
    Object localObject1 = TrustManagerFactory.getInstance("PKIX", "BCJSSE");
    Object localObject2 = (KeyStore)null;
    ((TrustManagerFactory)localObject1).init(null);
    Intrinsics.checkNotNullExpressionValue(localObject1, "factory");
    localObject2 = ((TrustManagerFactory)localObject1).getTrustManagers();
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
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
    throw ((Throwable)new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with BouncyCastle"));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\006\032\004\030\0010\007R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\003\020\005¨\006\b"}, d2={"Lokhttp3/internal/platform/BouncyCastlePlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/BouncyCastlePlatform;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final BouncyCastlePlatform buildIfSupported()
    {
      boolean bool = ((Companion)this).isSupported();
      BouncyCastlePlatform localBouncyCastlePlatform = null;
      if (bool) {
        localBouncyCastlePlatform = new BouncyCastlePlatform(null);
      }
      return localBouncyCastlePlatform;
    }
    
    public final boolean isSupported()
    {
      return BouncyCastlePlatform.access$isSupported$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/BouncyCastlePlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */