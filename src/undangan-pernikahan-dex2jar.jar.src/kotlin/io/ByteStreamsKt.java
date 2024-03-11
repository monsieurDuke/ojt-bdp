package kotlin.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.ByteIterator;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000Z\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\016\n\000\n\002\020\t\n\002\b\002\n\002\020\022\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\032\027\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\004H\b\032\027\020\000\032\0020\005*\0020\0062\b\b\002\020\003\032\0020\004H\b\032\027\020\007\032\0020\b*\0020\0022\b\b\002\020\t\032\0020\nH\b\032\027\020\013\032\0020\f*\0020\0062\b\b\002\020\t\032\0020\nH\b\032\027\020\r\032\0020\016*\0020\0172\b\b\002\020\t\032\0020\nH\b\032\034\020\020\032\0020\021*\0020\0022\006\020\022\032\0020\0062\b\b\002\020\003\032\0020\004\032\r\020\023\032\0020\016*\0020\024H\b\032\035\020\023\032\0020\016*\0020\0242\006\020\025\032\0020\0042\006\020\026\032\0020\004H\b\032\r\020\027\032\0020\030*\0020\001H\002\032\f\020\031\032\0020\024*\0020\002H\007\032\026\020\031\032\0020\024*\0020\0022\b\b\002\020\032\032\0020\004H\007\032\027\020\033\032\0020\034*\0020\0022\b\b\002\020\t\032\0020\nH\b\032\027\020\035\032\0020\036*\0020\0062\b\b\002\020\t\032\0020\nH\b¨\006\037"}, d2={"buffered", "Ljava/io/BufferedInputStream;", "Ljava/io/InputStream;", "bufferSize", "", "Ljava/io/BufferedOutputStream;", "Ljava/io/OutputStream;", "bufferedReader", "Ljava/io/BufferedReader;", "charset", "Ljava/nio/charset/Charset;", "bufferedWriter", "Ljava/io/BufferedWriter;", "byteInputStream", "Ljava/io/ByteArrayInputStream;", "", "copyTo", "", "out", "inputStream", "", "offset", "length", "iterator", "Lkotlin/collections/ByteIterator;", "readBytes", "estimatedSize", "reader", "Ljava/io/InputStreamReader;", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ByteStreamsKt
{
  private static final BufferedInputStream buffered(InputStream paramInputStream, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "<this>");
    if ((paramInputStream instanceof BufferedInputStream)) {
      paramInputStream = (BufferedInputStream)paramInputStream;
    } else {
      paramInputStream = new BufferedInputStream(paramInputStream, paramInt);
    }
    return paramInputStream;
  }
  
  private static final BufferedOutputStream buffered(OutputStream paramOutputStream, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramOutputStream, "<this>");
    if ((paramOutputStream instanceof BufferedOutputStream)) {
      paramOutputStream = (BufferedOutputStream)paramOutputStream;
    } else {
      paramOutputStream = new BufferedOutputStream(paramOutputStream, paramInt);
    }
    return paramOutputStream;
  }
  
  private static final BufferedReader bufferedReader(InputStream paramInputStream, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramInputStream = (Reader)new InputStreamReader(paramInputStream, paramCharset);
    if ((paramInputStream instanceof BufferedReader)) {
      paramInputStream = (BufferedReader)paramInputStream;
    } else {
      paramInputStream = new BufferedReader(paramInputStream, 8192);
    }
    return paramInputStream;
  }
  
  private static final BufferedWriter bufferedWriter(OutputStream paramOutputStream, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramOutputStream, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramOutputStream = (Writer)new OutputStreamWriter(paramOutputStream, paramCharset);
    if ((paramOutputStream instanceof BufferedWriter)) {
      paramOutputStream = (BufferedWriter)paramOutputStream;
    } else {
      paramOutputStream = new BufferedWriter(paramOutputStream, 8192);
    }
    return paramOutputStream;
  }
  
  private static final ByteArrayInputStream byteInputStream(String paramString, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramString = paramString.getBytes(paramCharset);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).getBytes(charset)");
    return new ByteArrayInputStream(paramString);
  }
  
  public static final long copyTo(InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "<this>");
    Intrinsics.checkNotNullParameter(paramOutputStream, "out");
    long l = 0L;
    byte[] arrayOfByte = new byte[paramInt];
    for (paramInt = paramInputStream.read(arrayOfByte); paramInt >= 0; paramInt = paramInputStream.read(arrayOfByte))
    {
      paramOutputStream.write(arrayOfByte, 0, paramInt);
      l += paramInt;
    }
    return l;
  }
  
  private static final ByteArrayInputStream inputStream(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "<this>");
    return new ByteArrayInputStream(paramArrayOfByte);
  }
  
  private static final ByteArrayInputStream inputStream(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "<this>");
    return new ByteArrayInputStream(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public static final ByteIterator iterator(BufferedInputStream paramBufferedInputStream)
  {
    Intrinsics.checkNotNullParameter(paramBufferedInputStream, "<this>");
    (ByteIterator)new ByteIterator()
    {
      final BufferedInputStream $this_iterator;
      private boolean finished;
      private int nextByte = -1;
      private boolean nextPrepared;
      
      private final void prepareNext()
      {
        if ((!this.nextPrepared) && (!this.finished))
        {
          int i = this.$this_iterator.read();
          this.nextByte = i;
          boolean bool = true;
          this.nextPrepared = true;
          if (i != -1) {
            bool = false;
          }
          this.finished = bool;
        }
      }
      
      public final boolean getFinished()
      {
        return this.finished;
      }
      
      public final int getNextByte()
      {
        return this.nextByte;
      }
      
      public final boolean getNextPrepared()
      {
        return this.nextPrepared;
      }
      
      public boolean hasNext()
      {
        prepareNext();
        return this.finished ^ true;
      }
      
      public byte nextByte()
      {
        prepareNext();
        if (!this.finished)
        {
          byte b = (byte)this.nextByte;
          this.nextPrepared = false;
          return b;
        }
        throw new NoSuchElementException("Input stream is over.");
      }
      
      public final void setFinished(boolean paramAnonymousBoolean)
      {
        this.finished = paramAnonymousBoolean;
      }
      
      public final void setNextByte(int paramAnonymousInt)
      {
        this.nextByte = paramAnonymousInt;
      }
      
      public final void setNextPrepared(boolean paramAnonymousBoolean)
      {
        this.nextPrepared = paramAnonymousBoolean;
      }
    };
  }
  
  public static final byte[] readBytes(InputStream paramInputStream)
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "<this>");
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(Math.max(8192, paramInputStream.available()));
    copyTo$default(paramInputStream, (OutputStream)localByteArrayOutputStream, 0, 2, null);
    paramInputStream = localByteArrayOutputStream.toByteArray();
    Intrinsics.checkNotNullExpressionValue(paramInputStream, "buffer.toByteArray()");
    return paramInputStream;
  }
  
  @Deprecated(message="Use readBytes() overload without estimatedSize parameter", replaceWith=@ReplaceWith(expression="readBytes()", imports={}))
  @DeprecatedSinceKotlin(errorSince="1.5", warningSince="1.3")
  public static final byte[] readBytes(InputStream paramInputStream, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "<this>");
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(Math.max(paramInt, paramInputStream.available()));
    copyTo$default(paramInputStream, (OutputStream)localByteArrayOutputStream, 0, 2, null);
    paramInputStream = localByteArrayOutputStream.toByteArray();
    Intrinsics.checkNotNullExpressionValue(paramInputStream, "buffer.toByteArray()");
    return paramInputStream;
  }
  
  private static final InputStreamReader reader(InputStream paramInputStream, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    return new InputStreamReader(paramInputStream, paramCharset);
  }
  
  private static final OutputStreamWriter writer(OutputStream paramOutputStream, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramOutputStream, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    return new OutputStreamWriter(paramOutputStream, paramCharset);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/ByteStreamsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */