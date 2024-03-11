package okhttp3.internal.http2;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\020\n\000\n\002\020\b\n\002\b\023\b\001\030\000 \0252\b\022\004\022\0020\0000\001:\001\025B\017\b\002\022\006\020\002\032\0020\003¢\006\002\020\004R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\005\020\006j\002\b\007j\002\b\bj\002\b\tj\002\b\nj\002\b\013j\002\b\fj\002\b\rj\002\b\016j\002\b\017j\002\b\020j\002\b\021j\002\b\022j\002\b\023j\002\b\024¨\006\026"}, d2={"Lokhttp3/internal/http2/ErrorCode;", "", "httpCode", "", "(Ljava/lang/String;II)V", "getHttpCode", "()I", "NO_ERROR", "PROTOCOL_ERROR", "INTERNAL_ERROR", "FLOW_CONTROL_ERROR", "SETTINGS_TIMEOUT", "STREAM_CLOSED", "FRAME_SIZE_ERROR", "REFUSED_STREAM", "CANCEL", "COMPRESSION_ERROR", "CONNECT_ERROR", "ENHANCE_YOUR_CALM", "INADEQUATE_SECURITY", "HTTP_1_1_REQUIRED", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public enum ErrorCode
{
  private static final ErrorCode[] $VALUES;
  public static final Companion Companion = new Companion(null);
  private final int httpCode;
  
  static
  {
    ErrorCode localErrorCode7 = new ErrorCode("NO_ERROR", 0, 0);
    NO_ERROR = localErrorCode7;
    ErrorCode localErrorCode13 = new ErrorCode("PROTOCOL_ERROR", 1, 1);
    PROTOCOL_ERROR = localErrorCode13;
    ErrorCode localErrorCode4 = new ErrorCode("INTERNAL_ERROR", 2, 2);
    INTERNAL_ERROR = localErrorCode4;
    ErrorCode localErrorCode9 = new ErrorCode("FLOW_CONTROL_ERROR", 3, 3);
    FLOW_CONTROL_ERROR = localErrorCode9;
    ErrorCode localErrorCode5 = new ErrorCode("SETTINGS_TIMEOUT", 4, 4);
    SETTINGS_TIMEOUT = localErrorCode5;
    ErrorCode localErrorCode6 = new ErrorCode("STREAM_CLOSED", 5, 5);
    STREAM_CLOSED = localErrorCode6;
    ErrorCode localErrorCode10 = new ErrorCode("FRAME_SIZE_ERROR", 6, 6);
    FRAME_SIZE_ERROR = localErrorCode10;
    ErrorCode localErrorCode12 = new ErrorCode("REFUSED_STREAM", 7, 7);
    REFUSED_STREAM = localErrorCode12;
    ErrorCode localErrorCode14 = new ErrorCode("CANCEL", 8, 8);
    CANCEL = localErrorCode14;
    ErrorCode localErrorCode11 = new ErrorCode("COMPRESSION_ERROR", 9, 9);
    COMPRESSION_ERROR = localErrorCode11;
    ErrorCode localErrorCode1 = new ErrorCode("CONNECT_ERROR", 10, 10);
    CONNECT_ERROR = localErrorCode1;
    ErrorCode localErrorCode3 = new ErrorCode("ENHANCE_YOUR_CALM", 11, 11);
    ENHANCE_YOUR_CALM = localErrorCode3;
    ErrorCode localErrorCode2 = new ErrorCode("INADEQUATE_SECURITY", 12, 12);
    INADEQUATE_SECURITY = localErrorCode2;
    ErrorCode localErrorCode8 = new ErrorCode("HTTP_1_1_REQUIRED", 13, 13);
    HTTP_1_1_REQUIRED = localErrorCode8;
    $VALUES = new ErrorCode[] { localErrorCode7, localErrorCode13, localErrorCode4, localErrorCode9, localErrorCode5, localErrorCode6, localErrorCode10, localErrorCode12, localErrorCode14, localErrorCode11, localErrorCode1, localErrorCode3, localErrorCode2, localErrorCode8 };
  }
  
  private ErrorCode(int paramInt)
  {
    this.httpCode = paramInt;
  }
  
  public final int getHttpCode()
  {
    return this.httpCode;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\003\032\004\030\0010\0042\006\020\005\032\0020\006¨\006\007"}, d2={"Lokhttp3/internal/http2/ErrorCode$Companion;", "", "()V", "fromHttp2", "Lokhttp3/internal/http2/ErrorCode;", "code", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final ErrorCode fromHttp2(int paramInt)
    {
      for (localErrorCode : )
      {
        int j;
        if (localErrorCode.getHttpCode() == paramInt) {
          j = 1;
        } else {
          j = 0;
        }
        if (j != 0) {
          break label56;
        }
      }
      ErrorCode localErrorCode = null;
      label56:
      return localErrorCode;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http2/ErrorCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */