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

package tk.leopro.petzyandroid.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.MapView

import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.utilities.UtilitiesFactory

/**
 * Show closest veterinars on the map
 */
class VetsMap : Fragment() {

    private var mMapView: MapView? = null

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle): View {

        val rootView = inflater.inflate(R.layout.vets_map_fragment, container, false)
        // Gets the MapView from the XML layout and creates it
        mMapView = rootView.findViewById(R.id.vets_map) as MapView
        mMapView!!.onCreate(savedInstanceState)
        UtilitiesFactory.getCustomMap(getActivity(), mMapView).doTask()
        return rootView
    }


}