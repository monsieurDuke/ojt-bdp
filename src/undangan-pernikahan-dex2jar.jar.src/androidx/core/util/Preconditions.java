package androidx.core.util;

import android.text.TextUtils;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class Preconditions
{
  public static void checkArgument(boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw new IllegalArgumentException();
  }
  
  public static void checkArgument(boolean paramBoolean, Object paramObject)
  {
    if (paramBoolean) {
      return;
    }
    paramObject = String.valueOf(paramObject);
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    throw new IllegalArgumentException((String)paramObject);
  }
  
  public static void checkArgument(boolean paramBoolean, String paramString, Object... paramVarArgs)
  {
    if (paramBoolean) {
      return;
    }
    paramString = String.format(paramString, paramVarArgs);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    throw new IllegalArgumentException(paramString);
  }
  
  public static float checkArgumentFinite(float paramFloat, String paramString)
  {
    if (!Float.isNaN(paramFloat))
    {
      if (!Float.isInfinite(paramFloat)) {
        return paramFloat;
      }
      throw new IllegalArgumentException(paramString + " must not be infinite");
    }
    throw new IllegalArgumentException(paramString + " must not be NaN");
  }
  
  public static double checkArgumentInRange(double paramDouble1, double paramDouble2, double paramDouble3, String paramString)
  {
    if (paramDouble1 >= paramDouble2)
    {
      if (paramDouble1 <= paramDouble3) {
        return paramDouble1;
      }
      paramString = String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", new Object[] { paramString, Double.valueOf(paramDouble2), Double.valueOf(paramDouble3) });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      throw new IllegalArgumentException(paramString);
    }
    paramString = String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", new Object[] { paramString, Double.valueOf(paramDouble2), Double.valueOf(paramDouble3) });
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    throw new IllegalArgumentException(paramString);
  }
  
  public static float checkArgumentInRange(float paramFloat1, float paramFloat2, float paramFloat3, String paramString)
  {
    if (paramFloat1 >= paramFloat2)
    {
      if (paramFloat1 <= paramFloat3) {
        return paramFloat1;
      }
      paramString = String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", new Object[] { paramString, Float.valueOf(paramFloat2), Float.valueOf(paramFloat3) });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      throw new IllegalArgumentException(paramString);
    }
    paramString = String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", new Object[] { paramString, Float.valueOf(paramFloat2), Float.valueOf(paramFloat3) });
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    throw new IllegalArgumentException(paramString);
  }
  
  public static int checkArgumentInRange(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    if (paramInt1 >= paramInt2)
    {
      if (paramInt1 <= paramInt3) {
        return paramInt1;
      }
      paramString = String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", new Object[] { paramString, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      throw new IllegalArgumentException(paramString);
    }
    paramString = String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", new Object[] { paramString, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    throw new IllegalArgumentException(paramString);
  }
  
  public static long checkArgumentInRange(long paramLong1, long paramLong2, long paramLong3, String paramString)
  {
    if (paramLong1 >= paramLong2)
    {
      if (paramLong1 <= paramLong3) {
        return paramLong1;
      }
      paramString = String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", new Object[] { paramString, Long.valueOf(paramLong2), Long.valueOf(paramLong3) });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      throw new IllegalArgumentException(paramString);
    }
    paramString = String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", new Object[] { paramString, Long.valueOf(paramLong2), Long.valueOf(paramLong3) });
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    throw new IllegalArgumentException(paramString);
  }
  
  public static int checkArgumentNonnegative(int paramInt)
  {
    if (paramInt >= 0) {
      return paramInt;
    }
    throw new IllegalArgumentException();
  }
  
  public static int checkArgumentNonnegative(int paramInt, String paramString)
  {
    if (paramInt >= 0) {
      return paramInt;
    }
    throw new IllegalArgumentException(paramString);
  }
  
  public static int checkFlagsArgument(int paramInt1, int paramInt2)
  {
    if ((paramInt1 & paramInt2) == paramInt1) {
      return paramInt1;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("Requested flags 0x");
    String str = Integer.toHexString(paramInt1);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append(", but only 0x");
    str = Integer.toHexString(paramInt2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str + " are allowed");
  }
  
  public static <T> T checkNotNull(T paramT)
  {
    if (paramT != null) {
      return paramT;
    }
    throw new NullPointerException();
  }
  
  public static <T> T checkNotNull(T paramT, Object paramObject)
  {
    if (paramT != null) {
      return paramT;
    }
    paramT = String.valueOf(paramObject);
    Log5ECF72.a(paramT);
    LogE84000.a(paramT);
    Log229316.a(paramT);
    throw new NullPointerException(paramT);
  }
  
  public static void checkState(boolean paramBoolean)
  {
    checkState(paramBoolean, null);
  }
  
  public static void checkState(boolean paramBoolean, String paramString)
  {
    if (paramBoolean) {
      return;
    }
    throw new IllegalStateException(paramString);
  }
  
  public static <T extends CharSequence> T checkStringNotEmpty(T paramT)
  {
    if (!TextUtils.isEmpty(paramT)) {
      return paramT;
    }
    throw new IllegalArgumentException();
  }
  
  public static <T extends CharSequence> T checkStringNotEmpty(T paramT, Object paramObject)
  {
    if (!TextUtils.isEmpty(paramT)) {
      return paramT;
    }
    paramT = String.valueOf(paramObject);
    Log5ECF72.a(paramT);
    LogE84000.a(paramT);
    Log229316.a(paramT);
    throw new IllegalArgumentException(paramT);
  }
  
  public static <T extends CharSequence> T checkStringNotEmpty(T paramT, String paramString, Object... paramVarArgs)
  {
    if (!TextUtils.isEmpty(paramT)) {
      return paramT;
    }
    paramT = String.format(paramString, paramVarArgs);
    Log5ECF72.a(paramT);
    LogE84000.a(paramT);
    Log229316.a(paramT);
    throw new IllegalArgumentException(paramT);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/Preconditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */