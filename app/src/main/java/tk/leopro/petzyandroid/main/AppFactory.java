package tk.leopro.petzyandroid.main;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;

import tk.leopro.petzyandroid.AppSpecific.ParkListMaker;
import tk.leopro.petzyandroid.AppSpecific.ParkMapMaker;
import tk.leopro.petzyandroid.AppSpecific.SqlParksBuilder;
import troll.Fragments.Interfaces.FactoryInterface;

/**
 * Factory for app specifics
 */
public class AppFactory {

    //Parsing info from html
    public static AsyncTask getHtmlInfo() {
        return new AdoptingHtmlParser().execute();
    }

    //Create list of park with data taken from parse database
    public static FactoryInterface getParksList(Context context, ListView listView) {
        return new ParkListMaker(context, listView);
    }

    //Build SQLite database using data from Parse
    public static FactoryInterface buildSQLParksData(Context context) {
        return new SqlParksBuilder(context);
    }
    //Create markers on google maps
    public static FactoryInterface createMarkers(GoogleMap googleMap,Context context) {
        return new ParkMapMaker(googleMap,context);
    }
}
