package tk.leopro.petzyandroid.AppSpecific;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.Fragments.CustomListAdapter;
import tk.leopro.petzyandroid.Interfaces.FactoryInterface;

/**
 * Created by Leo on 8/6/2015.
 */
final class ParksListMaker implements FactoryInterface{

    private ArrayList<Park> mParksList;
    private Context mContext;
    private ListView mListView;

    public ParksListMaker(Context context,ListView listView){
        mContext = context;
        mListView = listView;

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
                            Location parkLocation = new Location("Park Location");
                            parkLocation.setLatitude(Double.parseDouble(finalParks[2]));
                            parkLocation.setLongitude(Double.parseDouble(finalParks[3]));
                            mParksList.add(new Park(finalParks[0], linkStreetView(finalParks[2], finalParks[3], finalParks[4]), finalParks[1], getDistance(parkLocation)));

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    FragmentActivity activity = (FragmentActivity) mContext;
                    CustomListAdapter adapter = new CustomListAdapter(activity, mParksList);
                    mListView.setAdapter(adapter);
                } else {


                }
            }
        });
            return null;


    }
    private String linkStreetView(String lat,String lng,String imageSettings){
            Log.e("yay",imageSettings);
            if(imageSettings.contains("pitch=")){
            return  "https://maps.googleapis.com/maps/api/streetview?size=400x400&location="+lat +"," +lng + imageSettings;
            }else {
                return imageSettings;}
    }
    private String getDistance(Location parkLocation){
        String range;
        double distance = Math.round(AppController.currentLocation.distanceTo(parkLocation));
        if(distance < 1000){
            range = "meter";
        }else {
            range = "kilometre";
            distance =distance/1000;
        }
        return String.valueOf(distance + " " + range);

    }
}
