package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class Utf8Safe
  extends Utf8
{
  private static int computeEncodedLength(CharSequence paramCharSequence)
  {
    int n = paramCharSequence.length();
    int m = n;
    int i;
    int k;
    for (int j = 0;; j++)
    {
      i = m;
      k = j;
      if (j >= n) {
        break;
      }
      i = m;
      k = j;
      if (paramCharSequence.charAt(j) >= '') {
        break;
      }
    }
    for (;;)
    {
      j = i;
      if (k >= n) {
        break label96;
      }
      j = paramCharSequence.charAt(k);
      if (j >= 2048) {
        break;
      }
      i += (127 - j >>> 31);
      k++;
    }
    j = i + encodedLengthGeneral(paramCharSequence, k);
    label96:
    if (j >= n) {
      return j;
    }
    throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (j + 4294967296L));
  }
  
  public static String decodeUtf8Array(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt1 | paramInt2 | paramArrayOfByte.length - paramInt1 - paramInt2) >= 0)
    {
      int j = paramInt1 + paramInt2;
      char[] arrayOfChar = new char[paramInt2];
      byte b1;
      for (paramInt2 = 0; paramInt1 < j; paramInt2++)
      {
        b1 = paramArrayOfByte[paramInt1];
        if (!Utf8.DecodeUtil.isOneByte(b1)) {
          break;
        }
        paramInt1++;
        Utf8.DecodeUtil.handleOneByte(b1, arrayOfChar, paramInt2);
      }
      while (paramInt1 < j)
      {
        int i = paramInt1 + 1;
        byte b2 = paramArrayOfByte[paramInt1];
        if (Utf8.DecodeUtil.isOneByte(b2))
        {
          paramInt1 = paramInt2 + 1;
          Utf8.DecodeUtil.handleOneByte(b2, arrayOfChar, paramInt2);
          paramInt2 = i;
          while (paramInt2 < j)
          {
            b1 = paramArrayOfByte[paramInt2];
            if (!Utf8.DecodeUtil.isOneByte(b1)) {
              break;
            }
            paramInt2++;
            Utf8.DecodeUtil.handleOneByte(b1, arrayOfChar, paramInt1);
            paramInt1++;
          }
          i = paramInt2;
          paramInt2 = paramInt1;
          paramInt1 = i;
        }
        else if (Utf8.DecodeUtil.isTwoBytes(b2))
        {
          if (i < j)
          {
            Utf8.DecodeUtil.handleTwoBytes(b2, paramArrayOfByte[i], arrayOfChar, paramInt2);
            paramInt1 = i + 1;
            paramInt2++;
          }
          else
          {
            throw new IllegalArgumentException("Invalid UTF-8");
          }
        }
        else if (Utf8.DecodeUtil.isThreeBytes(b2))
        {
          if (i < j - 1)
          {
            paramInt1 = i + 1;
            Utf8.DecodeUtil.handleThreeBytes(b2, paramArrayOfByte[i], paramArrayOfByte[paramInt1], arrayOfChar, paramInt2);
            paramInt1++;
            paramInt2++;
          }
          else
          {
            throw new IllegalArgumentException("Invalid UTF-8");
          }
        }
        else
        {
          if (i >= j - 2) {
            break label305;
          }
          paramInt1 = i + 1;
          b1 = paramArrayOfByte[i];
          i = paramInt1 + 1;
          Utf8.DecodeUtil.handleFourBytes(b2, b1, paramArrayOfByte[paramInt1], paramArrayOfByte[i], arrayOfChar, paramInt2);
          paramInt1 = i + 1;
          paramInt2 = paramInt2 + 1 + 1;
        }
        continue;
        label305:
        throw new IllegalArgumentException("Invalid UTF-8");
      }
      paramArrayOfByte = new String(arrayOfChar, 0, paramInt2);
      Log5ECF72.a(paramArrayOfByte);
      LogE84000.a(paramArrayOfByte);
      Log229316.a(paramArrayOfByte);
      return paramArrayOfByte;
    }
    paramArrayOfByte = String.format("buffer length=%d, index=%d, size=%d", new Object[] { Integer.valueOf(paramArrayOfByte.length), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
    Log5ECF72.a(paramArrayOfByte);
    LogE84000.a(paramArrayOfByte);
    Log229316.a(paramArrayOfByte);
    throw new ArrayIndexOutOfBoundsException(paramArrayOfByte);
  }
  
  public static String decodeUtf8Buffer(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
  {
    if ((paramInt1 | paramInt2 | paramByteBuffer.limit() - paramInt1 - paramInt2) >= 0)
    {
      int j = paramInt1 + paramInt2;
      char[] arrayOfChar = new char[paramInt2];
      byte b1;
      for (paramInt2 = 0; paramInt1 < j; paramInt2++)
      {
        b1 = paramByteBuffer.get(paramInt1);
        if (!Utf8.DecodeUtil.isOneByte(b1)) {
          break;
        }
        paramInt1++;
        Utf8.DecodeUtil.handleOneByte(b1, arrayOfChar, paramInt2);
      }
      int i = paramInt2;
      paramInt2 = paramInt1;
      paramInt1 = i;
      while (paramInt2 < j)
      {
        i = paramInt2 + 1;
        byte b2 = paramByteBuffer.get(paramInt2);
        if (Utf8.DecodeUtil.isOneByte(b2))
        {
          paramInt2 = paramInt1 + 1;
          Utf8.DecodeUtil.handleOneByte(b2, arrayOfChar, paramInt1);
          paramInt1 = paramInt2;
          paramInt2 = i;
          while (paramInt2 < j)
          {
            b1 = paramByteBuffer.get(paramInt2);
            if (!Utf8.DecodeUtil.isOneByte(b1)) {
              break;
            }
            paramInt2++;
            Utf8.DecodeUtil.handleOneByte(b1, arrayOfChar, paramInt1);
            paramInt1++;
          }
        }
        else if (Utf8.DecodeUtil.isTwoBytes(b2))
        {
          if (i < j)
          {
            Utf8.DecodeUtil.handleTwoBytes(b2, paramByteBuffer.get(i), arrayOfChar, paramInt1);
            paramInt2 = i + 1;
            paramInt1++;
          }
          else
          {
            throw new IllegalArgumentException("Invalid UTF-8");
          }
        }
        else if (Utf8.DecodeUtil.isThreeBytes(b2))
        {
          if (i < j - 1)
          {
            paramInt2 = i + 1;
            Utf8.DecodeUtil.handleThreeBytes(b2, paramByteBuffer.get(i), paramByteBuffer.get(paramInt2), arrayOfChar, paramInt1);
            paramInt2++;
            paramInt1++;
          }
          else
          {
            throw new IllegalArgumentException("Invalid UTF-8");
          }
        }
        else
        {
          if (i >= j - 2) {
            break label327;
          }
          paramInt2 = i + 1;
          b1 = paramByteBuffer.get(i);
          i = paramInt2 + 1;
          Utf8.DecodeUtil.handleFourBytes(b2, b1, paramByteBuffer.get(paramInt2), paramByteBuffer.get(i), arrayOfChar, paramInt1);
          paramInt2 = i + 1;
          paramInt1 = paramInt1 + 1 + 1;
        }
        continue;
        label327:
        throw new IllegalArgumentException("Invalid UTF-8");
      }
      paramByteBuffer = new String(arrayOfChar, 0, paramInt1);
      Log5ECF72.a(paramByteBuffer);
      LogE84000.a(paramByteBuffer);
      Log229316.a(paramByteBuffer);
      return paramByteBuffer;
    }
    paramByteBuffer = String.format("buffer limit=%d, index=%d, limit=%d", new Object[] { Integer.valueOf(paramByteBuffer.limit()), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
    Log5ECF72.a(paramByteBuffer);
    LogE84000.a(paramByteBuffer);
    Log229316.a(paramByteBuffer);
    throw new ArrayIndexOutOfBoundsException(paramByteBuffer);
  }
  
  private static int encodeUtf8Array(CharSequence paramCharSequence, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int k = paramCharSequence.length();
    int j = 0;
    int m = paramInt1 + paramInt2;
    for (paramInt2 = j; (paramInt2 < k) && (paramInt2 + paramInt1 < m); paramInt2++)
    {
      j = paramCharSequence.charAt(paramInt2);
      if (j >= 128) {
        break;
      }
      paramArrayOfByte[(paramInt1 + paramInt2)] = ((byte)j);
    }
    if (paramInt2 == k) {
      return paramInt1 + k;
    }
    paramInt1 += paramInt2;
    while (paramInt2 < k)
    {
      int i = paramCharSequence.charAt(paramInt2);
      if ((i < 128) && (paramInt1 < m))
      {
        paramArrayOfByte[paramInt1] = ((byte)i);
        paramInt1++;
      }
      else if ((i < 2048) && (paramInt1 <= m - 2))
      {
        j = paramInt1 + 1;
        paramArrayOfByte[paramInt1] = ((byte)(i >>> 6 | 0x3C0));
        paramInt1 = j + 1;
        paramArrayOfByte[j] = ((byte)(i & 0x3F | 0x80));
      }
      else if (((i < 55296) || (57343 < i)) && (paramInt1 <= m - 3))
      {
        j = paramInt1 + 1;
        paramArrayOfByte[paramInt1] = ((byte)(i >>> 12 | 0x1E0));
        paramInt1 = j + 1;
        paramArrayOfByte[j] = ((byte)(i >>> 6 & 0x3F | 0x80));
        paramArrayOfByte[paramInt1] = ((byte)(i & 0x3F | 0x80));
        paramInt1++;
      }
      else
      {
        if (paramInt1 > m - 4) {
          break label414;
        }
        j = paramInt2;
        if (paramInt2 + 1 == paramCharSequence.length()) {
          break label400;
        }
        paramInt2++;
        char c = paramCharSequence.charAt(paramInt2);
        j = paramInt2;
        if (!Character.isSurrogatePair(i, c)) {
          break label400;
        }
        j = Character.toCodePoint(i, c);
        int n = paramInt1 + 1;
        paramArrayOfByte[paramInt1] = ((byte)(j >>> 18 | 0xF0));
        paramInt1 = n + 1;
        paramArrayOfByte[n] = ((byte)(j >>> 12 & 0x3F | 0x80));
        n = paramInt1 + 1;
        paramArrayOfByte[paramInt1] = ((byte)(j >>> 6 & 0x3F | 0x80));
        paramInt1 = n + 1;
        paramArrayOfByte[n] = ((byte)(j & 0x3F | 0x80));
      }
      paramInt2++;
      continue;
      label400:
      throw new UnpairedSurrogateException(j - 1, k);
      label414:
      if ((55296 <= i) && (i <= 57343) && ((paramInt2 + 1 == paramCharSequence.length()) || (!Character.isSurrogatePair(i, paramCharSequence.charAt(paramInt2 + 1))))) {
        throw new UnpairedSurrogateException(paramInt2, k);
      }
      throw new ArrayIndexOutOfBoundsException("Failed writing " + i + " at index " + paramInt1);
    }
    return paramInt1;
  }
  
  private static void encodeUtf8Buffer(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    int i2 = paramCharSequence.length();
    int n = paramByteBuffer.position();
    int j = 0;
    int i1;
    for (;;)
    {
      if (j < i2)
      {
        m = n;
        k = j;
        try
        {
          i1 = paramCharSequence.charAt(j);
          if (i1 < 128)
          {
            m = n;
            k = j;
            paramByteBuffer.put(n + j, (byte)i1);
            j++;
          }
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException1)
        {
          j = k;
          break label614;
        }
      }
    }
    if (j == i2)
    {
      m = n;
      k = j;
      paramByteBuffer.position(n + j);
      return;
    }
    n += j;
    while (j < i2)
    {
      m = n;
      k = j;
      int i = paramCharSequence.charAt(j);
      if (i < 128)
      {
        m = n;
        k = j;
        paramByteBuffer.put(n, (byte)i);
      }
      else
      {
        byte b;
        if (i < 2048)
        {
          m = n + 1;
          b = (byte)(i >>> 6 | 0xC0);
          k = m;
          try
          {
            paramByteBuffer.put(n, b);
            k = m;
            paramByteBuffer.put(m, (byte)(i & 0x3F | 0x80));
            n = m;
          }
          catch (IndexOutOfBoundsException localIndexOutOfBoundsException2)
          {
            m = k;
            break label614;
          }
        }
        else if ((i >= 55296) && (57343 >= i))
        {
          i1 = j;
          if (j + 1 != i2)
          {
            j++;
            m = n;
            k = j;
            char c = paramCharSequence.charAt(j);
            m = n;
            k = j;
            i1 = j;
            if (Character.isSurrogatePair(i, c))
            {
              m = n;
              k = j;
              int i3 = Character.toCodePoint(i, c);
              i1 = n + 1;
              b = (byte)(i3 >>> 18 | 0xF0);
              k = i1;
              try
              {
                paramByteBuffer.put(n, b);
                n = i1 + 1;
                b = (byte)(i3 >>> 12 & 0x3F | 0x80);
                m = n;
                k = j;
                paramByteBuffer.put(i1, b);
                m = n + 1;
                b = (byte)(i3 >>> 6 & 0x3F | 0x80);
                k = m;
                paramByteBuffer.put(n, b);
                k = m;
                paramByteBuffer.put(m, (byte)(i3 & 0x3F | 0x80));
                n = m;
              }
              catch (IndexOutOfBoundsException localIndexOutOfBoundsException3)
              {
                m = k;
                break label614;
              }
            }
          }
          m = n;
          k = i1;
          UnpairedSurrogateException localUnpairedSurrogateException = new androidx/emoji2/text/flatbuffer/Utf8Safe$UnpairedSurrogateException;
          m = n;
          k = i1;
          localUnpairedSurrogateException.<init>(i1, i2);
          m = n;
          k = i1;
          throw localUnpairedSurrogateException;
        }
        else
        {
          i1 = n + 1;
          b = (byte)(i >>> 12 | 0xE0);
          k = i1;
          paramByteBuffer.put(n, b);
          n = i1 + 1;
          b = (byte)(i >>> 6 & 0x3F | 0x80);
          m = n;
          k = j;
          paramByteBuffer.put(i1, b);
          m = n;
          k = j;
          paramByteBuffer.put(n, (byte)(i & 0x3F | 0x80));
        }
      }
      j++;
      n++;
    }
    int m = n;
    int k = j;
    paramByteBuffer.position(n);
    return;
    label614:
    k = paramByteBuffer.position();
    m = Math.max(j, m - paramByteBuffer.position() + 1);
    throw new ArrayIndexOutOfBoundsException("Failed writing " + paramCharSequence.charAt(j) + " at index " + (k + m));
  }
  
  private static int encodedLengthGeneral(CharSequence paramCharSequence, int paramInt)
  {
    int m = paramCharSequence.length();
    int i = 0;
    while (paramInt < m)
    {
      int n = paramCharSequence.charAt(paramInt);
      int j;
      if (n < 2048)
      {
        i += (127 - n >>> 31);
        j = paramInt;
      }
      else
      {
        int k = i + 2;
        i = k;
        j = paramInt;
        if (55296 <= n)
        {
          i = k;
          j = paramInt;
          if (n <= 57343) {
            if (Character.codePointAt(paramCharSequence, paramInt) >= 65536)
            {
              j = paramInt + 1;
              i = k;
            }
            else
            {
              throw new UnpairedSurrogateException(paramInt, m);
            }
          }
        }
      }
      paramInt = j + 1;
    }
    return i;
  }
  
  public String decodeUtf8(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
    throws IllegalArgumentException
  {
    if (paramByteBuffer.hasArray())
    {
      paramByteBuffer = decodeUtf8Array(paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramInt1, paramInt2);
      Log5ECF72.a(paramByteBuffer);
      LogE84000.a(paramByteBuffer);
      Log229316.a(paramByteBuffer);
      return paramByteBuffer;
    }
    paramByteBuffer = decodeUtf8Buffer(paramByteBuffer, paramInt1, paramInt2);
    Log5ECF72.a(paramByteBuffer);
    LogE84000.a(paramByteBuffer);
    Log229316.a(paramByteBuffer);
    return paramByteBuffer;
  }
  
  public void encodeUtf8(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.hasArray())
    {
      int i = paramByteBuffer.arrayOffset();
      paramByteBuffer.position(encodeUtf8Array(paramCharSequence, paramByteBuffer.array(), paramByteBuffer.position() + i, paramByteBuffer.remaining()) - i);
    }
    else
    {
      encodeUtf8Buffer(paramCharSequence, paramByteBuffer);
    }
  }
  
  public int encodedLength(CharSequence paramCharSequence)
  {
    return computeEncodedLength(paramCharSequence);
  }
  
  static class UnpairedSurrogateException
    extends IllegalArgumentException
  {
    UnpairedSurrogateException(int paramInt1, int paramInt2)
    {
      super();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/Utf8Safe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */