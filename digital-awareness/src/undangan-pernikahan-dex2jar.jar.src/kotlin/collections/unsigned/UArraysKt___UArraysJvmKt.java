package kotlin.collections.unsigned;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.Companion;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000h\n\000\n\002\020 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b \n\002\020\017\n\000\n\002\030\002\n\002\b\n\n\002\030\002\n\002\030\002\n\002\b\030\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\t\032\034\020\000\032\b\022\004\022\0020\0020\001*\0020\003H\007ø\001\000¢\006\004\b\004\020\005\032\034\020\000\032\b\022\004\022\0020\0060\001*\0020\007H\007ø\001\000¢\006\004\b\b\020\t\032\034\020\000\032\b\022\004\022\0020\n0\001*\0020\013H\007ø\001\000¢\006\004\b\f\020\r\032\034\020\000\032\b\022\004\022\0020\0160\001*\0020\017H\007ø\001\000¢\006\004\b\020\020\021\0322\020\022\032\0020\023*\0020\0032\006\020\024\032\0020\0022\b\b\002\020\025\032\0020\0232\b\b\002\020\026\032\0020\023H\007ø\001\000¢\006\004\b\027\020\030\0322\020\022\032\0020\023*\0020\0072\006\020\024\032\0020\0062\b\b\002\020\025\032\0020\0232\b\b\002\020\026\032\0020\023H\007ø\001\000¢\006\004\b\031\020\032\0322\020\022\032\0020\023*\0020\0132\006\020\024\032\0020\n2\b\b\002\020\025\032\0020\0232\b\b\002\020\026\032\0020\023H\007ø\001\000¢\006\004\b\033\020\034\0322\020\022\032\0020\023*\0020\0172\006\020\024\032\0020\0162\b\b\002\020\025\032\0020\0232\b\b\002\020\026\032\0020\023H\007ø\001\000¢\006\004\b\035\020\036\032\037\020\037\032\0020\002*\0020\0032\006\020 \032\0020\023H\bø\001\000¢\006\004\b!\020\"\032\037\020\037\032\0020\006*\0020\0072\006\020 \032\0020\023H\bø\001\000¢\006\004\b#\020$\032\037\020\037\032\0020\n*\0020\0132\006\020 \032\0020\023H\bø\001\000¢\006\004\b%\020&\032\037\020\037\032\0020\016*\0020\0172\006\020 \032\0020\023H\bø\001\000¢\006\004\b'\020(\032\030\020)\032\004\030\0010\002*\0020\003H\007ø\001\000¢\006\004\b*\020+\032\030\020)\032\004\030\0010\006*\0020\007H\007ø\001\000¢\006\004\b,\020-\032\030\020)\032\004\030\0010\n*\0020\013H\007ø\001\000¢\006\004\b.\020/\032\030\020)\032\004\030\0010\016*\0020\017H\007ø\001\000¢\006\004\b0\0201\032@\0202\032\004\030\0010\002\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0032\022\0205\032\016\022\004\022\0020\002\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\b7\0208\032@\0202\032\004\030\0010\006\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0072\022\0205\032\016\022\004\022\0020\006\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\b9\020:\032@\0202\032\004\030\0010\n\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0132\022\0205\032\016\022\004\022\0020\n\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\b;\020<\032@\0202\032\004\030\0010\016\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0172\022\0205\032\016\022\004\022\0020\016\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\b=\020>\0324\020?\032\004\030\0010\002*\0020\0032\032\020@\032\026\022\006\b\000\022\0020\0020Aj\n\022\006\b\000\022\0020\002`BH\007ø\001\000¢\006\004\bC\020D\0324\020?\032\004\030\0010\006*\0020\0072\032\020@\032\026\022\006\b\000\022\0020\0060Aj\n\022\006\b\000\022\0020\006`BH\007ø\001\000¢\006\004\bE\020F\0324\020?\032\004\030\0010\n*\0020\0132\032\020@\032\026\022\006\b\000\022\0020\n0Aj\n\022\006\b\000\022\0020\n`BH\007ø\001\000¢\006\004\bG\020H\0324\020?\032\004\030\0010\016*\0020\0172\032\020@\032\026\022\006\b\000\022\0020\0160Aj\n\022\006\b\000\022\0020\016`BH\007ø\001\000¢\006\004\bI\020J\032\030\020K\032\004\030\0010\002*\0020\003H\007ø\001\000¢\006\004\bL\020+\032\030\020K\032\004\030\0010\006*\0020\007H\007ø\001\000¢\006\004\bM\020-\032\030\020K\032\004\030\0010\n*\0020\013H\007ø\001\000¢\006\004\bN\020/\032\030\020K\032\004\030\0010\016*\0020\017H\007ø\001\000¢\006\004\bO\0201\032@\020P\032\004\030\0010\002\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0032\022\0205\032\016\022\004\022\0020\002\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\bQ\0208\032@\020P\032\004\030\0010\006\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0072\022\0205\032\016\022\004\022\0020\006\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\bR\020:\032@\020P\032\004\030\0010\n\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0132\022\0205\032\016\022\004\022\0020\n\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\bS\020<\032@\020P\032\004\030\0010\016\"\016\b\000\0203*\b\022\004\022\002H304*\0020\0172\022\0205\032\016\022\004\022\0020\016\022\004\022\002H306H\bø\001\001ø\001\000¢\006\004\bT\020>\0324\020U\032\004\030\0010\002*\0020\0032\032\020@\032\026\022\006\b\000\022\0020\0020Aj\n\022\006\b\000\022\0020\002`BH\007ø\001\000¢\006\004\bV\020D\0324\020U\032\004\030\0010\006*\0020\0072\032\020@\032\026\022\006\b\000\022\0020\0060Aj\n\022\006\b\000\022\0020\006`BH\007ø\001\000¢\006\004\bW\020F\0324\020U\032\004\030\0010\n*\0020\0132\032\020@\032\026\022\006\b\000\022\0020\n0Aj\n\022\006\b\000\022\0020\n`BH\007ø\001\000¢\006\004\bX\020H\0324\020U\032\004\030\0010\016*\0020\0172\032\020@\032\026\022\006\b\000\022\0020\0160Aj\n\022\006\b\000\022\0020\016`BH\007ø\001\000¢\006\004\bY\020J\032.\020Z\032\0020[*\0020\0032\022\0205\032\016\022\004\022\0020\002\022\004\022\0020[06H\bø\001\001ø\001\000¢\006\004\b\\\020]\032.\020Z\032\0020^*\0020\0032\022\0205\032\016\022\004\022\0020\002\022\004\022\0020^06H\bø\001\001ø\001\000¢\006\004\b_\020`\032.\020Z\032\0020[*\0020\0072\022\0205\032\016\022\004\022\0020\006\022\004\022\0020[06H\bø\001\001ø\001\000¢\006\004\b\\\020a\032.\020Z\032\0020^*\0020\0072\022\0205\032\016\022\004\022\0020\006\022\004\022\0020^06H\bø\001\001ø\001\000¢\006\004\b_\020b\032.\020Z\032\0020[*\0020\0132\022\0205\032\016\022\004\022\0020\n\022\004\022\0020[06H\bø\001\001ø\001\000¢\006\004\b\\\020c\032.\020Z\032\0020^*\0020\0132\022\0205\032\016\022\004\022\0020\n\022\004\022\0020^06H\bø\001\001ø\001\000¢\006\004\b_\020d\032.\020Z\032\0020[*\0020\0172\022\0205\032\016\022\004\022\0020\016\022\004\022\0020[06H\bø\001\001ø\001\000¢\006\004\b\\\020e\032.\020Z\032\0020^*\0020\0172\022\0205\032\016\022\004\022\0020\016\022\004\022\0020^06H\bø\001\001ø\001\000¢\006\004\b_\020f\002\013\n\002\b\031\n\005\b20\001¨\006g"}, d2={"asList", "", "Lkotlin/UByte;", "Lkotlin/UByteArray;", "asList-GBYM_sE", "([B)Ljava/util/List;", "Lkotlin/UInt;", "Lkotlin/UIntArray;", "asList--ajY-9A", "([I)Ljava/util/List;", "Lkotlin/ULong;", "Lkotlin/ULongArray;", "asList-QwZRm1k", "([J)Ljava/util/List;", "Lkotlin/UShort;", "Lkotlin/UShortArray;", "asList-rL5Bavg", "([S)Ljava/util/List;", "binarySearch", "", "element", "fromIndex", "toIndex", "binarySearch-WpHrYlw", "([BBII)I", "binarySearch-2fe2U9s", "([IIII)I", "binarySearch-K6DWlUc", "([JJII)I", "binarySearch-EtDCXyQ", "([SSII)I", "elementAt", "index", "elementAt-PpDY95g", "([BI)B", "elementAt-qFRl0hI", "([II)I", "elementAt-r7IrZao", "([JI)J", "elementAt-nggk6HY", "([SI)S", "max", "max-GBYM_sE", "([B)Lkotlin/UByte;", "max--ajY-9A", "([I)Lkotlin/UInt;", "max-QwZRm1k", "([J)Lkotlin/ULong;", "max-rL5Bavg", "([S)Lkotlin/UShort;", "maxBy", "R", "", "selector", "Lkotlin/Function1;", "maxBy-JOV_ifY", "([BLkotlin/jvm/functions/Function1;)Lkotlin/UByte;", "maxBy-jgv0xPQ", "([ILkotlin/jvm/functions/Function1;)Lkotlin/UInt;", "maxBy-MShoTSo", "([JLkotlin/jvm/functions/Function1;)Lkotlin/ULong;", "maxBy-xTcfx_M", "([SLkotlin/jvm/functions/Function1;)Lkotlin/UShort;", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "maxWith-XMRcp5o", "([BLjava/util/Comparator;)Lkotlin/UByte;", "maxWith-YmdZ_VM", "([ILjava/util/Comparator;)Lkotlin/UInt;", "maxWith-zrEWJaI", "([JLjava/util/Comparator;)Lkotlin/ULong;", "maxWith-eOHTfZs", "([SLjava/util/Comparator;)Lkotlin/UShort;", "min", "min-GBYM_sE", "min--ajY-9A", "min-QwZRm1k", "min-rL5Bavg", "minBy", "minBy-JOV_ifY", "minBy-jgv0xPQ", "minBy-MShoTSo", "minBy-xTcfx_M", "minWith", "minWith-XMRcp5o", "minWith-YmdZ_VM", "minWith-zrEWJaI", "minWith-eOHTfZs", "sumOf", "Ljava/math/BigDecimal;", "sumOfBigDecimal", "([BLkotlin/jvm/functions/Function1;)Ljava/math/BigDecimal;", "Ljava/math/BigInteger;", "sumOfBigInteger", "([BLkotlin/jvm/functions/Function1;)Ljava/math/BigInteger;", "([ILkotlin/jvm/functions/Function1;)Ljava/math/BigDecimal;", "([ILkotlin/jvm/functions/Function1;)Ljava/math/BigInteger;", "([JLkotlin/jvm/functions/Function1;)Ljava/math/BigDecimal;", "([JLkotlin/jvm/functions/Function1;)Ljava/math/BigInteger;", "([SLkotlin/jvm/functions/Function1;)Ljava/math/BigDecimal;", "([SLkotlin/jvm/functions/Function1;)Ljava/math/BigInteger;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, pn="kotlin.collections", xi=49, xs="kotlin/collections/unsigned/UArraysKt")
class UArraysKt___UArraysJvmKt
{
  public static final List<UInt> asList--ajY-9A(int[] paramArrayOfInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "$this$asList");
    (List)new AbstractList()
    {
      final int[] $this_asList;
      
      public boolean contains-WZ4Q5Ns(int paramAnonymousInt)
      {
        return UIntArray.contains-WZ4Q5Ns(this.$this_asList, paramAnonymousInt);
      }
      
      public int get-pVg5ArA(int paramAnonymousInt)
      {
        return UIntArray.get-pVg5ArA(this.$this_asList, paramAnonymousInt);
      }
      
      public int getSize()
      {
        return UIntArray.getSize-impl(this.$this_asList);
      }
      
      public int indexOf-WZ4Q5Ns(int paramAnonymousInt)
      {
        return ArraysKt.indexOf(this.$this_asList, paramAnonymousInt);
      }
      
      public boolean isEmpty()
      {
        return UIntArray.isEmpty-impl(this.$this_asList);
      }
      
      public int lastIndexOf-WZ4Q5Ns(int paramAnonymousInt)
      {
        return ArraysKt.lastIndexOf(this.$this_asList, paramAnonymousInt);
      }
    };
  }
  
  public static final List<UByte> asList-GBYM_sE(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$asList");
    (List)new AbstractList()
    {
      final byte[] $this_asList;
      
      public boolean contains-7apg3OU(byte paramAnonymousByte)
      {
        return UByteArray.contains-7apg3OU(this.$this_asList, paramAnonymousByte);
      }
      
      public byte get-w2LRezQ(int paramAnonymousInt)
      {
        return UByteArray.get-w2LRezQ(this.$this_asList, paramAnonymousInt);
      }
      
      public int getSize()
      {
        return UByteArray.getSize-impl(this.$this_asList);
      }
      
      public int indexOf-7apg3OU(byte paramAnonymousByte)
      {
        return ArraysKt.indexOf(this.$this_asList, paramAnonymousByte);
      }
      
      public boolean isEmpty()
      {
        return UByteArray.isEmpty-impl(this.$this_asList);
      }
      
      public int lastIndexOf-7apg3OU(byte paramAnonymousByte)
      {
        return ArraysKt.lastIndexOf(this.$this_asList, paramAnonymousByte);
      }
    };
  }
  
  public static final List<ULong> asList-QwZRm1k(long[] paramArrayOfLong)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfLong, "$this$asList");
    (List)new AbstractList()
    {
      final long[] $this_asList;
      
      public boolean contains-VKZWuLQ(long paramAnonymousLong)
      {
        return ULongArray.contains-VKZWuLQ(this.$this_asList, paramAnonymousLong);
      }
      
      public long get-s-VKNKU(int paramAnonymousInt)
      {
        return ULongArray.get-s-VKNKU(this.$this_asList, paramAnonymousInt);
      }
      
      public int getSize()
      {
        return ULongArray.getSize-impl(this.$this_asList);
      }
      
      public int indexOf-VKZWuLQ(long paramAnonymousLong)
      {
        return ArraysKt.indexOf(this.$this_asList, paramAnonymousLong);
      }
      
      public boolean isEmpty()
      {
        return ULongArray.isEmpty-impl(this.$this_asList);
      }
      
      public int lastIndexOf-VKZWuLQ(long paramAnonymousLong)
      {
        return ArraysKt.lastIndexOf(this.$this_asList, paramAnonymousLong);
      }
    };
  }
  
  public static final List<UShort> asList-rL5Bavg(short[] paramArrayOfShort)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfShort, "$this$asList");
    (List)new AbstractList()
    {
      final short[] $this_asList;
      
      public boolean contains-xj2QHRw(short paramAnonymousShort)
      {
        return UShortArray.contains-xj2QHRw(this.$this_asList, paramAnonymousShort);
      }
      
      public short get-Mh2AYeg(int paramAnonymousInt)
      {
        return UShortArray.get-Mh2AYeg(this.$this_asList, paramAnonymousInt);
      }
      
      public int getSize()
      {
        return UShortArray.getSize-impl(this.$this_asList);
      }
      
      public int indexOf-xj2QHRw(short paramAnonymousShort)
      {
        return ArraysKt.indexOf(this.$this_asList, paramAnonymousShort);
      }
      
      public boolean isEmpty()
      {
        return UShortArray.isEmpty-impl(this.$this_asList);
      }
      
      public int lastIndexOf-xj2QHRw(short paramAnonymousShort)
      {
        return ArraysKt.lastIndexOf(this.$this_asList, paramAnonymousShort);
      }
    };
  }
  
  public static final int binarySearch-2fe2U9s(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "$this$binarySearch");
    AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(paramInt2, paramInt3, UIntArray.getSize-impl(paramArrayOfInt));
    int i = paramInt2;
    paramInt2 = paramInt3 - 1;
    paramInt3 = i;
    while (paramInt3 <= paramInt2)
    {
      i = paramInt3 + paramInt2 >>> 1;
      int j = UnsignedKt.uintCompare(paramArrayOfInt[i], paramInt1);
      if (j < 0) {
        paramInt3 = i + 1;
      } else if (j > 0) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt3 + 1);
  }
  
  public static final int binarySearch-EtDCXyQ(short[] paramArrayOfShort, short paramShort, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfShort, "$this$binarySearch");
    AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(paramInt1, paramInt2, UShortArray.getSize-impl(paramArrayOfShort));
    int i = paramInt1;
    paramInt1 = paramInt2 - 1;
    paramInt2 = i;
    while (paramInt2 <= paramInt1)
    {
      i = paramInt2 + paramInt1 >>> 1;
      int j = UnsignedKt.uintCompare(paramArrayOfShort[i], 0xFFFF & paramShort);
      if (j < 0) {
        paramInt2 = i + 1;
      } else if (j > 0) {
        paramInt1 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt2 + 1);
  }
  
  public static final int binarySearch-K6DWlUc(long[] paramArrayOfLong, long paramLong, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfLong, "$this$binarySearch");
    AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(paramInt1, paramInt2, ULongArray.getSize-impl(paramArrayOfLong));
    int i = paramInt1;
    paramInt1 = paramInt2 - 1;
    paramInt2 = i;
    while (paramInt2 <= paramInt1)
    {
      int j = paramInt2 + paramInt1 >>> 1;
      i = UnsignedKt.ulongCompare(paramArrayOfLong[j], paramLong);
      if (i < 0) {
        paramInt2 = j + 1;
      } else if (i > 0) {
        paramInt1 = j - 1;
      } else {
        return j;
      }
    }
    return -(paramInt2 + 1);
  }
  
  public static final int binarySearch-WpHrYlw(byte[] paramArrayOfByte, byte paramByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$binarySearch");
    AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(paramInt1, paramInt2, UByteArray.getSize-impl(paramArrayOfByte));
    int i = paramInt1;
    paramInt1 = paramInt2 - 1;
    paramInt2 = i;
    while (paramInt2 <= paramInt1)
    {
      int j = paramInt2 + paramInt1 >>> 1;
      i = UnsignedKt.uintCompare(paramArrayOfByte[j], paramByte & 0xFF);
      if (i < 0) {
        paramInt2 = j + 1;
      } else if (i > 0) {
        paramInt1 = j - 1;
      } else {
        return j;
      }
    }
    return -(paramInt2 + 1);
  }
  
  private static final byte elementAt-PpDY95g(byte[] paramArrayOfByte, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$elementAt");
    return UByteArray.get-w2LRezQ(paramArrayOfByte, paramInt);
  }
  
  private static final short elementAt-nggk6HY(short[] paramArrayOfShort, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfShort, "$this$elementAt");
    return UShortArray.get-Mh2AYeg(paramArrayOfShort, paramInt);
  }
  
  private static final int elementAt-qFRl0hI(int[] paramArrayOfInt, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "$this$elementAt");
    return UIntArray.get-pVg5ArA(paramArrayOfInt, paramInt);
  }
  
  private static final long elementAt-r7IrZao(long[] paramArrayOfLong, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfLong, "$this$elementAt");
    return ULongArray.get-s-VKNKU(paramArrayOfLong, paramInt);
  }
  
  private static final BigDecimal sumOfBigDecimal(byte[] paramArrayOfByte, Function1<? super UByte, ? extends BigDecimal> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigDecimal localBigDecimal = BigDecimal.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    int j = UByteArray.getSize-impl(paramArrayOfByte);
    for (int i = 0; i < j; i++)
    {
      localBigDecimal = localBigDecimal.add((BigDecimal)paramFunction1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(paramArrayOfByte, i))));
      Intrinsics.checkNotNullExpressionValue(localBigDecimal, "this.add(other)");
    }
    return localBigDecimal;
  }
  
  private static final BigDecimal sumOfBigDecimal(int[] paramArrayOfInt, Function1<? super UInt, ? extends BigDecimal> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigDecimal localBigDecimal = BigDecimal.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    int j = UIntArray.getSize-impl(paramArrayOfInt);
    for (int i = 0; i < j; i++)
    {
      localBigDecimal = localBigDecimal.add((BigDecimal)paramFunction1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(paramArrayOfInt, i))));
      Intrinsics.checkNotNullExpressionValue(localBigDecimal, "this.add(other)");
    }
    return localBigDecimal;
  }
  
  private static final BigDecimal sumOfBigDecimal(long[] paramArrayOfLong, Function1<? super ULong, ? extends BigDecimal> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfLong, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigDecimal localBigDecimal = BigDecimal.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    int j = ULongArray.getSize-impl(paramArrayOfLong);
    for (int i = 0; i < j; i++)
    {
      localBigDecimal = localBigDecimal.add((BigDecimal)paramFunction1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(paramArrayOfLong, i))));
      Intrinsics.checkNotNullExpressionValue(localBigDecimal, "this.add(other)");
    }
    return localBigDecimal;
  }
  
  private static final BigDecimal sumOfBigDecimal(short[] paramArrayOfShort, Function1<? super UShort, ? extends BigDecimal> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfShort, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigDecimal localBigDecimal = BigDecimal.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigDecimal, "valueOf(this.toLong())");
    int j = UShortArray.getSize-impl(paramArrayOfShort);
    for (int i = 0; i < j; i++)
    {
      localBigDecimal = localBigDecimal.add((BigDecimal)paramFunction1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(paramArrayOfShort, i))));
      Intrinsics.checkNotNullExpressionValue(localBigDecimal, "this.add(other)");
    }
    return localBigDecimal;
  }
  
  private static final BigInteger sumOfBigInteger(byte[] paramArrayOfByte, Function1<? super UByte, ? extends BigInteger> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigInteger localBigInteger = BigInteger.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    int j = UByteArray.getSize-impl(paramArrayOfByte);
    for (int i = 0; i < j; i++)
    {
      localBigInteger = localBigInteger.add((BigInteger)paramFunction1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(paramArrayOfByte, i))));
      Intrinsics.checkNotNullExpressionValue(localBigInteger, "this.add(other)");
    }
    return localBigInteger;
  }
  
  private static final BigInteger sumOfBigInteger(int[] paramArrayOfInt, Function1<? super UInt, ? extends BigInteger> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigInteger localBigInteger = BigInteger.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    int j = UIntArray.getSize-impl(paramArrayOfInt);
    for (int i = 0; i < j; i++)
    {
      localBigInteger = localBigInteger.add((BigInteger)paramFunction1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(paramArrayOfInt, i))));
      Intrinsics.checkNotNullExpressionValue(localBigInteger, "this.add(other)");
    }
    return localBigInteger;
  }
  
  private static final BigInteger sumOfBigInteger(long[] paramArrayOfLong, Function1<? super ULong, ? extends BigInteger> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfLong, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigInteger localBigInteger = BigInteger.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    int j = ULongArray.getSize-impl(paramArrayOfLong);
    for (int i = 0; i < j; i++)
    {
      localBigInteger = localBigInteger.add((BigInteger)paramFunction1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(paramArrayOfLong, i))));
      Intrinsics.checkNotNullExpressionValue(localBigInteger, "this.add(other)");
    }
    return localBigInteger;
  }
  
  private static final BigInteger sumOfBigInteger(short[] paramArrayOfShort, Function1<? super UShort, ? extends BigInteger> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfShort, "$this$sumOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    BigInteger localBigInteger = BigInteger.valueOf(0L);
    Intrinsics.checkNotNullExpressionValue(localBigInteger, "valueOf(this.toLong())");
    int j = UShortArray.getSize-impl(paramArrayOfShort);
    for (int i = 0; i < j; i++)
    {
      localBigInteger = localBigInteger.add((BigInteger)paramFunction1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(paramArrayOfShort, i))));
      Intrinsics.checkNotNullExpressionValue(localBigInteger, "this.add(other)");
    }
    return localBigInteger;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/unsigned/UArraysKt___UArraysJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */