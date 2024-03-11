package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\b\026\030\000 \0332\0020\001:\002\033\034B\005¢\006\002\020\002J\022\020\b\032\0020\t2\b\020\n\032\004\030\0010\tH\001J\006\020\013\032\0020\fJ\006\020\r\032\0020\004J\022\020\016\032\0020\t2\b\020\n\032\004\030\0010\tH\024J\020\020\017\032\0020\0072\006\020\020\032\0020\007H\002J\016\020\021\032\0020\0222\006\020\021\032\0020\022J\016\020\023\032\0020\0242\006\020\023\032\0020\024J\b\020\025\032\0020\fH\024J%\020\026\032\002H\027\"\004\b\000\020\0272\f\020\030\032\b\022\004\022\002H\0270\031H\bø\001\000¢\006\002\020\032R\016\020\003\032\0020\004X\016¢\006\002\n\000R\020\020\005\032\004\030\0010\000X\016¢\006\002\n\000R\016\020\006\032\0020\007X\016¢\006\002\n\000\002\007\n\005\b20\001¨\006\035"}, d2={"Lokio/AsyncTimeout;", "Lokio/Timeout;", "()V", "inQueue", "", "next", "timeoutAt", "", "access$newTimeoutException", "Ljava/io/IOException;", "cause", "enter", "", "exit", "newTimeoutException", "remainingNanos", "now", "sink", "Lokio/Sink;", "source", "Lokio/Source;", "timedOut", "withTimeout", "T", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "Companion", "Watchdog", "okio"}, k=1, mv={1, 4, 0})
public class AsyncTimeout
  extends Timeout
{
  public static final Companion Companion = new Companion(null);
  private static final long IDLE_TIMEOUT_MILLIS;
  private static final long IDLE_TIMEOUT_NANOS;
  private static final int TIMEOUT_WRITE_SIZE = 65536;
  private static AsyncTimeout head;
  private boolean inQueue;
  private AsyncTimeout next;
  private long timeoutAt;
  
  static
  {
    long l = TimeUnit.SECONDS.toMillis(60L);
    IDLE_TIMEOUT_MILLIS = l;
    IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(l);
  }
  
  private final long remainingNanos(long paramLong)
  {
    return this.timeoutAt - paramLong;
  }
  
  public final IOException access$newTimeoutException(IOException paramIOException)
  {
    return newTimeoutException(paramIOException);
  }
  
  public final void enter()
  {
    if ((this.inQueue ^ true))
    {
      long l = timeoutNanos();
      boolean bool = hasDeadline();
      if ((l == 0L) && (!bool)) {
        return;
      }
      this.inQueue = true;
      Companion.access$scheduleTimeout(Companion, this, l, bool);
      return;
    }
    throw ((Throwable)new IllegalStateException("Unbalanced enter/exit".toString()));
  }
  
  public final boolean exit()
  {
    if (!this.inQueue) {
      return false;
    }
    this.inQueue = false;
    return Companion.access$cancelScheduledTimeout(Companion, this);
  }
  
  protected IOException newTimeoutException(IOException paramIOException)
  {
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    if (paramIOException != null) {
      localInterruptedIOException.initCause((Throwable)paramIOException);
    }
    return (IOException)localInterruptedIOException;
  }
  
  public final Sink sink(final Sink paramSink)
  {
    Intrinsics.checkNotNullParameter(paramSink, "sink");
    (Sink)new Sink()
    {
      final AsyncTimeout this$0;
      
      /* Error */
      public void close()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 41	okio/AsyncTimeout$sink$1:this$0	Lokio/AsyncTimeout;
        //   4: astore_2
        //   5: aload_2
        //   6: invokevirtual 53	okio/AsyncTimeout:enter	()V
        //   9: aload_0
        //   10: getfield 43	okio/AsyncTimeout$sink$1:$sink	Lokio/Sink;
        //   13: invokeinterface 55 1 0
        //   18: getstatic 61	kotlin/Unit:INSTANCE	Lkotlin/Unit;
        //   21: astore_1
        //   22: aload_2
        //   23: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   26: ifne +4 -> 30
        //   29: return
        //   30: aload_2
        //   31: aconst_null
        //   32: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   35: checkcast 71	java/lang/Throwable
        //   38: athrow
        //   39: astore_1
        //   40: goto +30 -> 70
        //   43: astore_1
        //   44: aload_2
        //   45: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   48: ifne +11 -> 59
        //   51: aload_1
        //   52: checkcast 71	java/lang/Throwable
        //   55: astore_1
        //   56: goto +12 -> 68
        //   59: aload_2
        //   60: aload_1
        //   61: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   64: checkcast 71	java/lang/Throwable
        //   67: astore_1
        //   68: aload_1
        //   69: athrow
        //   70: aload_2
        //   71: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   74: ifeq +16 -> 90
        //   77: iconst_0
        //   78: ifeq +12 -> 90
        //   81: aload_2
        //   82: aconst_null
        //   83: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   86: checkcast 71	java/lang/Throwable
        //   89: athrow
        //   90: aload_1
        //   91: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	92	0	this	1
        //   21	1	1	localUnit	Unit
        //   39	1	1	localObject	Object
        //   43	9	1	localIOException	IOException
        //   55	36	1	localThrowable	Throwable
        //   4	78	2	localAsyncTimeout	AsyncTimeout
        // Exception table:
        //   from	to	target	type
        //   9	22	39	finally
        //   44	56	39	finally
        //   59	68	39	finally
        //   68	70	39	finally
        //   9	22	43	java/io/IOException
      }
      
      /* Error */
      public void flush()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 41	okio/AsyncTimeout$sink$1:this$0	Lokio/AsyncTimeout;
        //   4: astore_2
        //   5: aload_2
        //   6: invokevirtual 53	okio/AsyncTimeout:enter	()V
        //   9: aload_0
        //   10: getfield 43	okio/AsyncTimeout$sink$1:$sink	Lokio/Sink;
        //   13: invokeinterface 73 1 0
        //   18: getstatic 61	kotlin/Unit:INSTANCE	Lkotlin/Unit;
        //   21: astore_1
        //   22: aload_2
        //   23: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   26: ifne +4 -> 30
        //   29: return
        //   30: aload_2
        //   31: aconst_null
        //   32: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   35: checkcast 71	java/lang/Throwable
        //   38: athrow
        //   39: astore_1
        //   40: goto +30 -> 70
        //   43: astore_1
        //   44: aload_2
        //   45: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   48: ifne +11 -> 59
        //   51: aload_1
        //   52: checkcast 71	java/lang/Throwable
        //   55: astore_1
        //   56: goto +12 -> 68
        //   59: aload_2
        //   60: aload_1
        //   61: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   64: checkcast 71	java/lang/Throwable
        //   67: astore_1
        //   68: aload_1
        //   69: athrow
        //   70: aload_2
        //   71: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   74: ifeq +16 -> 90
        //   77: iconst_0
        //   78: ifeq +12 -> 90
        //   81: aload_2
        //   82: aconst_null
        //   83: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   86: checkcast 71	java/lang/Throwable
        //   89: athrow
        //   90: aload_1
        //   91: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	92	0	this	1
        //   21	1	1	localUnit	Unit
        //   39	1	1	localObject	Object
        //   43	9	1	localIOException	IOException
        //   55	36	1	localThrowable	Throwable
        //   4	78	2	localAsyncTimeout	AsyncTimeout
        // Exception table:
        //   from	to	target	type
        //   9	22	39	finally
        //   44	56	39	finally
        //   59	68	39	finally
        //   68	70	39	finally
        //   9	22	43	java/io/IOException
      }
      
      public AsyncTimeout timeout()
      {
        return this.this$0;
      }
      
      public String toString()
      {
        return "AsyncTimeout.sink(" + paramSink + ')';
      }
      
      /* Error */
      public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
      {
        // Byte code:
        //   0: aload_1
        //   1: ldc 99
        //   3: invokestatic 105	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
        //   6: aload_1
        //   7: invokevirtual 111	okio/Buffer:size	()J
        //   10: lconst_0
        //   11: lload_2
        //   12: invokestatic 117	okio/_Util:checkOffsetAndCount	(JJJ)V
        //   15: lload_2
        //   16: lconst_0
        //   17: lcmp
        //   18: ifle +186 -> 204
        //   21: lconst_0
        //   22: lstore 4
        //   24: aload_1
        //   25: getfield 121	okio/Buffer:head	Lokio/Segment;
        //   28: astore 8
        //   30: aload 8
        //   32: invokestatic 125	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
        //   35: lload 4
        //   37: lstore 6
        //   39: lload 4
        //   41: ldc 126
        //   43: i2l
        //   44: lcmp
        //   45: ifge +48 -> 93
        //   48: lload 4
        //   50: aload 8
        //   52: getfield 132	okio/Segment:limit	I
        //   55: aload 8
        //   57: getfield 135	okio/Segment:pos	I
        //   60: isub
        //   61: i2l
        //   62: ladd
        //   63: lstore 4
        //   65: lload 4
        //   67: lload_2
        //   68: lcmp
        //   69: iflt +9 -> 78
        //   72: lload_2
        //   73: lstore 6
        //   75: goto +18 -> 93
        //   78: aload 8
        //   80: getfield 138	okio/Segment:next	Lokio/Segment;
        //   83: astore 8
        //   85: aload 8
        //   87: invokestatic 125	kotlin/jvm/internal/Intrinsics:checkNotNull	(Ljava/lang/Object;)V
        //   90: goto -55 -> 35
        //   93: aload_0
        //   94: getfield 41	okio/AsyncTimeout$sink$1:this$0	Lokio/AsyncTimeout;
        //   97: astore 8
        //   99: aload 8
        //   101: invokevirtual 53	okio/AsyncTimeout:enter	()V
        //   104: aload_0
        //   105: getfield 43	okio/AsyncTimeout$sink$1:$sink	Lokio/Sink;
        //   108: aload_1
        //   109: lload 6
        //   111: invokeinterface 140 4 0
        //   116: getstatic 61	kotlin/Unit:INSTANCE	Lkotlin/Unit;
        //   119: astore 9
        //   121: aload 8
        //   123: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   126: ifne +11 -> 137
        //   129: lload_2
        //   130: lload 6
        //   132: lsub
        //   133: lstore_2
        //   134: goto -119 -> 15
        //   137: aload 8
        //   139: aconst_null
        //   140: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   143: checkcast 71	java/lang/Throwable
        //   146: athrow
        //   147: astore_1
        //   148: goto +32 -> 180
        //   151: astore_1
        //   152: aload 8
        //   154: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   157: ifne +11 -> 168
        //   160: aload_1
        //   161: checkcast 71	java/lang/Throwable
        //   164: astore_1
        //   165: goto +13 -> 178
        //   168: aload 8
        //   170: aload_1
        //   171: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   174: checkcast 71	java/lang/Throwable
        //   177: astore_1
        //   178: aload_1
        //   179: athrow
        //   180: aload 8
        //   182: invokevirtual 65	okio/AsyncTimeout:exit	()Z
        //   185: ifeq +17 -> 202
        //   188: iconst_0
        //   189: ifeq +13 -> 202
        //   192: aload 8
        //   194: aconst_null
        //   195: invokevirtual 69	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   198: checkcast 71	java/lang/Throwable
        //   201: athrow
        //   202: aload_1
        //   203: athrow
        //   204: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	205	0	this	1
        //   0	205	1	paramAnonymousBuffer	Buffer
        //   0	205	2	paramAnonymousLong	long
        //   22	44	4	l1	long
        //   37	94	6	l2	long
        //   28	165	8	localObject	Object
        //   119	1	9	localUnit	Unit
        // Exception table:
        //   from	to	target	type
        //   104	121	147	finally
        //   152	165	147	finally
        //   168	178	147	finally
        //   178	180	147	finally
        //   104	121	151	java/io/IOException
      }
    };
  }
  
  public final Source source(final Source paramSource)
  {
    Intrinsics.checkNotNullParameter(paramSource, "source");
    (Source)new Source()
    {
      final AsyncTimeout this$0;
      
      /* Error */
      public void close()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 40	okio/AsyncTimeout$source$1:this$0	Lokio/AsyncTimeout;
        //   4: astore_2
        //   5: aload_2
        //   6: invokevirtual 52	okio/AsyncTimeout:enter	()V
        //   9: aload_0
        //   10: getfield 42	okio/AsyncTimeout$source$1:$source	Lokio/Source;
        //   13: invokeinterface 54 1 0
        //   18: getstatic 60	kotlin/Unit:INSTANCE	Lkotlin/Unit;
        //   21: astore_1
        //   22: aload_2
        //   23: invokevirtual 64	okio/AsyncTimeout:exit	()Z
        //   26: ifne +4 -> 30
        //   29: return
        //   30: aload_2
        //   31: aconst_null
        //   32: invokevirtual 68	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   35: checkcast 70	java/lang/Throwable
        //   38: athrow
        //   39: astore_1
        //   40: goto +30 -> 70
        //   43: astore_1
        //   44: aload_2
        //   45: invokevirtual 64	okio/AsyncTimeout:exit	()Z
        //   48: ifne +11 -> 59
        //   51: aload_1
        //   52: checkcast 70	java/lang/Throwable
        //   55: astore_1
        //   56: goto +12 -> 68
        //   59: aload_2
        //   60: aload_1
        //   61: invokevirtual 68	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   64: checkcast 70	java/lang/Throwable
        //   67: astore_1
        //   68: aload_1
        //   69: athrow
        //   70: aload_2
        //   71: invokevirtual 64	okio/AsyncTimeout:exit	()Z
        //   74: ifeq +16 -> 90
        //   77: iconst_0
        //   78: ifeq +12 -> 90
        //   81: aload_2
        //   82: aconst_null
        //   83: invokevirtual 68	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   86: checkcast 70	java/lang/Throwable
        //   89: athrow
        //   90: aload_1
        //   91: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	92	0	this	1
        //   21	1	1	localUnit	Unit
        //   39	1	1	localObject	Object
        //   43	9	1	localIOException	IOException
        //   55	36	1	localThrowable	Throwable
        //   4	78	2	localAsyncTimeout	AsyncTimeout
        // Exception table:
        //   from	to	target	type
        //   9	22	39	finally
        //   44	56	39	finally
        //   59	68	39	finally
        //   68	70	39	finally
        //   9	22	43	java/io/IOException
      }
      
      /* Error */
      public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
      {
        // Byte code:
        //   0: aload_1
        //   1: ldc 72
        //   3: invokestatic 78	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
        //   6: aload_0
        //   7: getfield 40	okio/AsyncTimeout$source$1:this$0	Lokio/AsyncTimeout;
        //   10: astore 4
        //   12: aload 4
        //   14: invokevirtual 52	okio/AsyncTimeout:enter	()V
        //   17: aload_0
        //   18: getfield 42	okio/AsyncTimeout$source$1:$source	Lokio/Source;
        //   21: aload_1
        //   22: lload_2
        //   23: invokeinterface 80 4 0
        //   28: lstore_2
        //   29: aload 4
        //   31: invokevirtual 64	okio/AsyncTimeout:exit	()Z
        //   34: ifne +5 -> 39
        //   37: lload_2
        //   38: lreturn
        //   39: aload 4
        //   41: aconst_null
        //   42: invokevirtual 68	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   45: checkcast 70	java/lang/Throwable
        //   48: athrow
        //   49: astore_1
        //   50: goto +32 -> 82
        //   53: astore_1
        //   54: aload 4
        //   56: invokevirtual 64	okio/AsyncTimeout:exit	()Z
        //   59: ifne +11 -> 70
        //   62: aload_1
        //   63: checkcast 70	java/lang/Throwable
        //   66: astore_1
        //   67: goto +13 -> 80
        //   70: aload 4
        //   72: aload_1
        //   73: invokevirtual 68	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   76: checkcast 70	java/lang/Throwable
        //   79: astore_1
        //   80: aload_1
        //   81: athrow
        //   82: aload 4
        //   84: invokevirtual 64	okio/AsyncTimeout:exit	()Z
        //   87: ifeq +17 -> 104
        //   90: iconst_0
        //   91: ifeq +13 -> 104
        //   94: aload 4
        //   96: aconst_null
        //   97: invokevirtual 68	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
        //   100: checkcast 70	java/lang/Throwable
        //   103: athrow
        //   104: aload_1
        //   105: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	106	0	this	1
        //   0	106	1	paramAnonymousBuffer	Buffer
        //   0	106	2	paramAnonymousLong	long
        //   10	85	4	localAsyncTimeout	AsyncTimeout
        // Exception table:
        //   from	to	target	type
        //   17	29	49	finally
        //   54	67	49	finally
        //   70	80	49	finally
        //   80	82	49	finally
        //   17	29	53	java/io/IOException
      }
      
      public AsyncTimeout timeout()
      {
        return this.this$0;
      }
      
      public String toString()
      {
        return "AsyncTimeout.source(" + paramSource + ')';
      }
    };
  }
  
  protected void timedOut() {}
  
  /* Error */
  public final <T> T withTimeout(kotlin.jvm.functions.Function0<? extends T> paramFunction0)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc -71
    //   3: invokestatic 171	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: invokevirtual 187	okio/AsyncTimeout:enter	()V
    //   10: aload_1
    //   11: invokeinterface 193 1 0
    //   16: astore_1
    //   17: iconst_1
    //   18: invokestatic 199	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   21: aload_0
    //   22: invokevirtual 201	okio/AsyncTimeout:exit	()Z
    //   25: ifne +9 -> 34
    //   28: iconst_1
    //   29: invokestatic 204	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   32: aload_1
    //   33: areturn
    //   34: aload_0
    //   35: aconst_null
    //   36: invokevirtual 206	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
    //   39: checkcast 148	java/lang/Throwable
    //   42: athrow
    //   43: astore_1
    //   44: goto +30 -> 74
    //   47: astore_1
    //   48: aload_0
    //   49: invokevirtual 201	okio/AsyncTimeout:exit	()Z
    //   52: ifne +11 -> 63
    //   55: aload_1
    //   56: checkcast 148	java/lang/Throwable
    //   59: astore_1
    //   60: goto +12 -> 72
    //   63: aload_0
    //   64: aload_1
    //   65: invokevirtual 206	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
    //   68: checkcast 148	java/lang/Throwable
    //   71: astore_1
    //   72: aload_1
    //   73: athrow
    //   74: iconst_1
    //   75: invokestatic 199	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   78: aload_0
    //   79: invokevirtual 201	okio/AsyncTimeout:exit	()Z
    //   82: ifeq +16 -> 98
    //   85: iconst_0
    //   86: ifeq +12 -> 98
    //   89: aload_0
    //   90: aconst_null
    //   91: invokevirtual 206	okio/AsyncTimeout:access$newTimeoutException	(Ljava/io/IOException;)Ljava/io/IOException;
    //   94: checkcast 148	java/lang/Throwable
    //   97: athrow
    //   98: iconst_1
    //   99: invokestatic 204	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   102: aload_1
    //   103: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	104	0	this	AsyncTimeout
    //   0	104	1	paramFunction0	kotlin.jvm.functions.Function0<? extends T>
    // Exception table:
    //   from	to	target	type
    //   10	17	43	finally
    //   48	60	43	finally
    //   63	72	43	finally
    //   72	74	43	finally
    //   10	17	47	java/io/IOException
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\017\020\n\032\004\030\0010\tH\000¢\006\002\b\013J\020\020\f\032\0020\r2\006\020\016\032\0020\tH\002J \020\017\032\0020\0202\006\020\016\032\0020\t2\006\020\021\032\0020\0042\006\020\022\032\0020\rH\002R\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\005\032\0020\004X\004¢\006\002\n\000R\016\020\006\032\0020\007XT¢\006\002\n\000R\020\020\b\032\004\030\0010\tX\016¢\006\002\n\000¨\006\023"}, d2={"Lokio/AsyncTimeout$Companion;", "", "()V", "IDLE_TIMEOUT_MILLIS", "", "IDLE_TIMEOUT_NANOS", "TIMEOUT_WRITE_SIZE", "", "head", "Lokio/AsyncTimeout;", "awaitTimeout", "awaitTimeout$okio", "cancelScheduledTimeout", "", "node", "scheduleTimeout", "", "timeoutNanos", "hasDeadline", "okio"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final boolean cancelScheduledTimeout(AsyncTimeout paramAsyncTimeout)
    {
      try
      {
        for (AsyncTimeout localAsyncTimeout = AsyncTimeout.access$getHead$cp(); localAsyncTimeout != null; localAsyncTimeout = AsyncTimeout.access$getNext$p(localAsyncTimeout)) {
          if (AsyncTimeout.access$getNext$p(localAsyncTimeout) == paramAsyncTimeout)
          {
            AsyncTimeout.access$setNext$p(localAsyncTimeout, AsyncTimeout.access$getNext$p(paramAsyncTimeout));
            localAsyncTimeout = (AsyncTimeout)null;
            AsyncTimeout.access$setNext$p(paramAsyncTimeout, null);
            return false;
          }
        }
        return true;
      }
      finally {}
    }
    
    private final void scheduleTimeout(AsyncTimeout paramAsyncTimeout, long paramLong, boolean paramBoolean)
    {
      try
      {
        if (AsyncTimeout.access$getHead$cp() == null)
        {
          localObject = new okio/AsyncTimeout;
          ((AsyncTimeout)localObject).<init>();
          AsyncTimeout.access$setHead$cp((AsyncTimeout)localObject);
          localObject = new okio/AsyncTimeout$Watchdog;
          ((AsyncTimeout.Watchdog)localObject).<init>();
          ((AsyncTimeout.Watchdog)localObject).start();
        }
        long l = System.nanoTime();
        if ((paramLong != 0L) && (paramBoolean))
        {
          AsyncTimeout.access$setTimeoutAt$p(paramAsyncTimeout, Math.min(paramLong, paramAsyncTimeout.deadlineNanoTime() - l) + l);
        }
        else if (paramLong != 0L)
        {
          AsyncTimeout.access$setTimeoutAt$p(paramAsyncTimeout, l + paramLong);
        }
        else
        {
          if (!paramBoolean) {
            break label212;
          }
          AsyncTimeout.access$setTimeoutAt$p(paramAsyncTimeout, paramAsyncTimeout.deadlineNanoTime());
        }
        paramLong = AsyncTimeout.access$remainingNanos(paramAsyncTimeout, l);
        Object localObject = AsyncTimeout.access$getHead$cp();
        Intrinsics.checkNotNull(localObject);
        while (AsyncTimeout.access$getNext$p((AsyncTimeout)localObject) != null)
        {
          AsyncTimeout localAsyncTimeout = AsyncTimeout.access$getNext$p((AsyncTimeout)localObject);
          Intrinsics.checkNotNull(localAsyncTimeout);
          if (paramLong < AsyncTimeout.access$remainingNanos(localAsyncTimeout, l)) {
            break;
          }
          localObject = AsyncTimeout.access$getNext$p((AsyncTimeout)localObject);
          Intrinsics.checkNotNull(localObject);
        }
        AsyncTimeout.access$setNext$p(paramAsyncTimeout, AsyncTimeout.access$getNext$p((AsyncTimeout)localObject));
        AsyncTimeout.access$setNext$p((AsyncTimeout)localObject, paramAsyncTimeout);
        if (localObject == AsyncTimeout.access$getHead$cp()) {
          ((Object)AsyncTimeout.class).notify();
        }
        paramAsyncTimeout = Unit.INSTANCE;
        return;
        label212:
        paramAsyncTimeout = new java/lang/AssertionError;
        paramAsyncTimeout.<init>();
        throw ((Throwable)paramAsyncTimeout);
      }
      finally {}
    }
    
    public final AsyncTimeout awaitTimeout$okio()
      throws InterruptedException
    {
      AsyncTimeout localAsyncTimeout1 = AsyncTimeout.access$getHead$cp();
      Intrinsics.checkNotNull(localAsyncTimeout1);
      AsyncTimeout localAsyncTimeout2 = AsyncTimeout.access$getNext$p(localAsyncTimeout1);
      localAsyncTimeout1 = null;
      long l1;
      if (localAsyncTimeout2 == null)
      {
        l1 = System.nanoTime();
        ((Object)AsyncTimeout.class).wait(AsyncTimeout.access$getIDLE_TIMEOUT_MILLIS$cp());
        localAsyncTimeout2 = AsyncTimeout.access$getHead$cp();
        Intrinsics.checkNotNull(localAsyncTimeout2);
        if ((AsyncTimeout.access$getNext$p(localAsyncTimeout2) == null) && (System.nanoTime() - l1 >= AsyncTimeout.access$getIDLE_TIMEOUT_NANOS$cp())) {
          localAsyncTimeout1 = AsyncTimeout.access$getHead$cp();
        }
        return localAsyncTimeout1;
      }
      long l2 = AsyncTimeout.access$remainingNanos(localAsyncTimeout2, System.nanoTime());
      if (l2 > 0L)
      {
        l1 = l2 / 1000000L;
        ((Object)AsyncTimeout.class).wait(l1, (int)(l2 - 1000000L * l1));
        return null;
      }
      localAsyncTimeout1 = AsyncTimeout.access$getHead$cp();
      Intrinsics.checkNotNull(localAsyncTimeout1);
      AsyncTimeout.access$setNext$p(localAsyncTimeout1, AsyncTimeout.access$getNext$p(localAsyncTimeout2));
      localAsyncTimeout1 = (AsyncTimeout)null;
      AsyncTimeout.access$setNext$p(localAsyncTimeout2, null);
      return localAsyncTimeout2;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\b\002\030\0002\0020\001B\007\b\000¢\006\002\020\002J\b\020\003\032\0020\004H\026¨\006\005"}, d2={"Lokio/AsyncTimeout$Watchdog;", "Ljava/lang/Thread;", "()V", "run", "", "okio"}, k=1, mv={1, 4, 0})
  private static final class Watchdog
    extends Thread
  {
    public Watchdog()
    {
      super();
      setDaemon(true);
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          AsyncTimeout localAsyncTimeout = (AsyncTimeout)null;
          try
          {
            localAsyncTimeout = AsyncTimeout.Companion.awaitTimeout$okio();
            if (localAsyncTimeout == AsyncTimeout.access$getHead$cp())
            {
              localAsyncTimeout = (AsyncTimeout)null;
              AsyncTimeout.access$setHead$cp(null);
              return;
            }
            Unit localUnit = Unit.INSTANCE;
            if (localAsyncTimeout != null) {
              localAsyncTimeout.timedOut();
            }
          }
          finally {}
        }
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/AsyncTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */