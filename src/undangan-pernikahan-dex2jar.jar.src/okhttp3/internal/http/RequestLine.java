package okhttp3.internal.http;

import java.net.Proxy.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bJ\030\020\t\032\0020\n2\006\020\005\032\0020\0062\006\020\007\032\0020\bH\002J\016\020\013\032\0020\0042\006\020\f\032\0020\r¨\006\016"}, d2={"Lokhttp3/internal/http/RequestLine;", "", "()V", "get", "", "request", "Lokhttp3/Request;", "proxyType", "Ljava/net/Proxy$Type;", "includeAuthorityInRequestLine", "", "requestPath", "url", "Lokhttp3/HttpUrl;", "okhttp"}, k=1, mv={1, 4, 0})
public final class RequestLine
{
  public static final RequestLine INSTANCE = new RequestLine();
  
  private final boolean includeAuthorityInRequestLine(Request paramRequest, Proxy.Type paramType)
  {
    boolean bool;
    if ((!paramRequest.isHttps()) && (paramType == Proxy.Type.HTTP)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final String get(Request paramRequest, Proxy.Type paramType)
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    Intrinsics.checkNotNullParameter(paramType, "proxyType");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramRequest.method());
    localStringBuilder.append(' ');
    RequestLine localRequestLine = INSTANCE;
    if (localRequestLine.includeAuthorityInRequestLine(paramRequest, paramType)) {
      localStringBuilder.append(paramRequest.url());
    } else {
      localStringBuilder.append(localRequestLine.requestPath(paramRequest.url()));
    }
    localStringBuilder.append(" HTTP/1.1");
    paramRequest = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramRequest, "StringBuilder().apply(builderAction).toString()");
    return paramRequest;
  }
  
  public final String requestPath(HttpUrl paramHttpUrl)
  {
    Intrinsics.checkNotNullParameter(paramHttpUrl, "url");
    String str = paramHttpUrl.encodedPath();
    paramHttpUrl = paramHttpUrl.encodedQuery();
    if (paramHttpUrl != null) {
      paramHttpUrl = str + '?' + paramHttpUrl;
    } else {
      paramHttpUrl = str;
    }
    return paramHttpUrl;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http/RequestLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */