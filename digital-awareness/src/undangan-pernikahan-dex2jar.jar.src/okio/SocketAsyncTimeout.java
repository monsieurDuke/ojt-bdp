package okio;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\006H\024J\b\020\b\032\0020\tH\024R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\n"}, d2={"Lokio/SocketAsyncTimeout;", "Lokio/AsyncTimeout;", "socket", "Ljava/net/Socket;", "(Ljava/net/Socket;)V", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "", "okio"}, k=1, mv={1, 4, 0})
final class SocketAsyncTimeout
  extends AsyncTimeout
{
  private final Socket socket;
  
  public SocketAsyncTimeout(Socket paramSocket)
  {
    this.socket = paramSocket;
  }
  
  protected IOException newTimeoutException(IOException paramIOException)
  {
    SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException("timeout");
    if (paramIOException != null) {
      localSocketTimeoutException.initCause((Throwable)paramIOException);
    }
    return (IOException)localSocketTimeoutException;
  }
  
  protected void timedOut()
  {
    try
    {
      this.socket.close();
    }
    catch (AssertionError localAssertionError)
    {
      if (Okio.isAndroidGetsocknameError(localAssertionError)) {
        Okio__JvmOkioKt.access$getLogger$p().log(Level.WARNING, "Failed to close timed out socket " + this.socket, (Throwable)localAssertionError);
      } else {
        throw ((Throwable)localAssertionError);
      }
    }
    catch (Exception localException)
    {
      Okio__JvmOkioKt.access$getLogger$p().log(Level.WARNING, "Failed to close timed out socket " + this.socket, (Throwable)localException);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/SocketAsyncTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */