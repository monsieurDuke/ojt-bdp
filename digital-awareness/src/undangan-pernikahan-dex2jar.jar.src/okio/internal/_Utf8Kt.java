package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\020\022\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\002\032\n\020\000\032\0020\001*\0020\002\032\036\020\003\032\0020\002*\0020\0012\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\005¨\006\007"}, d2={"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k=2, mv={1, 4, 0})
public final class _Utf8Kt
{
  public static final byte[] commonAsUtf8ToByteArray(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$commonAsUtf8ToByteArray");
    byte[] arrayOfByte = new byte[paramString.length() * 4];
    int k = paramString.length();
    for (int j = 0; j < k; j++)
    {
      int m = paramString.charAt(j);
      if (Intrinsics.compare(m, 128) >= 0)
      {
        m = j;
        int n = paramString.length();
        k = j;
        j = m;
        label460:
        while (k < n)
        {
          m = paramString.charAt(k);
          int i;
          if (Intrinsics.compare(m, 128) < 0)
          {
            i = (byte)m;
            m = j + 1;
            arrayOfByte[j] = i;
            k++;
            for (j = m; (k < n) && (Intrinsics.compare(paramString.charAt(k), 128) < 0); j++)
            {
              arrayOfByte[j] = ((byte)paramString.charAt(k));
              k++;
            }
          }
          else
          {
            int i1;
            if (Intrinsics.compare(m, 2048) < 0)
            {
              i = (byte)(m >> 6 | 0xC0);
              i1 = j + 1;
              arrayOfByte[j] = i;
              arrayOfByte[i1] = ((byte)(m & 0x3F | 0x80));
              k++;
              j = i1 + 1;
            }
            else if ((55296 <= m) && (57343 >= m))
            {
              if ((Intrinsics.compare(m, 56319) <= 0) && (n > k + 1))
              {
                i1 = paramString.charAt(k + 1);
                if ((56320 <= i1) && (57343 >= i1))
                {
                  m = (m << 10) + paramString.charAt(k + 1) - 56613888;
                  i = (byte)(m >> 18 | 0xF0);
                  i1 = j + 1;
                  arrayOfByte[j] = i;
                  i = (byte)(m >> 12 & 0x3F | 0x80);
                  j = i1 + 1;
                  arrayOfByte[i1] = i;
                  i = (byte)(m >> 6 & 0x3F | 0x80);
                  i1 = j + 1;
                  arrayOfByte[j] = i;
                  arrayOfByte[i1] = ((byte)(m & 0x3F | 0x80));
                  k += 2;
                  j = i1 + 1;
                  break label460;
                }
              }
              arrayOfByte[j] = 63;
              k++;
              j++;
            }
            else
            {
              i = (byte)(m >> 12 | 0xE0);
              i1 = j + 1;
              arrayOfByte[j] = i;
              i = (byte)(m >> 6 & 0x3F | 0x80);
              j = i1 + 1;
              arrayOfByte[i1] = i;
              arrayOfByte[j] = ((byte)(m & 0x3F | 0x80));
              k++;
              j++;
            }
          }
        }
        paramString = Arrays.copyOf(arrayOfByte, j);
        Intrinsics.checkNotNullExpressionValue(paramString, "java.util.Arrays.copyOf(this, newSize)");
        return paramString;
      }
      arrayOfByte[j] = ((byte)m);
    }
    paramString = Arrays.copyOf(arrayOfByte, paramString.length());
    Intrinsics.checkNotNullExpressionValue(paramString, "java.util.Arrays.copyOf(this, newSize)");
    return paramString;
  }
  
  public static final String commonToUtf8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$commonToUtf8String");
    if ((paramInt1 >= 0) && (paramInt2 <= paramArrayOfByte.length) && (paramInt1 <= paramInt2))
    {
      char[] arrayOfChar = new char[paramInt2 - paramInt1];
      int j = 0;
      int i3 = 0;
      int k = paramInt1;
      paramInt1 = j;
      while (k < paramInt2)
      {
        j = paramArrayOfByte[k];
        int i;
        if (j >= 0)
        {
          i = (char)j;
          j = paramInt1 + 1;
          arrayOfChar[paramInt1] = i;
          k++;
          paramInt1 = j;
          j = k;
          while ((j < paramInt2) && (paramArrayOfByte[j] >= 0))
          {
            arrayOfChar[paramInt1] = ((char)paramArrayOfByte[j]);
            j++;
            paramInt1++;
          }
        }
        else
        {
          Unit localUnit;
          int m;
          int n;
          if (j >> 5 == -2)
          {
            if (paramInt2 <= k + 1)
            {
              arrayOfChar[paramInt1] = ((char)65533);
              localUnit = Unit.INSTANCE;
              paramInt1++;
              j = 1;
            }
            else
            {
              m = paramArrayOfByte[k];
              n = paramArrayOfByte[(k + 1)];
              if ((n & 0xC0) == 128) {
                j = 1;
              } else {
                j = 0;
              }
              if (j == 0)
              {
                i = (char)65533;
                m = paramInt1 + 1;
                arrayOfChar[paramInt1] = i;
                localUnit = Unit.INSTANCE;
                j = 1;
                paramInt1 = m;
              }
              else
              {
                j = n ^ 0xF80 ^ m << 6;
                if (j < 128)
                {
                  i = (char)65533;
                  j = paramInt1 + 1;
                  arrayOfChar[paramInt1] = i;
                  paramInt1 = j;
                }
                else
                {
                  i = (char)j;
                  j = paramInt1 + 1;
                  arrayOfChar[paramInt1] = i;
                  paramInt1 = j;
                }
                localUnit = Unit.INSTANCE;
                j = 2;
              }
            }
            j = k + j;
          }
          else
          {
            int i1;
            if (j >> 4 == -2)
            {
              if (paramInt2 <= k + 2)
              {
                i = (char)65533;
                j = paramInt1 + 1;
                arrayOfChar[paramInt1] = i;
                localUnit = Unit.INSTANCE;
                if (paramInt2 > k + 1)
                {
                  if ((0xC0 & paramArrayOfByte[(k + 1)]) == 128) {
                    paramInt1 = 1;
                  } else {
                    paramInt1 = 0;
                  }
                  if (paramInt1 != 0)
                  {
                    paramInt1 = 2;
                    break label618;
                  }
                }
                paramInt1 = 1;
              }
              else
              {
                n = paramArrayOfByte[k];
                m = paramArrayOfByte[(k + 1)];
                if ((m & 0xC0) == 128) {
                  j = 1;
                } else {
                  j = 0;
                }
                if (j == 0)
                {
                  arrayOfChar[paramInt1] = ((char)65533);
                  localUnit = Unit.INSTANCE;
                  j = paramInt1 + 1;
                  paramInt1 = 1;
                }
                else
                {
                  i1 = paramArrayOfByte[(k + 2)];
                  if ((i1 & 0xC0) == 128) {
                    j = 1;
                  } else {
                    j = 0;
                  }
                  if (j == 0)
                  {
                    arrayOfChar[paramInt1] = ((char)65533);
                    localUnit = Unit.INSTANCE;
                    j = paramInt1 + 1;
                    paramInt1 = 2;
                  }
                  else
                  {
                    j = 0xFFFE1F80 ^ i1 ^ m << 6 ^ n << 12;
                    if (j < 2048)
                    {
                      i = (char)65533;
                      j = paramInt1 + 1;
                      arrayOfChar[paramInt1] = i;
                      paramInt1 = j;
                    }
                    for (;;)
                    {
                      localUnit = Unit.INSTANCE;
                      break;
                      if ((55296 <= j) && (57343 >= j))
                      {
                        i = (char)65533;
                        j = paramInt1 + 1;
                        arrayOfChar[paramInt1] = i;
                        paramInt1 = j;
                      }
                      else
                      {
                        i = (char)j;
                        j = paramInt1 + 1;
                        arrayOfChar[paramInt1] = i;
                        paramInt1 = j;
                      }
                    }
                    m = 3;
                    j = paramInt1;
                    paramInt1 = m;
                  }
                }
              }
              label618:
              k += paramInt1;
              paramInt1 = j;
              j = k;
            }
            else if (j >> 3 == -2)
            {
              if (paramInt2 <= k + 3)
              {
                if (65533 != 65533)
                {
                  i = (char)((65533 >>> 10) + 55232);
                  m = paramInt1 + 1;
                  arrayOfChar[paramInt1] = i;
                  i = (char)((0xFFFD & 0x3FF) + 56320);
                  j = m + 1;
                  arrayOfChar[m] = i;
                }
                else
                {
                  arrayOfChar[paramInt1] = 65533;
                  j = paramInt1 + 1;
                }
                localUnit = Unit.INSTANCE;
                if (paramInt2 > k + 1)
                {
                  if ((0xC0 & paramArrayOfByte[(k + 1)]) == 128) {
                    paramInt1 = 1;
                  } else {
                    paramInt1 = 0;
                  }
                  if (paramInt1 != 0)
                  {
                    if (paramInt2 > k + 2)
                    {
                      if ((0xC0 & paramArrayOfByte[(k + 2)]) == 128) {
                        paramInt1 = 1;
                      } else {
                        paramInt1 = 0;
                      }
                      if (paramInt1 != 0)
                      {
                        paramInt1 = 3;
                        break label1482;
                      }
                    }
                    paramInt1 = 2;
                    break label1482;
                  }
                }
                paramInt1 = 1;
              }
              else
              {
                n = paramArrayOfByte[k];
                m = paramArrayOfByte[(k + 1)];
                if ((m & 0xC0) == 128) {
                  j = 1;
                } else {
                  j = 0;
                }
                if (j == 0)
                {
                  if (65533 != 65533)
                  {
                    i = (char)((65533 >>> 10) + 55232);
                    m = paramInt1 + 1;
                    arrayOfChar[paramInt1] = i;
                    i = (char)((0xFFFD & 0x3FF) + 56320);
                    j = m + 1;
                    arrayOfChar[m] = i;
                  }
                  else
                  {
                    arrayOfChar[paramInt1] = 65533;
                    j = paramInt1 + 1;
                  }
                  localUnit = Unit.INSTANCE;
                  paramInt1 = 1;
                }
                else
                {
                  i1 = paramArrayOfByte[(k + 2)];
                  if ((i1 & 0xC0) == 128) {
                    j = 1;
                  } else {
                    j = 0;
                  }
                  if (j == 0)
                  {
                    if (65533 != 65533)
                    {
                      i = (char)((65533 >>> 10) + 55232);
                      j = paramInt1 + 1;
                      arrayOfChar[paramInt1] = i;
                      arrayOfChar[j] = ((char)((0xFFFD & 0x3FF) + 56320));
                      j++;
                    }
                    else
                    {
                      arrayOfChar[paramInt1] = 65533;
                      j = paramInt1 + 1;
                    }
                    localUnit = Unit.INSTANCE;
                    paramInt1 = 2;
                  }
                  else
                  {
                    int i2 = paramArrayOfByte[(k + 3)];
                    if ((i2 & 0xC0) == 128) {
                      j = 1;
                    } else {
                      j = 0;
                    }
                    if (j == 0)
                    {
                      if (65533 != 65533)
                      {
                        i = (char)((65533 >>> 10) + 55232);
                        j = paramInt1 + 1;
                        arrayOfChar[paramInt1] = i;
                        i = (char)((0xFFFD & 0x3FF) + 56320);
                        paramInt1 = j + 1;
                        arrayOfChar[j] = i;
                      }
                      else
                      {
                        arrayOfChar[paramInt1] = 65533;
                        paramInt1++;
                      }
                      localUnit = Unit.INSTANCE;
                      j = paramInt1;
                      paramInt1 = 3;
                    }
                    else
                    {
                      m = 0x381F80 ^ i2 ^ i1 << 6 ^ m << 12 ^ n << 18;
                      if (m > 1114111) {
                        if (65533 != 65533)
                        {
                          i = (char)((65533 >>> 10) + 55232);
                          j = paramInt1 + 1;
                          arrayOfChar[paramInt1] = i;
                          i = (char)((0xFFFD & 0x3FF) + 56320);
                          paramInt1 = j + 1;
                          arrayOfChar[j] = i;
                        }
                        else
                        {
                          j = paramInt1 + 1;
                          arrayOfChar[paramInt1] = 65533;
                          paramInt1 = j;
                        }
                      }
                      for (;;)
                      {
                        localUnit = Unit.INSTANCE;
                        break label1473;
                        if ((55296 <= m) && (57343 >= m))
                        {
                          if (65533 != 65533)
                          {
                            i = (char)((65533 >>> 10) + 55232);
                            j = paramInt1 + 1;
                            arrayOfChar[paramInt1] = i;
                            i = (char)((0xFFFD & 0x3FF) + 56320);
                            paramInt1 = j + 1;
                            arrayOfChar[j] = i;
                            break;
                          }
                          j = paramInt1 + 1;
                          arrayOfChar[paramInt1] = 65533;
                          paramInt1 = j;
                          continue;
                        }
                        if (m < 65536)
                        {
                          if (65533 != 65533)
                          {
                            i = (char)((65533 >>> 10) + 55232);
                            j = paramInt1 + 1;
                            arrayOfChar[paramInt1] = i;
                            i = (char)((0xFFFD & 0x3FF) + 56320);
                            paramInt1 = j + 1;
                            arrayOfChar[j] = i;
                            break;
                          }
                          j = paramInt1 + 1;
                          arrayOfChar[paramInt1] = 65533;
                          paramInt1 = j;
                          continue;
                        }
                        if (m != 65533)
                        {
                          i = (char)((m >>> 10) + 55232);
                          j = paramInt1 + 1;
                          arrayOfChar[paramInt1] = i;
                          i = (char)((m & 0x3FF) + 56320);
                          paramInt1 = j + 1;
                          arrayOfChar[j] = i;
                          break;
                        }
                        j = paramInt1 + 1;
                        arrayOfChar[paramInt1] = 65533;
                        paramInt1 = j;
                      }
                      label1473:
                      m = 4;
                      j = paramInt1;
                      paramInt1 = m;
                    }
                  }
                }
              }
              label1482:
              k += paramInt1;
              paramInt1 = j;
              j = k;
            }
            else
            {
              arrayOfChar[paramInt1] = 65533;
              j = k + 1;
              paramInt1++;
            }
          }
        }
        k = j;
      }
      paramArrayOfByte = new String(arrayOfChar, 0, paramInt1);
      Log5ECF72.a(paramArrayOfByte);
      LogE84000.a(paramArrayOfByte);
      Log229316.a(paramArrayOfByte);
      return paramArrayOfByte;
    }
    throw ((Throwable)new ArrayIndexOutOfBoundsException("size=" + paramArrayOfByte.length + " beginIndex=" + paramInt1 + " endIndex=" + paramInt2));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/internal/_Utf8Kt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */