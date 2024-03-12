package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealConnection;
import okio.BufferedSink;
import okio.Okio;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\t"}, d2={"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"}, k=1, mv={1, 4, 0})
public final class CallServerInterceptor
  implements Interceptor
{
  private final boolean forWebSocket;
  
  public CallServerInterceptor(boolean paramBoolean)
  {
    this.forWebSocket = paramBoolean;
  }
  
  public Response intercept(Interceptor.Chain paramChain)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramChain, "chain");
    paramChain = (RealInterceptorChain)paramChain;
    Object localObject2 = paramChain.getExchange$okhttp();
    Intrinsics.checkNotNull(localObject2);
    Object localObject3 = paramChain.getRequest$okhttp();
    Object localObject1 = ((Request)localObject3).body();
    long l = System.currentTimeMillis();
    ((Exchange)localObject2).writeRequestHeaders((Request)localObject3);
    int i = 1;
    int j = 1;
    paramChain = (Response.Builder)null;
    if ((HttpMethod.permitsRequestBody(((Request)localObject3).method())) && (localObject1 != null))
    {
      i = j;
      if (StringsKt.equals("100-continue", ((Request)localObject3).header("Expect"), true))
      {
        ((Exchange)localObject2).flushRequest();
        paramChain = ((Exchange)localObject2).readResponseHeaders(true);
        ((Exchange)localObject2).responseHeadersStart();
        i = 0;
      }
      if (paramChain == null)
      {
        if (((RequestBody)localObject1).isDuplex())
        {
          ((Exchange)localObject2).flushRequest();
          ((RequestBody)localObject1).writeTo(Okio.buffer(((Exchange)localObject2).createRequestBody((Request)localObject3, true)));
        }
        else
        {
          BufferedSink localBufferedSink = Okio.buffer(((Exchange)localObject2).createRequestBody((Request)localObject3, false));
          ((RequestBody)localObject1).writeTo(localBufferedSink);
          localBufferedSink.close();
        }
      }
      else
      {
        ((Exchange)localObject2).noRequestBody();
        if (!((Exchange)localObject2).getConnection$okhttp().isMultiplexed$okhttp()) {
          ((Exchange)localObject2).noNewExchangesOnConnection();
        }
      }
    }
    else
    {
      ((Exchange)localObject2).noRequestBody();
    }
    if ((localObject1 == null) || (!((RequestBody)localObject1).isDuplex())) {
      ((Exchange)localObject2).finishRequest();
    }
    j = i;
    localObject1 = paramChain;
    if (paramChain == null)
    {
      paramChain = ((Exchange)localObject2).readResponseHeaders(false);
      Intrinsics.checkNotNull(paramChain);
      j = i;
      localObject1 = paramChain;
      if (i != 0)
      {
        ((Exchange)localObject2).responseHeadersStart();
        j = 0;
        localObject1 = paramChain;
      }
    }
    paramChain = ((Response.Builder)localObject1).request((Request)localObject3).handshake(((Exchange)localObject2).getConnection$okhttp().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
    int k = paramChain.code();
    i = k;
    if (k == 100)
    {
      paramChain = ((Exchange)localObject2).readResponseHeaders(false);
      Intrinsics.checkNotNull(paramChain);
      if (j != 0) {
        ((Exchange)localObject2).responseHeadersStart();
      }
      paramChain = paramChain.request((Request)localObject3).handshake(((Exchange)localObject2).getConnection$okhttp().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
      i = paramChain.code();
    }
    ((Exchange)localObject2).responseHeadersEnd(paramChain);
    if ((this.forWebSocket) && (i == 101)) {
      paramChain = paramChain.newBuilder().body(Util.EMPTY_RESPONSE).build();
    } else {
      paramChain = paramChain.newBuilder().body(((Exchange)localObject2).openResponseBody(paramChain)).build();
    }
    if (!StringsKt.equals("close", paramChain.request().header("Connection"), true))
    {
      localObject1 = Response.header$default(paramChain, "Connection", null, 2, null);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      if (!StringsKt.equals("close", (String)localObject1, true)) {
        break label483;
      }
    }
    ((Exchange)localObject2).noNewExchangesOnConnection();
    label483:
    localObject1 = null;
    if ((i == 204) || (i == 205))
    {
      localObject2 = paramChain.body();
      if (localObject2 != null) {
        l = ((ResponseBody)localObject2).contentLength();
      } else {
        l = -1L;
      }
      if (l > 0L)
      {
        localObject2 = new StringBuilder().append("HTTP ").append(i).append(" had non-zero Content-Length: ");
        localObject3 = paramChain.body();
        paramChain = (Interceptor.Chain)localObject1;
        if (localObject3 != null) {
          paramChain = Long.valueOf(((ResponseBody)localObject3).contentLength());
        }
        throw ((Throwable)new ProtocolException(paramChain));
      }
    }
    return paramChain;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http/CallServerInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */