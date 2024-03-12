package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.RealBufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\000j\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\t\n\000\n\002\020\005\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\n\002\020\022\n\002\030\002\n\000\n\002\030\002\n\002\b\013\n\002\020\n\n\002\b\002\n\002\020\016\n\002\b\b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\032\r\020\000\032\0020\001*\0020\002H\b\032\r\020\003\032\0020\004*\0020\002H\b\032%\020\005\032\0020\006*\0020\0022\006\020\007\032\0020\b2\006\020\t\032\0020\0062\006\020\n\032\0020\006H\b\032\035\020\005\032\0020\006*\0020\0022\006\020\013\032\0020\f2\006\020\t\032\0020\006H\b\032\035\020\r\032\0020\006*\0020\0022\006\020\016\032\0020\f2\006\020\t\032\0020\006H\b\032\r\020\017\032\0020\020*\0020\002H\b\032-\020\021\032\0020\004*\0020\0022\006\020\022\032\0020\0062\006\020\013\032\0020\f2\006\020\023\032\0020\0242\006\020\025\032\0020\024H\b\032%\020\026\032\0020\024*\0020\0022\006\020\027\032\0020\0302\006\020\022\032\0020\0242\006\020\025\032\0020\024H\b\032\035\020\026\032\0020\006*\0020\0022\006\020\027\032\0020\0312\006\020\025\032\0020\006H\b\032\025\020\032\032\0020\006*\0020\0022\006\020\027\032\0020\033H\b\032\r\020\034\032\0020\b*\0020\002H\b\032\r\020\035\032\0020\030*\0020\002H\b\032\025\020\035\032\0020\030*\0020\0022\006\020\025\032\0020\006H\b\032\r\020\036\032\0020\f*\0020\002H\b\032\025\020\036\032\0020\f*\0020\0022\006\020\025\032\0020\006H\b\032\r\020\037\032\0020\006*\0020\002H\b\032\025\020 \032\0020\001*\0020\0022\006\020\027\032\0020\030H\b\032\035\020 \032\0020\001*\0020\0022\006\020\027\032\0020\0312\006\020\025\032\0020\006H\b\032\r\020!\032\0020\006*\0020\002H\b\032\r\020\"\032\0020\024*\0020\002H\b\032\r\020#\032\0020\024*\0020\002H\b\032\r\020$\032\0020\006*\0020\002H\b\032\r\020%\032\0020\006*\0020\002H\b\032\r\020&\032\0020'*\0020\002H\b\032\r\020(\032\0020'*\0020\002H\b\032\r\020)\032\0020**\0020\002H\b\032\025\020)\032\0020**\0020\0022\006\020\025\032\0020\006H\b\032\r\020+\032\0020\024*\0020\002H\b\032\017\020,\032\004\030\0010**\0020\002H\b\032\025\020-\032\0020**\0020\0022\006\020.\032\0020\006H\b\032\025\020/\032\0020\004*\0020\0022\006\020\025\032\0020\006H\b\032\025\0200\032\0020\001*\0020\0022\006\020\025\032\0020\006H\b\032\025\0201\032\0020\024*\0020\0022\006\0202\032\00203H\b\032\025\0204\032\0020\001*\0020\0022\006\020\025\032\0020\006H\b\032\r\0205\032\00206*\0020\002H\b\032\r\0207\032\0020**\0020\002H\b¨\0068"}, d2={"commonClose", "", "Lokio/RealBufferedSource;", "commonExhausted", "", "commonIndexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonPeek", "Lokio/BufferedSource;", "commonRangeEquals", "offset", "bytesOffset", "", "byteCount", "commonRead", "sink", "", "Lokio/Buffer;", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadShort", "", "commonReadShortLe", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonRequest", "commonRequire", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"}, k=2, mv={1, 4, 0})
public final class RealBufferedSourceKt
{
  public static final void commonClose(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonClose");
    if (paramRealBufferedSource.closed) {
      return;
    }
    paramRealBufferedSource.closed = true;
    paramRealBufferedSource.source.close();
    paramRealBufferedSource.bufferField.clear();
  }
  
  public static final boolean commonExhausted(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonExhausted");
    boolean bool2 = paramRealBufferedSource.closed;
    boolean bool1 = true;
    if ((bool2 ^ true))
    {
      if ((!paramRealBufferedSource.bufferField.exhausted()) || (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') != -1L)) {
        bool1 = false;
      }
      return bool1;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public static final long commonIndexOf(RealBufferedSource paramRealBufferedSource, byte paramByte, long paramLong1, long paramLong2)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonIndexOf");
    boolean bool = paramRealBufferedSource.closed;
    int i = 1;
    if ((bool ^ true))
    {
      if ((0L > paramLong1) || (paramLong2 < paramLong1)) {
        i = 0;
      }
      if (i != 0)
      {
        while (paramLong1 < paramLong2)
        {
          long l = paramRealBufferedSource.bufferField.indexOf(paramByte, paramLong1, paramLong2);
          if (l != -1L) {
            return l;
          }
          l = paramRealBufferedSource.bufferField.size();
          if ((l < paramLong2) && (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') != -1L)) {
            paramLong1 = Math.max(paramLong1, l);
          } else {
            return -1L;
          }
        }
        return -1L;
      }
      throw ((Throwable)new IllegalArgumentException(("fromIndex=" + paramLong1 + " toIndex=" + paramLong2).toString()));
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public static final long commonIndexOf(RealBufferedSource paramRealBufferedSource, ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonIndexOf");
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    if ((paramRealBufferedSource.closed ^ true)) {
      for (;;)
      {
        long l = paramRealBufferedSource.bufferField.indexOf(paramByteString, paramLong);
        if (l != -1L) {
          return l;
        }
        l = paramRealBufferedSource.bufferField.size();
        if (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') == -1L) {
          return -1L;
        }
        paramLong = Math.max(paramLong, l - paramByteString.size() + 1L);
      }
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public static final long commonIndexOfElement(RealBufferedSource paramRealBufferedSource, ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonIndexOfElement");
    Intrinsics.checkNotNullParameter(paramByteString, "targetBytes");
    if ((paramRealBufferedSource.closed ^ true)) {
      for (;;)
      {
        long l = paramRealBufferedSource.bufferField.indexOfElement(paramByteString, paramLong);
        if (l != -1L) {
          return l;
        }
        l = paramRealBufferedSource.bufferField.size();
        if (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') == -1L) {
          return -1L;
        }
        paramLong = Math.max(paramLong, l);
      }
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public static final BufferedSource commonPeek(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonPeek");
    return Okio.buffer((Source)new PeekSource((BufferedSource)paramRealBufferedSource));
  }
  
  public static final boolean commonRangeEquals(RealBufferedSource paramRealBufferedSource, long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonRangeEquals");
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    if ((paramRealBufferedSource.closed ^ true))
    {
      if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (paramByteString.size() - paramInt1 >= paramInt2))
      {
        for (int i = 0; i < paramInt2; i++)
        {
          long l = i + paramLong;
          if (!paramRealBufferedSource.request(1L + l)) {
            return false;
          }
          if (paramRealBufferedSource.bufferField.getByte(l) != paramByteString.getByte(paramInt1 + i)) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public static final int commonRead(RealBufferedSource paramRealBufferedSource, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonRead");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    if ((paramRealBufferedSource.bufferField.size() == 0L) && (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') == -1L)) {
      return -1;
    }
    long l = paramRealBufferedSource.bufferField.size();
    paramInt2 = (int)Math.min(paramInt2, l);
    return paramRealBufferedSource.bufferField.read(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public static final long commonRead(RealBufferedSource paramRealBufferedSource, Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonRead");
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if ((paramRealBufferedSource.closed ^ true))
      {
        if ((paramRealBufferedSource.bufferField.size() == 0L) && (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') == -1L)) {
          return -1L;
        }
        paramLong = Math.min(paramLong, paramRealBufferedSource.bufferField.size());
        return paramRealBufferedSource.bufferField.read(paramBuffer, paramLong);
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public static final long commonReadAll(RealBufferedSource paramRealBufferedSource, Sink paramSink)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadAll");
    Intrinsics.checkNotNullParameter(paramSink, "sink");
    for (long l1 = 0L; paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') != -1L; l1 = l2)
    {
      long l3 = paramRealBufferedSource.bufferField.completeSegmentByteCount();
      l2 = l1;
      if (l3 > 0L)
      {
        l2 = l1 + l3;
        paramSink.write(paramRealBufferedSource.bufferField, l3);
      }
    }
    long l2 = l1;
    if (paramRealBufferedSource.bufferField.size() > 0L)
    {
      l2 = l1 + paramRealBufferedSource.bufferField.size();
      paramSink.write(paramRealBufferedSource.bufferField, paramRealBufferedSource.bufferField.size());
    }
    return l2;
  }
  
  public static final byte commonReadByte(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadByte");
    paramRealBufferedSource.require(1L);
    return paramRealBufferedSource.bufferField.readByte();
  }
  
  public static final byte[] commonReadByteArray(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadByteArray");
    paramRealBufferedSource.bufferField.writeAll(paramRealBufferedSource.source);
    return paramRealBufferedSource.bufferField.readByteArray();
  }
  
  public static final byte[] commonReadByteArray(RealBufferedSource paramRealBufferedSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadByteArray");
    paramRealBufferedSource.require(paramLong);
    return paramRealBufferedSource.bufferField.readByteArray(paramLong);
  }
  
  public static final ByteString commonReadByteString(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadByteString");
    paramRealBufferedSource.bufferField.writeAll(paramRealBufferedSource.source);
    return paramRealBufferedSource.bufferField.readByteString();
  }
  
  public static final ByteString commonReadByteString(RealBufferedSource paramRealBufferedSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadByteString");
    paramRealBufferedSource.require(paramLong);
    return paramRealBufferedSource.bufferField.readByteString(paramLong);
  }
  
  public static final long commonReadDecimalLong(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadDecimalLong");
    paramRealBufferedSource.require(1L);
    long l = 0L;
    while (paramRealBufferedSource.request(l + 1L))
    {
      int i = paramRealBufferedSource.bufferField.getByte(l);
      if (((i >= (byte)48) && (i <= (byte)57)) || ((l == 0L) && (i == (byte)45)))
      {
        l += 1L;
      }
      else if (l == 0L)
      {
        paramRealBufferedSource = new StringBuilder().append("Expected leading [0-9] or '-' character but was 0x");
        String str = Integer.toString(i, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        throw ((Throwable)new NumberFormatException(str));
      }
    }
    return paramRealBufferedSource.bufferField.readDecimalLong();
  }
  
  public static final void commonReadFully(RealBufferedSource paramRealBufferedSource, Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadFully");
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    try
    {
      paramRealBufferedSource.require(paramLong);
      paramRealBufferedSource.bufferField.readFully(paramBuffer, paramLong);
      return;
    }
    catch (EOFException localEOFException)
    {
      paramBuffer.writeAll((Source)paramRealBufferedSource.bufferField);
      throw ((Throwable)localEOFException);
    }
  }
  
  public static final void commonReadFully(RealBufferedSource paramRealBufferedSource, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadFully");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    try
    {
      paramRealBufferedSource.require(paramArrayOfByte.length);
      paramRealBufferedSource.bufferField.readFully(paramArrayOfByte);
      return;
    }
    catch (EOFException localEOFException)
    {
      int i = 0;
      while (paramRealBufferedSource.bufferField.size() > 0L)
      {
        int j = paramRealBufferedSource.bufferField.read(paramArrayOfByte, i, (int)paramRealBufferedSource.bufferField.size());
        if (j != -1) {
          i += j;
        } else {
          throw ((Throwable)new AssertionError());
        }
      }
      throw ((Throwable)localEOFException);
    }
  }
  
  public static final long commonReadHexadecimalUnsignedLong(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadHexadecimalUnsignedLong");
    paramRealBufferedSource.require(1L);
    int i = 0;
    while (paramRealBufferedSource.request(i + 1))
    {
      int j = paramRealBufferedSource.bufferField.getByte(i);
      if (((j >= (byte)48) && (j <= (byte)57)) || ((j >= (byte)97) && (j <= (byte)102)) || ((j >= (byte)65) && (j <= (byte)70)))
      {
        i++;
      }
      else if (i == 0)
      {
        paramRealBufferedSource = new StringBuilder().append("Expected leading [0-9a-fA-F] character but was 0x");
        String str = Integer.toString(j, CharsKt.checkRadix(CharsKt.checkRadix(16)));
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        throw ((Throwable)new NumberFormatException(str));
      }
    }
    return paramRealBufferedSource.bufferField.readHexadecimalUnsignedLong();
  }
  
  public static final int commonReadInt(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadInt");
    paramRealBufferedSource.require(4L);
    return paramRealBufferedSource.bufferField.readInt();
  }
  
  public static final int commonReadIntLe(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadIntLe");
    paramRealBufferedSource.require(4L);
    return paramRealBufferedSource.bufferField.readIntLe();
  }
  
  public static final long commonReadLong(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadLong");
    paramRealBufferedSource.require(8L);
    return paramRealBufferedSource.bufferField.readLong();
  }
  
  public static final long commonReadLongLe(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadLongLe");
    paramRealBufferedSource.require(8L);
    return paramRealBufferedSource.bufferField.readLongLe();
  }
  
  public static final short commonReadShort(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadShort");
    paramRealBufferedSource.require(2L);
    return paramRealBufferedSource.bufferField.readShort();
  }
  
  public static final short commonReadShortLe(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadShortLe");
    paramRealBufferedSource.require(2L);
    return paramRealBufferedSource.bufferField.readShortLe();
  }
  
  public static final String commonReadUtf8(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadUtf8");
    paramRealBufferedSource.bufferField.writeAll(paramRealBufferedSource.source);
    return paramRealBufferedSource.bufferField.readUtf8();
  }
  
  public static final String commonReadUtf8(RealBufferedSource paramRealBufferedSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadUtf8");
    paramRealBufferedSource.require(paramLong);
    return paramRealBufferedSource.bufferField.readUtf8(paramLong);
  }
  
  public static final int commonReadUtf8CodePoint(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadUtf8CodePoint");
    paramRealBufferedSource.require(1L);
    int i = paramRealBufferedSource.bufferField.getByte(0L);
    if ((i & 0xE0) == 192) {
      paramRealBufferedSource.require(2L);
    } else if ((i & 0xF0) == 224) {
      paramRealBufferedSource.require(3L);
    } else if ((i & 0xF8) == 240) {
      paramRealBufferedSource.require(4L);
    }
    return paramRealBufferedSource.bufferField.readUtf8CodePoint();
  }
  
  public static final String commonReadUtf8Line(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadUtf8Line");
    long l = paramRealBufferedSource.indexOf((byte)10);
    if (l == -1L)
    {
      if (paramRealBufferedSource.bufferField.size() != 0L) {
        paramRealBufferedSource = paramRealBufferedSource.readUtf8(paramRealBufferedSource.bufferField.size());
      } else {
        paramRealBufferedSource = null;
      }
    }
    else
    {
      paramRealBufferedSource = BufferKt.readUtf8Line(paramRealBufferedSource.bufferField, l);
      Log5ECF72.a(paramRealBufferedSource);
      LogE84000.a(paramRealBufferedSource);
      Log229316.a(paramRealBufferedSource);
    }
    return paramRealBufferedSource;
  }
  
  public static final String commonReadUtf8LineStrict(RealBufferedSource paramRealBufferedSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonReadUtf8LineStrict");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramLong == Long.MAX_VALUE) {
        l1 = Long.MAX_VALUE;
      } else {
        l1 = paramLong + 1L;
      }
      byte b = (byte)10;
      long l2 = paramRealBufferedSource.indexOf(b, 0L, l1);
      if (l2 != -1L)
      {
        paramRealBufferedSource = BufferKt.readUtf8Line(paramRealBufferedSource.bufferField, l2);
        Log5ECF72.a(paramRealBufferedSource);
        LogE84000.a(paramRealBufferedSource);
        Log229316.a(paramRealBufferedSource);
        return paramRealBufferedSource;
      }
      if ((l1 < Long.MAX_VALUE) && (paramRealBufferedSource.request(l1)) && (paramRealBufferedSource.bufferField.getByte(l1 - 1L) == (byte)13) && (paramRealBufferedSource.request(1L + l1)) && (paramRealBufferedSource.bufferField.getByte(l1) == b))
      {
        paramRealBufferedSource = BufferKt.readUtf8Line(paramRealBufferedSource.bufferField, l1);
        Log5ECF72.a(paramRealBufferedSource);
        LogE84000.a(paramRealBufferedSource);
        Log229316.a(paramRealBufferedSource);
        return paramRealBufferedSource;
      }
      Buffer localBuffer1 = new Buffer();
      Buffer localBuffer2 = paramRealBufferedSource.bufferField;
      long l1 = paramRealBufferedSource.bufferField.size();
      localBuffer2.copyTo(localBuffer1, 0L, Math.min(32, l1));
      throw ((Throwable)new EOFException("\\n not found: limit=" + Math.min(paramRealBufferedSource.bufferField.size(), paramLong) + " content=" + localBuffer1.readByteString().hex() + "…"));
    }
    throw ((Throwable)new IllegalArgumentException(("limit < 0: " + paramLong).toString()));
  }
  
  public static final boolean commonRequest(RealBufferedSource paramRealBufferedSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonRequest");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if ((paramRealBufferedSource.closed ^ true))
      {
        while (paramRealBufferedSource.bufferField.size() < paramLong) {
          if (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') == -1L) {
            return false;
          }
        }
        return true;
      }
      throw ((Throwable)new IllegalStateException("closed".toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public static final void commonRequire(RealBufferedSource paramRealBufferedSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonRequire");
    if (paramRealBufferedSource.request(paramLong)) {
      return;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final int commonSelect(RealBufferedSource paramRealBufferedSource, Options paramOptions)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonSelect");
    Intrinsics.checkNotNullParameter(paramOptions, "options");
    if ((paramRealBufferedSource.closed ^ true)) {
      for (;;)
      {
        int i = BufferKt.selectPrefix(paramRealBufferedSource.bufferField, paramOptions, true);
        switch (i)
        {
        default: 
          int j = paramOptions.getByteStrings$okio()[i].size();
          paramRealBufferedSource.bufferField.skip(j);
          return i;
        case -1: 
          return -1;
        }
        if (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') == -1L) {
          return -1;
        }
      }
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public static final void commonSkip(RealBufferedSource paramRealBufferedSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonSkip");
    if ((paramRealBufferedSource.closed ^ true))
    {
      while (paramLong > 0L)
      {
        if ((paramRealBufferedSource.bufferField.size() == 0L) && (paramRealBufferedSource.source.read(paramRealBufferedSource.bufferField, ' ') == -1L)) {
          throw ((Throwable)new EOFException());
        }
        long l = Math.min(paramLong, paramRealBufferedSource.bufferField.size());
        paramRealBufferedSource.bufferField.skip(l);
        paramLong -= l;
      }
      return;
    }
    throw ((Throwable)new IllegalStateException("closed".toString()));
  }
  
  public static final Timeout commonTimeout(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonTimeout");
    return paramRealBufferedSource.source.timeout();
  }
  
  public static final String commonToString(RealBufferedSource paramRealBufferedSource)
  {
    Intrinsics.checkNotNullParameter(paramRealBufferedSource, "$this$commonToString");
    return "buffer(" + paramRealBufferedSource.source + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/internal/RealBufferedSourceKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */