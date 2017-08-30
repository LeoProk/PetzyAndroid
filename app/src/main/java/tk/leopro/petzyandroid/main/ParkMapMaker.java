package tk.leopro.petzyandroid.main;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.FirebaseItem;
import tk.leopro.petzyandroid.utilities.UtilitiesFactory;

/**
 * Created by Leo on 10/10/2015.
 */
final class ParkMapMaker implements FactoryInterface {

    private GoogleMap mMap;

    private Context mContext;

    public ParkMapMaker(GoogleMap map,Context context){

     mContext = context;
     mMap = map;

    }

    @Override
    public Object doTask() {
/*        List<FirebaseItem> parksList = new ArrayList();
        UtilitiesFactory.callSQL(mContext, , "retrieve").doTask();
        for (int i = 0; i < parksList.size() ; i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(parksList.get(i).getLatLng())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        }*/

        return null;
    }
}
