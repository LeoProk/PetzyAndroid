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
package tk.leopro.petzyandroid.utilities

import android.app.Fragment
import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.widget.ListView

import com.google.android.gms.maps.MapView

import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.pojo.FirebaseItem

/**
 * Factory for network statues , retrieving files , saving and appending new files.
 */
object UtilitiesFactory {

    //Switch between 2 fragments
    fun switchFragments(context: Context, newFragmentTag: String): FactoryInterface {
        return FragmentSwitcher(context, newFragmentTag)
    }

    //Check for network status return bool
    fun checkNetwork(context: Context, toastNeeded: Boolean): FactoryInterface {
        return NetworkCheck(context, toastNeeded)
    }

    //Gets file from external storage by name
    fun getFile(context: Context, filename: String): FactoryInterface {
        return FileManager(context, filename, "", "get")
    }

    //Save file to external storage with name and message
    fun saveFile(context: Context, filename: String, message: String): FactoryInterface {
        return FileManager(context, filename, message, "save")
    }

    //Appends message to existing file
    fun appendFile(context: Context, filename: String, message: String): FactoryInterface {
        return FileManager(context, filename, message, "append")
    }


    //Adds new fragment.
    fun addFragment(context: Context, fragment: Fragment, tag: String,
                    addToBackStack: Boolean): FactoryInterface {
        return AddFragment(context, fragment, tag, addToBackStack)
    }

    //Replace the current fragment with new fragment.
    fun replaceFragment(context: Context, fragment: Fragment, tag: String,
                        addToBackStack: Boolean): FactoryInterface {
        return ReplaceFragment(context, fragment, tag, addToBackStack)
    }

    //Removes fragment by tag
    fun removeFragment(context: Context): FactoryInterface {
        return RemoveFragment(context)
    }

    //Deletes file by name
    fun deleteFile(context: Context, filename: String): FactoryInterface {
        return FileManager(context, filename, "", "delete")
    }

    //Resets the app
    fun resetApp(context: Context): FactoryInterface {
        return AppRestart(context)
    }


    //Creates toolbar actionbar must be disabled in manifest
    fun getToolbar(context: Context, toolbar: Toolbar): FactoryInterface {
        return CustomToolbar(context, toolbar)
    }


    //Creates navigation drawer
    fun getDrawer(context: Context, mDrawerLayout: DrawerLayout,
                  mDrawerList: ListView, toolbar: Toolbar): FactoryInterface {
        return CustomDrawer(context, mDrawerLayout, mDrawerList, toolbar)
    }

    //Uses SQLite database with update save or retrieve commands
    fun callSQL(context: Context, parks: List<FirebaseItem>, saveRetrieveUpdate: String): FactoryInterface {
        return SQLDatabase(context, parks, saveRetrieveUpdate)
    }

    //Change the tabs of main tab layout
    fun createTabs(context: Context, tabLayout: TabLayout, tabNames: Array<String>, tags: Array<String>): FactoryInterface {
        return TabMaker(context, tabLayout, tabNames, tags)
    }

    //create map and show current user location
    fun getCustomMap(context: Context, mapView: MapView): FactoryInterface {
        return CustomMap(context, mapView)
    }
}
