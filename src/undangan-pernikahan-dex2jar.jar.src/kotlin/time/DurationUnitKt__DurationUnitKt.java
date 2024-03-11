package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\f\n\000\n\002\020\013\n\002\b\002\n\002\020\016\n\000\032\030\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\005H\001\032\020\020\006\032\0020\0012\006\020\007\032\0020\bH\001\032\f\020\007\032\0020\b*\0020\001H\001¨\006\t"}, d2={"durationUnitByIsoChar", "Lkotlin/time/DurationUnit;", "isoChar", "", "isTimeComponent", "", "durationUnitByShortName", "shortName", "", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/time/DurationUnitKt")
class DurationUnitKt__DurationUnitKt
  extends DurationUnitKt__DurationUnitJvmKt
{
  public static final DurationUnit durationUnitByIsoChar(char paramChar, boolean paramBoolean)
  {
    DurationUnit localDurationUnit;
    if (!paramBoolean)
    {
      if (paramChar == 'D') {
        localDurationUnit = DurationUnit.DAYS;
      } else {
        throw new IllegalArgumentException("Invalid or unsupported duration ISO non-time unit: " + paramChar);
      }
    }
    else if (paramChar == 'H')
    {
      localDurationUnit = DurationUnit.HOURS;
    }
    else if (paramChar == 'M')
    {
      localDurationUnit = DurationUnit.MINUTES;
    }
    else
    {
      if (paramChar != 'S') {
        break label82;
      }
      localDurationUnit = DurationUnit.SECONDS;
    }
    return localDurationUnit;
    label82:
    throw new IllegalArgumentException("Invalid duration ISO time unit: " + paramChar);
  }
  
  public static final DurationUnit durationUnitByShortName(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "shortName");
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("us"))
      {
        paramString = DurationUnit.MICROSECONDS;
        if (paramString.equals("ns"))
        {
          paramString = DurationUnit.NANOSECONDS;
          if (paramString.equals("ms"))
          {
            paramString = DurationUnit.MILLISECONDS;
            if (paramString.equals("s"))
            {
              paramString = DurationUnit.SECONDS;
              if (paramString.equals("m"))
              {
                paramString = DurationUnit.MINUTES;
                if (paramString.equals("h"))
                {
                  paramString = DurationUnit.HOURS;
                  if (paramString.equals("d"))
                  {
                    paramString = DurationUnit.DAYS;
                    return paramString;
                  }
                }
              }
            }
          }
        }
      }
    }
    throw new IllegalArgumentException("Unknown duration unit short name: " + paramString);
  }
  
  public static final String shortName(DurationUnit paramDurationUnit)
  {
    Intrinsics.checkNotNullParameter(paramDurationUnit, "<this>");
    switch (WhenMappings.$EnumSwitchMapping$0[paramDurationUnit.ordinal()])
    {
    default: 
      throw new IllegalStateException(("Unknown unit: " + paramDurationUnit).toString());
    case 7: 
      paramDurationUnit = "d";
      break;
    case 6: 
      paramDurationUnit = "h";
      break;
    case 5: 
      paramDurationUnit = "m";
      break;
    case 4: 
      paramDurationUnit = "s";
      break;
    case 3: 
      paramDurationUnit = "ms";
      break;
    case 2: 
      paramDurationUnit = "us";
      break;
    case 1: 
      paramDurationUnit = "ns";
    }
    return paramDurationUnit;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/DurationUnitKt__DurationUnitKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */