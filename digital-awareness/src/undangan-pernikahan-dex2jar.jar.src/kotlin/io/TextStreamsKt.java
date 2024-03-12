package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000X\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\020\022\n\002\030\002\n\000\n\002\020 \n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\006\032\027\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\004H\b\032\027\020\000\032\0020\005*\0020\0062\b\b\002\020\003\032\0020\004H\b\032\034\020\007\032\0020\b*\0020\0022\006\020\t\032\0020\0062\b\b\002\020\003\032\0020\004\032\036\020\n\032\0020\013*\0020\0022\022\020\f\032\016\022\004\022\0020\016\022\004\022\0020\0130\r\032\020\020\017\032\b\022\004\022\0020\0160\020*\0020\001\032\n\020\021\032\0020\022*\0020\023\032\020\020\024\032\b\022\004\022\0020\0160\025*\0020\002\032\n\020\026\032\0020\016*\0020\002\032\027\020\026\032\0020\016*\0020\0232\b\b\002\020\027\032\0020\030H\b\032\r\020\031\032\0020\032*\0020\016H\b\0328\020\033\032\002H\034\"\004\b\000\020\034*\0020\0022\030\020\035\032\024\022\n\022\b\022\004\022\0020\0160\020\022\004\022\002H\0340\rH\bø\001\000ø\001\001¢\006\002\020\036\002\017\n\005\b20\001\n\006\b\021(\0370\001¨\006 "}, d2={"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Requires newer compiler version to be inlined correctly.", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class TextStreamsKt
{
  private static final BufferedReader buffered(Reader paramReader, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramReader, "<this>");
    if ((paramReader instanceof BufferedReader)) {
      paramReader = (BufferedReader)paramReader;
    } else {
      paramReader = new BufferedReader(paramReader, paramInt);
    }
    return paramReader;
  }
  
  private static final BufferedWriter buffered(Writer paramWriter, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramWriter, "<this>");
    if ((paramWriter instanceof BufferedWriter)) {
      paramWriter = (BufferedWriter)paramWriter;
    } else {
      paramWriter = new BufferedWriter(paramWriter, paramInt);
    }
    return paramWriter;
  }
  
  public static final long copyTo(Reader paramReader, Writer paramWriter, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramReader, "<this>");
    Intrinsics.checkNotNullParameter(paramWriter, "out");
    long l = 0L;
    char[] arrayOfChar = new char[paramInt];
    for (paramInt = paramReader.read(arrayOfChar); paramInt >= 0; paramInt = paramReader.read(arrayOfChar))
    {
      paramWriter.write(arrayOfChar, 0, paramInt);
      l += paramInt;
    }
    return l;
  }
  
  public static final void forEachLine(Reader paramReader, Function1<? super String, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramReader, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    if ((paramReader instanceof BufferedReader)) {
      paramReader = (BufferedReader)paramReader;
    } else {
      paramReader = new BufferedReader(paramReader, 8192);
    }
    paramReader = (Closeable)paramReader;
    try
    {
      Iterator localIterator = lineSequence((BufferedReader)paramReader).iterator();
      while (localIterator.hasNext()) {
        paramFunction1.invoke(localIterator.next());
      }
      paramFunction1 = Unit.INSTANCE;
      CloseableKt.closeFinally(paramReader, null);
      return;
    }
    finally
    {
      try
      {
        throw paramFunction1;
      }
      finally
      {
        CloseableKt.closeFinally(paramReader, paramFunction1);
      }
    }
  }
  
  public static final Sequence<String> lineSequence(BufferedReader paramBufferedReader)
  {
    Intrinsics.checkNotNullParameter(paramBufferedReader, "<this>");
    return SequencesKt.constrainOnce((Sequence)new LinesSequence(paramBufferedReader));
  }
  
  public static final byte[] readBytes(URL paramURL)
  {
    Intrinsics.checkNotNullParameter(paramURL, "<this>");
    paramURL = (Closeable)paramURL.openStream();
    try
    {
      Object localObject1 = (InputStream)paramURL;
      Intrinsics.checkNotNullExpressionValue(localObject1, "it");
      localObject1 = ByteStreamsKt.readBytes((InputStream)localObject1);
      CloseableKt.closeFinally(paramURL, null);
      return (byte[])localObject1;
    }
    finally
    {
      try
      {
        throw localThrowable;
      }
      finally
      {
        CloseableKt.closeFinally(paramURL, localThrowable);
      }
    }
  }
  
  public static final List<String> readLines(Reader paramReader)
  {
    Intrinsics.checkNotNullParameter(paramReader, "<this>");
    ArrayList localArrayList = new ArrayList();
    forEachLine(paramReader, (Function1)new Lambda(localArrayList)
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
  
  public static final String readText(Reader paramReader)
  {
    Intrinsics.checkNotNullParameter(paramReader, "<this>");
    StringWriter localStringWriter = new StringWriter();
    copyTo$default(paramReader, (Writer)localStringWriter, 0, 2, null);
    paramReader = localStringWriter.toString();
    Intrinsics.checkNotNullExpressionValue(paramReader, "buffer.toString()");
    return paramReader;
  }
  
  private static final String readText(URL paramURL, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramURL, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramURL = new String(readBytes(paramURL), paramCharset);
    Log5ECF72.a(paramURL);
    LogE84000.a(paramURL);
    Log229316.a(paramURL);
    return paramURL;
  }
  
  private static final StringReader reader(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return new StringReader(paramString);
  }
  
  public static final <T> T useLines(Reader paramReader, Function1<? super Sequence<String>, ? extends T> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramReader, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    if ((paramReader instanceof BufferedReader)) {
      paramReader = (BufferedReader)paramReader;
    } else {
      paramReader = new BufferedReader(paramReader, 8192);
    }
    paramReader = (Closeable)paramReader;
    try
    {
      paramFunction1 = paramFunction1.invoke(lineSequence((BufferedReader)paramReader));
      InlineMarker.finallyStart(1);
      CloseableKt.closeFinally(paramReader, null);
      InlineMarker.finallyEnd(1);
      return paramFunction1;
    }
    finally
    {
      try
      {
        throw localThrowable;
      }
      finally
      {
        InlineMarker.finallyStart(1);
        CloseableKt.closeFinally(paramReader, localThrowable);
        InlineMarker.finallyEnd(1);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/TextStreamsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */