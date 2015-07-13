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

package tk.leopro.petzyandroid.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.UserInterface.UIFactory;

/**
 * Show closest veterinars on the map
 */
public class VetsMap extends Fragment {

    private MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.vets_fragment, container, false);
        // Gets the MapView from the XML layout and creates it
        mMapView = (MapView) rootView.findViewById(R.id.vets_map);
        mMapView.onCreate(savedInstanceState);
        UIFactory.getMap(getActivity(), mMapView).doTask();
        return rootView;
    }


}
