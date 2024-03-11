package androidx.core.location;

import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public final class LocationCompat
{
  public static final String EXTRA_BEARING_ACCURACY = "bearingAccuracy";
  public static final String EXTRA_IS_MOCK = "mockLocation";
  public static final String EXTRA_SPEED_ACCURACY = "speedAccuracy";
  public static final String EXTRA_VERTICAL_ACCURACY = "verticalAccuracy";
  private static Method sSetIsFromMockProviderMethod;
  
  public static float getBearingAccuracyDegrees(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getBearingAccuracyDegrees(paramLocation);
    }
    paramLocation = paramLocation.getExtras();
    if (paramLocation == null) {
      return 0.0F;
    }
    return paramLocation.getFloat("bearingAccuracy", 0.0F);
  }
  
  public static long getElapsedRealtimeMillis(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return TimeUnit.NANOSECONDS.toMillis(Api17Impl.getElapsedRealtimeNanos(paramLocation));
    }
    long l2 = System.currentTimeMillis() - paramLocation.getTime();
    long l1 = SystemClock.elapsedRealtime();
    if (l2 < 0L) {
      return l1;
    }
    if (l2 > l1) {
      return 0L;
    }
    return l1 - l2;
  }
  
  public static long getElapsedRealtimeNanos(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getElapsedRealtimeNanos(paramLocation);
    }
    return TimeUnit.MILLISECONDS.toNanos(getElapsedRealtimeMillis(paramLocation));
  }
  
  private static Method getSetIsFromMockProviderMethod()
    throws NoSuchMethodException
  {
    if (sSetIsFromMockProviderMethod == null)
    {
      Method localMethod = Location.class.getDeclaredMethod("setIsFromMockProvider", new Class[] { Boolean.TYPE });
      sSetIsFromMockProviderMethod = localMethod;
      localMethod.setAccessible(true);
    }
    return sSetIsFromMockProviderMethod;
  }
  
  public static float getSpeedAccuracyMetersPerSecond(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getSpeedAccuracyMetersPerSecond(paramLocation);
    }
    paramLocation = paramLocation.getExtras();
    if (paramLocation == null) {
      return 0.0F;
    }
    return paramLocation.getFloat("speedAccuracy", 0.0F);
  }
  
  public static float getVerticalAccuracyMeters(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getVerticalAccuracyMeters(paramLocation);
    }
    paramLocation = paramLocation.getExtras();
    if (paramLocation == null) {
      return 0.0F;
    }
    return paramLocation.getFloat("verticalAccuracy", 0.0F);
  }
  
  public static boolean hasBearingAccuracy(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.hasBearingAccuracy(paramLocation);
    }
    paramLocation = paramLocation.getExtras();
    if (paramLocation == null) {
      return false;
    }
    return paramLocation.containsKey("bearingAccuracy");
  }
  
  public static boolean hasSpeedAccuracy(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.hasSpeedAccuracy(paramLocation);
    }
    paramLocation = paramLocation.getExtras();
    if (paramLocation == null) {
      return false;
    }
    return paramLocation.containsKey("speedAccuracy");
  }
  
  public static boolean hasVerticalAccuracy(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.hasVerticalAccuracy(paramLocation);
    }
    paramLocation = paramLocation.getExtras();
    if (paramLocation == null) {
      return false;
    }
    return paramLocation.containsKey("verticalAccuracy");
  }
  
  public static boolean isMock(Location paramLocation)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return Api18Impl.isMock(paramLocation);
    }
    paramLocation = paramLocation.getExtras();
    if (paramLocation == null) {
      return false;
    }
    return paramLocation.getBoolean("mockLocation", false);
  }
  
  public static void setBearingAccuracyDegrees(Location paramLocation, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Api26Impl.setBearingAccuracyDegrees(paramLocation, paramFloat);
    }
    else
    {
      Bundle localBundle2 = paramLocation.getExtras();
      Bundle localBundle1 = localBundle2;
      if (localBundle2 == null)
      {
        paramLocation.setExtras(new Bundle());
        localBundle1 = paramLocation.getExtras();
      }
      localBundle1.putFloat("bearingAccuracy", paramFloat);
    }
  }
  
  public static void setMock(Location paramLocation, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 18)
    {
      try
      {
        getSetIsFromMockProviderMethod().invoke(paramLocation, new Object[] { Boolean.valueOf(paramBoolean) });
      }
      catch (InvocationTargetException paramLocation)
      {
        throw new RuntimeException(paramLocation);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        paramLocation = new IllegalAccessError();
        paramLocation.initCause(localIllegalAccessException);
        throw paramLocation;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        paramLocation = new NoSuchMethodError();
        paramLocation.initCause(localNoSuchMethodException);
        throw paramLocation;
      }
    }
    else
    {
      Bundle localBundle = paramLocation.getExtras();
      if (localBundle == null)
      {
        if (paramBoolean)
        {
          localBundle = new Bundle();
          localBundle.putBoolean("mockLocation", true);
          paramLocation.setExtras(localBundle);
        }
      }
      else if (paramBoolean)
      {
        localBundle.putBoolean("mockLocation", true);
      }
      else
      {
        localBundle.remove("mockLocation");
        if (localBundle.isEmpty()) {
          paramLocation.setExtras(null);
        }
      }
    }
  }
  
  public static void setSpeedAccuracyMetersPerSecond(Location paramLocation, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Api26Impl.setSpeedAccuracyMetersPerSecond(paramLocation, paramFloat);
    }
    else
    {
      Bundle localBundle2 = paramLocation.getExtras();
      Bundle localBundle1 = localBundle2;
      if (localBundle2 == null)
      {
        paramLocation.setExtras(new Bundle());
        localBundle1 = paramLocation.getExtras();
      }
      localBundle1.putFloat("speedAccuracy", paramFloat);
    }
  }
  
  public static void setVerticalAccuracyMeters(Location paramLocation, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Api26Impl.setVerticalAccuracyMeters(paramLocation, paramFloat);
    }
    else
    {
      Bundle localBundle2 = paramLocation.getExtras();
      Bundle localBundle1 = localBundle2;
      if (localBundle2 == null)
      {
        paramLocation.setExtras(new Bundle());
        localBundle1 = paramLocation.getExtras();
      }
      localBundle1.putFloat("verticalAccuracy", paramFloat);
    }
  }
  
  private static class Api17Impl
  {
    static long getElapsedRealtimeNanos(Location paramLocation)
    {
      return paramLocation.getElapsedRealtimeNanos();
    }
  }
  
  private static class Api18Impl
  {
    static boolean isMock(Location paramLocation)
    {
      return paramLocation.isFromMockProvider();
    }
  }
  
  private static class Api26Impl
  {
    static float getBearingAccuracyDegrees(Location paramLocation)
    {
      return paramLocation.getBearingAccuracyDegrees();
    }
    
    static float getSpeedAccuracyMetersPerSecond(Location paramLocation)
    {
      return paramLocation.getSpeedAccuracyMetersPerSecond();
    }
    
    static float getVerticalAccuracyMeters(Location paramLocation)
    {
      return paramLocation.getVerticalAccuracyMeters();
    }
    
    static boolean hasBearingAccuracy(Location paramLocation)
    {
      return paramLocation.hasBearingAccuracy();
    }
    
    static boolean hasSpeedAccuracy(Location paramLocation)
    {
      return paramLocation.hasSpeedAccuracy();
    }
    
    static boolean hasVerticalAccuracy(Location paramLocation)
    {
      return paramLocation.hasVerticalAccuracy();
    }
    
    static void setBearingAccuracyDegrees(Location paramLocation, float paramFloat)
    {
      paramLocation.setBearingAccuracyDegrees(paramFloat);
    }
    
    static void setSpeedAccuracyMetersPerSecond(Location paramLocation, float paramFloat)
    {
      paramLocation.setSpeedAccuracyMetersPerSecond(paramFloat);
    }
    
    static void setVerticalAccuracyMeters(Location paramLocation, float paramFloat)
    {
      paramLocation.setVerticalAccuracyMeters(paramFloat);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/location/LocationCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */