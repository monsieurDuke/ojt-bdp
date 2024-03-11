package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000B\n\000\n\002\020\f\n\002\020\r\n\000\n\002\020\b\n\002\b\004\n\002\020\017\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004H\b\032\023\020\005\032\004\030\0010\001*\0020\002H\007¢\006\002\020\006\032;\020\007\032\004\030\0010\001\"\016\b\000\020\b*\b\022\004\022\002H\b0\t*\0020\0022\022\020\n\032\016\022\004\022\0020\001\022\004\022\002H\b0\013H\bø\001\000¢\006\002\020\f\032/\020\r\032\004\030\0010\001*\0020\0022\032\020\016\032\026\022\006\b\000\022\0020\0010\017j\n\022\006\b\000\022\0020\001`\020H\007¢\006\002\020\021\032\023\020\022\032\004\030\0010\001*\0020\002H\007¢\006\002\020\006\032;\020\023\032\004\030\0010\001\"\016\b\000\020\b*\b\022\004\022\002H\b0\t*\0020\0022\022\020\n\032\016\022\004\022\0020\001\022\004\022\002H\b0\013H\bø\001\000¢\006\002\020\f\032/\020\024\032\004\030\0010\001*\0020\0022\032\020\016\032\026\022\006\b\000\022\0020\0010\017j\n\022\006\b\000\022\0020\001`\020H\007¢\006\002\020\021\032)\020\025\032\0020\026*\0020\0022\022\020\n\032\016\022\004\022\0020\001\022\004\022\0020\0260\013H\bø\001\000¢\006\002\b\027\032)\020\025\032\0020\030*\0020\0022\022\020\n\032\016\022\004\022\0020\001\022\004\022\0020\0300\013H\bø\001\000¢\006\002\b\031\032\020\020\032\032\b\022\004\022\0020\0010\033*\0020\002\002\007\n\005\b20\001¨\006\034"}, d2={"elementAt", "", "", "index", "", "max", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "maxBy", "R", "", "selector", "Lkotlin/Function1;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "sumOf", "Ljava/math/BigDecimal;", "sumOfBigDecimal", "Ljava/math/BigInteger;", "sumOfBigInteger", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt___StringsJvmKt
  extends StringsKt__StringsKt
{
  private static final char elementAt(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return paramCharSequence.charAt(paramInt);
  }
  
  private static final BigDecimal sumOfBigDecimal(CharSequence paramCharSequence, Function1<? super Character, ? extends BigDecimal> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigDecimal localBigDecimal = BigDecimal.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      localBigDecimal = localBigDecimal.add((BigDecimal)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i))));
      Intrinsics.checkNotNullExpressionValue(localBigDecimal, "this.add(other)");
    }
    return localBigDecimal;
  }
  
  private static final BigInteger sumOfBigInteger(CharSequence paramCharSequence, Function1<? super Character, ? extends BigInteger> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigInteger localBigInteger = BigInteger.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      localBigInteger = localBigInteger.add((BigInteger)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i))));
      Intrinsics.checkNotNullExpressionValue(localBigInteger, "this.add(other)");
    }
    return localBigInteger;
  }
  
  public static final SortedSet<Character> toSortedSet(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return (SortedSet)StringsKt.toCollection(paramCharSequence, (Collection)new TreeSet());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt___StringsJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */