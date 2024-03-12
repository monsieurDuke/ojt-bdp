package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000Z\n\002\b\003\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\005\n\000\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\004\n\002\020\t\n\000\n\002\020\n\n\002\b\002\0324\020\000\032\004\030\001H\001\"\004\b\000\020\0012\006\020\002\032\0020\0032\022\020\004\032\016\022\004\022\0020\003\022\004\022\002H\0010\005H\b¢\006\004\b\006\020\007\032\r\020\b\032\0020\t*\0020\003H\b\032\025\020\b\032\0020\t*\0020\0032\006\020\n\032\0020\013H\b\032\016\020\f\032\004\030\0010\t*\0020\003H\007\032\026\020\f\032\004\030\0010\t*\0020\0032\006\020\n\032\0020\013H\007\032\r\020\r\032\0020\016*\0020\003H\b\032\025\020\r\032\0020\016*\0020\0032\006\020\017\032\0020\020H\b\032\016\020\021\032\004\030\0010\016*\0020\003H\007\032\026\020\021\032\004\030\0010\016*\0020\0032\006\020\017\032\0020\020H\007\032\r\020\022\032\0020\023*\0020\003H\b\032\024\020\022\032\0020\023*\004\030\0010\003H\b¢\006\002\b\024\032\r\020\025\032\0020\026*\0020\003H\b\032\025\020\025\032\0020\026*\0020\0032\006\020\017\032\0020\020H\b\032\r\020\027\032\0020\030*\0020\003H\b\032\023\020\031\032\004\030\0010\030*\0020\003H\007¢\006\002\020\032\032\r\020\033\032\0020\034*\0020\003H\b\032\023\020\035\032\004\030\0010\034*\0020\003H\007¢\006\002\020\036\032\r\020\037\032\0020\020*\0020\003H\b\032\025\020\037\032\0020\020*\0020\0032\006\020\017\032\0020\020H\b\032\r\020 \032\0020!*\0020\003H\b\032\025\020 \032\0020!*\0020\0032\006\020\017\032\0020\020H\b\032\r\020\"\032\0020#*\0020\003H\b\032\025\020\"\032\0020#*\0020\0032\006\020\017\032\0020\020H\b\032\025\020$\032\0020\003*\0020\0262\006\020\017\032\0020\020H\b\032\025\020$\032\0020\003*\0020\0202\006\020\017\032\0020\020H\b\032\025\020$\032\0020\003*\0020!2\006\020\017\032\0020\020H\b\032\025\020$\032\0020\003*\0020#2\006\020\017\032\0020\020H\b¨\006%"}, d2={"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsJVMKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toBooleanNullable", "toByte", "", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toLong", "", "toShort", "", "toString", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsJVMKt
  extends StringsKt__StringBuilderKt
{
  private static final <T> T screenFloatValue$StringsKt__StringNumberConversionsJVMKt(String paramString, Function1<? super String, ? extends T> paramFunction1)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      if (ScreenFloatValueRegEx.value.matches((CharSequence)paramString)) {
        paramString = paramFunction1.invoke(paramString);
      } else {
        paramString = (String)localObject2;
      }
    }
    catch (NumberFormatException paramString)
    {
      paramString = (String)localObject1;
    }
    return paramString;
  }
  
  private static final BigDecimal toBigDecimal(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return new BigDecimal(paramString);
  }
  
  private static final BigDecimal toBigDecimal(String paramString, MathContext paramMathContext)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramMathContext, "mathContext");
    return new BigDecimal(paramString, paramMathContext);
  }
  
  public static final BigDecimal toBigDecimalOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Object localObject = null;
    BigDecimal localBigDecimal = null;
    try
    {
      if (ScreenFloatValueRegEx.value.matches((CharSequence)paramString))
      {
        localBigDecimal = new java/math/BigDecimal;
        localBigDecimal.<init>(paramString);
        paramString = localBigDecimal;
      }
      else
      {
        paramString = localBigDecimal;
      }
    }
    catch (NumberFormatException paramString)
    {
      paramString = (String)localObject;
    }
    return paramString;
  }
  
  public static final BigDecimal toBigDecimalOrNull(String paramString, MathContext paramMathContext)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramMathContext, "mathContext");
    Object localObject = null;
    BigDecimal localBigDecimal = null;
    try
    {
      if (ScreenFloatValueRegEx.value.matches((CharSequence)paramString))
      {
        localBigDecimal = new java/math/BigDecimal;
        localBigDecimal.<init>(paramString, paramMathContext);
        paramString = localBigDecimal;
      }
      else
      {
        paramString = localBigDecimal;
      }
    }
    catch (NumberFormatException paramString)
    {
      paramString = (String)localObject;
    }
    return paramString;
  }
  
  private static final BigInteger toBigInteger(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return new BigInteger(paramString);
  }
  
  private static final BigInteger toBigInteger(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return new BigInteger(paramString, CharsKt.checkRadix(paramInt));
  }
  
  public static final BigInteger toBigIntegerOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.toBigIntegerOrNull(paramString, 10);
  }
  
  public static final BigInteger toBigIntegerOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    CharsKt.checkRadix(paramInt);
    int j = paramString.length();
    int i = 0;
    switch (j)
    {
    default: 
      if (paramString.charAt(0) == '-') {
        i = 1;
      }
      break;
    case 1: 
      if (CharsKt.digitOf(paramString.charAt(0), paramInt) >= 0) {
        break label96;
      }
      return null;
    case 0: 
      return null;
    }
    while (i < j)
    {
      if (CharsKt.digitOf(paramString.charAt(i), paramInt) < 0) {
        return null;
      }
      i++;
    }
    label96:
    return new BigInteger(paramString, CharsKt.checkRadix(paramInt));
  }
  
  private static final boolean toBooleanNullable(String paramString)
  {
    return Boolean.parseBoolean(paramString);
  }
  
  private static final byte toByte(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Byte.parseByte(paramString);
  }
  
  private static final byte toByte(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Byte.parseByte(paramString, CharsKt.checkRadix(paramInt));
  }
  
  private static final double toDouble(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Double.parseDouble(paramString);
  }
  
  public static final Double toDoubleOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      if (ScreenFloatValueRegEx.value.matches((CharSequence)paramString)) {
        paramString = Double.valueOf(Double.parseDouble(paramString));
      } else {
        paramString = (String)localObject2;
      }
    }
    catch (NumberFormatException paramString)
    {
      paramString = (String)localObject1;
    }
    return paramString;
  }
  
  private static final float toFloat(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Float.parseFloat(paramString);
  }
  
  public static final Float toFloatOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      if (ScreenFloatValueRegEx.value.matches((CharSequence)paramString)) {
        paramString = Float.valueOf(Float.parseFloat(paramString));
      } else {
        paramString = (String)localObject2;
      }
    }
    catch (NumberFormatException paramString)
    {
      paramString = (String)localObject1;
    }
    return paramString;
  }
  
  private static final int toInt(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Integer.parseInt(paramString);
  }
  
  private static final int toInt(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Integer.parseInt(paramString, CharsKt.checkRadix(paramInt));
  }
  
  private static final long toLong(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Long.parseLong(paramString);
  }
  
  private static final long toLong(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Long.parseLong(paramString, CharsKt.checkRadix(paramInt));
  }
  
  private static final short toShort(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Short.parseShort(paramString);
  }
  
  private static final short toShort(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return Short.parseShort(paramString, CharsKt.checkRadix(paramInt));
  }
  
  private static final String toString(byte paramByte, int paramInt)
  {
    String str = Integer.toString(paramByte, CharsKt.checkRadix(CharsKt.checkRadix(paramInt)));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNullExpressionValue(str, "toString(this, checkRadix(radix))");
    return str;
  }
  
  private static final String toString(int paramInt1, int paramInt2)
  {
    String str = Integer.toString(paramInt1, CharsKt.checkRadix(paramInt2));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNullExpressionValue(str, "toString(this, checkRadix(radix))");
    return str;
  }
  
  private static final String toString(long paramLong, int paramInt)
  {
    String str = Long.toString(paramLong, CharsKt.checkRadix(paramInt));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNullExpressionValue(str, "toString(this, checkRadix(radix))");
    return str;
  }
  
  private static final String toString(short paramShort, int paramInt)
  {
    String str = Integer.toString(paramShort, CharsKt.checkRadix(CharsKt.checkRadix(paramInt)));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNullExpressionValue(str, "toString(this, checkRadix(radix))");
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__StringNumberConversionsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */