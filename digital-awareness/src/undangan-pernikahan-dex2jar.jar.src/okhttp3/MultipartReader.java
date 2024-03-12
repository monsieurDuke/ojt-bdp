package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.http1.HeadersReader;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.ByteString.Companion;
import okio.Okio;
import okio.Options;
import okio.Options.Companion;
import okio.Source;
import okio.Timeout;
import okio.Timeout.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000P\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\b\n\000\n\002\020\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\004\030\000 \0342\0020\001:\003\034\035\036B\017\b\026\022\006\020\002\032\0020\003¢\006\002\020\004B\025\022\006\020\005\032\0020\006\022\006\020\007\032\0020\b¢\006\002\020\tJ\b\020\025\032\0020\026H\026J\020\020\027\032\0020\0302\006\020\031\032\0020\030H\002J\b\020\032\032\004\030\0010\033R\023\020\007\032\0020\b8\007¢\006\b\n\000\032\004\b\007\020\nR\016\020\013\032\0020\fX\016¢\006\002\n\000R\016\020\r\032\0020\016X\004¢\006\002\n\000R\024\020\017\032\b\030\0010\020R\0020\000X\016¢\006\002\n\000R\016\020\021\032\0020\016X\004¢\006\002\n\000R\016\020\022\032\0020\fX\016¢\006\002\n\000R\016\020\023\032\0020\024X\016¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000¨\006\037"}, d2={"Lokhttp3/MultipartReader;", "Ljava/io/Closeable;", "response", "Lokhttp3/ResponseBody;", "(Lokhttp3/ResponseBody;)V", "source", "Lokio/BufferedSource;", "boundary", "", "(Lokio/BufferedSource;Ljava/lang/String;)V", "()Ljava/lang/String;", "closed", "", "crlfDashDashBoundary", "Lokio/ByteString;", "currentPart", "Lokhttp3/MultipartReader$PartSource;", "dashDashBoundary", "noMoreParts", "partCount", "", "close", "", "currentPartBytesRemaining", "", "maxResult", "nextPart", "Lokhttp3/MultipartReader$Part;", "Companion", "Part", "PartSource", "okhttp"}, k=1, mv={1, 4, 0})
public final class MultipartReader
  implements Closeable
{
  public static final Companion Companion = new Companion(null);
  private static final Options afterBoundaryOptions = Options.Companion.of(new ByteString[] { ByteString.Companion.encodeUtf8("\r\n"), ByteString.Companion.encodeUtf8("--"), ByteString.Companion.encodeUtf8(" "), ByteString.Companion.encodeUtf8("\t") });
  private final String boundary;
  private boolean closed;
  private final ByteString crlfDashDashBoundary;
  private PartSource currentPart;
  private final ByteString dashDashBoundary;
  private boolean noMoreParts;
  private int partCount;
  private final BufferedSource source;
  
  public MultipartReader(ResponseBody paramResponseBody)
    throws IOException
  {}
  
  public MultipartReader(BufferedSource paramBufferedSource, String paramString)
    throws IOException
  {
    this.source = paramBufferedSource;
    this.boundary = paramString;
    this.dashDashBoundary = new Buffer().writeUtf8("--").writeUtf8(paramString).readByteString();
    this.crlfDashDashBoundary = new Buffer().writeUtf8("\r\n--").writeUtf8(paramString).readByteString();
  }
  
  private final long currentPartBytesRemaining(long paramLong)
  {
    this.source.require(this.crlfDashDashBoundary.size());
    long l = this.source.getBuffer().indexOf(this.crlfDashDashBoundary);
    if (l == -1L) {
      paramLong = Math.min(paramLong, this.source.getBuffer().size() - this.crlfDashDashBoundary.size() + 1L);
    } else {
      paramLong = Math.min(paramLong, l);
    }
    return paramLong;
  }
  
  public final String boundary()
  {
    return this.boundary;
  }
  
  public void close()
    throws IOException
  {
    if (this.closed) {
      return;
    }
    this.closed = true;
    PartSource localPartSource = (PartSource)null;
    this.currentPart = null;
    this.source.close();
  }
  
  public final Part nextPart()
    throws IOException
  {
    if ((this.closed ^ true))
    {
      if (this.noMoreParts) {
        return null;
      }
      if ((this.partCount == 0) && (this.source.rangeEquals(0L, this.dashDashBoundary))) {
        this.source.skip(this.dashDashBoundary.size());
      }
      for (;;)
      {
        long l = currentPartBytesRemaining(8192L);
        if (l == 0L)
        {
          this.source.skip(this.crlfDashDashBoundary.size());
          int i = 0;
          for (;;)
          {
            switch (this.source.select(afterBoundaryOptions))
            {
            default: 
              break;
            case 2: 
            case 3: 
              i = 1;
            }
          }
          if (i == 0)
          {
            if (this.partCount != 0)
            {
              this.noMoreParts = true;
              return null;
            }
            throw ((Throwable)new ProtocolException("expected at least 1 part"));
          }
          throw ((Throwable)new ProtocolException("unexpected characters after boundary"));
          this.partCount += 1;
          Headers localHeaders = new HeadersReader(this.source).readHeaders();
          PartSource localPartSource = new PartSource();
          this.currentPart = localPartSource;
          return new Part(localHeaders, Okio.buffer((Source)localPartSource));
          throw ((Throwable)new ProtocolException("unexpected characters after boundary"));
        }
        this.source.skip(l);
      }
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006¨\006\007"}, d2={"Lokhttp3/MultipartReader$Companion;", "", "()V", "afterBoundaryOptions", "Lokio/Options;", "getAfterBoundaryOptions", "()Lokio/Options;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final Options getAfterBoundaryOptions()
    {
      return MultipartReader.access$getAfterBoundaryOptions$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\002\n\000\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\t\020\t\032\0020\nH\001R\023\020\004\032\0020\0058\007¢\006\b\n\000\032\004\b\004\020\007R\023\020\002\032\0020\0038\007¢\006\b\n\000\032\004\b\002\020\b¨\006\013"}, d2={"Lokhttp3/MultipartReader$Part;", "Ljava/io/Closeable;", "headers", "Lokhttp3/Headers;", "body", "Lokio/BufferedSource;", "(Lokhttp3/Headers;Lokio/BufferedSource;)V", "()Lokio/BufferedSource;", "()Lokhttp3/Headers;", "close", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Part
    implements Closeable
  {
    private final BufferedSource body;
    private final Headers headers;
    
    public Part(Headers paramHeaders, BufferedSource paramBufferedSource)
    {
      this.headers = paramHeaders;
      this.body = paramBufferedSource;
    }
    
    public final BufferedSource body()
    {
      return this.body;
    }
    
    public void close()
    {
      this.body.close();
    }
    
    public final Headers headers()
    {
      return this.headers;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\002\b\004\030\0002\0020\001B\005¢\006\002\020\002J\b\020\005\032\0020\006H\026J\030\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\013\032\0020\bH\026J\b\020\003\032\0020\004H\026R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\f"}, d2={"Lokhttp3/MultipartReader$PartSource;", "Lokio/Source;", "(Lokhttp3/MultipartReader;)V", "timeout", "Lokio/Timeout;", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 4, 0})
  private final class PartSource
    implements Source
  {
    final MultipartReader this$0;
    private final Timeout timeout = new Timeout();
    
    public void close()
    {
      if (Intrinsics.areEqual(MultipartReader.access$getCurrentPart$p(this.this$0), (PartSource)this))
      {
        MultipartReader localMultipartReader = this.this$0;
        PartSource localPartSource = (PartSource)null;
        MultipartReader.access$setCurrentPart$p(localMultipartReader, null);
      }
    }
    
    public long read(Buffer paramBuffer, long paramLong)
    {
      Intrinsics.checkNotNullParameter(paramBuffer, "sink");
      int i;
      if (paramLong >= 0L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (Intrinsics.areEqual(MultipartReader.access$getCurrentPart$p(this.this$0), (PartSource)this))
        {
          Timeout localTimeout1 = MultipartReader.access$getSource$p(this.this$0).timeout();
          Timeout localTimeout2 = this.timeout;
          long l1 = localTimeout1.timeoutNanos();
          localTimeout1.timeout(Timeout.Companion.minTimeout(localTimeout2.timeoutNanos(), localTimeout1.timeoutNanos()), TimeUnit.NANOSECONDS);
          if (localTimeout1.hasDeadline())
          {
            long l2 = localTimeout1.deadlineNanoTime();
            if (localTimeout2.hasDeadline()) {
              localTimeout1.deadlineNanoTime(Math.min(localTimeout1.deadlineNanoTime(), localTimeout2.deadlineNanoTime()));
            }
            try
            {
              paramLong = MultipartReader.access$currentPartBytesRemaining(this.this$0, paramLong);
              if (paramLong == 0L) {
                paramLong = -1L;
              } else {
                paramLong = MultipartReader.access$getSource$p(this.this$0).read(paramBuffer, paramLong);
              }
              return paramLong;
            }
            finally
            {
              localTimeout1.timeout(l1, TimeUnit.NANOSECONDS);
              if (localTimeout2.hasDeadline()) {
                localTimeout1.deadlineNanoTime(l2);
              }
            }
          }
          if (localTimeout2.hasDeadline()) {
            localTimeout1.deadlineNanoTime(localTimeout2.deadlineNanoTime());
          }
          try
          {
            paramLong = MultipartReader.access$currentPartBytesRemaining(this.this$0, paramLong);
            if (paramLong == 0L) {
              paramLong = -1L;
            } else {
              paramLong = MultipartReader.access$getSource$p(this.this$0).read(paramBuffer, paramLong);
            }
            return paramLong;
          }
          finally
          {
            localTimeout1.timeout(l1, TimeUnit.NANOSECONDS);
            if (localTimeout2.hasDeadline()) {
              localTimeout1.clearDeadline();
            }
          }
        }
        throw ((Throwable)new IllegalStateException("closed".toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
    }
    
    public Timeout timeout()
    {
      return this.timeout;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/MultipartReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */