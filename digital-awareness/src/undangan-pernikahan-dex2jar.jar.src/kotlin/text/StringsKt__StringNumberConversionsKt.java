package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000.\n\000\n\002\020\001\n\000\n\002\020\016\n\000\n\002\020\005\n\002\b\002\n\002\020\b\n\002\b\005\n\002\020\t\n\002\b\003\n\002\020\n\n\002\b\003\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\000\032\023\020\004\032\004\030\0010\005*\0020\003H\007¢\006\002\020\006\032\033\020\004\032\004\030\0010\005*\0020\0032\006\020\007\032\0020\bH\007¢\006\002\020\t\032\023\020\n\032\004\030\0010\b*\0020\003H\007¢\006\002\020\013\032\033\020\n\032\004\030\0010\b*\0020\0032\006\020\007\032\0020\bH\007¢\006\002\020\f\032\023\020\r\032\004\030\0010\016*\0020\003H\007¢\006\002\020\017\032\033\020\r\032\004\030\0010\016*\0020\0032\006\020\007\032\0020\bH\007¢\006\002\020\020\032\023\020\021\032\004\030\0010\022*\0020\003H\007¢\006\002\020\023\032\033\020\021\032\004\030\0010\022*\0020\0032\006\020\007\032\0020\bH\007¢\006\002\020\024¨\006\025"}, d2={"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsKt
  extends StringsKt__StringNumberConversionsJVMKt
{
  public static final Void numberFormatError(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "input");
    throw new NumberFormatException("Invalid number format: '" + paramString + '\'');
  }
  
  public static final Byte toByteOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.toByteOrNull(paramString, 10);
  }
  
  public static final Byte toByteOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = StringsKt.toIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.intValue();
      if ((paramInt >= -128) && (paramInt <= 127)) {
        return Byte.valueOf((byte)paramInt);
      }
      return null;
    }
    return null;
  }
  
  public static final Integer toIntOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.toIntOrNull(paramString, 10);
  }
  
  public static final Integer toIntOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    CharsKt.checkRadix(paramInt);
    int i2 = paramString.length();
    if (i2 == 0) {
      return null;
    }
    int i = paramString.charAt(0);
    int k;
    int j;
    if (Intrinsics.compare(i, 48) < 0)
    {
      if (i2 == 1) {
        return null;
      }
      k = 1;
      if (i == 45)
      {
        i = 1;
        j = Integer.MIN_VALUE;
      }
      else if (i == 43)
      {
        i = 0;
        j = -2147483647;
      }
      else
      {
        return null;
      }
    }
    else
    {
      k = 0;
      i = 0;
      j = -2147483647;
    }
    int i1 = -59652323;
    int n = 0;
    while (k < i2)
    {
      int i3 = CharsKt.digitOf(paramString.charAt(k), paramInt);
      if (i3 < 0) {
        return null;
      }
      int m = i1;
      if (n < i1) {
        if (i1 == -59652323)
        {
          i1 = j / paramInt;
          m = i1;
          if (n < i1) {
            return null;
          }
        }
        else
        {
          return null;
        }
      }
      n *= paramInt;
      if (n < j + i3) {
        return null;
      }
      n -= i3;
      k++;
      i1 = m;
    }
    if (i != 0) {
      paramString = Integer.valueOf(n);
    } else {
      paramString = Integer.valueOf(-n);
    }
    return paramString;
  }
  
  public static final Long toLongOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.toLongOrNull(paramString, 10);
  }
  
  public static final Long toLongOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    CharsKt.checkRadix(paramInt);
    int m = paramString.length();
    if (m == 0) {
      return null;
    }
    int k = paramString.charAt(0);
    int j;
    int i;
    long l1;
    if (Intrinsics.compare(k, 48) < 0)
    {
      if (m == 1) {
        return null;
      }
      j = 1;
      if (k == 45)
      {
        i = 1;
        l1 = Long.MIN_VALUE;
      }
      else if (k == 43)
      {
        i = 0;
        l1 = -9223372036854775807L;
      }
      else
      {
        return null;
      }
    }
    else
    {
      j = 0;
      i = 0;
      l1 = -9223372036854775807L;
    }
    long l2 = -256204778801521550L;
    long l4 = -256204778801521550L;
    long l3 = 0L;
    while (j < m)
    {
      int n = CharsKt.digitOf(paramString.charAt(j), paramInt);
      if (n < 0) {
        return null;
      }
      if (l3 < l4) {
        if (l4 == l2)
        {
          l4 = l1 / paramInt;
          if (l3 < l4) {
            return null;
          }
        }
        else
        {
          return null;
        }
      }
      l3 *= paramInt;
      if (l3 < n + l1) {
        return null;
      }
      l3 -= n;
      j++;
    }
    if (i != 0) {
      paramString = Long.valueOf(l3);
    } else {
      paramString = Long.valueOf(-l3);
    }
    return paramString;
  }
  
  public static final Short toShortOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.toShortOrNull(paramString, 10);
  }
  
  public static final Short toShortOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = StringsKt.toIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.intValue();
      if ((paramInt >= 32768) && (paramInt <= 32767)) {
        return Short.valueOf((short)paramInt);
      }
      return null;
    }
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__StringNumberConversionsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */