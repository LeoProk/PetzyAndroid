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
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.List;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.Park;

/**
 * Factory for network statues , retrieving files , saving and appending new files.
 */
public class UtilitiesFactory {

    //Switch between 2 fragments
    public static FactoryInterface switchFragments(Context context, String newFragmentTag) {
        return new FragmentSwitcher(context, newFragmentTag);
    }

    //Check for network status return bool
    public static FactoryInterface checkNetwork(Context context, boolean toastNeeded) {
        return new NetworkCheck(context, toastNeeded);
    }

    //Gets file from external storage by name
    public static FactoryInterface getFile(Context context, String filename) {
        return new FileManager(context, filename, "", "get");
    }

    //Save file to external storage with name and message
    public static FactoryInterface saveFile(Context context, String filename, String message) {
        return new FileManager(context, filename, message, "save");
    }

    //Appends message to existing file
    public static FactoryInterface appendFile(Context context, String filename, String message) {
        return new FileManager(context, filename, message, "append");
    }


    //Adds new fragment.
    public static FactoryInterface addFragment(Context context, Fragment fragment, String tag,
                                               boolean addToBackStack) {
        return new AddFragment(context, fragment, tag, addToBackStack);
    }

    //Replace the current fragment with new fragment.
    public static FactoryInterface replaceFragment(Context context, Fragment fragment, String tag,
                                                   boolean addToBackStack) {
        return new ReplaceFragment(context, fragment, tag, addToBackStack);
    }

    //Removes fragment by tag
    public static FactoryInterface removeFragment(Context context) {
        return new RemoveFragment(context);
    }

    //Deletes file by name
    public static FactoryInterface deleteFile(Context context, String filename) {
        return new FileManager(context, filename, "", "delete");
    }

    //Resets the app
    public static FactoryInterface resetApp(Context context) {
        return new AppRestart(context);
    }


    //Creates toolbar actionbar must be disabled in manifest
    public static FactoryInterface getToolbar(Context context, Toolbar toolbar) {
        return new CustomToolbar(context, toolbar);
    }


    //Creates navigation drawer
    public static FactoryInterface getDrawer(Context context, DrawerLayout mDrawerLayout,
                                             ListView mDrawerList, Toolbar toolbar) {
        return new CustomDrawer(context, mDrawerLayout, mDrawerList,toolbar);
    }

    //Uses SQLite database with update save or retrieve commands
    public static FactoryInterface callSQL(Context context, List<Park> parks, String saveRetrieveUpdate) {
        return new SQLDatabase(context, parks, saveRetrieveUpdate);
    }
    //Change the tabs of main tab layout
    public static FactoryInterface createTabs(Context context, TabLayout tabLayout, String[] tabNames, String[] tags) {
            return new TabMaker(context, tabLayout, tabNames, tags);
        }
}
