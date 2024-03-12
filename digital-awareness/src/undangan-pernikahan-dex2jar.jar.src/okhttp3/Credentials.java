package okhttp3;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okio.ByteString;
import okio.ByteString.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\003\n\002\030\002\n\000\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\"\020\003\032\0020\0042\006\020\005\032\0020\0042\006\020\006\032\0020\0042\b\b\002\020\007\032\0020\bH\007¨\006\t"}, d2={"Lokhttp3/Credentials;", "", "()V", "basic", "", "username", "password", "charset", "Ljava/nio/charset/Charset;", "okhttp"}, k=1, mv={1, 4, 0})
public final class Credentials
{
  public static final Credentials INSTANCE = new Credentials();
  
  @JvmStatic
  public static final String basic(String paramString1, String paramString2)
  {
    paramString1 = basic$default(paramString1, paramString2, null, 4, null);
    Log5ECF72.a(paramString1);
    LogE84000.a(paramString1);
    Log229316.a(paramString1);
    return paramString1;
  }
  
  @JvmStatic
  public static final String basic(String paramString1, String paramString2, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramString1, "username");
    Intrinsics.checkNotNullParameter(paramString2, "password");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramString1 = paramString1 + ':' + paramString2;
    paramString1 = ByteString.Companion.encodeString(paramString1, paramCharset).base64();
    return "Basic " + paramString1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/Credentials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */