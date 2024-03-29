package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Protocol;
import org.openjsse.javax.net.ssl.SSLParameters;
import org.openjsse.net.ssl.OpenJSSE;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\030\000 \0272\0020\001:\001\027B\007\b\002¢\006\002\020\002J-\020\005\032\0020\0062\006\020\007\032\0020\b2\b\020\t\032\004\030\0010\n2\021\020\013\032\r\022\t\022\0070\r¢\006\002\b\0160\fH\026J\022\020\017\032\004\030\0010\n2\006\020\007\032\0020\bH\026J\b\020\020\032\0020\021H\026J\b\020\022\032\0020\023H\026J\022\020\024\032\004\030\0010\0232\006\020\025\032\0020\026H\026R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\030"}, d2={"Lokhttp3/internal/platform/OpenJSSEPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "platformTrustManager", "Ljavax/net/ssl/X509TrustManager;", "trustManager", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class OpenJSSEPlatform
  extends Platform
{
  public static final Companion Companion;
  private static final boolean isSupported;
  private final Provider provider = (Provider)new OpenJSSE();
  
  static
  {
    Companion localCompanion = new Companion(null);
    Companion = localCompanion;
    boolean bool = false;
    try
    {
      Class.forName("org.openjsse.net.ssl.OpenJSSE", false, localCompanion.getClass().getClassLoader());
      bool = true;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    isSupported = bool;
  }
  
  public void configureTlsExtensions(javax.net.ssl.SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    if ((paramSSLSocket instanceof org.openjsse.javax.net.ssl.SSLSocket))
    {
      paramString = ((org.openjsse.javax.net.ssl.SSLSocket)paramSSLSocket).getSSLParameters();
      if ((paramString instanceof SSLParameters))
      {
        Object localObject = Platform.Companion.alpnProtocolNames(paramList);
        paramList = (SSLParameters)paramString;
        localObject = ((Collection)localObject).toArray(new String[0]);
        if (localObject != null)
        {
          paramList.setApplicationProtocols((String[])localObject);
          ((org.openjsse.javax.net.ssl.SSLSocket)paramSSLSocket).setSSLParameters(paramString);
        }
        else
        {
          throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
      }
    }
    else
    {
      super.configureTlsExtensions(paramSSLSocket, paramString, paramList);
    }
  }
  
  public String getSelectedProtocol(javax.net.ssl.SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    if ((paramSSLSocket instanceof org.openjsse.javax.net.ssl.SSLSocket))
    {
      String str = ((org.openjsse.javax.net.ssl.SSLSocket)paramSSLSocket).getApplicationProtocol();
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
    SSLContext localSSLContext = SSLContext.getInstance("TLSv1.3", this.provider);
    Intrinsics.checkNotNullExpressionValue(localSSLContext, "SSLContext.getInstance(\"TLSv1.3\", provider)");
    return localSSLContext;
  }
  
  public X509TrustManager platformTrustManager()
  {
    Object localObject1 = TrustManagerFactory.getDefaultAlgorithm();
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    localObject1 = TrustManagerFactory.getInstance((String)localObject1, this.provider);
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
    throw ((Throwable)new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with OpenJSSE"));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\006\032\004\030\0010\007R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\003\020\005¨\006\b"}, d2={"Lokhttp3/internal/platform/OpenJSSEPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/OpenJSSEPlatform;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final OpenJSSEPlatform buildIfSupported()
    {
      boolean bool = ((Companion)this).isSupported();
      OpenJSSEPlatform localOpenJSSEPlatform = null;
      if (bool) {
        localOpenJSSEPlatform = new OpenJSSEPlatform(null);
      }
      return localOpenJSSEPlatform;
    }
    
    public final boolean isSupported()
    {
      return OpenJSSEPlatform.access$isSupported$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/OpenJSSEPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */