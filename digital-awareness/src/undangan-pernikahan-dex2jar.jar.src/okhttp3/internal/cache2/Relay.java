package okhttp3.internal.cache2;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;
import okio.ByteString.Companion;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\n\n\002\020\b\n\002\b\016\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\t\030\000 :2\0020\001:\002:;B3\b\002\022\b\020\002\032\004\030\0010\003\022\b\020\004\032\004\030\0010\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t\022\006\020\n\032\0020\007¢\006\002\020\013J\016\0202\032\002032\006\0204\032\0020\007J\006\020\b\032\0020\tJ\b\0205\032\004\030\0010\005J \0206\032\002032\006\0207\032\0020\t2\006\0204\032\0020\0072\006\0208\032\0020\007H\002J\020\0209\032\002032\006\0204\032\0020\007H\002R\021\020\f\032\0020\r¢\006\b\n\000\032\004\b\016\020\017R\021\020\n\032\0020\007¢\006\b\n\000\032\004\b\020\020\021R\032\020\022\032\0020\023X\016¢\006\016\n\000\032\004\b\024\020\025\"\004\b\026\020\027R\034\020\002\032\004\030\0010\003X\016¢\006\016\n\000\032\004\b\030\020\031\"\004\b\032\020\033R\021\020\034\032\0020\0238F¢\006\006\032\004\b\034\020\025R\016\020\b\032\0020\tX\004¢\006\002\n\000R\032\020\035\032\0020\036X\016¢\006\016\n\000\032\004\b\037\020 \"\004\b!\020\"R\034\020\004\032\004\030\0010\005X\016¢\006\016\n\000\032\004\b#\020$\"\004\b%\020&R\021\020'\032\0020\r¢\006\b\n\000\032\004\b(\020\017R\032\020\006\032\0020\007X\016¢\006\016\n\000\032\004\b)\020\021\"\004\b*\020+R\034\020,\032\004\030\0010-X\016¢\006\016\n\000\032\004\b.\020/\"\004\b0\0201¨\006<"}, d2={"Lokhttp3/internal/cache2/Relay;", "", "file", "Ljava/io/RandomAccessFile;", "upstream", "Lokio/Source;", "upstreamPos", "", "metadata", "Lokio/ByteString;", "bufferMaxSize", "(Ljava/io/RandomAccessFile;Lokio/Source;JLokio/ByteString;J)V", "buffer", "Lokio/Buffer;", "getBuffer", "()Lokio/Buffer;", "getBufferMaxSize", "()J", "complete", "", "getComplete", "()Z", "setComplete", "(Z)V", "getFile", "()Ljava/io/RandomAccessFile;", "setFile", "(Ljava/io/RandomAccessFile;)V", "isClosed", "sourceCount", "", "getSourceCount", "()I", "setSourceCount", "(I)V", "getUpstream", "()Lokio/Source;", "setUpstream", "(Lokio/Source;)V", "upstreamBuffer", "getUpstreamBuffer", "getUpstreamPos", "setUpstreamPos", "(J)V", "upstreamReader", "Ljava/lang/Thread;", "getUpstreamReader", "()Ljava/lang/Thread;", "setUpstreamReader", "(Ljava/lang/Thread;)V", "commit", "", "upstreamSize", "newSource", "writeHeader", "prefix", "metadataSize", "writeMetadata", "Companion", "RelaySource", "okhttp"}, k=1, mv={1, 4, 0})
public final class Relay
{
  public static final Companion Companion = new Companion(null);
  private static final long FILE_HEADER_SIZE = 32L;
  public static final ByteString PREFIX_CLEAN = ByteString.Companion.encodeUtf8("OkHttp cache v1\n");
  public static final ByteString PREFIX_DIRTY = ByteString.Companion.encodeUtf8("OkHttp DIRTY :(\n");
  private static final int SOURCE_FILE = 2;
  private static final int SOURCE_UPSTREAM = 1;
  private final Buffer buffer;
  private final long bufferMaxSize;
  private boolean complete;
  private RandomAccessFile file;
  private final ByteString metadata;
  private int sourceCount;
  private Source upstream;
  private final Buffer upstreamBuffer;
  private long upstreamPos;
  private Thread upstreamReader;
  
  private Relay(RandomAccessFile paramRandomAccessFile, Source paramSource, long paramLong1, ByteString paramByteString, long paramLong2)
  {
    this.file = paramRandomAccessFile;
    this.upstream = paramSource;
    this.upstreamPos = paramLong1;
    this.metadata = paramByteString;
    this.bufferMaxSize = paramLong2;
    this.upstreamBuffer = new Buffer();
    boolean bool;
    if (this.upstream == null) {
      bool = true;
    } else {
      bool = false;
    }
    this.complete = bool;
    this.buffer = new Buffer();
  }
  
  private final void writeHeader(ByteString paramByteString, long paramLong1, long paramLong2)
    throws IOException
  {
    Buffer localBuffer = new Buffer();
    localBuffer.write(paramByteString);
    localBuffer.writeLong(paramLong1);
    localBuffer.writeLong(paramLong2);
    int i;
    if (localBuffer.size() == 32L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramByteString = this.file;
      Intrinsics.checkNotNull(paramByteString);
      paramByteString = paramByteString.getChannel();
      Intrinsics.checkNotNullExpressionValue(paramByteString, "file!!.channel");
      new FileOperator(paramByteString).write(0L, localBuffer, 32L);
      return;
    }
    throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
  }
  
  private final void writeMetadata(long paramLong)
    throws IOException
  {
    Buffer localBuffer = new Buffer();
    localBuffer.write(this.metadata);
    Object localObject = this.file;
    Intrinsics.checkNotNull(localObject);
    localObject = ((RandomAccessFile)localObject).getChannel();
    Intrinsics.checkNotNullExpressionValue(localObject, "file!!.channel");
    new FileOperator((FileChannel)localObject).write(32L + paramLong, localBuffer, this.metadata.size());
  }
  
  public final void commit(long paramLong)
    throws IOException
  {
    writeMetadata(paramLong);
    Object localObject1 = this.file;
    Intrinsics.checkNotNull(localObject1);
    ((RandomAccessFile)localObject1).getChannel().force(false);
    writeHeader(PREFIX_CLEAN, paramLong, this.metadata.size());
    localObject1 = this.file;
    Intrinsics.checkNotNull(localObject1);
    ((RandomAccessFile)localObject1).getChannel().force(false);
    try
    {
      this.complete = true;
      localObject1 = Unit.INSTANCE;
      localObject1 = this.upstream;
      if (localObject1 != null) {
        Util.closeQuietly((Closeable)localObject1);
      }
      localObject1 = (Source)null;
      this.upstream = null;
      return;
    }
    finally {}
  }
  
  public final Buffer getBuffer()
  {
    return this.buffer;
  }
  
  public final long getBufferMaxSize()
  {
    return this.bufferMaxSize;
  }
  
  public final boolean getComplete()
  {
    return this.complete;
  }
  
  public final RandomAccessFile getFile()
  {
    return this.file;
  }
  
  public final int getSourceCount()
  {
    return this.sourceCount;
  }
  
  public final Source getUpstream()
  {
    return this.upstream;
  }
  
  public final Buffer getUpstreamBuffer()
  {
    return this.upstreamBuffer;
  }
  
  public final long getUpstreamPos()
  {
    return this.upstreamPos;
  }
  
  public final Thread getUpstreamReader()
  {
    return this.upstreamReader;
  }
  
  public final boolean isClosed()
  {
    boolean bool;
    if (this.file == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final ByteString metadata()
  {
    return this.metadata;
  }
  
  public final Source newSource()
  {
    try
    {
      RandomAccessFile localRandomAccessFile = this.file;
      if (localRandomAccessFile == null) {
        return null;
      }
      this.sourceCount += 1;
      return (Source)new RelaySource();
    }
    finally {}
  }
  
  public final void setComplete(boolean paramBoolean)
  {
    this.complete = paramBoolean;
  }
  
  public final void setFile(RandomAccessFile paramRandomAccessFile)
  {
    this.file = paramRandomAccessFile;
  }
  
  public final void setSourceCount(int paramInt)
  {
    this.sourceCount = paramInt;
  }
  
  public final void setUpstream(Source paramSource)
  {
    this.upstream = paramSource;
  }
  
  public final void setUpstreamPos(long paramLong)
  {
    this.upstreamPos = paramLong;
  }
  
  public final void setUpstreamReader(Thread paramThread)
  {
    this.upstreamReader = paramThread;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J&\020\013\032\0020\f2\006\020\r\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\0062\006\020\022\032\0020\004J\016\020\023\032\0020\f2\006\020\r\032\0020\016R\016\020\003\032\0020\004XT¢\006\002\n\000R\020\020\005\032\0020\0068\006X\004¢\006\002\n\000R\020\020\007\032\0020\0068\006X\004¢\006\002\n\000R\016\020\b\032\0020\tXT¢\006\002\n\000R\016\020\n\032\0020\tXT¢\006\002\n\000¨\006\024"}, d2={"Lokhttp3/internal/cache2/Relay$Companion;", "", "()V", "FILE_HEADER_SIZE", "", "PREFIX_CLEAN", "Lokio/ByteString;", "PREFIX_DIRTY", "SOURCE_FILE", "", "SOURCE_UPSTREAM", "edit", "Lokhttp3/internal/cache2/Relay;", "file", "Ljava/io/File;", "upstream", "Lokio/Source;", "metadata", "bufferMaxSize", "read", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final Relay edit(File paramFile, Source paramSource, ByteString paramByteString, long paramLong)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramFile, "file");
      Intrinsics.checkNotNullParameter(paramSource, "upstream");
      Intrinsics.checkNotNullParameter(paramByteString, "metadata");
      paramFile = new RandomAccessFile(paramFile, "rw");
      paramSource = new Relay(paramFile, paramSource, 0L, paramByteString, paramLong, null);
      paramFile.setLength(0L);
      Relay.access$writeHeader(paramSource, Relay.PREFIX_DIRTY, -1L, -1L);
      return paramSource;
    }
    
    public final Relay read(File paramFile)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramFile, "file");
      paramFile = new RandomAccessFile(paramFile, "rw");
      Object localObject = paramFile.getChannel();
      Intrinsics.checkNotNullExpressionValue(localObject, "randomAccessFile.channel");
      localObject = new FileOperator((FileChannel)localObject);
      Buffer localBuffer = new Buffer();
      ((FileOperator)localObject).read(0L, localBuffer, 32L);
      if (!(Intrinsics.areEqual(localBuffer.readByteString(Relay.PREFIX_CLEAN.size()), Relay.PREFIX_CLEAN) ^ true))
      {
        long l1 = localBuffer.readLong();
        long l2 = localBuffer.readLong();
        localBuffer = new Buffer();
        ((FileOperator)localObject).read(l1 + 32L, localBuffer, l2);
        return new Relay(paramFile, null, l1, localBuffer.readByteString(), 0L, null);
      }
      throw ((Throwable)new IOException("unreadable cache file"));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\b\004\030\0002\0020\001B\005¢\006\002\020\002J\b\020\t\032\0020\nH\026J\030\020\013\032\0020\0062\006\020\f\032\0020\r2\006\020\016\032\0020\006H\026J\b\020\007\032\0020\bH\026R\020\020\003\032\004\030\0010\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000¨\006\017"}, d2={"Lokhttp3/internal/cache2/Relay$RelaySource;", "Lokio/Source;", "(Lokhttp3/internal/cache2/Relay;)V", "fileOperator", "Lokhttp3/internal/cache2/FileOperator;", "sourcePos", "", "timeout", "Lokio/Timeout;", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 4, 0})
  public final class RelaySource
    implements Source
  {
    private FileOperator fileOperator;
    private long sourcePos;
    final Relay this$0;
    private final Timeout timeout = new Timeout();
    
    public RelaySource()
    {
      this$1 = this$1.getFile();
      Intrinsics.checkNotNull(this$1);
      this$1 = this$1.getChannel();
      Intrinsics.checkNotNullExpressionValue(this$1, "file!!.channel");
      this.fileOperator = new FileOperator(this$1);
    }
    
    public void close()
      throws IOException
    {
      if (this.fileOperator == null) {
        return;
      }
      Object localObject1 = (FileOperator)null;
      this.fileOperator = null;
      localObject1 = (RandomAccessFile)null;
      synchronized (this.this$0)
      {
        Object localObject3 = this.this$0;
        ((Relay)localObject3).setSourceCount(((Relay)localObject3).getSourceCount() - 1);
        if (this.this$0.getSourceCount() == 0)
        {
          localObject1 = this.this$0.getFile();
          Relay localRelay2 = this.this$0;
          localObject3 = (RandomAccessFile)null;
          localRelay2.setFile(null);
        }
        localObject3 = Unit.INSTANCE;
        if (localObject1 != null) {
          Util.closeQuietly((Closeable)localObject1);
        }
        return;
      }
    }
    
    /* Error */
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      // Byte code:
      //   0: aload_1
      //   1: ldc 111
      //   3: invokestatic 114	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_0
      //   7: getfield 78	okhttp3/internal/cache2/Relay$RelaySource:fileOperator	Lokhttp3/internal/cache2/FileOperator;
      //   10: astore 9
      //   12: iconst_1
      //   13: istore 5
      //   15: aload 9
      //   17: ifnull +9 -> 26
      //   20: iconst_1
      //   21: istore 4
      //   23: goto +6 -> 29
      //   26: iconst_0
      //   27: istore 4
      //   29: iload 4
      //   31: ifeq +729 -> 760
      //   34: aload_0
      //   35: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   38: astore 9
      //   40: aload 9
      //   42: monitorenter
      //   43: aload_0
      //   44: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   47: invokevirtual 118	okhttp3/internal/cache2/Relay:getUpstreamPos	()J
      //   50: lstore 6
      //   52: aload_0
      //   53: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   56: lload 6
      //   58: lcmp
      //   59: ifeq +91 -> 150
      //   62: aload_0
      //   63: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   66: invokevirtual 118	okhttp3/internal/cache2/Relay:getUpstreamPos	()J
      //   69: aload_0
      //   70: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   73: invokevirtual 124	okhttp3/internal/cache2/Relay:getBuffer	()Lokio/Buffer;
      //   76: invokevirtual 129	okio/Buffer:size	()J
      //   79: lsub
      //   80: lstore 6
      //   82: aload_0
      //   83: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   86: lload 6
      //   88: lcmp
      //   89: ifge +9 -> 98
      //   92: iconst_2
      //   93: istore 4
      //   95: goto +114 -> 209
      //   98: lload_2
      //   99: aload_0
      //   100: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   103: invokevirtual 118	okhttp3/internal/cache2/Relay:getUpstreamPos	()J
      //   106: aload_0
      //   107: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   110: lsub
      //   111: invokestatic 135	java/lang/Math:min	(JJ)J
      //   114: lstore_2
      //   115: aload_0
      //   116: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   119: invokevirtual 124	okhttp3/internal/cache2/Relay:getBuffer	()Lokio/Buffer;
      //   122: aload_1
      //   123: aload_0
      //   124: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   127: lload 6
      //   129: lsub
      //   130: lload_2
      //   131: invokevirtual 139	okio/Buffer:copyTo	(Lokio/Buffer;JJ)Lokio/Buffer;
      //   134: pop
      //   135: aload_0
      //   136: aload_0
      //   137: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   140: lload_2
      //   141: ladd
      //   142: putfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   145: aload 9
      //   147: monitorexit
      //   148: lload_2
      //   149: lreturn
      //   150: aload_0
      //   151: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   154: invokevirtual 143	okhttp3/internal/cache2/Relay:getComplete	()Z
      //   157: istore 8
      //   159: iload 8
      //   161: ifeq +10 -> 171
      //   164: aload 9
      //   166: monitorexit
      //   167: ldc2_w 144
      //   170: lreturn
      //   171: aload_0
      //   172: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   175: invokevirtual 149	okhttp3/internal/cache2/Relay:getUpstreamReader	()Ljava/lang/Thread;
      //   178: ifnull +17 -> 195
      //   181: aload_0
      //   182: getfield 49	okhttp3/internal/cache2/Relay$RelaySource:timeout	Lokio/Timeout;
      //   185: aload_0
      //   186: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   189: invokevirtual 152	okio/Timeout:waitUntilNotified	(Ljava/lang/Object;)V
      //   192: goto -149 -> 43
      //   195: aload_0
      //   196: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   199: invokestatic 157	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   202: invokevirtual 161	okhttp3/internal/cache2/Relay:setUpstreamReader	(Ljava/lang/Thread;)V
      //   205: iload 5
      //   207: istore 4
      //   209: aload 9
      //   211: monitorexit
      //   212: iload 4
      //   214: iconst_2
      //   215: if_icmpne +58 -> 273
      //   218: lload_2
      //   219: aload_0
      //   220: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   223: invokevirtual 118	okhttp3/internal/cache2/Relay:getUpstreamPos	()J
      //   226: aload_0
      //   227: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   230: lsub
      //   231: invokestatic 135	java/lang/Math:min	(JJ)J
      //   234: lstore_2
      //   235: aload_0
      //   236: getfield 78	okhttp3/internal/cache2/Relay$RelaySource:fileOperator	Lokhttp3/internal/cache2/FileOperator;
      //   239: astore 9
      //   241: aload 9
      //   243: invokestatic 59	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
      //   246: aload 9
      //   248: aload_0
      //   249: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   252: ldc2_w 162
      //   255: ladd
      //   256: aload_1
      //   257: lload_2
      //   258: invokevirtual 166	okhttp3/internal/cache2/FileOperator:read	(JLokio/Buffer;J)V
      //   261: aload_0
      //   262: aload_0
      //   263: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   266: lload_2
      //   267: ladd
      //   268: putfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   271: lload_2
      //   272: lreturn
      //   273: aload_0
      //   274: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   277: invokevirtual 170	okhttp3/internal/cache2/Relay:getUpstream	()Lokio/Source;
      //   280: astore 9
      //   282: aload 9
      //   284: invokestatic 59	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
      //   287: aload 9
      //   289: aload_0
      //   290: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   293: invokevirtual 173	okhttp3/internal/cache2/Relay:getUpstreamBuffer	()Lokio/Buffer;
      //   296: aload_0
      //   297: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   300: invokevirtual 176	okhttp3/internal/cache2/Relay:getBufferMaxSize	()J
      //   303: invokeinterface 178 4 0
      //   308: lstore 6
      //   310: lload 6
      //   312: ldc2_w 144
      //   315: lcmp
      //   316: ifne +93 -> 409
      //   319: aload_0
      //   320: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   323: astore_1
      //   324: aload_1
      //   325: aload_1
      //   326: invokevirtual 118	okhttp3/internal/cache2/Relay:getUpstreamPos	()J
      //   329: invokevirtual 182	okhttp3/internal/cache2/Relay:commit	(J)V
      //   332: aload_0
      //   333: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   336: astore_1
      //   337: aload_1
      //   338: monitorenter
      //   339: aload_0
      //   340: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   343: astore 9
      //   345: aconst_null
      //   346: checkcast 154	java/lang/Thread
      //   349: astore 10
      //   351: aload 9
      //   353: aconst_null
      //   354: invokevirtual 161	okhttp3/internal/cache2/Relay:setUpstreamReader	(Ljava/lang/Thread;)V
      //   357: aload_0
      //   358: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   361: astore 9
      //   363: aload 9
      //   365: ifnull +22 -> 387
      //   368: aload 9
      //   370: checkcast 4	java/lang/Object
      //   373: invokevirtual 185	java/lang/Object:notifyAll	()V
      //   376: getstatic 100	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   379: astore 9
      //   381: aload_1
      //   382: monitorexit
      //   383: ldc2_w 144
      //   386: lreturn
      //   387: new 187	java/lang/NullPointerException
      //   390: astore 9
      //   392: aload 9
      //   394: ldc -67
      //   396: invokespecial 192	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   399: aload 9
      //   401: athrow
      //   402: astore 9
      //   404: aload_1
      //   405: monitorexit
      //   406: aload 9
      //   408: athrow
      //   409: lload 6
      //   411: lload_2
      //   412: invokestatic 135	java/lang/Math:min	(JJ)J
      //   415: lstore_2
      //   416: aload_0
      //   417: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   420: invokevirtual 173	okhttp3/internal/cache2/Relay:getUpstreamBuffer	()Lokio/Buffer;
      //   423: astore 9
      //   425: aload 9
      //   427: aload_1
      //   428: lconst_0
      //   429: lload_2
      //   430: invokevirtual 139	okio/Buffer:copyTo	(Lokio/Buffer;JJ)Lokio/Buffer;
      //   433: pop
      //   434: aload_0
      //   435: aload_0
      //   436: getfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   439: lload_2
      //   440: ladd
      //   441: putfield 120	okhttp3/internal/cache2/Relay$RelaySource:sourcePos	J
      //   444: aload_0
      //   445: getfield 78	okhttp3/internal/cache2/Relay$RelaySource:fileOperator	Lokhttp3/internal/cache2/FileOperator;
      //   448: astore_1
      //   449: aload_1
      //   450: invokestatic 59	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
      //   453: aload_1
      //   454: aload_0
      //   455: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   458: invokevirtual 118	okhttp3/internal/cache2/Relay:getUpstreamPos	()J
      //   461: ldc2_w 162
      //   464: ladd
      //   465: aload_0
      //   466: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   469: invokevirtual 173	okhttp3/internal/cache2/Relay:getUpstreamBuffer	()Lokio/Buffer;
      //   472: invokevirtual 195	okio/Buffer:clone	()Lokio/Buffer;
      //   475: lload 6
      //   477: invokevirtual 198	okhttp3/internal/cache2/FileOperator:write	(JLokio/Buffer;J)V
      //   480: aload_0
      //   481: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   484: astore 9
      //   486: aload 9
      //   488: monitorenter
      //   489: aload_0
      //   490: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   493: invokevirtual 124	okhttp3/internal/cache2/Relay:getBuffer	()Lokio/Buffer;
      //   496: astore_1
      //   497: aload_0
      //   498: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   501: invokevirtual 173	okhttp3/internal/cache2/Relay:getUpstreamBuffer	()Lokio/Buffer;
      //   504: astore 10
      //   506: aload_1
      //   507: aload 10
      //   509: lload 6
      //   511: invokevirtual 201	okio/Buffer:write	(Lokio/Buffer;J)V
      //   514: aload_0
      //   515: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   518: invokevirtual 124	okhttp3/internal/cache2/Relay:getBuffer	()Lokio/Buffer;
      //   521: invokevirtual 129	okio/Buffer:size	()J
      //   524: aload_0
      //   525: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   528: invokevirtual 176	okhttp3/internal/cache2/Relay:getBufferMaxSize	()J
      //   531: lcmp
      //   532: ifle +31 -> 563
      //   535: aload_0
      //   536: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   539: invokevirtual 124	okhttp3/internal/cache2/Relay:getBuffer	()Lokio/Buffer;
      //   542: aload_0
      //   543: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   546: invokevirtual 124	okhttp3/internal/cache2/Relay:getBuffer	()Lokio/Buffer;
      //   549: invokevirtual 129	okio/Buffer:size	()J
      //   552: aload_0
      //   553: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   556: invokevirtual 176	okhttp3/internal/cache2/Relay:getBufferMaxSize	()J
      //   559: lsub
      //   560: invokevirtual 204	okio/Buffer:skip	(J)V
      //   563: aload_0
      //   564: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   567: astore_1
      //   568: aload_1
      //   569: aload_1
      //   570: invokevirtual 118	okhttp3/internal/cache2/Relay:getUpstreamPos	()J
      //   573: lload 6
      //   575: ladd
      //   576: invokevirtual 207	okhttp3/internal/cache2/Relay:setUpstreamPos	(J)V
      //   579: getstatic 100	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   582: astore_1
      //   583: aload 9
      //   585: monitorexit
      //   586: aload_0
      //   587: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   590: astore_1
      //   591: aload_1
      //   592: monitorenter
      //   593: aload_0
      //   594: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   597: astore 10
      //   599: aconst_null
      //   600: checkcast 154	java/lang/Thread
      //   603: astore 9
      //   605: aload 10
      //   607: aconst_null
      //   608: invokevirtual 161	okhttp3/internal/cache2/Relay:setUpstreamReader	(Ljava/lang/Thread;)V
      //   611: aload_0
      //   612: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   615: astore 9
      //   617: aload 9
      //   619: ifnull +20 -> 639
      //   622: aload 9
      //   624: checkcast 4	java/lang/Object
      //   627: invokevirtual 185	java/lang/Object:notifyAll	()V
      //   630: getstatic 100	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   633: astore 9
      //   635: aload_1
      //   636: monitorexit
      //   637: lload_2
      //   638: lreturn
      //   639: new 187	java/lang/NullPointerException
      //   642: astore 9
      //   644: aload 9
      //   646: ldc -67
      //   648: invokespecial 192	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   651: aload 9
      //   653: athrow
      //   654: astore 9
      //   656: aload_1
      //   657: monitorexit
      //   658: aload 9
      //   660: athrow
      //   661: astore_1
      //   662: goto +4 -> 666
      //   665: astore_1
      //   666: aload 9
      //   668: monitorexit
      //   669: aload_1
      //   670: athrow
      //   671: astore_1
      //   672: goto +8 -> 680
      //   675: astore_1
      //   676: goto +4 -> 680
      //   679: astore_1
      //   680: aload_0
      //   681: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   684: astore 9
      //   686: aload 9
      //   688: monitorenter
      //   689: aload_0
      //   690: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   693: astore 10
      //   695: aconst_null
      //   696: checkcast 154	java/lang/Thread
      //   699: astore 11
      //   701: aload 10
      //   703: aconst_null
      //   704: invokevirtual 161	okhttp3/internal/cache2/Relay:setUpstreamReader	(Ljava/lang/Thread;)V
      //   707: aload_0
      //   708: getfield 42	okhttp3/internal/cache2/Relay$RelaySource:this$0	Lokhttp3/internal/cache2/Relay;
      //   711: astore 10
      //   713: aload 10
      //   715: ifnonnull +15 -> 730
      //   718: new 187	java/lang/NullPointerException
      //   721: astore_1
      //   722: aload_1
      //   723: ldc -67
      //   725: invokespecial 192	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   728: aload_1
      //   729: athrow
      //   730: aload 10
      //   732: checkcast 4	java/lang/Object
      //   735: invokevirtual 185	java/lang/Object:notifyAll	()V
      //   738: getstatic 100	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   741: astore 10
      //   743: aload 9
      //   745: monitorexit
      //   746: aload_1
      //   747: athrow
      //   748: astore_1
      //   749: aload 9
      //   751: monitorexit
      //   752: aload_1
      //   753: athrow
      //   754: astore_1
      //   755: aload 9
      //   757: monitorexit
      //   758: aload_1
      //   759: athrow
      //   760: new 209	java/lang/IllegalStateException
      //   763: dup
      //   764: ldc -45
      //   766: invokevirtual 215	java/lang/Object:toString	()Ljava/lang/String;
      //   769: invokespecial 216	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
      //   772: checkcast 218	java/lang/Throwable
      //   775: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	776	0	this	RelaySource
      //   0	776	1	paramBuffer	Buffer
      //   0	776	2	paramLong	long
      //   21	195	4	i	int
      //   13	193	5	j	int
      //   50	524	6	l	long
      //   157	3	8	bool	boolean
      //   10	390	9	localObject1	Object
      //   402	5	9	localObject2	Object
      //   423	229	9	localObject3	Object
      //   654	13	9	localObject4	Object
      //   349	393	10	localObject5	Object
      //   699	1	11	localThread	Thread
      // Exception table:
      //   from	to	target	type
      //   339	363	402	finally
      //   368	376	402	finally
      //   376	381	402	finally
      //   387	402	402	finally
      //   593	617	654	finally
      //   622	630	654	finally
      //   630	635	654	finally
      //   639	654	654	finally
      //   506	563	661	finally
      //   563	583	661	finally
      //   489	506	665	finally
      //   666	671	671	finally
      //   425	489	675	finally
      //   583	586	675	finally
      //   273	310	679	finally
      //   319	332	679	finally
      //   409	425	679	finally
      //   689	713	748	finally
      //   718	730	748	finally
      //   730	738	748	finally
      //   738	743	748	finally
      //   43	92	754	finally
      //   98	145	754	finally
      //   150	159	754	finally
      //   171	192	754	finally
      //   195	205	754	finally
    }
    
    public Timeout timeout()
    {
      return this.timeout;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/cache2/Relay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */