package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;

@Metadata(d1={"\000 \n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\003\n\000\n\002\020\002\n\002\b\002\032\036\020\000\032\0060\001j\002`\0022\b\020\003\032\004\030\0010\0042\b\020\005\032\004\030\0010\006\032\025\020\007\032\0020\b*\0020\0062\006\020\t\032\0020\006H\b*\n\020\000\"\0020\0012\0020\001¨\006\n"}, d2={"CancellationException", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "message", "", "cause", "", "addSuppressedThrowable", "", "other", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ExceptionsKt
{
  public static final CancellationException CancellationException(String paramString, Throwable paramThrowable)
  {
    paramString = new CancellationException(paramString);
    paramString.initCause(paramThrowable);
    return paramString;
  }
  
  public static final void addSuppressedThrowable(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    kotlin.ExceptionsKt.addSuppressed(paramThrowable1, paramThrowable2);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ExceptionsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */