package tk.leopro.petzyandroid.utilities


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.interfaces.FactoryInterface


/**
 * Checks if network connection avaliable  and if not show toast message
 */
internal class NetworkCheck(private val mContext: Context, private val mToastNeeded: Boolean) : FactoryInterface {

    // Checks if network connection avaliable  and if not show toast message
    @Override
    fun doTask(): Object {
        val cm = mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.getActiveNetworkInfo()
        if (ni == null) {
            if (mToastNeeded) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.no_connection),
                        Toast.LENGTH_LONG).show()
            }
            return false
        } else {
            return true
        }
    }
}
