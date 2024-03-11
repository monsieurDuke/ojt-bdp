package kotlin.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\\\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\022\n\000\n\002\030\002\n\000\n\002\020\031\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\030\002\n\002\b\007\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\003\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\024\032\0020\004H\002J\020\020\025\032\0020\0042\006\020\026\032\0020\020H\002J\030\020\027\032\0020\0042\006\020\030\032\0020\0042\006\020\031\032\0020\004H\002J\030\020\032\032\004\030\0010\0332\006\020\034\032\0020\0352\006\020\036\032\0020\037J\b\020 \032\0020!H\002J\b\020\"\032\0020!H\002J\020\020#\032\0020!2\006\020\036\032\0020\037H\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000R\016\020\t\032\0020\nX\004¢\006\002\n\000R\016\020\013\032\0020\fX\004¢\006\002\n\000R\016\020\r\032\0020\016X.¢\006\002\n\000R\016\020\017\032\0020\020X\016¢\006\002\n\000R\022\020\021\032\0060\022j\002`\023X\004¢\006\002\n\000¨\006$"}, d2={"Lkotlin/io/LineReader;", "", "()V", "BUFFER_SIZE", "", "byteBuf", "Ljava/nio/ByteBuffer;", "bytes", "", "charBuf", "Ljava/nio/CharBuffer;", "chars", "", "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "compactBytes", "decode", "endOfInput", "decodeEndOfInput", "nBytes", "nChars", "readLine", "", "inputStream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "resetAll", "", "trimStringBuilder", "updateCharset", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class LineReader
{
  private static final int BUFFER_SIZE = 32;
  public static final LineReader INSTANCE = new LineReader();
  private static final ByteBuffer byteBuf;
  private static final byte[] bytes;
  private static final CharBuffer charBuf;
  private static final char[] chars;
  private static CharsetDecoder decoder;
  private static boolean directEOL;
  private static final StringBuilder sb = new StringBuilder();
  
  static
  {
    Object localObject2 = new byte[32];
    bytes = (byte[])localObject2;
    Object localObject1 = new char[32];
    chars = (char[])localObject1;
    localObject2 = ByteBuffer.wrap((byte[])localObject2);
    Intrinsics.checkNotNullExpressionValue(localObject2, "wrap(bytes)");
    byteBuf = (ByteBuffer)localObject2;
    localObject1 = CharBuffer.wrap((char[])localObject1);
    Intrinsics.checkNotNullExpressionValue(localObject1, "wrap(chars)");
    charBuf = (CharBuffer)localObject1;
  }
  
  private final int compactBytes()
  {
    ByteBuffer localByteBuffer = byteBuf;
    localByteBuffer.compact();
    int i = localByteBuffer.position();
    localByteBuffer.position(0);
    return i;
  }
  
  private final int decode(boolean paramBoolean)
  {
    for (;;)
    {
      Object localObject2 = decoder;
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        Intrinsics.throwUninitializedPropertyAccessException("decoder");
        localObject1 = null;
      }
      Object localObject3 = byteBuf;
      localObject2 = charBuf;
      localObject1 = ((CharsetDecoder)localObject1).decode((ByteBuffer)localObject3, (CharBuffer)localObject2, paramBoolean);
      Intrinsics.checkNotNullExpressionValue(localObject1, "decoder.decode(byteBuf, charBuf, endOfInput)");
      if (((CoderResult)localObject1).isError())
      {
        resetAll();
        ((CoderResult)localObject1).throwException();
      }
      int i = ((CharBuffer)localObject2).position();
      if (!((CoderResult)localObject1).isOverflow()) {
        return i;
      }
      localObject1 = sb;
      localObject3 = chars;
      ((StringBuilder)localObject1).append((char[])localObject3, 0, i - 1);
      ((CharBuffer)localObject2).position(0);
      ((CharBuffer)localObject2).limit(32);
      ((CharBuffer)localObject2).put(localObject3[(i - 1)]);
    }
  }
  
  private final int decodeEndOfInput(int paramInt1, int paramInt2)
  {
    ByteBuffer localByteBuffer = byteBuf;
    localByteBuffer.limit(paramInt1);
    charBuf.position(paramInt2);
    paramInt1 = decode(true);
    CharsetDecoder localCharsetDecoder2 = decoder;
    CharsetDecoder localCharsetDecoder1 = localCharsetDecoder2;
    if (localCharsetDecoder2 == null)
    {
      Intrinsics.throwUninitializedPropertyAccessException("decoder");
      localCharsetDecoder1 = null;
    }
    localCharsetDecoder1.reset();
    localByteBuffer.position(0);
    return paramInt1;
  }
  
  private final void resetAll()
  {
    CharsetDecoder localCharsetDecoder2 = decoder;
    CharsetDecoder localCharsetDecoder1 = localCharsetDecoder2;
    if (localCharsetDecoder2 == null)
    {
      Intrinsics.throwUninitializedPropertyAccessException("decoder");
      localCharsetDecoder1 = null;
    }
    localCharsetDecoder1.reset();
    byteBuf.position(0);
    sb.setLength(0);
  }
  
  private final void trimStringBuilder()
  {
    StringBuilder localStringBuilder = sb;
    localStringBuilder.setLength(32);
    localStringBuilder.trimToSize();
  }
  
  private final void updateCharset(Charset paramCharset)
  {
    paramCharset = paramCharset.newDecoder();
    Intrinsics.checkNotNullExpressionValue(paramCharset, "charset.newDecoder()");
    decoder = paramCharset;
    ByteBuffer localByteBuffer = byteBuf;
    localByteBuffer.clear();
    CharBuffer localCharBuffer = charBuf;
    localCharBuffer.clear();
    localByteBuffer.put((byte)10);
    localByteBuffer.flip();
    CharsetDecoder localCharsetDecoder = decoder;
    paramCharset = localCharsetDecoder;
    if (localCharsetDecoder == null)
    {
      Intrinsics.throwUninitializedPropertyAccessException("decoder");
      paramCharset = null;
    }
    boolean bool2 = false;
    paramCharset.decode(localByteBuffer, localCharBuffer, false);
    boolean bool1 = bool2;
    if (localCharBuffer.position() == 1)
    {
      bool1 = bool2;
      if (localCharBuffer.get(0) == '\n') {
        bool1 = true;
      }
    }
    directEOL = bool1;
    resetAll();
  }
  
  /* Error */
  public final String readLine(java.io.InputStream paramInputStream, Charset paramCharset)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc -68
    //   5: invokestatic 191	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload_2
    //   9: ldc -64
    //   11: invokestatic 191	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   14: getstatic 107	kotlin/io/LineReader:decoder	Ljava/nio/charset/CharsetDecoder;
    //   17: astore 9
    //   19: aload 9
    //   21: ifnull +32 -> 53
    //   24: aload 9
    //   26: astore 8
    //   28: aload 9
    //   30: ifnonnull +11 -> 41
    //   33: ldc 108
    //   35: invokestatic 112	kotlin/jvm/internal/Intrinsics:throwUninitializedPropertyAccessException	(Ljava/lang/String;)V
    //   38: aconst_null
    //   39: astore 8
    //   41: aload 8
    //   43: invokevirtual 195	java/nio/charset/CharsetDecoder:charset	()Ljava/nio/charset/Charset;
    //   46: aload_2
    //   47: invokestatic 199	kotlin/jvm/internal/Intrinsics:areEqual	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   50: ifne +8 -> 58
    //   53: aload_0
    //   54: aload_2
    //   55: invokespecial 201	kotlin/io/LineReader:updateCharset	(Ljava/nio/charset/Charset;)V
    //   58: iconst_0
    //   59: istore_3
    //   60: iconst_0
    //   61: istore 4
    //   63: aload_1
    //   64: invokevirtual 206	java/io/InputStream:read	()I
    //   67: istore 7
    //   69: iconst_1
    //   70: istore 6
    //   72: iload 7
    //   74: iconst_m1
    //   75: if_icmpne +60 -> 135
    //   78: getstatic 91	kotlin/io/LineReader:sb	Ljava/lang/StringBuilder;
    //   81: checkcast 208	java/lang/CharSequence
    //   84: invokeinterface 211 1 0
    //   89: istore 5
    //   91: iload 5
    //   93: ifne +9 -> 102
    //   96: iconst_1
    //   97: istore 5
    //   99: goto +6 -> 105
    //   102: iconst_0
    //   103: istore 5
    //   105: iload 5
    //   107: ifeq +16 -> 123
    //   110: iload_3
    //   111: ifne +12 -> 123
    //   114: iload 4
    //   116: ifne +7 -> 123
    //   119: aload_0
    //   120: monitorexit
    //   121: aconst_null
    //   122: areturn
    //   123: aload_0
    //   124: iload_3
    //   125: iload 4
    //   127: invokespecial 213	kotlin/io/LineReader:decodeEndOfInput	(II)I
    //   130: istore 4
    //   132: goto +98 -> 230
    //   135: getstatic 59	kotlin/io/LineReader:bytes	[B
    //   138: astore_2
    //   139: iload_3
    //   140: iconst_1
    //   141: iadd
    //   142: istore 5
    //   144: aload_2
    //   145: iload_3
    //   146: iload 7
    //   148: i2b
    //   149: bastore
    //   150: iload 7
    //   152: bipush 10
    //   154: if_icmpeq +25 -> 179
    //   157: iload 5
    //   159: bipush 32
    //   161: if_icmpeq +18 -> 179
    //   164: getstatic 186	kotlin/io/LineReader:directEOL	Z
    //   167: ifne +6 -> 173
    //   170: goto +9 -> 179
    //   173: iload 5
    //   175: istore_3
    //   176: goto -113 -> 63
    //   179: getstatic 77	kotlin/io/LineReader:byteBuf	Ljava/nio/ByteBuffer;
    //   182: astore_2
    //   183: aload_2
    //   184: iload 5
    //   186: invokevirtual 148	java/nio/ByteBuffer:limit	(I)Ljava/nio/Buffer;
    //   189: pop
    //   190: getstatic 86	kotlin/io/LineReader:charBuf	Ljava/nio/CharBuffer;
    //   193: iload 4
    //   195: invokevirtual 139	java/nio/CharBuffer:position	(I)Ljava/nio/Buffer;
    //   198: pop
    //   199: aload_0
    //   200: iconst_0
    //   201: invokespecial 150	kotlin/io/LineReader:decode	(Z)I
    //   204: istore 4
    //   206: iload 4
    //   208: ifle +181 -> 389
    //   211: getstatic 61	kotlin/io/LineReader:chars	[C
    //   214: iload 4
    //   216: iconst_1
    //   217: isub
    //   218: caload
    //   219: bipush 10
    //   221: if_icmpne +168 -> 389
    //   224: aload_2
    //   225: iconst_0
    //   226: invokevirtual 104	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
    //   229: pop
    //   230: iload 4
    //   232: istore_3
    //   233: iload 4
    //   235: ifle +51 -> 286
    //   238: getstatic 61	kotlin/io/LineReader:chars	[C
    //   241: astore_1
    //   242: iload 4
    //   244: istore_3
    //   245: aload_1
    //   246: iload 4
    //   248: iconst_1
    //   249: isub
    //   250: caload
    //   251: bipush 10
    //   253: if_icmpne +33 -> 286
    //   256: iinc 4 -1
    //   259: iload 4
    //   261: istore_3
    //   262: iload 4
    //   264: ifle +22 -> 286
    //   267: iload 4
    //   269: istore_3
    //   270: aload_1
    //   271: iload 4
    //   273: iconst_1
    //   274: isub
    //   275: caload
    //   276: bipush 13
    //   278: if_icmpne +8 -> 286
    //   281: iload 4
    //   283: iconst_1
    //   284: isub
    //   285: istore_3
    //   286: getstatic 91	kotlin/io/LineReader:sb	Ljava/lang/StringBuilder;
    //   289: astore_1
    //   290: aload_1
    //   291: checkcast 208	java/lang/CharSequence
    //   294: invokeinterface 211 1 0
    //   299: ifne +10 -> 309
    //   302: iload 6
    //   304: istore 4
    //   306: goto +6 -> 312
    //   309: iconst_0
    //   310: istore 4
    //   312: iload 4
    //   314: ifeq +32 -> 346
    //   317: new 215	java/lang/String
    //   320: astore_1
    //   321: aload_1
    //   322: getstatic 61	kotlin/io/LineReader:chars	[C
    //   325: iconst_0
    //   326: iload_3
    //   327: invokespecial 218	java/lang/String:<init>	([CII)V
    //   330: aload_1
    //   331: invokestatic 224	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   334: aload_1
    //   335: invokestatic 227	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   338: aload_1
    //   339: invokestatic 230	mt/Log229316:a	(Ljava/lang/Object;)V
    //   342: aload_0
    //   343: monitorexit
    //   344: aload_1
    //   345: areturn
    //   346: aload_1
    //   347: getstatic 61	kotlin/io/LineReader:chars	[C
    //   350: iconst_0
    //   351: iload_3
    //   352: invokevirtual 138	java/lang/StringBuilder:append	([CII)Ljava/lang/StringBuilder;
    //   355: pop
    //   356: aload_1
    //   357: invokevirtual 234	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   360: astore_2
    //   361: aload_2
    //   362: ldc -20
    //   364: invokestatic 75	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
    //   367: aload_1
    //   368: invokevirtual 237	java/lang/StringBuilder:length	()I
    //   371: bipush 32
    //   373: if_icmple +7 -> 380
    //   376: aload_0
    //   377: invokespecial 239	kotlin/io/LineReader:trimStringBuilder	()V
    //   380: aload_1
    //   381: iconst_0
    //   382: invokevirtual 158	java/lang/StringBuilder:setLength	(I)V
    //   385: aload_0
    //   386: monitorexit
    //   387: aload_2
    //   388: areturn
    //   389: aload_0
    //   390: invokespecial 241	kotlin/io/LineReader:compactBytes	()I
    //   393: istore_3
    //   394: goto -331 -> 63
    //   397: astore_1
    //   398: aload_0
    //   399: monitorexit
    //   400: aload_1
    //   401: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	402	0	this	LineReader
    //   0	402	1	paramInputStream	java.io.InputStream
    //   0	402	2	paramCharset	Charset
    //   59	335	3	i	int
    //   61	252	4	j	int
    //   89	96	5	k	int
    //   70	233	6	m	int
    //   67	88	7	n	int
    //   26	16	8	localCharsetDecoder1	CharsetDecoder
    //   17	12	9	localCharsetDecoder2	CharsetDecoder
    // Exception table:
    //   from	to	target	type
    //   2	19	397	finally
    //   33	38	397	finally
    //   41	53	397	finally
    //   53	58	397	finally
    //   63	69	397	finally
    //   78	91	397	finally
    //   123	132	397	finally
    //   135	139	397	finally
    //   164	170	397	finally
    //   179	206	397	finally
    //   211	230	397	finally
    //   238	242	397	finally
    //   286	302	397	finally
    //   317	342	397	finally
    //   346	380	397	finally
    //   380	385	397	finally
    //   389	394	397	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/LineReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */