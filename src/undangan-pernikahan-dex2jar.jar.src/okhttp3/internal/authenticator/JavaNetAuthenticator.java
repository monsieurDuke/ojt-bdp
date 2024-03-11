package okhttp3.internal.authenticator;

import java.io.IOException;
import java.net.Authenticator.RequestorType;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Address;
import okhttp3.Challenge;
import okhttp3.Credentials;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.Route;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\0002\0020\001B\017\022\b\b\002\020\002\032\0020\003¢\006\002\020\004J\034\020\005\032\004\030\0010\0062\b\020\007\032\004\030\0010\b2\006\020\t\032\0020\nH\026J\034\020\013\032\0020\f*\0020\r2\006\020\016\032\0020\0172\006\020\020\032\0020\003H\002R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\021"}, d2={"Lokhttp3/internal/authenticator/JavaNetAuthenticator;", "Lokhttp3/Authenticator;", "defaultDns", "Lokhttp3/Dns;", "(Lokhttp3/Dns;)V", "authenticate", "Lokhttp3/Request;", "route", "Lokhttp3/Route;", "response", "Lokhttp3/Response;", "connectToInetAddress", "Ljava/net/InetAddress;", "Ljava/net/Proxy;", "url", "Lokhttp3/HttpUrl;", "dns", "okhttp"}, k=1, mv={1, 4, 0})
public final class JavaNetAuthenticator
  implements okhttp3.Authenticator
{
  private final Dns defaultDns;
  
  public JavaNetAuthenticator()
  {
    this(null, 1, null);
  }
  
  public JavaNetAuthenticator(Dns paramDns)
  {
    this.defaultDns = paramDns;
  }
  
  private final InetAddress connectToInetAddress(Proxy paramProxy, HttpUrl paramHttpUrl, Dns paramDns)
    throws IOException
  {
    Proxy.Type localType = paramProxy.type();
    if (localType != null) {}
    switch (JavaNetAuthenticator.WhenMappings.$EnumSwitchMapping$0[localType.ordinal()])
    {
    default: 
      break;
    case 1: 
      paramProxy = (InetAddress)CollectionsKt.first(paramDns.lookup(paramHttpUrl.host()));
      break;
    }
    paramProxy = paramProxy.address();
    if (paramProxy != null)
    {
      paramProxy = ((InetSocketAddress)paramProxy).getAddress();
      Intrinsics.checkNotNullExpressionValue(paramProxy, "(address() as InetSocketAddress).address");
      return paramProxy;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.net.InetSocketAddress");
  }
  
  public Request authenticate(Route paramRoute, Response paramResponse)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    Object localObject1 = paramResponse.challenges();
    Request localRequest = paramResponse.request();
    HttpUrl localHttpUrl = localRequest.url();
    int i;
    if (paramResponse.code() == 407) {
      i = 1;
    } else {
      i = 0;
    }
    if (paramRoute != null)
    {
      paramResponse = paramRoute.proxy();
      if (paramResponse != null) {}
    }
    else
    {
      paramResponse = Proxy.NO_PROXY;
    }
    Iterator localIterator = ((List)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Challenge localChallenge = (Challenge)localIterator.next();
      if (StringsKt.equals("Basic", localChallenge.scheme(), true))
      {
        if (paramRoute != null)
        {
          localObject1 = paramRoute.address();
          if (localObject1 != null)
          {
            localObject1 = ((Address)localObject1).dns();
            if (localObject1 != null) {
              break label146;
            }
          }
        }
        localObject1 = this.defaultDns;
        label146:
        Object localObject2;
        if (i != 0)
        {
          localObject2 = paramResponse.address();
          if (localObject2 != null)
          {
            localObject2 = (InetSocketAddress)localObject2;
            String str = ((InetSocketAddress)localObject2).getHostName();
            Intrinsics.checkNotNullExpressionValue(paramResponse, "proxy");
            localObject1 = java.net.Authenticator.requestPasswordAuthentication(str, connectToInetAddress(paramResponse, localHttpUrl, (Dns)localObject1), ((InetSocketAddress)localObject2).getPort(), localHttpUrl.scheme(), localChallenge.realm(), localChallenge.scheme(), localHttpUrl.url(), Authenticator.RequestorType.PROXY);
          }
          else
          {
            throw new NullPointerException("null cannot be cast to non-null type java.net.InetSocketAddress");
          }
        }
        else
        {
          localObject2 = localHttpUrl.host();
          Intrinsics.checkNotNullExpressionValue(paramResponse, "proxy");
          localObject1 = java.net.Authenticator.requestPasswordAuthentication((String)localObject2, connectToInetAddress(paramResponse, localHttpUrl, (Dns)localObject1), localHttpUrl.port(), localHttpUrl.scheme(), localChallenge.realm(), localChallenge.scheme(), localHttpUrl.url(), Authenticator.RequestorType.SERVER);
        }
        if (localObject1 != null)
        {
          if (i != 0) {
            paramRoute = "Proxy-Authorization";
          } else {
            paramRoute = "Authorization";
          }
          paramResponse = ((PasswordAuthentication)localObject1).getUserName();
          Intrinsics.checkNotNullExpressionValue(paramResponse, "auth.userName");
          localObject1 = ((PasswordAuthentication)localObject1).getPassword();
          Intrinsics.checkNotNullExpressionValue(localObject1, "auth.password");
          localObject1 = new String((char[])localObject1);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          paramResponse = Credentials.basic(paramResponse, (String)localObject1, localChallenge.charset());
          Log5ECF72.a(paramResponse);
          LogE84000.a(paramResponse);
          Log229316.a(paramResponse);
          return localRequest.newBuilder().header(paramRoute, paramResponse).build();
        }
      }
    }
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/authenticator/JavaNetAuthenticator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */