package okhttp3.internal.cache;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\b\030\000 \0132\0020\001:\002\013\fB\033\b\000\022\b\020\002\032\004\030\0010\003\022\b\020\004\032\004\030\0010\005¢\006\002\020\006R\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\007\020\bR\023\020\002\032\004\030\0010\003¢\006\b\n\000\032\004\b\t\020\n¨\006\r"}, d2={"Lokhttp3/internal/cache/CacheStrategy;", "", "networkRequest", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "(Lokhttp3/Request;Lokhttp3/Response;)V", "getCacheResponse", "()Lokhttp3/Response;", "getNetworkRequest", "()Lokhttp3/Request;", "Companion", "Factory", "okhttp"}, k=1, mv={1, 4, 0})
public final class CacheStrategy
{
  public static final Companion Companion = new Companion(null);
  private final Response cacheResponse;
  private final Request networkRequest;
  
  public CacheStrategy(Request paramRequest, Response paramResponse)
  {
    this.networkRequest = paramRequest;
    this.cacheResponse = paramResponse;
  }
  
  public final Response getCacheResponse()
  {
    return this.cacheResponse;
  }
  
  public final Request getNetworkRequest()
  {
    return this.networkRequest;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\b¨\006\t"}, d2={"Lokhttp3/internal/cache/CacheStrategy$Companion;", "", "()V", "isCacheable", "", "response", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final boolean isCacheable(Response paramResponse, Request paramRequest)
    {
      Intrinsics.checkNotNullParameter(paramResponse, "response");
      Intrinsics.checkNotNullParameter(paramRequest, "request");
      int i = paramResponse.code();
      boolean bool2 = false;
      switch (i)
      {
      default: 
        return false;
      case 302: 
      case 307: 
        String str = Response.header$default(paramResponse, "Expires", null, 2, null);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        if ((str == null) && (paramResponse.cacheControl().maxAgeSeconds() == -1) && (!paramResponse.cacheControl().isPublic()) && (!paramResponse.cacheControl().isPrivate())) {
          return false;
        }
        break;
      }
      boolean bool1 = bool2;
      if (!paramResponse.cacheControl().noStore())
      {
        bool1 = bool2;
        if (!paramRequest.cacheControl().noStore()) {
          bool1 = true;
        }
      }
      return bool1;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000B\n\002\030\002\n\002\020\000\n\000\n\002\020\t\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\030\0002\0020\001B\037\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\b\020\006\032\004\030\0010\007¢\006\002\020\bJ\b\020\027\032\0020\003H\002J\006\020\030\032\0020\031J\b\020\032\032\0020\031H\002J\b\020\033\032\0020\003H\002J\020\020\034\032\0020\0352\006\020\004\032\0020\005H\002J\b\020\036\032\0020\035H\002R\016\020\t\032\0020\nX\016¢\006\002\n\000R\020\020\006\032\004\030\0010\007X\004¢\006\002\n\000R\020\020\013\032\004\030\0010\fX\016¢\006\002\n\000R\020\020\r\032\004\030\0010\016X\016¢\006\002\n\000R\020\020\017\032\004\030\0010\016X\016¢\006\002\n\000R\020\020\020\032\004\030\0010\fX\016¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\021\032\0020\003X\016¢\006\002\n\000R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\022\020\023R\016\020\024\032\0020\003X\016¢\006\002\n\000R\020\020\025\032\004\030\0010\016X\016¢\006\002\n\000R\020\020\026\032\004\030\0010\fX\016¢\006\002\n\000¨\006\037"}, d2={"Lokhttp3/internal/cache/CacheStrategy$Factory;", "", "nowMillis", "", "request", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "(JLokhttp3/Request;Lokhttp3/Response;)V", "ageSeconds", "", "etag", "", "expires", "Ljava/util/Date;", "lastModified", "lastModifiedString", "receivedResponseMillis", "getRequest$okhttp", "()Lokhttp3/Request;", "sentRequestMillis", "servedDate", "servedDateString", "cacheResponseAge", "compute", "Lokhttp3/internal/cache/CacheStrategy;", "computeCandidate", "computeFreshnessLifetime", "hasConditions", "", "isFreshnessLifetimeHeuristic", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Factory
  {
    private int ageSeconds;
    private final Response cacheResponse;
    private String etag;
    private Date expires;
    private Date lastModified;
    private String lastModifiedString;
    private final long nowMillis;
    private long receivedResponseMillis;
    private final Request request;
    private long sentRequestMillis;
    private Date servedDate;
    private String servedDateString;
    
    public Factory(long paramLong, Request paramRequest, Response paramResponse)
    {
      this.nowMillis = paramLong;
      this.request = paramRequest;
      this.cacheResponse = paramResponse;
      this.ageSeconds = -1;
      if (paramResponse != null)
      {
        this.sentRequestMillis = paramResponse.sentRequestAtMillis();
        this.receivedResponseMillis = paramResponse.receivedResponseAtMillis();
        paramRequest = paramResponse.headers();
        int i = 0;
        int j = paramRequest.size();
        while (i < j)
        {
          String str = paramRequest.name(i);
          paramResponse = paramRequest.value(i);
          if (StringsKt.equals(str, "Date", true))
          {
            this.servedDate = DatesKt.toHttpDateOrNull(paramResponse);
            this.servedDateString = paramResponse;
          }
          else if (StringsKt.equals(str, "Expires", true))
          {
            this.expires = DatesKt.toHttpDateOrNull(paramResponse);
          }
          else if (StringsKt.equals(str, "Last-Modified", true))
          {
            this.lastModified = DatesKt.toHttpDateOrNull(paramResponse);
            this.lastModifiedString = paramResponse;
          }
          else if (StringsKt.equals(str, "ETag", true))
          {
            this.etag = paramResponse;
          }
          else if (StringsKt.equals(str, "Age", true))
          {
            this.ageSeconds = Util.toNonNegativeInt(paramResponse, -1);
          }
          i++;
        }
      }
    }
    
    private final long cacheResponseAge()
    {
      Date localDate = this.servedDate;
      long l1 = 0L;
      if (localDate != null) {
        l1 = Math.max(0L, this.receivedResponseMillis - localDate.getTime());
      }
      if (this.ageSeconds != -1) {
        l1 = Math.max(l1, TimeUnit.SECONDS.toMillis(this.ageSeconds));
      }
      long l2 = this.receivedResponseMillis;
      return l1 + (l2 - this.sentRequestMillis) + (this.nowMillis - l2);
    }
    
    private final CacheStrategy computeCandidate()
    {
      if (this.cacheResponse == null) {
        return new CacheStrategy(this.request, null);
      }
      if ((this.request.isHttps()) && (this.cacheResponse.handshake() == null)) {
        return new CacheStrategy(this.request, null);
      }
      if (!CacheStrategy.Companion.isCacheable(this.cacheResponse, this.request)) {
        return new CacheStrategy(this.request, null);
      }
      Object localObject2 = this.request.cacheControl();
      if ((!((CacheControl)localObject2).noCache()) && (!hasConditions(this.request)))
      {
        Object localObject1 = this.cacheResponse.cacheControl();
        long l5 = cacheResponseAge();
        long l2 = computeFreshnessLifetime();
        long l1 = l2;
        if (((CacheControl)localObject2).maxAgeSeconds() != -1) {
          l1 = Math.min(l2, TimeUnit.SECONDS.toMillis(((CacheControl)localObject2).maxAgeSeconds()));
        }
        l2 = 0L;
        if (((CacheControl)localObject2).minFreshSeconds() != -1) {
          l2 = TimeUnit.SECONDS.toMillis(((CacheControl)localObject2).minFreshSeconds());
        }
        long l4 = 0L;
        long l3 = l4;
        if (!((CacheControl)localObject1).mustRevalidate())
        {
          l3 = l4;
          if (((CacheControl)localObject2).maxStaleSeconds() != -1) {
            l3 = TimeUnit.SECONDS.toMillis(((CacheControl)localObject2).maxStaleSeconds());
          }
        }
        if ((!((CacheControl)localObject1).noCache()) && (l5 + l2 < l1 + l3))
        {
          localObject1 = this.cacheResponse.newBuilder();
          if (l5 + l2 >= l1) {
            ((Response.Builder)localObject1).addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
          }
          if ((l5 > 86400000L) && (isFreshnessLifetimeHeuristic())) {
            ((Response.Builder)localObject1).addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
          }
          return new CacheStrategy(null, ((Response.Builder)localObject1).build());
        }
        if (this.etag != null)
        {
          localObject1 = "If-None-Match";
          localObject2 = this.etag;
        }
        else if (this.lastModified != null)
        {
          localObject1 = "If-Modified-Since";
          localObject2 = this.lastModifiedString;
        }
        else
        {
          if (this.servedDate == null) {
            break label434;
          }
          localObject1 = "If-Modified-Since";
          localObject2 = this.servedDateString;
        }
        Headers.Builder localBuilder = this.request.headers().newBuilder();
        Intrinsics.checkNotNull(localObject2);
        localBuilder.addLenient$okhttp((String)localObject1, (String)localObject2);
        localObject1 = this.request.newBuilder().headers(localBuilder.build()).build();
        return new CacheStrategy((Request)localObject1, this.cacheResponse);
        label434:
        return new CacheStrategy(this.request, null);
      }
      return new CacheStrategy(this.request, null);
    }
    
    private final long computeFreshnessLifetime()
    {
      Object localObject = this.cacheResponse;
      Intrinsics.checkNotNull(localObject);
      localObject = ((Response)localObject).cacheControl();
      if (((CacheControl)localObject).maxAgeSeconds() != -1) {
        return TimeUnit.SECONDS.toMillis(((CacheControl)localObject).maxAgeSeconds());
      }
      localObject = this.expires;
      long l1 = 0L;
      long l2;
      if (localObject != null)
      {
        Date localDate = this.servedDate;
        if (localDate != null) {
          l2 = localDate.getTime();
        } else {
          l2 = this.receivedResponseMillis;
        }
        l2 = ((Date)localObject).getTime() - l2;
        if (l2 > 0L) {
          l1 = l2;
        }
        return l1;
      }
      if ((this.lastModified != null) && (this.cacheResponse.request().url().query() == null))
      {
        localObject = this.servedDate;
        if (localObject != null) {
          l2 = ((Date)localObject).getTime();
        } else {
          l2 = this.sentRequestMillis;
        }
        localObject = this.lastModified;
        Intrinsics.checkNotNull(localObject);
        l2 -= ((Date)localObject).getTime();
        if (l2 > 0L) {
          l1 = l2 / 10;
        }
        return l1;
      }
      return 0L;
    }
    
    private final boolean hasConditions(Request paramRequest)
    {
      boolean bool;
      if ((paramRequest.header("If-Modified-Since") == null) && (paramRequest.header("If-None-Match") == null)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    private final boolean isFreshnessLifetimeHeuristic()
    {
      Response localResponse = this.cacheResponse;
      Intrinsics.checkNotNull(localResponse);
      boolean bool;
      if ((localResponse.cacheControl().maxAgeSeconds() == -1) && (this.expires == null)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final CacheStrategy compute()
    {
      CacheStrategy localCacheStrategy = computeCandidate();
      if ((localCacheStrategy.getNetworkRequest() != null) && (this.request.cacheControl().onlyIfCached())) {
        return new CacheStrategy(null, null);
      }
      return localCacheStrategy;
    }
    
    public final Request getRequest$okhttp()
    {
      return this.request;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/cache/CacheStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */