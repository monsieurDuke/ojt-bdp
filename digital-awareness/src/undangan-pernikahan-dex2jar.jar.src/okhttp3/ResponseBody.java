package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\000b\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\003\b&\030\000 !2\0020\001:\002 !B\005¢\006\002\020\002J\006\020\005\032\0020\006J\006\020\007\032\0020\bJ\006\020\t\032\0020\nJ\006\020\013\032\0020\004J\b\020\f\032\0020\rH\002J\b\020\016\032\0020\017H\026J@\020\020\032\002H\021\"\b\b\000\020\021*\0020\0222\022\020\023\032\016\022\004\022\0020\025\022\004\022\002H\0210\0242\022\020\026\032\016\022\004\022\002H\021\022\004\022\0020\0270\024H\b¢\006\002\020\030J\b\020\031\032\0020\032H&J\n\020\033\032\004\030\0010\034H&J\b\020\035\032\0020\025H&J\006\020\036\032\0020\037R\020\020\003\032\004\030\0010\004X\016¢\006\002\n\000¨\006\""}, d2={"Lokhttp3/ResponseBody;", "Ljava/io/Closeable;", "()V", "reader", "Ljava/io/Reader;", "byteStream", "Ljava/io/InputStream;", "byteString", "Lokio/ByteString;", "bytes", "", "charStream", "charset", "Ljava/nio/charset/Charset;", "close", "", "consumeSource", "T", "", "consumer", "Lkotlin/Function1;", "Lokio/BufferedSource;", "sizeMapper", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "source", "string", "", "BomAwareReader", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public abstract class ResponseBody
  implements Closeable
{
  public static final Companion Companion = new Companion(null);
  private Reader reader;
  
  private final Charset charset()
  {
    Object localObject = contentType();
    if (localObject != null)
    {
      localObject = ((MediaType)localObject).charset(Charsets.UTF_8);
      if (localObject != null) {}
    }
    else
    {
      localObject = Charsets.UTF_8;
    }
    return (Charset)localObject;
  }
  
  private final <T> T consumeSource(Function1<? super BufferedSource, ? extends T> paramFunction1, Function1<? super T, Integer> paramFunction11)
  {
    long l = contentLength();
    if (l <= Integer.MAX_VALUE)
    {
      Closeable localCloseable = (Closeable)source();
      Throwable localThrowable = (Throwable)null;
      try
      {
        paramFunction1 = paramFunction1.invoke(localCloseable);
        InlineMarker.finallyStart(1);
        CloseableKt.closeFinally(localCloseable, null);
        InlineMarker.finallyEnd(1);
        int i = ((Number)paramFunction11.invoke(paramFunction1)).intValue();
        if ((l != -1L) && (l != i)) {
          throw ((Throwable)new IOException("Content-Length (" + l + ") and stream length (" + i + ") disagree"));
        }
        return paramFunction1;
      }
      finally
      {
        try
        {
          throw paramFunction11;
        }
        finally
        {
          InlineMarker.finallyStart(1);
          CloseableKt.closeFinally(localCloseable, paramFunction11);
          InlineMarker.finallyEnd(1);
        }
      }
    }
    throw ((Throwable)new IOException("Cannot buffer entire body for content length: " + l));
  }
  
  @JvmStatic
  public static final ResponseBody create(String paramString, MediaType paramMediaType)
  {
    return Companion.create(paramString, paramMediaType);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.asResponseBody(contentType, contentLength)", imports={"okhttp3.ResponseBody.Companion.asResponseBody"}))
  @JvmStatic
  public static final ResponseBody create(MediaType paramMediaType, long paramLong, BufferedSource paramBufferedSource)
  {
    return Companion.create(paramMediaType, paramLong, paramBufferedSource);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
  @JvmStatic
  public static final ResponseBody create(MediaType paramMediaType, String paramString)
  {
    return Companion.create(paramMediaType, paramString);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
  @JvmStatic
  public static final ResponseBody create(MediaType paramMediaType, ByteString paramByteString)
  {
    return Companion.create(paramMediaType, paramByteString);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
  @JvmStatic
  public static final ResponseBody create(MediaType paramMediaType, byte[] paramArrayOfByte)
  {
    return Companion.create(paramMediaType, paramArrayOfByte);
  }
  
  @JvmStatic
  public static final ResponseBody create(BufferedSource paramBufferedSource, MediaType paramMediaType, long paramLong)
  {
    return Companion.create(paramBufferedSource, paramMediaType, paramLong);
  }
  
  @JvmStatic
  public static final ResponseBody create(ByteString paramByteString, MediaType paramMediaType)
  {
    return Companion.create(paramByteString, paramMediaType);
  }
  
  @JvmStatic
  public static final ResponseBody create(byte[] paramArrayOfByte, MediaType paramMediaType)
  {
    return Companion.create(paramArrayOfByte, paramMediaType);
  }
  
  public final InputStream byteStream()
  {
    return source().inputStream();
  }
  
  public final ByteString byteString()
    throws IOException
  {
    long l = contentLength();
    if (l <= Integer.MAX_VALUE)
    {
      Closeable localCloseable = (Closeable)source();
      Object localObject1 = (Throwable)null;
      try
      {
        localObject1 = ((BufferedSource)localCloseable).readByteString();
        CloseableKt.closeFinally(localCloseable, null);
        int i = ((ByteString)localObject1).size();
        if ((l != -1L) && (l != i)) {
          throw ((Throwable)new IOException("Content-Length (" + l + ") and stream length (" + i + ") disagree"));
        }
        return (ByteString)localObject1;
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
    throw ((Throwable)new IOException("Cannot buffer entire body for content length: " + l));
  }
  
  public final byte[] bytes()
    throws IOException
  {
    long l = contentLength();
    if (l <= Integer.MAX_VALUE)
    {
      Closeable localCloseable = (Closeable)source();
      Object localObject1 = (Throwable)null;
      try
      {
        localObject1 = ((BufferedSource)localCloseable).readByteArray();
        CloseableKt.closeFinally(localCloseable, null);
        int i = localObject1.length;
        if ((l != -1L) && (l != i)) {
          throw ((Throwable)new IOException("Content-Length (" + l + ") and stream length (" + i + ") disagree"));
        }
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
          CloseableKt.closeFinally(localCloseable, localThrowable);
        }
      }
    }
    throw ((Throwable)new IOException("Cannot buffer entire body for content length: " + l));
  }
  
  public final Reader charStream()
  {
    Object localObject = this.reader;
    if (localObject == null)
    {
      localObject = new BomAwareReader(source(), charset());
      this.reader = ((Reader)localObject);
      localObject = (Reader)localObject;
    }
    return (Reader)localObject;
  }
  
  public void close()
  {
    Util.closeQuietly((Closeable)source());
  }
  
  public abstract long contentLength();
  
  public abstract MediaType contentType();
  
  public abstract BufferedSource source();
  
  public final String string()
    throws IOException
  {
    Closeable localCloseable = (Closeable)source();
    Object localObject1 = (Throwable)null;
    try
    {
      localObject1 = (BufferedSource)localCloseable;
      localObject1 = ((BufferedSource)localObject1).readString(Util.readBomAsCharset((BufferedSource)localObject1, charset()));
      CloseableKt.closeFinally(localCloseable, null);
      return (String)localObject1;
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
  
  @Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\020\b\n\000\n\002\020\031\n\002\b\003\b\000\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\b\020\n\032\0020\013H\026J \020\f\032\0020\r2\006\020\016\032\0020\0172\006\020\020\032\0020\r2\006\020\021\032\0020\rH\026R\016\020\004\032\0020\005X\004¢\006\002\n\000R\016\020\007\032\0020\bX\016¢\006\002\n\000R\020\020\t\032\004\030\0010\001X\016¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\022"}, d2={"Lokhttp3/ResponseBody$BomAwareReader;", "Ljava/io/Reader;", "source", "Lokio/BufferedSource;", "charset", "Ljava/nio/charset/Charset;", "(Lokio/BufferedSource;Ljava/nio/charset/Charset;)V", "closed", "", "delegate", "close", "", "read", "", "cbuf", "", "off", "len", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class BomAwareReader
    extends Reader
  {
    private final Charset charset;
    private boolean closed;
    private Reader delegate;
    private final BufferedSource source;
    
    public BomAwareReader(BufferedSource paramBufferedSource, Charset paramCharset)
    {
      this.source = paramBufferedSource;
      this.charset = paramCharset;
    }
    
    public void close()
      throws IOException
    {
      this.closed = true;
      Reader localReader = this.delegate;
      if (localReader != null) {
        localReader.close();
      } else {
        ((BomAwareReader)this).source.close();
      }
    }
    
    public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramArrayOfChar, "cbuf");
      if (!this.closed)
      {
        Object localObject = this.delegate;
        if (localObject == null)
        {
          localObject = new InputStreamReader(this.source.inputStream(), Util.readBomAsCharset(this.source, this.charset));
          this.delegate = ((Reader)localObject);
          localObject = (Reader)localObject;
        }
        return ((Reader)localObject).read(paramArrayOfChar, paramInt1, paramInt2);
      }
      throw ((Throwable)new IOException("Stream closed"));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\022\n\000\n\002\020\t\n\002\030\002\n\002\020\016\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\032\020\003\032\0020\0042\b\020\005\032\004\030\0010\0062\006\020\007\032\0020\bH\007J\"\020\003\032\0020\0042\b\020\005\032\004\030\0010\0062\006\020\t\032\0020\n2\006\020\007\032\0020\013H\007J\032\020\003\032\0020\0042\b\020\005\032\004\030\0010\0062\006\020\007\032\0020\fH\007J\032\020\003\032\0020\0042\b\020\005\032\004\030\0010\0062\006\020\007\032\0020\rH\007J'\020\016\032\0020\004*\0020\0132\n\b\002\020\005\032\004\030\0010\0062\b\b\002\020\t\032\0020\nH\007¢\006\002\b\003J\035\020\017\032\0020\004*\0020\b2\n\b\002\020\005\032\004\030\0010\006H\007¢\006\002\b\003J\035\020\017\032\0020\004*\0020\f2\n\b\002\020\005\032\004\030\0010\006H\007¢\006\002\b\003J\035\020\017\032\0020\004*\0020\r2\n\b\002\020\005\032\004\030\0010\006H\007¢\006\002\b\003¨\006\020"}, d2={"Lokhttp3/ResponseBody$Companion;", "", "()V", "create", "Lokhttp3/ResponseBody;", "contentType", "Lokhttp3/MediaType;", "content", "", "contentLength", "", "Lokio/BufferedSource;", "", "Lokio/ByteString;", "asResponseBody", "toResponseBody", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    @JvmStatic
    public final ResponseBody create(String paramString, MediaType paramMediaType)
    {
      Intrinsics.checkNotNullParameter(paramString, "$this$toResponseBody");
      Charset localCharset = Charsets.UTF_8;
      MediaType localMediaType1 = paramMediaType;
      MediaType localMediaType2 = localMediaType1;
      if (paramMediaType != null)
      {
        localCharset = MediaType.charset$default(paramMediaType, null, 1, null);
        if (localCharset == null)
        {
          localCharset = Charsets.UTF_8;
          localMediaType2 = MediaType.Companion.parse(paramMediaType + "; charset=utf-8");
        }
        else
        {
          localMediaType2 = localMediaType1;
        }
      }
      paramString = new Buffer().writeString(paramString, localCharset);
      return ((Companion)this).create((BufferedSource)paramString, localMediaType2, paramString.size());
    }
    
    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.asResponseBody(contentType, contentLength)", imports={"okhttp3.ResponseBody.Companion.asResponseBody"}))
    @JvmStatic
    public final ResponseBody create(MediaType paramMediaType, long paramLong, BufferedSource paramBufferedSource)
    {
      Intrinsics.checkNotNullParameter(paramBufferedSource, "content");
      return ((Companion)this).create(paramBufferedSource, paramMediaType, paramLong);
    }
    
    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public final ResponseBody create(MediaType paramMediaType, String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "content");
      return ((Companion)this).create(paramString, paramMediaType);
    }
    
    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public final ResponseBody create(MediaType paramMediaType, ByteString paramByteString)
    {
      Intrinsics.checkNotNullParameter(paramByteString, "content");
      return ((Companion)this).create(paramByteString, paramMediaType);
    }
    
    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public final ResponseBody create(MediaType paramMediaType, byte[] paramArrayOfByte)
    {
      Intrinsics.checkNotNullParameter(paramArrayOfByte, "content");
      return ((Companion)this).create(paramArrayOfByte, paramMediaType);
    }
    
    @JvmStatic
    public final ResponseBody create(BufferedSource paramBufferedSource, final MediaType paramMediaType, final long paramLong)
    {
      Intrinsics.checkNotNullParameter(paramBufferedSource, "$this$asResponseBody");
      (ResponseBody)new ResponseBody()
      {
        final BufferedSource $this_asResponseBody;
        
        public long contentLength()
        {
          return paramLong;
        }
        
        public MediaType contentType()
        {
          return paramMediaType;
        }
        
        public BufferedSource source()
        {
          return this.$this_asResponseBody;
        }
      };
    }
    
    @JvmStatic
    public final ResponseBody create(ByteString paramByteString, MediaType paramMediaType)
    {
      Intrinsics.checkNotNullParameter(paramByteString, "$this$toResponseBody");
      return ((Companion)this).create((BufferedSource)new Buffer().write(paramByteString), paramMediaType, paramByteString.size());
    }
    
    @JvmStatic
    public final ResponseBody create(byte[] paramArrayOfByte, MediaType paramMediaType)
    {
      Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$toResponseBody");
      return ((Companion)this).create((BufferedSource)new Buffer().write(paramArrayOfByte), paramMediaType, paramArrayOfByte.length);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/ResponseBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */