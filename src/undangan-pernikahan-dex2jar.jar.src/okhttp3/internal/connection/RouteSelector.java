package okhttp3.internal.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\000d\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\030\002\n\000\n\002\020\b\n\000\n\002\020!\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\003\030\000 !2\0020\001:\002!\"B%\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t¢\006\002\020\nJ\t\020\025\032\0020\026H\002J\b\020\027\032\0020\026H\002J\t\020\030\032\0020\031H\002J\b\020\032\032\0020\024H\002J\020\020\033\032\0020\0342\006\020\035\032\0020\024H\002J\032\020\036\032\0020\0342\006\020\037\032\0020 2\b\020\035\032\004\030\0010\024H\002R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\b\032\0020\tX\004¢\006\002\n\000R\024\020\013\032\b\022\004\022\0020\r0\fX\016¢\006\002\n\000R\016\020\016\032\0020\017X\016¢\006\002\n\000R\024\020\020\032\b\022\004\022\0020\0220\021X\004¢\006\002\n\000R\024\020\023\032\b\022\004\022\0020\0240\fX\016¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006#"}, d2={"Lokhttp3/internal/connection/RouteSelector;", "", "address", "Lokhttp3/Address;", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "call", "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/Address;Lokhttp3/internal/connection/RouteDatabase;Lokhttp3/Call;Lokhttp3/EventListener;)V", "inetSocketAddresses", "", "Ljava/net/InetSocketAddress;", "nextProxyIndex", "", "postponedRoutes", "", "Lokhttp3/Route;", "proxies", "Ljava/net/Proxy;", "hasNext", "", "hasNextProxy", "next", "Lokhttp3/internal/connection/RouteSelector$Selection;", "nextProxy", "resetNextInetSocketAddress", "", "proxy", "resetNextProxy", "url", "Lokhttp3/HttpUrl;", "Companion", "Selection", "okhttp"}, k=1, mv={1, 4, 0})
public final class RouteSelector
{
  public static final Companion Companion = new Companion(null);
  private final Address address;
  private final Call call;
  private final EventListener eventListener;
  private List<? extends InetSocketAddress> inetSocketAddresses;
  private int nextProxyIndex;
  private final List<Route> postponedRoutes;
  private List<? extends Proxy> proxies;
  private final RouteDatabase routeDatabase;
  
  public RouteSelector(Address paramAddress, RouteDatabase paramRouteDatabase, Call paramCall, EventListener paramEventListener)
  {
    this.address = paramAddress;
    this.routeDatabase = paramRouteDatabase;
    this.call = paramCall;
    this.eventListener = paramEventListener;
    this.proxies = CollectionsKt.emptyList();
    this.inetSocketAddresses = CollectionsKt.emptyList();
    this.postponedRoutes = ((List)new ArrayList());
    resetNextProxy(paramAddress.url(), paramAddress.proxy());
  }
  
  private final boolean hasNextProxy()
  {
    boolean bool;
    if (this.nextProxyIndex < this.proxies.size()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final Proxy nextProxy()
    throws IOException
  {
    if (hasNextProxy())
    {
      Object localObject = this.proxies;
      int i = this.nextProxyIndex;
      this.nextProxyIndex = (i + 1);
      localObject = (Proxy)((List)localObject).get(i);
      resetNextInetSocketAddress((Proxy)localObject);
      return (Proxy)localObject;
    }
    throw ((Throwable)new SocketException("No route to " + this.address.url().host() + "; exhausted proxy configurations: " + this.proxies));
  }
  
  private final void resetNextInetSocketAddress(Proxy paramProxy)
    throws IOException
  {
    List localList = (List)new ArrayList();
    this.inetSocketAddresses = localList;
    Object localObject;
    int i;
    if ((paramProxy.type() != Proxy.Type.DIRECT) && (paramProxy.type() != Proxy.Type.SOCKS))
    {
      SocketAddress localSocketAddress = paramProxy.address();
      if ((localSocketAddress instanceof InetSocketAddress))
      {
        localObject = Companion.getSocketHost((InetSocketAddress)localSocketAddress);
        i = ((InetSocketAddress)localSocketAddress).getPort();
      }
      else
      {
        throw ((Throwable)new IllegalArgumentException(("Proxy.address() is not an InetSocketAddress: " + localSocketAddress.getClass()).toString()));
      }
    }
    else
    {
      localObject = this.address.url().host();
      i = this.address.url().port();
    }
    if ((1 <= i) && (65535 >= i))
    {
      if (paramProxy.type() == Proxy.Type.SOCKS)
      {
        ((Collection)localList).add(InetSocketAddress.createUnresolved((String)localObject, i));
      }
      else
      {
        this.eventListener.dnsStart(this.call, (String)localObject);
        paramProxy = this.address.dns().lookup((String)localObject);
        if (paramProxy.isEmpty()) {
          break label276;
        }
        this.eventListener.dnsEnd(this.call, (String)localObject, paramProxy);
        localObject = paramProxy.iterator();
        while (((Iterator)localObject).hasNext())
        {
          paramProxy = (InetAddress)((Iterator)localObject).next();
          ((Collection)localList).add(new InetSocketAddress(paramProxy, i));
        }
      }
      return;
      label276:
      throw ((Throwable)new UnknownHostException(this.address.dns() + " returned no addresses for " + (String)localObject));
    }
    throw ((Throwable)new SocketException("No route to " + (String)localObject + ':' + i + "; port is out of range"));
  }
  
  private final void resetNextProxy(final HttpUrl paramHttpUrl, final Proxy paramProxy)
  {
    paramProxy = new Lambda(paramProxy)
    {
      final RouteSelector this$0;
      
      public final List<Proxy> invoke()
      {
        Object localObject = paramProxy;
        if (localObject != null) {
          return CollectionsKt.listOf(localObject);
        }
        localObject = paramHttpUrl.uri();
        if (((URI)localObject).getHost() == null) {
          return Util.immutableListOf(new Proxy[] { Proxy.NO_PROXY });
        }
        localObject = RouteSelector.access$getAddress$p(this.this$0).proxySelector().select((URI)localObject);
        Collection localCollection = (Collection)localObject;
        int i;
        if ((localCollection != null) && (!localCollection.isEmpty())) {
          i = 0;
        } else {
          i = 1;
        }
        if (i != 0) {
          return Util.immutableListOf(new Proxy[] { Proxy.NO_PROXY });
        }
        return Util.toImmutableList((List)localObject);
      }
    };
    this.eventListener.proxySelectStart(this.call, paramHttpUrl);
    paramProxy = paramProxy.invoke();
    this.proxies = paramProxy;
    this.nextProxyIndex = 0;
    this.eventListener.proxySelectEnd(this.call, paramHttpUrl, paramProxy);
  }
  
  public final boolean hasNext()
  {
    boolean bool3 = hasNextProxy();
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (!bool3) {
      if ((((Collection)this.postponedRoutes).isEmpty() ^ true)) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  public final Selection next()
    throws IOException
  {
    if (hasNext())
    {
      List localList = (List)new ArrayList();
      while (hasNextProxy())
      {
        Proxy localProxy = nextProxy();
        Iterator localIterator = this.inetSocketAddresses.iterator();
        while (localIterator.hasNext())
        {
          Object localObject = (InetSocketAddress)localIterator.next();
          localObject = new Route(this.address, localProxy, (InetSocketAddress)localObject);
          if (this.routeDatabase.shouldPostpone((Route)localObject)) {
            ((Collection)this.postponedRoutes).add(localObject);
          } else {
            ((Collection)localList).add(localObject);
          }
        }
        if ((((Collection)localList).isEmpty() ^ true)) {
          break;
        }
      }
      if (localList.isEmpty())
      {
        CollectionsKt.addAll((Collection)localList, (Iterable)this.postponedRoutes);
        this.postponedRoutes.clear();
      }
      return new Selection(localList);
    }
    throw ((Throwable)new NoSuchElementException());
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\025\020\003\032\0020\004*\0020\0058F¢\006\006\032\004\b\006\020\007¨\006\b"}, d2={"Lokhttp3/internal/connection/RouteSelector$Companion;", "", "()V", "socketHost", "", "Ljava/net/InetSocketAddress;", "getSocketHost", "(Ljava/net/InetSocketAddress;)Ljava/lang/String;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final String getSocketHost(InetSocketAddress paramInetSocketAddress)
    {
      Intrinsics.checkNotNullParameter(paramInetSocketAddress, "$this$socketHost");
      InetAddress localInetAddress = paramInetSocketAddress.getAddress();
      if (localInetAddress != null)
      {
        paramInetSocketAddress = localInetAddress.getHostAddress();
        Intrinsics.checkNotNullExpressionValue(paramInetSocketAddress, "address.hostAddress");
        return paramInetSocketAddress;
      }
      paramInetSocketAddress = paramInetSocketAddress.getHostName();
      Intrinsics.checkNotNullExpressionValue(paramInetSocketAddress, "hostName");
      return paramInetSocketAddress;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\002\030\0002\0020\001B\023\022\f\020\002\032\b\022\004\022\0020\0040\003¢\006\002\020\005J\t\020\n\032\0020\013H\002J\t\020\f\032\0020\004H\002R\016\020\006\032\0020\007X\016¢\006\002\n\000R\027\020\002\032\b\022\004\022\0020\0040\003¢\006\b\n\000\032\004\b\b\020\t¨\006\r"}, d2={"Lokhttp3/internal/connection/RouteSelector$Selection;", "", "routes", "", "Lokhttp3/Route;", "(Ljava/util/List;)V", "nextRouteIndex", "", "getRoutes", "()Ljava/util/List;", "hasNext", "", "next", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Selection
  {
    private int nextRouteIndex;
    private final List<Route> routes;
    
    public Selection(List<Route> paramList)
    {
      this.routes = paramList;
    }
    
    public final List<Route> getRoutes()
    {
      return this.routes;
    }
    
    public final boolean hasNext()
    {
      boolean bool;
      if (this.nextRouteIndex < this.routes.size()) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final Route next()
    {
      if (hasNext())
      {
        List localList = this.routes;
        int i = this.nextRouteIndex;
        this.nextRouteIndex = (i + 1);
        return (Route)localList.get(i);
      }
      throw ((Throwable)new NoSuchElementException());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/connection/RouteSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */