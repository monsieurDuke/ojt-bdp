package okio;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okio.internal.BufferKt;

@Metadata(bv={1, 0, 3}, d1={"\000ª\001\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\032\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020\t\n\002\b\005\n\002\020\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\005\n\002\b\005\n\002\020\b\n\002\b\r\n\002\030\002\n\002\b\b\n\002\030\002\n\002\020\022\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\017\n\002\020\n\n\002\b\003\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\027\030\0002\0020\0012\0020\0022\0020\0032\0020\004:\002\001B\005¢\006\002\020\005J\b\020\006\032\0020\000H\026J\006\020\021\032\0020\022J\b\020\023\032\0020\000H\026J\b\020\024\032\0020\022H\026J\006\020\025\032\0020\fJ\006\020\026\032\0020\000J$\020\027\032\0020\0002\006\020\030\032\0020\0312\b\b\002\020\032\032\0020\f2\b\b\002\020\033\032\0020\fH\007J\030\020\027\032\0020\0002\006\020\030\032\0020\0002\b\b\002\020\032\032\0020\fJ \020\027\032\0020\0002\006\020\030\032\0020\0002\b\b\002\020\032\032\0020\f2\006\020\033\032\0020\fJ\020\020\034\032\0020\0352\006\020\036\032\0020\037H\002J\b\020 \032\0020\000H\026J\b\020!\032\0020\000H\026J\023\020\"\032\0020#2\b\020$\032\004\030\0010%H\002J\b\020&\032\0020#H\026J\b\020'\032\0020\022H\026J\026\020(\032\0020)2\006\020*\032\0020\fH\002¢\006\002\b+J\025\020+\032\0020)2\006\020,\032\0020\fH\007¢\006\002\b-J\b\020.\032\0020/H\026J\030\0200\032\0020\0352\006\020\036\032\0020\0372\006\0201\032\0020\035H\002J\016\0202\032\0020\0352\006\0201\032\0020\035J\016\0203\032\0020\0352\006\0201\032\0020\035J\016\0204\032\0020\0352\006\0201\032\0020\035J\020\0205\032\0020\f2\006\0206\032\0020)H\026J\030\0205\032\0020\f2\006\0206\032\0020)2\006\0207\032\0020\fH\026J \0205\032\0020\f2\006\0206\032\0020)2\006\0207\032\0020\f2\006\0208\032\0020\fH\026J\020\0205\032\0020\f2\006\0209\032\0020\035H\026J\030\0205\032\0020\f2\006\0209\032\0020\0352\006\0207\032\0020\fH\026J\020\020:\032\0020\f2\006\020;\032\0020\035H\026J\030\020:\032\0020\f2\006\020;\032\0020\0352\006\0207\032\0020\fH\026J\b\020<\032\0020=H\026J\b\020>\032\0020#H\026J\006\020?\032\0020\035J\b\020@\032\0020\031H\026J\b\020A\032\0020\001H\026J\030\020B\032\0020#2\006\020\032\032\0020\f2\006\0209\032\0020\035H\026J(\020B\032\0020#2\006\020\032\032\0020\f2\006\0209\032\0020\0352\006\020C\032\0020/2\006\020\033\032\0020/H\026J\020\020D\032\0020/2\006\020E\032\0020FH\026J\020\020D\032\0020/2\006\020E\032\0020GH\026J \020D\032\0020/2\006\020E\032\0020G2\006\020\032\032\0020/2\006\020\033\032\0020/H\026J\030\020D\032\0020\f2\006\020E\032\0020\0002\006\020\033\032\0020\fH\026J\020\020H\032\0020\f2\006\020E\032\0020IH\026J\022\020J\032\0020K2\b\b\002\020L\032\0020KH\007J\b\020M\032\0020)H\026J\b\020N\032\0020GH\026J\020\020N\032\0020G2\006\020\033\032\0020\fH\026J\b\020O\032\0020\035H\026J\020\020O\032\0020\0352\006\020\033\032\0020\fH\026J\b\020P\032\0020\fH\026J\016\020Q\032\0020\0002\006\020R\032\0020=J\026\020Q\032\0020\0002\006\020R\032\0020=2\006\020\033\032\0020\fJ \020Q\032\0020\0222\006\020R\032\0020=2\006\020\033\032\0020\f2\006\020S\032\0020#H\002J\020\020T\032\0020\0222\006\020E\032\0020GH\026J\030\020T\032\0020\0222\006\020E\032\0020\0002\006\020\033\032\0020\fH\026J\b\020U\032\0020\fH\026J\b\020V\032\0020/H\026J\b\020W\032\0020/H\026J\b\020X\032\0020\fH\026J\b\020Y\032\0020\fH\026J\b\020Z\032\0020[H\026J\b\020\\\032\0020[H\026J\020\020]\032\0020\0372\006\020^\032\0020_H\026J\030\020]\032\0020\0372\006\020\033\032\0020\f2\006\020^\032\0020_H\026J\022\020`\032\0020K2\b\b\002\020L\032\0020KH\007J\b\020a\032\0020\037H\026J\020\020a\032\0020\0372\006\020\033\032\0020\fH\026J\b\020b\032\0020/H\026J\n\020c\032\004\030\0010\037H\026J\b\020d\032\0020\037H\026J\020\020d\032\0020\0372\006\020e\032\0020\fH\026J\020\020f\032\0020#2\006\020\033\032\0020\fH\026J\020\020g\032\0020\0222\006\020\033\032\0020\fH\026J\020\020h\032\0020/2\006\020i\032\0020jH\026J\006\020k\032\0020\035J\006\020l\032\0020\035J\006\020m\032\0020\035J\r\020\r\032\0020\fH\007¢\006\002\bnJ\020\020o\032\0020\0222\006\020\033\032\0020\fH\026J\006\020p\032\0020\035J\016\020p\032\0020\0352\006\020\033\032\0020/J\b\020q\032\0020rH\026J\b\020s\032\0020\037H\026J\025\020t\032\0020\n2\006\020u\032\0020/H\000¢\006\002\bvJ\020\020w\032\0020/2\006\020x\032\0020FH\026J\020\020w\032\0020\0002\006\020x\032\0020GH\026J \020w\032\0020\0002\006\020x\032\0020G2\006\020\032\032\0020/2\006\020\033\032\0020/H\026J\030\020w\032\0020\0222\006\020x\032\0020\0002\006\020\033\032\0020\fH\026J\020\020w\032\0020\0002\006\020y\032\0020\035H\026J \020w\032\0020\0002\006\020y\032\0020\0352\006\020\032\032\0020/2\006\020\033\032\0020/H\026J\030\020w\032\0020\0002\006\020x\032\0020z2\006\020\033\032\0020\fH\026J\020\020{\032\0020\f2\006\020x\032\0020zH\026J\020\020|\032\0020\0002\006\0206\032\0020/H\026J\020\020}\032\0020\0002\006\020~\032\0020\fH\026J\020\020\032\0020\0002\006\020~\032\0020\fH\026J\022\020\001\032\0020\0002\007\020\001\032\0020/H\026J\022\020\001\032\0020\0002\007\020\001\032\0020/H\026J\021\020\001\032\0020\0002\006\020~\032\0020\fH\026J\021\020\001\032\0020\0002\006\020~\032\0020\fH\026J\022\020\001\032\0020\0002\007\020\001\032\0020/H\026J\022\020\001\032\0020\0002\007\020\001\032\0020/H\026J\032\020\001\032\0020\0002\007\020\001\032\0020\0372\006\020^\032\0020_H\026J,\020\001\032\0020\0002\007\020\001\032\0020\0372\007\020\001\032\0020/2\007\020\001\032\0020/2\006\020^\032\0020_H\026J\033\020\001\032\0020\0002\006\020\030\032\0020\0312\b\b\002\020\033\032\0020\fH\007J\022\020\001\032\0020\0002\007\020\001\032\0020\037H\026J$\020\001\032\0020\0002\007\020\001\032\0020\0372\007\020\001\032\0020/2\007\020\001\032\0020/H\026J\022\020\001\032\0020\0002\007\020\001\032\0020/H\026R\024\020\006\032\0020\0008VX\004¢\006\006\032\004\b\007\020\bR\024\020\t\032\004\030\0010\n8\000@\000X\016¢\006\002\n\000R&\020\r\032\0020\f2\006\020\013\032\0020\f8G@@X\016¢\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020¨\006\001"}, d2={"Lokio/Buffer;", "Lokio/BufferedSource;", "Lokio/BufferedSink;", "", "Ljava/nio/channels/ByteChannel;", "()V", "buffer", "getBuffer", "()Lokio/Buffer;", "head", "Lokio/Segment;", "<set-?>", "", "size", "()J", "setSize$okio", "(J)V", "clear", "", "clone", "close", "completeSegmentByteCount", "copy", "copyTo", "out", "Ljava/io/OutputStream;", "offset", "byteCount", "digest", "Lokio/ByteString;", "algorithm", "", "emit", "emitCompleteSegments", "equals", "", "other", "", "exhausted", "flush", "get", "", "pos", "getByte", "index", "-deprecated_getByte", "hashCode", "", "hmac", "key", "hmacSha1", "hmacSha256", "hmacSha512", "indexOf", "b", "fromIndex", "toIndex", "bytes", "indexOfElement", "targetBytes", "inputStream", "Ljava/io/InputStream;", "isOpen", "md5", "outputStream", "peek", "rangeEquals", "bytesOffset", "read", "sink", "Ljava/nio/ByteBuffer;", "", "readAll", "Lokio/Sink;", "readAndWriteUnsafe", "Lokio/Buffer$UnsafeCursor;", "unsafeCursor", "readByte", "readByteArray", "readByteString", "readDecimalLong", "readFrom", "input", "forever", "readFully", "readHexadecimalUnsignedLong", "readInt", "readIntLe", "readLong", "readLongLe", "readShort", "", "readShortLe", "readString", "charset", "Ljava/nio/charset/Charset;", "readUnsafe", "readUtf8", "readUtf8CodePoint", "readUtf8Line", "readUtf8LineStrict", "limit", "request", "require", "select", "options", "Lokio/Options;", "sha1", "sha256", "sha512", "-deprecated_size", "skip", "snapshot", "timeout", "Lokio/Timeout;", "toString", "writableSegment", "minimumCapacity", "writableSegment$okio", "write", "source", "byteString", "Lokio/Source;", "writeAll", "writeByte", "writeDecimalLong", "v", "writeHexadecimalUnsignedLong", "writeInt", "i", "writeIntLe", "writeLong", "writeLongLe", "writeShort", "s", "writeShortLe", "writeString", "string", "beginIndex", "endIndex", "writeTo", "writeUtf8", "writeUtf8CodePoint", "codePoint", "UnsafeCursor", "okio"}, k=1, mv={1, 4, 0})
public final class Buffer
  implements BufferedSource, BufferedSink, Cloneable, ByteChannel
{
  public Segment head;
  private long size;
  
  private final ByteString digest(String paramString)
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
    Segment localSegment = this.head;
    if (localSegment != null)
    {
      localMessageDigest.update(localSegment.data, localSegment.pos, localSegment.limit - localSegment.pos);
      paramString = localSegment.next;
      Intrinsics.checkNotNull(paramString);
      while (paramString != localSegment)
      {
        localMessageDigest.update(paramString.data, paramString.pos, paramString.limit - paramString.pos);
        paramString = paramString.next;
        Intrinsics.checkNotNull(paramString);
      }
    }
    paramString = localMessageDigest.digest();
    Intrinsics.checkNotNullExpressionValue(paramString, "messageDigest.digest()");
    return new ByteString(paramString);
  }
  
  private final ByteString hmac(String paramString, ByteString paramByteString)
  {
    try
    {
      Mac localMac = Mac.getInstance(paramString);
      SecretKeySpec localSecretKeySpec = new javax/crypto/spec/SecretKeySpec;
      localSecretKeySpec.<init>(paramByteString.internalArray$okio(), paramString);
      localMac.init((Key)localSecretKeySpec);
      paramByteString = this.head;
      if (paramByteString != null)
      {
        localMac.update(paramByteString.data, paramByteString.pos, paramByteString.limit - paramByteString.pos);
        paramString = paramByteString.next;
        Intrinsics.checkNotNull(paramString);
        while (paramString != paramByteString)
        {
          localMac.update(paramString.data, paramString.pos, paramString.limit - paramString.pos);
          paramString = paramString.next;
          Intrinsics.checkNotNull(paramString);
        }
      }
      paramString = localMac.doFinal();
      Intrinsics.checkNotNullExpressionValue(paramString, "mac.doFinal()");
      paramString = new ByteString(paramString);
      return paramString;
    }
    catch (InvalidKeyException paramString)
    {
      throw ((Throwable)new IllegalArgumentException((Throwable)paramString));
    }
  }
  
  private final void readFrom(InputStream paramInputStream, long paramLong, boolean paramBoolean)
    throws IOException
  {
    for (;;)
    {
      if ((paramLong <= 0L) && (!paramBoolean)) {
        return;
      }
      Segment localSegment = writableSegment$okio(1);
      int i = (int)Math.min(paramLong, 8192 - localSegment.limit);
      i = paramInputStream.read(localSegment.data, localSegment.limit, i);
      if (i == -1)
      {
        if (localSegment.pos == localSegment.limit)
        {
          this.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        if (paramBoolean) {
          return;
        }
        throw ((Throwable)new EOFException());
      }
      localSegment.limit += i;
      this.size += i;
      paramLong -= i;
    }
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to operator function", replaceWith=@ReplaceWith(expression="this[index]", imports={}))
  public final byte -deprecated_getByte(long paramLong)
  {
    return getByte(paramLong);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="size", imports={}))
  public final long -deprecated_size()
  {
    return this.size;
  }
  
  public Buffer buffer()
  {
    return this;
  }
  
  public final void clear()
  {
    skip(size());
  }
  
  public Buffer clone()
  {
    return copy();
  }
  
  public void close() {}
  
  public final long completeSegmentByteCount()
  {
    long l2 = size();
    long l1 = 0L;
    if (l2 != 0L)
    {
      Segment localSegment = this.head;
      Intrinsics.checkNotNull(localSegment);
      localSegment = localSegment.prev;
      Intrinsics.checkNotNull(localSegment);
      l1 = l2;
      if (localSegment.limit < 8192)
      {
        l1 = l2;
        if (localSegment.owner) {
          l1 = l2 - (localSegment.limit - localSegment.pos);
        }
      }
    }
    return l1;
  }
  
  public final Buffer copy()
  {
    Buffer localBuffer = new Buffer();
    if (size() != 0L)
    {
      Segment localSegment2 = this.head;
      Intrinsics.checkNotNull(localSegment2);
      Segment localSegment3 = localSegment2.sharedCopy();
      localBuffer.head = localSegment3;
      localSegment3.prev = localSegment3;
      localSegment3.next = localSegment3.prev;
      for (Segment localSegment1 = localSegment2.next; localSegment1 != localSegment2; localSegment1 = localSegment1.next)
      {
        Segment localSegment4 = localSegment3.prev;
        Intrinsics.checkNotNull(localSegment4);
        Intrinsics.checkNotNull(localSegment1);
        localSegment4.push(localSegment1.sharedCopy());
      }
      localBuffer.setSize$okio(size());
    }
    return localBuffer;
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream)
    throws IOException
  {
    return copyTo$default(this, paramOutputStream, 0L, 0L, 6, null);
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    return copyTo$default(this, paramOutputStream, paramLong, 0L, 4, null);
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream, long paramLong1, long paramLong2)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramOutputStream, "out");
    _Util.checkOffsetAndCount(this.size, paramLong1, paramLong2);
    if (paramLong2 == 0L) {
      return this;
    }
    long l1;
    Segment localSegment2;
    long l2;
    for (Segment localSegment1 = this.head;; localSegment1 = localSegment1.next)
    {
      Intrinsics.checkNotNull(localSegment1);
      l1 = paramLong1;
      localSegment2 = localSegment1;
      l2 = paramLong2;
      if (paramLong1 < localSegment1.limit - localSegment1.pos) {
        break;
      }
      paramLong1 -= localSegment1.limit - localSegment1.pos;
    }
    while (l2 > 0L)
    {
      Intrinsics.checkNotNull(localSegment2);
      int j = (int)(localSegment2.pos + l1);
      int i = (int)Math.min(localSegment2.limit - j, l2);
      paramOutputStream.write(localSegment2.data, j, i);
      l2 -= i;
      l1 = 0L;
      localSegment2 = localSegment2.next;
    }
    return this;
  }
  
  public final Buffer copyTo(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "out");
    return copyTo(paramBuffer, paramLong, this.size - paramLong);
  }
  
  public final Buffer copyTo(Buffer paramBuffer, long paramLong1, long paramLong2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "out");
    _Util.checkOffsetAndCount(size(), paramLong1, paramLong2);
    if (paramLong2 != 0L)
    {
      paramBuffer.setSize$okio(paramBuffer.size() + paramLong2);
      long l1;
      Segment localSegment2;
      long l2;
      for (Segment localSegment1 = this.head;; localSegment1 = localSegment1.next)
      {
        Intrinsics.checkNotNull(localSegment1);
        l1 = paramLong1;
        localSegment2 = localSegment1;
        l2 = paramLong2;
        if (paramLong1 < localSegment1.limit - localSegment1.pos) {
          break;
        }
        paramLong1 -= localSegment1.limit - localSegment1.pos;
      }
      while (l2 > 0L)
      {
        Intrinsics.checkNotNull(localSegment2);
        localSegment1 = localSegment2.sharedCopy();
        localSegment1.pos += (int)l1;
        localSegment1.limit = Math.min(localSegment1.pos + (int)l2, localSegment1.limit);
        Segment localSegment3 = paramBuffer.head;
        if (localSegment3 == null)
        {
          localSegment1.prev = localSegment1;
          localSegment1.next = localSegment1.prev;
          paramBuffer.head = localSegment1.next;
        }
        else
        {
          Intrinsics.checkNotNull(localSegment3);
          localSegment3 = localSegment3.prev;
          Intrinsics.checkNotNull(localSegment3);
          localSegment3.push(localSegment1);
        }
        l2 -= localSegment1.limit - localSegment1.pos;
        l1 = 0L;
        localSegment2 = localSegment2.next;
      }
    }
    return this;
  }
  
  public Buffer emit()
  {
    return this;
  }
  
  public Buffer emitCompleteSegments()
  {
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (this == paramObject)
    {
      bool = true;
    }
    else if (!(paramObject instanceof Buffer))
    {
      bool = false;
    }
    else if (size() != ((Buffer)paramObject).size())
    {
      bool = false;
    }
    else if (size() == 0L)
    {
      bool = true;
    }
    else
    {
      Object localObject3 = this.head;
      Intrinsics.checkNotNull(localObject3);
      paramObject = ((Buffer)paramObject).head;
      Intrinsics.checkNotNull(paramObject);
      int j = ((Segment)localObject3).pos;
      int i = ((Segment)paramObject).pos;
      long l1 = 0L;
      while (l1 < size())
      {
        long l3 = Math.min(((Segment)localObject3).limit - j, ((Segment)paramObject).limit - i);
        long l2 = 0L;
        int k = j;
        while (l2 < l3)
        {
          if (localObject3.data[k] != paramObject.data[i])
          {
            bool = false;
            break label268;
          }
          l2 += 1L;
          k++;
          i++;
        }
        Object localObject1 = localObject3;
        j = k;
        if (k == ((Segment)localObject3).limit)
        {
          localObject1 = ((Segment)localObject3).next;
          Intrinsics.checkNotNull(localObject1);
          j = ((Segment)localObject1).pos;
        }
        Object localObject2 = paramObject;
        k = i;
        if (i == ((Segment)paramObject).limit)
        {
          localObject2 = ((Segment)paramObject).next;
          Intrinsics.checkNotNull(localObject2);
          k = ((Segment)localObject2).pos;
        }
        l1 += l3;
        localObject3 = localObject1;
        paramObject = localObject2;
        i = k;
      }
      bool = true;
    }
    label268:
    return bool;
  }
  
  public boolean exhausted()
  {
    boolean bool;
    if (this.size == 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void flush() {}
  
  public Buffer getBuffer()
  {
    return this;
  }
  
  public final byte getByte(long paramLong)
  {
    _Util.checkOffsetAndCount(size(), paramLong, 1L);
    Segment localSegment = this.head;
    byte b;
    if (localSegment != null)
    {
      long l1;
      if (size() - paramLong < paramLong)
      {
        for (l1 = size(); l1 > paramLong; l1 -= localSegment.limit - localSegment.pos)
        {
          localSegment = localSegment.prev;
          Intrinsics.checkNotNull(localSegment);
        }
        Intrinsics.checkNotNull(localSegment);
        b = localSegment.data[((int)(localSegment.pos + paramLong - l1))];
      }
      else
      {
        long l2;
        for (l1 = 0L;; l1 = l2)
        {
          l2 = localSegment.limit - localSegment.pos + l1;
          if (l2 > paramLong)
          {
            Intrinsics.checkNotNull(localSegment);
            b = localSegment.data[((int)(localSegment.pos + paramLong - l1))];
            break;
          }
          localSegment = localSegment.next;
          Intrinsics.checkNotNull(localSegment);
        }
      }
    }
    else
    {
      localSegment = (Segment)null;
      Intrinsics.checkNotNull(localSegment);
      b = localSegment.data[((int)(localSegment.pos + paramLong + 1L))];
    }
    return b;
  }
  
  public int hashCode()
  {
    Object localObject = this.head;
    int i;
    if (localObject != null)
    {
      int j = 1;
      Segment localSegment;
      do
      {
        int k = ((Segment)localObject).pos;
        int m = ((Segment)localObject).limit;
        i = j;
        while (k < m)
        {
          i = i * 31 + localObject.data[k];
          k++;
        }
        localSegment = ((Segment)localObject).next;
        Intrinsics.checkNotNull(localSegment);
        localObject = localSegment;
        j = i;
      } while (localSegment != this.head);
    }
    else
    {
      i = 0;
    }
    return i;
  }
  
  public final ByteString hmacSha1(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "key");
    return hmac("HmacSHA1", paramByteString);
  }
  
  public final ByteString hmacSha256(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "key");
    return hmac("HmacSHA256", paramByteString);
  }
  
  public final ByteString hmacSha512(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "key");
    return hmac("HmacSHA512", paramByteString);
  }
  
  public long indexOf(byte paramByte)
  {
    return indexOf(paramByte, 0L, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong)
  {
    return indexOf(paramByte, paramLong, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong1, long paramLong2)
  {
    int i;
    if ((0L <= paramLong1) && (paramLong2 >= paramLong1)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      long l1 = paramLong2;
      if (paramLong2 > size()) {
        l1 = size();
      }
      if (paramLong1 == l1)
      {
        paramLong1 = -1L;
      }
      else
      {
        long l2 = paramLong1;
        Object localObject = this;
        int k = 0;
        Segment localSegment1 = ((Buffer)localObject).head;
        if (localSegment1 != null)
        {
          Segment localSegment2;
          int j;
          if (((Buffer)localObject).size() - l2 < l2)
          {
            for (paramLong2 = ((Buffer)localObject).size(); paramLong2 > l2; paramLong2 -= localSegment1.limit - localSegment1.pos)
            {
              localSegment1 = localSegment1.prev;
              Intrinsics.checkNotNull(localSegment1);
            }
            localSegment2 = localSegment1;
            int m = 0;
            if (localSegment2 != null)
            {
              Segment localSegment3 = localSegment2;
              while (paramLong2 < l1)
              {
                byte[] arrayOfByte = localSegment3.data;
                j = (int)Math.min(localSegment3.limit, localSegment3.pos + l1 - paramLong2);
                for (i = (int)(localSegment3.pos + paramLong1 - paramLong2); i < j; i++) {
                  if (arrayOfByte[i] == paramByte)
                  {
                    paramLong1 = i - localSegment3.pos + paramLong2;
                    break label515;
                  }
                }
                paramLong2 += localSegment3.limit - localSegment3.pos;
                paramLong1 = paramLong2;
                localSegment3 = localSegment3.next;
                Intrinsics.checkNotNull(localSegment3);
              }
              paramLong1 = -1L;
            }
            else
            {
              paramLong1 = -1L;
            }
          }
          else
          {
            long l3;
            for (paramLong2 = 0L;; paramLong2 = l3)
            {
              l3 = localSegment1.limit - localSegment1.pos + paramLong2;
              if (l3 > l2)
              {
                k = 0;
                if (localSegment1 != null)
                {
                  localSegment2 = localSegment1;
                  l3 = paramLong2;
                  while (l3 < l1)
                  {
                    localObject = localSegment2.data;
                    j = (int)Math.min(localSegment2.limit, localSegment2.pos + l1 - l3);
                    for (i = (int)(localSegment2.pos + paramLong1 - l3); i < j; i++) {
                      if (localObject[i] == paramByte)
                      {
                        paramLong1 = i - localSegment2.pos + l3;
                        break label515;
                      }
                    }
                    l3 += localSegment2.limit - localSegment2.pos;
                    paramLong1 = l3;
                    localSegment2 = localSegment2.next;
                    Intrinsics.checkNotNull(localSegment2);
                  }
                  paramLong1 = -1L;
                  break;
                }
                paramLong1 = -1L;
                break;
              }
              localSegment1 = localSegment1.next;
              Intrinsics.checkNotNull(localSegment1);
            }
          }
        }
        else
        {
          localSegment1 = (Segment)null;
          paramLong1 = -1L;
        }
      }
      label515:
      return paramLong1;
    }
    throw ((Throwable)new IllegalArgumentException(("size=" + size() + " fromIndex=" + paramLong1 + " toIndex=" + paramLong2).toString()));
  }
  
  public long indexOf(ByteString paramByteString)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    return indexOf(paramByteString, 0L);
  }
  
  public long indexOf(ByteString paramByteString, long paramLong)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    Object localObject1 = this;
    int i;
    if (paramByteString.size() > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramLong >= 0L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        long l2 = paramLong;
        Object localObject3 = localObject1;
        int n = 0;
        Segment localSegment = ((Buffer)localObject3).head;
        if (localSegment != null)
        {
          long l1;
          Object localObject2;
          int j;
          int k;
          long l3;
          long l4;
          int m;
          if (((Buffer)localObject3).size() - l2 < l2)
          {
            for (l1 = ((Buffer)localObject3).size(); l1 > l2; l1 -= localSegment.limit - localSegment.pos)
            {
              localSegment = localSegment.prev;
              Intrinsics.checkNotNull(localSegment);
            }
            localObject2 = localSegment;
            if (localObject2 != null)
            {
              l2 = l1;
              byte[] arrayOfByte = paramByteString.internalArray$okio();
              j = arrayOfByte[0];
              k = paramByteString.size();
              l3 = ((Buffer)localObject1).size() - k + 1L;
              paramByteString = (ByteString)localObject2;
              while (l2 < l3)
              {
                localObject1 = paramByteString.data;
                i = paramByteString.limit;
                l4 = paramByteString.pos;
                m = (int)Math.min(i, l4 + l3 - l2);
                for (i = (int)(paramByteString.pos + paramLong - l2); i < m; i++) {
                  if ((localObject1[i] == j) && (BufferKt.rangeEquals(paramByteString, i + 1, arrayOfByte, 1, k)))
                  {
                    paramLong = i - paramByteString.pos + l2;
                    break label603;
                  }
                }
                l2 += paramByteString.limit - paramByteString.pos;
                paramLong = l2;
                paramByteString = paramByteString.next;
                Intrinsics.checkNotNull(paramByteString);
              }
              paramLong = -1L;
            }
            else
            {
              paramLong = -1L;
            }
          }
          else
          {
            for (l1 = 0L;; l1 = l3)
            {
              l3 = localSegment.limit - localSegment.pos + l1;
              if (l3 > l2)
              {
                if (localSegment != null)
                {
                  l2 = l1;
                  localObject2 = paramByteString.internalArray$okio();
                  i = localObject2[0];
                  k = paramByteString.size();
                  l3 = ((Buffer)localObject1).size() - k + 1L;
                  paramByteString = (ByteString)localObject2;
                  while (l2 < l3)
                  {
                    localObject2 = localSegment.data;
                    j = localSegment.limit;
                    l4 = localSegment.pos;
                    m = (int)Math.min(j, l4 + l3 - l2);
                    for (j = (int)(localSegment.pos + paramLong - l2); j < m; j++) {
                      if (localObject2[j] == i) {
                        if (BufferKt.rangeEquals(localSegment, j + 1, paramByteString, 1, k))
                        {
                          paramLong = j - localSegment.pos + l2;
                          break label603;
                        }
                      }
                    }
                    l2 += localSegment.limit - localSegment.pos;
                    paramLong = l2;
                    localSegment = localSegment.next;
                    Intrinsics.checkNotNull(localSegment);
                  }
                  paramLong = -1L;
                  break;
                }
                paramLong = -1L;
                break;
              }
              localSegment = localSegment.next;
              Intrinsics.checkNotNull(localSegment);
            }
          }
        }
        else
        {
          paramByteString = (Segment)null;
          paramLong = -1L;
        }
        label603:
        return paramLong;
      }
      throw ((Throwable)new IllegalArgumentException(("fromIndex < 0: " + paramLong).toString()));
    }
    throw ((Throwable)new IllegalArgumentException("bytes is empty".toString()));
  }
  
  public long indexOfElement(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "targetBytes");
    return indexOfElement(paramByteString, 0L);
  }
  
  public long indexOfElement(ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "targetBytes");
    Object localObject2 = this;
    int i2 = 0;
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      Object localObject3 = localObject2;
      int i1 = 0;
      Object localObject1 = ((Buffer)localObject3).head;
      if (localObject1 != null)
      {
        long l1;
        int m;
        int k;
        long l2;
        byte[] arrayOfByte;
        int j;
        int n;
        if (((Buffer)localObject3).size() - paramLong < paramLong)
        {
          for (l1 = ((Buffer)localObject3).size(); l1 > paramLong; l1 -= ((Segment)localObject1).limit - ((Segment)localObject1).pos)
          {
            localObject1 = ((Segment)localObject1).prev;
            Intrinsics.checkNotNull(localObject1);
          }
          if (localObject1 != null)
          {
            if (paramByteString.size() == 2)
            {
              m = paramByteString.getByte(0);
              k = paramByteString.getByte(1);
              paramByteString = (ByteString)localObject1;
              l2 = l1;
              l1 = paramLong;
              while (l2 < ((Buffer)localObject2).size())
              {
                arrayOfByte = paramByteString.data;
                i = (int)(paramByteString.pos + l1 - l2);
                j = paramByteString.limit;
                while (i < j)
                {
                  n = arrayOfByte[i];
                  if ((n != m) && (n != k))
                  {
                    i++;
                  }
                  else
                  {
                    paramLong = i - paramByteString.pos + l2;
                    break label828;
                  }
                }
                paramLong = l2 + (paramByteString.limit - paramByteString.pos);
                l1 = paramLong;
                paramByteString = paramByteString.next;
                Intrinsics.checkNotNull(paramByteString);
                l2 = paramLong;
              }
            }
            else
            {
              paramByteString = paramByteString.internalArray$okio();
              while (l1 < ((Buffer)localObject2).size())
              {
                localObject3 = ((Segment)localObject1).data;
                i = (int)(((Segment)localObject1).pos + paramLong - l1);
                k = ((Segment)localObject1).limit;
                while (i < k)
                {
                  m = localObject3[i];
                  n = paramByteString.length;
                  for (j = 0; j < n; j++) {
                    if (m == paramByteString[j])
                    {
                      paramLong = i - ((Segment)localObject1).pos + l1;
                      break label828;
                    }
                  }
                  i++;
                }
                l1 += ((Segment)localObject1).limit - ((Segment)localObject1).pos;
                paramLong = l1;
                localObject1 = ((Segment)localObject1).next;
                Intrinsics.checkNotNull(localObject1);
              }
            }
            paramLong = -1L;
          }
          else
          {
            paramLong = -1L;
          }
        }
        else
        {
          l1 = 0L;
          localObject3 = localObject1;
          for (;;)
          {
            l2 = ((Segment)localObject3).limit - ((Segment)localObject3).pos + l1;
            if (l2 > paramLong)
            {
              if (localObject3 != null)
              {
                localObject1 = localObject3;
                l2 = l1;
                if (paramByteString.size() == 2)
                {
                  j = paramByteString.getByte(0);
                  k = paramByteString.getByte(1);
                  while (l2 < ((Buffer)localObject2).size())
                  {
                    paramByteString = ((Segment)localObject1).data;
                    i = (int)(((Segment)localObject1).pos + paramLong - l2);
                    m = ((Segment)localObject1).limit;
                    while (i < m)
                    {
                      n = paramByteString[i];
                      if ((n != j) && (n != k))
                      {
                        i++;
                      }
                      else
                      {
                        paramLong = i - ((Segment)localObject1).pos + l2;
                        break label828;
                      }
                    }
                    l2 += ((Segment)localObject1).limit - ((Segment)localObject1).pos;
                    localObject1 = ((Segment)localObject1).next;
                    Intrinsics.checkNotNull(localObject1);
                    paramLong = l2;
                  }
                }
                else
                {
                  arrayOfByte = paramByteString.internalArray$okio();
                  localObject3 = localObject1;
                  l1 = paramLong;
                  paramByteString = (ByteString)localObject2;
                  localObject1 = arrayOfByte;
                  while (l2 < paramByteString.size())
                  {
                    localObject2 = ((Segment)localObject3).data;
                    i = (int)(((Segment)localObject3).pos + l1 - l2);
                    k = ((Segment)localObject3).limit;
                    while (i < k)
                    {
                      n = localObject2[i];
                      m = localObject1.length;
                      for (j = 0; j < m; j++) {
                        if (n == localObject1[j])
                        {
                          paramLong = i - ((Segment)localObject3).pos + l2;
                          break label828;
                        }
                      }
                      i++;
                    }
                    paramLong = l2 + (((Segment)localObject3).limit - ((Segment)localObject3).pos);
                    l1 = paramLong;
                    localObject3 = ((Segment)localObject3).next;
                    Intrinsics.checkNotNull(localObject3);
                    l2 = paramLong;
                  }
                }
                paramLong = -1L;
                break;
              }
              paramLong = -1L;
              break;
            }
            localObject3 = ((Segment)localObject3).next;
            Intrinsics.checkNotNull(localObject3);
            l1 = l2;
          }
        }
      }
      else
      {
        paramByteString = (Segment)null;
        paramLong = -1L;
      }
      label828:
      return paramLong;
    }
    throw ((Throwable)new IllegalArgumentException(("fromIndex < 0: " + paramLong).toString()));
  }
  
  public InputStream inputStream()
  {
    (InputStream)new InputStream()
    {
      final Buffer this$0;
      
      public int available()
      {
        return (int)Math.min(this.this$0.size(), Integer.MAX_VALUE);
      }
      
      public void close() {}
      
      public int read()
      {
        int i;
        if (this.this$0.size() > 0L) {
          i = this.this$0.readByte() & 0xFF;
        } else {
          i = -1;
        }
        return i;
      }
      
      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousArrayOfByte, "sink");
        return this.this$0.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      
      public String toString()
      {
        return this.this$0 + ".inputStream()";
      }
    };
  }
  
  public boolean isOpen()
  {
    return true;
  }
  
  public final ByteString md5()
  {
    return digest("MD5");
  }
  
  public OutputStream outputStream()
  {
    (OutputStream)new OutputStream()
    {
      final Buffer this$0;
      
      public void close() {}
      
      public void flush() {}
      
      public String toString()
      {
        return this.this$0 + ".outputStream()";
      }
      
      public void write(int paramAnonymousInt)
      {
        this.this$0.writeByte(paramAnonymousInt);
      }
      
      public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousArrayOfByte, "data");
        this.this$0.write(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
    };
  }
  
  public BufferedSource peek()
  {
    return Okio.buffer((Source)new PeekSource((BufferedSource)this));
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    return rangeEquals(paramLong, paramByteString, 0, paramByteString.size());
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    boolean bool = false;
    if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (size() - paramLong >= paramInt2) && (paramByteString.size() - paramInt1 >= paramInt2))
    {
      for (int i = 0; i < paramInt2; i++) {
        if (getByte(i + paramLong) != paramByteString.getByte(paramInt1 + i)) {
          break label100;
        }
      }
      bool = true;
    }
    label100:
    return bool;
  }
  
  public int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteBuffer, "sink");
    Segment localSegment = this.head;
    if (localSegment != null)
    {
      int i = Math.min(paramByteBuffer.remaining(), localSegment.limit - localSegment.pos);
      paramByteBuffer.put(localSegment.data, localSegment.pos, i);
      localSegment.pos += i;
      this.size -= i;
      if (localSegment.pos == localSegment.limit)
      {
        this.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      return i;
    }
    return -1;
  }
  
  public int read(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    Segment localSegment = this.head;
    if (localSegment != null)
    {
      paramInt2 = Math.min(paramInt2, localSegment.limit - localSegment.pos);
      ArraysKt.copyInto(localSegment.data, paramArrayOfByte, paramInt1, localSegment.pos, localSegment.pos + paramInt2);
      localSegment.pos += paramInt2;
      setSize$okio(size() - paramInt2);
      if (localSegment.pos == localSegment.limit)
      {
        this.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      paramInt1 = paramInt2;
    }
    else
    {
      paramInt1 = -1;
    }
    return paramInt1;
  }
  
  public long read(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    long l = paramLong;
    int i;
    if (l >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (size() == 0L)
      {
        paramLong = -1L;
      }
      else
      {
        paramLong = l;
        if (l > size()) {
          paramLong = size();
        }
        paramBuffer.write(this, paramLong);
      }
      return paramLong;
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + l).toString()));
  }
  
  public long readAll(Sink paramSink)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramSink, "sink");
    long l = size();
    if (l > 0L) {
      paramSink.write(this, l);
    }
    return l;
  }
  
  public final UnsafeCursor readAndWriteUnsafe()
  {
    return readAndWriteUnsafe$default(this, null, 1, null);
  }
  
  public final UnsafeCursor readAndWriteUnsafe(UnsafeCursor paramUnsafeCursor)
  {
    Intrinsics.checkNotNullParameter(paramUnsafeCursor, "unsafeCursor");
    int i;
    if (paramUnsafeCursor.buffer == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramUnsafeCursor.buffer = ((Buffer)this);
      paramUnsafeCursor.readWrite = true;
      return paramUnsafeCursor;
    }
    throw ((Throwable)new IllegalStateException("already attached to a buffer".toString()));
  }
  
  public byte readByte()
    throws EOFException
  {
    if (size() != 0L)
    {
      Segment localSegment = this.head;
      Intrinsics.checkNotNull(localSegment);
      int i = localSegment.pos;
      int j = localSegment.limit;
      byte[] arrayOfByte = localSegment.data;
      int k = i + 1;
      byte b = arrayOfByte[i];
      setSize$okio(size() - 1L);
      if (k == j)
      {
        this.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      else
      {
        localSegment.pos = k;
      }
      return b;
    }
    throw ((Throwable)new EOFException());
  }
  
  public byte[] readByteArray()
  {
    return readByteArray(size());
  }
  
  public byte[] readByteArray(long paramLong)
    throws EOFException
  {
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (size() >= paramLong)
      {
        byte[] arrayOfByte = new byte[(int)paramLong];
        readFully(arrayOfByte);
        return arrayOfByte;
      }
      throw ((Throwable)new EOFException());
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount: " + paramLong).toString()));
  }
  
  public ByteString readByteString()
  {
    return readByteString(size());
  }
  
  public ByteString readByteString(long paramLong)
    throws EOFException
  {
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (size() >= paramLong)
      {
        ByteString localByteString;
        if (paramLong >= 'က')
        {
          localByteString = snapshot((int)paramLong);
          skip(paramLong);
        }
        else
        {
          localByteString = new ByteString(readByteArray(paramLong));
        }
        return localByteString;
      }
      throw ((Throwable)new EOFException());
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount: " + paramLong).toString()));
  }
  
  public long readDecimalLong()
    throws EOFException
  {
    Object localObject1 = this;
    int i2 = 0;
    if (((Buffer)localObject1).size() != 0L)
    {
      long l1 = 0L;
      int k = 0;
      int m = 0;
      int i = 0;
      long l2 = -7L;
      for (;;)
      {
        Segment localSegment = ((Buffer)localObject1).head;
        Intrinsics.checkNotNull(localSegment);
        Object localObject2 = localSegment.data;
        int j = localSegment.pos;
        int n = localSegment.limit;
        while (j < n)
        {
          byte b1 = localObject2[j];
          byte b2 = (byte)48;
          if ((b1 >= b2) && (b1 <= (byte)57))
          {
            int i1;
            b2 -= b1;
            if ((l1 >= -922337203685477580L) && ((l1 != -922337203685477580L) || (i1 >= l2)))
            {
              l1 = l1 * 10L + i1;
            }
            else
            {
              localObject1 = new Buffer().writeDecimalLong(l1).writeByte(b1);
              if (m == 0) {
                ((Buffer)localObject1).readByte();
              }
              throw ((Throwable)new NumberFormatException("Number too large: " + ((Buffer)localObject1).readUtf8()));
            }
          }
          else
          {
            if ((b1 != (byte)45) || (k != 0)) {
              break label238;
            }
            m = 1;
            l2 -= 1L;
          }
          j++;
          k++;
          continue;
          label238:
          if (k != 0)
          {
            i = 1;
          }
          else
          {
            localObject2 = new StringBuilder().append("Expected leading [0-9] or '-' character but was 0x");
            localObject1 = _Util.toHexString(b1);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
            throw ((Throwable)new NumberFormatException((String)localObject1));
          }
        }
        if (j == n)
        {
          ((Buffer)localObject1).head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        else
        {
          localSegment.pos = j;
        }
        if ((i != 0) || (((Buffer)localObject1).head == null)) {
          break;
        }
      }
      ((Buffer)localObject1).setSize$okio(((Buffer)localObject1).size() - k);
      if (m == 0) {
        l1 = -l1;
      }
      return l1;
    }
    throw ((Throwable)new EOFException());
  }
  
  public final Buffer readFrom(InputStream paramInputStream)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "input");
    readFrom(paramInputStream, Long.MAX_VALUE, true);
    return this;
  }
  
  public final Buffer readFrom(InputStream paramInputStream, long paramLong)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramInputStream, "input");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      readFrom(paramInputStream, paramLong, false);
      return this;
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public void readFully(Buffer paramBuffer, long paramLong)
    throws EOFException
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "sink");
    if (size() >= paramLong)
    {
      paramBuffer.write(this, paramLong);
      return;
    }
    paramBuffer.write(this, size());
    throw ((Throwable)new EOFException());
  }
  
  public void readFully(byte[] paramArrayOfByte)
    throws EOFException
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = read(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j != -1) {
        i += j;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public long readHexadecimalUnsignedLong()
    throws EOFException
  {
    if (size() != 0L)
    {
      long l2 = 0L;
      int j = 0;
      int k = 0;
      int m;
      long l1;
      label225:
      label293:
      do
      {
        Object localObject1 = this.head;
        Intrinsics.checkNotNull(localObject1);
        Object localObject2 = ((Segment)localObject1).data;
        int n = ((Segment)localObject1).pos;
        int i2 = ((Segment)localObject1).limit;
        m = j;
        l1 = l2;
        int i1;
        int i;
        for (;;)
        {
          i1 = k;
          if (n >= i2) {
            break label293;
          }
          i = localObject2[n];
          j = (byte)48;
          if ((i >= j) && (i <= (byte)57))
          {
            j = i - j;
          }
          else
          {
            j = (byte)97;
            if ((i >= j) && (i <= (byte)102))
            {
              j = i - j + 10;
            }
            else
            {
              j = (byte)65;
              if ((i < j) || (i > (byte)70)) {
                break label225;
              }
              j = i - j + 10;
            }
          }
          if ((0xF000000000000000 & l1) != 0L) {
            break;
          }
          l1 = l1 << 4 | j;
          n++;
          m++;
        }
        localObject1 = new Buffer().writeHexadecimalUnsignedLong(l1).writeByte(i);
        throw ((Throwable)new NumberFormatException("Number too large: " + ((Buffer)localObject1).readUtf8()));
        if (m != 0)
        {
          i1 = 1;
        }
        else
        {
          localObject2 = new StringBuilder().append("Expected leading [0-9a-fA-F] character but was 0x");
          localObject1 = _Util.toHexString(i);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          throw ((Throwable)new NumberFormatException((String)localObject1));
        }
        if (n == i2)
        {
          this.head = ((Segment)localObject1).pop();
          SegmentPool.recycle((Segment)localObject1);
        }
        else
        {
          ((Segment)localObject1).pos = n;
        }
        if (i1 != 0) {
          break;
        }
        l2 = l1;
        j = m;
        k = i1;
      } while (this.head != null);
      setSize$okio(size() - m);
      return l1;
    }
    throw ((Throwable)new EOFException());
  }
  
  public int readInt()
    throws EOFException
  {
    if (size() >= 4L)
    {
      Segment localSegment = this.head;
      Intrinsics.checkNotNull(localSegment);
      int j = localSegment.pos;
      int i = localSegment.limit;
      if (i - j < 4L)
      {
        i = (readByte() & 0xFF) << 24 | (readByte() & 0xFF) << 16 | (readByte() & 0xFF) << 8 | readByte() & 0xFF;
      }
      else
      {
        byte[] arrayOfByte = localSegment.data;
        int k = j + 1;
        j = arrayOfByte[j];
        int m = k + 1;
        k = arrayOfByte[k];
        int i1 = m + 1;
        int n = arrayOfByte[m];
        m = i1 + 1;
        i1 = arrayOfByte[i1];
        setSize$okio(size() - 4L);
        if (m == i)
        {
          this.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        else
        {
          localSegment.pos = m;
        }
        i = (j & 0xFF) << 24 | (k & 0xFF) << 16 | (n & 0xFF) << 8 | i1 & 0xFF;
      }
      return i;
    }
    throw ((Throwable)new EOFException());
  }
  
  public int readIntLe()
    throws EOFException
  {
    return _Util.reverseBytes(readInt());
  }
  
  public long readLong()
    throws EOFException
  {
    if (size() >= 8L)
    {
      Segment localSegment = this.head;
      Intrinsics.checkNotNull(localSegment);
      int k = localSegment.pos;
      int i = localSegment.limit;
      long l1;
      if (i - k < 8L)
      {
        l1 = (readInt() & 0xFFFFFFFF) << 32 | readInt() & 0xFFFFFFFF;
      }
      else
      {
        byte[] arrayOfByte = localSegment.data;
        int j = k + 1;
        long l8 = arrayOfByte[k];
        k = j + 1;
        long l4 = arrayOfByte[j];
        j = k + 1;
        l1 = arrayOfByte[k];
        k = j + 1;
        long l7 = arrayOfByte[j];
        j = k + 1;
        long l5 = arrayOfByte[k];
        k = j + 1;
        long l2 = arrayOfByte[j];
        j = k + 1;
        long l6 = arrayOfByte[k];
        k = j + 1;
        long l3 = arrayOfByte[j];
        setSize$okio(size() - 8L);
        if (k == i)
        {
          this.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        else
        {
          localSegment.pos = k;
        }
        l1 = (l4 & 0xFF) << 48 | (0xFF & l8) << 56 | (0xFF & l1) << 40 | (l7 & 0xFF) << 32 | (0xFF & l5) << 24 | (l2 & 0xFF) << 16 | (0xFF & l6) << 8 | l3 & 0xFF;
      }
      return l1;
    }
    throw ((Throwable)new EOFException());
  }
  
  public long readLongLe()
    throws EOFException
  {
    return _Util.reverseBytes(readLong());
  }
  
  public short readShort()
    throws EOFException
  {
    if (size() >= 2L)
    {
      Segment localSegment = this.head;
      Intrinsics.checkNotNull(localSegment);
      int j = localSegment.pos;
      int i = localSegment.limit;
      short s;
      if (i - j < 2)
      {
        s = (short)((readByte() & 0xFF) << 8 | readByte() & 0xFF);
      }
      else
      {
        byte[] arrayOfByte = localSegment.data;
        int m = j + 1;
        j = arrayOfByte[j];
        int k = m + 1;
        m = arrayOfByte[m];
        setSize$okio(size() - 2L);
        if (k == i)
        {
          this.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        else
        {
          localSegment.pos = k;
        }
        s = (short)((j & 0xFF) << 8 | m & 0xFF);
      }
      return s;
    }
    throw ((Throwable)new EOFException());
  }
  
  public short readShortLe()
    throws EOFException
  {
    return _Util.reverseBytes(readShort());
  }
  
  public String readString(long paramLong, Charset paramCharset)
    throws EOFException
  {
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (this.size >= paramLong)
      {
        if (paramLong == 0L) {
          return "";
        }
        Segment localSegment = this.head;
        Intrinsics.checkNotNull(localSegment);
        if (localSegment.pos + paramLong > localSegment.limit)
        {
          paramCharset = new String(readByteArray(paramLong), paramCharset);
          Log5ECF72.a(paramCharset);
          LogE84000.a(paramCharset);
          Log229316.a(paramCharset);
          return paramCharset;
        }
        paramCharset = new String(localSegment.data, localSegment.pos, (int)paramLong, paramCharset);
        Log5ECF72.a(paramCharset);
        LogE84000.a(paramCharset);
        Log229316.a(paramCharset);
        localSegment.pos += (int)paramLong;
        this.size -= paramLong;
        if (localSegment.pos == localSegment.limit)
        {
          this.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        return paramCharset;
      }
      throw ((Throwable)new EOFException());
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount: " + paramLong).toString()));
  }
  
  public String readString(Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    return readString(this.size, paramCharset);
  }
  
  public final UnsafeCursor readUnsafe()
  {
    return readUnsafe$default(this, null, 1, null);
  }
  
  public final UnsafeCursor readUnsafe(UnsafeCursor paramUnsafeCursor)
  {
    Intrinsics.checkNotNullParameter(paramUnsafeCursor, "unsafeCursor");
    int i;
    if (paramUnsafeCursor.buffer == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramUnsafeCursor.buffer = ((Buffer)this);
      paramUnsafeCursor.readWrite = false;
      return paramUnsafeCursor;
    }
    throw ((Throwable)new IllegalStateException("already attached to a buffer".toString()));
  }
  
  public String readUtf8()
  {
    return readString(this.size, Charsets.UTF_8);
  }
  
  public String readUtf8(long paramLong)
    throws EOFException
  {
    return readString(paramLong, Charsets.UTF_8);
  }
  
  public int readUtf8CodePoint()
    throws EOFException
  {
    if (size() != 0L)
    {
      int i = getByte(0L);
      int i1 = 65533;
      int j;
      int k;
      int m;
      if ((0x80 & i) == 0)
      {
        j = i & 0x7F;
        k = 1;
        m = 0;
      }
      else if ((0xE0 & i) == 192)
      {
        j = i & 0x1F;
        k = 2;
        m = 128;
      }
      else if ((0xF0 & i) == 224)
      {
        j = i & 0xF;
        k = 3;
        m = 2048;
      }
      else
      {
        if ((0xF8 & i) != 240) {
          break label328;
        }
        j = i & 0x7;
        k = 4;
        m = 65536;
      }
      if (size() >= k)
      {
        int n = 1;
        while (n < k)
        {
          int i2 = getByte(n);
          if ((0xC0 & i2) == 128)
          {
            j = j << 6 | 0x3F & i2;
            n++;
          }
          else
          {
            skip(n);
            j = i1;
            break label336;
          }
        }
        skip(k);
        if (j > 1114111) {
          j = i1;
        } else if ((55296 <= j) && (57343 >= j)) {
          j = i1;
        } else if (j < m) {
          j = i1;
        }
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder().append("size < ").append(k).append(": ").append(size()).append(" (to read code point prefixed 0x");
        String str = _Util.toHexString(i);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        throw ((Throwable)new EOFException(str + ')'));
        label328:
        skip(1L);
        j = i1;
      }
      label336:
      return j;
    }
    throw ((Throwable)new EOFException());
  }
  
  public String readUtf8Line()
    throws EOFException
  {
    long l = indexOf((byte)10);
    String str;
    if (l != -1L)
    {
      str = BufferKt.readUtf8Line(this, l);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
    }
    else if (size() != 0L)
    {
      str = readUtf8(size());
    }
    else
    {
      str = null;
    }
    return str;
  }
  
  public String readUtf8LineStrict()
    throws EOFException
  {
    return readUtf8LineStrict(Long.MAX_VALUE);
  }
  
  public String readUtf8LineStrict(long paramLong)
    throws EOFException
  {
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      long l1 = Long.MAX_VALUE;
      if (paramLong != Long.MAX_VALUE) {
        l1 = paramLong + 1L;
      }
      byte b = (byte)10;
      long l2 = indexOf(b, 0L, l1);
      if (l2 != -1L)
      {
        localObject = BufferKt.readUtf8Line(this, l2);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
      }
      else
      {
        if ((l1 >= size()) || (getByte(l1 - 1L) != (byte)13) || (getByte(l1) != b)) {
          break label150;
        }
        localObject = BufferKt.readUtf8Line(this, l1);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
      }
      return (String)localObject;
      label150:
      Object localObject = new Buffer();
      l1 = size();
      copyTo((Buffer)localObject, 0L, Math.min(32, l1));
      StringBuilder localStringBuilder = new StringBuilder().append("\\n not found: limit=");
      l1 = size();
      throw ((Throwable)new EOFException(Math.min(l1, paramLong) + " content=" + ((Buffer)localObject).readByteString().hex() + '…'));
    }
    throw ((Throwable)new IllegalArgumentException(("limit < 0: " + paramLong).toString()));
  }
  
  public boolean request(long paramLong)
  {
    boolean bool;
    if (this.size >= paramLong) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void require(long paramLong)
    throws EOFException
  {
    if (this.size >= paramLong) {
      return;
    }
    throw ((Throwable)new EOFException());
  }
  
  public int select(Options paramOptions)
  {
    Intrinsics.checkNotNullParameter(paramOptions, "options");
    int i = BufferKt.selectPrefix$default(this, paramOptions, false, 2, null);
    if (i == -1) {
      i = -1;
    } else {
      skip(paramOptions.getByteStrings$okio()[i].size());
    }
    return i;
  }
  
  public final void setSize$okio(long paramLong)
  {
    this.size = paramLong;
  }
  
  public final ByteString sha1()
  {
    return digest("SHA-1");
  }
  
  public final ByteString sha256()
  {
    return digest("SHA-256");
  }
  
  public final ByteString sha512()
  {
    return digest("SHA-512");
  }
  
  public final long size()
  {
    return this.size;
  }
  
  public void skip(long paramLong)
    throws EOFException
  {
    while (paramLong > 0L)
    {
      Segment localSegment = this.head;
      if (localSegment != null)
      {
        int i = (int)Math.min(paramLong, localSegment.limit - localSegment.pos);
        setSize$okio(size() - i);
        paramLong -= i;
        localSegment.pos += i;
        if (localSegment.pos == localSegment.limit)
        {
          this.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
      }
      else
      {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public final ByteString snapshot()
  {
    int i;
    if (size() <= Integer.MAX_VALUE) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return snapshot((int)size());
    }
    throw ((Throwable)new IllegalStateException(("size > Int.MAX_VALUE: " + size()).toString()));
  }
  
  public final ByteString snapshot(int paramInt)
  {
    Object localObject;
    if (paramInt == 0)
    {
      localObject = ByteString.EMPTY;
    }
    else
    {
      _Util.checkOffsetAndCount(size(), 0L, paramInt);
      int j = 0;
      int i = 0;
      localObject = this.head;
      while (j < paramInt)
      {
        Intrinsics.checkNotNull(localObject);
        if (((Segment)localObject).limit != ((Segment)localObject).pos)
        {
          j += ((Segment)localObject).limit - ((Segment)localObject).pos;
          i++;
          localObject = ((Segment)localObject).next;
        }
        else
        {
          throw ((Throwable)new AssertionError("s.limit == s.pos"));
        }
      }
      byte[][] arrayOfByte = new byte[i][];
      int[] arrayOfInt = new int[i * 2];
      j = 0;
      i = 0;
      for (localObject = this.head; j < paramInt; localObject = ((Segment)localObject).next)
      {
        Intrinsics.checkNotNull(localObject);
        arrayOfByte[i] = ((Segment)localObject).data;
        j += ((Segment)localObject).limit - ((Segment)localObject).pos;
        arrayOfInt[i] = Math.min(j, paramInt);
        arrayOfInt[(((Object[])arrayOfByte).length + i)] = ((Segment)localObject).pos;
        ((Segment)localObject).shared = true;
        i++;
      }
      localObject = (ByteString)new SegmentedByteString((byte[][])arrayOfByte, arrayOfInt);
    }
    return (ByteString)localObject;
  }
  
  public Timeout timeout()
  {
    return Timeout.NONE;
  }
  
  public String toString()
  {
    return snapshot().toString();
  }
  
  public final Segment writableSegment$okio(int paramInt)
  {
    int i = 1;
    if ((paramInt < 1) || (paramInt > 8192)) {
      i = 0;
    }
    if (i != 0)
    {
      Segment localSegment = this.head;
      if (localSegment == null)
      {
        localSegment = SegmentPool.take();
        this.head = localSegment;
        localSegment.prev = localSegment;
        localSegment.next = localSegment;
      }
      else
      {
        Intrinsics.checkNotNull(localSegment);
        localSegment = localSegment.prev;
        Intrinsics.checkNotNull(localSegment);
        if ((localSegment.limit + paramInt <= 8192) && (localSegment.owner)) {
          break label100;
        }
        localSegment = localSegment.push(SegmentPool.take());
      }
      label100:
      return localSegment;
    }
    throw ((Throwable)new IllegalArgumentException("unexpected capacity".toString()));
  }
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramByteBuffer, "source");
    int j = paramByteBuffer.remaining();
    int i = j;
    while (i > 0)
    {
      Segment localSegment = writableSegment$okio(1);
      int k = Math.min(i, 8192 - localSegment.limit);
      paramByteBuffer.get(localSegment.data, localSegment.limit, k);
      i -= k;
      localSegment.limit += k;
    }
    this.size += j;
    return j;
  }
  
  public Buffer write(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "byteString");
    paramByteString.write$okio(this, 0, paramByteString.size());
    return this;
  }
  
  public Buffer write(ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "byteString");
    paramByteString.write$okio(this, paramInt1, paramInt2);
    return this;
  }
  
  public Buffer write(Source paramSource, long paramLong)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramSource, "source");
    while (paramLong > 0L)
    {
      long l = paramSource.read(this, paramLong);
      if (l != -1L) {
        paramLong -= l;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
    return this;
  }
  
  public Buffer write(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "source");
    return write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public Buffer write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "source");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    int i = paramInt1 + paramInt2;
    while (paramInt1 < i)
    {
      Segment localSegment = writableSegment$okio(1);
      int j = Math.min(i - paramInt1, 8192 - localSegment.limit);
      byte[] arrayOfByte = localSegment.data;
      int k = localSegment.limit;
      ArraysKt.copyInto(paramArrayOfByte, arrayOfByte, k, paramInt1, paramInt1 + j);
      paramInt1 += j;
      localSegment.limit += j;
    }
    setSize$okio(size() + paramInt2);
    return this;
  }
  
  public void write(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "source");
    int i;
    if (paramBuffer != this) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      _Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramLong);
      while (paramLong > 0L)
      {
        Segment localSegment1 = paramBuffer.head;
        Intrinsics.checkNotNull(localSegment1);
        i = localSegment1.limit;
        localSegment1 = paramBuffer.head;
        Intrinsics.checkNotNull(localSegment1);
        if (paramLong < i - localSegment1.pos)
        {
          localSegment1 = this.head;
          if (localSegment1 != null)
          {
            Intrinsics.checkNotNull(localSegment1);
            localSegment1 = localSegment1.prev;
          }
          else
          {
            localSegment1 = null;
          }
          if ((localSegment1 != null) && (localSegment1.owner))
          {
            l = localSegment1.limit;
            if (localSegment1.shared) {
              i = 0;
            } else {
              i = localSegment1.pos;
            }
            if (l + paramLong - i <= ' ')
            {
              localSegment2 = paramBuffer.head;
              Intrinsics.checkNotNull(localSegment2);
              localSegment2.writeTo(localSegment1, (int)paramLong);
              paramBuffer.setSize$okio(paramBuffer.size() - paramLong);
              setSize$okio(size() + paramLong);
              break;
            }
          }
          localSegment1 = paramBuffer.head;
          Intrinsics.checkNotNull(localSegment1);
          paramBuffer.head = localSegment1.split((int)paramLong);
        }
        localSegment1 = paramBuffer.head;
        Intrinsics.checkNotNull(localSegment1);
        long l = localSegment1.limit - localSegment1.pos;
        paramBuffer.head = localSegment1.pop();
        Segment localSegment2 = this.head;
        if (localSegment2 == null)
        {
          this.head = localSegment1;
          localSegment1.prev = localSegment1;
          localSegment1.next = localSegment1.prev;
        }
        else
        {
          Intrinsics.checkNotNull(localSegment2);
          localSegment2 = localSegment2.prev;
          Intrinsics.checkNotNull(localSegment2);
          localSegment2.push(localSegment1).compact();
        }
        paramBuffer.setSize$okio(paramBuffer.size() - l);
        setSize$okio(size() + l);
        paramLong -= l;
      }
      return;
    }
    throw ((Throwable)new IllegalArgumentException("source == this".toString()));
  }
  
  public long writeAll(Source paramSource)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramSource, "source");
    long l2;
    for (long l1 = 0L;; l1 += l2)
    {
      l2 = paramSource.read(this, ' ');
      if (l2 == -1L) {
        return l1;
      }
    }
  }
  
  public Buffer writeByte(int paramInt)
  {
    Segment localSegment = writableSegment$okio(1);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    localSegment.limit = (i + 1);
    arrayOfByte[i] = ((byte)paramInt);
    setSize$okio(size() + 1L);
    return this;
  }
  
  public Buffer writeDecimalLong(long paramLong)
  {
    Buffer localBuffer = this;
    long l = paramLong;
    if (l == 0L)
    {
      localBuffer = localBuffer.writeByte(48);
    }
    else
    {
      int j = 0;
      paramLong = l;
      if (l < 0L)
      {
        paramLong = -l;
        if (paramLong < 0L) {
          localBuffer = localBuffer.writeUtf8("-9223372036854775808");
        } else {
          j = 1;
        }
      }
      else
      {
        if (paramLong < 100000000L)
        {
          if (paramLong < 10000L)
          {
            if (paramLong < 100L)
            {
              if (paramLong < 10L) {
                i = 1;
              } else {
                i = 2;
              }
            }
            else if (paramLong < 1000L) {
              i = 3;
            } else {
              i = 4;
            }
          }
          else if (paramLong < 1000000L)
          {
            if (paramLong < 100000L) {
              i = 5;
            } else {
              i = 6;
            }
          }
          else if (paramLong < 10000000L) {
            i = 7;
          } else {
            i = 8;
          }
        }
        else if (paramLong < 1000000000000L)
        {
          if (paramLong < 10000000000L)
          {
            if (paramLong < 1000000000L) {
              i = 9;
            } else {
              i = 10;
            }
          }
          else if (paramLong < 100000000000L) {
            i = 11;
          } else {
            i = 12;
          }
        }
        else if (paramLong < 1000000000000000L)
        {
          if (paramLong < 10000000000000L) {
            i = 13;
          } else if (paramLong < 100000000000000L) {
            i = 14;
          } else {
            i = 15;
          }
        }
        else if (paramLong < 100000000000000000L)
        {
          if (paramLong < 10000000000000000L) {
            i = 16;
          } else {
            i = 17;
          }
        }
        else if (paramLong < 1000000000000000000L) {
          i = 18;
        } else {
          i = 19;
        }
        int k = i;
        if (j != 0) {
          k = i + 1;
        }
        Segment localSegment = localBuffer.writableSegment$okio(k);
        byte[] arrayOfByte = localSegment.data;
        int i = localSegment.limit + k;
        while (paramLong != 0L)
        {
          l = 10;
          int m = (int)(paramLong % l);
          i--;
          arrayOfByte[i] = BufferKt.getHEX_DIGIT_BYTES()[m];
          paramLong /= l;
        }
        if (j != 0) {
          arrayOfByte[(i - 1)] = ((byte)45);
        }
        localSegment.limit += k;
        localBuffer.setSize$okio(localBuffer.size() + k);
      }
    }
    return localBuffer;
  }
  
  public Buffer writeHexadecimalUnsignedLong(long paramLong)
  {
    Buffer localBuffer = this;
    if (paramLong == 0L)
    {
      localBuffer = localBuffer.writeByte(48);
    }
    else
    {
      long l = paramLong | paramLong >>> 1;
      l |= l >>> 2;
      l |= l >>> 4;
      l |= l >>> 8;
      l |= l >>> 16;
      l |= l >>> 32;
      l -= (l >>> 1 & 0x5555555555555555);
      l = (l >>> 2 & 0x3333333333333333) + (0x3333333333333333 & l);
      l = (l >>> 4) + l & 0xF0F0F0F0F0F0F0F;
      l += (l >>> 8);
      l += (l >>> 16);
      int j = (int)((3 + ((l & 0x3F) + (0x3F & l >>> 32))) / 4);
      Segment localSegment = localBuffer.writableSegment$okio(j);
      byte[] arrayOfByte = localSegment.data;
      int i = localSegment.limit + j - 1;
      int k = localSegment.limit;
      while (i >= k)
      {
        arrayOfByte[i] = BufferKt.getHEX_DIGIT_BYTES()[((int)(0xF & paramLong))];
        paramLong >>>= 4;
        i--;
      }
      localSegment.limit += j;
      localBuffer.setSize$okio(localBuffer.size() + j);
    }
    return localBuffer;
  }
  
  public Buffer writeInt(int paramInt)
  {
    Segment localSegment = writableSegment$okio(4);
    byte[] arrayOfByte = localSegment.data;
    int j = localSegment.limit;
    int i = j + 1;
    arrayOfByte[j] = ((byte)(paramInt >>> 24 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(paramInt >>> 16 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[i] = ((byte)(paramInt & 0xFF));
    localSegment.limit = (i + 1);
    setSize$okio(size() + 4L);
    return this;
  }
  
  public Buffer writeIntLe(int paramInt)
  {
    return writeInt(_Util.reverseBytes(paramInt));
  }
  
  public Buffer writeLong(long paramLong)
  {
    Segment localSegment = writableSegment$okio(8);
    byte[] arrayOfByte = localSegment.data;
    int j = localSegment.limit;
    int i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 56 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(int)(paramLong >>> 48 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 40 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(int)(paramLong >>> 32 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 24 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(int)(paramLong >>> 16 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 8 & 0xFF));
    arrayOfByte[i] = ((byte)(int)(paramLong & 0xFF));
    localSegment.limit = (i + 1);
    setSize$okio(size() + 8L);
    return this;
  }
  
  public Buffer writeLongLe(long paramLong)
  {
    return writeLong(_Util.reverseBytes(paramLong));
  }
  
  public Buffer writeShort(int paramInt)
  {
    Segment localSegment = writableSegment$okio(2);
    byte[] arrayOfByte = localSegment.data;
    int j = localSegment.limit;
    int i = j + 1;
    arrayOfByte[j] = ((byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[i] = ((byte)(paramInt & 0xFF));
    localSegment.limit = (i + 1);
    setSize$okio(size() + 2L);
    return this;
  }
  
  public Buffer writeShortLe(int paramInt)
  {
    return writeShort(_Util.reverseBytes((short)paramInt));
  }
  
  public Buffer writeString(String paramString, int paramInt1, int paramInt2, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    int j = 1;
    int i;
    if (paramInt1 >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt2 >= paramInt1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt2 <= paramString.length()) {
          i = j;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          if (Intrinsics.areEqual(paramCharset, Charsets.UTF_8)) {
            return writeUtf8(paramString, paramInt1, paramInt2);
          }
          paramString = paramString.substring(paramInt1, paramInt2);
          Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          if (paramString != null)
          {
            paramString = paramString.getBytes(paramCharset);
            Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.String).getBytes(charset)");
            return write(paramString, 0, paramString.length);
          }
          throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        throw ((Throwable)new IllegalArgumentException(("endIndex > string.length: " + paramInt2 + " > " + paramString.length()).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("endIndex < beginIndex: " + paramInt2 + " < " + paramInt1).toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("beginIndex < 0: " + paramInt1).toString()));
  }
  
  public Buffer writeString(String paramString, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    int i = paramString.length();
    return writeString(paramString, 0, i, paramCharset);
  }
  
  public final Buffer writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    return writeTo$default(this, paramOutputStream, 0L, 2, null);
  }
  
  public final Buffer writeTo(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramOutputStream, "out");
    _Util.checkOffsetAndCount(this.size, 0L, paramLong);
    Object localObject2;
    for (Object localObject1 = this.head; paramLong > 0L; localObject1 = localObject2)
    {
      Intrinsics.checkNotNull(localObject1);
      int i = (int)Math.min(paramLong, ((Segment)localObject1).limit - ((Segment)localObject1).pos);
      paramOutputStream.write(((Segment)localObject1).data, ((Segment)localObject1).pos, i);
      ((Segment)localObject1).pos += i;
      this.size -= i;
      paramLong -= i;
      localObject2 = localObject1;
      if (((Segment)localObject1).pos == ((Segment)localObject1).limit)
      {
        localObject2 = ((Segment)localObject1).pop();
        this.head = ((Segment)localObject2);
        SegmentPool.recycle((Segment)localObject1);
      }
    }
    return this;
  }
  
  public Buffer writeUtf8(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    return writeUtf8(paramString, 0, paramString.length());
  }
  
  public Buffer writeUtf8(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "string");
    int i;
    if (paramInt1 >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt2 >= paramInt1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt2 <= paramString.length()) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          while (paramInt1 < paramInt2)
          {
            int k = paramString.charAt(paramInt1);
            Object localObject;
            if (k < 128)
            {
              Segment localSegment = writableSegment$okio(1);
              localObject = localSegment.data;
              int j = localSegment.limit - paramInt1;
              int m = Math.min(paramInt2, 8192 - j);
              i = paramInt1 + 1;
              localObject[(paramInt1 + j)] = ((byte)k);
              for (paramInt1 = i; paramInt1 < m; paramInt1++)
              {
                i = paramString.charAt(paramInt1);
                if (i >= 128) {
                  break;
                }
                localObject[(paramInt1 + j)] = ((byte)i);
              }
              i = paramInt1 + j - localSegment.limit;
              localSegment.limit += i;
              long l = size();
              setSize$okio(i + l);
            }
            else if (k < 2048)
            {
              localObject = writableSegment$okio(2);
              ((Segment)localObject).data[localObject.limit] = ((byte)(k >> 6 | 0xC0));
              ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x80 | k & 0x3F));
              ((Segment)localObject).limit += 2;
              setSize$okio(size() + 2L);
              paramInt1++;
            }
            else if ((k >= 55296) && (k <= 57343))
            {
              if (paramInt1 + 1 < paramInt2) {
                i = paramString.charAt(paramInt1 + 1);
              } else {
                i = 0;
              }
              if ((k <= 56319) && (56320 <= i) && (57343 >= i))
              {
                i = ((k & 0x3FF) << 10 | i & 0x3FF) + 65536;
                localObject = writableSegment$okio(4);
                ((Segment)localObject).data[localObject.limit] = ((byte)(i >> 18 | 0xF0));
                ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(i >> 12 & 0x3F | 0x80));
                ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(i >> 6 & 0x3F | 0x80));
                ((Segment)localObject).data[(localObject.limit + 3)] = ((byte)(0x80 | i & 0x3F));
                ((Segment)localObject).limit += 4;
                setSize$okio(size() + 4L);
                paramInt1 += 2;
              }
              else
              {
                writeByte(63);
                paramInt1++;
              }
            }
            else
            {
              localObject = writableSegment$okio(3);
              ((Segment)localObject).data[localObject.limit] = ((byte)(k >> 12 | 0xE0));
              ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x3F & k >> 6 | 0x80));
              ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(k & 0x3F | 0x80));
              ((Segment)localObject).limit += 3;
              setSize$okio(size() + 3L);
              paramInt1++;
            }
          }
          return this;
        }
        throw ((Throwable)new IllegalArgumentException(("endIndex > string.length: " + paramInt2 + " > " + paramString.length()).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("endIndex < beginIndex: " + paramInt2 + " < " + paramInt1).toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("beginIndex < 0: " + paramInt1).toString()));
  }
  
  public Buffer writeUtf8CodePoint(int paramInt)
  {
    if (paramInt < 128)
    {
      writeByte(paramInt);
    }
    else if (paramInt < 2048)
    {
      localObject = writableSegment$okio(2);
      ((Segment)localObject).data[localObject.limit] = ((byte)(paramInt >> 6 | 0xC0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x80 | paramInt & 0x3F));
      ((Segment)localObject).limit += 2;
      setSize$okio(size() + 2L);
    }
    else if ((55296 <= paramInt) && (57343 >= paramInt))
    {
      writeByte(63);
    }
    else if (paramInt < 65536)
    {
      localObject = writableSegment$okio(3);
      ((Segment)localObject).data[localObject.limit] = ((byte)(paramInt >> 12 | 0xE0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x3F & paramInt >> 6 | 0x80));
      ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(0x80 | paramInt & 0x3F));
      ((Segment)localObject).limit += 3;
      setSize$okio(size() + 3L);
    }
    else
    {
      if (paramInt > 1114111) {
        break label339;
      }
      localObject = writableSegment$okio(4);
      ((Segment)localObject).data[localObject.limit] = ((byte)(paramInt >> 18 | 0xF0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(paramInt >> 12 & 0x3F | 0x80));
      ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(paramInt >> 6 & 0x3F | 0x80));
      ((Segment)localObject).data[(localObject.limit + 3)] = ((byte)(0x80 | paramInt & 0x3F));
      ((Segment)localObject).limit += 4;
      setSize$okio(size() + 4L);
    }
    return this;
    label339:
    StringBuilder localStringBuilder = new StringBuilder().append("Unexpected code point: 0x");
    Object localObject = _Util.toHexString(paramInt);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw ((Throwable)new IllegalArgumentException((String)localObject));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\022\n\000\n\002\020\b\n\000\n\002\020\t\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\007\030\0002\0020\001B\005¢\006\002\020\002J\b\020\020\032\0020\021H\026J\016\020\022\032\0020\n2\006\020\023\032\0020\bJ\006\020\024\032\0020\bJ\016\020\025\032\0020\n2\006\020\026\032\0020\nJ\016\020\027\032\0020\b2\006\020\t\032\0020\nR\024\020\003\032\004\030\0010\0048\006@\006X\016¢\006\002\n\000R\024\020\005\032\004\030\0010\0068\006@\006X\016¢\006\002\n\000R\022\020\007\032\0020\b8\006@\006X\016¢\006\002\n\000R\022\020\t\032\0020\n8\006@\006X\016¢\006\002\n\000R\022\020\013\032\0020\f8\006@\006X\016¢\006\002\n\000R\020\020\r\032\004\030\0010\016X\016¢\006\002\n\000R\022\020\017\032\0020\b8\006@\006X\016¢\006\002\n\000¨\006\030"}, d2={"Lokio/Buffer$UnsafeCursor;", "Ljava/io/Closeable;", "()V", "buffer", "Lokio/Buffer;", "data", "", "end", "", "offset", "", "readWrite", "", "segment", "Lokio/Segment;", "start", "close", "", "expandBuffer", "minByteCount", "next", "resizeBuffer", "newSize", "seek", "okio"}, k=1, mv={1, 4, 0})
  public static final class UnsafeCursor
    implements Closeable
  {
    public Buffer buffer;
    public byte[] data;
    public int end = -1;
    public long offset = -1L;
    public boolean readWrite;
    private Segment segment;
    public int start = -1;
    
    public void close()
    {
      int i;
      if (this.buffer != null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        Object localObject = (Buffer)null;
        this.buffer = null;
        localObject = (Segment)null;
        this.segment = null;
        this.offset = -1L;
        localObject = (byte[])null;
        this.data = null;
        this.start = -1;
        this.end = -1;
        return;
      }
      throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
    }
    
    public final long expandBuffer(int paramInt)
    {
      int j = 1;
      int i;
      if (paramInt > 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt <= 8192) {
          i = j;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          Buffer localBuffer = this.buffer;
          if (localBuffer != null)
          {
            if (this.readWrite)
            {
              long l = localBuffer.size();
              Segment localSegment = localBuffer.writableSegment$okio(paramInt);
              paramInt = 8192 - localSegment.limit;
              localSegment.limit = 8192;
              localBuffer.setSize$okio(paramInt + l);
              this.segment = localSegment;
              this.offset = l;
              this.data = localSegment.data;
              this.start = (8192 - paramInt);
              this.end = 8192;
              return paramInt;
            }
            throw ((Throwable)new IllegalStateException("expandBuffer() only permitted for read/write buffers".toString()));
          }
          throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
        }
        throw ((Throwable)new IllegalArgumentException(("minByteCount > Segment.SIZE: " + paramInt).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("minByteCount <= 0: " + paramInt).toString()));
    }
    
    public final int next()
    {
      long l = this.offset;
      Buffer localBuffer = this.buffer;
      Intrinsics.checkNotNull(localBuffer);
      int i;
      if (l != localBuffer.size()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        l = this.offset;
        if (l == -1L) {
          l = 0L;
        } else {
          l += this.end - this.start;
        }
        return seek(l);
      }
      throw ((Throwable)new IllegalStateException("no more bytes".toString()));
    }
    
    public final long resizeBuffer(long paramLong)
    {
      Buffer localBuffer = this.buffer;
      if (localBuffer != null)
      {
        if (this.readWrite)
        {
          long l2 = localBuffer.size();
          int i = 1;
          long l1;
          Object localObject;
          if (paramLong <= l2)
          {
            if (paramLong < 0L) {
              i = 0;
            }
            if (i != 0)
            {
              l1 = l2 - paramLong;
              while (l1 > 0L)
              {
                localObject = localBuffer.head;
                Intrinsics.checkNotNull(localObject);
                localObject = ((Segment)localObject).prev;
                Intrinsics.checkNotNull(localObject);
                i = ((Segment)localObject).limit - ((Segment)localObject).pos;
                if (i <= l1)
                {
                  localBuffer.head = ((Segment)localObject).pop();
                  SegmentPool.recycle((Segment)localObject);
                  l1 -= i;
                }
                else
                {
                  ((Segment)localObject).limit -= (int)l1;
                }
              }
              localObject = (Segment)null;
              this.segment = null;
              this.offset = paramLong;
              localObject = (byte[])null;
              this.data = null;
              this.start = -1;
              this.end = -1;
            }
            else
            {
              throw ((Throwable)new IllegalArgumentException(("newSize < 0: " + paramLong).toString()));
            }
          }
          else if (paramLong > l2)
          {
            i = 1;
            l1 = paramLong - l2;
            while (l1 > 0L)
            {
              localObject = localBuffer.writableSegment$okio(1);
              int k = (int)Math.min(l1, 8192 - ((Segment)localObject).limit);
              ((Segment)localObject).limit += k;
              l1 -= k;
              int j = i;
              if (i != 0)
              {
                this.segment = ((Segment)localObject);
                this.offset = l2;
                this.data = ((Segment)localObject).data;
                this.start = (((Segment)localObject).limit - k);
                this.end = ((Segment)localObject).limit;
                j = 0;
              }
              i = j;
            }
          }
          localBuffer.setSize$okio(paramLong);
          return l2;
        }
        throw ((Throwable)new IllegalStateException("resizeBuffer() only permitted for read/write buffers".toString()));
      }
      throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
    }
    
    public final int seek(long paramLong)
    {
      Buffer localBuffer = this.buffer;
      if (localBuffer != null)
      {
        if ((paramLong >= -1) && (paramLong <= localBuffer.size()))
        {
          if ((paramLong != -1L) && (paramLong != localBuffer.size()))
          {
            long l4 = 0L;
            long l3 = localBuffer.size();
            Segment localSegment1 = localBuffer.head;
            Segment localSegment2 = localBuffer.head;
            Segment localSegment3 = this.segment;
            long l2 = l4;
            long l1 = l3;
            localObject1 = localSegment1;
            Object localObject2 = localSegment2;
            if (localSegment3 != null)
            {
              l1 = this.offset;
              i = this.start;
              Intrinsics.checkNotNull(localSegment3);
              l1 -= i - localSegment3.pos;
              if (l1 > paramLong)
              {
                localObject2 = this.segment;
                l2 = l4;
                localObject1 = localSegment1;
              }
              else
              {
                l2 = l1;
                localObject1 = this.segment;
                localObject2 = localSegment2;
                l1 = l3;
              }
            }
            if (l1 - paramLong > paramLong - l2)
            {
              l1 = l2;
              for (localObject2 = localObject1;; localObject2 = ((Segment)localObject2).next)
              {
                Intrinsics.checkNotNull(localObject2);
                localObject1 = localObject2;
                l2 = l1;
                if (paramLong < ((Segment)localObject2).limit - ((Segment)localObject2).pos + l1) {
                  break;
                }
                l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
              }
            }
            for (;;)
            {
              localObject1 = localObject2;
              l2 = l1;
              if (l1 <= paramLong) {
                break;
              }
              Intrinsics.checkNotNull(localObject2);
              localObject2 = ((Segment)localObject2).prev;
              Intrinsics.checkNotNull(localObject2);
              l1 -= ((Segment)localObject2).limit - ((Segment)localObject2).pos;
            }
            localObject2 = localObject1;
            if (this.readWrite)
            {
              Intrinsics.checkNotNull(localObject1);
              localObject2 = localObject1;
              if (((Segment)localObject1).shared)
              {
                localObject2 = ((Segment)localObject1).unsharedCopy();
                if (localBuffer.head == localObject1) {
                  localBuffer.head = ((Segment)localObject2);
                }
                localObject2 = ((Segment)localObject1).push((Segment)localObject2);
                localObject1 = ((Segment)localObject2).prev;
                Intrinsics.checkNotNull(localObject1);
                ((Segment)localObject1).pop();
              }
            }
            this.segment = ((Segment)localObject2);
            this.offset = paramLong;
            Intrinsics.checkNotNull(localObject2);
            this.data = ((Segment)localObject2).data;
            this.start = (((Segment)localObject2).pos + (int)(paramLong - l2));
            int i = ((Segment)localObject2).limit;
            this.end = i;
            return i - this.start;
          }
          localObject1 = (Segment)null;
          this.segment = null;
          this.offset = paramLong;
          localObject1 = (byte[])null;
          this.data = null;
          this.start = -1;
          this.end = -1;
          return -1;
        }
        Object localObject1 = StringCompanionObject.INSTANCE;
        localObject1 = String.format("offset=%s > size=%s", Arrays.copyOf(new Object[] { Long.valueOf(paramLong), Long.valueOf(localBuffer.size()) }, 2));
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        Intrinsics.checkNotNullExpressionValue(localObject1, "java.lang.String.format(format, *args)");
        throw ((Throwable)new ArrayIndexOutOfBoundsException((String)localObject1));
      }
      throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/Buffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */