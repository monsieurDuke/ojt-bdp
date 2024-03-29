package kotlin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000(\n\000\n\002\030\002\n\002\b\013\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\002\b\003\032\025\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f\032\r\020\003\032\0020\001*\0020\001H\n\032\025\020\004\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\r\020\005\032\0020\001*\0020\001H\n\032\r\020\006\032\0020\001*\0020\001H\b\032\025\020\007\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\025\020\b\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f\032\025\020\t\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\025\020\n\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\025\020\013\032\0020\001*\0020\0012\006\020\f\032\0020\rH\f\032\025\020\016\032\0020\001*\0020\0012\006\020\f\032\0020\rH\f\032\025\020\017\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\r\020\020\032\0020\021*\0020\001H\b\032!\020\020\032\0020\021*\0020\0012\b\b\002\020\022\032\0020\r2\b\b\002\020\023\032\0020\024H\b\032\r\020\025\032\0020\001*\0020\rH\b\032\r\020\025\032\0020\001*\0020\026H\b\032\r\020\027\032\0020\001*\0020\001H\n\032\025\020\030\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f¨\006\031"}, d2={"and", "Ljava/math/BigInteger;", "other", "dec", "div", "inc", "inv", "minus", "or", "plus", "rem", "shl", "n", "", "shr", "times", "toBigDecimal", "Ljava/math/BigDecimal;", "scale", "mathContext", "Ljava/math/MathContext;", "toBigInteger", "", "unaryMinus", "xor", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/NumbersKt")
class NumbersKt__BigIntegersKt
  extends NumbersKt__BigDecimalsKt
{
  private static final BigInteger and(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.and(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.and(other)");
    return paramBigInteger1;
  }
  
  private static final BigInteger dec(BigInteger paramBigInteger)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    paramBigInteger = paramBigInteger.subtract(BigInteger.ONE);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger, "this.subtract(BigInteger.ONE)");
    return paramBigInteger;
  }
  
  private static final BigInteger div(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.divide(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.divide(other)");
    return paramBigInteger1;
  }
  
  private static final BigInteger inc(BigInteger paramBigInteger)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    paramBigInteger = paramBigInteger.add(BigInteger.ONE);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger, "this.add(BigInteger.ONE)");
    return paramBigInteger;
  }
  
  private static final BigInteger inv(BigInteger paramBigInteger)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    paramBigInteger = paramBigInteger.not();
    Intrinsics.checkNotNullExpressionValue(paramBigInteger, "this.not()");
    return paramBigInteger;
  }
  
  private static final BigInteger minus(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.subtract(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.subtract(other)");
    return paramBigInteger1;
  }
  
  private static final BigInteger or(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.or(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.or(other)");
    return paramBigInteger1;
  }
  
  private static final BigInteger plus(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.add(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.add(other)");
    return paramBigInteger1;
  }
  
  private static final BigInteger rem(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.remainder(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.remainder(other)");
    return paramBigInteger1;
  }
  
  private static final BigInteger shl(BigInteger paramBigInteger, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    paramBigInteger = paramBigInteger.shiftLeft(paramInt);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger, "this.shiftLeft(n)");
    return paramBigInteger;
  }
  
  private static final BigInteger shr(BigInteger paramBigInteger, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    paramBigInteger = paramBigInteger.shiftRight(paramInt);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger, "this.shiftRight(n)");
    return paramBigInteger;
  }
  
  private static final BigInteger times(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.multiply(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.multiply(other)");
    return paramBigInteger1;
  }
  
  private static final BigDecimal toBigDecimal(BigInteger paramBigInteger)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    return new BigDecimal(paramBigInteger);
  }
  
  private static final BigDecimal toBigDecimal(BigInteger paramBigInteger, int paramInt, MathContext paramMathContext)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    Intrinsics.checkNotNullParameter(paramMathContext, "mathContext");
    return new BigDecimal(paramBigInteger, paramInt, paramMathContext);
  }
  
  private static final BigInteger toBigInteger(int paramInt)
  {
    BigInteger localBigInteger = BigInteger.valueOf(paramInt);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    return localBigInteger;
  }
  
  private static final BigInteger toBigInteger(long paramLong)
  {
    BigInteger localBigInteger = BigInteger.valueOf(paramLong);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this)");
    return localBigInteger;
  }
  
  private static final BigInteger unaryMinus(BigInteger paramBigInteger)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger, "<this>");
    paramBigInteger = paramBigInteger.negate();
    Intrinsics.checkNotNullExpressionValue(paramBigInteger, "this.negate()");
    return paramBigInteger;
  }
  
  private static final BigInteger xor(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    Intrinsics.checkNotNullParameter(paramBigInteger1, "<this>");
    Intrinsics.checkNotNullParameter(paramBigInteger2, "other");
    paramBigInteger1 = paramBigInteger1.xor(paramBigInteger2);
    Intrinsics.checkNotNullExpressionValue(paramBigInteger1, "this.xor(other)");
    return paramBigInteger1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/NumbersKt__BigIntegersKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */