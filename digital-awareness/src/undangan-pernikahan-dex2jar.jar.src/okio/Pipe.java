package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000D\n\002\030\002\n\002\020\000\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\030\002\n\002\b\013\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\005\n\002\030\002\n\002\030\002\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\006\020!\032\0020\"J\016\020#\032\0020\"2\006\020\027\032\0020\020J\r\020\027\032\0020\020H\007¢\006\002\b$J\r\020\033\032\0020\034H\007¢\006\002\b%J&\020&\032\0020\"*\0020\0202\027\020'\032\023\022\004\022\0020\020\022\004\022\0020\"0(¢\006\002\b)H\bR\024\020\005\032\0020\006X\004¢\006\b\n\000\032\004\b\007\020\bR\032\020\t\032\0020\nX\016¢\006\016\n\000\032\004\b\013\020\f\"\004\b\r\020\016R\034\020\017\032\004\030\0010\020X\016¢\006\016\n\000\032\004\b\021\020\022\"\004\b\023\020\024R\024\020\002\032\0020\003X\004¢\006\b\n\000\032\004\b\025\020\026R\023\020\027\032\0020\0208G¢\006\b\n\000\032\004\b\027\020\022R\032\020\030\032\0020\nX\016¢\006\016\n\000\032\004\b\031\020\f\"\004\b\032\020\016R\023\020\033\032\0020\0348G¢\006\b\n\000\032\004\b\033\020\035R\032\020\036\032\0020\nX\016¢\006\016\n\000\032\004\b\037\020\f\"\004\b \020\016¨\006*"}, d2={"Lokio/Pipe;", "", "maxBufferSize", "", "(J)V", "buffer", "Lokio/Buffer;", "getBuffer$okio", "()Lokio/Buffer;", "canceled", "", "getCanceled$okio", "()Z", "setCanceled$okio", "(Z)V", "foldedSink", "Lokio/Sink;", "getFoldedSink$okio", "()Lokio/Sink;", "setFoldedSink$okio", "(Lokio/Sink;)V", "getMaxBufferSize$okio", "()J", "sink", "sinkClosed", "getSinkClosed$okio", "setSinkClosed$okio", "source", "Lokio/Source;", "()Lokio/Source;", "sourceClosed", "getSourceClosed$okio", "setSourceClosed$okio", "cancel", "", "fold", "-deprecated_sink", "-deprecated_source", "forward", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "okio"}, k=1, mv={1, 4, 0})
public final class Pipe
{
  private final Buffer buffer;
  private boolean canceled;
  private Sink foldedSink;
  private final long maxBufferSize;
  private final Sink sink;
  private boolean sinkClosed;
  private final Source source;
  private boolean sourceClosed;
  
  public Pipe(long paramLong)
  {
    this.maxBufferSize = paramLong;
    this.buffer = new Buffer();
    int i;
    if (paramLong >= 1L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.sink = ((Sink)new Sink()
      {
        final Pipe this$0;
        private final Timeout timeout = new Timeout();
        
        /* Error */
        public void close()
        {
          // Byte code:
          //   0: aconst_null
          //   1: checkcast 6	okio/Sink
          //   4: astore 6
          //   6: aload_0
          //   7: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   10: invokevirtual 52	okio/Pipe:getBuffer$okio	()Lokio/Buffer;
          //   13: astore 8
          //   15: aload 8
          //   17: monitorenter
          //   18: aload_0
          //   19: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   22: invokevirtual 56	okio/Pipe:getSinkClosed$okio	()Z
          //   25: istore_1
          //   26: iload_1
          //   27: ifeq +7 -> 34
          //   30: aload 8
          //   32: monitorexit
          //   33: return
          //   34: aload_0
          //   35: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   38: invokevirtual 60	okio/Pipe:getFoldedSink$okio	()Lokio/Sink;
          //   41: astore 7
          //   43: aload 7
          //   45: ifnull +10 -> 55
          //   48: aload 7
          //   50: astore 6
          //   52: goto +79 -> 131
          //   55: aload_0
          //   56: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   59: invokevirtual 63	okio/Pipe:getSourceClosed$okio	()Z
          //   62: ifeq +39 -> 101
          //   65: aload_0
          //   66: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   69: invokevirtual 52	okio/Pipe:getBuffer$okio	()Lokio/Buffer;
          //   72: invokevirtual 69	okio/Buffer:size	()J
          //   75: lconst_0
          //   76: lcmp
          //   77: ifgt +6 -> 83
          //   80: goto +21 -> 101
          //   83: new 71	java/io/IOException
          //   86: astore 6
          //   88: aload 6
          //   90: ldc 73
          //   92: invokespecial 76	java/io/IOException:<init>	(Ljava/lang/String;)V
          //   95: aload 6
          //   97: checkcast 78	java/lang/Throwable
          //   100: athrow
          //   101: aload_0
          //   102: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   105: iconst_1
          //   106: invokevirtual 82	okio/Pipe:setSinkClosed$okio	(Z)V
          //   109: aload_0
          //   110: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   113: invokevirtual 52	okio/Pipe:getBuffer$okio	()Lokio/Buffer;
          //   116: astore 7
          //   118: aload 7
          //   120: ifnull +276 -> 396
          //   123: aload 7
          //   125: checkcast 4	java/lang/Object
          //   128: invokevirtual 85	java/lang/Object:notifyAll	()V
          //   131: getstatic 91	kotlin/Unit:INSTANCE	Lkotlin/Unit;
          //   134: astore 7
          //   136: aload 8
          //   138: monitorexit
          //   139: aload 6
          //   141: ifnull +254 -> 395
          //   144: aload_0
          //   145: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   148: astore 8
          //   150: aload 6
          //   152: invokeinterface 94 1 0
          //   157: astore 7
          //   159: aload 8
          //   161: invokevirtual 97	okio/Pipe:sink	()Lokio/Sink;
          //   164: invokeinterface 94 1 0
          //   169: astore 8
          //   171: aload 7
          //   173: invokevirtual 100	okio/Timeout:timeoutNanos	()J
          //   176: lstore 4
          //   178: aload 7
          //   180: getstatic 104	okio/Timeout:Companion	Lokio/Timeout$Companion;
          //   183: aload 8
          //   185: invokevirtual 100	okio/Timeout:timeoutNanos	()J
          //   188: aload 7
          //   190: invokevirtual 100	okio/Timeout:timeoutNanos	()J
          //   193: invokevirtual 110	okio/Timeout$Companion:minTimeout	(JJ)J
          //   196: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   199: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   202: pop
          //   203: aload 7
          //   205: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   208: ifeq +103 -> 311
          //   211: aload 7
          //   213: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   216: lstore_2
          //   217: aload 8
          //   219: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   222: ifeq +22 -> 244
          //   225: aload 7
          //   227: aload 7
          //   229: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   232: aload 8
          //   234: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   237: invokestatic 130	java/lang/Math:min	(JJ)J
          //   240: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   243: pop
          //   244: aload 6
          //   246: invokeinterface 135 1 0
          //   251: aload 7
          //   253: lload 4
          //   255: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   258: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   261: pop
          //   262: aload 8
          //   264: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   267: ifeq +10 -> 277
          //   270: aload 7
          //   272: lload_2
          //   273: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   276: pop
          //   277: goto +85 -> 362
          //   280: astore 6
          //   282: aload 7
          //   284: lload 4
          //   286: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   289: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   292: pop
          //   293: aload 8
          //   295: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   298: ifeq +10 -> 308
          //   301: aload 7
          //   303: lload_2
          //   304: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   307: pop
          //   308: aload 6
          //   310: athrow
          //   311: aload 8
          //   313: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   316: ifeq +14 -> 330
          //   319: aload 7
          //   321: aload 8
          //   323: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   326: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   329: pop
          //   330: aload 6
          //   332: invokeinterface 135 1 0
          //   337: aload 7
          //   339: lload 4
          //   341: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   344: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   347: pop
          //   348: aload 8
          //   350: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   353: ifeq +9 -> 362
          //   356: aload 7
          //   358: invokevirtual 138	okio/Timeout:clearDeadline	()Lokio/Timeout;
          //   361: pop
          //   362: goto +33 -> 395
          //   365: astore 6
          //   367: aload 7
          //   369: lload 4
          //   371: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   374: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   377: pop
          //   378: aload 8
          //   380: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   383: ifeq +9 -> 392
          //   386: aload 7
          //   388: invokevirtual 138	okio/Timeout:clearDeadline	()Lokio/Timeout;
          //   391: pop
          //   392: aload 6
          //   394: athrow
          //   395: return
          //   396: new 140	java/lang/NullPointerException
          //   399: astore 6
          //   401: aload 6
          //   403: ldc -114
          //   405: invokespecial 143	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
          //   408: aload 6
          //   410: athrow
          //   411: astore 6
          //   413: aload 8
          //   415: monitorexit
          //   416: aload 6
          //   418: athrow
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	419	0	this	1
          //   25	2	1	bool	boolean
          //   216	88	2	l1	long
          //   176	194	4	l2	long
          //   4	241	6	localObject1	Object
          //   280	51	6	localObject2	Object
          //   365	28	6	localObject3	Object
          //   399	10	6	localNullPointerException	NullPointerException
          //   411	6	6	localObject4	Object
          //   41	346	7	localObject5	Object
          //   13	401	8	localObject6	Object
          // Exception table:
          //   from	to	target	type
          //   244	251	280	finally
          //   330	337	365	finally
          //   18	26	411	finally
          //   34	43	411	finally
          //   55	80	411	finally
          //   83	101	411	finally
          //   101	118	411	finally
          //   123	131	411	finally
          //   131	136	411	finally
          //   396	411	411	finally
        }
        
        /* Error */
        public void flush()
        {
          // Byte code:
          //   0: aconst_null
          //   1: checkcast 6	okio/Sink
          //   4: astore 6
          //   6: aload_0
          //   7: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   10: invokevirtual 52	okio/Pipe:getBuffer$okio	()Lokio/Buffer;
          //   13: astore 7
          //   15: aload 7
          //   17: monitorenter
          //   18: aload_0
          //   19: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   22: invokevirtual 56	okio/Pipe:getSinkClosed$okio	()Z
          //   25: iconst_1
          //   26: ixor
          //   27: ifeq +362 -> 389
          //   30: aload_0
          //   31: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   34: invokevirtual 146	okio/Pipe:getCanceled$okio	()Z
          //   37: ifne +334 -> 371
          //   40: aload_0
          //   41: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   44: invokevirtual 60	okio/Pipe:getFoldedSink$okio	()Lokio/Sink;
          //   47: astore 5
          //   49: aload 5
          //   51: ifnull +6 -> 57
          //   54: goto +57 -> 111
          //   57: aload 6
          //   59: astore 5
          //   61: aload_0
          //   62: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   65: invokevirtual 63	okio/Pipe:getSourceClosed$okio	()Z
          //   68: ifeq +43 -> 111
          //   71: aload_0
          //   72: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   75: invokevirtual 52	okio/Pipe:getBuffer$okio	()Lokio/Buffer;
          //   78: invokevirtual 69	okio/Buffer:size	()J
          //   81: lconst_0
          //   82: lcmp
          //   83: ifgt +10 -> 93
          //   86: aload 6
          //   88: astore 5
          //   90: goto +21 -> 111
          //   93: new 71	java/io/IOException
          //   96: astore 5
          //   98: aload 5
          //   100: ldc 73
          //   102: invokespecial 76	java/io/IOException:<init>	(Ljava/lang/String;)V
          //   105: aload 5
          //   107: checkcast 78	java/lang/Throwable
          //   110: athrow
          //   111: getstatic 91	kotlin/Unit:INSTANCE	Lkotlin/Unit;
          //   114: astore 6
          //   116: aload 7
          //   118: monitorexit
          //   119: aload 5
          //   121: ifnull +249 -> 370
          //   124: aload_0
          //   125: getfield 39	okio/Pipe$sink$1:this$0	Lokio/Pipe;
          //   128: astore 7
          //   130: aload 5
          //   132: invokeinterface 94 1 0
          //   137: astore 6
          //   139: aload 7
          //   141: invokevirtual 97	okio/Pipe:sink	()Lokio/Sink;
          //   144: invokeinterface 94 1 0
          //   149: astore 7
          //   151: aload 6
          //   153: invokevirtual 100	okio/Timeout:timeoutNanos	()J
          //   156: lstore_1
          //   157: aload 6
          //   159: getstatic 104	okio/Timeout:Companion	Lokio/Timeout$Companion;
          //   162: aload 7
          //   164: invokevirtual 100	okio/Timeout:timeoutNanos	()J
          //   167: aload 6
          //   169: invokevirtual 100	okio/Timeout:timeoutNanos	()J
          //   172: invokevirtual 110	okio/Timeout$Companion:minTimeout	(JJ)J
          //   175: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   178: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   181: pop
          //   182: aload 6
          //   184: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   187: ifeq +101 -> 288
          //   190: aload 6
          //   192: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   195: lstore_3
          //   196: aload 7
          //   198: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   201: ifeq +22 -> 223
          //   204: aload 6
          //   206: aload 6
          //   208: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   211: aload 7
          //   213: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   216: invokestatic 130	java/lang/Math:min	(JJ)J
          //   219: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   222: pop
          //   223: aload 5
          //   225: invokeinterface 148 1 0
          //   230: aload 6
          //   232: lload_1
          //   233: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   236: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   239: pop
          //   240: aload 7
          //   242: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   245: ifeq +10 -> 255
          //   248: aload 6
          //   250: lload_3
          //   251: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   254: pop
          //   255: goto +83 -> 338
          //   258: astore 5
          //   260: aload 6
          //   262: lload_1
          //   263: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   266: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   269: pop
          //   270: aload 7
          //   272: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   275: ifeq +10 -> 285
          //   278: aload 6
          //   280: lload_3
          //   281: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   284: pop
          //   285: aload 5
          //   287: athrow
          //   288: aload 7
          //   290: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   293: ifeq +14 -> 307
          //   296: aload 6
          //   298: aload 7
          //   300: invokevirtual 125	okio/Timeout:deadlineNanoTime	()J
          //   303: invokevirtual 133	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
          //   306: pop
          //   307: aload 5
          //   309: invokeinterface 148 1 0
          //   314: aload 6
          //   316: lload_1
          //   317: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   320: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   323: pop
          //   324: aload 7
          //   326: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   329: ifeq +9 -> 338
          //   332: aload 6
          //   334: invokevirtual 138	okio/Timeout:clearDeadline	()Lokio/Timeout;
          //   337: pop
          //   338: goto +32 -> 370
          //   341: astore 5
          //   343: aload 6
          //   345: lload_1
          //   346: getstatic 116	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
          //   349: invokevirtual 119	okio/Timeout:timeout	(JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
          //   352: pop
          //   353: aload 7
          //   355: invokevirtual 122	okio/Timeout:hasDeadline	()Z
          //   358: ifeq +9 -> 367
          //   361: aload 6
          //   363: invokevirtual 138	okio/Timeout:clearDeadline	()Lokio/Timeout;
          //   366: pop
          //   367: aload 5
          //   369: athrow
          //   370: return
          //   371: new 71	java/io/IOException
          //   374: astore 5
          //   376: aload 5
          //   378: ldc -106
          //   380: invokespecial 76	java/io/IOException:<init>	(Ljava/lang/String;)V
          //   383: aload 5
          //   385: checkcast 78	java/lang/Throwable
          //   388: athrow
          //   389: new 152	java/lang/IllegalStateException
          //   392: astore 5
          //   394: aload 5
          //   396: ldc -102
          //   398: invokevirtual 158	java/lang/Object:toString	()Ljava/lang/String;
          //   401: invokespecial 159	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
          //   404: aload 5
          //   406: checkcast 78	java/lang/Throwable
          //   409: athrow
          //   410: astore 5
          //   412: aload 7
          //   414: monitorexit
          //   415: aload 5
          //   417: athrow
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	418	0	this	1
          //   156	190	1	l1	long
          //   195	86	3	l2	long
          //   47	177	5	localObject1	Object
          //   258	50	5	localObject2	Object
          //   341	27	5	localObject3	Object
          //   374	31	5	localObject4	Object
          //   410	6	5	localObject5	Object
          //   4	358	6	localObject6	Object
          //   13	400	7	localObject7	Object
          // Exception table:
          //   from	to	target	type
          //   223	230	258	finally
          //   307	314	341	finally
          //   18	49	410	finally
          //   61	86	410	finally
          //   93	111	410	finally
          //   111	116	410	finally
          //   371	389	410	finally
          //   389	410	410	finally
        }
        
        public Timeout timeout()
        {
          return this.timeout;
        }
        
        public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousBuffer, "source");
          Object localObject2 = (Sink)null;
          synchronized (this.this$0.getBuffer$okio())
          {
            if ((this.this$0.getSinkClosed$okio() ^ true))
            {
              Object localObject1;
              label215:
              label230:
              long l2;
              if (!this.this$0.getCanceled$okio())
              {
                long l1;
                for (;;)
                {
                  localObject1 = localObject2;
                  if (paramAnonymousLong <= 0L) {
                    break label230;
                  }
                  localObject1 = this.this$0.getFoldedSink$okio();
                  if (localObject1 != null) {
                    break label230;
                  }
                  if (this.this$0.getSourceClosed$okio()) {
                    break label215;
                  }
                  l1 = this.this$0.getMaxBufferSize$okio() - this.this$0.getBuffer$okio().size();
                  if (l1 == 0L)
                  {
                    this.timeout.waitUntilNotified(this.this$0.getBuffer$okio());
                    if (this.this$0.getCanceled$okio())
                    {
                      paramAnonymousBuffer = new java/io/IOException;
                      paramAnonymousBuffer.<init>("canceled");
                      throw ((Throwable)paramAnonymousBuffer);
                    }
                  }
                  else
                  {
                    l1 = Math.min(l1, paramAnonymousLong);
                    this.this$0.getBuffer$okio().write(paramAnonymousBuffer, l1);
                    paramAnonymousLong -= l1;
                    localObject1 = this.this$0.getBuffer$okio();
                    if (localObject1 == null) {
                      break;
                    }
                    ((Object)localObject1).notifyAll();
                  }
                }
                paramAnonymousBuffer = new java/lang/NullPointerException;
                paramAnonymousBuffer.<init>("null cannot be cast to non-null type java.lang.Object");
                throw paramAnonymousBuffer;
                paramAnonymousBuffer = new java/io/IOException;
                paramAnonymousBuffer.<init>("source is closed");
                throw ((Throwable)paramAnonymousBuffer);
                localObject2 = Unit.INSTANCE;
                if (localObject1 != null)
                {
                  ??? = this.this$0;
                  localObject2 = ((Sink)localObject1).timeout();
                  ??? = ((Pipe)???).sink().timeout();
                  l2 = ((Timeout)localObject2).timeoutNanos();
                  ((Timeout)localObject2).timeout(Timeout.Companion.minTimeout(((Timeout)???).timeoutNanos(), ((Timeout)localObject2).timeoutNanos()), TimeUnit.NANOSECONDS);
                  if (((Timeout)localObject2).hasDeadline())
                  {
                    l1 = ((Timeout)localObject2).deadlineNanoTime();
                    if (((Timeout)???).hasDeadline()) {
                      ((Timeout)localObject2).deadlineNanoTime(Math.min(((Timeout)localObject2).deadlineNanoTime(), ((Timeout)???).deadlineNanoTime()));
                    }
                  }
                  try
                  {
                    ((Sink)localObject1).write(paramAnonymousBuffer, paramAnonymousLong);
                    ((Timeout)localObject2).timeout(l2, TimeUnit.NANOSECONDS);
                    if (((Timeout)???).hasDeadline()) {
                      ((Timeout)localObject2).deadlineNanoTime(l1);
                    }
                  }
                  finally
                  {
                    ((Timeout)localObject2).timeout(l2, TimeUnit.NANOSECONDS);
                    if (((Timeout)???).hasDeadline()) {
                      ((Timeout)localObject2).deadlineNanoTime(l1);
                    }
                  }
                }
              }
              try
              {
                ((Sink)localObject1).write(paramAnonymousBuffer, paramAnonymousLong);
                ((Timeout)localObject2).timeout(l2, TimeUnit.NANOSECONDS);
                if (((Timeout)???).hasDeadline()) {
                  ((Timeout)localObject2).clearDeadline();
                }
              }
              finally
              {
                ((Timeout)localObject2).timeout(l2, TimeUnit.NANOSECONDS);
                if (((Timeout)???).hasDeadline()) {
                  ((Timeout)localObject2).clearDeadline();
                }
              }
              throw ((Throwable)paramAnonymousBuffer);
            }
            paramAnonymousBuffer = new java/lang/IllegalStateException;
            paramAnonymousBuffer.<init>("closed".toString());
            throw ((Throwable)paramAnonymousBuffer);
          }
        }
      });
      this.source = ((Source)new Source()
      {
        final Pipe this$0;
        private final Timeout timeout = new Timeout();
        
        public void close()
        {
          synchronized (this.this$0.getBuffer$okio())
          {
            this.this$0.setSourceClosed$okio(true);
            Object localObject1 = this.this$0.getBuffer$okio();
            if (localObject1 != null)
            {
              ((Object)localObject1).notifyAll();
              localObject1 = Unit.INSTANCE;
              return;
            }
            localObject1 = new java/lang/NullPointerException;
            ((NullPointerException)localObject1).<init>("null cannot be cast to non-null type java.lang.Object");
            throw ((Throwable)localObject1);
          }
        }
        
        public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousBuffer, "sink");
          synchronized (this.this$0.getBuffer$okio())
          {
            if ((this.this$0.getSourceClosed$okio() ^ true))
            {
              if (!this.this$0.getCanceled$okio())
              {
                while (this.this$0.getBuffer$okio().size() == 0L)
                {
                  boolean bool = this.this$0.getSinkClosed$okio();
                  if (bool) {
                    return -1L;
                  }
                  this.timeout.waitUntilNotified(this.this$0.getBuffer$okio());
                  if (this.this$0.getCanceled$okio())
                  {
                    paramAnonymousBuffer = new java/io/IOException;
                    paramAnonymousBuffer.<init>("canceled");
                    throw ((Throwable)paramAnonymousBuffer);
                  }
                }
                paramAnonymousLong = this.this$0.getBuffer$okio().read(paramAnonymousBuffer, paramAnonymousLong);
                paramAnonymousBuffer = this.this$0.getBuffer$okio();
                if (paramAnonymousBuffer != null)
                {
                  ((Object)paramAnonymousBuffer).notifyAll();
                  return paramAnonymousLong;
                }
                paramAnonymousBuffer = new java/lang/NullPointerException;
                paramAnonymousBuffer.<init>("null cannot be cast to non-null type java.lang.Object");
                throw paramAnonymousBuffer;
              }
              paramAnonymousBuffer = new java/io/IOException;
              paramAnonymousBuffer.<init>("canceled");
              throw ((Throwable)paramAnonymousBuffer);
            }
            paramAnonymousBuffer = new java/lang/IllegalStateException;
            paramAnonymousBuffer.<init>("closed".toString());
            throw ((Throwable)paramAnonymousBuffer);
          }
        }
        
        public Timeout timeout()
        {
          return this.timeout;
        }
      });
      return;
    }
    throw ((Throwable)new IllegalArgumentException(("maxBufferSize < 1: " + paramLong).toString()));
  }
  
  private final void forward(Sink paramSink, Function1<? super Sink, Unit> paramFunction1)
  {
    Timeout localTimeout2 = paramSink.timeout();
    Timeout localTimeout1 = sink().timeout();
    long l2 = localTimeout2.timeoutNanos();
    localTimeout2.timeout(Timeout.Companion.minTimeout(localTimeout1.timeoutNanos(), localTimeout2.timeoutNanos()), TimeUnit.NANOSECONDS);
    long l1;
    if (localTimeout2.hasDeadline())
    {
      l1 = localTimeout2.deadlineNanoTime();
      if (localTimeout1.hasDeadline()) {
        localTimeout2.deadlineNanoTime(Math.min(localTimeout2.deadlineNanoTime(), localTimeout1.deadlineNanoTime()));
      }
    }
    try
    {
      paramFunction1.invoke(paramSink);
      InlineMarker.finallyStart(1);
      localTimeout2.timeout(l2, TimeUnit.NANOSECONDS);
      if (localTimeout1.hasDeadline()) {
        localTimeout2.deadlineNanoTime(l1);
      }
      InlineMarker.finallyEnd(1);
    }
    finally
    {
      InlineMarker.finallyStart(1);
      localTimeout2.timeout(l2, TimeUnit.NANOSECONDS);
      if (localTimeout1.hasDeadline()) {
        localTimeout2.deadlineNanoTime(l1);
      }
      InlineMarker.finallyEnd(1);
    }
    try
    {
      paramFunction1.invoke(paramSink);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      localTimeout2.timeout(l2, TimeUnit.NANOSECONDS);
      if (localTimeout1.hasDeadline()) {
        localTimeout2.clearDeadline();
      }
      InlineMarker.finallyEnd(1);
    }
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="sink", imports={}))
  public final Sink -deprecated_sink()
  {
    return this.sink;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="source", imports={}))
  public final Source -deprecated_source()
  {
    return this.source;
  }
  
  public final void cancel()
  {
    synchronized (this.buffer)
    {
      this.canceled = true;
      this.buffer.clear();
      Object localObject1 = this.buffer;
      if (localObject1 != null)
      {
        ((Object)localObject1).notifyAll();
        localObject1 = Unit.INSTANCE;
        return;
      }
      localObject1 = new java/lang/NullPointerException;
      ((NullPointerException)localObject1).<init>("null cannot be cast to non-null type java.lang.Object");
      throw ((Throwable)localObject1);
    }
  }
  
  public final void fold(Sink arg1)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(???, "sink");
    synchronized (this.buffer)
    {
      for (;;)
      {
        int i;
        if (this.foldedSink == null) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          break label256;
        }
        if (this.canceled) {
          break label236;
        }
        if (this.buffer.exhausted())
        {
          this.sourceClosed = true;
          this.foldedSink = ???;
          return;
        }
        boolean bool = this.sinkClosed;
        Object localObject3 = new okio/Buffer;
        ((Buffer)localObject3).<init>();
        Object localObject4 = this.buffer;
        ((Buffer)localObject3).write((Buffer)localObject4, ((Buffer)localObject4).size());
        localObject4 = this.buffer;
        if (localObject4 != null)
        {
          ((Object)localObject4).notifyAll();
          localObject4 = Unit.INSTANCE;
          try
          {
            ???.write((Buffer)localObject3, ((Buffer)localObject3).size());
            if (bool) {
              ???.close();
            } else {
              ???.flush();
            }
          }
          finally
          {
            synchronized (this.buffer)
            {
              this.sourceClosed = true;
              localObject3 = this.buffer;
              NullPointerException localNullPointerException;
              if (localObject3 == null)
              {
                localNullPointerException = new java/lang/NullPointerException;
                localNullPointerException.<init>("null cannot be cast to non-null type java.lang.Object");
                throw localNullPointerException;
              }
              ((Object)localObject3).notifyAll();
              localObject3 = Unit.INSTANCE;
              throw localNullPointerException;
            }
          }
        }
      }
      ??? = new java/lang/NullPointerException;
      ???.<init>("null cannot be cast to non-null type java.lang.Object");
      throw ???;
      label236:
      this.foldedSink = ???;
      ??? = new java/io/IOException;
      ???.<init>("canceled");
      throw ((Throwable)???);
      label256:
      ??? = new java/lang/IllegalStateException;
      ???.<init>("sink already folded".toString());
      throw ((Throwable)???);
    }
  }
  
  public final Buffer getBuffer$okio()
  {
    return this.buffer;
  }
  
  public final boolean getCanceled$okio()
  {
    return this.canceled;
  }
  
  public final Sink getFoldedSink$okio()
  {
    return this.foldedSink;
  }
  
  public final long getMaxBufferSize$okio()
  {
    return this.maxBufferSize;
  }
  
  public final boolean getSinkClosed$okio()
  {
    return this.sinkClosed;
  }
  
  public final boolean getSourceClosed$okio()
  {
    return this.sourceClosed;
  }
  
  public final void setCanceled$okio(boolean paramBoolean)
  {
    this.canceled = paramBoolean;
  }
  
  public final void setFoldedSink$okio(Sink paramSink)
  {
    this.foldedSink = paramSink;
  }
  
  public final void setSinkClosed$okio(boolean paramBoolean)
  {
    this.sinkClosed = paramBoolean;
  }
  
  public final void setSourceClosed$okio(boolean paramBoolean)
  {
    this.sourceClosed = paramBoolean;
  }
  
  public final Sink sink()
  {
    return this.sink;
  }
  
  public final Source source()
  {
    return this.source;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/Pipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */