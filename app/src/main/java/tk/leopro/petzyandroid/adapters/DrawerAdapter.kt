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

/**
 * Custom adapter which extends BaseAdapter , this is used for inflating each row items of the listview .
 */


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.pojo.RowItem

class DrawerAdapter(internal var context: Context, internal var rowItem: List<RowItem>) : BaseAdapter() {

    val count: Int
        @Override
        get() = rowItem.size()

    @Override
    fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val mInflater = context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = mInflater.inflate(R.layout.for_drawer_layout, null)
        }
        val imgIcon = convertView!!.findViewById(R.id.icon) as ImageView
        val txtTitle = convertView!!.findViewById(R.id.title) as TextView
        val row_pos = rowItem[position]

        // setting the image resource and title
        imgIcon.setImageResource(row_pos.getIcon())
        txtTitle.setText(row_pos.getTitle())
        return convertView
    }

    @Override
    fun getItem(position: Int): Object {
        return rowItem[position]
    }

    @Override
    fun getItemId(position: Int): Long {
        return rowItem.indexOf(getItem(position))
    }
}
