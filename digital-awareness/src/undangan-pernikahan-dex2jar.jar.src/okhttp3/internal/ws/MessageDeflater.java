package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.zip.Deflater;
import kotlin.Metadata;
import okio.Buffer;
import okio.ByteString;
import okio.DeflaterSink;
import okio.Sink;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\013\032\0020\fH\026J\016\020\r\032\0020\f2\006\020\016\032\0020\006J\024\020\017\032\0020\003*\0020\0062\006\020\020\032\0020\021H\002R\016\020\005\032\0020\006X\004¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000R\016\020\t\032\0020\nX\004¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\022"}, d2={"Lokhttp3/internal/ws/MessageDeflater;", "Ljava/io/Closeable;", "noContextTakeover", "", "(Z)V", "deflatedBytes", "Lokio/Buffer;", "deflater", "Ljava/util/zip/Deflater;", "deflaterSink", "Lokio/DeflaterSink;", "close", "", "deflate", "buffer", "endsWith", "suffix", "Lokio/ByteString;", "okhttp"}, k=1, mv={1, 4, 0})
public final class MessageDeflater
  implements Closeable
{
  private final Buffer deflatedBytes;
  private final Deflater deflater;
  private final DeflaterSink deflaterSink;
  private final boolean noContextTakeover;
  
  public MessageDeflater(boolean paramBoolean)
  {
    this.noContextTakeover = paramBoolean;
    Buffer localBuffer = new Buffer();
    this.deflatedBytes = localBuffer;
    Deflater localDeflater = new Deflater(-1, true);
    this.deflater = localDeflater;
    this.deflaterSink = new DeflaterSink((Sink)localBuffer, localDeflater);
  }
  
  private final boolean endsWith(Buffer paramBuffer, ByteString paramByteString)
  {
    return paramBuffer.rangeEquals(paramBuffer.size() - paramByteString.size(), paramByteString);
  }
  
  public void close()
    throws IOException
  {
    this.deflaterSink.close();
  }
  
  /* Error */
  public final void deflate(Buffer paramBuffer)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 85
    //   3: invokestatic 91	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: getfield 47	okhttp3/internal/ws/MessageDeflater:deflatedBytes	Lokio/Buffer;
    //   10: invokevirtual 69	okio/Buffer:size	()J
    //   13: lconst_0
    //   14: lcmp
    //   15: ifne +8 -> 23
    //   18: iconst_1
    //   19: istore_2
    //   20: goto +5 -> 25
    //   23: iconst_0
    //   24: istore_2
    //   25: iload_2
    //   26: ifeq +146 -> 172
    //   29: aload_0
    //   30: getfield 42	okhttp3/internal/ws/MessageDeflater:noContextTakeover	Z
    //   33: ifeq +10 -> 43
    //   36: aload_0
    //   37: getfield 54	okhttp3/internal/ws/MessageDeflater:deflater	Ljava/util/zip/Deflater;
    //   40: invokevirtual 94	java/util/zip/Deflater:reset	()V
    //   43: aload_0
    //   44: getfield 63	okhttp3/internal/ws/MessageDeflater:deflaterSink	Lokio/DeflaterSink;
    //   47: aload_1
    //   48: aload_1
    //   49: invokevirtual 69	okio/Buffer:size	()J
    //   52: invokevirtual 98	okio/DeflaterSink:write	(Lokio/Buffer;J)V
    //   55: aload_0
    //   56: getfield 63	okhttp3/internal/ws/MessageDeflater:deflaterSink	Lokio/DeflaterSink;
    //   59: invokevirtual 101	okio/DeflaterSink:flush	()V
    //   62: aload_0
    //   63: aload_0
    //   64: getfield 47	okhttp3/internal/ws/MessageDeflater:deflatedBytes	Lokio/Buffer;
    //   67: invokestatic 107	okhttp3/internal/ws/MessageDeflaterKt:access$getEMPTY_DEFLATE_BLOCK$p	()Lokio/ByteString;
    //   70: invokespecial 109	okhttp3/internal/ws/MessageDeflater:endsWith	(Lokio/Buffer;Lokio/ByteString;)Z
    //   73: ifeq +72 -> 145
    //   76: aload_0
    //   77: getfield 47	okhttp3/internal/ws/MessageDeflater:deflatedBytes	Lokio/Buffer;
    //   80: invokevirtual 69	okio/Buffer:size	()J
    //   83: lstore_3
    //   84: iconst_4
    //   85: i2l
    //   86: lstore 5
    //   88: aload_0
    //   89: getfield 47	okhttp3/internal/ws/MessageDeflater:deflatedBytes	Lokio/Buffer;
    //   92: aconst_null
    //   93: iconst_1
    //   94: aconst_null
    //   95: invokestatic 113	okio/Buffer:readAndWriteUnsafe$default	(Lokio/Buffer;Lokio/Buffer$UnsafeCursor;ILjava/lang/Object;)Lokio/Buffer$UnsafeCursor;
    //   98: checkcast 6	java/io/Closeable
    //   101: astore 7
    //   103: aconst_null
    //   104: checkcast 115	java/lang/Throwable
    //   107: astore 8
    //   109: aload 7
    //   111: checkcast 117	okio/Buffer$UnsafeCursor
    //   114: lload_3
    //   115: lload 5
    //   117: lsub
    //   118: invokevirtual 121	okio/Buffer$UnsafeCursor:resizeBuffer	(J)J
    //   121: pop2
    //   122: aload 7
    //   124: aconst_null
    //   125: invokestatic 127	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   128: goto +26 -> 154
    //   131: astore_1
    //   132: aload_1
    //   133: athrow
    //   134: astore 8
    //   136: aload 7
    //   138: aload_1
    //   139: invokestatic 127	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   142: aload 8
    //   144: athrow
    //   145: aload_0
    //   146: getfield 47	okhttp3/internal/ws/MessageDeflater:deflatedBytes	Lokio/Buffer;
    //   149: iconst_0
    //   150: invokevirtual 131	okio/Buffer:writeByte	(I)Lokio/Buffer;
    //   153: pop
    //   154: aload_0
    //   155: getfield 47	okhttp3/internal/ws/MessageDeflater:deflatedBytes	Lokio/Buffer;
    //   158: astore 7
    //   160: aload_1
    //   161: aload 7
    //   163: aload 7
    //   165: invokevirtual 69	okio/Buffer:size	()J
    //   168: invokevirtual 132	okio/Buffer:write	(Lokio/Buffer;J)V
    //   171: return
    //   172: new 134	java/lang/IllegalArgumentException
    //   175: dup
    //   176: ldc -120
    //   178: invokevirtual 140	java/lang/Object:toString	()Ljava/lang/String;
    //   181: invokespecial 143	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   184: checkcast 115	java/lang/Throwable
    //   187: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	188	0	this	MessageDeflater
    //   0	188	1	paramBuffer	Buffer
    //   19	7	2	i	int
    //   83	32	3	l1	long
    //   86	30	5	l2	long
    //   101	63	7	localObject1	Object
    //   107	1	8	localThrowable	Throwable
    //   134	9	8	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   109	122	131	finally
    //   132	134	134	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/ws/MessageDeflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */