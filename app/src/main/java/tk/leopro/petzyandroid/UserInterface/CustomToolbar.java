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
package tk.leopro.petzyandroid.UserInterface;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tk.leopro.petzyandroid.Interfaces.FactoryInterface;
import tk.leopro.petzyandroid.R;

/**
 * This class contain logic of toolbar
 */
final class CustomToolbar implements FactoryInterface {

    private Context mContext;

    private Toolbar mToolbar;

    protected CustomToolbar(Context context, Toolbar toolbar) {
        this.mContext = context;
        this.mToolbar = toolbar;
    }

    //replace the default action bat with toolbar
    @Override
    public Object doTask() {
        final AppCompatActivity activity = ( AppCompatActivity) mContext;
        activity.setSupportActionBar(mToolbar);
        ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle("Petzy");
        ab.setDisplayUseLogoEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        return null;
    }


}
