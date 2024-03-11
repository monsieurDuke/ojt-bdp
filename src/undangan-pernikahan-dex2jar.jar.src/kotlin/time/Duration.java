package kotlin.time;

import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000n\n\002\030\002\n\002\020\017\n\000\n\002\020\t\n\002\b\005\n\002\020\b\n\002\b\005\n\002\020\006\n\002\b-\n\002\030\002\n\002\b\027\n\002\020\013\n\002\020\000\n\002\b\033\n\002\030\002\n\002\030\002\n\002\b\b\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\t\n\002\020\016\n\002\b\020\n\002\020\002\n\002\030\002\n\002\030\002\n\002\b\b\b@\030\000 ¤\0012\b\022\004\022\0020\0000\001:\002¤\001B\024\b\000\022\006\020\002\032\0020\003ø\001\000¢\006\004\b\004\020\005J%\020D\032\0020\0002\006\020E\032\0020\0032\006\020F\032\0020\003H\002ø\001\001ø\001\000¢\006\004\bG\020HJ\033\020I\032\0020\t2\006\020J\032\0020\000H\002ø\001\000¢\006\004\bK\020LJ\036\020M\032\0020\0002\006\020N\032\0020\017H\002ø\001\001ø\001\000¢\006\004\bO\020PJ\036\020M\032\0020\0002\006\020N\032\0020\tH\002ø\001\001ø\001\000¢\006\004\bO\020QJ\033\020M\032\0020\0172\006\020J\032\0020\000H\002ø\001\000¢\006\004\bR\020SJ\032\020T\032\0020U2\b\020J\032\004\030\0010VHÖ\003¢\006\004\bW\020XJ\020\020Y\032\0020\tHÖ\001¢\006\004\bZ\020\rJ\r\020[\032\0020U¢\006\004\b\\\020]J\017\020^\032\0020UH\002¢\006\004\b_\020]J\017\020`\032\0020UH\002¢\006\004\ba\020]J\r\020b\032\0020U¢\006\004\bc\020]J\r\020d\032\0020U¢\006\004\be\020]J\r\020f\032\0020U¢\006\004\bg\020]J\033\020h\032\0020\0002\006\020J\032\0020\000H\002ø\001\000¢\006\004\bi\020jJ\033\020k\032\0020\0002\006\020J\032\0020\000H\002ø\001\000¢\006\004\bl\020jJ\036\020m\032\0020\0002\006\020N\032\0020\017H\002ø\001\001ø\001\000¢\006\004\bn\020PJ\036\020m\032\0020\0002\006\020N\032\0020\tH\002ø\001\001ø\001\000¢\006\004\bn\020QJ\001\020o\032\002Hp\"\004\b\000\020p2u\020q\032q\022\023\022\0210\003¢\006\f\bs\022\b\bt\022\004\b\b(u\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(v\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(w\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(x\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(y\022\004\022\002Hp0rH\bø\001\002\002\n\n\b\b\001\022\002\020\001 \001¢\006\004\bz\020{J\001\020o\032\002Hp\"\004\b\000\020p2`\020q\032\\\022\023\022\0210\003¢\006\f\bs\022\b\bt\022\004\b\b(v\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(w\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(x\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(y\022\004\022\002Hp0|H\bø\001\002\002\n\n\b\b\001\022\002\020\001 \001¢\006\004\bz\020}Js\020o\032\002Hp\"\004\b\000\020p2K\020q\032G\022\023\022\0210\003¢\006\f\bs\022\b\bt\022\004\b\b(w\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(x\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(y\022\004\022\002Hp0~H\bø\001\002\002\n\n\b\b\001\022\002\020\001 \001¢\006\004\bz\020J`\020o\032\002Hp\"\004\b\000\020p27\020q\0323\022\023\022\0210\003¢\006\f\bs\022\b\bt\022\004\b\b(x\022\023\022\0210\t¢\006\f\bs\022\b\bt\022\004\b\b(y\022\004\022\002Hp0\001H\bø\001\002\002\n\n\b\b\001\022\002\020\001 \001¢\006\005\bz\020\001J\031\020\001\032\0020\0172\007\020\001\032\0020=¢\006\006\b\001\020\001J\031\020\001\032\0020\t2\007\020\001\032\0020=¢\006\006\b\001\020\001J\021\020\001\032\0030\001¢\006\006\b\001\020\001J\031\020\001\032\0020\0032\007\020\001\032\0020=¢\006\006\b\001\020\001J\021\020\001\032\0020\003H\007¢\006\005\b\001\020\005J\021\020\001\032\0020\003H\007¢\006\005\b\001\020\005J\023\020\001\032\0030\001H\026¢\006\006\b\001\020\001J%\020\001\032\0030\0012\007\020\001\032\0020=2\t\b\002\020\001\032\0020\t¢\006\006\b\001\020\001J\030\020\001\032\0020\000H\002ø\001\001ø\001\000¢\006\005\b\001\020\005JK\020\001\032\0030\001*\b0\001j\003`\0012\007\020\001\032\0020\t2\007\020\001\032\0020\t2\007\020 \001\032\0020\t2\b\020\001\032\0030\0012\007\020¡\001\032\0020UH\002¢\006\006\b¢\001\020£\001R\027\020\006\032\0020\0008Fø\001\000ø\001\001¢\006\006\032\004\b\007\020\005R\032\020\b\032\0020\t8@X\004¢\006\f\022\004\b\n\020\013\032\004\b\f\020\rR\032\020\016\032\0020\0178FX\004¢\006\f\022\004\b\020\020\013\032\004\b\021\020\022R\032\020\023\032\0020\0178FX\004¢\006\f\022\004\b\024\020\013\032\004\b\025\020\022R\032\020\026\032\0020\0178FX\004¢\006\f\022\004\b\027\020\013\032\004\b\030\020\022R\032\020\031\032\0020\0178FX\004¢\006\f\022\004\b\032\020\013\032\004\b\033\020\022R\032\020\034\032\0020\0178FX\004¢\006\f\022\004\b\035\020\013\032\004\b\036\020\022R\032\020\037\032\0020\0178FX\004¢\006\f\022\004\b \020\013\032\004\b!\020\022R\032\020\"\032\0020\0178FX\004¢\006\f\022\004\b#\020\013\032\004\b$\020\022R\021\020%\032\0020\0038F¢\006\006\032\004\b&\020\005R\021\020'\032\0020\0038F¢\006\006\032\004\b(\020\005R\021\020)\032\0020\0038F¢\006\006\032\004\b*\020\005R\021\020+\032\0020\0038F¢\006\006\032\004\b,\020\005R\021\020-\032\0020\0038F¢\006\006\032\004\b.\020\005R\021\020/\032\0020\0038F¢\006\006\032\004\b0\020\005R\021\0201\032\0020\0038F¢\006\006\032\004\b2\020\005R\032\0203\032\0020\t8@X\004¢\006\f\022\004\b4\020\013\032\004\b5\020\rR\032\0206\032\0020\t8@X\004¢\006\f\022\004\b7\020\013\032\004\b8\020\rR\016\020\002\032\0020\003X\004¢\006\002\n\000R\032\0209\032\0020\t8@X\004¢\006\f\022\004\b:\020\013\032\004\b;\020\rR\024\020<\032\0020=8BX\004¢\006\006\032\004\b>\020?R\025\020@\032\0020\t8Â\002X\004¢\006\006\032\004\bA\020\rR\024\020B\032\0020\0038BX\004¢\006\006\032\004\bC\020\005\001\002\001\0020\003ø\001\000\002\017\n\002\b\031\n\002\b!\n\005\b20\001¨\006¥\001"}, d2={"Lkotlin/time/Duration;", "", "rawValue", "", "constructor-impl", "(J)J", "absoluteValue", "getAbsoluteValue-UwyO8pc", "hoursComponent", "", "getHoursComponent$annotations", "()V", "getHoursComponent-impl", "(J)I", "inDays", "", "getInDays$annotations", "getInDays-impl", "(J)D", "inHours", "getInHours$annotations", "getInHours-impl", "inMicroseconds", "getInMicroseconds$annotations", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds$annotations", "getInMilliseconds-impl", "inMinutes", "getInMinutes$annotations", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds$annotations", "getInNanoseconds-impl", "inSeconds", "getInSeconds$annotations", "getInSeconds-impl", "inWholeDays", "getInWholeDays-impl", "inWholeHours", "getInWholeHours-impl", "inWholeMicroseconds", "getInWholeMicroseconds-impl", "inWholeMilliseconds", "getInWholeMilliseconds-impl", "inWholeMinutes", "getInWholeMinutes-impl", "inWholeNanoseconds", "getInWholeNanoseconds-impl", "inWholeSeconds", "getInWholeSeconds-impl", "minutesComponent", "getMinutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "getNanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "getSecondsComponent$annotations", "getSecondsComponent-impl", "storageUnit", "Lkotlin/time/DurationUnit;", "getStorageUnit-impl", "(J)Lkotlin/time/DurationUnit;", "unitDiscriminator", "getUnitDiscriminator-impl", "value", "getValue-impl", "addValuesMixedRanges", "thisMillis", "otherNanos", "addValuesMixedRanges-UwyO8pc", "(JJJ)J", "compareTo", "other", "compareTo-LRDsOJo", "(JJ)I", "div", "scale", "div-UwyO8pc", "(JD)J", "(JI)J", "div-LRDsOJo", "(JJ)D", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "isFinite", "isFinite-impl", "(J)Z", "isInMillis", "isInMillis-impl", "isInNanos", "isInNanos-impl", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "(JJ)J", "plus", "plus-LRDsOJo", "times", "times-UwyO8pc", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(JLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(JLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(JLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "toDouble-impl", "(JLkotlin/time/DurationUnit;)D", "toInt", "toInt-impl", "(JLkotlin/time/DurationUnit;)I", "toIsoString", "", "toIsoString-impl", "(J)Ljava/lang/String;", "toLong", "toLong-impl", "(JLkotlin/time/DurationUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(JLkotlin/time/DurationUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-UwyO8pc", "appendFractional", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "whole", "fractional", "fractionalSize", "isoZeroes", "appendFractional-impl", "(JLjava/lang/StringBuilder;IIILjava/lang/String;Z)V", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class Duration
  implements Comparable<Duration>
{
  public static final Companion Companion = new Companion(null);
  private static final long INFINITE = DurationKt.access$durationOfMillis(4611686018427387903L);
  private static final long NEG_INFINITE = DurationKt.access$durationOfMillis(-4611686018427387903L);
  private static final long ZERO = constructor-impl(0L);
  private final long rawValue;
  
  private static final long addValuesMixedRanges-UwyO8pc(long paramLong1, long paramLong2, long paramLong3)
  {
    paramLong1 = DurationKt.access$nanosToMillis(paramLong3);
    paramLong2 += paramLong1;
    if (new LongRange(-4611686018426L, 4611686018426L).contains(paramLong2))
    {
      paramLong1 = DurationKt.access$millisToNanos(paramLong1);
      paramLong1 = DurationKt.access$durationOfNanos(DurationKt.access$millisToNanos(paramLong2) + (paramLong3 - paramLong1));
    }
    else
    {
      paramLong1 = DurationKt.access$durationOfMillis(RangesKt.coerceIn(paramLong2, -4611686018427387903L, 4611686018427387903L));
    }
    return paramLong1;
  }
  
  private static final void appendFractional-impl(long paramLong, StringBuilder paramStringBuilder, int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean)
  {
    paramStringBuilder.append(paramInt1);
    if (paramInt2 != 0)
    {
      paramStringBuilder.append('.');
      Object localObject = String.valueOf(paramInt2);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      String str = StringsKt.padStart((String)localObject, paramInt3, '0');
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localObject = (CharSequence)str;
      paramInt1 = ((CharSequence)localObject).length();
      paramInt3 = -1;
      paramInt1--;
      if (paramInt1 >= 0)
      {
        int i;
        do
        {
          i = paramInt1 - 1;
          if (((CharSequence)localObject).charAt(paramInt1) != '0') {
            paramInt2 = 1;
          } else {
            paramInt2 = 0;
          }
          if (paramInt2 != 0) {
            break;
          }
          paramInt1 = i;
        } while (i >= 0);
      }
      paramInt1 = paramInt3;
      paramInt1++;
      if ((!paramBoolean) && (paramInt1 < 3)) {
        Intrinsics.checkNotNullExpressionValue(paramStringBuilder.append((CharSequence)str, 0, paramInt1), "this.append(value, startIndex, endIndex)");
      } else {
        Intrinsics.checkNotNullExpressionValue(paramStringBuilder.append((CharSequence)str, 0, (paramInt1 + 2) / 3 * 3), "this.append(value, startIndex, endIndex)");
      }
    }
    paramStringBuilder.append(paramString);
  }
  
  public static int compareTo-LRDsOJo(long paramLong1, long paramLong2)
  {
    long l = paramLong1 ^ paramLong2;
    if ((l >= 0L) && (((int)l & 0x1) != 0))
    {
      int i = ((int)paramLong1 & 0x1) - ((int)paramLong2 & 0x1);
      if (isNegative-impl(paramLong1)) {
        i = -i;
      }
      return i;
    }
    return Intrinsics.compare(paramLong1, paramLong2);
  }
  
  public static long constructor-impl(long paramLong)
  {
    if (DurationJvmKt.getDurationAssertionsEnabled()) {
      if (isInNanos-impl(paramLong))
      {
        if (!new LongRange(-4611686018426999999L, 4611686018426999999L).contains(getValue-impl(paramLong))) {
          throw new AssertionError(getValue-impl(paramLong) + " ns is out of nanoseconds range");
        }
      }
      else if (new LongRange(-4611686018427387903L, 4611686018427387903L).contains(getValue-impl(paramLong)))
      {
        if (new LongRange(-4611686018426L, 4611686018426L).contains(getValue-impl(paramLong))) {
          throw new AssertionError(getValue-impl(paramLong) + " ms is denormalized");
        }
      }
      else {
        throw new AssertionError(getValue-impl(paramLong) + " ms is out of milliseconds range");
      }
    }
    return paramLong;
  }
  
  public static final double div-LRDsOJo(long paramLong1, long paramLong2)
  {
    DurationUnit localDurationUnit = (DurationUnit)ComparisonsKt.maxOf((Comparable)getStorageUnit-impl(paramLong1), (Comparable)getStorageUnit-impl(paramLong2));
    return toDouble-impl(paramLong1, localDurationUnit) / toDouble-impl(paramLong2, localDurationUnit);
  }
  
  public static final long div-UwyO8pc(long paramLong, double paramDouble)
  {
    int j = MathKt.roundToInt(paramDouble);
    int i;
    if (j == paramDouble) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) && (j != 0)) {
      return div-UwyO8pc(paramLong, j);
    }
    DurationUnit localDurationUnit = getStorageUnit-impl(paramLong);
    return DurationKt.toDuration(toDouble-impl(paramLong, localDurationUnit) / paramDouble, localDurationUnit);
  }
  
  public static final long div-UwyO8pc(long paramLong, int paramInt)
  {
    if (paramInt == 0)
    {
      if (isPositive-impl(paramLong))
      {
        paramLong = INFINITE;
      }
      else
      {
        if (!isNegative-impl(paramLong)) {
          break label31;
        }
        paramLong = NEG_INFINITE;
      }
      return paramLong;
      label31:
      throw new IllegalArgumentException("Dividing zero duration by zero yields an undefined result.");
    }
    if (isInNanos-impl(paramLong)) {
      return DurationKt.access$durationOfNanos(getValue-impl(paramLong) / paramInt);
    }
    if (isInfinite-impl(paramLong)) {
      return times-UwyO8pc(paramLong, MathKt.getSign(paramInt));
    }
    long l = getValue-impl(paramLong) / paramInt;
    if (new LongRange(-4611686018426L, 4611686018426L).contains(l))
    {
      paramLong = DurationKt.access$millisToNanos(getValue-impl(paramLong) - paramInt * l) / paramInt;
      return DurationKt.access$durationOfNanos(DurationKt.access$millisToNanos(l) + paramLong);
    }
    return DurationKt.access$durationOfMillis(l);
  }
  
  public static boolean equals-impl(long paramLong, Object paramObject)
  {
    if (!(paramObject instanceof Duration)) {
      return false;
    }
    return paramLong == ((Duration)paramObject).unbox-impl();
  }
  
  public static final boolean equals-impl0(long paramLong1, long paramLong2)
  {
    boolean bool;
    if (paramLong1 == paramLong2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final long getAbsoluteValue-UwyO8pc(long paramLong)
  {
    if (isNegative-impl(paramLong)) {
      paramLong = unaryMinus-UwyO8pc(paramLong);
    }
    return paramLong;
  }
  
  public static final int getHoursComponent-impl(long paramLong)
  {
    int i;
    if (isInfinite-impl(paramLong)) {
      i = 0;
    } else {
      i = (int)(getInWholeHours-impl(paramLong) % 24);
    }
    return i;
  }
  
  public static final double getInDays-impl(long paramLong)
  {
    return toDouble-impl(paramLong, DurationUnit.DAYS);
  }
  
  public static final double getInHours-impl(long paramLong)
  {
    return toDouble-impl(paramLong, DurationUnit.HOURS);
  }
  
  public static final double getInMicroseconds-impl(long paramLong)
  {
    return toDouble-impl(paramLong, DurationUnit.MICROSECONDS);
  }
  
  public static final double getInMilliseconds-impl(long paramLong)
  {
    return toDouble-impl(paramLong, DurationUnit.MILLISECONDS);
  }
  
  public static final double getInMinutes-impl(long paramLong)
  {
    return toDouble-impl(paramLong, DurationUnit.MINUTES);
  }
  
  public static final double getInNanoseconds-impl(long paramLong)
  {
    return toDouble-impl(paramLong, DurationUnit.NANOSECONDS);
  }
  
  public static final double getInSeconds-impl(long paramLong)
  {
    return toDouble-impl(paramLong, DurationUnit.SECONDS);
  }
  
  public static final long getInWholeDays-impl(long paramLong)
  {
    return toLong-impl(paramLong, DurationUnit.DAYS);
  }
  
  public static final long getInWholeHours-impl(long paramLong)
  {
    return toLong-impl(paramLong, DurationUnit.HOURS);
  }
  
  public static final long getInWholeMicroseconds-impl(long paramLong)
  {
    return toLong-impl(paramLong, DurationUnit.MICROSECONDS);
  }
  
  public static final long getInWholeMilliseconds-impl(long paramLong)
  {
    if ((isInMillis-impl(paramLong)) && (isFinite-impl(paramLong))) {
      paramLong = getValue-impl(paramLong);
    } else {
      paramLong = toLong-impl(paramLong, DurationUnit.MILLISECONDS);
    }
    return paramLong;
  }
  
  public static final long getInWholeMinutes-impl(long paramLong)
  {
    return toLong-impl(paramLong, DurationUnit.MINUTES);
  }
  
  public static final long getInWholeNanoseconds-impl(long paramLong)
  {
    long l = getValue-impl(paramLong);
    if (isInNanos-impl(paramLong)) {
      paramLong = l;
    } else if (l > 9223372036854L) {
      paramLong = Long.MAX_VALUE;
    } else if (l < -9223372036854L) {
      paramLong = Long.MIN_VALUE;
    } else {
      paramLong = DurationKt.access$millisToNanos(l);
    }
    return paramLong;
  }
  
  public static final long getInWholeSeconds-impl(long paramLong)
  {
    return toLong-impl(paramLong, DurationUnit.SECONDS);
  }
  
  public static final int getMinutesComponent-impl(long paramLong)
  {
    int i;
    if (isInfinite-impl(paramLong)) {
      i = 0;
    } else {
      i = (int)(getInWholeMinutes-impl(paramLong) % 60);
    }
    return i;
  }
  
  public static final int getNanosecondsComponent-impl(long paramLong)
  {
    int i;
    if (isInfinite-impl(paramLong)) {
      i = 0;
    } else if (isInMillis-impl(paramLong)) {
      i = (int)DurationKt.access$millisToNanos(getValue-impl(paramLong) % 'Ϩ');
    } else {
      i = (int)(getValue-impl(paramLong) % 1000000000);
    }
    return i;
  }
  
  public static final int getSecondsComponent-impl(long paramLong)
  {
    int i;
    if (isInfinite-impl(paramLong)) {
      i = 0;
    } else {
      i = (int)(getInWholeSeconds-impl(paramLong) % 60);
    }
    return i;
  }
  
  private static final DurationUnit getStorageUnit-impl(long paramLong)
  {
    DurationUnit localDurationUnit;
    if (isInNanos-impl(paramLong)) {
      localDurationUnit = DurationUnit.NANOSECONDS;
    } else {
      localDurationUnit = DurationUnit.MILLISECONDS;
    }
    return localDurationUnit;
  }
  
  private static final int getUnitDiscriminator-impl(long paramLong)
  {
    return (int)paramLong & 0x1;
  }
  
  private static final long getValue-impl(long paramLong)
  {
    return paramLong >> 1;
  }
  
  public static int hashCode-impl(long paramLong)
  {
    return (int)(paramLong >>> 32 ^ paramLong);
  }
  
  public static final boolean isFinite-impl(long paramLong)
  {
    return isInfinite-impl(paramLong) ^ true;
  }
  
  private static final boolean isInMillis-impl(long paramLong)
  {
    int i = (int)paramLong;
    boolean bool = true;
    if ((i & 0x1) != 1) {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean isInNanos-impl(long paramLong)
  {
    int i = (int)paramLong;
    boolean bool = true;
    if ((i & 0x1) != 0) {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isInfinite-impl(long paramLong)
  {
    boolean bool;
    if ((paramLong != INFINITE) && (paramLong != NEG_INFINITE)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final boolean isNegative-impl(long paramLong)
  {
    boolean bool;
    if (paramLong < 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isPositive-impl(long paramLong)
  {
    boolean bool;
    if (paramLong > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final long minus-LRDsOJo(long paramLong1, long paramLong2)
  {
    return plus-LRDsOJo(paramLong1, unaryMinus-UwyO8pc(paramLong2));
  }
  
  public static final long plus-LRDsOJo(long paramLong1, long paramLong2)
  {
    if (isInfinite-impl(paramLong1))
    {
      if ((!isFinite-impl(paramLong2)) && ((paramLong1 ^ paramLong2) < 0L)) {
        throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
      }
      return paramLong1;
    }
    if (isInfinite-impl(paramLong2)) {
      return paramLong2;
    }
    if (((int)paramLong1 & 0x1) == ((int)paramLong2 & 0x1))
    {
      paramLong2 = getValue-impl(paramLong1) + getValue-impl(paramLong2);
      if (isInNanos-impl(paramLong1)) {
        paramLong1 = DurationKt.access$durationOfNanosNormalized(paramLong2);
      } else {
        paramLong1 = DurationKt.access$durationOfMillisNormalized(paramLong2);
      }
    }
    else if (isInMillis-impl(paramLong1))
    {
      paramLong1 = addValuesMixedRanges-UwyO8pc(paramLong1, getValue-impl(paramLong1), getValue-impl(paramLong2));
    }
    else
    {
      paramLong1 = addValuesMixedRanges-UwyO8pc(paramLong1, getValue-impl(paramLong2), getValue-impl(paramLong1));
    }
    return paramLong1;
  }
  
  public static final long times-UwyO8pc(long paramLong, double paramDouble)
  {
    int j = MathKt.roundToInt(paramDouble);
    int i;
    if (j == paramDouble) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return times-UwyO8pc(paramLong, j);
    }
    DurationUnit localDurationUnit = getStorageUnit-impl(paramLong);
    return DurationKt.toDuration(toDouble-impl(paramLong, localDurationUnit) * paramDouble, localDurationUnit);
  }
  
  public static final long times-UwyO8pc(long paramLong, int paramInt)
  {
    if (isInfinite-impl(paramLong))
    {
      if (paramInt != 0)
      {
        if (paramInt <= 0) {
          paramLong = unaryMinus-UwyO8pc(paramLong);
        }
        return paramLong;
      }
      throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
    }
    if (paramInt == 0) {
      return ZERO;
    }
    long l1 = getValue-impl(paramLong);
    long l2 = paramInt * l1;
    if (isInNanos-impl(paramLong))
    {
      if (new LongRange(-2147483647L, 2147483647L).contains(l1))
      {
        paramLong = DurationKt.access$durationOfNanos(l2);
      }
      else if (l2 / paramInt == l1)
      {
        paramLong = DurationKt.access$durationOfNanosNormalized(l2);
      }
      else
      {
        paramLong = DurationKt.access$nanosToMillis(l1);
        long l3 = DurationKt.access$millisToNanos(paramLong);
        l2 = paramInt * paramLong;
        l3 = DurationKt.access$nanosToMillis(paramInt * (l1 - l3)) + l2;
        if ((l2 / paramInt == paramLong) && ((l3 ^ l2) >= 0L)) {
          paramLong = DurationKt.access$durationOfMillis(RangesKt.coerceIn(l3, (ClosedRange)new LongRange(-4611686018427387903L, 4611686018427387903L)));
        } else if (MathKt.getSign(l1) * MathKt.getSign(paramInt) > 0) {
          paramLong = INFINITE;
        } else {
          paramLong = NEG_INFINITE;
        }
      }
    }
    else if (l2 / paramInt == l1) {
      paramLong = DurationKt.access$durationOfMillis(RangesKt.coerceIn(l2, (ClosedRange)new LongRange(-4611686018427387903L, 4611686018427387903L)));
    } else if (MathKt.getSign(l1) * MathKt.getSign(paramInt) > 0) {
      paramLong = INFINITE;
    } else {
      paramLong = NEG_INFINITE;
    }
    return paramLong;
  }
  
  public static final <T> T toComponents-impl(long paramLong, Function2<? super Long, ? super Integer, ? extends T> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    return (T)paramFunction2.invoke(Long.valueOf(getInWholeSeconds-impl(paramLong)), Integer.valueOf(getNanosecondsComponent-impl(paramLong)));
  }
  
  public static final <T> T toComponents-impl(long paramLong, Function3<? super Long, ? super Integer, ? super Integer, ? extends T> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramFunction3, "action");
    return (T)paramFunction3.invoke(Long.valueOf(getInWholeMinutes-impl(paramLong)), Integer.valueOf(getSecondsComponent-impl(paramLong)), Integer.valueOf(getNanosecondsComponent-impl(paramLong)));
  }
  
  public static final <T> T toComponents-impl(long paramLong, Function4<? super Long, ? super Integer, ? super Integer, ? super Integer, ? extends T> paramFunction4)
  {
    Intrinsics.checkNotNullParameter(paramFunction4, "action");
    return (T)paramFunction4.invoke(Long.valueOf(getInWholeHours-impl(paramLong)), Integer.valueOf(getMinutesComponent-impl(paramLong)), Integer.valueOf(getSecondsComponent-impl(paramLong)), Integer.valueOf(getNanosecondsComponent-impl(paramLong)));
  }
  
  public static final <T> T toComponents-impl(long paramLong, Function5<? super Long, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> paramFunction5)
  {
    Intrinsics.checkNotNullParameter(paramFunction5, "action");
    return (T)paramFunction5.invoke(Long.valueOf(getInWholeDays-impl(paramLong)), Integer.valueOf(getHoursComponent-impl(paramLong)), Integer.valueOf(getMinutesComponent-impl(paramLong)), Integer.valueOf(getSecondsComponent-impl(paramLong)), Integer.valueOf(getNanosecondsComponent-impl(paramLong)));
  }
  
  public static final double toDouble-impl(long paramLong, DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "unit");
    double d;
    if (paramLong == INFINITE) {
      d = Double.POSITIVE_INFINITY;
    } else if (paramLong == NEG_INFINITE) {
      d = Double.NEGATIVE_INFINITY;
    } else {
      d = DurationUnitKt.convertDurationUnit(getValue-impl(paramLong), getStorageUnit-impl(paramLong), paramDurationUnit);
    }
    return d;
  }
  
  public static final int toInt-impl(long paramLong, DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "unit");
    return (int)RangesKt.coerceIn(toLong-impl(paramLong, paramDurationUnit), -2147483648L, 2147483647L);
  }
  
  public static final String toIsoString-impl(long paramLong)
  {
    Object localObject = new StringBuilder();
    if (isNegative-impl(paramLong)) {
      ((StringBuilder)localObject).append('-');
    }
    ((StringBuilder)localObject).append("PT");
    long l2 = getAbsoluteValue-UwyO8pc(paramLong);
    long l1 = getInWholeHours-impl(l2);
    int n = getMinutesComponent-impl(l2);
    int i2 = getSecondsComponent-impl(l2);
    int i1 = getNanosecondsComponent-impl(l2);
    if (isInfinite-impl(paramLong)) {
      l1 = 9999999999999L;
    }
    int m = 1;
    int i;
    if (l1 != 0L) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if ((i2 == 0) && (i1 == 0)) {
      j = 0;
    } else {
      j = 1;
    }
    int k = m;
    if (n == 0) {
      if ((j != 0) && (i != 0)) {
        k = m;
      } else {
        k = 0;
      }
    }
    if (i != 0) {
      ((StringBuilder)localObject).append(l1).append('H');
    }
    if (k != 0) {
      ((StringBuilder)localObject).append(n).append('M');
    }
    if ((j == 0) && ((i != 0) || (k != 0))) {
      break label216;
    }
    appendFractional-impl(paramLong, (StringBuilder)localObject, i2, i1, 9, "S", true);
    label216:
    localObject = ((StringBuilder)localObject).toString();
    Intrinsics.checkNotNullExpressionValue(localObject, "StringBuilder().apply(builderAction).toString()");
    return (String)localObject;
  }
  
  public static final long toLong-impl(long paramLong, DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "unit");
    if (paramLong == INFINITE) {
      paramLong = Long.MAX_VALUE;
    } else if (paramLong == NEG_INFINITE) {
      paramLong = Long.MIN_VALUE;
    } else {
      paramLong = DurationUnitKt.convertDurationUnit(getValue-impl(paramLong), getStorageUnit-impl(paramLong), paramDurationUnit);
    }
    return paramLong;
  }
  
  @Deprecated(message="Use inWholeMilliseconds property instead.", replaceWith=@ReplaceWith(expression="this.inWholeMilliseconds", imports={}))
  public static final long toLongMilliseconds-impl(long paramLong)
  {
    return getInWholeMilliseconds-impl(paramLong);
  }
  
  @Deprecated(message="Use inWholeNanoseconds property instead.", replaceWith=@ReplaceWith(expression="this.inWholeNanoseconds", imports={}))
  public static final long toLongNanoseconds-impl(long paramLong)
  {
    return getInWholeNanoseconds-impl(paramLong);
  }
  
  public static String toString-impl(long paramLong)
  {
    Object localObject;
    if (paramLong == 0L)
    {
      localObject = "0s";
    }
    else if (paramLong == INFINITE)
    {
      localObject = "Infinity";
    }
    else if (paramLong == NEG_INFINITE)
    {
      localObject = "-Infinity";
    }
    else
    {
      boolean bool = isNegative-impl(paramLong);
      localObject = new StringBuilder();
      if (bool) {
        ((StringBuilder)localObject).append('-');
      }
      long l2 = getAbsoluteValue-UwyO8pc(paramLong);
      long l1 = getInWholeDays-impl(l2);
      int i5 = getHoursComponent-impl(l2);
      int i4 = getMinutesComponent-impl(l2);
      int i2 = getSecondsComponent-impl(l2);
      int i3 = getNanosecondsComponent-impl(l2);
      int i1 = 0;
      int k;
      if (l1 != 0L) {
        k = 1;
      } else {
        k = 0;
      }
      int m;
      if (i5 != 0) {
        m = 1;
      } else {
        m = 0;
      }
      int n;
      if (i4 != 0) {
        n = 1;
      } else {
        n = 0;
      }
      if ((i2 != 0) || (i3 != 0)) {
        i1 = 1;
      }
      int j = 0;
      if (k != 0)
      {
        ((StringBuilder)localObject).append(l1).append('d');
        j = 0 + 1;
      }
      if (m == 0)
      {
        i = j;
        if (k == 0) {
          break label252;
        }
        if (n == 0)
        {
          i = j;
          if (i1 == 0) {
            break label252;
          }
        }
      }
      if (j > 0) {
        ((StringBuilder)localObject).append(' ');
      }
      ((StringBuilder)localObject).append(i5).append('h');
      int i = j + 1;
      label252:
      if (n == 0)
      {
        j = i;
        if (i1 == 0) {
          break label305;
        }
        if (m == 0)
        {
          j = i;
          if (k == 0) {
            break label305;
          }
        }
      }
      if (i > 0) {
        ((StringBuilder)localObject).append(' ');
      }
      ((StringBuilder)localObject).append(i4).append('m');
      j = i + 1;
      label305:
      if (i1 != 0)
      {
        if (j > 0) {
          ((StringBuilder)localObject).append(' ');
        }
        if ((i2 == 0) && (k == 0) && (m == 0) && (n == 0))
        {
          if (i3 >= 1000000) {
            appendFractional-impl(paramLong, (StringBuilder)localObject, i3 / 1000000, i3 % 1000000, 6, "ms", false);
          } else if (i3 >= 1000) {
            appendFractional-impl(paramLong, (StringBuilder)localObject, i3 / 1000, i3 % 1000, 3, "us", false);
          } else {
            ((StringBuilder)localObject).append(i3).append("ns");
          }
        }
        else {
          appendFractional-impl(paramLong, (StringBuilder)localObject, i2, i3, 9, "s", false);
        }
        j++;
      }
      if ((bool) && (j > 1)) {
        ((StringBuilder)localObject).insert(1, '(').append(')');
      }
      localObject = ((StringBuilder)localObject).toString();
      Intrinsics.checkNotNullExpressionValue(localObject, "StringBuilder().apply(builderAction).toString()");
    }
    return (String)localObject;
  }
  
  public static final String toString-impl(long paramLong, DurationUnit paramDurationUnit, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "unit");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      double d = toDouble-impl(paramLong, paramDurationUnit);
      if (Double.isInfinite(d))
      {
        paramDurationUnit = String.valueOf(d);
        Log5ECF72.a(paramDurationUnit);
        LogE84000.a(paramDurationUnit);
        Log229316.a(paramDurationUnit);
        return paramDurationUnit;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      String str = DurationJvmKt.formatToExactDecimals(d, RangesKt.coerceAtMost(paramInt, 12));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder = localStringBuilder.append(str);
      paramDurationUnit = DurationUnitKt.shortName(paramDurationUnit);
      Log5ECF72.a(paramDurationUnit);
      LogE84000.a(paramDurationUnit);
      Log229316.a(paramDurationUnit);
      return paramDurationUnit;
    }
    throw new IllegalArgumentException(("decimals must be not negative, but was " + paramInt).toString());
  }
  
  public static final long unaryMinus-UwyO8pc(long paramLong)
  {
    return DurationKt.access$durationOf(-getValue-impl(paramLong), (int)paramLong & 0x1);
  }
  
  public int compareTo-LRDsOJo(long paramLong)
  {
    return compareTo-LRDsOJo(this.rawValue, paramLong);
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.rawValue, paramObject);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.rawValue);
  }
  
  public String toString()
  {
    String str = toString-impl(this.rawValue);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  @Metadata(d1={"\000<\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\b\n\002\020\006\n\002\b\004\n\002\020\b\n\002\b\002\n\002\020\t\n\002\b\027\n\002\030\002\n\002\b\b\n\002\020\016\n\002\b\n\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J \020*\032\0020\r2\006\020+\032\0020\r2\006\020,\032\0020-2\006\020.\032\0020-H\007J\035\020\f\032\0020\0042\006\020+\032\0020\rH\007ø\001\001ø\001\000¢\006\004\b/\020\021J\035\020\f\032\0020\0042\006\020+\032\0020\022H\007ø\001\001ø\001\000¢\006\004\b/\020\024J\035\020\f\032\0020\0042\006\020+\032\0020\025H\007ø\001\001ø\001\000¢\006\004\b/\020\027J\035\020\030\032\0020\0042\006\020+\032\0020\rH\007ø\001\001ø\001\000¢\006\004\b0\020\021J\035\020\030\032\0020\0042\006\020+\032\0020\022H\007ø\001\001ø\001\000¢\006\004\b0\020\024J\035\020\030\032\0020\0042\006\020+\032\0020\025H\007ø\001\001ø\001\000¢\006\004\b0\020\027J\035\020\033\032\0020\0042\006\020+\032\0020\rH\007ø\001\001ø\001\000¢\006\004\b1\020\021J\035\020\033\032\0020\0042\006\020+\032\0020\022H\007ø\001\001ø\001\000¢\006\004\b1\020\024J\035\020\033\032\0020\0042\006\020+\032\0020\025H\007ø\001\001ø\001\000¢\006\004\b1\020\027J\035\020\036\032\0020\0042\006\020+\032\0020\rH\007ø\001\001ø\001\000¢\006\004\b2\020\021J\035\020\036\032\0020\0042\006\020+\032\0020\022H\007ø\001\001ø\001\000¢\006\004\b2\020\024J\035\020\036\032\0020\0042\006\020+\032\0020\025H\007ø\001\001ø\001\000¢\006\004\b2\020\027J\035\020!\032\0020\0042\006\020+\032\0020\rH\007ø\001\001ø\001\000¢\006\004\b3\020\021J\035\020!\032\0020\0042\006\020+\032\0020\022H\007ø\001\001ø\001\000¢\006\004\b3\020\024J\035\020!\032\0020\0042\006\020+\032\0020\025H\007ø\001\001ø\001\000¢\006\004\b3\020\027J\035\020$\032\0020\0042\006\020+\032\0020\rH\007ø\001\001ø\001\000¢\006\004\b4\020\021J\035\020$\032\0020\0042\006\020+\032\0020\022H\007ø\001\001ø\001\000¢\006\004\b4\020\024J\035\020$\032\0020\0042\006\020+\032\0020\025H\007ø\001\001ø\001\000¢\006\004\b4\020\027J\033\0205\032\0020\0042\006\020+\032\00206ø\001\001ø\001\000¢\006\004\b7\0208J\033\0209\032\0020\0042\006\020+\032\00206ø\001\001ø\001\000¢\006\004\b:\0208J\033\020;\032\004\030\0010\0042\006\020+\032\00206ø\001\001ø\001\000¢\006\002\b<J\033\020=\032\004\030\0010\0042\006\020+\032\00206ø\001\001ø\001\000¢\006\002\b>J\035\020'\032\0020\0042\006\020+\032\0020\rH\007ø\001\001ø\001\000¢\006\004\b?\020\021J\035\020'\032\0020\0042\006\020+\032\0020\022H\007ø\001\001ø\001\000¢\006\004\b?\020\024J\035\020'\032\0020\0042\006\020+\032\0020\025H\007ø\001\001ø\001\000¢\006\004\b?\020\027R\031\020\003\032\0020\004ø\001\000ø\001\001¢\006\n\n\002\020\007\032\004\b\005\020\006R\034\020\b\032\0020\004X\004ø\001\000ø\001\001¢\006\n\n\002\020\007\032\004\b\t\020\006R\031\020\n\032\0020\004ø\001\000ø\001\001¢\006\n\n\002\020\007\032\004\b\013\020\006R%\020\f\032\0020\004*\0020\r8Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\016\020\017\032\004\b\020\020\021R%\020\f\032\0020\004*\0020\0228Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\016\020\023\032\004\b\020\020\024R%\020\f\032\0020\004*\0020\0258Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\016\020\026\032\004\b\020\020\027R%\020\030\032\0020\004*\0020\r8Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\031\020\017\032\004\b\032\020\021R%\020\030\032\0020\004*\0020\0228Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\031\020\023\032\004\b\032\020\024R%\020\030\032\0020\004*\0020\0258Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\031\020\026\032\004\b\032\020\027R%\020\033\032\0020\004*\0020\r8Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\034\020\017\032\004\b\035\020\021R%\020\033\032\0020\004*\0020\0228Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\034\020\023\032\004\b\035\020\024R%\020\033\032\0020\004*\0020\0258Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\034\020\026\032\004\b\035\020\027R%\020\036\032\0020\004*\0020\r8Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\037\020\017\032\004\b \020\021R%\020\036\032\0020\004*\0020\0228Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\037\020\023\032\004\b \020\024R%\020\036\032\0020\004*\0020\0258Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\037\020\026\032\004\b \020\027R%\020!\032\0020\004*\0020\r8Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\"\020\017\032\004\b#\020\021R%\020!\032\0020\004*\0020\0228Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\"\020\023\032\004\b#\020\024R%\020!\032\0020\004*\0020\0258Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b\"\020\026\032\004\b#\020\027R%\020$\032\0020\004*\0020\r8Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b%\020\017\032\004\b&\020\021R%\020$\032\0020\004*\0020\0228Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b%\020\023\032\004\b&\020\024R%\020$\032\0020\004*\0020\0258Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b%\020\026\032\004\b&\020\027R%\020'\032\0020\004*\0020\r8Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b(\020\017\032\004\b)\020\021R%\020'\032\0020\004*\0020\0228Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b(\020\023\032\004\b)\020\024R%\020'\032\0020\004*\0020\0258Æ\002X\004ø\001\000ø\001\001¢\006\f\022\004\b(\020\026\032\004\b)\020\027\002\b\n\002\b\031\n\002\b!¨\006@"}, d2={"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE-UwyO8pc", "()J", "J", "NEG_INFINITE", "getNEG_INFINITE-UwyO8pc$kotlin_stdlib", "ZERO", "getZERO-UwyO8pc", "days", "", "getDays-UwyO8pc$annotations", "(D)V", "getDays-UwyO8pc", "(D)J", "", "(I)V", "(I)J", "", "(J)V", "(J)J", "hours", "getHours-UwyO8pc$annotations", "getHours-UwyO8pc", "microseconds", "getMicroseconds-UwyO8pc$annotations", "getMicroseconds-UwyO8pc", "milliseconds", "getMilliseconds-UwyO8pc$annotations", "getMilliseconds-UwyO8pc", "minutes", "getMinutes-UwyO8pc$annotations", "getMinutes-UwyO8pc", "nanoseconds", "getNanoseconds-UwyO8pc$annotations", "getNanoseconds-UwyO8pc", "seconds", "getSeconds-UwyO8pc$annotations", "getSeconds-UwyO8pc", "convert", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "days-UwyO8pc", "hours-UwyO8pc", "microseconds-UwyO8pc", "milliseconds-UwyO8pc", "minutes-UwyO8pc", "nanoseconds-UwyO8pc", "parse", "", "parse-UwyO8pc", "(Ljava/lang/String;)J", "parseIsoString", "parseIsoString-UwyO8pc", "parseIsoStringOrNull", "parseIsoStringOrNull-FghU774", "parseOrNull", "parseOrNull-FghU774", "seconds-UwyO8pc", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    private final long getDays-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.DAYS);
    }
    
    private final long getDays-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.DAYS);
    }
    
    private final long getDays-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.DAYS);
    }
    
    private final long getHours-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.HOURS);
    }
    
    private final long getHours-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.HOURS);
    }
    
    private final long getHours-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.HOURS);
    }
    
    private final long getMicroseconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.MICROSECONDS);
    }
    
    private final long getMicroseconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.MICROSECONDS);
    }
    
    private final long getMicroseconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.MICROSECONDS);
    }
    
    private final long getMilliseconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.MILLISECONDS);
    }
    
    private final long getMilliseconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.MILLISECONDS);
    }
    
    private final long getMilliseconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.MILLISECONDS);
    }
    
    private final long getMinutes-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.MINUTES);
    }
    
    private final long getMinutes-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.MINUTES);
    }
    
    private final long getMinutes-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.MINUTES);
    }
    
    private final long getNanoseconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.NANOSECONDS);
    }
    
    private final long getNanoseconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.NANOSECONDS);
    }
    
    private final long getNanoseconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.NANOSECONDS);
    }
    
    private final long getSeconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.SECONDS);
    }
    
    private final long getSeconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.SECONDS);
    }
    
    private final long getSeconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.SECONDS);
    }
    
    public final double convert(double paramDouble, DurationUnit paramDurationUnit1, DurationUnit paramDurationUnit2)
    {
      Intrinsics.checkNotNullParameter(paramDurationUnit1, "sourceUnit");
      Intrinsics.checkNotNullParameter(paramDurationUnit2, "targetUnit");
      return DurationUnitKt.convertDurationUnit(paramDouble, paramDurationUnit1, paramDurationUnit2);
    }
    
    @Deprecated(message="Use 'Double.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.days", imports={"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long days-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.DAYS);
    }
    
    @Deprecated(message="Use 'Int.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.days", imports={"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long days-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.DAYS);
    }
    
    @Deprecated(message="Use 'Long.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.days", imports={"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long days-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.DAYS);
    }
    
    public final long getINFINITE-UwyO8pc()
    {
      return Duration.access$getINFINITE$cp();
    }
    
    public final long getNEG_INFINITE-UwyO8pc$kotlin_stdlib()
    {
      return Duration.access$getNEG_INFINITE$cp();
    }
    
    public final long getZERO-UwyO8pc()
    {
      return Duration.access$getZERO$cp();
    }
    
    @Deprecated(message="Use 'Double.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.hours", imports={"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long hours-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.HOURS);
    }
    
    @Deprecated(message="Use 'Int.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.hours", imports={"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long hours-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.HOURS);
    }
    
    @Deprecated(message="Use 'Long.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.hours", imports={"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long hours-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.HOURS);
    }
    
    @Deprecated(message="Use 'Double.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long microseconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.MICROSECONDS);
    }
    
    @Deprecated(message="Use 'Int.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long microseconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.MICROSECONDS);
    }
    
    @Deprecated(message="Use 'Long.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long microseconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.MICROSECONDS);
    }
    
    @Deprecated(message="Use 'Double.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long milliseconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.MILLISECONDS);
    }
    
    @Deprecated(message="Use 'Int.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long milliseconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.MILLISECONDS);
    }
    
    @Deprecated(message="Use 'Long.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long milliseconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.MILLISECONDS);
    }
    
    @Deprecated(message="Use 'Double.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long minutes-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.MINUTES);
    }
    
    @Deprecated(message="Use 'Int.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long minutes-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.MINUTES);
    }
    
    @Deprecated(message="Use 'Long.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long minutes-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.MINUTES);
    }
    
    @Deprecated(message="Use 'Double.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long nanoseconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.NANOSECONDS);
    }
    
    @Deprecated(message="Use 'Int.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long nanoseconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.NANOSECONDS);
    }
    
    @Deprecated(message="Use 'Long.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long nanoseconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.NANOSECONDS);
    }
    
    public final long parse-UwyO8pc(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "value");
      try
      {
        long l = DurationKt.access$parseDuration(paramString, false);
        return l;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw new IllegalArgumentException("Invalid duration string format: '" + paramString + "'.", (Throwable)localIllegalArgumentException);
      }
    }
    
    public final long parseIsoString-UwyO8pc(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "value");
      try
      {
        long l = DurationKt.access$parseDuration(paramString, true);
        return l;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw new IllegalArgumentException("Invalid ISO duration string format: '" + paramString + "'.", (Throwable)localIllegalArgumentException);
      }
    }
    
    public final Duration parseIsoStringOrNull-FghU774(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "value");
      try
      {
        paramString = Duration.box-impl(DurationKt.access$parseDuration(paramString, true));
      }
      catch (IllegalArgumentException paramString)
      {
        paramString = (Duration)null;
        paramString = null;
      }
      return paramString;
    }
    
    public final Duration parseOrNull-FghU774(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "value");
      try
      {
        paramString = Duration.box-impl(DurationKt.access$parseDuration(paramString, false));
      }
      catch (IllegalArgumentException paramString)
      {
        paramString = (Duration)null;
        paramString = null;
      }
      return paramString;
    }
    
    @Deprecated(message="Use 'Double.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long seconds-UwyO8pc(double paramDouble)
    {
      return DurationKt.toDuration(paramDouble, DurationUnit.SECONDS);
    }
    
    @Deprecated(message="Use 'Int.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long seconds-UwyO8pc(int paramInt)
    {
      return DurationKt.toDuration(paramInt, DurationUnit.SECONDS);
    }
    
    @Deprecated(message="Use 'Long.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince="1.6")
    public final long seconds-UwyO8pc(long paramLong)
    {
      return DurationKt.toDuration(paramLong, DurationUnit.SECONDS);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/Duration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */