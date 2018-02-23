package tk.leopro.petzyandroid.utilities

import android.app.FragmentTransaction
import android.content.Context
import android.support.v7.app.AppCompatActivity

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * Switches between 2 fragments
 */
internal class FragmentSwitcher(private val mContext: Context, private val mNewFragTag: String) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        //gets the application class
        val appController = mContext.getApplicationContext() as AppController
        val activity = mContext as AppCompatActivity
        val ft = activity.getFragmentManager().beginTransaction()
        ft.remove(activity.getFragmentManager().findFragmentByTag(appController.fragmentTag))
        appController.fragmentTag = mNewFragTag
        ft.show(activity.getFragmentManager().findFragmentByTag(mNewFragTag)).commit()


        return null
    }
}
