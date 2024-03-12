package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000.\n\000\n\002\020\013\n\002\b\003\n\002\020\021\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\b\n\000\n\002\020\016\n\000\n\002\020\006\n\002\b\002\032\020\020\t\032\0020\0072\006\020\n\032\0020\013H\002\032\030\020\f\032\0020\r2\006\020\016\032\0020\0172\006\020\n\032\0020\013H\000\032\030\020\020\032\0020\r2\006\020\016\032\0020\0172\006\020\n\032\0020\013H\000\"\024\020\000\032\0020\001X\004¢\006\b\n\000\032\004\b\002\020\003\"\034\020\004\032\016\022\n\022\b\022\004\022\0020\0070\0060\005X\004¢\006\004\n\002\020\b¨\006\021"}, d2={"durationAssertionsEnabled", "", "getDurationAssertionsEnabled", "()Z", "precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "createFormatForDecimals", "decimals", "", "formatToExactDecimals", "", "value", "", "formatUpToDecimals", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class DurationJvmKt
{
  private static final boolean durationAssertionsEnabled;
  private static final ThreadLocal<DecimalFormat>[] precisionFormats;
  
  static
  {
    int i = 0;
    durationAssertionsEnabled = false;
    ThreadLocal[] arrayOfThreadLocal = new ThreadLocal[4];
    while (i < 4)
    {
      arrayOfThreadLocal[i] = new ThreadLocal();
      i++;
    }
    precisionFormats = arrayOfThreadLocal;
  }
  
  private static final DecimalFormat createFormatForDecimals(int paramInt)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("0");
    if (paramInt > 0) {
      localDecimalFormat.setMinimumFractionDigits(paramInt);
    }
    localDecimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    return localDecimalFormat;
  }
  
  public static final String formatToExactDecimals(double paramDouble, int paramInt)
  {
    Object localObject1 = precisionFormats;
    if (paramInt < localObject1.length)
    {
      Object localObject2 = localObject1[paramInt];
      localObject1 = ((ThreadLocal)localObject2).get();
      if (localObject1 == null)
      {
        localObject1 = createFormatForDecimals(paramInt);
        ((ThreadLocal)localObject2).set(localObject1);
      }
      else
      {
        Intrinsics.checkNotNullExpressionValue(localObject1, "get() ?: default().also(this::set)");
      }
      localObject1 = (DecimalFormat)localObject1;
    }
    else
    {
      localObject1 = createFormatForDecimals(paramInt);
    }
    localObject1 = ((DecimalFormat)localObject1).format(paramDouble);
    Intrinsics.checkNotNullExpressionValue(localObject1, "format.format(value)");
    return (String)localObject1;
  }
  
  public static final String formatUpToDecimals(double paramDouble, int paramInt)
  {
    Object localObject = createFormatForDecimals(0);
    ((DecimalFormat)localObject).setMaximumFractionDigits(paramInt);
    localObject = ((DecimalFormat)localObject).format(paramDouble);
    Intrinsics.checkNotNullExpressionValue(localObject, "createFormatForDecimals(… }\n        .format(value)");
    return (String)localObject;
  }
  
  public static final boolean getDurationAssertionsEnabled()
  {
    return durationAssertionsEnabled;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/DurationJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */