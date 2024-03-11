package okio.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.SegmentedByteString;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\000R\n\000\n\002\020\b\n\002\020\025\n\002\b\004\n\002\020\013\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\020\005\n\002\b\003\n\002\020\022\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\004\032$\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\0012\006\020\004\032\0020\0012\006\020\005\032\0020\001H\000\032\027\020\006\032\0020\007*\0020\b2\b\020\t\032\004\030\0010\nH\b\032\r\020\013\032\0020\001*\0020\bH\b\032\r\020\f\032\0020\001*\0020\bH\b\032\025\020\r\032\0020\016*\0020\b2\006\020\017\032\0020\001H\b\032-\020\020\032\0020\007*\0020\b2\006\020\021\032\0020\0012\006\020\t\032\0020\0222\006\020\023\032\0020\0012\006\020\024\032\0020\001H\b\032-\020\020\032\0020\007*\0020\b2\006\020\021\032\0020\0012\006\020\t\032\0020\0252\006\020\023\032\0020\0012\006\020\024\032\0020\001H\b\032\035\020\026\032\0020\025*\0020\b2\006\020\027\032\0020\0012\006\020\030\032\0020\001H\b\032\r\020\031\032\0020\022*\0020\bH\b\032%\020\032\032\0020\033*\0020\b2\006\020\034\032\0020\0352\006\020\021\032\0020\0012\006\020\024\032\0020\001H\b\032]\020\036\032\0020\033*\0020\b2K\020\037\032G\022\023\022\0210\022¢\006\f\b!\022\b\b\"\022\004\b\b(#\022\023\022\0210\001¢\006\f\b!\022\b\b\"\022\004\b\b(\021\022\023\022\0210\001¢\006\f\b!\022\b\b\"\022\004\b\b(\024\022\004\022\0020\0330 H\bø\001\000\032j\020\036\032\0020\033*\0020\b2\006\020\027\032\0020\0012\006\020\030\032\0020\0012K\020\037\032G\022\023\022\0210\022¢\006\f\b!\022\b\b\"\022\004\b\b(#\022\023\022\0210\001¢\006\f\b!\022\b\b\"\022\004\b\b(\021\022\023\022\0210\001¢\006\f\b!\022\b\b\"\022\004\b\b(\024\022\004\022\0020\0330 H\b\032\024\020$\032\0020\001*\0020\b2\006\020\017\032\0020\001H\000\002\007\n\005\b20\001¨\006%"}, d2={"binarySearch", "", "", "value", "fromIndex", "toIndex", "commonEquals", "", "Lokio/SegmentedByteString;", "other", "", "commonGetSize", "commonHashCode", "commonInternalGet", "", "pos", "commonRangeEquals", "offset", "", "otherOffset", "byteCount", "Lokio/ByteString;", "commonSubstring", "beginIndex", "endIndex", "commonToByteArray", "commonWrite", "", "buffer", "Lokio/Buffer;", "forEachSegment", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "segment", "okio"}, k=2, mv={1, 4, 0})
public final class SegmentedByteStringKt
{
  public static final int binarySearch(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "$this$binarySearch");
    int i = paramInt2;
    paramInt2 = paramInt3 - 1;
    paramInt3 = i;
    while (paramInt3 <= paramInt2)
    {
      i = paramInt3 + paramInt2 >>> 1;
      int j = paramArrayOfInt[i];
      if (j < paramInt1)
      {
        paramInt3 = i + 1;
      }
      else
      {
        if (j <= paramInt1) {
          break label62;
        }
        paramInt2 = i - 1;
      }
      continue;
      label62:
      return i;
    }
    return -paramInt3 - 1;
  }
  
  public static final boolean commonEquals(SegmentedByteString paramSegmentedByteString, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonEquals");
    boolean bool = true;
    if (paramObject != paramSegmentedByteString) {
      if ((paramObject instanceof ByteString))
      {
        if ((((ByteString)paramObject).size() != paramSegmentedByteString.size()) || (!paramSegmentedByteString.rangeEquals(0, (ByteString)paramObject, 0, paramSegmentedByteString.size()))) {
          bool = false;
        }
      }
      else {
        bool = false;
      }
    }
    return bool;
  }
  
  public static final int commonGetSize(SegmentedByteString paramSegmentedByteString)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonGetSize");
    return paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length - 1)];
  }
  
  public static final int commonHashCode(SegmentedByteString paramSegmentedByteString)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonHashCode");
    int i = paramSegmentedByteString.getHashCode$okio();
    if (i != 0) {
      return i;
    }
    int k = 1;
    int i2 = ((Object[])paramSegmentedByteString.getSegments$okio()).length;
    int j = 0;
    i = 0;
    while (j < i2)
    {
      int i1 = paramSegmentedByteString.getDirectory$okio()[(i2 + j)];
      int n = paramSegmentedByteString.getDirectory$okio()[j];
      byte[] arrayOfByte = paramSegmentedByteString.getSegments$okio()[j];
      for (int m = i1; m < i1 + (n - i); m++) {
        k = k * 31 + arrayOfByte[m];
      }
      i = n;
      j++;
    }
    paramSegmentedByteString.setHashCode$okio(k);
    return k;
  }
  
  public static final byte commonInternalGet(SegmentedByteString paramSegmentedByteString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonInternalGet");
    _Util.checkOffsetAndCount(paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length - 1)], paramInt, 1L);
    int j = segment(paramSegmentedByteString, paramInt);
    int i;
    if (j == 0) {
      i = 0;
    } else {
      i = paramSegmentedByteString.getDirectory$okio()[(j - 1)];
    }
    int k = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + j)];
    return paramSegmentedByteString.getSegments$okio()[j][(paramInt - i + k)];
  }
  
  public static final boolean commonRangeEquals(SegmentedByteString paramSegmentedByteString, int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3)
  {
    int n = 0;
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonRangeEquals");
    Intrinsics.checkNotNullParameter(paramByteString, "other");
    if ((paramInt1 >= 0) && (paramInt1 <= paramSegmentedByteString.size() - paramInt3))
    {
      int i = paramInt2;
      int j = paramInt1 + paramInt3;
      paramInt2 = segment(paramSegmentedByteString, paramInt1);
      paramInt3 = i;
      while (paramInt1 < j)
      {
        if (paramInt2 == 0) {
          i = 0;
        } else {
          i = paramSegmentedByteString.getDirectory$okio()[(paramInt2 - 1)];
        }
        int m = paramSegmentedByteString.getDirectory$okio()[paramInt2];
        int k = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + paramInt2)];
        m = Math.min(j, i + (m - i)) - paramInt1;
        if (!paramByteString.rangeEquals(paramInt3, paramSegmentedByteString.getSegments$okio()[paramInt2], paramInt1 - i + k, m)) {
          return false;
        }
        paramInt3 += m;
        paramInt1 += m;
        paramInt2++;
      }
      return true;
    }
    return false;
  }
  
  public static final boolean commonRangeEquals(SegmentedByteString paramSegmentedByteString, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonRangeEquals");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "other");
    if ((paramInt1 >= 0) && (paramInt1 <= paramSegmentedByteString.size() - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3))
    {
      int i = paramInt2;
      int j = paramInt1 + paramInt3;
      paramInt2 = segment(paramSegmentedByteString, paramInt1);
      paramInt3 = i;
      while (paramInt1 < j)
      {
        if (paramInt2 == 0) {
          i = 0;
        } else {
          i = paramSegmentedByteString.getDirectory$okio()[(paramInt2 - 1)];
        }
        int m = paramSegmentedByteString.getDirectory$okio()[paramInt2];
        int k = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + paramInt2)];
        m = Math.min(j, i + (m - i)) - paramInt1;
        if (!_Util.arrayRangeEquals(paramSegmentedByteString.getSegments$okio()[paramInt2], k + (paramInt1 - i), paramArrayOfByte, paramInt3, m)) {
          return false;
        }
        paramInt3 += m;
        paramInt1 += m;
        paramInt2++;
      }
      return true;
    }
    return false;
  }
  
  public static final ByteString commonSubstring(SegmentedByteString paramSegmentedByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonSubstring");
    int j = 0;
    int k = 1;
    int i;
    if (paramInt1 >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt2 <= paramSegmentedByteString.size()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        int m = paramInt2 - paramInt1;
        if (m >= 0) {
          i = k;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          if ((paramInt1 == 0) && (paramInt2 == paramSegmentedByteString.size())) {
            return (ByteString)paramSegmentedByteString;
          }
          if (paramInt1 == paramInt2) {
            return ByteString.EMPTY;
          }
          k = segment(paramSegmentedByteString, paramInt1);
          int n = segment(paramSegmentedByteString, paramInt2 - 1);
          byte[][] arrayOfByte = (byte[][])ArraysKt.copyOfRange((Object[])paramSegmentedByteString.getSegments$okio(), k, n + 1);
          int[] arrayOfInt = new int[((Object[])arrayOfByte).length * 2];
          paramInt2 = 0;
          if (k <= n)
          {
            i = k;
            for (;;)
            {
              arrayOfInt[paramInt2] = Math.min(paramSegmentedByteString.getDirectory$okio()[i] - paramInt1, m);
              arrayOfInt[(paramInt2 + ((Object[])arrayOfByte).length)] = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + i)];
              if (i == n) {
                break;
              }
              i++;
              paramInt2++;
            }
          }
          if (k == 0) {
            paramInt2 = j;
          } else {
            paramInt2 = paramSegmentedByteString.getDirectory$okio()[(k - 1)];
          }
          i = ((Object[])arrayOfByte).length;
          arrayOfInt[i] += paramInt1 - paramInt2;
          return (ByteString)new SegmentedByteString(arrayOfByte, arrayOfInt);
        }
        throw ((Throwable)new IllegalArgumentException(("endIndex=" + paramInt2 + " < beginIndex=" + paramInt1).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("endIndex=" + paramInt2 + " > length(" + paramSegmentedByteString.size() + ')').toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("beginIndex=" + paramInt1 + " < 0").toString()));
  }
  
  public static final byte[] commonToByteArray(SegmentedByteString paramSegmentedByteString)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonToByteArray");
    byte[] arrayOfByte2 = new byte[paramSegmentedByteString.size()];
    int j = 0;
    int n = ((Object[])paramSegmentedByteString.getSegments$okio()).length;
    int i = 0;
    int k = 0;
    while (i < n)
    {
      int i1 = paramSegmentedByteString.getDirectory$okio()[(n + i)];
      int m = paramSegmentedByteString.getDirectory$okio()[i];
      byte[] arrayOfByte1 = paramSegmentedByteString.getSegments$okio()[i];
      k = m - k;
      ArraysKt.copyInto(arrayOfByte1, arrayOfByte2, j, i1, i1 + k);
      j += k;
      k = m;
      i++;
    }
    return arrayOfByte2;
  }
  
  public static final void commonWrite(SegmentedByteString paramSegmentedByteString, Buffer paramBuffer, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$commonWrite");
    Intrinsics.checkNotNullParameter(paramBuffer, "buffer");
    int j = paramInt1 + paramInt2;
    int i = segment(paramSegmentedByteString, paramInt1);
    paramInt2 = paramInt1;
    for (paramInt1 = i; paramInt2 < j; paramInt1++)
    {
      if (paramInt1 == 0) {
        i = 0;
      } else {
        i = paramSegmentedByteString.getDirectory$okio()[(paramInt1 - 1)];
      }
      int k = paramSegmentedByteString.getDirectory$okio()[paramInt1];
      int m = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + paramInt1)];
      k = Math.min(j, i + (k - i)) - paramInt2;
      i = paramInt2 - i + m;
      Segment localSegment1 = new Segment(paramSegmentedByteString.getSegments$okio()[paramInt1], i, i + k, true, false);
      if (paramBuffer.head == null)
      {
        localSegment1.prev = localSegment1;
        localSegment1.next = localSegment1.prev;
        paramBuffer.head = localSegment1.next;
      }
      else
      {
        Segment localSegment2 = paramBuffer.head;
        Intrinsics.checkNotNull(localSegment2);
        localSegment2 = localSegment2.prev;
        Intrinsics.checkNotNull(localSegment2);
        localSegment2.push(localSegment1);
      }
      paramInt2 += k;
    }
    paramBuffer.setSize$okio(paramBuffer.size() + paramSegmentedByteString.size());
  }
  
  private static final void forEachSegment(SegmentedByteString paramSegmentedByteString, int paramInt1, int paramInt2, Function3<? super byte[], ? super Integer, ? super Integer, Unit> paramFunction3)
  {
    for (int i = segment(paramSegmentedByteString, paramInt1); paramInt1 < paramInt2; i++)
    {
      int j;
      if (i == 0) {
        j = 0;
      } else {
        j = paramSegmentedByteString.getDirectory$okio()[(i - 1)];
      }
      int m = paramSegmentedByteString.getDirectory$okio()[i];
      int k = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + i)];
      m = Math.min(paramInt2, j + (m - j)) - paramInt1;
      paramFunction3.invoke(paramSegmentedByteString.getSegments$okio()[i], Integer.valueOf(paramInt1 - j + k), Integer.valueOf(m));
      paramInt1 += m;
    }
  }
  
  public static final void forEachSegment(SegmentedByteString paramSegmentedByteString, Function3<? super byte[], ? super Integer, ? super Integer, Unit> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$forEachSegment");
    Intrinsics.checkNotNullParameter(paramFunction3, "action");
    int m = ((Object[])paramSegmentedByteString.getSegments$okio()).length;
    int i = 0;
    int j = 0;
    while (i < m)
    {
      int n = paramSegmentedByteString.getDirectory$okio()[(m + i)];
      int k = paramSegmentedByteString.getDirectory$okio()[i];
      paramFunction3.invoke(paramSegmentedByteString.getSegments$okio()[i], Integer.valueOf(n), Integer.valueOf(k - j));
      j = k;
      i++;
    }
  }
  
  public static final int segment(SegmentedByteString paramSegmentedByteString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramSegmentedByteString, "$this$segment");
    paramInt = binarySearch(paramSegmentedByteString.getDirectory$okio(), paramInt + 1, 0, ((Object[])paramSegmentedByteString.getSegments$okio()).length);
    if (paramInt < 0) {
      paramInt ^= 0xFFFFFFFF;
    }
    return paramInt;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/internal/SegmentedByteStringKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */