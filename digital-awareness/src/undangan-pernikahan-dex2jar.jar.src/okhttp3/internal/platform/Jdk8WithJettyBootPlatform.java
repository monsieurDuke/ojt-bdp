package okhttp3.internal.platform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Protocol;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\004\030\000 \0262\0020\001:\002\025\026B5\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003\022\n\020\006\032\006\022\002\b\0030\007\022\n\020\b\032\006\022\002\b\0030\007¢\006\002\020\tJ\020\020\n\032\0020\0132\006\020\f\032\0020\rH\026J(\020\016\032\0020\0132\006\020\f\032\0020\r2\b\020\017\032\004\030\0010\0202\f\020\021\032\b\022\004\022\0020\0230\022H\026J\022\020\024\032\004\030\0010\0202\006\020\f\032\0020\rH\026R\022\020\006\032\006\022\002\b\0030\007X\004¢\006\002\n\000R\016\020\004\032\0020\003X\004¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\005\032\0020\003X\004¢\006\002\n\000R\022\020\b\032\006\022\002\b\0030\007X\004¢\006\002\n\000¨\006\027"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform;", "Lokhttp3/internal/platform/Platform;", "putMethod", "Ljava/lang/reflect/Method;", "getMethod", "removeMethod", "clientProviderClass", "Ljava/lang/Class;", "serverProviderClass", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/Class;Ljava/lang/Class;)V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "AlpnProvider", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class Jdk8WithJettyBootPlatform
  extends Platform
{
  public static final Companion Companion = new Companion(null);
  private final Class<?> clientProviderClass;
  private final Method getMethod;
  private final Method putMethod;
  private final Method removeMethod;
  private final Class<?> serverProviderClass;
  
  public Jdk8WithJettyBootPlatform(Method paramMethod1, Method paramMethod2, Method paramMethod3, Class<?> paramClass1, Class<?> paramClass2)
  {
    this.putMethod = paramMethod1;
    this.getMethod = paramMethod2;
    this.removeMethod = paramMethod3;
    this.clientProviderClass = paramClass1;
    this.serverProviderClass = paramClass2;
  }
  
  public void afterHandshake(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    try
    {
      this.removeMethod.invoke(null, new Object[] { paramSSLSocket });
      return;
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to remove ALPN", (Throwable)paramSSLSocket));
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to remove ALPN", (Throwable)paramSSLSocket));
    }
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    Intrinsics.checkNotNullParameter(paramList, "protocols");
    Object localObject = Platform.Companion.alpnProtocolNames(paramList);
    try
    {
      paramList = Platform.class.getClassLoader();
      paramString = this.clientProviderClass;
      Class localClass = this.serverProviderClass;
      AlpnProvider localAlpnProvider = new okhttp3/internal/platform/Jdk8WithJettyBootPlatform$AlpnProvider;
      localAlpnProvider.<init>((List)localObject);
      localObject = (InvocationHandler)localAlpnProvider;
      paramString = Proxy.newProxyInstance(paramList, new Class[] { paramString, localClass }, (InvocationHandler)localObject);
      this.putMethod.invoke(null, new Object[] { paramSSLSocket, paramString });
      return;
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to set ALPN", (Throwable)paramSSLSocket));
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to set ALPN", (Throwable)paramSSLSocket));
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkNotNullParameter(paramSSLSocket, "sslSocket");
    try
    {
      Method localMethod = this.getMethod;
      Object localObject = null;
      paramSSLSocket = Proxy.getInvocationHandler(localMethod.invoke(null, new Object[] { paramSSLSocket }));
      if (paramSSLSocket != null)
      {
        paramSSLSocket = (AlpnProvider)paramSSLSocket;
        if ((!paramSSLSocket.getUnsupported()) && (paramSSLSocket.getSelected() == null))
        {
          Platform.log$default(this, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", 0, null, 6, null);
          return null;
        }
        if (paramSSLSocket.getUnsupported()) {
          paramSSLSocket = (SSLSocket)localObject;
        } else {
          paramSSLSocket = paramSSLSocket.getSelected();
        }
        return paramSSLSocket;
      }
      paramSSLSocket = new java/lang/NullPointerException;
      paramSSLSocket.<init>("null cannot be cast to non-null type okhttp3.internal.platform.Jdk8WithJettyBootPlatform.AlpnProvider");
      throw paramSSLSocket;
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to get ALPN selected protocol", (Throwable)paramSSLSocket));
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to get ALPN selected protocol", (Throwable)paramSSLSocket));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\000\n\002\020 \n\002\020\016\n\002\b\007\n\002\020\013\n\002\b\005\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\021\n\002\b\002\b\002\030\0002\0020\001B\023\022\f\020\002\032\b\022\004\022\0020\0040\003¢\006\002\020\005J0\020\021\032\004\030\0010\0222\006\020\023\032\0020\0222\006\020\024\032\0020\0252\016\020\026\032\n\022\004\022\0020\022\030\0010\027H\002¢\006\002\020\030R\024\020\002\032\b\022\004\022\0020\0040\003X\004¢\006\002\n\000R\034\020\006\032\004\030\0010\004X\016¢\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\nR\032\020\013\032\0020\fX\016¢\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020¨\006\031"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$AlpnProvider;", "Ljava/lang/reflect/InvocationHandler;", "protocols", "", "", "(Ljava/util/List;)V", "selected", "getSelected", "()Ljava/lang/String;", "setSelected", "(Ljava/lang/String;)V", "unsupported", "", "getUnsupported", "()Z", "setUnsupported", "(Z)V", "invoke", "", "proxy", "method", "Ljava/lang/reflect/Method;", "args", "", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", "okhttp"}, k=1, mv={1, 4, 0})
  private static final class AlpnProvider
    implements InvocationHandler
  {
    private final List<String> protocols;
    private String selected;
    private boolean unsupported;
    
    public AlpnProvider(List<String> paramList)
    {
      this.protocols = paramList;
    }
    
    public final String getSelected()
    {
      return this.selected;
    }
    
    public final boolean getUnsupported()
    {
      return this.unsupported;
    }
    
    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      Intrinsics.checkNotNullParameter(paramObject, "proxy");
      Intrinsics.checkNotNullParameter(paramMethod, "method");
      if (paramArrayOfObject != null) {
        paramObject = paramArrayOfObject;
      } else {
        paramObject = new Object[0];
      }
      String str = paramMethod.getName();
      paramArrayOfObject = paramMethod.getReturnType();
      if ((Intrinsics.areEqual(str, "supports")) && (Intrinsics.areEqual(Boolean.TYPE, paramArrayOfObject))) {
        return Boolean.valueOf(true);
      }
      if ((Intrinsics.areEqual(str, "unsupported")) && (Intrinsics.areEqual(Void.TYPE, paramArrayOfObject)))
      {
        this.unsupported = true;
        return null;
      }
      int i;
      if (Intrinsics.areEqual(str, "protocols"))
      {
        if (paramObject.length == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          return this.protocols;
        }
      }
      if (((Intrinsics.areEqual(str, "selectProtocol")) || (Intrinsics.areEqual(str, "select"))) && (Intrinsics.areEqual(String.class, paramArrayOfObject)) && (paramObject.length == 1) && ((paramObject[0] instanceof List)))
      {
        paramObject = paramObject[0];
        if (paramObject != null)
        {
          paramObject = (List)paramObject;
          int j = ((List)paramObject).size();
          if (j >= 0)
          {
            for (i = 0;; i++)
            {
              paramMethod = ((List)paramObject).get(i);
              if (paramMethod == null) {
                break;
              }
              paramMethod = (String)paramMethod;
              if (this.protocols.contains(paramMethod))
              {
                this.selected = paramMethod;
                return paramMethod;
              }
              if (i == j) {
                break label257;
              }
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
          }
          label257:
          paramObject = (String)this.protocols.get(0);
          this.selected = ((String)paramObject);
          return paramObject;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.List<*>");
      }
      if (((Intrinsics.areEqual(str, "protocolSelected")) || (Intrinsics.areEqual(str, "selected"))) && (paramObject.length == 1))
      {
        paramObject = paramObject[0];
        if (paramObject != null)
        {
          this.selected = ((String)paramObject);
          return null;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      }
      return paramMethod.invoke(this, Arrays.copyOf((Object[])paramObject, paramObject.length));
    }
    
    public final void setSelected(String paramString)
    {
      this.selected = paramString;
    }
    
    public final void setUnsupported(boolean paramBoolean)
    {
      this.unsupported = paramBoolean;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\003\032\004\030\0010\004¨\006\005"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final Platform buildIfSupported()
    {
      String str = System.getProperty("java.specification.version", "unknown");
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      try
      {
        Intrinsics.checkNotNullExpressionValue(str, "jvmVersion");
        int i = Integer.parseInt(str);
        if (i >= 9) {
          return null;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        try
        {
          Object localObject2 = Class.forName("org.eclipse.jetty.alpn.ALPN", true, null);
          Object localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          Object localObject4 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$Provider", true, null);
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          localObject1 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ClientProvider", true, null);
          Object localObject3 = new java/lang/StringBuilder;
          ((StringBuilder)localObject3).<init>();
          localObject3 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ServerProvider", true, null);
          Method localMethod = ((Class)localObject2).getMethod("put", new Class[] { SSLSocket.class, localObject4 });
          localObject4 = ((Class)localObject2).getMethod("get", new Class[] { SSLSocket.class });
          localObject2 = ((Class)localObject2).getMethod("remove", new Class[] { SSLSocket.class });
          Jdk8WithJettyBootPlatform localJdk8WithJettyBootPlatform = new okhttp3/internal/platform/Jdk8WithJettyBootPlatform;
          Intrinsics.checkNotNullExpressionValue(localMethod, "putMethod");
          Intrinsics.checkNotNullExpressionValue(localObject4, "getMethod");
          Intrinsics.checkNotNullExpressionValue(localObject2, "removeMethod");
          Intrinsics.checkNotNullExpressionValue(localObject1, "clientProviderClass");
          Intrinsics.checkNotNullExpressionValue(localObject3, "serverProviderClass");
          localJdk8WithJettyBootPlatform.<init>(localMethod, (Method)localObject4, (Method)localObject2, (Class)localObject1, (Class)localObject3);
          localObject1 = (Platform)localJdk8WithJettyBootPlatform;
          return (Platform)localObject1;
        }
        catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
      }
      return null;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/Jdk8WithJettyBootPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */