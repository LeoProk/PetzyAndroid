package tk.leopro.petzyandroid.main;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.FirebaseItem;

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
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(("https://petzy-1001.firebaseio.com/input"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    firebaseItems.add(postSnapshot.getValue(FirebaseItem.class));
                }
                Collections.sort(firebaseItems);

                //UtilitiesFactory.callSQL(mContext, firebaseItems, "save").doTask();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        //mParksList.add(new Park(finalParks[0], linkStreetView(finalParks[2], finalParks[3], finalParks[4]), finalParks[1], finalParks[2], finalParks[3]));

        return firebaseItems;
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
