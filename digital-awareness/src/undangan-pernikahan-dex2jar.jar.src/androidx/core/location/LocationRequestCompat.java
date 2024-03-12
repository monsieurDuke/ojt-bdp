package androidx.core.location;

import android.location.LocationRequest;
import android.location.LocationRequest.Builder;
import android.os.Build.VERSION;
import androidx.core.util.Preconditions;
import androidx.core.util.TimeUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class LocationRequestCompat
{
  private static final long IMPLICIT_MIN_UPDATE_INTERVAL = -1L;
  public static final long PASSIVE_INTERVAL = Long.MAX_VALUE;
  public static final int QUALITY_BALANCED_POWER_ACCURACY = 102;
  public static final int QUALITY_HIGH_ACCURACY = 100;
  public static final int QUALITY_LOW_POWER = 104;
  final long mDurationMillis;
  final long mIntervalMillis;
  final long mMaxUpdateDelayMillis;
  final int mMaxUpdates;
  final float mMinUpdateDistanceMeters;
  final long mMinUpdateIntervalMillis;
  final int mQuality;
  
  LocationRequestCompat(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, float paramFloat, long paramLong4)
  {
    this.mIntervalMillis = paramLong1;
    this.mQuality = paramInt1;
    this.mMinUpdateIntervalMillis = paramLong3;
    this.mDurationMillis = paramLong2;
    this.mMaxUpdates = paramInt2;
    this.mMinUpdateDistanceMeters = paramFloat;
    this.mMaxUpdateDelayMillis = paramLong4;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof LocationRequestCompat)) {
      return false;
    }
    paramObject = (LocationRequestCompat)paramObject;
    if ((this.mQuality != ((LocationRequestCompat)paramObject).mQuality) || (this.mIntervalMillis != ((LocationRequestCompat)paramObject).mIntervalMillis) || (this.mMinUpdateIntervalMillis != ((LocationRequestCompat)paramObject).mMinUpdateIntervalMillis) || (this.mDurationMillis != ((LocationRequestCompat)paramObject).mDurationMillis) || (this.mMaxUpdates != ((LocationRequestCompat)paramObject).mMaxUpdates) || (Float.compare(((LocationRequestCompat)paramObject).mMinUpdateDistanceMeters, this.mMinUpdateDistanceMeters) != 0) || (this.mMaxUpdateDelayMillis != ((LocationRequestCompat)paramObject).mMaxUpdateDelayMillis)) {
      bool = false;
    }
    return bool;
  }
  
  public long getDurationMillis()
  {
    return this.mDurationMillis;
  }
  
  public long getIntervalMillis()
  {
    return this.mIntervalMillis;
  }
  
  public long getMaxUpdateDelayMillis()
  {
    return this.mMaxUpdateDelayMillis;
  }
  
  public int getMaxUpdates()
  {
    return this.mMaxUpdates;
  }
  
  public float getMinUpdateDistanceMeters()
  {
    return this.mMinUpdateDistanceMeters;
  }
  
  public long getMinUpdateIntervalMillis()
  {
    long l = this.mMinUpdateIntervalMillis;
    if (l == -1L) {
      return this.mIntervalMillis;
    }
    return l;
  }
  
  public int getQuality()
  {
    return this.mQuality;
  }
  
  public int hashCode()
  {
    int j = this.mQuality;
    long l = this.mIntervalMillis;
    int i = (int)(l ^ l >>> 32);
    l = this.mMinUpdateIntervalMillis;
    return (j * 31 + i) * 31 + (int)(l ^ l >>> 32);
  }
  
  public LocationRequest toLocationRequest()
  {
    return Api31Impl.toLocationRequest(this);
  }
  
  public LocationRequest toLocationRequest(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 31) {
      return toLocationRequest();
    }
    return (LocationRequest)Api19Impl.toLocationRequest(this, paramString);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Request[");
    if (this.mIntervalMillis != Long.MAX_VALUE)
    {
      localStringBuilder.append("@");
      TimeUtils.formatDuration(this.mIntervalMillis, localStringBuilder);
      switch (this.mQuality)
      {
      case 101: 
      case 103: 
      default: 
        break;
      case 104: 
        localStringBuilder.append(" LOW_POWER");
        break;
      case 102: 
        localStringBuilder.append(" BALANCED");
        break;
      case 100: 
        localStringBuilder.append(" HIGH_ACCURACY");
      }
    }
    else
    {
      localStringBuilder.append("PASSIVE");
    }
    if (this.mDurationMillis != Long.MAX_VALUE)
    {
      localStringBuilder.append(", duration=");
      TimeUtils.formatDuration(this.mDurationMillis, localStringBuilder);
    }
    if (this.mMaxUpdates != Integer.MAX_VALUE) {
      localStringBuilder.append(", maxUpdates=").append(this.mMaxUpdates);
    }
    long l = this.mMinUpdateIntervalMillis;
    if ((l != -1L) && (l < this.mIntervalMillis))
    {
      localStringBuilder.append(", minUpdateInterval=");
      TimeUtils.formatDuration(this.mMinUpdateIntervalMillis, localStringBuilder);
    }
    if (this.mMinUpdateDistanceMeters > 0.0D) {
      localStringBuilder.append(", minUpdateDistance=").append(this.mMinUpdateDistanceMeters);
    }
    if (this.mMaxUpdateDelayMillis / 2L > this.mIntervalMillis)
    {
      localStringBuilder.append(", maxUpdateDelay=");
      TimeUtils.formatDuration(this.mMaxUpdateDelayMillis, localStringBuilder);
    }
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
  
  private static class Api19Impl
  {
    private static Method sCreateFromDeprecatedProviderMethod;
    private static Class<?> sLocationRequestClass;
    private static Method sSetExpireInMethod;
    private static Method sSetFastestIntervalMethod;
    private static Method sSetNumUpdatesMethod;
    private static Method sSetQualityMethod;
    
    public static Object toLocationRequest(LocationRequestCompat paramLocationRequestCompat, String paramString)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        try
        {
          if (sLocationRequestClass == null) {
            sLocationRequestClass = Class.forName("android.location.LocationRequest");
          }
          Method localMethod;
          if (sCreateFromDeprecatedProviderMethod == null)
          {
            localMethod = sLocationRequestClass.getDeclaredMethod("createFromDeprecatedProvider", new Class[] { String.class, Long.TYPE, Float.TYPE, Boolean.TYPE });
            sCreateFromDeprecatedProviderMethod = localMethod;
            localMethod.setAccessible(true);
          }
          paramString = sCreateFromDeprecatedProviderMethod.invoke(null, new Object[] { paramString, Long.valueOf(paramLocationRequestCompat.getIntervalMillis()), Float.valueOf(paramLocationRequestCompat.getMinUpdateDistanceMeters()), Boolean.valueOf(false) });
          if (paramString == null) {
            return null;
          }
          if (sSetQualityMethod == null)
          {
            localMethod = sLocationRequestClass.getDeclaredMethod("setQuality", new Class[] { Integer.TYPE });
            sSetQualityMethod = localMethod;
            localMethod.setAccessible(true);
          }
          sSetQualityMethod.invoke(paramString, new Object[] { Integer.valueOf(paramLocationRequestCompat.getQuality()) });
          if (sSetFastestIntervalMethod == null)
          {
            localMethod = sLocationRequestClass.getDeclaredMethod("setFastestInterval", new Class[] { Long.TYPE });
            sSetFastestIntervalMethod = localMethod;
            localMethod.setAccessible(true);
          }
          sSetFastestIntervalMethod.invoke(paramString, new Object[] { Long.valueOf(paramLocationRequestCompat.getMinUpdateIntervalMillis()) });
          if (paramLocationRequestCompat.getMaxUpdates() < Integer.MAX_VALUE)
          {
            if (sSetNumUpdatesMethod == null)
            {
              localMethod = sLocationRequestClass.getDeclaredMethod("setNumUpdates", new Class[] { Integer.TYPE });
              sSetNumUpdatesMethod = localMethod;
              localMethod.setAccessible(true);
            }
            sSetNumUpdatesMethod.invoke(paramString, new Object[] { Integer.valueOf(paramLocationRequestCompat.getMaxUpdates()) });
          }
          if (paramLocationRequestCompat.getDurationMillis() < Long.MAX_VALUE)
          {
            if (sSetExpireInMethod == null)
            {
              localMethod = sLocationRequestClass.getDeclaredMethod("setExpireIn", new Class[] { Long.TYPE });
              sSetExpireInMethod = localMethod;
              localMethod.setAccessible(true);
            }
            sSetExpireInMethod.invoke(paramString, new Object[] { Long.valueOf(paramLocationRequestCompat.getDurationMillis()) });
          }
          return paramString;
        }
        catch (ClassNotFoundException paramLocationRequestCompat) {}catch (IllegalAccessException paramLocationRequestCompat) {}catch (InvocationTargetException paramLocationRequestCompat) {}catch (NoSuchMethodException paramLocationRequestCompat) {}
      }
      return null;
    }
  }
  
  private static class Api31Impl
  {
    public static LocationRequest toLocationRequest(LocationRequestCompat paramLocationRequestCompat)
    {
      return new LocationRequest.Builder(paramLocationRequestCompat.getIntervalMillis()).setQuality(paramLocationRequestCompat.getQuality()).setMinUpdateIntervalMillis(paramLocationRequestCompat.getMinUpdateIntervalMillis()).setDurationMillis(paramLocationRequestCompat.getDurationMillis()).setMaxUpdates(paramLocationRequestCompat.getMaxUpdates()).setMinUpdateDistanceMeters(paramLocationRequestCompat.getMinUpdateDistanceMeters()).setMaxUpdateDelayMillis(paramLocationRequestCompat.getMaxUpdateDelayMillis()).build();
    }
  }
  
  public static final class Builder
  {
    private long mDurationMillis;
    private long mIntervalMillis;
    private long mMaxUpdateDelayMillis;
    private int mMaxUpdates;
    private float mMinUpdateDistanceMeters;
    private long mMinUpdateIntervalMillis;
    private int mQuality;
    
    public Builder(long paramLong)
    {
      setIntervalMillis(paramLong);
      this.mQuality = 102;
      this.mDurationMillis = Long.MAX_VALUE;
      this.mMaxUpdates = Integer.MAX_VALUE;
      this.mMinUpdateIntervalMillis = -1L;
      this.mMinUpdateDistanceMeters = 0.0F;
      this.mMaxUpdateDelayMillis = 0L;
    }
    
    public Builder(LocationRequestCompat paramLocationRequestCompat)
    {
      this.mIntervalMillis = paramLocationRequestCompat.mIntervalMillis;
      this.mQuality = paramLocationRequestCompat.mQuality;
      this.mDurationMillis = paramLocationRequestCompat.mDurationMillis;
      this.mMaxUpdates = paramLocationRequestCompat.mMaxUpdates;
      this.mMinUpdateIntervalMillis = paramLocationRequestCompat.mMinUpdateIntervalMillis;
      this.mMinUpdateDistanceMeters = paramLocationRequestCompat.mMinUpdateDistanceMeters;
      this.mMaxUpdateDelayMillis = paramLocationRequestCompat.mMaxUpdateDelayMillis;
    }
    
    public LocationRequestCompat build()
    {
      boolean bool;
      if ((this.mIntervalMillis == Long.MAX_VALUE) && (this.mMinUpdateIntervalMillis == -1L)) {
        bool = false;
      } else {
        bool = true;
      }
      Preconditions.checkState(bool, "passive location requests must have an explicit minimum update interval");
      long l = this.mIntervalMillis;
      return new LocationRequestCompat(l, this.mQuality, this.mDurationMillis, this.mMaxUpdates, Math.min(this.mMinUpdateIntervalMillis, l), this.mMinUpdateDistanceMeters, this.mMaxUpdateDelayMillis);
    }
    
    public Builder clearMinUpdateIntervalMillis()
    {
      this.mMinUpdateIntervalMillis = -1L;
      return this;
    }
    
    public Builder setDurationMillis(long paramLong)
    {
      this.mDurationMillis = Preconditions.checkArgumentInRange(paramLong, 1L, Long.MAX_VALUE, "durationMillis");
      return this;
    }
    
    public Builder setIntervalMillis(long paramLong)
    {
      this.mIntervalMillis = Preconditions.checkArgumentInRange(paramLong, 0L, Long.MAX_VALUE, "intervalMillis");
      return this;
    }
    
    public Builder setMaxUpdateDelayMillis(long paramLong)
    {
      this.mMaxUpdateDelayMillis = paramLong;
      this.mMaxUpdateDelayMillis = Preconditions.checkArgumentInRange(paramLong, 0L, Long.MAX_VALUE, "maxUpdateDelayMillis");
      return this;
    }
    
    public Builder setMaxUpdates(int paramInt)
    {
      this.mMaxUpdates = Preconditions.checkArgumentInRange(paramInt, 1, Integer.MAX_VALUE, "maxUpdates");
      return this;
    }
    
    public Builder setMinUpdateDistanceMeters(float paramFloat)
    {
      this.mMinUpdateDistanceMeters = paramFloat;
      this.mMinUpdateDistanceMeters = Preconditions.checkArgumentInRange(paramFloat, 0.0F, Float.MAX_VALUE, "minUpdateDistanceMeters");
      return this;
    }
    
    public Builder setMinUpdateIntervalMillis(long paramLong)
    {
      this.mMinUpdateIntervalMillis = Preconditions.checkArgumentInRange(paramLong, 0L, Long.MAX_VALUE, "minUpdateIntervalMillis");
      return this;
    }
    
    public Builder setQuality(int paramInt)
    {
      boolean bool;
      if ((paramInt != 104) && (paramInt != 102) && (paramInt != 100)) {
        bool = false;
      } else {
        bool = true;
      }
      Preconditions.checkArgument(bool, "quality must be a defined QUALITY constant, not %d", new Object[] { Integer.valueOf(paramInt) });
      this.mQuality = paramInt;
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Quality {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/location/LocationRequestCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */