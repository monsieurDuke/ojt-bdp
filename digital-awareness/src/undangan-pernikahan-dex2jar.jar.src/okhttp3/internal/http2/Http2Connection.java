package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.LongRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskQueue.execute.1;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\000´\001\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\016\n\002\b\003\n\002\020#\n\002\020\b\n\002\b\f\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020%\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\013\n\002\020 \n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\035\n\002\030\002\n\002\b\024\030\000 \0012\0020\001:\b\001\001\001\001B\017\b\000\022\006\020\002\032\0020\003¢\006\002\020\004J\006\020P\032\0020QJ\b\020R\032\0020QH\026J'\020R\032\0020Q2\006\020S\032\0020T2\006\020U\032\0020T2\b\020V\032\004\030\0010WH\000¢\006\002\bXJ\022\020Y\032\0020Q2\b\020Z\032\004\030\0010WH\002J\006\020[\032\0020QJ\020\020\\\032\004\030\0010B2\006\020]\032\0020\022J\016\020^\032\0020\t2\006\020_\032\0020\006J&\020`\032\0020B2\006\020a\032\0020\0222\f\020b\032\b\022\004\022\0020d0c2\006\020e\032\0020\tH\002J\034\020`\032\0020B2\f\020b\032\b\022\004\022\0020d0c2\006\020e\032\0020\tJ\006\020f\032\0020\022J-\020g\032\0020Q2\006\020h\032\0020\0222\006\020i\032\0020j2\006\020k\032\0020\0222\006\020l\032\0020\tH\000¢\006\002\bmJ+\020n\032\0020Q2\006\020h\032\0020\0222\f\020b\032\b\022\004\022\0020d0c2\006\020l\032\0020\tH\000¢\006\002\boJ#\020p\032\0020Q2\006\020h\032\0020\0222\f\020b\032\b\022\004\022\0020d0cH\000¢\006\002\bqJ\035\020r\032\0020Q2\006\020h\032\0020\0222\006\020s\032\0020TH\000¢\006\002\btJ$\020u\032\0020B2\006\020a\032\0020\0222\f\020b\032\b\022\004\022\0020d0c2\006\020e\032\0020\tJ\025\020v\032\0020\t2\006\020h\032\0020\022H\000¢\006\002\bwJ\027\020x\032\004\030\0010B2\006\020h\032\0020\022H\000¢\006\002\byJ\r\020z\032\0020QH\000¢\006\002\b{J\016\020|\032\0020Q2\006\020}\032\0020&J\016\020~\032\0020Q2\006\020\032\0020TJ\036\020\001\032\0020Q2\t\b\002\020\001\032\0020\t2\b\b\002\020E\032\0020FH\007J\030\020\001\032\0020Q2\007\020\001\032\0020\006H\000¢\006\003\b\001J,\020\001\032\0020Q2\006\020h\032\0020\0222\007\020\001\032\0020\t2\n\020\001\032\005\030\0010\0012\006\020k\032\0020\006J/\020\001\032\0020Q2\006\020h\032\0020\0222\007\020\001\032\0020\t2\r\020\001\032\b\022\004\022\0020d0cH\000¢\006\003\b\001J\007\020\001\032\0020QJ\"\020\001\032\0020Q2\007\020\001\032\0020\t2\007\020\001\032\0020\0222\007\020\001\032\0020\022J\007\020\001\032\0020QJ\037\020\001\032\0020Q2\006\020h\032\0020\0222\006\020\032\0020TH\000¢\006\003\b\001J\037\020\001\032\0020Q2\006\020h\032\0020\0222\006\020s\032\0020TH\000¢\006\003\b\001J \020\001\032\0020Q2\006\020h\032\0020\0222\007\020\001\032\0020\006H\000¢\006\003\b\001R\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\007\032\0020\006X\016¢\006\002\n\000R\024\020\b\032\0020\tX\004¢\006\b\n\000\032\004\b\n\020\013R\024\020\f\032\0020\rX\004¢\006\b\n\000\032\004\b\016\020\017R\024\020\020\032\b\022\004\022\0020\0220\021X\004¢\006\002\n\000R\016\020\023\032\0020\006X\016¢\006\002\n\000R\016\020\024\032\0020\006X\016¢\006\002\n\000R\016\020\025\032\0020\006X\016¢\006\002\n\000R\016\020\026\032\0020\006X\016¢\006\002\n\000R\016\020\027\032\0020\006X\016¢\006\002\n\000R\016\020\030\032\0020\tX\016¢\006\002\n\000R\032\020\031\032\0020\022X\016¢\006\016\n\000\032\004\b\032\020\033\"\004\b\034\020\035R\024\020\036\032\0020\037X\004¢\006\b\n\000\032\004\b \020!R\032\020\"\032\0020\022X\016¢\006\016\n\000\032\004\b#\020\033\"\004\b$\020\035R\021\020%\032\0020&¢\006\b\n\000\032\004\b'\020(R\032\020)\032\0020&X\016¢\006\016\n\000\032\004\b*\020(\"\004\b+\020,R\016\020-\032\0020.X\004¢\006\002\n\000R\016\020/\032\00200X\004¢\006\002\n\000R\036\0202\032\0020\0062\006\0201\032\0020\006@BX\016¢\006\b\n\000\032\004\b3\0204R\036\0205\032\0020\0062\006\0201\032\0020\006@BX\016¢\006\b\n\000\032\004\b6\0204R\025\0207\032\00608R\0020\000¢\006\b\n\000\032\004\b9\020:R\016\020;\032\00200X\004¢\006\002\n\000R\024\020<\032\0020=X\004¢\006\b\n\000\032\004\b>\020?R \020@\032\016\022\004\022\0020\022\022\004\022\0020B0AX\004¢\006\b\n\000\032\004\bC\020DR\016\020E\032\0020FX\004¢\006\002\n\000R\036\020G\032\0020\0062\006\0201\032\0020\006@BX\016¢\006\b\n\000\032\004\bH\0204R\036\020I\032\0020\0062\006\0201\032\0020\006@BX\016¢\006\b\n\000\032\004\bJ\0204R\021\020K\032\0020L¢\006\b\n\000\032\004\bM\020NR\016\020O\032\00200X\004¢\006\002\n\000¨\006\001"}, d2={"Lokhttp3/internal/http2/Http2Connection;", "Ljava/io/Closeable;", "builder", "Lokhttp3/internal/http2/Http2Connection$Builder;", "(Lokhttp3/internal/http2/Http2Connection$Builder;)V", "awaitPingsSent", "", "awaitPongsReceived", "client", "", "getClient$okhttp", "()Z", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "currentPushRequests", "", "", "degradedPingsSent", "degradedPongDeadlineNs", "degradedPongsReceived", "intervalPingsSent", "intervalPongsReceived", "isShutdown", "lastGoodStreamId", "getLastGoodStreamId$okhttp", "()I", "setLastGoodStreamId$okhttp", "(I)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "nextStreamId", "getNextStreamId$okhttp", "setNextStreamId$okhttp", "okHttpSettings", "Lokhttp3/internal/http2/Settings;", "getOkHttpSettings", "()Lokhttp3/internal/http2/Settings;", "peerSettings", "getPeerSettings", "setPeerSettings", "(Lokhttp3/internal/http2/Settings;)V", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "pushQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "<set-?>", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "readBytesTotal", "getReadBytesTotal", "readerRunnable", "Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "getReaderRunnable", "()Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "settingsListenerQueue", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "streams", "", "Lokhttp3/internal/http2/Http2Stream;", "getStreams$okhttp", "()Ljava/util/Map;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "writeBytesMaximum", "getWriteBytesMaximum", "writeBytesTotal", "getWriteBytesTotal", "writer", "Lokhttp3/internal/http2/Http2Writer;", "getWriter", "()Lokhttp3/internal/http2/Http2Writer;", "writerQueue", "awaitPong", "", "close", "connectionCode", "Lokhttp3/internal/http2/ErrorCode;", "streamCode", "cause", "Ljava/io/IOException;", "close$okhttp", "failConnection", "e", "flush", "getStream", "id", "isHealthy", "nowNs", "newStream", "associatedStreamId", "requestHeaders", "", "Lokhttp3/internal/http2/Header;", "out", "openStreamCount", "pushDataLater", "streamId", "source", "Lokio/BufferedSource;", "byteCount", "inFinished", "pushDataLater$okhttp", "pushHeadersLater", "pushHeadersLater$okhttp", "pushRequestLater", "pushRequestLater$okhttp", "pushResetLater", "errorCode", "pushResetLater$okhttp", "pushStream", "pushedStream", "pushedStream$okhttp", "removeStream", "removeStream$okhttp", "sendDegradedPingLater", "sendDegradedPingLater$okhttp", "setSettings", "settings", "shutdown", "statusCode", "start", "sendConnectionPreface", "updateConnectionFlowControl", "read", "updateConnectionFlowControl$okhttp", "writeData", "outFinished", "buffer", "Lokio/Buffer;", "writeHeaders", "alternating", "writeHeaders$okhttp", "writePing", "reply", "payload1", "payload2", "writePingAndAwaitPong", "writeSynReset", "writeSynReset$okhttp", "writeSynResetLater", "writeSynResetLater$okhttp", "writeWindowUpdateLater", "unacknowledgedBytesRead", "writeWindowUpdateLater$okhttp", "Builder", "Companion", "Listener", "ReaderRunnable", "okhttp"}, k=1, mv={1, 4, 0})
public final class Http2Connection
  implements Closeable
{
  public static final int AWAIT_PING = 3;
  public static final Companion Companion = new Companion(null);
  private static final Settings DEFAULT_SETTINGS;
  public static final int DEGRADED_PING = 2;
  public static final int DEGRADED_PONG_TIMEOUT_NS = 1000000000;
  public static final int INTERVAL_PING = 1;
  public static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
  private long awaitPingsSent;
  private long awaitPongsReceived;
  private final boolean client;
  private final String connectionName;
  private final Set<Integer> currentPushRequests;
  private long degradedPingsSent;
  private long degradedPongDeadlineNs;
  private long degradedPongsReceived;
  private long intervalPingsSent;
  private long intervalPongsReceived;
  private boolean isShutdown;
  private int lastGoodStreamId;
  private final Listener listener;
  private int nextStreamId;
  private final Settings okHttpSettings;
  private Settings peerSettings;
  private final PushObserver pushObserver;
  private final TaskQueue pushQueue;
  private long readBytesAcknowledged;
  private long readBytesTotal;
  private final ReaderRunnable readerRunnable;
  private final TaskQueue settingsListenerQueue;
  private final Socket socket;
  private final Map<Integer, Http2Stream> streams;
  private final TaskRunner taskRunner;
  private long writeBytesMaximum;
  private long writeBytesTotal;
  private final Http2Writer writer;
  private final TaskQueue writerQueue;
  
  static
  {
    Settings localSettings = new Settings();
    localSettings.set(7, 65535);
    localSettings.set(5, 16384);
    DEFAULT_SETTINGS = localSettings;
  }
  
  public Http2Connection(Builder paramBuilder)
  {
    boolean bool = paramBuilder.getClient$okhttp();
    this.client = bool;
    this.listener = paramBuilder.getListener$okhttp();
    this.streams = ((Map)new LinkedHashMap());
    Object localObject1 = paramBuilder.getConnectionName$okhttp();
    this.connectionName = ((String)localObject1);
    int i;
    if (paramBuilder.getClient$okhttp()) {
      i = 3;
    } else {
      i = 2;
    }
    this.nextStreamId = i;
    Object localObject2 = paramBuilder.getTaskRunner$okhttp();
    this.taskRunner = ((TaskRunner)localObject2);
    this.writerQueue = ((TaskRunner)localObject2).newQueue();
    this.pushQueue = ((TaskRunner)localObject2).newQueue();
    this.settingsListenerQueue = ((TaskRunner)localObject2).newQueue();
    this.pushObserver = paramBuilder.getPushObserver$okhttp();
    Settings localSettings = new Settings();
    if (paramBuilder.getClient$okhttp()) {
      localSettings.set(7, 16777216);
    }
    localObject2 = Unit.INSTANCE;
    this.okHttpSettings = localSettings;
    localObject2 = DEFAULT_SETTINGS;
    this.peerSettings = ((Settings)localObject2);
    this.writeBytesMaximum = ((Settings)localObject2).getInitialWindowSize();
    this.socket = paramBuilder.getSocket$okhttp();
    this.writer = new Http2Writer(paramBuilder.getSink$okhttp(), bool);
    this.readerRunnable = new ReaderRunnable(new Http2Reader(paramBuilder.getSource$okhttp(), bool));
    this.currentPushRequests = ((Set)new LinkedHashSet());
    if (paramBuilder.getPingIntervalMillis$okhttp() != 0)
    {
      final long l = TimeUnit.MILLISECONDS.toNanos(paramBuilder.getPingIntervalMillis$okhttp());
      paramBuilder = this.writerQueue;
      localObject1 = (String)localObject1 + " ping";
      localObject1 = (Task)new Task((String)localObject1, (String)localObject1, this, l)
      {
        final String $name;
        
        public long runOnce()
        {
          synchronized (jdField_this)
          {
            int i;
            if (Http2Connection.access$getIntervalPongsReceived$p(jdField_this) < Http2Connection.access$getIntervalPingsSent$p(jdField_this))
            {
              i = 1;
            }
            else
            {
              Http2Connection localHttp2Connection2 = jdField_this;
              Http2Connection.access$setIntervalPingsSent$p(localHttp2Connection2, Http2Connection.access$getIntervalPingsSent$p(localHttp2Connection2) + 1L);
              i = 0;
            }
            long l;
            if (i != 0)
            {
              Http2Connection.access$failConnection(jdField_this, null);
              l = -1L;
            }
            else
            {
              jdField_this.writePing(false, 1, 0);
              l = l;
            }
            return l;
          }
        }
      };
      paramBuilder.schedule((Task)localObject1, l);
    }
  }
  
  private final void failConnection(IOException paramIOException)
  {
    close$okhttp(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR, paramIOException);
  }
  
  /* Error */
  private final Http2Stream newStream(int paramInt, List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    // Byte code:
    //   0: iload_3
    //   1: iconst_1
    //   2: ixor
    //   3: istore 6
    //   5: aload_0
    //   6: getfield 324	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   9: astore 11
    //   11: aload 11
    //   13: monitorenter
    //   14: aload_0
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield 270	okhttp3/internal/http2/Http2Connection:nextStreamId	I
    //   20: ldc_w 435
    //   23: if_icmple +10 -> 33
    //   26: aload_0
    //   27: getstatic 438	okhttp3/internal/http2/ErrorCode:REFUSED_STREAM	Lokhttp3/internal/http2/ErrorCode;
    //   30: invokevirtual 441	okhttp3/internal/http2/Http2Connection:shutdown	(Lokhttp3/internal/http2/ErrorCode;)V
    //   33: aload_0
    //   34: getfield 414	okhttp3/internal/http2/Http2Connection:isShutdown	Z
    //   37: ifne +222 -> 259
    //   40: aload_0
    //   41: getfield 270	okhttp3/internal/http2/Http2Connection:nextStreamId	I
    //   44: istore 5
    //   46: aload_0
    //   47: iload 5
    //   49: iconst_2
    //   50: iadd
    //   51: putfield 270	okhttp3/internal/http2/Http2Connection:nextStreamId	I
    //   54: new 443	okhttp3/internal/http2/Http2Stream
    //   57: astore 12
    //   59: aload 12
    //   61: iload 5
    //   63: aload_0
    //   64: iload 6
    //   66: iconst_0
    //   67: aconst_null
    //   68: invokespecial 446	okhttp3/internal/http2/Http2Stream:<init>	(ILokhttp3/internal/http2/Http2Connection;ZZLokhttp3/Headers;)V
    //   71: iload_3
    //   72: ifeq +50 -> 122
    //   75: aload_0
    //   76: getfield 448	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   79: aload_0
    //   80: getfield 309	okhttp3/internal/http2/Http2Connection:writeBytesMaximum	J
    //   83: lcmp
    //   84: ifge +38 -> 122
    //   87: aload 12
    //   89: invokevirtual 450	okhttp3/internal/http2/Http2Stream:getWriteBytesTotal	()J
    //   92: lstore 9
    //   94: aload 12
    //   96: invokevirtual 452	okhttp3/internal/http2/Http2Stream:getWriteBytesMaximum	()J
    //   99: lstore 7
    //   101: lload 9
    //   103: lload 7
    //   105: lcmp
    //   106: iflt +6 -> 112
    //   109: goto +13 -> 122
    //   112: iconst_0
    //   113: istore 4
    //   115: goto +10 -> 125
    //   118: astore_2
    //   119: goto +154 -> 273
    //   122: iconst_1
    //   123: istore 4
    //   125: aload 12
    //   127: invokevirtual 455	okhttp3/internal/http2/Http2Stream:isOpen	()Z
    //   130: istore_3
    //   131: iload_3
    //   132: ifeq +20 -> 152
    //   135: aload_0
    //   136: getfield 264	okhttp3/internal/http2/Http2Connection:streams	Ljava/util/Map;
    //   139: iload 5
    //   141: invokestatic 461	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   144: aload 12
    //   146: invokeinterface 465 3 0
    //   151: pop
    //   152: getstatic 300	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   155: astore 13
    //   157: aload_0
    //   158: monitorexit
    //   159: iload_1
    //   160: ifne +22 -> 182
    //   163: aload_0
    //   164: getfield 324	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   167: astore 13
    //   169: aload 13
    //   171: iload 6
    //   173: iload 5
    //   175: aload_2
    //   176: invokevirtual 469	okhttp3/internal/http2/Http2Writer:headers	(ZILjava/util/List;)V
    //   179: goto +23 -> 202
    //   182: aload_0
    //   183: getfield 253	okhttp3/internal/http2/Http2Connection:client	Z
    //   186: iconst_1
    //   187: ixor
    //   188: ifeq +36 -> 224
    //   191: aload_0
    //   192: getfield 324	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   195: iload_1
    //   196: iload 5
    //   198: aload_2
    //   199: invokevirtual 473	okhttp3/internal/http2/Http2Writer:pushPromise	(IILjava/util/List;)V
    //   202: getstatic 300	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   205: astore_2
    //   206: aload 11
    //   208: monitorexit
    //   209: iload 4
    //   211: ifeq +10 -> 221
    //   214: aload_0
    //   215: getfield 324	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   218: invokevirtual 475	okhttp3/internal/http2/Http2Writer:flush	()V
    //   221: aload 12
    //   223: areturn
    //   224: new 477	java/lang/IllegalArgumentException
    //   227: astore_2
    //   228: aload_2
    //   229: ldc_w 479
    //   232: invokevirtual 480	java/lang/Object:toString	()Ljava/lang/String;
    //   235: invokespecial 483	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   238: aload_2
    //   239: checkcast 485	java/lang/Throwable
    //   242: athrow
    //   243: astore_2
    //   244: goto +34 -> 278
    //   247: astore_2
    //   248: goto +30 -> 278
    //   251: astore_2
    //   252: goto +21 -> 273
    //   255: astore_2
    //   256: goto +17 -> 273
    //   259: new 487	okhttp3/internal/http2/ConnectionShutdownException
    //   262: astore_2
    //   263: aload_2
    //   264: invokespecial 488	okhttp3/internal/http2/ConnectionShutdownException:<init>	()V
    //   267: aload_2
    //   268: checkcast 485	java/lang/Throwable
    //   271: athrow
    //   272: astore_2
    //   273: aload_0
    //   274: monitorexit
    //   275: aload_2
    //   276: athrow
    //   277: astore_2
    //   278: aload 11
    //   280: monitorexit
    //   281: aload_2
    //   282: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	283	0	this	Http2Connection
    //   0	283	1	paramInt	int
    //   0	283	2	paramList	List<Header>
    //   0	283	3	paramBoolean	boolean
    //   113	97	4	i	int
    //   44	153	5	j	int
    //   3	169	6	bool	boolean
    //   99	5	7	l1	long
    //   92	10	9	l2	long
    //   9	270	11	localHttp2Writer	Http2Writer
    //   57	165	12	localHttp2Stream	Http2Stream
    //   155	15	13	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   75	101	118	finally
    //   135	152	118	finally
    //   169	179	243	finally
    //   182	202	243	finally
    //   202	206	243	finally
    //   224	243	243	finally
    //   157	159	247	finally
    //   163	169	247	finally
    //   59	71	251	finally
    //   125	131	251	finally
    //   152	157	251	finally
    //   46	59	255	finally
    //   16	33	272	finally
    //   33	46	272	finally
    //   259	272	272	finally
    //   14	16	277	finally
    //   273	277	277	finally
  }
  
  public final void awaitPong()
    throws InterruptedException
  {
    try
    {
      while (this.awaitPongsReceived < this.awaitPingsSent) {
        ((Object)this).wait();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void close()
  {
    close$okhttp(ErrorCode.NO_ERROR, ErrorCode.CANCEL, null);
  }
  
  public final void close$okhttp(ErrorCode paramErrorCode1, ErrorCode paramErrorCode2, IOException paramIOException)
  {
    Intrinsics.checkNotNullParameter(paramErrorCode1, "connectionCode");
    Intrinsics.checkNotNullParameter(paramErrorCode2, "streamCode");
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      paramErrorCode1 = new StringBuilder().append("Thread ");
      paramErrorCode2 = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(paramErrorCode2, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(paramErrorCode2.getName() + " MUST NOT hold lock on " + this));
    }
    try
    {
      shutdown(paramErrorCode1);
    }
    catch (IOException paramErrorCode1) {}
    paramErrorCode1 = (Http2Stream[])null;
    try
    {
      boolean bool = this.streams.isEmpty();
      int i = 0;
      if ((bool ^ true))
      {
        paramErrorCode1 = this.streams.values().toArray(new Http2Stream[0]);
        if (paramErrorCode1 != null)
        {
          paramErrorCode1 = (Http2Stream[])paramErrorCode1;
          this.streams.clear();
        }
        else
        {
          paramErrorCode1 = new java/lang/NullPointerException;
          paramErrorCode1.<init>("null cannot be cast to non-null type kotlin.Array<T>");
          throw paramErrorCode1;
        }
      }
      Unit localUnit = Unit.INSTANCE;
      if (paramErrorCode1 != null)
      {
        int j = paramErrorCode1.length;
        while (i < j)
        {
          localUnit = paramErrorCode1[i];
          try
          {
            localUnit.close(paramErrorCode2, paramIOException);
          }
          catch (IOException localIOException) {}
          i++;
        }
      }
      try
      {
        this.writer.close();
      }
      catch (IOException paramErrorCode1) {}
      try
      {
        this.socket.close();
      }
      catch (IOException paramErrorCode1) {}
      this.writerQueue.shutdown();
      this.pushQueue.shutdown();
      this.settingsListenerQueue.shutdown();
      return;
    }
    finally {}
  }
  
  public final void flush()
    throws IOException
  {
    this.writer.flush();
  }
  
  public final boolean getClient$okhttp()
  {
    return this.client;
  }
  
  public final String getConnectionName$okhttp()
  {
    return this.connectionName;
  }
  
  public final int getLastGoodStreamId$okhttp()
  {
    return this.lastGoodStreamId;
  }
  
  public final Listener getListener$okhttp()
  {
    return this.listener;
  }
  
  public final int getNextStreamId$okhttp()
  {
    return this.nextStreamId;
  }
  
  public final Settings getOkHttpSettings()
  {
    return this.okHttpSettings;
  }
  
  public final Settings getPeerSettings()
  {
    return this.peerSettings;
  }
  
  public final long getReadBytesAcknowledged()
  {
    return this.readBytesAcknowledged;
  }
  
  public final long getReadBytesTotal()
  {
    return this.readBytesTotal;
  }
  
  public final ReaderRunnable getReaderRunnable()
  {
    return this.readerRunnable;
  }
  
  public final Socket getSocket$okhttp()
  {
    return this.socket;
  }
  
  public final Http2Stream getStream(int paramInt)
  {
    try
    {
      Http2Stream localHttp2Stream = (Http2Stream)this.streams.get(Integer.valueOf(paramInt));
      return localHttp2Stream;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final Map<Integer, Http2Stream> getStreams$okhttp()
  {
    return this.streams;
  }
  
  public final long getWriteBytesMaximum()
  {
    return this.writeBytesMaximum;
  }
  
  public final long getWriteBytesTotal()
  {
    return this.writeBytesTotal;
  }
  
  public final Http2Writer getWriter()
  {
    return this.writer;
  }
  
  public final boolean isHealthy(long paramLong)
  {
    try
    {
      boolean bool = this.isShutdown;
      if (bool) {
        return false;
      }
      if (this.degradedPongsReceived < this.degradedPingsSent)
      {
        long l = this.degradedPongDeadlineNs;
        if (paramLong >= l) {
          return false;
        }
      }
      return true;
    }
    finally {}
  }
  
  public final Http2Stream newStream(List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramList, "requestHeaders");
    return newStream(0, paramList, paramBoolean);
  }
  
  public final int openStreamCount()
  {
    try
    {
      int i = this.streams.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void pushDataLater$okhttp(final int paramInt1, BufferedSource paramBufferedSource, final int paramInt2, final boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBufferedSource, "source");
    final Object localObject = new Buffer();
    paramBufferedSource.require(paramInt2);
    paramBufferedSource.read((Buffer)localObject, paramInt2);
    paramBufferedSource = this.pushQueue;
    String str = this.connectionName + '[' + paramInt1 + "] onData";
    localObject = (Task)new Task(str, true)
    {
      final String $name;
      
      public long runOnce()
      {
        try
        {
          boolean bool = Http2Connection.access$getPushObserver$p(jdField_this).onData(paramInt1, (BufferedSource)localObject, paramInt2, paramBoolean);
          if (bool) {
            jdField_this.getWriter().rstStream(paramInt1, ErrorCode.CANCEL);
          }
          if ((bool) || (paramBoolean)) {}
          synchronized (jdField_this)
          {
            Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt1));
          }
          return -1L;
        }
        catch (IOException localIOException) {}
      }
    };
    paramBufferedSource.schedule((Task)localObject, 0L);
  }
  
  public final void pushHeadersLater$okhttp(final int paramInt, final List<Header> paramList, final boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramList, "requestHeaders");
    TaskQueue localTaskQueue = this.pushQueue;
    String str = this.connectionName + '[' + paramInt + "] onHeaders";
    paramList = (Task)new Task(str, true)
    {
      final String $name;
      
      public long runOnce()
      {
        boolean bool = Http2Connection.access$getPushObserver$p(jdField_this).onHeaders(paramInt, paramList, paramBoolean);
        if (bool) {
          try
          {
            jdField_this.getWriter().rstStream(paramInt, ErrorCode.CANCEL);
          }
          catch (IOException localIOException)
          {
            break label101;
          }
        }
        if ((bool) || (paramBoolean)) {}
        synchronized (jdField_this)
        {
          Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt));
        }
        label101:
        return -1L;
      }
    };
    localTaskQueue.schedule(paramList, 0L);
  }
  
  public final void pushRequestLater$okhttp(final int paramInt, final List<Header> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "requestHeaders");
    try
    {
      if (this.currentPushRequests.contains(Integer.valueOf(paramInt)))
      {
        writeSynResetLater$okhttp(paramInt, ErrorCode.PROTOCOL_ERROR);
        return;
      }
      this.currentPushRequests.add(Integer.valueOf(paramInt));
      TaskQueue localTaskQueue = this.pushQueue;
      String str = this.connectionName + '[' + paramInt + "] onRequest";
      paramList = (Task)new Task(str, true)
      {
        final String $name;
        
        public long runOnce()
        {
          boolean bool = Http2Connection.access$getPushObserver$p(jdField_this).onRequest(paramInt, paramList);
          if (bool) {
            try
            {
              jdField_this.getWriter().rstStream(paramInt, ErrorCode.CANCEL);
              synchronized (jdField_this)
              {
                Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt));
              }
              return -1L;
            }
            catch (IOException localIOException) {}
          }
        }
      };
      localTaskQueue.schedule(paramList, 0L);
      return;
    }
    finally {}
  }
  
  public final void pushResetLater$okhttp(final int paramInt, final ErrorCode paramErrorCode)
  {
    Intrinsics.checkNotNullParameter(paramErrorCode, "errorCode");
    TaskQueue localTaskQueue = this.pushQueue;
    String str = this.connectionName + '[' + paramInt + "] onReset";
    paramErrorCode = (Task)new Task(str, true)
    {
      final String $name;
      
      public long runOnce()
      {
        Http2Connection.access$getPushObserver$p(jdField_this).onReset(paramInt, paramErrorCode);
        synchronized (jdField_this)
        {
          Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt));
          Unit localUnit = Unit.INSTANCE;
          return -1L;
        }
      }
    };
    localTaskQueue.schedule(paramErrorCode, 0L);
  }
  
  public final Http2Stream pushStream(int paramInt, List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramList, "requestHeaders");
    if ((this.client ^ true)) {
      return newStream(paramInt, paramList, paramBoolean);
    }
    throw ((Throwable)new IllegalStateException("Client cannot push requests.".toString()));
  }
  
  public final boolean pushedStream$okhttp(int paramInt)
  {
    boolean bool;
    if ((paramInt != 0) && ((paramInt & 0x1) == 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final Http2Stream removeStream$okhttp(int paramInt)
  {
    try
    {
      Http2Stream localHttp2Stream = (Http2Stream)this.streams.remove(Integer.valueOf(paramInt));
      ((Object)this).notifyAll();
      return localHttp2Stream;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void sendDegradedPingLater$okhttp()
  {
    try
    {
      long l1 = this.degradedPongsReceived;
      long l2 = this.degradedPingsSent;
      if (l1 < l2) {
        return;
      }
      this.degradedPingsSent = (l2 + 1L);
      this.degradedPongDeadlineNs = (System.nanoTime() + 1000000000);
      Object localObject1 = Unit.INSTANCE;
      localObject1 = this.writerQueue;
      Object localObject3 = this.connectionName + " ping";
      localObject3 = (Task)new Task((String)localObject3, true)
      {
        final String $name;
        
        public long runOnce()
        {
          jdField_this.writePing(false, 2, 0);
          return -1L;
        }
      };
      ((TaskQueue)localObject1).schedule((Task)localObject3, 0L);
      return;
    }
    finally {}
  }
  
  public final void setLastGoodStreamId$okhttp(int paramInt)
  {
    this.lastGoodStreamId = paramInt;
  }
  
  public final void setNextStreamId$okhttp(int paramInt)
  {
    this.nextStreamId = paramInt;
  }
  
  public final void setPeerSettings(Settings paramSettings)
  {
    Intrinsics.checkNotNullParameter(paramSettings, "<set-?>");
    this.peerSettings = paramSettings;
  }
  
  public final void setSettings(Settings paramSettings)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramSettings, "settings");
    synchronized (this.writer)
    {
      try
      {
        if (!this.isShutdown)
        {
          this.okHttpSettings.merge(paramSettings);
          Unit localUnit = Unit.INSTANCE;
          this.writer.settings(paramSettings);
          paramSettings = Unit.INSTANCE;
          return;
        }
        paramSettings = new okhttp3/internal/http2/ConnectionShutdownException;
        paramSettings.<init>();
        throw ((Throwable)paramSettings);
      }
      finally {}
    }
  }
  
  public final void shutdown(ErrorCode paramErrorCode)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramErrorCode, "statusCode");
    synchronized (this.writer)
    {
      try
      {
        boolean bool = this.isShutdown;
        if (bool) {
          return;
        }
        this.isShutdown = true;
        int i = this.lastGoodStreamId;
        Unit localUnit = Unit.INSTANCE;
        this.writer.goAway(i, paramErrorCode, Util.EMPTY_BYTE_ARRAY);
        paramErrorCode = Unit.INSTANCE;
        return;
      }
      finally {}
    }
  }
  
  public final void start()
    throws IOException
  {
    start$default(this, false, null, 3, null);
  }
  
  public final void start(boolean paramBoolean)
    throws IOException
  {
    start$default(this, paramBoolean, null, 2, null);
  }
  
  public final void start(boolean paramBoolean, TaskRunner paramTaskRunner)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramTaskRunner, "taskRunner");
    if (paramBoolean)
    {
      this.writer.connectionPreface();
      this.writer.settings(this.okHttpSettings);
      int i = this.okHttpSettings.getInitialWindowSize();
      if (i != 65535) {
        this.writer.windowUpdate(0, i - 65535);
      }
    }
    paramTaskRunner = paramTaskRunner.newQueue();
    Object localObject = this.connectionName;
    localObject = (Task)new TaskQueue.execute.1((Function0)this.readerRunnable, (String)localObject, true, (String)localObject, true);
    paramTaskRunner.schedule((Task)localObject, 0L);
  }
  
  public final void updateConnectionFlowControl$okhttp(long paramLong)
  {
    try
    {
      paramLong = this.readBytesTotal + paramLong;
      this.readBytesTotal = paramLong;
      paramLong -= this.readBytesAcknowledged;
      if (paramLong >= this.okHttpSettings.getInitialWindowSize() / 2)
      {
        writeWindowUpdateLater$okhttp(0, paramLong);
        this.readBytesAcknowledged += paramLong;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public final void writeData(int paramInt, boolean paramBoolean, Buffer paramBuffer, long paramLong)
    throws IOException
  {
    // Byte code:
    //   0: lload 4
    //   2: lconst_0
    //   3: lcmp
    //   4: ifne +15 -> 19
    //   7: aload_0
    //   8: getfield 324	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   11: iload_2
    //   12: iload_1
    //   13: aload_3
    //   14: iconst_0
    //   15: invokevirtual 720	okhttp3/internal/http2/Http2Writer:data	(ZILokio/Buffer;I)V
    //   18: return
    //   19: lload 4
    //   21: lconst_0
    //   22: lcmp
    //   23: ifle +185 -> 208
    //   26: aload_0
    //   27: monitorenter
    //   28: aload_0
    //   29: getfield 448	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   32: lstore 9
    //   34: aload_0
    //   35: getfield 309	okhttp3/internal/http2/Http2Connection:writeBytesMaximum	J
    //   38: lstore 7
    //   40: lload 9
    //   42: lload 7
    //   44: lcmp
    //   45: iflt +45 -> 90
    //   48: aload_0
    //   49: getfield 264	okhttp3/internal/http2/Http2Connection:streams	Ljava/util/Map;
    //   52: iload_1
    //   53: invokestatic 461	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   56: invokeinterface 723 2 0
    //   61: ifeq +13 -> 74
    //   64: aload_0
    //   65: checkcast 4	java/lang/Object
    //   68: invokevirtual 504	java/lang/Object:wait	()V
    //   71: goto -43 -> 28
    //   74: new 434	java/io/IOException
    //   77: astore_3
    //   78: aload_3
    //   79: ldc_w 725
    //   82: invokespecial 726	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   85: aload_3
    //   86: checkcast 485	java/lang/Throwable
    //   89: athrow
    //   90: lload 4
    //   92: lload 7
    //   94: lload 9
    //   96: lsub
    //   97: invokestatic 732	java/lang/Math:min	(JJ)J
    //   100: l2i
    //   101: aload_0
    //   102: getfield 324	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   105: invokevirtual 735	okhttp3/internal/http2/Http2Writer:maxDataLength	()I
    //   108: invokestatic 738	java/lang/Math:min	(II)I
    //   111: istore 6
    //   113: aload_0
    //   114: aload_0
    //   115: getfield 448	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   118: iload 6
    //   120: i2l
    //   121: ladd
    //   122: putfield 448	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   125: getstatic 300	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   128: astore 12
    //   130: aload_0
    //   131: monitorexit
    //   132: lload 4
    //   134: iload 6
    //   136: i2l
    //   137: lsub
    //   138: lstore 4
    //   140: aload_0
    //   141: getfield 324	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   144: astore 12
    //   146: iload_2
    //   147: ifeq +16 -> 163
    //   150: lload 4
    //   152: lconst_0
    //   153: lcmp
    //   154: ifne +9 -> 163
    //   157: iconst_1
    //   158: istore 11
    //   160: goto +6 -> 166
    //   163: iconst_0
    //   164: istore 11
    //   166: aload 12
    //   168: iload 11
    //   170: iload_1
    //   171: aload_3
    //   172: iload 6
    //   174: invokevirtual 720	okhttp3/internal/http2/Http2Writer:data	(ZILokio/Buffer;I)V
    //   177: goto -158 -> 19
    //   180: astore_3
    //   181: goto +23 -> 204
    //   184: astore_3
    //   185: invokestatic 529	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   188: invokevirtual 741	java/lang/Thread:interrupt	()V
    //   191: new 743	java/io/InterruptedIOException
    //   194: astore_3
    //   195: aload_3
    //   196: invokespecial 744	java/io/InterruptedIOException:<init>	()V
    //   199: aload_3
    //   200: checkcast 485	java/lang/Throwable
    //   203: athrow
    //   204: aload_0
    //   205: monitorexit
    //   206: aload_3
    //   207: athrow
    //   208: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	209	0	this	Http2Connection
    //   0	209	1	paramInt	int
    //   0	209	2	paramBoolean	boolean
    //   0	209	3	paramBuffer	Buffer
    //   0	209	4	paramLong	long
    //   111	62	6	i	int
    //   38	55	7	l1	long
    //   32	63	9	l2	long
    //   158	11	11	bool	boolean
    //   128	39	12	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   28	40	180	finally
    //   48	71	180	finally
    //   74	90	180	finally
    //   90	130	180	finally
    //   185	204	180	finally
    //   28	40	184	java/lang/InterruptedException
    //   48	71	184	java/lang/InterruptedException
    //   74	90	184	java/lang/InterruptedException
  }
  
  public final void writeHeaders$okhttp(int paramInt, boolean paramBoolean, List<Header> paramList)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramList, "alternating");
    this.writer.headers(paramBoolean, paramInt, paramList);
  }
  
  public final void writePing()
    throws InterruptedException
  {
    try
    {
      this.awaitPingsSent += 1L;
      writePing(false, 3, 1330343787);
      return;
    }
    finally {}
  }
  
  public final void writePing(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    try
    {
      this.writer.ping(paramBoolean, paramInt1, paramInt2);
    }
    catch (IOException localIOException)
    {
      failConnection(localIOException);
    }
  }
  
  public final void writePingAndAwaitPong()
    throws InterruptedException
  {
    writePing();
    awaitPong();
  }
  
  public final void writeSynReset$okhttp(int paramInt, ErrorCode paramErrorCode)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramErrorCode, "statusCode");
    this.writer.rstStream(paramInt, paramErrorCode);
  }
  
  public final void writeSynResetLater$okhttp(final int paramInt, final ErrorCode paramErrorCode)
  {
    Intrinsics.checkNotNullParameter(paramErrorCode, "errorCode");
    TaskQueue localTaskQueue = this.writerQueue;
    String str = this.connectionName + '[' + paramInt + "] writeSynReset";
    paramErrorCode = (Task)new Task(str, true)
    {
      final String $name;
      
      public long runOnce()
      {
        try
        {
          jdField_this.writeSynReset$okhttp(paramInt, paramErrorCode);
        }
        catch (IOException localIOException)
        {
          Http2Connection.access$failConnection(jdField_this, localIOException);
        }
        return -1L;
      }
    };
    localTaskQueue.schedule(paramErrorCode, 0L);
  }
  
  public final void writeWindowUpdateLater$okhttp(final int paramInt, final long paramLong)
  {
    TaskQueue localTaskQueue = this.writerQueue;
    Object localObject = this.connectionName + '[' + paramInt + "] windowUpdate";
    localObject = (Task)new Task((String)localObject, true)
    {
      final String $name;
      
      public long runOnce()
      {
        try
        {
          jdField_this.getWriter().windowUpdate(paramInt, paramLong);
        }
        catch (IOException localIOException)
        {
          Http2Connection.access$failConnection(jdField_this, localIOException);
        }
        return -1L;
      }
    };
    localTaskQueue.schedule((Task)localObject, 0L);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000X\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\006\n\002\020\016\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\006\0207\032\00208J\016\020\021\032\0020\0002\006\020\021\032\0020\022J\016\020\027\032\0020\0002\006\020\027\032\0020\030J\016\020\035\032\0020\0002\006\020\035\032\0020\036J.\020)\032\0020\0002\006\020)\032\0020*2\b\b\002\0209\032\0020\f2\b\b\002\020/\032\002002\b\b\002\020#\032\0020$H\007R\032\020\002\032\0020\003X\016¢\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\nR\032\020\013\032\0020\fX.¢\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020R\032\020\021\032\0020\022X\016¢\006\016\n\000\032\004\b\023\020\024\"\004\b\025\020\026R\032\020\027\032\0020\030X\016¢\006\016\n\000\032\004\b\031\020\032\"\004\b\033\020\034R\032\020\035\032\0020\036X\016¢\006\016\n\000\032\004\b\037\020 \"\004\b!\020\"R\032\020#\032\0020$X.¢\006\016\n\000\032\004\b%\020&\"\004\b'\020(R\032\020)\032\0020*X.¢\006\016\n\000\032\004\b+\020,\"\004\b-\020.R\032\020/\032\00200X.¢\006\016\n\000\032\004\b1\0202\"\004\b3\0204R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b5\0206¨\006:"}, d2={"Lokhttp3/internal/http2/Http2Connection$Builder;", "", "client", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "(ZLokhttp3/internal/concurrent/TaskRunner;)V", "getClient$okhttp", "()Z", "setClient$okhttp", "(Z)V", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "setConnectionName$okhttp", "(Ljava/lang/String;)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "setListener$okhttp", "(Lokhttp3/internal/http2/Http2Connection$Listener;)V", "pingIntervalMillis", "", "getPingIntervalMillis$okhttp", "()I", "setPingIntervalMillis$okhttp", "(I)V", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "getPushObserver$okhttp", "()Lokhttp3/internal/http2/PushObserver;", "setPushObserver$okhttp", "(Lokhttp3/internal/http2/PushObserver;)V", "sink", "Lokio/BufferedSink;", "getSink$okhttp", "()Lokio/BufferedSink;", "setSink$okhttp", "(Lokio/BufferedSink;)V", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "setSocket$okhttp", "(Ljava/net/Socket;)V", "source", "Lokio/BufferedSource;", "getSource$okhttp", "()Lokio/BufferedSource;", "setSource$okhttp", "(Lokio/BufferedSource;)V", "getTaskRunner$okhttp", "()Lokhttp3/internal/concurrent/TaskRunner;", "build", "Lokhttp3/internal/http2/Http2Connection;", "peerName", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Builder
  {
    private boolean client;
    public String connectionName;
    private Http2Connection.Listener listener;
    private int pingIntervalMillis;
    private PushObserver pushObserver;
    public BufferedSink sink;
    public Socket socket;
    public BufferedSource source;
    private final TaskRunner taskRunner;
    
    public Builder(boolean paramBoolean, TaskRunner paramTaskRunner)
    {
      this.client = paramBoolean;
      this.taskRunner = paramTaskRunner;
      this.listener = Http2Connection.Listener.REFUSE_INCOMING_STREAMS;
      this.pushObserver = PushObserver.CANCEL;
    }
    
    public final Http2Connection build()
    {
      return new Http2Connection(this);
    }
    
    public final boolean getClient$okhttp()
    {
      return this.client;
    }
    
    public final String getConnectionName$okhttp()
    {
      String str = this.connectionName;
      if (str == null) {
        Intrinsics.throwUninitializedPropertyAccessException("connectionName");
      }
      return str;
    }
    
    public final Http2Connection.Listener getListener$okhttp()
    {
      return this.listener;
    }
    
    public final int getPingIntervalMillis$okhttp()
    {
      return this.pingIntervalMillis;
    }
    
    public final PushObserver getPushObserver$okhttp()
    {
      return this.pushObserver;
    }
    
    public final BufferedSink getSink$okhttp()
    {
      BufferedSink localBufferedSink = this.sink;
      if (localBufferedSink == null) {
        Intrinsics.throwUninitializedPropertyAccessException("sink");
      }
      return localBufferedSink;
    }
    
    public final Socket getSocket$okhttp()
    {
      Socket localSocket = this.socket;
      if (localSocket == null) {
        Intrinsics.throwUninitializedPropertyAccessException("socket");
      }
      return localSocket;
    }
    
    public final BufferedSource getSource$okhttp()
    {
      BufferedSource localBufferedSource = this.source;
      if (localBufferedSource == null) {
        Intrinsics.throwUninitializedPropertyAccessException("source");
      }
      return localBufferedSource;
    }
    
    public final TaskRunner getTaskRunner$okhttp()
    {
      return this.taskRunner;
    }
    
    public final Builder listener(Http2Connection.Listener paramListener)
    {
      Intrinsics.checkNotNullParameter(paramListener, "listener");
      ((Builder)this).listener = paramListener;
      return (Builder)this;
    }
    
    public final Builder pingIntervalMillis(int paramInt)
    {
      ((Builder)this).pingIntervalMillis = paramInt;
      return (Builder)this;
    }
    
    public final Builder pushObserver(PushObserver paramPushObserver)
    {
      Intrinsics.checkNotNullParameter(paramPushObserver, "pushObserver");
      ((Builder)this).pushObserver = paramPushObserver;
      return (Builder)this;
    }
    
    public final void setClient$okhttp(boolean paramBoolean)
    {
      this.client = paramBoolean;
    }
    
    public final void setConnectionName$okhttp(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "<set-?>");
      this.connectionName = paramString;
    }
    
    public final void setListener$okhttp(Http2Connection.Listener paramListener)
    {
      Intrinsics.checkNotNullParameter(paramListener, "<set-?>");
      this.listener = paramListener;
    }
    
    public final void setPingIntervalMillis$okhttp(int paramInt)
    {
      this.pingIntervalMillis = paramInt;
    }
    
    public final void setPushObserver$okhttp(PushObserver paramPushObserver)
    {
      Intrinsics.checkNotNullParameter(paramPushObserver, "<set-?>");
      this.pushObserver = paramPushObserver;
    }
    
    public final void setSink$okhttp(BufferedSink paramBufferedSink)
    {
      Intrinsics.checkNotNullParameter(paramBufferedSink, "<set-?>");
      this.sink = paramBufferedSink;
    }
    
    public final void setSocket$okhttp(Socket paramSocket)
    {
      Intrinsics.checkNotNullParameter(paramSocket, "<set-?>");
      this.socket = paramSocket;
    }
    
    public final void setSource$okhttp(BufferedSource paramBufferedSource)
    {
      Intrinsics.checkNotNullParameter(paramBufferedSource, "<set-?>");
      this.source = paramBufferedSource;
    }
    
    public final Builder socket(Socket paramSocket)
      throws IOException
    {
      return socket$default(this, paramSocket, null, null, null, 14, null);
    }
    
    public final Builder socket(Socket paramSocket, String paramString)
      throws IOException
    {
      return socket$default(this, paramSocket, paramString, null, null, 12, null);
    }
    
    public final Builder socket(Socket paramSocket, String paramString, BufferedSource paramBufferedSource)
      throws IOException
    {
      return socket$default(this, paramSocket, paramString, paramBufferedSource, null, 8, null);
    }
    
    public final Builder socket(Socket paramSocket, String paramString, BufferedSource paramBufferedSource, BufferedSink paramBufferedSink)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramSocket, "socket");
      Intrinsics.checkNotNullParameter(paramString, "peerName");
      Intrinsics.checkNotNullParameter(paramBufferedSource, "source");
      Intrinsics.checkNotNullParameter(paramBufferedSink, "sink");
      Builder localBuilder = (Builder)this;
      localBuilder.socket = paramSocket;
      if (localBuilder.client) {
        paramSocket = Util.okHttpName + ' ' + paramString;
      } else {
        paramSocket = "MockWebServer " + paramString;
      }
      localBuilder.connectionName = paramSocket;
      localBuilder.source = paramBufferedSource;
      localBuilder.sink = paramBufferedSink;
      return (Builder)this;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\021\020\005\032\0020\006¢\006\b\n\000\032\004\b\007\020\bR\016\020\t\032\0020\004XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\016\020\013\032\0020\004XT¢\006\002\n\000R\016\020\f\032\0020\004XT¢\006\002\n\000¨\006\r"}, d2={"Lokhttp3/internal/http2/Http2Connection$Companion;", "", "()V", "AWAIT_PING", "", "DEFAULT_SETTINGS", "Lokhttp3/internal/http2/Settings;", "getDEFAULT_SETTINGS", "()Lokhttp3/internal/http2/Settings;", "DEGRADED_PING", "DEGRADED_PONG_TIMEOUT_NS", "INTERVAL_PING", "OKHTTP_CLIENT_WINDOW_SIZE", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final Settings getDEFAULT_SETTINGS()
    {
      return Http2Connection.access$getDEFAULT_SETTINGS$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\b&\030\000 \f2\0020\001:\001\fB\005¢\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\026J\020\020\t\032\0020\0042\006\020\n\032\0020\013H&¨\006\r"}, d2={"Lokhttp3/internal/http2/Http2Connection$Listener;", "", "()V", "onSettings", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "settings", "Lokhttp3/internal/http2/Settings;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
  public static abstract class Listener
  {
    public static final Companion Companion = new Companion(null);
    public static final Listener REFUSE_INCOMING_STREAMS = (Listener)new Http2Connection.Listener.Companion.REFUSE_INCOMING_STREAMS.1();
    
    public void onSettings(Http2Connection paramHttp2Connection, Settings paramSettings)
    {
      Intrinsics.checkNotNullParameter(paramHttp2Connection, "connection");
      Intrinsics.checkNotNullParameter(paramSettings, "settings");
    }
    
    public abstract void onStream(Http2Stream paramHttp2Stream)
      throws IOException;
    
    @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000¨\006\005"}, d2={"Lokhttp3/internal/http2/Http2Connection$Listener$Companion;", "", "()V", "REFUSE_INCOMING_STREAMS", "Lokhttp3/internal/http2/Http2Connection$Listener;", "okhttp"}, k=1, mv={1, 4, 0})
    public static final class Companion {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000`\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\000\n\002\030\002\n\002\b\006\n\002\020\b\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\n\002\020 \n\002\030\002\n\002\b\020\b\004\030\0002\0020\0012\b\022\004\022\0020\0030\002B\017\b\000\022\006\020\004\032\0020\005¢\006\002\020\006J\b\020\t\032\0020\003H\026J8\020\n\032\0020\0032\006\020\013\032\0020\f2\006\020\r\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\0162\006\020\022\032\0020\f2\006\020\023\032\0020\024H\026J\026\020\025\032\0020\0032\006\020\026\032\0020\0272\006\020\030\032\0020\031J(\020\032\032\0020\0032\006\020\033\032\0020\0272\006\020\013\032\0020\f2\006\020\034\032\0020\0352\006\020\036\032\0020\fH\026J \020\037\032\0020\0032\006\020 \032\0020\f2\006\020!\032\0020\"2\006\020#\032\0020\020H\026J.\020$\032\0020\0032\006\020\033\032\0020\0272\006\020\013\032\0020\f2\006\020%\032\0020\f2\f\020&\032\b\022\004\022\0020(0'H\026J\t\020)\032\0020\003H\002J \020*\032\0020\0032\006\020+\032\0020\0272\006\020,\032\0020\f2\006\020-\032\0020\fH\026J(\020.\032\0020\0032\006\020\013\032\0020\f2\006\020/\032\0020\f2\006\0200\032\0020\f2\006\0201\032\0020\027H\026J&\0202\032\0020\0032\006\020\013\032\0020\f2\006\0203\032\0020\f2\f\0204\032\b\022\004\022\0020(0'H\026J\030\0205\032\0020\0032\006\020\013\032\0020\f2\006\020!\032\0020\"H\026J\030\020\030\032\0020\0032\006\020\026\032\0020\0272\006\020\030\032\0020\031H\026J\030\0206\032\0020\0032\006\020\013\032\0020\f2\006\0207\032\0020\024H\026R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\007\020\b¨\0068"}, d2={"Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "Lokhttp3/internal/http2/Http2Reader$Handler;", "Lkotlin/Function0;", "", "reader", "Lokhttp3/internal/http2/Http2Reader;", "(Lokhttp3/internal/http2/Http2Connection;Lokhttp3/internal/http2/Http2Reader;)V", "getReader$okhttp", "()Lokhttp3/internal/http2/Http2Reader;", "ackSettings", "alternateService", "streamId", "", "origin", "", "protocol", "Lokio/ByteString;", "host", "port", "maxAge", "", "applyAndAckSettings", "clearPrevious", "", "settings", "Lokhttp3/internal/http2/Settings;", "data", "inFinished", "source", "Lokio/BufferedSource;", "length", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "headers", "associatedStreamId", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "invoke", "ping", "ack", "payload1", "payload2", "priority", "streamDependency", "weight", "exclusive", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "windowUpdate", "windowSizeIncrement", "okhttp"}, k=1, mv={1, 4, 0})
  public final class ReaderRunnable
    implements Http2Reader.Handler, Function0<Unit>
  {
    private final Http2Reader reader;
    
    public ReaderRunnable()
    {
      this.reader = ((Http2Reader)localObject);
    }
    
    public void ackSettings() {}
    
    public void alternateService(int paramInt1, String paramString1, ByteString paramByteString, String paramString2, int paramInt2, long paramLong)
    {
      Intrinsics.checkNotNullParameter(paramString1, "origin");
      Intrinsics.checkNotNullParameter(paramByteString, "protocol");
      Intrinsics.checkNotNullParameter(paramString2, "host");
    }
    
    /* Error */
    public final void applyAndAckSettings(boolean paramBoolean, Settings paramSettings)
    {
      // Byte code:
      //   0: aload_2
      //   1: ldc 111
      //   3: invokestatic 94	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
      //   6: new 113	kotlin/jvm/internal/Ref$LongRef
      //   9: dup
      //   10: invokespecial 114	kotlin/jvm/internal/Ref$LongRef:<init>	()V
      //   13: astore 8
      //   15: new 116	kotlin/jvm/internal/Ref$ObjectRef
      //   18: dup
      //   19: invokespecial 117	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
      //   22: astore 11
      //   24: new 116	kotlin/jvm/internal/Ref$ObjectRef
      //   27: dup
      //   28: invokespecial 117	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
      //   31: astore 12
      //   33: aload_0
      //   34: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   37: invokevirtual 121	okhttp3/internal/http2/Http2Connection:getWriter	()Lokhttp3/internal/http2/Http2Writer;
      //   40: astore 9
      //   42: aload 9
      //   44: monitorenter
      //   45: aload_0
      //   46: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   49: astore 10
      //   51: aload 10
      //   53: monitorenter
      //   54: aload_0
      //   55: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   58: invokevirtual 125	okhttp3/internal/http2/Http2Connection:getPeerSettings	()Lokhttp3/internal/http2/Settings;
      //   61: astore 13
      //   63: iload_1
      //   64: ifeq +9 -> 73
      //   67: aload_2
      //   68: astore 7
      //   70: goto +31 -> 101
      //   73: new 127	okhttp3/internal/http2/Settings
      //   76: astore 7
      //   78: aload 7
      //   80: invokespecial 128	okhttp3/internal/http2/Settings:<init>	()V
      //   83: aload 7
      //   85: aload 13
      //   87: invokevirtual 132	okhttp3/internal/http2/Settings:merge	(Lokhttp3/internal/http2/Settings;)V
      //   90: aload 7
      //   92: aload_2
      //   93: invokevirtual 132	okhttp3/internal/http2/Settings:merge	(Lokhttp3/internal/http2/Settings;)V
      //   96: getstatic 138	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   99: astore 14
      //   101: aload 12
      //   103: aload 7
      //   105: putfield 142	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
      //   108: aload 8
      //   110: aload 12
      //   112: getfield 142	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
      //   115: checkcast 127	okhttp3/internal/http2/Settings
      //   118: invokevirtual 146	okhttp3/internal/http2/Settings:getInitialWindowSize	()I
      //   121: i2l
      //   122: aload 13
      //   124: invokevirtual 146	okhttp3/internal/http2/Settings:getInitialWindowSize	()I
      //   127: i2l
      //   128: lsub
      //   129: putfield 149	kotlin/jvm/internal/Ref$LongRef:element	J
      //   132: aload 8
      //   134: getfield 149	kotlin/jvm/internal/Ref$LongRef:element	J
      //   137: lstore 5
      //   139: lload 5
      //   141: lconst_0
      //   142: lcmp
      //   143: ifeq +75 -> 218
      //   146: aload_0
      //   147: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   150: invokevirtual 153	okhttp3/internal/http2/Http2Connection:getStreams$okhttp	()Ljava/util/Map;
      //   153: invokeinterface 159 1 0
      //   158: ifeq +6 -> 164
      //   161: goto +57 -> 218
      //   164: aload_0
      //   165: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   168: invokevirtual 153	okhttp3/internal/http2/Http2Connection:getStreams$okhttp	()Ljava/util/Map;
      //   171: invokeinterface 163 1 0
      //   176: iconst_0
      //   177: anewarray 165	okhttp3/internal/http2/Http2Stream
      //   180: invokeinterface 171 2 0
      //   185: astore 7
      //   187: aload 7
      //   189: ifnull +13 -> 202
      //   192: aload 7
      //   194: checkcast 173	[Lokhttp3/internal/http2/Http2Stream;
      //   197: astore 7
      //   199: goto +22 -> 221
      //   202: new 175	java/lang/NullPointerException
      //   205: astore_2
      //   206: aload_2
      //   207: ldc -79
      //   209: invokespecial 180	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   212: aload_2
      //   213: athrow
      //   214: astore_2
      //   215: goto +250 -> 465
      //   218: aconst_null
      //   219: astore 7
      //   221: aload 11
      //   223: aload 7
      //   225: putfield 142	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
      //   228: aload_0
      //   229: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   232: aload 12
      //   234: getfield 142	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
      //   237: checkcast 127	okhttp3/internal/http2/Settings
      //   240: invokevirtual 183	okhttp3/internal/http2/Http2Connection:setPeerSettings	(Lokhttp3/internal/http2/Settings;)V
      //   243: aload_0
      //   244: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   247: invokestatic 187	okhttp3/internal/http2/Http2Connection:access$getSettingsListenerQueue$p	(Lokhttp3/internal/http2/Http2Connection;)Lokhttp3/internal/concurrent/TaskQueue;
      //   250: astore 13
      //   252: new 189	java/lang/StringBuilder
      //   255: astore 7
      //   257: aload 7
      //   259: invokespecial 190	java/lang/StringBuilder:<init>	()V
      //   262: aload 7
      //   264: aload_0
      //   265: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   268: invokevirtual 194	okhttp3/internal/http2/Http2Connection:getConnectionName$okhttp	()Ljava/lang/String;
      //   271: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   274: ldc -56
      //   276: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   279: invokevirtual 203	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   282: astore 14
      //   284: new 14	okhttp3/internal/http2/Http2Connection$ReaderRunnable$applyAndAckSettings$$inlined$synchronized$lambda$1
      //   287: astore 15
      //   289: aload 8
      //   291: astore 7
      //   293: aload 15
      //   295: aload 14
      //   297: iconst_1
      //   298: aload 14
      //   300: iconst_1
      //   301: aload_0
      //   302: aload 12
      //   304: iload_1
      //   305: aload_2
      //   306: aload 8
      //   308: aload 11
      //   310: invokespecial 206	okhttp3/internal/http2/Http2Connection$ReaderRunnable$applyAndAckSettings$$inlined$synchronized$lambda$1:<init>	(Ljava/lang/String;ZLjava/lang/String;ZLokhttp3/internal/http2/Http2Connection$ReaderRunnable;Lkotlin/jvm/internal/Ref$ObjectRef;ZLokhttp3/internal/http2/Settings;Lkotlin/jvm/internal/Ref$LongRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      //   313: aload 15
      //   315: checkcast 208	okhttp3/internal/concurrent/Task
      //   318: astore_2
      //   319: aload 13
      //   321: aload_2
      //   322: lconst_0
      //   323: invokevirtual 214	okhttp3/internal/concurrent/TaskQueue:schedule	(Lokhttp3/internal/concurrent/Task;J)V
      //   326: getstatic 138	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   329: astore_2
      //   330: aload 10
      //   332: monitorexit
      //   333: aload_0
      //   334: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   337: invokevirtual 121	okhttp3/internal/http2/Http2Connection:getWriter	()Lokhttp3/internal/http2/Http2Writer;
      //   340: aload 12
      //   342: getfield 142	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
      //   345: checkcast 127	okhttp3/internal/http2/Settings
      //   348: invokevirtual 218	okhttp3/internal/http2/Http2Writer:applyAndAckSettings	(Lokhttp3/internal/http2/Settings;)V
      //   351: goto +16 -> 367
      //   354: astore_2
      //   355: goto +120 -> 475
      //   358: astore_2
      //   359: aload_0
      //   360: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   363: aload_2
      //   364: invokestatic 222	okhttp3/internal/http2/Http2Connection:access$failConnection	(Lokhttp3/internal/http2/Http2Connection;Ljava/io/IOException;)V
      //   367: getstatic 138	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   370: astore_2
      //   371: aload 9
      //   373: monitorexit
      //   374: aload 11
      //   376: getfield 142	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
      //   379: checkcast 173	[Lokhttp3/internal/http2/Http2Stream;
      //   382: ifnull +69 -> 451
      //   385: aload 11
      //   387: getfield 142	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
      //   390: checkcast 173	[Lokhttp3/internal/http2/Http2Stream;
      //   393: astore_2
      //   394: aload_2
      //   395: invokestatic 226	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
      //   398: aload_2
      //   399: arraylength
      //   400: istore 4
      //   402: iconst_0
      //   403: istore_3
      //   404: iload_3
      //   405: iload 4
      //   407: if_icmpge +41 -> 448
      //   410: aload_2
      //   411: iload_3
      //   412: aaload
      //   413: astore 8
      //   415: aload 8
      //   417: monitorenter
      //   418: aload 8
      //   420: aload 7
      //   422: getfield 149	kotlin/jvm/internal/Ref$LongRef:element	J
      //   425: invokevirtual 230	okhttp3/internal/http2/Http2Stream:addBytesToWriteWindow	(J)V
      //   428: getstatic 138	kotlin/Unit:INSTANCE	Lkotlin/Unit;
      //   431: astore 9
      //   433: aload 8
      //   435: monitorexit
      //   436: iinc 3 1
      //   439: goto -35 -> 404
      //   442: astore_2
      //   443: aload 8
      //   445: monitorexit
      //   446: aload_2
      //   447: athrow
      //   448: goto +3 -> 451
      //   451: return
      //   452: astore_2
      //   453: goto +4 -> 457
      //   456: astore_2
      //   457: goto +18 -> 475
      //   460: astore_2
      //   461: goto +4 -> 465
      //   464: astore_2
      //   465: aload 10
      //   467: monitorexit
      //   468: aload_2
      //   469: athrow
      //   470: astore_2
      //   471: goto +4 -> 475
      //   474: astore_2
      //   475: aload 9
      //   477: monitorexit
      //   478: aload_2
      //   479: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	480	0	this	ReaderRunnable
      //   0	480	1	paramBoolean	boolean
      //   0	480	2	paramSettings	Settings
      //   403	34	3	i	int
      //   400	8	4	j	int
      //   137	3	5	l	long
      //   68	353	7	localObject1	Object
      //   13	431	8	localLongRef	Ref.LongRef
      //   40	436	9	localObject2	Object
      //   49	417	10	localHttp2Connection	Http2Connection
      //   22	364	11	localObjectRef1	Ref.ObjectRef
      //   31	310	12	localObjectRef2	Ref.ObjectRef
      //   61	259	13	localObject3	Object
      //   99	200	14	localObject4	Object
      //   287	27	15	local1	applyAndAckSettings..inlined.synchronized.lambda.1
      // Exception table:
      //   from	to	target	type
      //   146	161	214	finally
      //   164	187	214	finally
      //   192	199	214	finally
      //   202	214	214	finally
      //   333	351	354	finally
      //   333	351	358	java/io/IOException
      //   418	433	442	finally
      //   359	367	452	finally
      //   367	371	452	finally
      //   330	333	456	finally
      //   293	319	460	finally
      //   319	326	460	finally
      //   326	330	460	finally
      //   54	63	464	finally
      //   73	101	464	finally
      //   101	132	464	finally
      //   132	139	464	finally
      //   221	289	464	finally
      //   465	470	470	finally
      //   45	54	474	finally
    }
    
    public void data(boolean paramBoolean, int paramInt1, BufferedSource paramBufferedSource, int paramInt2)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramBufferedSource, "source");
      if (Http2Connection.this.pushedStream$okhttp(paramInt1))
      {
        Http2Connection.this.pushDataLater$okhttp(paramInt1, paramBufferedSource, paramInt2, paramBoolean);
        return;
      }
      Http2Stream localHttp2Stream = Http2Connection.this.getStream(paramInt1);
      if (localHttp2Stream == null)
      {
        Http2Connection.this.writeSynResetLater$okhttp(paramInt1, ErrorCode.PROTOCOL_ERROR);
        Http2Connection.this.updateConnectionFlowControl$okhttp(paramInt2);
        paramBufferedSource.skip(paramInt2);
        return;
      }
      localHttp2Stream.receiveData(paramBufferedSource, paramInt2);
      if (paramBoolean) {
        localHttp2Stream.receiveHeaders(Util.EMPTY_HEADERS, true);
      }
    }
    
    public final Http2Reader getReader$okhttp()
    {
      return this.reader;
    }
    
    public void goAway(int paramInt, ErrorCode arg2, ByteString paramByteString)
    {
      Intrinsics.checkNotNullParameter(???, "errorCode");
      Intrinsics.checkNotNullParameter(paramByteString, "debugData");
      paramByteString.size();
      synchronized (Http2Connection.this)
      {
        paramByteString = Http2Connection.this.getStreams$okhttp().values();
        int i = 0;
        paramByteString = paramByteString.toArray(new Http2Stream[0]);
        if (paramByteString != null)
        {
          paramByteString = (Http2Stream[])paramByteString;
          Http2Connection.access$setShutdown$p(Http2Connection.this, true);
          Unit localUnit = Unit.INSTANCE;
          int j = paramByteString.length;
          while (i < j)
          {
            ??? = paramByteString[i];
            if ((???.getId() > paramInt) && (???.isLocallyInitiated()))
            {
              ???.receiveRstStream(ErrorCode.REFUSED_STREAM);
              Http2Connection.this.removeStream$okhttp(???.getId());
            }
            i++;
          }
          return;
        }
        paramByteString = new java/lang/NullPointerException;
        paramByteString.<init>("null cannot be cast to non-null type kotlin.Array<T>");
        throw paramByteString;
      }
    }
    
    public void headers(boolean paramBoolean, int paramInt1, int paramInt2, List<Header> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "headerBlock");
      if (Http2Connection.this.pushedStream$okhttp(paramInt1))
      {
        Http2Connection.this.pushHeadersLater$okhttp(paramInt1, paramList, paramBoolean);
        return;
      }
      Http2Stream localHttp2Stream;
      synchronized (Http2Connection.this)
      {
        localHttp2Stream = Http2Connection.this.getStream(paramInt1);
        if (localHttp2Stream == null) {
          try
          {
            boolean bool = Http2Connection.access$isShutdown$p(Http2Connection.this);
            if (bool) {
              return;
            }
            paramInt2 = Http2Connection.this.getLastGoodStreamId$okhttp();
            if (paramInt1 <= paramInt2) {
              return;
            }
            paramInt2 = Http2Connection.this.getNextStreamId$okhttp();
            if (paramInt1 % 2 == paramInt2 % 2) {
              return;
            }
            Object localObject2 = Util.toHeaders(paramList);
            Object localObject1 = new okhttp3/internal/http2/Http2Stream;
            ((Http2Stream)localObject1).<init>(paramInt1, Http2Connection.this, false, paramBoolean, (Headers)localObject2);
            Http2Connection.this.setLastGoodStreamId$okhttp(paramInt1);
            Http2Connection.this.getStreams$okhttp().put(Integer.valueOf(paramInt1), localObject1);
            localObject2 = Http2Connection.access$getTaskRunner$p(Http2Connection.this).newQueue();
            Object localObject3 = new java/lang/StringBuilder;
            ((StringBuilder)localObject3).<init>();
            localObject3 = Http2Connection.this.getConnectionName$okhttp() + '[' + paramInt1 + "] onStream";
            Task local1 = new okhttp3/internal/http2/Http2Connection$ReaderRunnable$headers$$inlined$synchronized$lambda$1;
            try
            {
              local1.<init>((String)localObject3, true, (String)localObject3, true, (Http2Stream)localObject1, this, localHttp2Stream, paramInt1, paramList, paramBoolean);
              paramList = (Task)local1;
              ((TaskQueue)localObject2).schedule(paramList, 0L);
              return;
            }
            finally {}
            try
            {
              localObject1 = Unit.INSTANCE;
              localHttp2Stream.receiveHeaders(Util.toHeaders(paramList), paramBoolean);
              return;
            }
            finally {}
          }
          finally {}
        }
      }
    }
    
    /* Error */
    public void invoke()
    {
      // Byte code:
      //   0: getstatic 369	okhttp3/internal/http2/ErrorCode:INTERNAL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   3: astore_3
      //   4: getstatic 369	okhttp3/internal/http2/ErrorCode:INTERNAL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   7: astore 7
      //   9: aconst_null
      //   10: checkcast 110	java/io/IOException
      //   13: astore 5
      //   15: aload_3
      //   16: astore_1
      //   17: aload 5
      //   19: astore 4
      //   21: aload_3
      //   22: astore_2
      //   23: aload_0
      //   24: getfield 101	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   27: aload_0
      //   28: checkcast 7	okhttp3/internal/http2/Http2Reader$Handler
      //   31: invokevirtual 375	okhttp3/internal/http2/Http2Reader:readConnectionPreface	(Lokhttp3/internal/http2/Http2Reader$Handler;)V
      //   34: aload_3
      //   35: astore_1
      //   36: aload 5
      //   38: astore 4
      //   40: aload_3
      //   41: astore_2
      //   42: aload_0
      //   43: getfield 101	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   46: iconst_0
      //   47: aload_0
      //   48: checkcast 7	okhttp3/internal/http2/Http2Reader$Handler
      //   51: invokevirtual 379	okhttp3/internal/http2/Http2Reader:nextFrame	(ZLokhttp3/internal/http2/Http2Reader$Handler;)Z
      //   54: ifeq +6 -> 60
      //   57: goto -23 -> 34
      //   60: aload_3
      //   61: astore_1
      //   62: aload 5
      //   64: astore 4
      //   66: aload_3
      //   67: astore_2
      //   68: getstatic 382	okhttp3/internal/http2/ErrorCode:NO_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   71: astore_3
      //   72: aload_3
      //   73: astore_1
      //   74: aload 5
      //   76: astore 4
      //   78: aload_3
      //   79: astore_2
      //   80: getstatic 385	okhttp3/internal/http2/ErrorCode:CANCEL	Lokhttp3/internal/http2/ErrorCode;
      //   83: astore 6
      //   85: aload 6
      //   87: astore_1
      //   88: aload_3
      //   89: astore_2
      //   90: aload 5
      //   92: astore_3
      //   93: goto +30 -> 123
      //   96: astore_2
      //   97: goto +47 -> 144
      //   100: astore_3
      //   101: aload_2
      //   102: astore_1
      //   103: aload_3
      //   104: astore 4
      //   106: getstatic 249	okhttp3/internal/http2/ErrorCode:PROTOCOL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   109: astore_2
      //   110: aload_2
      //   111: astore_1
      //   112: aload_3
      //   113: astore 4
      //   115: getstatic 249	okhttp3/internal/http2/ErrorCode:PROTOCOL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   118: astore 5
      //   120: aload 5
      //   122: astore_1
      //   123: aload_0
      //   124: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   127: aload_2
      //   128: aload_1
      //   129: aload_3
      //   130: invokevirtual 389	okhttp3/internal/http2/Http2Connection:close$okhttp	(Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;Ljava/io/IOException;)V
      //   133: aload_0
      //   134: getfield 101	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   137: checkcast 391	java/io/Closeable
      //   140: invokestatic 395	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   143: return
      //   144: aload_0
      //   145: getfield 96	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   148: aload_1
      //   149: aload 7
      //   151: aload 4
      //   153: invokevirtual 389	okhttp3/internal/http2/Http2Connection:close$okhttp	(Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;Ljava/io/IOException;)V
      //   156: aload_0
      //   157: getfield 101	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   160: checkcast 391	java/io/Closeable
      //   163: invokestatic 395	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   166: aload_2
      //   167: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	168	0	this	ReaderRunnable
      //   16	133	1	localObject1	Object
      //   22	68	2	localObject2	Object
      //   96	6	2	localObject3	Object
      //   109	58	2	localErrorCode1	ErrorCode
      //   3	90	3	localObject4	Object
      //   100	30	3	localIOException	IOException
      //   19	133	4	localObject5	Object
      //   13	108	5	localObject6	Object
      //   83	3	6	localErrorCode2	ErrorCode
      //   7	143	7	localErrorCode3	ErrorCode
      // Exception table:
      //   from	to	target	type
      //   23	34	96	finally
      //   42	57	96	finally
      //   68	72	96	finally
      //   80	85	96	finally
      //   106	110	96	finally
      //   115	120	96	finally
      //   23	34	100	java/io/IOException
      //   42	57	100	java/io/IOException
      //   68	72	100	java/io/IOException
      //   80	85	100	java/io/IOException
    }
    
    public void ping(boolean paramBoolean, final int paramInt1, final int paramInt2)
    {
      Object localObject1;
      if (paramBoolean)
      {
        localObject1 = Http2Connection.this;
        switch (paramInt1)
        {
        }
        try
        {
          localObject2 = Unit.INSTANCE;
        }
        finally
        {
          Object localObject2;
          long l;
          break label170;
        }
        localObject2 = Http2Connection.this;
        Http2Connection.access$setAwaitPongsReceived$p((Http2Connection)localObject2, Http2Connection.access$getAwaitPongsReceived$p((Http2Connection)localObject2) + 1L);
        localObject2 = Http2Connection.this;
        if (localObject2 != null)
        {
          ((Object)localObject2).notifyAll();
          localObject2 = Unit.INSTANCE;
        }
        else
        {
          localObject2 = new java/lang/NullPointerException;
          ((NullPointerException)localObject2).<init>("null cannot be cast to non-null type java.lang.Object");
          throw ((Throwable)localObject2);
          localObject2 = Http2Connection.this;
          l = Http2Connection.access$getDegradedPongsReceived$p((Http2Connection)localObject2);
          Http2Connection.access$setDegradedPongsReceived$p((Http2Connection)localObject2, 1L + l);
          break label164;
          localObject2 = Http2Connection.this;
          l = Http2Connection.access$getIntervalPongsReceived$p((Http2Connection)localObject2);
          Http2Connection.access$setIntervalPongsReceived$p((Http2Connection)localObject2, 1L + l);
        }
        label164:
        return;
        label170:
        throw ((Throwable)localObject3);
      }
      else
      {
        localObject1 = Http2Connection.access$getWriterQueue$p(Http2Connection.this);
        Object localObject4 = Http2Connection.this.getConnectionName$okhttp() + " ping";
        localObject4 = (Task)new Task((String)localObject4, true)
        {
          final String $name;
          
          public long runOnce()
          {
            jdField_this.this$0.writePing(true, paramInt1, paramInt2);
            return -1L;
          }
        };
        ((TaskQueue)localObject1).schedule((Task)localObject4, 0L);
      }
    }
    
    public void priority(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {}
    
    public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
    {
      Intrinsics.checkNotNullParameter(paramList, "requestHeaders");
      Http2Connection.this.pushRequestLater$okhttp(paramInt2, paramList);
    }
    
    public void rstStream(int paramInt, ErrorCode paramErrorCode)
    {
      Intrinsics.checkNotNullParameter(paramErrorCode, "errorCode");
      if (Http2Connection.this.pushedStream$okhttp(paramInt))
      {
        Http2Connection.this.pushResetLater$okhttp(paramInt, paramErrorCode);
        return;
      }
      Http2Stream localHttp2Stream = Http2Connection.this.removeStream$okhttp(paramInt);
      if (localHttp2Stream != null) {
        localHttp2Stream.receiveRstStream(paramErrorCode);
      }
    }
    
    public void settings(final boolean paramBoolean, final Settings paramSettings)
    {
      Intrinsics.checkNotNullParameter(paramSettings, "settings");
      TaskQueue localTaskQueue = Http2Connection.access$getWriterQueue$p(Http2Connection.this);
      String str = Http2Connection.this.getConnectionName$okhttp() + " applyAndAckSettings";
      paramSettings = (Task)new Task(str, true)
      {
        final String $name;
        
        public long runOnce()
        {
          jdField_this.applyAndAckSettings(paramBoolean, paramSettings);
          return -1L;
        }
      };
      localTaskQueue.schedule(paramSettings, 0L);
    }
    
    public void windowUpdate(int paramInt, long paramLong)
    {
      if (paramInt == 0) {
        synchronized (Http2Connection.this)
        {
          Object localObject2 = Http2Connection.this;
          Http2Connection.access$setWriteBytesMaximum$p((Http2Connection)localObject2, ((Http2Connection)localObject2).getWriteBytesMaximum() + paramLong);
          localObject2 = Http2Connection.this;
          if (localObject2 != null)
          {
            ((Object)localObject2).notifyAll();
            localObject2 = Unit.INSTANCE;
            return;
          }
          localObject2 = new java/lang/NullPointerException;
          ((NullPointerException)localObject2).<init>("null cannot be cast to non-null type java.lang.Object");
          throw ((Throwable)localObject2);
        }
      }
      ??? = Http2Connection.this.getStream(paramInt);
      if (??? != null) {
        try
        {
          ((Http2Stream)???).addBytesToWriteWindow(paramLong);
          Unit localUnit = Unit.INSTANCE;
        }
        finally
        {
          localObject4 = finally;
          throw ((Throwable)localObject4);
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http2/Http2Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */