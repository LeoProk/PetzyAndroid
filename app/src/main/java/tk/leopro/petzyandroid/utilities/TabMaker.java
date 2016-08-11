package tk.leopro.petzyandroid.utilities;

import android.app.Fragment;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import tk.leopro.petzyandroid.AppController;
import tk.leopro.petzyandroid.fragments.CatsAdopting;
import tk.leopro.petzyandroid.fragments.DogsAdopting;
import tk.leopro.petzyandroid.fragments.Found;
import tk.leopro.petzyandroid.fragments.Lost;
import tk.leopro.petzyandroid.fragments.OthersAdopting;
import tk.leopro.petzyandroid.fragments.ParksClosest;
import tk.leopro.petzyandroid.fragments.ParksMap;
import tk.leopro.petzyandroid.fragments.VetsClosest;
import tk.leopro.petzyandroid.fragments.VetsMap;
import tk.leopro.petzyandroid.interfaces.FactoryInterface;

/**
 * This Class create add tabs and on tab click.
 */
final class TabMaker implements FactoryInterface {

    private Context mContext;
    private TabLayout mTabLayout;
    private String[] mTabNames;
    private String[] mTags;
    private String mTag;
    private Fragment mFragment;

    public TabMaker(Context context, TabLayout tabLayout, String[] tabNames, String[] tags) {
        mContext = context;
        mTabLayout = tabLayout;
        mTabNames = tabNames;
        mTags = tags;

    }

    @Override
    public Object doTask() {
        final AppCompatActivity activity = (AppCompatActivity) mContext;
        mTabLayout.removeAllTabs();
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for (int i = 0; i < mTabNames.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabNames[i]));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mTag = mTags[0];
                        mFragment = getFragmentByTag(mTag);
                        break;
                    case 1:
                        mTag = mTags[1];
                        mFragment = getFragmentByTag(mTag);
                        break;
                    case 2:
                        mTag = mTags[2];
                        mFragment = getFragmentByTag(mTag);
                        break;
                    case 3:
                        mTag = mTags[3];
                        mFragment = getFragmentByTag(mTag);
                        break;
                    default:
                        break;
                }
                if (activity.getFragmentManager().findFragmentByTag(mTag) == null) {
                    //gets the application class
                    final AppController appController = (AppController) mContext.getApplicationContext();
                    appController.fragmentTag = mTag;
                    UtilitiesFactory.addFragment(mContext, mFragment, mTag, true).doTask();
                } else {
                    UtilitiesFactory.switchFragments(mContext, mTag).doTask();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return null;
    }

    final Fragment getFragmentByTag(String tag) {

        switch (tag) {
            case "dog":
                return new DogsAdopting();
            case "cat":
                return new CatsAdopting();
            case "other":
                return new OthersAdopting();
            case "lost":
                return new Lost();
            case "found":
                return new Found();
            case "parkNear":
                return new ParksClosest();
            case "parkMap":
                return new ParksMap();
            case "vetNear":
                return new VetsClosest();
            case "vetMap":
                return new VetsMap();

        }
        return null;
    }
}
