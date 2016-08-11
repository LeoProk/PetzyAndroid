package tk.leopro.petzyandroid.main;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.FirebaseItem;
import tk.leopro.petzyandroid.utilities.UtilitiesFactory;

/**
 * Get the parks info from parse database and create new SQLite file.
 */
final class SqlParksBuilder implements FactoryInterface {

    private Context mContext;

    public SqlParksBuilder(Context context) {

        mContext = context;

    }

    @Override
    public Object doTask() {
        final ArrayList firebaseItems = new ArrayList<>();
        // Get a reference to firebase database
        final Firebase ref = new Firebase("https://luminous-fire-5859.firebaseio.com/input");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    firebaseItems.add(postSnapshot.getValue(FirebaseItem.class));
                }

                UtilitiesFactory.callSQL(mContext, firebaseItems, "save").doTask();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //mParksList.add(new Park(finalParks[0], linkStreetView(finalParks[2], finalParks[3], finalParks[4]), finalParks[1], finalParks[2], finalParks[3]));

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
