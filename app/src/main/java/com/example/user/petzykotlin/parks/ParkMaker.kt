package com.example.user.petzykotlin.parks

import android.content.Context
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.leoprok.petzyandroid.pojos.Park

/**
 * retrieve data from firebase by urls
 * sets listener to when new value is added cleans and remakes the adapter
 * set the new adapter to list view
 */
class ParkMaker(val context:Context,val listView:ListView){

    init {
        val parksList = ArrayList<Park>()
        //get reference to firebase by url
        val ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://petzy-1001.firebaseio.com/input")
//        val valueListener = object :ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        }
        //set listener to data changes
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot : DataSnapshot?) {
                //clean the parklist to avoid doubplicate values
                parksList.clear()
                //loop trew the values to build new list
                for (postSnapshot in dataSnapshot!!.children){
                    parksList.add(postSnapshot.value as Park)
                }
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
    }
}