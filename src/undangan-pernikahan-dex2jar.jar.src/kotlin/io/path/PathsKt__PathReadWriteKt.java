package kotlin.io.path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\001\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\020\034\n\002\020\r\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\021\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\020\016\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\002\n\002\030\002\n\002\b\r\n\002\030\002\n\002\b\002\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004H\b\032%\020\005\032\0020\002*\0020\0022\f\020\006\032\b\022\004\022\0020\b0\0072\b\b\002\020\t\032\0020\nH\b\032%\020\005\032\0020\002*\0020\0022\f\020\006\032\b\022\004\022\0020\b0\0132\b\b\002\020\t\032\0020\nH\b\032\036\020\f\032\0020\001*\0020\0022\006\020\r\032\0020\b2\b\b\002\020\t\032\0020\nH\007\032:\020\016\032\0020\017*\0020\0022\b\b\002\020\t\032\0020\n2\b\b\002\020\020\032\0020\0212\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\020\025\032:\020\026\032\0020\027*\0020\0022\b\b\002\020\t\032\0020\n2\b\b\002\020\020\032\0020\0212\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\020\030\032=\020\031\032\0020\001*\0020\0022\b\b\002\020\t\032\0020\n2!\020\032\032\035\022\023\022\0210\034¢\006\f\b\035\022\b\b\036\022\004\b\b(\037\022\004\022\0020\0010\033H\bø\001\000\032&\020 \032\0020!*\0020\0022\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\020\"\032&\020#\032\0020$*\0020\0022\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\020%\032\r\020&\032\0020\004*\0020\002H\b\032\035\020'\032\b\022\004\022\0020\0340(*\0020\0022\b\b\002\020\t\032\0020\nH\b\032\026\020)\032\0020\034*\0020\0022\b\b\002\020\t\032\0020\nH\007\0320\020*\032\0020+*\0020\0022\b\b\002\020\t\032\0020\n2\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\020,\032?\020-\032\002H.\"\004\b\000\020.*\0020\0022\b\b\002\020\t\032\0020\n2\030\020/\032\024\022\n\022\b\022\004\022\0020\0340\013\022\004\022\002H.0\033H\bø\001\000¢\006\002\0200\032.\0201\032\0020\001*\0020\0022\006\020\003\032\0020\0042\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\0202\032>\0203\032\0020\002*\0020\0022\f\020\006\032\b\022\004\022\0020\b0\0072\b\b\002\020\t\032\0020\n2\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\0204\032>\0203\032\0020\002*\0020\0022\f\020\006\032\b\022\004\022\0020\b0\0132\b\b\002\020\t\032\0020\n2\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\0205\0327\0206\032\0020\001*\0020\0022\006\020\r\032\0020\b2\b\b\002\020\t\032\0020\n2\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\007¢\006\002\0207\0320\0208\032\00209*\0020\0022\b\b\002\020\t\032\0020\n2\022\020\022\032\n\022\006\b\001\022\0020\0240\023\"\0020\024H\b¢\006\002\020:\002\007\n\005\b20\001¨\006;"}, d2={"appendBytes", "", "Ljava/nio/file/Path;", "array", "", "appendLines", "lines", "", "", "charset", "Ljava/nio/charset/Charset;", "Lkotlin/sequences/Sequence;", "appendText", "text", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedReader;", "bufferedWriter", "Ljava/io/BufferedWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;", "forEachLine", "action", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "line", "inputStream", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;", "outputStream", "Ljava/io/OutputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStream;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/InputStreamReader;", "useLines", "T", "block", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "(Ljava/nio/file/Path;[B[Ljava/nio/file/OpenOption;)V", "writeLines", "(Ljava/nio/file/Path;Ljava/lang/Iterable;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "(Ljava/nio/file/Path;Lkotlin/sequences/Sequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "writeText", "(Ljava/nio/file/Path;Ljava/lang/CharSequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)V", "writer", "Ljava/io/OutputStreamWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStreamWriter;", "kotlin-stdlib-jdk7"}, k=5, mv={1, 6, 0}, xi=49, xs="kotlin/io/path/PathsKt")
class PathsKt__PathReadWriteKt
{
  private static final void appendBytes(Path paramPath, byte[] paramArrayOfByte)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
    Files.write(paramPath, paramArrayOfByte, new OpenOption[] { (OpenOption)StandardOpenOption.APPEND });
  }
  
  private static final Path appendLines(Path paramPath, Iterable<? extends CharSequence> paramIterable, Charset paramCharset)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "lines");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramPath = Files.write(paramPath, paramIterable, paramCharset, new OpenOption[] { (OpenOption)StandardOpenOption.APPEND });
    Intrinsics.checkNotNullExpressionValue(paramPath, "write(this, lines, chars…tandardOpenOption.APPEND)");
    return paramPath;
  }
  
  private static final Path appendLines(Path paramPath, Sequence<? extends CharSequence> paramSequence, Charset paramCharset)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "lines");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramPath = Files.write(paramPath, SequencesKt.asIterable(paramSequence), paramCharset, new OpenOption[] { (OpenOption)StandardOpenOption.APPEND });
    Intrinsics.checkNotNullExpressionValue(paramPath, "write(this, lines.asIter…tandardOpenOption.APPEND)");
    return paramPath;
  }
  
  public static final void appendText(Path paramPath, CharSequence paramCharSequence, Charset paramCharset)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "text");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramPath = Files.newOutputStream(paramPath, new OpenOption[] { (OpenOption)StandardOpenOption.APPEND });
    Intrinsics.checkNotNullExpressionValue(paramPath, "newOutputStream(this, StandardOpenOption.APPEND)");
    paramPath = (Closeable)new OutputStreamWriter(paramPath, paramCharset);
    try
    {
      ((OutputStreamWriter)paramPath).append(paramCharSequence);
      CloseableKt.closeFinally(paramPath, null);
      return;
    }
    finally
    {
      try
      {
        throw paramCharset;
      }
      finally
      {
        CloseableKt.closeFinally(paramPath, paramCharset);
      }
    }
  }
  
  private static final BufferedReader bufferedReader(Path paramPath, Charset paramCharset, int paramInt, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.newInputStream(paramPath, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    paramPath = (Reader)new InputStreamReader(paramPath, paramCharset);
    return new BufferedReader(paramPath, paramInt);
  }
  
  private static final BufferedWriter bufferedWriter(Path paramPath, Charset paramCharset, int paramInt, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.newOutputStream(paramPath, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    paramPath = (Writer)new OutputStreamWriter(paramPath, paramCharset);
    return new BufferedWriter(paramPath, paramInt);
  }
  
  private static final void forEachLine(Path paramPath, Charset paramCharset, Function1<? super String, Unit> paramFunction1)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramPath = Files.newBufferedReader(paramPath, paramCharset);
    Intrinsics.checkNotNullExpressionValue(paramPath, "newBufferedReader(this, charset)");
    paramPath = (Closeable)(Reader)paramPath;
    try
    {
      paramCharset = TextStreamsKt.lineSequence((BufferedReader)paramPath).iterator();
      while (paramCharset.hasNext()) {
        paramFunction1.invoke(paramCharset.next());
      }
      paramCharset = Unit.INSTANCE;
      InlineMarker.finallyStart(1);
      CloseableKt.closeFinally(paramPath, null);
      InlineMarker.finallyEnd(1);
      return;
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
        CloseableKt.closeFinally(paramPath, paramCharset);
        InlineMarker.finallyEnd(1);
      }
    }
  }
  
  private static final InputStream inputStream(Path paramPath, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.newInputStream(paramPath, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "newInputStream(this, *options)");
    return paramPath;
  }
  
  private static final OutputStream outputStream(Path paramPath, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.newOutputStream(paramPath, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "newOutputStream(this, *options)");
    return paramPath;
  }
  
  private static final byte[] readBytes(Path paramPath)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = Files.readAllBytes(paramPath);
    Intrinsics.checkNotNullExpressionValue(paramPath, "readAllBytes(this)");
    return paramPath;
  }
  
  private static final List<String> readLines(Path paramPath, Charset paramCharset)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramPath = Files.readAllLines(paramPath, paramCharset);
    Intrinsics.checkNotNullExpressionValue(paramPath, "readAllLines(this, charset)");
    return paramPath;
  }
  
  public static final String readText(Path paramPath, Charset paramCharset)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramPath = (Closeable)new InputStreamReader(Files.newInputStream(paramPath, (OpenOption[])Arrays.copyOf(new OpenOption[0], 0)), paramCharset);
    try
    {
      paramCharset = TextStreamsKt.readText((Reader)paramPath);
      Log5ECF72.a(paramCharset);
      LogE84000.a(paramCharset);
      Log229316.a(paramCharset);
      CloseableKt.closeFinally(paramPath, null);
      return paramCharset;
    }
    finally
    {
      try
      {
        throw localThrowable;
      }
      finally
      {
        CloseableKt.closeFinally(paramPath, localThrowable);
      }
    }
  }
  
  private static final InputStreamReader reader(Path paramPath, Charset paramCharset, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return new InputStreamReader(Files.newInputStream(paramPath, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length)), paramCharset);
  }
  
  private static final <T> T useLines(Path paramPath, Charset paramCharset, Function1<? super Sequence<String>, ? extends T> paramFunction1)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramPath = (Closeable)Files.newBufferedReader(paramPath, paramCharset);
    try
    {
      paramCharset = (BufferedReader)paramPath;
      Intrinsics.checkNotNullExpressionValue(paramCharset, "it");
      paramCharset = paramFunction1.invoke(TextStreamsKt.lineSequence(paramCharset));
      InlineMarker.finallyStart(1);
      CloseableKt.closeFinally(paramPath, null);
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
        CloseableKt.closeFinally(paramPath, paramCharset);
        InlineMarker.finallyEnd(1);
      }
    }
  }
  
  private static final void writeBytes(Path paramPath, byte[] paramArrayOfByte, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    Files.write(paramPath, paramArrayOfByte, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  private static final Path writeLines(Path paramPath, Iterable<? extends CharSequence> paramIterable, Charset paramCharset, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "lines");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.write(paramPath, paramIterable, paramCharset, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "write(this, lines, charset, *options)");
    return paramPath;
  }
  
  private static final Path writeLines(Path paramPath, Sequence<? extends CharSequence> paramSequence, Charset paramCharset, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "lines");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.write(paramPath, SequencesKt.asIterable(paramSequence), paramCharset, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "write(this, lines.asIterable(), charset, *options)");
    return paramPath;
  }
  
  public static final void writeText(Path paramPath, CharSequence paramCharSequence, Charset paramCharset, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "text");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.newOutputStream(paramPath, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "newOutputStream(this, *options)");
    paramPath = (Closeable)new OutputStreamWriter(paramPath, paramCharset);
    try
    {
      ((OutputStreamWriter)paramPath).append(paramCharSequence);
      CloseableKt.closeFinally(paramPath, null);
      return;
    }
    finally
    {
      try
      {
        throw paramCharSequence;
      }
      finally
      {
        CloseableKt.closeFinally(paramPath, paramCharSequence);
      }
    }
  }
  
  private static final OutputStreamWriter writer(Path paramPath, Charset paramCharset, OpenOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return new OutputStreamWriter(Files.newOutputStream(paramPath, (OpenOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length)), paramCharset);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/path/PathsKt__PathReadWriteKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */