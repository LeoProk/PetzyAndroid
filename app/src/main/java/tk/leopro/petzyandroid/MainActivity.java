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

package tk.leopro.petzyandroid;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import tk.leopro.petzyandroid.Fragments.Found;
import tk.leopro.petzyandroid.Fragments.NewsFragment;
import tk.leopro.petzyandroid.Fragments.VetsClosest;
import tk.leopro.petzyandroid.Interfaces.FactoryInterface;
import tk.leopro.petzyandroid.UserInterface.UIFactory;
import tk.leopro.petzyandroid.Utilities.UtilitiesFactory;


public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FactoryInterface getToolbar = UIFactory.getToolbar(this, toolbar);
        getToolbar.doTask();
        //create the drawer
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.slider_list);
        FactoryInterface getDrawer = UIFactory.getDrawer(this, mDrawerLayout, mDrawerList);
        mDrawerToggle = (ActionBarDrawerToggle) getDrawer.doTask();
        //Create news Fragment
        UtilitiesFactory.addFragment(this, new NewsFragment(), AppController.mFragmentTag, true).doTask();
        //Create tabs
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeTabs(String[] tabNames,String[] tags, Fragment[] fragments) {
        UtilitiesFactory.createTabs(this,mTabLayout,tabNames,tags,fragments).doTask();
    }
}
