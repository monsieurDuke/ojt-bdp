package okio;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000D\n\000\n\002\020\b\n\002\b\005\n\002\020\005\n\000\n\002\020\f\n\002\b\002\n\002\020\013\n\002\b\004\n\002\020\022\n\002\b\003\n\002\030\002\n\002\020\002\n\002\b\004\n\002\020\016\n\002\b\002\n\002\020\t\n\002\b\002\032\021\020\013\032\0020\f2\006\020\r\032\0020\001H\b\032\021\020\016\032\0020\f2\006\020\017\032\0020\007H\b\0324\020\020\032\0020\001*\0020\0212\006\020\022\032\0020\0012\006\020\023\032\0020\0012\022\020\024\032\016\022\004\022\0020\001\022\004\022\0020\0260\025H\bø\001\000\0324\020\027\032\0020\001*\0020\0212\006\020\022\032\0020\0012\006\020\023\032\0020\0012\022\020\024\032\016\022\004\022\0020\001\022\004\022\0020\0260\025H\bø\001\000\0324\020\030\032\0020\001*\0020\0212\006\020\022\032\0020\0012\006\020\023\032\0020\0012\022\020\024\032\016\022\004\022\0020\001\022\004\022\0020\0260\025H\bø\001\000\0324\020\031\032\0020\026*\0020\0212\006\020\022\032\0020\0012\006\020\023\032\0020\0012\022\020\024\032\016\022\004\022\0020\t\022\004\022\0020\0260\025H\bø\001\000\0324\020\032\032\0020\026*\0020\0332\006\020\022\032\0020\0012\006\020\023\032\0020\0012\022\020\024\032\016\022\004\022\0020\007\022\004\022\0020\0260\025H\bø\001\000\0324\020\034\032\0020\026*\0020\0212\006\020\022\032\0020\0012\006\020\023\032\0020\0012\022\020\024\032\016\022\004\022\0020\001\022\004\022\0020\0260\025H\bø\001\000\032%\020\035\032\0020\036*\0020\0332\b\b\002\020\022\032\0020\0012\b\b\002\020\023\032\0020\001H\007¢\006\002\b\037\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\016\020\002\032\0020\001XT¢\006\002\n\000\"\016\020\003\032\0020\001XT¢\006\002\n\000\"\016\020\004\032\0020\001XT¢\006\002\n\000\"\016\020\005\032\0020\001XT¢\006\002\n\000\"\016\020\006\032\0020\007XT¢\006\002\n\000\"\016\020\b\032\0020\tXT¢\006\002\n\000\"\016\020\n\032\0020\001XT¢\006\002\n\000\002\007\n\005\b20\001¨\006 "}, d2={"HIGH_SURROGATE_HEADER", "", "LOG_SURROGATE_HEADER", "MASK_2BYTES", "MASK_3BYTES", "MASK_4BYTES", "REPLACEMENT_BYTE", "", "REPLACEMENT_CHARACTER", "", "REPLACEMENT_CODE_POINT", "isIsoControl", "", "codePoint", "isUtf8Continuation", "byte", "process2Utf8Bytes", "", "beginIndex", "endIndex", "yield", "Lkotlin/Function1;", "", "process3Utf8Bytes", "process4Utf8Bytes", "processUtf16Chars", "processUtf8Bytes", "", "processUtf8CodePoints", "utf8Size", "", "size", "okio"}, k=2, mv={1, 4, 0})
public final class Utf8
{
  public static final int HIGH_SURROGATE_HEADER = 55232;
  public static final int LOG_SURROGATE_HEADER = 56320;
  public static final int MASK_2BYTES = 3968;
  public static final int MASK_3BYTES = -123008;
  public static final int MASK_4BYTES = 3678080;
  public static final byte REPLACEMENT_BYTE = 63;
  public static final char REPLACEMENT_CHARACTER = '�';
  public static final int REPLACEMENT_CODE_POINT = 65533;
  
  public static final boolean isIsoControl(int paramInt)
  {
    boolean bool;
    if (((paramInt >= 0) && (31 >= paramInt)) || ((127 <= paramInt) && (159 >= paramInt))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isUtf8Continuation(byte paramByte)
  {
    boolean bool;
    if ((0xC0 & paramByte) == 128) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final int process2Utf8Bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Function1<? super Integer, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$process2Utf8Bytes");
    Intrinsics.checkNotNullParameter(paramFunction1, "yield");
    Integer localInteger = Integer.valueOf(65533);
    if (paramInt2 <= paramInt1 + 1)
    {
      paramFunction1.invoke(localInteger);
      return 1;
    }
    paramInt2 = paramArrayOfByte[paramInt1];
    int i = paramArrayOfByte[(paramInt1 + 1)];
    if ((0xC0 & i) == 128) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if (paramInt1 == 0)
    {
      paramFunction1.invoke(localInteger);
      return 1;
    }
    paramInt1 = i ^ 0xF80 ^ paramInt2 << 6;
    if (paramInt1 < 128) {
      paramFunction1.invoke(localInteger);
    } else {
      paramFunction1.invoke(Integer.valueOf(paramInt1));
    }
    return 2;
  }
  
  public static final int process3Utf8Bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Function1<? super Integer, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$process3Utf8Bytes");
    Intrinsics.checkNotNullParameter(paramFunction1, "yield");
    int i = 0;
    int j = 0;
    Integer localInteger = Integer.valueOf(65533);
    if (paramInt2 <= paramInt1 + 2)
    {
      paramFunction1.invoke(localInteger);
      if (paramInt2 > paramInt1 + 1)
      {
        paramInt2 = j;
        if ((0xC0 & paramArrayOfByte[(paramInt1 + 1)]) == 128) {
          paramInt2 = 1;
        }
        if (paramInt2 != 0) {
          return 2;
        }
      }
      return 1;
    }
    j = paramArrayOfByte[paramInt1];
    int k = paramArrayOfByte[(paramInt1 + 1)];
    if ((0xC0 & k) == 128) {
      paramInt2 = 1;
    } else {
      paramInt2 = 0;
    }
    if (paramInt2 == 0)
    {
      paramFunction1.invoke(localInteger);
      return 1;
    }
    paramInt2 = paramArrayOfByte[(paramInt1 + 2)];
    paramInt1 = i;
    if ((0xC0 & paramInt2) == 128) {
      paramInt1 = 1;
    }
    if (paramInt1 == 0)
    {
      paramFunction1.invoke(localInteger);
      return 2;
    }
    paramInt1 = 0xFFFE1F80 ^ paramInt2 ^ k << 6 ^ j << 12;
    if (paramInt1 < 2048) {
      paramFunction1.invoke(localInteger);
    } else if ((55296 <= paramInt1) && (57343 >= paramInt1)) {
      paramFunction1.invoke(localInteger);
    } else {
      paramFunction1.invoke(Integer.valueOf(paramInt1));
    }
    return 3;
  }
  
  public static final int process4Utf8Bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Function1<? super Integer, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$process4Utf8Bytes");
    Intrinsics.checkNotNullParameter(paramFunction1, "yield");
    int i = 0;
    int j = 0;
    Integer localInteger = Integer.valueOf(65533);
    if (paramInt2 <= paramInt1 + 3)
    {
      paramFunction1.invoke(localInteger);
      if (paramInt2 > paramInt1 + 1)
      {
        if ((0xC0 & paramArrayOfByte[(paramInt1 + 1)]) == 128) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          if (paramInt2 > paramInt1 + 2)
          {
            paramInt2 = j;
            if ((0xC0 & paramArrayOfByte[(paramInt1 + 2)]) == 128) {
              paramInt2 = 1;
            }
            if (paramInt2 != 0) {
              return 3;
            }
          }
          return 2;
        }
      }
      return 1;
    }
    j = paramArrayOfByte[paramInt1];
    int k = paramArrayOfByte[(paramInt1 + 1)];
    if ((0xC0 & k) == 128) {
      paramInt2 = 1;
    } else {
      paramInt2 = 0;
    }
    if (paramInt2 == 0)
    {
      paramFunction1.invoke(localInteger);
      return 1;
    }
    int m = paramArrayOfByte[(paramInt1 + 2)];
    if ((0xC0 & m) == 128) {
      paramInt2 = 1;
    } else {
      paramInt2 = 0;
    }
    if (paramInt2 == 0)
    {
      paramFunction1.invoke(localInteger);
      return 2;
    }
    paramInt2 = paramArrayOfByte[(paramInt1 + 3)];
    paramInt1 = i;
    if ((0xC0 & paramInt2) == 128) {
      paramInt1 = 1;
    }
    if (paramInt1 == 0)
    {
      paramFunction1.invoke(localInteger);
      return 3;
    }
    paramInt1 = 0x381F80 ^ paramInt2 ^ m << 6 ^ k << 12 ^ j << 18;
    if (paramInt1 > 1114111) {
      paramFunction1.invoke(localInteger);
    } else if ((55296 <= paramInt1) && (57343 >= paramInt1)) {
      paramFunction1.invoke(localInteger);
    } else if (paramInt1 < 65536) {
      paramFunction1.invoke(localInteger);
    } else {
      paramFunction1.invoke(Integer.valueOf(paramInt1));
    }
    return 4;
  }
  
  public static final void processUtf16Chars(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Function1<? super Character, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$processUtf16Chars");
    Intrinsics.checkNotNullParameter(paramFunction1, "yield");
    for (int i = paramInt1; i < paramInt2; i = paramInt1)
    {
      paramInt1 = paramArrayOfByte[i];
      if (paramInt1 >= 0)
      {
        paramFunction1.invoke(Character.valueOf((char)paramInt1));
        i++;
        for (;;)
        {
          paramInt1 = i;
          if (i >= paramInt2) {
            break;
          }
          paramInt1 = i;
          if (paramArrayOfByte[i] < 0) {
            break;
          }
          paramFunction1.invoke(Character.valueOf((char)paramArrayOfByte[i]));
          i++;
        }
      }
      Unit localUnit;
      int k;
      int j;
      if (paramInt1 >> 5 == -2)
      {
        if (paramInt2 <= i + 1)
        {
          paramFunction1.invoke(Character.valueOf((char)65533));
          localUnit = Unit.INSTANCE;
          paramInt1 = 1;
        }
        else
        {
          k = paramArrayOfByte[i];
          j = paramArrayOfByte[(i + 1)];
          if ((j & 0xC0) == 128) {
            paramInt1 = 1;
          } else {
            paramInt1 = 0;
          }
          if (paramInt1 == 0)
          {
            paramFunction1.invoke(Character.valueOf((char)65533));
            localUnit = Unit.INSTANCE;
            paramInt1 = 1;
          }
          else
          {
            paramInt1 = j ^ 0xF80 ^ k << 6;
            if (paramInt1 < 128) {
              paramInt1 = 65533;
            }
            paramFunction1.invoke(Character.valueOf((char)paramInt1));
            localUnit = Unit.INSTANCE;
            paramInt1 = 2;
          }
        }
        paramInt1 = i + paramInt1;
      }
      else
      {
        int m;
        if (paramInt1 >> 4 == -2)
        {
          if (paramInt2 <= i + 2)
          {
            paramFunction1.invoke(Character.valueOf((char)65533));
            localUnit = Unit.INSTANCE;
            if (paramInt2 > i + 1)
            {
              if ((0xC0 & paramArrayOfByte[(i + 1)]) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 != 0)
              {
                paramInt1 = 2;
                break label507;
              }
            }
            paramInt1 = 1;
          }
          else
          {
            k = paramArrayOfByte[i];
            j = paramArrayOfByte[(i + 1)];
            if ((j & 0xC0) == 128) {
              paramInt1 = 1;
            } else {
              paramInt1 = 0;
            }
            if (paramInt1 == 0)
            {
              paramFunction1.invoke(Character.valueOf((char)65533));
              localUnit = Unit.INSTANCE;
              paramInt1 = 1;
            }
            else
            {
              m = paramArrayOfByte[(i + 2)];
              if ((m & 0xC0) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 == 0)
              {
                paramFunction1.invoke(Character.valueOf((char)65533));
                localUnit = Unit.INSTANCE;
                paramInt1 = 2;
              }
              else
              {
                paramInt1 = 0xFFFE1F80 ^ m ^ j << 6 ^ k << 12;
                if (paramInt1 < 2048) {
                  paramInt1 = 65533;
                }
                for (;;)
                {
                  paramFunction1.invoke(Character.valueOf((char)paramInt1));
                  localUnit = Unit.INSTANCE;
                  break;
                  if ((55296 <= paramInt1) && (57343 >= paramInt1)) {
                    paramInt1 = 65533;
                  }
                }
                paramInt1 = 3;
              }
            }
          }
          label507:
          paramInt1 = i + paramInt1;
        }
        else if (paramInt1 >> 3 == -2)
        {
          if (paramInt2 <= i + 3)
          {
            if (65533 != 65533)
            {
              paramFunction1.invoke(Character.valueOf((char)((65533 >>> 10) + 55232)));
              paramFunction1.invoke(Character.valueOf((char)((0xFFFD & 0x3FF) + 56320)));
            }
            else
            {
              paramFunction1.invoke(Character.valueOf(65533));
            }
            localUnit = Unit.INSTANCE;
            if (paramInt2 > i + 1)
            {
              if ((0xC0 & paramArrayOfByte[(i + 1)]) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 != 0)
              {
                if (paramInt2 > i + 2)
                {
                  if ((0xC0 & paramArrayOfByte[(i + 2)]) == 128) {
                    paramInt1 = 1;
                  } else {
                    paramInt1 = 0;
                  }
                  if (paramInt1 != 0)
                  {
                    paramInt1 = 3;
                    break label1158;
                  }
                }
                paramInt1 = 2;
                break label1158;
              }
            }
            paramInt1 = 1;
          }
          else
          {
            k = paramArrayOfByte[i];
            j = paramArrayOfByte[(i + 1)];
            if ((j & 0xC0) == 128) {
              paramInt1 = 1;
            } else {
              paramInt1 = 0;
            }
            if (paramInt1 == 0)
            {
              if (65533 != 65533)
              {
                paramFunction1.invoke(Character.valueOf((char)((65533 >>> 10) + 55232)));
                paramFunction1.invoke(Character.valueOf((char)((0xFFFD & 0x3FF) + 56320)));
              }
              else
              {
                paramFunction1.invoke(Character.valueOf(65533));
              }
              localUnit = Unit.INSTANCE;
              paramInt1 = 1;
            }
            else
            {
              m = paramArrayOfByte[(i + 2)];
              if ((m & 0xC0) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 == 0)
              {
                if (65533 != 65533)
                {
                  paramFunction1.invoke(Character.valueOf((char)((65533 >>> 10) + 55232)));
                  paramFunction1.invoke(Character.valueOf((char)((0xFFFD & 0x3FF) + 56320)));
                }
                else
                {
                  paramFunction1.invoke(Character.valueOf(65533));
                }
                localUnit = Unit.INSTANCE;
                paramInt1 = 2;
              }
              else
              {
                int n = paramArrayOfByte[(i + 3)];
                if ((n & 0xC0) == 128) {
                  paramInt1 = 1;
                } else {
                  paramInt1 = 0;
                }
                if (paramInt1 == 0)
                {
                  if (65533 != 65533)
                  {
                    paramFunction1.invoke(Character.valueOf((char)((65533 >>> 10) + 55232)));
                    paramFunction1.invoke(Character.valueOf((char)((0xFFFD & 0x3FF) + 56320)));
                  }
                  else
                  {
                    paramFunction1.invoke(Character.valueOf(65533));
                  }
                  localUnit = Unit.INSTANCE;
                  paramInt1 = 3;
                }
                else
                {
                  paramInt1 = 0x381F80 ^ n ^ m << 6 ^ j << 12 ^ k << 18;
                  if (paramInt1 > 1114111)
                  {
                    paramInt1 = 65533;
                    if (65533 == 65533) {}
                  }
                  for (;;)
                  {
                    paramFunction1.invoke(Character.valueOf((char)((paramInt1 >>> 10) + 55232)));
                    paramFunction1.invoke(Character.valueOf((char)((paramInt1 & 0x3FF) + 56320)));
                    label1147:
                    do
                    {
                      do
                      {
                        do
                        {
                          paramFunction1.invoke(Character.valueOf(65533));
                          localUnit = Unit.INSTANCE;
                          break label1156;
                          if ((55296 > paramInt1) || (57343 < paramInt1)) {
                            break;
                          }
                          paramInt1 = 65533;
                        } while (65533 == 65533);
                        break;
                        if (paramInt1 >= 65536) {
                          break label1147;
                        }
                        paramInt1 = 65533;
                      } while (65533 == 65533);
                      break;
                    } while (paramInt1 == 65533);
                  }
                  label1156:
                  paramInt1 = 4;
                }
              }
            }
          }
          label1158:
          paramInt1 = i + paramInt1;
        }
        else
        {
          paramFunction1.invoke(Character.valueOf(65533));
          paramInt1 = i + 1;
        }
      }
    }
  }
  
  public static final void processUtf8Bytes(String paramString, int paramInt1, int paramInt2, Function1<? super Byte, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$processUtf8Bytes");
    Intrinsics.checkNotNullParameter(paramFunction1, "yield");
    label414:
    while (paramInt1 < paramInt2)
    {
      int i = paramString.charAt(paramInt1);
      if (Intrinsics.compare(i, 128) < 0)
      {
        paramFunction1.invoke(Byte.valueOf((byte)i));
        for (i = paramInt1 + 1;; i++)
        {
          paramInt1 = i;
          if (i >= paramInt2) {
            break;
          }
          paramInt1 = i;
          if (Intrinsics.compare(paramString.charAt(i), 128) >= 0) {
            break;
          }
          paramFunction1.invoke(Byte.valueOf((byte)paramString.charAt(i)));
        }
      }
      if (Intrinsics.compare(i, 2048) < 0)
      {
        paramFunction1.invoke(Byte.valueOf((byte)(i >> 6 | 0xC0)));
        paramFunction1.invoke(Byte.valueOf((byte)(0x80 | i & 0x3F)));
        paramInt1++;
      }
      else if ((55296 <= i) && (57343 >= i))
      {
        if ((Intrinsics.compare(i, 56319) <= 0) && (paramInt2 > paramInt1 + 1))
        {
          int j = paramString.charAt(paramInt1 + 1);
          if ((56320 <= j) && (57343 >= j))
          {
            i = (i << 10) + paramString.charAt(paramInt1 + 1) - 56613888;
            paramFunction1.invoke(Byte.valueOf((byte)(i >> 18 | 0xF0)));
            paramFunction1.invoke(Byte.valueOf((byte)(i >> 12 & 0x3F | 0x80)));
            paramFunction1.invoke(Byte.valueOf((byte)(0x3F & i >> 6 | 0x80)));
            paramFunction1.invoke(Byte.valueOf((byte)(0x80 | i & 0x3F)));
            paramInt1 += 2;
            break label414;
          }
        }
        paramFunction1.invoke(Byte.valueOf((byte)63));
        paramInt1++;
      }
      else
      {
        paramFunction1.invoke(Byte.valueOf((byte)(i >> 12 | 0xE0)));
        paramFunction1.invoke(Byte.valueOf((byte)(i >> 6 & 0x3F | 0x80)));
        paramFunction1.invoke(Byte.valueOf((byte)(0x80 | i & 0x3F)));
        paramInt1++;
      }
    }
  }
  
  public static final void processUtf8CodePoints(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Function1<? super Integer, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$processUtf8CodePoints");
    Intrinsics.checkNotNullParameter(paramFunction1, "yield");
    for (int i = paramInt1; i < paramInt2; i = paramInt1)
    {
      paramInt1 = paramArrayOfByte[i];
      if (paramInt1 >= 0)
      {
        paramFunction1.invoke(Integer.valueOf(paramInt1));
        i++;
        for (;;)
        {
          paramInt1 = i;
          if (i >= paramInt2) {
            break;
          }
          paramInt1 = i;
          if (paramArrayOfByte[i] < 0) {
            break;
          }
          paramFunction1.invoke(Integer.valueOf(paramArrayOfByte[i]));
          i++;
        }
      }
      Unit localUnit;
      int k;
      int j;
      if (paramInt1 >> 5 == -2)
      {
        if (paramInt2 <= i + 1)
        {
          paramFunction1.invoke(Integer.valueOf(65533));
          localUnit = Unit.INSTANCE;
          paramInt1 = 1;
        }
        else
        {
          k = paramArrayOfByte[i];
          j = paramArrayOfByte[(i + 1)];
          if ((j & 0xC0) == 128) {
            paramInt1 = 1;
          } else {
            paramInt1 = 0;
          }
          if (paramInt1 == 0)
          {
            paramFunction1.invoke(Integer.valueOf(65533));
            localUnit = Unit.INSTANCE;
            paramInt1 = 1;
          }
          else
          {
            paramInt1 = j ^ 0xF80 ^ k << 6;
            if (paramInt1 < 128) {
              paramInt1 = 65533;
            }
            paramFunction1.invoke(Integer.valueOf(paramInt1));
            localUnit = Unit.INSTANCE;
            paramInt1 = 2;
          }
        }
        paramInt1 = i + paramInt1;
      }
      else
      {
        int m;
        if (paramInt1 >> 4 == -2)
        {
          if (paramInt2 <= i + 2)
          {
            paramFunction1.invoke(Integer.valueOf(65533));
            localUnit = Unit.INSTANCE;
            if (paramInt2 > i + 1)
            {
              if ((0xC0 & paramArrayOfByte[(i + 1)]) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 != 0)
              {
                paramInt1 = 2;
                break label498;
              }
            }
            paramInt1 = 1;
          }
          else
          {
            j = paramArrayOfByte[i];
            k = paramArrayOfByte[(i + 1)];
            if ((k & 0xC0) == 128) {
              paramInt1 = 1;
            } else {
              paramInt1 = 0;
            }
            if (paramInt1 == 0)
            {
              paramFunction1.invoke(Integer.valueOf(65533));
              localUnit = Unit.INSTANCE;
              paramInt1 = 1;
            }
            else
            {
              m = paramArrayOfByte[(i + 2)];
              if ((m & 0xC0) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 == 0)
              {
                paramFunction1.invoke(Integer.valueOf(65533));
                localUnit = Unit.INSTANCE;
                paramInt1 = 2;
              }
              else
              {
                paramInt1 = 0xFFFE1F80 ^ m ^ k << 6 ^ j << 12;
                if (paramInt1 < 2048) {
                  paramInt1 = 65533;
                }
                for (;;)
                {
                  paramFunction1.invoke(Integer.valueOf(paramInt1));
                  localUnit = Unit.INSTANCE;
                  break;
                  if ((55296 <= paramInt1) && (57343 >= paramInt1)) {
                    paramInt1 = 65533;
                  }
                }
                paramInt1 = 3;
              }
            }
          }
          label498:
          paramInt1 = i + paramInt1;
        }
        else if (paramInt1 >> 3 == -2)
        {
          if (paramInt2 <= i + 3)
          {
            paramFunction1.invoke(Integer.valueOf(65533));
            localUnit = Unit.INSTANCE;
            if (paramInt2 > i + 1)
            {
              if ((0xC0 & paramArrayOfByte[(i + 1)]) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 != 0)
              {
                if (paramInt2 > i + 2)
                {
                  if ((0xC0 & paramArrayOfByte[(i + 2)]) == 128) {
                    paramInt1 = 1;
                  } else {
                    paramInt1 = 0;
                  }
                  if (paramInt1 != 0)
                  {
                    paramInt1 = 3;
                    break label885;
                  }
                }
                paramInt1 = 2;
                break label885;
              }
            }
            paramInt1 = 1;
          }
          else
          {
            j = paramArrayOfByte[i];
            k = paramArrayOfByte[(i + 1)];
            if ((k & 0xC0) == 128) {
              paramInt1 = 1;
            } else {
              paramInt1 = 0;
            }
            if (paramInt1 == 0)
            {
              paramFunction1.invoke(Integer.valueOf(65533));
              localUnit = Unit.INSTANCE;
              paramInt1 = 1;
            }
            else
            {
              m = paramArrayOfByte[(i + 2)];
              if ((m & 0xC0) == 128) {
                paramInt1 = 1;
              } else {
                paramInt1 = 0;
              }
              if (paramInt1 == 0)
              {
                paramFunction1.invoke(Integer.valueOf(65533));
                localUnit = Unit.INSTANCE;
                paramInt1 = 2;
              }
              else
              {
                int n = paramArrayOfByte[(i + 3)];
                if ((n & 0xC0) == 128) {
                  paramInt1 = 1;
                } else {
                  paramInt1 = 0;
                }
                if (paramInt1 == 0)
                {
                  paramFunction1.invoke(Integer.valueOf(65533));
                  localUnit = Unit.INSTANCE;
                  paramInt1 = 3;
                }
                else
                {
                  paramInt1 = 0x381F80 ^ n ^ m << 6 ^ k << 12 ^ j << 18;
                  if (paramInt1 > 1114111) {
                    paramInt1 = 65533;
                  }
                  for (;;)
                  {
                    paramFunction1.invoke(Integer.valueOf(paramInt1));
                    localUnit = Unit.INSTANCE;
                    break;
                    if ((55296 <= paramInt1) && (57343 >= paramInt1)) {
                      paramInt1 = 65533;
                    } else if (paramInt1 < 65536) {
                      paramInt1 = 65533;
                    }
                  }
                  paramInt1 = 4;
                }
              }
            }
          }
          label885:
          paramInt1 = i + paramInt1;
        }
        else
        {
          paramFunction1.invoke(Integer.valueOf(65533));
          paramInt1 = i + 1;
        }
      }
    }
  }
  
  public static final long size(String paramString)
  {
    return size$default(paramString, 0, 0, 3, null);
  }
  
  public static final long size(String paramString, int paramInt)
  {
    return size$default(paramString, paramInt, 0, 2, null);
  }
  
  public static final long size(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$utf8Size");
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
          long l = 0L;
          while (paramInt1 < paramInt2)
          {
            j = paramString.charAt(paramInt1);
            if (j < 128)
            {
              l += 1L;
              paramInt1++;
            }
            else if (j < 2048)
            {
              l += 2;
              paramInt1++;
            }
            else if ((j >= 55296) && (j <= 57343))
            {
              if (paramInt1 + 1 < paramInt2) {
                i = paramString.charAt(paramInt1 + 1);
              } else {
                i = 0;
              }
              if ((j <= 56319) && (i >= 56320) && (i <= 57343))
              {
                l += 4;
                paramInt1 += 2;
              }
              else
              {
                l += 1L;
                paramInt1++;
              }
            }
            else
            {
              l += 3;
              paramInt1++;
            }
          }
          return l;
        }
        throw ((Throwable)new IllegalArgumentException(("endIndex > string.length: " + paramInt2 + " > " + paramString.length()).toString()));
      }
      throw ((Throwable)new IllegalArgumentException(("endIndex < beginIndex: " + paramInt2 + " < " + paramInt1).toString()));
    }
    throw ((Throwable)new IllegalArgumentException(("beginIndex < 0: " + paramInt1).toString()));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/Utf8.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */