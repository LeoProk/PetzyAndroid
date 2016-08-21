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

package tk.leopro.petzyandroid.utilities;


import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.MainActivity;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.adapters.DrawerAdapter;
import tk.leopro.petzyandroid.fragments.AdoptionTips;
import tk.leopro.petzyandroid.fragments.DogsAdopting;
import tk.leopro.petzyandroid.fragments.Lost;
import tk.leopro.petzyandroid.fragments.NewParkFragment;
import tk.leopro.petzyandroid.fragments.ParksClosest;
import tk.leopro.petzyandroid.fragments.VetsClosest;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.RowItem;

import java.util.ArrayList;

/**
 * This class contain logic of drawer.
 */

final class CustomDrawer implements FactoryInterface {

    private Context mContext;

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    private Toolbar mToolbar;

    protected CustomDrawer(Context context, DrawerLayout drawerLayout, ListView drawerList,Toolbar toolbar) {
        mContext = context;
        mDrawerLayout = drawerLayout;
        mDrawerList = drawerList;
        mToolbar = toolbar;
    }

    //class of creating and populating navigation drawer
    @Override
    public ActionBarDrawerToggle doTask() {

        final AppCompatActivity activity = (AppCompatActivity) mContext;
        final ArrayList<RowItem> rowItems = new ArrayList<>();
        //get the arrays from strings
        final String[] menuTitles = mContext.getResources().getStringArray(R.array.titles);
        final TypedArray menuIcons = mContext.getResources().obtainTypedArray(R.array.icons);
        //create the drawer
        ActionBarDrawerToggle drawerToggle = null;
        if(mToolbar!= null) {
            drawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout,
                    mToolbar, R.string.drawer_open, R.string.drawer_close) {

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
            drawerToggle.syncState();
        }
        //check if user login if not disable add new item option and shows the log in button
        int startingListNum = 0;
        //create the drawer list names and icons
        for (int i = startingListNum; i < menuTitles.length; i++) {
            RowItem items = new RowItem(menuTitles[i], menuIcons.getResourceId(i, -1));
            rowItems.add(items);

        }
        //sets drawer adapter
        DrawerAdapter adapter = new DrawerAdapter(mContext, rowItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new CustomDrawer.SlideitemListener());
        menuIcons.recycle();
        mDrawerLayout.setDrawerListener(drawerToggle);
        return drawerToggle;
    }

    //create fragment based on clicked position
    private void updateDisplay(int position) {
        final AppController appController = (AppController) mContext.getApplicationContext();
        //empty fragment and tag
        String tag = "search";
        String[] tabName = null;
        String[] tabTags = null;
        Fragment fragment = null;
        //create new fragment bassed on click location in drawer list
        // set the search value for item list fragments
        switch (position) {
            case 0:
                tag = "new";
                fragment = new NewParkFragment();
                break;
           /* case 0:
                tag = "dog";
                fragment = new DogsAdopting();
                tabName = new String[]{mContext.getResources().getString(R.string.dogs), mContext.getResources().getString(R.string.cats), mContext.getResources().getString(R.string.other)};
                tabTags = new String[]{"dog", "cat", "other"};
                break;
            case 1:
                tag = "lost";
                fragment = new Lost();
                tabName = new String[]{mContext.getResources().getString(R.string.lost), mContext.getResources().getString(R.string.found)};
                tabTags = new String[]{"lost", "found"};
                break;
            case 2:
                tag = "tips";
                fragment = new AdoptionTips();
                break;
            case 3:
                tag = "parksNear";
                fragment = new ParksClosest();
                tabName = new String[]{mContext.getResources().getString(R.string.closest), mContext.getResources().getString(R.string.map)};
                tabTags = new String[]{"parkNear", "parkMap"};
                break;
            case 4:
                tag = "vetNear";
                fragment = new VetsClosest();
                tabName = new String[]{mContext.getResources().getString(R.string.closest), mContext.getResources().getString(R.string.map)};
                tabTags = new String[]{"vetNear", "vetMap"};
                break;*/
            default:
                break;
        }
        //Create tabs
        if (tabName != null) {
            ((MainActivity) mContext).changeTabs(tabName, tabTags);
        }
        //Change to clicked Fragment
        if (((MainActivity) mContext).getFragmentManager().findFragmentByTag(tag) == null) {
            appController.fragmentTag = tag;
            UtilitiesFactory.addFragment(mContext, fragment, tag, true).doTask();
        } else {
            UtilitiesFactory.switchFragments(mContext, tag).doTask();
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    // called when one of the items in drawer is clicked
    class SlideitemListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            updateDisplay(position);
        }
    }

}
