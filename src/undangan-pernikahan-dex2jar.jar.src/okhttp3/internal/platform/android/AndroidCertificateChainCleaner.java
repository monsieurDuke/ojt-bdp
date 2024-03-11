package okhttp3.internal.platform.android;

import android.net.http.X509TrustManagerExtensions;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.tls.CertificateChainCleaner;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\b\000\030\000 \0232\0020\001:\001\023B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J$\020\007\032\b\022\004\022\0020\t0\b2\f\020\n\032\b\022\004\022\0020\t0\b2\006\020\013\032\0020\fH\027J\023\020\r\032\0020\0162\b\020\017\032\004\030\0010\020H\002J\b\020\021\032\0020\022H\026R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\024"}, d2={"Lokhttp3/internal/platform/android/AndroidCertificateChainCleaner;", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "x509TrustManagerExtensions", "Landroid/net/http/X509TrustManagerExtensions;", "(Ljavax/net/ssl/X509TrustManager;Landroid/net/http/X509TrustManagerExtensions;)V", "clean", "", "Ljava/security/cert/Certificate;", "chain", "hostname", "", "equals", "", "other", "", "hashCode", "", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class AndroidCertificateChainCleaner
  extends CertificateChainCleaner
{
  public static final Companion Companion = new Companion(null);
  private final X509TrustManager trustManager;
  private final X509TrustManagerExtensions x509TrustManagerExtensions;
  
  public AndroidCertificateChainCleaner(X509TrustManager paramX509TrustManager, X509TrustManagerExtensions paramX509TrustManagerExtensions)
  {
    this.trustManager = paramX509TrustManager;
    this.x509TrustManagerExtensions = paramX509TrustManagerExtensions;
  }
  
  public List<Certificate> clean(List<? extends Certificate> paramList, String paramString)
    throws SSLPeerUnverifiedException
  {
    Intrinsics.checkNotNullParameter(paramList, "chain");
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    paramList = ((Collection)paramList).toArray(new X509Certificate[0]);
    if (paramList != null)
    {
      paramList = (X509Certificate[])paramList;
      try
      {
        paramList = this.x509TrustManagerExtensions.checkServerTrusted(paramList, "RSA", paramString);
        Intrinsics.checkNotNullExpressionValue(paramList, "x509TrustManagerExtensio…ficates, \"RSA\", hostname)");
        return paramList;
      }
      catch (CertificateException paramList)
      {
        paramString = new SSLPeerUnverifiedException(paramList.getMessage());
        paramString.initCause((Throwable)paramList);
        throw ((Throwable)paramString);
      }
    }
    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof AndroidCertificateChainCleaner)) && (((AndroidCertificateChainCleaner)paramObject).trustManager == this.trustManager)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return System.identityHashCode(this.trustManager);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\022\020\003\032\004\030\0010\0042\006\020\005\032\0020\006H\007¨\006\007"}, d2={"Lokhttp3/internal/platform/android/AndroidCertificateChainCleaner$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/android/AndroidCertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final AndroidCertificateChainCleaner buildIfSupported(X509TrustManager paramX509TrustManager)
    {
      Intrinsics.checkNotNullParameter(paramX509TrustManager, "trustManager");
      Object localObject = null;
      X509TrustManagerExtensions localX509TrustManagerExtensions2;
      try
      {
        X509TrustManagerExtensions localX509TrustManagerExtensions1 = new android/net/http/X509TrustManagerExtensions;
        localX509TrustManagerExtensions1.<init>(paramX509TrustManager);
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localX509TrustManagerExtensions2 = null;
      }
      if (localX509TrustManagerExtensions2 != null) {
        paramX509TrustManager = new AndroidCertificateChainCleaner(paramX509TrustManager, localX509TrustManagerExtensions2);
      } else {
        paramX509TrustManager = (X509TrustManager)localObject;
      }
      return paramX509TrustManager;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/AndroidCertificateChainCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */