package okhttp3.internal.http;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.Collection;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Authenticator;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.http2.ConnectionShutdownException;

@Metadata(bv={1, 0, 3}, d1={"\000R\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\030\000 \0362\0020\001:\001\036B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\032\020\005\032\004\030\0010\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nH\002J\034\020\013\032\004\030\0010\0062\006\020\007\032\0020\b2\b\020\f\032\004\030\0010\rH\002J\020\020\016\032\0020\b2\006\020\017\032\0020\020H\026J\030\020\021\032\0020\0222\006\020\023\032\0020\0242\006\020\025\032\0020\022H\002J(\020\026\032\0020\0222\006\020\023\032\0020\0242\006\020\027\032\0020\0302\006\020\031\032\0020\0062\006\020\025\032\0020\022H\002J\030\020\032\032\0020\0222\006\020\023\032\0020\0242\006\020\031\032\0020\006H\002J\030\020\033\032\0020\0342\006\020\007\032\0020\b2\006\020\035\032\0020\034H\002R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\037"}, d2={"Lokhttp3/internal/http/RetryAndFollowUpInterceptor;", "Lokhttp3/Interceptor;", "client", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "buildRedirectRequest", "Lokhttp3/Request;", "userResponse", "Lokhttp3/Response;", "method", "", "followUpRequest", "exchange", "Lokhttp3/internal/connection/Exchange;", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "isRecoverable", "", "e", "Ljava/io/IOException;", "requestSendStarted", "recover", "call", "Lokhttp3/internal/connection/RealCall;", "userRequest", "requestIsOneShot", "retryAfter", "", "defaultDelay", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class RetryAndFollowUpInterceptor
  implements Interceptor
{
  public static final Companion Companion = new Companion(null);
  private static final int MAX_FOLLOW_UPS = 20;
  private final OkHttpClient client;
  
  public RetryAndFollowUpInterceptor(OkHttpClient paramOkHttpClient)
  {
    this.client = paramOkHttpClient;
  }
  
  private final Request buildRedirectRequest(Response paramResponse, String paramString)
  {
    boolean bool = this.client.followRedirects();
    RequestBody localRequestBody = null;
    if (!bool) {
      return null;
    }
    Object localObject = Response.header$default(paramResponse, "Location", null, 2, null);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    if (localObject != null)
    {
      localObject = paramResponse.request().url().resolve((String)localObject);
      if (localObject != null)
      {
        if ((!Intrinsics.areEqual(((HttpUrl)localObject).scheme(), paramResponse.request().url().scheme())) && (!this.client.followSslRedirects())) {
          return null;
        }
        Request.Builder localBuilder = paramResponse.request().newBuilder();
        if (HttpMethod.permitsRequestBody(paramString))
        {
          int j = paramResponse.code();
          int i;
          if ((!HttpMethod.INSTANCE.redirectsWithBody(paramString)) && (j != 308) && (j != 307)) {
            i = 0;
          } else {
            i = 1;
          }
          if ((HttpMethod.INSTANCE.redirectsToGet(paramString)) && (j != 308) && (j != 307))
          {
            localBuilder.method("GET", null);
          }
          else
          {
            if (i != 0) {
              localRequestBody = paramResponse.request().body();
            }
            localBuilder.method(paramString, localRequestBody);
          }
          if (i == 0)
          {
            localBuilder.removeHeader("Transfer-Encoding");
            localBuilder.removeHeader("Content-Length");
            localBuilder.removeHeader("Content-Type");
          }
        }
        if (!Util.canReuseConnectionFor(paramResponse.request().url(), (HttpUrl)localObject)) {
          localBuilder.removeHeader("Authorization");
        }
        return localBuilder.url((HttpUrl)localObject).build();
      }
      return null;
    }
    return null;
  }
  
  private final Request followUpRequest(Response paramResponse, Exchange paramExchange)
    throws IOException
  {
    if (paramExchange != null)
    {
      localObject = paramExchange.getConnection$okhttp();
      if (localObject != null)
      {
        localObject = ((RealConnection)localObject).route();
        break label28;
      }
    }
    Object localObject = null;
    label28:
    int i = paramResponse.code();
    String str = paramResponse.request().method();
    switch (i)
    {
    default: 
      return null;
    case 503: 
      paramExchange = paramResponse.priorResponse();
      if ((paramExchange != null) && (paramExchange.code() == 503)) {
        return null;
      }
      if (retryAfter(paramResponse, Integer.MAX_VALUE) == 0) {
        return paramResponse.request();
      }
      return null;
    case 421: 
      localObject = paramResponse.request().body();
      if ((localObject != null) && (((RequestBody)localObject).isOneShot())) {
        return null;
      }
      if ((paramExchange != null) && (paramExchange.isCoalescedConnection$okhttp()))
      {
        paramExchange.getConnection$okhttp().noCoalescedConnections$okhttp();
        return paramResponse.request();
      }
      return null;
    case 408: 
      if (!this.client.retryOnConnectionFailure()) {
        return null;
      }
      paramExchange = paramResponse.request().body();
      if ((paramExchange != null) && (paramExchange.isOneShot())) {
        return null;
      }
      paramExchange = paramResponse.priorResponse();
      if ((paramExchange != null) && (paramExchange.code() == 408)) {
        return null;
      }
      if (retryAfter(paramResponse, 0) > 0) {
        return null;
      }
      return paramResponse.request();
    case 407: 
      Intrinsics.checkNotNull(localObject);
      if (((Route)localObject).proxy().type() == Proxy.Type.HTTP) {
        return this.client.proxyAuthenticator().authenticate((Route)localObject, paramResponse);
      }
      throw ((Throwable)new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy"));
    case 401: 
      return this.client.authenticator().authenticate((Route)localObject, paramResponse);
    }
    return buildRedirectRequest(paramResponse, str);
  }
  
  private final boolean isRecoverable(IOException paramIOException, boolean paramBoolean)
  {
    boolean bool1 = paramIOException instanceof ProtocolException;
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if ((paramIOException instanceof InterruptedIOException))
    {
      bool1 = bool2;
      if ((paramIOException instanceof SocketTimeoutException))
      {
        bool1 = bool2;
        if (!paramBoolean) {
          bool1 = true;
        }
      }
      return bool1;
    }
    if (((paramIOException instanceof SSLHandshakeException)) && ((paramIOException.getCause() instanceof CertificateException))) {
      return false;
    }
    return !(paramIOException instanceof SSLPeerUnverifiedException);
  }
  
  private final boolean recover(IOException paramIOException, RealCall paramRealCall, Request paramRequest, boolean paramBoolean)
  {
    if (!this.client.retryOnConnectionFailure()) {
      return false;
    }
    if ((paramBoolean) && (requestIsOneShot(paramIOException, paramRequest))) {
      return false;
    }
    if (!isRecoverable(paramIOException, paramBoolean)) {
      return false;
    }
    return paramRealCall.retryAfterFailure();
  }
  
  private final boolean requestIsOneShot(IOException paramIOException, Request paramRequest)
  {
    paramRequest = paramRequest.body();
    boolean bool;
    if (((paramRequest != null) && (paramRequest.isOneShot())) || ((paramIOException instanceof FileNotFoundException))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final int retryAfter(Response paramResponse, int paramInt)
  {
    paramResponse = Response.header$default(paramResponse, "Retry-After", null, 2, null);
    Log5ECF72.a(paramResponse);
    LogE84000.a(paramResponse);
    Log229316.a(paramResponse);
    if (paramResponse != null)
    {
      CharSequence localCharSequence = (CharSequence)paramResponse;
      if (new Regex("\\d+").matches(localCharSequence))
      {
        paramResponse = Integer.valueOf(paramResponse);
        Intrinsics.checkNotNullExpressionValue(paramResponse, "Integer.valueOf(header)");
        return paramResponse.intValue();
      }
      return Integer.MAX_VALUE;
    }
    return paramInt;
  }
  
  public Response intercept(Interceptor.Chain paramChain)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramChain, "chain");
    RealInterceptorChain localRealInterceptorChain = (RealInterceptorChain)paramChain;
    Object localObject1 = ((RealInterceptorChain)paramChain).getRequest$okhttp();
    RealCall localRealCall = localRealInterceptorChain.getCall$okhttp();
    Object localObject2 = (Response)null;
    paramChain = CollectionsKt.emptyList();
    boolean bool1 = true;
    int i = 0;
    for (;;)
    {
      localRealCall.enterNetworkInterceptorExchange((Request)localObject1, bool1);
      try
      {
        bool1 = localRealCall.isCanceled();
        if (!bool1)
        {
          bool1 = false;
          try
          {
            Object localObject3 = localRealInterceptorChain.proceed((Request)localObject1);
            bool1 = true;
            localObject1 = localObject3;
            if (localObject2 != null)
            {
              localObject1 = ((Response)localObject3).newBuilder();
              localObject1 = ((Response.Builder)localObject1).priorResponse(((Response)localObject2).newBuilder().body(null).build()).build();
            }
            localObject3 = localRealCall.getInterceptorScopedExchange$okhttp();
            localObject2 = followUpRequest((Response)localObject1, (Exchange)localObject3);
            if (localObject2 == null)
            {
              if ((localObject3 != null) && (((Exchange)localObject3).isDuplex$okhttp())) {
                localRealCall.timeoutEarlyExit();
              }
              localRealCall.exitNetworkInterceptorExchange$okhttp(false);
              return (Response)localObject1;
            }
            localObject3 = ((Request)localObject2).body();
            if (localObject3 != null)
            {
              boolean bool2 = ((RequestBody)localObject3).isOneShot();
              if (bool2)
              {
                localRealCall.exitNetworkInterceptorExchange$okhttp(false);
                return (Response)localObject1;
              }
            }
            localObject3 = ((Response)localObject1).body();
            if (localObject3 != null) {
              Util.closeQuietly((Closeable)localObject3);
            }
            i++;
            if (i <= 20)
            {
              localObject3 = localObject1;
              localRealCall.exitNetworkInterceptorExchange$okhttp(true);
              localObject1 = localObject2;
              localObject2 = localObject3;
              continue;
            }
            paramChain = new java/net/ProtocolException;
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            paramChain.<init>("Too many follow-up requests: " + i);
            throw ((Throwable)paramChain);
          }
          catch (IOException localIOException)
          {
            if (!(localIOException instanceof ConnectionShutdownException)) {
              bool1 = true;
            }
            if (recover(localIOException, localRealCall, (Request)localObject1, bool1))
            {
              paramChain = CollectionsKt.plus((Collection)paramChain, localIOException);
              bool1 = false;
              localRealCall.exitNetworkInterceptorExchange$okhttp(true);
              continue;
            }
            throw Util.withSuppressed((Exception)localIOException, paramChain);
          }
          catch (RouteException localRouteException)
          {
            if (recover(localRouteException.getLastConnectException(), localRealCall, (Request)localObject1, false))
            {
              paramChain = CollectionsKt.plus((Collection)paramChain, localRouteException.getFirstConnectException());
              bool1 = false;
              localRealCall.exitNetworkInterceptorExchange$okhttp(true);
              continue;
            }
            throw Util.withSuppressed((Exception)localRouteException.getFirstConnectException(), paramChain);
          }
        }
        paramChain = new java/io/IOException;
        paramChain.<init>("Canceled");
        throw ((Throwable)paramChain);
      }
      finally
      {
        localRealCall.exitNetworkInterceptorExchange$okhttp(true);
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lokhttp3/internal/http/RetryAndFollowUpInterceptor$Companion;", "", "()V", "MAX_FOLLOW_UPS", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http/RetryAndFollowUpInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */