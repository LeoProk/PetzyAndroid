package tk.leopro.petzyandroid.utilities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.R;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;

/**
 * Add new fragment.
 */
final class AddFragment implements FactoryInterface {


    private Context mContext;

    private Fragment mFragment;

    private String mTag;

    private boolean mAddToBackStack;


    public AddFragment(Context context, Fragment fragment, String tag, boolean addToBackStack) {
        mContext = context;
        mFragment = fragment;
        mTag = tag;
        mAddToBackStack = addToBackStack;

    }

    @Override
    public Object doTask() {
        final AppController appController = (AppController) mContext.getApplicationContext();
        appController.fragmentTag = mTag;
        if (mAddToBackStack) {
            Fragment fragment = mFragment;
            FragmentManager fragmentManager = ((Activity) mContext)
                    .getFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment, mTag)
                    .addToBackStack(mTag).commit();

        } else {
            Fragment fragment = mFragment;
            FragmentManager fragmentManager = ((Activity) mContext)
                    .getFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment, mTag).commit();

        }
        return null;
    }
}
