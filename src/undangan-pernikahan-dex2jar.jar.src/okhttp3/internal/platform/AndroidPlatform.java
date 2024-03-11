package okhttp3.internal.platform;

import android.os.Build.VERSION;
import android.security.NetworkSecurityPolicy;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
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
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner;
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner.Companion;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter.Companion;
import okhttp3.internal.platform.android.CloseGuard;
import okhttp3.internal.platform.android.CloseGuard.Companion;
import okhttp3.internal.platform.android.ConscryptSocketAdapter;
import okhttp3.internal.platform.android.ConscryptSocketAdapter.Companion;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;
import okhttp3.internal.platform.android.StandardAndroidSocketAdapter;
import okhttp3.internal.platform.android.StandardAndroidSocketAdapter.Companion;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

@Metadata(bv={1, 0, 3}, d1={"\000x\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\003\b\007\030\000 )2\0020\001:\002)*B\005¢\006\002\020\002J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\020\020\f\032\0020\r2\006\020\n\032\0020\013H\026J-\020\016\032\0020\0172\006\020\020\032\0020\0212\b\020\022\032\004\030\0010\0232\021\020\024\032\r\022\t\022\0070\025¢\006\002\b\0260\006H\026J \020\027\032\0020\0172\006\020\030\032\0020\0312\006\020\032\032\0020\0332\006\020\034\032\0020\035H\026J\022\020\036\032\004\030\0010\0232\006\020\020\032\0020\021H\026J\022\020\037\032\004\030\0010 2\006\020!\032\0020\023H\026J\020\020\"\032\0020#2\006\020\022\032\0020\023H\026J\032\020$\032\0020\0172\006\020%\032\0020\0232\b\020&\032\004\030\0010 H\026J\022\020\n\032\004\030\0010\0132\006\020'\032\0020(H\026R\016\020\003\032\0020\004X\004¢\006\002\n\000R\024\020\005\032\b\022\004\022\0020\0070\006X\004¢\006\002\n\000¨\006+"}, d2={"Lokhttp3/internal/platform/AndroidPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "closeGuard", "Lokhttp3/internal/platform/android/CloseGuard;", "socketAdapters", "", "Lokhttp3/internal/platform/android/SocketAdapter;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "connectSocket", "socket", "Ljava/net/Socket;", "address", "Ljava/net/InetSocketAddress;", "connectTimeout", "", "getSelectedProtocol", "getStackTraceForCloseable", "", "closer", "isCleartextTrafficPermitted", "", "logCloseableLeak", "message", "stackTrace", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "CustomTrustRootIndex", "okhttp"}, k=1, mv={1, 4, 0})
public final class AndroidPlatform
  extends Platform
{
  public static final Companion Companion = new Companion(null);
  private static final boolean isSupported;
  private final CloseGuard closeGuard;
  private final List<SocketAdapter> socketAdapters;
  
  static
  {
    boolean bool2 = Platform.Companion.isAndroid();
    boolean bool1 = true;
    int i = 0;
    if (!bool2)
    {
      bool1 = false;
    }
    else if (Build.VERSION.SDK_INT >= 30)
    {
      bool1 = false;
    }
    else
    {
      if (Build.VERSION.SDK_INT >= 21) {
        i = 1;
      }
      if (i == 0) {
        break label63;
      }
    }
    isSupported = bool1;
    return;
    label63:
    throw ((Throwable)new IllegalStateException(("Expected Android API level 21+ but was " + Build.VERSION.SDK_INT).toString()));
  }
  
  public AndroidPlatform()
  {
    Object localObject1 = (Iterable)CollectionsKt.listOfNotNull(new SocketAdapter[] { StandardAndroidSocketAdapter.Companion.buildIfSupported$default(StandardAndroidSocketAdapter.Companion, null, 1, null), (SocketAdapter)new DeferredSocketAdapter(AndroidSocketAdapter.Companion.getPlayProviderFactory()), (SocketAdapter)new DeferredSocketAdapter(ConscryptSocketAdapter.Companion.getFactory()), (SocketAdapter)new DeferredSocketAdapter(BouncyCastleSocketAdapter.Companion.getFactory()) });
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
    this.closeGuard = CloseGuard.Companion.get();
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
  
  public TrustRootIndex buildTrustRootIndex(X509TrustManager paramX509TrustManager)
  {
    Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
    try
    {
      Object localObject = paramX509TrustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[] { X509Certificate.class });
      Intrinsics.checkNotNullExpressionValue(localObject, "method");
      ((Method)localObject).setAccessible(true);
      CustomTrustRootIndex localCustomTrustRootIndex = new okhttp3/internal/platform/AndroidPlatform$CustomTrustRootIndex;
      localCustomTrustRootIndex.<init>(paramX509TrustManager, (Method)localObject);
      localObject = (TrustRootIndex)localCustomTrustRootIndex;
      paramX509TrustManager = (X509TrustManager)localObject;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      paramX509TrustManager = super.buildTrustRootIndex(paramX509TrustManager);
    }
    return paramX509TrustManager;
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    Iterator localIterator = ((Iterable)this.socketAdapters).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if (((SocketAdapter)localObject).matchesSocket(paramSSLSocket)) {
        break label67;
      }
    }
    Object localObject = null;
    label67:
    localObject = (SocketAdapter)localObject;
    if (localObject != null) {
      ((SocketAdapter)localObject).configureTlsExtensions(paramSSLSocket, paramString, paramList);
    }
  }
  
  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramSocket, "socket");
    Intrinsics.checkNotNullParameter(paramInetSocketAddress, "address");
    try
    {
      paramSocket.connect((SocketAddress)paramInetSocketAddress, paramInt);
      return;
    }
    catch (ClassCastException paramSocket)
    {
      if (Build.VERSION.SDK_INT == 26) {
        throw ((Throwable)new IOException("Exception in connect", (Throwable)paramSocket));
      }
      throw ((Throwable)paramSocket);
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
    break label62;
    Object localObject1 = null;
    label62:
    localObject3 = (SocketAdapter)localObject1;
    localObject1 = localObject2;
    if (localObject3 != null) {
      localObject1 = ((SocketAdapter)localObject3).getSelectedProtocol(paramSSLSocket);
    }
    return (String)localObject1;
  }
  
  public Object getStackTraceForCloseable(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "closer");
    return this.closeGuard.createAndOpen(paramString);
  }
  
  public boolean isCleartextTrafficPermitted(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24)
    {
      bool = NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(paramString);
    }
    else if (Build.VERSION.SDK_INT >= 23)
    {
      paramString = NetworkSecurityPolicy.getInstance();
      Intrinsics.checkNotNullExpressionValue(paramString, "NetworkSecurityPolicy.getInstance()");
      bool = paramString.isCleartextTrafficPermitted();
    }
    else
    {
      bool = true;
    }
    return bool;
  }
  
  public void logCloseableLeak(String paramString, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramString, "message");
    if (!this.closeGuard.warnIfOpen(paramObject)) {
      Platform.log$default(this, paramString, 5, null, 4, null);
    }
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
    break label62;
    Object localObject1 = null;
    label62:
    localObject1 = (SocketAdapter)localObject1;
    if (localObject1 != null) {
      paramSSLSocketFactory = ((SocketAdapter)localObject1).trustManager(paramSSLSocketFactory);
    } else {
      paramSSLSocketFactory = (SSLSocketFactory)localObject2;
    }
    return paramSSLSocketFactory;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\006\032\004\030\0010\007R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\003\020\005¨\006\b"}, d2={"Lokhttp3/internal/platform/AndroidPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final Platform buildIfSupported()
    {
      Platform localPlatform;
      if (((Companion)this).isSupported()) {
        localPlatform = (Platform)new AndroidPlatform();
      } else {
        localPlatform = null;
      }
      return localPlatform;
    }
    
    public final boolean isSupported()
    {
      return AndroidPlatform.access$isSupported$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\000\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\b\b\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\t\020\007\032\0020\003HÂ\003J\t\020\b\032\0020\005HÂ\003J\035\020\t\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\005HÆ\001J\023\020\n\032\0020\0132\b\020\f\032\004\030\0010\rHÖ\003J\022\020\016\032\004\030\0010\0172\006\020\020\032\0020\017H\026J\t\020\021\032\0020\022HÖ\001J\t\020\023\032\0020\024HÖ\001R\016\020\004\032\0020\005X\004¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\025"}, d2={"Lokhttp3/internal/platform/AndroidPlatform$CustomTrustRootIndex;", "Lokhttp3/internal/tls/TrustRootIndex;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "findByIssuerAndSignatureMethod", "Ljava/lang/reflect/Method;", "(Ljavax/net/ssl/X509TrustManager;Ljava/lang/reflect/Method;)V", "component1", "component2", "copy", "equals", "", "other", "", "findByIssuerAndSignature", "Ljava/security/cert/X509Certificate;", "cert", "hashCode", "", "toString", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class CustomTrustRootIndex
    implements TrustRootIndex
  {
    private final Method findByIssuerAndSignatureMethod;
    private final X509TrustManager trustManager;
    
    public CustomTrustRootIndex(X509TrustManager paramX509TrustManager, Method paramMethod)
    {
      this.trustManager = paramX509TrustManager;
      this.findByIssuerAndSignatureMethod = paramMethod;
    }
    
    private final X509TrustManager component1()
    {
      return this.trustManager;
    }
    
    private final Method component2()
    {
      return this.findByIssuerAndSignatureMethod;
    }
    
    public final CustomTrustRootIndex copy(X509TrustManager paramX509TrustManager, Method paramMethod)
    {
      Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
      Intrinsics.checkNotNullParameter(paramMethod, "findByIssuerAndSignatureMethod");
      return new CustomTrustRootIndex(paramX509TrustManager, paramMethod);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this != paramObject) {
        if ((paramObject instanceof CustomTrustRootIndex))
        {
          paramObject = (CustomTrustRootIndex)paramObject;
          if ((Intrinsics.areEqual(this.trustManager, ((CustomTrustRootIndex)paramObject).trustManager)) && (Intrinsics.areEqual(this.findByIssuerAndSignatureMethod, ((CustomTrustRootIndex)paramObject).findByIssuerAndSignatureMethod))) {}
        }
        else
        {
          return false;
        }
      }
      return true;
    }
    
    public X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate)
    {
      Intrinsics.checkNotNullParameter(paramX509Certificate, "cert");
      try
      {
        paramX509Certificate = this.findByIssuerAndSignatureMethod.invoke(this.trustManager, new Object[] { paramX509Certificate });
        if (paramX509Certificate != null)
        {
          paramX509Certificate = ((TrustAnchor)paramX509Certificate).getTrustedCert();
        }
        else
        {
          paramX509Certificate = new java/lang/NullPointerException;
          paramX509Certificate.<init>("null cannot be cast to non-null type java.security.cert.TrustAnchor");
          throw paramX509Certificate;
        }
      }
      catch (InvocationTargetException paramX509Certificate)
      {
        paramX509Certificate = null;
        return paramX509Certificate;
      }
      catch (IllegalAccessException paramX509Certificate)
      {
        throw ((Throwable)new AssertionError("unable to get issues and signature", (Throwable)paramX509Certificate));
      }
    }
    
    public int hashCode()
    {
      Object localObject = this.trustManager;
      int j = 0;
      int i;
      if (localObject != null) {
        i = localObject.hashCode();
      } else {
        i = 0;
      }
      localObject = this.findByIssuerAndSignatureMethod;
      if (localObject != null) {
        j = localObject.hashCode();
      }
      return i * 31 + j;
    }
    
    public String toString()
    {
      return "CustomTrustRootIndex(trustManager=" + this.trustManager + ", findByIssuerAndSignatureMethod=" + this.findByIssuerAndSignatureMethod + ")";
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/AndroidPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */