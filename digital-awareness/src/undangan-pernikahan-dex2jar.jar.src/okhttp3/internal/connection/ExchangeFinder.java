package okhttp3.internal.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

@Metadata(bv={1, 0, 3}, d1={"\000r\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\006\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\030\0002\0020\001B%\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t¢\006\002\020\nJ\026\020\027\032\0020\0302\006\020\031\032\0020\0322\006\020\033\032\0020\034J0\020\035\032\0020\0362\006\020\037\032\0020\0162\006\020 \032\0020\0162\006\020!\032\0020\0162\006\020\"\032\0020\0162\006\020#\032\0020$H\002J8\020%\032\0020\0362\006\020\037\032\0020\0162\006\020 \032\0020\0162\006\020!\032\0020\0162\006\020\"\032\0020\0162\006\020#\032\0020$2\006\020&\032\0020$H\002J\006\020'\032\0020$J\n\020(\032\004\030\0010\020H\002J\016\020)\032\0020$2\006\020*\032\0020+J\016\020,\032\0020-2\006\020.\032\0020/R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\013\020\fR\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\r\032\0020\016X\016¢\006\002\n\000R\016\020\b\032\0020\tX\004¢\006\002\n\000R\020\020\017\032\004\030\0010\020X\016¢\006\002\n\000R\016\020\021\032\0020\016X\016¢\006\002\n\000R\016\020\022\032\0020\016X\016¢\006\002\n\000R\020\020\023\032\004\030\0010\024X\016¢\006\002\n\000R\020\020\025\032\004\030\0010\026X\016¢\006\002\n\000¨\0060"}, d2={"Lokhttp3/internal/connection/ExchangeFinder;", "", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;)V", "getAddress$okhttp", "()Lokhttp3/Address;", "connectionShutdownCount", "", "nextRouteToTry", "Lokhttp3/Route;", "otherFailureCount", "refusedStreamCount", "routeSelection", "Lokhttp3/internal/connection/RouteSelector$Selection;", "routeSelector", "Lokhttp3/internal/connection/RouteSelector;", "find", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "findConnection", "Lokhttp3/internal/connection/RealConnection;", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "", "findHealthyConnection", "doExtensiveHealthChecks", "retryAfterFailure", "retryRoute", "sameHostAndPort", "url", "Lokhttp3/HttpUrl;", "trackFailure", "", "e", "Ljava/io/IOException;", "okhttp"}, k=1, mv={1, 4, 0})
public final class ExchangeFinder
{
  private final Address address;
  private final RealCall call;
  private final RealConnectionPool connectionPool;
  private int connectionShutdownCount;
  private final EventListener eventListener;
  private Route nextRouteToTry;
  private int otherFailureCount;
  private int refusedStreamCount;
  private RouteSelector.Selection routeSelection;
  private RouteSelector routeSelector;
  
  public ExchangeFinder(RealConnectionPool paramRealConnectionPool, Address paramAddress, RealCall paramRealCall, EventListener paramEventListener)
  {
    this.connectionPool = paramRealConnectionPool;
    this.address = paramAddress;
    this.call = paramRealCall;
    this.eventListener = paramEventListener;
  }
  
  private final RealConnection findConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    throws IOException
  {
    if (!this.call.isCanceled())
    {
      Object localObject4 = this.call.getConnection();
      int i = 1;
      if (localObject4 != null)
      {
        Socket localSocket = (Socket)null;
        try
        {
          if ((((RealConnection)localObject4).getNoNewExchanges()) || (!sameHostAndPort(((RealConnection)localObject4).route().address().url()))) {
            localSocket = this.call.releaseConnectionNoEvents$okhttp();
          }
          localObject5 = Unit.INSTANCE;
          if (this.call.getConnection() != null)
          {
            if (localSocket == null) {
              paramInt1 = i;
            } else {
              paramInt1 = 0;
            }
            if (paramInt1 != 0) {
              return (RealConnection)localObject4;
            }
            throw ((Throwable)new IllegalStateException("Check failed.".toString()));
          }
          if (localSocket != null) {
            Util.closeQuietly(localSocket);
          }
          this.eventListener.connectionReleased((Call)this.call, (Connection)localObject4);
        }
        finally {}
      }
      this.refusedStreamCount = 0;
      this.connectionShutdownCount = 0;
      this.otherFailureCount = 0;
      if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, null, false))
      {
        localObject2 = this.call.getConnection();
        Intrinsics.checkNotNull(localObject2);
        this.eventListener.connectionAcquired((Call)this.call, (Connection)localObject2);
        return (RealConnection)localObject2;
      }
      Object localObject2 = this.nextRouteToTry;
      if (localObject2 != null)
      {
        localObject4 = (List)null;
        Intrinsics.checkNotNull(localObject2);
        localObject5 = (Route)null;
        this.nextRouteToTry = null;
      }
      else
      {
        localObject2 = this.routeSelection;
        if (localObject2 != null)
        {
          Intrinsics.checkNotNull(localObject2);
          if (((RouteSelector.Selection)localObject2).hasNext())
          {
            localObject4 = (List)null;
            localObject2 = this.routeSelection;
            Intrinsics.checkNotNull(localObject2);
            localObject2 = ((RouteSelector.Selection)localObject2).next();
            break label472;
          }
        }
        localObject4 = this.routeSelector;
        localObject2 = localObject4;
        if (localObject4 == null)
        {
          localObject2 = new RouteSelector(this.address, this.call.getClient().getRouteDatabase(), (Call)this.call, this.eventListener);
          this.routeSelector = ((RouteSelector)localObject2);
        }
        localObject2 = ((RouteSelector)localObject2).next();
        this.routeSelection = ((RouteSelector.Selection)localObject2);
        localObject4 = ((RouteSelector.Selection)localObject2).getRoutes();
        if (this.call.isCanceled()) {
          break label708;
        }
        if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, (List)localObject4, false))
        {
          localObject2 = this.call.getConnection();
          Intrinsics.checkNotNull(localObject2);
          this.eventListener.connectionAcquired((Call)this.call, (Connection)localObject2);
          return (RealConnection)localObject2;
        }
        localObject2 = ((RouteSelector.Selection)localObject2).next();
      }
      label472:
      Object localObject5 = new RealConnection(this.connectionPool, (Route)localObject2);
      this.call.setConnectionToCancel((RealConnection)localObject5);
      try
      {
        ((RealConnection)localObject5).connect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, (Call)this.call, this.eventListener);
        RealCall localRealCall2 = this.call;
        RealConnection localRealConnection = (RealConnection)null;
        localRealCall2.setConnectionToCancel(null);
        this.call.getClient().getRouteDatabase().connected(((RealConnection)localObject5).route());
        if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, (List)localObject4, true))
        {
          localObject4 = this.call.getConnection();
          Intrinsics.checkNotNull(localObject4);
          this.nextRouteToTry = ((Route)localObject2);
          Util.closeQuietly(((RealConnection)localObject5).socket());
          this.eventListener.connectionAcquired((Call)this.call, (Connection)localObject4);
          return (RealConnection)localObject4;
        }
        try
        {
          this.connectionPool.put((RealConnection)localObject5);
          this.call.acquireConnectionNoEvents((RealConnection)localObject5);
          localObject2 = Unit.INSTANCE;
          this.eventListener.connectionAcquired((Call)this.call, (Connection)localObject5);
          return (RealConnection)localObject5;
        }
        finally {}
        RealCall localRealCall1;
        throw ((Throwable)new IOException("Canceled"));
      }
      finally
      {
        localRealCall1 = this.call;
        localObject4 = (RealConnection)null;
        localRealCall1.setConnectionToCancel(null);
      }
    }
    label708:
    throw ((Throwable)new IOException("Canceled"));
  }
  
  private final RealConnection findHealthyConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    boolean bool1;
    do
    {
      boolean bool2;
      do
      {
        do
        {
          localObject = findConnection(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean1);
          if (((RealConnection)localObject).isHealthy(paramBoolean2)) {
            return (RealConnection)localObject;
          }
          ((RealConnection)localObject).noNewExchanges$okhttp();
        } while (this.nextRouteToTry != null);
        localObject = this.routeSelection;
        bool2 = true;
        if (localObject != null) {
          bool1 = ((RouteSelector.Selection)localObject).hasNext();
        } else {
          bool1 = true;
        }
      } while (bool1);
      Object localObject = this.routeSelector;
      bool1 = bool2;
      if (localObject != null) {
        bool1 = ((RouteSelector)localObject).hasNext();
      }
    } while (bool1);
    throw ((Throwable)new IOException("exhausted all routes"));
  }
  
  private final Route retryRoute()
  {
    if ((this.refusedStreamCount <= 1) && (this.connectionShutdownCount <= 1) && (this.otherFailureCount <= 0))
    {
      RealConnection localRealConnection = this.call.getConnection();
      if (localRealConnection != null) {
        try
        {
          int i = localRealConnection.getRouteFailureCount$okhttp();
          if (i != 0) {
            return null;
          }
          boolean bool = Util.canReuseConnectionFor(localRealConnection.route().address().url(), this.address.url());
          if (!bool) {
            return null;
          }
          Route localRoute = localRealConnection.route();
          return localRoute;
        }
        finally {}
      }
      return null;
    }
    return null;
  }
  
  public final ExchangeCodec find(OkHttpClient paramOkHttpClient, RealInterceptorChain paramRealInterceptorChain)
  {
    Intrinsics.checkNotNullParameter(paramOkHttpClient, "client");
    Intrinsics.checkNotNullParameter(paramRealInterceptorChain, "chain");
    try
    {
      paramOkHttpClient = findHealthyConnection(paramRealInterceptorChain.getConnectTimeoutMillis$okhttp(), paramRealInterceptorChain.getReadTimeoutMillis$okhttp(), paramRealInterceptorChain.getWriteTimeoutMillis$okhttp(), paramOkHttpClient.pingIntervalMillis(), paramOkHttpClient.retryOnConnectionFailure(), Intrinsics.areEqual(paramRealInterceptorChain.getRequest$okhttp().method(), "GET") ^ true).newCodec$okhttp(paramOkHttpClient, paramRealInterceptorChain);
      return paramOkHttpClient;
    }
    catch (IOException paramOkHttpClient)
    {
      trackFailure(paramOkHttpClient);
      throw ((Throwable)new RouteException(paramOkHttpClient));
    }
    catch (RouteException paramOkHttpClient)
    {
      trackFailure(paramOkHttpClient.getLastConnectException());
      throw ((Throwable)paramOkHttpClient);
    }
  }
  
  public final Address getAddress$okhttp()
  {
    return this.address;
  }
  
  public final boolean retryAfterFailure()
  {
    if ((this.refusedStreamCount == 0) && (this.connectionShutdownCount == 0) && (this.otherFailureCount == 0)) {
      return false;
    }
    if (this.nextRouteToTry != null) {
      return true;
    }
    Object localObject = retryRoute();
    if (localObject != null)
    {
      this.nextRouteToTry = ((Route)localObject);
      return true;
    }
    localObject = this.routeSelection;
    if ((localObject != null) && (((RouteSelector.Selection)localObject).hasNext() == true)) {
      return true;
    }
    localObject = this.routeSelector;
    if (localObject != null) {
      return ((RouteSelector)localObject).hasNext();
    }
    return true;
  }
  
  public final boolean sameHostAndPort(HttpUrl paramHttpUrl)
  {
    Intrinsics.checkNotNullParameter(paramHttpUrl, "url");
    HttpUrl localHttpUrl = this.address.url();
    boolean bool;
    if ((paramHttpUrl.port() == localHttpUrl.port()) && (Intrinsics.areEqual(paramHttpUrl.host(), localHttpUrl.host()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final void trackFailure(IOException paramIOException)
  {
    Intrinsics.checkNotNullParameter(paramIOException, "e");
    Route localRoute = (Route)null;
    this.nextRouteToTry = null;
    if (((paramIOException instanceof StreamResetException)) && (((StreamResetException)paramIOException).errorCode == ErrorCode.REFUSED_STREAM)) {
      this.refusedStreamCount += 1;
    } else if ((paramIOException instanceof ConnectionShutdownException)) {
      this.connectionShutdownCount += 1;
    } else {
      this.otherFailureCount += 1;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/connection/ExchangeFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */