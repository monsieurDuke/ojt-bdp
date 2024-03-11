package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\030\000 \0172\0020\001:\001\017B\017\022\b\020\002\032\004\030\0010\003¢\006\002\020\004J\032\020\007\032\0020\b2\b\020\t\032\004\030\0010\n2\006\020\013\032\0020\bH\002J\020\020\f\032\0020\b2\006\020\r\032\0020\016H\026R\026\020\002\032\004\030\0010\003X\004¢\006\b\n\000\032\004\b\005\020\006¨\006\020"}, d2={"Lokhttp3/internal/cache/CacheInterceptor;", "Lokhttp3/Interceptor;", "cache", "Lokhttp3/Cache;", "(Lokhttp3/Cache;)V", "getCache$okhttp", "()Lokhttp3/Cache;", "cacheWritingResponse", "Lokhttp3/Response;", "cacheRequest", "Lokhttp3/internal/cache/CacheRequest;", "response", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class CacheInterceptor
  implements Interceptor
{
  public static final Companion Companion = new Companion(null);
  private final Cache cache;
  
  public CacheInterceptor(Cache paramCache)
  {
    this.cache = paramCache;
  }
  
  private final Response cacheWritingResponse(final CacheRequest paramCacheRequest, Response paramResponse)
    throws IOException
  {
    if (paramCacheRequest == null) {
      return paramResponse;
    }
    Sink localSink = paramCacheRequest.body();
    Object localObject = paramResponse.body();
    Intrinsics.checkNotNull(localObject);
    localObject = new Source()
    {
      final BufferedSource $source;
      private boolean cacheRequestClosed;
      
      public void close()
        throws IOException
      {
        if ((!this.cacheRequestClosed) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS)))
        {
          this.cacheRequestClosed = true;
          paramCacheRequest.abort();
        }
        this.$source.close();
      }
      
      public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        Intrinsics.checkNotNullParameter(paramAnonymousBuffer, "sink");
        try
        {
          paramAnonymousLong = this.$source.read(paramAnonymousBuffer, paramAnonymousLong);
          if (paramAnonymousLong == -1L)
          {
            if (!this.cacheRequestClosed)
            {
              this.cacheRequestClosed = true;
              this.$cacheBody.close();
            }
            return -1L;
          }
          paramAnonymousBuffer.copyTo(this.$cacheBody.getBuffer(), paramAnonymousBuffer.size() - paramAnonymousLong, paramAnonymousLong);
          this.$cacheBody.emitCompleteSegments();
          return paramAnonymousLong;
        }
        catch (IOException paramAnonymousBuffer)
        {
          if (!this.cacheRequestClosed)
          {
            this.cacheRequestClosed = true;
            paramCacheRequest.abort();
          }
          throw ((Throwable)paramAnonymousBuffer);
        }
      }
      
      public Timeout timeout()
      {
        return this.$source.timeout();
      }
    };
    paramCacheRequest = Response.header$default(paramResponse, "Content-Type", null, 2, null);
    Log5ECF72.a(paramCacheRequest);
    LogE84000.a(paramCacheRequest);
    Log229316.a(paramCacheRequest);
    long l = paramResponse.body().contentLength();
    return paramResponse.newBuilder().body((ResponseBody)new RealResponseBody(paramCacheRequest, l, Okio.buffer((Source)localObject))).build();
  }
  
  public final Cache getCache$okhttp()
  {
    return this.cache;
  }
  
  public Response intercept(Interceptor.Chain paramChain)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramChain, "chain");
    Call localCall = paramChain.call();
    Object localObject1 = this.cache;
    Object localObject2;
    if (localObject1 != null) {
      localObject2 = ((Cache)localObject1).get$okhttp(paramChain.request());
    } else {
      localObject2 = null;
    }
    Object localObject4 = new CacheStrategy.Factory(System.currentTimeMillis(), paramChain.request(), (Response)localObject2).compute();
    Object localObject3 = ((CacheStrategy)localObject4).getNetworkRequest();
    Response localResponse = ((CacheStrategy)localObject4).getCacheResponse();
    localObject1 = this.cache;
    if (localObject1 != null) {
      ((Cache)localObject1).trackResponse$okhttp((CacheStrategy)localObject4);
    }
    if (!(localCall instanceof RealCall)) {
      localObject1 = null;
    } else {
      localObject1 = localCall;
    }
    localObject1 = (RealCall)localObject1;
    if (localObject1 != null)
    {
      localObject1 = ((RealCall)localObject1).getEventListener$okhttp();
      if (localObject1 != null) {}
    }
    else
    {
      localObject1 = EventListener.NONE;
    }
    if ((localObject2 != null) && (localResponse == null))
    {
      localObject4 = ((Response)localObject2).body();
      if (localObject4 != null) {
        Util.closeQuietly((Closeable)localObject4);
      }
    }
    if ((localObject3 == null) && (localResponse == null))
    {
      paramChain = new Response.Builder().request(paramChain.request()).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
      ((EventListener)localObject1).satisfactionFailure(localCall, paramChain);
      return paramChain;
    }
    if (localObject3 == null)
    {
      Intrinsics.checkNotNull(localResponse);
      paramChain = localResponse.newBuilder().cacheResponse(Companion.access$stripBody(Companion, localResponse)).build();
      ((EventListener)localObject1).cacheHit(localCall, paramChain);
      return paramChain;
    }
    if (localResponse != null) {
      ((EventListener)localObject1).cacheConditionalHit(localCall, localResponse);
    } else if (this.cache != null) {
      ((EventListener)localObject1).cacheMiss(localCall);
    }
    localObject4 = (Response)null;
    try
    {
      paramChain = paramChain.proceed((Request)localObject3);
      if ((paramChain == null) && (localObject2 != null))
      {
        localObject2 = ((Response)localObject2).body();
        if (localObject2 != null) {
          Util.closeQuietly((Closeable)localObject2);
        }
      }
      if (localResponse != null)
      {
        if ((paramChain != null) && (paramChain.code() == 304))
        {
          localObject3 = localResponse.newBuilder();
          localObject2 = Companion;
          localObject2 = ((Response.Builder)localObject3).headers(Companion.access$combine((Companion)localObject2, localResponse.headers(), paramChain.headers())).sentRequestAtMillis(paramChain.sentRequestAtMillis()).receivedResponseAtMillis(paramChain.receivedResponseAtMillis()).cacheResponse(Companion.access$stripBody((Companion)localObject2, localResponse)).networkResponse(Companion.access$stripBody((Companion)localObject2, paramChain)).build();
          paramChain = paramChain.body();
          Intrinsics.checkNotNull(paramChain);
          paramChain.close();
          paramChain = this.cache;
          Intrinsics.checkNotNull(paramChain);
          paramChain.trackConditionalCacheHit$okhttp();
          this.cache.update$okhttp(localResponse, (Response)localObject2);
          ((EventListener)localObject1).cacheHit(localCall, (Response)localObject2);
          return (Response)localObject2;
        }
        localObject2 = localResponse.body();
        if (localObject2 != null) {
          Util.closeQuietly((Closeable)localObject2);
        }
      }
      Intrinsics.checkNotNull(paramChain);
      localObject4 = paramChain.newBuilder();
      localObject2 = Companion;
      paramChain = ((Response.Builder)localObject4).cacheResponse(Companion.access$stripBody((Companion)localObject2, localResponse)).networkResponse(Companion.access$stripBody((Companion)localObject2, paramChain)).build();
      if (this.cache != null)
      {
        if ((HttpHeaders.promisesBody(paramChain)) && (CacheStrategy.Companion.isCacheable(paramChain, (Request)localObject3)))
        {
          paramChain = cacheWritingResponse(this.cache.put$okhttp(paramChain), paramChain);
          if (localResponse != null) {
            ((EventListener)localObject1).cacheMiss(localCall);
          }
          return paramChain;
        }
        if (HttpMethod.INSTANCE.invalidatesCache(((Request)localObject3).method())) {
          try
          {
            this.cache.remove$okhttp((Request)localObject3);
          }
          catch (IOException localIOException) {}
        }
      }
      return paramChain;
    }
    finally
    {
      if (localObject2 != null)
      {
        ResponseBody localResponseBody = ((Response)localObject2).body();
        if (localResponseBody != null) {
          Util.closeQuietly((Closeable)localResponseBody);
        }
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0042\006\020\006\032\0020\004H\002J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\002J\020\020\013\032\0020\b2\006\020\t\032\0020\nH\002J\024\020\f\032\004\030\0010\r2\b\020\016\032\004\030\0010\rH\002¨\006\017"}, d2={"Lokhttp3/internal/cache/CacheInterceptor$Companion;", "", "()V", "combine", "Lokhttp3/Headers;", "cachedHeaders", "networkHeaders", "isContentSpecificHeader", "", "fieldName", "", "isEndToEnd", "stripBody", "Lokhttp3/Response;", "response", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final Headers combine(Headers paramHeaders1, Headers paramHeaders2)
    {
      Headers.Builder localBuilder = new Headers.Builder();
      int k = paramHeaders1.size();
      int j = 0;
      for (int i = 0; i < k; i++)
      {
        String str1 = paramHeaders1.name(i);
        String str2 = paramHeaders1.value(i);
        if (((!StringsKt.equals("Warning", str1, true)) || (!StringsKt.startsWith$default(str2, "1", false, 2, null))) && ((((Companion)this).isContentSpecificHeader(str1)) || (!((Companion)this).isEndToEnd(str1)) || (paramHeaders2.get(str1) == null))) {
          localBuilder.addLenient$okhttp(str1, str2);
        }
      }
      k = paramHeaders2.size();
      for (i = j; i < k; i++)
      {
        paramHeaders1 = paramHeaders2.name(i);
        if ((!((Companion)this).isContentSpecificHeader(paramHeaders1)) && (((Companion)this).isEndToEnd(paramHeaders1))) {
          localBuilder.addLenient$okhttp(paramHeaders1, paramHeaders2.value(i));
        }
      }
      return localBuilder.build();
    }
    
    private final boolean isContentSpecificHeader(String paramString)
    {
      boolean bool = true;
      if ((!StringsKt.equals("Content-Length", paramString, true)) && (!StringsKt.equals("Content-Encoding", paramString, true)) && (!StringsKt.equals("Content-Type", paramString, true))) {
        bool = false;
      }
      return bool;
    }
    
    private final boolean isEndToEnd(String paramString)
    {
      boolean bool = true;
      if ((StringsKt.equals("Connection", paramString, true)) || (StringsKt.equals("Keep-Alive", paramString, true)) || (StringsKt.equals("Proxy-Authenticate", paramString, true)) || (StringsKt.equals("Proxy-Authorization", paramString, true)) || (StringsKt.equals("TE", paramString, true)) || (StringsKt.equals("Trailers", paramString, true)) || (StringsKt.equals("Transfer-Encoding", paramString, true)) || (StringsKt.equals("Upgrade", paramString, true))) {
        bool = false;
      }
      return bool;
    }
    
    private final Response stripBody(Response paramResponse)
    {
      ResponseBody localResponseBody;
      if (paramResponse != null) {
        localResponseBody = paramResponse.body();
      } else {
        localResponseBody = null;
      }
      if (localResponseBody != null) {
        paramResponse = paramResponse.newBuilder().body(null).build();
      }
      return paramResponse;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/cache/CacheInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */