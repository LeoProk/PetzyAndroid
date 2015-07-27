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

import tk.leopro.petzyandroid.AppSpecific.AppFactory;
import tk.leopro.petzyandroid.MainActivity;
import tk.leopro.petzyandroid.R;

/**
 * This fragment show list of pet available for adopting sorted by time . also it have
 * option to add pets that you want other to adopt
 */
public class DogsAdopting extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.dogs_fragment, container, false);
        //AppFactory.getHtmlInfo();
        final String[] tabName = {getResources().getString(R.string.dogs),getResources().getString(R.string.cats),getResources().getString(R.string.other)};
        final String[] tabTags = {"dog","cat","other"};
        ((MainActivity) getActivity()).changeTabs(tabName, tabTags);
        return rootView;
    }


}
