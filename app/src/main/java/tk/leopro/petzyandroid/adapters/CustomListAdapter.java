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

package tk.leopro.petzyandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.pojo.FirebaseItem;


/**
 * base adapter for list view of items
 * the item info is taken from firebase servers in pojo
 */

public class CustomListAdapter extends BaseAdapter {

    private Activity mActivity;

    private LayoutInflater mInflater;

    private ArrayList<FirebaseItem> mFirebaseItems;

    private ImageLoader mImageLoader;

    public CustomListAdapter(Activity activity, ArrayList<FirebaseItem> firebaseItem) {
        mActivity = activity;
        mFirebaseItems = firebaseItem;
    }


    @Override
    public int getCount() {
        return mFirebaseItems.size();
    }

    @Override
    public Object getItem(int location) {
        return mFirebaseItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (mInflater == null)
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_row, null);
        if (mImageLoader == null) {
            mImageLoader = AppController.getInstance().getImageLoader();
        }
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        final TextView title = (TextView) convertView.findViewById(R.id.title);
        final TextView address = (TextView) convertView.findViewById(R.id.address);
        final TextView length = (TextView) convertView.findViewById(R.id.length);
        // getting item data for the row
        final FirebaseItem firebaseItem = mFirebaseItems.get(position);
        thumbNail.setImageUrl(firebaseItem.thumbnailUrl(firebaseItem.getUser()), mImageLoader);
        address.setText(firebaseItem.getAddress());
        title.setText(firebaseItem.getTitle());
        length.setText(distanceInKM(firebaseItem.calculateDistance()));
        return convertView;
    }
    //return string distance in meter or kelometres
    private String distanceInKM(int parkDistance) {
        String range;
        if (parkDistance < 1000) {
            range = "מטרים";
        } else {
            range = "קילומטרים";
            parkDistance = parkDistance / 1000;
        }
        return String.valueOf(parkDistance) + " " + range;
    }
}
