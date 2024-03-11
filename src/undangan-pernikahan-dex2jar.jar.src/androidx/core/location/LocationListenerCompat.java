package androidx.core.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.util.List;

public abstract interface LocationListenerCompat
  extends LocationListener
{
  public void onFlushComplete(int paramInt) {}
  
  public void onLocationChanged(List<Location> paramList)
  {
    int j = paramList.size();
    for (int i = 0; i < j; i++) {
      onLocationChanged((Location)paramList.get(i));
    }
  }
  
  public void onProviderDisabled(String paramString) {}
  
  public void onProviderEnabled(String paramString) {}
  
  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/location/LocationListenerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */