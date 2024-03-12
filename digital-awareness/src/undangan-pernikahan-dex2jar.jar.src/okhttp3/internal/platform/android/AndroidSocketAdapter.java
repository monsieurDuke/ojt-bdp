package okhttp3.internal.platform.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Protocol;
import okhttp3.internal.platform.AndroidPlatform;
import okhttp3.internal.platform.AndroidPlatform.Companion;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\002\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\b\026\030\000 \0302\0020\001:\001\030B\025\022\016\020\002\032\n\022\006\b\000\022\0020\0040\003¢\006\002\020\005J(\020\f\032\0020\r2\006\020\016\032\0020\0042\b\020\017\032\004\030\0010\0202\f\020\021\032\b\022\004\022\0020\0230\022H\026J\022\020\024\032\004\030\0010\0202\006\020\016\032\0020\004H\026J\b\020\025\032\0020\026H\026J\020\020\027\032\0020\0262\006\020\016\032\0020\004H\026R\026\020\006\032\n \b*\004\030\0010\0070\007X\004¢\006\002\n\000R\026\020\t\032\n \b*\004\030\0010\0070\007X\004¢\006\002\n\000R\026\020\n\032\n \b*\004\030\0010\0070\007X\004¢\006\002\n\000R\016\020\013\032\0020\007X\004¢\006\002\n\000R\026\020\002\032\n\022\006\b\000\022\0020\0040\003X\004¢\006\002\n\000¨\006\031"}, d2={"Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "sslSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "(Ljava/lang/Class;)V", "getAlpnSelectedProtocol", "Ljava/lang/reflect/Method;", "kotlin.jvm.PlatformType", "setAlpnProtocols", "setHostname", "setUseSessionTickets", "configureTlsExtensions", "", "sslSocket", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public class AndroidSocketAdapter
  implements SocketAdapter
{
  public static final Companion Companion;
  private static final DeferredSocketAdapter.Factory playProviderFactory;
  private final Method getAlpnSelectedProtocol;
  private final Method setAlpnProtocols;
  private final Method setHostname;
  private final Method setUseSessionTickets;
  private final Class<? super SSLSocket> sslSocketClass;
  
  static
  {
    Companion localCompanion = new Companion(null);
    Companion = localCompanion;
    playProviderFactory = localCompanion.factory("com.google.android.gms.org.conscrypt");
  }
  
  public AndroidSocketAdapter(Class<? super SSLSocket> paramClass)
  {
    this.sslSocketClass = paramClass;
    Method localMethod = paramClass.getDeclaredMethod("setUseSessionTickets", new Class[] { Boolean.TYPE });
    Intrinsics.checkNotNullExpressionValue(localMethod, "sslSocketClass.getDeclar…:class.javaPrimitiveType)");
    this.setUseSessionTickets = localMethod;
    this.setHostname = paramClass.getMethod("setHostname", new Class[] { String.class });
    this.getAlpnSelectedProtocol = paramClass.getMethod("getAlpnSelectedProtocol", new Class[0]);
    this.setAlpnProtocols = paramClass.getMethod("setAlpnProtocols", new Class[] { byte[].class });
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    if (matchesSocket(paramSSLSocket)) {
      try
      {
        this.setUseSessionTickets.invoke(paramSSLSocket, new Object[] { Boolean.valueOf(true) });
        if (paramString != null) {
          this.setHostname.invoke(paramSSLSocket, new Object[] { paramString });
        }
        this.setAlpnProtocols.invoke(paramSSLSocket, new Object[] { Platform.Companion.concatLengthPrefixed(paramList) });
      }
      catch (InvocationTargetException paramSSLSocket)
      {
        throw ((Throwable)new AssertionError(paramSSLSocket));
      }
      catch (IllegalAccessException paramSSLSocket)
      {
        throw ((Throwable)new AssertionError(paramSSLSocket));
      }
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    boolean bool = matchesSocket(paramSSLSocket);
    Object localObject = null;
    if (!bool) {
      return null;
    }
    try
    {
      byte[] arrayOfByte = (byte[])this.getAlpnSelectedProtocol.invoke(paramSSLSocket, new Object[0]);
      paramSSLSocket = (SSLSocket)localObject;
      if (arrayOfByte == null) {
        break label129;
      }
      Charset localCharset = StandardCharsets.UTF_8;
      Intrinsics.checkNotNullExpressionValue(localCharset, "StandardCharsets.UTF_8");
      paramSSLSocket = new java/lang/String;
      paramSSLSocket.<init>(arrayOfByte, localCharset);
      Log5ECF72.a(paramSSLSocket);
      LogE84000.a(paramSSLSocket);
      Log229316.a(paramSSLSocket);
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError(paramSSLSocket));
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError(paramSSLSocket));
    }
    catch (NullPointerException paramSSLSocket)
    {
      if (!Intrinsics.areEqual(paramSSLSocket.getMessage(), "ssl == null")) {
        break label131;
      }
    }
    paramSSLSocket = (String)null;
    paramSSLSocket = (SSLSocket)localObject;
    label129:
    return paramSSLSocket;
    label131:
    throw ((Throwable)paramSSLSocket);
  }
  
  public boolean isSupported()
  {
    return AndroidPlatform.Companion.isSupported();
  }
  
  public boolean matchesSocket(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    return this.sslSocketClass.isInstance(paramSSLSocket);
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
  
  @Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\030\020\007\032\0020\b2\016\020\t\032\n\022\006\b\000\022\0020\0130\nH\002J\016\020\f\032\0020\0042\006\020\r\032\0020\016R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006¨\006\017"}, d2={"Lokhttp3/internal/platform/android/AndroidSocketAdapter$Companion;", "", "()V", "playProviderFactory", "Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "getPlayProviderFactory", "()Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "build", "Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "actualSSLSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "factory", "packageName", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final AndroidSocketAdapter build(Class<? super SSLSocket> paramClass)
    {
      Object localObject = paramClass;
      while ((localObject != null) && ((Intrinsics.areEqual(((Class)localObject).getSimpleName(), "OpenSSLSocketImpl") ^ true)))
      {
        localObject = ((Class)localObject).getSuperclass();
        if (localObject == null) {
          throw ((Throwable)new AssertionError("No OpenSSLSocketImpl superclass of socket of type " + paramClass));
        }
      }
      Intrinsics.checkNotNull(localObject);
      return new AndroidSocketAdapter((Class)localObject);
    }
    
    public final DeferredSocketAdapter.Factory factory(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "packageName");
      (DeferredSocketAdapter.Factory)new DeferredSocketAdapter.Factory()
      {
        final String $packageName;
        
        public SocketAdapter create(SSLSocket paramAnonymousSSLSocket)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousSSLSocket, "sslSocket");
          return (SocketAdapter)AndroidSocketAdapter.Companion.access$build(AndroidSocketAdapter.Companion, paramAnonymousSSLSocket.getClass());
        }
        
        public boolean matchesSocket(SSLSocket paramAnonymousSSLSocket)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousSSLSocket, "sslSocket");
          paramAnonymousSSLSocket = paramAnonymousSSLSocket.getClass().getName();
          Intrinsics.checkNotNullExpressionValue(paramAnonymousSSLSocket, "sslSocket.javaClass.name");
          return StringsKt.startsWith$default(paramAnonymousSSLSocket, this.$packageName + '.', false, 2, null);
        }
      };
    }
    
    public final DeferredSocketAdapter.Factory getPlayProviderFactory()
    {
      return AndroidSocketAdapter.access$getPlayProviderFactory$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/AndroidSocketAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */