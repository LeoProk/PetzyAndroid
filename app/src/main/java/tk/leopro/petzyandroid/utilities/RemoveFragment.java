package tk.leopro.petzyandroid.utilities;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;

/**
 * Removes fagment by tag
 */
final class RemoveFragment implements FactoryInterface {

    private Context mContext;

    public RemoveFragment(Context context){
        mContext = context;
    }

    @Override
    public Object doTask() {
        final AppController appController = (AppController) mContext.getApplicationContext();
        //Fragment fragment = ((AppCompatActivity)mContext).getSupportFragmentManager().findFragmentByTag(appController.fragmentTag);
        ((AppCompatActivity)mContext).getFragmentManager().popBackStack();
        appController.fragmentTag = null;
        return null;
    }
}
