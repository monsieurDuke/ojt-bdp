package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okio.Buffer;
import okio.ByteString;
import okio._Base64;
import okio._Platform;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\000P\n\000\n\002\020\031\n\002\b\003\n\002\020\b\n\000\n\002\020\022\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\f\n\000\n\002\020\016\n\002\b\007\n\002\020\013\n\002\b\002\n\002\020\000\n\000\n\002\020\005\n\002\b\030\n\002\020\002\n\000\n\002\030\002\n\000\032\030\020\004\032\0020\0052\006\020\006\032\0020\0072\006\020\b\032\0020\005H\002\032\021\020\t\032\0020\n2\006\020\013\032\0020\007H\b\032\020\020\f\032\0020\0052\006\020\r\032\0020\016H\002\032\r\020\017\032\0020\020*\0020\nH\b\032\r\020\021\032\0020\020*\0020\nH\b\032\025\020\022\032\0020\005*\0020\n2\006\020\023\032\0020\nH\b\032\017\020\024\032\004\030\0010\n*\0020\020H\b\032\r\020\025\032\0020\n*\0020\020H\b\032\r\020\026\032\0020\n*\0020\020H\b\032\025\020\027\032\0020\030*\0020\n2\006\020\031\032\0020\007H\b\032\025\020\027\032\0020\030*\0020\n2\006\020\031\032\0020\nH\b\032\027\020\032\032\0020\030*\0020\n2\b\020\023\032\004\030\0010\033H\b\032\025\020\034\032\0020\035*\0020\n2\006\020\036\032\0020\005H\b\032\r\020\037\032\0020\005*\0020\nH\b\032\r\020 \032\0020\005*\0020\nH\b\032\r\020!\032\0020\020*\0020\nH\b\032\035\020\"\032\0020\005*\0020\n2\006\020\023\032\0020\0072\006\020#\032\0020\005H\b\032\r\020$\032\0020\007*\0020\nH\b\032\035\020%\032\0020\005*\0020\n2\006\020\023\032\0020\0072\006\020#\032\0020\005H\b\032\035\020%\032\0020\005*\0020\n2\006\020\023\032\0020\n2\006\020#\032\0020\005H\b\032-\020&\032\0020\030*\0020\n2\006\020'\032\0020\0052\006\020\023\032\0020\0072\006\020(\032\0020\0052\006\020)\032\0020\005H\b\032-\020&\032\0020\030*\0020\n2\006\020'\032\0020\0052\006\020\023\032\0020\n2\006\020(\032\0020\0052\006\020)\032\0020\005H\b\032\025\020*\032\0020\030*\0020\n2\006\020+\032\0020\007H\b\032\025\020*\032\0020\030*\0020\n2\006\020+\032\0020\nH\b\032\035\020,\032\0020\n*\0020\n2\006\020-\032\0020\0052\006\020.\032\0020\005H\b\032\r\020/\032\0020\n*\0020\nH\b\032\r\0200\032\0020\n*\0020\nH\b\032\r\0201\032\0020\007*\0020\nH\b\032\035\0202\032\0020\n*\0020\0072\006\020'\032\0020\0052\006\020)\032\0020\005H\b\032\r\0203\032\0020\020*\0020\nH\b\032\r\0204\032\0020\020*\0020\nH\b\032$\0205\032\00206*\0020\n2\006\0207\032\002082\006\020'\032\0020\0052\006\020)\032\0020\005H\000\"\024\020\000\032\0020\001X\004¢\006\b\n\000\032\004\b\002\020\003¨\0069"}, d2={"HEX_DIGIT_CHARS", "", "getHEX_DIGIT_CHARS", "()[C", "codePointIndexToCharIndex", "", "s", "", "codePointCount", "commonOf", "Lokio/ByteString;", "data", "decodeHexDigit", "c", "", "commonBase64", "", "commonBase64Url", "commonCompareTo", "other", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", "", "suffix", "commonEquals", "", "commonGetByte", "", "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", "offset", "otherOffset", "byteCount", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToByteString", "commonToString", "commonUtf8", "commonWrite", "", "buffer", "Lokio/Buffer;", "okio"}, k=2, mv={1, 4, 0})
public final class ByteStringKt
{
  private static final char[] HEX_DIGIT_CHARS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  
  private static final int codePointIndexToCharIndex(byte[] paramArrayOfByte, int paramInt)
  {
    int j = 0;
    int n = 0;
    int i25 = 0;
    int i23 = paramArrayOfByte.length;
    int k;
    for (int m = 0; m < i23; m = k)
    {
      int i24 = paramArrayOfByte[m];
      int i20 = 0;
      int i18 = 0;
      int i17 = 0;
      int i14 = 0;
      int i19 = 0;
      int i13 = 0;
      int i9 = 0;
      int i7 = 0;
      int i11 = 0;
      int i10 = 0;
      int i21 = 0;
      int i12 = 0;
      int i5 = 0;
      int i4 = 0;
      int i3 = 0;
      int i2 = 0;
      int i1 = 0;
      int i22 = 0;
      k = 0;
      int i = 2;
      int i15 = 1;
      int i8 = 1;
      int i6 = 1;
      int i16 = 1;
      if (i24 >= 0)
      {
        if (n == paramInt) {
          return j;
        }
        if ((i24 != 10) && (i24 != 13))
        {
          if (((i24 >= 0) && (31 >= i24)) || ((127 <= i24) && (159 >= i24))) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0) {}
        }
        else
        {
          if (i24 != 65533) {
            break label176;
          }
        }
        return -1;
        label176:
        if (i24 < 65536) {
          i = 1;
        } else {
          i = 2;
        }
        j += i;
        k = m + 1;
        for (i = n + 1; (k < i23) && (paramArrayOfByte[k] >= 0); i++)
        {
          n = paramArrayOfByte[k];
          if (i == paramInt) {
            return j;
          }
          if ((n != 10) && (n != 13))
          {
            if (((n >= 0) && (31 >= n)) || ((127 <= n) && (159 >= n))) {
              m = 1;
            } else {
              m = 0;
            }
            if (m != 0) {}
          }
          else
          {
            if (n != 65533) {
              break label299;
            }
          }
          return -1;
          label299:
          if (n < 65536) {
            m = 1;
          } else {
            m = 2;
          }
          j += m;
          k++;
        }
      }
      else
      {
        label425:
        Unit localUnit;
        if (i24 >> 5 == -2)
        {
          if (i23 <= m + 1)
          {
            i1 = n + 1;
            if (n == paramInt) {
              return j;
            }
            if ((65533 != 10) && (65533 != 13))
            {
              if (((65533 >= 0) && (31 >= 65533)) || ((127 <= 65533) && (159 >= 65533))) {
                k = 1;
              }
              if (k != 0) {}
            }
            else
            {
              if (65533 != 65533) {
                break label425;
              }
            }
            return -1;
            if (65533 < 65536) {
              i = 1;
            }
            j += i;
            localUnit = Unit.INSTANCE;
            i = 1;
            k = i1;
          }
          else
          {
            i2 = paramArrayOfByte[m];
            i1 = paramArrayOfByte[(m + 1)];
            if ((i1 & 0xC0) == 128) {
              k = 1;
            } else {
              k = 0;
            }
            if (k == 0)
            {
              if (n == paramInt) {
                return j;
              }
              if ((65533 != 10) && (65533 != 13))
              {
                if (65533 >= 0) {
                  if (31 >= 65533) {
                    break label555;
                  }
                }
                if (127 > 65533)
                {
                  k = i20;
                }
                else
                {
                  k = i20;
                  if (159 >= 65533) {
                    label555:
                    k = 1;
                  }
                }
                if (k != 0) {}
              }
              else
              {
                if (65533 != 65533) {
                  break label572;
                }
              }
              return -1;
              label572:
              if (65533 < 65536) {
                i = 1;
              }
              j += i;
              localUnit = Unit.INSTANCE;
              i = 1;
              k = n + 1;
            }
            else
            {
              i1 = i1 ^ 0xF80 ^ i2 << 6;
              if (i1 < 128)
              {
                if (n == paramInt) {
                  return j;
                }
                if ((65533 != 10) && (65533 != 13))
                {
                  if (65533 >= 0) {
                    if (31 >= 65533) {
                      break label686;
                    }
                  }
                  if (127 > 65533)
                  {
                    k = i18;
                  }
                  else
                  {
                    k = i18;
                    if (159 >= 65533) {
                      label686:
                      k = 1;
                    }
                  }
                  if (k != 0) {}
                }
                else
                {
                  if (65533 != 65533) {
                    break label703;
                  }
                }
                return -1;
                label703:
                if (65533 < 65536) {
                  k = i16;
                } else {
                  k = 2;
                }
                j += k;
                localUnit = Unit.INSTANCE;
                k = n + 1;
              }
              else
              {
                if (n == paramInt) {
                  return j;
                }
                if ((i1 != 10) && (i1 != 13))
                {
                  if (i1 >= 0) {
                    if (31 >= i1) {
                      break label802;
                    }
                  }
                  if (127 > i1)
                  {
                    k = i17;
                  }
                  else
                  {
                    k = i17;
                    if (159 >= i1) {
                      label802:
                      k = 1;
                    }
                  }
                  if (k != 0) {}
                }
                else
                {
                  if (i1 != 65533) {
                    break label819;
                  }
                }
                return -1;
                label819:
                if (i1 < 65536) {
                  k = i15;
                } else {
                  k = 2;
                }
                j += k;
                localUnit = Unit.INSTANCE;
                k = n + 1;
              }
            }
          }
          m += i;
          i = k;
          k = m;
        }
        else if (i24 >> 4 == -2)
        {
          if (i23 <= m + 2)
          {
            i1 = n + 1;
            if (n == paramInt) {
              return j;
            }
            if ((65533 != 10) && (65533 != 13))
            {
              if (((65533 >= 0) && (31 >= 65533)) || ((127 <= 65533) && (159 >= 65533))) {
                k = 1;
              } else {
                k = 0;
              }
              if (k != 0) {}
            }
            else
            {
              if (65533 != 65533) {
                break label967;
              }
            }
            return -1;
            label967:
            if (65533 < 65536) {
              k = 1;
            } else {
              k = 2;
            }
            k = j + k;
            localUnit = Unit.INSTANCE;
            if (i23 > m + 1)
            {
              j = i14;
              if ((0xC0 & paramArrayOfByte[(m + 1)]) == 128) {
                j = 1;
              }
              if (j != 0)
              {
                j = k;
                k = i1;
                break label1729;
              }
            }
            i = 1;
            j = k;
            k = i1;
          }
          else
          {
            i2 = paramArrayOfByte[m];
            i1 = paramArrayOfByte[(m + 1)];
            if ((i1 & 0xC0) == 128) {
              k = 1;
            } else {
              k = 0;
            }
            if (k == 0)
            {
              if (n == paramInt) {
                return j;
              }
              if ((65533 != 10) && (65533 != 13))
              {
                if (65533 >= 0) {
                  if (31 >= 65533) {
                    break label1156;
                  }
                }
                if (127 > 65533)
                {
                  k = i19;
                }
                else
                {
                  k = i19;
                  if (159 >= 65533) {
                    label1156:
                    k = 1;
                  }
                }
                if (k != 0) {}
              }
              else
              {
                if (65533 != 65533) {
                  break label1173;
                }
              }
              return -1;
              label1173:
              if (65533 < 65536) {
                i = 1;
              }
              j += i;
              localUnit = Unit.INSTANCE;
              i = 1;
              k = n + 1;
            }
            else
            {
              i3 = paramArrayOfByte[(m + 2)];
              if ((i3 & 0xC0) == 128) {
                k = 1;
              } else {
                k = 0;
              }
              if (k == 0)
              {
                if (n == paramInt) {
                  return j;
                }
                if ((65533 != 10) && (65533 != 13))
                {
                  if (65533 >= 0) {
                    if (31 >= 65533) {
                      break label1299;
                    }
                  }
                  if (127 > 65533)
                  {
                    k = i13;
                  }
                  else
                  {
                    k = i13;
                    if (159 >= 65533) {
                      label1299:
                      k = 1;
                    }
                  }
                  if (k != 0) {}
                }
                else
                {
                  if (65533 != 65533) {
                    break label1316;
                  }
                }
                return -1;
                label1316:
                if (65533 < 65536) {
                  k = i8;
                } else {
                  k = 2;
                }
                j += k;
                localUnit = Unit.INSTANCE;
                k = n + 1;
              }
              else
              {
                i1 = 0xFFFE1F80 ^ i3 ^ i1 << 6 ^ i2 << 12;
                if (i1 < 2048)
                {
                  i1 = n + 1;
                  if (n == paramInt) {
                    return j;
                  }
                  if ((65533 != 10) && (65533 != 13))
                  {
                    if (65533 >= 0) {
                      if (31 >= 65533) {
                        break label1448;
                      }
                    }
                    if (127 > 65533)
                    {
                      k = i9;
                    }
                    else
                    {
                      k = i9;
                      if (159 >= 65533) {
                        label1448:
                        k = 1;
                      }
                    }
                    if (k != 0) {}
                  }
                  else
                  {
                    if (65533 != 65533) {
                      break label1465;
                    }
                  }
                  return -1;
                  label1465:
                  if (65533 < 65536) {
                    i = 1;
                  }
                  j += i;
                  i = i1;
                }
                for (;;)
                {
                  localUnit = Unit.INSTANCE;
                  break;
                  if ((55296 <= i1) && (57343 >= i1))
                  {
                    k = n + 1;
                    if (n == paramInt) {
                      return j;
                    }
                    if ((65533 != 10) && (65533 != 13))
                    {
                      if (65533 >= 0) {
                        if (31 >= 65533) {
                          break label1575;
                        }
                      }
                      if (127 > 65533)
                      {
                        n = i7;
                      }
                      else
                      {
                        n = i7;
                        if (159 >= 65533) {
                          label1575:
                          n = 1;
                        }
                      }
                      if (n != 0) {}
                    }
                    else
                    {
                      if (65533 != 65533) {
                        break label1592;
                      }
                    }
                    return -1;
                    label1592:
                    n = i;
                    i = k;
                    if (65533 >= 65536) {
                      break label1716;
                    }
                    i = k;
                  }
                  else
                  {
                    k = n + 1;
                    if (n == paramInt) {
                      return j;
                    }
                    if ((i1 != 10) && (i1 != 13))
                    {
                      if (i1 >= 0) {
                        if (31 >= i1) {
                          break label1680;
                        }
                      }
                      if (127 > i1)
                      {
                        n = i11;
                      }
                      else
                      {
                        n = i11;
                        if (159 >= i1) {
                          label1680:
                          n = 1;
                        }
                      }
                      if (n != 0) {}
                    }
                    else
                    {
                      if (i1 != 65533) {
                        break label1697;
                      }
                    }
                    return -1;
                    label1697:
                    n = i;
                    i = k;
                    if (i1 >= 65536) {
                      break label1716;
                    }
                    i = k;
                  }
                  n = 1;
                  label1716:
                  j += n;
                }
                k = i;
                i = 3;
              }
            }
          }
          label1729:
          m += i;
          i = k;
          k = m;
        }
        else if (i24 >> 3 == -2)
        {
          if (i23 <= m + 3)
          {
            i1 = n + 1;
            if (n == paramInt) {
              return j;
            }
            if ((65533 != 10) && (65533 != 13))
            {
              if (((65533 >= 0) && (31 >= 65533)) || ((127 <= 65533) && (159 >= 65533))) {
                k = 1;
              } else {
                k = 0;
              }
              if (k != 0) {}
            }
            else
            {
              if (65533 != 65533) {
                break label1844;
              }
            }
            return -1;
            label1844:
            if (65533 < 65536) {
              k = 1;
            } else {
              k = 2;
            }
            k = j + k;
            localUnit = Unit.INSTANCE;
            if (i23 > m + 1)
            {
              if ((0xC0 & paramArrayOfByte[(m + 1)]) == 128) {
                j = 1;
              } else {
                j = 0;
              }
              if (j != 0)
              {
                if (i23 > m + 2)
                {
                  j = i10;
                  if ((0xC0 & paramArrayOfByte[(m + 2)]) == 128) {
                    j = 1;
                  }
                  if (j != 0)
                  {
                    i = 3;
                    j = k;
                    k = i1;
                    break label2917;
                  }
                }
                j = k;
                k = i1;
                break label2917;
              }
            }
            i = 1;
            j = k;
            k = i1;
          }
          else
          {
            i8 = paramArrayOfByte[m];
            i7 = paramArrayOfByte[(m + 1)];
            if ((0xC0 & i7) == 128) {
              k = 1;
            } else {
              k = 0;
            }
            if (k == 0)
            {
              if (n == paramInt) {
                return j;
              }
              if ((65533 != 10) && (65533 != 13))
              {
                if (65533 >= 0) {
                  if (31 >= 65533) {
                    break label2084;
                  }
                }
                if (127 > 65533)
                {
                  k = i21;
                }
                else
                {
                  k = i21;
                  if (159 >= 65533) {
                    label2084:
                    k = 1;
                  }
                }
                if (k != 0) {}
              }
              else
              {
                if (65533 != 65533) {
                  break label2101;
                }
              }
              return -1;
              label2101:
              if (65533 < 65536) {
                i = 1;
              }
              j += i;
              localUnit = Unit.INSTANCE;
              i = 1;
              k = n + 1;
            }
            else
            {
              i9 = paramArrayOfByte[(m + 2)];
              if ((0xC0 & i9) == 128) {
                k = 1;
              } else {
                k = 0;
              }
              if (k == 0)
              {
                if (n == paramInt) {
                  return j;
                }
                if ((65533 != 10) && (65533 != 13))
                {
                  if (65533 >= 0) {
                    if (31 >= 65533) {
                      break label2227;
                    }
                  }
                  if (127 > 65533)
                  {
                    k = i12;
                  }
                  else
                  {
                    k = i12;
                    if (159 >= 65533) {
                      label2227:
                      k = 1;
                    }
                  }
                  if (k != 0) {}
                }
                else
                {
                  if (65533 != 65533) {
                    break label2244;
                  }
                }
                return -1;
                label2244:
                if (65533 < 65536) {
                  k = i6;
                } else {
                  k = 2;
                }
                j += k;
                localUnit = Unit.INSTANCE;
                k = n + 1;
              }
              else
              {
                i6 = paramArrayOfByte[(m + 3)];
                if ((i6 & 0xC0) == 128) {
                  k = 1;
                } else {
                  k = 0;
                }
                if (k == 0)
                {
                  if (n == paramInt) {
                    return j;
                  }
                  if ((65533 != 10) && (65533 != 13))
                  {
                    if (65533 >= 0) {
                      if (31 >= 65533) {
                        break label2377;
                      }
                    }
                    if (127 > 65533)
                    {
                      k = i5;
                    }
                    else
                    {
                      k = i5;
                      if (159 >= 65533) {
                        label2377:
                        k = 1;
                      }
                    }
                    if (k != 0) {}
                  }
                  else
                  {
                    if (65533 != 65533) {
                      break label2394;
                    }
                  }
                  return -1;
                  label2394:
                  if (65533 < 65536) {
                    i = 1;
                  }
                  j += i;
                  localUnit = Unit.INSTANCE;
                  k = n + 1;
                  i = 3;
                }
                else
                {
                  i5 = 0x381F80 ^ i6 ^ i9 << 6 ^ i7 << 12 ^ i8 << 18;
                  if (i5 > 1114111)
                  {
                    i1 = n + 1;
                    if (n == paramInt) {
                      return j;
                    }
                    if ((65533 != 10) && (65533 != 13))
                    {
                      if (65533 >= 0) {
                        if (31 >= 65533) {
                          break label2524;
                        }
                      }
                      if (127 > 65533)
                      {
                        k = i4;
                      }
                      else
                      {
                        k = i4;
                        if (159 >= 65533) {
                          label2524:
                          k = 1;
                        }
                      }
                      if (k != 0) {}
                    }
                    else
                    {
                      if (65533 != 65533) {
                        break label2541;
                      }
                    }
                    return -1;
                    label2541:
                    if (65533 < 65536) {
                      i = 1;
                    }
                    j += i;
                    i = i1;
                    localUnit = Unit.INSTANCE;
                    k = i;
                  }
                  else
                  {
                    if ((55296 <= i5) && (57343 >= i5))
                    {
                      k = n + 1;
                      if (n == paramInt) {
                        return j;
                      }
                      if ((65533 != 10) && (65533 != 13))
                      {
                        if (65533 >= 0) {
                          if (31 >= 65533) {
                            break label2654;
                          }
                        }
                        if (127 > 65533)
                        {
                          n = i3;
                        }
                        else
                        {
                          n = i3;
                          if (159 >= 65533) {
                            label2654:
                            n = 1;
                          }
                        }
                        if (n != 0) {}
                      }
                      else
                      {
                        if (65533 != 65533) {
                          break label2671;
                        }
                      }
                      return -1;
                      label2671:
                      n = i;
                      i = k;
                      if (65533 < 65536) {
                        i = k;
                      }
                    }
                    for (;;)
                    {
                      label2687:
                      n = 1;
                      label2774:
                      label2791:
                      label2810:
                      label2879:
                      label2896:
                      do
                      {
                        do
                        {
                          j += n;
                          break;
                          if (i5 >= 65536) {
                            break label2810;
                          }
                          k = n + 1;
                          if (n == paramInt) {
                            return j;
                          }
                          if ((65533 != 10) && (65533 != 13))
                          {
                            if (65533 >= 0) {
                              if (31 >= 65533) {
                                break label2774;
                              }
                            }
                            if (127 > 65533)
                            {
                              n = i2;
                            }
                            else
                            {
                              n = i2;
                              if (159 >= 65533) {
                                n = 1;
                              }
                            }
                            if (n != 0) {}
                          }
                          else
                          {
                            if (65533 != 65533) {
                              break label2791;
                            }
                          }
                          return -1;
                          n = i;
                          i = k;
                        } while (65533 >= 65536);
                        i = k;
                        break label2687;
                        k = n + 1;
                        if (n == paramInt) {
                          return j;
                        }
                        if ((i5 != 10) && (i5 != 13))
                        {
                          if (i5 >= 0) {
                            if (31 >= i5) {
                              break label2879;
                            }
                          }
                          if (127 > i5)
                          {
                            n = i1;
                          }
                          else
                          {
                            n = i1;
                            if (159 >= i5) {
                              n = 1;
                            }
                          }
                          if (n != 0) {}
                        }
                        else
                        {
                          if (i5 != 65533) {
                            break label2896;
                          }
                        }
                        return -1;
                        n = i;
                        i = k;
                      } while (i5 >= 65536);
                      i = k;
                    }
                  }
                  i = 4;
                }
              }
            }
          }
          label2917:
          m += i;
          i = k;
          k = m;
        }
        else
        {
          if (n == paramInt) {
            return j;
          }
          if ((65533 != 10) && (65533 != 13))
          {
            if ((65533 < 0) || (31 < 65533))
            {
              if (127 > 65533)
              {
                k = i22;
              }
              else
              {
                k = i22;
                if (159 < 65533) {}
              }
            }
            else {
              k = 1;
            }
            if (k != 0) {}
          }
          else
          {
            if (65533 != 65533) {
              break label3010;
            }
          }
          return -1;
          label3010:
          if (65533 < 65536) {
            i = 1;
          }
          j += i;
          k = m + 1;
          i = n + 1;
        }
      }
      n = i;
    }
    return j;
  }
  
  public static final String commonBase64(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonBase64");
    paramByteString = _Base64.encodeBase64$default(paramByteString.getData$okio(), null, 1, null);
    Log5ECF72.a(paramByteString);
    LogE84000.a(paramByteString);
    Log229316.a(paramByteString);
    return paramByteString;
  }
  
  public static final String commonBase64Url(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonBase64Url");
    paramByteString = _Base64.encodeBase64(paramByteString.getData$okio(), _Base64.getBASE64_URL_SAFE());
    Log5ECF72.a(paramByteString);
    LogE84000.a(paramByteString);
    Log229316.a(paramByteString);
    return paramByteString;
  }
  
  public static final int commonCompareTo(ByteString paramByteString1, ByteString paramByteString2)
  {
    Intrinsics.checkNotNullParameter(paramByteString1, "$this$commonCompareTo");
    Intrinsics.checkNotNullParameter(paramByteString2, "other");
    int n = paramByteString1.size();
    int k = paramByteString2.size();
    int j = 0;
    int m = Math.min(n, k);
    int i;
    int i2;
    int i1;
    for (;;)
    {
      i = -1;
      if (j >= m) {
        break label92;
      }
      i2 = paramByteString1.getByte(j) & 0xFF;
      i1 = paramByteString2.getByte(j) & 0xFF;
      if (i2 != i1) {
        break;
      }
      j++;
    }
    if (i2 >= i1) {
      i = 1;
    }
    return i;
    label92:
    if (n == k) {
      return 0;
    }
    if (n >= k) {
      i = 1;
    }
    return i;
  }
  
  public static final ByteString commonDecodeBase64(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$commonDecodeBase64");
    paramString = _Base64.decodeBase64ToArray(paramString);
    if (paramString != null) {
      paramString = new ByteString(paramString);
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public static final ByteString commonDecodeHex(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$commonDecodeHex");
    int i = paramString.length();
    int j = 0;
    if (i % 2 == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      byte[] arrayOfByte = new byte[paramString.length() / 2];
      int k = arrayOfByte.length;
      for (i = j; i < k; i++) {
        arrayOfByte[i] = ((byte)((access$decodeHexDigit(paramString.charAt(i * 2)) << 4) + access$decodeHexDigit(paramString.charAt(i * 2 + 1))));
      }
      return new ByteString(arrayOfByte);
    }
    throw ((Throwable)new IllegalArgumentException(("Unexpected hex string: " + paramString).toString()));
  }
  
  public static final ByteString commonEncodeUtf8(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$commonEncodeUtf8");
    ByteString localByteString = new ByteString(_Platform.asUtf8ToByteArray(paramString));
    localByteString.setUtf8$okio(paramString);
    return localByteString;
  }
  
  public static final boolean commonEndsWith(ByteString paramByteString1, ByteString paramByteString2)
  {
    Intrinsics.checkNotNullParameter(paramByteString1, "$this$commonEndsWith");
    Intrinsics.checkNotNullParameter(paramByteString2, "suffix");
    return paramByteString1.rangeEquals(paramByteString1.size() - paramByteString2.size(), paramByteString2, 0, paramByteString2.size());
  }
  
  public static final boolean commonEndsWith(ByteString paramByteString, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonEndsWith");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "suffix");
    return paramByteString.rangeEquals(paramByteString.size() - paramArrayOfByte.length, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final boolean commonEquals(ByteString paramByteString, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonEquals");
    boolean bool = true;
    if (paramObject != paramByteString) {
      if ((paramObject instanceof ByteString))
      {
        if ((((ByteString)paramObject).size() != paramByteString.getData$okio().length) || (!((ByteString)paramObject).rangeEquals(0, paramByteString.getData$okio(), 0, paramByteString.getData$okio().length))) {
          bool = false;
        }
      }
      else {
        bool = false;
      }
    }
    return bool;
  }
  
  public static final byte commonGetByte(ByteString paramByteString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonGetByte");
    return paramByteString.getData$okio()[paramInt];
  }
  
  public static final int commonGetSize(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonGetSize");
    return paramByteString.getData$okio().length;
  }
  
  public static final int commonHashCode(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonHashCode");
    int i = paramByteString.getHashCode$okio();
    if (i != 0) {
      return i;
    }
    i = Arrays.hashCode(paramByteString.getData$okio());
    paramByteString.setHashCode$okio(i);
    return i;
  }
  
  public static final String commonHex(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonHex");
    char[] arrayOfChar = new char[paramByteString.getData$okio().length * 2];
    int j = 0;
    for (int n : paramByteString.getData$okio())
    {
      int m = j + 1;
      arrayOfChar[j] = getHEX_DIGIT_CHARS()[(n >> 4 & 0xF)];
      j = m + 1;
      arrayOfChar[m] = getHEX_DIGIT_CHARS()[(0xF & n)];
    }
    paramByteString = new String(arrayOfChar);
    Log5ECF72.a(paramByteString);
    LogE84000.a(paramByteString);
    Log229316.a(paramByteString);
    return paramByteString;
  }
  
  public static final int commonIndexOf(ByteString paramByteString, byte[] paramArrayOfByte, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonIndexOf");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "other");
    int i = paramByteString.getData$okio().length - paramArrayOfByte.length;
    paramInt = Math.max(paramInt, 0);
    if (paramInt <= i) {
      for (;;)
      {
        if (_Util.arrayRangeEquals(paramByteString.getData$okio(), paramInt, paramArrayOfByte, 0, paramArrayOfByte.length)) {
          return paramInt;
        }
        if (paramInt == i) {
          break;
        }
        paramInt++;
      }
    }
    return -1;
  }
  
  public static final byte[] commonInternalArray(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonInternalArray");
    return paramByteString.getData$okio();
  }
  
  public static final int commonLastIndexOf(ByteString paramByteString1, ByteString paramByteString2, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramByteString1, "$this$commonLastIndexOf");
    Intrinsics.checkNotNullParameter(paramByteString2, "other");
    return paramByteString1.lastIndexOf(paramByteString2.internalArray$okio(), paramInt);
  }
  
  public static final int commonLastIndexOf(ByteString paramByteString, byte[] paramArrayOfByte, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonLastIndexOf");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "other");
    for (paramInt = Math.min(paramInt, paramByteString.getData$okio().length - paramArrayOfByte.length); paramInt >= 0; paramInt--) {
      if (_Util.arrayRangeEquals(paramByteString.getData$okio(), paramInt, paramArrayOfByte, 0, paramArrayOfByte.length)) {
        return paramInt;
      }
    }
    return -1;
  }
  
  public static final ByteString commonOf(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "data");
    paramArrayOfByte = Arrays.copyOf(paramArrayOfByte, paramArrayOfByte.length);
    Intrinsics.checkNotNullExpressionValue(paramArrayOfByte, "java.util.Arrays.copyOf(this, size)");
    return new ByteString(paramArrayOfByte);
  }
  
  public static final boolean commonRangeEquals(ByteString paramByteString1, int paramInt1, ByteString paramByteString2, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramByteString1, "$this$commonRangeEquals");
    Intrinsics.checkNotNullParameter(paramByteString2, "other");
    return paramByteString2.rangeEquals(paramInt2, paramByteString1.getData$okio(), paramInt1, paramInt3);
  }
  
  public static final boolean commonRangeEquals(ByteString paramByteString, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonRangeEquals");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "other");
    boolean bool;
    if ((paramInt1 >= 0) && (paramInt1 <= paramByteString.getData$okio().length - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3) && (_Util.arrayRangeEquals(paramByteString.getData$okio(), paramInt1, paramArrayOfByte, paramInt2, paramInt3))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean commonStartsWith(ByteString paramByteString1, ByteString paramByteString2)
  {
    Intrinsics.checkNotNullParameter(paramByteString1, "$this$commonStartsWith");
    Intrinsics.checkNotNullParameter(paramByteString2, "prefix");
    return paramByteString1.rangeEquals(0, paramByteString2, 0, paramByteString2.size());
  }
  
  public static final boolean commonStartsWith(ByteString paramByteString, byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonStartsWith");
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "prefix");
    return paramByteString.rangeEquals(0, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final ByteString commonSubstring(ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonSubstring");
    int j = 1;
    int i;
    if (paramInt1 >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt2 <= paramByteString.getData$okio().length) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt2 - paramInt1 >= 0) {
          i = j;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          if ((paramInt1 == 0) && (paramInt2 == paramByteString.getData$okio().length)) {
            return paramByteString;
          }
          return new ByteString(ArraysKt.copyOfRange(paramByteString.getData$okio(), paramInt1, paramInt2));
        }
        throw ((Throwable)new IllegalArgumentException("endIndex < beginIndex".toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("endIndex > length(" + paramByteString.getData$okio().length + ')').toString()));
    }
    throw ((Throwable)new IllegalArgumentException("beginIndex < 0".toString()));
  }
  
  public static final ByteString commonToAsciiLowercase(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonToAsciiLowercase");
    for (int i = 0; i < paramByteString.getData$okio().length; i++)
    {
      int n = paramByteString.getData$okio()[i];
      int m = (byte)65;
      if (n >= m)
      {
        int k = (byte)90;
        if (n <= k)
        {
          paramByteString = paramByteString.getData$okio();
          paramByteString = Arrays.copyOf(paramByteString, paramByteString.length);
          Intrinsics.checkNotNullExpressionValue(paramByteString, "java.util.Arrays.copyOf(this, size)");
          int j = i + 1;
          paramByteString[i] = ((byte)(n + 32));
          i = j;
          while (i < paramByteString.length)
          {
            j = paramByteString[i];
            if ((j >= m) && (j <= k))
            {
              paramByteString[i] = ((byte)(j + 32));
              i++;
            }
            else
            {
              i++;
            }
          }
          return new ByteString(paramByteString);
        }
      }
    }
    return paramByteString;
  }
  
  public static final ByteString commonToAsciiUppercase(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonToAsciiUppercase");
    for (int i = 0; i < paramByteString.getData$okio().length; i++)
    {
      int n = paramByteString.getData$okio()[i];
      int k = (byte)97;
      if (n >= k)
      {
        int m = (byte)122;
        if (n <= m)
        {
          paramByteString = paramByteString.getData$okio();
          paramByteString = Arrays.copyOf(paramByteString, paramByteString.length);
          Intrinsics.checkNotNullExpressionValue(paramByteString, "java.util.Arrays.copyOf(this, size)");
          int j = i + 1;
          paramByteString[i] = ((byte)(n - 32));
          i = j;
          while (i < paramByteString.length)
          {
            j = paramByteString[i];
            if ((j >= k) && (j <= m))
            {
              paramByteString[i] = ((byte)(j - 32));
              i++;
            }
            else
            {
              i++;
            }
          }
          return new ByteString(paramByteString);
        }
      }
    }
    return paramByteString;
  }
  
  public static final byte[] commonToByteArray(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonToByteArray");
    paramByteString = paramByteString.getData$okio();
    paramByteString = Arrays.copyOf(paramByteString, paramByteString.length);
    Intrinsics.checkNotNullExpressionValue(paramByteString, "java.util.Arrays.copyOf(this, size)");
    return paramByteString;
  }
  
  public static final ByteString commonToByteString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$commonToByteString");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    return new ByteString(ArraysKt.copyOfRange(paramArrayOfByte, paramInt1, paramInt1 + paramInt2));
  }
  
  public static final String commonToString(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonToString");
    int i = paramByteString.getData$okio().length;
    int j = 1;
    if (i == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return "[size=0]";
    }
    i = access$codePointIndexToCharIndex(paramByteString.getData$okio(), 64);
    if (i == -1)
    {
      if (paramByteString.getData$okio().length <= 64)
      {
        paramByteString = "[hex=" + paramByteString.hex() + ']';
      }
      else
      {
        localObject = new StringBuilder().append("[size=").append(paramByteString.getData$okio().length).append(" hex=");
        if (64 <= paramByteString.getData$okio().length) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          break label226;
        }
        if (64 - 0 >= 0) {
          i = j;
        } else {
          i = 0;
        }
        if (i == 0) {
          break label209;
        }
        if (64 != paramByteString.getData$okio().length) {
          paramByteString = new ByteString(ArraysKt.copyOfRange(paramByteString.getData$okio(), 0, 64));
        }
        paramByteString = paramByteString.hex() + "…]";
      }
      return paramByteString;
      label209:
      throw ((Throwable)new IllegalArgumentException("endIndex < beginIndex".toString()));
      label226:
      throw ((Throwable)new IllegalArgumentException(("endIndex > length(" + paramByteString.getData$okio().length + ')').toString()));
    }
    Object localObject = paramByteString.utf8();
    if (localObject != null)
    {
      String str = ((String)localObject).substring(0, i);
      Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      str = StringsKt.replace$default(str, "\\", "\\\\", false, 4, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      str = StringsKt.replace$default(str, "\n", "\\n", false, 4, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      str = StringsKt.replace$default(str, "\r", "\\r", false, 4, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (i < ((String)localObject).length()) {
        paramByteString = "[size=" + paramByteString.getData$okio().length + " text=" + str + "…]";
      } else {
        paramByteString = "[text=" + str + ']';
      }
      return paramByteString;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
  }
  
  public static final String commonUtf8(ByteString paramByteString)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonUtf8");
    String str2 = paramByteString.getUtf8$okio();
    String str1 = str2;
    if (str2 == null)
    {
      str1 = _Platform.toUtf8String(paramByteString.internalArray$okio());
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
      paramByteString.setUtf8$okio(str1);
    }
    return str1;
  }
  
  public static final void commonWrite(ByteString paramByteString, Buffer paramBuffer, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramByteString, "$this$commonWrite");
    Intrinsics.checkNotNullParameter(paramBuffer, "buffer");
    paramBuffer.write(paramByteString.getData$okio(), paramInt1, paramInt2);
  }
  
  private static final int decodeHexDigit(char paramChar)
  {
    int i;
    if (('0' <= paramChar) && ('9' >= paramChar))
    {
      i = paramChar - '0';
    }
    else if (('a' <= paramChar) && ('f' >= paramChar))
    {
      i = paramChar - 'a' + 10;
    }
    else
    {
      if (('A' > paramChar) || ('F' < paramChar)) {
        break label71;
      }
      i = paramChar - 'A' + 10;
    }
    return i;
    label71:
    throw ((Throwable)new IllegalArgumentException("Unexpected hex digit: " + paramChar));
  }
  
  public static final char[] getHEX_DIGIT_CHARS()
  {
    return HEX_DIGIT_CHARS;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/internal/ByteStringKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */