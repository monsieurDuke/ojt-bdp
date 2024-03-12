package okio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"okio/Okio__JvmOkioKt", "okio/Okio__OkioKt"}, k=4, mv={1, 4, 0})
public final class Okio
{
  public static final Sink appendingSink(File paramFile)
    throws FileNotFoundException
  {
    return Okio__JvmOkioKt.appendingSink(paramFile);
  }
  
  public static final Sink blackhole()
  {
    return Okio__OkioKt.blackhole();
  }
  
  public static final BufferedSink buffer(Sink paramSink)
  {
    return Okio__OkioKt.buffer(paramSink);
  }
  
  public static final BufferedSource buffer(Source paramSource)
  {
    return Okio__OkioKt.buffer(paramSource);
  }
  
  public static final boolean isAndroidGetsocknameError(AssertionError paramAssertionError)
  {
    return Okio__JvmOkioKt.isAndroidGetsocknameError(paramAssertionError);
  }
  
  public static final Sink sink(File paramFile)
    throws FileNotFoundException
  {
    return sink$default(paramFile, false, 1, null);
  }
  
  public static final Sink sink(File paramFile, boolean paramBoolean)
    throws FileNotFoundException
  {
    return Okio__JvmOkioKt.sink(paramFile, paramBoolean);
  }
  
  public static final Sink sink(OutputStream paramOutputStream)
  {
    return Okio__JvmOkioKt.sink(paramOutputStream);
  }
  
  public static final Sink sink(Socket paramSocket)
    throws IOException
  {
    return Okio__JvmOkioKt.sink(paramSocket);
  }
  
  public static final Sink sink(Path paramPath, OpenOption... paramVarArgs)
    throws IOException
  {
    return Okio__JvmOkioKt.sink(paramPath, paramVarArgs);
  }
  
  public static final Source source(File paramFile)
    throws FileNotFoundException
  {
    return Okio__JvmOkioKt.source(paramFile);
  }
  
  public static final Source source(InputStream paramInputStream)
  {
    return Okio__JvmOkioKt.source(paramInputStream);
  }
  
  public static final Source source(Socket paramSocket)
    throws IOException
  {
    return Okio__JvmOkioKt.source(paramSocket);
  }
  
  public static final Source source(Path paramPath, OpenOption... paramVarArgs)
    throws IOException
  {
    return Okio__JvmOkioKt.source(paramPath, paramVarArgs);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/Okio.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */