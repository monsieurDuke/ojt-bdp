package kotlin.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000<\n\000\n\002\020\002\n\000\n\002\020\000\n\002\020\013\n\002\020\005\n\002\020\f\n\002\020\031\n\002\020\006\n\002\020\007\n\002\020\b\n\002\020\t\n\002\020\n\n\002\b\002\n\002\020\016\n\002\b\003\032\023\020\000\032\0020\0012\b\020\002\032\004\030\0010\003H\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\004H\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\005H\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\006H\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\007H\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\bH\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\tH\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\nH\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\013H\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\fH\b\032\t\020\r\032\0020\001H\b\032\023\020\r\032\0020\0012\b\020\002\032\004\030\0010\003H\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\004H\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\005H\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\006H\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\007H\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\bH\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\tH\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\nH\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\013H\b\032\021\020\r\032\0020\0012\006\020\002\032\0020\fH\b\032\b\020\016\032\004\030\0010\017\032\b\020\020\032\0020\017H\007\032\n\020\021\032\004\030\0010\017H\007¨\006\022"}, d2={"print", "", "message", "", "", "", "", "", "", "", "", "", "", "println", "readLine", "", "readln", "readlnOrNull", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ConsoleKt
{
  private static final void print(byte paramByte)
  {
    System.out.print(Byte.valueOf(paramByte));
  }
  
  private static final void print(char paramChar)
  {
    System.out.print(paramChar);
  }
  
  private static final void print(double paramDouble)
  {
    System.out.print(paramDouble);
  }
  
  private static final void print(float paramFloat)
  {
    System.out.print(paramFloat);
  }
  
  private static final void print(int paramInt)
  {
    System.out.print(paramInt);
  }
  
  private static final void print(long paramLong)
  {
    System.out.print(paramLong);
  }
  
  private static final void print(Object paramObject)
  {
    System.out.print(paramObject);
  }
  
  private static final void print(short paramShort)
  {
    System.out.print(Short.valueOf(paramShort));
  }
  
  private static final void print(boolean paramBoolean)
  {
    System.out.print(paramBoolean);
  }
  
  private static final void print(char[] paramArrayOfChar)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "message");
    System.out.print(paramArrayOfChar);
  }
  
  private static final void println()
  {
    System.out.println();
  }
  
  private static final void println(byte paramByte)
  {
    System.out.println(Byte.valueOf(paramByte));
  }
  
  private static final void println(char paramChar)
  {
    System.out.println(paramChar);
  }
  
  private static final void println(double paramDouble)
  {
    System.out.println(paramDouble);
  }
  
  private static final void println(float paramFloat)
  {
    System.out.println(paramFloat);
  }
  
  private static final void println(int paramInt)
  {
    System.out.println(paramInt);
  }
  
  private static final void println(long paramLong)
  {
    System.out.println(paramLong);
  }
  
  private static final void println(Object paramObject)
  {
    System.out.println(paramObject);
  }
  
  private static final void println(short paramShort)
  {
    System.out.println(Short.valueOf(paramShort));
  }
  
  private static final void println(boolean paramBoolean)
  {
    System.out.println(paramBoolean);
  }
  
  private static final void println(char[] paramArrayOfChar)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "message");
    System.out.println(paramArrayOfChar);
  }
  
  public static final String readLine()
  {
    LineReader localLineReader = LineReader.INSTANCE;
    InputStream localInputStream = System.in;
    Intrinsics.checkNotNullExpressionValue(localInputStream, "`in`");
    Charset localCharset = Charset.defaultCharset();
    Intrinsics.checkNotNullExpressionValue(localCharset, "defaultCharset()");
    return localLineReader.readLine(localInputStream, localCharset);
  }
  
  public static final String readln()
  {
    String str = readlnOrNull();
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    if (str != null) {
      return str;
    }
    throw new ReadAfterEOFException("EOF has already been reached");
  }
  
  public static final String readlnOrNull()
  {
    String str = readLine();
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/ConsoleKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */