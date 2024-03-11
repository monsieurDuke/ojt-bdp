package kotlin;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import kotlin.internal.PlatformImplementations;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0004\n\000\n\002\020\021\n\002\030\002\n\002\020\003\n\002\b\005\n\002\020 \n\002\b\004\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\032\024\020\r\032\0020\016*\0020\0032\006\020\017\032\0020\003H\007\032\r\020\020\032\0020\016*\0020\003H\b\032\025\020\020\032\0020\016*\0020\0032\006\020\021\032\0020\022H\b\032\025\020\020\032\0020\016*\0020\0032\006\020\023\032\0020\024H\b\032\f\020\025\032\0020\026*\0020\003H\007\"!\020\000\032\b\022\004\022\0020\0020\001*\0020\0038F¢\006\f\022\004\b\004\020\005\032\004\b\006\020\007\"$\020\b\032\b\022\004\022\0020\0030\t*\0020\0038FX\004¢\006\f\022\004\b\n\020\005\032\004\b\013\020\f¨\006\027"}, d2={"stackTrace", "", "Ljava/lang/StackTraceElement;", "", "getStackTrace$annotations", "(Ljava/lang/Throwable;)V", "getStackTrace", "(Ljava/lang/Throwable;)[Ljava/lang/StackTraceElement;", "suppressedExceptions", "", "getSuppressedExceptions$annotations", "getSuppressedExceptions", "(Ljava/lang/Throwable;)Ljava/util/List;", "addSuppressed", "", "exception", "printStackTrace", "stream", "Ljava/io/PrintStream;", "writer", "Ljava/io/PrintWriter;", "stackTraceToString", "", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/ExceptionsKt")
class ExceptionsKt__ExceptionsKt
{
  public static final void addSuppressed(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    Intrinsics.checkNotNullParameter(paramThrowable1, "<this>");
    Intrinsics.checkNotNullParameter(paramThrowable2, "exception");
    if (paramThrowable1 != paramThrowable2) {
      PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(paramThrowable1, paramThrowable2);
    }
  }
  
  public static final StackTraceElement[] getStackTrace(Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "<this>");
    paramThrowable = paramThrowable.getStackTrace();
    Intrinsics.checkNotNull(paramThrowable);
    return paramThrowable;
  }
  
  public static final List<Throwable> getSuppressedExceptions(Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "<this>");
    return PlatformImplementationsKt.IMPLEMENTATIONS.getSuppressed(paramThrowable);
  }
  
  private static final void printStackTrace(Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "<this>");
    paramThrowable.printStackTrace();
  }
  
  private static final void printStackTrace(Throwable paramThrowable, PrintStream paramPrintStream)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "<this>");
    Intrinsics.checkNotNullParameter(paramPrintStream, "stream");
    paramThrowable.printStackTrace(paramPrintStream);
  }
  
  private static final void printStackTrace(Throwable paramThrowable, PrintWriter paramPrintWriter)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "<this>");
    Intrinsics.checkNotNullParameter(paramPrintWriter, "writer");
    paramThrowable.printStackTrace(paramPrintWriter);
  }
  
  public static final String stackTraceToString(Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "<this>");
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter((Writer)localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    localPrintWriter.flush();
    paramThrowable = localStringWriter.toString();
    Intrinsics.checkNotNullExpressionValue(paramThrowable, "sw.toString()");
    return paramThrowable;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ExceptionsKt__ExceptionsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */