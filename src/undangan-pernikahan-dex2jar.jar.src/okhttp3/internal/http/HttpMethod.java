package okhttp3.internal.http;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\020\016\n\002\b\005\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\003\032\0020\0042\006\020\005\032\0020\006J\020\020\007\032\0020\0042\006\020\005\032\0020\006H\007J\016\020\b\032\0020\0042\006\020\005\032\0020\006J\016\020\t\032\0020\0042\006\020\005\032\0020\006J\020\020\n\032\0020\0042\006\020\005\032\0020\006H\007¨\006\013"}, d2={"Lokhttp3/internal/http/HttpMethod;", "", "()V", "invalidatesCache", "", "method", "", "permitsRequestBody", "redirectsToGet", "redirectsWithBody", "requiresRequestBody", "okhttp"}, k=1, mv={1, 4, 0})
public final class HttpMethod
{
  public static final HttpMethod INSTANCE = new HttpMethod();
  
  @JvmStatic
  public static final boolean permitsRequestBody(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "method");
    boolean bool;
    if ((!Intrinsics.areEqual(paramString, "GET")) && (!Intrinsics.areEqual(paramString, "HEAD"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @JvmStatic
  public static final boolean requiresRequestBody(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "method");
    boolean bool;
    if ((!Intrinsics.areEqual(paramString, "POST")) && (!Intrinsics.areEqual(paramString, "PUT")) && (!Intrinsics.areEqual(paramString, "PATCH")) && (!Intrinsics.areEqual(paramString, "PROPPATCH")) && (!Intrinsics.areEqual(paramString, "REPORT"))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public final boolean invalidatesCache(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "method");
    boolean bool;
    if ((!Intrinsics.areEqual(paramString, "POST")) && (!Intrinsics.areEqual(paramString, "PATCH")) && (!Intrinsics.areEqual(paramString, "PUT")) && (!Intrinsics.areEqual(paramString, "DELETE")) && (!Intrinsics.areEqual(paramString, "MOVE"))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public final boolean redirectsToGet(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "method");
    return Intrinsics.areEqual(paramString, "PROPFIND") ^ true;
  }
  
  public final boolean redirectsWithBody(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "method");
    return Intrinsics.areEqual(paramString, "PROPFIND");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http/HttpMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */