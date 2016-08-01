package tk.leopro.petzyandroid.utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;


/**
 * Checks if network connection avaliable  and if not show toast message
 */
final class NetworkCheck implements FactoryInterface {

    private Context mContext;

    private boolean mToastNeeded;

    public NetworkCheck(Context context, boolean toastNeeded) {
        mContext = context;
        mToastNeeded = toastNeeded;
    }

    // Checks if network connection avaliable  and if not show toast message
    @Override
    public Object doTask() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            if (mToastNeeded) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.no_connection),
                        Toast.LENGTH_LONG).show();
            }
            return false;
        } else {
            return true;
        }
    }
}
