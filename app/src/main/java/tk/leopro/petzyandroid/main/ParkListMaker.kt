/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tk.leopro.petzyandroid.main

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.ListView

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.ArrayList
import java.util.Collections

import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.adapters.CustomListAdapter
import tk.leopro.petzyandroid.pojo.FirebaseItem

/**
 *
 * retrieve data from firebase by urls
 * sets listener to when new value is added cleans and remakes the adapter
 * set the new adapter to list view
 */
internal class ParkListMaker(//context
        private val mContext: Context, //list view of parks
        private val mListView: ListView) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        //create new array of firebase items (parks)
        val parksList = ArrayList()
        //gets the current context
        val activity = mContext as FragmentActivity
        //create new custom adapter for list of parks
        val adapter = CustomListAdapter(activity, parksList)
        //get reference to firebase by url
        val ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://petzy-1001.firebaseio.com/input")
        //set listener to data changes
        ref.addValueEventListener(object : ValueEventListener() {
            @Override
            fun onDataChange(dataSnapshot: DataSnapshot) {
                //clean the parklist to avoid doubplicate values
                parksList.clear()
                //loop trew the values to build new list
                for (postSnapshot in dataSnapshot.getChildren()) {
                    parksList.add(postSnapshot.getValue(FirebaseItem::class.java))
                }
                //set the updated list
                Collections.sort(parksList)
                mListView.setAdapter(adapter)
            }

            @Override
            fun onCancelled(databaseError: DatabaseError) {

            }

        })
        return null


    }
}
