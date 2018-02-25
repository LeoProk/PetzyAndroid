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

package tk.leopro.petzyandroid.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

import java.util.ArrayList

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.pojo.FirebaseItem


/**
 * base adapter for list view of items
 * the item info is taken from firebase servers in pojo
 */

class CustomListAdapter(private val mActivity: Activity, private val mFirebaseItems: ArrayList<FirebaseItem>) : BaseAdapter() {

    private var mInflater: LayoutInflater? = null

    private var mImageLoader: ImageLoader? = null


    val count: Int
    get() = mFirebaseItems.size()

    override fun getItem(location: Int): Object {
        return mFirebaseItems.get(location)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        if (mInflater == null)
            mInflater = mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null)
            convertView = mInflater!!.inflate(R.layout.list_row, null)
        if (mImageLoader == null) {
            mImageLoader = AppController.getInstance().getImageLoader()
        }
        val thumbNail = convertView!!
                .findViewById(R.id.thumbnail) as NetworkImageView
        val title = convertView!!.findViewById(R.id.title) as TextView
        val address = convertView!!.findViewById(R.id.address) as TextView
        val length = convertView!!.findViewById(R.id.length) as TextView
        // getting item data for the row
        val firebaseItem = mFirebaseItems.get(position)
        thumbNail.setImageUrl(firebaseItem.getThumbnail(firebaseItem.getUser()), mImageLoader)
        address.setText(firebaseItem.getAddress())
        title.setText(firebaseItem.getTitle())
        length.setText(distanceInKM(firebaseItem.getDistance()))
        return convertView
    }

    //return string distance in meter or kelometres
    private fun distanceInKM(parkDistance: Int): String {
        var parkDistance = parkDistance
        val range: String
        if (parkDistance < 1000) {
            range = "מטרים"
        } else {
            range = "קילומטרים"
            parkDistance = parkDistance / 1000
        }
        return String.valueOf(parkDistance) + " " + range
    }
}
