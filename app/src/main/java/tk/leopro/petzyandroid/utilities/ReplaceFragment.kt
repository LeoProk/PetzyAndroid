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

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * Creates new fragment and replace the existing one/
 */
internal class ReplaceFragment(private val mContext: Context, private val mFragment: Fragment, private val mTag: String, private val mAddToBackStack: Boolean) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        val appController = mContext.getApplicationContext() as AppController
        appController.fragmentTag = mTag
        if (mAddToBackStack) {
            val fragment = mFragment
            val fragmentManager = (mContext as Activity)
                    .getFragmentManager()
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, mTag)
                    .addToBackStack(mTag).commit()

        } else {
            val fragment = mFragment
            val fragmentManager = (mContext as Activity)
                    .getFragmentManager()
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, mTag)
                    .commit()

        }
        return null
    }
}
