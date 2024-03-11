package okhttp3;

import java.io.IOException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\020\n\000\n\002\020\016\n\002\b\n\b\001\030\000 \f2\b\022\004\022\0020\0000\001:\001\fB\017\b\002\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\005\032\0020\003H\026R\016\020\002\032\0020\003X\004¢\006\002\n\000j\002\b\006j\002\b\007j\002\b\bj\002\b\tj\002\b\nj\002\b\013¨\006\r"}, d2={"Lokhttp3/Protocol;", "", "protocol", "", "(Ljava/lang/String;ILjava/lang/String;)V", "toString", "HTTP_1_0", "HTTP_1_1", "SPDY_3", "HTTP_2", "H2_PRIOR_KNOWLEDGE", "QUIC", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public enum Protocol
{
  private static final Protocol[] $VALUES;
  public static final Companion Companion = new Companion(null);
  private final String protocol;
  
  static
  {
    Protocol localProtocol4 = new Protocol("HTTP_1_0", 0, "http/1.0");
    HTTP_1_0 = localProtocol4;
    Protocol localProtocol1 = new Protocol("HTTP_1_1", 1, "http/1.1");
    HTTP_1_1 = localProtocol1;
    Protocol localProtocol5 = new Protocol("SPDY_3", 2, "spdy/3.1");
    SPDY_3 = localProtocol5;
    Protocol localProtocol2 = new Protocol("HTTP_2", 3, "h2");
    HTTP_2 = localProtocol2;
    Protocol localProtocol6 = new Protocol("H2_PRIOR_KNOWLEDGE", 4, "h2_prior_knowledge");
    H2_PRIOR_KNOWLEDGE = localProtocol6;
    Protocol localProtocol3 = new Protocol("QUIC", 5, "quic");
    QUIC = localProtocol3;
    $VALUES = new Protocol[] { localProtocol4, localProtocol1, localProtocol5, localProtocol2, localProtocol6, localProtocol3 };
  }
  
  private Protocol(String paramString)
  {
    this.protocol = paramString;
  }
  
  @JvmStatic
  public static final Protocol get(String paramString)
    throws IOException
  {
    return Companion.get(paramString);
  }
  
  public String toString()
  {
    return this.protocol;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\006H\007¨\006\007"}, d2={"Lokhttp3/Protocol$Companion;", "", "()V", "get", "Lokhttp3/Protocol;", "protocol", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    @JvmStatic
    public final Protocol get(String paramString)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramString, "protocol");
      String str = Protocol.access$getProtocol$p(Protocol.HTTP_1_0);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (Intrinsics.areEqual(paramString, str))
      {
        paramString = Protocol.HTTP_1_0;
      }
      else
      {
        str = Protocol.access$getProtocol$p(Protocol.HTTP_1_1);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        if (Intrinsics.areEqual(paramString, str))
        {
          paramString = Protocol.HTTP_1_1;
        }
        else
        {
          str = Protocol.access$getProtocol$p(Protocol.H2_PRIOR_KNOWLEDGE);
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          if (Intrinsics.areEqual(paramString, str))
          {
            paramString = Protocol.H2_PRIOR_KNOWLEDGE;
          }
          else
          {
            str = Protocol.access$getProtocol$p(Protocol.HTTP_2);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            if (Intrinsics.areEqual(paramString, str))
            {
              paramString = Protocol.HTTP_2;
            }
            else
            {
              str = Protocol.access$getProtocol$p(Protocol.SPDY_3);
              Log5ECF72.a(str);
              LogE84000.a(str);
              Log229316.a(str);
              if (Intrinsics.areEqual(paramString, str))
              {
                paramString = Protocol.SPDY_3;
              }
              else
              {
                str = Protocol.access$getProtocol$p(Protocol.QUIC);
                Log5ECF72.a(str);
                LogE84000.a(str);
                Log229316.a(str);
                if (!Intrinsics.areEqual(paramString, str)) {
                  break label209;
                }
                paramString = Protocol.QUIC;
              }
            }
          }
        }
      }
      return paramString;
      label209:
      throw ((Throwable)new IOException("Unexpected protocol: " + paramString));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/Protocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */