package tk.leopro.petzyandroid.UserInterface;

import android.content.Context;
import android.location.Location;
import android.provider.SyncStateContract;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import tk.leopro.petzyandroid.Interfaces.FactoryInterface;

/**
 * Creates google map and zooms in to current location.
 */
final class CustomMap implements FactoryInterface {

    private Context mContext;
    private MapView mMapView;

    public CustomMap(Context context,MapView mapView){

        mContext = context;
        mMapView = mapView;
    }

    @Override
    public Object doTask() {
        GoogleMap map = mMapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        MapsInitializer.initialize(mContext);
        Location currentLocation = map.getMyLocation();
        LatLng latLngLocation;
        if (currentLocation != null) {
            latLngLocation = new LatLng(currentLocation.getLatitude(),
                    currentLocation.getLongitude());
        }else {
            latLngLocation = new LatLng(31.7833,35.2167);
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngLocation,15));
        return null;
    }
}
