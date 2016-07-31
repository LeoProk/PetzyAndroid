package tk.leopro.petzyandroid.main;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import troll.Fragments.Interfaces.FactoryInterface;
import troll.Fragments.Utilities.UtilitiesFactory;
import tk.leopro.petzyandroid.pojo.Park;

/**
 * Get the parks info from parse database and create new SQLite file.
 */
final class SqlParksBuilder implements FactoryInterface {

    private ArrayList<Park> mParksList;
    private Context mContext;

    public SqlParksBuilder(Context context) {

        mContext = context;

    }

    @Override
    public Object doTask() {
        mParksList = new ArrayList<>();
        final ParseQuery query = ParseQuery.getQuery("Parks");
        query.getInBackground("X2xpPlxMB5", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    JSONArray myArray = object.getJSONArray("parks");
                    for (int i = 0; i < myArray.length(); i++) {
                        try {
                            String[] finalParks = myArray.get(i).toString().split("Break:");
                            Log.e("New Park Info", finalParks[0] + " " + linkStreetView(finalParks[2], finalParks[3], finalParks[4]) + " " + finalParks[1] + " " + finalParks[2]);
                            mParksList.add(new Park(finalParks[0], linkStreetView(finalParks[2], finalParks[3], finalParks[4]), finalParks[1], finalParks[2], finalParks[3]));


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    UtilitiesFactory.callSQL(mContext, mParksList, "save").doTask();
                } else {


                }
            }
        });


        return null;
    }

    private String linkStreetView(String lat, String lng, String imageSettings) {
        Log.e("yay", imageSettings);
        if (imageSettings.contains("pitch=")) {
            return "https://maps.googleapis.com/maps/api/streetview?size=400x400&location=" + lat + "," + lng + imageSettings;
        } else {
            return imageSettings;
        }
    }
}
