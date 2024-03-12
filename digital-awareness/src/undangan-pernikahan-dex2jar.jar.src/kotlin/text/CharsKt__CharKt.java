package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.CharRange;
import kotlin.ranges.IntRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\036\n\000\n\002\020\f\n\002\020\b\n\002\b\006\n\002\020\013\n\002\b\004\n\002\020\016\n\002\b\002\032\f\020\000\032\0020\001*\0020\002H\007\032\024\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\002H\007\032\f\020\004\032\0020\002*\0020\001H\007\032\024\020\004\032\0020\002*\0020\0012\006\020\003\032\0020\002H\007\032\023\020\005\032\004\030\0010\002*\0020\001H\007¢\006\002\020\006\032\033\020\005\032\004\030\0010\002*\0020\0012\006\020\003\032\0020\002H\007¢\006\002\020\007\032\034\020\b\032\0020\t*\0020\0012\006\020\n\032\0020\0012\b\b\002\020\013\032\0020\t\032\n\020\f\032\0020\t*\0020\001\032\025\020\r\032\0020\016*\0020\0012\006\020\n\032\0020\016H\n\032\f\020\017\032\0020\016*\0020\001H\007¨\006\020"}, d2={"digitToChar", "", "", "radix", "digitToInt", "digitToIntOrNull", "(C)Ljava/lang/Integer;", "(CI)Ljava/lang/Integer;", "equals", "", "other", "ignoreCase", "isSurrogate", "plus", "", "titlecase", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/CharsKt")
class CharsKt__CharKt
  extends CharsKt__CharJVMKt
{
  public static final char digitToChar(int paramInt)
  {
    if (new IntRange(0, 9).contains(paramInt)) {
      return (char)(paramInt + 48);
    }
    throw new IllegalArgumentException("Int " + paramInt + " is not a decimal digit");
  }
  
  public static final char digitToChar(int paramInt1, int paramInt2)
  {
    if (new IntRange(2, 36).contains(paramInt2))
    {
      if ((paramInt1 >= 0) && (paramInt1 < paramInt2))
      {
        char c;
        if (paramInt1 < 10) {
          c = (char)(paramInt1 + 48);
        } else {
          c = (char)((char)(paramInt1 + 65) - '\n');
        }
        return c;
      }
      throw new IllegalArgumentException("Digit " + paramInt1 + " does not represent a valid digit in radix " + paramInt2);
    }
    throw new IllegalArgumentException("Invalid radix: " + paramInt2 + ". Valid radix values are in range 2..36");
  }
  
  public static final int digitToInt(char paramChar)
  {
    int i = CharsKt.digitOf(paramChar, 10);
    if (i >= 0) {
      return i;
    }
    throw new IllegalArgumentException("Char " + paramChar + " is not a decimal digit");
  }
  
  public static final int digitToInt(char paramChar, int paramInt)
  {
    Integer localInteger = CharsKt.digitToIntOrNull(paramChar, paramInt);
    if (localInteger != null) {
      return localInteger.intValue();
    }
    throw new IllegalArgumentException("Char " + paramChar + " is not a digit in the given radix=" + paramInt);
  }
  
  public static final Integer digitToIntOrNull(char paramChar)
  {
    Integer localInteger = Integer.valueOf(CharsKt.digitOf(paramChar, 10));
    int i;
    if (((Number)localInteger).intValue() >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0) {
      localInteger = null;
    }
    return localInteger;
  }
  
  public static final Integer digitToIntOrNull(char paramChar, int paramInt)
  {
    CharsKt.checkRadix(paramInt);
    Integer localInteger = Integer.valueOf(CharsKt.digitOf(paramChar, paramInt));
    if (((Number)localInteger).intValue() >= 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (paramInt == 0) {
      localInteger = null;
    }
    return localInteger;
  }
  
  public static final boolean equals(char paramChar1, char paramChar2, boolean paramBoolean)
  {
    boolean bool = true;
    if (paramChar1 == paramChar2) {
      return true;
    }
    if (!paramBoolean) {
      return false;
    }
    paramChar1 = Character.toUpperCase(paramChar1);
    paramChar2 = Character.toUpperCase(paramChar2);
    paramBoolean = bool;
    if (paramChar1 != paramChar2) {
      if (Character.toLowerCase(paramChar1) == Character.toLowerCase(paramChar2)) {
        paramBoolean = bool;
      } else {
        paramBoolean = false;
      }
    }
    return paramBoolean;
  }
  
  public static final boolean isSurrogate(char paramChar)
  {
    return new CharRange(55296, 57343).contains(paramChar);
  }
  
  private static final String plus(char paramChar, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "other");
    return paramChar + paramString;
  }
  
  public static final String titlecase(char paramChar)
  {
    String str = _OneToManyTitlecaseMappingsKt.titlecaseImpl(paramChar);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/CharsKt__CharKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */