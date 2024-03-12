package kotlin.time;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.CharRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000>\n\000\n\002\020\t\n\002\b\003\n\002\020\b\n\000\n\002\030\002\n\002\020\006\n\002\b*\n\002\020\016\n\000\n\002\020\013\n\002\b\005\n\002\030\002\n\002\020\f\n\002\b\t\n\002\030\002\n\002\b\004\032 \020#\032\0020\0072\006\020$\032\0020\0012\006\020%\032\0020\005H\002ø\001\000¢\006\002\020&\032\030\020'\032\0020\0072\006\020(\032\0020\001H\002ø\001\000¢\006\002\020\020\032\030\020)\032\0020\0072\006\020*\032\0020\001H\002ø\001\000¢\006\002\020\020\032\030\020+\032\0020\0072\006\020,\032\0020\001H\002ø\001\000¢\006\002\020\020\032\030\020-\032\0020\0072\006\020.\032\0020\001H\002ø\001\000¢\006\002\020\020\032\020\020/\032\0020\0012\006\020*\032\0020\001H\002\032\020\0200\032\0020\0012\006\020.\032\0020\001H\002\032 \0201\032\0020\0072\006\0202\032\002032\006\0204\032\00205H\002ø\001\000¢\006\002\0206\032\020\0207\032\0020\0012\006\0202\032\00203H\002\032)\0208\032\0020\005*\002032\006\0209\032\0020\0052\022\020:\032\016\022\004\022\0020<\022\004\022\002050;H\b\032)\020=\032\00203*\002032\006\0209\032\0020\0052\022\020:\032\016\022\004\022\0020<\022\004\022\002050;H\b\032\037\020>\032\0020\007*\0020\b2\006\020?\032\0020\007H\nø\001\000¢\006\004\b@\020A\032\037\020>\032\0020\007*\0020\0052\006\020?\032\0020\007H\nø\001\000¢\006\004\bB\020C\032\034\020D\032\0020\007*\0020\b2\006\020E\032\0020FH\007ø\001\000¢\006\002\020G\032\034\020D\032\0020\007*\0020\0052\006\020E\032\0020FH\007ø\001\000¢\006\002\020H\032\034\020D\032\0020\007*\0020\0012\006\020E\032\0020FH\007ø\001\000¢\006\002\020I\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\016\020\002\032\0020\001XT¢\006\002\n\000\"\016\020\003\032\0020\001XT¢\006\002\n\000\"\016\020\004\032\0020\005XT¢\006\002\n\000\"!\020\006\032\0020\007*\0020\b8FX\004ø\001\000¢\006\f\022\004\b\t\020\n\032\004\b\013\020\f\"!\020\006\032\0020\007*\0020\0058FX\004ø\001\000¢\006\f\022\004\b\t\020\r\032\004\b\013\020\016\"!\020\006\032\0020\007*\0020\0018FX\004ø\001\000¢\006\f\022\004\b\t\020\017\032\004\b\013\020\020\"!\020\021\032\0020\007*\0020\b8FX\004ø\001\000¢\006\f\022\004\b\022\020\n\032\004\b\023\020\f\"!\020\021\032\0020\007*\0020\0058FX\004ø\001\000¢\006\f\022\004\b\022\020\r\032\004\b\023\020\016\"!\020\021\032\0020\007*\0020\0018FX\004ø\001\000¢\006\f\022\004\b\022\020\017\032\004\b\023\020\020\"!\020\024\032\0020\007*\0020\b8FX\004ø\001\000¢\006\f\022\004\b\025\020\n\032\004\b\026\020\f\"!\020\024\032\0020\007*\0020\0058FX\004ø\001\000¢\006\f\022\004\b\025\020\r\032\004\b\026\020\016\"!\020\024\032\0020\007*\0020\0018FX\004ø\001\000¢\006\f\022\004\b\025\020\017\032\004\b\026\020\020\"!\020\027\032\0020\007*\0020\b8FX\004ø\001\000¢\006\f\022\004\b\030\020\n\032\004\b\031\020\f\"!\020\027\032\0020\007*\0020\0058FX\004ø\001\000¢\006\f\022\004\b\030\020\r\032\004\b\031\020\016\"!\020\027\032\0020\007*\0020\0018FX\004ø\001\000¢\006\f\022\004\b\030\020\017\032\004\b\031\020\020\"!\020\032\032\0020\007*\0020\b8FX\004ø\001\000¢\006\f\022\004\b\033\020\n\032\004\b\034\020\f\"!\020\032\032\0020\007*\0020\0058FX\004ø\001\000¢\006\f\022\004\b\033\020\r\032\004\b\034\020\016\"!\020\032\032\0020\007*\0020\0018FX\004ø\001\000¢\006\f\022\004\b\033\020\017\032\004\b\034\020\020\"!\020\035\032\0020\007*\0020\b8FX\004ø\001\000¢\006\f\022\004\b\036\020\n\032\004\b\037\020\f\"!\020\035\032\0020\007*\0020\0058FX\004ø\001\000¢\006\f\022\004\b\036\020\r\032\004\b\037\020\016\"!\020\035\032\0020\007*\0020\0018FX\004ø\001\000¢\006\f\022\004\b\036\020\017\032\004\b\037\020\020\"!\020 \032\0020\007*\0020\b8FX\004ø\001\000¢\006\f\022\004\b!\020\n\032\004\b\"\020\f\"!\020 \032\0020\007*\0020\0058FX\004ø\001\000¢\006\f\022\004\b!\020\r\032\004\b\"\020\016\"!\020 \032\0020\007*\0020\0018FX\004ø\001\000¢\006\f\022\004\b!\020\017\032\004\b\"\020\020\002\004\n\002\b\031¨\006J"}, d2={"MAX_MILLIS", "", "MAX_NANOS", "MAX_NANOS_IN_MILLIS", "NANOS_IN_MILLIS", "", "days", "Lkotlin/time/Duration;", "", "getDays$annotations", "(D)V", "getDays", "(D)J", "(I)V", "(I)J", "(J)V", "(J)J", "hours", "getHours$annotations", "getHours", "microseconds", "getMicroseconds$annotations", "getMicroseconds", "milliseconds", "getMilliseconds$annotations", "getMilliseconds", "minutes", "getMinutes$annotations", "getMinutes", "nanoseconds", "getNanoseconds$annotations", "getNanoseconds", "seconds", "getSeconds$annotations", "getSeconds", "durationOf", "normalValue", "unitDiscriminator", "(JI)J", "durationOfMillis", "normalMillis", "durationOfMillisNormalized", "millis", "durationOfNanos", "normalNanos", "durationOfNanosNormalized", "nanos", "millisToNanos", "nanosToMillis", "parseDuration", "value", "", "strictIso", "", "(Ljava/lang/String;Z)J", "parseOverLongIsoComponent", "skipWhile", "startIndex", "predicate", "Lkotlin/Function1;", "", "substringWhile", "times", "duration", "times-kIfJnKk", "(DJ)J", "times-mvk6XK0", "(IJ)J", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLkotlin/time/DurationUnit;)J", "(ILkotlin/time/DurationUnit;)J", "(JLkotlin/time/DurationUnit;)J", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class DurationKt
{
  public static final long MAX_MILLIS = 4611686018427387903L;
  public static final long MAX_NANOS = 4611686018426999999L;
  private static final long MAX_NANOS_IN_MILLIS = 4611686018426L;
  public static final int NANOS_IN_MILLIS = 1000000;
  
  private static final long durationOf(long paramLong, int paramInt)
  {
    return Duration.constructor-impl((paramLong << 1) + paramInt);
  }
  
  private static final long durationOfMillis(long paramLong)
  {
    return Duration.constructor-impl((paramLong << 1) + 1L);
  }
  
  private static final long durationOfMillisNormalized(long paramLong)
  {
    if (new LongRange(-4611686018426L, 4611686018426L).contains(paramLong)) {
      paramLong = durationOfNanos(millisToNanos(paramLong));
    } else {
      paramLong = durationOfMillis(RangesKt.coerceIn(paramLong, -4611686018427387903L, 4611686018427387903L));
    }
    return paramLong;
  }
  
  private static final long durationOfNanos(long paramLong)
  {
    return Duration.constructor-impl(paramLong << 1);
  }
  
  private static final long durationOfNanosNormalized(long paramLong)
  {
    if (new LongRange(-4611686018426999999L, 4611686018426999999L).contains(paramLong)) {
      paramLong = durationOfNanos(paramLong);
    } else {
      paramLong = durationOfMillis(nanosToMillis(paramLong));
    }
    return paramLong;
  }
  
  public static final long getDays(double paramDouble)
  {
    return toDuration(paramDouble, DurationUnit.DAYS);
  }
  
  public static final long getDays(int paramInt)
  {
    return toDuration(paramInt, DurationUnit.DAYS);
  }
  
  public static final long getDays(long paramLong)
  {
    return toDuration(paramLong, DurationUnit.DAYS);
  }
  
  public static final long getHours(double paramDouble)
  {
    return toDuration(paramDouble, DurationUnit.HOURS);
  }
  
  public static final long getHours(int paramInt)
  {
    return toDuration(paramInt, DurationUnit.HOURS);
  }
  
  public static final long getHours(long paramLong)
  {
    return toDuration(paramLong, DurationUnit.HOURS);
  }
  
  public static final long getMicroseconds(double paramDouble)
  {
    return toDuration(paramDouble, DurationUnit.MICROSECONDS);
  }
  
  public static final long getMicroseconds(int paramInt)
  {
    return toDuration(paramInt, DurationUnit.MICROSECONDS);
  }
  
  public static final long getMicroseconds(long paramLong)
  {
    return toDuration(paramLong, DurationUnit.MICROSECONDS);
  }
  
  public static final long getMilliseconds(double paramDouble)
  {
    return toDuration(paramDouble, DurationUnit.MILLISECONDS);
  }
  
  public static final long getMilliseconds(int paramInt)
  {
    return toDuration(paramInt, DurationUnit.MILLISECONDS);
  }
  
  public static final long getMilliseconds(long paramLong)
  {
    return toDuration(paramLong, DurationUnit.MILLISECONDS);
  }
  
  public static final long getMinutes(double paramDouble)
  {
    return toDuration(paramDouble, DurationUnit.MINUTES);
  }
  
  public static final long getMinutes(int paramInt)
  {
    return toDuration(paramInt, DurationUnit.MINUTES);
  }
  
  public static final long getMinutes(long paramLong)
  {
    return toDuration(paramLong, DurationUnit.MINUTES);
  }
  
  public static final long getNanoseconds(double paramDouble)
  {
    return toDuration(paramDouble, DurationUnit.NANOSECONDS);
  }
  
  public static final long getNanoseconds(int paramInt)
  {
    return toDuration(paramInt, DurationUnit.NANOSECONDS);
  }
  
  public static final long getNanoseconds(long paramLong)
  {
    return toDuration(paramLong, DurationUnit.NANOSECONDS);
  }
  
  public static final long getSeconds(double paramDouble)
  {
    return toDuration(paramDouble, DurationUnit.SECONDS);
  }
  
  public static final long getSeconds(int paramInt)
  {
    return toDuration(paramInt, DurationUnit.SECONDS);
  }
  
  public static final long getSeconds(long paramLong)
  {
    return toDuration(paramLong, DurationUnit.SECONDS);
  }
  
  private static final long millisToNanos(long paramLong)
  {
    return 1000000 * paramLong;
  }
  
  private static final long nanosToMillis(long paramLong)
  {
    return paramLong / 1000000;
  }
  
  private static final long parseDuration(String paramString, boolean paramBoolean)
  {
    int k = paramString.length();
    if (k != 0)
    {
      int i = 0;
      long l = Duration.Companion.getZERO-UwyO8pc();
      Object localObject1 = "Infinity";
      int j = paramString.charAt(0);
      if (j == 43) {}
      while (j == 45)
      {
        j = 1;
        break;
      }
      j = 0;
      if (j != 0) {
        i = 0 + 1;
      }
      j = i;
      if (j > 0) {
        i = 1;
      } else {
        i = 0;
      }
      int n = i;
      if ((n != 0) && (StringsKt.startsWith$default((CharSequence)paramString, '-', false, 2, null))) {
        i = 1;
      } else {
        i = 0;
      }
      if (k > j)
      {
        int m = paramString.charAt(j);
        Object localObject4 = "Unexpected order of duration components";
        Object localObject3 = "this as java.lang.String…ing(startIndex, endIndex)";
        String str1 = "null cannot be cast to non-null type java.lang.String";
        Object localObject2;
        int i1;
        char c;
        if (m == 80)
        {
          j++;
          if (j != k)
          {
            localObject2 = "+-.";
            paramBoolean = false;
            localObject4 = null;
            m = k;
            while (j < m) {
              if (paramString.charAt(j) == 'T')
              {
                if (!paramBoolean)
                {
                  j++;
                  if (j != m)
                  {
                    paramBoolean = true;
                    continue;
                  }
                }
                throw new IllegalArgumentException();
              }
              else
              {
                Object localObject5 = paramString;
                i1 = j;
                k = i;
                i = n;
                for (n = i1; n < ((String)localObject5).length(); n++)
                {
                  c = ((String)localObject5).charAt(n);
                  if ((!new CharRange('0', '9').contains(c)) && (!StringsKt.contains$default((CharSequence)localObject2, c, false, 2, null))) {
                    i1 = 0;
                  } else {
                    i1 = 1;
                  }
                  if (i1 == 0) {
                    break;
                  }
                }
                Intrinsics.checkNotNull(paramString, str1);
                String str2 = paramString.substring(j, n);
                Intrinsics.checkNotNullExpressionValue(str2, (String)localObject3);
                if (((CharSequence)str2).length() == 0) {
                  n = 1;
                } else {
                  n = 0;
                }
                if (n == 0)
                {
                  j += str2.length();
                  localObject5 = (CharSequence)paramString;
                  if ((j >= 0) && (j <= StringsKt.getLastIndex((CharSequence)localObject5)))
                  {
                    c = ((CharSequence)localObject5).charAt(j);
                    j++;
                    localObject5 = DurationUnitKt.durationUnitByIsoChar(c, paramBoolean);
                    if ((localObject4 != null) && (((DurationUnit)localObject4).compareTo((Enum)localObject5) <= 0)) {
                      throw new IllegalArgumentException("Unexpected order of duration components");
                    }
                    localObject4 = localObject5;
                    n = StringsKt.indexOf$default((CharSequence)str2, '.', 0, false, 6, null);
                    if ((localObject5 == DurationUnit.SECONDS) && (n > 0))
                    {
                      Intrinsics.checkNotNull(str2, str1);
                      String str3 = str2.substring(0, n);
                      Intrinsics.checkNotNullExpressionValue(str3, (String)localObject3);
                      l = Duration.plus-LRDsOJo(l, toDuration(parseOverLongIsoComponent(str3), (DurationUnit)localObject5));
                      Intrinsics.checkNotNull(str2, str1);
                      str2 = str2.substring(n);
                      Intrinsics.checkNotNullExpressionValue(str2, "this as java.lang.String).substring(startIndex)");
                      l = Duration.plus-LRDsOJo(l, toDuration(Double.parseDouble(str2), (DurationUnit)localObject5));
                      n = i;
                      i = k;
                    }
                    else
                    {
                      l = Duration.plus-LRDsOJo(l, toDuration(parseOverLongIsoComponent(str2), (DurationUnit)localObject5));
                      n = i;
                      i = k;
                    }
                  }
                  else
                  {
                    throw new IllegalArgumentException("Missing unit for value " + str2);
                  }
                }
                else
                {
                  throw new IllegalArgumentException();
                }
              }
            }
            m = i;
          }
          else
          {
            throw new IllegalArgumentException();
          }
        }
        else
        {
          m = i;
          if (paramBoolean) {
            break label1286;
          }
          if (StringsKt.regionMatches(paramString, j, "Infinity", 0, Math.max(k - j, "Infinity".length()), true))
          {
            l = Duration.Companion.getINFINITE-UwyO8pc();
          }
          else
          {
            localObject2 = null;
            i1 = 0;
            if (n == 0) {
              i = 1;
            } else {
              i = 0;
            }
            if ((n != 0) && (paramString.charAt(j) == '(') && (StringsKt.last((CharSequence)paramString) == ')'))
            {
              i = 1;
              j++;
              n = k - 1;
              if (j != n)
              {
                k = i1;
                localObject1 = localObject4;
              }
              else
              {
                throw new IllegalArgumentException("No components");
              }
            }
            else
            {
              localObject1 = localObject4;
              n = k;
              k = i1;
            }
            while (j < n)
            {
              if ((k != 0) && (i != 0)) {
                while (j < paramString.length())
                {
                  if (paramString.charAt(j) == ' ') {
                    i1 = 1;
                  } else {
                    i1 = 0;
                  }
                  if (i1 == 0) {
                    break;
                  }
                  j++;
                }
              }
              paramBoolean = false;
              localObject3 = paramString;
              for (k = j; k < ((String)localObject3).length(); k++)
              {
                c = ((String)localObject3).charAt(k);
                if ((!new CharRange('0', '9').contains(c)) && (c != '.')) {
                  i1 = 0;
                } else {
                  i1 = 1;
                }
                if (i1 == 0) {
                  break;
                }
              }
              Intrinsics.checkNotNull(paramString, "null cannot be cast to non-null type java.lang.String");
              str1 = paramString.substring(j, k);
              Intrinsics.checkNotNullExpressionValue(str1, "this as java.lang.String…ing(startIndex, endIndex)");
              if (((CharSequence)str1).length() == 0) {
                k = 1;
              } else {
                k = 0;
              }
              if (k == 0)
              {
                k = j + str1.length();
                int i2 = 0;
                localObject3 = paramString;
                paramBoolean = false;
                for (j = k; j < ((String)localObject3).length(); j++)
                {
                  c = ((String)localObject3).charAt(j);
                  if (!new CharRange('a', 'z').contains(c)) {
                    break;
                  }
                }
                Intrinsics.checkNotNull(paramString, "null cannot be cast to non-null type java.lang.String");
                localObject3 = paramString.substring(k, j);
                Intrinsics.checkNotNullExpressionValue(localObject3, "this as java.lang.String…ing(startIndex, endIndex)");
                j = k + ((String)localObject3).length();
                localObject3 = DurationUnitKt.durationUnitByShortName((String)localObject3);
                if ((localObject2 != null) && (((DurationUnit)localObject2).compareTo((Enum)localObject3) <= 0)) {
                  throw new IllegalArgumentException((String)localObject1);
                }
                localObject2 = localObject3;
                k = StringsKt.indexOf$default((CharSequence)str1, '.', 0, false, 6, null);
                if (k > 0)
                {
                  Intrinsics.checkNotNull(str1, "null cannot be cast to non-null type java.lang.String");
                  localObject4 = str1.substring(0, k);
                  Intrinsics.checkNotNullExpressionValue(localObject4, "this as java.lang.String…ing(startIndex, endIndex)");
                  l = Duration.plus-LRDsOJo(l, toDuration(Long.parseLong((String)localObject4), (DurationUnit)localObject3));
                  Intrinsics.checkNotNull(str1, "null cannot be cast to non-null type java.lang.String");
                  str1 = str1.substring(k);
                  Intrinsics.checkNotNullExpressionValue(str1, "this as java.lang.String).substring(startIndex)");
                  l = Duration.plus-LRDsOJo(l, toDuration(Double.parseDouble(str1), (DurationUnit)localObject3));
                  if (j >= n) {
                    k = 1;
                  } else {
                    throw new IllegalArgumentException("Fractional component must be last");
                  }
                }
                else
                {
                  l = Duration.plus-LRDsOJo(l, toDuration(Long.parseLong(str1), (DurationUnit)localObject3));
                  k = 1;
                }
              }
              else
              {
                throw new IllegalArgumentException();
              }
            }
          }
        }
        if (m != 0) {
          l = Duration.unaryMinus-UwyO8pc(l);
        }
        return l;
        label1286:
        throw new IllegalArgumentException();
      }
      throw new IllegalArgumentException("No components");
    }
    throw new IllegalArgumentException("The string is empty");
  }
  
  private static final long parseOverLongIsoComponent(String paramString)
  {
    int k = paramString.length();
    int j = 0;
    int i = j;
    if (k > 0)
    {
      i = j;
      if (StringsKt.contains$default((CharSequence)"+-", paramString.charAt(0), false, 2, null)) {
        i = 0 + 1;
      }
    }
    label150:
    long l;
    if (k - i > 16)
    {
      Object localObject = (Iterable)new IntRange(i, StringsKt.getLastIndex((CharSequence)paramString));
      if (((localObject instanceof Collection)) && (((Collection)localObject).isEmpty()))
      {
        i = 1;
      }
      else
      {
        localObject = ((Iterable)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          i = ((IntIterator)localObject).nextInt();
          if (!new CharRange('0', '9').contains(paramString.charAt(i)))
          {
            i = 0;
            break label150;
          }
        }
        i = 1;
      }
      if (i != 0)
      {
        if (paramString.charAt(0) == '-') {
          l = Long.MIN_VALUE;
        } else {
          l = Long.MAX_VALUE;
        }
        return l;
      }
    }
    if (StringsKt.startsWith$default(paramString, "+", false, 2, null))
    {
      paramString = StringsKt.drop(paramString, 1);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      l = Long.parseLong(paramString);
    }
    else
    {
      l = Long.parseLong(paramString);
    }
    return l;
  }
  
  private static final int skipWhile(String paramString, int paramInt, Function1<? super Character, Boolean> paramFunction1)
  {
    while ((paramInt < paramString.length()) && (((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(paramInt)))).booleanValue())) {
      paramInt++;
    }
    return paramInt;
  }
  
  private static final String substringWhile(String paramString, int paramInt, Function1<? super Character, Boolean> paramFunction1)
  {
    for (int i = paramInt; (i < paramString.length()) && (((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(i)))).booleanValue()); i++) {}
    Intrinsics.checkNotNull(paramString, "null cannot be cast to non-null type java.lang.String");
    paramString = paramString.substring(paramInt, i);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
    return paramString;
  }
  
  private static final long times-kIfJnKk(double paramDouble, long paramLong)
  {
    return Duration.times-UwyO8pc(paramLong, paramDouble);
  }
  
  private static final long times-mvk6XK0(int paramInt, long paramLong)
  {
    return Duration.times-UwyO8pc(paramLong, paramInt);
  }
  
  public static final long toDuration(double paramDouble, DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "unit");
    double d = DurationUnitKt.convertDurationUnit(paramDouble, paramDurationUnit, DurationUnit.NANOSECONDS);
    if ((Double.isNaN(d) ^ true))
    {
      long l = MathKt.roundToLong(d);
      if (new LongRange(-4611686018426999999L, 4611686018426999999L).contains(l)) {
        l = durationOfNanos(l);
      } else {
        l = durationOfMillisNormalized(MathKt.roundToLong(DurationUnitKt.convertDurationUnit(paramDouble, paramDurationUnit, DurationUnit.MILLISECONDS)));
      }
      return l;
    }
    throw new IllegalArgumentException("Duration value cannot be NaN.".toString());
  }
  
  public static final long toDuration(int paramInt, DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "unit");
    long l;
    if (paramDurationUnit.compareTo((Enum)DurationUnit.SECONDS) <= 0) {
      l = durationOfNanos(DurationUnitKt.convertDurationUnitOverflow(paramInt, paramDurationUnit, DurationUnit.NANOSECONDS));
    } else {
      l = toDuration(paramInt, paramDurationUnit);
    }
    return l;
  }
  
  public static final long toDuration(long paramLong, DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "unit");
    long l = DurationUnitKt.convertDurationUnitOverflow(4611686018426999999L, DurationUnit.NANOSECONDS, paramDurationUnit);
    if (new LongRange(-l, l).contains(paramLong)) {
      return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow(paramLong, paramDurationUnit, DurationUnit.NANOSECONDS));
    }
    return durationOfMillis(RangesKt.coerceIn(DurationUnitKt.convertDurationUnit(paramLong, paramDurationUnit, DurationUnit.MILLISECONDS), -4611686018427387903L, 4611686018427387903L));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/DurationKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */