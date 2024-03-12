package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000z\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\n\000\032\022\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004\032\034\020\005\032\0020\001*\0020\0022\006\020\006\032\0020\0072\b\b\002\020\b\032\0020\t\032!\020\n\032\0020\013*\0020\0022\b\b\002\020\b\032\0020\t2\b\b\002\020\f\032\0020\rH\b\032!\020\016\032\0020\017*\0020\0022\b\b\002\020\b\032\0020\t2\b\b\002\020\f\032\0020\rH\b\032B\020\020\032\0020\001*\0020\00226\020\021\0322\022\023\022\0210\004¢\006\f\b\023\022\b\b\024\022\004\b\b(\025\022\023\022\0210\r¢\006\f\b\023\022\b\b\024\022\004\b\b(\026\022\004\022\0020\0010\022\032J\020\020\032\0020\001*\0020\0022\006\020\027\032\0020\r26\020\021\0322\022\023\022\0210\004¢\006\f\b\023\022\b\b\024\022\004\b\b(\025\022\023\022\0210\r¢\006\f\b\023\022\b\b\024\022\004\b\b(\026\022\004\022\0020\0010\022\0327\020\030\032\0020\001*\0020\0022\b\b\002\020\b\032\0020\t2!\020\021\032\035\022\023\022\0210\007¢\006\f\b\023\022\b\b\024\022\004\b\b(\032\022\004\022\0020\0010\031\032\r\020\033\032\0020\034*\0020\002H\b\032\r\020\035\032\0020\036*\0020\002H\b\032\027\020\037\032\0020 *\0020\0022\b\b\002\020\b\032\0020\tH\b\032\n\020!\032\0020\004*\0020\002\032\032\020\"\032\b\022\004\022\0020\0070#*\0020\0022\b\b\002\020\b\032\0020\t\032\024\020$\032\0020\007*\0020\0022\b\b\002\020\b\032\0020\t\032\027\020%\032\0020&*\0020\0022\b\b\002\020\b\032\0020\tH\b\032B\020'\032\002H(\"\004\b\000\020(*\0020\0022\b\b\002\020\b\032\0020\t2\030\020)\032\024\022\n\022\b\022\004\022\0020\0070*\022\004\022\002H(0\031H\bø\001\000ø\001\001¢\006\002\020+\032\022\020-\032\0020\001*\0020\0022\006\020\003\032\0020\004\032\034\020.\032\0020\001*\0020\0022\006\020\006\032\0020\0072\b\b\002\020\b\032\0020\t\032\027\020/\032\00200*\0020\0022\b\b\002\020\b\032\0020\tH\b\002\017\n\005\b20\001\n\006\b\021(,0\001¨\0061"}, d2={"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Requires newer compiler version to be inlined correctly.", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/io/FilesKt")
class FilesKt__FileReadWriteKt
  extends FilesKt__FilePathComponentsKt
{
  public static final void appendBytes(File paramFile, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
    paramFile = (Closeable)new FileOutputStream(paramFile, true);
    try
    {
      ((FileOutputStream)paramFile).write(paramArrayOfByte);
      paramArrayOfByte = Unit.INSTANCE;
      CloseableKt.closeFinally(paramFile, null);
      return;
    }
    finally
    {
      try
      {
        throw localThrowable;
      }
      finally
      {
        CloseableKt.closeFinally(paramFile, localThrowable);
      }
    }
  }
  
  public static final void appendText(File paramFile, String paramString, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "text");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramString = paramString.getBytes(paramCharset);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).getBytes(charset)");
    FilesKt.appendBytes(paramFile, paramString);
  }
  
  private static final BufferedReader bufferedReader(File paramFile, Charset paramCharset, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramFile = (Reader)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedReader)) {
      paramFile = (BufferedReader)paramFile;
    } else {
      paramFile = new BufferedReader(paramFile, paramInt);
    }
    return paramFile;
  }
  
  private static final BufferedWriter bufferedWriter(File paramFile, Charset paramCharset, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramFile = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedWriter)) {
      paramFile = (BufferedWriter)paramFile;
    } else {
      paramFile = new BufferedWriter(paramFile, paramInt);
    }
    return paramFile;
  }
  
  /* Error */
  public static final void forEachBlock(File paramFile, int paramInt, Function2<? super byte[], ? super Integer, Unit> paramFunction2)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 73
    //   3: invokestatic 79	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc -84
    //   9: invokestatic 79	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: iload_1
    //   13: sipush 512
    //   16: invokestatic 178	kotlin/ranges/RangesKt:coerceAtLeast	(II)I
    //   19: newarray <illegal type>
    //   21: astore_3
    //   22: new 134	java/io/FileInputStream
    //   25: dup
    //   26: aload_0
    //   27: invokespecial 137	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   30: checkcast 87	java/io/Closeable
    //   33: astore_0
    //   34: aload_0
    //   35: checkcast 134	java/io/FileInputStream
    //   38: astore 4
    //   40: aload 4
    //   42: aload_3
    //   43: invokevirtual 182	java/io/FileInputStream:read	([B)I
    //   46: istore_1
    //   47: iload_1
    //   48: ifgt +13 -> 61
    //   51: getstatic 97	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   54: astore_2
    //   55: aload_0
    //   56: aconst_null
    //   57: invokestatic 103	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   60: return
    //   61: aload_2
    //   62: aload_3
    //   63: iload_1
    //   64: invokestatic 188	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   67: invokeinterface 194 3 0
    //   72: pop
    //   73: goto -33 -> 40
    //   76: astore_3
    //   77: aload_3
    //   78: athrow
    //   79: astore_2
    //   80: aload_0
    //   81: aload_3
    //   82: invokestatic 103	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   85: aload_2
    //   86: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	87	0	paramFile	File
    //   0	87	1	paramInt	int
    //   0	87	2	paramFunction2	Function2<? super byte[], ? super Integer, Unit>
    //   21	42	3	arrayOfByte	byte[]
    //   76	6	3	localThrowable	Throwable
    //   38	3	4	localFileInputStream	FileInputStream
    // Exception table:
    //   from	to	target	type
    //   34	40	76	finally
    //   40	47	76	finally
    //   51	55	76	finally
    //   61	73	76	finally
    //   77	79	79	finally
  }
  
  public static final void forEachBlock(File paramFile, Function2<? super byte[], ? super Integer, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    FilesKt.forEachBlock(paramFile, 4096, paramFunction2);
  }
  
  public static final void forEachLine(File paramFile, Charset paramCharset, Function1<? super String, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    TextStreamsKt.forEachLine((Reader)new BufferedReader((Reader)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset)), paramFunction1);
  }
  
  private static final FileInputStream inputStream(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    return new FileInputStream(paramFile);
  }
  
  private static final FileOutputStream outputStream(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    return new FileOutputStream(paramFile);
  }
  
  private static final PrintWriter printWriter(File paramFile, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramFile = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedWriter)) {
      paramFile = (BufferedWriter)paramFile;
    } else {
      paramFile = new BufferedWriter(paramFile, 8192);
    }
    return new PrintWriter((Writer)paramFile);
  }
  
  public static final byte[] readBytes(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Closeable localCloseable = (Closeable)new FileInputStream(paramFile);
    try
    {
      Object localObject3 = (FileInputStream)localCloseable;
      int j = 0;
      long l = paramFile.length();
      if (l <= 2147483647L)
      {
        int i = (int)l;
        localObject1 = new byte[i];
        while (i > 0)
        {
          int k = ((FileInputStream)localObject3).read((byte[])localObject1, j, i);
          if (k < 0) {
            break;
          }
          i -= k;
          j += k;
        }
        if (i > 0)
        {
          paramFile = Arrays.copyOf((byte[])localObject1, j);
          Intrinsics.checkNotNullExpressionValue(paramFile, "copyOf(this, newSize)");
        }
        else
        {
          i = ((FileInputStream)localObject3).read();
          if (i == -1)
          {
            paramFile = (File)localObject1;
          }
          else
          {
            localObject2 = new kotlin/io/ExposingBufferByteArrayOutputStream;
            ((ExposingBufferByteArrayOutputStream)localObject2).<init>(8193);
            ((ExposingBufferByteArrayOutputStream)localObject2).write(i);
            ByteStreamsKt.copyTo$default((InputStream)localObject3, (OutputStream)localObject2, 0, 2, null);
            i = localObject1.length + ((ExposingBufferByteArrayOutputStream)localObject2).size();
            if (i < 0) {
              break label212;
            }
            localObject3 = ((ExposingBufferByteArrayOutputStream)localObject2).getBuffer();
            paramFile = Arrays.copyOf((byte[])localObject1, i);
            Intrinsics.checkNotNullExpressionValue(paramFile, "copyOf(this, newSize)");
            paramFile = ArraysKt.copyInto((byte[])localObject3, paramFile, localObject1.length, 0, ((ExposingBufferByteArrayOutputStream)localObject2).size());
          }
        }
        CloseableKt.closeFinally(localCloseable, null);
        return paramFile;
        label212:
        localObject1 = new java/lang/OutOfMemoryError;
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((OutOfMemoryError)localObject1).<init>("File " + paramFile + " is too big to fit in memory.");
        throw ((Throwable)localObject1);
      }
      Object localObject1 = new java/lang/OutOfMemoryError;
      Object localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((OutOfMemoryError)localObject1).<init>("File " + paramFile + " is too big (" + l + " bytes) to fit in memory.");
      throw ((Throwable)localObject1);
    }
    finally
    {
      try
      {
        throw localThrowable;
      }
      finally
      {
        CloseableKt.closeFinally(localCloseable, localThrowable);
      }
    }
  }
  
  public static final List<String> readLines(File paramFile, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    ArrayList localArrayList = new ArrayList();
    FilesKt.forEachLine(paramFile, paramCharset, (Function1)new Lambda(localArrayList)
    {
      final ArrayList<String> $result;
      
      public final void invoke(String paramAnonymousString)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousString, "it");
        this.$result.add(paramAnonymousString);
      }
    });
    return (List)localArrayList;
  }
  
  public static final String readText(File paramFile, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramFile = (Closeable)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
    try
    {
      paramCharset = TextStreamsKt.readText((Reader)paramFile);
      Log5ECF72.a(paramCharset);
      LogE84000.a(paramCharset);
      Log229316.a(paramCharset);
      CloseableKt.closeFinally(paramFile, null);
      return paramCharset;
    }
    finally
    {
      try
      {
        throw paramCharset;
      }
      finally
      {
        CloseableKt.closeFinally(paramFile, paramCharset);
      }
    }
  }
  
  private static final InputStreamReader reader(File paramFile, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    return new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
  }
  
  public static final <T> T useLines(File paramFile, Charset paramCharset, Function1<? super Sequence<String>, ? extends T> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramFile = (Reader)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedReader)) {
      paramFile = (BufferedReader)paramFile;
    } else {
      paramFile = new BufferedReader(paramFile, 8192);
    }
    paramFile = (Closeable)paramFile;
    try
    {
      paramCharset = paramFunction1.invoke(TextStreamsKt.lineSequence((BufferedReader)paramFile));
      InlineMarker.finallyStart(1);
      CloseableKt.closeFinally(paramFile, null);
      InlineMarker.finallyEnd(1);
      return paramCharset;
    }
    finally
    {
      try
      {
        throw paramCharset;
      }
      finally
      {
        InlineMarker.finallyStart(1);
        CloseableKt.closeFinally(paramFile, paramCharset);
        InlineMarker.finallyEnd(1);
      }
    }
  }
  
  public static final void writeBytes(File paramFile, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
    paramFile = (Closeable)new FileOutputStream(paramFile);
    try
    {
      ((FileOutputStream)paramFile).write(paramArrayOfByte);
      paramArrayOfByte = Unit.INSTANCE;
      CloseableKt.closeFinally(paramFile, null);
      return;
    }
    finally
    {
      try
      {
        throw localThrowable;
      }
      finally
      {
        CloseableKt.closeFinally(paramFile, localThrowable);
      }
    }
  }
  
  public static final void writeText(File paramFile, String paramString, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "text");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramString = paramString.getBytes(paramCharset);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).getBytes(charset)");
    FilesKt.writeBytes(paramFile, paramString);
  }
  
  private static final OutputStreamWriter writer(File paramFile, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    return new OutputStreamWriter((OutputStream)new FileOutputStream(paramFile), paramCharset);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/FilesKt__FileReadWriteKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */