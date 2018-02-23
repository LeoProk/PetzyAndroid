package tk.leopro.petzyandroid.utilities


import android.content.Context
import android.support.v7.app.AppCompatActivity

import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * Removes fagment by tag
 */
internal class RemoveFragment(private val mContext: Context) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        val appController = mContext.getApplicationContext() as AppController
        //Fragment fragment = ((AppCompatActivity)mContext).getSupportFragmentManager().findFragmentByTag(appController.fragmentTag);
        (mContext as AppCompatActivity).getFragmentManager().popBackStack()
        appController.fragmentTag = null
        return null
    }
}
