package kotlin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000$\n\000\n\002\030\002\n\002\b\b\n\002\020\006\n\000\n\002\030\002\n\002\020\007\n\002\020\b\n\002\020\t\n\002\b\002\032\r\020\000\032\0020\001*\0020\001H\n\032\025\020\002\032\0020\001*\0020\0012\006\020\003\032\0020\001H\n\032\r\020\004\032\0020\001*\0020\001H\n\032\025\020\005\032\0020\001*\0020\0012\006\020\003\032\0020\001H\n\032\025\020\006\032\0020\001*\0020\0012\006\020\003\032\0020\001H\n\032\025\020\007\032\0020\001*\0020\0012\006\020\003\032\0020\001H\n\032\025\020\b\032\0020\001*\0020\0012\006\020\003\032\0020\001H\n\032\r\020\t\032\0020\001*\0020\nH\b\032\025\020\t\032\0020\001*\0020\n2\006\020\013\032\0020\fH\b\032\r\020\t\032\0020\001*\0020\rH\b\032\025\020\t\032\0020\001*\0020\r2\006\020\013\032\0020\fH\b\032\r\020\t\032\0020\001*\0020\016H\b\032\025\020\t\032\0020\001*\0020\0162\006\020\013\032\0020\fH\b\032\r\020\t\032\0020\001*\0020\017H\b\032\025\020\t\032\0020\001*\0020\0172\006\020\013\032\0020\fH\b\032\r\020\020\032\0020\001*\0020\001H\n¨\006\021"}, d2={"dec", "Ljava/math/BigDecimal;", "div", "other", "inc", "minus", "plus", "rem", "times", "toBigDecimal", "", "mathContext", "Ljava/math/MathContext;", "", "", "", "unaryMinus", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/NumbersKt")
class NumbersKt__BigDecimalsKt
{
  private static final BigDecimal dec(BigDecimal paramBigDecimal)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal, "<this>");
    paramBigDecimal = paramBigDecimal.subtract(BigDecimal.ONE);
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal, "this.subtract(BigDecimal.ONE)");
    return paramBigDecimal;
  }
  
  private static final BigDecimal div(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigDecimal2, "other");
    paramBigDecimal1 = paramBigDecimal1.divide(paramBigDecimal2, RoundingMode.HALF_EVEN);
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal1, "this.divide(other, RoundingMode.HALF_EVEN)");
    return paramBigDecimal1;
  }
  
  private static final BigDecimal inc(BigDecimal paramBigDecimal)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal, "<this>");
    paramBigDecimal = paramBigDecimal.add(BigDecimal.ONE);
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal, "this.add(BigDecimal.ONE)");
    return paramBigDecimal;
  }
  
  private static final BigDecimal minus(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigDecimal2, "other");
    paramBigDecimal1 = paramBigDecimal1.subtract(paramBigDecimal2);
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal1, "this.subtract(other)");
    return paramBigDecimal1;
  }
  
  private static final BigDecimal plus(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigDecimal2, "other");
    paramBigDecimal1 = paramBigDecimal1.add(paramBigDecimal2);
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal1, "this.add(other)");
    return paramBigDecimal1;
  }
  
  private static final BigDecimal rem(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigDecimal2, "other");
    paramBigDecimal1 = paramBigDecimal1.remainder(paramBigDecimal2);
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal1, "this.remainder(other)");
    return paramBigDecimal1;
  }
  
  private static final BigDecimal times(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigDecimal2, "other");
    paramBigDecimal1 = paramBigDecimal1.multiply(paramBigDecimal2);
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal1, "this.multiply(other)");
    return paramBigDecimal1;
  }
  
  private static final BigDecimal toBigDecimal(double paramDouble)
  {
    String str = String.valueOf(paramDouble);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return new BigDecimal(str);
  }
  
  private static final BigDecimal toBigDecimal(double paramDouble, MathContext paramMathContext)
  {
    Intrinsics.checkNotNullParameter(paramMathContext, "mathContext");
    String str = String.valueOf(paramDouble);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return new BigDecimal(str, paramMathContext);
  }
  
  private static final BigDecimal toBigDecimal(float paramFloat)
  {
    String str = String.valueOf(paramFloat);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return new BigDecimal(str);
  }
  
  private static final BigDecimal toBigDecimal(float paramFloat, MathContext paramMathContext)
  {
    Intrinsics.checkNotNullParameter(paramMathContext, "mathContext");
    String str = String.valueOf(paramFloat);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return new BigDecimal(str, paramMathContext);
  }
  
  private static final BigDecimal toBigDecimal(int paramInt)
  {
    BigDecimal localBigDecimal = BigDecimal.valueOf(paramInt);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    return localBigDecimal;
  }
  
  private static final BigDecimal toBigDecimal(int paramInt, MathContext paramMathContext)
  {
    Intrinsics.checkNotNullParameter(paramMathContext, "mathContext");
    return new BigDecimal(paramInt, paramMathContext);
  }
  
  private static final BigDecimal toBigDecimal(long paramLong)
  {
    BigDecimal localBigDecimal = BigDecimal.valueOf(paramLong);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this)");
    return localBigDecimal;
  }
  
  private static final BigDecimal toBigDecimal(long paramLong, MathContext paramMathContext)
  {
    Intrinsics.checkNotNullParameter(paramMathContext, "mathContext");
    return new BigDecimal(paramLong, paramMathContext);
  }
  
  private static final BigDecimal unaryMinus(BigDecimal paramBigDecimal)
  {
    Intrinsics.checkNotNullParameter(paramBigDecimal, "<this>");
    paramBigDecimal = paramBigDecimal.negate();
    Intrinsics.checkNotNullExpressionValue(paramBigDecimal, "this.negate()");
    return paramBigDecimal;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/NumbersKt__BigDecimalsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */