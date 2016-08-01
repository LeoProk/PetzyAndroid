package tk.leopro.petzyandroid.utilities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;

/**
 * Switches between 2 fragments
 */
final class FragmentSwitcher implements FactoryInterface {

    private Context mContext;

    private String mNewFragTag;


    public FragmentSwitcher(Context context, String newFragTag) {

        mNewFragTag = newFragTag;

        mContext = context;
    }

    @Override
    public Object doTask() {
        //gets the application class
        final AppController appController = (AppController) mContext.getApplicationContext();
        final AppCompatActivity activity = (AppCompatActivity) mContext;
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.remove(activity.getFragmentManager().findFragmentByTag(appController.fragmentTag));
        appController.fragmentTag = mNewFragTag;
        ft.show(activity.getFragmentManager().findFragmentByTag(mNewFragTag)).commit();


        return null;
    }
}
