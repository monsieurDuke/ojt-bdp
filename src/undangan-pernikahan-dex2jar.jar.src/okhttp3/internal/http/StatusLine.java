package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Protocol;
import okhttp3.Response;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\004\030\000 \n2\0020\001:\001\nB\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bJ\b\020\t\032\0020\007H\026R\020\020\004\032\0020\0058\006X\004¢\006\002\n\000R\020\020\006\032\0020\0078\006X\004¢\006\002\n\000R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006\013"}, d2={"Lokhttp3/internal/http/StatusLine;", "", "protocol", "Lokhttp3/Protocol;", "code", "", "message", "", "(Lokhttp3/Protocol;ILjava/lang/String;)V", "toString", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class StatusLine
{
  public static final Companion Companion = new Companion(null);
  public static final int HTTP_CONTINUE = 100;
  public static final int HTTP_MISDIRECTED_REQUEST = 421;
  public static final int HTTP_PERM_REDIRECT = 308;
  public static final int HTTP_TEMP_REDIRECT = 307;
  public final int code;
  public final String message;
  public final Protocol protocol;
  
  public StatusLine(Protocol paramProtocol, int paramInt, String paramString)
  {
    this.protocol = paramProtocol;
    this.code = paramInt;
    this.message = paramString;
  }
  
  public String toString()
  {
    Object localObject = new StringBuilder();
    if (this.protocol == Protocol.HTTP_1_0) {
      ((StringBuilder)localObject).append("HTTP/1.0");
    } else {
      ((StringBuilder)localObject).append("HTTP/1.1");
    }
    ((StringBuilder)localObject).append(' ').append(this.code);
    ((StringBuilder)localObject).append(' ').append(this.message);
    localObject = ((StringBuilder)localObject).toString();
    Intrinsics.checkNotNullExpressionValue(localObject, "StringBuilder().apply(builderAction).toString()");
    return (String)localObject;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\b\032\0020\t2\006\020\n\032\0020\013J\016\020\f\032\0020\t2\006\020\r\032\0020\016R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000¨\006\017"}, d2={"Lokhttp3/internal/http/StatusLine$Companion;", "", "()V", "HTTP_CONTINUE", "", "HTTP_MISDIRECTED_REQUEST", "HTTP_PERM_REDIRECT", "HTTP_TEMP_REDIRECT", "get", "Lokhttp3/internal/http/StatusLine;", "response", "Lokhttp3/Response;", "parse", "statusLine", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final StatusLine get(Response paramResponse)
    {
      Intrinsics.checkNotNullParameter(paramResponse, "response");
      return new StatusLine(paramResponse.protocol(), paramResponse.code(), paramResponse.message());
    }
    
    public final StatusLine parse(String paramString)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramString, "statusLine");
      int j;
      int i;
      Protocol localProtocol;
      if (StringsKt.startsWith$default(paramString, "HTTP/1.", false, 2, null))
      {
        if ((paramString.length() >= 9) && (paramString.charAt(8) == ' '))
        {
          j = paramString.charAt(7) - '0';
          i = 9;
          if (j == 0)
          {
            localProtocol = Protocol.HTTP_1_0;
          }
          else
          {
            if (j != 1) {
              break label76;
            }
            localProtocol = Protocol.HTTP_1_1;
          }
          break label155;
          label76:
          throw ((Throwable)new ProtocolException("Unexpected status line: " + paramString));
        }
        else
        {
          throw ((Throwable)new ProtocolException("Unexpected status line: " + paramString));
        }
      }
      else
      {
        if (!StringsKt.startsWith$default(paramString, "ICY ", false, 2, null)) {
          break label338;
        }
        localProtocol = Protocol.HTTP_1_0;
        i = 4;
      }
      label155:
      if (paramString.length() >= i + 3) {
        try
        {
          String str = paramString.substring(i, i + 3);
          Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          j = Integer.parseInt(str);
          str = "";
          if (paramString.length() > i + 3) {
            if (paramString.charAt(i + 3) == ' ')
            {
              str = paramString.substring(i + 4);
              Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.String).substring(startIndex)");
            }
            else
            {
              throw ((Throwable)new ProtocolException("Unexpected status line: " + paramString));
            }
          }
          return new StatusLine(localProtocol, j, str);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw ((Throwable)new ProtocolException("Unexpected status line: " + paramString));
        }
      }
      throw ((Throwable)new ProtocolException("Unexpected status line: " + paramString));
      label338:
      throw ((Throwable)new ProtocolException("Unexpected status line: " + paramString));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http/StatusLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */