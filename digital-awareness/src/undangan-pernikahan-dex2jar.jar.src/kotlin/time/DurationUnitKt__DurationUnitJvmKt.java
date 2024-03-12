package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000 \n\000\n\002\020\006\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\002\032 \020\000\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\0042\006\020\005\032\0020\004H\001\032 \020\000\032\0020\0062\006\020\002\032\0020\0062\006\020\003\032\0020\0042\006\020\005\032\0020\004H\001\032 \020\007\032\0020\0062\006\020\002\032\0020\0062\006\020\003\032\0020\0042\006\020\005\032\0020\004H\001\032\f\020\b\032\0020\004*\0020\tH\007\032\f\020\n\032\0020\t*\0020\004H\007¨\006\013"}, d2={"convertDurationUnit", "", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "", "convertDurationUnitOverflow", "toDurationUnit", "Ljava/util/concurrent/TimeUnit;", "toTimeUnit", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/time/DurationUnitKt")
class DurationUnitKt__DurationUnitJvmKt
{
  public static final double convertDurationUnit(double paramDouble, DurationUnit paramDurationUnit1, DurationUnit paramDurationUnit2)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit1, "sourceUnit");
    Intrinsics.checkNotNullParameter(paramDurationUnit2, "targetUnit");
    long l = paramDurationUnit2.getTimeUnit$kotlin_stdlib().convert(1L, paramDurationUnit1.getTimeUnit$kotlin_stdlib());
    if (l > 0L) {
      return l * paramDouble;
    }
    return paramDouble / paramDurationUnit1.getTimeUnit$kotlin_stdlib().convert(1L, paramDurationUnit2.getTimeUnit$kotlin_stdlib());
  }
  
  public static final long convertDurationUnit(long paramLong, DurationUnit paramDurationUnit1, DurationUnit paramDurationUnit2)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit1, "sourceUnit");
    Intrinsics.checkNotNullParameter(paramDurationUnit2, "targetUnit");
    return paramDurationUnit2.getTimeUnit$kotlin_stdlib().convert(paramLong, paramDurationUnit1.getTimeUnit$kotlin_stdlib());
  }
  
  public static final long convertDurationUnitOverflow(long paramLong, DurationUnit paramDurationUnit1, DurationUnit paramDurationUnit2)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit1, "sourceUnit");
    Intrinsics.checkNotNullParameter(paramDurationUnit2, "targetUnit");
    return paramDurationUnit2.getTimeUnit$kotlin_stdlib().convert(paramLong, paramDurationUnit1.getTimeUnit$kotlin_stdlib());
  }
  
  public static final DurationUnit toDurationUnit(TimeUnit paramTimeUnit)
  {
    Intrinsics.checkNotNullParameter(paramTimeUnit, "<this>");
    switch (WhenMappings.$EnumSwitchMapping$0[paramTimeUnit.ordinal()])
    {
    default: 
      throw new NoWhenBranchMatchedException();
    case 7: 
      paramTimeUnit = DurationUnit.DAYS;
      break;
    case 6: 
      paramTimeUnit = DurationUnit.HOURS;
      break;
    case 5: 
      paramTimeUnit = DurationUnit.MINUTES;
      break;
    case 4: 
      paramTimeUnit = DurationUnit.SECONDS;
      break;
    case 3: 
      paramTimeUnit = DurationUnit.MILLISECONDS;
      break;
    case 2: 
      paramTimeUnit = DurationUnit.MICROSECONDS;
      break;
    case 1: 
      paramTimeUnit = DurationUnit.NANOSECONDS;
    }
    return paramTimeUnit;
  }
  
  public static final TimeUnit toTimeUnit(DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "<this>");
    return paramDurationUnit.getTimeUnit$kotlin_stdlib();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/DurationUnitKt__DurationUnitJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */