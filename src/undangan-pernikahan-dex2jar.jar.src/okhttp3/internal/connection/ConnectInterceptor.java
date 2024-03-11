package okhttp3.internal.connection;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Response;
import okhttp3.internal.http.RealInterceptorChain;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\006H\026¨\006\007"}, d2={"Lokhttp3/internal/connection/ConnectInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"}, k=1, mv={1, 4, 0})
public final class ConnectInterceptor
  implements Interceptor
{
  public static final ConnectInterceptor INSTANCE = new ConnectInterceptor();
  
  public Response intercept(Interceptor.Chain paramChain)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramChain, "chain");
    RealInterceptorChain localRealInterceptorChain = (RealInterceptorChain)paramChain;
    return RealInterceptorChain.copy$okhttp$default(localRealInterceptorChain, 0, localRealInterceptorChain.getCall$okhttp().initExchange$okhttp((RealInterceptorChain)paramChain), null, 0, 0, 0, 61, null).proceed(localRealInterceptorChain.getRequest$okhttp());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/connection/ConnectInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */