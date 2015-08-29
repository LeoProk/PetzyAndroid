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
import tk.leopro.petzyandroid.Fragments.Lost;
import tk.leopro.petzyandroid.Fragments.ParksClosest;
import tk.leopro.petzyandroid.Fragments.ParksMap;
import tk.leopro.petzyandroid.Fragments.DogsAdopting;
import tk.leopro.petzyandroid.Fragments.VetsClosest;
import tk.leopro.petzyandroid.Interfaces.FactoryInterface;
import tk.leopro.petzyandroid.MainActivity;
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
        String[] tabName = null;
        String[] tabTags = null;
        Fragment fragment = null;
        switch (position) {
            case 0:
                tag = "dog";
                fragment = new DogsAdopting();
                tabName = new String[]{mContext.getResources().getString(R.string.dogs),mContext.getResources().getString(R.string.cats),mContext.getResources().getString(R.string.other)};
                tabTags = new String[] {"dog","cat","other"};
                break;
            case 1:
                tag = "lost";
                fragment = new Lost();
                tabName = new String[]{mContext.getResources().getString(R.string.lost),mContext.getResources().getString(R.string.found)};
                tabTags = new String[]{"lost","found"};
                break;
            case 2:
                tag = "tips";
                fragment = new AdoptionTips();
                break;
            case 3:
                tag = "parksNear";
                fragment = new ParksClosest();
                tabName = new String[]{mContext.getResources().getString(R.string.closest),mContext.getResources().getString(R.string.map)};
                tabTags = new String[]{"parkNear","parkMap"};
                break;
            case 4:
                tag = "vetNear";
                fragment = new VetsClosest();
                tabName = new String[]{mContext.getResources().getString(R.string.closest),mContext.getResources().getString(R.string.map)};
                tabTags = new String[]{"vetNear","vetMap"};
                break;
            default:
                break;
        }
        //Create tabs
        if(tabName != null) {
            ((MainActivity) mContext).changeTabs(tabName, tabTags);
        }
        //Change to clicked Fragment
        if(activity.getFragmentManager().findFragmentByTag(tag)==null){
            AppController.mFragmentTag = tag;
            UtilitiesFactory.addFragment(mContext,fragment,tag,true).doTask();
        }else {
            UtilitiesFactory.switchFragments(mContext,tag).doTask();
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
