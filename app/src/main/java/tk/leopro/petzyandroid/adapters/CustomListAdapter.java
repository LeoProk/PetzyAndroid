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
import android.widget.TextView;

import java.util.List;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.pojo.FirebaseItem;

public class CustomListAdapter extends BaseAdapter {

    private Activity mActivity;

    private LayoutInflater mInflater;

    private List<FirebaseItem> mParkItems;

    public CustomListAdapter(Activity activity, List<FirebaseItem> parksItems) {
        mActivity = activity;
        mParkItems = parksItems;
    }

    @Override
    public int getCount() {
        return mParkItems.size();
    }

    @Override
    public Object getItem(int location) {
        return mParkItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (mInflater == null) {
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_row, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView location = (TextView) convertView.findViewById(R.id.text);

        TextView length = (TextView) convertView.findViewById(R.id.length);
        // getting movie data for the row
        FirebaseItem park = mParkItems.get(position);
        title.setText(park.getTitle());
        location.setText(park.getTitle());
        //length.setText(park.getLocation());

        return convertView;
    }

}