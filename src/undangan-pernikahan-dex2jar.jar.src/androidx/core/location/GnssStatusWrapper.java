package androidx.core.location;

import android.location.GnssStatus;
import android.os.Build.VERSION;
import androidx.core.util.Preconditions;

class GnssStatusWrapper
  extends GnssStatusCompat
{
  private final GnssStatus mWrapped;
  
  GnssStatusWrapper(Object paramObject)
  {
    this.mWrapped = ((GnssStatus)Preconditions.checkNotNull((GnssStatus)paramObject));
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof GnssStatusWrapper)) {
      return false;
    }
    paramObject = (GnssStatusWrapper)paramObject;
    return this.mWrapped.equals(((GnssStatusWrapper)paramObject).mWrapped);
  }
  
  public float getAzimuthDegrees(int paramInt)
  {
    return this.mWrapped.getAzimuthDegrees(paramInt);
  }
  
  public float getBasebandCn0DbHz(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.getBasebandCn0DbHz(this.mWrapped, paramInt);
    }
    throw new UnsupportedOperationException();
  }
  
  public float getCarrierFrequencyHz(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getCarrierFrequencyHz(this.mWrapped, paramInt);
    }
    throw new UnsupportedOperationException();
  }
  
  public float getCn0DbHz(int paramInt)
  {
    return this.mWrapped.getCn0DbHz(paramInt);
  }
  
  public int getConstellationType(int paramInt)
  {
    return this.mWrapped.getConstellationType(paramInt);
  }
  
  public float getElevationDegrees(int paramInt)
  {
    return this.mWrapped.getElevationDegrees(paramInt);
  }
  
  public int getSatelliteCount()
  {
    return this.mWrapped.getSatelliteCount();
  }
  
  public int getSvid(int paramInt)
  {
    return this.mWrapped.getSvid(paramInt);
  }
  
  public boolean hasAlmanacData(int paramInt)
  {
    return this.mWrapped.hasAlmanacData(paramInt);
  }
  
  public boolean hasBasebandCn0DbHz(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.hasBasebandCn0DbHz(this.mWrapped, paramInt);
    }
    return false;
  }
  
  public boolean hasCarrierFrequencyHz(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.hasCarrierFrequencyHz(this.mWrapped, paramInt);
    }
    return false;
  }
  
  public boolean hasEphemerisData(int paramInt)
  {
    return this.mWrapped.hasEphemerisData(paramInt);
  }
  
  public int hashCode()
  {
    return this.mWrapped.hashCode();
  }
  
  public boolean usedInFix(int paramInt)
  {
    return this.mWrapped.usedInFix(paramInt);
  }
  
  static class Api26Impl
  {
    static float getCarrierFrequencyHz(GnssStatus paramGnssStatus, int paramInt)
    {
      return paramGnssStatus.getCarrierFrequencyHz(paramInt);
    }
    
    static boolean hasCarrierFrequencyHz(GnssStatus paramGnssStatus, int paramInt)
    {
      return paramGnssStatus.hasCarrierFrequencyHz(paramInt);
    }
  }
  
  static class Api30Impl
  {
    static float getBasebandCn0DbHz(GnssStatus paramGnssStatus, int paramInt)
    {
      return paramGnssStatus.getBasebandCn0DbHz(paramInt);
    }
    
    static boolean hasBasebandCn0DbHz(GnssStatus paramGnssStatus, int paramInt)
    {
      return paramGnssStatus.hasBasebandCn0DbHz(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/location/GnssStatusWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */