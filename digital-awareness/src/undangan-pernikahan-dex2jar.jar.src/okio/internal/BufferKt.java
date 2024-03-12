package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okio.Buffer;
import okio.ByteString;
import okio.Options;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio._Platform;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\000v\n\000\n\002\020\022\n\002\b\003\n\002\020\t\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\005\n\002\020\002\n\002\030\002\n\002\b\b\n\002\020\000\n\000\n\002\020\005\n\002\b\006\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\t\n\002\020\n\n\000\n\002\020\016\n\002\b\006\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\025\n\002\030\002\n\002\b\004\0320\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\b2\006\020\016\032\0020\0012\006\020\017\032\0020\b2\006\020\020\032\0020\bH\000\032\r\020\021\032\0020\022*\0020\023H\b\032\r\020\024\032\0020\005*\0020\023H\b\032\r\020\025\032\0020\023*\0020\023H\b\032%\020\026\032\0020\023*\0020\0232\006\020\027\032\0020\0232\006\020\030\032\0020\0052\006\020\031\032\0020\005H\b\032\027\020\032\032\0020\n*\0020\0232\b\020\033\032\004\030\0010\034H\b\032\025\020\035\032\0020\036*\0020\0232\006\020\037\032\0020\005H\b\032\r\020 \032\0020\b*\0020\023H\b\032%\020!\032\0020\005*\0020\0232\006\020\"\032\0020\0362\006\020#\032\0020\0052\006\020$\032\0020\005H\b\032\035\020!\032\0020\005*\0020\0232\006\020\016\032\0020%2\006\020#\032\0020\005H\b\032\035\020&\032\0020\005*\0020\0232\006\020'\032\0020%2\006\020#\032\0020\005H\b\032-\020(\032\0020\n*\0020\0232\006\020\030\032\0020\0052\006\020\016\032\0020%2\006\020\017\032\0020\b2\006\020\031\032\0020\bH\b\032\025\020)\032\0020\b*\0020\0232\006\020*\032\0020\001H\b\032%\020)\032\0020\b*\0020\0232\006\020*\032\0020\0012\006\020\030\032\0020\b2\006\020\031\032\0020\bH\b\032\035\020)\032\0020\005*\0020\0232\006\020*\032\0020\0232\006\020\031\032\0020\005H\b\032\025\020+\032\0020\005*\0020\0232\006\020*\032\0020,H\b\032\r\020-\032\0020\036*\0020\023H\b\032\r\020.\032\0020\001*\0020\023H\b\032\025\020.\032\0020\001*\0020\0232\006\020\031\032\0020\005H\b\032\r\020/\032\0020%*\0020\023H\b\032\025\020/\032\0020%*\0020\0232\006\020\031\032\0020\005H\b\032\r\0200\032\0020\005*\0020\023H\b\032\025\0201\032\0020\022*\0020\0232\006\020*\032\0020\001H\b\032\035\0201\032\0020\022*\0020\0232\006\020*\032\0020\0232\006\020\031\032\0020\005H\b\032\r\0202\032\0020\005*\0020\023H\b\032\r\0203\032\0020\b*\0020\023H\b\032\r\0204\032\0020\005*\0020\023H\b\032\r\0205\032\00206*\0020\023H\b\032\025\0207\032\00208*\0020\0232\006\020\031\032\0020\005H\b\032\r\0209\032\0020\b*\0020\023H\b\032\017\020:\032\004\030\00108*\0020\023H\b\032\025\020;\032\00208*\0020\0232\006\020<\032\0020\005H\b\032\025\020=\032\0020\b*\0020\0232\006\020>\032\0020?H\b\032\025\020@\032\0020\022*\0020\0232\006\020\031\032\0020\005H\b\032\r\020A\032\0020%*\0020\023H\b\032\025\020A\032\0020%*\0020\0232\006\020\031\032\0020\bH\b\032\025\020B\032\0020\f*\0020\0232\006\020C\032\0020\bH\b\032\025\020D\032\0020\023*\0020\0232\006\020E\032\0020\001H\b\032%\020D\032\0020\023*\0020\0232\006\020E\032\0020\0012\006\020\030\032\0020\b2\006\020\031\032\0020\bH\b\032\035\020D\032\0020\022*\0020\0232\006\020E\032\0020\0232\006\020\031\032\0020\005H\b\032)\020D\032\0020\023*\0020\0232\006\020F\032\0020%2\b\b\002\020\030\032\0020\b2\b\b\002\020\031\032\0020\bH\b\032\035\020D\032\0020\023*\0020\0232\006\020E\032\0020G2\006\020\031\032\0020\005H\b\032\025\020H\032\0020\005*\0020\0232\006\020E\032\0020GH\b\032\025\020I\032\0020\023*\0020\0232\006\020\"\032\0020\bH\b\032\025\020J\032\0020\023*\0020\0232\006\020K\032\0020\005H\b\032\025\020L\032\0020\023*\0020\0232\006\020K\032\0020\005H\b\032\025\020M\032\0020\023*\0020\0232\006\020N\032\0020\bH\b\032\025\020O\032\0020\023*\0020\0232\006\020K\032\0020\005H\b\032\025\020P\032\0020\023*\0020\0232\006\020Q\032\0020\bH\b\032%\020R\032\0020\023*\0020\0232\006\020S\032\002082\006\020T\032\0020\b2\006\020U\032\0020\bH\b\032\025\020V\032\0020\023*\0020\0232\006\020W\032\0020\bH\b\032\024\020X\032\00208*\0020\0232\006\020Y\032\0020\005H\000\032?\020Z\032\002H[\"\004\b\000\020[*\0020\0232\006\020#\032\0020\0052\032\020\\\032\026\022\006\022\004\030\0010\f\022\004\022\0020\005\022\004\022\002H[0]H\bø\001\000¢\006\002\020^\032\036\020_\032\0020\b*\0020\0232\006\020>\032\0020?2\b\b\002\020`\032\0020\nH\000\"\024\020\000\032\0020\001X\004¢\006\b\n\000\032\004\b\002\020\003\"\016\020\004\032\0020\005XT¢\006\002\n\000\"\016\020\006\032\0020\005XT¢\006\002\n\000\"\016\020\007\032\0020\bXT¢\006\002\n\000\002\007\n\005\b20\001¨\006a"}, d2={"HEX_DIGIT_BYTES", "", "getHEX_DIGIT_BYTES", "()[B", "OVERFLOW_DIGIT_START", "", "OVERFLOW_ZONE", "SEGMENTING_THRESHOLD", "", "rangeEquals", "", "segment", "Lokio/Segment;", "segmentPos", "bytes", "bytesOffset", "bytesLimit", "commonClear", "", "Lokio/Buffer;", "commonCompleteSegmentByteCount", "commonCopy", "commonCopyTo", "out", "offset", "byteCount", "commonEquals", "other", "", "commonGet", "", "pos", "commonHashCode", "commonIndexOf", "b", "fromIndex", "toIndex", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonRangeEquals", "commonRead", "sink", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadLong", "commonReadShort", "", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonSnapshot", "commonWritableSegment", "minimumCapacity", "commonWrite", "source", "byteString", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteLong", "commonWriteShort", "s", "commonWriteUtf8", "string", "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "readUtf8Line", "newline", "seek", "T", "lambda", "Lkotlin/Function2;", "(Lokio/Buffer;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "selectPrefix", "selectTruncated", "okio"}, k=2, mv={1, 4, 0})
public final class BufferKt
{
  private static final byte[] HEX_DIGIT_BYTES = _Platform.asUtf8ToByteArray("0123456789abcdef");
  public static final long OVERFLOW_DIGIT_START = -7L;
  public static final long OVERFLOW_ZONE = -922337203685477580L;
  public static final int SEGMENTING_THRESHOLD = 4096;
  
  public static final void commonClear(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonClear");
    paramBuffer.skip(paramBuffer.size());
  }
  
  public static final long commonCompleteSegmentByteCount(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonCompleteSegmentByteCount");
    long l2 = paramBuffer.size();
    if (l2 == 0L) {
      return 0L;
    }
    paramBuffer = paramBuffer.head;
    Intrinsics.checkNotNull(paramBuffer);
    paramBuffer = paramBuffer.prev;
    Intrinsics.checkNotNull(paramBuffer);
    long l1 = l2;
    if (paramBuffer.limit < 8192)
    {
      l1 = l2;
      if (paramBuffer.owner) {
        l1 = l2 - (paramBuffer.limit - paramBuffer.pos);
      }
    }
    return l1;
  }
  
  public static final Buffer commonCopy(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonCopy");
    Buffer localBuffer = new Buffer();
    if (paramBuffer.size() == 0L) {
      return localBuffer;
    }
    Segment localSegment2 = paramBuffer.head;
    Intrinsics.checkNotNull(localSegment2);
    Segment localSegment3 = localSegment2.sharedCopy();
    localBuffer.head = localSegment3;
    localSegment3.prev = localBuffer.head;
    localSegment3.next = localSegment3.prev;
    for (Segment localSegment1 = localSegment2.next; localSegment1 != localSegment2; localSegment1 = localSegment1.next)
    {
      Segment localSegment4 = localSegment3.prev;
      Intrinsics.checkNotNull(localSegment4);
      Intrinsics.checkNotNull(localSegment1);
      localSegment4.push(localSegment1.sharedCopy());
    }
    localBuffer.setSize$okio(paramBuffer.size());
    return localBuffer;
  }
  
  public static final Buffer commonCopyTo(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong1, long paramLong2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer1, "$this$commonCopyTo");
    Intrinsics.checkNotNullParameter(paramBuffer2, "out");
    _Util.checkOffsetAndCount(paramBuffer1.size(), paramLong1, paramLong2);
    if (paramLong2 == 0L) {
      return paramBuffer1;
    }
    paramBuffer2.setSize$okio(paramBuffer2.size() + paramLong2);
    long l1;
    Segment localSegment2;
    long l2;
    for (Segment localSegment1 = paramBuffer1.head;; localSegment1 = localSegment1.next)
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
      if (paramBuffer2.head == null)
      {
        localSegment1.prev = localSegment1;
        localSegment1.next = localSegment1.prev;
        paramBuffer2.head = localSegment1.next;
      }
      else
      {
        Segment localSegment3 = paramBuffer2.head;
        Intrinsics.checkNotNull(localSegment3);
        localSegment3 = localSegment3.prev;
        Intrinsics.checkNotNull(localSegment3);
        localSegment3.push(localSegment1);
      }
      l2 -= localSegment1.limit - localSegment1.pos;
      l1 = 0L;
      localSegment2 = localSegment2.next;
    }
    return paramBuffer1;
  }
  
  public static final boolean commonEquals(Buffer paramBuffer, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonEquals");
    if (paramBuffer == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Buffer)) {
      return false;
    }
    if (paramBuffer.size() != ((Buffer)paramObject).size()) {
      return false;
    }
    if (paramBuffer.size() == 0L) {
      return true;
    }
    Object localObject3 = paramBuffer.head;
    Intrinsics.checkNotNull(localObject3);
    paramObject = ((Buffer)paramObject).head;
    Intrinsics.checkNotNull(paramObject);
    int j = ((Segment)localObject3).pos;
    int i = ((Segment)paramObject).pos;
    long l1 = 0L;
    while (l1 < paramBuffer.size())
    {
      long l3 = Math.min(((Segment)localObject3).limit - j, ((Segment)paramObject).limit - i);
      long l2 = 0L;
      int k = j;
      while (l2 < l3)
      {
        if (localObject3.data[k] != paramObject.data[i]) {
          return false;
        }
        k++;
        i++;
        l2 += 1L;
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
    return true;
  }
  
  public static final byte commonGet(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonGet");
    _Util.checkOffsetAndCount(paramBuffer.size(), paramLong, 1L);
    Segment localSegment = paramBuffer.head;
    if (localSegment != null)
    {
      if (paramBuffer.size() - paramLong < paramLong)
      {
        for (l1 = paramBuffer.size(); l1 > paramLong; l1 -= localSegment.limit - localSegment.pos)
        {
          localSegment = localSegment.prev;
          Intrinsics.checkNotNull(localSegment);
        }
        Intrinsics.checkNotNull(localSegment);
        return localSegment.data[((int)(localSegment.pos + paramLong - l1))];
      }
      long l1 = 0L;
      paramBuffer = localSegment;
      for (;;)
      {
        long l2 = paramBuffer.limit - paramBuffer.pos + l1;
        if (l2 > paramLong)
        {
          Intrinsics.checkNotNull(paramBuffer);
          return paramBuffer.data[((int)(paramBuffer.pos + paramLong - l1))];
        }
        paramBuffer = paramBuffer.next;
        Intrinsics.checkNotNull(paramBuffer);
        l1 = l2;
      }
    }
    paramBuffer = (Segment)null;
    Intrinsics.checkNotNull(paramBuffer);
    return paramBuffer.data[((int)(paramBuffer.pos + paramLong + 1L))];
  }
  
  public static final int commonHashCode(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonHashCode");
    Object localObject = paramBuffer.head;
    if (localObject != null)
    {
      int i = 1;
      int k;
      Segment localSegment;
      do
      {
        int j = ((Segment)localObject).pos;
        int m = ((Segment)localObject).limit;
        k = i;
        while (j < m)
        {
          k = k * 31 + localObject.data[j];
          j++;
        }
        localSegment = ((Segment)localObject).next;
        Intrinsics.checkNotNull(localSegment);
        localObject = localSegment;
        i = k;
      } while (localSegment != paramBuffer.head);
      return k;
    }
    return 0;
  }
  
  public static final long commonIndexOf(Buffer paramBuffer, byte paramByte, long paramLong1, long paramLong2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonIndexOf");
    long l1 = paramLong1;
    int i;
    if ((0L <= l1) && (paramLong2 >= l1)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      long l2 = paramLong2;
      if (paramLong2 > paramBuffer.size()) {
        l2 = paramBuffer.size();
      }
      if (l1 == l2) {
        return -1L;
      }
      Object localObject2 = paramBuffer;
      int k = 0;
      paramBuffer = ((Buffer)localObject2).head;
      if (paramBuffer != null)
      {
        Object localObject1;
        int j;
        if (((Buffer)localObject2).size() - l1 < l1)
        {
          for (paramLong1 = ((Buffer)localObject2).size(); paramLong1 > l1; paramLong1 -= paramBuffer.limit - paramBuffer.pos)
          {
            paramBuffer = paramBuffer.prev;
            Intrinsics.checkNotNull(paramBuffer);
          }
          localObject1 = paramBuffer;
          int m = 0;
          if (localObject1 != null)
          {
            Object localObject3 = localObject1;
            while (paramLong1 < l2)
            {
              byte[] arrayOfByte = ((Segment)localObject3).data;
              j = (int)Math.min(((Segment)localObject3).limit, ((Segment)localObject3).pos + l2 - paramLong1);
              for (i = (int)(((Segment)localObject3).pos + l1 - paramLong1); i < j; i++) {
                if (arrayOfByte[i] == paramByte) {
                  return i - ((Segment)localObject3).pos + paramLong1;
                }
              }
              paramLong1 += ((Segment)localObject3).limit - ((Segment)localObject3).pos;
              l1 = paramLong1;
              localObject3 = ((Segment)localObject3).next;
              Intrinsics.checkNotNull(localObject3);
            }
            return -1L;
          }
          return -1L;
        }
        for (paramLong1 = 0L;; paramLong1 = paramLong2)
        {
          paramLong2 = paramBuffer.limit - paramBuffer.pos + paramLong1;
          if (paramLong2 > l1)
          {
            paramLong2 = paramLong1;
            if (paramBuffer != null)
            {
              localObject1 = paramBuffer;
              long l3 = paramLong2;
              while (l3 < l2)
              {
                localObject2 = ((Segment)localObject1).data;
                j = (int)Math.min(((Segment)localObject1).limit, ((Segment)localObject1).pos + l2 - l3);
                for (i = (int)(((Segment)localObject1).pos + l1 - l3); i < j; i++) {
                  if (localObject2[i] == paramByte) {
                    return i - ((Segment)localObject1).pos + l3;
                  }
                }
                l3 += ((Segment)localObject1).limit - ((Segment)localObject1).pos;
                l1 = l3;
                localObject1 = ((Segment)localObject1).next;
                Intrinsics.checkNotNull(localObject1);
              }
              return -1L;
            }
            return -1L;
          }
          paramBuffer = paramBuffer.next;
          Intrinsics.checkNotNull(paramBuffer);
        }
      }
      paramBuffer = (Segment)null;
      return -1L;
    }
    throw ((Throwable)new IllegalArgumentException(("size=" + paramBuffer.size() + " fromIndex=" + l1 + " toIndex=" + paramLong2).toString()));
  }
  
  public static final long commonIndexOf(Buffer paramBuffer, ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonIndexOf");
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
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
        int n = 0;
        Segment localSegment = paramBuffer.head;
        if (localSegment != null)
        {
          Object localObject;
          int k;
          int j;
          long l4;
          int m;
          if (paramBuffer.size() - l2 < l2)
          {
            for (l1 = paramBuffer.size(); l1 > l2; l1 -= localSegment.limit - localSegment.pos)
            {
              localSegment = localSegment.prev;
              Intrinsics.checkNotNull(localSegment);
            }
            localObject = localSegment;
            if (localObject != null)
            {
              l2 = l1;
              byte[] arrayOfByte = paramByteString.internalArray$okio();
              k = arrayOfByte[0];
              j = paramByteString.size();
              l3 = paramBuffer.size() - j + 1L;
              paramBuffer = (Buffer)localObject;
              while (l2 < l3)
              {
                paramByteString = paramBuffer.data;
                i = paramBuffer.limit;
                l4 = paramBuffer.pos;
                m = (int)Math.min(i, l4 + l3 - l2);
                for (i = (int)(paramBuffer.pos + paramLong - l2); i < m; i++) {
                  if ((paramByteString[i] == k) && (rangeEquals(paramBuffer, i + 1, arrayOfByte, 1, j))) {
                    return i - paramBuffer.pos + l2;
                  }
                }
                l2 += paramBuffer.limit - paramBuffer.pos;
                paramLong = l2;
                paramBuffer = paramBuffer.next;
                Intrinsics.checkNotNull(paramBuffer);
              }
              return -1L;
            }
            return -1L;
          }
          long l3 = 0L;
          long l1 = l2;
          for (l2 = l3;; l2 = l3)
          {
            l3 = localSegment.limit - localSegment.pos + l2;
            if (l3 > l1)
            {
              n = 0;
              if (localSegment != null)
              {
                l3 = l2;
                localObject = paramByteString.internalArray$okio();
                j = localObject[0];
                k = paramByteString.size();
                l4 = paramBuffer.size() - k + 1L;
                while (l3 < l4)
                {
                  paramBuffer = localSegment.data;
                  i = localSegment.limit;
                  long l5 = localSegment.pos;
                  m = (int)Math.min(i, l5 + l4 - l3);
                  for (i = (int)(localSegment.pos + paramLong - l3); i < m; i++) {
                    if ((paramBuffer[i] == j) && (rangeEquals(localSegment, i + 1, (byte[])localObject, 1, k))) {
                      return i - localSegment.pos + l3;
                    }
                  }
                  l3 += localSegment.limit - localSegment.pos;
                  paramLong = l3;
                  localSegment = localSegment.next;
                  Intrinsics.checkNotNull(localSegment);
                }
                return -1L;
              }
              return -1L;
            }
            localSegment = localSegment.next;
            Intrinsics.checkNotNull(localSegment);
          }
        }
        paramBuffer = (Segment)null;
        return -1L;
      }
      throw ((Throwable)new IllegalArgumentException(("fromIndex < 0: " + paramLong).toString()));
    }
    throw ((Throwable)new IllegalArgumentException("bytes is empty".toString()));
  }
  
  public static final long commonIndexOfElement(Buffer paramBuffer, ByteString paramByteString, long paramLong)
  {
    int i1 = 0;
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonIndexOfElement");
    Intrinsics.checkNotNullParameter(paramByteString, "targetBytes");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      Object localObject2 = paramBuffer;
      Object localObject1 = ((Buffer)localObject2).head;
      if (localObject1 != null)
      {
        int m;
        int k;
        long l2;
        int j;
        int n;
        if (((Buffer)localObject2).size() - paramLong < paramLong)
        {
          for (l1 = ((Buffer)localObject2).size(); l1 > paramLong; l1 -= ((Segment)localObject1).limit - ((Segment)localObject1).pos)
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
              l2 = paramLong;
              while (l1 < paramBuffer.size())
              {
                localObject1 = paramByteString.data;
                j = (int)(paramByteString.pos + l2 - l1);
                i = paramByteString.limit;
                while (j < i)
                {
                  n = localObject1[j];
                  if ((n != m) && (n != k)) {
                    j++;
                  } else {
                    return j - paramByteString.pos + l1;
                  }
                }
                paramLong = l1 + (paramByteString.limit - paramByteString.pos);
                l2 = paramLong;
                paramByteString = paramByteString.next;
                Intrinsics.checkNotNull(paramByteString);
                l1 = paramLong;
              }
            }
            else
            {
              localObject2 = paramByteString.internalArray$okio();
              paramByteString = (ByteString)localObject1;
              while (l1 < paramBuffer.size())
              {
                localObject1 = paramByteString.data;
                i = (int)(paramByteString.pos + paramLong - l1);
                k = paramByteString.limit;
                while (i < k)
                {
                  n = localObject1[i];
                  m = localObject2.length;
                  for (j = 0; j < m; j++) {
                    if (n == localObject2[j]) {
                      return i - paramByteString.pos + l1;
                    }
                  }
                  i++;
                }
                l1 += paramByteString.limit - paramByteString.pos;
                paramLong = l1;
                paramByteString = paramByteString.next;
                Intrinsics.checkNotNull(paramByteString);
              }
            }
            return -1L;
          }
          return -1L;
        }
        for (long l1 = 0L;; l1 = l2)
        {
          l2 = ((Segment)localObject1).limit - ((Segment)localObject1).pos + l1;
          if (l2 > paramLong)
          {
            if (localObject1 != null)
            {
              localObject2 = localObject1;
              l2 = l1;
              if (paramByteString.size() == 2)
              {
                k = paramByteString.getByte(0);
                m = paramByteString.getByte(1);
                while (l2 < paramBuffer.size())
                {
                  paramByteString = ((Segment)localObject2).data;
                  j = (int)(((Segment)localObject2).pos + paramLong - l2);
                  i = ((Segment)localObject2).limit;
                  while (j < i)
                  {
                    n = paramByteString[j];
                    if ((n != k) && (n != m)) {
                      j++;
                    } else {
                      return j - ((Segment)localObject2).pos + l2;
                    }
                  }
                  l2 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                  paramLong = l2;
                  localObject2 = ((Segment)localObject2).next;
                  Intrinsics.checkNotNull(localObject2);
                }
              }
              else
              {
                paramByteString = paramByteString.internalArray$okio();
                l1 = paramLong;
                while (l2 < paramBuffer.size())
                {
                  byte[] arrayOfByte = ((Segment)localObject2).data;
                  i = (int)(((Segment)localObject2).pos + l1 - l2);
                  m = ((Segment)localObject2).limit;
                  while (i < m)
                  {
                    n = arrayOfByte[i];
                    k = paramByteString.length;
                    for (j = 0; j < k; j++) {
                      if (n == paramByteString[j]) {
                        return i - ((Segment)localObject2).pos + l2;
                      }
                    }
                    i++;
                  }
                  paramLong = l2 + (((Segment)localObject2).limit - ((Segment)localObject2).pos);
                  l1 = paramLong;
                  localObject2 = ((Segment)localObject2).next;
                  Intrinsics.checkNotNull(localObject2);
                  l2 = paramLong;
                }
              }
              return -1L;
            }
            return -1L;
          }
          localObject1 = ((Segment)localObject1).next;
          Intrinsics.checkNotNull(localObject1);
        }
      }
      paramBuffer = (Segment)null;
      return -1L;
    }
    throw ((Throwable)new IllegalArgumentException(("fromIndex < 0: " + paramLong).toString()));
  }
  
  public static final boolean commonRangeEquals(Buffer paramBuffer, long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonRangeEquals");
    Intrinsics.checkNotNullParameter(paramByteString, "bytes");
    if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (paramBuffer.size() - paramLong >= paramInt2) && (paramByteString.size() - paramInt1 >= paramInt2))
    {
      for (int i = 0; i < paramInt2; i++) {
        if (paramBuffer.getByte(i + paramLong) != paramByteString.getByte(paramInt1 + i)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public static final int commonRead(Buffer paramBuffer, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonRead");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    return paramBuffer.read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final int commonRead(Buffer paramBuffer, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonRead");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    Segment localSegment = paramBuffer.head;
    if (localSegment != null)
    {
      paramInt2 = Math.min(paramInt2, localSegment.limit - localSegment.pos);
      ArraysKt.copyInto(localSegment.data, paramArrayOfByte, paramInt1, localSegment.pos, localSegment.pos + paramInt2);
      localSegment.pos += paramInt2;
      paramBuffer.setSize$okio(paramBuffer.size() - paramInt2);
      if (localSegment.pos == localSegment.limit)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      return paramInt2;
    }
    return -1;
  }
  
  public static final long commonRead(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer1, "$this$commonRead");
    Intrinsics.checkNotNullParameter(paramBuffer2, "sink");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer1.size() == 0L) {
        return -1L;
      }
      long l = paramLong;
      if (paramLong > paramBuffer1.size()) {
        l = paramBuffer1.size();
      }
      paramBuffer2.write(paramBuffer1, l);
      return l;
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount < 0: " + paramLong).toString()));
  }
  
  public static final long commonReadAll(Buffer paramBuffer, Sink paramSink)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadAll");
    Intrinsics.checkNotNullParameter(paramSink, "sink");
    long l = paramBuffer.size();
    if (l > 0L) {
      paramSink.write(paramBuffer, l);
    }
    return l;
  }
  
  public static final byte commonReadByte(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadByte");
    if (paramBuffer.size() != 0L)
    {
      Segment localSegment = paramBuffer.head;
      Intrinsics.checkNotNull(localSegment);
      int j = localSegment.pos;
      int k = localSegment.limit;
      byte[] arrayOfByte = localSegment.data;
      int i = j + 1;
      byte b = arrayOfByte[j];
      paramBuffer.setSize$okio(paramBuffer.size() - 1L);
      if (i == k)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      else
      {
        localSegment.pos = i;
      }
      return b;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final byte[] commonReadByteArray(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadByteArray");
    return paramBuffer.readByteArray(paramBuffer.size());
  }
  
  public static final byte[] commonReadByteArray(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadByteArray");
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer.size() >= paramLong)
      {
        byte[] arrayOfByte = new byte[(int)paramLong];
        paramBuffer.readFully(arrayOfByte);
        return arrayOfByte;
      }
      throw ((Throwable)new EOFException());
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount: " + paramLong).toString()));
  }
  
  public static final ByteString commonReadByteString(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadByteString");
    return paramBuffer.readByteString(paramBuffer.size());
  }
  
  public static final ByteString commonReadByteString(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadByteString");
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer.size() >= paramLong)
      {
        if (paramLong >= 'က')
        {
          ByteString localByteString = paramBuffer.snapshot((int)paramLong);
          paramBuffer.skip(paramLong);
          return localByteString;
        }
        return new ByteString(paramBuffer.readByteArray(paramLong));
      }
      throw ((Throwable)new EOFException());
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount: " + paramLong).toString()));
  }
  
  public static final long commonReadDecimalLong(Buffer paramBuffer)
  {
    Object localObject = paramBuffer;
    int i2 = 0;
    Intrinsics.checkNotNullParameter(localObject, "$this$commonReadDecimalLong");
    if (paramBuffer.size() != 0L)
    {
      long l1 = 0L;
      int k = 0;
      int m = 0;
      int i = 0;
      long l2 = -7L;
      for (;;)
      {
        localObject = paramBuffer;
        Segment localSegment = ((Buffer)localObject).head;
        Intrinsics.checkNotNull(localSegment);
        localObject = localSegment.data;
        int j = localSegment.pos;
        int n = localSegment.limit;
        while (j < n)
        {
          byte b1 = localObject[j];
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
              paramBuffer = new Buffer().writeDecimalLong(l1).writeByte(b1);
              if (m == 0) {
                paramBuffer.readByte();
              }
              throw ((Throwable)new NumberFormatException("Number too large: " + paramBuffer.readUtf8()));
            }
          }
          else
          {
            if ((b1 != (byte)45) || (k != 0)) {
              break label245;
            }
            m = 1;
            l2 -= 1L;
          }
          j++;
          k++;
          continue;
          label245:
          if (k != 0)
          {
            i = 1;
          }
          else
          {
            paramBuffer = new StringBuilder().append("Expected leading [0-9] or '-' character but was 0x");
            localObject = _Util.toHexString(b1);
            Log5ECF72.a(localObject);
            LogE84000.a(localObject);
            Log229316.a(localObject);
            throw ((Throwable)new NumberFormatException((String)localObject));
          }
        }
        if (j == n)
        {
          paramBuffer.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        else
        {
          localSegment.pos = j;
        }
        if ((i != 0) || (paramBuffer.head == null)) {
          break;
        }
        localObject = paramBuffer;
      }
      paramBuffer.setSize$okio(paramBuffer.size() - k);
      if (m == 0) {
        l1 = -l1;
      }
      return l1;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final void commonReadFully(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer1, "$this$commonReadFully");
    Intrinsics.checkNotNullParameter(paramBuffer2, "sink");
    if (paramBuffer1.size() >= paramLong)
    {
      paramBuffer2.write(paramBuffer1, paramLong);
      return;
    }
    paramBuffer2.write(paramBuffer1, paramBuffer1.size());
    throw ((Throwable)new EOFException());
  }
  
  public static final void commonReadFully(Buffer paramBuffer, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadFully");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "sink");
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = paramBuffer.read(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j != -1) {
        i += j;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public static final long commonReadHexadecimalUnsignedLong(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadHexadecimalUnsignedLong");
    if (paramBuffer.size() != 0L)
    {
      long l1 = 0L;
      int j = 0;
      int k = 0;
      int m;
      long l2;
      label230:
      label293:
      do
      {
        Object localObject = paramBuffer.head;
        Intrinsics.checkNotNull(localObject);
        byte[] arrayOfByte = ((Segment)localObject).data;
        int n = ((Segment)localObject).pos;
        int i2 = ((Segment)localObject).limit;
        m = j;
        l2 = l1;
        int i1;
        int i;
        for (;;)
        {
          i1 = k;
          if (n >= i2) {
            break label293;
          }
          i = arrayOfByte[n];
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
                break label230;
              }
              j = i - j + 10;
            }
          }
          if ((0xF000000000000000 & l2) != 0L) {
            break;
          }
          l2 = l2 << 4 | j;
          n++;
          m++;
        }
        paramBuffer = new Buffer().writeHexadecimalUnsignedLong(l2).writeByte(i);
        throw ((Throwable)new NumberFormatException("Number too large: " + paramBuffer.readUtf8()));
        if (m != 0)
        {
          i1 = 1;
        }
        else
        {
          localObject = new StringBuilder().append("Expected leading [0-9a-fA-F] character but was 0x");
          paramBuffer = _Util.toHexString(i);
          Log5ECF72.a(paramBuffer);
          LogE84000.a(paramBuffer);
          Log229316.a(paramBuffer);
          throw ((Throwable)new NumberFormatException(paramBuffer));
        }
        if (n == i2)
        {
          paramBuffer.head = ((Segment)localObject).pop();
          SegmentPool.recycle((Segment)localObject);
        }
        else
        {
          ((Segment)localObject).pos = n;
        }
        if (i1 != 0) {
          break;
        }
        l1 = l2;
        j = m;
        k = i1;
      } while (paramBuffer.head != null);
      paramBuffer.setSize$okio(paramBuffer.size() - m);
      return l2;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final int commonReadInt(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadInt");
    if (paramBuffer.size() >= 4L)
    {
      Segment localSegment = paramBuffer.head;
      Intrinsics.checkNotNull(localSegment);
      int j = localSegment.pos;
      int i = localSegment.limit;
      if (i - j < 4L) {
        return (paramBuffer.readByte() & 0xFF) << 24 | (paramBuffer.readByte() & 0xFF) << 16 | (paramBuffer.readByte() & 0xFF) << 8 | paramBuffer.readByte() & 0xFF;
      }
      byte[] arrayOfByte = localSegment.data;
      int k = j + 1;
      j = arrayOfByte[j];
      int m = k + 1;
      k = arrayOfByte[k];
      int n = m + 1;
      int i1 = arrayOfByte[m];
      m = n + 1;
      n = arrayOfByte[n];
      paramBuffer.setSize$okio(paramBuffer.size() - 4L);
      if (m == i)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      else
      {
        localSegment.pos = m;
      }
      return (j & 0xFF) << 24 | (k & 0xFF) << 16 | (i1 & 0xFF) << 8 | n & 0xFF;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final long commonReadLong(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadLong");
    if (paramBuffer.size() >= 8L)
    {
      Segment localSegment = paramBuffer.head;
      Intrinsics.checkNotNull(localSegment);
      int j = localSegment.pos;
      int i = localSegment.limit;
      if (i - j < 8L) {
        return (paramBuffer.readInt() & 0xFFFFFFFF) << 32 | paramBuffer.readInt() & 0xFFFFFFFF;
      }
      byte[] arrayOfByte = localSegment.data;
      int k = j + 1;
      long l1 = arrayOfByte[j];
      j = k + 1;
      long l7 = arrayOfByte[k];
      k = j + 1;
      long l2 = arrayOfByte[j];
      j = k + 1;
      long l4 = arrayOfByte[k];
      k = j + 1;
      long l6 = arrayOfByte[j];
      int m = k + 1;
      long l8 = arrayOfByte[k];
      j = m + 1;
      long l5 = arrayOfByte[m];
      k = j + 1;
      long l3 = arrayOfByte[j];
      paramBuffer.setSize$okio(paramBuffer.size() - 8L);
      if (k == i)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      else
      {
        localSegment.pos = k;
      }
      return (l7 & 0xFF) << 48 | (0xFF & l1) << 56 | (0xFF & l2) << 40 | (l4 & 0xFF) << 32 | (0xFF & l6) << 24 | (l8 & 0xFF) << 16 | (0xFF & l5) << 8 | l3 & 0xFF;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final short commonReadShort(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadShort");
    if (paramBuffer.size() >= 2L)
    {
      Segment localSegment = paramBuffer.head;
      Intrinsics.checkNotNull(localSegment);
      int j = localSegment.pos;
      int i = localSegment.limit;
      if (i - j < 2) {
        return (short)((paramBuffer.readByte() & 0xFF) << 8 | paramBuffer.readByte() & 0xFF);
      }
      byte[] arrayOfByte = localSegment.data;
      int m = j + 1;
      j = arrayOfByte[j];
      int k = m + 1;
      m = arrayOfByte[m];
      paramBuffer.setSize$okio(paramBuffer.size() - 2L);
      if (k == i)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      else
      {
        localSegment.pos = k;
      }
      return (short)((j & 0xFF) << 8 | m & 0xFF);
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final String commonReadUtf8(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadUtf8");
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer.size() >= paramLong)
      {
        if (paramLong == 0L) {
          return "";
        }
        Segment localSegment = paramBuffer.head;
        Intrinsics.checkNotNull(localSegment);
        if (localSegment.pos + paramLong > localSegment.limit)
        {
          paramBuffer = _Utf8Kt.commonToUtf8String$default(paramBuffer.readByteArray(paramLong), 0, 0, 3, null);
          Log5ECF72.a(paramBuffer);
          LogE84000.a(paramBuffer);
          Log229316.a(paramBuffer);
          return paramBuffer;
        }
        String str = _Utf8Kt.commonToUtf8String(localSegment.data, localSegment.pos, localSegment.pos + (int)paramLong);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        localSegment.pos += (int)paramLong;
        paramBuffer.setSize$okio(paramBuffer.size() - paramLong);
        if (localSegment.pos == localSegment.limit)
        {
          paramBuffer.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
        return str;
      }
      throw ((Throwable)new EOFException());
    }
    throw ((Throwable)new IllegalArgumentException(("byteCount: " + paramLong).toString()));
  }
  
  public static final int commonReadUtf8CodePoint(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadUtf8CodePoint");
    if (paramBuffer.size() != 0L)
    {
      int i = paramBuffer.getByte(0L);
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
          break label327;
        }
        j = i & 0x7;
        k = 4;
        m = 65536;
      }
      if (paramBuffer.size() >= k)
      {
        int n = 1;
        while (n < k)
        {
          int i2 = paramBuffer.getByte(n);
          if ((0xC0 & i2) == 128)
          {
            j = j << 6 | 0x3F & i2;
            n++;
          }
          else
          {
            paramBuffer.skip(n);
            return 65533;
          }
        }
        paramBuffer.skip(k);
        if (j > 1114111) {
          j = i1;
        } else if ((55296 <= j) && (57343 >= j)) {
          j = i1;
        } else if (j < m) {
          j = i1;
        }
        return j;
      }
      StringBuilder localStringBuilder = new StringBuilder().append("size < ").append(k).append(": ").append(paramBuffer.size()).append(" (to read code point prefixed 0x");
      paramBuffer = _Util.toHexString(i);
      Log5ECF72.a(paramBuffer);
      LogE84000.a(paramBuffer);
      Log229316.a(paramBuffer);
      throw ((Throwable)new EOFException(paramBuffer + ')'));
      label327:
      paramBuffer.skip(1L);
      return 65533;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final String commonReadUtf8Line(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadUtf8Line");
    long l = paramBuffer.indexOf((byte)10);
    if (l != -1L)
    {
      paramBuffer = readUtf8Line(paramBuffer, l);
      Log5ECF72.a(paramBuffer);
      LogE84000.a(paramBuffer);
      Log229316.a(paramBuffer);
    }
    else if (paramBuffer.size() != 0L)
    {
      paramBuffer = paramBuffer.readUtf8(paramBuffer.size());
    }
    else
    {
      paramBuffer = null;
    }
    return paramBuffer;
  }
  
  public static final String commonReadUtf8LineStrict(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonReadUtf8LineStrict");
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
      long l2 = paramBuffer.indexOf(b, 0L, l1);
      if (l2 != -1L)
      {
        paramBuffer = readUtf8Line(paramBuffer, l2);
        Log5ECF72.a(paramBuffer);
        LogE84000.a(paramBuffer);
        Log229316.a(paramBuffer);
        return paramBuffer;
      }
      if ((l1 < paramBuffer.size()) && (paramBuffer.getByte(l1 - 1L) == (byte)13) && (paramBuffer.getByte(l1) == b))
      {
        paramBuffer = readUtf8Line(paramBuffer, l1);
        Log5ECF72.a(paramBuffer);
        LogE84000.a(paramBuffer);
        Log229316.a(paramBuffer);
        return paramBuffer;
      }
      Buffer localBuffer = new Buffer();
      l1 = paramBuffer.size();
      paramBuffer.copyTo(localBuffer, 0L, Math.min(32, l1));
      StringBuilder localStringBuilder = new StringBuilder().append("\\n not found: limit=");
      l1 = paramBuffer.size();
      throw ((Throwable)new EOFException(Math.min(l1, paramLong) + " content=" + localBuffer.readByteString().hex() + '…'));
    }
    throw ((Throwable)new IllegalArgumentException(("limit < 0: " + paramLong).toString()));
  }
  
  public static final int commonSelect(Buffer paramBuffer, Options paramOptions)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonSelect");
    Intrinsics.checkNotNullParameter(paramOptions, "options");
    int i = selectPrefix$default(paramBuffer, paramOptions, false, 2, null);
    if (i == -1) {
      return -1;
    }
    paramBuffer.skip(paramOptions.getByteStrings$okio()[i].size());
    return i;
  }
  
  public static final void commonSkip(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonSkip");
    while (paramLong > 0L)
    {
      Segment localSegment = paramBuffer.head;
      if (localSegment != null)
      {
        int i = (int)Math.min(paramLong, localSegment.limit - localSegment.pos);
        paramBuffer.setSize$okio(paramBuffer.size() - i);
        paramLong -= i;
        localSegment.pos += i;
        if (localSegment.pos == localSegment.limit)
        {
          paramBuffer.head = localSegment.pop();
          SegmentPool.recycle(localSegment);
        }
      }
      else
      {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public static final ByteString commonSnapshot(Buffer paramBuffer)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonSnapshot");
    int i;
    if (paramBuffer.size() <= Integer.MAX_VALUE) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramBuffer.snapshot((int)paramBuffer.size());
    }
    throw ((Throwable)new IllegalStateException(("size > Int.MAX_VALUE: " + paramBuffer.size()).toString()));
  }
  
  public static final ByteString commonSnapshot(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonSnapshot");
    if (paramInt == 0) {
      return ByteString.EMPTY;
    }
    _Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramInt);
    int j = 0;
    int i = 0;
    Object localObject = paramBuffer.head;
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
    localObject = new int[i * 2];
    j = 0;
    i = 0;
    for (paramBuffer = paramBuffer.head; j < paramInt; paramBuffer = paramBuffer.next)
    {
      Intrinsics.checkNotNull(paramBuffer);
      arrayOfByte[i] = paramBuffer.data;
      j += paramBuffer.limit - paramBuffer.pos;
      localObject[i] = Math.min(j, paramInt);
      localObject[(((Object[])arrayOfByte).length + i)] = paramBuffer.pos;
      paramBuffer.shared = true;
      i++;
    }
    return (ByteString)new SegmentedByteString((byte[][])arrayOfByte, (int[])localObject);
  }
  
  public static final Segment commonWritableSegment(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWritableSegment");
    int i = 1;
    if ((paramInt < 1) || (paramInt > 8192)) {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer.head == null)
      {
        localSegment = SegmentPool.take();
        paramBuffer.head = localSegment;
        localSegment.prev = localSegment;
        localSegment.next = localSegment;
        return localSegment;
      }
      paramBuffer = paramBuffer.head;
      Intrinsics.checkNotNull(paramBuffer);
      Segment localSegment = paramBuffer.prev;
      Intrinsics.checkNotNull(localSegment);
      if (localSegment.limit + paramInt <= 8192)
      {
        paramBuffer = localSegment;
        if (localSegment.owner) {}
      }
      else
      {
        paramBuffer = localSegment.push(SegmentPool.take());
      }
      return paramBuffer;
    }
    throw ((Throwable)new IllegalArgumentException("unexpected capacity".toString()));
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWrite");
    Intrinsics.checkNotNullParameter(paramByteString, "byteString");
    paramByteString.write$okio(paramBuffer, paramInt1, paramInt2);
    return paramBuffer;
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, Source paramSource, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWrite");
    Intrinsics.checkNotNullParameter(paramSource, "source");
    while (paramLong > 0L)
    {
      long l = paramSource.read(paramBuffer, paramLong);
      if (l != -1L) {
        paramLong -= l;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
    return paramBuffer;
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWrite");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "source");
    return paramBuffer.write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWrite");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "source");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    int i = paramInt1 + paramInt2;
    while (paramInt1 < i)
    {
      Segment localSegment = paramBuffer.writableSegment$okio(1);
      int j = Math.min(i - paramInt1, 8192 - localSegment.limit);
      byte[] arrayOfByte = localSegment.data;
      int k = localSegment.limit;
      ArraysKt.copyInto(paramArrayOfByte, arrayOfByte, k, paramInt1, paramInt1 + j);
      paramInt1 += j;
      localSegment.limit += j;
    }
    paramBuffer.setSize$okio(paramBuffer.size() + paramInt2);
    return paramBuffer;
  }
  
  public static final void commonWrite(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer1, "$this$commonWrite");
    Intrinsics.checkNotNullParameter(paramBuffer2, "source");
    int i;
    if (paramBuffer2 != paramBuffer1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      _Util.checkOffsetAndCount(paramBuffer2.size(), 0L, paramLong);
      while (paramLong > 0L)
      {
        Segment localSegment1 = paramBuffer2.head;
        Intrinsics.checkNotNull(localSegment1);
        i = localSegment1.limit;
        localSegment1 = paramBuffer2.head;
        Intrinsics.checkNotNull(localSegment1);
        Segment localSegment2;
        if (paramLong < i - localSegment1.pos)
        {
          if (paramBuffer1.head != null)
          {
            localSegment1 = paramBuffer1.head;
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
              localSegment2 = paramBuffer2.head;
              Intrinsics.checkNotNull(localSegment2);
              localSegment2.writeTo(localSegment1, (int)paramLong);
              paramBuffer2.setSize$okio(paramBuffer2.size() - paramLong);
              paramBuffer1.setSize$okio(paramBuffer1.size() + paramLong);
              return;
            }
          }
          localSegment1 = paramBuffer2.head;
          Intrinsics.checkNotNull(localSegment1);
          paramBuffer2.head = localSegment1.split((int)paramLong);
        }
        localSegment1 = paramBuffer2.head;
        Intrinsics.checkNotNull(localSegment1);
        long l = localSegment1.limit - localSegment1.pos;
        paramBuffer2.head = localSegment1.pop();
        if (paramBuffer1.head == null)
        {
          paramBuffer1.head = localSegment1;
          localSegment1.prev = localSegment1;
          localSegment1.next = localSegment1.prev;
        }
        else
        {
          localSegment2 = paramBuffer1.head;
          Intrinsics.checkNotNull(localSegment2);
          localSegment2 = localSegment2.prev;
          Intrinsics.checkNotNull(localSegment2);
          localSegment2.push(localSegment1).compact();
        }
        paramBuffer2.setSize$okio(paramBuffer2.size() - l);
        paramBuffer1.setSize$okio(paramBuffer1.size() + l);
        paramLong -= l;
      }
      return;
    }
    throw ((Throwable)new IllegalArgumentException("source == this".toString()));
  }
  
  public static final long commonWriteAll(Buffer paramBuffer, Source paramSource)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteAll");
    Intrinsics.checkNotNullParameter(paramSource, "source");
    long l2;
    for (long l1 = 0L;; l1 += l2)
    {
      l2 = paramSource.read(paramBuffer, ' ');
      if (l2 == -1L) {
        return l1;
      }
    }
  }
  
  public static final Buffer commonWriteByte(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteByte");
    Segment localSegment = paramBuffer.writableSegment$okio(1);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    localSegment.limit = (i + 1);
    arrayOfByte[i] = ((byte)paramInt);
    paramBuffer.setSize$okio(paramBuffer.size() + 1L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteDecimalLong(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteDecimalLong");
    long l = paramLong;
    if (l == 0L) {
      return paramBuffer.writeByte(48);
    }
    int j = 0;
    paramLong = l;
    if (l < 0L)
    {
      paramLong = -l;
      if (paramLong < 0L) {
        return paramBuffer.writeUtf8("-9223372036854775808");
      }
      j = 1;
    }
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
    Segment localSegment = paramBuffer.writableSegment$okio(k);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit + k;
    while (paramLong != 0L)
    {
      l = 10;
      int m = (int)(paramLong % l);
      i--;
      arrayOfByte[i] = getHEX_DIGIT_BYTES()[m];
      paramLong /= l;
    }
    if (j != 0) {
      arrayOfByte[(i - 1)] = ((byte)45);
    }
    localSegment.limit += k;
    paramBuffer.setSize$okio(paramBuffer.size() + k);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteHexadecimalUnsignedLong(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteHexadecimalUnsignedLong");
    if (paramLong == 0L) {
      return paramBuffer.writeByte(48);
    }
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
    int k = (int)((3 + ((l & 0x3F) + (0x3F & l >>> 32))) / 4);
    Segment localSegment = paramBuffer.writableSegment$okio(k);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit + k - 1;
    int j = localSegment.limit;
    while (i >= j)
    {
      arrayOfByte[i] = getHEX_DIGIT_BYTES()[((int)(0xF & paramLong))];
      paramLong >>>= 4;
      i--;
    }
    localSegment.limit += k;
    paramBuffer.setSize$okio(paramBuffer.size() + k);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteInt(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteInt");
    Segment localSegment = paramBuffer.writableSegment$okio(4);
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
    paramBuffer.setSize$okio(paramBuffer.size() + 4L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteLong(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteLong");
    Segment localSegment = paramBuffer.writableSegment$okio(8);
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
    int k = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 24 & 0xFF));
    i = k + 1;
    arrayOfByte[k] = ((byte)(int)(paramLong >>> 16 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(int)(paramLong >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(int)(paramLong & 0xFF));
    localSegment.limit = (j + 1);
    paramBuffer.setSize$okio(paramBuffer.size() + 8L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteShort(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteShort");
    Segment localSegment = paramBuffer.writableSegment$okio(2);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(paramInt & 0xFF));
    localSegment.limit = (j + 1);
    paramBuffer.setSize$okio(paramBuffer.size() + 2L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteUtf8(Buffer paramBuffer, String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteUtf8");
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
            int m = paramString.charAt(paramInt1);
            Object localObject;
            if (m < 128)
            {
              Segment localSegment = paramBuffer.writableSegment$okio(1);
              localObject = localSegment.data;
              int j = localSegment.limit - paramInt1;
              int k = Math.min(paramInt2, 8192 - j);
              i = paramInt1 + 1;
              localObject[(paramInt1 + j)] = ((byte)m);
              for (paramInt1 = i; paramInt1 < k; paramInt1++)
              {
                i = paramString.charAt(paramInt1);
                if (i >= 128) {
                  break;
                }
                localObject[(paramInt1 + j)] = ((byte)i);
              }
              i = paramInt1 + j - localSegment.limit;
              localSegment.limit += i;
              long l = paramBuffer.size();
              paramBuffer.setSize$okio(i + l);
            }
            else if (m < 2048)
            {
              localObject = paramBuffer.writableSegment$okio(2);
              ((Segment)localObject).data[localObject.limit] = ((byte)(m >> 6 | 0xC0));
              ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x80 | m & 0x3F));
              ((Segment)localObject).limit += 2;
              paramBuffer.setSize$okio(paramBuffer.size() + 2L);
              paramInt1++;
            }
            else if ((m >= 55296) && (m <= 57343))
            {
              if (paramInt1 + 1 < paramInt2) {
                i = paramString.charAt(paramInt1 + 1);
              } else {
                i = 0;
              }
              if ((m <= 56319) && (56320 <= i) && (57343 >= i))
              {
                i = ((m & 0x3FF) << 10 | i & 0x3FF) + 65536;
                localObject = paramBuffer.writableSegment$okio(4);
                ((Segment)localObject).data[localObject.limit] = ((byte)(i >> 18 | 0xF0));
                ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(i >> 12 & 0x3F | 0x80));
                ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(i >> 6 & 0x3F | 0x80));
                ((Segment)localObject).data[(localObject.limit + 3)] = ((byte)(0x80 | i & 0x3F));
                ((Segment)localObject).limit += 4;
                paramBuffer.setSize$okio(paramBuffer.size() + 4L);
                paramInt1 += 2;
              }
              else
              {
                paramBuffer.writeByte(63);
                paramInt1++;
              }
            }
            else
            {
              localObject = paramBuffer.writableSegment$okio(3);
              ((Segment)localObject).data[localObject.limit] = ((byte)(m >> 12 | 0xE0));
              ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x3F & m >> 6 | 0x80));
              ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(m & 0x3F | 0x80));
              ((Segment)localObject).limit += 3;
              paramBuffer.setSize$okio(paramBuffer.size() + 3L);
              paramInt1++;
            }
          }
          return paramBuffer;
        }
        throw ((Throwable)new IllegalArgumentException(("endIndex > string.length: " + paramInt2 + " > " + paramString.length()).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("endIndex < beginIndex: " + paramInt2 + " < " + paramInt1).toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("beginIndex < 0: " + paramInt1).toString()));
  }
  
  public static final Buffer commonWriteUtf8CodePoint(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$commonWriteUtf8CodePoint");
    if (paramInt < 128)
    {
      paramBuffer.writeByte(paramInt);
    }
    else if (paramInt < 2048)
    {
      localObject = paramBuffer.writableSegment$okio(2);
      ((Segment)localObject).data[localObject.limit] = ((byte)(paramInt >> 6 | 0xC0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x80 | paramInt & 0x3F));
      ((Segment)localObject).limit += 2;
      paramBuffer.setSize$okio(paramBuffer.size() + 2L);
    }
    else if ((55296 <= paramInt) && (57343 >= paramInt))
    {
      paramBuffer.writeByte(63);
    }
    else if (paramInt < 65536)
    {
      localObject = paramBuffer.writableSegment$okio(3);
      ((Segment)localObject).data[localObject.limit] = ((byte)(paramInt >> 12 | 0xE0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(0x3F & paramInt >> 6 | 0x80));
      ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(0x80 | paramInt & 0x3F));
      ((Segment)localObject).limit += 3;
      paramBuffer.setSize$okio(paramBuffer.size() + 3L);
    }
    else
    {
      if (paramInt > 1114111) {
        break label346;
      }
      localObject = paramBuffer.writableSegment$okio(4);
      ((Segment)localObject).data[localObject.limit] = ((byte)(paramInt >> 18 | 0xF0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(paramInt >> 12 & 0x3F | 0x80));
      ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(paramInt >> 6 & 0x3F | 0x80));
      ((Segment)localObject).data[(localObject.limit + 3)] = ((byte)(0x80 | paramInt & 0x3F));
      ((Segment)localObject).limit += 4;
      paramBuffer.setSize$okio(paramBuffer.size() + 4L);
    }
    return paramBuffer;
    label346:
    paramBuffer = new StringBuilder().append("Unexpected code point: 0x");
    Object localObject = _Util.toHexString(paramInt);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw ((Throwable)new IllegalArgumentException((String)localObject));
  }
  
  public static final byte[] getHEX_DIGIT_BYTES()
  {
    return HEX_DIGIT_BYTES;
  }
  
  public static final boolean rangeEquals(Segment paramSegment, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramSegment, "segment");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "bytes");
    Object localObject2 = paramSegment;
    int j = ((Segment)localObject2).limit;
    paramSegment = ((Segment)localObject2).data;
    while (paramInt2 < paramInt3)
    {
      Object localObject1 = localObject2;
      int k = paramInt1;
      int i = j;
      if (paramInt1 == j)
      {
        localObject1 = ((Segment)localObject2).next;
        Intrinsics.checkNotNull(localObject1);
        paramSegment = ((Segment)localObject1).data;
        k = ((Segment)localObject1).pos;
        i = ((Segment)localObject1).limit;
      }
      if (paramSegment[k] != paramArrayOfByte[paramInt2]) {
        return false;
      }
      paramInt1 = k + 1;
      paramInt2++;
      localObject2 = localObject1;
      j = i;
    }
    return true;
  }
  
  public static final String readUtf8Line(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$readUtf8Line");
    String str;
    if ((paramLong > 0L) && (paramBuffer.getByte(paramLong - 1L) == (byte)13))
    {
      str = paramBuffer.readUtf8(paramLong - 1L);
      paramBuffer.skip(2L);
      paramBuffer = str;
    }
    else
    {
      str = paramBuffer.readUtf8(paramLong);
      paramBuffer.skip(1L);
      paramBuffer = str;
    }
    return paramBuffer;
  }
  
  public static final <T> T seek(Buffer paramBuffer, long paramLong, Function2<? super Segment, ? super Long, ? extends T> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$seek");
    Intrinsics.checkNotNullParameter(paramFunction2, "lambda");
    Segment localSegment = paramBuffer.head;
    if (localSegment != null)
    {
      if (paramBuffer.size() - paramLong < paramLong)
      {
        for (l1 = paramBuffer.size(); l1 > paramLong; l1 -= localSegment.limit - localSegment.pos)
        {
          localSegment = localSegment.prev;
          Intrinsics.checkNotNull(localSegment);
        }
        return (T)paramFunction2.invoke(localSegment, Long.valueOf(l1));
      }
      long l1 = 0L;
      paramBuffer = localSegment;
      for (;;)
      {
        long l2 = paramBuffer.limit - paramBuffer.pos + l1;
        if (l2 > paramLong) {
          return (T)paramFunction2.invoke(paramBuffer, Long.valueOf(l1));
        }
        paramBuffer = paramBuffer.next;
        Intrinsics.checkNotNull(paramBuffer);
        l1 = l2;
      }
    }
    return (T)paramFunction2.invoke(null, Long.valueOf(-1L));
  }
  
  public static final int selectPrefix(Buffer paramBuffer, Options paramOptions, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$selectPrefix");
    Intrinsics.checkNotNullParameter(paramOptions, "options");
    Segment localSegment = paramBuffer.head;
    int i;
    if (localSegment != null)
    {
      paramBuffer = localSegment;
      byte[] arrayOfByte = localSegment.data;
      int j = localSegment.pos;
      i = localSegment.limit;
      int[] arrayOfInt = paramOptions.getTrie$okio();
      int k = 0;
      int m = -1;
      paramOptions = arrayOfByte;
      int i1 = k + 1;
      int i3 = arrayOfInt[k];
      int n = i1 + 1;
      k = arrayOfInt[i1];
      if (k != -1) {
        m = k;
      }
      if (paramBuffer != null)
      {
        if (i3 < 0)
        {
          k = n;
          i1 = j;
        }
      }
      else {
        for (;;)
        {
          j = i1 + 1;
          i1 = paramOptions[i1];
          int i2 = k + 1;
          if ((i1 & 0xFF) != arrayOfInt[k]) {
            return m;
          }
          if (i2 == n + i3 * -1) {
            k = 1;
          } else {
            k = 0;
          }
          if (j == i)
          {
            Intrinsics.checkNotNull(paramBuffer);
            paramBuffer = paramBuffer.next;
            Intrinsics.checkNotNull(paramBuffer);
            i = paramBuffer.pos;
            paramOptions = paramBuffer.data;
            j = paramBuffer.limit;
            if (paramBuffer == localSegment)
            {
              if (k == 0)
              {
                if (paramBoolean) {
                  return -2;
                }
                return m;
              }
              paramBuffer = (Segment)null;
            }
          }
          else
          {
            i1 = j;
            j = i;
            i = i1;
          }
          if (k != 0)
          {
            n = arrayOfInt[i2];
            k = j;
            break;
          }
          k = i2;
          i1 = i;
          i = j;
        }
      }
      k = j + 1;
      i1 = paramOptions[j];
      for (j = n;; j++)
      {
        if (j == n + i3) {
          return m;
        }
        if ((i1 & 0xFF) == arrayOfInt[j])
        {
          n = arrayOfInt[(j + i3)];
          if (k == i)
          {
            paramBuffer = paramBuffer.next;
            Intrinsics.checkNotNull(paramBuffer);
            i = paramBuffer.pos;
            paramOptions = paramBuffer.data;
            k = paramBuffer.limit;
            if (paramBuffer == localSegment) {
              paramBuffer = (Segment)null;
            }
          }
          else
          {
            j = k;
            k = i;
            i = j;
          }
          if (n >= 0) {
            return n;
          }
          n = -n;
          j = i;
          i = k;
          k = n;
          break;
        }
      }
    }
    if (paramBoolean) {
      i = -2;
    } else {
      i = -1;
    }
    return i;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/internal/BufferKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */