package okhttp3.internal.platform.android;

import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.BouncyCastlePlatform;
import okhttp3.internal.platform.BouncyCastlePlatform.Companion;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCSSLSocket;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\030\000 \0202\0020\001:\001\020B\005¢\006\002\020\002J(\020\003\032\0020\0042\006\020\005\032\0020\0062\b\020\007\032\004\030\0010\b2\f\020\t\032\b\022\004\022\0020\0130\nH\026J\022\020\f\032\004\030\0010\b2\006\020\005\032\0020\006H\026J\b\020\r\032\0020\016H\026J\020\020\017\032\0020\0162\006\020\005\032\0020\006H\026¨\006\021"}, d2={"Lokhttp3/internal/platform/android/BouncyCastleSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "()V", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class BouncyCastleSocketAdapter
  implements SocketAdapter
{
  public static final Companion Companion = new Companion(null);
  private static final DeferredSocketAdapter.Factory factory = (DeferredSocketAdapter.Factory)new BouncyCastleSocketAdapter.Companion.factory.1();
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    if (matchesSocket(paramSSLSocket))
    {
      paramString = (BCSSLSocket)paramSSLSocket;
      paramSSLSocket = paramString.getParameters();
      Intrinsics.checkNotNullExpressionValue(paramSSLSocket, "sslParameters");
      paramList = ((Collection)Platform.Companion.alpnProtocolNames(paramList)).toArray(new String[0]);
      if (paramList != null)
      {
        paramSSLSocket.setApplicationProtocols((String[])paramList);
        paramString.setParameters(paramSSLSocket);
      }
      else
      {
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
      }
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
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
      paramSSLSocket = null;
    }
    return paramSSLSocket;
  }
  
  public boolean isSupported()
  {
    return BouncyCastlePlatform.Companion.isSupported();
  }
  
  public boolean matchesSocket(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    return paramSSLSocket instanceof BCSSLSocket;
  }
  
  public boolean matchesSocketFactory(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
    return SocketAdapter.DefaultImpls.matchesSocketFactory(this, paramSSLSocketFactory);
  }
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
    return SocketAdapter.DefaultImpls.trustManager(this, paramSSLSocketFactory);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006¨\006\007"}, d2={"Lokhttp3/internal/platform/android/BouncyCastleSocketAdapter$Companion;", "", "()V", "factory", "Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "getFactory", "()Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final DeferredSocketAdapter.Factory getFactory()
    {
      return BouncyCastleSocketAdapter.access$getFactory$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/BouncyCastleSocketAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */