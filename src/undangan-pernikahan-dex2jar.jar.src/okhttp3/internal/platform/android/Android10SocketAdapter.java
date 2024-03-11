package okhttp3.internal.platform.android;

import android.net.ssl.SSLSockets;
import android.os.Build.VERSION;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\b\007\030\000 \0202\0020\001:\001\020B\005¢\006\002\020\002J(\020\003\032\0020\0042\006\020\005\032\0020\0062\b\020\007\032\004\030\0010\b2\f\020\t\032\b\022\004\022\0020\0130\nH\027J\022\020\f\032\004\030\0010\b2\006\020\005\032\0020\006H\027J\b\020\r\032\0020\016H\026J\020\020\017\032\0020\0162\006\020\005\032\0020\006H\026¨\006\021"}, d2={"Lokhttp3/internal/platform/android/Android10SocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "()V", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class Android10SocketAdapter
  implements SocketAdapter
{
  public static final Companion Companion = new Companion(null);
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    try
    {
      SSLSockets.setUseSessionTickets(paramSSLSocket, true);
      paramString = paramSSLSocket.getSSLParameters();
      Intrinsics.checkNotNullExpressionValue(paramString, "sslParameters");
      paramList = ((Collection)Platform.Companion.alpnProtocolNames(paramList)).toArray(new String[0]);
      if (paramList != null)
      {
        paramString.setApplicationProtocols((String[])paramList);
        paramSSLSocket.setSSLParameters(paramString);
        return;
      }
      paramSSLSocket = new java/lang/NullPointerException;
      paramSSLSocket.<init>("null cannot be cast to non-null type kotlin.Array<T>");
      throw paramSSLSocket;
    }
    catch (IllegalArgumentException paramSSLSocket)
    {
      throw ((Throwable)new IOException("Android internal error", (Throwable)paramSSLSocket));
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    String str = paramSSLSocket.getApplicationProtocol();
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
    return Companion.isSupported();
  }
  
  public boolean matchesSocket(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    return SSLSockets.isSupportedSocket(paramSSLSocket);
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
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\003\032\004\030\0010\004J\006\020\005\032\0020\006¨\006\007"}, d2={"Lokhttp3/internal/platform/android/Android10SocketAdapter$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/android/SocketAdapter;", "isSupported", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final SocketAdapter buildIfSupported()
    {
      SocketAdapter localSocketAdapter;
      if (((Companion)this).isSupported()) {
        localSocketAdapter = (SocketAdapter)new Android10SocketAdapter();
      } else {
        localSocketAdapter = null;
      }
      return localSocketAdapter;
    }
    
    public final boolean isSupported()
    {
      boolean bool;
      if ((Platform.Companion.isAndroid()) && (Build.VERSION.SDK_INT >= 29)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/Android10SocketAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */