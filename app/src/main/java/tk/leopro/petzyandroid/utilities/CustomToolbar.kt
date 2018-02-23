package tk.leopro.petzyandroid.utilities

import android.content.Context
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import tk.leopro.petzyandroid.interfaces.FactoryInterface

/**
 * This class contain logic of toolbar
 */
internal class CustomToolbar protected constructor(private val mContext: Context, private val mToolbar: Toolbar) : FactoryInterface {

    //replace the default action bat with toolbar
    @Override
    fun doTask(): Object? {
        val activity = mContext as AppCompatActivity
        activity.setSupportActionBar(mToolbar)
        val ab = activity.getSupportActionBar()
        ab.setDisplayHomeAsUpEnabled(true)
        ab.setDisplayShowHomeEnabled(true)
        ab.setDisplayUseLogoEnabled(true)
        /*activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);*/
        return null
    }
}
