package okhttp3.internal.platform;

import android.os.Build.VERSION;
import android.security.NetworkSecurityPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.android.Android10SocketAdapter;
import okhttp3.internal.platform.android.Android10SocketAdapter.Companion;
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner;
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner.Companion;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter.Companion;
import okhttp3.internal.platform.android.ConscryptSocketAdapter;
import okhttp3.internal.platform.android.ConscryptSocketAdapter.Companion;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;
import okhttp3.internal.tls.CertificateChainCleaner;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\002\b\007\030\000 \0272\0020\001:\001\027B\005¢\006\002\020\002J\020\020\006\032\0020\0072\006\020\b\032\0020\tH\026J(\020\n\032\0020\0132\006\020\f\032\0020\r2\b\020\016\032\004\030\0010\0172\f\020\020\032\b\022\004\022\0020\0210\004H\026J\022\020\022\032\004\030\0010\0172\006\020\f\032\0020\rH\026J\020\020\023\032\0020\0242\006\020\016\032\0020\017H\027J\022\020\b\032\004\030\0010\t2\006\020\025\032\0020\026H\026R\024\020\003\032\b\022\004\022\0020\0050\004X\004¢\006\002\n\000¨\006\030"}, d2={"Lokhttp3/internal/platform/Android10Platform;", "Lokhttp3/internal/platform/Platform;", "()V", "socketAdapters", "", "Lokhttp3/internal/platform/android/SocketAdapter;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "Lokhttp3/Protocol;", "getSelectedProtocol", "isCleartextTrafficPermitted", "", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class Android10Platform
  extends Platform
{
  public static final Companion Companion = new Companion(null);
  private static final boolean isSupported;
  private final List<SocketAdapter> socketAdapters;
  
  static
  {
    boolean bool;
    if ((Platform.Companion.isAndroid()) && (Build.VERSION.SDK_INT >= 29)) {
      bool = true;
    } else {
      bool = false;
    }
    isSupported = bool;
  }
  
  public Android10Platform()
  {
    Object localObject1 = (Iterable)CollectionsKt.listOfNotNull(new SocketAdapter[] { Android10SocketAdapter.Companion.buildIfSupported(), (SocketAdapter)new DeferredSocketAdapter(AndroidSocketAdapter.Companion.getPlayProviderFactory()), (SocketAdapter)new DeferredSocketAdapter(ConscryptSocketAdapter.Companion.getFactory()), (SocketAdapter)new DeferredSocketAdapter(BouncyCastleSocketAdapter.Companion.getFactory()) });
    Collection localCollection = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = ((Iterator)localObject1).next();
      if (((SocketAdapter)localObject2).isSupported()) {
        localCollection.add(localObject2);
      }
    }
    this.socketAdapters = ((List)localCollection);
  }
  
  public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager paramX509TrustManager)
  {
    Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
    AndroidCertificateChainCleaner localAndroidCertificateChainCleaner = AndroidCertificateChainCleaner.Companion.buildIfSupported(paramX509TrustManager);
    if (localAndroidCertificateChainCleaner != null) {
      paramX509TrustManager = (CertificateChainCleaner)localAndroidCertificateChainCleaner;
    } else {
      paramX509TrustManager = super.buildCertificateChainCleaner(paramX509TrustManager);
    }
    return paramX509TrustManager;
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    Iterator localIterator = ((Iterable)this.socketAdapters).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if (((SocketAdapter)localObject).matchesSocket(paramSSLSocket)) {
        break label65;
      }
    }
    Object localObject = null;
    label65:
    localObject = (SocketAdapter)localObject;
    if (localObject != null) {
      ((SocketAdapter)localObject).configureTlsExtensions(paramSSLSocket, paramString, paramList);
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Object localObject3 = ((Iterable)this.socketAdapters).iterator();
    Object localObject2;
    do
    {
      boolean bool = ((Iterator)localObject3).hasNext();
      localObject2 = null;
      if (!bool) {
        break;
      }
      localObject1 = ((Iterator)localObject3).next();
    } while (!((SocketAdapter)localObject1).matchesSocket(paramSSLSocket));
    break label61;
    Object localObject1 = null;
    label61:
    localObject3 = (SocketAdapter)localObject1;
    localObject1 = localObject2;
    if (localObject3 != null) {
      localObject1 = ((SocketAdapter)localObject3).getSelectedProtocol(paramSSLSocket);
    }
    return (String)localObject1;
  }
  
  public boolean isCleartextTrafficPermitted(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(paramString);
  }
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocketFactory, "sslSocketFactory");
    Iterator localIterator = ((Iterable)this.socketAdapters).iterator();
    Object localObject2;
    do
    {
      boolean bool = localIterator.hasNext();
      localObject2 = null;
      if (!bool) {
        break;
      }
      localObject1 = localIterator.next();
    } while (!((SocketAdapter)localObject1).matchesSocketFactory(paramSSLSocketFactory));
    break label61;
    Object localObject1 = null;
    label61:
    localObject1 = (SocketAdapter)localObject1;
    if (localObject1 != null) {
      paramSSLSocketFactory = ((SocketAdapter)localObject1).trustManager(paramSSLSocketFactory);
    } else {
      paramSSLSocketFactory = (SSLSocketFactory)localObject2;
    }
    return paramSSLSocketFactory;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\006\032\004\030\0010\007R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\003\020\005¨\006\b"}, d2={"Lokhttp3/internal/platform/Android10Platform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final Platform buildIfSupported()
    {
      Platform localPlatform;
      if (((Companion)this).isSupported()) {
        localPlatform = (Platform)new Android10Platform();
      } else {
        localPlatform = null;
      }
      return localPlatform;
    }
    
    public final boolean isSupported()
    {
      return Android10Platform.access$isSupported$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/Android10Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */