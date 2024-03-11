package okhttp3.internal.platform.android;

import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\003\030\0002\0020\001:\001\024B\r\022\006\020\002\032\0020\003¢\006\002\020\004J(\020\006\032\0020\0072\006\020\b\032\0020\t2\b\020\n\032\004\030\0010\0132\f\020\f\032\b\022\004\022\0020\0160\rH\026J\022\020\017\032\004\030\0010\0012\006\020\b\032\0020\tH\002J\022\020\020\032\004\030\0010\0132\006\020\b\032\0020\tH\026J\b\020\021\032\0020\022H\026J\020\020\023\032\0020\0222\006\020\b\032\0020\tH\026R\020\020\005\032\004\030\0010\001X\016¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\025"}, d2={"Lokhttp3/internal/platform/android/DeferredSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "socketAdapterFactory", "Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "(Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;)V", "delegate", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getDelegate", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Factory", "okhttp"}, k=1, mv={1, 4, 0})
public final class DeferredSocketAdapter
  implements SocketAdapter
{
  private SocketAdapter delegate;
  private final Factory socketAdapterFactory;
  
  public DeferredSocketAdapter(Factory paramFactory)
  {
    this.socketAdapterFactory = paramFactory;
  }
  
  private final SocketAdapter getDelegate(SSLSocket paramSSLSocket)
  {
    try
    {
      if ((this.delegate == null) && (this.socketAdapterFactory.matchesSocket(paramSSLSocket))) {
        this.delegate = this.socketAdapterFactory.create(paramSSLSocket);
      }
      paramSSLSocket = this.delegate;
      return paramSSLSocket;
    }
    finally {}
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    SocketAdapter localSocketAdapter = getDelegate(paramSSLSocket);
    if (localSocketAdapter != null) {
      localSocketAdapter.configureTlsExtensions(paramSSLSocket, paramString, paramList);
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    SocketAdapter localSocketAdapter = getDelegate(paramSSLSocket);
    if (localSocketAdapter != null) {
      paramSSLSocket = localSocketAdapter.getSelectedProtocol(paramSSLSocket);
    } else {
      paramSSLSocket = null;
    }
    return paramSSLSocket;
  }
  
  public boolean isSupported()
  {
    return true;
  }
  
  public boolean matchesSocket(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    return this.socketAdapterFactory.matchesSocket(paramSSLSocket);
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
  
  @Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&J\020\020\006\032\0020\0072\006\020\004\032\0020\005H&¨\006\b"}, d2={"Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "", "create", "Lokhttp3/internal/platform/android/SocketAdapter;", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "matchesSocket", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static abstract interface Factory
  {
    public abstract SocketAdapter create(SSLSocket paramSSLSocket);
    
    public abstract boolean matchesSocket(SSLSocket paramSSLSocket);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/DeferredSocketAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */