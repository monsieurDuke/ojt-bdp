package androidx.core.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.util.List;

/* loaded from: classes.dex */
public interface LocationListenerCompat extends LocationListener {
    @Override // android.location.LocationListener
    default void onFlushComplete(int requestCode) {
    }

    @Override // android.location.LocationListener
    default void onLocationChanged(List<Location> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            onLocationChanged(list.get(i));
        }
    }

    @Override // android.location.LocationListener
    default void onProviderDisabled(String provider) {
    }

    @Override // android.location.LocationListener
    default void onProviderEnabled(String provider) {
    }

    @Override // android.location.LocationListener
    default void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
