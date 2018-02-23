package tk.leopro.petzyandroid.utilities

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * Add new fragment.
 */
internal class AddFragment(private val mContext: Context, private val mFragment: Fragment, private val mTag: String, private val mAddToBackStack: Boolean) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        val appController = mContext.getApplicationContext() as AppController
        appController.fragmentTag = mTag
        if (mAddToBackStack) {
            val fragment = mFragment
            val fragmentManager = (mContext as Activity)
                    .getFragmentManager()
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment, mTag)
                    .addToBackStack(mTag).commit()

        } else {
            val fragment = mFragment
            val fragmentManager = (mContext as Activity)
                    .getFragmentManager()
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment, mTag).commit()

        }
        return null
    }
}
