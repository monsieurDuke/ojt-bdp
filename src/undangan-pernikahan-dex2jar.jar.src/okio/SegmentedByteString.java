package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import okio.internal.SegmentedByteStringKt;

@Metadata(bv={1, 0, 3}, d1={"\000h\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\020\022\n\000\n\002\020\025\n\002\b\007\n\002\030\002\n\000\n\002\020\016\n\002\b\005\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\013\n\002\020\005\n\002\b\t\n\002\030\002\n\002\b\t\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\b\000\030\0002\0020\001B\035\b\000\022\f\020\002\032\b\022\004\022\0020\0040\003\022\006\020\005\032\0020\006¢\006\002\020\007J\b\020\r\032\0020\016H\026J\b\020\017\032\0020\020H\026J\b\020\021\032\0020\020H\026J\025\020\022\032\0020\0012\006\020\023\032\0020\020H\020¢\006\002\b\024J\023\020\025\032\0020\0262\b\020\027\032\004\030\0010\030H\002J\r\020\031\032\0020\032H\020¢\006\002\b\033J\b\020\034\032\0020\032H\026J\b\020\035\032\0020\020H\026J\035\020\036\032\0020\0012\006\020\023\032\0020\0202\006\020\037\032\0020\001H\020¢\006\002\b J\030\020!\032\0020\0322\006\020\027\032\0020\0042\006\020\"\032\0020\032H\026J\r\020#\032\0020\004H\020¢\006\002\b$J\025\020%\032\0020&2\006\020'\032\0020\032H\020¢\006\002\b(J\030\020)\032\0020\0322\006\020\027\032\0020\0042\006\020\"\032\0020\032H\026J(\020*\032\0020\0262\006\020+\032\0020\0322\006\020\027\032\0020\0042\006\020,\032\0020\0322\006\020-\032\0020\032H\026J(\020*\032\0020\0262\006\020+\032\0020\0322\006\020\027\032\0020\0012\006\020,\032\0020\0322\006\020-\032\0020\032H\026J\020\020.\032\0020\0202\006\020/\032\00200H\026J\030\0201\032\0020\0012\006\0202\032\0020\0322\006\0203\032\0020\032H\026J\b\0204\032\0020\001H\026J\b\0205\032\0020\001H\026J\b\0206\032\0020\004H\026J\b\0207\032\0020\001H\002J\b\0208\032\0020\020H\026J\020\0209\032\0020:2\006\020;\032\0020<H\026J%\0209\032\0020:2\006\020=\032\0020>2\006\020+\032\0020\0322\006\020-\032\0020\032H\020¢\006\002\b?J\b\020@\032\0020AH\002R\024\020\005\032\0020\006X\004¢\006\b\n\000\032\004\b\b\020\tR\034\020\002\032\b\022\004\022\0020\0040\003X\004¢\006\n\n\002\020\f\032\004\b\n\020\013¨\006B"}, d2={"Lokio/SegmentedByteString;", "Lokio/ByteString;", "segments", "", "", "directory", "", "([[B[I)V", "getDirectory$okio", "()[I", "getSegments$okio", "()[[B", "[[B", "asByteBuffer", "Ljava/nio/ByteBuffer;", "base64", "", "base64Url", "digest", "algorithm", "digest$okio", "equals", "", "other", "", "getSize", "", "getSize$okio", "hashCode", "hex", "hmac", "key", "hmac$okio", "indexOf", "fromIndex", "internalArray", "internalArray$okio", "internalGet", "", "pos", "internalGet$okio", "lastIndexOf", "rangeEquals", "offset", "otherOffset", "byteCount", "string", "charset", "Ljava/nio/charset/Charset;", "substring", "beginIndex", "endIndex", "toAsciiLowercase", "toAsciiUppercase", "toByteArray", "toByteString", "toString", "write", "", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "write$okio", "writeReplace", "Ljava/lang/Object;", "okio"}, k=1, mv={1, 4, 0})
public final class SegmentedByteString
  extends ByteString
{
  private final transient int[] directory;
  private final transient byte[][] segments;
  
  public SegmentedByteString(byte[][] paramArrayOfByte, int[] paramArrayOfInt)
  {
    super(ByteString.EMPTY.getData$okio());
    this.segments = paramArrayOfByte;
    this.directory = paramArrayOfInt;
  }
  
  private final ByteString toByteString()
  {
    return new ByteString(toByteArray());
  }
  
  private final Object writeReplace()
  {
    ByteString localByteString = toByteString();
    if (localByteString != null) {
      return (Object)localByteString;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
  }
  
  public ByteBuffer asByteBuffer()
  {
    ByteBuffer localByteBuffer = ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    Intrinsics.checkNotNullExpressionValue(localByteBuffer, "ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer()");
    return localByteBuffer;
  }
  
  public String base64()
  {
    return toByteString().base64();
  }
  
  public String base64Url()
  {
    return toByteString().base64Url();
  }
  
  public ByteString digest$okio(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "algorithm");
    paramString = MessageDigest.getInstance(paramString);
    int m = ((Object[])getSegments$okio()).length;
    int j = 0;
    int i = 0;
    while (j < m)
    {
      int n = getDirectory$okio()[(m + j)];
      int k = getDirectory$okio()[j];
      paramString.update(getSegments$okio()[j], n, k - i);
      i = k;
      j++;
    }
    paramString = paramString.digest();
    Intrinsics.checkNotNullExpressionValue(paramString, "digest.digest()");
    return new ByteString(paramString);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject != this) {
      if ((paramObject instanceof ByteString))
      {
        if ((((ByteString)paramObject).size() != size()) || (!rangeEquals(0, (ByteString)paramObject, 0, size()))) {
          bool = false;
        }
      }
      else {
        bool = false;
      }
    }
    return bool;
  }
  
  public final int[] getDirectory$okio()
  {
    return this.directory;
  }
  
  public final byte[][] getSegments$okio()
  {
    return this.segments;
  }
  
  public int getSize$okio()
  {
    return getDirectory$okio()[(((Object[])getSegments$okio()).length - 1)];
  }
  
  public int hashCode()
  {
    int i = getHashCode$okio();
    if (i == 0)
    {
      i = 1;
      int i2 = ((Object[])getSegments$okio()).length;
      int j = 0;
      int k = 0;
      while (j < i2)
      {
        int n = getDirectory$okio()[(i2 + j)];
        int i1 = getDirectory$okio()[j];
        byte[] arrayOfByte = getSegments$okio()[j];
        for (int m = n; m < n + (i1 - k); m++) {
          i = i * 31 + arrayOfByte[m];
        }
        k = i1;
        j++;
      }
      setHashCode$okio(i);
    }
    return i;
  }
  
  public String hex()
  {
    return toByteString().hex();
  }
  
  public ByteString hmac$okio(String paramString, ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramString, "algorithm");
    Intrinsics.checkNotNullParameter(paramByteString, "key");
    try
    {
      Mac localMac = Mac.getInstance(paramString);
      SecretKeySpec localSecretKeySpec = new javax/crypto/spec/SecretKeySpec;
      localSecretKeySpec.<init>(paramByteString.toByteArray(), paramString);
      localMac.init((Key)localSecretKeySpec);
      int m = ((Object[])getSegments$okio()).length;
      int i = 0;
      int j = 0;
      while (i < m)
      {
        int n = getDirectory$okio()[(m + i)];
        int k = getDirectory$okio()[i];
        localMac.update(getSegments$okio()[i], n, k - j);
        j = k;
        i++;
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
  
  public int indexOf(byte[] paramArrayOfByte, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "other");
    return toByteString().indexOf(paramArrayOfByte, paramInt);
  }
  
  public byte[] internalArray$okio()
  {
    return toByteArray();
  }
  
  public byte internalGet$okio(int paramInt)
  {
    _Util.checkOffsetAndCount(getDirectory$okio()[(((Object[])getSegments$okio()).length - 1)], paramInt, 1L);
    int j = SegmentedByteStringKt.segment(this, paramInt);
    int i;
    if (j == 0) {
      i = 0;
    } else {
      i = getDirectory$okio()[(j - 1)];
    }
    int k = getDirectory$okio()[(((Object[])getSegments$okio()).length + j)];
    return getSegments$okio()[j][(paramInt - i + k)];
  }
  
  public int lastIndexOf(byte[] paramArrayOfByte, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "other");
    ByteString localByteString = toByteString();
    return localByteString.lastIndexOf(paramArrayOfByte, paramInt);
  }
  
  public boolean rangeEquals(int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "other");
    SegmentedByteString localSegmentedByteString2 = this;
    boolean bool;
    if ((paramInt1 >= 0) && (paramInt1 <= localSegmentedByteString2.size() - paramInt3))
    {
      int i = paramInt2;
      int j = paramInt1 + paramInt3;
      paramInt3 = SegmentedByteStringKt.segment(localSegmentedByteString2, paramInt1);
      paramInt2 = paramInt1;
      paramInt1 = paramInt3;
      paramInt3 = i;
      SegmentedByteString localSegmentedByteString1 = localSegmentedByteString2;
      while (paramInt2 < j)
      {
        if (paramInt1 == 0) {
          i = 0;
        } else {
          i = localSegmentedByteString2.getDirectory$okio()[(paramInt1 - 1)];
        }
        int k = localSegmentedByteString2.getDirectory$okio()[paramInt1];
        int m = localSegmentedByteString2.getDirectory$okio()[(((Object[])localSegmentedByteString2.getSegments$okio()).length + paramInt1)];
        k = Math.min(j, i + (k - i)) - paramInt2;
        if (!paramByteString.rangeEquals(paramInt3, localSegmentedByteString2.getSegments$okio()[paramInt1], paramInt2 - i + m, k))
        {
          bool = false;
          break label188;
        }
        paramInt3 += k;
        paramInt2 += k;
        paramInt1++;
      }
      bool = true;
    }
    else
    {
      bool = false;
    }
    label188:
    return bool;
  }
  
  public boolean rangeEquals(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "other");
    boolean bool;
    if ((paramInt1 >= 0) && (paramInt1 <= size() - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3))
    {
      int i = paramInt2;
      int j = paramInt1 + paramInt3;
      paramInt2 = SegmentedByteStringKt.segment(this, paramInt1);
      paramInt3 = i;
      while (paramInt1 < j)
      {
        if (paramInt2 == 0) {
          i = 0;
        } else {
          i = getDirectory$okio()[(paramInt2 - 1)];
        }
        int k = getDirectory$okio()[paramInt2];
        int m = getDirectory$okio()[(((Object[])getSegments$okio()).length + paramInt2)];
        k = Math.min(j, i + (k - i)) - paramInt1;
        if (!_Util.arrayRangeEquals(getSegments$okio()[paramInt2], m + (paramInt1 - i), paramArrayOfByte, paramInt3, k))
        {
          bool = false;
          break label181;
        }
        paramInt3 += k;
        paramInt1 += k;
        paramInt2++;
      }
      bool = true;
    }
    else
    {
      bool = false;
    }
    label181:
    return bool;
  }
  
  public String string(Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    return toByteString().string(paramCharset);
  }
  
  public ByteString substring(int paramInt1, int paramInt2)
  {
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
      if (paramInt2 <= size()) {
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
          Object localObject;
          if ((paramInt1 == 0) && (paramInt2 == size()))
          {
            localObject = (ByteString)this;
          }
          else if (paramInt1 == paramInt2)
          {
            localObject = ByteString.EMPTY;
          }
          else
          {
            k = SegmentedByteStringKt.segment(this, paramInt1);
            int n = SegmentedByteStringKt.segment(this, paramInt2 - 1);
            localObject = (byte[][])ArraysKt.copyOfRange((Object[])getSegments$okio(), k, n + 1);
            int[] arrayOfInt = new int[((Object[])localObject).length * 2];
            paramInt2 = 0;
            if (k <= n)
            {
              i = k;
              for (;;)
              {
                arrayOfInt[paramInt2] = Math.min(getDirectory$okio()[i] - paramInt1, m);
                arrayOfInt[(paramInt2 + ((Object[])localObject).length)] = getDirectory$okio()[(((Object[])getSegments$okio()).length + i)];
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
              paramInt2 = getDirectory$okio()[(k - 1)];
            }
            i = ((Object[])localObject).length;
            arrayOfInt[i] += paramInt1 - paramInt2;
            localObject = (ByteString)new SegmentedByteString((byte[][])localObject, arrayOfInt);
          }
          return (ByteString)localObject;
        }
        throw ((Throwable)new IllegalArgumentException(("endIndex=" + paramInt2 + " < beginIndex=" + paramInt1).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("endIndex=" + paramInt2 + " > length(" + size() + ')').toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("beginIndex=" + paramInt1 + " < 0").toString()));
  }
  
  public ByteString toAsciiLowercase()
  {
    return toByteString().toAsciiLowercase();
  }
  
  public ByteString toAsciiUppercase()
  {
    return toByteString().toAsciiUppercase();
  }
  
  public byte[] toByteArray()
  {
    byte[] arrayOfByte1 = new byte[size()];
    int j = 0;
    int n = ((Object[])getSegments$okio()).length;
    int i = 0;
    int k = 0;
    while (i < n)
    {
      int i1 = getDirectory$okio()[(n + i)];
      int m = getDirectory$okio()[i];
      byte[] arrayOfByte2 = getSegments$okio()[i];
      k = m - k;
      ArraysKt.copyInto(arrayOfByte2, arrayOfByte1, j, i1, i1 + k);
      j += k;
      k = m;
      i++;
    }
    return arrayOfByte1;
  }
  
  public String toString()
  {
    return toByteString().toString();
  }
  
  public void write(OutputStream paramOutputStream)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramOutputStream, "out");
    int m = ((Object[])getSegments$okio()).length;
    int i = 0;
    int j = 0;
    while (i < m)
    {
      int n = getDirectory$okio()[(m + i)];
      int k = getDirectory$okio()[i];
      paramOutputStream.write(getSegments$okio()[i], n, k - j);
      j = k;
      i++;
    }
  }
  
  public void write$okio(Buffer paramBuffer, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "buffer");
    int j = paramInt1 + paramInt2;
    for (paramInt2 = SegmentedByteStringKt.segment(this, paramInt1); paramInt1 < j; paramInt2++)
    {
      if (paramInt2 == 0) {
        i = 0;
      } else {
        i = getDirectory$okio()[(paramInt2 - 1)];
      }
      int k = getDirectory$okio()[paramInt2];
      int m = getDirectory$okio()[(((Object[])getSegments$okio()).length + paramInt2)];
      k = Math.min(j, i + (k - i)) - paramInt1;
      int i = paramInt1 - i + m;
      Segment localSegment1 = new Segment(getSegments$okio()[paramInt2], i, i + k, true, false);
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
      paramInt1 += k;
    }
    paramBuffer.setSize$okio(paramBuffer.size() + size());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/SegmentedByteString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */