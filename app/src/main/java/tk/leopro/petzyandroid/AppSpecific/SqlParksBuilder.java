package tk.leopro.petzyandroid.AppSpecific;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.Interfaces.FactoryInterface;
import tk.leopro.petzyandroid.Utilities.UtilitiesFactory;

/**
 * Created by Leo on 9/5/2015.
 */
final class SqlParksBuilder implements FactoryInterface {

    private ArrayList<Park> mParksList;
    private Context mContext;

    public SqlParksBuilder(Context context){

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
                    UtilitiesFactory.callSQL(mContext,mParksList,"save").doTask();
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
}
