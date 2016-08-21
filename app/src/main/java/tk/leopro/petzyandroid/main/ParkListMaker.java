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
package tk.leopro.petzyandroid.main;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.adapters.CustomListAdapter;
import tk.leopro.petzyandroid.pojo.FirebaseItem;

/**
 * Retrieve data from sql and put it in list view adapter
 */
final class ParkListMaker implements FactoryInterface {
    private Context mContext;
    private ListView mListView;

    public ParkListMaker(Context context, ListView listView) {
        mContext = context;
        mListView = listView;

    }

    @Override
    public Object doTask() {
        final ArrayList<FirebaseItem> parksList = new ArrayList();
        FragmentActivity activity = (FragmentActivity) mContext;
        final CustomListAdapter adapter = new CustomListAdapter(activity, parksList);
        final Firebase ref = new Firebase("https://petzy-1001.firebaseio.com/input");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    parksList.add(postSnapshot.getValue(FirebaseItem.class));
                }
                Collections.sort(parksList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return null;


    }
}
