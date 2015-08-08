package tk.leopro.petzyandroid.AppSpecific;

import android.content.Context;
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
                            Log.e("aya", finalParks[0] + " " + linkStreetView(finalParks[2], finalParks[3]) + " " + finalParks[1] + " " + finalParks[2]);
                            mParksList.add(new Park(finalParks[0], linkStreetView(finalParks[2], finalParks[3]), finalParks[1], finalParks[2]));

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    FragmentActivity activity = (FragmentActivity) mContext;
                    CustomListAdapter adapter = new CustomListAdapter(activity,mParksList);
                    mListView.setAdapter(adapter);
                } else {


                }
            }
        });
            return null;


    }
    private String linkStreetView(String lat,String lng){
       return  "https://maps.googleapis.com/maps/api/streetview?size=400x400&location="+lat+","+lng+"&fov=90&heading=235&pitch=10";
    }
}
