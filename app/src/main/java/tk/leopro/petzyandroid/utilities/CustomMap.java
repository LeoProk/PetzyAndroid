package tk.leopro.petzyandroid.utilities;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.main.AppFactory;

/**
 * Creates google map and zooms in to current location.
 */
final class CustomMap implements FactoryInterface {

    private Context mContext;
    private MapView mMapView;

    public CustomMap(Context context, MapView mapView) {

        mContext = context;
        mMapView = mapView;
    }

    @Override
    public Object doTask() {
     /*   final GoogleMap map = mMapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                AppFactory.createMarkers(map, mContext).doTask();
            }
        });
        MapsInitializer.initialize(mContext);
        LatLng latLngLocation;
        if (AppController.sCurrentLocation != null) {
            latLngLocation = new LatLng(AppController.sCurrentLocation.getLatitude(),
                    AppController.sCurrentLocation.getLongitude());
        } else {
            latLngLocation = new LatLng(31.7833, 35.2167);
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngLocation, 15));*/
        return null;
    }

}