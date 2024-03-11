package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ByteString.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000¶\001\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\030\002\n\002\b\003\n\002\020\016\n\002\b\003\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\034\030\000 `2\0020\0012\0020\002:\005_`abcB?\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006\022\006\020\007\032\0020\b\022\006\020\t\032\0020\n\022\006\020\013\032\0020\f\022\b\020\r\032\004\030\0010\016\022\006\020\017\032\0020\f¢\006\002\020\020J\026\0202\032\002032\006\0204\032\0020\f2\006\0205\032\00206J\b\0207\032\00203H\026J\037\0208\032\002032\006\0209\032\0020:2\b\020;\032\004\030\0010<H\000¢\006\002\b=J\032\020>\032\0020\0222\006\020?\032\0020%2\b\020@\032\004\030\0010\030H\026J \020>\032\0020\0222\006\020?\032\0020%2\b\020@\032\004\030\0010\0302\006\020A\032\0020\fJ\016\020B\032\002032\006\020C\032\0020DJ\034\020E\032\002032\n\020F\032\0060Gj\002`H2\b\0209\032\004\030\0010:J\026\020I\032\002032\006\020\036\032\0020\0302\006\020*\032\0020+J\006\020J\032\00203J\030\020K\032\002032\006\020?\032\0020%2\006\020@\032\0020\030H\026J\020\020L\032\002032\006\020M\032\0020\030H\026J\020\020L\032\002032\006\020N\032\0020 H\026J\020\020O\032\002032\006\020P\032\0020 H\026J\020\020Q\032\002032\006\020P\032\0020 H\026J\016\020R\032\0020\0222\006\020P\032\0020 J\006\020S\032\0020\022J\b\020!\032\0020\fH\026J\006\020'\032\0020%J\006\020(\032\0020%J\b\020T\032\0020\006H\026J\b\020U\032\00203H\002J\020\020V\032\0020\0222\006\020M\032\0020\030H\026J\020\020V\032\0020\0222\006\020N\032\0020 H\026J\030\020V\032\0020\0222\006\020W\032\0020 2\006\020X\032\0020%H\002J\006\020)\032\0020%J\006\020Y\032\00203J\r\020Z\032\0020\022H\000¢\006\002\b[J\r\020\\\032\00203H\000¢\006\002\b]J\f\020^\032\0020\022*\0020\016H\002R\016\020\021\032\0020\022X\016¢\006\002\n\000R\020\020\023\032\004\030\0010\024X\016¢\006\002\n\000R\016\020\025\032\0020\022X\016¢\006\002\n\000R\020\020\r\032\004\030\0010\016X\016¢\006\002\n\000R\016\020\026\032\0020\022X\016¢\006\002\n\000R\016\020\027\032\0020\030X\004¢\006\002\n\000R\024\020\007\032\0020\bX\004¢\006\b\n\000\032\004\b\031\020\032R\024\020\033\032\b\022\004\022\0020\0350\034X\004¢\006\002\n\000R\016\020\017\032\0020\fX\016¢\006\002\n\000R\020\020\036\032\004\030\0010\030X\016¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000R\016\020\013\032\0020\fX\004¢\006\002\n\000R\024\020\037\032\b\022\004\022\0020 0\034X\004¢\006\002\n\000R\016\020!\032\0020\fX\016¢\006\002\n\000R\016\020\t\032\0020\nX\004¢\006\002\n\000R\020\020\"\032\004\030\0010#X\016¢\006\002\n\000R\016\020$\032\0020%X\016¢\006\002\n\000R\020\020&\032\004\030\0010\030X\016¢\006\002\n\000R\016\020'\032\0020%X\016¢\006\002\n\000R\016\020(\032\0020%X\016¢\006\002\n\000R\016\020)\032\0020%X\016¢\006\002\n\000R\020\020*\032\004\030\0010+X\016¢\006\002\n\000R\016\020,\032\0020-X\016¢\006\002\n\000R\020\020.\032\004\030\0010/X\016¢\006\002\n\000R\020\0200\032\004\030\00101X\016¢\006\002\n\000¨\006d"}, d2={"Lokhttp3/internal/ws/RealWebSocket;", "Lokhttp3/WebSocket;", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "originalRequest", "Lokhttp3/Request;", "listener", "Lokhttp3/WebSocketListener;", "random", "Ljava/util/Random;", "pingIntervalMillis", "", "extensions", "Lokhttp3/internal/ws/WebSocketExtensions;", "minimumDeflateSize", "(Lokhttp3/internal/concurrent/TaskRunner;Lokhttp3/Request;Lokhttp3/WebSocketListener;Ljava/util/Random;JLokhttp3/internal/ws/WebSocketExtensions;J)V", "awaitingPong", "", "call", "Lokhttp3/Call;", "enqueuedClose", "failed", "key", "", "getListener$okhttp", "()Lokhttp3/WebSocketListener;", "messageAndCloseQueue", "Ljava/util/ArrayDeque;", "", "name", "pongQueue", "Lokio/ByteString;", "queueSize", "reader", "Lokhttp3/internal/ws/WebSocketReader;", "receivedCloseCode", "", "receivedCloseReason", "receivedPingCount", "receivedPongCount", "sentPingCount", "streams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "taskQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "writer", "Lokhttp3/internal/ws/WebSocketWriter;", "writerTask", "Lokhttp3/internal/concurrent/Task;", "awaitTermination", "", "timeout", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "cancel", "checkUpgradeSuccess", "response", "Lokhttp3/Response;", "exchange", "Lokhttp3/internal/connection/Exchange;", "checkUpgradeSuccess$okhttp", "close", "code", "reason", "cancelAfterCloseMillis", "connect", "client", "Lokhttp3/OkHttpClient;", "failWebSocket", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "initReaderAndWriter", "loopReader", "onReadClose", "onReadMessage", "text", "bytes", "onReadPing", "payload", "onReadPong", "pong", "processNextFrame", "request", "runWriter", "send", "data", "formatOpcode", "tearDown", "writeOneFrame", "writeOneFrame$okhttp", "writePingFrame", "writePingFrame$okhttp", "isValid", "Close", "Companion", "Message", "Streams", "WriterTask", "okhttp"}, k=1, mv={1, 4, 0})
public final class RealWebSocket
  implements WebSocket, WebSocketReader.FrameCallback
{
  private static final long CANCEL_AFTER_CLOSE_MILLIS = 60000L;
  public static final Companion Companion = new Companion(null);
  public static final long DEFAULT_MINIMUM_DEFLATE_SIZE = 1024L;
  private static final long MAX_QUEUE_SIZE = 16777216L;
  private static final List<Protocol> ONLY_HTTP1 = CollectionsKt.listOf(Protocol.HTTP_1_1);
  private boolean awaitingPong;
  private Call call;
  private boolean enqueuedClose;
  private WebSocketExtensions extensions;
  private boolean failed;
  private final String key;
  private final WebSocketListener listener;
  private final ArrayDeque<Object> messageAndCloseQueue;
  private long minimumDeflateSize;
  private String name;
  private final Request originalRequest;
  private final long pingIntervalMillis;
  private final ArrayDeque<ByteString> pongQueue;
  private long queueSize;
  private final Random random;
  private WebSocketReader reader;
  private int receivedCloseCode;
  private String receivedCloseReason;
  private int receivedPingCount;
  private int receivedPongCount;
  private int sentPingCount;
  private Streams streams;
  private TaskQueue taskQueue;
  private WebSocketWriter writer;
  private Task writerTask;
  
  public RealWebSocket(TaskRunner paramTaskRunner, Request paramRequest, WebSocketListener paramWebSocketListener, Random paramRandom, long paramLong1, WebSocketExtensions paramWebSocketExtensions, long paramLong2)
  {
    this.originalRequest = paramRequest;
    this.listener = paramWebSocketListener;
    this.random = paramRandom;
    this.pingIntervalMillis = paramLong1;
    this.extensions = paramWebSocketExtensions;
    this.minimumDeflateSize = paramLong2;
    this.taskQueue = paramTaskRunner.newQueue();
    this.pongQueue = new ArrayDeque();
    this.messageAndCloseQueue = new ArrayDeque();
    this.receivedCloseCode = -1;
    if (Intrinsics.areEqual("GET", paramRequest.method()))
    {
      paramRequest = ByteString.Companion;
      paramTaskRunner = new byte[16];
      paramRandom.nextBytes(paramTaskRunner);
      paramWebSocketListener = Unit.INSTANCE;
      this.key = ByteString.Companion.of$default(paramRequest, paramTaskRunner, 0, 0, 3, null).base64();
      return;
    }
    throw ((Throwable)new IllegalArgumentException(("Request must be GET: " + paramRequest.method()).toString()));
  }
  
  private final boolean isValid(WebSocketExtensions paramWebSocketExtensions)
  {
    if (paramWebSocketExtensions.unknownValues) {
      return false;
    }
    if (paramWebSocketExtensions.clientMaxWindowBits != null) {
      return false;
    }
    if (paramWebSocketExtensions.serverMaxWindowBits != null)
    {
      int i = paramWebSocketExtensions.serverMaxWindowBits.intValue();
      if ((8 > i) || (15 < i)) {
        return false;
      }
    }
    return true;
  }
  
  private final void runWriter()
  {
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      localObject = new StringBuilder().append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(localThread.getName() + " MUST hold lock on " + this));
    }
    Object localObject = this.writerTask;
    if (localObject != null) {
      TaskQueue.schedule$default(this.taskQueue, (Task)localObject, 0L, 2, null);
    }
  }
  
  private final boolean send(ByteString paramByteString, int paramInt)
  {
    try
    {
      if ((!this.failed) && (!this.enqueuedClose))
      {
        if (this.queueSize + paramByteString.size() > 16777216L)
        {
          close(1001, null);
          return false;
        }
        this.queueSize += paramByteString.size();
        ArrayDeque localArrayDeque = this.messageAndCloseQueue;
        Message localMessage = new okhttp3/internal/ws/RealWebSocket$Message;
        localMessage.<init>(paramInt, paramByteString);
        localArrayDeque.add(localMessage);
        runWriter();
        return true;
      }
      return false;
    }
    finally {}
  }
  
  public final void awaitTermination(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    Intrinsics.checkNotNullParameter(paramTimeUnit, "timeUnit");
    this.taskQueue.idleLatch().await(paramLong, paramTimeUnit);
  }
  
  public void cancel()
  {
    Call localCall = this.call;
    Intrinsics.checkNotNull(localCall);
    localCall.cancel();
  }
  
  public final void checkUpgradeSuccess$okhttp(Response paramResponse, Exchange paramExchange)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    if (paramResponse.code() == 101)
    {
      String str = Response.header$default(paramResponse, "Connection", null, 2, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (StringsKt.equals("Upgrade", str, true))
      {
        str = Response.header$default(paramResponse, "Upgrade", null, 2, null);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        if (StringsKt.equals("websocket", str, true))
        {
          paramResponse = Response.header$default(paramResponse, "Sec-WebSocket-Accept", null, 2, null);
          Log5ECF72.a(paramResponse);
          LogE84000.a(paramResponse);
          Log229316.a(paramResponse);
          str = ByteString.Companion.encodeUtf8(this.key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").sha1().base64();
          if (!(true ^ Intrinsics.areEqual(str, paramResponse)))
          {
            if (paramExchange != null) {
              return;
            }
            throw ((Throwable)new ProtocolException("Web Socket exchange missing: bad interceptor?"));
          }
          throw ((Throwable)new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '" + str + "' but was '" + paramResponse + '\''));
        }
        throw ((Throwable)new ProtocolException("Expected 'Upgrade' header value 'websocket' but was '" + str + '\''));
      }
      throw ((Throwable)new ProtocolException("Expected 'Connection' header value 'Upgrade' but was '" + str + '\''));
    }
    throw ((Throwable)new ProtocolException("Expected HTTP 101 response but was '" + paramResponse.code() + ' ' + paramResponse.message() + '\''));
  }
  
  public boolean close(int paramInt, String paramString)
  {
    return close(paramInt, paramString, 60000L);
  }
  
  public final boolean close(int paramInt, String paramString, long paramLong)
  {
    try
    {
      WebSocketProtocol.INSTANCE.validateCloseCode(paramInt);
      Object localObject1 = null;
      Object localObject2 = (ByteString)null;
      if (paramString != null)
      {
        localObject1 = ByteString.Companion.encodeUtf8(paramString);
        int i;
        if (((ByteString)localObject1).size() <= 123L) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0)
        {
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          localObject1 = "reason.size() > 123: " + paramString;
          paramString = new java/lang/IllegalArgumentException;
          paramString.<init>(localObject1.toString());
          throw ((Throwable)paramString);
        }
      }
      if ((!this.failed) && (!this.enqueuedClose))
      {
        this.enqueuedClose = true;
        localObject2 = this.messageAndCloseQueue;
        paramString = new okhttp3/internal/ws/RealWebSocket$Close;
        paramString.<init>(paramInt, (ByteString)localObject1, paramLong);
        ((ArrayDeque)localObject2).add(paramString);
        runWriter();
        return true;
      }
      return false;
    }
    finally {}
  }
  
  public final void connect(final OkHttpClient paramOkHttpClient)
  {
    Intrinsics.checkNotNullParameter(paramOkHttpClient, "client");
    if (this.originalRequest.header("Sec-WebSocket-Extensions") != null)
    {
      failWebSocket((Exception)new ProtocolException("Request header not permitted: 'Sec-WebSocket-Extensions'"), null);
      return;
    }
    Object localObject = paramOkHttpClient.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
    paramOkHttpClient = this.originalRequest.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header("Sec-WebSocket-Key", this.key).header("Sec-WebSocket-Version", "13").header("Sec-WebSocket-Extensions", "permessage-deflate").build();
    localObject = (Call)new RealCall((OkHttpClient)localObject, paramOkHttpClient, true);
    this.call = ((Call)localObject);
    Intrinsics.checkNotNull(localObject);
    ((Call)localObject).enqueue((Callback)new Callback()
    {
      final RealWebSocket this$0;
      
      public void onFailure(Call paramAnonymousCall, IOException paramAnonymousIOException)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousCall, "call");
        Intrinsics.checkNotNullParameter(paramAnonymousIOException, "e");
        this.this$0.failWebSocket((Exception)paramAnonymousIOException, null);
      }
      
      public void onResponse(Call paramAnonymousCall, Response paramAnonymousResponse)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousCall, "call");
        Intrinsics.checkNotNullParameter(paramAnonymousResponse, "response");
        ??? = paramAnonymousResponse.exchange();
        try
        {
          this.this$0.checkUpgradeSuccess$okhttp(paramAnonymousResponse, (Exchange)???);
          Intrinsics.checkNotNull(???);
          paramAnonymousCall = ((Exchange)???).newWebSocketStreams();
          ??? = WebSocketExtensions.Companion.parse(paramAnonymousResponse.headers());
          RealWebSocket.access$setExtensions$p(this.this$0, (WebSocketExtensions)???);
          if (!RealWebSocket.access$isValid(this.this$0, (WebSocketExtensions)???)) {
            synchronized (this.this$0)
            {
              RealWebSocket.access$getMessageAndCloseQueue$p(this.this$0).clear();
              this.this$0.close(1010, "unexpected Sec-WebSocket-Extensions in response header");
            }
          }
          try
          {
            ??? = new java/lang/StringBuilder;
            ((StringBuilder)???).<init>();
            ??? = Util.okHttpName + " WebSocket " + paramOkHttpClient.url().redact();
            this.this$0.initReaderAndWriter((String)???, paramAnonymousCall);
            this.this$0.getListener$okhttp().onOpen((WebSocket)this.this$0, paramAnonymousResponse);
            this.this$0.loopReader();
          }
          catch (Exception paramAnonymousCall)
          {
            this.this$0.failWebSocket(paramAnonymousCall, null);
          }
          return;
        }
        catch (IOException paramAnonymousCall)
        {
          if (??? != null) {
            ((Exchange)???).webSocketUpgradeFailed();
          }
          this.this$0.failWebSocket((Exception)paramAnonymousCall, paramAnonymousResponse);
          Util.closeQuietly((Closeable)paramAnonymousResponse);
        }
      }
    });
  }
  
  /* Error */
  public final void failWebSocket(Exception paramException, Response paramResponse)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 562
    //   4: invokestatic 180	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 355	okhttp3/internal/ws/RealWebSocket:failed	Z
    //   13: istore_3
    //   14: iload_3
    //   15: ifeq +6 -> 21
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: aload_0
    //   22: iconst_1
    //   23: putfield 355	okhttp3/internal/ws/RealWebSocket:failed	Z
    //   26: aload_0
    //   27: getfield 564	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   30: astore 4
    //   32: aconst_null
    //   33: checkcast 19	okhttp3/internal/ws/RealWebSocket$Streams
    //   36: astore 5
    //   38: aload_0
    //   39: aconst_null
    //   40: putfield 564	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   43: aload_0
    //   44: getfield 566	okhttp3/internal/ws/RealWebSocket:reader	Lokhttp3/internal/ws/WebSocketReader;
    //   47: astore 5
    //   49: aconst_null
    //   50: checkcast 568	okhttp3/internal/ws/WebSocketReader
    //   53: astore 6
    //   55: aload_0
    //   56: aconst_null
    //   57: putfield 566	okhttp3/internal/ws/RealWebSocket:reader	Lokhttp3/internal/ws/WebSocketReader;
    //   60: aload_0
    //   61: getfield 570	okhttp3/internal/ws/RealWebSocket:writer	Lokhttp3/internal/ws/WebSocketWriter;
    //   64: astore 6
    //   66: aconst_null
    //   67: checkcast 572	okhttp3/internal/ws/WebSocketWriter
    //   70: astore 7
    //   72: aload_0
    //   73: aconst_null
    //   74: putfield 570	okhttp3/internal/ws/RealWebSocket:writer	Lokhttp3/internal/ws/WebSocketWriter;
    //   77: aload_0
    //   78: getfield 205	okhttp3/internal/ws/RealWebSocket:taskQueue	Lokhttp3/internal/concurrent/TaskQueue;
    //   81: invokevirtual 575	okhttp3/internal/concurrent/TaskQueue:shutdown	()V
    //   84: getstatic 243	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   87: astore 7
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_0
    //   92: getfield 189	okhttp3/internal/ws/RealWebSocket:listener	Lokhttp3/WebSocketListener;
    //   95: aload_0
    //   96: checkcast 6	okhttp3/WebSocket
    //   99: aload_1
    //   100: checkcast 274	java/lang/Throwable
    //   103: aload_2
    //   104: invokevirtual 581	okhttp3/WebSocketListener:onFailure	(Lokhttp3/WebSocket;Ljava/lang/Throwable;Lokhttp3/Response;)V
    //   107: aload 4
    //   109: ifnull +11 -> 120
    //   112: aload 4
    //   114: checkcast 583	java/io/Closeable
    //   117: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   120: aload 5
    //   122: ifnull +11 -> 133
    //   125: aload 5
    //   127: checkcast 583	java/io/Closeable
    //   130: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   133: aload 6
    //   135: ifnull +11 -> 146
    //   138: aload 6
    //   140: checkcast 583	java/io/Closeable
    //   143: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   146: return
    //   147: astore_1
    //   148: aload 4
    //   150: ifnull +11 -> 161
    //   153: aload 4
    //   155: checkcast 583	java/io/Closeable
    //   158: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   161: aload 5
    //   163: ifnull +11 -> 174
    //   166: aload 5
    //   168: checkcast 583	java/io/Closeable
    //   171: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   174: aload 6
    //   176: ifnull +11 -> 187
    //   179: aload 6
    //   181: checkcast 583	java/io/Closeable
    //   184: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   187: aload_1
    //   188: athrow
    //   189: astore_1
    //   190: aload_0
    //   191: monitorexit
    //   192: aload_1
    //   193: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	194	0	this	RealWebSocket
    //   0	194	1	paramException	Exception
    //   0	194	2	paramResponse	Response
    //   13	2	3	bool	boolean
    //   30	124	4	localStreams	Streams
    //   36	131	5	localObject1	Object
    //   53	127	6	localObject2	Object
    //   70	18	7	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   91	107	147	finally
    //   9	14	189	finally
    //   21	89	189	finally
  }
  
  public final WebSocketListener getListener$okhttp()
  {
    return this.listener;
  }
  
  public final void initReaderAndWriter(String paramString, Streams paramStreams)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramString, "name");
    Intrinsics.checkNotNullParameter(paramStreams, "streams");
    WebSocketExtensions localWebSocketExtensions = this.extensions;
    Intrinsics.checkNotNull(localWebSocketExtensions);
    try
    {
      this.name = paramString;
      this.streams = paramStreams;
      Object localObject1 = new okhttp3/internal/ws/WebSocketWriter;
      ((WebSocketWriter)localObject1).<init>(paramStreams.getClient(), paramStreams.getSink(), this.random, localWebSocketExtensions.perMessageDeflate, localWebSocketExtensions.noContextTakeover(paramStreams.getClient()), this.minimumDeflateSize);
      this.writer = ((WebSocketWriter)localObject1);
      localObject1 = new okhttp3/internal/ws/RealWebSocket$WriterTask;
      ((WriterTask)localObject1).<init>(this);
      this.writerTask = ((Task)localObject1);
      if (this.pingIntervalMillis != 0L)
      {
        long l = TimeUnit.MILLISECONDS.toNanos(this.pingIntervalMillis);
        localObject1 = this.taskQueue;
        Object localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        localObject2 = paramString + " ping";
        Task local1 = new okhttp3/internal/ws/RealWebSocket$initReaderAndWriter$$inlined$synchronized$lambda$1;
        local1.<init>((String)localObject2, (String)localObject2, l, this, paramString, paramStreams, localWebSocketExtensions);
        paramString = (Task)local1;
        ((TaskQueue)localObject1).schedule(paramString, l);
      }
      if ((((Collection)this.messageAndCloseQueue).isEmpty() ^ true)) {
        runWriter();
      }
      paramString = Unit.INSTANCE;
      this.reader = new WebSocketReader(paramStreams.getClient(), paramStreams.getSource(), (WebSocketReader.FrameCallback)this, localWebSocketExtensions.perMessageDeflate, localWebSocketExtensions.noContextTakeover(paramStreams.getClient() ^ true));
      return;
    }
    finally {}
  }
  
  public final void loopReader()
    throws IOException
  {
    while (this.receivedCloseCode == -1)
    {
      WebSocketReader localWebSocketReader = this.reader;
      Intrinsics.checkNotNull(localWebSocketReader);
      localWebSocketReader.processNextFrame();
    }
  }
  
  public void onReadClose(int paramInt, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "reason");
    int j = 1;
    int i;
    if (paramInt != -1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      Object localObject3 = (Streams)null;
      WebSocketReader localWebSocketReader2 = (WebSocketReader)null;
      WebSocketWriter localWebSocketWriter = (WebSocketWriter)null;
      try
      {
        if (this.receivedCloseCode == -1) {
          i = j;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          this.receivedCloseCode = paramInt;
          this.receivedCloseReason = paramString;
          Object localObject2 = localObject3;
          WebSocketReader localWebSocketReader1 = localWebSocketReader2;
          Object localObject1 = localWebSocketWriter;
          if (this.enqueuedClose)
          {
            localObject2 = localObject3;
            localWebSocketReader1 = localWebSocketReader2;
            localObject1 = localWebSocketWriter;
            if (this.messageAndCloseQueue.isEmpty())
            {
              localObject2 = this.streams;
              localObject1 = (Streams)null;
              this.streams = null;
              localWebSocketReader1 = this.reader;
              localObject1 = (WebSocketReader)null;
              this.reader = null;
              localObject1 = this.writer;
              localObject3 = (WebSocketWriter)null;
              this.writer = null;
              this.taskQueue.shutdown();
            }
          }
          localObject3 = Unit.INSTANCE;
          try
          {
            this.listener.onClosing((WebSocket)this, paramInt, paramString);
            if (localObject2 != null) {
              this.listener.onClosed((WebSocket)this, paramInt, paramString);
            }
            return;
          }
          finally
          {
            if (localObject2 != null) {
              Util.closeQuietly((Closeable)localObject2);
            }
            if (localWebSocketReader1 != null) {
              Util.closeQuietly((Closeable)localWebSocketReader1);
            }
            if (localObject1 != null) {
              Util.closeQuietly((Closeable)localObject1);
            }
          }
        }
        paramString = new java/lang/IllegalStateException;
        paramString.<init>("already closed".toString());
        throw ((Throwable)paramString);
      }
      finally {}
    }
    throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
  }
  
  public void onReadMessage(String paramString)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramString, "text");
    this.listener.onMessage((WebSocket)this, paramString);
  }
  
  public void onReadMessage(ByteString paramByteString)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    this.listener.onMessage((WebSocket)this, paramByteString);
  }
  
  public void onReadPing(ByteString paramByteString)
  {
    try
    {
      Intrinsics.checkNotNullParameter(paramByteString, "payload");
      if ((!this.failed) && ((!this.enqueuedClose) || (!this.messageAndCloseQueue.isEmpty())))
      {
        this.pongQueue.add(paramByteString);
        runWriter();
        this.receivedPingCount += 1;
        return;
      }
      return;
    }
    finally {}
  }
  
  public void onReadPong(ByteString paramByteString)
  {
    try
    {
      Intrinsics.checkNotNullParameter(paramByteString, "payload");
      this.receivedPongCount += 1;
      this.awaitingPong = false;
      return;
    }
    finally
    {
      paramByteString = finally;
      throw paramByteString;
    }
  }
  
  public final boolean pong(ByteString paramByteString)
  {
    try
    {
      Intrinsics.checkNotNullParameter(paramByteString, "payload");
      if ((!this.failed) && ((!this.enqueuedClose) || (!this.messageAndCloseQueue.isEmpty())))
      {
        this.pongQueue.add(paramByteString);
        runWriter();
        return true;
      }
      return false;
    }
    finally {}
  }
  
  public final boolean processNextFrame()
    throws IOException
  {
    boolean bool = false;
    try
    {
      WebSocketReader localWebSocketReader = this.reader;
      Intrinsics.checkNotNull(localWebSocketReader);
      localWebSocketReader.processNextFrame();
      int i = this.receivedCloseCode;
      if (i == -1) {
        bool = true;
      }
    }
    catch (Exception localException)
    {
      failWebSocket(localException, null);
    }
    return bool;
  }
  
  public long queueSize()
  {
    try
    {
      long l = this.queueSize;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int receivedPingCount()
  {
    try
    {
      int i = this.receivedPingCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int receivedPongCount()
  {
    try
    {
      int i = this.receivedPongCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Request request()
  {
    return this.originalRequest;
  }
  
  public boolean send(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "text");
    return send(ByteString.Companion.encodeUtf8(paramString), 1);
  }
  
  public boolean send(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    return send(paramByteString, 2);
  }
  
  public final int sentPingCount()
  {
    try
    {
      int i = this.sentPingCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void tearDown()
    throws InterruptedException
  {
    this.taskQueue.shutdown();
    this.taskQueue.idleLatch().await(10L, TimeUnit.SECONDS);
  }
  
  /* Error */
  public final boolean writeOneFrame$okhttp()
    throws IOException
  {
    // Byte code:
    //   0: new 695	kotlin/jvm/internal/Ref$ObjectRef
    //   3: dup
    //   4: invokespecial 696	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
    //   7: astore 11
    //   9: aload 11
    //   11: aconst_null
    //   12: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   15: new 702	kotlin/jvm/internal/Ref$IntRef
    //   18: dup
    //   19: invokespecial 703	kotlin/jvm/internal/Ref$IntRef:<init>	()V
    //   22: astore 5
    //   24: aload 5
    //   26: iconst_m1
    //   27: putfield 705	kotlin/jvm/internal/Ref$IntRef:element	I
    //   30: new 695	kotlin/jvm/internal/Ref$ObjectRef
    //   33: dup
    //   34: invokespecial 696	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
    //   37: astore 9
    //   39: aconst_null
    //   40: checkcast 707	java/lang/String
    //   43: astore 6
    //   45: aload 9
    //   47: aconst_null
    //   48: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   51: new 695	kotlin/jvm/internal/Ref$ObjectRef
    //   54: dup
    //   55: invokespecial 696	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
    //   58: astore 8
    //   60: aconst_null
    //   61: checkcast 19	okhttp3/internal/ws/RealWebSocket$Streams
    //   64: astore 6
    //   66: aload 8
    //   68: aconst_null
    //   69: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   72: new 695	kotlin/jvm/internal/Ref$ObjectRef
    //   75: dup
    //   76: invokespecial 696	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
    //   79: astore 7
    //   81: aconst_null
    //   82: checkcast 568	okhttp3/internal/ws/WebSocketReader
    //   85: astore 6
    //   87: aload 7
    //   89: aconst_null
    //   90: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   93: new 695	kotlin/jvm/internal/Ref$ObjectRef
    //   96: dup
    //   97: invokespecial 696	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
    //   100: astore 6
    //   102: aconst_null
    //   103: checkcast 572	okhttp3/internal/ws/WebSocketWriter
    //   106: astore 10
    //   108: aload 6
    //   110: aconst_null
    //   111: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   114: aload_0
    //   115: monitorenter
    //   116: aload_0
    //   117: getfield 355	okhttp3/internal/ws/RealWebSocket:failed	Z
    //   120: istore 4
    //   122: iload 4
    //   124: ifeq +7 -> 131
    //   127: aload_0
    //   128: monitorexit
    //   129: iconst_0
    //   130: ireturn
    //   131: aload_0
    //   132: getfield 570	okhttp3/internal/ws/RealWebSocket:writer	Lokhttp3/internal/ws/WebSocketWriter;
    //   135: astore 10
    //   137: aload_0
    //   138: getfield 210	okhttp3/internal/ws/RealWebSocket:pongQueue	Ljava/util/ArrayDeque;
    //   141: invokevirtual 711	java/util/ArrayDeque:poll	()Ljava/lang/Object;
    //   144: checkcast 228	okio/ByteString
    //   147: astore 12
    //   149: aload 12
    //   151: ifnonnull +302 -> 453
    //   154: aload 11
    //   156: aload_0
    //   157: getfield 212	okhttp3/internal/ws/RealWebSocket:messageAndCloseQueue	Ljava/util/ArrayDeque;
    //   160: invokevirtual 711	java/util/ArrayDeque:poll	()Ljava/lang/Object;
    //   163: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   166: aload 11
    //   168: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   171: instanceof 10
    //   174: istore 4
    //   176: iload 4
    //   178: ifeq +249 -> 427
    //   181: aload 5
    //   183: aload_0
    //   184: getfield 214	okhttp3/internal/ws/RealWebSocket:receivedCloseCode	I
    //   187: putfield 705	kotlin/jvm/internal/Ref$IntRef:element	I
    //   190: aload 9
    //   192: aload_0
    //   193: getfield 649	okhttp3/internal/ws/RealWebSocket:receivedCloseReason	Ljava/lang/String;
    //   196: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   199: aload 5
    //   201: getfield 705	kotlin/jvm/internal/Ref$IntRef:element	I
    //   204: istore_1
    //   205: iload_1
    //   206: iconst_m1
    //   207: if_icmpeq +78 -> 285
    //   210: aload 8
    //   212: aload_0
    //   213: getfield 564	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   216: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   219: aconst_null
    //   220: checkcast 19	okhttp3/internal/ws/RealWebSocket$Streams
    //   223: astore 13
    //   225: aload_0
    //   226: aconst_null
    //   227: putfield 564	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   230: aload 7
    //   232: aload_0
    //   233: getfield 566	okhttp3/internal/ws/RealWebSocket:reader	Lokhttp3/internal/ws/WebSocketReader;
    //   236: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   239: aconst_null
    //   240: checkcast 568	okhttp3/internal/ws/WebSocketReader
    //   243: astore 13
    //   245: aload_0
    //   246: aconst_null
    //   247: putfield 566	okhttp3/internal/ws/RealWebSocket:reader	Lokhttp3/internal/ws/WebSocketReader;
    //   250: aload 6
    //   252: aload_0
    //   253: getfield 570	okhttp3/internal/ws/RealWebSocket:writer	Lokhttp3/internal/ws/WebSocketWriter;
    //   256: putfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   259: aconst_null
    //   260: checkcast 572	okhttp3/internal/ws/WebSocketWriter
    //   263: astore 13
    //   265: aload_0
    //   266: aconst_null
    //   267: putfield 570	okhttp3/internal/ws/RealWebSocket:writer	Lokhttp3/internal/ws/WebSocketWriter;
    //   270: aload_0
    //   271: getfield 205	okhttp3/internal/ws/RealWebSocket:taskQueue	Lokhttp3/internal/concurrent/TaskQueue;
    //   274: invokevirtual 575	okhttp3/internal/concurrent/TaskQueue:shutdown	()V
    //   277: goto +176 -> 453
    //   280: astore 5
    //   282: goto +640 -> 922
    //   285: aload 11
    //   287: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   290: astore 13
    //   292: aload 13
    //   294: ifnull +107 -> 401
    //   297: aload 13
    //   299: checkcast 10	okhttp3/internal/ws/RealWebSocket$Close
    //   302: invokevirtual 714	okhttp3/internal/ws/RealWebSocket$Close:getCancelAfterCloseMillis	()J
    //   305: lstore_2
    //   306: aload_0
    //   307: getfield 205	okhttp3/internal/ws/RealWebSocket:taskQueue	Lokhttp3/internal/concurrent/TaskQueue;
    //   310: astore 13
    //   312: new 258	java/lang/StringBuilder
    //   315: astore 14
    //   317: aload 14
    //   319: invokespecial 259	java/lang/StringBuilder:<init>	()V
    //   322: aload 14
    //   324: aload_0
    //   325: getfield 282	okhttp3/internal/ws/RealWebSocket:name	Ljava/lang/String;
    //   328: invokevirtual 265	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: ldc_w 716
    //   334: invokevirtual 265	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: invokevirtual 268	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   340: astore 15
    //   342: getstatic 618	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   345: lload_2
    //   346: invokevirtual 622	java/util/concurrent/TimeUnit:toNanos	(J)J
    //   349: lstore_2
    //   350: new 29	okhttp3/internal/ws/RealWebSocket$writeOneFrame$$inlined$synchronized$lambda$1
    //   353: astore 14
    //   355: aload 14
    //   357: aload 15
    //   359: iconst_1
    //   360: aload 15
    //   362: iconst_1
    //   363: aload_0
    //   364: aload 10
    //   366: aload 12
    //   368: aload 11
    //   370: aload 5
    //   372: aload 9
    //   374: aload 8
    //   376: aload 7
    //   378: aload 6
    //   380: invokespecial 719	okhttp3/internal/ws/RealWebSocket$writeOneFrame$$inlined$synchronized$lambda$1:<init>	(Ljava/lang/String;ZLjava/lang/String;ZLokhttp3/internal/ws/RealWebSocket;Lokhttp3/internal/ws/WebSocketWriter;Lokio/ByteString;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$IntRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
    //   383: aload 14
    //   385: checkcast 613	okhttp3/internal/concurrent/Task
    //   388: astore 14
    //   390: aload 13
    //   392: aload 14
    //   394: lload_2
    //   395: invokevirtual 631	okhttp3/internal/concurrent/TaskQueue:schedule	(Lokhttp3/internal/concurrent/Task;J)V
    //   398: goto +55 -> 453
    //   401: new 721	java/lang/NullPointerException
    //   404: astore 5
    //   406: aload 5
    //   408: ldc_w 723
    //   411: invokespecial 724	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   414: aload 5
    //   416: athrow
    //   417: astore 5
    //   419: goto +503 -> 922
    //   422: astore 5
    //   424: goto +498 -> 922
    //   427: aload 11
    //   429: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   432: astore 13
    //   434: aload 13
    //   436: ifnonnull +17 -> 453
    //   439: aload_0
    //   440: monitorexit
    //   441: iconst_0
    //   442: ireturn
    //   443: astore 5
    //   445: goto +477 -> 922
    //   448: astore 5
    //   450: goto +472 -> 922
    //   453: getstatic 243	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   456: astore 13
    //   458: aload_0
    //   459: monitorexit
    //   460: aload 12
    //   462: ifnull +28 -> 490
    //   465: aload 10
    //   467: invokestatic 393	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
    //   470: aload 10
    //   472: aload 12
    //   474: invokevirtual 727	okhttp3/internal/ws/WebSocketWriter:writePong	(Lokio/ByteString;)V
    //   477: goto +246 -> 723
    //   480: astore 5
    //   482: goto +356 -> 838
    //   485: astore 5
    //   487: goto +351 -> 838
    //   490: aload 11
    //   492: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   495: instanceof 16
    //   498: istore 4
    //   500: iload 4
    //   502: ifeq +95 -> 597
    //   505: aload 11
    //   507: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   510: astore 5
    //   512: aload 5
    //   514: ifnull +67 -> 581
    //   517: aload 5
    //   519: checkcast 16	okhttp3/internal/ws/RealWebSocket$Message
    //   522: astore 5
    //   524: aload 10
    //   526: invokestatic 393	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
    //   529: aload 10
    //   531: aload 5
    //   533: invokevirtual 730	okhttp3/internal/ws/RealWebSocket$Message:getFormatOpcode	()I
    //   536: aload 5
    //   538: invokevirtual 733	okhttp3/internal/ws/RealWebSocket$Message:getData	()Lokio/ByteString;
    //   541: invokevirtual 736	okhttp3/internal/ws/WebSocketWriter:writeMessageFrame	(ILokio/ByteString;)V
    //   544: aload_0
    //   545: monitorenter
    //   546: aload_0
    //   547: aload_0
    //   548: getfield 359	okhttp3/internal/ws/RealWebSocket:queueSize	J
    //   551: aload 5
    //   553: invokevirtual 733	okhttp3/internal/ws/RealWebSocket$Message:getData	()Lokio/ByteString;
    //   556: invokevirtual 362	okio/ByteString:size	()I
    //   559: i2l
    //   560: lsub
    //   561: putfield 359	okhttp3/internal/ws/RealWebSocket:queueSize	J
    //   564: getstatic 243	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   567: astore 5
    //   569: aload_0
    //   570: monitorexit
    //   571: goto +152 -> 723
    //   574: astore 5
    //   576: aload_0
    //   577: monitorexit
    //   578: aload 5
    //   580: athrow
    //   581: new 721	java/lang/NullPointerException
    //   584: astore 5
    //   586: aload 5
    //   588: ldc_w 738
    //   591: invokespecial 724	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   594: aload 5
    //   596: athrow
    //   597: aload 11
    //   599: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   602: instanceof 10
    //   605: ifeq +210 -> 815
    //   608: aload 11
    //   610: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   613: astore 11
    //   615: aload 11
    //   617: ifnull +182 -> 799
    //   620: aload 11
    //   622: checkcast 10	okhttp3/internal/ws/RealWebSocket$Close
    //   625: astore 11
    //   627: aload 10
    //   629: invokestatic 393	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
    //   632: aload 10
    //   634: aload 11
    //   636: invokevirtual 741	okhttp3/internal/ws/RealWebSocket$Close:getCode	()I
    //   639: aload 11
    //   641: invokevirtual 744	okhttp3/internal/ws/RealWebSocket$Close:getReason	()Lokio/ByteString;
    //   644: invokevirtual 747	okhttp3/internal/ws/WebSocketWriter:writeClose	(ILokio/ByteString;)V
    //   647: aload 8
    //   649: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   652: checkcast 19	okhttp3/internal/ws/RealWebSocket$Streams
    //   655: astore 10
    //   657: aload 10
    //   659: ifnull +64 -> 723
    //   662: aload_0
    //   663: getfield 189	okhttp3/internal/ws/RealWebSocket:listener	Lokhttp3/WebSocketListener;
    //   666: astore 11
    //   668: aload_0
    //   669: checkcast 6	okhttp3/WebSocket
    //   672: astore 10
    //   674: aload 5
    //   676: getfield 705	kotlin/jvm/internal/Ref$IntRef:element	I
    //   679: istore_1
    //   680: aload 9
    //   682: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   685: checkcast 707	java/lang/String
    //   688: astore 5
    //   690: aload 5
    //   692: invokestatic 393	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
    //   695: aload 11
    //   697: aload 10
    //   699: iload_1
    //   700: aload 5
    //   702: invokevirtual 657	okhttp3/WebSocketListener:onClosed	(Lokhttp3/WebSocket;ILjava/lang/String;)V
    //   705: goto +18 -> 723
    //   708: astore 5
    //   710: goto +128 -> 838
    //   713: astore 5
    //   715: goto +123 -> 838
    //   718: astore 5
    //   720: goto +118 -> 838
    //   723: aload 8
    //   725: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   728: checkcast 19	okhttp3/internal/ws/RealWebSocket$Streams
    //   731: astore 5
    //   733: aload 5
    //   735: ifnull +11 -> 746
    //   738: aload 5
    //   740: checkcast 583	java/io/Closeable
    //   743: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   746: aload 7
    //   748: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   751: checkcast 568	okhttp3/internal/ws/WebSocketReader
    //   754: astore 5
    //   756: aload 5
    //   758: ifnull +11 -> 769
    //   761: aload 5
    //   763: checkcast 583	java/io/Closeable
    //   766: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   769: aload 6
    //   771: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   774: checkcast 572	okhttp3/internal/ws/WebSocketWriter
    //   777: astore 5
    //   779: aload 5
    //   781: ifnull +11 -> 792
    //   784: aload 5
    //   786: checkcast 583	java/io/Closeable
    //   789: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   792: iconst_1
    //   793: ireturn
    //   794: astore 5
    //   796: goto +42 -> 838
    //   799: new 721	java/lang/NullPointerException
    //   802: astore 5
    //   804: aload 5
    //   806: ldc_w 723
    //   809: invokespecial 724	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   812: aload 5
    //   814: athrow
    //   815: new 333	java/lang/AssertionError
    //   818: astore 5
    //   820: aload 5
    //   822: invokespecial 748	java/lang/AssertionError:<init>	()V
    //   825: aload 5
    //   827: checkcast 274	java/lang/Throwable
    //   830: athrow
    //   831: astore 5
    //   833: goto +5 -> 838
    //   836: astore 5
    //   838: aload 8
    //   840: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   843: checkcast 19	okhttp3/internal/ws/RealWebSocket$Streams
    //   846: astore 8
    //   848: aload 8
    //   850: ifnull +11 -> 861
    //   853: aload 8
    //   855: checkcast 583	java/io/Closeable
    //   858: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   861: aload 7
    //   863: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   866: checkcast 568	okhttp3/internal/ws/WebSocketReader
    //   869: astore 7
    //   871: aload 7
    //   873: ifnull +11 -> 884
    //   876: aload 7
    //   878: checkcast 583	java/io/Closeable
    //   881: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   884: aload 6
    //   886: getfield 700	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
    //   889: checkcast 572	okhttp3/internal/ws/WebSocketWriter
    //   892: astore 6
    //   894: aload 6
    //   896: ifnull +11 -> 907
    //   899: aload 6
    //   901: checkcast 583	java/io/Closeable
    //   904: invokestatic 587	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   907: aload 5
    //   909: athrow
    //   910: astore 5
    //   912: goto +10 -> 922
    //   915: astore 5
    //   917: goto +5 -> 922
    //   920: astore 5
    //   922: aload_0
    //   923: monitorexit
    //   924: aload 5
    //   926: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	927	0	this	RealWebSocket
    //   204	496	1	i	int
    //   305	90	2	l	long
    //   120	381	4	bool	boolean
    //   22	178	5	localIntRef1	Ref.IntRef
    //   280	91	5	localIntRef2	Ref.IntRef
    //   404	11	5	localNullPointerException	NullPointerException
    //   417	1	5	localObject1	Object
    //   422	1	5	localObject2	Object
    //   443	1	5	localObject3	Object
    //   448	1	5	localObject4	Object
    //   480	1	5	localObject5	Object
    //   485	1	5	localObject6	Object
    //   510	58	5	localObject7	Object
    //   574	5	5	localObject8	Object
    //   584	117	5	localObject9	Object
    //   708	1	5	localObject10	Object
    //   713	1	5	localObject11	Object
    //   718	1	5	localObject12	Object
    //   731	54	5	localObject13	Object
    //   794	1	5	localObject14	Object
    //   802	24	5	localObject15	Object
    //   831	1	5	localObject16	Object
    //   836	72	5	localObject17	Object
    //   910	1	5	localObject18	Object
    //   915	1	5	localObject19	Object
    //   920	5	5	localObject20	Object
    //   43	857	6	localObject21	Object
    //   79	798	7	localObject22	Object
    //   58	796	8	localObject23	Object
    //   37	644	9	localObjectRef	Ref.ObjectRef
    //   106	592	10	localObject24	Object
    //   7	689	11	localObject25	Object
    //   147	326	12	localByteString	ByteString
    //   223	234	13	localObject26	Object
    //   315	78	14	localObject27	Object
    //   340	21	15	str	String
    // Exception table:
    //   from	to	target	type
    //   210	277	280	finally
    //   355	390	417	finally
    //   390	398	417	finally
    //   401	417	417	finally
    //   181	205	422	finally
    //   285	292	422	finally
    //   297	355	422	finally
    //   427	434	443	finally
    //   154	176	448	finally
    //   470	477	480	finally
    //   505	512	480	finally
    //   517	546	480	finally
    //   569	571	480	finally
    //   576	581	480	finally
    //   581	597	480	finally
    //   465	470	485	finally
    //   546	569	574	finally
    //   680	705	708	finally
    //   674	680	713	finally
    //   662	674	718	finally
    //   647	657	794	finally
    //   799	815	831	finally
    //   815	831	831	finally
    //   490	500	836	finally
    //   597	615	836	finally
    //   620	647	836	finally
    //   453	458	910	finally
    //   137	149	915	finally
    //   116	122	920	finally
    //   131	137	920	finally
  }
  
  public final void writePingFrame$okhttp()
  {
    try
    {
      boolean bool = this.failed;
      if (bool) {
        return;
      }
      WebSocketWriter localWebSocketWriter = this.writer;
      if (localWebSocketWriter != null)
      {
        int i;
        if (this.awaitingPong) {
          i = this.sentPingCount;
        } else {
          i = -1;
        }
        this.sentPingCount += 1;
        this.awaitingPong = true;
        Object localObject1 = Unit.INSTANCE;
        if (i != -1)
        {
          localObject1 = (Exception)new SocketTimeoutException("sent ping but didn't receive pong within " + this.pingIntervalMillis + "ms (after " + (i - 1) + " successful ping/pongs)");
          failWebSocket((Exception)localObject1, null);
          return;
        }
        try
        {
          localWebSocketWriter.writePing(ByteString.EMPTY);
        }
        catch (IOException localIOException)
        {
          failWebSocket((Exception)localIOException, null);
        }
        return;
      }
      return;
    }
    finally {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\b\b\000\030\0002\0020\001B\037\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\005\022\006\020\006\032\0020\007¢\006\002\020\bR\021\020\006\032\0020\007¢\006\b\n\000\032\004\b\t\020\nR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\013\020\fR\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\r\020\016¨\006\017"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Close;", "", "code", "", "reason", "Lokio/ByteString;", "cancelAfterCloseMillis", "", "(ILokio/ByteString;J)V", "getCancelAfterCloseMillis", "()J", "getCode", "()I", "getReason", "()Lokio/ByteString;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Close
  {
    private final long cancelAfterCloseMillis;
    private final int code;
    private final ByteString reason;
    
    public Close(int paramInt, ByteString paramByteString, long paramLong)
    {
      this.code = paramInt;
      this.reason = paramByteString;
      this.cancelAfterCloseMillis = paramLong;
    }
    
    public final long getCancelAfterCloseMillis()
    {
      return this.cancelAfterCloseMillis;
    }
    
    public final int getCode()
    {
      return this.code;
    }
    
    public final ByteString getReason()
    {
      return this.reason;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\002\b\003\n\002\020 \n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\024\020\007\032\b\022\004\022\0020\t0\bX\004¢\006\002\n\000¨\006\n"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Companion;", "", "()V", "CANCEL_AFTER_CLOSE_MILLIS", "", "DEFAULT_MINIMUM_DEFLATE_SIZE", "MAX_QUEUE_SIZE", "ONLY_HTTP1", "", "Lokhttp3/Protocol;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\006\b\000\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\007\020\bR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\t\020\n¨\006\013"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Message;", "", "formatOpcode", "", "data", "Lokio/ByteString;", "(ILokio/ByteString;)V", "getData", "()Lokio/ByteString;", "getFormatOpcode", "()I", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Message
  {
    private final ByteString data;
    private final int formatOpcode;
    
    public Message(int paramInt, ByteString paramByteString)
    {
      this.formatOpcode = paramInt;
      this.data = paramByteString;
    }
    
    public final ByteString getData()
    {
      return this.data;
    }
    
    public final int getFormatOpcode()
    {
      return this.formatOpcode;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\b\b&\030\0002\0020\001B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\t\020\nR\021\020\006\032\0020\007¢\006\b\n\000\032\004\b\013\020\fR\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\r\020\016¨\006\017"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Streams;", "Ljava/io/Closeable;", "client", "", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", "(ZLokio/BufferedSource;Lokio/BufferedSink;)V", "getClient", "()Z", "getSink", "()Lokio/BufferedSink;", "getSource", "()Lokio/BufferedSource;", "okhttp"}, k=1, mv={1, 4, 0})
  public static abstract class Streams
    implements Closeable
  {
    private final boolean client;
    private final BufferedSink sink;
    private final BufferedSource source;
    
    public Streams(boolean paramBoolean, BufferedSource paramBufferedSource, BufferedSink paramBufferedSink)
    {
      this.client = paramBoolean;
      this.source = paramBufferedSource;
      this.sink = paramBufferedSink;
    }
    
    public final boolean getClient()
    {
      return this.client;
    }
    
    public final BufferedSink getSink()
    {
      return this.sink;
    }
    
    public final BufferedSource getSource()
    {
      return this.source;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\b\020\003\032\0020\004H\026¨\006\005"}, d2={"Lokhttp3/internal/ws/RealWebSocket$WriterTask;", "Lokhttp3/internal/concurrent/Task;", "(Lokhttp3/internal/ws/RealWebSocket;)V", "runOnce", "", "okhttp"}, k=1, mv={1, 4, 0})
  private final class WriterTask
    extends Task
  {
    final RealWebSocket this$0;
    
    public WriterTask()
    {
      super(false, 2, null);
    }
    
    public long runOnce()
    {
      try
      {
        boolean bool = this.this$0.writeOneFrame$okhttp();
        if (bool) {
          return 0L;
        }
      }
      catch (IOException localIOException)
      {
        this.this$0.failWebSocket((Exception)localIOException, null);
      }
      return -1L;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/ws/RealWebSocket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */