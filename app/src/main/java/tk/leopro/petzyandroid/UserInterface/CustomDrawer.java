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

import android.app.Fragment;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.Fragments.AdoptionTips;
import tk.leopro.petzyandroid.Fragments.LostFound;
import tk.leopro.petzyandroid.Fragments.ParksMap;
import tk.leopro.petzyandroid.Fragments.PetAdopting;
import tk.leopro.petzyandroid.Fragments.VetsMap;
import tk.leopro.petzyandroid.Interfaces.FactoryInterface;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.Utilities.UtilitiesFactory;

import java.util.ArrayList;

/**
 * This class contain logic of drawer.
 */

final class CustomDrawer implements FactoryInterface {

    private Context mContext;

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    protected CustomDrawer(Context context, DrawerLayout drawerLayout, ListView drawerList) {
        mContext = context;
        this.mDrawerLayout = drawerLayout;
        this.mDrawerList = drawerList;
    }

    //class of creating and populating navigation drawer
    @Override
    public ActionBarDrawerToggle doTask() {

        final AppCompatActivity activity = (AppCompatActivity) mContext;
        //get the arrays from strings
        final String[] menutitles = mContext.getResources().getStringArray(R.array.titles);
        /*final TypedArray menuIcons = context.getResources().obtainTypedArray(R.array.icons);*/
        ArrayList<RowItem> rowItems = new ArrayList<>();
        //create the drawer
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            //* Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            //* Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        for (int i = 0; i < menutitles.length; i++) {
            RowItem items = new RowItem(menutitles[i], i);
            rowItems.add(items);

        }
//        menuIcons.recycle();
        CustomAdapter adapter = new CustomAdapter(mContext, rowItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new CustomDrawer.SlideitemListener());
        return mDrawerToggle;
    }

    //create fragment based on clicked position
    private void updateDisplay(int position) {
        final AppCompatActivity activity = ( AppCompatActivity) mContext;
        //change the fragment tag based on location pressed
        String tag = null;
        Fragment fragment = null;
        switch (position) {
            case 0:
                tag = "adopt";
                fragment = new PetAdopting();
                break;
            case 1:
                tag = "lost";
                fragment = new LostFound();
                break;
            case 2:
                tag = "tips";
                fragment = new AdoptionTips();
                break;
            case 3:
                tag = "parks";
                fragment = new ParksMap();
                break;
            case 4:
                tag = "vets";
                fragment = new VetsMap();
                break;
            default:
                break;
        }
        AppController.currentFragment = tag;
        if(activity.getFragmentManager().findFragmentByTag(tag)==null){
            UtilitiesFactory.addFragment(mContext,fragment,tag,true).doTask();
        }else {
            UtilitiesFactory.switchFragments(mContext,tag).doTask();
        }

    }

    // called when one of the items in drawer is clicked
    class SlideitemListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            updateDisplay(position);
        }
    }

}
