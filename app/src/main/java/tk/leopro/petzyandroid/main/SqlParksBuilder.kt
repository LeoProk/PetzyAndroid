package tk.leopro.petzyandroid.main


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import android.content.Context
import android.util.Log

import java.util.ArrayList
import java.util.Collections

import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.pojo.FirebaseItem

/**
 * Get the parks info from parse database and create new SQLite file.
 */
internal class SqlParksBuilder(private val mContext: Context) : FactoryInterface {

    @Override
    fun doTask(): Object {
        val firebaseItems = ArrayList()
        // Get a reference to firebase database
        val ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://petzy-1001.firebaseio.com/input")
        ref.addValueEventListener(object : ValueEventListener() {
            @Override
            fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.getChildren()) {
                    firebaseItems.add(postSnapshot.getValue(FirebaseItem::class.java))
                }
                Collections.sort(firebaseItems)

                //UtilitiesFactory.callSQL(mContext, firebaseItems, "save").doTask();
            }

            @Override
            fun onCancelled(databaseError: DatabaseError) {

            }

        })
        //mParksList.add(new Park(finalParks[0], linkStreetView(finalParks[2], finalParks[3], finalParks[4]), finalParks[1], finalParks[2], finalParks[3]));

        return firebaseItems
    }

    private fun linkStreetView(lat: String, lng: String, imageSettings: String): String {
        Log.e("yay", imageSettings)
        return if (imageSettings.contains("pitch=")) {
            "https://maps.googleapis.com/maps/api/streetview?size=400x400&location=$lat,$lng$imageSettings"
        } else {
            imageSettings
        }
    }
}
