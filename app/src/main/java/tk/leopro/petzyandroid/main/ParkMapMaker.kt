package tk.leopro.petzyandroid.main

import android.content.Context

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions

import java.util.ArrayList

import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.pojo.FirebaseItem
import tk.leopro.petzyandroid.utilities.UtilitiesFactory

/**
 * Used to show all the park on the map
 * Still not in use
 */
internal class ParkMapMaker(private val mMap: GoogleMap, private val mContext: Context) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        /*        List<FirebaseItem> parksList = new ArrayList();
        UtilitiesFactory.callSQL(mContext, , "retrieve").doTask();
        for (int i = 0; i < parksList.size() ; i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(parksList.get(i).getLatLng())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        }*/

        return null
    }
}
