package kotlin.coroutines.cancellation;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;

@Metadata(d1={"\000\036\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\003\n\002\030\002\n\002\b\003\032!\020\000\032\0060\001j\002`\0022\b\020\003\032\004\030\0010\0042\b\020\005\032\004\030\0010\006H\b\032\027\020\000\032\0060\001j\002`\0022\b\020\005\032\004\030\0010\006H\b*\032\b\007\020\000\"\0020\0012\0020\001B\f\b\007\022\b\b\b\022\004\b\b(\t¨\006\n"}, d2={"CancellationException", "Ljava/util/concurrent/CancellationException;", "Lkotlin/coroutines/cancellation/CancellationException;", "message", "", "cause", "", "Lkotlin/SinceKotlin;", "version", "1.4", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class CancellationExceptionKt
{
  private static final CancellationException CancellationException(String paramString, Throwable paramThrowable)
  {
    paramString = new CancellationException(paramString);
    paramString.initCause(paramThrowable);
    return paramString;
  }
  
  private static final CancellationException CancellationException(Throwable paramThrowable)
  {
    if (paramThrowable != null) {
      localObject = paramThrowable.toString();
    } else {
      localObject = null;
    }
    Object localObject = new CancellationException((String)localObject);
    ((CancellationException)localObject).initCause(paramThrowable);
    return (CancellationException)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/coroutines/cancellation/CancellationExceptionKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */